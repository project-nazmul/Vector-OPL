package com.opl.pharmavector.service;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.LocationResult;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.geolocation.DoctorChamberLocate;
import com.opl.pharmavector.pmdVector.DashBoardPMD;

public class MyLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE = "com.opl.pharmavector.googlelocationbackground.UPDATE_LOCATION";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("locationAdd", "MyLocationService onReceive called!");
        if (intent != null) {
            final String aciton = intent.getAction();

            if (ACTION_PROCESS_UPDATE.equals(aciton)) {
                LocationResult result = LocationResult.extractResult(intent);

                try {
                    Location location = result.getLastLocation();
                    String location_string = "" + location.getLatitude() +
                            "/" + location.getLongitude();

                    String lat = String.valueOf(location.getLatitude());
                    String lang = String.valueOf(location.getLongitude());
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // try {
                    Log.e("tryBlock-->", latitude + "---" + longitude);
                    String myLang = String.valueOf(longitude);
                    String myLat = String.valueOf(latitude);
                    Intent local = new Intent();
                    local.setAction(ACTION_PROCESS_UPDATE);
                    local.putExtra("langtitude", myLang);
                    local.putExtra("latitude", myLat);
                    context.sendBroadcast(local);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}