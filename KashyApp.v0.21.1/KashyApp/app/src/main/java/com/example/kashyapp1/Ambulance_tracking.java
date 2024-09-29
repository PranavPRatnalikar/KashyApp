package com.example.kashyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.kashyapp1.databinding.ActivityAmbulanceTrackingBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Ambulance_tracking extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ActivityAmbulanceTrackingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAmbulanceTrackingBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.ambulancecallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:7758063017"));
                startActivity(intent);
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        LatLng pune = new LatLng(18.463845771797303, 73.86825734134763);
//        mMap.addMarker(new MarkerOptions().position(pune).title("VIT PUNE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pune));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pune,16f));

//        LatLng Sumadhu = new LatLng(18.457973403799556, 73.87013031255186);
//        mMap.addMarker(new MarkerOptions().position(Sumadhu).title("sumadhu Hostel"));

        LatLng siddhivinayak = new LatLng(18.455987541325896, 73.8710834760922);
        mMap.addMarker(new MarkerOptions().position(siddhivinayak).title("Siddhi Vinayak Hospital"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(siddhivinayak));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(siddhivinayak,18f));

        LatLng bhakare = new LatLng(18.45770788861744, 73.86773398300392);
        mMap.addMarker(new MarkerOptions().position(bhakare).title("Bhakare Super-Speciality Hospital").);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:7758063017"));


        LatLng punemultispeciality = new LatLng(18.463195546921195, 73.86749459717682);
        mMap.addMarker(new MarkerOptions().position(punemultispeciality).title("Pune municipality Raja Shivchatrapati Hospital"));

        LatLng premchand = new LatLng(18.46777377002083, 73.86820179797286);
        mMap.addMarker(new MarkerOptions().position(premchand).title("Premchand Oswal Hospital"));

    }

}