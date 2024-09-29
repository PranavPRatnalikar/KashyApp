package com.example.kashyapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kashyapp1.databinding.ActivityPatientMedicalRecords2Binding;

public class patient_medical_records2 extends AppCompatActivity {

    ActivityPatientMedicalRecords2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientMedicalRecords2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}