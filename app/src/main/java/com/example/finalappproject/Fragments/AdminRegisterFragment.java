package com.example.finalappproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalappproject.R;
import com.example.finalappproject.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminRegisterFragment extends Fragment {

    EditText etName, etEmail, etCountry, etAInstitudeAbroad;
    MaterialAutoCompleteTextView degree,institudeIsrael;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    MaterialButton btnRegister;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Student student;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public AdminRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminRegisterFragment newInstance(String param1, String param2) {
        AdminRegisterFragment fragment = new AdminRegisterFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_register, container, false);
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etCountry = view.findViewById(R.id.etCountry);
        etAInstitudeAbroad = view.findViewById(R.id.etInstitudeAbroad);
        institudeIsrael = view.findViewById(R.id.inputInstituteIsrael);
        degree = view.findViewById(R.id.inputDegree);
        btnRegister = view.findViewById(R.id.btnRegister);
        progressBar = view.findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String country = etCountry.getText().toString().trim();
                String institudeAbroad = etAInstitudeAbroad.getText().toString().trim();
                String institudeInIsrael = institudeIsrael.getText().toString().trim();
                String degreeInput = degree.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Name is required", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if (country.isEmpty()) {
                    Toast.makeText(getActivity(), "Country is required", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if (institudeAbroad.isEmpty()) {
                    Toast.makeText(getActivity(), "Institude Abroad is required", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if (institudeInIsrael.isEmpty()) {
                    Toast.makeText(getActivity(), "Institude in Israel is required", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }
                if (degreeInput.isEmpty()) {
                    Toast.makeText(getActivity(), "Degree is required", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                student = new Student(name,email,country,institudeAbroad,institudeInIsrael,degreeInput);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Users") && dataSnapshot.child("Users").hasChild(student.getEmail().replace(".","")))
                        {    Toast.makeText(getActivity(), "User already exists", Toast.LENGTH_SHORT).show();
                            // The value exists
                            // You can access the value using dataSnapshot.getValue()
                        } else {
                            addDatatoFirebase(student);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//                fAuth.createUserWithEmailAndPassword(email,"123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()) {
//                            addDatatoFirebase(name,email,country,institudeAbroad,institudeInIsrael,degreeInput);
//                            Toast.makeText(getActivity(), "User created", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getActivity(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });
        return view;
    }

    private void addDatatoFirebase(Student student) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email",student.getEmail());
        hashMap.put("name",student.getName());
        hashMap.put("country",student.getCountryAbroad());
        hashMap.put("institudeAbroad",student.getInstitudeAbroad());
        hashMap.put("institudeIsrael",student.getInstitudeIsrael());
        hashMap.put("degree",student.getDegree());
        databaseReference.child("Users")
                .child(student.getEmail().replace(".",""))
                .setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "data added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Fail to add data " + e, Toast.LENGTH_SHORT).show();
                    }
                });
                // after adding this data we are showing toast message.
        }
}
