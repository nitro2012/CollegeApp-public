package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class FullPage extends AppCompatActivity {
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_page);
        image=findViewById(R.id.imageV);
        Intent intent=getIntent();
        String im=intent.getStringExtra("image");
        Glide.with(FullPage.this).load(im).into(image);

    }
}