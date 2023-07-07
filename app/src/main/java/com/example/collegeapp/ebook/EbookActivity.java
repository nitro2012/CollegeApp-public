package com.example.collegeapp.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EbookActivity extends AppCompatActivity {
private RecyclerView eBookRecycler;
private DatabaseReference reference;
private List<ebookData> list;
private ebookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        eBookRecycler=findViewById(R.id.eBookRecycler);
        reference= Utils.getDatabase().getReference().child("E-learning");

        getEbook();

    }

    private void getEbook() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.add(dataSnapshot.getValue(ebookData.class));
                }
                adapter=new ebookAdapter(EbookActivity.this,list);
                adapter.notifyDataSetChanged();
                eBookRecycler.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
                eBookRecycler.setHasFixedSize(true);
                eBookRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(EbookActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}