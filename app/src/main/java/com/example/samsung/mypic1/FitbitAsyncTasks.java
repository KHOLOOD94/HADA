package com.example.samsung.mypic1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class FitbitAsyncTasks extends AsyncTask<String, Void, String>
{
    Context context;
    private HttpURLConnection conn;
    private ProgressDialog progressDialog;
    private Singleton singleton;
    public FitbitAsyncTasks(Context context)
    {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings)
    {
//        makeServiceConnectionPost();
        return makeServiceConnectionGet("https://api.fitbit.com/1/user/4FBLJW/activities/heart/date/today/1d.json");
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);

        parseJsonData(result);
        progressDialog.dismiss();
    }

    private void parseJsonData(String result)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("activities-heart-intraday"))
            {
                JSONObject jObject = jsonObject.getJSONObject("activities-heart-intraday");
                if (jObject.has("dataset"))
                {
                    JSONArray jsonArray = jObject.getJSONArray("dataset");
                    for (int i = jsonArray.length() - 1; i < jsonArray.length(); i++)
                    {
                        JSONObject rateObject = jsonArray.getJSONObject(i);
                        if (rateObject.has("time") && rateObject.has("value"))
                        {
                            String timHRtimee = rateObject.getString("time");
                            String HRValue = rateObject.getString("value");
                            Toast.makeText(context, "HRtime = " + timHRtimee + " HRValue = " + HRValue, Toast.LENGTH_LONG).show();
                            singleton = Singleton.getInstance();

                            TextView t = (TextView)((HeartRate)context).findViewById(R.id.heartRate);
                            t.setText(HRValue);
                            singleton.setHeartRate(t.getText().toString());
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private String makeServiceConnectionGet(String strUrl)
    {
        String response = "";
        try
        {
            URL url = new URL("https://api.fitbit.com/1/user/4FBLJW/activities/heart/date/today/1d.json");
            conn = (HttpURLConnection) url.openConnection();
//            String userCredentials = "Jumanaalseraihi@yahoo.com:jumana1994";
            String auth = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0RkJMSlciLCJhdWQiOiIyMjhUTVAiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3bnV0IHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNDk1NTg3NDY5LCJpYXQiOjE0OTU1NTg2Njl9.-K_juVa1JXSFT54igLkBPcWtlxL2sZLWPYHq1PmsoKk";
            String basicAuth = "Bearer " + auth;//new String(Base64.encode(auth.getBytes(),Base64.DEFAULT));
            conn.setRequestProperty ("Authorization", basicAuth);
//            conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0RllYOFkiLCJhdWQiOiIyMjhUTVAiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd251dCB3cHJvIHdzbGUgd3dlaSB3c29jIHdhY3Qgd3NldCB3bG9jIiwiZXhwIjoxNDk1NDY2Nzg4LCJpYXQiOjE0OTU0Mzc5ODh9.K3pSn3o9R28F9z6pBMvYijiCL82BjiF3uQivBWGg02o");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setReadTimeout(30000); /* milliseconds */
            conn.setConnectTimeout(35000); /* milliseconds */
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int code = conn.getResponseCode();
            InputStream stream = conn.getInputStream();
//            int statusCode = conn.getResponseCode();
            response = convertInputStreamToString(stream);
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
        return response;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    public String makeServiceConnectionPost()
    {
        String response = "";
        try
        {
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("client_id", "228JN6");
            postDataParams.put("grant_type", "authorization_code");
            postDataParams.put("redirect_uri", "228JN6");
            URL url = new URL("https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=228JN6&redirect_uri=http%3A%2F%2Fcare-of-me.com%2F&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800\n");
//            conn = (HttpURLConnection) url.openConnection();
//
//            conn.setReadTimeout(10000); /* milliseconds */
//            conn.setConnectTimeout(15000); /* milliseconds */
//            conn.setRequestMethod("GET");
//
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            conn.setRequestProperty("Authorization", "Basic " + Base64.encode("228JN6:caca2e5b13810c9583db0b3108b2a5bf".getBytes(), Base64.NO_WRAP));
////            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.connect();
//
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getPostDataString(postDataParams));
//
//            writer.flush();
//            writer.close();
//            os.close();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0RllYOFkiLCJhdWQiOiIyMjhUTVAiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd251dCB3cHJvIHdzbGUgd3dlaSB3c29jIHdhY3Qgd3NldCB3bG9jIiwiZXhwIjoxNDk1NDY2Nzg4LCJpYXQiOjE0OTU0Mzc5ODh9.K3pSn3o9R28F9z6pBMvYijiCL82BjiF3uQivBWGg02o");
            conn.setReadTimeout(30000); /* milliseconds */
            conn.setConnectTimeout(35000); /* milliseconds */
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            InputStream stream = conn.getInputStream();
//            statusCode = conn.getResponseCode();
//          InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertInputStreamToString(stream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            conn.disconnect();
        }
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}

