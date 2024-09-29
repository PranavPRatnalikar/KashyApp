package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.kashyapp1.databinding.ActivityPatientMedicalRecordsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class patient_medical_records extends AppCompatActivity {

    ActivityPatientMedicalRecordsBinding binding;
    DatabaseReference patdb = FirebaseDatabase.getInstance().getReference("Patients");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientMedicalRecordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateData();

        binding.patmreditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_medical_records.this, patient_edit_medical_records.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateData() {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");
        final Calendar calendar = Calendar.getInstance();

        String postcongmail = emailtext.toString().replace('.', ',');

        patdb.child(postcongmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Uri uri = Uri.parse(snapshot.child("patpfp").getValue().toString());

                binding.patmrname.setText(snapshot.child("patname").getValue().toString());
                binding.patmrcontact.setText(snapshot.child("patcontact").getValue().toString());
                binding.patmremail.setText(emailtext);
//                binding.patmraddress.setText(snapshot.child("pataddress").getValue().toString());
                binding.patmrgender.setText(snapshot.child("patgender").getValue().toString());
                binding.patmrage.setText(snapshot.child("patage").getValue().toString());
                binding.patmrrelname.setText(snapshot.child("patrelationname").getValue().toString());
                binding.patmrreltype.setText(snapshot.child("patrelationtype").getValue().toString());
                binding.patmrrelcontact.setText(snapshot.child("patrelationcontact").getValue().toString());
                binding.patmrheight.setText(snapshot.child("patheight").getValue().toString());
                binding.patmrweight.setText(snapshot.child("patweight").getValue().toString());
                binding.patmrbgrp.setText(snapshot.child("patbgrp").getValue().toString());
                binding.patmrsurgicalhist.setText(snapshot.child("patsurgicalhist").getValue().toString());
                binding.patmrgenetichist.setText(snapshot.child("patgenetichist").getValue().toString());
                binding.patmrvaccinehist.setText(snapshot.child("patvaccinehist").getValue().toString());
                binding.patmrchronicdiseases.setText(snapshot.child("patchronicdiseases").getValue().toString());
                binding.patmrallergies.setText(snapshot.child("patallergies").getValue().toString());
                binding.patmrmedallergies.setText(snapshot.child("patmedallergies").getValue().toString());
                binding.patmrsmoking.setText(snapshot.child("patsmoking").getValue().toString());
                binding.patmralcohol.setText(snapshot.child("patalcohol").getValue().toString());
                binding.patmrtobacco.setText(snapshot.child("pattobacco").getValue().toString());
                binding.patmractivity.setText(snapshot.child("patactivity").getValue().toString());
                binding.patmrdiet.setText(snapshot.child("patdiet").getValue().toString());

                String image = snapshot.child("patpfp").getValue(String.class);
                if (image.isEmpty()){

                }
                else{
                    Picasso.get().load(image).into(binding.patimg1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}