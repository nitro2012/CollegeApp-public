package com.example.collegeapp.ui.faculty;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.Faculty.TeacherData;
import com.example.collegeapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewAdapter> {
    private List<TeacherData> list;
    private Context context;
    private String category;
    public FacultyAdapter(List<TeacherData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }

    @NonNull
    @NotNull
    @Override
    public FacultyViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.teacherviewlayout,parent,false);

        return new FacultyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FacultyViewAdapter holder, int position) {
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

        


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FacultyViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,email,post;

        ImageView imageView;
        public FacultyViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.teacherName);
            email=itemView.findViewById(R.id.teacherEmail);
            post=itemView.findViewById(R.id.teacherPost);

            imageView=itemView.findViewById(R.id.teacherImage);
            
        }
    }
}
