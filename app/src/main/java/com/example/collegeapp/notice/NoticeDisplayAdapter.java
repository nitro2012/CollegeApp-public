package com.example.collegeapp.notice;

import android.app.AlertDialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoticeDisplayAdapter extends RecyclerView.Adapter<NoticeDisplayAdapter.NoticeViewAdapter> {
    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeDisplayAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public NoticeDisplayAdapter.NoticeViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout2,parent,false);


        return new NoticeDisplayAdapter.NoticeViewAdapter(view);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull NoticeDisplayAdapter.NoticeViewAdapter holder, int position) {
        NoticeData currentItem=list.get(position);
        holder.NoticeTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());

        try {
            if(currentItem.getImage()!=null)
                Picasso.get().load(currentItem.getImage()).into(holder.NoticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.downloadNotice.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setMessage("Download notice?");
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

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {
        private Button downloadNotice;
        private TextView NoticeTitle,date,time;
        private ImageView NoticeImage;
        public NoticeViewAdapter(@NonNull @NotNull View itemView)
        {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            downloadNotice=itemView.findViewById(R.id.downloadNotice);
            NoticeTitle=itemView.findViewById(R.id.NoticeTitle);
            NoticeImage=itemView.findViewById(R.id.NoticeImage);
        }
    }
}
