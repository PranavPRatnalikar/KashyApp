package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.kashyapp1.databinding.ActivityDoctorProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class doctor_profile extends AppCompatActivity {

    private final int GALLARY_REQ_CODE=1000;
    ActivityDoctorProfileBinding binding;
    DatabaseReference docdb = FirebaseDatabase.getInstance().getReference("Doctors");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateProfile();

        binding.doceditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(doctor_profile.this, doctor_profile_details.class);
                startActivity(intent);
            }
        });

        binding.docimg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent iGallery=new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLARY_REQ_CODE);


            }

        });
    }

    private void updateProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");

            String postcongmail = emailtext.toString().replace('.', ',');

            docdb.child(postcongmail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    binding.docsname.setText(snapshot.child("docname").getValue().toString());
                    binding.docseducation.setText(snapshot.child("doceducation").getValue().toString());
                    binding.docsspeciality.setText(snapshot.child("docspeciality").getValue().toString());
                    binding.docsexperience.setText(snapshot.child("docexperience").getValue().toString()+"+ years");
                    binding.docshospitalname.setText(snapshot.child("dochosname").getValue().toString());
                    binding.docscontact.setText(snapshot.child("doccontact").getValue().toString());
                    binding.docsmedregno.setText(snapshot.child("docmedregno").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {
            if (requestCode == GALLARY_REQ_CODE) {
                //for gallery
                binding.docimg.setImageURI(data.getData());
            }
        }
    }

}