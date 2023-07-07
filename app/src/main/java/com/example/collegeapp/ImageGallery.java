package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ImageGallery extends AppCompatActivity {
RecyclerView eventRecyclerView,festRecyclerView,workshopRecyclerView,activityRecyclerView,otherRecyclerView;
GalleryAdapter adapter;
DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        eventRecyclerView=findViewById(R.id.eventRecycler);
        festRecyclerView=findViewById(R.id.festRecycler);
        workshopRecyclerView=findViewById(R.id.workshopRecycler);
        activityRecyclerView=findViewById(R.id.activityRecycler);
        otherRecyclerView=findViewById(R.id.otherRecycler);

        reference= Utils.getDatabase().getReference().child("Gallery");

        getWorkshopImage();
        getFestImage();
        getEventImage();
        getActivityImage();
        getOtherImage();
    }

    private void getFestImage() {
        reference.child("Fests").addValueEventListener(new ValueEventListener() {
            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data=(String)dataSnapshot.getValue();
                    imageList.add(data);
                }
                if(imageList.size()!=0)
                { adapter=new GalleryAdapter(ImageGallery.this,imageList);
                festRecyclerView.setLayoutManager(new GridLayoutManager(ImageGallery.this,3));
                festRecyclerView.setAdapter(adapter);}

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void getEventImage() {
        reference.child("Events").addValueEventListener(new ValueEventListener() {
            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data=(String)dataSnapshot.getValue();
                    imageList.add(data);
                }
                if(imageList.size()!=0){
                adapter=new GalleryAdapter(ImageGallery.this,imageList);
                eventRecyclerView.setLayoutManager(new GridLayoutManager(ImageGallery.this,3));
                eventRecyclerView.setAdapter(adapter);}

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void getWorkshopImage() {
        reference.child("Workshops").addValueEventListener(new ValueEventListener() {
            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data=(String)dataSnapshot.getValue();
                    imageList.add(data);
                }
                if(imageList.size()!=0){
                adapter=new GalleryAdapter(ImageGallery.this,imageList);
                workshopRecyclerView.setLayoutManager(new GridLayoutManager(ImageGallery.this,3));
                    workshopRecyclerView.setAdapter(adapter);}

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void getActivityImage() {
        reference.child("Activities").addValueEventListener(new ValueEventListener() {
            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data=(String)dataSnapshot.getValue();
                    imageList.add(data);
                }
                if(imageList.size()!=0){
                adapter=new GalleryAdapter(ImageGallery.this,imageList);
                activityRecyclerView.setLayoutManager(new GridLayoutManager(ImageGallery.this,3));
                activityRecyclerView.setAdapter(adapter);}

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void getOtherImage() {
        reference.child("Other Events").addValueEventListener(new ValueEventListener() {
            List<String> imageList=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data=(String)dataSnapshot.getValue();
                    imageList.add(data);
                }
                if(imageList.size()!=0){
                adapter=new GalleryAdapter(ImageGallery.this,imageList);
                otherRecyclerView.setLayoutManager(new GridLayoutManager(ImageGallery.this,3));
                otherRecyclerView.setAdapter(adapter);}

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}