package com.revolut.ratesapp.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

public class NetworkAvailability {


    @Inject
    Context context;

    public NetworkAvailability(Context context) {
        this.context = context;

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        boolean val = (activeNetworkInfo != null && activeNetworkInfo.isConnected());

        return val;
    }

}
