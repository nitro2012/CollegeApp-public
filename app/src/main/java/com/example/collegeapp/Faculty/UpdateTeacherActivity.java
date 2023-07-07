package com.example.collegeapp.Faculty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacherActivity extends AppCompatActivity {
    private final int REQ = 1;
    private ImageView updateTeacherImage;
    private EditText updateTeacherName, updateTeacherPost, updateTeacherEmail;
    private Button updateTeacherBtn, deleteTeacherBtn;
    private String name, image, post, email;
    private Bitmap bitmap = null;
    private StorageReference storageReference;
    private String downloadUrl, category, ukey;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);
        storageReference = FirebaseStorage.getInstance().getReference();
        reference = Utils.getDatabase().getReference().child("Teacher");
        ukey = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("category");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        post = getIntent().getStringExtra("post");
        image = getIntent().getStringExtra("image");
        updateTeacherImage = findViewById(R.id.updateTeacherImage);
        updateTeacherName = findViewById(R.id.updateTeacherName);
        updateTeacherPost = findViewById(R.id.updateTeacherPost);
        updateTeacherEmail = findViewById(R.id.updateTeacherEmail);
        deleteTeacherBtn = findViewById(R.id.DeleteTeacherButton);
        updateTeacherBtn = findViewById(R.id.UpdateTeacherButton);
        try {
            if (image != null)
                Picasso.get().load(image).into(updateTeacherImage);
            else
                Picasso.get().load(R.drawable.teach_icon).into(updateTeacherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateTeacherEmail.setText(email);
        updateTeacherName.setText(name);
        updateTeacherPost.setText(post);
        updateTeacherImage.setOnClickListener(v -> openGallery());
        updateTeacherBtn.setOnClickListener(v -> {
            name = updateTeacherName.getText().toString();
            email = updateTeacherEmail.getText().toString();
            post = updateTeacherPost.getText().toString();
            checkValidation();
        });
        deleteTeacherBtn.setOnClickListener(v -> deleteData());


    }

    private void deleteData() {
        reference.child(category).child(ukey).removeValue().addOnCompleteListener(task -> {
            Toast.makeText(UpdateTeacherActivity.this, "Teacher deleted successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateTeacherActivity.this, UpdateFaculty.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }).addOnFailureListener(e -> Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show());
    }

    private void checkValidation() {
        if (name.isEmpty()) {
            updateTeacherName.setError("Empty");
            updateTeacherName.requestFocus();

        } else if (post.isEmpty()) {
            updateTeacherPost.setError("Empty");
            updateTeacherPost.requestFocus();

        } else if (email.isEmpty()) {
            updateTeacherEmail.setError("Empty");
            updateTeacherEmail.requestFocus();

        } else if (bitmap == null) {
            updateData(image);
        } else {
            uploadImage();
        }

    }

    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finImg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Teachers").child(finImg + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finImg);
        uploadTask.addOnCompleteListener(UpdateTeacherActivity.this, task -> {
                    if (task.isSuccessful()) {
                        uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                            downloadUrl = String.valueOf(uri);
                            updateData(downloadUrl);
                        }));
                    } else {
                        //progressDialog.dismiss();
                        Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                }
        );
    }

    private void updateData(String s) {
        HashMap<String, Object> hp = new HashMap<String, Object>();
        hp.put("name", name);
        hp.put("email", email);
        hp.put("post", post);
        hp.put("image", s);

        reference.child(category).child(ukey).updateChildren(hp).addOnSuccessListener(o -> {
            Toast.makeText(UpdateTeacherActivity.this, "Teacher updated successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateTeacherActivity.this, UpdateFaculty.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }).addOnFailureListener(e -> Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show());
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
            updateTeacherImage.setImageBitmap(bitmap);
        }
    }
}