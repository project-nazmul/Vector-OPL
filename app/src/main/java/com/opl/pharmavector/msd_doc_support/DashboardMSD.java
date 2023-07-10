package com.opl.pharmavector.msd_doc_support;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.R;
import com.opl.pharmavector.pmdVector.DashBoardPMD;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.service.MyLocationService;
import com.opl.pharmavector.util.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DashboardMSD extends Activity {
    int count;
    Bundle bundle;
    Button logout;
    ImageView profileImage;
    private String logStatus = "M";
    LocationRequest locationRequest;
    PreferenceManager preferenceManager;
    TextView profileName, profileCode, profileDesignation;
    FusedLocationProviderClient fusedLocationProviderClient;
    String globalCode, globalName, globalTerri, profileImageUrl, pmdName, pmdLoc, pmdLocCode, pmdLocPass, pmdType, pmdCode;
    String imageBaseurl = ApiClient.BASE_URL+"vector_ff_image/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_msd);

        initViews();
        statusBarHide();
        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardMSD.this, R.style.Theme_Design_BottomSheetDialog);
            builder.setTitle("Exit !").setMessage("Are you sure you want to exit Vector?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Thread server = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                logStatus = "N";
                                preferenceManager.clearPreferences();
                                count = 0;
                                Intent logoutIntent = new Intent(DashboardMSD.this, Login.class);
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

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardMSD.this, R.style.Theme_Design_BottomSheetDialog);
            builder.setTitle("App Require Location");
            builder.setMessage("This app collects location data to enable Doctor Chamber Location Feature even when app is running");
            builder.setPositiveButton("Proceed", (dialog, which) -> {
                Thread server = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                            dexterPermission(DashboardMSD.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                        else {
                            dexterPermission(DashboardMSD.this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
                        }
                    }
                });
                server.start();
            });
            builder.setNegativeButton("Quit App", (dialog, which) -> {
                preferenceManager.clearPreferences();
                count = 0;
                Intent logoutIntent = new Intent(DashboardMSD.this, Login.class);
                startActivity(logoutIntent);
                finish();
            });
            builder.show();
        }
    }

    private void statusBarHide() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void initViews() {
        preferenceManager = new PreferenceManager(this);
        count = preferenceManager.getTasbihCounter();
        globalCode = preferenceManager.getAdmin_Code();
        globalName = preferenceManager.getAdmin_Name();
        globalTerri = preferenceManager.getAdmin_Terri();
        bundle = getIntent().getExtras();
        assert bundle != null;
        pmdCode = bundle.getString("executive_code");
        pmdName = bundle.getString("executive_name");
        pmdLoc = bundle.getString("executive_loc");
        pmdLocCode = bundle.getString("executive_loccode");
        pmdLocPass = bundle.getString("executive_locpass");
        pmdType = bundle.getString("executive_type");
        profileName = findViewById(R.id.executive_name);
        profileName.setText(pmdName);
        profileImage = findViewById(R.id.imageView2);
        profileImageUrl = imageBaseurl+globalCode+"."+"jpg" ;
        Picasso.get().load(profileImageUrl).into(profileImage);
        profileCode = findViewById(R.id.executive_code);
        profileCode.setText(pmdCode);
        profileDesignation = findViewById(R.id.executive_loc);
        profileDesignation.setText(pmdLoc);
        logout = findViewById(R.id.logout);
    }

    private void dexterPermission(Context context, String... permissions) {
        Dexter.withContext(this)
                .withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!report.areAllPermissionsGranted()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardMSD.this, R.style.Theme_Design_BottomSheetDialog);
                            builder.setTitle("App Require Location").setMessage("All permission must be Granted")
                                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Thread server = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dexterPermission(DashboardMSD.this);
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
                                            Intent logoutIntent = new Intent(DashboardMSD.this, Login.class);
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
}