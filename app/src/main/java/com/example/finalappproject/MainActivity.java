package com.example.finalappproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalappproject.Fragments.LoginFragment;
import com.example.finalappproject.Fragments.LoginRegisterFragment;
import com.example.finalappproject.Fragments.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //go to loginRegisterFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainConrtainer, new LoginRegisterFragment()).commit();

    }

}