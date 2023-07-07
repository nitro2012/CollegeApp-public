package com.example.collegeapp.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {
FloatingActionButton fab;
private RecyclerView csDepartment,mechanicalDepartment,civilDepartment,electricalDepartment;
private LinearLayout csNoData,mechNoData,civilNoData,electricalNoData;
private List<TeacherData> list1,list2,list3,list4;
private DatabaseReference reference,dbRef;
private TeacherAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);
        csDepartment=findViewById(R.id.csDepartment);
                mechanicalDepartment=findViewById(R.id.mechDepartment);
        civilDepartment =findViewById(R.id.civilDepartment);
                electricalDepartment=findViewById(R.id.electricalDepartment);
        csNoData=findViewById(R.id.csNoData);
        civilNoData=findViewById(R.id.civilNoData);
        mechNoData=findViewById(R.id.mechNoData);
        electricalNoData=findViewById(R.id.electricalNoData);
        reference= Utils.getDatabase().getReference().child("Teacher");
        csDepartment();
        mechanicalDepartment();
        civilDepartment();
        electricalDepartment();
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(UpdateFaculty.this,AddTeacher.class)));
    }

    private void csDepartment() {
        dbRef=reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
list1=new ArrayList<>();
if(!snapshot.exists()){
    csNoData.setVisibility(View.VISIBLE);
    csDepartment.setVisibility(View.GONE);
}
else
{
    csNoData.setVisibility(View.GONE);
    csDepartment.setVisibility(View.VISIBLE);
    for(DataSnapshot dsnapshot:snapshot.getChildren()){
        TeacherData data=dsnapshot.getValue(TeacherData.class);


        list1.add(data);
    }


    csDepartment.setHasFixedSize(true);
    csDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
adapter=new TeacherAdapter(list1,UpdateFaculty.this,"Computer Science");
csDepartment.setAdapter(adapter);
}
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mechanicalDepartment() {
        dbRef=reference.child("Mechanical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
list2=new ArrayList<>();
if(!snapshot.exists()){
    mechNoData.setVisibility(View.VISIBLE);
    mechanicalDepartment.setVisibility(View.GONE);
}
else
{
    mechNoData.setVisibility(View.GONE);
    mechanicalDepartment.setVisibility(View.VISIBLE);
    for(DataSnapshot dsnapshot:snapshot.getChildren()){
        TeacherData data=dsnapshot.getValue(TeacherData.class);
        list2.add(data);
    }
    mechanicalDepartment.setHasFixedSize(true);
    mechanicalDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
adapter=new TeacherAdapter(list2,UpdateFaculty.this,"Mechanical");
mechanicalDepartment.setAdapter(adapter);
}
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void civilDepartment() {
        dbRef=reference.child("Civil");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
list3=new ArrayList<>();
if(!snapshot.exists()){
    civilNoData.setVisibility(View.VISIBLE);
    civilDepartment.setVisibility(View.GONE);
}
else
{
    civilNoData.setVisibility(View.GONE);
    civilDepartment.setVisibility(View.VISIBLE);
    for(DataSnapshot dsnapshot:snapshot.getChildren()){
        TeacherData data=dsnapshot.getValue(TeacherData.class);
        list3.add(data);
    }
    civilDepartment.setHasFixedSize(true);
    civilDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
adapter=new TeacherAdapter(list3,UpdateFaculty.this,"Civil");
civilDepartment.setAdapter(adapter);
}
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void electricalDepartment() {
        dbRef=reference.child("Electrical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
list4=new ArrayList<>();
if(!snapshot.exists()){
    electricalNoData.setVisibility(View.VISIBLE);
    electricalDepartment.setVisibility(View.GONE);
}
else
{
    electricalNoData.setVisibility(View.GONE);
    electricalDepartment.setVisibility(View.VISIBLE);
    for(DataSnapshot dsnapshot:snapshot.getChildren()){
        TeacherData data=dsnapshot.getValue(TeacherData.class);
        list4.add(data);
    }
    electricalDepartment.setHasFixedSize(true);
    electricalDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
adapter=new TeacherAdapter(list4,UpdateFaculty.this,"Electrical");
electricalDepartment.setAdapter(adapter);
}
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}