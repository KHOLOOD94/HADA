package com.example.samsung.mypic1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HeartRate extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 0;
    Singleton singleton;
    int abnormalRate = 1000;
    TextView heartRate;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heartrate);

        heartRate = (TextView) findViewById(R.id.heartRate);

        heartRate.setText("100");
        int rate = Integer.parseInt(heartRate.getText().toString().trim());
        if( rate >= abnormalRate){
            sendSMSMessage();
        }

    }
    protected void sendSMSMessage() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    singleton = Singleton.getInstance();
                    message = singleton.getLocation();
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+966545129728", null,"HELP\n"+ message, null, null);
                    Toast.makeText(getApplicationContext(), "Message sent.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Message faild, please try later.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
    public void displayProfile(View view){
        Intent intent = new Intent(this,EscortProfile.class);
        startActivity(intent);
    }
    public void displayLocation(View view){
        Intent intent = new Intent(this,Alert.class);
        startActivity(intent);
    }
}
