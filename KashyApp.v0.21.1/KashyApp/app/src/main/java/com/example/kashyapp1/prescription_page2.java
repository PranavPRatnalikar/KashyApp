package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kashyapp1.databinding.ActivityPrescriptionPage2Binding;
import com.example.kashyapp1.databinding.ActivityPrescriptionPageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class prescription_page2 extends AppCompatActivity {

    ActivityPrescriptionPage2Binding binding;
    final Calendar calendar = Calendar.getInstance();

    AlertDialog dialog1,dialog2,dialog3;
    DatabaseReference docdb = FirebaseDatabase.getInstance().getReference("Doctors");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrescriptionPage2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updatefields();

        buildDialog1();
        buildDialog2();
        buildDialog3();

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(prescription_page2.this, "Successfully prescribed medication.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(prescription_page2.this, doctor_homepage.class);
                startActivity(intent);
            }
        });

        binding.onschedulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.show();
            }
        });

        binding.atintervalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.show();
            }
        });

        binding.whenneededbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.show();
            }
        });



    }

    private void updatefields() {
        SharedPreferences sharedPreferences = getSharedPreferences("useremail",MODE_PRIVATE);
        String emailtext = sharedPreferences.getString("useremail", "");
        String postcongmail = emailtext.toString().replace('.', ',');

        docdb.child(postcongmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent = getIntent();
                String patientname= intent.getStringExtra("patientname");

                binding.hname.setText(snapshot.child("dochosname").getValue().toString());
                binding.pname.setText(patientname);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        binding.date.setText(format.format(date));

    }

    @SuppressLint("MissingInflatedId")
    private void buildDialog3() {

        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        View view3 = getLayoutInflater().inflate(R.layout.dialog3_whenneeded,null);

        builder3.setView(view3);

        builder3.setTitle("Enter Medicine")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText medname = view3.findViewById(R.id.etmedname3);
                        EditText strength = view3.findViewById(R.id.etstrength3);
                        EditText during = view3.findViewById(R.id.etduring3);
                        EditText start = view3.findViewById(R.id.etstartdate3);
                        EditText end = view3.findViewById(R.id.etenddate3);

                        String medstr = medname.getText().toString();
                        String strengthstr = strength.getText().toString();
                        String duringstr = during.getText().toString();
                        String startstr = start.getText().toString();
                        String endstr = end.getText().toString();

                        addCard3(medstr, strengthstr, duringstr, startstr, endstr);

                        medname.setText(null);
                        strength.setText(null);
                        during.setText(null);
                        start.setText(null);
                        end.setText(null);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        EditText start = view3.findViewById(R.id.etstartdate3);
        EditText end = view3.findViewById(R.id.etenddate3);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(prescription_page2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

                        start.setText(format);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(prescription_page2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

                        end.setText(format);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        dialog3 = builder3.create();
    }

    private void addCard3(String medstr, String strengthstr, String duringstr, String startstr, String endstr) {
        View view3 = getLayoutInflater().inflate(R.layout.card3_when_needed,null);
        TextView cmedname = view3.findViewById(R.id.tvmedname3);
        TextView cstrength = view3.findViewById(R.id.tvstrength3);
        TextView cduring = view3.findViewById(R.id.tvduring3);
        TextView cstart = view3.findViewById(R.id.tvstartdate3);
        TextView cend = view3.findViewById(R.id.tvenddate3);

        Button delete3 = view3.findViewById(R.id.delete3);
        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.containerpres2.removeView(view3);

            }
        });
        cmedname.setText(medstr);
        cstrength.setText(strengthstr + " mg");
        cduring .setText(duringstr);
        cstart.setText(startstr);
        cend.setText(endstr);

        binding.containerpres2.addView(view3);
    }


    @SuppressLint("MissingInflatedId")
    private void buildDialog2() {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        View view2 = getLayoutInflater().inflate(R.layout.dialog2_atintervals,null);

        builder2.setView(view2);

        builder2.setTitle("Enter Medicine")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText medname = view2.findViewById(R.id.etmedname2);
                        EditText strength = view2.findViewById(R.id.etstrength2);
                        EditText interval = view2.findViewById(R.id.etinterval2);
                        EditText limit = view2.findViewById(R.id.etlimit2);
                        EditText start = view2.findViewById(R.id.etstartdate2);
                        EditText end = view2.findViewById(R.id.etenddate2);

                        String medstr = medname.getText().toString();
                        String strengthstr = strength.getText().toString();
                        String intervalstr = interval.getText().toString();
                        String limitstr = limit.getText().toString();
                        String startstr = start.getText().toString();
                        String endstr = end.getText().toString();

                        addCard2(medstr, strengthstr, intervalstr, limitstr, startstr, endstr);

                        medname.setText(null);
                        strength.setText(null);
                        interval.setText(null);
                        limit.setText(null);
                        start.setText(null);
                        end.setText(null);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        EditText start = view2.findViewById(R.id.etstartdate2);
        EditText end = view2.findViewById(R.id.etenddate2);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(prescription_page2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

                        start.setText(format);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(prescription_page2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

                        end.setText(format);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        dialog2 = builder2.create();
    }

    private void addCard2(String medstr, String strengthstr, String intervalstr, String limitstr, String startstr, String endstr) {

        View view2 = getLayoutInflater().inflate(R.layout.card2_at_intervals,null);
        TextView cmedname = view2.findViewById(R.id.tvmedname2);
        TextView cstrength = view2.findViewById(R.id.tvstrength2);
        TextView cinterval = view2.findViewById(R.id.tvinterval2);
        TextView climit = view2.findViewById(R.id.tvlimit2);
        TextView cstart = view2.findViewById(R.id.tvstartdate1);
        TextView cend = view2.findViewById(R.id.tvenddate1);

        Button delete2 = view2.findViewById(R.id.delete2);
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.containerpres2.removeView(view2);

            }
        });
        cmedname.setText(medstr);
        cstrength.setText(strengthstr + " mg");
        cinterval.setText(intervalstr + " hours");
        climit.setText(limitstr + " times per day");
        cstart.setText(startstr);
        cend.setText(endstr);

        binding.containerpres2.addView(view2);
    }


    @SuppressLint("MissingInflatedId")
    private void buildDialog1() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        View view1 = getLayoutInflater().inflate(R.layout.dialog, null);

        EditText medname = view1.findViewById(R.id.nameEdit);
        EditText strength = view1.findViewById(R.id.strengthEdit);

        builder1.setView(view1);

        builder1.setTitle("Enter Medicine")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText medname = view1.findViewById(R.id.nameEdit);
                        EditText strength = view1.findViewById(R.id.strengthEdit);
                        EditText start = view1.findViewById(R.id.etstartdate1);
                        EditText end = view1.findViewById(R.id.etenddate1);

                        String medstr = medname.getText().toString();
                        String strengthstr = strength.getText().toString();
                        String startstr = start.getText().toString();
                        String endstr = end.getText().toString();



                        addCard1(medstr, strengthstr, startstr, endstr);

                        medname.setText(null);
                        strength.setText(null);
                        start.setText(null);
                        end.setText(null);

                 //      clearTextfields();
                    }


                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        EditText start = view1.findViewById(R.id.etstartdate1);
        EditText end = view1.findViewById(R.id.etenddate1);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(prescription_page2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

                        start.setText(format);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(prescription_page2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        String format = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

                        end.setText(format);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

         LinearLayout layoutcont = view1.findViewById(R.id.edittextcontainer);
        TextView addbtn = view1.findViewById(R.id.addetbtn1);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button cancelet = view1.findViewById(R.id.cancelets);
//                EditText et1 = view1.findViewById(R.id.ettimes1);
//                layoutcont.addView(et1);
                EditText editText = new EditText(prescription_page2.this);
                layoutcont.addView(editText);

                cancelet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layoutcont.removeView(editText);
                    }
                });
            }
        });


        dialog1 = builder1.create();
    }

    @SuppressLint("MissingInflatedId")
    private void addCard1(String medstr, String strengthstr, String startstr, String endstr) {

        View view1 = getLayoutInflater().inflate(R.layout.card,null);
        TextView cmedname = view1.findViewById(R.id.tvmedname1);
        TextView cstrength = view1.findViewById(R.id.tvstrength1);
        TextView cstart = view1.findViewById(R.id.tvstartdate1);
        TextView cend = view1.findViewById(R.id.tvenddate1);

        Button delete1 = view1.findViewById(R.id.delete1);
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.containerpres2.removeView(view1);

            }
        });

        cmedname.setText(medstr);
        cstrength.setText(strengthstr + " mg");
        cstart.setText(startstr);
        cend.setText(endstr);

        binding.containerpres2.addView(view1);
    }

}