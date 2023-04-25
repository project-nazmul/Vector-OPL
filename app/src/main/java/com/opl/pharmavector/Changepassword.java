package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import java.lang.String;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.opl.pharmavector.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Changepassword extends AppCompatActivity implements OnClickListener {
    private EditText user, pass, Currentuserid, Currentuserpassword, New_password1, New_password2;
    private Button mChangepassword;
    private TextView error_userid, Urroruser_password, Erroruser_newpassword;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = BASE_URL+"vector_login/changepassword.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public String FF_ROLE;
    Toast toast;
    Toast toast1;
    Toast toast2;
    Toast toast3;
    Toast toast4;
    Toast toast5;
    Toast toast6;
    ImageView user_profile_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        //setContentView(R.layout.activity_change_password);

        statusBarHide();
        Currentuserid = findViewById(R.id.currentuserid);
        Currentuserpassword = findViewById(R.id.currentuserpassword);
        New_password1 = findViewById(R.id.new_password1);
        New_password2 = findViewById(R.id.new_password2);
        mChangepassword = findViewById(R.id.changepassword);
        user_profile_photo = findViewById(R.id.user_profile_photo);
        mChangepassword.setOnClickListener(this);
        error_userid = findViewById(R.id.erroruserid);
        Urroruser_password = findViewById(R.id.erroruser_password);
        Erroruser_newpassword = findViewById(R.id.erroruser_newpassword);
        toast1 = Toast.makeText(Changepassword.this, "Please enter your old Password !!!.", Toast.LENGTH_LONG);
        toast2 = Toast.makeText(Changepassword.this, "Please enter your Userid !!!.", Toast.LENGTH_LONG);
        toast3 = Toast.makeText(Changepassword.this, "Please enter your Newpassword !!!.", Toast.LENGTH_LONG);
        toast4 = Toast.makeText(Changepassword.this, "Please confirm Newpassword !!!.", Toast.LENGTH_LONG);
        toast5 = Toast.makeText(Changepassword.this, "The passwords you entered does not match. please try again!!!.", Toast.LENGTH_LONG);
        toast6 = Toast.makeText(Changepassword.this, "New password must be greater than 4 characters !!!.", Toast.LENGTH_LONG);
        Button back_btn = (Button) findViewById(R.id.back);
        Bundle b = getIntent().getExtras();
        final String userName = b.getString("UserName");
        final String UserName_2 = b.getString("UserName_2");
        final String new_version = b.getString("new_version");
        FF_ROLE = b.getString("FF_ROLE");

        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Changepassword.this, Login.class);
                        startActivity(i);
                    }
                });
                mysells.start();
            }
        });
    }

    private void statusBarHide() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onClick(View v) {
        String cuserid = Currentuserid.getText().toString();
        String cpassword = Currentuserpassword.getText().toString();
        String New_pass1 = New_password1.getText().toString();
        String New_pass2 = New_password2.getText().toString();
        int size = New_pass1.length();

        if ((Currentuserid.getText().toString().trim().equals(""))) {
            toast2.show();
        } else if (Currentuserpassword.getText().toString().trim().equals("")) {
            toast1.show();
        } else if (New_password1.getText().toString().trim().equals("")) {
            toast3.show();
        } else if (size < 4) {
            toast6.show();
        } else if (New_password2.getText().toString().trim().equals("")) {
            toast4.show();
        } else if (New_pass1.equals(New_pass2)) {
            new CreateUser().execute();
        } else {
            toast5.show();
        }
    }

    class CreateUser extends AsyncTask<String, String, String> {
        boolean failure = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Changepassword.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // protected Void doInBackground(Void... arg0) {
            // Check for success tag
            int success;

            @SuppressLint("WrongThread") String cuserid = Currentuserid.getText().toString();
            @SuppressLint("WrongThread") String cpassword = Currentuserpassword.getText().toString();
            @SuppressLint("WrongThread") String New_pass1 = New_password1.getText().toString();
            @SuppressLint("WrongThread") String New_pass2 = New_password2.getText().toString();
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Currentuserid", cuserid));
                params.add(new BasicNameValuePair("Currentuserpassword", cpassword));
                params.add(new BasicNameValuePair("username", cuserid));
                params.add(new BasicNameValuePair("password", cpassword));
                params.add(new BasicNameValuePair("new_password1", New_pass1));
                params.add(new BasicNameValuePair("new_password2", New_pass2));
                params.add(new BasicNameValuePair("FF_ROLE", FF_ROLE));
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    finish();
                    Intent i = new Intent(Changepassword.this, Login.class);
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                } else if (success == 0) {
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Changepassword.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void back() {
        Intent i = new Intent(Changepassword.this, Login.class);
        startActivity(i);
        finish();
    }
}
