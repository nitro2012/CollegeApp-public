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
import com.example.collegeapp.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {
    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout,parent,false);


        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoticeViewAdapter holder, int position) {
        NoticeData currentItem=list.get(position);
        holder.deleteNoticeTitle.setText(currentItem.getTitle());
        try {
            if(currentItem.getImage()!=null)
            Picasso.get().load(currentItem.getImage()).into(holder.deleteNoticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.deleteNotice.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want you delete this feed?");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", (dialog, which) -> {
                DatabaseReference reference = Utils.getDatabase().getReference().child("Notice").child("Notice");
                reference.child(currentItem.getKey()).removeValue()
                        .addOnCompleteListener(task -> Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                notifyItemRemoved(position);
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
        private Button deleteNotice;
        private TextView deleteNoticeTitle;
        private ImageView deleteNoticeImage;
        public NoticeViewAdapter(@NonNull @NotNull View itemView)
        {
            super(itemView);
            deleteNotice=itemView.findViewById(R.id.deleteNotice);
            deleteNoticeTitle=itemView.findViewById(R.id.deleteNoticeTitle);
            deleteNoticeImage=itemView.findViewById(R.id.deleteNoticeImage);
        }
    }
}
