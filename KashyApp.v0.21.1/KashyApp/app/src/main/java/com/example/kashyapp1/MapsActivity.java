package com.example.kashyapp1;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.kashyapp1.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
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

        LatLng bhakare = new LatLng(18.45770788861744, 73.86773398300392);
        mMap.addMarker(new MarkerOptions().position(bhakare).title("Bhakare Super-Speciality Hospital"));

        LatLng punemultispeciality = new LatLng(18.463195546921195, 73.86749459717682);
        mMap.addMarker(new MarkerOptions().position(punemultispeciality).title("Pune municipality Raja Shivchatrapati Hospital"));

        LatLng premchand = new LatLng(18.46777377002083, 73.86820179797286);
        mMap.addMarker(new MarkerOptions().position(premchand).title("Premchand Oswal Hospital"));


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}