package com.example.collegeapp.notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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

public class DeleteNoticeActivity extends AppCompatActivity {
        private RecyclerView deleteNoticeRecycler;
        private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;

    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);
         reference = Utils.getDatabase().getReference().child("Notice").child("Notice");
        deleteNoticeRecycler =findViewById(R.id.deleteNoticeRecycler);
        progressBar =findViewById(R.id.progressBar);
        deleteNoticeRecycler.setLayoutManager(new LinearLayoutManager(this));
        deleteNoticeRecycler.setHasFixedSize(true);
        getNotice();
    }

    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for(DataSnapshot dsnapshot:snapshot.getChildren()){
                    NoticeData data=dsnapshot.getValue(NoticeData.class);
                    list.add(data);
                }
                adapter=new NoticeAdapter(DeleteNoticeActivity.this,list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                deleteNoticeRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(DeleteNoticeActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}