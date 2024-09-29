package com.example.kashyapp1;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kashyapp1.databinding.ActivityPatientEditMedicalRecordsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class patient_edit_medical_records extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    ImageButton patimg;
    ActivityPatientEditMedicalRecordsBinding binding;
    ActivityResultLauncher<String> img;
    DatabaseReference patdb = FirebaseDatabase.getInstance().getReference("Patients");
    StorageReference storageReference;
    Uri result;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientEditMedicalRecordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        patimg= findViewById(R.id.patimg);

        final Calendar calendar = Calendar.getInstance();

        img = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {

                        binding.patimg.setImageURI(result);
                        uploadPfp(result);
                    }
                }
        );

        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");

        binding.pateremail.setText(emailtext);

        fetchData();

        patimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.launch("image/*");


            }
        });

        binding.patersavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPatientData();
                Toast.makeText(patient_edit_medical_records.this, "Saved changes.", Toast.LENGTH_SHORT).show();
                medRecordsActivity();
            }
        });

        binding.paterdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(patient_edit_medical_records.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
                        binding.paterdob.setText(format);
                        binding.paterage.setText(Integer.toString(calculateAge(calendar.getTimeInMillis())));
                        write(format);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });



    }

    private void uploadPfp(Uri result) {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");
        String postcongmail = emailtext.replace('.', ',');

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading files...");
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance().getReference("Pfp/" + emailtext);

        storageReference.putFile(result)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                patdb.child(postcongmail).child("patpfp").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        if(progressDialog.isShowing())
                                            progressDialog.dismiss();

                                        Toast.makeText(patient_edit_medical_records.this, "Successfully updated your profile picture.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();

                        Toast.makeText(patient_edit_medical_records.this, "Failed to update your profile picture.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int calculateAge(long date) {
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        return age;
    }

    private void fetchData() {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");
        final Calendar calendar = Calendar.getInstance();

        String postcongmail = emailtext.toString().replace('.', ',');

        patdb.child(postcongmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
      //  //        binding.paterbgrp.setSelected(Boolean.parseBoolean(snapshot.child("patbgrp").getValue().toString()));
                binding.patername.setText(snapshot.child("patname").getValue().toString());
                binding.patercontact.setText(snapshot.child("patcontact").getValue().toString());
                binding.pateremail.setText(emailtext);
                binding.pateraddress.setText(snapshot.child("pataddress").getValue().toString());
//                binding.patergender.setText(snapshot.child("patgender").getValue().toString());
                binding.paterdob.setText(snapshot.child("patdob").getValue().toString());
                binding.paterage.setText(snapshot.child("patage").getValue().toString());
                binding.paterrelname.setText(snapshot.child("patrelationname").getValue().toString());
//                binding.paterreltype.setText(snapshot.child("patrelationtype").getValue().toString());
                binding.paterrelcontact.setText(snapshot.child("patrelationcontact").getValue().toString());
//                binding.paterheight.setText(snapshot.child("patheight").getValue().toString());
                binding.paterweight.setText(snapshot.child("patweight").getValue().toString());
//              //  binding.paterbgrp.setText(snapshot.child("patbgrp").getValue().toString());
                binding.patersurgicalhist.setText(snapshot.child("patsurgicalhist").getValue().toString());
                binding.patergenetichist.setText(snapshot.child("patgenetichist").getValue().toString());
                binding.patervaccinehist.setText(snapshot.child("patvaccinehist").getValue().toString());
//                binding.paterchronicdiseases.setText(snapshot.child("patchronicdiseases").getValue().toString());
                binding.paterallergies.setText(snapshot.child("patallergies").getValue().toString());
                binding.patermedallergies.setText(snapshot.child("patmedallergies").getValue().toString());
//                binding.patersmoking.setText(snapshot.child("patsmoking").getValue().toString());
//                binding.pateralcohol.setText(snapshot.child("patalcohol").getValue().toString());
//                binding.patertobacco.setText(snapshot.child("pattobacco").getValue().toString());
//                binding.pateractivity.setText(snapshot.child("patactivity").getValue().toString());
//                binding.paterdiet.setText(snapshot.child("patdiet").getValue().toString());

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


    private void write(String format) {
        SharedPreferences sharedPreferences = getSharedPreferences("dob",MODE_PRIVATE);
        sharedPreferences.edit().putString("dob",format).commit();
    }

    private void insertPatientData() {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");
        String postcongmail = emailtext.replace('.', ',');

        patdb.child(postcongmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ppfp = snapshot.child("patpfp").getValue().toString();
                String pprescriptions = snapshot.child("patprescriptions").getValue().toString();

                String pname = binding.patername.getText().toString();
                String pcontact = binding.patercontact.getText().toString();
                String pgmail = binding.pateremail.getText().toString();
                String paddress = binding.pateraddress.getText().toString();
                String pgender = binding.patergender.getSelectedItem().toString();
                String pdob = binding.paterdob.getText().toString();
                String page = binding.paterdob.getText().toString();
                String prname = binding.paterrelname.getText().toString();
                String prtype = binding.paterreltype.getSelectedItem().toString();
                String prcontact = binding.paterrelcontact.getText().toString();
                String pheight = binding.paterheight.getSelectedItem().toString();
                String pweight = binding.paterweight.getText().toString();
                String pbgrp = binding.paterbgrp.getSelectedItem().toString();
                String psurgicalhist = binding.patersurgicalhist.getText().toString();
                String pgenetichist = binding.patergenetichist.getText().toString();
                String pvaccinehist = binding.patervaccinehist.getText().toString();
                String pchronicdiseases = binding.paterchronicdiseases.getSelectedItem().toString();
                String pallergies = binding.paterallergies.getText().toString();
                String pmedallergies = binding.patermedallergies.getText().toString();
                String psmoking = binding.patersmoking.getSelectedItem().toString();
                String palcohol = binding.pateralcohol.getSelectedItem().toString();
                String ptobacco = binding.patertobacco.getSelectedItem().toString();
                String pactivity = binding.pateractivity.getSelectedItem().toString();
                String pdiet = binding.paterdiet.getSelectedItem().toString();

                PatientsRecords records = new PatientsRecords(pname, pcontact, page, pgmail, paddress, pgender, pdob, prname, prtype,
                        prcontact, pheight, pweight, pbgrp, psurgicalhist, pgenetichist, pvaccinehist, pchronicdiseases,
                        pallergies, pmedallergies, psmoking, palcohol, ptobacco, pactivity, pdiet, ppfp, pprescriptions);
                patdb.child(postcongmail).setValue(records);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void medRecordsActivity() {
        Intent intent = new Intent(patient_edit_medical_records.this, patient_medical_records.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}