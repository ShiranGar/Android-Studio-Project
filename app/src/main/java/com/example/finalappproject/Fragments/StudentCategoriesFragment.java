package com.example.finalappproject.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalappproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentCategoriesFragment extends Fragment {

    Button btnOtherStudent,btnStudies,btnFun;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentCategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentCategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentCategoriesFragment newInstance(String param1, String param2) {
        StudentCategoriesFragment fragment = new StudentCategoriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_student_categories, container, false);
        btnOtherStudent = view.findViewById(R.id.btnOtherStudent);
        btnStudies = view.findViewById(R.id.btnStudies);
        btnFun = view.findViewById(R.id.btnFun);
        btnOtherStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainConrtainer,
                        new OtherStudentsFragment()).commit();
            }
        });

        btnStudies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to UploadPDFFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainConrtainer,
                        new UploadPDFFragment()).commit();
            }
        });

        btnFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to UploadImageFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainConrtainer,
                        new UploadImageFragment()).commit();
            }
        });
        return view;
    }
}