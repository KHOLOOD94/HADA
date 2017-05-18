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
import java.util.regex.Pattern;

public class EscortProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escortprofile);
        new B(this).execute();

    }
}

class B extends AsyncTask<Void,Void,String> {
    private String json_string;
   // private String readPhoneNumber;
   // private String readName;
    private Singleton singleton;
    private Context con;
    private String url_string="http://kholood.heliohost.org/getPhoneNumber.php";

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
        singleton = Singleton.getInstance();
        TextView name , phone;
        name = (TextView)((EscortProfile)con).findViewById(R.id.escortname);
        phone = (TextView)((EscortProfile)con).findViewById(R.id.phoneNumber);
       // readName = aVoid;
       // readPhoneNumber = aVoid;
       // String regx1 ="\"relative_name\":\"";
       // String regx2 = "\"relative_phone_no\":\"";
        //String n="";
       // String [] s =  aVoid.split("\",\"");
       // n = s[0];
       // String [] ss = s[1].split("\"]]]");
     //   String nameResult = splitNameAndPhone(readName,regx1);
      //  phone.setText("0"+ss[0]);
       // String [] nn = n.split(Pattern.quote("[[[\""));
        name.setText(aVoid);

     //   String [] s1 = readPhoneNumber.split(regx2);
      //  String [] ss1 = s[0].split("\"");

        //String phoneResult = splitNameAndPhone(readPhoneNumber,regx2);
       // phone.setText("0"+ss1[0]);
      //  singleton.setPhoneNumber(phone.getText().toString());
    }
    protected String splitNameAndPhone(String obj,String regx){
        String result="";
        String [] s =  obj.split(regx);
        String [] ss = s[1].split("\"");
        result = ss[0];
        return result;
    }
}