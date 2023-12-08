package com.example.mapdemo;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private final OnMapReadyCallback callback = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(callback);

        SearchView searchView = findViewById(R.id.place);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Address> addresses = null;
                if (!query.isEmpty()) {
                    Toast.makeText(MainActivity.this, "GET", Toast.LENGTH_SHORT).show();
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try {
                        if (Build.VERSION.SDK_INT >= 33) {
                            addresses = geocoder.getFromLocationName(query, 1);
                        } else {
                            addresses = geocoder.getFromLocationName(query, 1);
                        }
                    } catch (Exception e) {
                        Log.i("exception", String.valueOf(e));
                    }
                    if (addresses != null && !addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        double latitude = address.getLatitude();
                        double longitude = address.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        googleMap.clear();
                        googleMap.resetMinMaxZoomPreference();
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(query));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.setMinZoomPreference(6.0f);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
