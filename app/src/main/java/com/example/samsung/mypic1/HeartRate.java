package com.example.samsung.mypic1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HeartRate extends AppCompatActivity {

    String message;
    String phone;
    Singleton singleton;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heartrate);

        new FitbitAsyncTasks(HeartRate.this).execute();


    }

    protected void Alarm(){
       mediaPlayer = MediaPlayer.create(this,R.raw.start);
        mediaPlayer.start();
        }
    protected void sendSMSMessage() {

      //  Toast.makeText(getApplicationContext(), "inside sendSMSMessage().", Toast.LENGTH_LONG).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
          //  Toast.makeText(getApplicationContext(), "inside first if sendSMSMessage().", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            //    Toast.makeText(getApplicationContext(), "inside second if sendSMSMessage().", Toast.LENGTH_LONG).show();
            } else {
             //   Toast.makeText(getApplicationContext(), "inside else sendSMSMessage().", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
              //  Toast.makeText(getApplicationContext(), "inside case.", Toast.LENGTH_LONG).show();
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 //   Toast.makeText(getApplicationContext(), "inside if.", Toast.LENGTH_LONG).show();
                    singleton = Singleton.getInstance();
                    message = singleton.getLocation();
                    phone = singleton.getPhoneNumber();
                 //   Toast.makeText(getApplicationContext(), message + "\n" + phone, Toast.LENGTH_LONG).show();
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone.trim(), null, "HELP\n" + message, null, null);
                    Toast.makeText(getApplicationContext(), "Message sent.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Message faild, please try later.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    public void displayProfile(View view) {
        Intent intent = new Intent(this, EscortProfile.class);
        startActivity(intent);
    }

    public void displayLocation(View view) {
        Intent intent = new Intent(this, Alert.class);
        startActivity(intent);
    }


}
