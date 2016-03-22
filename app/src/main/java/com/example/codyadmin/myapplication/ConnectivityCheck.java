package com.example.codyadmin.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by CodyADMIN on 3/20/2016.
 */

public class ConnectivityCheck{
    Context context;

    ConnectivityCheck(Context context) {
        this.context = context;
    }
    public boolean isNetworkReachable() {
        ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = mManager.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.getState() == NetworkInfo.State.CONNECTED);
    }
    public boolean isNetwork(){
        boolean isReachable = isNetworkReachable();
        if (!isReachable) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No Network Connection");
            builder.setMessage("The Network is unavailable. Please try your request again later.");
            builder.setPositiveButton("OK", null);
            builder.create().show();
        }
        return isReachable;
    }
 }