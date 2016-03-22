package com.example.codyadmin.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
// NULL OBJECT WITH doSpinner() FIX ITTTTTTTTTTT
public class MainActivity extends AppCompatActivity {
private Spinner spinner;
    private SharedPreferences myPreference;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private String url;
    JSONArray jsonArray;
    int numberentries;
    int currententry;
    ArrayList<Pet> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        spinner = (Spinner) findViewById(R.id.spinner1);
        getPrefs();
        ConnectivityCheck check = new ConnectivityCheck(this);
        if(check.isNetwork()){
            DownloadActivity dl = new DownloadActivity(this);
            dl.execute(url);

         PreferenceManager.getDefaultSharedPreferences(this);
         listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals("sites")) {
                    url = myPreference.getString("site","http://www.tetonsoftware.com/pets/");
                }

            }
        };
        myPreference.registerOnSharedPreferenceChangeListener(listener);
    }
        Context context = getApplicationContext();
        CharSequence text =url;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();}

    public void getPrefs(){
        myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = myPreference.getString("site","http://www.tetonsoftware.com/pets/");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent myIntent = new Intent(this, SettingsActivity.class);
                startActivity(myIntent);
                break;
            default:
                break;
        }
        return true;
    }
    public String getUrl(){
        return url;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    public void doSpinner(String string) {

    }
}
