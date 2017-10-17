package com.example.locationupdate;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;


import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends Activity {
    private TextView textView;
    private Location location;
    private LocationManager locationManager;
    private LocationListener listener;
    private Runnable runnable;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.location);
        textView.setText("正在获取经纬度信息...");
        listener = new MyListener();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        runnable = new MyRunnable();
        Looper looper = Looper.myLooper();
        handler = new Handler(looper);
        handler.postDelayed(runnable,60000);
        locationManager.requestSingleUpdate(GPS_PROVIDER,listener,looper);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.removeUpdates(listener);
                handler.removeCallbacks(runnable);
                locationManager.requestLocationUpdates(GPS_PROVIDER,1000,0,listener);
            }
        });
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            locationManager.removeUpdates(listener);
            if (location == null){
                textView.setText("获取位置失败");
            }else {
                textView.setText("经度:"+location.getLongitude()+"\n纬度："+location.getLatitude());
            }
        }
    }

    @Override
    protected void onStop() {
        locationManager.removeUpdates(listener);
        handler.removeCallbacks(runnable);
        super.onStop();
    }

    private class MyListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            MainActivity.this.location = location;
            textView.setText("经度:"+location.getLongitude()+"\n纬度:"+location.getLatitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
