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

import com.example.kashyapp1.databinding.ActivityPatientProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class patient_profile extends AppCompatActivity {
 //  private final int GALLARY_REQ_CODE=1000;

    ActivityPatientProfileBinding binding;
    DatabaseReference patdb = FirebaseDatabase.getInstance().getReference("Patients");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateProfile();

//        binding.patimg.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent iGallery=new Intent(Intent.ACTION_PICK);
//                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(iGallery,GALLARY_REQ_CODE);
//            }
//
//        });

        binding.pateditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_profile.this, patient_edit_medical_records.class);
                startActivity(intent);
            }
        });

    }

    private void updateProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");

            String postcongmail = emailtext.toString().replace('.', ',');

            patdb.child(postcongmail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    binding.patsname.setText(snapshot.child("patname").getValue().toString());
                    binding.patsgender.setText(snapshot.child("patgender").getValue().toString());
                    binding.patsaddress.setText(snapshot.child("pataddress").getValue().toString());
                    binding.patsage.setText(snapshot.child("patage").getValue().toString());
                    binding.patscontact.setText(snapshot.child("patcontact").getValue().toString());

                    String image = snapshot.child("patpfp").getValue(String.class);
                    if (image.isEmpty()){

                    }
                    else{
                        Picasso.get().load(image).into(binding.patimg);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode==RESULT_OK) {
//            if (requestCode == GALLARY_REQ_CODE) {
//                //for gallery
//                binding.patimg.setImageURI(data.getData());
//            }
//        }
//    }
}