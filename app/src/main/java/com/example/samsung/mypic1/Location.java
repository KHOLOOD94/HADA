package com.example.samsung.mypic1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Location extends AppCompatActivity {

    Singleton singleton;
    TextView t;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);
        t = (TextView) findViewById(R.id.LocationText);
        loc();
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
                loc();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loc() {
        singleton = Singleton.getInstance();

        gps = new GPS(Location.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            t.setText("My Location is - \nLat: "
                    + latitude + "\nLong: " + longitude);
            singleton.setLocation("My Location is - \nLat: "
                    + latitude + "\nLong: " + longitude);


        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }
    public void displayProfile(View view){
        Intent intent = new Intent(this,EscortProfile.class);
        startActivity(intent);
    }
}

