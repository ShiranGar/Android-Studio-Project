package com.example.finalappproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalappproject.R;
import com.example.finalappproject.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminRegisterFragment extends Fragment {

    EditText etName, etEmail, etCountry, etAInstitudeAbroad,etCity;
    MaterialAutoCompleteTextView degree,institudeIsrael;
    FirebaseAuth fAuth;
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
        etCity = view.findViewById(R.id.etCity);
        etAInstitudeAbroad = view.findViewById(R.id.etInstitudeAbroad);
        institudeIsrael = view.findViewById(R.id.inputInstituteIsrael);
        degree = view.findViewById(R.id.inputDegree);
        btnRegister = view.findViewById(R.id.btnRegister);
        fAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String country = etCountry.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String institudeAbroad = etAInstitudeAbroad.getText().toString().trim();
                String institudeInIsrael = institudeIsrael.getText().toString().trim();
                String degreeInput = degree.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Name is required", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if (country.isEmpty()) {
                    Toast.makeText(getActivity(), "Country is required", Toast.LENGTH_SHORT).show();
                    etCountry.requestFocus();
                    return;
                }
                if (city.isEmpty()) {
                    Toast.makeText(getActivity(), "City is required", Toast.LENGTH_SHORT).show();
                    etCity.requestFocus();
                    return;
                }
                if (institudeAbroad.isEmpty()) {
                    Toast.makeText(getActivity(), "Institute Abroad is required", Toast.LENGTH_SHORT).show();
                    etAInstitudeAbroad.requestFocus();
                    return;
                }
                if (institudeInIsrael.isEmpty()) {
                    Toast.makeText(getActivity(), "Institute in Israel is required", Toast.LENGTH_SHORT).show();
                    institudeIsrael.requestFocus();
                }
                if (degreeInput.isEmpty()) {
                    Toast.makeText(getActivity(), "Degree is required", Toast.LENGTH_SHORT).show();
                    degree.requestFocus();
                }
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                student = new Student(name,email,institudeInIsrael,institudeAbroad,degreeInput,country,city);
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
        hashMap.put("userType","student");
        hashMap.put("name",student.getName());
        hashMap.put("email",student.getEmail());
        hashMap.put("instituteIsrael",student.getInstituteIsrael());
        hashMap.put("instituteAbroad",student.getInstituteAbroad());
        hashMap.put("degree",student.getDegree());
        hashMap.put("country",student.getCountryAbroad());
        hashMap.put("city",student.getCity());
        databaseReference.child("Users")
                .child(student.getEmail().replace(".",""))
                .setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "data added", Toast.LENGTH_SHORT).show();
                        //clearing the fields
                        etName.setText("");
                        etEmail.setText("");
                        etCountry.setText("");
                        etCity.setText("");
                        etAInstitudeAbroad.setText("");
                        institudeIsrael.setText("");
                        degree.setText("");
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
