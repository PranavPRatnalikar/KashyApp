package com.example.kashyapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kashyapp1.databinding.ActivityDoctorProfileDetailsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class doctor_profile_details extends AppCompatActivity {

    ActivityDoctorProfileDetailsBinding binding;
    FirebaseDatabase patient_database = FirebaseDatabase.getInstance();
    DatabaseReference patdb = patient_database.getReference().child("Doctors");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorProfileDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        readFile();

        binding.docsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        binding.docskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });

    }

    private void check() {
        if (binding.docname.getText().toString().isEmpty()){
            Toast.makeText(doctor_profile_details.this, "Please input your name.", Toast.LENGTH_SHORT).show();
        }
        else if (binding.docmedregno.getText().toString().isEmpty()){
            Toast.makeText(doctor_profile_details.this, "Please input your Medical Registration Number.", Toast.LENGTH_SHORT).show();
        }
        else if (binding.doccontact.getText().toString().isEmpty()){
            Toast.makeText(doctor_profile_details.this, "Please input your contact number.", Toast.LENGTH_SHORT).show();
        }
        else if (binding.doceducation.getSelectedItem().toString().matches("Select...")){
            Toast.makeText(doctor_profile_details.this, "Please select your educational details.", Toast.LENGTH_SHORT).show();
        }
        else if (binding.docspeciality.getSelectedItem().toString().matches("Select...")){
            Toast.makeText(doctor_profile_details.this, "Please select your specialising field.", Toast.LENGTH_SHORT).show();
        }
        else if (binding.docexperience.getText().toString().isEmpty()){
            Toast.makeText(doctor_profile_details.this, "Please input your experience (in years).", Toast.LENGTH_SHORT).show();
        }
        else {
            insertDoctorData();
            Toast.makeText(doctor_profile_details.this,"Profile created successfully!", Toast.LENGTH_SHORT).show();
            nextActivity();
        }
    }
    private void readFile() {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");
        binding.docgmail.setText(emailtext);
        binding.docgmail.setEnabled(false);
    }

    private void insertDoctorData() {
        String demail = binding.docgmail.getText().toString();
        String dname = binding.docname.getText().toString();
        String dmedregno = binding.docmedregno.getText().toString();
        String dcontact = binding.doccontact.getText().toString();
        String dexperience = binding.docexperience.getText().toString();
        String dhosname = binding.dochosname.getText().toString();
        String dhosregno = binding.dochosregno.getText().toString();

        String deducation = binding.doceducation.getSelectedItem().toString();
        String dspeciality = binding.docspeciality.getSelectedItem().toString();

        String postcongmail = demail.replace('.', ',');

        Doctors doctors = new Doctors(dname, dmedregno, dcontact, demail, deducation, dspeciality, dexperience, dhosname, dhosregno);
        patdb.child(postcongmail).setValue(doctors);

    }

    private void nextActivity() {
        Intent intent = new Intent(doctor_profile_details.this, doctor_homepage.class);
        startActivity(intent);
    }

}