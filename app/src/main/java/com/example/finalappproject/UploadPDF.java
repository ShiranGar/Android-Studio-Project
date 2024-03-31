package com.example.finalappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class UploadPDF extends AppCompatActivity {
    Button upload_btn;
    EditText pdf_name;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        upload_btn = findViewById(R.id.upload_btn);
        pdf_name = findViewById(R.id.name);
    }
}