package com.opl.pharmavector.doctorservice;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.Offlinereport;
import com.opl.pharmavector.pcconference.PcProposal;
import com.opl.pharmavector.R;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.geolocation.DoctorChamberLocate;
import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;

import java.util.List;

import android.app.ProgressDialog;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class DoctorServiceDashboard extends Activity implements View.OnClickListener {


    JSONParser jsonParser;
    List<NameValuePair> params;

    public AutoCompleteTextView actv;
    private ListView lv, lv2;
    private ProgressDialog pDialog;
    private DatabaseHandler db;
    private String TAG = Offlinereport.class.getSimpleName();
    ArrayList<HashMap<String, String>> productList;
    ArrayList<HashMap<String, String>> customerlist;
    private Button logout;
    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;
    public TextView user_show1, user_show2;
    private SessionManager session;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    LinearLayout l1, l2, l3,l4;
    Button doc_service_followup, manager_doc_service_followup, doc_service_ack, doc_service_track, doc_chamber, back;
    ProgressBar bar_1, bar_2, bar_4, bar_5, bar_6;
    String userName,UserName_2,new_version,user_flag;
    LocationManager locationManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorservicedashboard);

        intiViews();
        trackdoctorService();
        followupdocService();
        ackknwldgedocService();
        managerdocServiceFollowup();
        docChamberLocate();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        finish();
                    }
                });
                backthred.start();
            }
        });
        session = new SessionManager(getApplicationContext());

    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void trackdoctorService() {

        doc_service_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //  Intent i = new Intent(Dashboard.this, DoctorServiceFollowup.class);
                                Intent i = new Intent(DoctorServiceDashboard.this, DoctorServiceTrackMonthly.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "M");
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                startActivity(i);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(DoctorServiceDashboard.this, DoctorServiceTrackMonthly.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "M");
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("user_flag", "M");
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
    }

    private void followupdocService() {

        doc_service_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(DoctorServiceDashboard.this, DoctorServiceDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //  Intent i = new Intent(Dashboard.this, DoctorServiceFollowup.class);
                                Intent i = new Intent(DoctorServiceDashboard.this, DoctorServiceFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "M");
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("user_flag", "M");
                                startActivity(i);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                            } else {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(DoctorServiceDashboard.this, DoctorServiceFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "M");
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("user_flag", "M");
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
    }

    private void ackknwldgedocService() {

        doc_service_ack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(DoctorServiceDashboard.this, DoctorServiceAck.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "M");
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                startActivity(i);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();


            }
        });
        bar_2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(DoctorServiceDashboard.this, DoctorServiceAck.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "M");
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();


            }
        });
    }

    private void docChamberLocate() {

        doc_chamber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {
                                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                                    Intent i = new Intent(DoctorServiceDashboard.this, DoctorChamberLocate.class);
                                    startActivity(i);
                                }else{
                                    showGPSDisabledAlertToUser();
                                }



                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();


            }
        });
        bar_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(DoctorServiceDashboard.this, DoctorChamberLocate.class);

                            } else {
                                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                                    Intent i = new Intent(DoctorServiceDashboard.this, DoctorChamberLocate.class);
                                    startActivity(i);
                                }else{
                                    showGPSDisabledAlertToUser();
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();


            }
        });

    }

    private void managerdocServiceFollowup() {

        manager_doc_service_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(DoctorServiceDashboard.this, DoctorServiceDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(DoctorServiceDashboard.this, ManagerDoctorServiceFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                startActivity(i);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();


            }
        });
        bar_4.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {



                            } else {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(DoctorServiceDashboard.this, ManagerDoctorServiceFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();


            }
        });
    }



    private void intiViews() {

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        l1 = findViewById(R.id.mpo_doc_l1);
        l2 = findViewById(R.id.manager_doc_l2);
        l3 = findViewById(R.id.manager_doc_serv);
        l4 = findViewById(R.id.doc_chamber);
        doc_service_followup = findViewById(R.id.doc_service_followup);
        bar_1 = findViewById(R.id.progressBar1);
        doc_service_ack = findViewById(R.id.doc_service_ack);
        bar_2 = findViewById(R.id.progressBar2);
        manager_doc_service_followup = findViewById(R.id.manager_doc_service_followup);
        bar_4 = findViewById(R.id.progressBar4);
        doc_service_track = findViewById(R.id.doc_service_track);
        bar_5 = findViewById(R.id.progressBar5);

        doc_chamber = findViewById(R.id.btn_doc_chamber);
        bar_6 = findViewById(R.id.progressBar6);

        back = findViewById(R.id.back);
        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        user_show1 = findViewById(R.id.user_show1);
        Bundle b = getIntent().getExtras();


         userName = b.getString("UserName");
         UserName_2 = b.getString("UserName_2");
         new_version = b.getString("new_version");
         user_flag = b.getString("user_flag");

        if (user_flag.equals("M")) {
            l1.setVisibility(View.VISIBLE);
            l3.setVisibility(View.GONE);
        } else {
            l1.setVisibility(View.GONE);
            l4.setVisibility(View.GONE);
            l2.setVisibility(View.VISIBLE);
        }

        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        db = new DatabaseHandler(this);
        ArrayList<String> mpo_code_interna = db.getterritoryname();
        String mpo_code_i = mpo_code_interna.toString();
        user_show1.setText(userName + " " + UserName_2 + " ");
    }

    private void logoutUser() {
        session.setLogin(false);
        // session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(DoctorServiceDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onClick(View v) {

    }
}


