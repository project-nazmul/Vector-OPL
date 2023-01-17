package com.opl.pharmavector;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.nativecss.NativeCSS;
import com.opl.pharmavector.util.NetInfo;
import org.apache.http.NameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import android.app.ProgressDialog;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL_AM;


public class MPODCCDashboard extends Activity implements View.OnClickListener {

    public String userName_1, userName, userName_2;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public String mpo_code;
    public AutoCompleteTextView actv;
    private ListView lv, lv2;
    private ProgressDialog pDialog;
    private DatabaseHandler db;
    private static String url = BASE_URL_AM+"get_products.php";
    private String URL_CUSOTMER = BASE_URL_AM+"get_customer.php";
    private String TAG = Offlinereport.class.getSimpleName();
    ArrayList<HashMap<String, String>> productList;
    ArrayList<HashMap<String, String>> customerlist;
    private Button logout;
    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;

    private SessionManager session;


    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpodccdashboard);
        Button mpo_dcc_followup =  findViewById(R.id.mpo_dcc_followup);
        Button mpo_dcc_split =  findViewById(R.id.mpo_dcc_split);
        ProgressBar p_bar1 =  findViewById(R.id.p_bar1);
        ProgressBar p_bar2 =  findViewById(R.id.p_bar2);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");

        Bundle b = getIntent().getExtras();
        final String userName = b.getString("UserName");
        final String UserName_2 = Dashboard.globalterritorycode;
        final String new_version = b.getString("new_version");

        mpo_code = Dashboard.globalmpocode;
        logout =  findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf060 ");// &#xf060
        db=new DatabaseHandler(this);

        mpo_dcc_followup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                pDialog = new ProgressDialog(MPODCCDashboard.this);
                pDialog.setMessage("Campaign Data is loading.. Please wait ");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODCCDashboard.this, MPODCCDashboard.class);
                                i.putExtra("UserName", Dashboard.globalmpocode);
                                i.putExtra("UserName_2", Dashboard.globalterritorycode);
                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODCCDashboard.this, MPODCCFollowupgift.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
                pDialog.dismiss();
            }

        });

        p_bar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODCCDashboard.this, MPODCCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODCCDashboard.this, MPODCCFollowupgift.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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

        mpo_dcc_split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODCCDashboard.this, MPODCCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODCCDashboard.this, MPODccStock.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("mpo_code", mpo_code);
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

        p_bar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODCCDashboard.this, MPODCCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODCCDashboard.this, MPODccStock.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            /*
                            Intent i = new Intent(MPODCCDashboard.this, Dashboard.class);
                            i.putExtra("UserName", Dashboard.globalmpocode);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_2", Dashboard.globalterritorycode);
                            i.putExtra("ff_type", Dashboard.ff_type);
                            startActivity(i);
                            */
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();
            }
        });

    }


    private void logoutUser() {
        session.setLogin(false);
        session.invalidate();
        Intent intent = new Intent(MPODCCDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();

    }


    @Override
    public void onClick(View v) {

    }
}