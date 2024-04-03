package com.example.finalappproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalappproject.MainActivity;
import com.example.finalappproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    EditText etEmail, etPassword, edRepeatPassword;
    MaterialButton btnRegister;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Button btnBackToMain;


//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public RegisterFragment() {
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
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        edRepeatPassword = view.findViewById(R.id.etRepPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnBackToMain = view.findViewById(R.id.btnBackToMain);
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String repeatPassword = edRepeatPassword.getText().toString().trim();
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
                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean emailExists = false;
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String userEmail = userSnapshot.child("email").getValue(String.class);
                            if (userEmail != null && userEmail.equals(email)) {
                                // Email exists in the database
                                emailExists = true;
                                break;
                            }
                        }

                        if (emailExists) {
                            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getActivity(), "User created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                            databaseReference.child(email.replace(".", "")).child("password").setValue(password);
//                            Toast.makeText(getActivity(), "Email exists in the database.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Email does not exist, handle accordingly
                            // For example:
                            Toast.makeText(getActivity(), "Email does not exist in the database.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                        Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to loginRegisterFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainConrtainer,
                        new LoginRegisterFragment()).commit();
            }
        });
        return view;
    }

    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}