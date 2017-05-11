package com.example.samsung.mypic1;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class locationlestiner implements LocationListener {

   private Context context;

    locationlestiner(Context c){
        this.context = c;
    }
    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(context,"log: "+ Double.toString(location.getLongitude())+" , lat: "+ Double.toString(location.getLatitude()),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        Toast.makeText(context," GPS status is changed",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderEnabled(String s) {

        Toast.makeText(context," GPS is on ",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(context," GPS is off ",Toast.LENGTH_LONG).show();
    }
}

