
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

public class ReportDashboard extends Activity implements View.OnClickListener {

    public String userName_1, userName, userName_2, UserName_2, new_version;
    JSONParser jsonParser;
    List<NameValuePair> params;
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
    Button mpo_dcr, dcr_monitor;
    ProgressBar p_bar1, p_bar2;
    private SessionManager session;
    Typeface fontFamily;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportdashboard);
        initViews();
        mpodcrfollowup();
        admindcrmonitor();
        session = new SessionManager(getApplicationContext());
        logout.setOnClickListener(new View.OnClickListener() {
            Bundle b = getIntent().getExtras();

            //UserName = b.getString("UserName");
            //UserName_1= b.getString("UserName_1");
            //UserName_2= b.getString("UserName_2");
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {
                            Log.d("New_pass2", "New_pass2");

                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String UserName_1 = b.getString("userName_1");
                            String UserName_2 = b.getString("userName_2");

                            Intent i = new Intent(ReportDashboard.this, RmDashboard.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_1);
                            i.putExtra("UserName_2", UserName_2);

                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_1);
                            i.putExtra("userName_2", UserName_2);

                            Log.d("userName", "UserName" + userName);

                            Log.d("UserName_1", "UserName_1" + UserName_1);
                            Log.d("UserName_2", "UserName_2" + UserName_2);


                            startActivity(i);
                            //finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();

            }
        });

    }

    private void admindcrmonitor() {

        dcr_monitor.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(ReportDashboard.this, ReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                               // startActivity(i);
                            } else {
                                Intent i = new Intent(ReportDashboard.this, FMfollowupreport.class);
                                i.putExtra("rm_code", userName);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", RmDashboard.globalRegionalCode);
                                i.putExtra("sm_flag", "N");
                                i.putExtra("sm_code", "XXX");
                                i.putExtra("admin_flag", "N");
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

                            } else {
                                Intent i = new Intent(ReportDashboard.this, FMfollowupreport.class);
                                i.putExtra("rm_code", userName);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", RmDashboard.globalRegionalCode);
                                i.putExtra("sm_flag", "N");
                                i.putExtra("sm_code", "XXX");
                                i.putExtra("admin_flag", "N");
                                Log.w("PassedFMFollowupReport", userName + "---" + RmDashboard.globalRegionalCode +"-------"+ userName);
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

    private void mpodcrfollowup() {
        mpo_dcr.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(ReportDashboard.this, ReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);

                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(ReportDashboard.this, MPODailyReport.class);
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
                                Intent i = new Intent(ReportDashboard.this, ReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(ReportDashboard.this, MPODailyReport.class);
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
    }



    private void initViews() {
        mpo_dcr = findViewById(R.id.mpo_dcr);
        dcr_monitor = findViewById(R.id.doctor_dcr);
        p_bar1 = findViewById(R.id.p_bar1);
        p_bar2 = findViewById(R.id.p_bar2);

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b");
        db = new DatabaseHandler(this);



    }


    private void logoutUser() {

        session.setLogin(false);
        session.invalidate();
        Intent intent = new Intent(ReportDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();

    }


    @Override
    public void onClick(View v) {

    }
}