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
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.github.tutorialsandroid.appxupdater.AppUpdater;
import com.github.tutorialsandroid.appxupdater.AppUpdaterUtils;
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
import com.opl.pharmavector.dcfpFollowup.DoctorReachActivity;
import com.opl.pharmavector.dcfpFollowup.MPODcfpEntryActivity;
import com.opl.pharmavector.doctorList.DoctorListActivity;
import com.opl.pharmavector.doctorservice.DoctorServiceAck;
import com.opl.pharmavector.doctorservice.DoctorServiceFollowup;
import com.opl.pharmavector.doctorservice.DoctorServiceTrackMonthly;
import com.opl.pharmavector.exam.ExamResultFollowup;
import com.opl.pharmavector.geolocation.DoctorChamberLocate;
import com.opl.pharmavector.giftfeedback.FieldFeedBack;
import com.opl.pharmavector.giftfeedback.GiftFeedbackEntry;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mpodcr.DcfpActivity;
import com.opl.pharmavector.mpodcr.Dcr;
import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.msd_doc_support.DocSupportFollowup;
import com.opl.pharmavector.msd_doc_support.DocSupportReq;
import com.opl.pharmavector.msd_doc_support.MSDCommitmentFollowup;
import com.opl.pharmavector.msd_doc_support.MSDProgramApproval;
import com.opl.pharmavector.msd_doc_support.MSDProgramFollowup;
import com.opl.pharmavector.order_online.ReadComments;
import com.opl.pharmavector.pcconference.PcConferenceFollowup;
import com.opl.pharmavector.pcconference.PcProposal;
import com.opl.pharmavector.prescriptionsurvey.MPORxSumMISActivity;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionEntry;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.ImageLoadActivity;
import com.opl.pharmavector.promomat.PromoMaterialFollowup;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.service.MyLocationService;
import com.opl.pharmavector.util.NetInfo;
import com.opl.pharmavector.util.NotificationUtils;
import com.opl.pharmavector.util.PreferenceManager;
import com.opl.pharmavector.util.VectorUtils;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends Activity implements View.OnClickListener {
    public String userName_1, userName, designation, terriName, userName_2, UserName_2, global_admin_Code;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public static final String TAG_SUCCESS = "mysuccess";
    public static final String TAG_MESSAGE = "mymessage";
    public static final String TAG_success_1 = "success_1";
    public static final String TAG_ord_no = "ord_no";
    public AutoCompleteTextView actv;
    private DatabaseHandler db;
    private String TAG = Offlinereport.class.getSimpleName();
    private Button logout;
    public TextView user_show1, user_show2, tvLocationName, versionname;
    private SessionManager session;
    private final String fetch_exam_flag = BASE_URL + "/vectorexam/fetch_exam_flag.php";
    private final String submit_url = BASE_URL + "/notification/save_vector_notification_token_data_test.php";
    public String message, locationName, currentVersion;
    public String success, mymessage, mysuccess, myexamid, myexamtime, myexamtimeleft;
    public String exam_flag;
    public static String globalmpocode, globalmpoflag, globalterritorycode, globalfftype, ff_type, build_model, build_brand,
            build_manufac, build_id, build_device, build_version, password, globalempCode, globalempName, new_version, message_3, os_version;
    public static String track_lat, track_lang, track_add = "No Address";
    public static String vectorToken;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private int count;
    Boolean isAddressSubmit;
    PreferenceManager preferenceManager;
    AppUpdaterUtils appUpdaterUtils;
    AppUpdater appUpdater;
    public static String version, phoneNumber;
    static Dashboard instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<HashMap<String, String>> customerlist;
    public static String examid;
    private final String URL_CUSOTMER = BASE_URL + "/get_customer.php";
    private ProgressDialog pDialog;
    private String log_status = "A";
    private String vector_version;
    double fetchedlang, fetchedlat;
    Context context;
    BroadcastReceiver updateUIReciver;
    CardView cardview_dcr, practiceCard2, practiceCard3, practiceCard4, practiceCard5, practiceCard6, cardView_prescriber, cardview_achv_earn,
            practiceCard7, practiceCard8, practiceCard9, cardview_pc, cardview_promomat, cardview_salereports, cardview_msd, cardview_pmd_contact,
            cardview_doctor_list, cardView_chemistList;
    ImageButton profileB, img_btn_dcr, img_btn_dcc, img_btn_productorder, img_btn_docservice, img_btn_docgiftfeedback, img_doctor_list,
            img_btn_notification, img_btn_rx, img_btn_personalexpense, img_btn_pc, img_btn_promomat, img_btn_salereports, img_btn_msd, img_btn_exam,
            img_pmd_contact;
    TextView tv_dcr, tv_productorder, tv_dcc, tv_docservice, tv_docgiftfeedback, tv_notification, tv_rx, tv_personalexpense,
            tv_pc, tv_promomat, tv_salereports, tv_msd, tv_exam, tv_pmd_contact, tv_doctor_list;
    Button btn_dcr, btn_productorder, btn_dcc, btn_docservice, btn_docgiftfeedback, btn_notification, btn_rx, btn_personalexpense, btn_pc, btn_promomat, btn_salereports,
            btn_msd, btn_exam, btn_vector_feedback, btn_pmd_contact, btn_doctor_list;
    public TextView t4, t5, tvDesignation;
    public ImageView imageView2, logo_team;
    public static String team_logo, profile_image;
    public String base_url = ApiClient.BASE_URL + "vector_ff_image/";
    LocationManager locationManager;
    private static final int PHONE_NUMBER_CODE = 101;

    public static Dashboard getInstance() {
        return instance;
    }

    @SuppressLint({"CutPasteId", "HardwareIds", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_mpo_dashboard);
        //setContentView(R.layout.activity_mpo_dashboard);

        isUpdateAvailable();
        VectorUtils.screenShotProtect(this);
        isAddressSubmit = true;
        preferenceManager = new PreferenceManager(this);
        count = preferenceManager.getTasbihCounter();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        global_admin_Code = preferenceManager.getAdmin_Code();
        statusBarHide();
        initViews();
        initBroadcastReceiver();
        registerReceiver(updateUIReciver, new IntentFilter(MyLocationService.ACTION_PROCESS_UPDATE));
        //RunAnimation();
        Log.e("Admin Code--->", preferenceManager.getAdmin_Code());

        updateLocation();
        orderEvents();
        dcrClickEvent();
        personalExpenseEvent();
        dccfollowupEvent();
        promoMaterialFollowupEvent();
        salesReportEvent();
        noticeBoradEvent();
        pcConferenceEvent();
        doctorServiceEvent();
        mrcExamEvent();
        prescriptionEvent();
        doctorGiftEvent();
        vectorFeedback();
        getDeviceDetails();
        msdDocSupport();
        pmdContact();
        doctorListInfo();
        prescriberEvent();
        achieveEarnEvent();
        getDeviceSimNumber();
        chemistListEvent();

        session = new SessionManager(getApplicationContext());
        PackageManager pm = getApplicationContext().getPackageManager();
        String pkgName = getApplicationContext().getPackageName();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        vector_version = Objects.requireNonNull(pkgInfo).versionName;

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this, R.style.Theme_Design_BottomSheetDialog);
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
                                        //unregisterReceiver(updateUIReciver);
                                        Intent logoutIntent = new Intent(Dashboard.this, Login.class);
                                        startActivity(logoutIntent);
                                        finish();
                                    }
                                });
                                server.start();
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
        instance = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED /*||
                checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("App Require Location").setMessage("This app collects location data to enable Doctor Chamber Location Feature even when app is running")
                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                            dexterPermission(Dashboard.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                                        } else {
                                            dexterPermission(Dashboard.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
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
                                Intent logoutIntent = new Intent(Dashboard.this, Login.class);
                                startActivity(logoutIntent);
                                finish();

                            }
                        })
                        .show();
            }
        }
        db = new DatabaseHandler(this);
        customerlist = new ArrayList<>();
        //vector_version = getString(R.string.vector_version);
        Log.e("vector_version---->", vector_version);
        firebaseEvent();
        autoLogout();
        TeamLogo();
        userLogIn(track_add);
        //userLogIn();
    }

    private void isUpdateAvailable() {
        AppUpdateManager mAppUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
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
    private void prescriberEventNew() {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.dcfp_bottom_sheet_dialog);
            CardView cardView_topPrescriber = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
            Objects.requireNonNull(cardView_topPrescriber).setVisibility(View.GONE);
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
            Objects.requireNonNull(button1).setText("16.1");
            Objects.requireNonNull(button2).setText("16.1");
            Objects.requireNonNull(button3).setText("16.2");
            Objects.requireNonNull(textView4).setText("SPI Top Prescriber\n(Generic)");
            Objects.requireNonNull(textView5).setText("SPI \nReport");
            Objects.requireNonNull(textView6).setText("Doctor \nReach");
            Objects.requireNonNull(changePassword).setText(R.string.spiReport);
            ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
            Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
            Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
            bottomSheetDialog.show();

            Objects.requireNonNull(cardView_spiReport).setOnClickListener(v -> {
                Intent i = new Intent(Dashboard.this, MRDPresReport.class);
                i.putExtra("userName", globalempName);
                i.putExtra("UserName", globalempCode);
                i.putExtra("report_flag", "SPI");
                i.putExtra("asm_flag", "N");
                i.putExtra("sm_flag", "N");
                i.putExtra("gm_flag", "N");
                i.putExtra("rm_flag", "N");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "Y");
                startActivity(i);
            });
            Objects.requireNonNull(cardView_doctorReach).setOnClickListener(v -> {
                Intent i = new Intent(Dashboard.this, DoctorReachActivity.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", globalempCode);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                i.putExtra("UserRole", "MPO");
                i.putExtra("report_flag", "SPI");
                i.putExtra("asm_flag", "N");
                i.putExtra("sm_flag", "N");
                i.putExtra("gm_flag", "N");
                i.putExtra("rm_flag", "N");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "Y");
                startActivity(i);
            });
    }

    private void prescriberEvent() {
        cardView_prescriber.setOnClickListener(pres -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.dcfp_bottom_sheet_dialog);
            CardView cardView_topPrescriber = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
            Objects.requireNonNull(cardView_topPrescriber).setVisibility(View.GONE);
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
            Objects.requireNonNull(button1).setText("16.1");
            Objects.requireNonNull(button2).setText("16.1");
            Objects.requireNonNull(button3).setText("16.2");
            Objects.requireNonNull(textView4).setText("SPI Top Prescriber\n(Generic)");
            Objects.requireNonNull(textView5).setText("SPI \nReport");
            Objects.requireNonNull(textView6).setText("Doctor \nReach");
            Objects.requireNonNull(changePassword).setText(R.string.spiReport);
            ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
            Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
            Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
            bottomSheetDialog.show();

            Objects.requireNonNull(cardView_spiReport).setOnClickListener(v -> {
                Intent i = new Intent(Dashboard.this, MRDPresReport.class);
                i.putExtra("userName", globalempName);
                i.putExtra("UserName", globalempCode);
                i.putExtra("report_flag", "SPI");
                i.putExtra("asm_flag", "N");
                i.putExtra("sm_flag", "N");
                i.putExtra("gm_flag", "N");
                i.putExtra("rm_flag", "N");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "Y");
                startActivity(i);
            });
            Objects.requireNonNull(cardView_doctorReach).setOnClickListener(v -> {
                Intent i = new Intent(Dashboard.this, DoctorReachActivity.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", globalempCode);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                i.putExtra("UserRole", "MPO");
                i.putExtra("report_flag", "SPI");
                i.putExtra("asm_flag", "N");
                i.putExtra("sm_flag", "N");
                i.putExtra("gm_flag", "N");
                i.putExtra("rm_flag", "N");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "Y");
                startActivity(i);
            });
        });
    }

    private void achieveEarnEventNew() {
        Intent i = new Intent(Dashboard.this, AchieveEarnActivity.class);
        i.putExtra("UserName", globalempName);
        i.putExtra("UserCode", userName);
        i.putExtra("new_version", version);
        i.putExtra("message_3", message_3);
        i.putExtra("UserRole", "MPO");
        startActivity(i);
    }

    private void achieveEarnEvent() {
        cardview_achv_earn.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, AchieveEarnActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", userName);
            i.putExtra("new_version", version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "MPO");
            startActivity(i);
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

    private void autoLogout() {
        if (preferenceManager.getexecutive_name().equals("PMD") || preferenceManager.getexecutive_name().equals("Vector") || preferenceManager.getexecutive_name().equals(" ")) {
            log_status = "N";
            preferenceManager.clearPreferences();
            count = 0;
            Intent logoutIntent = new Intent(Dashboard.this, Login.class);
            startActivity(logoutIntent);
            finish();
        }
    }

    private void RunAnimation() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.animated_textview);
        a.reset();
        //TextView tv = (TextView) findViewById(R.id.firstTextView);
        user_show1.clearAnimation();
        user_show1.startAnimation(a);
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
        versionname = findViewById(R.id.versionname);
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

        cardview_doctor_list = findViewById(R.id.cardview_doctor_list);
        btn_doctor_list = findViewById(R.id.btn_doctor_list);
        img_doctor_list = findViewById(R.id.img_doctor_list);
        tv_doctor_list = findViewById(R.id.tv_doctor_list);
        btn_vector_feedback = findViewById(R.id.btn_vector_feedback);
        cardView_prescriber = findViewById(R.id.cardView_prescriber);
        cardview_achv_earn = findViewById(R.id.cardview_achv_earn);
        cardView_chemistList = findViewById(R.id.cardView_chemistList);
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
        Log.e("Personal Image Path : ", base_url + globalempCode + "." + "jpg");
        profile_image = base_url + globalempCode + "." + "jpg";
        Picasso.get().load(profile_image).into(imageView2);
        db = new DatabaseHandler(this);
        ArrayList<String> mpo_code_interna = db.getterritoryname();
        String mpo_code_i = mpo_code_interna.toString();
        globalmpocode = userName;
        globalterritorycode = UserName_2;
        globalmpocode = userName;
        t4.setText(globalmpocode);
        //t5.setText(globalterritorycode);

        if (designation != null && terriName != null) {
            tvDesignation.setText(designation);
            t5.setText(terriName);
        } else {
            tvDesignation.setText(preferenceManager.getDesignation());
            t5.setText(globalterritorycode);
        }
        //tvDesignation.setText(preferenceManager.getDesignation());
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

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog() {
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
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_product_order);

        Objects.requireNonNull(textView4).setText("Order\nOnline");
        Objects.requireNonNull(textView5).setText("Order\nOffline");
        Objects.requireNonNull(changepassword).setText(R.string.mpo_product_order);
        Objects.requireNonNull(button1).setText("7.1");
        Objects.requireNonNull(button2).setText("7.2");
        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        Objects.requireNonNull(cardview_rx_summary_B).setVisibility(View.GONE);

        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, ReadComments.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", new_version);
            i.putExtra("emp_code", globalempCode);
            i.putExtra("emp_location", track_add);
            startActivity(i);
            //bottomSheetDialog.dismiss();
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, Offlinereport.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", new_version);
            startActivity(i);
            //bottomSheetDialog.dismiss();
        });
        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.setOnDismissListener(dialog -> {
            //Toast.makeText(getApplicationContext(), "bottomSheetDialog is Dismissed ", Toast.LENGTH_LONG).show();
        });
        bottomSheetDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_DCR() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        CardView cardview_dcfpPreview = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        CardView cardview_dcfpEntry = bottomSheetDialog.findViewById(R.id.cardview_commitment_followup);
        CardView cardview_dcfpDocList = bottomSheetDialog.findViewById(R.id.cardview_dcfp_docList);
        Objects.requireNonNull(cardview_dcfpEntry).setVisibility(View.VISIBLE);
        Objects.requireNonNull(cardview_dcfpDocList).setVisibility(View.VISIBLE);
        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        TextView textView6 = bottomSheetDialog.findViewById(R.id.textView6);
        TextView tvDcfpEntry = bottomSheetDialog.findViewById(R.id.tv_commitment_followup);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button button3 = bottomSheetDialog.findViewById(R.id.button3);
        Button btnDcfpEntry = bottomSheetDialog.findViewById(R.id.btn_commitment_followup);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("1.1");
        Objects.requireNonNull(button2).setText("1.2");
        Objects.requireNonNull(button3).setText("1.3");
        Objects.requireNonNull(btnDcfpEntry).setText("1.4");
        Objects.requireNonNull(textView4).setText("DCR\nOnline");
        Objects.requireNonNull(textView5).setText("DCR\nReport");
        Objects.requireNonNull(textView6).setText("DCFP\nPreview");
        Objects.requireNonNull(tvDcfpEntry).setText("DCFP\nEntry");
        Objects.requireNonNull(changepassword).setText(R.string.dailycallreport);
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);

        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, Dcr.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            startActivity(i);
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DcrReport.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            startActivity(i);
        });
        Objects.requireNonNull(cardview_dcfpPreview).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DcfpActivity.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            startActivity(i);
        });
        Objects.requireNonNull(cardview_dcfpEntry).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, MPODcfpEntryActivity.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            startActivity(i);
        });
        Objects.requireNonNull(cardview_dcfpDocList).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DcfpDoctorListActivity.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", terriName);
            i.putExtra("UserRole", "MPO");
            startActivity(i);
        });
        bottomSheetDialog.setOnDismissListener(dialog -> {
            //Toast.makeText(getApplicationContext(), "BottomSheetDialog is Dismissed", Toast.LENGTH_LONG).show();
        });
        bottomSheetDialog.show();
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
                ArrayList<String> UserName_2 = db.getterritoryname();
                String user = UserName_2.toString();
                Intent i = new Intent(Dashboard.this, PersonalExpenses.class);
                i.putExtra("UserName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, ReportPersonalExpenses.class);
                i.putExtra("UserName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
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

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_DCC() {
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

        Objects.requireNonNull(button1).setText("2.1");
        Objects.requireNonNull(button2).setText("2.2");
        Objects.requireNonNull(textView4).setText("Dcc\nFollowup");
        Objects.requireNonNull(textView5).setText("Dcc\nAvailable Stock");
        Objects.requireNonNull(changepassword).setText("DCC Followup");
        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        Objects.requireNonNull(cardview_rx_summary_B).setVisibility(View.GONE);
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        imageView3.setBackgroundResource(R.drawable.ic_dcc);

        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, MPODCCFollowupgift.class);
                i.putExtra("UserName", Dashboard.globalmpocode);
                i.putExtra("UserName_2", Dashboard.globalterritorycode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, MPODccStock.class);
                i.putExtra("UserName", Dashboard.globalmpocode);
                i.putExtra("UserName_2", Dashboard.globalterritorycode);
                i.putExtra("mpo_code", Dashboard.globalmpocode);
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

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_MSD() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.msd_rx_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        CardView cardview_rx_summary_C = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_C);
        CardView cardview_rx_summary_E = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_E);
        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        TextView textView6 = bottomSheetDialog.findViewById(R.id.textView6);
        TextView textView7 = bottomSheetDialog.findViewById(R.id.textView7);
        TextView textView8 = bottomSheetDialog.findViewById(R.id.textView8);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button button3 = bottomSheetDialog.findViewById(R.id.button3);
        Button button4 = bottomSheetDialog.findViewById(R.id.button4);
        Button button5 = bottomSheetDialog.findViewById(R.id.button5);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("13.1");
        Objects.requireNonNull(button2).setText("13.2");
        Objects.requireNonNull(button3).setText("13.3");
        Objects.requireNonNull(button4).setText("13.4");
        Objects.requireNonNull(button5).setText("13.5");
        Objects.requireNonNull(textView4).setText("Doctor\n Support Requisition");
        Objects.requireNonNull(textView5).setText("Doctor\nSupport Follow-up");
        Objects.requireNonNull(textView6).setText("MSD\nProgram Follow-up");
        Objects.requireNonNull(textView7).setText("MSD\nProgram Approval");
        Objects.requireNonNull(textView8).setText("MSD Program\nCommitment Follow-up");
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_doctor_service);

        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        Objects.requireNonNull(changepassword).setText("MSD");
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DocSupportReq.class);
            i.putExtra("user_code", Dashboard.globalmpocode);
            i.putExtra("user_name", Dashboard.globalterritorycode);
            i.putExtra("user_flag", "MPO");
            startActivity(i);
            //bottomSheetDialog.dismiss();
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DocSupportFollowup.class);
            i.putExtra("user_code", Dashboard.globalmpocode);
            i.putExtra("user_name", Dashboard.globalterritorycode);
            i.putExtra("user_flag", "MPO");
            startActivity(i);
            //bottomSheetDialog.dismiss();
        });
        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, MSDProgramFollowup.class);
            i.putExtra("user_code", Dashboard.globalmpocode);
            i.putExtra("user_name", Dashboard.globalterritorycode);
            i.putExtra("user_flag", "MPO");
            startActivity(i);
            //bottomSheetDialog.dismiss();
        });
        Objects.requireNonNull(cardview_rx_summary_C).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, MSDProgramApproval.class);
            i.putExtra("user_code", Dashboard.globalmpocode);
            i.putExtra("user_name", Dashboard.globalterritorycode);
            i.putExtra("user_flag", "MPO");
            startActivity(i);
            //bottomSheetDialog.dismiss();
        });
        Objects.requireNonNull(cardview_rx_summary_E).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, MSDCommitmentFollowup.class);
            i.putExtra("user_code", Dashboard.globalmpocode);
            i.putExtra("user_name", Dashboard.globalterritorycode);
            i.putExtra("user_flag", "MPO");
            startActivity(i);
            //bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.setOnDismissListener(dialog -> {
            //Toast.makeText(getApplicationContext(), "bottomSheetDialog is Dismissed ", Toast.LENGTH_LONG).show();
        });
        bottomSheetDialog.show();
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
        imageView3.setBackgroundResource(R.drawable.ic_promo_mat);

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
                Intent i = new Intent(Dashboard.this, PromoMaterialFollowup.class);
                i.putExtra("userName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                i.putExtra("user_flag", "MPO");
                i.putExtra("promo_type", "S");
                i.putExtra("user_code", globalmpocode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, PromoMaterialFollowup.class);
                i.putExtra("userName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                i.putExtra("user_flag", "MPO");
                i.putExtra("promo_type", "P");
                i.putExtra("user_code", globalmpocode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, PromoMaterialFollowup.class);
                i.putExtra("userName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                i.putExtra("user_flag", "MPO");
                i.putExtra("promo_type", "G");
                i.putExtra("user_code", globalmpocode);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
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
        TextView changepassword = bottomSheetDialog2.findViewById(R.id.changepassword);
        ImageView imageView3 = bottomSheetDialog2.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_doctor_service);
        Objects.requireNonNull(changepassword).setText("Doctor Service");

        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog2.dismiss());
        Objects.requireNonNull(cardview1).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DoctorServiceFollowup.class);
            i.putExtra("userName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", new_version);
            i.putExtra("user_flag", "M");
            startActivity(i);
            //bottomSheetDialog2.dismiss();
        });
        Objects.requireNonNull(cardview2).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DoctorServiceAck.class);
            i.putExtra("userName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", new_version);
            i.putExtra("user_flag", "M");
            startActivity(i);
            bottomSheetDialog2.dismiss();
        });
        Objects.requireNonNull(cardview3).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DoctorServiceTrackMonthly.class);
            i.putExtra("userName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", new_version);
            i.putExtra("user_flag", "M");
            startActivity(i);
            //bottomSheetDialog2.dismiss();
        });
        Objects.requireNonNull(cardview4).setOnClickListener(v -> {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Intent i = new Intent(Dashboard.this, DoctorChamberLocate.class);
                startActivity(i);
            } else {
                showGPSDisabledAlertToUser();
            }
        });
        bottomSheetDialog2.setOnDismissListener(dialog -> {
            //Toast.makeText(getApplicationContext(), "bottomSheetDialog is Dismissed ", Toast.LENGTH_LONG).show();
        });
        bottomSheetDialog2.show();
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

        Objects.requireNonNull(button1).setText("10.1");
        Objects.requireNonNull(button2).setText("10.2");
        Objects.requireNonNull(button3).setText("10.3");
        Objects.requireNonNull(button4).setText("10.4");
        Objects.requireNonNull(textView4).setText("PC\nProposal");
        Objects.requireNonNull(textView5).setText("PC Proposal\nFollow up");
        Objects.requireNonNull(textView6).setText("PC\nBill");
        Objects.requireNonNull(textView7).setText("PC\nBill Follow up");
        ImageView imageView3 = bottomSheetDialog2.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_pc_conference);

        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog2.dismiss());
        Objects.requireNonNull(changepassword).setText("PC Conference");
        Objects.requireNonNull(cardview1).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, PcProposal.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            startActivity(i);
            //bottomSheetDialog2.dismiss();
        });
        Objects.requireNonNull(cardview2).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, PcConferenceFollowup.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("user_flag", "M");
            startActivity(i);
            //bottomSheetDialog2.dismiss();
        });
        Objects.requireNonNull(cardview3).setOnClickListener(v -> {});
        Objects.requireNonNull(cardview4).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, PCBillFollowup.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("user_flag", "M");
            startActivity(i);
            //bottomSheetDialog2.dismiss();
        });
        bottomSheetDialog2.show();
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
        Objects.requireNonNull(button2).setText("8.2");
        Objects.requireNonNull(button3).setText("8.3");
        Objects.requireNonNull(button4).setText("8.4");
        Objects.requireNonNull(textView4).setText("RX\nEntry");
        Objects.requireNonNull(textView5).setText("RX\nSearch");
        Objects.requireNonNull(textView6).setText("RX\nSummary");
        Objects.requireNonNull(textView7).setText("RX\nSummary(MIS)");
        Objects.requireNonNull(changepassword).setText("Prescription Capture");
        //Objects.requireNonNull(cardview4).setVisibility(View.GONE);

        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog2.dismiss());
        Objects.requireNonNull(cardview1).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
            startActivity(i);
            //bottomSheetDialog2.dismiss();
        });
        Objects.requireNonNull(cardview2).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, ImageLoadActivity.class);
            i.putExtra("manager_code", Dashboard.globalmpocode);
            i.putExtra("manager_detail", Dashboard.globalterritorycode);
            i.putExtra("manager_flag", "MPO");
            startActivity(i);
        });
        Objects.requireNonNull(cardview3).setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, PrescriptionFollowup.class);
            i.putExtra("manager_code", Dashboard.globalmpocode);
            i.putExtra("manager_detail", Dashboard.globalterritorycode);
            i.putExtra("manager_flag", "MPO");
            startActivity(i);
            //bottomSheetDialog2.dismiss();
        });
        Objects.requireNonNull(cardview4).setOnClickListener(v -> {
            //Intent i = new Intent(Dashboard.this, PrescriptionFollowup2.class);
            Intent i = new Intent(Dashboard.this, MPORxSumMISActivity.class);
            i.putExtra("ffCode", GMDashboard1.globalAdmin);
            i.putExtra("ffType", "MPO");
            startActivity(i);
        });
        bottomSheetDialog2.show();
    }

    private void dcrClickEvent() {
        cardview_dcr.setOnClickListener(v -> showBottomSheetDialog_DCR());
        btn_dcr.setOnClickListener(v -> showBottomSheetDialog_DCR());
        img_btn_dcr.setOnClickListener(v -> showBottomSheetDialog_DCR());
        tv_dcr.setOnClickListener(v -> showBottomSheetDialog_DCR());
    }

    private void dccfollowupEvent() {
        practiceCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_DCC();
            }
        });
        btn_dcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_DCC();
            }
        });
        tv_dcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_DCC();
            }
        });
        img_btn_dcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_DCC();
            }
        });
    }

    private void doctorServiceEvent() {
        practiceCard3.setOnClickListener(v -> showBottomSheetDialog_DOCSUPPORT());
        img_btn_docservice.setOnClickListener(v -> showBottomSheetDialog_DOCSUPPORT());
        btn_docservice.setOnClickListener(v -> showBottomSheetDialog_DOCSUPPORT());
        tv_docservice.setOnClickListener(v -> showBottomSheetDialog_DOCSUPPORT());
    }

    private void doctorGiftEvent() {
        btn_docgiftfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent intent = new Intent(Dashboard.this, GiftFeedbackEntry.class);
                                intent.putExtra("UserName", globalmpocode);
                                intent.putExtra("UserName_2", globalterritorycode);
                                intent.putExtra("new_version", new_version);
                                intent.putExtra("user_flag", "MPO");
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
        tv_docgiftfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent intent = new Intent(Dashboard.this, GiftFeedbackEntry.class);
                                intent.putExtra("UserName", globalmpocode);
                                intent.putExtra("UserName_2", globalterritorycode);
                                intent.putExtra("new_version", new_version);
                                intent.putExtra("user_flag", "MPO");
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
        img_btn_docgiftfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent intent = new Intent(Dashboard.this, GiftFeedbackEntry.class);
                                intent.putExtra("UserName", globalmpocode);
                                intent.putExtra("UserName_2", globalterritorycode);
                                intent.putExtra("new_version", new_version);
                                intent.putExtra("user_flag", "MPO");
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
        practiceCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent intent = new Intent(Dashboard.this, GiftFeedbackEntry.class);
                                intent.putExtra("UserName", globalmpocode);
                                intent.putExtra("UserName_2", globalterritorycode);
                                intent.putExtra("new_version", new_version);
                                intent.putExtra("user_flag", "MPO");
                                startActivity(intent);
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

    private void doctorGiftFeedback() {
        Thread backthred = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!NetInfo.isOnline(getBaseContext())) {
                        showSnack();
                    } else {
                        ArrayList<String> UserName_2 = db.getterritoryname();
                        String user = UserName_2.toString();
                        Intent intent = new Intent(Dashboard.this, GiftFeedbackEntry.class);
                        intent.putExtra("UserName", globalmpocode);
                        intent.putExtra("UserName_2", globalterritorycode);
                        intent.putExtra("new_version", new_version);
                        intent.putExtra("user_flag", "MPO");
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        backthred.start();
    }

    private void noticeBoardEventNew() {
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            ShortcutBadger.applyCount(getBaseContext(), 0);
                            ArrayList<String> UserName_2 = db.getterritoryname();
                            String user = UserName_2.toString();
                            Intent i = new Intent(Dashboard.this, NoticeBoard.class);
                            i.putExtra("UserName", globalmpocode);
                            i.putExtra("UserName_2", globalterritorycode);
                            i.putExtra("new_version", globalterritorycode);
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            backthred.start();
    }

    private void noticeBoradEvent() {
        practiceCard6.setOnClickListener(v -> {
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            ShortcutBadger.applyCount(getBaseContext(), 0);
                            ArrayList<String> UserName_2 = db.getterritoryname();
                            String user = UserName_2.toString();
                            Intent i = new Intent(Dashboard.this, NoticeBoard.class);
                            i.putExtra("UserName", globalmpocode);
                            i.putExtra("UserName_2", globalterritorycode);
                            i.putExtra("new_version", globalterritorycode);
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            backthred.start();
        });
        img_btn_notification.setOnClickListener(v -> {
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            ArrayList<String> UserName_2 = db.getterritoryname();
                            String user = UserName_2.toString();
                            Intent i = new Intent(Dashboard.this, NoticeBoard.class);
                            i.putExtra("UserName", globalmpocode);
                            i.putExtra("UserName_2", globalterritorycode);
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            backthred.start();
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", globalterritorycode);
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
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

    private void orderEvents() {
        practiceCard7.setOnClickListener(v -> showBottomSheetDialog());
        btn_productorder.setOnClickListener(v -> showBottomSheetDialog());
        img_btn_productorder.setOnClickListener(v -> showBottomSheetDialog());
        tv_productorder.setOnClickListener(v -> showBottomSheetDialog());
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

    private void pcConferenceEvent() {
        cardview_pc.setOnClickListener(v -> showBottomSheetDialog_PCCONFERENCE());
        btn_pc.setOnClickListener(v -> showBottomSheetDialog_PCCONFERENCE());
        tv_pc.setOnClickListener(v -> showBottomSheetDialog_PCCONFERENCE());
        img_btn_pc.setOnClickListener(v -> showBottomSheetDialog_PCCONFERENCE());
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

    private void salesReportEventNew() {
            Thread backthred = new Thread(() -> {
                try {
                    if (!NetInfo.isOnline(getBaseContext())) {
                        showSnack();
                    } else {
                        ArrayList<String> UserName_2 = db.getterritoryname();
                        String user = UserName_2.toString();
                        Intent i = new Intent(Dashboard.this, Report.class);
                        i.putExtra("UserName", globalmpocode);
                        i.putExtra("UserName_2", globalterritorycode);
                        i.putExtra("new_version", Login.version);
                        i.putExtra("message_3", message_3);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            backthred.start();
    }

    private void salesReportEvent() {
        cardview_salereports.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
                try {
                    if (!NetInfo.isOnline(getBaseContext())) {
                        showSnack();
                    } else {
                        ArrayList<String> UserName_2 = db.getterritoryname();
                        String user = UserName_2.toString();
                        Intent i = new Intent(Dashboard.this, Report.class);
                        i.putExtra("UserName", globalmpocode);
                        i.putExtra("UserName_2", globalterritorycode);
                        i.putExtra("new_version", Login.version);
                        i.putExtra("message_3", message_3);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            backthred.start();
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, Report.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", Login.version);
                                i.putExtra("message_3", message_3);
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, Report.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", Login.version);
                                i.putExtra("message_3", message_3);
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, Report.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", Login.version);
                                i.putExtra("message_3", message_3);
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

    private void msdDocSupport() {
        cardview_msd.setOnClickListener(v -> showBottomSheetDialog_MSD());
        img_btn_msd.setOnClickListener(v -> showBottomSheetDialog_MSD());
        btn_msd.setOnClickListener(v -> showBottomSheetDialog_MSD());
        tv_msd.setOnClickListener(v -> showBottomSheetDialog_MSD());
    }

    private void pmdContactNew() {
        Intent i = new Intent(Dashboard.this, Activity_PMD_Contact.class);
        i.putExtra("UserName", globalmpocode);
        i.putExtra("UserName_2", globalterritorycode);
        i.putExtra("new_version", Login.version);
        i.putExtra("message_3", message_3);
        startActivity(i);
    }

    private void pmdContact() {
        cardview_pmd_contact.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, Activity_PMD_Contact.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            startActivity(i);
        });
        img_pmd_contact.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, Activity_PMD_Contact.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            startActivity(i);
        });
        btn_pmd_contact.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, Activity_PMD_Contact.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            startActivity(i);
        });
        tv_pmd_contact.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, Activity_PMD_Contact.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            startActivity(i);
        });
    }

    private void doctorListInfoNew() {
        Intent i = new Intent(Dashboard.this, DoctorListActivity.class);
        i.putExtra("UserName", globalmpocode);
        i.putExtra("UserName_2", globalterritorycode);
        i.putExtra("new_version", Login.version);
        i.putExtra("message_3", message_3);
        startActivity(i);
    }

    private void doctorListInfo() {
        cardview_doctor_list.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DoctorListActivity.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            startActivity(i);
        });
        img_doctor_list.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DoctorListActivity.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            startActivity(i);
        });
        btn_doctor_list.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DoctorListActivity.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            startActivity(i);
        });
        tv_doctor_list.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, DoctorListActivity.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", globalterritorycode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            startActivity(i);
        });
    }

    private void chemistListEvent() {
        cardView_chemistList.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, ChemistListActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", globalmpocode);
            i.putExtra("TerriCode", userName);
            startActivity(i);
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, FieldFeedBack.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "MPO");
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

    private void mrcExamEventNew() {
        Thread backthred = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!NetInfo.isOnline(getBaseContext())) {
                        showSnack();
                    } else {
                        Intent i = new Intent(Dashboard.this, ExamResultFollowup.class);
                        i.putExtra("mpo_code", Dashboard.globalmpocode);
                        i.putExtra("territory_name", Dashboard.globalterritorycode);
                        i.putExtra("user_flag", new_version);
                        i.putExtra("message_3", message_3);
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
                                Intent i = new Intent(Dashboard.this, ExamResultFollowup.class);
                                i.putExtra("mpo_code", Dashboard.globalmpocode);
                                i.putExtra("territory_name", Dashboard.globalterritorycode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
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

        img_btn_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(Dashboard.this, ExamResultFollowup.class);
                                i.putExtra("mpo_code", Dashboard.globalmpocode);
                                i.putExtra("territory_name", Dashboard.globalterritorycode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
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

        btn_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // new FetchExamFlag().execute();
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(Dashboard.this, ExamResultFollowup.class);
                                i.putExtra("mpo_code", Dashboard.globalmpocode);
                                i.putExtra("territory_name", Dashboard.globalterritorycode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
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

        tv_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                Intent i = new Intent(Dashboard.this, ExamResultFollowup.class);
                                i.putExtra("mpo_code", Dashboard.globalmpocode);
                                i.putExtra("territory_name", Dashboard.globalterritorycode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
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

    private void dexterPermission(Context context, String... permissions) {
        Dexter.withContext(this)
                .withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!report.areAllPermissionsGranted()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this, R.style.Theme_Design_BottomSheetDialog);
                            builder.setTitle("App Require Location").setMessage("All permission must be Granted")
                                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Thread server = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dexterPermission(Dashboard.this);
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
                                            Intent logoutIntent = new Intent(Dashboard.this, Login.class);
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

    private void userLog(final String key) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.userData(key, vector_version, vectorToken, Dashboard.track_lat, Dashboard.track_lang, build_model, build_brand, Dashboard.globalmpocode, Dashboard.track_add, globalempCode);
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

    private void userLogIn(String loc_name) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.userLogIn(globalempCode, globalmpocode, vector_version, track_lat, track_lang, build_model, build_brand, globalmpocode, track_add, os_version);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                assert response.body() != null;
                int success = response.body().getSuccess();
                String message = response.body().getMassage();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {}
        });
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

    private PendingIntent getPendingIntent() {
        Log.d("locationAdd", "getPendingIntent called!");
        Intent myIntent = new Intent(this, MyLocationService.class);
        myIntent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        //return PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        //return PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
        } else {
            return PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(200);
        locationRequest.setSmallestDisplacement(10f);
        Log.d("loca-->", locationRequest.toString());
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(Dashboard.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            track_add = obj.getAddressLine(0);
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

    public void getDeviceDetails() {
        build_version = Build.VERSION.RELEASE;
        build_model = Build.MODEL;
        build_device = Build.DEVICE;
        build_brand = Build.BRAND;
        build_id = Build.ID;
        os_version = String.valueOf(Build.VERSION.RELEASE);
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public void getDeviceSimNumber() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            ActivityCompat.requestPermissions(Dashboard.this, new String[]{Manifest.permission.READ_PHONE_NUMBERS}, PHONE_NUMBER_CODE);
        } else {
            TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            phoneNumber = tMgr.getLine1Number();
        }

        if (ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this, Manifest.permission.READ_PHONE_NUMBERS);
        } else {
            SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                phoneNumber = subscriptionManager.getPhoneNumber(0);
                Log.d("phoneNumber", phoneNumber);
            }
            List<SubscriptionInfo> subsInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            Log.d("Test", "Current list = " + subsInfoList);

            for (SubscriptionInfo subscriptionInfo : subsInfoList) {
                String number = subscriptionInfo.getNumber();
                Log.d("phoneNumber", " Number is  " + number);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHONE_NUMBER_CODE) {
            //String phoneNumber = data.
            Log.d("phoneNumber1", String.valueOf(data));
        }
    }

    private void firebaseEvent() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Dashboard.this, instanceIdResult -> {
            vectorToken = instanceIdResult.getToken();
            Log.d("vectorToken-->", vectorToken);

            if (vectorToken != null) {
                //userLog(log_status);
            }
        });

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
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                }
            }
        };
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

    private void showSnack() {
        new Thread(() -> Dashboard.this.runOnUiThread(() -> {
            String message;
            message = "No internet Connection, Please Check Your Connection";
            Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
        })).start();
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        (dialog, id) -> {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                (dialog, id) -> dialog.cancel());
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {}

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifeCycle", "onResume called!");
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
        Log.d("lifeCycle", "onPause called!");
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
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
        Log.d("lifeCycle", "onDestroy called!");
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
        preferenceManager.setAdmin_Code(global_admin_Code);
        Log.e("onDestroy----->", global_admin_Code);
        //updateLocation();
    }

    @Override
    protected void onStop() {
        //updateLocation();
        super.onStop();
        Log.d("lifeCycle", "onStop called!");
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
                    //Toast.makeText(Dashboard.this, "You are locked...", Toast.LENGTH_LONG).show();
                    Toast.makeText(Dashboard.this, message, Toast.LENGTH_LONG).show();
                    log_status = "N";
                    preferenceManager.clearPreferences();
                    count = 0;
                    Intent logoutIntent = new Intent(Dashboard.this, Login.class);
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
