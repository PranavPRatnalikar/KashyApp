package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kashyapp1.databinding.ActivityPatientHomepageBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

public class patient_homepage extends AppCompatActivity {

    ActivityPatientHomepageBinding binding;
    TextView greeter, navname, navemail;
    ImageButton inbox,accounts,records;
    Button ambulance;
    DrawerLayout drawerlayout;
    NavigationView navview;
    Toolbar toolbar;


    DatabaseReference patdb = FirebaseDatabase.getInstance().getReference("Patients");

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        greeter = findViewById(R.id.texthello);
        navname = findViewById(R.id.navname);
        navemail = findViewById(R.id.navemail);
        inbox = findViewById(R.id.inbox);
        accounts = findViewById(R.id.accounts);
        ambulance = findViewById(R.id.ambulancebtn);
        records =findViewById(R.id.records);
        drawerlayout = findViewById(R.id.drawerlayout);
        navview = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);


        greeterTextChange();         //Comment-out if app crashes on homepage

       inbox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(patient_homepage.this, patient_inbox.class);
               startActivity(intent);

           }
       });

        accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_homepage.this, patient_accounts.class);
                startActivity(intent);
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_homepage.this,Ambulance_tracking.class);
                startActivity(intent);
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_homepage.this, patient_medical_records.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.openDrawer, R.string.closeDrawer);

        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id==R.id.opthome){
                    Intent intent = new Intent(patient_homepage.this, patient_profile.class);
                    startActivity(intent);
                }

                else if (id==R.id.opthelp){
                    Toast.makeText(patient_homepage.this,"Help",Toast.LENGTH_SHORT).show();

                }

                else if (id==R.id.optlogout){

                    FirebaseAuth.getInstance().signOut();

                    SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

                    sharedPreferences.edit().remove("loginaspatient").commit();
                    sharedPreferences.edit().remove("useremail").commit();
                    sharedPreferences.edit().remove("dob").commit();

                    Toast.makeText(patient_homepage.this, "Signed out of your account!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(patient_homepage.this, login.class);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(patient_homepage.this,"Settings",Toast.LENGTH_SHORT).show();


                }



                drawerlayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void greeterTextChange() {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");

            String postcongmail = emailtext.replace('.', ',');
            String name = patdb.child(postcongmail).child("patname").toString();
            if(name != "") {
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int hour = cal.get(Calendar.HOUR_OF_DAY);

                patdb.child(postcongmail).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //Greeting text change
                        String name = snapshot.child("patname").getValue().toString();
                        if (hour >= 12 && hour < 17) {
                            greeter.setText(" Good Afternoon ☀️\n" + name);
                            ;
                        } else if (hour >= 17 && hour < 24) {
                            greeter.setText(" Good Evening \uD83C\uDF19 \n" + name);
                        } else {
                            greeter.setText(" Good Morning ☀️\n" + name);
                        }

                        //Fill pfp
//                        View view = getLayoutInflater().inflate(R.layout.header,null);
//                        ImageView circlepfp = view.findViewById(R.id.circlepfp);
//
//                        String image = snapshot.child("patpfp").getValue(String.class);
//                        if (image.isEmpty()){
//
//                        }
//                        else{
//                            Picasso.get().load(image).into(circlepfp);
//                        }

                        //Fill navigationview fields

//                        navname.setText(snapshot.child("patname").getValue().toString());
//                        navemail.setText(snapshot.child("patemail").getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            else{
                greeter.setText("Could not find patient name.");}
    }


    public void onBackPressed(){

        if(drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}