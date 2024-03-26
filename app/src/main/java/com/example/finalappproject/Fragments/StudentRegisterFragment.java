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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentRegisterFragment extends Fragment {
    EditText etEmail, etPassword, edRepeatPassword;
    MaterialAutoCompleteTextView autoCompleteTextView;
    MaterialButton btnRegister;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;


//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public StudentRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentRegisterFragment newInstance(String param1, String param2) {
        StudentRegisterFragment fragment = new StudentRegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_student_register, container, false);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        edRepeatPassword = view.findViewById(R.id.etRepPassword);
        autoCompleteTextView = view.findViewById(R.id.inputTV);
        btnRegister = view.findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String repeatPassword = edRepeatPassword.getText().toString().trim();
                String institute = autoCompleteTextView.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "Password is required", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
                if (repeatPassword.isEmpty()) {
                    Toast.makeText(getActivity(), "Repeat password is required", Toast.LENGTH_SHORT).show();
                    edRepeatPassword.requestFocus();
                    return;
                }
                if (!password.equals(repeatPassword)) {
                    Toast.makeText(getActivity(), "Password does not match", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
                if (institute.isEmpty()) {
                    Toast.makeText(getActivity(), "Course is required", Toast.LENGTH_SHORT).show();
                    autoCompleteTextView.requestFocus();
                }
                if(!isValidEmail(email)) {
                    Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if(password.length() < 6) {
                    Toast.makeText(getActivity(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
                databaseReference = FirebaseDatabase.getInstance().getReference("StudentsInfo");
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for(DataSnapshot data: dataSnapshot.getChildren()){
//                            if (data.child(email).exists()) {
//                                Toast.makeText(getActivity(), "This email exsits", Toast.LENGTH_SHORT).show();
//                                //do ur stuff
//                            } else {
//                                //do something if not exists
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//                });
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(getActivity(), "Register success.",
//                                            Toast.LENGTH_SHORT).show();
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(getActivity(), "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

            }
        });
        return view;
    }

    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}