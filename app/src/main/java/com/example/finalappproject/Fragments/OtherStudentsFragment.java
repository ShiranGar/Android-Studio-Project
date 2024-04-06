package com.example.finalappproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalappproject.Adapters.MyStudentAdapter;
import com.example.finalappproject.R;
import com.example.finalappproject.Classes.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtherStudentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtherStudentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<Student> list;
    DatabaseReference reference;
    MyStudentAdapter adapter;
    Button btnBack;

    public OtherStudentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtherStudentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtherStudentsFragment newInstance(String param1, String param2) {
        OtherStudentsFragment fragment = new OtherStudentsFragment();
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
        View view = inflater.inflate(R.layout.fragment_other_students, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        recyclerView = view.findViewById(R.id.recycleView);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyStudentAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.mainConrtainer,new StudentCategoriesFragment()).commit();
            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getCurrentUserCountry(new CountryCallback() {
                    @Override
                    public void onCountryReceived(String currentUserCountry) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String userType = dataSnapshot.child("userType").getValue(String.class);
                            String userCountry = dataSnapshot.child("country").getValue(String.class);
                            if(userType.equals("student") && userCountry.equals(currentUserCountry)) {
                                String name = dataSnapshot.child("name").getValue(String.class);
                                String email = dataSnapshot.child("email").getValue(String.class);
                                String instituteIsrael = dataSnapshot.child("instituteIsrael").getValue(String.class);
                                String instituteAbroad = dataSnapshot.child("instituteAbroad").getValue(String.class);
                                String degree = dataSnapshot.child("degree").getValue(String.class);
                                String city = dataSnapshot.child("city").getValue(String.class);
                                Student student = new Student(name,email,instituteIsrael,instituteAbroad,degree,userCountry,city);
                                list.add(student);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public interface CountryCallback {
        void onCountryReceived(String country);
        void onError(String message);
    }

    private void getCurrentUserCountry(CountryCallback callback) {
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
                        String currentUserCountry = dataSnapshot.child("country").getValue(String.class);
                        callback.onCountryReceived(currentUserCountry);
                        return;
                    }
                }
                callback.onError("Country not found");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError("Error: " + error.getMessage());
            }
        });
    }
}