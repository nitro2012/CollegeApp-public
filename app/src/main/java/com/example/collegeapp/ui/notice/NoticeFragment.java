package com.example.collegeapp.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.collegeapp.Utils;
import com.example.collegeapp.notice.NoticeData;
import com.example.collegeapp.R;
import com.example.collegeapp.notice.NoticeDisplayAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class NoticeFragment extends Fragment {
    private RecyclerView NoticeRecycler;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeDisplayAdapter adapter;

    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notice, container, false);


        reference = Utils.getDatabase().getReference().child("Notice").child("Notice");

        NoticeRecycler =view.findViewById(R.id.NoticeRecycler);
        progressBar =view.findViewById(R.id.progressbar);
        NoticeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        NoticeRecycler.setHasFixedSize(true);
        getNotice();
        // Inflate the layout for this fragment
        return view;

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
                adapter=new NoticeDisplayAdapter(getActivity(),list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                NoticeRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}