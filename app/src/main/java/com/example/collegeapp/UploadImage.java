package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class UploadImage extends AppCompatActivity {
    private static final int REQ = 1;
    private Spinner imageCategory;
    private CardView selectImage;
    private Button uploadImage;
    private ImageView galleryImageView;
    private String category;
    private Bitmap bitmap;
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference databaseReference, dref;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
           pd=new ProgressDialog(this);
        imageCategory=findViewById(R.id.imageCategory);
        selectImage=findViewById(R.id.uploadGalleryImage);
        uploadImage=findViewById(R.id.uploadGalleryBtn);
        databaseReference = Utils.getDatabase().getReference().child("Gallery");
        storageReference = FirebaseStorage.getInstance().getReference().child("Gallery");
galleryImageView=findViewById(R.id.galleryImageView);
        String[] items = new String[]{"Select Category", "Events", "Fests", "Workshops", "Activities", "Other Events"};
imageCategory.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,items));
imageCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
   @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category=imageCategory.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
selectImage.setOnClickListener(v -> openGallery());
uploadImage.setOnClickListener(v -> {
    if(bitmap==null)
        Toast.makeText(UploadImage.this, "Please Select an image", Toast.LENGTH_SHORT).show();
    else if(category.equals("Select Category")){
        Toast.makeText(UploadImage.this, "Please Select a Category", Toast.LENGTH_SHORT).show();
    }
    else
    {
        pd.setMessage("Uploading...");
        pd.show();
        upload();
    }
});
    }

    private void upload() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finImg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child(finImg + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finImg);
        uploadTask.addOnCompleteListener(UploadImage.this, task -> {
                    if (task.isSuccessful()) {
                        uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                            downloadUrl = String.valueOf(uri);
                            uploadData();
                        }));
                    } else {
                        pd.dismiss();
                        Toast.makeText(UploadImage.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                }
        );
    }

    private void uploadData() {
        dref=databaseReference.child(category);
        final String uniquekey=dref.push().getKey();
        assert uniquekey != null;
        dref.child(uniquekey).setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Image Uploaded!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UploadImage.this,AdminPanel.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
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
            galleryImageView.setImageBitmap(bitmap);
        }
    }
    }
