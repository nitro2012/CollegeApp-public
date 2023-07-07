package com.example.collegeapp.Faculty;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {
    private List<TeacherData> list;
    private Context context;
    private String category;
    public TeacherAdapter(List<TeacherData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }

    @NonNull
    @NotNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout,parent,false);

        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TeacherViewAdapter holder, int position) {
TeacherData item=list.get(position);
holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try {
            if(item.getImage()==null){
                Picasso.get().load(R.drawable.teach_icon).into(holder.imageView);
            }
            else
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {

            e.printStackTrace();
        }

        holder.update.setOnClickListener(v -> {
            Intent intent=new Intent(context,UpdateTeacherActivity.class);
            intent.putExtra("name",item.getName());
            intent.putExtra("email",item.getEmail());
            intent.putExtra("post",item.getPost());
            intent.putExtra("image",item.getImage());
            intent.putExtra("key",item.getKey());
            intent.putExtra("category",category);

            context.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {
private TextView name,email,post;
Button update;
ImageView imageView;
        public TeacherViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.teacherName);
            email=itemView.findViewById(R.id.teacherEmail);
            post=itemView.findViewById(R.id.teacherPost);
            imageView=itemView.findViewById(R.id.teacherImage);
            update=itemView.findViewById(R.id.teacherUpdateBtn);
        }
    }
}
