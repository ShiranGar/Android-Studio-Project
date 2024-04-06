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

import com.example.finalappproject.Adapters.MyStudiesFilesAdapter;
import com.example.finalappproject.R;
import com.example.finalappproject.Classes.putPDF;
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
 * Use the {@link RetrievePDFFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetrievePDFFragment extends Fragment {

//    ListView listView;
//    DatabaseReference databaseReference;
//    List<putPDF> uploadedPDF;
    RecyclerView recyclerView;
    ArrayList<putPDF> list;
    DatabaseReference reference;
    MyStudiesFilesAdapter adapter;
    Button btnBack;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RetrievePDFFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RetrievePDFFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RetrievePDFFragment newInstance(String param1, String param2) {
        RetrievePDFFragment fragment = new RetrievePDFFragment();
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
        View view = inflater.inflate(R.layout.fragment_retrieve_p_d_f, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        recyclerView = view.findViewById(R.id.recycleViewFiles);
        reference = FirebaseDatabase.getInstance().getReference().child("UploadPDF");
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyStudiesFilesAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.mainConrtainer, new UploadPDFFragment()).commit();
            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getCurrentUserCountry(new filesInfoCallback() {
                    @Override
                    public void onDegreeAndCountryReceived(String currentUserDegree, String currentUserCountry) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String userDegree = dataSnapshot.child("degree").getValue(String.class);
                            String userCountry = dataSnapshot.child("country").getValue(String.class);
                            if(userDegree.equals(currentUserDegree) && userCountry.equals(currentUserCountry)) {
                                String courseName = dataSnapshot.child("courseName").getValue(String.class);
                                String degree = dataSnapshot.child("degree").getValue(String.class);
                                String instituteAbroad = dataSnapshot.child("instituteAbroad").getValue(String.class);
                                String name = dataSnapshot.child("name").getValue(String.class);
                                String url = dataSnapshot.child("url").getValue(String.class);
                                putPDF putPDF = new putPDF(name,url,courseName,degree,instituteAbroad,userCountry);
                                list.add(putPDF);
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


//        listView = view.findViewById(R.id.listView);
//        uploadedPDF = new ArrayList<>();
//
//        retrievePDFFiles();
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                putPDF pdf = uploadedPDF.get(position);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(pdf.getUrl()));
//                startActivity(intent);
//                //Toast.makeText(getActivity(), "Downloading...", Toast.LENGTH_SHORT).show();
//                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pdf.getUrl())));
//            }
//        });

        return view;
    }

    public interface filesInfoCallback {
        void onDegreeAndCountryReceived(String degree,String country);
        void onError(String message);
    }

    private void getCurrentUserCountry(RetrievePDFFragment.filesInfoCallback callback) {
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
                        String currentUserDegree = dataSnapshot.child("degree").getValue(String.class);
                        callback.onDegreeAndCountryReceived(currentUserDegree,currentUserCountry);
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

//    private void retrievePDFFiles() {
//        databaseReference = FirebaseDatabase.getInstance().getReference("UploadPDF");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    putPDF pdf = ds.getValue(putPDF.class);
//                    uploadedPDF.add(pdf);
//                }
//                String[] uploadsName = new String[uploadedPDF.size()];
//
//                for (int i = 0; i < uploadsName.length; i++) {
//                    uploadsName[i] = uploadedPDF.get(i).getName();
//                }
//
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, uploadsName)
//                {
//                    @Override
//                    public View getView(int position, View convertView, ViewGroup parent) {
//                        View view = super.getView(position, convertView, parent);
//                        TextView textView = view.findViewById(android.R.id.text1);
//                        textView.setTextColor(Color.BLACK);
//                        textView.setTextSize(20);
//                        return view;
//                    }
//
//                };
//                listView.setAdapter(arrayAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}