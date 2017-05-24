package com.example.samsung.mypic1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class EscortProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escortprofile);

                new B(this).execute();


    }
    public void displayHearRate(View view){
        Intent intent = new Intent(this,HeartRate.class);
        startActivity(intent);
    }
}

class B extends AsyncTask<Void,Void,String> {


    private Context con;
    private String url_string="http://kholood.heliohost.org/getPhoneNumber.php";
    private URL url;
    private HttpURLConnection httpURLConnection;
    private OutputStream os;
    private BufferedWriter bufferedWriter;
    private String data;
    private InputStream is;
    private BufferedReader bufferedReader;
    private Singleton singleton;
    private String result = "";
    private String line;

    public B(Context context) {

        con = context;
        singleton = Singleton.getInstance();
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            urlConnection(url_string);

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(singleton.getId(), "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();
            is = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));

            if ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            is.close();
            httpURLConnection.disconnect();

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String aVoid) {

        TextView name, phone;
        name = (TextView) ((EscortProfile) con).findViewById(R.id.escortname);
        phone = (TextView) ((EscortProfile) con).findViewById(R.id.phoneNumber);


        String n = "";
        String[] s = aVoid.split("\",\"");
        n = s[0];
        String[] ss = s[1].split("\"]]]");
        phone.setText("+966" + ss[0]);
        singleton.setPhoneNumber("+966" + ss[0]);

        String[] nn = n.split(Pattern.quote("[[[\""));
        name.setText(nn[1]);


    }
    private void urlConnection(String urlLink) throws Exception {

        url = new URL(urlLink);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        os = httpURLConnection.getOutputStream();

    }
}