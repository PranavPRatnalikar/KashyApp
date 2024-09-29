package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kashyapp1.databinding.ActivityDoctorHomepageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class doctor_homepage extends AppCompatActivity {

    MaterialButton searchButton;
    EditText searchbar;
    TextView greeter;
    DrawerLayout doc_drawerlayout;
    NavigationView doc_navview;
    Toolbar doc_toolbar;

    ActivityDoctorHomepageBinding binding;
    DatabaseReference patdb = FirebaseDatabase.getInstance().getReference("Patients");
    DatabaseReference docdb = FirebaseDatabase.getInstance().getReference("Doctors");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        greeterTextChange();

        searchButton = findViewById(R.id.searchButton);
        searchbar = findViewById(R.id.searchbar);
        greeter = findViewById(R.id.docgreeter);
        doc_drawerlayout =findViewById(R.id.doc_drawerLayout);
        doc_navview = findViewById(R.id.doc_navigatinView);
        doc_toolbar = findViewById(R.id.doc_toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, doc_drawerlayout, doc_toolbar, R.string.openDrawer, R.string.closeDrawer);

        doc_drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        doc_navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id==R.id.optprofile){
                    Intent intent = new Intent(doctor_homepage.this, doctor_profile.class);
                    startActivity(intent);
                }

                else if (id==R.id.opthelp){
                    Toast.makeText(doctor_homepage.this,"Help",Toast.LENGTH_SHORT).show();
                }

                else if (id==R.id.optlogout){
                    FirebaseAuth.getInstance().signOut();

                    SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

                    sharedPreferences.edit().remove("loginaspatient").commit();
                    sharedPreferences.edit().remove("useremail").commit();

                    Toast.makeText(doctor_homepage.this, "Signed out of your account!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(doctor_homepage.this, login.class);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(doctor_homepage.this,"Settings",Toast.LENGTH_SHORT).show();


                }

                doc_drawerlayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtext = searchbar.getText().toString();
                String pgmail = emailtext.replace('.', ',');

                if(!pgmail.isEmpty()){
                    performSearch(pgmail);
                }

                else{
                    Toast.makeText(doctor_homepage.this, "Please enter patient's email address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void greeterTextChange() {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");

            String postcongmail = emailtext.replace('.', ',');

            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int hour = cal.get(Calendar.HOUR_OF_DAY);

            docdb.child(postcongmail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    //Greeting text change
                    String name = snapshot.child("docname").getValue().toString();
                    if(hour>= 12 && hour < 17){
                        greeter.setText(" Good Afternoon ☀️\n Dr. " + name );;
                    } else if(hour >= 17 && hour < 24){
                        greeter.setText(" Good Evening \uD83C\uDF19 \n Dr. " + name);
                    } else {
                        greeter.setText(" Good Morning ☀️\n Dr. " + name );
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }


    private void performSearch(String pgmail) {

        patdb.child(pgmail).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String pgmailfound = (String) dataSnapshot.child("patemail").getValue();
                        Toast.makeText(doctor_homepage.this, "Patient found with email: "+pgmailfound, Toast.LENGTH_SHORT).show();

                        nextActivity(pgmailfound);

                    }
                    else {
                        Toast.makeText(doctor_homepage.this, "Patient does not exist.", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(doctor_homepage.this, "Unable to read data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void nextActivity(String pgmailfound) {
        Intent intent = new Intent(doctor_homepage.this, doctor_action_menu.class);
        intent.putExtra("pgmailfound", pgmailfound);
        startActivity(intent);
    }

    public void onBackPressed(){

        if(doc_drawerlayout.isDrawerOpen(GravityCompat.START)){
            doc_drawerlayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}