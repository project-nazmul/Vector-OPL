package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL_RM;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.opl.pharmavector.app.Config;
import com.opl.pharmavector.contact.Activity_PMD_Contact;
import com.opl.pharmavector.doctorservice.DoctorServiceDashboard;
import com.opl.pharmavector.doctorservice.DoctorServiceTrackMonthly;
import com.opl.pharmavector.doctorservice.ManagerDoctorServiceFollowup;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.msd_doc_support.DocSupportDashboard;
import com.opl.pharmavector.msd_doc_support.DocSupportFollowup;
import com.opl.pharmavector.msd_doc_support.MSDProgramFollowup;
import com.opl.pharmavector.pcconference.PcApproval;
import com.opl.pharmavector.pcconference.PcConferenceFollowup;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionDashboard;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup2;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.ImageLoadActivity;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.NetInfo;
import com.opl.pharmavector.util.NotificationUtils;
import com.opl.pharmavector.util.PreferenceManager;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;

import java.util.List;

import android.app.ProgressDialog;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesManagerDashboard extends Activity implements View.OnClickListener {
    public String userName_1, userName, userName_2, user, sm_code,global_admin_Code;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public AutoCompleteTextView actv;
    private ListView lv, lv2;
    private ProgressDialog pDialog;
    private DatabaseHandler db;
    private static String url = BASE_URL_RM+"get_products.php";
    private String URL_CUSOTMER = BASE_URL_RM+"get_customer.php";
    private String TAG = Offlinereport.class.getSimpleName();
    ArrayList<HashMap<String, String>> productList;
    ArrayList<HashMap<String, String>> customerlist;
    private Button logout;
    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;
    public TextView user_show1, user_show2, sm;
    private SessionManager session;
    private String submit_url = BASE_URL+"notification/save_vector_notification_token_data_test.php";
    public String message;
    public int success;
    public String tokenid;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static String IMEINumber = "0000", DeviceID = "XXXX";
    private static final int REQUEST_CODE = 101;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Button followup_report, sales_report, managers_report, notification, am_dcc_camp, pc_conference_btn, doctor_service_followup, prescription_entry,btn_vacant_rm, btn_pmd_contact,
    btn_msd_doctor_support;
    ProgressBar bar_11, bar_2, bar_21, bar_22, bar_15, bar_16, bar_prescription, bar_10,bar_vacant_rm, bar_msd_doctor_support;
    String UserName_2, mpo_code_i;
    Bundle b;
    Typeface fontFamily;
    ArrayList<String> mpo_code_interna;
    PreferenceManager preferenceManager;
    private int count;
    public static String globalempCode, globalempName ;
    CardView cardview_dcr, practiceCard2, practiceCard3, practiceCard4, practiceCard5, practiceCard6,
             practiceCard7, practiceCard8, practiceCard9, cardview_pc, cardview_promomat, cardview_salereports, cardview_msd, cardview_salesfollowup, cardview_mastercode, cardview_pmd_contact;
    ImageButton profileB, img_btn_dcr, img_btn_dcc, img_btn_productorder, img_btn_docservice, img_btn_docgiftfeedback,
             img_btn_notification, img_btn_rx, img_btn_personalexpense, img_btn_pc, img_btn_promomat, img_btn_salereports, img_btn_msd, img_btn_exam, img_btn_salesfollowup,
             img_btn_mastercode, img_pmd_contact;
    TextView tv_dcr, tv_productorder, tv_dcc, tv_docservice, tv_docgiftfeedback,
             tv_notification, tv_rx, tv_personalexpense, tv_pc, tv_promomat, tv_salereports, tv_msd,tv_exam, tv_salesfollowup, tv_mastercode, tv_pmd_contact;
    Button btn_dcr,btn_productorder,btn_dcc,btn_docservice,
            btn_docgiftfeedback,btn_notification,btn_rx,btn_personalexpense,btn_pc,btn_promomat,btn_salereports,btn_msd,btn_exam,btn_vector_feedback,btn_mastercode,btn_salesfollowup;
    public TextView t4,t5,textView3;
    public ImageView imageView2,logo_team;
    public static String team_logo,profile_image;
    public String base_url =  ApiClient.BASE_URL+"vector_ff_image/";
    public String get_ext_dt, date_flag, check_flag;
    public static String password,vectorToken, globalSMCode, globalDivisionCode,message_3,new_version,vector_version,ff_type;

    @SuppressLint("CutPasteId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.assistantmanager);
        setContentView(R.layout.activity_vector_asm_dashboard);

        initViews();
        preferenceManager = new PreferenceManager(this);
        count = preferenceManager.getTasbihCounter();
        global_admin_Code=preferenceManager.getAdmin_Code();
        Log.e("Admin Code--->",preferenceManager.getAdmin_Code());

        logoutEvent();
        firebaseToken();
        TeamLogo();
        dcrfollowup();
        docservicefollowup();
        msdDocSupport();

        vacantMpoPwd();
        noticeBoradEvent();
        salesFollowup();
        managersreport();
        prescriptionentry();
        pcConferenceEvent();
        dccFollowup();
        pmdContact();

        /*
        prescriptionEntry();
        doctorService();
        pcConference();
        notificationEvent();
        adminSalesReport();
        dcrFollowup();
        salesreportDashboard();
        VacantPassword();
        msdDocSupport();
        */
    }
/*
    private void initViews() {
        followup_report = (Button) findViewById(R.id.followup_report);
        bar_11 = (ProgressBar) findViewById(R.id.progressBar11);
        sales_report = (Button) findViewById(R.id.sales_report);
        managers_report = (Button) findViewById(R.id.managers_report);
        notification = (Button) findViewById(R.id.notification);
        bar_10 = (ProgressBar) findViewById(R.id.progressBar10);
        am_dcc_camp = (Button) findViewById(R.id.am_dcc_camp);
        bar_2 = (ProgressBar) findViewById(R.id.progressBar2);
        bar_21 = (ProgressBar) findViewById(R.id.progressBar21);
        bar_22 = (ProgressBar) findViewById(R.id.progressBar22);
        pc_conference_btn = (Button) findViewById(R.id.pc_conference_btn);
        bar_15 = (ProgressBar) findViewById(R.id.progressBar15);
        doctor_service_followup = (Button) findViewById(R.id.doctor_service_followup);
        bar_16 = (ProgressBar) findViewById(R.id.progressBar16);
        prescription_entry = findViewById(R.id.prescription_entry);
        bar_prescription = (ProgressBar) findViewById(R.id.progressBar18);
        btn_vacant_rm = findViewById(R.id.btn_vacant_rm);
        bar_vacant_rm = findViewById(R.id.bar_vacant_rm);
        user_show1 = (TextView) findViewById(R.id.user_show1);

        btn_msd_doctor_support = findViewById(R.id.btn_msd_doc_support);
        bar_msd_doctor_support = findViewById(R.id.bar_msd_doc_support);

        sm = (TextView) findViewById(R.id.sm);
        b = getIntent().getExtras();
        assert b != null;
        userName = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        sm_code = userName;
        new_version = b.getString("new_version");
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b");
        db = new DatabaseHandler(this);

        mpo_code_interna = db.getterritoryname();
        mpo_code_i = mpo_code_interna.toString();
        user = mpo_code_i;
        if(userName_2 == null) {
            userName_2 = mpo_code_i;
        }
        user_show1.setText(String.format("%s(%s)", UserName_2, userName));
        session = new SessionManager(getApplicationContext());
        sm.setText(R.string.sales_manager_name);

        globalSMCode = userName;
        globalDivisionCode = UserName_2;
    }
*/

    private void TeamLogo() {
        String team = ff_type;
        team_logo = ApiClient.BASE_URL+"team_logo/" ;

        switch (team) {
            case "G":
                String logo_image = team_logo+"d"+"."+"png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
            case "T":
                logo_image = team_logo+"t"+"."+"png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
            case "I":
                logo_image = team_logo+"i"+"."+"png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
            case "V":
                logo_image = team_logo+"v"+"."+"png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
            case "C":
                logo_image = team_logo+"g"+"."+"png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
        }
    }

    @SuppressLint("CutPasteId")
    private void initViews(){
        logout= findViewById(R.id.logout);
        user_show1 = findViewById(R.id.user_show1);
        t4= findViewById(R.id.t4);
        t5= findViewById(R.id.t5);
        textView3 = findViewById(R.id.textView3);
        imageView2 = findViewById(R.id.imageView2);
        logo_team =findViewById(R.id.logo_team);

        btn_productorder     = findViewById(R.id.btn_productorder);
        img_btn_productorder = findViewById(R.id.img_btn_productorder);
        tv_productorder      = findViewById(R.id.tv_productorder);
        practiceCard7        = findViewById(R.id.practiceCard7);

        btn_dcr       = findViewById(R.id.btn_dcr);
        img_btn_dcr   = findViewById(R.id.img_btn_dcr);
        tv_dcr        = findViewById(R.id.tv_dcr);
        cardview_dcr  = findViewById(R.id.cardview_dcr);

        btn_dcc        = findViewById(R.id.btn_dcc);
        img_btn_dcc    = findViewById(R.id.img_btn_dcc);
        tv_dcc         = findViewById(R.id.tv_dcc);
        practiceCard2  = findViewById(R.id.practiceCard2);

        btn_docservice       = findViewById(R.id.btn_docservice);
        img_btn_docservice   = findViewById(R.id.img_btn_docservice);
        tv_docservice        = findViewById(R.id.tv_docservice);
        practiceCard3        = findViewById(R.id.practiceCard3);

        btn_docgiftfeedback       = findViewById(R.id.btn_docgiftfeedback);
        img_btn_docgiftfeedback   = findViewById(R.id.img_btn_docgiftfeedback);
        tv_docgiftfeedback        = findViewById(R.id.tv_docgiftfeedback);
        practiceCard4             = findViewById(R.id.practiceCard4);

        btn_notification       = findViewById(R.id.btn_notification);
        img_btn_notification   = findViewById(R.id.img_btn_notification);
        tv_notification        = findViewById(R.id.tv_notification);
        practiceCard6             = findViewById(R.id.practiceCard6);

        btn_rx       = findViewById(R.id.btn_rx);
        img_btn_rx   = findViewById(R.id.img_btn_rx);
        tv_rx        = findViewById(R.id.tv_rx);
        practiceCard8 = findViewById(R.id.practiceCard8);

        btn_personalexpense       = findViewById(R.id.btn_personalexpense);
        img_btn_personalexpense   = findViewById(R.id.img_btn_personalexpense);
        tv_personalexpense        = findViewById(R.id.tv_personalexpense);
        practiceCard9             = findViewById(R.id.practiceCard9);

        btn_pc       = findViewById(R.id.btn_pc);
        img_btn_pc   = findViewById(R.id.img_btn_pc);
        tv_pc        = findViewById(R.id.tv_pc);
        cardview_pc  = findViewById(R.id.cardview_pc);

        btn_promomat       = findViewById(R.id.btn_promomat);
        img_btn_promomat  = findViewById(R.id.img_btn_promomat);
        tv_promomat       = findViewById(R.id.tv_promomat);
        cardview_promomat  = findViewById(R.id.cardview_promomat);

        btn_salereports       = findViewById(R.id.btn_salereports);
        img_btn_salereports  = findViewById(R.id.img_btn_salereports);
        tv_salereports       = findViewById(R.id.tv_salereports);
        cardview_salereports  = findViewById(R.id.cardview_salereports);

        btn_msd = findViewById(R.id.btn_msd);
        img_btn_msd  = findViewById(R.id.img_btn_msd);
        tv_msd       = findViewById(R.id.tv_msd);
        cardview_msd  = findViewById(R.id.cardview_msd);

        btn_salesfollowup       = findViewById(R.id.btn_salesfollowup);
        img_btn_salesfollowup  = findViewById(R.id.img_btn_salesfollowup);
        tv_salesfollowup       = findViewById(R.id.tv_salesfollowup);
        cardview_salesfollowup  = findViewById(R.id.cardview_salesfollowup);

        btn_exam = findViewById(R.id.btn_exam);
        img_btn_exam  = findViewById(R.id.img_btn_exam);
        tv_exam       = findViewById(R.id.tv_exam);
        practiceCard5  = findViewById(R.id.practiceCard5);

        btn_mastercode = findViewById(R.id.btn_mastercode);
        img_btn_mastercode  = findViewById(R.id.img_btn_mastercode);
        tv_mastercode      = findViewById(R.id.tv_mastercode);
        cardview_mastercode  = findViewById(R.id.cardview_mastercode);

        btn_pmd_contact      = findViewById(R.id.btn_pmd_contact);
        img_pmd_contact      = findViewById(R.id.img_pmd_contact);
        tv_pmd_contact       = findViewById(R.id.tv_pmd_contact);
        cardview_pmd_contact = findViewById(R.id.cardview_pmd_contact);
        btn_vector_feedback = findViewById(R.id.btn_vector_feedback);

        ff_type      = null;
        Bundle b     = getIntent().getExtras();
        assert b    != null;
        userName    = b.getString("UserName");
        UserName_2  = b.getString("UserName_2");
        new_version = b.getString("new_version");
        message_3   = b.getString("message_3");
        password    = b.getString("password");
        ff_type     = b.getString("ff_type");
        vector_version =b.getString("vector_version");
        globalempCode  =b.getString("emp_code");
        globalempName =b.getString("emp_name");

        user_show1.setText(globalempName);
        profile_image= base_url+globalempCode+"."+"jpg" ;

        Picasso.get()
                .load(profile_image)
                .into(imageView2);

        globalSMCode = userName;
        globalDivisionCode = UserName_2;
        globalSMCode = userName;
        t4.setText(globalSMCode);
        t5.setText(globalDivisionCode);
        textView3.setText("Sales Manager");
        lock_emp_check(globalempCode);
    }

    private void logoutEvent() {
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SalesManagerDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("Exit !").setMessage("Are you sure you want to exit Vector?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        preferenceManager.clearPreferences();
                                        count = 0;
                                        //unregisterReceiver(updateUIReciver);
                                        Intent logoutIntent = new Intent(SalesManagerDashboard.this, Login.class);
                                        startActivity(logoutIntent);
                                        finish();
                                    }
                                });
                                server.start();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
            }
        });
    }

    private void showSnack() {
        new Thread() {
            public void run() {
                SalesManagerDashboard.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "No internet Connection, Please Check Your Connection";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    private void firebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SalesManagerDashboard.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                vectorToken = instanceIdResult.getToken();
                //new SaveDeviceToken().execute();
            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("vector")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed) + vectorToken;
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                    }
                });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(com.opl.pharmavector.app.Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(com.opl.pharmavector.app.Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    //txtMessage.setText(message);
                }
            }
        };
    }

    private void dcrfollowup() {
        cardview_dcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {

                                Intent i = new Intent(SalesManagerDashboard.this, ASMFollowupReport.class);
                                String sm_flag = "Y";
                                String admin_flag = "N";
                                i.putExtra("sm_flag", sm_flag);
                                i.putExtra("sm_code", globalSMCode);
                                i.putExtra("UserName", userName);
                                i.putExtra("userName_1", userName_1);
                                i.putExtra("userName_2", userName_2);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("admin_flag", admin_flag);
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

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_DOCSUPPORT() {

        final BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(this);
        bottomSheetDialog2.setContentView(R.layout.multi_option_bottom_sheet_dialog);

        CardView cardview1 = bottomSheetDialog2.findViewById(R.id.cardview_1);
        CardView cardview2 = bottomSheetDialog2.findViewById(R.id.cardview_2);

        CardView cardview3 = bottomSheetDialog2.findViewById(R.id.cardview_3);
        CardView cardview4 = bottomSheetDialog2.findViewById(R.id.cardview_4);
        Button btn_1 = bottomSheetDialog2.findViewById(R.id.btn_1);
        cardview3.setVisibility(View.GONE);
        cardview4.setVisibility(View.GONE);

        TextView textView4 = bottomSheetDialog2.findViewById(R.id.textView4);


        Objects.requireNonNull(textView4).setText("Tracking\nDoctor");


        ImageView imageView3 = bottomSheetDialog2.findViewById(R.id.imageView3);
        imageView3.setBackgroundResource(R.drawable.ic_doctor_service);

        TextView changepassword = bottomSheetDialog2.findViewById(R.id.changepassword);
        Objects.requireNonNull(changepassword).setText("Doctor Service");


        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog2.dismiss();
            }
        });
        Objects.requireNonNull(cardview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SalesManagerDashboard.this, ManagerDoctorServiceFollowup.class);
                i.putExtra("userName", globalSMCode);
                i.putExtra("UserName_2", globalDivisionCode);
                i.putExtra("new_version", new_version);
                i.putExtra("user_flag", "S");
                startActivity(i);
                //  bottomSheetDialog2.dismiss();
            }
        });

        Objects.requireNonNull(cardview1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SalesManagerDashboard.this, DoctorServiceTrackMonthly.class);
                i.putExtra("userName", globalSMCode);
                i.putExtra("UserName_2", globalDivisionCode);
                i.putExtra("new_version", new_version);
                i.putExtra("user_flag", "S");
                startActivity(i);
                bottomSheetDialog2.dismiss();
            }
        });





        bottomSheetDialog2.show();
    }
    private void docservicefollowup() {
        practiceCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                showBottomSheetDialog_DOCSUPPORT();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_MSD() {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);

        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        TextView textView6 = bottomSheetDialog.findViewById(R.id.textView6);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button button3 = bottomSheetDialog.findViewById(R.id.button3);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("4.1");
        Objects.requireNonNull(button2).setText("4.2");
        Objects.requireNonNull(button3).setText("13.3");
        Objects.requireNonNull(textView4).setText("MSD\nProgram Follow up");
        Objects.requireNonNull(textView5).setText("Doctor\nSupport Follow up");
        Objects.requireNonNull(textView6).setText("MSD\nProgram Follow-up");

        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        imageView3.setBackgroundResource(R.drawable.ic_doctor_service);

        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        Objects.requireNonNull(changepassword).setText("MSD");
        cardview_rx_summary_B.setVisibility(View.GONE);

        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, MSDProgramFollowup.class);
                i.putExtra("user_code", globalSMCode);
                i.putExtra("user_name", globalDivisionCode);
                i.putExtra("user_flag", "SM");
                startActivity(i);

            }
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, DocSupportFollowup.class);
                i.putExtra("user_code", globalSMCode);
                i.putExtra("user_name", globalDivisionCode);
                i.putExtra("user_flag", "SM");
                startActivity(i);

            }
        });

        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, MSDProgramFollowup.class);
                i.putExtra("user_code", globalSMCode);
                i.putExtra("user_name", globalDivisionCode);
                i.putExtra("user_flag", "SM");
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Toast.makeText(getApplicationContext(), "bottomSheetDialog is Dismissed ", Toast.LENGTH_LONG).show();
            }
        });

        bottomSheetDialog.show();
    }
    private void msdDocSupport() {
        cardview_msd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                showBottomSheetDialog_MSD();

            }


        });


    }


    private void vacantMpoPwd() {

        cardview_mastercode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, VacantMpoPwd.class);
                i.putExtra("user_flag", "SM");
                startActivity(i);
            }
        });
        btn_mastercode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, VacantMpoPwd.class);
                i.putExtra("user_flag", "SM");
                startActivity(i);
            }
        });
        img_btn_mastercode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, VacantMpoPwd.class);
                i.putExtra("user_flag", "SM");
                startActivity(i);
            }
        });
        tv_mastercode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, VacantMpoPwd.class);
                i.putExtra("user_flag", "SM");
                startActivity(i);
            }
        });

    }
    private void noticeBoradEvent() {

        practiceCard6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("new_version", new_version);
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
        img_btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("new_version", new_version);
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
        tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("new_version", new_version);
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
        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("new_version", new_version);
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


    private void salesFollowup() {

        cardview_salesfollowup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Toast.makeText(getApplicationContext(),
                                        "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("asm_code", globalSMCode);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
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
        img_btn_salesfollowup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("asm_code", globalSMCode);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
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
        tv_salesfollowup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("asm_code", globalSMCode);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
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
        btn_salesfollowup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                                showSnack();

                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("asm_code", globalSMCode);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
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

    private void managersreport() {

        cardview_salereports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, AdminReportDashboard.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("asm_code", globalSMCode);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
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
        tv_salereports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, AdminReportDashboard.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("asm_code", globalSMCode);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
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
        img_btn_salereports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, AdminReportDashboard.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("asm_code", globalSMCode);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
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
        btn_salereports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(SalesManagerDashboard.this, AdminReportDashboard.class);
                                i.putExtra("UserName", globalSMCode);
                                i.putExtra("UserName_2", globalDivisionCode);
                                i.putExtra("asm_code", globalSMCode);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
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



    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_RXCAPTURE() {

        final BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(this);
        bottomSheetDialog2.setContentView(R.layout.multi_option_bottom_sheet_dialog);

        CardView cardview1 = bottomSheetDialog2.findViewById(R.id.cardview_1);
        CardView cardview2 = bottomSheetDialog2.findViewById(R.id.cardview_2);
        CardView cardview3 = bottomSheetDialog2.findViewById(R.id.cardview_3);
        CardView cardview4 = bottomSheetDialog2.findViewById(R.id.cardview_4);


        TextView changepassword = bottomSheetDialog2.findViewById(R.id.changepassword);

        TextView textView4 = bottomSheetDialog2.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog2.findViewById(R.id.textView5);
        TextView textView6 = bottomSheetDialog2.findViewById(R.id.textView6);
        TextView textView7 = bottomSheetDialog2.findViewById(R.id.textView7);

        Button button1 = bottomSheetDialog2.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog2.findViewById(R.id.button2);
        Button button3 = bottomSheetDialog2.findViewById(R.id.button3);
        Button button4 = bottomSheetDialog2.findViewById(R.id.button4);
        Button btn_1 = bottomSheetDialog2.findViewById(R.id.btn_1);

        ImageView imageView3 = bottomSheetDialog2.findViewById(R.id.imageView3);
        imageView3.setBackgroundResource(R.drawable.ic_rx_capture);

        Objects.requireNonNull(button1).setText("8.1");
        Objects.requireNonNull(button2).setText("8.1");
        Objects.requireNonNull(button3).setText("8.2");
        Objects.requireNonNull(button4).setText("8.3");
        Objects.requireNonNull(textView4).setText("RX\nEntry");
        Objects.requireNonNull(textView5).setText("RX\nSearch");
        Objects.requireNonNull(textView6).setText("RX\nSummary");
        Objects.requireNonNull(textView7).setText("RX\nSummary B");


        Objects.requireNonNull(changepassword).setText("Prescription Capture");

        Objects.requireNonNull(cardview1).setVisibility(View.GONE);
        Objects.requireNonNull(cardview4).setVisibility(View.GONE);
        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog2.dismiss();
            }
        });



        Objects.requireNonNull(cardview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, ImageLoadActivity.class);
                i.putExtra("manager_code",globalSMCode);
                i.putExtra("manager_detail",globalDivisionCode);
                i.putExtra("manager_flag","SM");
                startActivity(i);

            }
        });

        Objects.requireNonNull(cardview3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, PrescriptionFollowup.class);
                i.putExtra("manager_code",globalSMCode);
                i.putExtra("manager_detail", "SM");
                startActivity(i);
                // bottomSheetDialog2.dismiss();
            }
        });

        Objects.requireNonNull(cardview4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, PrescriptionFollowup2.class);
                i.putExtra("manager_code",globalSMCode);
                i.putExtra("manager_detail", "SM");
                startActivity(i);

            }
        });


        bottomSheetDialog2.show();
    }
    private void prescriptionentry(){

        practiceCard8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                showBottomSheetDialog_RXCAPTURE();
                // startActivity(i);
            }
        });

        btn_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                showBottomSheetDialog_RXCAPTURE();
                // startActivity(i);
            }
        });

        img_btn_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                showBottomSheetDialog_RXCAPTURE();
                // startActivity(i);
            }
        });

        tv_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                showBottomSheetDialog_RXCAPTURE();
                // startActivity(i);
            }
        });

    }




    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_PCCONFERENCE() {

        final BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(this);
        bottomSheetDialog2.setContentView(R.layout.multi_option_bottom_sheet_dialog);

        CardView cardview1 = bottomSheetDialog2.findViewById(R.id.cardview_1);
        CardView cardview2 = bottomSheetDialog2.findViewById(R.id.cardview_2);
        CardView cardview3 = bottomSheetDialog2.findViewById(R.id.cardview_3);
        CardView cardview4 = bottomSheetDialog2.findViewById(R.id.cardview_4);


        TextView changepassword = bottomSheetDialog2.findViewById(R.id.changepassword);

        TextView textView4 = bottomSheetDialog2.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog2.findViewById(R.id.textView5);
        TextView textView6 = bottomSheetDialog2.findViewById(R.id.textView6);
        TextView textView7 = bottomSheetDialog2.findViewById(R.id.textView7);

        Button button1 = bottomSheetDialog2.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog2.findViewById(R.id.button2);
        Button button3 = bottomSheetDialog2.findViewById(R.id.button3);
        Button button4 = bottomSheetDialog2.findViewById(R.id.button4);
        Button btn_1 = bottomSheetDialog2.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("7.1");
        Objects.requireNonNull(button2).setText("7.2");
        Objects.requireNonNull(button3).setText("7.3");
        Objects.requireNonNull(button4).setText("7.4");
        Objects.requireNonNull(textView4).setText("PC Conference\nApproval");
        Objects.requireNonNull(textView5).setText("PC Conference\nFollow up");
        Objects.requireNonNull(textView6).setText("PC\nBill Approval");
        Objects.requireNonNull(textView7).setText("PC\nBill Follow up");

        ImageView imageView3 = bottomSheetDialog2.findViewById(R.id.imageView3);
        imageView3.setBackgroundResource(R.drawable.ic_pc_conference);

        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog2.dismiss();
            }
        });

        Objects.requireNonNull(changepassword).setText("PC Conference");

        Objects.requireNonNull(cardview1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, PcApproval.class);
                i.putExtra("userName", globalSMCode);
                i.putExtra("UserName_2", globalDivisionCode);
                i.putExtra("new_version", R.string.vector_version);
                i.putExtra("UserName", globalSMCode);
                startActivity(i);
            }
        });

        Objects.requireNonNull(cardview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, PcConferenceFollowup.class);
                i.putExtra("UserName", globalSMCode);
                i.putExtra("UserName_2", globalDivisionCode);
                i.putExtra("user_flag", "SM");
                startActivity(i);
            }
        });

        Objects.requireNonNull(cardview3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, PcBillApproval.class);
                i.putExtra("userName", globalSMCode);
                i.putExtra("UserName_2", globalDivisionCode);
                i.putExtra("new_version", new_version);
                startActivity(i);
            }
        });

        Objects.requireNonNull(cardview4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SalesManagerDashboard.this, PCBillFollowup.class);
                i.putExtra("UserName", globalSMCode);
                i.putExtra("UserName_2", globalDivisionCode);
                i.putExtra("user_flag", "SM");
                startActivity(i);
            }
        });
        bottomSheetDialog2.show();
    }

    private void pcConferenceEvent() {
        cardview_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_PCCONFERENCE();
            }
        });
        btn_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_PCCONFERENCE();
            }
        });
        tv_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_PCCONFERENCE();
            }
        });
        img_btn_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_PCCONFERENCE();
            }
        });
    }

    private void dccFollowup() {
        practiceCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {

                                Intent i = new Intent(SalesManagerDashboard.this, SalesManagerDCCFollowup.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("asm_code", userName);
                                i.putExtra("asm_flag", "Y");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");

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

        img_btn_dcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();

                                Intent i = new Intent(SalesManagerDashboard.this, SalesManagerDCCFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("asm_code", userName);
                                i.putExtra("asm_code", userName);
                                i.putExtra("asm_flag", "Y");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");

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

    private void pmdContact() {
        cardview_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(SalesManagerDashboard.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", userName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        img_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(SalesManagerDashboard.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", userName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        btn_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(SalesManagerDashboard.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", userName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        tv_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(SalesManagerDashboard.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", userName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
    }
    private void salesreportDashboard() {
        sales_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Toast.makeText(getApplicationContext(),
                                        "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                // Intent i = new Intent(Dashboard.this, Dashboard.class);
                                Intent i = new Intent(SalesManagerDashboard.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("asm_code", userName);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
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

        bar_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();

                                Toast.makeText(getApplicationContext(),
                                        "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("asm_code", userName);
                                i.putExtra("asm_code", userName);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");

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

    private void adminSalesReport() {
        managers_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = getIntent().getExtras();
                        String userName = b.getString("UserName");
                        String userName_1 = b.getString("UserName_1");
                        String userName_2 = b.getString("UserName_2");

                        try {
                            if (!com.opl.pharmavector.util.NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, SalesManagerDashboard.class);

                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", "N");
                                i.putExtra("message_3", "SM");

                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, AdminReportDashboard.class);
                                String sm_flag = "N";
                                String asm_flag = "N";
                                String gm_flag = "N";

                                i.putExtra("UserName", userName);
                                i.putExtra("userName_1", userName_1);
                                i.putExtra("userName_2", userName_2);
                                i.putExtra("asm_flag", asm_flag);
                                i.putExtra("sm_flag", "Y");
                                i.putExtra("gm_flag", gm_flag);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("am_code", userName);

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

        bar_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!com.opl.pharmavector.util.NetInfo.isOnline(getBaseContext())) {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, AssistantManagerDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                String sm_flag = "Y";
                                String asm_flag = "N";
                                String gm_flag = "N";

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, AdminReportDashboard.class);
                                i.putExtra("sm_flag", sm_flag);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("UserName", userName);
                                i.putExtra("asm_flag", asm_flag);
                                i.putExtra("sm_flag", sm_flag);
                                i.putExtra("gm_flag", gm_flag);
                                i.putExtra("message_3", "SM");

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

    private void prescriptionEntry() {
        prescription_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                // showSnack();
                            } else {
                                // Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
                                Intent i = new Intent(SalesManagerDashboard.this, PrescriptionDashboard.class);
                                i.putExtra("user_flag", "SM");
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

        bar_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                // showSnack();
                            } else {

                                Intent i = new Intent(SalesManagerDashboard.this, PrescriptionDashboard.class);
                                i.putExtra("user_flag", "SM");
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

    private void pcConference() {
        pc_conference_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, AssistantManagerDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "SM");

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

        bar_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, SalesManagerDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "SM");
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

    private  void VacantPassword(){
        btn_vacant_rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Toast.makeText(getApplicationContext(),
                                        "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                // Intent i = new Intent(Dashboard.this, Dashboard.class);
                                Intent i = new Intent(SalesManagerDashboard.this, VacantMpoPwd.class);
                                i.putExtra("user_flag", "SM");
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

        bar_vacant_rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();

                                Toast.makeText(getApplicationContext(),
                                        "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(SalesManagerDashboard.this, VacantMpoPwd.class);
                                i.putExtra("user_flag", "SM");
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

    private void logoutUser() {
       // session.setLogin(false);
        // session.removeAttribute();
      //  session.invalidate();
        Intent intent = new Intent(SalesManagerDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    public void onBackPressed() {
        // Toast.makeText(this, "Press the back button to close the application.", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
        //	super.onBackPressed();
    }

    @Override
    public void onClick(View v) {}

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(getApplicationContext());
        preferenceManager.setTasbihCounter(count);
        preferenceManager.setusername(userName);
        preferenceManager.setpassword(password);
        preferenceManager.setuserrole(message_3);
        preferenceManager.setuserdtl(UserName_2);
        preferenceManager.setfftype(ff_type);
        preferenceManager.setcurrentVersion(vector_version);
        preferenceManager.setexecutive_name(globalempName);
        preferenceManager.setemp_code(globalempCode);
        preferenceManager.setAdmin_Code(global_admin_Code);
        Log.e("onResume----->",global_admin_Code);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        //updateLocation();
        super.onPause();
        preferenceManager.setTasbihCounter(count);
        preferenceManager.setusername(userName);
        preferenceManager.setpassword(password);
        preferenceManager.setuserrole(message_3);
        preferenceManager.setuserdtl(UserName_2);
        preferenceManager.setfftype(ff_type);
        preferenceManager.setcurrentVersion(vector_version);
        preferenceManager.setexecutive_name(globalempName);
        preferenceManager.setemp_code(globalempCode);
        preferenceManager.setAdmin_Code(global_admin_Code);
        Log.e("onPause----->",global_admin_Code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferenceManager.setTasbihCounter(count);
        preferenceManager.setusername(userName);
        preferenceManager.setpassword(password);
        preferenceManager.setuserrole(message_3);
        preferenceManager.setuserdtl(UserName_2);
        preferenceManager.setfftype(ff_type);
        preferenceManager.setcurrentVersion(vector_version);
        preferenceManager.setexecutive_name(globalempName);
        preferenceManager.setemp_code(globalempCode);
        preferenceManager.setAdmin_Code(global_admin_Code);
        Log.e("onDestroy----->",global_admin_Code);
        //updateLocation();
    }

    private void lock_emp_check(String emp_code) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Check locked user...");
        //progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.lock_emp_check(emp_code);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(@NonNull Call<Patient> call, @NonNull Response<Patient> response) {
                //progressDialog.dismiss();
                assert response.body() != null;
                String status      = response.body().getTerritory_name();
                Log.e("Check locked user-->",status);

                if (status.equals("Y")){
                    Toast.makeText(SalesManagerDashboard.this, "You are locked...", Toast.LENGTH_LONG).show();
                    preferenceManager.clearPreferences();
                    count = 0;
                    Intent logoutIntent = new Intent(SalesManagerDashboard.this, Login.class);
                    startActivity(logoutIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Patient> call, @NonNull Throwable t) {
                //progressDialog.dismiss();
            }
        });
    }
}


