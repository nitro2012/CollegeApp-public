package com.example.collegeapp.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.collegeapp.R;
import com.example.collegeapp.Student.StudentPanel;
import com.example.collegeapp.Utils;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class viewClass extends AppCompatActivity {
private TextView className,section,clink;
String classId;
private RecyclerView classRec;
DatabaseReference ref;
String name;
    private feedAdapter adapter;
    private ArrayList<feedData> list;
    private FirebaseFirestore fstore;
    private MaterialCardView tapShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);
        className=findViewById(R.id.cname1);
        section=findViewById(R.id.sec1);
        clink=findViewById(R.id.clink);
        tapShare=findViewById(R.id.tapShare);
        classRec=findViewById(R.id.classRec);
        classId=getIntent().getStringExtra("id");
        ref= Utils.getDatabase().getReference().child("Classes").child(classId);
        fstore= FirebaseFirestore.getInstance();
        DocumentReference df=fstore.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        df.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {
                    name=document.getString("Name");
                    tapShare.setOnClickListener(v -> Openshare());
                    setVals();
                } else {
                    Log.d("thisone", "No such document");
                    tapShare.setOnClickListener(v -> Openshare());

                }
            }else {
                tapShare.setOnClickListener(v -> Openshare());

                Log.d("TAG", "get failed with ", task.getException());
            }

        });




        //TODO finish

    }
    @Override
    public void onResume(){
        super.onResume();
        setVals();

    }
    private void setVals() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                classesData data=snapshot.getValue(classesData.class);
                assert data != null;
                className.setText(data.getClassName());
                section.setText(data.getSection());
                clink.setText(data.getClassId());
                list=new ArrayList<>();
                if(snapshot.hasChild("fData")){
                    ArrayList fList= (ArrayList) snapshot.child("fData").getValue();
                    for(Object hm:fList){
                        HashMap hass=(HashMap) hm;
                        String text= (String) hass.get("text");
                        String time= (String) hass.get("time");
                        String date= (String) hass.get("date");
                        String author= (String) hass.get("author");
                        List<String> urlList= (List<String>) hass.get("urlList");
                        feedData fD=new feedData(text,time,date,author,urlList);

                                list.add(fD);
                    }

                    classRec.setVisibility(View.VISIBLE);
                   // List<feedData> fData=data.getData();

                    classRec.setHasFixedSize(false);
                    classRec.setLayoutManager(new LinearLayoutManager(viewClass.this));
                    adapter = new feedAdapter(list, viewClass.this);
                    adapter.notifyDataSetChanged();
                    classRec.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void share(View view) {
    }

    public void Openshare() {

        Intent intent=new Intent(viewClass.this,ShareClass.class);
        intent.putExtra("id",classId);
        intent.putExtra("name",name);
        startActivity(intent);
    }
}