package com.example.kashyapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splashscreen extends AppCompatActivity {

  //  ImageView applogo;
  //  Animation upslide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

       // upslide = AnimationUtils.loadAnimation(this, R.anim.upslide);
     //   applogo.setAnimation(upslide);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashscreen.this, login.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}