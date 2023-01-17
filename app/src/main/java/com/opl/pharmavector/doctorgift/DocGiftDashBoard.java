package com.opl.pharmavector.doctorgift;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opl.pharmavector.AmDashboard;
import com.opl.pharmavector.AssistantManagerDashboard;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.GMDashboard1;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RmDashboard;
import com.opl.pharmavector.SalesManagerDashboard;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.giftfeedback.GiftFeedbackEntry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DocGiftDashBoard extends Activity implements View.OnClickListener {
    public String userName_1, userName, userName_2;
    JSONParser jsonParser;
    public  TextView user_show1,user_show2;
    List<NameValuePair> params;
    private DatabaseHandler db;
    private SessionManager session;
    String UserName_2;
    String new_version;
    String user_flag;
    Button backbt,back;
    Button b1,b2,b3;
    ProgressBar p1,p2,p3;
    LinearLayout l1,l2,l3,layout_gift_feedback_entry;
    Button btn_gift_feedback_followup,btn_gift_feedback_entry;
    ProgressBar bar_gift_feedback_followup,bar_gift_feedback_entry;
    TextView tv_gift_feedback_followup,tv_gift_feedback_entry;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorgift_dashboard);
        initViews();

        backbt.setOnClickListener(new View.OnClickListener() {
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
                            switch (user_flag.toString().trim()) {
                                case "MPO": {
                                    Intent i = new Intent(DocGiftDashBoard.this, Dashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", new_version);
                                    i.putExtra("user_flag", user_flag);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("ff_type", Dashboard.ff_type);
                                    i.putExtra("emp_code", Dashboard.globalempCode);
                                    i.putExtra("emp_name", Dashboard.globalempName);
                                    startActivity(i);
                                    break;
                                }
                                case "FM": {
                                    Intent i = new Intent(DocGiftDashBoard.this, AmDashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", new_version);
                                    i.putExtra("user_flag", user_flag);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("ff_type", Login.user_ff_type);

                                    Log.d("userName", "UserName" + userName);
                                    Log.d("new_version", "new_version" + new_version);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    Log.d("user_flag", "user_flag" + user_flag);
                                    startActivity(i);
                                    break;
                                }
                                case "RM": {
                                    Intent i = new Intent(DocGiftDashBoard.this, RmDashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", new_version);
                                    i.putExtra("user_flag", user_flag);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("ff_type", Login.user_ff_type);

                                    Log.d("userName", "UserName" + userName);
                                    Log.d("new_version", "new_version" + new_version);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    Log.d("user_flag", "user_flag" + user_flag);
                                    startActivity(i);
                                    break;
                                }
                                case "AM": {
                                    Intent i = new Intent(DocGiftDashBoard.this, AssistantManagerDashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", new_version);
                                    i.putExtra("user_flag", user_flag);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("ff_type", Login.user_ff_type);


                                    Log.d("userName", "UserName" + userName);
                                    Log.d("new_version", "new_version" + new_version);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    Log.d("user_flag", "user_flag" + user_flag);
                                    startActivity(i);
                                    break;
                                }
                                case "SM": {
                                    Intent i = new Intent(DocGiftDashBoard.this, SalesManagerDashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", new_version);
                                    i.putExtra("user_flag", user_flag);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("ff_type", Login.user_ff_type);

                                    Log.d("userName", "UserName" + userName);
                                    Log.d("new_version", "new_version" + new_version);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    Log.d("user_flag", "user_flag" + user_flag);
                                    startActivity(i);
                                    break;
                                }
                                case "AD": {
                                    Intent i = new Intent(DocGiftDashBoard.this, GMDashboard1.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", new_version);
                                    i.putExtra("user_flag", user_flag);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("ff_type", Login.user_ff_type);

                                    Log.d("userName", "UserName" + userName);
                                    Log.d("new_version", "new_version" + new_version);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    Log.d("user_flag", "user_flag" + user_flag);
                                    startActivity(i);
                                    break;
                                }
                            }
                            //finish();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(DocGiftDashBoard.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                builder.setTitle("Exit !").setMessage("Are you sure you want to exit Vector?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        JSONParser jsonParser = new JSONParser();
                                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("logout", "logout"));
                                        JSONObject json = jsonParser.makeHttpRequest(Login.LOGIN_URL, "POST", params);
                                    }
                                });
                                server.start();
                                logoutUser();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("MainActivity", "");
                            }
                        })
                        .show();
            }
        });

    }

    private void initViews() {

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        back = findViewById(R.id.back);
        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        user_show1 = findViewById(R.id.user_show1);
        user_show2 =  findViewById(R.id.user_show2);
        l1= (LinearLayout) findViewById(R.id.gift_entry);
        l2= (LinearLayout) findViewById(R.id.gift_entry_followup);
        l3= (LinearLayout) findViewById(R.id.gift_entry_approval);
        layout_gift_feedback_entry= (LinearLayout) findViewById(R.id.layout_gift_feedback_entry);
        b1= (Button) findViewById(R.id.btn_gift_entry);
        b2= (Button) findViewById(R.id.btn_gift_approval);
        b3= (Button) findViewById(R.id.btn_gift_follow_up);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        btn_gift_feedback_followup = findViewById(R.id.btn_gift_feedback_followup);
        btn_gift_feedback_entry = findViewById(R.id.btn_gift_feedback_entry);
        bar_gift_feedback_followup = findViewById(R.id.bar_gift_feedback_followup);
        bar_gift_feedback_entry = findViewById(R.id.bar_gift_feedback_entry);
        p1=  findViewById(R.id.progressBar1);
        p2=  findViewById(R.id.progressBar2);
        p3=  findViewById(R.id.progressBar3);
        p1.setOnClickListener(this);
        p2.setOnClickListener(this);
        p3.setOnClickListener(this);
        btn_gift_feedback_followup.setOnClickListener(this);
        btn_gift_feedback_entry.setOnClickListener(this);
        bar_gift_feedback_followup.setOnClickListener(this);
        bar_gift_feedback_entry.setOnClickListener(this);
        backbt =findViewById(R.id.backbt);
        backbt.setTypeface(fontFamily);
        backbt.setText("\uf08b");
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        user_flag = b.getString("user_flag");
        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        db = new DatabaseHandler(this);
        user_show1.setText(userName + " " + UserName_2 + " ");
        l1.setVisibility(View.GONE);
        l3.setVisibility(View.GONE);


        switch (user_flag) {
            case "MPO":
                l3.setVisibility(View.GONE);
                break;
            case "ASM":
                l1.setVisibility(View.GONE);
                break;
            case "SM":
                l1.setVisibility(View.GONE);
                break;
            case "GM":
                l1.setVisibility(View.GONE);
                break;
        }

    }


    private void logoutUser() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onClick(View v) {

            if (v.getId()==R.id.btn_gift_entry|| v.getId()== R.id.progressBar1){
                Intent intent = new Intent(DocGiftDashBoard.this,DocGiftLoad.class);
                intent.putExtra("UserName", userName);
                intent.putExtra("UserName_2", UserName_2);
                intent.putExtra("new_version", new_version);
                intent.putExtra("user_flag", user_flag);
                startActivity(intent);
            }else if(v.getId()==R.id.btn_gift_approval|| v.getId()== R.id.progressBar3){
                Intent intent = new Intent(DocGiftDashBoard.this,DocGiftEntryApproval.class);
                intent.putExtra("UserName", userName);
                intent.putExtra("UserName_2", UserName_2);
                intent.putExtra("new_version", new_version);
                intent.putExtra("user_flag", user_flag);
                startActivity(intent);
            }else if (v.getId()== R.id.btn_gift_follow_up || v.getId()== R.id.progressBar2){
                Intent intent = new Intent(DocGiftDashBoard.this,GiftEntryFollowup.class);
                intent.putExtra("UserName", userName);
                intent.putExtra("UserName_2", UserName_2);
                intent.putExtra("new_version", new_version);
                intent.putExtra("user_flag", user_flag);
                startActivity(intent);
            }
            else if (v.getId()== R.id.btn_gift_feedback_entry || v.getId()== R.id.bar_gift_feedback_entry){
                Intent intent = new Intent(DocGiftDashBoard.this, GiftFeedbackEntry.class);
                intent.putExtra("UserName", userName);
                intent.putExtra("UserName_2", UserName_2);
                intent.putExtra("new_version", new_version);
                intent.putExtra("user_flag", user_flag);
                startActivity(intent);
            }


    }
}
