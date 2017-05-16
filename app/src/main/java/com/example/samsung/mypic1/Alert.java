package com.example.samsung.mypic1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class Alert extends AppCompatActivity {

    TextView t;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    // GPSTracker class
    GPSTracker gps;


    String json_string;
    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loc();
    }

    public void loc() {
        t = (TextView) findViewById(R.id.LocationText);
        gps = new GPSTracker(Alert.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            t.setText("Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }
}


   /*public void getRelativeNumber() {

        new BackgroundTask().execute();

    }


    class BackgroundTask extends AsyncTask<Void,Void,String> {

        String url_string="http://kholood.heliohost.org/getPhoneNumber.php";

        public BackgroundTask() {
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                URL url = new URL(url_string);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();

                while((json_string=bufferedReader.readLine())!=null){

                    stringBuilder.append(json_string+"\n");

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            TextView t = (TextView) findViewById(R.id.CallMe);
            JSON_STRING = aVoid;

            String [] s = JSON_STRING.split("\"relative_phone_no\":\"");
            String [] ss = s[1].split("\"");
            t.setText(ss[0]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }*/

