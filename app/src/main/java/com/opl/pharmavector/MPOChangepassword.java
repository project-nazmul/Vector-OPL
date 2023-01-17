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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MPOChangepassword extends Activity implements OnClickListener{

    private EditText user, pass, Currentuserid ,Currentuserpassword,New_password1,New_password2;
    private Button  mChangepassword;
    private TextView error_userid,Urroruser_password,Erroruser_newpassword;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = BASE_URL+"vector_login/changepassword.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public String FF_ROLE;


    public String am_code;
    Toast toast;
    Toast toast1;
    Toast toast2;
    Toast toast3;
    Toast toast4;
    Toast toast5;
    Toast toast6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpochangepassword);
        Currentuserid = (EditText)findViewById(R.id.currentuserid);
        Currentuserpassword = (EditText)findViewById(R.id.currentuserpassword);
        New_password1 = (EditText)findViewById(R.id.new_password1);
        New_password2 = (EditText)findViewById(R.id.new_password2);
        mChangepassword = (Button)findViewById(R.id.changepassword);
        mChangepassword.setBackgroundResource(R.drawable.default_button);
        mChangepassword.setOnClickListener(this);
        error_userid = (TextView) findViewById(R.id.erroruserid);
        Urroruser_password = (TextView) findViewById(R.id.erroruser_password);
        Erroruser_newpassword = (TextView) findViewById(R.id.erroruser_newpassword);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        // error_userid.setTypeface(fontFamily);
        //  error_userid.setText("\uf08b");
        //Urroruser_password.setText("\uf08b");

        toast1 = Toast.makeText(MPOChangepassword.this, "Please enter your old Password !!!.", Toast.LENGTH_LONG);
        toast2 = Toast.makeText(MPOChangepassword.this, "Please enter your Userid !!!.", Toast.LENGTH_LONG);
        toast3 = Toast.makeText(MPOChangepassword.this, "Please enter your Newpassword !!!.", Toast.LENGTH_LONG);
        toast4 = Toast.makeText(MPOChangepassword.this, "Please confirm Newpassword !!!.", Toast.LENGTH_LONG);
        toast5 = Toast.makeText(MPOChangepassword.this, "The passwords you entered does not match. please try again!!!.", Toast.LENGTH_LONG);
        toast6 = Toast.makeText(MPOChangepassword.this, "New password must be greater than 4 characters !!!.", Toast.LENGTH_LONG);
        Button back_btn = (Button) findViewById(R.id.back);

        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        //back_btn.setTypeface(fontFamily);
        // back_btn.setText("\uf060 ");// &#xf060

        Bundle b = getIntent().getExtras();
        final String userName = b.getString("UserName");
        final String UserName_2 = b.getString("UserName_2");

      //  final String mpo_code = b.getString("UserName");
        final String mpo_pwd = b.getString("mpo_pwd");

        final String mpo_code = b.getString("mpo_code");
         am_code = b.getString("am_code");


       //   Toast.makeText(MPOChangepassword.this,"MPO PASSWORD"+mpo_code, Toast.LENGTH_LONG).show();








        Currentuserid.setText(mpo_code);
        Currentuserpassword.setText(mpo_pwd);



        New_password1.setFocusableInTouchMode(true);
        New_password1.setFocusable(true);
        New_password1.setFocusableInTouchMode(true);
        New_password1.requestFocus();




        final String new_version = b.getString("new_version");
        FF_ROLE = "MPO";





        Log.d("passtochangepass", userName+UserName_2+new_version+FF_ROLE+"onok");

        /*---------------back----------------*/

        /*---------------Update Sells----------*/




        back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(MPOChangepassword.this, VacantMpoPwd.class);


                        i.putExtra("userName", am_code);
                        i.putExtra("new_version", am_code);
                        i.putExtra("UserName", am_code);



                        System.out.println("userName " );
                        Log.d("MPOChangepassword","Login");
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });







        /*----------------------------------------*/
        /*---------------back----end------------*/

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub




        String cuserid = Currentuserid.getText().toString();
        String cpassword = Currentuserpassword.getText().toString();
        String New_pass1 = New_password1.getText().toString();
        String New_pass2 = New_password2.getText().toString();

        Log.d("cuserid", cuserid);
        Log.d("cpassword", cpassword);
        Log.d("New_pass1", New_pass1);
        Log.d("New_pass2", New_pass2);
        int size=New_pass1.length();

        if ((Currentuserid.getText().toString().trim().equals("")))
        {
            Log.d("userid and password!", "Please Fill up User id and password");
            // error_userid.setText("Please Fill up User id !!!" );
            toast2.show();
            //return null;

            // error_userid.setText("Please Select payment mode by click! " );
            //error_payment.setError( "Please Select payment mode by click!" );
        }else if(Currentuserpassword.getText().toString().trim().equals("")) {
            //Urroruser_password.setText("Please Fill up Password !!!" );
            toast1.show();

        }
        else if(New_password1.getText().toString().trim().equals("")) {
            Log.d("password and password2!", "New Password Not Match");
            //  Erroruser_newpassword.setText("Please Fill up New Password and Retype New Password !!!" );
            // return null;

            toast3.show();
        } else if(size<4){
            toast6.show();
        }

        else if(New_password2.getText().toString().trim().equals("")) {
            Log.d("password and password2!", "New Password Not Match");
            // Erroruser_newpassword.setText("Please Fill up New Password and Retype New Password !!!" );
            toast4.show();
            // return null;
        }
        else if(New_pass1.equals(New_pass2)) {
            new CreateUser().execute();
            // toast3.show();
        }

        else {
            toast5.show();

            // new CreateUser().execute();
        }
    }

    // class CreateUser extends AsyncTask<String, String, String> {

    class CreateUser extends AsyncTask<String, String, String> {

        // AsyncTask<Void, Void, Void>


        boolean failure = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MPOChangepassword.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            @SuppressLint("WrongThread") String cuserid = Currentuserid.getText().toString();
            @SuppressLint("WrongThread") String cpassword = Currentuserpassword.getText().toString();
            @SuppressLint("WrongThread") String New_pass1 = New_password1.getText().toString();
            @SuppressLint("WrongThread") String New_pass2 = New_password2.getText().toString();







            Log.d("cuserid", cuserid);
            Log.d("cpassword", cpassword);
            Log.d("New_pass1", New_pass1);
            Log.d("New_pass2", New_pass2);
            Log.d("New_pass2", New_pass2);

            try {

                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("Currentuserid", cuserid));
                params.add(new BasicNameValuePair("Currentuserpassword", cpassword));
                params.add(new BasicNameValuePair("username", cuserid));
                params.add(new BasicNameValuePair("password", cpassword));

                params.add(new BasicNameValuePair("new_password1", New_pass1));
                params.add(new BasicNameValuePair("new_password2", New_pass2));
                params.add(new BasicNameValuePair("FF_ROLE", FF_ROLE));


                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                // full json response
                Log.d("Login attempt", json.toString());
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    finish();

                   //  Toast.makeText(MPOChangepassword.this, "Password changed" + success,Toast.LENGTH_LONG).show();


                    Intent i = new Intent(MPOChangepassword.this, VacantMpoPwd.class);


                    i.putExtra("userName", am_code);
                    i.putExtra("new_version", am_code);
                    i.putExtra("UserName", am_code);



                    System.out.println("userName " );
                    Log.d("MPOChangepassword","Login");
                    startActivity(i);







                    return json.getString(TAG_MESSAGE);
                } else if(success == 0){
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;


            // }
            //return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(MPOChangepassword.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }





    private void back() {
        Intent i = new Intent(MPOChangepassword.this, Login.class);
        startActivity(i);
        finish();

    }







}
