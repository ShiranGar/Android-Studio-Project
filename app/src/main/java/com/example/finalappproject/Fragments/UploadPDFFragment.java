package com.example.finalappproject.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalappproject.R;
import com.example.finalappproject.Classes.putPDF;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadPDFFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadPDFFragment extends Fragment {
    Button btnRead,btnUpload,btnBack;
    EditText etFileName,etCourse;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadPDFFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadPDFFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadPDFFragment newInstance(String param1, String param2) {
        UploadPDFFragment fragment = new UploadPDFFragment();
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
        View view = inflater.inflate(R.layout.fragment_upload_p_d_f, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        etCourse = view.findViewById(R.id.etCourse);
        etFileName = view.findViewById(R.id.editText);
        btnRead = view.findViewById(R.id.btnRead);
        btnUpload = view.findViewById(R.id.btnUpload);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("UploadPDF");
        btnUpload.setEnabled(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.mainConrtainer,new StudentCategoriesFragment()).commit();
            }
        });
        etFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enable the button
                selectPDF();
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to ReadPDFActivity
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.mainConrtainer,new RetrievePDFFragment()).commit();
            }
        });

        return view;
    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),12);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 12 && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null){
            btnUpload.setEnabled(true);
            etFileName.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPDFFileToFirebase(data.getData());
                }
            });
        }
    }

    private void uploadPDFFileToFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("File is loading...");
        progressDialog.show();
        StorageReference reference = storageReference.child("UploadPDF/"+System.currentTimeMillis()+".pdf");

        // Get user info
        getStudentInfo(new StudentInfoCallback() {
            @Override
            public void onStudentInfoReceived(String degree,String instituteAbroad, String country) {
                reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url = uri.getResult();
                        String fileName = etFileName.getText().toString();
                        String courseName = etCourse.getText().toString();
                        putPDF putPDF = new putPDF(fileName, url.toString(),courseName,degree,instituteAbroad,country);
                        databaseReference.child(databaseReference.push().getKey()).setValue(putPDF);
                        Toast.makeText(getActivity(), "File uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("File uploaded..."+(int)progress+"%");
                    }
                });
            }

            @Override
            public void onError(String message) {
                // Handle error
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public interface StudentInfoCallback {
        void onStudentInfoReceived(String degree,String instituteAbroad, String country);
        void onError(String message);
    }

    private void getStudentInfo(UploadPDFFragment.StudentInfoCallback callback) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        String currentUserEmail = user.getEmail();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String email = dataSnapshot.child("email").getValue(String.class).toLowerCase();
                    if (email.equals(currentUserEmail)) {
                        String country = dataSnapshot.child("country").getValue(String.class);
                        String degree = dataSnapshot.child("degree").getValue(String.class);
                        String instituteAbroad = dataSnapshot.child("instituteAbroad").getValue(String.class);
                        callback.onStudentInfoReceived(degree,instituteAbroad,country);
                        return;
                    }
                }
                callback.onError("User name or country not found");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError("Error: " + error.getMessage());
            }
        });
    }

}