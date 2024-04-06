package com.example.finalappproject.Fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    MaterialButton btnLogin;
    DatabaseReference databaseReference;
    EditText etEmail,etPassword;
    Button btnBack;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        setupOnBackPressed();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = view.findViewById(R.id.btnLogin);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainConrtainer,
                        new LoginRegisterFragment()).commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your password", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
                FirebaseCallback callback = new FirebaseCallback() {
                    @Override
                    public void onCallback(String userType, boolean isSuccess) {
                        if (isSuccess) {
                            Toast.makeText(getActivity(), "Authentication successful", Toast.LENGTH_SHORT).show();
                            if (userType.equals("student")) {
                                // Navigate to StudentCategoriesFragment
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainConrtainer,
                                        new StudentCategoriesFragment()).commit();
                            } else if (userType.equals("admin")) {
                                // Navigate to AdminRegisterFragment
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainConrtainer,
                                        new AdminOptionsFragment()).commit();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                checkCredentials(email, password, callback);
            }
        });

        return view;
    }

    private void setupOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Navigate to MainActivity
            }
        });
    }

    private void isUserExist(String email, FirebaseCallback callback) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isExist = false;
                String userType = "";
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userEmail = userSnapshot.child("email").getValue(String.class);
                    if (userEmail.equals(email))
                    {
                        isExist = true;
                        userType = userSnapshot.child("userType").getValue(String.class);
                        break;
                    }
                }
                callback.onCallback(userType, isExist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkCredentials(String email, String password, FirebaseCallback callback) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    boolean isSuccess = task.isSuccessful();
                    if (isSuccess) {
                        isUserExist(email, callback);
                    } else {
                        callback.onCallback("",false); // Authentication failed
                    }
                });
    }


    public interface FirebaseCallback {
        void onCallback(String userType, boolean isSuccess);
    }


}