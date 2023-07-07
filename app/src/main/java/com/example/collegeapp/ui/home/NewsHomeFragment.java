package com.example.collegeapp.ui.home;

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

import com.example.collegeapp.R;
import com.example.collegeapp.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewsHomeFragment extends Fragment {
    private RecyclerView NewsRecycler;
    private ProgressBar progressBar;
    private ArrayList<NewsData> list;
    private NewsDisplayAdapter adapter;

    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news_home, container, false);


        reference = Utils.getDatabase().getReference().child("Notice").child("News Feed");

        NewsRecycler =view.findViewById(R.id.NewsRecycler);
        progressBar =view.findViewById(R.id.newsprogressbar);
        NewsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        NewsRecycler.setHasFixedSize(true);
        getNews();
        // Inflate the layout for this fragment
        return view;

    }
    private void getNews() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list=new ArrayList<NewsData>();
                for(DataSnapshot dsnapshot:snapshot.getChildren()){
                    NewsData data=dsnapshot.getValue(NewsData.class);
                    list.add(data);
                }
                adapter=new NewsDisplayAdapter(getActivity(),list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                NewsRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}