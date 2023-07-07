package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.collegeapp.Faculty.UpdateFaculty;
import com.example.collegeapp.Student.AddStudent;
import com.example.collegeapp.notice.DeleteNoticeActivity;
import com.example.collegeapp.notice.UploadNotice;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener {
    CardView uploadNotice,galleryImage,uploadPdf,addfaculty,addStudent,deleteNotices;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_admin_panel);
        addfaculty=findViewById(R.id.faculty);
        addStudent=findViewById(R.id.addStudent);
        deleteNotices=findViewById(R.id.deleteNotices);
        uploadPdf=findViewById(R.id.addeLearningResource);
        uploadNotice = findViewById(R.id.addNotice);
        uploadNotice.setOnClickListener(this);
        galleryImage = findViewById(R.id.addGalleryImage);
        galleryImage.setOnClickListener(this);
        uploadPdf.setOnClickListener(this);
        addfaculty.setOnClickListener(this);
        addStudent.setOnClickListener(this);
        deleteNotices.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNotice:
                startActivity(new Intent(AdminPanel.this, UploadNotice.class));
                break;
            case R.id.addGalleryImage:
                startActivity(new Intent(AdminPanel.this, UploadImage.class));
                break;
            case R.id.addeLearningResource:
                startActivity(new Intent(AdminPanel.this, UploadPdf.class));
                break;
            case R.id.faculty:
                startActivity(new Intent(AdminPanel.this, UpdateFaculty.class));
                break;
            case R.id.deleteNotices:
                startActivity(new Intent(AdminPanel.this, DeleteNoticeActivity.class));
                break;
            case R.id.addStudent:
                startActivity(new Intent(AdminPanel.this, AddStudent.class));
                break;
        }

    }

    public void LogOut(View view) {
        auth.signOut();

        startActivity(new Intent(AdminPanel.this, MainActivity.class));
        finish();
    }
}