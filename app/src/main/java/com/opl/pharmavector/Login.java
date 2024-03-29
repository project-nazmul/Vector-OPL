package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.util.Objects;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.github.tutorialsandroid.appxupdater.AppUpdater;
import com.github.tutorialsandroid.appxupdater.AppUpdaterUtils;
import com.github.tutorialsandroid.appxupdater.enums.AppUpdaterError;
import com.github.tutorialsandroid.appxupdater.enums.Display;
import com.github.tutorialsandroid.appxupdater.enums.UpdateFrom;
import com.github.tutorialsandroid.appxupdater.objects.Update;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.opl.pharmavector.app.Config;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.msd_doc_support.DashboardMSD;
import com.opl.pharmavector.pmdVector.DashBoardPMD;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.NetInfo;
import com.opl.pharmavector.util.NotificationUtils;
import com.opl.pharmavector.util.PreferenceManager;
import com.opl.pharmavector.util.ResetPasswordDialog;
import com.opl.pharmavector.util.VectorUtils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements OnClickListener {
    String newVersion, currentVersion;
    private EditText user, pass;
    private TextView versionname;
    private Button mSubmit, mShare, mChangepassword;
    private SessionManager session;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    public int k;
    public static final String LOGIN_URL = BASE_URL + "vector_login/vector_login.php";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_MESSAGE_2 = "message_2";
    public static final String TAG_MESSAGE_3 = "message_3";
    public static final String TAG_MESSAGE_new_version = "new_version";
    Toast toast;
    Toast toast2;
    Toast toast3;
    Toast toast_error;
    private DatabaseHandler db;
    private String f_name, s_name, t_name, ff_type;
    private static final String TAG = Login.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public String regId;
    public static String version, user_ff_type;
    AppUpdaterUtils appUpdaterUtils;
    AppUpdater appUpdater;
    PreferenceManager preferenceManager;
    private int count;
    public String tempPassword, tmpLocation, tempLogin, tempDtl, tempRole, tmpFFtype, tmpvectorVersion, vector_version, tmpEmpName, tmpEmpCode, tempDesignation;
    public static String vectorToken = "xxxx";
    public String globalempName, globalempCode, build_model, build_brand, build_manufac, build_id, build_device, build_version, designation;
    AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 123;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_login);

        getDeviceDetails();
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.d("Login", currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("Login", e.toString());
        }
        statusBarHide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initViews();

        //AppUpdateManager mAppUpdateManager = AppUpdateManagerFactory.create(Login.this);
        mAppUpdateManager = AppUpdateManagerFactory.create(Login.this);
        //Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();
        //mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(result -> {
        appUpdateInfoTask.addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Update available").setMessage("Check out the latest version of Vector?")
                        .setPositiveButton("Update now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(VectorUtils.googlePlayLink)));
                                } catch (android.content.ActivityNotFoundException exception) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(VectorUtils.googlePlayLink)));
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
                //makeUpdateDialog(mContext).show();
//                try {
//                    mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.FLEXIBLE, Login.this, RC_APP_UPDATE);
//                    Log.d("appUpdate", "update called!");
//                } catch (IntentSender.SendIntentException e) {
//                    Log.d("appUpdate", e.toString());
//                    throw new RuntimeException(e);
//                }
                //mAppUpdateManager.startUpdateFlowForResult(result, activityResultLauncher, AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build());
                //activityResultLauncher.launch(result.);
            }
        });
        //mAppUpdateManager.registerListener(listener);
        firebaseEvent();
    }

//    InstallStateUpdatedListener listener = state -> {
//        if (state.installStatus() == InstallStatus.DOWNLOADED) {
//            popupSnackbarForCompleteUpdate();
//        }
//    };

    // Displays the snackbar notification and call to action.
    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", view -> mAppUpdateManager.completeUpdate());
        snackbar.setActionTextColor(
                getResources().getColor(android.R.color.holo_red_dark));
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RC_APP_UPDATE) {
                Log.d("appUpdate", "Update flow failed! Result code: " + resultCode + " :: " + data);
            }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mAppUpdateManager.unregisterListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(getApplicationContext());

//        mAppUpdateManager.getAppUpdateInfo()
//                .addOnSuccessListener(appUpdateInfo -> {
//                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
//                        popupSnackbarForCompleteUpdate();
//                    }
//                });
    }

    private void initAppUpdate() {
        appUpdaterUtils = new AppUpdaterUtils(this)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        Log.e("isUpdateAvailable=====>", String.valueOf(isUpdateAvailable));
                        version = update.getLatestVersion();
                        vector_version = update.getLatestVersion();
                        Log.e("Version=====>", version);
                        Log.e("vector_version=====>", vector_version);
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {}
                });
        appUpdaterUtils.start();
        appUpdater = new AppUpdater(this);
        appUpdater.start();
        new AppUpdater(this)
                .setTitleOnUpdateAvailable("Update available")
                .setContentOnUpdateAvailable("Check out the latest version of Vector")
                .setButtonUpdate("Update now?")
                .setButtonDismiss("Maybe later")
                .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                .setDisplay(Display.SNACKBAR)
                .showAppUpdated(true)
                .setCancelable(false);

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
        }
    }

    private void statusBarHide() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @SuppressLint({"ShowToast", "SetTextI18n"})
    private void initViews() {
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        versionname = findViewById(R.id.versionname);

        if (!currentVersion.isEmpty()) {
            versionname.setText(currentVersion);
        }
        mSubmit = findViewById(R.id.login);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/acme.ttf");
        db = new DatabaseHandler(this);
        toast = Toast.makeText(Login.this, "Authentication failed. please try again.", Toast.LENGTH_LONG);
        toast2 = Toast.makeText(Login.this, "Offline log in.", Toast.LENGTH_LONG);
        toast3 = Toast.makeText(Login.this, "offline log in fail.", Toast.LENGTH_LONG);
        toast_error = Toast.makeText(Login.this, "Authentication failed. Please try again !", Toast.LENGTH_LONG);
        mChangepassword = findViewById(R.id.changepassword);
        user.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        user.setTypeface(fontFamily);
        pass.setTypeface(fontFamily);
        mChangepassword.setTypeface(fontFamily);
        mSubmit.setTypeface(fontFamily);
        mSubmit.setOnClickListener(this);
        mChangepassword.setOnClickListener(this);
        session = new SessionManager(getApplicationContext());
        preferenceManager = new PreferenceManager(this);
        count = preferenceManager.getTasbihCounter();
        Log.e("loginCount----->", String.valueOf(count));

        if (count == 1) {
            tempLogin = preferenceManager.getusername();
            tempPassword = preferenceManager.getpassword();
            tempRole = preferenceManager.getuserrole();
            tempDtl = preferenceManager.getuserdtl();
            tmpFFtype = preferenceManager.getfftype();
            tmpvectorVersion = preferenceManager.getcurrentVersion();
            tmpEmpName = preferenceManager.getexecutive_name();
            tmpEmpCode = preferenceManager.getemp_code();
            tempDesignation = preferenceManager.getDesignation();

            Intent i = new Intent(Login.this, Dashboard.class);  // Dashboard change in API call (Below) + Master Code
            //Intent i = new Intent(Login.this, Dashboard.class);
            i.putExtra("UserName", tempLogin);
            i.putExtra("UserName_1", tempDtl);
            i.putExtra("UserName_2", tempDtl);
            i.putExtra("new_version", "Vector");
            i.putExtra("message_3", tempRole);
            i.putExtra("password", tempPassword);
            i.putExtra("ff_type", tmpFFtype);
            i.putExtra("vector_version", tmpvectorVersion);
            i.putExtra("emp_name", tmpEmpName);
            i.putExtra("emp_code", tmpEmpCode);
            i.putExtra("emp_design", tempDesignation);

            preferenceManager.setTasbihCounter(1);
            preferenceManager.setusername(tempLogin);
            preferenceManager.setpassword(tempPassword);
            preferenceManager.setuserrole(tempRole);
            preferenceManager.setuserdtl(tempDtl);
            preferenceManager.setfftype(tmpFFtype);
            preferenceManager.setcurrentVersion(tmpvectorVersion);
            preferenceManager.setexecutive_name(tmpEmpName);
            preferenceManager.setemp_code(tmpEmpCode);
            preferenceManager.setDesignation(tempDesignation);
            startActivity(i);
        } else if (count == 2) {
            tempLogin = preferenceManager.getusername();
            tempPassword = preferenceManager.getpassword();
            tempRole = preferenceManager.getuserrole();
            tempDtl = preferenceManager.getuserdtl();
            tmpFFtype = preferenceManager.getfftype();
            tmpvectorVersion = preferenceManager.getcurrentVersion();
            tmpEmpName = preferenceManager.getexecutive_name();
            tmpEmpCode = preferenceManager.getemp_code();
            tempDesignation = preferenceManager.getDesignation();

            Intent i = new Intent(Login.this, AmDashboard.class);
            i.putExtra("UserName", tempLogin);
            i.putExtra("UserName_1", tempDtl);
            i.putExtra("UserName_2", tempDtl);
            i.putExtra("new_version", "Vector");
            i.putExtra("message_3", tempRole);
            i.putExtra("password", tempPassword);
            i.putExtra("ff_type", tmpFFtype);
            i.putExtra("vector_version", tmpvectorVersion);
            i.putExtra("emp_name", tmpEmpName);
            i.putExtra("emp_code", tmpEmpCode);
            i.putExtra("emp_design", tempDesignation);

            preferenceManager.setTasbihCounter(2);
            preferenceManager.setusername(tempLogin);
            preferenceManager.setpassword(tempPassword);
            preferenceManager.setuserrole(tempRole);
            preferenceManager.setuserdtl(tempDtl);
            preferenceManager.setfftype(tmpFFtype);
            preferenceManager.setcurrentVersion(tmpvectorVersion);
            preferenceManager.setexecutive_name(tmpEmpName);
            preferenceManager.setemp_code(tmpEmpCode);
            preferenceManager.setDesignation(tempDesignation);
            startActivity(i);
        } else if (count == 3) {
            tempLogin = preferenceManager.getusername();
            tempPassword = preferenceManager.getpassword();
            tempRole = preferenceManager.getuserrole();
            tempDtl = preferenceManager.getuserdtl();
            tmpFFtype = preferenceManager.getfftype();
            tmpvectorVersion = preferenceManager.getcurrentVersion();
            tmpEmpName = preferenceManager.getexecutive_name();
            tmpEmpCode = preferenceManager.getemp_code();
            tempDesignation = preferenceManager.getDesignation();

            Intent i = new Intent(Login.this, RmDashboard.class);
            i.putExtra("UserName", tempLogin);
            i.putExtra("UserName_1", tempDtl);
            i.putExtra("UserName_2", tempDtl);
            i.putExtra("new_version", "Vector");
            i.putExtra("message_3", tempRole);
            i.putExtra("password", tempPassword);
            i.putExtra("ff_type", tmpFFtype);
            i.putExtra("vector_version", tmpvectorVersion);
            i.putExtra("emp_name", tmpEmpName);
            i.putExtra("emp_code", tmpEmpCode);
            i.putExtra("emp_design", tempDesignation);

            preferenceManager.setTasbihCounter(3);
            preferenceManager.setusername(tempLogin);
            preferenceManager.setpassword(tempPassword);
            preferenceManager.setuserrole(tempRole);
            preferenceManager.setuserdtl(tempDtl);
            preferenceManager.setfftype(tmpFFtype);
            preferenceManager.setcurrentVersion(tmpvectorVersion);
            preferenceManager.setexecutive_name(tmpEmpName);
            preferenceManager.setemp_code(tmpEmpCode);
            preferenceManager.setDesignation(tempDesignation);
            //A Log.e("getVal==>", tempDtl);
            startActivity(i);
        } else if (count == 4) {
            tempLogin = preferenceManager.getusername();
            tempPassword = preferenceManager.getpassword();
            tempRole = preferenceManager.getuserrole();
            tempDtl = preferenceManager.getuserdtl();
            tmpFFtype = preferenceManager.getfftype();
            tmpvectorVersion = preferenceManager.getcurrentVersion();
            tmpEmpName = preferenceManager.getexecutive_name();
            tmpEmpCode = preferenceManager.getemp_code();
            tempDesignation = preferenceManager.getDesignation();

            Intent i = new Intent(Login.this, AssistantManagerDashboard.class);
            i.putExtra("UserName", tempLogin);
            i.putExtra("UserName_1", tempDtl);
            i.putExtra("UserName_2", tempDtl);
            i.putExtra("new_version", "Vector");
            i.putExtra("message_3", tempRole);
            i.putExtra("password", tempPassword);
            i.putExtra("ff_type", tmpFFtype);
            i.putExtra("vector_version", tmpvectorVersion);
            i.putExtra("emp_name", tmpEmpName);
            i.putExtra("emp_code", tmpEmpCode);
            i.putExtra("emp_design", tempDesignation);

            preferenceManager.setTasbihCounter(4);
            preferenceManager.setusername(tempLogin);
            preferenceManager.setpassword(tempPassword);
            preferenceManager.setuserrole(tempRole);
            preferenceManager.setuserdtl(tempDtl);
            preferenceManager.setfftype(tmpFFtype);
            preferenceManager.setcurrentVersion(tmpvectorVersion);
            preferenceManager.setexecutive_name(tmpEmpName);
            preferenceManager.setemp_code(tmpEmpCode);
            preferenceManager.setDesignation(tempDesignation);
            startActivity(i);
        } else if (count == 5) {
            tempLogin = preferenceManager.getusername();
            tempPassword = preferenceManager.getpassword();
            tempRole = preferenceManager.getuserrole();
            tempDtl = preferenceManager.getuserdtl();
            tmpFFtype = preferenceManager.getfftype();
            tmpvectorVersion = preferenceManager.getcurrentVersion();
            tmpEmpName = preferenceManager.getexecutive_name();
            tmpEmpCode = preferenceManager.getemp_code();
            tempDesignation = preferenceManager.getDesignation();

            Intent i = new Intent(Login.this, SalesManagerDashboard.class);
            i.putExtra("UserName", tempLogin);
            i.putExtra("UserName_1", tempDtl);
            i.putExtra("UserName_2", tempDtl);
            i.putExtra("new_version", "Vector");
            i.putExtra("message_3", tempRole);
            i.putExtra("password", tempPassword);
            i.putExtra("ff_type", tmpFFtype);
            i.putExtra("vector_version", tmpvectorVersion);
            i.putExtra("emp_name", tmpEmpName);
            i.putExtra("emp_code", tmpEmpCode);
            i.putExtra("emp_design", tempDesignation);

            preferenceManager.setTasbihCounter(5);
            preferenceManager.setusername(tempLogin);
            preferenceManager.setpassword(tempPassword);
            preferenceManager.setuserrole(tempRole);
            preferenceManager.setuserdtl(tempDtl);
            preferenceManager.setfftype(tmpFFtype);
            preferenceManager.setcurrentVersion(tmpvectorVersion);
            preferenceManager.setexecutive_name(tmpEmpName);
            preferenceManager.setemp_code(tmpEmpCode);
            preferenceManager.setDesignation(tempDesignation);
            startActivity(i);
        }
        else if (count == 6) {
            tempLogin = preferenceManager.getusername();
            tempPassword = preferenceManager.getpassword();
            tempRole = preferenceManager.getuserrole();
            tempDtl = preferenceManager.getuserdtl();
            tmpFFtype = preferenceManager.getfftype();
            tmpvectorVersion = preferenceManager.getcurrentVersion();
            tmpEmpName = preferenceManager.getexecutive_name();
            tmpEmpCode = preferenceManager.getemp_code();
            tempDesignation = preferenceManager.getDesignation();

            Intent i = new Intent(Login.this, GMDashboard1.class);
            i.putExtra("UserName", tempLogin);
            i.putExtra("UserName_1", tempDtl);
            i.putExtra("UserName_2", tempDtl);
            i.putExtra("new_version", "Vector");
            i.putExtra("message_3", tempRole);
            i.putExtra("password", tempPassword);
            i.putExtra("ff_type", "AD");
            i.putExtra("vector_version", tmpvectorVersion);
            i.putExtra("emp_name", tmpEmpName);
            i.putExtra("emp_code", tmpEmpCode);
            i.putExtra("emp_design", tempDesignation);
            Log.d("countff_type==>", "AD---" + tempLogin);

            preferenceManager.setTasbihCounter(6);
            preferenceManager.setusername(tempLogin);
            preferenceManager.setpassword(tempPassword);
            preferenceManager.setuserrole(tempRole);
            preferenceManager.setuserdtl(tempDtl);
            preferenceManager.setfftype(tmpFFtype);
            preferenceManager.setcurrentVersion(tmpvectorVersion);
            preferenceManager.setexecutive_name(tmpEmpName);
            preferenceManager.setemp_code(tmpEmpCode);
            preferenceManager.setDesignation(tempDesignation);
            startActivity(i);
        } else if (count == 7) {
            Log.e("preferenceManager->", "PMD rUN");
            Intent i = new Intent(Login.this, DashBoardPMD.class);
            i.putExtra("executive_code", preferenceManager.getuserrole());
            i.putExtra("executive_loc", preferenceManager.getexecutive_name());
            i.putExtra("executive_loccode", preferenceManager.getusername());
            i.putExtra("executive_locpass", preferenceManager.getpassword());
            i.putExtra("executive_type", preferenceManager.getfftype());
            i.putExtra("executive_name", preferenceManager.getuserdtl());
            preferenceManager.setTasbihCounter(7);

            preferenceManager.setusername(preferenceManager.getusername());
            preferenceManager.setpassword(preferenceManager.getpassword());
            preferenceManager.setuserrole(preferenceManager.getuserrole());
            preferenceManager.setuserdtl(preferenceManager.getuserdtl());
            preferenceManager.setfftype(preferenceManager.getfftype());
            preferenceManager.setexecutive_name(preferenceManager.getexecutive_name());
            startActivity(i);
        } else if (count == 8) {
            tempLogin = preferenceManager.getusername();
            tempPassword = preferenceManager.getpassword();
            tempRole = preferenceManager.getuserrole();
            tempDtl = preferenceManager.getuserdtl();
            tmpFFtype = preferenceManager.getfftype();
            tmpvectorVersion = preferenceManager.getcurrentVersion();
            tmpEmpName = preferenceManager.getexecutive_name();
            tmpEmpCode = preferenceManager.getemp_code();
            tempDesignation = preferenceManager.getDesignation();

            Intent i = new Intent(Login.this, DashboardMSD.class);
            i.putExtra("executive_code", preferenceManager.getuserrole());
            i.putExtra("executive_loc", preferenceManager.getexecutive_name());
            i.putExtra("executive_loccode", preferenceManager.getusername());
            i.putExtra("executive_locpass", preferenceManager.getpassword());
            i.putExtra("executive_type", preferenceManager.getfftype());
            i.putExtra("executive_name", preferenceManager.getuserdtl());
            i.putExtra("emp_design", tempDesignation);
            preferenceManager.setTasbihCounter(8);

            preferenceManager.setusername(preferenceManager.getusername());
            preferenceManager.setpassword(preferenceManager.getpassword());
            preferenceManager.setuserrole(preferenceManager.getuserrole());
            preferenceManager.setuserdtl(preferenceManager.getuserdtl());
            preferenceManager.setfftype(preferenceManager.getfftype());
            preferenceManager.setexecutive_name(preferenceManager.getexecutive_name());
            preferenceManager.setDesignation(tempDesignation);
            startActivity(i);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                session.setLogin(true);
                if (NetInfo.isOnline(getBaseContext())) {
                    if (user.getText().toString().trim().equals("")) {
                        user.setError("Username is required!");
                    } else if (pass.getText().toString().trim().equals("")) {
                        pass.setError("Password is required");
                    } else {
                        postSingIn("signIn");
                    }
                } else {
                    String username = user.getText().toString();
                    String password = pass.getText().toString();

                    if (db.checkUserLogin(username, password)) {
                        Intent i = new Intent(Login.this, Offlinereadcomments.class);
                        i.putExtra("UserName", username);
                        i.putExtra("UserName_1", username);
                        i.putExtra("new_version", 4);
                        startActivity(i);
                    } else {
                        Toast.makeText(getBaseContext(), "Username or password is incorrect", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.changepassword:
                Intent i = new Intent(Login.this, Changepassword.class);
                i.putExtra("changepassword", "changepassword");
                startActivity(i);
                break;
            default:
                break;
        }
    }

    private void postSingIn(final String key) {
        tempLogin = user.getText().toString().trim();
        tempPassword = pass.getText().toString().trim();
        Log.e("sendData-->", tempLogin + "==" + tempPassword);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login to Vector...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.vectorlogin(tempLogin, tempPassword, vectorToken);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(@NonNull Call<Patient> call, @NonNull Response<Patient> response) {
                progressDialog.dismiss();
                assert response.body() != null;
                int success = response.body().getSuccess();
                String value = response.body().getValue();
                String message = response.body().getMassage();
                String message_1 = response.body().getTerritory_name();
                String message_2 = response.body().getTerritory_name();
                String message_3 = response.body().getFF_role();
                String mpo_ff_type = response.body().getFF_type();
                String new_version = response.body().getnew_version();
                globalempName = response.body().getFirst_name();
                globalempCode = response.body().getLast_name();
                designation = response.body().getDesignation();

                if (!Objects.equals(vectorToken, "xxxx") && globalempCode != null) {
                    userLog("", message, globalempCode);
                }

                if (success == 2) {
                    globalempName = response.body().getFirst_name();
                    globalempCode = response.body().getLast_name();

                    switch (message_3) {
                        case "MPO": {
                            Intent i = new Intent(Login.this, Dashboard.class);
                            //Intent i = new Intent(Login.this, DashboardNew.class); // New Dashboard (28/12/23)
                            //Intent i = new Intent(Login.this, MPODashboard.class);
                            i.putExtra("UserName", message);
                            i.putExtra("UserName_1", message_1);
                            i.putExtra("UserName_2", message_2);
                            i.putExtra("new_version", new_version);
                            i.putExtra("message_3", message_3);
                            i.putExtra("ff_type", mpo_ff_type);
                            i.putExtra("password", tempPassword);
                            i.putExtra("vector_version", vector_version);
                            i.putExtra("emp_name", globalempName);
                            i.putExtra("emp_code", globalempCode);
                            i.putExtra("designation", designation);
                            db.logindata();
                            f_name = user.getText().toString();
                            s_name = pass.getText().toString();
                            db.addContacts(new Contact(f_name, s_name, message_2));
                            preferenceManager.setTasbihCounter(1);
                            preferenceManager.setusername(tempLogin);
                            preferenceManager.setpassword(tempPassword);
                            preferenceManager.setuserrole(message_3);
                            preferenceManager.setuserdtl(message_2);
                            preferenceManager.setfftype(mpo_ff_type);
                            preferenceManager.setcurrentVersion(vector_version);
                            preferenceManager.setexecutive_name(globalempName);
                            preferenceManager.setemp_code(globalempCode);
                            preferenceManager.setDesignation(designation);
                            startActivity(i);
                            break;
                        }
                        case "FM": {
                            user_ff_type = mpo_ff_type;
                            Intent i = new Intent(Login.this, AmDashboard.class);
                            i.putExtra("UserName", message);
                            i.putExtra("UserName_1", message_1);
                            i.putExtra("UserName_2", message_2);
                            i.putExtra("new_version", new_version);
                            i.putExtra("message_3", message_3);
                            i.putExtra("ff_type", user_ff_type);
                            i.putExtra("vector_version", vector_version);
                            i.putExtra("emp_name", globalempName);
                            i.putExtra("emp_code", globalempCode);
                            i.putExtra("designation", designation);
                            db.logindata();
                            f_name = user.getText().toString();
                            s_name = pass.getText().toString();
                            db.addContacts(new Contact(f_name, s_name, message_2));
                            preferenceManager.setTasbihCounter(2);
                            preferenceManager.setusername(tempLogin);
                            preferenceManager.setpassword(tempPassword);
                            preferenceManager.setuserrole(message_2);
                            preferenceManager.setuserdtl(message_3);
                            preferenceManager.setfftype(ff_type);
                            preferenceManager.setcurrentVersion(vector_version);
                            preferenceManager.setexecutive_name(globalempName);
                            preferenceManager.setemp_code(globalempCode);
                            preferenceManager.setDesignation(designation);
                            startActivity(i);
                            break;
                        }
                        case "RM": {
                            user_ff_type = mpo_ff_type;
                            Intent i = new Intent(Login.this, RmDashboard.class);
                            i.putExtra("UserName", message);
                            i.putExtra("UserName_1", message_1);
                            i.putExtra("UserName_2", message_2);
                            i.putExtra("new_version", new_version);
                            i.putExtra("message_3", message_3);
                            i.putExtra("ff_type", user_ff_type);
                            i.putExtra("vector_version", vector_version);
                            i.putExtra("emp_name", globalempName);
                            i.putExtra("emp_code", globalempCode);
                            i.putExtra("designation", designation);
                            db.logindata();
                            f_name = user.getText().toString();
                            s_name = pass.getText().toString();
                            db.addContacts(new Contact(f_name, s_name, message_2));
                            preferenceManager.setTasbihCounter(3);
                            preferenceManager.setusername(tempLogin);
                            preferenceManager.setpassword(tempPassword);
                            preferenceManager.setuserrole(message_2);
                            preferenceManager.setuserdtl(message_3);
                            preferenceManager.setfftype(ff_type);
                            preferenceManager.setcurrentVersion(vector_version);
                            preferenceManager.setexecutive_name(globalempName);
                            preferenceManager.setemp_code(globalempCode);
                            preferenceManager.setDesignation(designation);
                            Log.e("user_ff_type", user_ff_type);
                            startActivity(i);
                            break;
                        }
                        case "AM": {
                            user_ff_type = mpo_ff_type;
                            Intent i = new Intent(Login.this, AssistantManagerDashboard.class);
                            i.putExtra("UserName", message);
                            i.putExtra("UserName_1", message_1);
                            i.putExtra("UserName_2", message_1);
                            i.putExtra("new_version", new_version);
                            i.putExtra("message_3", message_3);
                            i.putExtra("ff_type", user_ff_type);
                            i.putExtra("vector_version", vector_version);
                            i.putExtra("emp_name", globalempName);
                            i.putExtra("emp_code", globalempCode);
                            i.putExtra("designation", designation);
                            preferenceManager.setTasbihCounter(4);
                            preferenceManager.setusername(tempLogin);
                            preferenceManager.setpassword(tempPassword);
                            preferenceManager.setuserrole(message_2);
                            preferenceManager.setuserdtl(message_1);
                            preferenceManager.setfftype(ff_type);
                            preferenceManager.setcurrentVersion(vector_version);
                            preferenceManager.setexecutive_name(globalempName);
                            preferenceManager.setemp_code(globalempCode);
                            preferenceManager.setDesignation(designation);
                            Log.e("passed==>", message_1);
                            startActivity(i);
                            break;
                        }
                        case "SM": {
                            user_ff_type = mpo_ff_type;
                            Intent i = new Intent(Login.this, SalesManagerDashboard.class);
                            i.putExtra("UserName", message);
                            i.putExtra("UserName_1", message_1);
                            i.putExtra("UserName_2", message_1);
                            i.putExtra("new_version", new_version);
                            i.putExtra("message_3", message_3);
                            i.putExtra("ff_type", user_ff_type);
                            i.putExtra("vector_version", vector_version);
                            i.putExtra("emp_name", globalempName);
                            i.putExtra("emp_code", globalempCode);
                            i.putExtra("designation", designation);
                            db.logindata();
                            f_name = user.getText().toString();
                            s_name = pass.getText().toString();
                            db.addContacts(new Contact(f_name, s_name, message_2));
                            preferenceManager.setTasbihCounter(5);
                            preferenceManager.setusername(tempLogin);
                            preferenceManager.setpassword(tempPassword);
                            preferenceManager.setuserrole(message_2);
                            preferenceManager.setuserdtl(message_1);
                            preferenceManager.setfftype(ff_type);
                            preferenceManager.setcurrentVersion(vector_version);
                            preferenceManager.setexecutive_name(globalempName);
                            preferenceManager.setemp_code(globalempCode);
                            preferenceManager.setDesignation(designation);
                            startActivity(i);
                            break;
                        }
                        case "AD": {
                            Intent i = new Intent(Login.this, GMDashboard1.class);
                            i.putExtra("UserName", message);
                            i.putExtra("UserName_1", message_1);
                            i.putExtra("UserName_2", message_2);
                            i.putExtra("new_version", new_version);
                            i.putExtra("message_3", message_3);
                            i.putExtra("ff_type", ff_type);
                            i.putExtra("vector_version", vector_version);
                            i.putExtra("emp_name", globalempName);
                            i.putExtra("emp_code", globalempCode);
                            i.putExtra("designation", designation);
                            db.logindata();
                            f_name = user.getText().toString();
                            s_name = pass.getText().toString();
                            db.addContacts(new Contact(f_name, s_name, message_2));
                            preferenceManager.setTasbihCounter(6);
                            preferenceManager.setusername(tempLogin);
                            preferenceManager.setpassword(tempPassword);
                            preferenceManager.setuserrole(message_2);
                            preferenceManager.setuserdtl(message_3);
                            preferenceManager.setfftype(ff_type);
                            preferenceManager.setcurrentVersion(vector_version);
                            preferenceManager.setexecutive_name(globalempName);
                            preferenceManager.setemp_code(globalempCode);
                            preferenceManager.setAdmin_Code(globalempCode);
                            preferenceManager.setAdmin_Name(globalempName);
                            preferenceManager.setAdmin_Terri(message_2);
                            preferenceManager.setDesignation(designation);
                            startActivity(i);
                            break;
                        }
                        case "PMD": {
                            Intent i = new Intent(Login.this, DashBoardPMD.class);
                            i.putExtra("executive_code", message);
                            i.putExtra("executive_loc", message_1);
                            i.putExtra("executive_loccode", tempLogin);
                            i.putExtra("executive_locpass", tempPassword);
                            i.putExtra("executive_type", mpo_ff_type);
                            i.putExtra("executive_name", new_version);
                            i.putExtra("designation", designation);
                            f_name = user.getText().toString();
                            s_name = pass.getText().toString();
                            db.addContacts(new Contact(f_name, s_name, message_2));
                            preferenceManager.setTasbihCounter(7);
                            preferenceManager.setusername(tempLogin); // LOCATION CODE
                            preferenceManager.setpassword(tempPassword); // LOCATION PASSWORD
                            preferenceManager.setuserrole(message); // EXECUTIVE CODE
                            preferenceManager.setuserdtl(new_version); // EXECUTIVE NAME
                            preferenceManager.setfftype(mpo_ff_type); // EXECUTIVE TYPE
                            preferenceManager.setexecutive_name(message_1); // EXECUTIVE LOCATION DETAILS
                            preferenceManager.setDesignation(designation);
                            startActivity(i);
                            break;
                        }
                        case "MSD": {
                            Intent i = new Intent(Login.this, DashboardMSD.class);
                            i.putExtra("executive_code", message);
                            i.putExtra("executive_loc", message_1);
                            i.putExtra("executive_loccode", tempLogin);
                            i.putExtra("executive_locpass", tempPassword);
                            i.putExtra("executive_type", mpo_ff_type);
                            i.putExtra("executive_name", globalempName);
                            i.putExtra("designation", designation);
                            f_name = user.getText().toString();
                            s_name = pass.getText().toString();
                            db.addContacts(new Contact(f_name, s_name, message_2));
                            preferenceManager.setTasbihCounter(8);
                            preferenceManager.setusername(tempLogin); // LOCATION CODE
                            preferenceManager.setpassword(tempPassword); // LOCATION PASSWORD
                            preferenceManager.setuserrole(message); // EXECUTIVE CODE
                            preferenceManager.setuserdtl(new_version); // EXECUTIVE NAME
                            preferenceManager.setfftype(mpo_ff_type); // EXECUTIVE TYPE
                            preferenceManager.setexecutive_name(globalempName); // EXECUTIVE LOCATION DETAILS
                            preferenceManager.setDesignation(designation);
                            startActivity(i);
                            break;
                        }
                    }
                } else if (success == 1) {
                    Intent i = new Intent(Login.this, Changepassword.class);
                    i.putExtra("UserName", message);
                    i.putExtra("UserName_1", message_1);
                    i.putExtra("UserName_2", message_2);
                    i.putExtra("new_version", new_version);
                    i.putExtra("FF_ROLE", message_3);
                    startActivity(i);
                } else {
                    errorSnack();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Patient> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(getWindow().getDecorView().getRootView(), "Temporarily Unavailable", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void errorSnack() {
        new Thread(() -> Login.this.runOnUiThread(() -> {
            String message;
            message = "Username or Password is incorrect";
            Toasty.error(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
        })).start();
    }

    private void firebaseEvent() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Login.this, instanceIdResult -> {
            vectorToken = instanceIdResult.getToken();
            Log.e("LoginVectorToken-->", vectorToken);
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
                if (intent.getAction().equals(com.opl.pharmavector.app.Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(com.opl.pharmavector.app.Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                }
            }
        };
    }

    private void userLog(final String key, String employeeCode, String empCode) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.userData(key, vector_version, vectorToken, "", "", build_model, build_brand, employeeCode, "", empCode);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        k = 0;
    }

    @Override
    public void onBackPressed() {
        k++;
        if (k == 1) {

        } else {
            Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
            homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
            homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeScreenIntent);
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public void passwordForgotten(View view) {
        new ResetPasswordDialog().show(getSupportFragmentManager(), "Dialog");
    }

    private void showSnack() {
        new Thread(() -> Login.this.runOnUiThread(new Runnable() {
            public void run() {
                String message;
                message = "The user name or password is incorrect";
                Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
            }
        })).start();
    }
}

