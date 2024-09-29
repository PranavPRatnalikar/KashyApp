package com.example.kashyapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.kashyapp1.databinding.ActivityPatientProfileDetailsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class patient_profile_details extends AppCompatActivity {


    ActivityPatientProfileDetailsBinding binding;
    DatabaseReference patdb = FirebaseDatabase.getInstance().getReference("Patients");
    final Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientProfileDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //readFile();


        binding.patsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        binding.patskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });

        binding.patdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(patient_profile_details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

                        binding.patdob.setText(format);
                        binding.patage.setText(Integer.toString(calculateAge(calendar.getTimeInMillis())));
                        write(format);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private int calculateAge(long date) {
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    private void write(String format) {
        SharedPreferences sharedPreferences = getSharedPreferences("dob", MODE_PRIVATE);
        sharedPreferences.edit().putString("dob", format).commit();
    }

    private void check() {
        if (binding.patname.getText().toString().isEmpty()) {
            Toast.makeText(patient_profile_details.this, "Please input your name.", Toast.LENGTH_SHORT).show();
        } else if (binding.patcontact.getText().toString().isEmpty()) {
            Toast.makeText(patient_profile_details.this, "Please input your contact number.", Toast.LENGTH_SHORT).show();
        } else if (binding.patgender.getSelectedItem().toString().matches("Select...")) {
            Toast.makeText(patient_profile_details.this, "Please select your gender.", Toast.LENGTH_SHORT).show();
        } else if (binding.patdob.getText().toString().isEmpty()) {
            Toast.makeText(patient_profile_details.this, "Please input your age.", Toast.LENGTH_SHORT).show();
        } else {
            insertPatientData();
            Toast.makeText(patient_profile_details.this, "Profile created successfully!", Toast.LENGTH_SHORT).show();
            nextActivity();
        }
    }


    private void insertPatientData() {
        String pname = binding.patname.getText().toString();
        String pgender = binding.patgender.getSelectedItem().toString();
        String page = binding.patage.getText().toString();
        String pdob = binding.patdob.getText().toString();
        String pcontact = binding.patcontact.getText().toString();

        //read shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("useremail", MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");
        String postcongmail = emailtext.replace('.', ',');
        //

        String nulll = "";

        PatientsRecords records = new PatientsRecords(pname, pcontact, page, emailtext, nulll, pgender, pdob, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll, nulll);
        patdb.child(postcongmail).setValue(records);
    }

    private void nextActivity() {
        Intent intent = new Intent(patient_profile_details.this, patient_greeting_splashscreen.class);
        startActivity(intent);
        finish();
    }

}


