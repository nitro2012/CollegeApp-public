package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class UploadPdf extends AppCompatActivity {
    private CardView addPdf;
    private final int REQ = 1;
    private ProgressDialog pd;
    private EditText pdfTitle;
    private Button pdfButton;
    private Uri pdfData;
    private DatabaseReference databaseReference, dref;
    private TextView pdfTextView;
    private StorageReference storageReference;
    private String downloadUrl = "";
    private String pdfName;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);
        pd = new ProgressDialog(this);
        databaseReference = Utils.getDatabase().getReference().child("E-learning");
        storageReference = FirebaseStorage.getInstance().getReference();
        addPdf=findViewById(R.id.uploadPdf);
        pdfTextView=findViewById(R.id.pdfTextView);
        pdfTitle=findViewById(R.id.pdfTitle);
        pdfButton=findViewById(R.id.uploadPdfBtn);
        pdfButton.setOnClickListener(v -> {

            if (pdfTitle.getText().toString().isEmpty()) {
                pdfTitle.setError("Please give a PDF title");
                pdfTitle.requestFocus();
            }
            else if(pdfData==null){
                Toast.makeText(this, "Please Upload pdf!!", Toast.LENGTH_SHORT).show();
            }
            else {
                title=pdfTitle.getText().toString();
               uploadData();
            }
        });
        addPdf.setOnClickListener(v -> openGallery());
    }

    private void uploadData() {
        pd.setTitle("Please wait...");
        pd.setMessage("Uploading...");
        pd.show();

   storageReference.child("pdf/"+pdfName+"-"+System.currentTimeMillis()+".pdf").putFile(pdfData).addOnSuccessListener(taskSnapshot -> {
       Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
       while(!uriTask.isComplete());
       Uri uri=uriTask.getResult();
       UploadDAta(String.valueOf(uri));
   })


       .addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(UploadPdf.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
        });
        }

    private void UploadDAta(String uri) {

        String ukey=databaseReference.push().getKey();
        HashMap data=new HashMap();
        data.put("name",title);
        data.put("pdfUrl",uri);
        databaseReference.child(ukey).setValue(data).addOnCompleteListener(task -> {
            pd.dismiss();
            Toast.makeText(UploadPdf.this, "Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UploadPdf.this,AdminPanel.class));
            finish();
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(UploadPdf.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("pdf/docs/ppt");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select pdf title"), REQ);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            pdfData=data.getData();

if(pdfData.toString().startsWith("content://")){
Cursor cursor;
    try {
        cursor=UploadPdf.this.getContentResolver().query(pdfData,null,null,null,null);
        if(cursor!=null&&cursor.moveToFirst()){
            pdfName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
else if (pdfData.toString().startsWith("file://")){
    pdfName=new File(pdfData.toString()).getName();
}
pdfTextView.setText(pdfName);
        }
    }
}