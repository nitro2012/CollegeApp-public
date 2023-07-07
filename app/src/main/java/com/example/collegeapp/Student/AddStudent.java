package com.example.collegeapp.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.collegeapp.AdminPanel;
import com.example.collegeapp.Faculty.AddTeacher;
import com.example.collegeapp.Faculty.TeacherData;
import com.example.collegeapp.R;
import com.example.collegeapp.Utils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity {

    private EditText addStudentName;
    private EditText addStudentEmail;
    private EditText addStudentRoll;
    private Spinner addStudentCategory;
    private Button addStudentBtn;

    private String category;
    private String sname,semail,sroll;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth2;
    private FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        progressDialog = new ProgressDialog(this);


        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()

                .setApiKey("<your apikey from firebase>")
                .setApplicationId("<your appid from firebase>").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }


        fstore= FirebaseFirestore.getInstance();
        databaseReference = Utils.getDatabase().getReference().child("Student");


        addStudentName=findViewById(R.id.addStudentName);
        addStudentEmail=findViewById(R.id.addStudentEmail);
        addStudentRoll=findViewById(R.id.addStudentRoll);
        addStudentCategory=findViewById(R.id.addStudentCategory);
        addStudentBtn=findViewById(R.id.addStudentBtn);

        String[] items = new String[]{"Select Category", "Computer Science", "Mechanical", "Civil", "Electrical"};

        addStudentCategory.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items));
        addStudentCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=addStudentCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addStudentBtn.setOnClickListener(v -> checkValidation());
    }

    private void checkValidation() {
        sname=addStudentName.getText().toString();
        semail=addStudentEmail.getText().toString();
        sroll=addStudentRoll.getText().toString();
        if(sname.isEmpty()){
            addStudentName.setError("Empty");
            addStudentName.requestFocus();
        }
        else if(semail.isEmpty()){
            addStudentEmail.setError("Empty");
            addStudentEmail.requestFocus();
        }
        else if(sroll.isEmpty()){
            addStudentRoll.setError("Empty");
            addStudentRoll.requestFocus();
        }
        else if(category.equals("Select Category")){
            Toast.makeText(this, "Please provide Student category ", Toast.LENGTH_SHORT).show();

        }

        else
        {
            insertData();
        }
    }
    private void insertData() {




        try {
            mAuth2.createUserWithEmailAndPassword(semail,"qwerty").addOnSuccessListener(authResult -> {
                FirebaseUser user=mAuth2.getCurrentUser();
                DocumentReference df=fstore.collection("users").document(user.getUid());
                String uid=user.getUid();

                Map<String,Object> userInfo=new HashMap<>();
                userInfo.put("Name",sname);
                userInfo.put("UserEmail",semail);
                userInfo.put("isStudent",true);


                df.set(userInfo);
                ArrayList<String> classId;
                classId=null;
                StudentData studentData = new StudentData(sname, semail, sroll,uid,category,classId);
                databaseReference.child(uid).setValue(studentData).addOnSuccessListener(aVoid -> {
                    progressDialog.dismiss();


                    Toast.makeText(AddStudent.this, "Student Successfully Registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddStudent.this, AdminPanel.class));


                    finish();


                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(AddStudent.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                });
            });

        }catch (Exception e){
            progressDialog.dismiss();
            Toast.makeText(AddStudent.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }


    }

}