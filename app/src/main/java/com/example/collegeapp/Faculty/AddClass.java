package com.example.collegeapp.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegeapp.AdminPanel;
import com.example.collegeapp.R;
import com.example.collegeapp.Utils;
import com.example.collegeapp.notice.UploadNotice;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddClass extends AppCompatActivity {
    private EditText subject,section;
private  String uniqueKey;
    private DatabaseReference databaseReference,teacherReference;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        uid=getIntent().getStringExtra("id");
        String category=getIntent().getStringExtra("category");
        databaseReference = Utils.getDatabase().getReference().child("Classes");
        teacherReference = Utils.getDatabase().getReference().child("Teacher").child(category);//.child(uid);
        subject=findViewById(R.id.subject);
        section=findViewById(R.id.section);

    }

    public void createClass(View view) {


        if(subject.getText().toString().isEmpty()){
            subject.setError("Empty");
            subject.requestFocus();
        }
        else if(section.getText().toString().isEmpty()){
            section.setError("Empty");
            section.requestFocus();
        }
        else
        {
            uploadData();
        }

    }

    private void uploadData() {
        uniqueKey = databaseReference.push().getKey();
        classesData classData = new classesData(subject.getText().toString(),section.getText().toString(),uid,uniqueKey,null);
        databaseReference.child(uniqueKey).setValue(classData).addOnSuccessListener(unused -> {
            Toast.makeText(AddClass.this, "Class created", Toast.LENGTH_SHORT).show();
            updateTeacher();
            /*startActivity(new Intent(AddClass.this, AdminPanel.class));
            finish();*/
        }).addOnFailureListener(e -> {

            Toast.makeText(AddClass.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
        });

    }

    private void updateTeacher() {

        teacherReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                HashMap<String, Object> hp = new HashMap<String, Object>();
                ArrayList<String> classIdArr;

                TeacherData td = snapshot.getValue(TeacherData.class);
                classIdArr = td.getClassIdArr();
                if(classIdArr==null)
               classIdArr=new ArrayList<>();
                    classIdArr.add(uniqueKey);
//

                TeacherData post = new TeacherData(td.getName(),td.getEmail(),td.getPost(),td.getImage(),td.getKey(),classIdArr);
                Map<String, Object> postValues = post.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/"+uid, postValues);

                teacherReference.updateChildren(childUpdates).addOnSuccessListener(o -> {

                    Intent intent = new Intent(AddClass.this, FacultyPanel.class);

                    startActivity(intent);
                }).addOnFailureListener(e -> Toast.makeText(AddClass.this, "Something went wrong", Toast.LENGTH_SHORT).show());




            }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });




    }
}