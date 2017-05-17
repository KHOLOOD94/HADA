package com.example.samsung.mypic1;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EscortProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escortprofile);
        new BackgroundTask(this).execute();

    }
}

class B extends AsyncTask<Void,Void,String> {

    String json_string;
    String readPhoneNumber;
    String readName;
    Singleton singleton;
    Context con;
    String url_string="http://kholood.heliohost.org/getPhoneNumber.php";

    public B(Context context) {
        con = context;
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
        TextView name , phone;
        name = (TextView)((EscortProfile)con).findViewById(R.id.name);
        phone = (TextView)((EscortProfile)con).findViewById(R.id.phone);
        readName = aVoid;
        readPhoneNumber = aVoid;

        String regx1 = "\"relative_phone_no\":\"";
        String regx2 ="\"relative_name\":\"";
        String nameResult = splitNameAndPhone(readName,regx1);
        name.setText(nameResult);
        String phoneResult = splitNameAndPhone(readPhoneNumber,regx2);
        phone.setText(phoneResult);

    }
    protected String splitNameAndPhone(String obj,String regx){
        String result="";
        String [] s =  obj.split(regx);
        String [] ss = s[1].split("\"");
        result = ss[0];
        return result;
    }
}