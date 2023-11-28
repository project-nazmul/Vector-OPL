package com.opl.pharmavector.util;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.chaos.view.PinView;
import com.google.android.material.snackbar.Snackbar;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;
import com.opl.pharmavector.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitNewPassActivity extends AppCompatActivity {
    private SmsVerifyCatcher smsVerifyCatcher;
    private ApiInterface apiInterface;
    public String sms_otp, confirm_password, phone_number, otpSLnumber, ffrole;
    public PinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_new_password);
        pinView = findViewById(R.id.pin_view);

        EditText new_password = findViewById(R.id.new_password);
        EditText confirm_new_password = findViewById(R.id.confirm_new_password);
        final Button btnVerify = (Button) findViewById(R.id.resetButton);

        Bundle bundle = getIntent().getExtras();
        phone_number= bundle.getString("phoneNumber");
        otpSLnumber= bundle.getString("otpSLnumber");
        ffrole= bundle.getString("ffrole");
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message); //parse verification code
                pinView.setText(code); //set code in edit text
                sms_otp=pinView.getText().toString();
                confirm_password=confirm_new_password.getText().toString();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms_otp=pinView.getText().toString();
                confirm_password=confirm_new_password.getText().toString();
                postOTPData();
            }
        });
    }

    private void postOTPData() {
        Log.e("passedvalues==>",sms_otp+"--"+otpSLnumber+"---"+ffrole+"---"+phone_number+"---"+confirm_password);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Password Updating...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.setnewpassword(sms_otp, otpSLnumber, ffrole, phone_number, confirm_password);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();
                Log.e("responseValue==>",value);
                Log.e("responseMessage==>",message);

                if (value.equals("1")) {
                    progressDialog.dismiss();
                    Snackbar.make(getWindow().getDecorView().getRootView(),message, Snackbar.LENGTH_LONG)
                            .setAction("Your Password is updated Successfully," +
                                    "Please Login using your Phone Number and New Password", null).show();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent createAccountIntent = new Intent(SubmitNewPassActivity.this, Login.class);
                            startActivity(createAccountIntent);
                        }
                    }, 2 * 1000);
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {}
        });
    }

    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void callNextScreenFromOTP(View view) {}
}


