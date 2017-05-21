package com.example.samsung.mypic1;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;


class BackgroundTask extends AsyncTask<String ,Void , String> {

    private Context cont;
    private String method ;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private OutputStream os;
    private BufferedWriter bufferedWriter;
    private String data;
    private InputStream is;
    private BufferedReader bufferedReader;
    private String result = "";
    private String line;
    private String name ;
    private String email ;
    private String password ;
    private String phone;
    Singleton singleton;


    BackgroundTask(Context c) {
        this.cont = c;

    }


    @Override
    protected String doInBackground(String... voids) {
        //URL to the php files uploaded to the online server
        String reg_url = "http://kholood.heliohost.org/Register.php";
        String login_url = "http://kholood.heliohost.org/Login.php";
        String relative_url = "http://kholood.heliohost.org/AddEscort.php";

        method = voids[0];

        try{
        if (method.equalsIgnoreCase("register".trim())) {

             name = voids[1];
             email = voids[2];
             password = voids[3];

                 urlConnection(reg_url);

                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));


                data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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

        }
        if (method.equalsIgnoreCase("login".trim())) {

            name = voids[1];
            password = voids[2];

                urlConnection(login_url);


                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") ;
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
                return result.trim();
        }

        if (method.equalsIgnoreCase("addEscorts".trim())) {

            name = voids[1];
            phone = voids[2];

                urlConnection(relative_url);

                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
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
                return result.trim();
          }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        String login= result;
        String [] string = login.split("\t");
        singleton = Singleton.getInstance();
        singleton.setId(string[1].trim());

       Intent intent;
      if(string[0].trim().equalsIgnoreCase("Login Success")){
          Toast.makeText(cont, string[0] , Toast.LENGTH_LONG).show();

         // start();

          intent = new Intent(cont,HeartRate.class);
          cont.startActivity(intent);
          Toast.makeText(cont, singleton.getLocation() , Toast.LENGTH_LONG).show();
          Toast.makeText(cont, singleton.getPhone() , Toast.LENGTH_LONG).show();
        }
        if(result.trim().equalsIgnoreCase("Escort Added")){
            Toast.makeText(cont, result, Toast.LENGTH_LONG).show();
            intent = new Intent(cont,HeartRate.class);
            cont.startActivity(intent);
        }
        if(result.trim().equalsIgnoreCase("Register Success")){
            Toast.makeText(cont, result, Toast.LENGTH_LONG).show();
            intent = new Intent(cont,AddEscorts.class);
            cont.startActivity(intent);
        }
    }

    protected void urlConnection(String urlLink) throws Exception {

        url = new URL(urlLink);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        os = httpURLConnection.getOutputStream();

    }
    protected void start(){

        Intent i1 = new Intent(cont,Alert.class);
        Intent i2 = new Intent(cont,EscortProfile.class);
        Intent i3 = new Intent(cont,HeartRate.class);


        cont.startActivity(i1);
        cont.startActivity(i2);
        cont.startActivity(i3);


    }
}
