package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.kashyapp1.databinding.ActivityPatientGreetingSplashscreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class patient_greeting_splashscreen extends AppCompatActivity {

    ActivityPatientGreetingSplashscreenBinding binding;
    DatabaseReference patdb = FirebaseDatabase.getInstance().getReference("Patients");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientGreetingSplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");
        String postcongmail = emailtext.toString().replace('.', ',');

        patdb.child(postcongmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.splashgreeter.setText("Welcome \n" + snapshot.child("patname").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(patient_greeting_splashscreen.this, patient_homepage.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}