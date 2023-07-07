package com.example.collegeapp.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;


import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.collegeapp.AdminPanel;

import com.example.collegeapp.LoginPage;
import com.example.collegeapp.R;

import com.example.collegeapp.Utils;
import com.google.android.gms.tasks.OnFailureListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddTeacher extends AppCompatActivity {
private ImageView addTeacherImage;
private EditText addTeacherName;
    private EditText addTeacherEmail;
    private EditText addTeacherPost;
    private Spinner addTeacherCategory;
    private Button addTeacherBtn;
    private int REQ=1;
    private Bitmap bitmap=null;
    private String category;
    private String tname,temail,tpost,tdownloadUrl;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth2,mAuth1;
    private FirebaseFirestore fstore;
    private DatabaseReference dref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        progressDialog = new ProgressDialog(this);
        mAuth1 = FirebaseAuth.getInstance();

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()

                .setApiKey("<your api key from firebase>")
                .setApplicationId("<your appid from firebase>").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }


        fstore=FirebaseFirestore.getInstance();
        databaseReference = Utils.getDatabase().getReference().child("Teacher");
        storageReference = FirebaseStorage.getInstance().getReference();
        addTeacherImage=findViewById(R.id.addTeacherImage);
        addTeacherName=findViewById(R.id.addTeacherName);
        addTeacherEmail=findViewById(R.id.addTeacherEmail);
        addTeacherPost=findViewById(R.id.addTeacherPost);
        addTeacherCategory=findViewById(R.id.addTeacherCategory);
        addTeacherBtn=findViewById(R.id.addTeacherBtn);

        String[] items = new String[]{"Select Category", "Computer Science", "Mechanical", "Civil", "Electrical"};
        
        addTeacherCategory.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,items));
        addTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=addTeacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addTeacherImage.setOnClickListener(v -> openGallery());
        addTeacherBtn.setOnClickListener(v -> checkValidation());
    }

    private void checkValidation() {
        tname=addTeacherName.getText().toString();
        temail=addTeacherEmail.getText().toString();
        tpost=addTeacherPost.getText().toString();
        if(tname.isEmpty()){
            addTeacherName.setError("Empty");
            addTeacherName.requestFocus();
        }
        else if(temail.isEmpty()){
            addTeacherEmail.setError("Empty");
            addTeacherEmail.requestFocus();
        }
        else if(tpost.isEmpty()){
            addTeacherPost.setError("Empty");
            addTeacherPost.requestFocus();
        }
        else if(category.equals("Select Category")){
            Toast.makeText(this, "Please provide Teacher category ", Toast.LENGTH_SHORT).show();

        }
        else if(bitmap==null){
            insertData();

        }
        else
        {
            uploadImage();
        }
    }

    private void insertData() {



            
        try {
            mAuth2.createUserWithEmailAndPassword(temail,"qwerty").addOnSuccessListener(authResult -> {
                FirebaseUser user=mAuth2.getCurrentUser();
                DocumentReference df=fstore.collection("users").document(user.getUid());
                String uid=user.getUid();
                dref=databaseReference.child(category);
                Map<String,Object> userInfo=new HashMap<>();
                userInfo.put("Name",tname);
                userInfo.put("UserEmail",temail);
                userInfo.put("isFaculty",true);
                userInfo.put("category",category);

                df.set(userInfo);
                ArrayList <String>classId;
                classId=null;
                TeacherData teacherData = new TeacherData(tname, temail, tpost,tdownloadUrl,uid,classId);
                dref.child(uid).setValue(teacherData).addOnSuccessListener(aVoid -> {
                    progressDialog.dismiss();


                    Toast.makeText(AddTeacher.this, "Teacher Successfully Registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTeacher.this, AdminPanel.class));


                    finish();


                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(AddTeacher.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                });
            });

        }catch (Exception e){
            progressDialog.dismiss();
            Toast.makeText(AddTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        }


    private void uploadImage() {
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finImg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Teachers").child(finImg + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finImg);
        uploadTask.addOnCompleteListener(AddTeacher.this, task -> {
                    if (task.isSuccessful()) {
                        uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                            tdownloadUrl = String.valueOf(uri);
                            insertData();
                        }));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(AddTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                }
        );
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

            addTeacherImage.setImageBitmap(bitmap);
        }
    }
}