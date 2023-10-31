package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.opl.pharmavector.achieve.AchieveEarnActivity;
import com.opl.pharmavector.app.Config;
import com.opl.pharmavector.contact.Activity_PMD_Contact;
import com.opl.pharmavector.dcfpFollowup.DcfpFollowupActivity;
import com.opl.pharmavector.doctorList.DoctorListActivity;
import com.opl.pharmavector.doctorservice.DoctorServiceTrackMonthly;
import com.opl.pharmavector.doctorservice.ManagerDoctorServiceFollowup;
import com.opl.pharmavector.master_code.MasterCode;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.msd_doc_support.DocSupportFollowup;
import com.opl.pharmavector.msd_doc_support.MSDCommitmentFollowup;
import com.opl.pharmavector.msd_doc_support.MSDProgramApproval;
import com.opl.pharmavector.msd_doc_support.MSDProgramFollowup;
import com.opl.pharmavector.pcconference.PcApproval;
import com.opl.pharmavector.pcconference.PcConferenceFollowup;
import com.opl.pharmavector.pmdVector.ff_contact.ff_contact_activity;
import com.opl.pharmavector.prescriber.TopPrescriberActivity;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup2;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.ImageLoadActivity;
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
import java.util.List;

import android.app.ProgressDialog;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.opl.pharmavector.util.VectorUtils;

public class GMDashboard1 extends Activity implements View.OnClickListener { // Activity -> (AppCompatActivity) replaced by me
    public String userName_1, userName, userName_2, UserName_2, user, sm_code, gm_code, global_admin_Code, global_admin_name, global_admin_terri;
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
    public TextView user_show1, user_show2, sm, versionname;
    private SessionManager session;
    private final String submit_url = BASE_URL + "notification/save_vector_notification_token_data_test.php";
    public String message, currentVersion;
    public int success;
    public String tokenid, globalterritorycode;
    public static String IMEINumber = "0000", DeviceID = "XXXX";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private static final int REQUEST_CODE = 101;
    public static String vectorToken, globalAdmin, globalAdminDtl;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    PreferenceManager preferenceManager;
    private int count;
    public static String ff_type, password, globalempCode, globalempName, new_version, message_3, vector_version, globalmpocode, os_version,
            build_model, build_brand, build_id, build_device, build_version;
    Typeface fontFamily;
    CardView cardview_dcr, practiceCard2, practiceCard3, practiceCard6, cardview_doctor_list, cardView_prescriber, cardview_achv_earn,
            practiceCard7, practiceCard8, cardview_pc, cardview_salereports, cardview_msd, cardview_salesfollowup, cardview_mastercode, cardview_pmd_contact, cardview_ff_contact;
    ImageButton profileB, img_btn_dcr, img_btn_dcc, img_btn_productorder, img_btn_docservice,
            img_btn_notification, img_btn_rx, img_btn_pc, img_btn_salereports, img_btn_msd, img_btn_salesfollowup, img_btn_mastercode, img_pmd_contact, img_doctor_list;
    TextView tv_dcr, tv_productorder, tv_dcc, tv_docservice,
            tv_notification, tv_rx, tv_pc, tv_salereports, tv_msd, tv_salesfollowup, tv_mastercode, tv_pmd_contact, tv_doctor_list;
    Button btn_dcr, btn_productorder, btn_dcc, btn_docservice, btn_notification, btn_rx, btn_pc, btn_salereports, btn_msd, btn_salesfollowup, btn_vector_feedback,
            btn_mastercode, btn_pmd_contact, btn_doctor_list;
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
    private String log_status = "A";
    final int NOTIFICATION_PERMISSION_CODE = 101;

    @SuppressLint("CutPasteId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_gm_dashboard);

        VectorUtils.screenShotProtect(this);
        isAddressSubmit = true;
        CardView cardView = findViewById(R.id.cardView2);
        cardView.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 33) {
                askForNotificationPermission();
                Log.d("notification33", "clicked!");
            }
        });
        preferenceManager = new PreferenceManager(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        count = preferenceManager.getTasbihCounter();
        statusBarHide();
        initViews();
        initBroadcastReceiver();
        registerReceiver(updateUIReciver, new IntentFilter(MyLocationService.ACTION_PROCESS_UPDATE));

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(GMDashboard1.this, instanceIdResult -> vectorToken = instanceIdResult.getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("vector")
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed) + vectorToken;
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                });
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                }
            }
        };
        getDeviceDetails();
        updateLocation();
        dcrfollowup();
        docservicefollowup();
        msdDocSupport();
        masterCode();
        noticeBoradEvent();
        salesFollowup();
        managersreport();
        pcConferenceEvent();
        prescriptionentry();
        pmdContact();
        doctorListInfo();
        topPrescriberEvent();
        achievementEarnEvent();

        PackageManager pm = getApplicationContext().getPackageManager();
        String pkgName = getApplicationContext().getPackageName();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        vector_version = pkgInfo.versionName;
        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GMDashboard1.this, R.style.Theme_Design_BottomSheetDialog);
            builder.setTitle("Exit !").setMessage("Are you sure you want to exit Vector?")
                    .setPositiveButton("Yes", (dialog, which) -> {
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
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                    })
                    .show();
        });
        btn_vector_feedback.setOnClickListener(v -> FeedbackshowSnack());
        autoLogout();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED /*||
            checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {

            AlertDialog.Builder builder = new AlertDialog.Builder(GMDashboard1.this, R.style.Theme_Design_BottomSheetDialog);
            builder.setTitle("App Require Location").setMessage("This app collects location data to enable Doctor Chamber Location Feature even when app is running")
                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Thread server = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                        dexterPermission(GMDashboard1.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                                    } else {
                                        dexterPermission(GMDashboard1.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
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
                            Intent logoutIntent = new Intent(GMDashboard1.this, Login.class);
                            startActivity(logoutIntent);
                            finish();

                        }
                    })
                    .show();
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
                //userLog(log_status);
            }
        };
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(GMDashboard1.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            track_add = obj.getAddressLine(0);
            //track_add = track_add + "\n" + obj.getCountryName();
            //track_add = track_add + "\n" + obj.getCountryCode();
            tvLocationName.setText(track_add);
            //userLog(log_status);
            if (isAddressSubmit) {
                userLogIn(track_add);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(GMDashboard1.this, R.style.Theme_Design_BottomSheetDialog);
                            builder.setTitle("App Require Location").setMessage("All permission must be Granted")
                                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Thread server = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dexterPermission(GMDashboard1.this);
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
                                            Intent logoutIntent = new Intent(GMDashboard1.this, Login.class);
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
            Objects.requireNonNull(button1).setText("14.1");
            Objects.requireNonNull(button2).setText("14.2");
            Objects.requireNonNull(button3).setText("14.3");
            Objects.requireNonNull(textView4).setText("SPI Top Prescriber\n(Generic)");
            Objects.requireNonNull(textView5).setText("SPI \nReport");
            Objects.requireNonNull(textView6).setText("Doctor \nReach");
            Objects.requireNonNull(changePassword).setText(R.string.spiReport);
            ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
            Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
            Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
            bottomSheetDialog.show();

            Objects.requireNonNull(cardView_topPrescriber).setOnClickListener(v -> {
                Intent i = new Intent(GMDashboard1.this, TopPrescriberActivity.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", globalempCode);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                i.putExtra("UserRole", "AD");
                startActivity(i);
            });
            Objects.requireNonNull(cardView_spiReport).setOnClickListener(v -> {
                Intent i = new Intent(GMDashboard1.this, MRDPresReport.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", globalempCode);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                i.putExtra("UserRole", "AD");
                i.putExtra("report_flag", "SPI");
                i.putExtra("asm_flag", "N");
                i.putExtra("sm_flag", "N");
                i.putExtra("gm_flag", "Y");
                startActivity(i);
            });
        });
    }

    private void achievementEarnEvent() {
        cardview_achv_earn.setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, AchieveEarnActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", globalempCode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "AD");
            startActivity(i);
        });
    }

    public void getNotificationPermission() {
        try {
            if (Build.VERSION.SDK_INT > 32) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
            }
        } catch (Exception ignored) {

        }
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Permission is not granted", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SupportAnnotationUsage")
    private void askForNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            //requestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }

    private void autoLogout() {
        if (preferenceManager.getexecutive_name().equals("PMD") || preferenceManager.getexecutive_name().equals("Vector") || preferenceManager.getexecutive_name().equals(" ")) {
            log_status = "N";
            preferenceManager.clearPreferences();
            count = 0;
            Intent logoutIntent = new Intent(GMDashboard1.this, Login.class);
            startActivity(logoutIntent);
            finish();
        }
    }

    private void statusBarHide() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void initViews() {
        logout = findViewById(R.id.logout);
        user_show1 = findViewById(R.id.user_show1);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        imageView2 = findViewById(R.id.imageView2);
        logo_team = findViewById(R.id.logo_team);
        tvLocationName = findViewById(R.id.location_name);
        btn_vector_feedback = findViewById(R.id.btn_vector_feedback);

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

        btn_notification = findViewById(R.id.btn_notification);
        img_btn_notification = findViewById(R.id.img_btn_notification);
        tv_notification = findViewById(R.id.tv_notification);
        practiceCard6 = findViewById(R.id.practiceCard6);

        btn_rx = findViewById(R.id.btn_rx);
        img_btn_rx = findViewById(R.id.img_btn_rx);
        tv_rx = findViewById(R.id.tv_rx);
        practiceCard8 = findViewById(R.id.practiceCard8);

        btn_pc = findViewById(R.id.btn_pc);
        img_btn_pc = findViewById(R.id.img_btn_pc);
        tv_pc = findViewById(R.id.tv_pc);
        cardview_pc = findViewById(R.id.cardview_pc);

        btn_salesfollowup = findViewById(R.id.btn_salesfollowup);
        img_btn_salesfollowup = findViewById(R.id.img_btn_salesfollowup);
        tv_salesfollowup = findViewById(R.id.tv_salesfollowup);
        cardview_salesfollowup = findViewById(R.id.cardview_salesfollowup);

        btn_salereports = findViewById(R.id.btn_salereports);
        img_btn_salereports = findViewById(R.id.img_btn_salereports);
        tv_salereports = findViewById(R.id.tv_salereports);
        cardview_salereports = findViewById(R.id.cardview_salereports);

        btn_msd = findViewById(R.id.btn_msd);
        img_btn_msd = findViewById(R.id.img_btn_msd);
        tv_msd = findViewById(R.id.tv_msd);
        cardview_msd = findViewById(R.id.cardview_msd);

        btn_mastercode = findViewById(R.id.btn_mastercode);
        img_btn_mastercode = findViewById(R.id.img_btn_mastercode);
        tv_mastercode = findViewById(R.id.tv_mastercode);
        cardview_mastercode = findViewById(R.id.cardview_mastercode);

        btn_pmd_contact = findViewById(R.id.btn_pmd_contact);
        img_pmd_contact = findViewById(R.id.img_pmd_contact);
        tv_pmd_contact = findViewById(R.id.tv_pmd_contact);
        cardview_pmd_contact = findViewById(R.id.cardview_pmd_contact);
        cardview_ff_contact = findViewById(R.id.cardview_ff_contact);
        cardView_prescriber = findViewById(R.id.cardView_prescriber);
        cardview_achv_earn = findViewById(R.id.cardview_achv_earn);

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
        UserName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
        password = b.getString("password");
        ff_type = b.getString("ff_type");
        vector_version = String.valueOf(R.string.vector_version);
        globalempCode = b.getString("emp_code");
        globalempName = b.getString("emp_name");
        global_admin_Code = preferenceManager.getAdmin_Code();
        global_admin_name = preferenceManager.getAdmin_Name();
        global_admin_terri = preferenceManager.getAdmin_Terri();
        Log.d("UserCode", global_admin_Code + "-----------" + global_admin_name + "-----------" + global_admin_terri);

        //user_show1.setText(globalempName);
        user_show1.setText(global_admin_name);
        profile_image = base_url + global_admin_Code + "." + "jpg";
        Picasso.get().load(profile_image).into(imageView2);
        db = new DatabaseHandler(this);
        ArrayList<String> mpo_code_interna = db.getterritoryname();
        String mpo_code_i = mpo_code_interna.toString();
        globalAdmin = userName;
        globalAdminDtl = UserName_2;
        t4.setText(global_admin_Code);
        t5.setText(global_admin_terri);
        tvDesignation.setText(preferenceManager.getDesignation());

        versionname = findViewById(R.id.versionname);
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

    private void dcrfollowup() {
        cardview_dcr.setOnClickListener(v -> {
            showBottomSheetDialog_DCR();
//            Thread backthred = new Thread(() -> {
//                Bundle b = getIntent().getExtras();
//                String userName = b.getString("UserName");
//                String userName_1 = b.getString("UserName_1");
//                String userName_2 = b.getString("UserName_2");
//                try {
//                    if (!NetInfo.isOnline(getBaseContext())) {
//                       showSnack();
//                    } else {
//                        Intent i = new Intent(GMDashboard1.this, GMDashboard.class);
//                        String gm_flag = "Y";
//                        i.putExtra("sm_code", globalAdmin);
//                        i.putExtra("UserName", userName);
//                        i.putExtra("userName_1", userName_1);
//                        i.putExtra("userName_2", userName_2);
//                        i.putExtra("UserName", userName);
//                        i.putExtra("UserName_2", user);
//                        i.putExtra("gm_flag", gm_flag);
//                        startActivity(i);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//            backthred.start();
        });
    }

    private void showBottomSheetDialog_DCR() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dcr_bottom_sheet_dialog);
        CardView cardview_followup = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_report = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("1.1");
        Objects.requireNonNull(button2).setText("1.2");
        Objects.requireNonNull(textView4).setText("Dcr\nFollowup");
        Objects.requireNonNull(textView5).setText("DCFP\nFollowup");
        Objects.requireNonNull(changepassword).setText(R.string.dailycallreport);
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);

        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        Objects.requireNonNull(cardview_followup).setOnClickListener(v -> {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String userName_1 = b.getString("UserName_1");
            String userName_2 = b.getString("UserName_2");
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
        });
        Objects.requireNonNull(cardview_report).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, DcfpFollowupActivity.class);
            i.putExtra("UserName", globalmpocode);
            i.putExtra("UserName_2", userName_2);
            i.putExtra("UserName_3", globalAdmin);
            startActivity(i);
        });
        bottomSheetDialog.setOnDismissListener(dialog -> {
            //Toast.makeText(getApplicationContext(), "bottomSheetDialog is Dismissed ", Toast.LENGTH_LONG).show();
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
        Objects.requireNonNull(cardview3).setVisibility(View.GONE);
        Objects.requireNonNull(cardview4).setVisibility(View.GONE);
        TextView textView4 = bottomSheetDialog2.findViewById(R.id.textView4);
        Objects.requireNonNull(textView4).setText("Tracking\nDoctor");
        ImageView imageView3 = bottomSheetDialog2.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_doctor_service);
        TextView changepassword = bottomSheetDialog2.findViewById(R.id.changepassword);
        Objects.requireNonNull(changepassword).setText("Doctor Service");

        Objects.requireNonNull(cardview2).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, ManagerDoctorServiceFollowup.class);
            i.putExtra("userName", globalAdmin);
            i.putExtra("UserName_2", globalAdminDtl);
            i.putExtra("new_version", new_version);
            i.putExtra("user_flag", "G");
            startActivity(i);
            //bottomSheetDialog2.dismiss();
        });
        Objects.requireNonNull(cardview1).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, DoctorServiceTrackMonthly.class);
            i.putExtra("userName", globalAdmin);
            i.putExtra("UserName_2", globalAdminDtl);
            i.putExtra("new_version", new_version);
            i.putExtra("user_flag", "G");
            startActivity(i);
            bottomSheetDialog2.dismiss();
        });
        bottomSheetDialog2.show();
    }

    private void docservicefollowup() {
        practiceCard3.setOnClickListener(v -> showBottomSheetDialog_DOCSUPPORT());
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
        Objects.requireNonNull(button1).setText("4.1");
        Objects.requireNonNull(button2).setText("4.2");
        Objects.requireNonNull(button3).setText("4.3");
        Objects.requireNonNull(btn_commitment_followup).setText("4.4");
        Objects.requireNonNull(textView4).setText("MSD\nProgram Follow up");
        Objects.requireNonNull(textView5).setText("Doctor\nSupport Follow up");
        Objects.requireNonNull(textView6).setText("MSD\nProgram Approval");
        Objects.requireNonNull(tv_commitment_followup).setText("MSD Program\nCommitment Follow-up");

        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_doctor_service);
        Objects.requireNonNull(btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(changepassword).setText("MSD");
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, MSDProgramFollowup.class);
            i.putExtra("user_code", GMDashboard1.globalAdmin);
            i.putExtra("user_name", GMDashboard1.globalAdminDtl);
            i.putExtra("user_flag", "GM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, DocSupportFollowup.class);
            i.putExtra("user_code", GMDashboard1.globalAdmin);
            i.putExtra("user_name", GMDashboard1.globalAdminDtl);
            i.putExtra("user_flag", "GM");
            startActivity(i);
            //bottomSheetDialog.dismiss();
        });
        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, MSDProgramApproval.class);
            i.putExtra("user_code", GMDashboard1.globalAdmin);
            i.putExtra("user_name", GMDashboard1.globalAdminDtl);
            i.putExtra("user_flag", "GM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_commitment_followup).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, MSDCommitmentFollowup.class);
            i.putExtra("user_code", GMDashboard1.globalAdmin);
            i.putExtra("user_name", GMDashboard1.globalAdminDtl);
            i.putExtra("user_flag", "GM");
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

    private void masterCode() {
        cardview_mastercode.setOnClickListener(v -> {
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
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
        });

        btn_mastercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                                Toast.makeText(getApplicationContext(), "Check Internet connection", Toast.LENGTH_LONG).show();
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
                                ShortcutBadger.applyCount(getBaseContext(), 0);
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
        tv_notification.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
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
            });
            backthred.start();
        });
        btn_notification.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
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
            });
            backthred.start();
        });
    }

    private void managersreport() {
        cardview_salereports.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
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
            });
            backthred.start();
        });

        tv_salereports.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
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
            });
            backthred.start();
        });

        img_btn_salereports.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
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
            });
            backthred.start();
        });

        btn_salereports.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
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
            });
            backthred.start();
        });

        cardview_ff_contact.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
                try {
                    if (!NetInfo.isOnline(getBaseContext())) {

                    } else {
                        Intent i = new Intent(GMDashboard1.this, ff_contact_activity.class);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            backthred.start();
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
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_pc_conference);
        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog2.dismiss());
        Objects.requireNonNull(changepassword).setText("PC Conference");
        Objects.requireNonNull(cardview1).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, PcApproval.class);
            i.putExtra("userName", globalAdmin);
            i.putExtra("UserName_2", globalAdminDtl);
            i.putExtra("new_version", R.string.vector_version);
            i.putExtra("UserName", globalAdmin);
            Log.e("globalAdmin==>", globalAdmin);
            startActivity(i);
        });
        Objects.requireNonNull(cardview2).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, PcConferenceFollowup.class);
            i.putExtra("UserName", globalAdmin);
            i.putExtra("UserName_2", globalAdminDtl);
            i.putExtra("user_flag", "GM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview3).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, PcBillApproval.class);
            i.putExtra("userName", globalAdmin);
            i.putExtra("UserName_2", globalAdminDtl);
            i.putExtra("new_version", new_version);
            i.putExtra("userName", globalAdmin);
            startActivity(i);
        });
        Objects.requireNonNull(cardview4).setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, PCBillFollowup.class);
            i.putExtra("UserName", globalAdmin);
            i.putExtra("UserName_2", globalAdminDtl);
            i.putExtra("user_flag", "GM");
            startActivity(i);
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
                i.putExtra("manager_code", GMDashboard1.globalAdmin);
                i.putExtra("manager_detail", GMDashboard1.globalAdminDtl);
                i.putExtra("manager_flag", "AD");
                startActivity(i);
            }
        });
        Objects.requireNonNull(cardview3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, PrescriptionFollowup.class);
                i.putExtra("manager_code", GMDashboard1.globalAdmin);
                i.putExtra("manager_detail", "AD");
                startActivity(i);
                //bottomSheetDialog2.dismiss();
            }
        });
        Objects.requireNonNull(cardview4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GMDashboard1.this, PrescriptionFollowup2.class);
                i.putExtra("manager_code", GMDashboard1.globalAdmin);
                i.putExtra("manager_detail", "AD");
                startActivity(i);
            }
        });
        bottomSheetDialog2.show();
    }

    private void prescriptionentry() {
        practiceCard8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_RXCAPTURE();
                //startActivity(i);
            }
        });
    }

    private void pmdContact() {
        cardview_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(GMDashboard1.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        img_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(GMDashboard1.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        btn_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(GMDashboard1.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        tv_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(GMDashboard1.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", globalAdmin);
                i.putExtra("UserName_2", globalAdminDtl);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
    }

    private void doctorListInfo() {
        cardview_doctor_list.setOnClickListener(v -> {
            Intent i = new Intent(GMDashboard1.this, DoctorListActivity.class);
            i.putExtra("UserName", global_admin_Code);
            i.putExtra("UserName_2", global_admin_name);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            startActivity(i);
        });
        img_doctor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(GMDashboard1.this, DoctorListActivity.class);
                i.putExtra("UserName", global_admin_Code);
                i.putExtra("UserName_2", global_admin_name);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        btn_doctor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(GMDashboard1.this, DoctorListActivity.class);
                i.putExtra("UserName", global_admin_Code);
                i.putExtra("UserName_2", global_admin_name);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
        tv_doctor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(GMDashboard1.this, DoctorListActivity.class);
                i.putExtra("UserName", global_admin_Code);
                i.putExtra("UserName_2", global_admin_name);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
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
        Log.e("onResume------->", global_admin_Code);
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
        preferenceManager.setAdmin_Code(global_admin_Code);
        preferenceManager.setAdmin_Name(global_admin_name);
        preferenceManager.setAdmin_Terri(global_admin_terri);
        Log.e("Destroy----->", userName);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
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
                    //Toast.makeText(GMDashboard1.this, "You are locked...", Toast.LENGTH_LONG).show();
                    Toast.makeText(GMDashboard1.this, message, Toast.LENGTH_LONG).show();
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


