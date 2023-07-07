package com.example.collegeapp.ebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ebookAdapter extends RecyclerView.Adapter<ebookAdapter.ebookViewHolder> {
private Context context;
private List<ebookData> list;

    public ebookAdapter(Context context, List<ebookData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ebookViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item_layout,parent,false);

        return new ebookViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ebookViewHolder holder, int position) {
        holder.eBookName.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent1=new Intent(context,pdfViewer.class);
            intent1.putExtra("url",list.get(position).getPdfUrl());

            context.startActivity(intent1);
        });
        holder.eBookDownload.setOnClickListener(v -> {
            Intent  intent =new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(list.get(position).getPdfUrl()));

            context.startActivity(intent);
        });
    }

    public class ebookViewHolder extends RecyclerView.ViewHolder {
        private TextView eBookName;
        private ImageView eBookDownload;
        public ebookViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            eBookName=itemView.findViewById(R.id.eBookName);
            eBookDownload=itemView.findViewById(R.id.eBookDownload);
        }
    }
}
