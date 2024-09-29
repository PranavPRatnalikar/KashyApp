package com.example.kashyapp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kashyapp1.databinding.ActivityPatientEditMedicalRecordsBinding;
import com.example.kashyapp1.databinding.ActivityPrescriptionPageBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class prescriptionPage extends AppCompatActivity {}

//    ActivityPrescriptionPageBinding binding;
//
//    Button add;
//    AlertDialog dialog,dialog2;
//    LinearLayout layout,layoutdose;
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityPrescriptionPageBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        add = findViewById(R.id.addp);
//        layout = findViewById(R.id.container3p);
//        layoutdose = findViewById(R.id.containerDose);
//
//        buildDialog();
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.show();
//            }
//        });
//    }
//    @SuppressLint("MissingInflatedId")
//    private void buildDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View view = getLayoutInflater().inflate(R.layout.dialog,null);
//
//        String value;
//        int posi;
//
//        EditText name = view.findViewById(R.id.nameEdit);
////         EditText strength = view.findViewById(R.id.strengthEdit);
////        Spinner dosespinner = view.findViewById(R.id.spinnerDosage);
////        EditText startdate = view.findViewById(R.id.etstartdate);
////        EditText enddate = view.findViewById(R.id.etenddate);
////        EditText times = view.findViewById(R.id.ettimes);
//
//
//
////        value = dosespinner.getSelectedItem().toString();
////        posi = dosespinner.getSelectedItemPosition();
//
//       // dosespinner.setSelection(posi);
//
//
//
//
//
//        builder.setView(view);
//
//        builder.setTitle("enter medicine name")
//                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        addCard(name.getText().toString(),strength.getText().toString(),value,posi,
//                                startdate.getText().toString(),enddate.getText().toString(),times.getText().toString());
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//        dialog = builder.create();
//
//
//    }
//
//
//
//    @SuppressLint("MissingInflatedId")
//    private void addCard(String name,String pillstrength,String dose,int posi,String startdate,String enddate,String times){
//        View view = getLayoutInflater().inflate(R.layout.card,null);
//
//        TextView nameview = view.findViewById(R.id.name);
//        TextView pillstr = view.findViewById(R.id.pillstrength);
//        Button delete = view.findViewById(R.id.delete);
//        TextView dosage = view.findViewById(R.id.tvDosage);
//        TextView startdt = view.findViewById(R.id.tvstartdate);
//        TextView enddt = view.findViewById(R.id.tvenddate);
//        TextView time = view.findViewById(R.id.tvtimes);
//
//
//        nameview.setText(name);
//        pillstr.setText(pillstrength);
//        dosage.setText(dose);
//        startdt.setText(startdate);
//        enddt.setText(enddate);
//        time.setText(times);
//
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {layout.removeView(view);}
//        });
//
//
//     //   dosageOptions(posi);
//        layout.addView(view);
//
//    }
//}
//
//
//
//
//
//
//
////////////////////////////////////////////
//
////    private void dosageOptions(int pos) {
////
////        View view1 = getLayoutInflater().inflate(R.layout.card1_on_schedule,null);
////        View view2 = getLayoutInflater().inflate(R.layout.card2_at_intervals,null);
////        View view3 = getLayoutInflater().inflate(R.layout.card3_when_needed,null);
////
////        if(pos == 0){
////
////            layout.addView(view1);
////
////        }
////
////        else if(pos == 1){
////
////            layout.addView(view2);
////
////        }
////        else if(pos == 2){
////            layout.addView(view3);
////        }
////
////        else {
////            return;
////        }
////
////    }