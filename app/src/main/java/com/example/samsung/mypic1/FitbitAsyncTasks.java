package com.example.samsung.mypic1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FitbitAsyncTasks extends AsyncTask<String, Void, String>
{
    Context context;
    private HttpURLConnection conn;
    private ProgressDialog progressDialog;
    private Singleton singleton;
    int max = 100;
    int min = 50;
    int dan = 1000;
    int rate;
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

                            String hearRate = singleton.getHeartRate();

                            if (!hearRate.isEmpty()) {

                                rate = Integer.parseInt(hearRate);

                                if(dan < min || dan > max) {
                                    ((HeartRate)context).Alarm();
                                    ((HeartRate)context).sendSMSMessage();
                                }

                            }
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
            String auth = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0RkJMSlciLCJhdWQiOiIyMjhUTVAiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3bnV0IHdzbGUgd3dlaSB3c29jIHdzZXQgd2FjdCB3bG9jIiwiZXhwIjoxNDk1NTg3NDY5LCJpYXQiOjE0OTU1NTg2Njl9.-K_juVa1JXSFT54igLkBPcWtlxL2sZLWPYHq1PmsoKk";
            String basicAuth = "Bearer " + auth;
            conn.setRequestProperty ("Authorization", basicAuth);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(35000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int code = conn.getResponseCode();
            InputStream stream = conn.getInputStream();
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

}

