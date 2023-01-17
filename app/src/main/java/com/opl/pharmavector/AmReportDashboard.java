//ReportDashboard
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

public class AmReportDashboard extends Activity implements View.OnClickListener {

    public String userName_1, userName, userName_2;
    JSONParser jsonParser;
    List<NameValuePair> params;

    public AutoCompleteTextView actv;
    private ListView lv, lv2;
    private ProgressDialog pDialog;
    private DatabaseHandler db;
    private static String url = "http://opsonin.com.bd/dept_order_android_am2/get_products.php";
    private String URL_CUSOTMER = "http://opsonin.com.bd/dept_order_android_am2/get_customer.php";
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
        setContentView(R.layout.amreportdashboard);
        Button mpo_dcr = (Button) findViewById(R.id.mpo_dcr);
        Button doctor_dcr = (Button) findViewById(R.id.doctor_dcr);
        Button not_visit = (Button) findViewById(R.id.not_visit);

        Button am_dcc_followup = (Button) findViewById(R.id.am_dcc_followup);

        ProgressBar p_bar1 = (ProgressBar) findViewById(R.id.p_bar1);
        ProgressBar p_bar2 = (ProgressBar) findViewById(R.id.p_bar2);
        ProgressBar p_bar3 = (ProgressBar) findViewById(R.id.p_bar3);
        ProgressBar p_bar4 = (ProgressBar) findViewById(R.id.p_bar4);


        Bundle b = getIntent().getExtras();
        final String userName = b.getString("UserName");
        final String UserName_2 = b.getString("UserName_2");
        final String new_version = b.getString("new_version");
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf060 ");// &#xf060
        db=new DatabaseHandler(this);





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
                                Intent i = new Intent(AmReportDashboard.this, AmReportDashboard.class);

                                //  Intent i = new Intent(Dashboard.this, DcrReport.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {


                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                   Intent i = new Intent(AmReportDashboard.this, AmMPODailyReport.class);


                                //Intent i = new Intent(AmReportDashboard.this, MPODailyReport.class);MPODailyReport
                                 //Intent i = new Intent(AmDashboard.this, AmDcrReport.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
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
                                Intent i = new Intent(AmReportDashboard.this, AmReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed personalexpenses", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {


                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();

                                // Intent i = new Intent(Dashboard.this, MPODailyReport.class);

                                Intent i = new Intent(AmReportDashboard.this, AmMPODailyReport.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed personalexpenses", userName + "---" + UserName_2);

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




        doctor_dcr.setOnClickListener(new View.OnClickListener() {


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
                                Intent i = new Intent(AmReportDashboard.this, AmReportDashboard.class);

                                //  Intent i = new Intent(Dashboard.this, DcrReport.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {


                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //   Intent i = new Intent(Dashboard.this, MPODailyReport.class);


                                Intent i = new Intent(AmReportDashboard.this, AmDoctorDailyReport.class);
                                // Intent i = new Intent(Dashboard.this, DcrReport.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
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
                                Intent i = new Intent(AmReportDashboard.this, AmReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed personalexpenses", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {


                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();

                                // Intent i = new Intent(Dashboard.this, MPODailyReport.class);

                                Intent i = new Intent(AmReportDashboard.this, AmDoctorDailyReport.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed personalexpenses", userName + "---" + UserName_2);

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









        /*============================================================ end doctor followup     ========================================*/




        not_visit.setOnClickListener(new View.OnClickListener() {


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
                                Intent i = new Intent(AmReportDashboard.this, AmReportDashboard.class);

                                //  Intent i = new Intent(Dashboard.this, DcrReport.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {



                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //   Intent i = new Intent(Dashboard.this, MPODailyReport.class);


                                Intent i = new Intent(AmReportDashboard.this, AmNotVisitedDoctor.class);
                                // Intent i = new Intent(Dashboard.this, DcrReport.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
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





        p_bar3.setOnClickListener(new View.OnClickListener() {


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
                                Intent i = new Intent(AmReportDashboard.this, AmReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed personalexpenses", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {



                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();

                                // Intent i = new Intent(Dashboard.this, MPODailyReport.class);

                                Intent i = new Intent(AmReportDashboard.this, AmNotVisitedDoctor.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed personalexpenses", userName + "---" + UserName_2);

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





        /*============================================================ end doctor followup     ========================================*/






        /*============================================================ DCC FOLLOWUP    ========================================*/




        am_dcc_followup.setOnClickListener(new View.OnClickListener() {


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
                                Intent i = new Intent(AmReportDashboard.this, AmReportDashboard.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {



                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //   Intent i = new Intent(Dashboard.this, MPODailyReport.class);


                                Intent i = new Intent(AmReportDashboard.this, AmDCCFollowup.class);
                                // Intent i = new Intent(Dashboard.this, DcrReport.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
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





        p_bar4.setOnClickListener(new View.OnClickListener() {


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
                                Intent i = new Intent(AmReportDashboard.this, AmReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed personalexpenses", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {



                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();

                                // Intent i = new Intent(Dashboard.this, MPODailyReport.class);

                                Intent i = new Intent(AmReportDashboard.this, AmDCCFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed personalexpenses", userName + "---" + UserName_2);

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













        /*============================================================ end DCC FOLLOWUP      ========================================*/







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

                            Intent i = new Intent(AmReportDashboard.this, AmDashboard.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_1);
                            i.putExtra("UserName_2", UserName_2);

                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_1);
                            i.putExtra("userName_2", UserName_2);

                            Log.d("userName","UserName"+userName);

                            Log.d("UserName_1","UserName_1"+UserName_1);
                            Log.d("UserName_2","UserName_2"+UserName_2);







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


    private void logoutUser() {
        session.setLogin(false);
        // session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(AmReportDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();

    }





    @Override
    public void onClick(View v) {

    }
}