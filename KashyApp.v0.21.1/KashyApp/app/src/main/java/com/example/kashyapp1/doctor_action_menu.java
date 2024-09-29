package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kashyapp1.databinding.ActivityDoctorActionMenuBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class doctor_action_menu extends AppCompatActivity {

    ActivityDoctorActionMenuBinding binding;

    DatabaseReference patdb = FirebaseDatabase.getInstance().getReference("Patients");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorActionMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        String pgmailfound = extras.getString("pgmailfound");
        String postcongmail = pgmailfound.replace('.', ',');

        patdb.child(postcongmail).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();

                binding.f1.setText(String.valueOf(dataSnapshot.child("patname").getValue()));
                binding.f2.setText(String.valueOf(dataSnapshot.child("patgender").getValue()));
                binding.f3.setText(String.valueOf(dataSnapshot.child("patage").getValue()));
            }
        });

        binding.docPrescribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(doctor_action_menu.this,prescription_page2.class);
                intent.putExtra("patientname", binding.f1.getText().toString());
                startActivity(intent);
            }
        });


    }
}