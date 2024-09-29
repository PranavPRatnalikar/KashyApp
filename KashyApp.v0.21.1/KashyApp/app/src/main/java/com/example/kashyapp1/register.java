package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {


    EditText email, password, confirmpassword;
    Button btnRegister;
    String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    String passPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.patemail);
        password = findViewById(R.id.patpassword);
        confirmpassword = findViewById(R.id.patconfirmpassword);
        btnRegister = findViewById(R.id.patregisterbtn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();

            }
        });
    }

    private void performAuth() {

        String emailtext = email.getText().toString();
        String passwordtext = password.getText().toString();
        String confirmpasswordtext = confirmpassword.getText().toString();

        if (!emailtext.matches(emailPattern)){
            email.setError("Enter correct email!");
            return;
        }
        else if (!passwordtext.equals(confirmpasswordtext)){
            confirmpassword.setError("Password and confirm password do not match.");
            return;
        }
        else if (!passwordtext.matches(passPattern)){
            confirmpassword.setError("Password must have: \n> 1 uppercase character. \n> 1 lowercase character.\n> A numeric value.\n> Length of 8 characters.");
            return;
        }
        else{
            progressDialog.setMessage("Please wait for registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(emailtext, passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseAuth.getInstance().signOut();
                        NextIntent();
                        progressDialog.dismiss();
                        Toast.makeText(register.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(register.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void NextIntent() {
        Intent intent = new Intent(register.this, login.class);
        startActivity(intent);
    }
}