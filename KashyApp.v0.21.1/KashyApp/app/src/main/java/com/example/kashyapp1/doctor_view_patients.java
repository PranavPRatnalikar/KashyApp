package com.example.kashyapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class doctor_view_patients extends AppCompatActivity {
    Button pat1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_patients);
        pat1 = findViewById(R.id.pat1);

        pat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(doctor_view_patients.this, doctor_action_menu.class);
                startActivity(intent);
            }
        });
    }
}