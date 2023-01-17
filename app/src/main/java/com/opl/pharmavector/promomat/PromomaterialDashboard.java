package com.opl.pharmavector.promomat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

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
import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
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

public class PromomaterialDashboard extends Activity implements View.OnClickListener {

    public String userName_1, userName, userName_2;
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


    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promomatdashboard);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        LinearLayout l1 =  findViewById(R.id.mpo_doc_l1);
        LinearLayout l2 =  findViewById(R.id.manager_doc_l2);
        LinearLayout l3 =  findViewById(R.id.manager_doc_serv);


        Button promo_sample_followup =  findViewById(R.id.promo_sample_followup);
        Button promo_ppm_followup =  findViewById(R.id.promo_ppm_followup);
        Button promo_gift_followup =  findViewById(R.id.promo_gift_followup);


        ProgressBar bar_1 =  findViewById(R.id.progressBar1);
        ProgressBar bar_2 =  findViewById(R.id.progressBar2);
        ProgressBar bar_3 =  findViewById(R.id.progressBar3);


        Button back =  findViewById(R.id.back);
        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        user_show1 =  findViewById(R.id.user_show1);
        user_show2 =  findViewById(R.id.user_show1);
        Bundle b = getIntent().getExtras();
        final String userName = b.getString("UserName");
        final String UserName_2 = b.getString("UserName_2");
        final String new_version = b.getString("new_version");
        final String user_flag = b.getString("user_flag");
        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        db = new DatabaseHandler(this);
        ArrayList<String> mpo_code_interna = db.getterritoryname();
        String mpo_code_i = mpo_code_interna.toString();
        user_show1.setText(userName + " " + UserName_2 + " ");


        promo_sample_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(PromomaterialDashboard.this, PromomaterialDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PromomaterialDashboard.this, PromoMaterialFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("promo_type", "S");
                                i.putExtra("user_code", userName);
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
                                Intent i = new Intent(PromomaterialDashboard.this, PromomaterialDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                startActivity(i);

                            } else {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PromomaterialDashboard.this, PromoMaterialFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("promo_type", "S");
                                i.putExtra("user_code", userName);
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
        promo_ppm_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(PromomaterialDashboard.this, PromomaterialDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PromomaterialDashboard.this, PromoMaterialFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("promo_type", "P");
                                i.putExtra("user_code", userName);
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


                                Intent i = new Intent(PromomaterialDashboard.this, PromomaterialDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                startActivity(i);

                            } else {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PromomaterialDashboard.this, PromoMaterialFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("promo_type", "P");
                                i.putExtra("user_code", userName);
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
        promo_gift_followup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(PromomaterialDashboard.this, PromomaterialDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PromomaterialDashboard.this, PromoMaterialFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("promo_type", "G");
                                i.putExtra("user_code", userName);
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
        bar_3.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(PromomaterialDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                startActivity(i);

                            } else {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PromomaterialDashboard.this, PromoMaterialFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("promo_type", "G");
                                i.putExtra("user_code", userName);
                                startActivity(i);

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
        session = new SessionManager(getApplicationContext());

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        

    }


    private void logoutUser() {
        session.setLogin(false);
        // session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(PromomaterialDashboard.this, Login.class);
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


