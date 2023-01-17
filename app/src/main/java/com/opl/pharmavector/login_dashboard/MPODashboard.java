package com.opl.pharmavector.login_dashboard;

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
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.github.tutorialsandroid.appxupdater.AppUpdater;
import com.github.tutorialsandroid.appxupdater.AppUpdaterUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.DcrReport;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.MPODCCDashboard;
import com.opl.pharmavector.NoticeBoard;
import com.opl.pharmavector.Offlinereport;
import com.opl.pharmavector.PCDashboard;
import com.opl.pharmavector.PersonalExpenses;
import com.opl.pharmavector.R;
import com.opl.pharmavector.Report;
import com.opl.pharmavector.ReportPersonalExpenses;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.app.Config;
import com.opl.pharmavector.doctorservice.DoctorServiceDashboard;
import com.opl.pharmavector.doctorgift.DocGiftDashBoard;
import com.opl.pharmavector.exam.ExamDashboard;
import com.opl.pharmavector.giftfeedback.FieldFeedBack;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mpodcr.Dcr;
import com.opl.pharmavector.msd_doc_support.DocSupportDashboard;
import com.opl.pharmavector.order_online.ReadComments;
import com.opl.pharmavector.pmdVector.DashBoardPMD;
import com.opl.pharmavector.pmdVector.pmdRX.ImageLoadActivity;
import com.opl.pharmavector.pmdVector.pmdRX.PMDRXSummary;
import com.opl.pharmavector.pmdVector.pmdRX.PMDRXSummary2;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionDashboard;
import com.opl.pharmavector.promomat.PromomaterialDashboard;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.service.MyLocationService;
import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.opl.pharmavector.util.NotificationUtils;
import com.opl.pharmavector.util.PreferenceManager;

public class MPODashboard extends Activity implements View.OnClickListener {

    public String userName_1, userName, userName_2, UserName_2, new_version, message_3;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public static final String TAG_SUCCESS = "mysuccess";
    public static final String TAG_MESSAGE = "mymessage";
    public static final String TAG_success_1 = "success_1";
    public static final String TAG_ord_no = "ord_no";
    public AutoCompleteTextView actv;
    private DatabaseHandler db;
    private String TAG = Offlinereport.class.getSimpleName();
    public TextView user_show1, user_show2,t4,t5;
    private SessionManager session;
    public String message;
    public String success;
    public static String globalmpocode,globalfftype, globalterritorycode, ff_type,build_model,build_brand,build_manufac,build_id,build_device,build_version,password;
    public static String track_lat, track_lang, track_add;
    public static String vectorToken;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private int count;
    PreferenceManager preferenceManager;
    public static String version;
    static MPODashboard instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<HashMap<String, String>> customerlist;

    private ProgressDialog pDialog;
    private String log_status ="A";
    private String vector_version;
    double fetchedlang,fetchedlat;
    Context context;
    BroadcastReceiver updateUIReciver;

    CardView cardview_dcr,practiceCard2,practiceCard3,practiceCard4,practiceCard5,practiceCard6,
            practiceCard7,practiceCard8,practiceCard9,cardview_pc,cardview_promomat,cardview_salereports;

    ImageButton img_btn_dcr,img_btn_dcc,img_btn_productorder,img_btn_docservice,img_btn_docgiftfeedback,
            img_btn_notification,img_btn_rx,img_btn_personalexpense,img_btn_pc,img_btn_promomat,img_btn_salereports;

    TextView tv_dcr,tv_productorder,tv_dcc,tv_docservice,tv_docgiftfeedback,
            tv_notification,tv_rx,tv_personalexpense,tv_pc,tv_promomat,tv_salereports;

    Button logout,btn_dcr,btn_productorder,btn_dcc,btn_docservice,
            btn_docgiftfeedback,btn_notification,btn_rx,btn_personalexpense,btn_pc,btn_promomat,btn_salereports;

    public static MPODashboard getInstance() {
        return instance;
    }

    @SuppressLint({"CutPasteId", "HardwareIds", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.O)

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.dashboard);
        setContentView(R.layout.activity_vector_mpo_dashboard);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        preferenceManager = new PreferenceManager(this);
        count = preferenceManager.getTasbihCounter();



        dcrClickEvent();
        dccfollowupEvent();
        doctorServiceEvent();
        doctorGiftEvent();
        noticeBoradEvent();
        prescriptionEvent();
        orderEvents();
        personalExpenseEvent();
        pcConferenceEvent();
        promoMaterialFollowupEvent();
        salesReportEvent();

  /*



        mrcExamEvent();
        prescriptionEvent();
        vectorFeedback();
        msdDocSupport();
        */
        getDevicedetails();
        firebaseEvent();

        session = new SessionManager(getApplicationContext());



        instance = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED /*||
                checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MPODashboard.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("App Require Location").setMessage("This app collects location data to enable Doctor Chamber Location Feature even when app is running")
                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                            dexterPermission(MPODashboard.this,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);
                                        }else {
                                            dexterPermission(MPODashboard.this,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION);
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
                                Intent logoutIntent = new Intent(MPODashboard.this, Login.class);
                                startActivity(logoutIntent);
                                finish();

                            }
                        })
                        .show();
            }


        }
        db = new DatabaseHandler(this);
        customerlist = new ArrayList<>();
        vector_version = getString(R.string.vector_version);

        initBroadcastReceiver();
        registerReceiver(updateUIReciver,new IntentFilter(MyLocationService.ACTION_PROCESS_UPDATE));
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MPODashboard.this, R.style.Theme_Design_BottomSheetDialog);
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
                                        Intent logoutIntent = new Intent(MPODashboard.this, Login.class);
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


    }




    @SuppressLint("CutPasteId")
    private void initViews(){

            logout= findViewById(R.id.logout);
            t4= findViewById(R.id.t4);
            t5= findViewById(R.id.t5);

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
            practiceCard4             = findViewById(R.id.practiceCard3);


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


            db = new DatabaseHandler(this);
            ArrayList<String> mpo_code_interna = db.getterritoryname();
            String mpo_code_i = mpo_code_interna.toString();
            globalmpocode = userName;
            globalterritorycode = UserName_2;
            globalmpocode = userName;

            t4.setText(globalmpocode);
            t5.setText(globalterritorycode);

            /*
            online_order = findViewById(R.id.online_order);
            offline_order = findViewById(R.id.offline_order);
            dcr = findViewById(R.id.dcr);
            dcr_report = findViewById(R.id.p_expense);
            personal_expenses = findViewById(R.id.personal_expenses);
            personal_expenses_report = findViewById(R.id.personal_expenses_report);
            sales_report = findViewById(R.id.sales_report);
            dcc_dashboard = findViewById(R.id.dcc_dashboard);
            notification = findViewById(R.id.notification);
            pc_conference_btn = findViewById(R.id.pc_conference_btn);
            doctor_service_followup = findViewById(R.id.doctor_service_followup);
            prescription_entry = findViewById(R.id.prescription_entry);
            promo_material_followup = findViewById(R.id.promo_material_followup);
            doc_gift_btn = findViewById(R.id.doc_gift_btn);
            mrc_exam = findViewById(R.id.mrc_exam);

            btn_msd_doctor_support = findViewById(R.id.btn_msd_doc_support);
            bar_msd_doctor_support = findViewById(R.id.bar_msd_doc_support);

            bar_1 = findViewById(R.id.progressBar);
            bar_2 = findViewById(R.id.progressBar1);
            bar_3 = findViewById(R.id.progressBar3);
            bar_4 = findViewById(R.id.progressBar4);
            bar_5 = findViewById(R.id.progressBar5);
            bar_6 = findViewById(R.id.progressBar6);
            bar_7 = findViewById(R.id.progressBar7);
            bar_9 = findViewById(R.id.progressBar9);
            bar_10 = findViewById(R.id.progressBar10);
            bar_11 = findViewById(R.id.progressBar14);
            bar_12 = findViewById(R.id.progressBar16);
            bar_13 = findViewById(R.id.progressBar15);
            bar_17 = findViewById(R.id.progressBar17);
            bar_14 = findViewById(R.id.progressBar22);
            bar_18 = findViewById(R.id.progressBar11);
            bar_vector_feedback = findViewById(R.id.bar_vector_feedback);
            btn_vector_feedback = findViewById(R.id.btn_vector_feedback);

            user_show1 = findViewById(R.id.user_show1);
            user_show2 = findViewById(R.id.user_show1);
            */

            /*
            Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
            logout = (Button) findViewById(R.id.logout);
            logout.setTypeface(fontFamily);
            logout.setText("\uf08b");
            db = new DatabaseHandler(this);
            ArrayList<String> mpo_code_interna = db.getterritoryname();
            String mpo_code_i = mpo_code_interna.toString();
            globalmpocode = userName;
            globalterritorycode = UserName_2;
            user_show1.setText(String.format("%s [ %s ] ", globalmpocode, globalterritorycode, globalfftype));
            globalmpocode = userName;
            globalmpoflag = "M";
            examid = globalmpocode;
            */
    }



    private void dexterPermission(Context context,String... permissions) {
        Dexter.withContext(this)
                .withPermissions(permissions).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (!report.areAllPermissionsGranted()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MPODashboard.this, R.style.Theme_Design_BottomSheetDialog);
                    builder.setTitle("App Require Location").setMessage("All permission must be Granted")
                            .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Thread server = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dexterPermission(MPODashboard.this);
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
                                    Intent logoutIntent = new Intent(MPODashboard.this, Login.class);
                                    startActivity(logoutIntent);
                                    finish();

                                }
                            })
                            .show();
                }
                else{
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
        Call<Patient> call = apiInterface.userData(key,vector_version,vectorToken,MPODashboard.track_lat,MPODashboard.track_lang,build_model,build_brand,MPODashboard.globalmpocode,Dashboard.track_add);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                assert response.body() != null;
                int success           = response.body().getSuccess();
                String message        = response.body().getMassage();
                Log.e("mpoLocationUpdate->",message+"===>"+Dashboard.track_lat+"-----"+Dashboard.track_lang);

            }
            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });
    }

    private void initBroadcastReceiver() {

        Log.e("initBroadCast-->","initBroadcastReceiver");
        updateUIReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String parselang = intent.getStringExtra("langtitude");
                String parselat = intent.getStringExtra("latitude");
                fetchedlang = Double.parseDouble(parselang);
                fetchedlat = Double.parseDouble(parselat);
                track_lat = parselat;
                track_lang = parselang;
                // Log.e("parseLang-->",parselang);
                // Log.e("parselat-->",parselat);
                getAddress(fetchedlat,fetchedlang);
                userLog(log_status);
            }
        };
    }

    private PendingIntent getPendingIntent() {
        Intent myIntent = new Intent(this, MyLocationService.class);
        myIntent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(200);
        locationRequest.setSmallestDisplacement(10f);
        Log.e("loca-->",locationRequest.toString());
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MPODashboard.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            track_add = obj.getAddressLine(0);
            track_add = track_add + "\n" + obj.getCountryName();
            track_add = track_add + "\n" + obj.getCountryCode();
            //userLog(log_status);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    public void getDevicedetails() {
        build_version = Build.VERSION.RELEASE;
        build_model = Build.MODEL;
        build_device = Build.DEVICE;
        build_brand =Build.BRAND;
        build_id = Build.ID;
    }

    private void firebaseEvent() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MPODashboard.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                vectorToken = instanceIdResult.getToken();
                Log.e("vectorToken-->",vectorToken);
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
                }
            }
        };
    }

    private void updateLocation() {
        buildLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());

    }

    private void showSnack() {
        new Thread() {
            public void run() {
                MPODashboard.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "No internet Connection, Please Check Your Connection";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();

    }


    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        Button changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);

        textView4.setText("Order\nOnline");
        textView5.setText("Order\nOffline");
        changepassword.setText("Product Order");

        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        cardview_rx_summary_B.setVisibility(View.GONE);


        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MPODashboard.this, ReadComments.class);
                i.putExtra("UserName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                i.putExtra("new_version", new_version);
                startActivity(i);
                bottomSheetDialog.dismiss();
            }
        });

        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MPODashboard.this, Offlinereport.class);
                i.putExtra("UserName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                i.putExtra("new_version", new_version);
                startActivity(i);
                bottomSheetDialog.dismiss();
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

    private void showBottomSheetDialog_DCR() {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        Button changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);

        textView4.setText("Dcr\nOnline");
        textView5.setText("Dcr\nReport");
        changepassword.setText("Daily Call Report");

        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        cardview_rx_summary_B.setVisibility(View.GONE);


        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MPODashboard.this, Dcr.class);
                i.putExtra("UserName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                startActivity(i);
                bottomSheetDialog.dismiss();
            }
        });

        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MPODashboard.this, DcrReport.class);
                i.putExtra("UserName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                startActivity(i);
                bottomSheetDialog.dismiss();
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

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_PE() {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
        CardView cardview_onlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_offlineorder = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        Button changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
        TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
        Button button1 = bottomSheetDialog.findViewById(R.id.button1);
        Button button2 = bottomSheetDialog.findViewById(R.id.button2);

        Objects.requireNonNull(button1).setText("9.1");
        Objects.requireNonNull(button2).setText("9.2");
        Objects.requireNonNull(textView4).setText("Personal Expense\nEntry");
        Objects.requireNonNull(textView5).setText("Personal Expense\nReport");
        Objects.requireNonNull(changepassword).setText("Personal Expense");

        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        Objects.requireNonNull(cardview_rx_summary_B).setVisibility(View.GONE);


        Objects.requireNonNull(cardview_onlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> UserName_2 = db.getterritoryname();
                String user = UserName_2.toString();
                Intent i = new Intent(MPODashboard.this, PersonalExpenses.class);
                i.putExtra("UserName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                startActivity(i);
                bottomSheetDialog.dismiss();
            }
        });

        Objects.requireNonNull(cardview_offlineorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MPODashboard.this, ReportPersonalExpenses.class);
                i.putExtra("UserName", globalmpocode);
                i.putExtra("UserName_2", globalterritorycode);
                startActivity(i);
                bottomSheetDialog.dismiss();
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




    private void dcrClickEvent() {


        cardview_dcr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                showBottomSheetDialog_DCR();
            }
        });

        btn_dcr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog_DCR();
            }
        });

        img_btn_dcr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog_DCR();
            }
        });
        tv_dcr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog_DCR();
            }
        });




    }

    private void dccfollowupEvent() {


            practiceCard2.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, MPODCCDashboard.class);
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
            btn_dcc.setOnClickListener(new View.OnClickListener() {
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
                                    ArrayList<String> UserName_2 = db.getterritoryname();
                                    String user = UserName_2.toString();
                                    Intent i = new Intent(MPODashboard.this, MPODCCDashboard.class);
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
            tv_dcc.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, MPODCCDashboard.class);
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
            img_btn_dcc.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, MPODCCDashboard.class);
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

    private void doctorServiceEvent(){

        practiceCard3.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, DoctorServiceDashboard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", new_version);
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

        img_btn_docservice.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, DoctorServiceDashboard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", new_version);
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

        btn_docservice.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, DoctorServiceDashboard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", new_version);
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

        tv_docservice.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, DoctorServiceDashboard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", new_version);
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

    private void doctorGiftEvent() {

        btn_docgiftfeedback.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, DocGiftDashBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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
        tv_docgiftfeedback.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, DocGiftDashBoard.class);
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
        img_btn_docgiftfeedback.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, DocGiftDashBoard.class);
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
        practiceCard4.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, DocGiftDashBoard.class);
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, NoticeBoard.class);
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, NoticeBoard.class);
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, NoticeBoard.class);
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
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, NoticeBoard.class);
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
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                // Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
                                Intent i = new Intent(MPODashboard.this, PrescriptionDashboard.class);
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
        img_btn_rx.setOnClickListener(new View.OnClickListener() {
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
                                // Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
                                Intent i = new Intent(MPODashboard.this, PrescriptionDashboard.class);
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
        btn_rx.setOnClickListener(new View.OnClickListener() {
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
                                // Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
                                Intent i = new Intent(MPODashboard.this, PrescriptionDashboard.class);
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
        tv_rx.setOnClickListener(new View.OnClickListener() {
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
                                // Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
                                Intent i = new Intent(MPODashboard.this, PrescriptionDashboard.class);
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

    private void orderEvents() {

        practiceCard7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        btn_productorder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        img_btn_productorder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        tv_productorder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

    }

    private void personalExpenseEvent(){

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


    private void salesReportEvent() {

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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, Report.class);
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, Report.class);
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
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, Report.class);
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
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, Report.class);
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

    private void promoMaterialFollowupEvent(){

        btn_promomat.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, PromomaterialDashboard.class);
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
        img_btn_promomat.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, PromomaterialDashboard.class);
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
        tv_promomat.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, PromomaterialDashboard.class);
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
        cardview_promomat.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, PromomaterialDashboard.class);
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

    private void pcConferenceEvent() {
        cardview_pc.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, PCDashboard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", new_version);
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
        btn_pc.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, PCDashboard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", new_version);
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
        tv_pc.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, PCDashboard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", new_version);
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
        img_btn_pc.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(MPODashboard.this, PCDashboard.class);
                                i.putExtra("UserName", globalmpocode);
                                i.putExtra("UserName_2", globalterritorycode);
                                i.putExtra("new_version", new_version);
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



/*
    private void showSnack() {
        new Thread() {
            public void run() {
                MPODashboard.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "No internet Connection, Please Check Your Connection";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();

    }
    private void msdDocSupport() {

        btn_msd_doctor_support.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, DocSupportDashboard.class);
                                i.putExtra("user_code", Dashboard.globalmpocode);
                                i.putExtra("user_name", Dashboard.globalterritorycode);
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

        bar_msd_doctor_support.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, DocSupportDashboard.class);
                                i.putExtra("user_code", Dashboard.globalmpocode);
                                i.putExtra("user_name", Dashboard.globalterritorycode);
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
    private void vectorFeedback() {

        btn_vector_feedback.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, FieldFeedBack.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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

        bar_vector_feedback.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, FieldFeedBack.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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

    private void dcrClickEvent() {
        dcr.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, Dcr.class);
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
        bar_3.setOnClickListener(new View.OnClickListener() {


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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, Dcr.class);
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

        dcr_report.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, DcrReport.class);
                                i.putExtra("UserName", userName);
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

        bar_4.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, DcrReport.class);
                                i.putExtra("UserName", userName);
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
    private void personalExpenseEvent() {

        personal_expenses_report.setOnClickListener(new View.OnClickListener() {


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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, ReportPersonalExpenses.class);
                                i.putExtra("UserName", userName);
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
        bar_6.setOnClickListener(new View.OnClickListener() {


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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, ReportPersonalExpenses.class);
                                i.putExtra("UserName", userName);
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
        personal_expenses.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, PersonalExpenses.class);
                                i.putExtra("UserName", userName);
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
        bar_5.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, PersonalExpenses.class);
                                i.putExtra("UserName", userName);
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
    private void dccfollowupEvent() {
        dcc_dashboard.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, MPODCCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", globalterritorycode);
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

        bar_9.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, MPODCCDashboard.class);
                                i.putExtra("UserName", userName);
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
    private void promoMaterialFollowupEvent() {

        promo_material_followup.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, PromomaterialDashboard.class);
                                i.putExtra("UserName", userName);
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
        bar_14.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, PromomaterialDashboard.class);
                                i.putExtra("UserName", userName);
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
    private void salesReportEvent() {

        sales_report.setOnClickListener(new View.OnClickListener() {

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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, Report.class);
                                i.putExtra("UserName", userName);
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
        bar_7.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, Report.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", globalterritorycode);
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
    private void noticeBoradEvent() {

        notification.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", userName);
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
        bar_10.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, NoticeBoard.class);
                                i.putExtra("UserName", userName);
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
    private void pcConferenceEvent() {

        pc_conference_btn.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
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
        bar_11.setOnClickListener(new View.OnClickListener() {


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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
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
    private void doctorServiceEvent() {

        doctor_service_followup.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, DoctorServiceDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "M");
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
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
        bar_12.setOnClickListener(new View.OnClickListener() {
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
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();

                                Intent i = new Intent(Dashboard.this, DoctorServiceDashboard.class);
                                //  Intent i = new Intent(Dashboard.this, DoctorServiceFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
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
    private void mrcExamEvent() {
        mrc_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //  new FetchExamFlag().execute();
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, ExamDashboard.class);
                                i.putExtra("mpo_code", userName);
                                i.putExtra("territory_name", globalterritorycode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
                                i.putExtra("myexamid", myexamid);
                                i.putExtra("myexamtime", myexamtime);
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
        bar_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                showSnack();

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, ExamDashboard.class);
                                i.putExtra("mpo_code", userName);
                                i.putExtra("territory_name", globalterritorycode);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
                                i.putExtra("myexamid", myexamid);
                                i.putExtra("myexamtime", myexamtime);
                                i.putExtra("myexamtimeleft", myexamtimeleft);
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
    private void prescriptionEvent() {
        prescription_entry.setOnClickListener(new View.OnClickListener() {
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
                                // Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
                                Intent i = new Intent(Dashboard.this, PrescriptionDashboard.class);
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
        bar_13.setOnClickListener(new View.OnClickListener() {
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
                                // Intent i = new Intent(Dashboard.this, PrescriptionEntry.class);
                                Intent i = new Intent(Dashboard.this, PrescriptionDashboard.class);
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
    private void doctorGiftEvent() {
        doc_gift_btn.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(Dashboard.this, DocGiftDashBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "MPO");
                                startActivity(i);
                            } else {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, DocGiftDashBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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

        bar_17.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(Dashboard.this, DocGiftDashBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", "MPO");
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Dashboard.this, DocGiftDashBoard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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

  */


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onResume() {
        //
        //  Toast.makeText(getApplicationContext(),"OnResume==>",Toast.LENGTH_LONG).show();
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
    }

    @Override
    protected void onPause() {
        //Toast.makeText(getApplicationContext(),"OnPause==>",Toast.LENGTH_LONG).show();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        updateLocation();
        super.onPause();
        preferenceManager.setTasbihCounter(count);
        preferenceManager.setusername(userName);
        preferenceManager.setpassword(password);
        preferenceManager.setuserrole(message_3);
        preferenceManager.setuserdtl(UserName_2);
        preferenceManager.setfftype(ff_type);
        preferenceManager.setcurrentVersion(vector_version);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        //  Toast.makeText(getApplicationContext(),"OnDestroy==>",Toast.LENGTH_LONG).show();
        unregisterReceiver(updateUIReciver);
        preferenceManager.setTasbihCounter(count);
        preferenceManager.setusername(userName);
        preferenceManager.setpassword(password);
        preferenceManager.setuserrole(message_3);
        preferenceManager.setuserdtl(UserName_2);
        preferenceManager.setfftype(ff_type);
        preferenceManager.setcurrentVersion(vector_version);
        updateLocation();

    }

    @Override
    protected void onStop() {
        updateLocation();
        super.onStop();
        // Toast.makeText(getApplicationContext(),"OnStop==>",Toast.LENGTH_LONG).show();
    }

}
