package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.opl.pharmavector.achieve.AchieveEarnActivity;
import com.opl.pharmavector.app.Config;
import com.opl.pharmavector.chemistList.ChemistListActivity;
import com.opl.pharmavector.contact.Activity_PMD_Contact;
import com.opl.pharmavector.dcfpFollowup.DcfpDoctorListActivity;
import com.opl.pharmavector.dcfpFollowup.DcfpFollowupActivity;
import com.opl.pharmavector.dcfpFollowup.DoctorReachActivity;
import com.opl.pharmavector.doctorList.DoctorListActivity;
import com.opl.pharmavector.doctorservice.DoctorServiceTrackMonthly;
import com.opl.pharmavector.doctorservice.ManagerDoctorServiceFollowup;
import com.opl.pharmavector.exam.ExamResultFollowup;
import com.opl.pharmavector.giftfeedback.FieldFeedBack;
import com.opl.pharmavector.liveDepot.ADSStockPMDActivity;
import com.opl.pharmavector.liveDepot.LiveDepotStockActivity;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mpodcr.entry.DcrEntryActivity;
import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.msd_doc_support.DocSupportFollowup;
import com.opl.pharmavector.msd_doc_support.MSDCommitmentFollowup;
import com.opl.pharmavector.msd_doc_support.MSDProgramFollowup;
import com.opl.pharmavector.pcconference.PcApproval;
import com.opl.pharmavector.pcconference.PcConferenceFollowup;
import com.opl.pharmavector.pcconference.RMPCPermission;
import com.opl.pharmavector.pmdVector.ff_contact.ff_contact_activity;
import com.opl.pharmavector.prescriber.TopPrescriberActivity;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup;
import com.opl.pharmavector.prescriptionsurvey.RMRxSumMISActivity;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.ImageLoadActivity;
import com.opl.pharmavector.promomat.PromoMaterialFollowup;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.service.MyLocationService;
import com.opl.pharmavector.tourPlan.TourPlanActivity;
import com.opl.pharmavector.util.NetInfo;
import com.opl.pharmavector.util.NotificationUtils;
import com.opl.pharmavector.util.PreferenceManager;
import com.opl.pharmavector.util.VectorUtils;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.List;

import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RmDashboard extends Activity implements View.OnClickListener {
    public String userName_1, userName, designation, terriName, userName_2, UserName_2, mpo_code_i, global_admin_Code;
    public AutoCompleteTextView actv;
    private DatabaseHandler db;
    private String TAG = Offlinereport.class.getSimpleName();
    private Button logout;
    private String log_status = "A";
    public TextView user_show1, user_show2, versionname;
    private SessionManager session;
    private String submit_url = BASE_URL + "notification/save_vector_notification_token_data_test.php";
    public String message, currentVersion;
    public int success;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static String vectorToken, globalRegionalCode, globalRMCode, ff_type;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ArrayList<String> mpo_code_interna;
    private int count;
    private ArrayList<Customer> checkdatelist;
    PreferenceManager preferenceManager;
    public static String password, globalempCode, globalempName, new_version, message_3, vector_version, build_model, build_brand, build_id, build_device, build_version, os_version;
    CardView cardview_dcr, practiceCard2, practiceCard3, practiceCard4, practiceCard5, practiceCard6, cardview_pmd_contact, cardView_prescriber, cardview_achv_earn,
            practiceCard7, practiceCard8, practiceCard9, cardview_pc, cardview_promomat, cardview_salereports, cardview_msd, cardview_doctor_list, cardview_ff_contact,
            cardView_productStock, cardView_tourPlan, cardView_chemist_list;
    ImageButton profileB, img_btn_dcr, img_btn_dcc, img_btn_productorder, img_btn_docservice, img_btn_docgiftfeedback,
            img_btn_notification, img_btn_rx, img_btn_personalexpense, img_btn_pc, img_btn_promomat, img_btn_salereports, img_btn_msd, img_btn_exam, img_pmd_contact, img_doctor_list;
    TextView tv_dcr, tv_productorder, tv_dcc, tv_docservice, tv_docgiftfeedback,
            tv_notification, tv_rx, tv_personalexpense, tv_pc, tv_promomat, tv_salereports, tv_msd, tv_exam, tv_pmd_contact, tv_doctor_list;
    Button btn_dcr, btn_productorder, btn_dcc, btn_docservice,
            btn_docgiftfeedback, btn_notification, btn_rx, btn_personalexpense, btn_pc, btn_promomat, btn_salereports, btn_msd, btn_exam, btn_vector_feedback, btn_pmd_contact, btn_doctor_list;
    LocationManager locationManager;
    BroadcastReceiver updateUIReciver;
    double fetchedlang, fetchedlat;
    LocationRequest locationRequest;
    Boolean isAddressSubmit;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static String track_lat, track_lang, track_add = "No Address";
    public TextView t4, t5, tvDesignation, tvLocationName;
    public ImageView imageView2, logo_team;
    public static String team_logo, profile_image;
    public String base_url = ApiClient.BASE_URL + "vector_ff_image/";
    public String get_ext_dt, date_flag, check_flag;

    @SuppressLint("CutPasteId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.rmdashboard);
        setContentView(R.layout.activity_vector_rm_dashboard);

        isUpdateAvailable();
        VectorUtils.screenShotProtect(this);
        isAddressSubmit = true;
        preferenceManager = new PreferenceManager(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        statusBarHide();
        initViews();
        initBroadcastReceiver();
        registerReceiver(updateUIReciver, new IntentFilter(MyLocationService.ACTION_PROCESS_UPDATE));
        count = preferenceManager.getTasbihCounter();
        checkdatelist = new ArrayList<Customer>();
        global_admin_Code = preferenceManager.getAdmin_Code();
        Log.e("Admin Code--->", preferenceManager.getAdmin_Code());
        //autoLogout();

        getDeviceDetails();
        updateLocation();
        dcrClickEvent();
        amMonitor();
        personalExpenseEvent();
        salesReportEvent();
        msdDocSupport();
        vectorFeedback();
        promoMaterialFollowupEvent();
        docservicefollowup();
        noticeBoradEvent();
        prescriptionEvent();
        pcConferenceEvent();
        mpoDCREvent();
        mrcExamEvent();
        TeamLogo();
        vacantMpoPwd();
        pmdContact();
        doctorListInfo();
        achieveEarnEvent();
        productStockEvent();
        topPrescriberEvent();
        monthlyTourPlanEvent();
        chemistListEvent();
        //pendingPC();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(RmDashboard.this, instanceIdResult -> vectorToken = instanceIdResult.getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("vector")
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed) + vectorToken;
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d(TAG, msg);
                });
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //checking for type intent filter
                if (intent.getAction().equals(com.opl.pharmavector.app.Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(com.opl.pharmavector.app.Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                }
            }
        };
        cardview_ff_contact.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
                try {
                    if (!NetInfo.isOnline(getBaseContext())) {

                    } else {
                        Intent i = new Intent(RmDashboard.this, ff_contact_activity.class);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            backthred.start();
        });
        /*
        prescription_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                // Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
                                Intent i = new Intent(RmDashboard.this, PrescriptionDashboard.class);
                                i.putExtra("user_flag", "RM");
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
                                showSnack();
                            } else {

                                Intent i = new Intent(RmDashboard.this, PrescriptionDashboard.class);
                                i.putExtra("user_flag", "RM");
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
        doc_gift_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, DocGiftDashBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "RM");
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, DocGiftDashBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "RM");
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
        bar_G1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, DocGiftDashBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "RM");
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, DocGiftDashBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "RM");
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
        mrc_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //new FetchExamFlag().execute();
                ArrayList<String> UserName_2 = db.getterritoryname();
                String user = UserName_2.toString();
                Intent i = new Intent(RmDashboard.this, ExamDashboard.class);
                i.putExtra("mpo_code", userName);
                i.putExtra("territory_name", user);
                i.putExtra("user_flag", new_version);
                i.putExtra("message_3", "am");
                i.putExtra("myexamid", "1");
                i.putExtra("myexamtime", "0");
                i.putExtra("user_flag", "R");
                startActivity(i);
            }
        });
        bar_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ArrayList<String> UserName_2 = db.getterritoryname();
                String user = UserName_2.toString();
                Intent i = new Intent(RmDashboard.this, ExamDashboard.class);
                i.putExtra("mpo_code", userName);
                i.putExtra("territory_name", user);
                i.putExtra("user_flag", new_version);
                i.putExtra("message_3", "am");
                i.putExtra("myexamid", "1");
                i.putExtra("myexamtime", "0");
                i.putExtra("user_flag", "R");
                startActivity(i);
            }
        });
        promo_material_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(RmDashboard.this, Dashboard.class);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, PromomaterialDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "RM");
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
        bar_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(RmDashboard.this, Dashboard.class);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, PromomaterialDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "RM");
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
        doctor_service_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, AmDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, DoctorServiceDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "R");
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("user_flag", "R");
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
        bar_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, Dashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, DoctorServiceDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("user_flag", "R");
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
                                Intent i = new Intent(RmDashboard.this, Dashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "R");
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
                                Intent i = new Intent(RmDashboard.this, Dashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "R");
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
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, AmDashboard.class);
                                Toast.makeText(getBaseContext(), "No internet Connection", Toast.LENGTH_LONG).show();
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", user);
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
        bar_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                Toast.makeText(getBaseContext(), "No internet Connection", Toast.LENGTH_LONG).show();
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, NoticeBoard.class);
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
        dcc_camp_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, AmDashboard.class);
                                //Intent i = new Intent(Dashboard.this, DcrReport.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //Intent i = new Intent(Dashboard.this, MPODailyReport.class);
                                Intent i = new Intent(RmDashboard.this, RmDCCFollowup.class);
                                //Intent i = new Intent(Dashboard.this, DcrReport.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("rm_code", userName);
                                i.putExtra("rm_flag", "Y");
                                i.putExtra("asm_flag", "N");
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
        bar_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, AmDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("PassedAmReportDashboard", userName + "---" + UserName_2);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //AmReportDashboard
                                //Intent i = new Intent(Dashboard.this, MPODailyReport.class);
                                Intent i = new Intent(RmDashboard.this, RmDCCFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("rm_flag", "Y");
                                i.putExtra("rm_code", userName);
                                i.putExtra("asm_flag", "N");
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
        offline_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, Offlinereadcomments.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, Offlinereadcomments.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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
        bar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, Offlinereadcomments.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                startActivity(i);
                            } else {
                                //Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME);
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, Offlinereadcomments.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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
        p_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //    Intent i = new Intent(Dashboard.this, PersonalExpenses.class);
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //Intent i = new Intent(Dashboard.this, PersonalExpenses.class);
                                Intent i = new Intent(RmDashboard.this, RMDcrReport.class);
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
        bar_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RMDcrReport.class);
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
        personal_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                //Intent i = new Intent(Dashboard.this, DcrReport.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RMPersonalExpenses.class);
                                //Intent i = new Intent(Dashboard.this, DcrReport.class);
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
        bar_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RMPersonalExpenses.class);
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
        bar_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //Intent i = new Intent(Dashboard.this, MPODailyReport.class);
                                Intent i = new Intent(RmDashboard.this, ReportDashboard.class);
                                //Intent i = new Intent(RmDashboard.this, com.opl.rmsalematrix.ReportDashboard.class);
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
        personal_expenses_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                //Intent i = new Intent(Dashboard.this, DcrReport.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RMReportPersonalExpenses.class);
                                //Intent i = new Intent(Dashboard.this, DcrReport.class);
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
        bar_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RMReportPersonalExpenses.class);
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
        bar_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, MpoDcrMonitorDaily.class);
                                //Intent i = new Intent(RmDashboard.this, MpoDcrMonitorDaily.class);
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
        rx_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                //Intent i = new Intent(Dashboard.this, DcrReport.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                //Intent i = new Intent(RmDashboard.this,RX.class);
                                //Intent i = new Intent(Dashboard.this, DcrReport.class);
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

        bar_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDashboard.class);
                                //Intent i = new Intent(RmDashboard.this, RX.class);
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

        rx_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //Intent i = new Intent(Dashboard.this, Dashboard.class);
                                Toast.makeText(getApplicationContext(),
                                        "RX report will be availabe within next month", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Toast.makeText(getApplicationContext(),
                                        "RX report will be availabe within next month", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
        bar_10.setOnClickListener(new View.OnClickListener() {
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
                                        "RX report will be availabe within next month", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Toast.makeText(getApplicationContext(),
                                        "RX report will be availabe within next month", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
        gift_camp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //Intent i = new Intent(Dashboard.this, Dashboard.class);
                                Toast.makeText(getApplicationContext(),
                                        "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //Intent i = new Intent(Dashboard.this, Dashboard.class);
                                Intent i = new Intent(RmDashboard.this, ChemistGiftCampaign.class);
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
        bar_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //Intent i = new Intent(Dashboard.this, Dashboard.class);
                                //i.putExtra("UserName", userName);
                                //i.putExtra("UserName_2", user);
                                //Log.w("Passed personalexpenses", userName + "---" + UserName_2);
                                //startActivity(i);
                                Toast.makeText(getApplicationContext(),
                                        "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, ChemistGiftCampaign.class);
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
        dcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDcr.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmDcr.class);
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
        sales_report.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(RmDashboard.this, RmSalesReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmSalesReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("userName_1", userName_1);
                                i.putExtra("userName_2", userName_2);
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
        bar_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!com.opl.pharmavector.util.NetInfo.isOnline(getBaseContext())) {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmSalesReportDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(RmDashboard.this, RmSalesReportDashboard.class);
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
        */
        session = new SessionManager(getApplicationContext());
        PackageManager pm = getApplicationContext().getPackageManager();
        String pkgName = getApplicationContext().getPackageName();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        vector_version = pkgInfo.versionName;

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RmDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("Exit !").setMessage("Are you sure you want to exit Vector?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        preferenceManager.clearPreferences();
                                        count = 0;
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
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED /*||
                checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RmDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("App Require Location").setMessage("This app collects location data to enable Doctor Chamber Location Feature even when app is running")
                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                            dexterPermission(RmDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                                        } else {
                                            dexterPermission(RmDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                                        }
                                    }
                                });
                                server.start();
                            }
                        })
                        .setNegativeButton("Quit App", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preferenceManager.clearPreferences();
                                count = 0;
                                Intent logoutIntent = new Intent(RmDashboard.this, Login.class);
                                startActivity(logoutIntent);
                                finish();

                            }
                        })
                        .show();
            }
        }
        userLogIn(track_add);
    }

    private void updateLocation() {
            Log.d("locationAdd", "updateLocation called!");
            buildLocationRequest();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(200);
        locationRequest.setSmallestDisplacement(10f);
        Log.d("loca-->", locationRequest.toString());
    }

    private PendingIntent getPendingIntent() {
        Log.d("locationAdd", "getPendingIntent called!");
        Intent myIntent = new Intent(this, MyLocationService.class);
        myIntent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
        } else {
            return PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    private void initBroadcastReceiver() {
        updateUIReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("broadcast1", "called!");
                String parselang = intent.getStringExtra("langtitude");
                String parselat = intent.getStringExtra("latitude");
                fetchedlang = Double.parseDouble(parselang);
                fetchedlat = Double.parseDouble(parselat);
                track_lat = parselat;
                track_lang = parselang;
                getAddress(fetchedlat, fetchedlang);
                userLog(log_status);
            }
        };
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(RmDashboard.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            track_add = obj.getAddressLine(0);
            //track_add = track_add + "\n" + obj.getCountryName();
            //track_add = track_add + "\n" + obj.getCountryCode();
            tvLocationName.setText(track_add);
            //userLog(log_status);
            if (isAddressSubmit) {
                //userLogIn(track_add);
                isAddressSubmit = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userLog(final String key) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.userData(key, vector_version, vectorToken, track_lat, track_lang, build_model, build_brand, userName, track_add, globalempCode);
        //Log.d("tokenApi->", vectorToken);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                assert response.body() != null;
                int success = response.body().getSuccess();
                String message = response.body().getMassage();
                Log.d("mpoLocationUpdate->", message + "===>" + vectorToken);
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.d("tokenError", "error called! " + t);
            }
        });
    }

    public void getDeviceDetails() {
        build_version = Build.VERSION.RELEASE;
        build_model = Build.MODEL;
        build_device = Build.DEVICE;
        build_brand = Build.BRAND;
        build_id = Build.ID;
        os_version = String.valueOf(Build.VERSION.RELEASE);
    }

    private void userLogIn(String loc_name) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.userLogIn(globalempCode, userName, vector_version, track_lat, track_lang, build_model, build_brand, userName, track_add, os_version);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                assert response.body() != null;
                int success = response.body().getSuccess();
                String message = response.body().getMassage();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });
    }

    private void dexterPermission(Context context, String... permissions) {
        Dexter.withContext(this)
                .withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!report.areAllPermissionsGranted()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RmDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                            builder.setTitle("App Require Location").setMessage("All permission must be Granted")
                                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Thread server = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dexterPermission(RmDashboard.this);
                                                }
                                            });
                                            server.start();
                                        }
                                    })
                                    .setNegativeButton("Quit App", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            preferenceManager.clearPreferences();
                                            count = 0;
                                            Intent logoutIntent = new Intent(RmDashboard.this, Login.class);
                                            startActivity(logoutIntent);
                                            finish();
                                        }
                                    })
                                    .show();
                        } else {
                            updateLocation();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();
    }

    private void productStockEvent() {
        cardView_productStock.setOnClickListener(v -> showBottomSheetDialog_ProdStock());
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_ProdStock() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.stock_bottom_sheet_dialog);
        CardView cardView_adsStock = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardView_dailyStock = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        CardView cardView_doctorReach = bottomSheetDialog.findViewById(R.id.card_doctorReach);
        Objects.requireNonNull(cardView_doctorReach).setVisibility(View.GONE);
        TextView changePassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        TextView textView6 = bottomSheetDialog.findViewById(R.id.tv_doctorReach);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button button3 = bottomSheetDialog.findViewById(R.id.btn_doctorReach);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("19.1");
        Objects.requireNonNull(button2).setText("19.2");
        Objects.requireNonNull(button3).setText("19.3");
        Objects.requireNonNull(textView4).setText("ADS - PMD");
        Objects.requireNonNull(textView5).setText("Daily\nLive Stock");
        Objects.requireNonNull(textView6).setText("Doctor \nReach");
        Objects.requireNonNull(changePassword).setText("Product Stock");
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();

        Objects.requireNonNull(cardView_adsStock).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, ADSStockPMDActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", globalRMCode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "RM");
            startActivity(i);
        });
        Objects.requireNonNull(cardView_dailyStock).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, LiveDepotStockActivity.class);
            i.putExtra("userName", globalRMCode);
            i.putExtra("userCode", globalempCode);
            i.putExtra("userRole", "RM");
            i.putExtra("asm_flag", "N");
            i.putExtra("sm_flag", "N");
            i.putExtra("gm_flag", "N");
            i.putExtra("rm_flag", "Y");
            i.putExtra("fm_flag", "N");
            i.putExtra("mpo_flag", "N");
            startActivity(i);
        });
        Objects.requireNonNull(cardView_doctorReach).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, DoctorReachActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", globalempCode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "AD");
            i.putExtra("report_flag", "SPI");
            i.putExtra("asm_flag", "N");
            i.putExtra("sm_flag", "N");
            i.putExtra("gm_flag", "Y");
            i.putExtra("rm_flag", "N");
            i.putExtra("fm_flag", "N");
            i.putExtra("mpo_flag", "N");
            startActivity(i);
        });
    }

    private void monthlyTourPlanEvent() {
        cardView_tourPlan.setOnClickListener(v -> showBottomSheetDialog_Tour());
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_Tour() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.tour_bottom_sheet_dialog);
        CardView cardView_tourPlan = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_tourFollow = bottomSheetDialog.findViewById(R.id.cardview_tour_follow);
        //Objects.requireNonNull(cardView_spiReport).setVisibility(View.GONE);
        CardView cardView_doctorReach = bottomSheetDialog.findViewById(R.id.card_doctorReach);
        Objects.requireNonNull(cardView_doctorReach).setVisibility(View.GONE);
        TextView changePassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        //TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        TextView textView6 = bottomSheetDialog.findViewById(R.id.tv_doctorReach);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button8);
        Button button3 = bottomSheetDialog.findViewById(R.id.btn_doctorReach);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("20.1");
        Objects.requireNonNull(button2).setText("20.2");
        Objects.requireNonNull(button3).setText("20.3");
        Objects.requireNonNull(textView4).setText("Tour\nObservation Entry");
        //Objects.requireNonNull(textView5).setText("SPI \nReport");
        Objects.requireNonNull(textView6).setText("Tour \nPlan Entry");
        Objects.requireNonNull(changePassword).setText("Tour Program");
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();

        Objects.requireNonNull(cardView_tourPlan).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, DcrEntryActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", globalempCode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "AD");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_tourFollow).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, DcfpFollowupActivity.class);
            i.putExtra("UserName", globalRMCode);
            i.putExtra("UserName_2", userName_2);
            i.putExtra("UserName_3", globalRMCode);
            i.putExtra("UserRole", "T"); // T -> TOUR
            startActivity(i);
        });
        Objects.requireNonNull(cardView_doctorReach).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, DoctorReachActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", globalempCode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "AD");
            i.putExtra("report_flag", "SPI");
            i.putExtra("asm_flag", "N");
            i.putExtra("sm_flag", "N");
            i.putExtra("gm_flag", "Y");
            i.putExtra("rm_flag", "N");
            i.putExtra("fm_flag", "N");
            i.putExtra("mpo_flag", "N");
            startActivity(i);
        });
    }

    @SuppressLint("SetTextI18n")
    private void topPrescriberEvent() {
        cardView_prescriber.setOnClickListener(pres -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.dcfp_bottom_sheet_dialog);
            CardView cardView_topPrescriber = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
            CardView cardView_spiReport = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
            CardView cardView_doctorReach = bottomSheetDialog.findViewById(R.id.card_doctorReach);
            TextView changePassword = bottomSheetDialog.findViewById(R.id.changepassword);
            TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
            TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
            TextView textView6 = bottomSheetDialog.findViewById(R.id.tv_doctorReach);
            Button button1 = bottomSheetDialog.findViewById(R.id.button1);
            Button button2 = bottomSheetDialog.findViewById(R.id.button2);
            Button button3 = bottomSheetDialog.findViewById(R.id.btn_doctorReach);
            Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
            Objects.requireNonNull(button1).setText("17.1");
            Objects.requireNonNull(button2).setText("17.2");
            Objects.requireNonNull(button3).setText("17.3");
            Objects.requireNonNull(textView4).setText("SPI Top Prescriber\n(Generic)");
            Objects.requireNonNull(textView5).setText("SPI \nReport");
            Objects.requireNonNull(textView6).setText("Doctor \nReach");
            Objects.requireNonNull(changePassword).setText(R.string.spiReport);
            ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
            Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
            Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
            bottomSheetDialog.show();

            Objects.requireNonNull(cardView_topPrescriber).setOnClickListener(v -> {
                Intent i = new Intent(RmDashboard.this, TopPrescriberActivity.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", userName);
                i.putExtra("new_version", new_version);
                i.putExtra("message_3", message_3);
                i.putExtra("UserRole", "RM");
                startActivity(i);
            });
            Objects.requireNonNull(cardView_spiReport).setOnClickListener(v -> {
                Intent i = new Intent(RmDashboard.this, MRDPresReport.class);
                i.putExtra("userName", userName);
                i.putExtra("UserName", userName);
                i.putExtra("report_flag", "SPI");
                i.putExtra("asm_flag", "N");
                i.putExtra("sm_flag", "N");
                i.putExtra("gm_flag", "N");
                i.putExtra("rm_flag", "Y");
                i.putExtra("fm_flag", "N");
                startActivity(i);
            });
            Objects.requireNonNull(cardView_doctorReach).setOnClickListener(v -> {
                Intent i = new Intent(RmDashboard.this, DoctorReachActivity.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", globalempCode);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                i.putExtra("UserRole", "RM");
                i.putExtra("report_flag", "SPI");
                i.putExtra("asm_flag", "N");
                i.putExtra("sm_flag", "N");
                i.putExtra("gm_flag", "N");
                i.putExtra("rm_flag", "Y");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "N");
                startActivity(i);
            });
        });
    }

    private void achieveEarnEvent() {
        cardview_achv_earn.setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, AchieveEarnActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", userName);
            i.putExtra("new_version", new_version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "RM");
            i.putExtra("TeamCode", ff_type);
            startActivity(i);
        });
    }


    private void mrcExamEvent() {
        practiceCard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //new FetchExamFlag().execute();
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(RmDashboard.this, ExamResultFollowup.class);
                                i.putExtra("mpo_code", globalRMCode);
                                i.putExtra("territory_name", globalRegionalCode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
                                i.putExtra("user_flag", "R");
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
        img_btn_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //new FetchExamFlag().execute();
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(RmDashboard.this, ExamResultFollowup.class);
                                i.putExtra("mpo_code", globalRMCode);
                                i.putExtra("territory_name", globalRegionalCode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
                                i.putExtra("user_flag", "R");
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
        tv_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //new FetchExamFlag().execute();
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(RmDashboard.this, ExamResultFollowup.class);
                                i.putExtra("mpo_code", globalRMCode);
                                i.putExtra("territory_name", globalRegionalCode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
                                i.putExtra("user_flag", "R");
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
        btn_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //new FetchExamFlag().execute();
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(RmDashboard.this, ExamResultFollowup.class);
                                i.putExtra("mpo_code", globalRMCode);
                                i.putExtra("territory_name", globalRegionalCode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
                                i.putExtra("user_flag", "R");
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
        //bottomSheetDialog2.setContentView(R.layout.multi_option_bottom_sheet_dialog);
        bottomSheetDialog2.setContentView(R.layout.rm_pc_bottomsheet);
        CardView cardview1 = bottomSheetDialog2.findViewById(R.id.cardview_1);
        CardView cardview2 = bottomSheetDialog2.findViewById(R.id.cardview_2);
        CardView cardview3 = bottomSheetDialog2.findViewById(R.id.cardview_3);
        CardView cardview4 = bottomSheetDialog2.findViewById(R.id.cardview_4);
        CardView cardview5 = bottomSheetDialog2.findViewById(R.id.cardview_5);
        CardView cardview6 = bottomSheetDialog2.findViewById(R.id.cardview_6);

        TextView changepassword = bottomSheetDialog2.findViewById(R.id.changepassword);
        Button btn_1 = bottomSheetDialog2.findViewById(R.id.btn_1);
        ImageView imageView3 = bottomSheetDialog2.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_pc_conference);

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
                Intent i = new Intent(RmDashboard.this, RMPCPermission.class);
                i.putExtra("UserName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                startActivity(i);
            }
        });
        Objects.requireNonNull(cardview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, RMPCPendingPermission.class);
                i.putExtra("UserName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("new_version", new_version);
                //startActivity(i);
            }
        });
        Objects.requireNonNull(cardview3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, PcApproval.class);
                i.putExtra("userName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("new_version", R.string.vector_version);
                i.putExtra("UserName", globalRMCode);
                startActivity(i);
            }
        });
        Objects.requireNonNull(cardview4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, PcConferenceFollowup.class);
                i.putExtra("UserName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("user_flag", "R");
                startActivity(i);
            }
        });
        Objects.requireNonNull(cardview5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, PcBillApproval.class);
                i.putExtra("userName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("new_version", new_version);
                i.putExtra("userName", globalRMCode);
                startActivity(i);
            }
        });
        Objects.requireNonNull(cardview6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, PCBillFollowup.class);
                i.putExtra("UserName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("user_flag", "R");
                startActivity(i);
            }

          /* Intent i = new Intent(RmDashboard.this, PCDashboard.class);
             Intent i = new Intent(PCDashboard.this, RMPCPermission.class);
             i.putExtra("UserName", userName);
             i.putExtra("UserName_2", UserName_2);
             i.putExtra("new_version", new_version);
            */
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

    private void TeamLogo() {
        String team = ff_type;
        team_logo = ApiClient.BASE_URL + "team_logo/";

        switch (team) {
            case "G":
                String logo_image = team_logo + "d" + "." + "png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
            case "T":
                logo_image = team_logo + "t" + "." + "png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
            case "I":
                logo_image = team_logo + "i" + "." + "png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
            case "V":
                logo_image = team_logo + "v" + "." + "png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
            case "C":
                logo_image = team_logo + "g" + "." + "png";
                Picasso.get()
                        .load(logo_image)
                        .into(logo_team);
                break;
        }
    }

    private void statusBarHide() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @SuppressLint("CutPasteId")
    private void initViews() {
        logout = findViewById(R.id.logout);
        user_show1 = findViewById(R.id.user_show1);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        imageView2 = findViewById(R.id.imageView2);
        logo_team = findViewById(R.id.logo_team);
        tvLocationName = findViewById(R.id.location_name);

        btn_productorder = findViewById(R.id.btn_productorder);
        img_btn_productorder = findViewById(R.id.img_btn_productorder);
        tv_productorder = findViewById(R.id.tv_productorder);
        practiceCard7 = findViewById(R.id.practiceCard7);

        btn_dcr = findViewById(R.id.btn_dcr);
        img_btn_dcr = findViewById(R.id.img_btn_dcr);
        tv_dcr = findViewById(R.id.tv_dcr);
        cardview_dcr = findViewById(R.id.cardview_dcr);

        btn_dcc = findViewById(R.id.btn_dcc);
        img_btn_dcc = findViewById(R.id.img_btn_dcc);
        tv_dcc = findViewById(R.id.tv_dcc);
        practiceCard2 = findViewById(R.id.practiceCard2);

        btn_docservice = findViewById(R.id.btn_docservice);
        img_btn_docservice = findViewById(R.id.img_btn_docservice);
        tv_docservice = findViewById(R.id.tv_docservice);
        practiceCard3 = findViewById(R.id.practiceCard3);

        btn_docgiftfeedback = findViewById(R.id.btn_docgiftfeedback);
        img_btn_docgiftfeedback = findViewById(R.id.img_btn_docgiftfeedback);
        tv_docgiftfeedback = findViewById(R.id.tv_docgiftfeedback);
        practiceCard4 = findViewById(R.id.practiceCard4);

        btn_notification = findViewById(R.id.btn_notification);
        img_btn_notification = findViewById(R.id.img_btn_notification);
        tv_notification = findViewById(R.id.tv_notification);
        practiceCard6 = findViewById(R.id.practiceCard6);

        btn_rx = findViewById(R.id.btn_rx);
        img_btn_rx = findViewById(R.id.img_btn_rx);
        tv_rx = findViewById(R.id.tv_rx);
        practiceCard8 = findViewById(R.id.practiceCard8);

        btn_personalexpense = findViewById(R.id.btn_personalexpense);
        img_btn_personalexpense = findViewById(R.id.img_btn_personalexpense);
        tv_personalexpense = findViewById(R.id.tv_personalexpense);
        practiceCard9 = findViewById(R.id.practiceCard9);

        btn_pc = findViewById(R.id.btn_pc);
        img_btn_pc = findViewById(R.id.img_btn_pc);
        tv_pc = findViewById(R.id.tv_pc);
        cardview_pc = findViewById(R.id.cardview_pc);

        btn_promomat = findViewById(R.id.btn_promomat);
        img_btn_promomat = findViewById(R.id.img_btn_promomat);
        tv_promomat = findViewById(R.id.tv_promomat);
        cardview_promomat = findViewById(R.id.cardview_promomat);

        btn_salereports = findViewById(R.id.btn_salereports);
        img_btn_salereports = findViewById(R.id.img_btn_salereports);
        tv_salereports = findViewById(R.id.tv_salereports);
        cardview_salereports = findViewById(R.id.cardview_salereports);

        btn_msd = findViewById(R.id.btn_msd);
        img_btn_msd = findViewById(R.id.img_btn_msd);
        tv_msd = findViewById(R.id.tv_msd);
        cardview_msd = findViewById(R.id.cardview_msd);

        btn_exam = findViewById(R.id.btn_exam);
        img_btn_exam = findViewById(R.id.img_btn_exam);
        tv_exam = findViewById(R.id.tv_exam);
        practiceCard5 = findViewById(R.id.practiceCard5);

        btn_pmd_contact = findViewById(R.id.btn_pmd_contact);
        img_pmd_contact = findViewById(R.id.img_pmd_contact);
        tv_pmd_contact = findViewById(R.id.tv_pmd_contact);
        cardview_pmd_contact = findViewById(R.id.cardview_pmd_contact);
        cardview_ff_contact = findViewById(R.id.cardview_ff_contact);
        cardView_prescriber = findViewById(R.id.cardView_prescriber);
        cardview_achv_earn = findViewById(R.id.cardview_achv_earn);
        cardView_productStock = findViewById(R.id.cardView_productStock);
        cardView_tourPlan = findViewById(R.id.cardView_tourPlan);
        cardView_chemist_list = findViewById(R.id.cardView_chemist_list);

        btn_doctor_list = findViewById(R.id.btn_doctor_list);
        tv_doctor_list = findViewById(R.id.tv_doctor_list);
        img_doctor_list = findViewById(R.id.img_doctor_list);
        cardview_doctor_list = findViewById(R.id.cardview_doctor_list);
        btn_vector_feedback = findViewById(R.id.btn_vector_feedback);
        tvDesignation = findViewById(R.id.textView3);

        ff_type = null;
        Bundle b = getIntent().getExtras();
        assert b != null;
        userName = b.getString("UserName");
        designation = b.getString("Designation");
        terriName = b.getString("TerriName");
        UserName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
        password = b.getString("password");
        ff_type = b.getString("ff_type");
        vector_version = b.getString("vector_version");
        globalempCode = b.getString("emp_code");
        globalempName = b.getString("emp_name");
        user_show1.setText(globalempName);
        profile_image = base_url + globalempCode + "." + "jpg";
        Picasso.get().load(profile_image).into(imageView2);
        globalRMCode = userName;
        globalRegionalCode = UserName_2;
        globalRMCode = userName;
        t4.setText(globalRMCode);
        //t5.setText(globalRegionalCode);
        //tvDesignation.setText(preferenceManager.getDesignation());
        versionname = findViewById(R.id.versionname);

        if (designation != null && terriName != null) {
            tvDesignation.setText(designation);
            t5.setText(terriName);
        } else {
            tvDesignation.setText(preferenceManager.getDesignation());
            t5.setText(globalRegionalCode);
        }
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.d("Login", currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("Login", e.toString());
        }
        if (!currentVersion.isEmpty()) {
            versionname.setText(currentVersion);
        }
        lock_emp_check(globalempCode);
    }

    private void isUpdateAvailable() {
        AppUpdateManager mAppUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RmDashboard.this);
                builder.setTitle("Update available").setMessage("Check out the latest version of Vector?")
                        .setPositiveButton("Update now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(VectorUtils.googlePlayLink)));
                                } catch (android.content.ActivityNotFoundException exception) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(VectorUtils.alternativeLink)));
                                }
                            }
                        })
                        .setNegativeButton("Maybe later", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_DCR() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dcr_rm_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        CardView cardview_dcfpDocList = bottomSheetDialog.findViewById(R.id.cardview_dcfp_docList);
        CardView cardview_dcrEntry = bottomSheetDialog.findViewById(R.id.cardview_dcr_entry);
        CardView cardview_tourFollow = bottomSheetDialog.findViewById(R.id.cardview_tour_follow);
        Objects.requireNonNull(cardview_dcrEntry).setVisibility(View.GONE);
        Objects.requireNonNull(cardview_tourFollow).setVisibility(View.GONE);
        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        TextView textView6 = bottomSheetDialog.findViewById(R.id.textView6);
        TextView textView8 = bottomSheetDialog.findViewById(R.id.textView8);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button button3 = bottomSheetDialog.findViewById(R.id.button3);
        Button button7 = bottomSheetDialog.findViewById(R.id.button7);
        Button button8 = bottomSheetDialog.findViewById(R.id.button8);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("1.1");
        Objects.requireNonNull(button2).setText("1.2");
        Objects.requireNonNull(button3).setText("1.3");
        Objects.requireNonNull(button7).setText("1.4");
        Objects.requireNonNull(button8).setText("1.5");
        Objects.requireNonNull(textView4).setText("Dcr\nOnline");
        Objects.requireNonNull(textView5).setText("Dcr\nReport");
        Objects.requireNonNull(textView6).setText("DCFP\nFollowup");
        Objects.requireNonNull(textView8).setText("Dcr\nEntry (New)");
        Objects.requireNonNull(changepassword).setText(R.string.dailycallreport);
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);

        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, RmDcr.class);
            i.putExtra("UserName", globalRMCode);
            i.putExtra("UserName_2", globalRegionalCode);
            startActivity(i);
        });
        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, DcfpFollowupActivity.class);
            i.putExtra("UserName", globalRMCode);
            i.putExtra("UserName_2", globalRegionalCode);
            i.putExtra("UserName_3", globalRMCode);
            i.putExtra("UserRole", "D"); // D -> DCFP
            startActivity(i);
        });
        Objects.requireNonNull(cardview_tourFollow).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, DcfpFollowupActivity.class);
            i.putExtra("UserName", globalRMCode);
            i.putExtra("UserName_2", globalRegionalCode);
            i.putExtra("UserName_3", globalRMCode);
            i.putExtra("UserRole", "T"); // T -> TOUR
            startActivity(i);
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, RMDcrReport.class);
            i.putExtra("UserName", globalRMCode);
            i.putExtra("UserName_2", globalRegionalCode);
            startActivity(i);
        });
        Objects.requireNonNull(cardview_dcfpDocList).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, DcfpDoctorListActivity.class);
            i.putExtra("UserName", globalRMCode);
            i.putExtra("UserName_2", globalRegionalCode);
            i.putExtra("UserRole", "RM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_dcrEntry).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, DcrEntryActivity.class);
            i.putExtra("UserName", globalRMCode);
            i.putExtra("UserName_2", globalRegionalCode);
            i.putExtra("UserRole", "RM");
            startActivity(i);
        });
        bottomSheetDialog.setOnDismissListener(dialog -> {
            //Toast.makeText(getApplicationContext(), "bottomSheetDialog is Dismissed ", Toast.LENGTH_LONG).show();
        });
        bottomSheetDialog.show();
    }

    private void dcrClickEvent() {
        cardview_dcr.setOnClickListener(v -> showBottomSheetDialog_DCR());
        btn_dcr.setOnClickListener(v -> showBottomSheetDialog_DCR());
        img_btn_dcr.setOnClickListener(v -> showBottomSheetDialog_DCR());
        tv_dcr.setOnClickListener(v -> showBottomSheetDialog_DCR());
    }

    private void amMonitor() {
        practiceCard2.setOnClickListener(v -> {
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(RmDashboard.this, MpoDcrMonitorDaily.class);
                            i.putExtra("UserName", globalRMCode);
                            i.putExtra("UserName_2", globalRegionalCode);
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            backthred.start();
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
        Button btn_1 = bottomSheetDialog2.findViewById(R.id.btn_1);

        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog2.dismiss();
            }
        });
        Objects.requireNonNull(cardview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, ManagerDoctorServiceFollowup.class);
                i.putExtra("userName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("new_version", new_version);
                i.putExtra("user_flag", "R");
                startActivity(i);
                //bottomSheetDialog2.dismiss();
            }
        });
        Objects.requireNonNull(cardview1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, DoctorServiceTrackMonthly.class);
                i.putExtra("userName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("new_version", new_version);
                i.putExtra("user_flag", "R");
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
                showBottomSheetDialog_DOCSUPPORT();
            }
        });
    }

    private void showBottomSheetDialog_MPODCR() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("4.1");
        Objects.requireNonNull(button2).setText("4.2");
        Objects.requireNonNull(textView4).setText("MPO\nFollowup");
        Objects.requireNonNull(textView5).setText("DCR\nFollow up");
        Objects.requireNonNull(changepassword).setText("Dcr Monitor");
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        Objects.requireNonNull(cardview_rx_summary_B).setVisibility(View.GONE);

        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, MPODailyReport.class);
                i.putExtra("UserName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                startActivity(i);
            }
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, FMfollowupreport.class);
                i.putExtra("rm_code", globalRMCode);
                i.putExtra("UserName", globalRegionalCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("sm_flag", "N");
                i.putExtra("sm_code", "XXX");
                i.putExtra("admin_flag", "N");
                startActivity(i);
            }
        });
        bottomSheetDialog.show();
    }

    private void mpoDCREvent() {
        practiceCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_MPODCR();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_PE() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("9.1");
        Objects.requireNonNull(button2).setText("9.2");
        Objects.requireNonNull(textView4).setText("Personal Expense\nEntry");
        Objects.requireNonNull(textView5).setText("Personal Expense\nReport");
        Objects.requireNonNull(changepassword).setText("Personal Expense");
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_personal_expense);
        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        Objects.requireNonNull(cardview_rx_summary_B).setVisibility(View.GONE);

        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, RMPersonalExpenses.class);
                i.putExtra("UserName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, RMReportPersonalExpenses.class);
                i.putExtra("UserName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //Toast.makeText(getApplicationContext(), "bottomSheetDialog is Dismissed ", Toast.LENGTH_LONG).show();
            }
        });
        bottomSheetDialog.show();
    }

    private void personalExpenseEvent() {
        practiceCard9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog_PE();
            }
        });
        btn_personalexpense.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog_PE();
            }
        });
        img_btn_personalexpense.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog_PE();
            }
        });
        tv_personalexpense.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog_PE();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_PROMOMAT() {
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
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_promo_mat);
        Objects.requireNonNull(button1).setText("11.1");
        Objects.requireNonNull(button2).setText("11.2");
        Objects.requireNonNull(button3).setText("11.3");
        Objects.requireNonNull(textView4).setText("Sample\nRequisition");
        Objects.requireNonNull(textView5).setText("PPM\nFollow up");
        Objects.requireNonNull(textView6).setText("Gift\nFollow up");
        Objects.requireNonNull(changepassword).setText("Promo Material");

        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, PromoMaterialFollowup.class);
                i.putExtra("userName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("user_flag", "RM");
                i.putExtra("promo_type", "S");
                i.putExtra("user_code", globalRMCode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, PromoMaterialFollowup.class);
                i.putExtra("userName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("user_flag", "RM");
                i.putExtra("promo_type", "P");
                i.putExtra("user_code", globalRMCode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, PromoMaterialFollowup.class);
                i.putExtra("userName", globalRMCode);
                i.putExtra("UserName_2", globalRegionalCode);
                i.putExtra("user_flag", "RM");
                i.putExtra("promo_type", "G");
                i.putExtra("user_code", globalRMCode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void promoMaterialFollowupEvent() {
        btn_promomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_PROMOMAT();
            }
        });
        img_btn_promomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_PROMOMAT();
            }
        });
        tv_promomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_PROMOMAT();
            }
        });
        cardview_promomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_PROMOMAT();
            }
        });
    }

    private void salesReportEvent() {
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
                                Intent i = new Intent(RmDashboard.this, RmSalesReportDashboard.class);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("userName_1", globalRegionalCode);
                                i.putExtra("userName_2", globalRegionalCode);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("UserName_2", globalRMCode);
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
                                Intent i = new Intent(RmDashboard.this, RmSalesReportDashboard.class);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("userName_1", globalRegionalCode);
                                i.putExtra("userName_2", globalRegionalCode);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("UserName_2", globalRMCode);
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
                                Intent i = new Intent(RmDashboard.this, RmSalesReportDashboard.class);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("userName_1", globalRegionalCode);
                                i.putExtra("userName_2", globalRegionalCode);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("UserName_2", globalRMCode);
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
                                Intent i = new Intent(RmDashboard.this, RmSalesReportDashboard.class);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("userName_1", globalRegionalCode);
                                i.putExtra("userName_2", globalRegionalCode);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("UserName_2", globalRMCode);
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

    private void vectorFeedback() {
        btn_vector_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(RmDashboard.this, FieldFeedBack.class);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("UserName_2", globalRegionalCode);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "RM");
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
    private void showBottomSheetDialog_MSD() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        CardView cardview_commitment_followup = bottomSheetDialog.findViewById(R.id.cardview_commitment_followup);
        Objects.requireNonNull(cardview_commitment_followup).setVisibility(View.VISIBLE);
        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        TextView textView6 = bottomSheetDialog.findViewById(R.id.textView6);
        TextView tv_commitment_followup = bottomSheetDialog.findViewById(R.id.tv_commitment_followup);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button button3 = bottomSheetDialog.findViewById(R.id.button3);
        Button btn_commitment_followup = bottomSheetDialog.findViewById(R.id.btn_commitment_followup);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("13.1");
        Objects.requireNonNull(button2).setText("13.2");
        Objects.requireNonNull(button3).setText("13.3");
        Objects.requireNonNull(btn_commitment_followup).setText("13.3");
        Objects.requireNonNull(textView4).setText("MSD\nProgram Follow up");
        Objects.requireNonNull(textView5).setText("Doctor\nSupport Follow up");
        Objects.requireNonNull(textView6).setText("MSD\nProgram Follow-up");
        Objects.requireNonNull(tv_commitment_followup).setText("MSD Program\nCommitment Follow-up");
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_doctor_service);

        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        Objects.requireNonNull(changepassword).setText("MSD");
        Objects.requireNonNull(cardview_rx_summary_B).setVisibility(View.GONE);
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, MSDProgramFollowup.class);
            i.putExtra("user_code", globalRMCode);
            i.putExtra("user_name", globalRegionalCode);
            i.putExtra("user_flag", "RM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, DocSupportFollowup.class);
            i.putExtra("user_code", globalRMCode);
            i.putExtra("user_name", globalRegionalCode);
            i.putExtra("user_flag", "RM");
            startActivity(i);
            //bottomSheetDialog.dismiss();
        });
        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(v -> {
            //bottomSheetDialog.dismiss();
        });
        Objects.requireNonNull(cardview_commitment_followup).setOnClickListener(v -> {
            Intent i = new Intent(RmDashboard.this, MSDCommitmentFollowup.class);
            i.putExtra("user_code", globalRMCode);
            i.putExtra("user_name", globalRegionalCode);
            i.putExtra("user_flag", "RM");
            startActivity(i);
        });
        bottomSheetDialog.setOnDismissListener(dialog -> {
            //Toast.makeText(getApplicationContext(), "bottomSheetDialog is Dismissed ", Toast.LENGTH_LONG).show();
        });
        bottomSheetDialog.show();
    }

    private void msdDocSupport() {
        cardview_msd.setOnClickListener(v -> showBottomSheetDialog_MSD());
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
                                ShortcutBadger.applyCount(getBaseContext(), 0);
                                Intent i = new Intent(RmDashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("UserName_2", globalRegionalCode);
                                i.putExtra("new_version", globalRegionalCode);
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
                                Intent i = new Intent(RmDashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("UserName_2", globalRegionalCode);
                                i.putExtra("new_version", globalRegionalCode);
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
                                Intent i = new Intent(RmDashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("UserName_2", globalRegionalCode);
                                i.putExtra("new_version", globalRegionalCode);
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
                                Intent i = new Intent(RmDashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalRMCode);
                                i.putExtra("UserName_2", globalRegionalCode);
                                i.putExtra("new_version", globalRegionalCode);
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
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_rx_capture);
        Objects.requireNonNull(button1).setText("8.1");
        Objects.requireNonNull(button2).setText("8.1");
        Objects.requireNonNull(button3).setText("8.2");
        Objects.requireNonNull(button4).setText("8.3");
        Objects.requireNonNull(textView4).setText("RX\nEntry");
        Objects.requireNonNull(textView5).setText("RX\nSearch");
        Objects.requireNonNull(textView6).setText("RX\nSummary");
        Objects.requireNonNull(textView7).setText("RX\nSummary(MIS)");
        Objects.requireNonNull(changepassword).setText("Prescription Capture");
        Objects.requireNonNull(cardview1).setVisibility(View.GONE);
        //Objects.requireNonNull(cardview4).setVisibility(View.GONE);

        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog2.dismiss();
            }
        });
        Objects.requireNonNull(cardview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, ImageLoadActivity.class);
                i.putExtra("manager_code", globalRMCode);
                i.putExtra("manager_detail", globalRegionalCode);
                i.putExtra("manager_flag", "RM");
                startActivity(i);
            }
        });
        Objects.requireNonNull(cardview3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, PrescriptionFollowup.class);
                i.putExtra("manager_code", globalRMCode);
                i.putExtra("manager_detail", "RM");
                startActivity(i);
                //bottomSheetDialog2.dismiss();
            }
        });
        Objects.requireNonNull(cardview4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(RmDashboard.this, PrescriptionFollowup2.class);
                Intent i = new Intent(RmDashboard.this, RMRxSumMISActivity.class);
                i.putExtra("ffCode", globalRMCode);
                i.putExtra("ffType", "RM");
                startActivity(i);
            }
        });
        bottomSheetDialog2.show();
    }

    private void prescriptionEvent() {
        practiceCard8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_RXCAPTURE();
            }
        });
        img_btn_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_RXCAPTURE();
            }
        });
        btn_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_RXCAPTURE();
            }
        });
        tv_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_RXCAPTURE();
            }
        });
    }

    private void vacantMpoPwd() {
        practiceCard7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, VacantMpoPwd.class);
                i.putExtra("user_flag", "RM");
                startActivity(i);
            }
        });
        btn_productorder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, VacantMpoPwd.class);
                i.putExtra("user_flag", "RM");
                startActivity(i);
            }
        });
        img_btn_productorder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, VacantMpoPwd.class);
                i.putExtra("user_flag", "RM");
                startActivity(i);
            }
        });
        tv_productorder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(RmDashboard.this, VacantMpoPwd.class);
                i.putExtra("user_flag", "RM");
                startActivity(i);
            }
        });
    }

    private void pmdContact() {
        cardview_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(RmDashboard.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        img_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(RmDashboard.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        btn_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(RmDashboard.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        tv_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(RmDashboard.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
    }

    private void chemistListEvent() {
        cardView_chemist_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(RmDashboard.this, ChemistListActivity.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
    }

    private void doctorListInfo() {
        cardview_doctor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(RmDashboard.this, DoctorListActivity.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        img_doctor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(RmDashboard.this, DoctorListActivity.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        btn_doctor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(RmDashboard.this, DoctorListActivity.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        tv_doctor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(RmDashboard.this, DoctorListActivity.class);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
    }

    /*
    private void initViews() {
        online_order = findViewById(R.id.online_order);
        offline_order = findViewById(R.id.offline_order);
        dcr = findViewById(R.id.dcr);
        p_expense = findViewById(R.id.p_expense);
        personal_expenses = findViewById(R.id.personal_expenses);
        daily_report_am = findViewById(R.id.daily_report_am);
        personal_expenses_report = findViewById(R.id.personal_expenses_report);
        daily_monitor = findViewById(R.id.daily_monitor);
        sales_report = findViewById(R.id.sales_report);
        rx_entry = findViewById(R.id.rx_entry);
        rx_report = findViewById(R.id.rx_report);
        gift_camp = findViewById(R.id.gift_camp);
        dcc_camp_rep = findViewById(R.id.dcc_camp_rep);
        notification = findViewById(R.id.notification);
        pc_conference_btn = findViewById(R.id.pc_conference_btn);
        doctor_service_followup = findViewById(R.id.doctor_service_followup);
        promo_material_followup = findViewById(R.id.promo_material_followup);
        mrc_exam = findViewById(R.id.mrc_exam);
        doc_gift_btn = findViewById(R.id.doc_gift_btn);
        bar_1 = findViewById(R.id.progressBar);
        bar_2 = findViewById(R.id.progressBar1);
        bar_3 = findViewById(R.id.progressBar3);
        bar_4 = findViewById(R.id.progressBar4);
        bar_5 = findViewById(R.id.progressBar5);
        bar_6 = findViewById(R.id.progressBar6);
        bar_7 = findViewById(R.id.progressBar7);
        bar_8 = findViewById(R.id.progressBar8);
        bar_9 = findViewById(R.id.progressBar9);
        bar_10 = findViewById(R.id.progressBar10);
        bar_11 = findViewById(R.id.progressBar11);
        bar_12 = findViewById(R.id.progressBar12);
        bar_13 = findViewById(R.id.progressBar13);
        bar_14 = findViewById(R.id.progressBar14);
        bar_15 = findViewById(R.id.progressBar15);
        bar_16 = findViewById(R.id.progressBar16);
        bar_17 = findViewById(R.id.progressBar22);
        bar_18 = findViewById(R.id.progressBar20);
        bar_G1 = findViewById(R.id.progressBarG1);


        prescription_entry = findViewById(R.id.prescription_entry);
        bar_prescription = findViewById(R.id.progressBar18);

        bar_vector_feedback =  findViewById(R.id.bar_vector_feedback);
        btn_vector_feedback =  findViewById(R.id.btn_vector_feedback);

        btn_msd_doctor_support = findViewById(R.id.btn_msd_doc_support);
        bar_msd_doctor_support = findViewById(R.id.bar_msd_doc_support);


        user_show1 = findViewById(R.id.user_show1);
        Bundle b = getIntent().getExtras();
        assert b != null;
        userName = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b");
        db = new DatabaseHandler(this);
        mpo_code_interna = db.getterritoryname();
        mpo_code_i = mpo_code_interna.toString();
        if(userName_2 == null) {
            userName_2 = mpo_code_i;
        }
        user_show1.setText(String.format("%s(%s)", UserName_2, userName));
        globalRMCode = userName;
        globalRegionalCode = UserName_2;
    }
*/
    private void logoutUser() {
        session.setLogin(false);
        session.invalidate();
        Intent intent = new Intent(RmDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    private void showSnack() {
        new Thread() {
            public void run() {
                RmDashboard.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "No internet Connection, Please Check Your Connection";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    public void onBackPressed() {
        moveTaskToBack(true);
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
        Log.e("onPause----->", global_admin_Code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateUIReciver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        preferenceManager.setTasbihCounter(count);
        preferenceManager.setusername(userName);
        preferenceManager.setpassword(password);
        preferenceManager.setuserrole(message_3);
        preferenceManager.setuserdtl(UserName_2);
        preferenceManager.setfftype(ff_type);
        preferenceManager.setcurrentVersion(vector_version);
        preferenceManager.setexecutive_name(globalempName);
        preferenceManager.setemp_code(globalempCode);
        //updateLocation();
        preferenceManager.setAdmin_Code(global_admin_Code);
        Log.e("onDestroy----->", global_admin_Code);
    }

    @Override
    public void onClick(View v) {

    }

    private void lock_emp_check(String emp_code) {
        String vectorVersion = currentVersion.replace(".", "");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Check locked user...");
        //progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.lock_emp_check(globalempCode, vectorVersion);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(@NonNull Call<Patient> call, @NonNull Response<Patient> response) {
                //progressDialog.dismiss();
                assert response.body() != null;
                String status = response.body().getTerritory_name();
                Log.e("Check locked user-->", status);

                if (status.equals("Y")) {
                    String message = response.body().getMessage_2();
                    Toast.makeText(RmDashboard.this, message, Toast.LENGTH_LONG).show();
                    //Toast.makeText(RmDashboard.this, "You are locked...", Toast.LENGTH_LONG).show();
                    preferenceManager.clearPreferences();
                    count = 0;
                    Intent logoutIntent = new Intent(RmDashboard.this, Login.class);
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