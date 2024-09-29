package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    Spinner logintype;
    EditText email, password;
    String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    String passPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    MaterialButton loginbtn, regbtn;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();

        if (currentuser != null){
            skipLogin();
        }
    }

    private void skipLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        if (sharedPreferences.getBoolean("loginaspatient", true)){
            Intent intent = new Intent(this, patient_homepage.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, doctor_homepage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        regbtn = findViewById(R.id.regbtn);
        logintype = findViewById(R.id.logintype);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String emailtext = email.getText().toString();
        String passwordtext = password.getText().toString();
        String selecteditem = logintype.getSelectedItem().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        if (!emailtext.matches(emailPattern)) {
            email.setError("Enter correct email!");
            return;
        }
        else if(selecteditem.matches("Login as...")){
            Toast.makeText(login.this, "Please select your login type.", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setMessage("Logging in...");
            progressDialog.setTitle("Sign in");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(emailtext, passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        if (selecteditem.matches("Patient")){
                            sharedPreferences.edit().putBoolean("loginaspatient",true).commit();
                            patActivity();
                            progressDialog.dismiss();
                            Toast.makeText(login.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else if (selecteditem.matches("Doctor")){
                            sharedPreferences.edit().putBoolean("loginaspatient",false).commit();
                            docActivity();
                            progressDialog.dismiss();
                            Toast.makeText(login.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(login.this, "Incorrect login credentials.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //https://youtu.be/CRrDJkkzY2A
    private void write(String emailtext) {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        sharedPreferences.edit().putString("useremail",emailtext).commit();
    }

    private void docActivity() {
        String emailtext = email.getText().toString();
        Intent intent = new Intent(this, doctor_profile_details.class);

        write(emailtext);
        startActivity(intent);
        finish();
    }

    private void patActivity() {
        String emailtext = email.getText().toString();
        Intent intent = new Intent(this, patient_profile_details.class);

        write(emailtext);
        startActivity(intent);
        finish();
    }
}
