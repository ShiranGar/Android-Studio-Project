package com.example.finalappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalappproject.Fragments.LoginFragment;
import com.example.finalappproject.Fragments.StudentRegisterFragment;

public class MainActivity extends AppCompatActivity {

    Button btnRegisterStudent, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegisterStudent = (Button) findViewById(R.id.btnStudentFragment);
        btnRegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegisterStudent.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainConrtainer,
                        new StudentRegisterFragment()).commit();
            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setVisibility(View.GONE);
                btnRegisterStudent.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainConrtainer,
                        new LoginFragment()).commit();
            }
        });
    }
}