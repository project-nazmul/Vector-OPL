package com.opl.pharmavector.pmdVector;

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
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.GMDashboard1;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.ManagersSalesFollowup;
import com.opl.pharmavector.NoticeBoard;
import com.opl.pharmavector.R;
import com.opl.pharmavector.achieve.AchieveEarnActivity;
import com.opl.pharmavector.app.Config;
import com.opl.pharmavector.contact.Activity_PMD_Contact;
import com.opl.pharmavector.dcfpFollowup.DoctorReachActivity;
import com.opl.pharmavector.incentive.IncentiveActivity;
import com.opl.pharmavector.liveDepot.ADSStockInfoActivity;
import com.opl.pharmavector.liveDepot.ADSStockPMDActivity;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.pmdVector.ff_contact.ff_contact_activity;
import com.opl.pharmavector.pmdVector.pmdRX.ImageLoadActivity;
import com.opl.pharmavector.pmdVector.pmdRX.PMDRXSummary;
import com.opl.pharmavector.pmdVector.pmdRX.PMDRXSummary2;
import com.opl.pharmavector.pmdVector.pmd_sales_reports.Pmd_Sales_Dashboard;
import com.opl.pharmavector.pmdVector.sales_4p.Activity_4p_Sales;
import com.opl.pharmavector.pmdVector.utils.PMDNotification;

import com.opl.pharmavector.prescriber.TopPrescriberActivity;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import com.opl.pharmavector.service.MyLocationService;
import com.opl.pharmavector.tourPlan.TourPlanActivity;
import com.opl.pharmavector.util.NetInfo;
import com.opl.pharmavector.util.NotificationUtils;
import com.opl.pharmavector.util.PreferenceManager;
import com.opl.pharmavector.util.VectorUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardPMD extends Activity implements View.OnClickListener {
    TextView executive_name, executive_code, executive_loc, executive_designation;
    Bundle b;
    public static String pmd_name, pmd_loc, pmd_loccode, pmd_locpass, pmd_type, pmd_code, currentVersion;
    public ImageView imageView2;
    public String base_url = ApiClient.BASE_URL + "pmd_vector/pmd_images/";
    CardView practiceCard2, cardview_sales_reports, cardview_ff_contact, cardview_4p_sales, cardview_achv_earn, cardView_productStock, cardview_pmd_contact, cardView_incentive;
    ImageButton img_btn_rx, img_btn_sales_reports, img_btn_ff_contact, img_btn_4p_sales;
    TextView tv_sales_reports, tv_ff_contact, tv_4p_sales, versionName;
    Button btn_sales_reports, btn_ff_contact, btn_4p_sales;
    public static String profile_image;
    PreferenceManager preferenceManager;
    int count;
    public static String track_lat, vector_version, track_lang, track_add, build_model, build_brand, build_manufac, build_id, build_device, build_version;
    @SuppressLint("StaticFieldLeak")
    static DashBoardPMD instance;
    public static String vectorToken = "xxxx";
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String log_status = "P", designation, terriName;
    double fetchedlang, fetchedlat;
    Context context;
    private DatabaseHandler db;
    BroadcastReceiver updateUIReciver;
    CardView practiceCard1;
    Button logout, btn_dashboard_1, btn_dashboard_2;
    ImageButton img_btn_notice;

    public static DashBoardPMD getInstance() {
        return instance;
    }

    @SuppressLint({"CutPasteId", "HardwareIds", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardpmd);

        isUpdateAvailable();
        VectorUtils.screenShotProtect(this);
        preferenceManager = new PreferenceManager(this);
        count = preferenceManager.getTasbihCounter();
        statusBarHide();
        initViews();
        //viewAnimated();
        firebaseEvent();
        getDevicedetails();
        rxEvent();
        pmdContact();
        notificationEvent();
        sales_reports();
        FF_Contact_Event();
        Sales_4p_Event();
        achieveEarnEvent();
        productStockEvent();
        incentiveEvent();
        instance = this;

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardPMD.this, R.style.Theme_Design_BottomSheetDialog);
            builder.setTitle("App Require Location");
            builder.setMessage("This app collects location data to enable Doctor Chamber Location Feature even when app is running");
            builder.setPositiveButton("Proceed", (dialog, which) -> {
                Thread server = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                            dexterPermission(DashBoardPMD.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                        else {
                            dexterPermission(DashBoardPMD.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                        }
                    }
                });
                server.start();
            });
            builder.setNegativeButton("Quit App", (dialog, which) -> {
                preferenceManager.clearPreferences();
                count = 0;
                Intent logoutIntent = new Intent(DashBoardPMD.this, Login.class);
                startActivity(logoutIntent);
                finish();
            });
            builder.show();
        }
        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardPMD.this, R.style.Theme_Design_BottomSheetDialog);
            builder.setTitle("Exit !").setMessage("Are you sure you want to exit Vector?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Thread server = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                log_status = "N";
                                preferenceManager.clearPreferences();
                                count = 0;
                                //unregisterReceiver(updateUIReceiver);
                                Intent logoutIntent = new Intent(DashBoardPMD.this, Login.class);
                                startActivity(logoutIntent);
                                finish();
                            }
                        });
                        server.start();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                    })
                    .show();
        });
        initBroadcastReceiver();
        registerReceiver(updateUIReciver, new IntentFilter(MyLocationService.ACTION_PROCESS_UPDATE));
    }

    private void pmdContact() {
        cardview_pmd_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(DashBoardPMD.this, Activity_PMD_Contact.class);
                i.putExtra("UserName", pmd_name);
                i.putExtra("UserName_2", pmd_code);
                startActivity(i);
            }
        });
    }

    private void rxEvent() {
        btn_dashboard_1.setOnClickListener(v -> showBottomSheetDialog_RX());
        img_btn_rx.setOnClickListener(v -> showBottomSheetDialog_RX());
        practiceCard1.setOnClickListener(v -> showBottomSheetDialog_RX());
    }

    private void notificationEvent() {
        btn_dashboard_2.setOnClickListener(v -> {
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!NetInfo.isOnline(getBaseContext())) {

                        } else {
                            Intent i = new Intent(DashBoardPMD.this, PMDNotification.class);
                            //i.putExtra("UserName", DashBoardPMD.pmd_loccode);
                            //i.putExtra("UserName_2", DashBoardPMD.pmd_code);
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            backthred.start();
        });
        img_btn_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {
                                Intent i = new Intent(DashBoardPMD.this, PMDNotification.class);
                                //i.putExtra("UserName", DashBoardPMD.pmd_loccode);
                                //i.putExtra("UserName_2", DashBoardPMD.pmd_code);
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
        practiceCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {
                                ShortcutBadger.applyCount(getBaseContext(), 0);
                                Intent i = new Intent(DashBoardPMD.this, PMDNotification.class);
                                //i.putExtra("UserName", DashBoardPMD.pmd_loccode);
                                //i.putExtra("UserName_2", DashBoardPMD.pmd_code);
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

    private void Sales_4p_Event() {
        cardview_4p_sales.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(final View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DashBoardPMD.this);
                bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
                CardView cardView_topPrescriber = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
                CardView cardView_spiReport = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
                CardView cardView_doctorReach = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
                CardView cardView_spiGeneric = bottomSheetDialog.findViewById(R.id.cardview_commitment_followup);
                Objects.requireNonNull(cardView_spiGeneric).setVisibility(View.VISIBLE);
                TextView changePassword = bottomSheetDialog.findViewById(R.id.changepassword);
                TextView textView4 = bottomSheetDialog.findViewById(R.id.textView4);
                TextView textView5 = bottomSheetDialog.findViewById(R.id.textView5);
                TextView textView6 = bottomSheetDialog.findViewById(R.id.textView6);
                TextView textView7 = bottomSheetDialog.findViewById(R.id.tv_commitment_followup);
                TextView button1 = bottomSheetDialog.findViewById(R.id.button1);
                TextView button2 = bottomSheetDialog.findViewById(R.id.button2);
                TextView button3 = bottomSheetDialog.findViewById(R.id.button3);
                TextView button4 = bottomSheetDialog.findViewById(R.id.btn_commitment_followup);
                Objects.requireNonNull(button1).setText("4.1");
                Objects.requireNonNull(button2).setText("4.2");
                Objects.requireNonNull(button3).setText("4.3");
                Objects.requireNonNull(button4).setText("4.4");
                Objects.requireNonNull(textView4).setText("SPI Top Prescriber\n(Generic)");
                Objects.requireNonNull(textView5).setText("SPI \nReport");
                Objects.requireNonNull(textView6).setText("Doctor \nReach");
                Objects.requireNonNull(textView7).setText("SPI \nGeneric");
                ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
                Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_rx_capture);
                Objects.requireNonNull(changePassword).setText("SPI Report");

                Objects.requireNonNull(cardView_topPrescriber).setOnClickListener(v -> {
                    Intent i = new Intent(DashBoardPMD.this, TopPrescriberActivity.class);
                    i.putExtra("UserName", pmd_name);
                    i.putExtra("UserCode", pmd_code);
                    i.putExtra("UserRole", "PMD");
                    startActivity(i);
                });
                Objects.requireNonNull(cardView_spiReport).setOnClickListener(v -> {
                    Intent i = new Intent(DashBoardPMD.this, MRDPresReport.class);
                    i.putExtra("UserName", pmd_name);
                    i.putExtra("UserCode", pmd_code);
                    i.putExtra("UserRole", "PMD");
                    i.putExtra("report_flag", "SPI");
                    i.putExtra("asm_flag", "N");
                    i.putExtra("sm_flag", "N");
                    i.putExtra("gm_flag", "Y");
                    i.putExtra("rm_flag", "N");
                    i.putExtra("fm_flag", "N");
                    i.putExtra("mpo_flag", "N");
                    startActivity(i);
                });
                Objects.requireNonNull(cardView_doctorReach).setOnClickListener(v -> {
                    Intent i = new Intent(DashBoardPMD.this, DoctorReachActivity.class);
                    i.putExtra("UserName", pmd_name);
                    i.putExtra("UserCode", pmd_code);
                    i.putExtra("UserRole", "PMD");
                    i.putExtra("report_flag", "SPI");
                    i.putExtra("asm_flag", "N");
                    i.putExtra("sm_flag", "N");
                    i.putExtra("gm_flag", "Y");
                    i.putExtra("rm_flag", "N");
                    i.putExtra("fm_flag", "N");
                    i.putExtra("mpo_flag", "N");
                    startActivity(i);
                });
                Objects.requireNonNull(cardView_spiGeneric).setOnClickListener(v -> {
                    Intent i = new Intent(DashBoardPMD.this, Activity_4p_Sales.class);
                    startActivity(i);
                });
                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //Toast.makeText(getApplicationContext(), "bottomSheetDialog is Dismissed ", Toast.LENGTH_LONG).show();
                    }
                });
                bottomSheetDialog.show();
            }
        });
    }

    private void FF_Contact_Event() {
        btn_ff_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {
                                Intent i = new Intent(DashBoardPMD.this, ff_contact_activity.class);
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
        img_btn_ff_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                            } else {
                                Intent i = new Intent(DashBoardPMD.this, ff_contact_activity.class);
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
        cardview_ff_contact.setOnClickListener(v -> {
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!NetInfo.isOnline(getBaseContext())) {

                        } else {
                            Intent i = new Intent(DashBoardPMD.this, ff_contact_activity.class);
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

    private void initBroadcastReceiver() {
        updateUIReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String parselang = intent.getStringExtra("langtitude");
                String parselat = intent.getStringExtra("latitude");
                fetchedlang = Double.parseDouble(parselang);
                fetchedlat = Double.parseDouble(parselat);
                track_lang = parselang;
                track_lat = parselat;
                getAddress(fetchedlat, fetchedlang);
                userLog(log_status);
            }
        };
    }

    private void viewAnimated() {
        Animation imageAnimation = AnimationUtils.loadAnimation(this, R.anim.view_rotate_anim);
        imageView2.startAnimation(imageAnimation);
    }

    private void statusBarHide() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void initViews() {
        executive_name = findViewById(R.id.executive_name);
        executive_code = findViewById(R.id.executive_code);
        executive_loc = findViewById(R.id.executive_loc);
        executive_designation = findViewById(R.id.textView3);
        imageView2 = findViewById(R.id.imageView2);
        logout = findViewById(R.id.logout);
        versionName = findViewById(R.id.versionName);
        btn_dashboard_1 = findViewById(R.id.btn_dashboard_1);
        btn_dashboard_2 = findViewById(R.id.btn_dashboard_2);

        img_btn_rx = findViewById(R.id.img_btn_rx);
        img_btn_notice = findViewById(R.id.img_btn_notice);

        practiceCard1 = findViewById(R.id.practiceCard1);
        practiceCard2 = findViewById(R.id.practiceCard2);

        btn_sales_reports = findViewById(R.id.btn_sales_reports);
        img_btn_sales_reports = findViewById(R.id.img_btn_sales_reports);
        tv_sales_reports = findViewById(R.id.tv_sales_reports);
        cardview_sales_reports = findViewById(R.id.cardview_sales_reports);

        btn_4p_sales = findViewById(R.id.btn_4p_sales);
        img_btn_4p_sales = findViewById(R.id.img_btn_4p_sales);
        tv_4p_sales = findViewById(R.id.tv_4p_sales);
        cardview_4p_sales = findViewById(R.id.cardview_4p_sales);

        btn_ff_contact = findViewById(R.id.btn_ff_contact);
        img_btn_ff_contact = findViewById(R.id.img_btn_ff_contact);
        tv_ff_contact = findViewById(R.id.tv_ff_contact);
        cardview_ff_contact = findViewById(R.id.cardview_ff_contact);
        cardview_achv_earn = findViewById(R.id.cardview_achv_earn);
        cardView_incentive = findViewById(R.id.cardView_incentive);
        cardview_pmd_contact = findViewById(R.id.cardview_pmd_contact);
        cardView_productStock = findViewById(R.id.cardView_productStock);

        b = getIntent().getExtras();
        assert b != null;
        pmd_code = b.getString("executive_code");
        pmd_name = b.getString("executive_name");
        pmd_loc = b.getString("executive_loc");
        designation = b.getString("Designation");
        terriName = b.getString("TerriName");
        pmd_loccode = b.getString("executive_loccode");
        pmd_locpass = b.getString("executive_locpass");
        pmd_type = b.getString("executive_type");
        executive_code.setText(pmd_code);
        executive_name.setText(pmd_name);
        //executive_loc.setText(pmd_loc);
        //executive_designation.setText(designation);
        profile_image = base_url + pmd_code + "." + "jpg";
        vector_version = getString(R.string.vector_version);
        Picasso.get().load(profile_image).into(imageView2);

        if (designation != null && terriName != null) {
            executive_loc.setText(terriName);
            executive_designation.setText(designation);
        } else {
            executive_loc.setText(pmd_loc);
            executive_designation.setText(preferenceManager.getDesignation());
        }
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.d("Login", currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("Login", e.toString());
        }
        if (!currentVersion.isEmpty()) {
            versionName.setText(currentVersion);
        }
    }

    private void achieveEarnEvent() {
        cardview_achv_earn.setOnClickListener(v -> {
            Intent i = new Intent(DashBoardPMD.this, AchieveEarnActivity.class);
            i.putExtra("UserName", pmd_name);
            i.putExtra("UserCode", pmd_code);
            i.putExtra("message_3", pmd_loc);
            i.putExtra("UserRole", "PMD");
            startActivity(i);
        });
    }

    private void productStockEvent() {
        //cardView_productStock.setOnClickListener(v -> showBottomSheetDialog_ProdStock());
        cardView_productStock.setOnClickListener(v -> {
            Intent i = new Intent(DashBoardPMD.this, ADSStockPMDActivity.class);
            i.putExtra("UserName", pmd_name);
            i.putExtra("UserCode", pmd_code);
            i.putExtra("new_version", Login.version);
            i.putExtra("UserRole", "PMD");
            startActivity(i);
        });
    }

    private void incentiveEvent() {
        cardView_incentive.setOnClickListener(v -> {
            Intent i = new Intent(DashBoardPMD.this, IncentiveActivity.class);
            i.putExtra("UserName", pmd_name);
            i.putExtra("UserCode", pmd_code);
            i.putExtra("new_version", Login.version);
            i.putExtra("UserRole", "PMD");
            startActivity(i);
        });
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_ProdStock() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.tour_bottom_sheet_dialog);
        CardView cardView_adsStock = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardView_dailyStock = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        Objects.requireNonNull(cardView_dailyStock).setVisibility(View.GONE);
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
        Objects.requireNonNull(button1).setText("7.1");
        Objects.requireNonNull(button2).setText("17.2");
        Objects.requireNonNull(button3).setText("17.3");
        Objects.requireNonNull(textView4).setText("ADS - Available\nDepot Stock");
        Objects.requireNonNull(textView5).setText("Daily\nLive Stock");
        Objects.requireNonNull(textView6).setText("Doctor \nReach");
        Objects.requireNonNull(changePassword).setText("Product Stock");
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_dcr);
        Objects.requireNonNull(btn_1).setOnClickListener(v -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();

        Objects.requireNonNull(cardView_adsStock).setOnClickListener(v -> {
            Intent i = new Intent(DashBoardPMD.this, ADSStockPMDActivity.class);
            i.putExtra("UserName", pmd_name);
            i.putExtra("UserCode", pmd_code);
            i.putExtra("new_version", Login.version);
            i.putExtra("UserRole", "PMD");
            startActivity(i);
        });
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog_RX() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.pmd_rx_bottom_sheet_dialog);
        CardView cardview_rx_image = bottomSheetDialog.findViewById(R.id.cardview_rx_image);
        CardView cardview_rx_summary_A = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_A);
        CardView cardview_rx_summary_B = bottomSheetDialog.findViewById(R.id.cardview_rx_summary_B);
        TextView changepassword = bottomSheetDialog.findViewById(R.id.changepassword);
        ImageView imageView3 = bottomSheetDialog.findViewById(R.id.imageView3);
        Objects.requireNonNull(imageView3).setBackgroundResource(R.drawable.ic_rx_capture);
        Objects.requireNonNull(changepassword).setText("Prescription Capture");
        Objects.requireNonNull(cardview_rx_summary_B).setVisibility(View.GONE);
        Objects.requireNonNull(cardview_rx_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoardPMD.this, ImageLoadActivity.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "layout_rx_image is Clicked ", Toast.LENGTH_LONG).show();
                //bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_rx_summary_A).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoardPMD.this, PMDRXSummary.class);
                startActivity(i);
                //bottomSheetDialog.dismiss();
            }
        });
        Objects.requireNonNull(cardview_rx_summary_B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "layout_rx_report_b is Clicked ", Toast.LENGTH_LONG).show();
                Intent i = new Intent(DashBoardPMD.this, PMDRXSummary2.class);
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

    private void updateLocation() {
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
    }

    private PendingIntent getPendingIntent() {
        Intent myIntent = new Intent(this, MyLocationService.class);
        myIntent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_IMMUTABLE);
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(DashBoardPMD.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            //List<Address> addresses = geocoder.getFromLocation(fetchedlat, fetchedlang, 1);
            Address obj = addresses.get(0);
            track_add = obj.getAddressLine(0);
            track_add = track_add + "\n" + obj.getCountryName();
            track_add = track_add + "\n" + obj.getCountryCode();
            Log.e("address-->", track_add);
            //userLog(log_status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDevicedetails() {
        build_version = Build.VERSION.RELEASE;
        build_model = Build.MODEL;
        build_device = Build.DEVICE;
        build_brand = Build.BRAND;
        build_id = Build.ID;
    }

    private void firebaseEvent() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(DashBoardPMD.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                vectorToken = instanceIdResult.getToken();
                Log.e("vectorToken-->", vectorToken);
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
                        //Log.d(TAG, msg);
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

    private void isUpdateAvailable() {
        AppUpdateManager mAppUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardPMD.this);
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
        Call<Patient> call = apiInterface.userData(key, vector_version, vectorToken, String.valueOf(fetchedlat), String.valueOf(fetchedlang),
                build_model, build_brand, pmd_loccode, track_add, "");

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                assert response.body() != null;
                int success = response.body().getSuccess();
                String message = response.body().getMassage();
                Log.e("serverResponse->", message);
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {}
        });
    }

    private void dexterPermission(Context context, String... permissions) {
        Dexter.withContext(this)
                .withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!report.areAllPermissionsGranted()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardPMD.this, R.style.Theme_Design_BottomSheetDialog);
                            builder.setTitle("App Require Location").setMessage("All permission must be Granted")
                                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Thread server = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dexterPermission(DashBoardPMD.this);
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
                                            Intent logoutIntent = new Intent(DashBoardPMD.this, Login.class);
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
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {}
                }).check();
    }

    @Override
    public void onClick(View view) {}

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));
        registerReceiver(updateUIReciver, new IntentFilter(MyLocationService.ACTION_PROCESS_UPDATE));
        NotificationUtils.clearNotifications(getApplicationContext());
        preferenceManager.setTasbihCounter(count);
        preferenceManager.setusername(pmd_loccode);
        preferenceManager.setpassword(pmd_locpass);
        preferenceManager.setuserrole(pmd_code);
        preferenceManager.setuserdtl(pmd_name);
        preferenceManager.setfftype(pmd_type);
        preferenceManager.setcurrentVersion(vector_version);
        preferenceManager.setexecutive_name(pmd_loc);
        updateLocation();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        updateLocation();
        super.onPause();
        preferenceManager.setTasbihCounter(count);
        preferenceManager.setusername(pmd_loccode);
        preferenceManager.setpassword(pmd_locpass);
        preferenceManager.setuserrole(pmd_code);
        preferenceManager.setuserdtl(pmd_name);
        preferenceManager.setfftype(pmd_type);
        preferenceManager.setexecutive_name(pmd_loc);
        Log.e("onPause----->", String.valueOf(count) + "::" + preferenceManager.getTasbihCounter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateUIReciver);
        preferenceManager.setTasbihCounter(count);
        preferenceManager.setusername(pmd_loccode);
        preferenceManager.setpassword(pmd_locpass);
        preferenceManager.setuserrole(pmd_code);
        preferenceManager.setuserdtl(pmd_name);
        preferenceManager.setfftype(pmd_type);
        preferenceManager.setexecutive_name(pmd_loc);
        updateLocation();
    }

    @Override
    protected void onStop() {
        updateLocation();
        super.onStop();
    }

    private void sales_reports() {
        cardview_sales_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Toast.makeText(getApplicationContext(), "Check Internet connection", Toast.LENGTH_LONG).show();
                            } else {
                                Intent i = new Intent(DashBoardPMD.this, Pmd_Sales_Dashboard.class);
                                i.putExtra("UserName", pmd_name);
                                i.putExtra("UserCode", pmd_code);
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
        img_btn_sales_reports.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(DashBoardPMD.this, Pmd_Sales_Dashboard.class);
                                i.putExtra("UserName", pmd_name);
                                i.putExtra("UserCode", pmd_code);
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
        tv_sales_reports.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(DashBoardPMD.this, Pmd_Sales_Dashboard.class);
                                i.putExtra("UserName", pmd_name);
                                i.putExtra("UserCode", pmd_code);
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
        btn_sales_reports.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(DashBoardPMD.this, Pmd_Sales_Dashboard.class);
                                i.putExtra("UserName", pmd_name);
                                i.putExtra("UserCode", pmd_code);
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

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
