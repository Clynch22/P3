package com.example.codyadmin.myapplication;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Cody Lynch And Anthony Wiegratz
 *
 */
public class DownloadActivity extends AsyncTask<String, Void, String> {
    private static final int READ_THIS_AMOUNT = 8096;
    private static final String TAG = "DownloadTask";
    MainActivity myActivity;
    private ArrayList<Pet> myList = new ArrayList<Pet>();
    JSONObject myJO;
    JSONArray myJA;
    JSONObject jUrl;
    ArrayList<String> names = new ArrayList<String>();
    // 1 second
    private static final int TIMEOUT = 1000;
    private String myQuery = "";


    DownloadActivity(MainActivity activity) {
        attach(activity);
    }

    //
    /**
     * @param name
     * @param value
     * @return this allows you to build a safe URL with all spaces and illegal
     *         characters URLEncoded usage mytask.setnameValuePair("param1",
     *         "value1").setnameValuePair("param2",
     *         "value2").setnameValuePair("param3", "value3")....
     */
    public DownloadActivity setnameValuePair(String name, String value) {
        try {
            if (name.length() != 0 && value.length() != 0) {

                // if 1st pair that include ? otherwise use the joiner char &
                if (myQuery.length() == 0)
                    myQuery += "?";
                else
                    myQuery += "&";

                myQuery += name + "=" + URLEncoder.encode(value, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this;
    }

    @Override
    protected String doInBackground(String... params) {
       myJO = getJS();

        try{
            myJA = myJO.getJSONArray("pets");
            for(int i = 0; i<myJA.length(); i++){
                myJO = myJA.getJSONObject(i);
                Pet pet = new Pet();
                pet.setName(myJO.optString("name"));
           ;
               pet.setFile(myJO.optString("file"));
                myList.add(pet);
                names.add(myJO.optString("name"));
            }
        }
        catch(Exception e){
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

      return null;
    }

    private JSONObject getJS(){
        String json = "";
        BufferedReader reader = null;

        try{
            String mUrl = myActivity.getUrl();
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            try{
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()), READ_THIS_AMOUNT);
                StringBuffer sb = new StringBuffer();
                String line = "";
                while((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                    System.out.println(line);
                }
                json = sb.toString();
            }
            finally {
                reader.close();
                connection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            jUrl = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jUrl;
    }
    @Override
    protected void onPostExecute(String result) {
        if (myActivity != null) {
            myActivity.doSpinner(names);
            myActivity.doToast("apples");

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onCancelled(java.lang.Object)
     */
    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }

    /**
     * important do not hold a reference so garbage collector can grab old
     * defunct dying activity
     */
    void detach() {
        myActivity = null;
    }

    /**
     * @param activity
     *            grab a reference to this activity, mindful of leaks
     */
    void attach(MainActivity activity) {
        this.myActivity = activity;
    }

};