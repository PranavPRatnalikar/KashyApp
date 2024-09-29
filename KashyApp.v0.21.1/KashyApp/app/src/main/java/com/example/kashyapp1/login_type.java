package com.example.kashyapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class login_type extends AppCompatActivity {

    MaterialButton docbtn;
    MaterialButton patbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);

        docbtn = findViewById(R.id.docbtn);
        patbtn = findViewById(R.id.patbtn);

        patbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_type.this, patient_profile_details.class);
                startActivity(intent);
                finish();
            }
        });
        docbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_type.this, doctor_profile_details.class);
                startActivity(intent);
                finish();
            }
        });

    }


}