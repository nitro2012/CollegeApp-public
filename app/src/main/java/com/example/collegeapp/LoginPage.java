package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.Faculty.FacultyPanel;
import com.example.collegeapp.Student.StudentPanel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {
    EditText username, password;
    Button btnLogIn;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    ProgressDialog pd;
    String userType;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_page);
        login = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        userType = getIntent().getExtras().getString("name");
        fstore = FirebaseFirestore.getInstance();
        username = findViewById(R.id.Username_input);
        password = findViewById(R.id.Password_input);
        btnLogIn = findViewById(R.id.login_button);


    }


    public void LogInUser(View view) {

        String user = username.getText().toString();
        String pwd = password.getText().toString();
        if (user.isEmpty() && pwd.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter credentials", Toast.LENGTH_SHORT).show();
        } else if (user.isEmpty()) {
            username.setError("Please enter username");
        } else if (pwd.isEmpty()) {
            username.setError("Please enter password");
        } else {


            auth.signInWithEmailAndPassword(user, pwd).addOnSuccessListener(authResult -> {
                checkUserAccessLevel(authResult.getUser().getUid(), 0);
            }).addOnFailureListener(e ->
            {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "error " + e, Toast.LENGTH_SHORT).show();

            });


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        login.setText(userType + " Login");
        if (MainActivity.userType != null && MainActivity.userType.equals(userType)) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                checkUserAccessLevel(FirebaseAuth.getInstance().getCurrentUser().getUid(), 1);
            }

        }
    }


    private void checkUserAccessLevel(String uid, int flag) {
        pd.setMessage("Logging in...");
        pd.show();
        try {
            DocumentReference df = fstore.collection("users").document(uid);
            df.get().addOnSuccessListener(documentSnapshot -> {
                switch (userType) {
                    case "Admin":
                        if (documentSnapshot.getBoolean("isAdmin") != null && documentSnapshot.getBoolean("isAdmin")) {
                            pd.dismiss();
                            Intent intent1 = new Intent(LoginPage.this, AdminPanel.class);
                            startActivity(intent1);
                            finish();
                        } else {
                            pd.dismiss();
                            if (flag == 0)
                                auth.signOut();
                            Toast.makeText(getApplicationContext(), "Not admin!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Faculty":

                        if (documentSnapshot.getBoolean("isFaculty") != null && documentSnapshot.getBoolean("isFaculty")) {
                            pd.dismiss();
                            Intent intent2 = new Intent(LoginPage.this, FacultyPanel.class);
                            startActivity(intent2);
                            finish();
                        } else {
                            pd.dismiss();
                            if (flag == 0)
                                auth.signOut();
                            Toast.makeText(getApplicationContext(), "Not faculty!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Student":
                        if (documentSnapshot.getBoolean("isStudent") != null && documentSnapshot.getBoolean("isStudent")) {
                            pd.dismiss();
                            Intent intent3 = new Intent(LoginPage.this, StudentPanel.class);
                            startActivity(intent3);
                            finish();
                        } else {
                            pd.dismiss();
                            if (flag == 0)
                                auth.signOut();
                            Toast.makeText(getApplicationContext(), "Not Student!", Toast.LENGTH_SHORT).show();
                        }

                        break;
                }
            }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Not admin!This error", Toast.LENGTH_SHORT).show();
        }

    }

}

