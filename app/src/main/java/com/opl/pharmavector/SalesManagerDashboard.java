package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL_RM;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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
import com.opl.pharmavector.liveDepot.ADSStockPMDActivity;
import com.opl.pharmavector.liveDepot.LiveDepotStockActivity;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mpodcr.entry.DcrEntryActivity;
import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.msd_doc_support.DocSupportFollowup;
import com.opl.pharmavector.msd_doc_support.MSDCommitmentFollowup;
import com.opl.pharmavector.msd_doc_support.MSDProgramApproval;
import com.opl.pharmavector.msd_doc_support.MSDProgramFollowup;
import com.opl.pharmavector.pcconference.PcApproval;
import com.opl.pharmavector.pcconference.PcConferenceFollowup;
import com.opl.pharmavector.pmdVector.ff_contact.ff_contact_activity;
import com.opl.pharmavector.prescriber.TopPrescriberActivity;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionDashboard;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup;
import com.opl.pharmavector.prescriptionsurvey.RxSummaryMISActivity;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.ImageLoadActivity;
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

import android.app.ProgressDialog;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

public class SalesManagerDashboard extends Activity implements View.OnClickListener {
    public String userName_1, userName, terriName, designation, userName_2, user, sm_code, global_admin_Code;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public AutoCompleteTextView actv;
    private ListView lv, lv2;
    private ProgressDialog pDialog;
    private String log_status = "A";
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
    public TextView user_show1, user_show2, sm, versionname;
    private SessionManager session;
    private String submit_url = BASE_URL+"notification/save_vector_notification_token_data_test.php";
    public String message, currentVersion;
    public int success;
    public String tokenid;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static String IMEINumber = "0000", DeviceID = "XXXX";
    private static final int REQUEST_CODE = 101;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Button followup_report, sales_report, managers_report, notification, am_dcc_camp, pc_conference_btn, doctor_service_followup, prescription_entry,btn_vacant_rm, btn_pmd_contact,
    btn_msd_doctor_support;
    ProgressBar bar_11, bar_2, bar_21, bar_22, bar_15, bar_16, bar_prescription, bar_10, bar_vacant_rm, bar_msd_doctor_support;
    String UserName_2, mpo_code_i;
    Bundle b;
    Typeface fontFamily;
    ArrayList<String> mpo_code_interna;
    PreferenceManager preferenceManager;
    private int count;
    public static String globalempCode, globalempName, build_model, build_brand, build_id, build_device, build_version, os_version;
    CardView cardview_dcr, practiceCard2, practiceCard3, practiceCard4, practiceCard5, practiceCard6, cardview_doctor_list, cardview_ff_contact, cardView_prescriber, cardview_achv_earn,
             practiceCard7, practiceCard8, practiceCard9, cardview_pc, cardview_promomat, cardview_salereports, cardview_msd, cardview_salesfollowup, cardview_mastercode,
             cardview_pmd_contact, cardView_productStock, cardView_tourPlan, cardView_chemist_list;
    ImageButton profileB, img_btn_dcr, img_btn_dcc, img_btn_productorder, img_btn_docservice, img_btn_docgiftfeedback,
             img_btn_notification, img_btn_rx, img_btn_personalexpense, img_btn_pc, img_btn_promomat, img_btn_salereports, img_btn_msd, img_btn_exam, img_btn_salesfollowup,
             img_btn_mastercode, img_pmd_contact, img_doctor_list;
    TextView tv_dcr, tv_productorder, tv_dcc, tv_docservice, tv_docgiftfeedback, tv_doctor_list,
             tv_notification, tv_rx, tv_personalexpense, tv_pc, tv_promomat, tv_salereports, tv_msd,tv_exam, tv_salesfollowup, tv_mastercode, tv_pmd_contact;
    Button btn_dcr,btn_productorder,btn_dcc,btn_docservice, btn_doctor_list,
            btn_docgiftfeedback,btn_notification,btn_rx,btn_personalexpense,btn_pc,btn_promomat,btn_salereports,btn_msd,btn_exam,btn_vector_feedback,btn_mastercode,btn_salesfollowup;
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
    public String base_url = ApiClient.BASE_URL+"vector_ff_image/";
    public String get_ext_dt, date_flag, check_flag;
    public static String password, vectorToken, globalSMCode, globalDivisionCode, message_3, new_version, vector_version, ff_type;

    @SuppressLint("CutPasteId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.assistantmanager);
        setContentView(R.layout.activity_vector_asm_dashboard);

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
        global_admin_Code = preferenceManager.getAdmin_Code();
        Log.e("Admin Code--->", preferenceManager.getAdmin_Code());

        getDeviceDetails();
        updateLocation();
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
        doctorListInfo();
        achieveEarnEvent();
        productStockEvent();
        topPrescriberEvent();
        monthlyTourPlanEvent();
        chemistListEvent();

        PackageManager pm = getApplicationContext().getPackageManager();
        String pkgName = getApplicationContext().getPackageName();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        vector_version = pkgInfo.versionName;
        cardview_ff_contact.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
                try {
                    if (!NetInfo.isOnline(getBaseContext())) {

                    } else {
                        Intent i = new Intent(SalesManagerDashboard.this, ff_contact_activity.class);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            backthred.start();
        });
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
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED /*||
            checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {

            AlertDialog.Builder builder = new AlertDialog.Builder(SalesManagerDashboard.this, R.style.Theme_Design_BottomSheetDialog);
            builder.setTitle("App Require Location").setMessage("This app collects location data to enable Doctor Chamber Location Feature even when app is running")
                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Thread server = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                        dexterPermission(SalesManagerDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                                    } else {
                                        dexterPermission(SalesManagerDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
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
                            Intent logoutIntent = new Intent(SalesManagerDashboard.this, Login.class);
                            startActivity(logoutIntent);
                            finish();

                        }
                    })
                    .show();
        }
        userLogIn(track_add);
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
        Geocoder geocoder = new Geocoder(SalesManagerDashboard.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            track_add = obj.getAddressLine(0);
            tvLocationName.setText(track_add);
            //track_add = track_add + "\n" + obj.getCountryName();
            //track_add = track_add + "\n" + obj.getCountryCode();
            //userLog(log_status);

            if (isAddressSubmit) {
                //userLogIn(track_add);
                isAddressSubmit = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void isUpdateAvailable() {
        AppUpdateManager mAppUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SalesManagerDashboard.this);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(SalesManagerDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                            builder.setTitle("App Require Location").setMessage("All permission must be Granted")
                                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Thread server = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dexterPermission(SalesManagerDashboard.this);
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
                                            Intent logoutIntent = new Intent(SalesManagerDashboard.this, Login.class);
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
        Objects.requireNonNull(button1).setText("16.1");
        Objects.requireNonNull(button2).setText("16.2");
        Objects.requireNonNull(button3).setText("16.3");
        Objects.requireNonNull(textView4).setText("ADS - PMD");
        Objects.requireNonNull(textView5).setText("Daily\nLive Stock");
        Objects.requireNonNull(textView6).setText("Doctor \nReach");
        Objects.requireNonNull(changePassword).setText("Product Stock");
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();

        Objects.requireNonNull(cardView_adsStock).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, ADSStockPMDActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", globalSMCode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "SM");
            i.putExtra("report_flag", "SPI");
            i.putExtra("asm_flag", "N");
            i.putExtra("sm_flag", "Y");
            i.putExtra("gm_flag", "N");
            i.putExtra("rm_flag", "N");
            i.putExtra("fm_flag", "N");
            i.putExtra("mpo_flag", "N");
            startActivity(i);
        });
        Objects.requireNonNull(cardView_dailyStock).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, LiveDepotStockActivity.class);
            i.putExtra("userName", globalSMCode);
            i.putExtra("userCode", globalempCode);
            i.putExtra("userRole", "SM");
            i.putExtra("asm_flag", "N");
            i.putExtra("sm_flag", "N");
            i.putExtra("gm_flag", "Y");
            i.putExtra("rm_flag", "N");
            i.putExtra("fm_flag", "N");
            i.putExtra("mpo_flag", "N");
            startActivity(i);
        });
        Objects.requireNonNull(cardView_doctorReach).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, DoctorReachActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", globalempCode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "SM");
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
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button button3 = bottomSheetDialog.findViewById(R.id.btn_doctorReach);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("17.1");
        //Objects.requireNonNull(button2).setText("16.2");
        Objects.requireNonNull(button3).setText("17.3");
        Objects.requireNonNull(textView4).setText("Tour\nObservation Entry");
        //Objects.requireNonNull(textView5).setText("SPI \nReport");
        Objects.requireNonNull(textView6).setText("Tour \nPlan Entry");
        Objects.requireNonNull(changePassword).setText("Tour Program");
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();

        Objects.requireNonNull(cardView_tourPlan).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, DcrEntryActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", globalempCode);
            i.putExtra("new_version", Login.version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "AD");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_tourFollow).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, DcfpFollowupActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserName_2", userName_2);
            i.putExtra("UserName_3", globalSMCode);
            i.putExtra("UserRole", "T"); // T -> TOUR
            startActivity(i);
        });
        Objects.requireNonNull(cardView_doctorReach).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, DoctorReachActivity.class);
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
                Intent i = new Intent(SalesManagerDashboard.this, TopPrescriberActivity.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", userName);
                i.putExtra("new_version", new_version);
                i.putExtra("message_3", message_3);
                i.putExtra("UserRole", "SM");
                startActivity(i);
            });
            Objects.requireNonNull(cardView_spiReport).setOnClickListener(v -> {
                Intent i = new Intent(SalesManagerDashboard.this, MRDPresReport.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", globalempCode);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                i.putExtra("UserRole", "SM");
                i.putExtra("report_flag", "SPI");
                i.putExtra("asm_flag", "N");
                i.putExtra("sm_flag", "Y");
                i.putExtra("gm_flag", "N");
                i.putExtra("rm_flag", "N");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "N");
                startActivity(i);
            });
            Objects.requireNonNull(cardView_doctorReach).setOnClickListener(v -> {
                Intent i = new Intent(SalesManagerDashboard.this, DoctorReachActivity.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", globalempCode);
                i.putExtra("new_version", Login.version);
                i.putExtra("message_3", message_3);
                i.putExtra("UserRole", "SM");
                i.putExtra("report_flag", "SPI");
                i.putExtra("asm_flag", "N");
                i.putExtra("sm_flag", "Y");
                i.putExtra("gm_flag", "N");
                i.putExtra("rm_flag", "N");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "N");
                startActivity(i);
            });
        });
    }

    private void achieveEarnEvent() {
        cardview_achv_earn.setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, AchieveEarnActivity.class);
            i.putExtra("UserName", globalempName);
            i.putExtra("UserCode", userName);
            i.putExtra("new_version", new_version);
            i.putExtra("message_3", message_3);
            i.putExtra("UserRole", "SM");
            i.putExtra("TeamCode", ff_type);
            startActivity(i);
        });
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

    private void statusBarHide() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @SuppressLint("CutPasteId")
    private void initViews(){
        logout = findViewById(R.id.logout);
        user_show1 = findViewById(R.id.user_show1);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        tvDesignation = findViewById(R.id.textView3);
        imageView2 = findViewById(R.id.imageView2);
        logo_team = findViewById(R.id.logo_team);
        tvLocationName = findViewById(R.id.location_name);

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
        practiceCard6          = findViewById(R.id.practiceCard6);

        btn_rx        = findViewById(R.id.btn_rx);
        img_btn_rx    = findViewById(R.id.img_btn_rx);
        tv_rx         = findViewById(R.id.tv_rx);
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
        img_btn_promomat   = findViewById(R.id.img_btn_promomat);
        tv_promomat        = findViewById(R.id.tv_promomat);
        cardview_promomat  = findViewById(R.id.cardview_promomat);

        btn_salereports       = findViewById(R.id.btn_salereports);
        img_btn_salereports   = findViewById(R.id.img_btn_salereports);
        tv_salereports        = findViewById(R.id.tv_salereports);
        cardview_salereports  = findViewById(R.id.cardview_salereports);

        btn_msd       = findViewById(R.id.btn_msd);
        img_btn_msd   = findViewById(R.id.img_btn_msd);
        tv_msd        = findViewById(R.id.tv_msd);
        cardview_msd  = findViewById(R.id.cardview_msd);

        btn_salesfollowup       = findViewById(R.id.btn_salesfollowup);
        img_btn_salesfollowup   = findViewById(R.id.img_btn_salesfollowup);
        tv_salesfollowup        = findViewById(R.id.tv_salesfollowup);
        cardview_salesfollowup  = findViewById(R.id.cardview_salesfollowup);

        btn_exam       = findViewById(R.id.btn_exam);
        img_btn_exam   = findViewById(R.id.img_btn_exam);
        tv_exam        = findViewById(R.id.tv_exam);
        practiceCard5  = findViewById(R.id.practiceCard5);

        btn_mastercode       = findViewById(R.id.btn_mastercode);
        img_btn_mastercode   = findViewById(R.id.img_btn_mastercode);
        tv_mastercode        = findViewById(R.id.tv_mastercode);
        cardview_mastercode  = findViewById(R.id.cardview_mastercode);

        btn_pmd_contact      = findViewById(R.id.btn_pmd_contact);
        img_pmd_contact      = findViewById(R.id.img_pmd_contact);
        tv_pmd_contact       = findViewById(R.id.tv_pmd_contact);
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
        btn_vector_feedback  = findViewById(R.id.btn_vector_feedback);

        ff_type        = null;
        Bundle b       = getIntent().getExtras();
        assert b       != null;
        userName       = b.getString("UserName");
        designation    = b.getString("Designation");
        terriName      = b.getString("TerriName");
        UserName_2     = b.getString("UserName_2");
        new_version    = b.getString("new_version");
        message_3      = b.getString("message_3");
        password       = b.getString("password");
        ff_type        = b.getString("ff_type");
        vector_version = b.getString("vector_version");
        globalempCode  = b.getString("emp_code");
        globalempName  = b.getString("emp_name");

        user_show1.setText(globalempName);
        profile_image= base_url+globalempCode+"."+"jpg" ;
        Picasso.get().load(profile_image).into(imageView2);
        globalSMCode = userName;
        globalDivisionCode = UserName_2;
        globalSMCode = userName;
        t4.setText(globalSMCode);
        //t5.setText(globalDivisionCode);
        //tvDesignation.setText(preferenceManager.getDesignation());
        versionname = findViewById(R.id.versionname);

        if (designation != null && terriName != null) {
            tvDesignation.setText(designation);
            t5.setText(terriName);
        } else {
            tvDesignation.setText(preferenceManager.getDesignation());
            t5.setText(globalDivisionCode);
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
                //checking for type intent filter
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
        cardview_dcr.setOnClickListener(v -> {
            showBottomSheetDialog_DCR();
//            Thread backthred = new Thread(() -> {
//                try {
//                    if (!NetInfo.isOnline(getBaseContext())) {
//                        showSnack();
//                    } else {
//                        Intent i = new Intent(SalesManagerDashboard.this, ASMFollowupReport.class);
//                        String sm_flag = "Y";
//                        String admin_flag = "N";
//                        i.putExtra("sm_flag", sm_flag);
//                        i.putExtra("sm_code", globalSMCode);
//                        i.putExtra("UserName", userName);
//                        i.putExtra("userName_1", userName_1);
//                        i.putExtra("userName_2", userName_2);
//                        i.putExtra("UserName", userName);
//                        i.putExtra("UserName_2", user);
//                        i.putExtra("admin_flag", admin_flag);
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
        bottomSheetDialog.setContentView(R.layout.dcr_asm_bottom_sheet_dialog);
        CardView cardview_followup = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_report = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        CardView cardview_dcfpDocList = bottomSheetDialog.findViewById(R.id.cardview_dcfp_docList);
        CardView cardview_dcrEntry = bottomSheetDialog.findViewById(R.id.cardview_dcr_entry);
        Objects.requireNonNull(cardview_dcrEntry).setVisibility(View.GONE);
        CardView cardview_tourFollow = bottomSheetDialog.findViewById(R.id.cardview_tour_follow);
        Objects.requireNonNull(cardview_tourFollow).setVisibility(View.GONE);
        Objects.requireNonNull(cardview_dcfpDocList).setVisibility(View.VISIBLE);
        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        TextView textView8 = bottomSheetDialog.findViewById(R.id.textView8);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);
        Button button7 = bottomSheetDialog.findViewById(R.id.button7);
        Button button8 = bottomSheetDialog.findViewById(R.id.button8);
        Button btn_1 = bottomSheetDialog.findViewById(R.id.btn_1);
        Objects.requireNonNull(button1).setText("1.2");
        Objects.requireNonNull(button2).setText("1.3");
        Objects.requireNonNull(button7).setText("1.4");
        Objects.requireNonNull(button8).setText("1.1");
        Objects.requireNonNull(textView4).setText("Dcr\nFollowup");
        Objects.requireNonNull(textView5).setText("DCFP\nFollowup");
        Objects.requireNonNull(textView8).setText("Dcr\nEntry");
        Objects.requireNonNull(changepassword).setText(R.string.dailycallreport);
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);

        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        Objects.requireNonNull(cardview_followup).setOnClickListener(v -> {
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
        });
        Objects.requireNonNull(cardview_report).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, DcfpFollowupActivity.class);
            i.putExtra("UserName", globalSMCode);
            i.putExtra("UserName_2", userName_2);
            i.putExtra("UserName_3", globalSMCode);
            i.putExtra("UserRole", "D"); // D -> DCFP
            startActivity(i);
        });
        Objects.requireNonNull(cardview_dcfpDocList).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, DcfpDoctorListActivity.class);
            i.putExtra("UserName", globalSMCode);
            i.putExtra("UserName_2", userName_2);
            i.putExtra("UserRole", "SM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_dcrEntry).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, DcrEntryActivity.class);
            i.putExtra("UserName", globalSMCode);
            i.putExtra("UserName_2", userName_2);
            i.putExtra("UserRole", "SM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_tourFollow).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, DcfpFollowupActivity.class);
            i.putExtra("UserName", globalSMCode);
            i.putExtra("UserName_2", userName_2);
            i.putExtra("UserName_3", globalSMCode);
            i.putExtra("UserRole", "T"); // T -> TOUR
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
        Button btn_1 = bottomSheetDialog2.findViewById(R.id.btn_1);
        Objects.requireNonNull(cardview3).setVisibility(View.GONE);
        Objects.requireNonNull(cardview4).setVisibility(View.GONE);

        TextView textView4 = bottomSheetDialog2.findViewById(R.id.textView4);
        Objects.requireNonNull(textView4).setText("Tracking\nDoctor");

        ImageView imageView3 = bottomSheetDialog2.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_doctor_service);

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
                //bottomSheetDialog2.dismiss();
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
        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        Objects.requireNonNull(changepassword).setText("MSD");
        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, MSDProgramFollowup.class);
            i.putExtra("user_code", globalSMCode);
            i.putExtra("user_name", globalDivisionCode);
            i.putExtra("user_flag", "SM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, DocSupportFollowup.class);
            i.putExtra("user_code", globalSMCode);
            i.putExtra("user_name", globalDivisionCode);
            i.putExtra("user_flag", "SM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, MSDProgramApproval.class);
            i.putExtra("user_code", globalSMCode);
            i.putExtra("user_name", globalDivisionCode);
            i.putExtra("user_flag", "SM");
            startActivity(i);
        });
        Objects.requireNonNull(cardview_commitment_followup).setOnClickListener(v -> {
            Intent i = new Intent(SalesManagerDashboard.this, MSDCommitmentFollowup.class);
            i.putExtra("user_code", globalSMCode);
            i.putExtra("user_name", globalDivisionCode);
            i.putExtra("user_flag", "SM");
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                ShortcutBadger.applyCount(getBaseContext(), 0);
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                i.putExtra("manager_code", globalSMCode);
                i.putExtra("manager_detail", "SM");
                startActivity(i);
                //bottomSheetDialog2.dismiss();
            }
        });
        Objects.requireNonNull(cardview4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(SalesManagerDashboard.this, PrescriptionFollowup2.class);
                Intent i = new Intent(SalesManagerDashboard.this, RxSummaryMISActivity.class);
                i.putExtra("manager_code", globalSMCode);
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
                showBottomSheetDialog_RXCAPTURE();
                //startActivity(i);
            }
        });
        btn_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_RXCAPTURE();
                //startActivity(i);
            }
        });
        img_btn_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_RXCAPTURE();
                //startActivity(i);
            }
        });
        tv_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showBottomSheetDialog_RXCAPTURE();
                //startActivity(i);
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
                            if (!NetInfo.isOnline(getBaseContext())) {} else {
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

    private void doctorListInfo() {
        cardview_doctor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(SalesManagerDashboard.this, DoctorListActivity.class);
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
                Intent i = new Intent(SalesManagerDashboard.this, DoctorListActivity.class);
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
                Intent i = new Intent(SalesManagerDashboard.this, DoctorListActivity.class);
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
                Intent i = new Intent(SalesManagerDashboard.this, DoctorListActivity.class);
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
                Intent i = new Intent(SalesManagerDashboard.this, ChemistListActivity.class);
                i.putExtra("UserName", globalempName);
                i.putExtra("UserCode", globalSMCode);
                i.putExtra("TerriCode", userName);
                startActivity(i);
            }
        });
    }

    private void salesReportDashboard() {
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
                                Toast.makeText(getApplicationContext(), "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //Intent i = new Intent(Dashboard.this, Dashboard.class);
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

                                Toast.makeText(getApplicationContext(), "Check Internet connection", Toast.LENGTH_LONG).show();
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
                                //showSnack();
                            } else {
                                //Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
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
                                //showSnack();
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
                                Toast.makeText(getApplicationContext(), "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                //Intent i = new Intent(Dashboard.this, Dashboard.class);
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
        //session.setLogin(false);
        //session.removeAttribute();
        //session.invalidate();
        Intent intent = new Intent(SalesManagerDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    public void onBackPressed() {
        //Toast.makeText(this, "Press the back button to close the application.", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
        //super.onBackPressed();
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
        Log.e("onResume----->", global_admin_Code);
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
        Log.e("onPause----->", String.valueOf(count));
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
        Log.e("onDestroy----->", String.valueOf(count));
        //updateLocation();
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
                    Toast.makeText(SalesManagerDashboard.this, message, Toast.LENGTH_LONG).show();
                    //Toast.makeText(SalesManagerDashboard.this, "You are locked...", Toast.LENGTH_LONG).show();
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


