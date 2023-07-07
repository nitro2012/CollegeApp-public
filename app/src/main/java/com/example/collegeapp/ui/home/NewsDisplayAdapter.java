package com.example.collegeapp.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.notice.NoticeData;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsDisplayAdapter extends RecyclerView.Adapter<com.example.collegeapp.ui.home.NewsDisplayAdapter.NewsViewAdapter> {
    private Context context;
    private ArrayList<NewsData> list;

    public NewsDisplayAdapter(Context context, ArrayList<NewsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public com.example.collegeapp.ui.home.NewsDisplayAdapter.NewsViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout,parent,false);


        return new com.example.collegeapp.ui.home.NewsDisplayAdapter.NewsViewAdapter(view);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull com.example.collegeapp.ui.home.NewsDisplayAdapter.NewsViewAdapter holder, int position) {
        NewsData currentItem=list.get(position);
        holder.NewsTitle.setText(currentItem.getTitle());
        holder.datenews.setText(currentItem.getDate());
        holder.timenews.setText(currentItem.getTime());

        try {
            if(currentItem.getImage()!=null)
                Picasso.get().load(currentItem.getImage()).into(holder.NewsImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.readmoreNews.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setMessage("Want to read more about it?");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", (dialog, which) -> {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            AlertDialog dialog=null;
            try {
                dialog=builder.create();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(dialog!=null)
                dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsViewAdapter extends RecyclerView.ViewHolder {
        private Button readmoreNews;
        private TextView NewsTitle,datenews,timenews;
        private ImageView NewsImage;
        public NewsViewAdapter(@NonNull @NotNull View itemView)
        {
            super(itemView);
            datenews=itemView.findViewById(R.id.datenews);
            timenews=itemView.findViewById(R.id.timenews);
            readmoreNews=itemView.findViewById(R.id.readmoreNews);
            NewsTitle=itemView.findViewById(R.id.NewsTitle);
            NewsImage=itemView.findViewById(R.id.NewsImage);
        }
    }
}

