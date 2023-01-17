package com.opl.pharmavector.msd_doc_support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.opl.pharmavector.AmDashboard;
import com.opl.pharmavector.AssistantManagerDashboard;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.GMDashboard1;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.exam.ExamResultFollowup;
import com.opl.pharmavector.exam.QuizActivity;
import com.opl.pharmavector.pcconference.PcProposal;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RmDashboard;
import com.opl.pharmavector.SalesManagerDashboard;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.VacantMpoPwd;
import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class DocSupportDashboard extends Activity implements View.OnClickListener {

    public String user_code,user_name,user_flag;
    Button btn_doc_support_req,btn_doc_support_approval,btn_doc_support_followup,back,btn_msd_program_followup;
    ProgressBar bar_doc_support_req,bar_doc_support_approval,bar_doc_support_followup,bar_msd_program_followup;
    TextView user_show1;
    LinearLayout layout_mpo_doc_support,layout_msd_program;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_support_dashboard);
        initViews();

        doc_support_req();
        doc_support_followup();
        msd_program_followup();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                switch (user_flag) {
                    case "MPO": {
                        Intent i = new Intent(DocSupportDashboard.this, Dashboard.class);
                        i.putExtra("UserName", user_code);
                        i.putExtra("UserName_2", Dashboard.globalterritorycode);
                        i.putExtra("new_version", "Vector");
                        i.putExtra("message_3", user_flag);
                        i.putExtra("ff_type", Dashboard.ff_type);
                        i.putExtra("password", Dashboard.password);
                        i.putExtra("vector_version", getResources().getString(R.string.vector_version));
                        i.putExtra("emp_code", Dashboard.globalempCode);
                        i.putExtra("emp_name", Dashboard.globalempName);
                        startActivity(i);
                        break;
                    }
                    case "FM": {
                        Intent i = new Intent(DocSupportDashboard.this, AmDashboard.class);
                        i.putExtra("UserName", AmDashboard.globalFMCode);
                        i.putExtra("new_version", AmDashboard.new_version);
                        i.putExtra("UserName_2", AmDashboard.globalAreaCode);
                        i.putExtra("message_3", AmDashboard.message_3);
                        i.putExtra("password", AmDashboard.password);
                        i.putExtra("ff_type", AmDashboard.ff_type);
                        i.putExtra("vector_version", R.string.vector_version);
                        i.putExtra("emp_code", AmDashboard.globalempCode);
                        i.putExtra("emp_name", AmDashboard.globalempName);
                        startActivity(i);
                        break;
                    }
                    case "RM": {
                        Intent i = new Intent(DocSupportDashboard.this, RmDashboard.class);
                        i.putExtra("UserName", user_code);
                        i.putExtra("UserName_2", RmDashboard.globalRegionalCode);
                        i.putExtra("new_version", "Vector");
                        i.putExtra("message_3", user_flag);
                        i.putExtra("ff_type", RmDashboard.ff_type);
                        i.putExtra("vector_version", getResources().getString(R.string.vector_version));
                        startActivity(i);
                        break;
                    }
                    case "AM": {
                        Intent i = new Intent(DocSupportDashboard.this, AssistantManagerDashboard.class);
                        i.putExtra("UserName", user_code);
                        i.putExtra("UserName_2", AssistantManagerDashboard.globalZONECode);
                        i.putExtra("new_version", "Vector");
                        i.putExtra("message_3", user_flag);
                        i.putExtra("vector_version", getResources().getString(R.string.vector_version));
                        startActivity(i);
                        break;
                    }
                    case "SM": {
                        Intent i = new Intent(DocSupportDashboard.this, SalesManagerDashboard.class);
                        i.putExtra("UserName", user_code);
                        i.putExtra("UserName_2", SalesManagerDashboard.globalDivisionCode);
                        i.putExtra("new_version", "Vector");
                        i.putExtra("message_3", user_flag);
                        i.putExtra("vector_version", getResources().getString(R.string.vector_version));
                        startActivity(i);
                        break;
                    }
                    default: {
                        Intent i = new Intent(DocSupportDashboard.this, GMDashboard1.class);
                        i.putExtra("UserName", GMDashboard1.globalAdmin);
                        i.putExtra("new_version", GMDashboard1.new_version);
                        i.putExtra("UserName_2", GMDashboard1.globalAdminDtl);
                        i.putExtra("message_3", GMDashboard1.message_3);
                        i.putExtra("password", GMDashboard1.password);
                        i.putExtra("ff_type", GMDashboard1.ff_type);
                        i.putExtra("vector_version", R.string.vector_version);
                        i.putExtra("emp_code", GMDashboard1.globalempCode);
                        i.putExtra("emp_name", GMDashboard1.globalempName);

                        startActivity(i);
                        break;
                    }
                }


            }
        });

    }

    private void doc_support_req() {

        bar_doc_support_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {

                            if (user_flag.equals("MPO")){
                                if (!NetInfo.isOnline(getBaseContext())) {
                                    noInternet();
                                } else {
                                    Intent i = new Intent(DocSupportDashboard.this, DocSupportReq.class);
                                    i.putExtra("user_code", user_code);
                                    i.putExtra("user_name", user_name);
                                    i.putExtra("user_flag", user_flag);
                                    startActivity(i);

                                }
                            }else{
                                showSnack();
                            }

                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();

            }
        });

        btn_doc_support_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (user_flag.equals("MPO")){
                                if (!NetInfo.isOnline(getBaseContext())) {
                                    noInternet();
                                } else {
                                    Intent i = new Intent(DocSupportDashboard.this, DocSupportReq.class);
                                    i.putExtra("user_code", user_code);
                                    i.putExtra("user_name", user_name);
                                    i.putExtra("user_flag", user_flag);
                                    startActivity(i);

                                }
                            }else{

                                showSnack();
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

    private void doc_support_followup() {

        bar_doc_support_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                noInternet();
                            } else {
                                Intent i = new Intent(DocSupportDashboard.this, DocSupportFollowup.class);
                                i.putExtra("user_code", user_code);
                                i.putExtra("user_name", user_name);
                                i.putExtra("user_flag", user_flag);
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

        btn_doc_support_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                noInternet();
                            } else {

                                Intent i = new Intent(DocSupportDashboard.this, DocSupportFollowup.class);
                                i.putExtra("user_code", user_code);
                                i.putExtra("user_name", user_name);
                                i.putExtra("user_flag", user_flag);
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


    private void msd_program_followup() {

        bar_msd_program_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                noInternet();
                            } else {
                                Intent i = new Intent(DocSupportDashboard.this, MSDProgramFollowup.class);
                                i.putExtra("user_code", user_code);
                                i.putExtra("user_name", user_name);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);
                                //showSnack2();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();

            }
        });

        btn_msd_program_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                noInternet();
                            } else {

                                Intent i = new Intent(DocSupportDashboard.this, MSDProgramFollowup.class);
                                i.putExtra("user_code", user_code);
                                i.putExtra("user_name", user_name);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);
                                //showSnack2();
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

    @SuppressLint("SetTextI18n")
    private void initViews() {

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        back = findViewById(R.id.back);
        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        user_show1 = findViewById(R.id.user_show1);
        layout_mpo_doc_support = findViewById(R.id.layout_mpo_doc_support);
        layout_msd_program = findViewById(R.id.layout_msd_program);

        btn_doc_support_req  = findViewById(R.id.btn_doc_support_req);
        bar_doc_support_req  = findViewById(R.id.bar_doc_support_req);
        btn_doc_support_followup  = findViewById(R.id.btn_doc_support_followup);
        bar_doc_support_followup  = findViewById(R.id.bar_doc_support_followup);

        Bundle b = getIntent().getExtras();
        assert b != null;

        btn_msd_program_followup  = findViewById(R.id.btn_msd_program_followup);
        bar_msd_program_followup  = findViewById(R.id.bar_msd_program_followup);

        user_code = b.getString("user_code");
        user_name = b.getString("user_name");
        user_flag = b.getString("user_flag");
        user_show1.setText(user_code+"\t" +"["+user_name+"]");
        layout_mpo_doc_support.setVisibility(View.VISIBLE);



    }


    private void showSnack() {
        new Thread() {
            public void run() {
               DocSupportDashboard.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "You dont have permission to submit proposal";
                        Toasty.warning(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();

                    }
                });
            }
        }.start();

    }


    private void showSnack2() {
        new Thread() {
            public void run() {
                DocSupportDashboard.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "This feature will be available soon";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();

                    }
                });
            }
        }.start();

    }

    private void noInternet() {
        new Thread() {
            public void run() {
                DocSupportDashboard.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "No internet Connection";
                        Toasty.warning(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();

                    }
                });
            }
        }.start();

    }


    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onClick(View v) {

    }
}




