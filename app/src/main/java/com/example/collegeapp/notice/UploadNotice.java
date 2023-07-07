package com.example.collegeapp.notice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.collegeapp.AdminPanel;
import com.example.collegeapp.R;
import com.example.collegeapp.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadNotice extends AppCompatActivity {
    private CardView addImage;
    private ImageView noticeImage;
    private Spinner noticeCategory;
    private final int REQ = 1;
    private Bitmap bitmap;
    private String category;
    private ProgressDialog progressDialog;
    private EditText noticeTitle;
    private Button noticeButton;
    private DatabaseReference databaseReference, dReference;
    private StorageReference storageReference;
    private String downloadUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);
        addImage = findViewById(R.id.uploadNoticeImage);
        noticeImage = findViewById(R.id.NoticeImageView);
        noticeTitle = findViewById(R.id.noticeTitle);
        noticeCategory = findViewById(R.id.noticeCategory);
        String[] items = new String[]{"Select Category", "Notice", "News Feed"};
        noticeCategory.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,items));
        noticeCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=noticeCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        progressDialog = new ProgressDialog(this);
        databaseReference = Utils.getDatabase().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        noticeButton = findViewById(R.id.uploadNoticeBtn);
        noticeButton.setOnClickListener(v -> {
            if (noticeTitle.getText().toString().isEmpty()) {
                noticeTitle.setError("Please give a notice title or a headline");
                noticeTitle.requestFocus();
            } else if (bitmap == null) {
                uploadData();
            } else if(category.equals("Select Category")) {
                Toast.makeText(UploadNotice.this, "Please Select a Category", Toast.LENGTH_SHORT).show();

            } else
                uploadImage();
        });
        addImage.setOnClickListener(v -> openGallery());
    }

    private void uploadImage() {
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finImg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Notice").child(finImg + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finImg);
        uploadTask.addOnCompleteListener(UploadNotice.this, task -> {
                    if (task.isSuccessful()) {
                        uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                            downloadUrl = String.valueOf(uri);
                            uploadData();
                        }));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UploadNotice.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                }
        );
    }

    private void uploadData() {
        dReference = databaseReference.child("Notice").child(category);
        final String uniqueKey = dReference.push().getKey();
        Calendar calForDate = Calendar.getInstance();
        String title = noticeTitle.getText().toString();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calForDate.getTime());
        Calendar calForTime = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());
        NoticeData noticeData = new NoticeData(title, downloadUrl, date, time, uniqueKey);
        dReference.child(uniqueKey).setValue(noticeData).addOnSuccessListener(aVoid -> {
            progressDialog.dismiss();
            Toast.makeText(UploadNotice.this, "Feed Uploaded", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UploadNotice.this, AdminPanel.class));
            finish();
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(UploadNotice.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "error " + e, Toast.LENGTH_SHORT);
            }
            noticeImage.setImageBitmap(bitmap);
        }
    }
}