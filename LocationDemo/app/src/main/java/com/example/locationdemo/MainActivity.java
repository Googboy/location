package com.example.locationdemo;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView longitude;
    private TextView latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
        Location mLocation = getLocation(this);
        longitude.setText("Longitude:"+mLocation.getLongitude());//获取经度
        latitude.setText("Latitude:"+mLocation.getLatitude());//获取纬度
    }

    private Location getLocation(MainActivity mainActivity) {
        LocationManager locationManager = (LocationManager) mainActivity.getSystemService(MainActivity.LOCATION_SERVICE);
        @SuppressWarnings("MissingPermission")
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null){
            //noinspection MissingPermission
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return location;
    }
}
