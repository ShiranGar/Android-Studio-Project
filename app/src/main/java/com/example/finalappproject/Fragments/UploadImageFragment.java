package com.example.finalappproject.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalappproject.Classes.Model;
import com.example.finalappproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadImageFragment extends Fragment {

    Button uploadBtn,showAllBtn,btnBack;
    ImageView imageView;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Uri imageUri;
    EditText etDescription;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadImageFragment newInstance(String param1, String param2) {
        UploadImageFragment fragment = new UploadImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        etDescription = view.findViewById(R.id.etDescription);
        uploadBtn = view.findViewById(R.id.uploadBtn);
        showAllBtn = view.findViewById(R.id.showAllBtn);
        imageView = view.findViewById(R.id.imageView);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        storageReference = FirebaseStorage.getInstance().getReference("Images/");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    uploadToFirebase(imageUri);
                }
                else
                {
                    Toast.makeText(getContext(),"Please select an image",Toast.LENGTH_SHORT).show();
                }
            }
        });

        showAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mainConrtainer,new ShowImagesFragment()).commit();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mainConrtainer,new StudentCategoriesFragment()).commit();
            }
        });

        return view;
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String description = etDescription.getText().toString();
                        Model model = new Model(uri.toString(),description);
                        String modelId = databaseReference.push().getKey();
                        databaseReference.child(modelId).setValue(model);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.baseline_add_photo_alternate_24);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Failed to get download URL", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Image upload failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExtension(Uri mUri) {
       ContentResolver cr = getActivity().getContentResolver();
       MimeTypeMap mime = MimeTypeMap.getSingleton();
       return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}