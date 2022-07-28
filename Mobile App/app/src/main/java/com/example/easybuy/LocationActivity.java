package com.example.easybuy;

import androidx.fragment.app.FragmentActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.easybuy.databinding.ActivityLocationBinding;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#24A0ED")));

        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        Intent intent = getIntent();
        String address = intent.getStringExtra("Customer Address");

        LatLng deliveryLocation = getDeliveryLocation(address);
        mMap.addMarker(new MarkerOptions().position(deliveryLocation).title("Delivery Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deliveryLocation,15));
    }

    private LatLng getDeliveryLocation(String address){

        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList;

        try{

            addressList = geocoder.getFromLocationName(address,1);

            if(addressList != null){
                double latitude = addressList.get(0).getLatitude();
                double longitude = addressList.get(0).getLongitude();

                return new LatLng(latitude,longitude);
            }
        } catch (Exception exception) { exception.printStackTrace(); }

        return null;
    }
}