package com.opl.pharmavector;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.opl.pharmavector.app.Config;
import com.opl.pharmavector.doctorservice.DoctorServiceAck;
import com.opl.pharmavector.doctorservice.DoctorServiceDashboard;
import com.opl.pharmavector.doctorservice.DoctorServiceFollowup;
import com.opl.pharmavector.doctorservice.DoctorServiceTrackMonthly;
import com.opl.pharmavector.doctorservice.ManagerDoctorServiceFollowup;
import com.opl.pharmavector.geolocation.DoctorChamberLocate;
import com.opl.pharmavector.master_code.MasterCode;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.msd_doc_support.DocSupportDashboard;
import com.opl.pharmavector.msd_doc_support.DocSupportFollowup;
import com.opl.pharmavector.msd_doc_support.DocSupportReq;
import com.opl.pharmavector.msd_doc_support.MSDProgramFollowup;
import com.opl.pharmavector.pcconference.PcApproval;
import com.opl.pharmavector.pcconference.PcConferenceFollowup;
import com.opl.pharmavector.pcconference.PcProposal;
import com.opl.pharmavector.pmdVector.DashBoardPMD;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionDashboard;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionEntry;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup2;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.ImageLoadActivity;
import com.opl.pharmavector.promomat.model.Promo;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.NetInfo;
import com.opl.pharmavector.util.NotificationUtils;
import com.opl.pharmavector.util.PreferenceManager;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;

import java.text.MessageFormat;
import java.util.List;

import android.app.ProgressDialog;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

public class GMDashboard1 extends Activity implements View.OnClickListener {
    public String userName_1, userName, userName_2, UserName_2, user, sm_code, gm_code,global_admin_Code,global_admin_name,global_admin_terri;
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
    public TextView user_show1, user_show2, sm;
    private SessionManager session;
    private final String submit_url = BASE_URL+"notification/save_vector_notification_token_data_test.php";
    public String message;
    public int success;
    public String tokenid;
    public static String IMEINumber = "0000", DeviceID = "XXXX";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private static final int REQUEST_CODE = 101;
    public static String vectorToken, globalAdmin, globalAdminDtl;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    PreferenceManager preferenceManager;
    private int count;
    public static String ff_type,password,globalempCode,globalempName,new_version, message_3,vector_version;
    Typeface fontFamily;
    CardView cardview_dcr,practiceCard2,practiceCard3,practiceCard6,
            practiceCard7,practiceCard8,cardview_pc,cardview_salereports,cardview_msd,cardview_salesfollowup,cardview_mastercode;
    ImageButton profileB, img_btn_dcr,img_btn_dcc,img_btn_productorder,img_btn_docservice,
            img_btn_notification,img_btn_rx,img_btn_pc,img_btn_salereports,img_btn_msd,img_btn_salesfollowup,img_btn_mastercode;
    TextView tv_dcr,tv_productorder,tv_dcc,tv_docservice,
            tv_notification,tv_rx,tv_pc,tv_salereports,tv_msd,tv_salesfollowup,tv_mastercode;
    Button btn_dcr,btn_productorder,btn_dcc,btn_docservice,btn_notification,btn_rx,btn_pc,btn_salereports,btn_msd,btn_salesfollowup,btn_vector_feedback,
            btn_mastercode;
    public TextView t4,t5;
    public ImageView imageView2,logo_team;
    public static String team_logo,profile_image;
    public String base_url =  ApiClient.BASE_URL+"vector_ff_image/";
    private String log_status ="A";

    @SuppressLint("CutPasteId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_gm_dashboard);

        preferenceManager = new PreferenceManager(this);
        count = preferenceManager.getTasbihCounter();
        initViews();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(GMDashboard1.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                vectorToken = instanceIdResult.getToken();
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

                    }
                });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(com.opl.pharmavector.app.Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(com.opl.pharmavector.app.Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                }
            }
        };


        dcrfollowup();
        docservicefollowup();
        msdDocSupport();
        masterCode();
        noticeBoradEvent();
        salesFollowup();
        managersreport();
        pcConferenceEvent();
        prescriptionentry();

        logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GMDashboard1.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("Exit !").setMessage("Are you sure you want to exit Vector?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        log_status = "N";
                                        preferenceManager.clearPreferences();
                                        count = 0;
                                        Intent logoutIntent = new Intent(GMDashboard1.this, Login.class);
                                        startActivity(logoutIntent);
                                        finish();

                                    }
                                });
                                server.start();
                                //logoutUser();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();


            }
        });
        btn_vector_feedback.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                FeedbackshowSnack();
            }
        });

        autoLogout();

    }

    private void autoLogout() {

        if (preferenceManager.getexecutive_name().equals("PMD") || preferenceManager.getexecutive_name().equals("Vector") || preferenceManager.getexecutive_name().equals(" ")){
            log_status = "N";
            preferenceManager.clearPreferences();
            count = 0;
            Intent logoutIntent = new Intent(GMDashboard1.this, Login.class);
            startActivity(logoutIntent);
            finish();
        }

    }

    private void initViews(){
        logout= findViewById(R.id.logout);
        user_show1 = findViewById(R.id.user_show1);
        t4= findViewById(R.id.t4);
        t5= findViewById(R.id.t5);
        imageView2 = findViewById(R.id.imageView2);
        logo_team =findViewById(R.id.logo_team);
        btn_vector_feedback =findViewById(R.id.btn_vector_feedback);


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




        btn_notification       = findViewById(R.id.btn_notification);
        img_btn_notification   = findViewById(R.id.img_btn_notification);
        tv_notification        = findViewById(R.id.tv_notification);
        practiceCard6             = findViewById(R.id.practiceCard6);

        btn_rx       = findViewById(R.id.btn_rx);
        img_btn_rx   = findViewById(R.id.img_btn_rx);
        tv_rx        = findViewById(R.id.tv_rx);
        practiceCard8 = findViewById(R.id.practiceCard8);



        btn_pc       = findViewById(R.id.btn_pc);
        img_btn_pc   = findViewById(R.id.img_btn_pc);
        tv_pc        = findViewById(R.id.tv_pc);
        cardview_pc  = findViewById(R.id.cardview_pc);

        btn_salesfollowup      = findViewById(R.id.btn_salesfollowup);
        img_btn_salesfollowup  = findViewById(R.id.img_btn_salesfollowup);
        tv_salesfollowup       = findViewById(R.id.tv_salesfollowup);
        cardview_salesfollowup = findViewById(R.id.cardview_salesfollowup);


        btn_salereports      = findViewById(R.id.btn_salereports);
        img_btn_salereports  = findViewById(R.id.img_btn_salereports);
        tv_salereports       = findViewById(R.id.tv_salereports);
        cardview_salereports = findViewById(R.id.cardview_salereports);


        btn_msd = findViewById(R.id.btn_msd);
        img_btn_msd  = findViewById(R.id.img_btn_msd);
        tv_msd       = findViewById(R.id.tv_msd);
        cardview_msd  = findViewById(R.id.cardview_msd);

        btn_mastercode = findViewById(R.id.btn_mastercode);
        img_btn_mastercode  = findViewById(R.id.img_btn_mastercode);
        tv_mastercode       = findViewById(R.id.tv_mastercode);
        cardview_mastercode  = findViewById(R.id.cardview_mastercode);


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
        vector_version = String.valueOf(R.string.vector_version);
        globalempCode  =b.getString("emp_code");
        globalempName =b.getString("emp_name");
        global_admin_Code=preferenceManager.getAdmin_Code();
        global_admin_name=preferenceManager.getAdmin_Name();
        global_admin_terri=preferenceManager.getAdmin_Terri();

        Log.e("UserCode------>",global_admin_Code+"-----------"+global_admin_name+"-----------"+global_admin_terri);

        //user_show1.setText(globalempName);
        user_show1.setText(global_admin_name);
        profile_image= base_url+global_admin_Code+"."+"jpg" ;

        Picasso.get()
                .load(profile_image)
                .into(imageView2);

        db = new DatabaseHandler(this);
        ArrayList<String> mpo_code_interna = db.getterritoryname();
        String mpo_code_i = mpo_code_interna.toString();
        globalAdmin = userName;
        globalAdminDtl = UserName_2;

        t4.setText(global_admin_Code);
        t5.setText(global_admin_terri);

        lock_emp_check(global_admin_Code);

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
                        Bundle b = getIntent().getExtras();
                        String userName = b.getString("UserName");
                        String userName_1 = b.getString("UserName_1");
                        String userName_2 = b.getString("UserName_2");
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                               showSnack();
                            } else {
                                Intent i = new Intent(GMDashboard1.this, GMDashboard.class);
                                String gm_flag = "Y";
                                i.putExtra("sm_code", globalAdmin);
                                i.putExtra("UserName", userName);
                                i.putExtra("userName_1", userName_1);
                                i.putExtra("userName_2", userName_2);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("gm_flag", gm_flag);
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

        cardview3.setVisibility(View.GONE);
        cardview4.setVisibility(View.GONE);

        TextView textView4 = bottomSheetDialog2.findViewById(R.id.textView4);


        Objects.requireNonNull(textView4).setText("Tracking\nDoctor");


        ImageView imageView3 = bottomSheetDialog2.findViewById(R.id.imageView3);
        imageView3.setBackgroundResource(R.drawable.ic_doctor_service);

        TextView changepassword = bottomSheetDialog2.findViewById(R.id.changepassword);
        Objects.requireNonNull(changepassword).setText("Doctor Service");

        Objects.requireNonNull(cardview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(GMDashboard1.this, ManagerDoctorServiceFollowup.class);
                i.putExtra("userName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("new_version", new_version);
                i.putExtra("user_flag", "G");
                startActivity(i);
                //  bottomSheetDialog2.dismiss();
            }
        });

        Objects.requireNonNull(cardview1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(GMDashboard1.this, DoctorServiceTrackMonthly.class);
                i.putExtra("userName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("new_version", new_version);
                i.putExtra("user_flag", "G");
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
                Intent i = new Intent(GMDashboard1.this, MSDProgramFollowup.class);
                i.putExtra("user_code", GMDashboard1.globalAdmin);
                i.putExtra("user_name", GMDashboard1.globalAdminDtl);
                i.putExtra("user_flag", "GM");
                startActivity(i);

            }
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, DocSupportFollowup.class);
                i.putExtra("user_code", GMDashboard1.globalAdmin);
                i.putExtra("user_name", GMDashboard1.globalAdminDtl);
                i.putExtra("user_flag", "GM");
                startActivity(i);
                // bottomSheetDialog.dismiss();
            }
        });

        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, MSDProgramFollowup.class);
                i.putExtra("user_code", GMDashboard1.globalAdmin);
                i.putExtra("user_name", GMDashboard1.globalAdminDtl);
                i.putExtra("user_flag", "GM");
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


    private void masterCode() {

        cardview_mastercode.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(GMDashboard1.this, MasterCode.class);
                                i.putExtra("user_flag", "AD");
                                i.putExtra("admin_code--->", global_admin_Code);
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

        btn_mastercode.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(GMDashboard1.this, MasterCode.class);
                                i.putExtra("user_flag", "AD");
                                i.putExtra("admin_code", global_admin_Code);
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

        img_btn_mastercode.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(GMDashboard1.this, MasterCode.class);
                                i.putExtra("user_flag", "AD");
                                i.putExtra("admin_code", global_admin_Code);
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

        tv_mastercode.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(GMDashboard1.this, MasterCode.class);
                                i.putExtra("user_flag", "AD");
                                i.putExtra("admin_code", global_admin_Code);
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Toast.makeText(getApplicationContext(),
                                        "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(GMDashboard1.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
                                i.putExtra("asm_code", globalAdmin);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "N");
                                i.putExtra("gm_flag", "Y");
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
                                Intent i = new Intent(GMDashboard1.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("asm_code", userName);
                                i.putExtra("asm_code", userName);
                                i.putExtra("query_code", userName);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "N");
                                i.putExtra("gm_flag", "Y");
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Toast.makeText(getApplicationContext(),
                                        "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(GMDashboard1.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
                                i.putExtra("asm_code", globalAdmin);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "N");
                                i.putExtra("gm_flag", "Y");
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
                                Intent i = new Intent(GMDashboard1.this, ManagersSalesFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("asm_code", userName);
                                i.putExtra("asm_code", userName);
                                i.putExtra("query_code", userName);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "N");
                                i.putExtra("gm_flag", "Y");
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

    private void noticeBoradEvent() {
        practiceCard6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(GMDashboard1.this, NoticeBoard.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(GMDashboard1.this, NoticeBoard.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(GMDashboard1.this, NoticeBoard.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(GMDashboard1.this, NoticeBoard.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
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

    private void managersreport() {
        cardview_salereports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                             showSnack();
                            } else {
                                Intent i = new Intent(GMDashboard1.this, AdminReportDashboard.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
                                i.putExtra("asm_code", globalAdmin);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "N");
                                i.putExtra("gm_flag", "Y");
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(GMDashboard1.this, AdminReportDashboard.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
                                i.putExtra("asm_code", globalAdmin);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "N");
                                i.putExtra("gm_flag", "Y");
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(GMDashboard1.this, AdminReportDashboard.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
                                i.putExtra("asm_code", globalAdmin);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "N");
                                i.putExtra("gm_flag", "Y");
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(GMDashboard1.this, AdminReportDashboard.class);
                                i.putExtra("UserName", globalAdmin);
                                i.putExtra("UserName_2", globalAdminDtl);
                                i.putExtra("asm_code", globalAdmin);
                                i.putExtra("asm_flag", "N");
                                i.putExtra("sm_flag", "N");
                                i.putExtra("gm_flag", "Y");
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
                Intent i = new Intent(GMDashboard1.this, PcApproval.class);
                i.putExtra("userName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("new_version", R.string.vector_version);
                i.putExtra("UserName", globalAdmin);
                Log.e("globalAdmin==>",globalAdmin);
                startActivity(i);
            }
        });

        Objects.requireNonNull(cardview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, PcConferenceFollowup.class);
                i.putExtra("UserName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("user_flag", "GM");
                startActivity(i);
            }
        });

        Objects.requireNonNull(cardview3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, PcBillApproval.class);
                i.putExtra("userName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("new_version", new_version);
                i.putExtra("userName", globalAdmin);
                startActivity(i);
            }
        });

        Objects.requireNonNull(cardview4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, PCBillFollowup.class);
                i.putExtra("UserName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("user_flag", "GM");
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
        cardview4.setVisibility(View.GONE);
        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog2.dismiss();
            }
        });

        Objects.requireNonNull(cardview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, ImageLoadActivity.class);
                i.putExtra("manager_code",GMDashboard1.globalAdmin);
                i.putExtra("manager_detail",GMDashboard1.globalAdminDtl);
                i.putExtra("manager_flag","AD");
                startActivity(i);
            }
        });

        Objects.requireNonNull(cardview3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, PrescriptionFollowup.class);
                i.putExtra("manager_code",GMDashboard1.globalAdmin);
                i.putExtra("manager_detail", "AD");
                startActivity(i);
                // bottomSheetDialog2.dismiss();
            }
        });

        Objects.requireNonNull(cardview4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, PrescriptionFollowup2.class);
                i.putExtra("manager_code",GMDashboard1.globalAdmin);
                i.putExtra("manager_detail", "AD");
                startActivity(i);
            }
        });
        bottomSheetDialog2.show();
    }

    private void prescriptionentry(){
        practiceCard8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_RXCAPTURE();
               // startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void logoutUser() {
        session.setLogin(false);
        session.invalidate();
        Intent intent = new Intent(GMDashboard1.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    private void showSnack() {
        new Thread() {
            public void run() {
                GMDashboard1.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "No internet Connection, Please Check Your Connection";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    private void FeedbackshowSnack() {
        new Thread() {
            public void run() {
                GMDashboard1.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "Admins are not allowed for this feature!";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

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
        preferenceManager.setAdmin_Name(global_admin_name);
        preferenceManager.setAdmin_Terri(global_admin_terri);
        Log.e("onResume------->",global_admin_Code);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
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
        preferenceManager.setAdmin_Name(global_admin_name);
        preferenceManager.setAdmin_Terri(global_admin_terri);
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
        preferenceManager.setAdmin_Name(global_admin_name);
        preferenceManager.setAdmin_Terri(global_admin_terri);
        Log.e("Destroy----->",userName);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {

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
                    Toast.makeText(GMDashboard1.this, "You are locked...", Toast.LENGTH_LONG).show();
                    log_status = "N";
                    preferenceManager.clearPreferences();
                    count = 0;
                    Intent logoutIntent = new Intent(GMDashboard1.this, Login.class);
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


