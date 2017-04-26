package com.example.samsung.mypic1;


import android.content.Context;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String ,Void , String> {

    Context cont;
    BackgroundTask(Context c) {

        this.cont = c;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... voids) {
       //URL to the php files uploaded to the online server
        String reg_url = "http://kholood.heliohost.org/Register.php";
        String login_url="http://kholood.heliohost.org/Login.php";
        String relative_url = "http://kholood.heliohost.org/AddEscort.php";
        //////////////////////////////////////////////////////////////////
        String method = voids[0];
        URL url;
        HttpURLConnection httpURLConnection;
        OutputStream os;
        BufferedWriter bufferedWriter;
        String data;
        InputStream is;
        BufferedReader bufferedReader;
        String result = "";
        String line;
        ///////////////////////////////////////////////////////////////////
        if(method.equalsIgnoreCase("register".trim())){

            String name = voids[1];
            String email = voids[2];
            String password = voids[3];


            try {
                url = new URL(reg_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                os = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));


                data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                    break;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(method.equalsIgnoreCase("login".trim())){

            String log_name = voids[1];
            String log_password = voids[2];
            try {

                url = new URL(login_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                os = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(log_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(log_password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                is = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                    break;
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return result.trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(method.equalsIgnoreCase("addEscorts".trim())){

            String name = voids[1];
            String phone = voids[2];
            try {

                url = new URL(relative_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                os = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                is = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                    break;
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return result.trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(cont,result,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}

