package com.opl.pharmavector.doctorservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.R;
import com.opl.pharmavector.adapter.CategoryAdapter6;
import com.opl.pharmavector.model.Category5;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser6;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
import static com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser6.selectedCategories4;


public class DoctorServiceFollowup extends AppCompatActivity {
    Context context;
    ArrayList<Category5> array_list;
    FavouriteCategoriesJsonParser6 categoryJsonParser;
    String categoriesCsv;
    String categoriesPhone;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, my_val;
    private String pc_approval_submit = BASE_URL+"doctor_service/doctor_service_ack_rcv.php";
    private String pc_cancel_submit = BASE_URL+"pc_conference_cancel_submit.php";

    public String conference_date, UserName_2;
    public String market_code;
    public String market_name;
    public String venue_name;
    public String pc_rmp_val;
    public String doc_val;
    public String inhouse_val;
    public String total_participant;
    public String venue_charge;
    public String food_per_person;
    public String food_total_bdt;
    public String miscell, miscell_bdt;
    public String impact, TOTAL_BUDGET;
    public String product_list;
    public String approval_user_role;
    public static String UserName;
    TextView user_show;
    Button button, button2, logback;
    ListView lv_approval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.mpo_doc_service_approval);
        initViews();


        new asyncTask_getCategories().execute();

        logback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
                backthred.start();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                categoriesCsv = FavouriteCategoriesJsonParser6.selectedCategories4.toString();
                categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);
                Thread server = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONParser jsonParser = new JSONParser();
                        Bundle b = getIntent().getExtras();
                        final String userName = b.getString("UserName");
                        final String UserName_2 = b.getString("UserName_2");
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("categoriesCsv", categoriesCsv));
                        params.add(new BasicNameValuePair("UserName", UserName));
                        JSONObject json = jsonParser.makeHttpRequest(pc_approval_submit, "POST", params);
                        try {
                            success = json.getInt(TAG_SUCCESS);
                            message = json.getString(TAG_MESSAGE);
                            Log.w("please wait TRY ...." + message, json.toString());
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.w("Please wait ...." + message, json.toString());
                        }
                        Intent in = getIntent();
                        Intent inten = getIntent();
                        Bundle bundle = in.getExtras();
                        inten.getExtras();
                        String MPO_CODE = bundle.getString("MPO_CODE");
                        String pc_sl_no = message;
                        Intent sameint = new Intent(DoctorServiceFollowup.this, DoctorServiceFollowup.class);
                        sameint.putExtra("UserName", UserName);
                        sameint.putExtra("UserName_2", UserName_2);
                        sameint.putExtra("Ord_NO", message);
                        sameint.putExtra("userName", UserName);
                        startActivity(sameint);
                        selectedCategories4.clear();

                    }
                });
                server.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                categoriesCsv = FavouriteCategoriesJsonParser6.selectedCategories4.toString();
                categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);
                ProgressDialog pDialog = new ProgressDialog(DoctorServiceFollowup.this);
                pDialog.setTitle("Please wait !");
                pDialog.setMessage("Sending Acknowledgement..");
                pDialog.setCancelable(false);
                pDialog.show();
                Bundle b = getIntent().getExtras();
                Thread server = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONParser jsonParser = new JSONParser();
                        Bundle b = getIntent().getExtras();
                        final String userName = b.getString("UserName");
                        final String UserName_2 = b.getString("UserName_2");
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("categoriesCsv", categoriesCsv));
                        params.add(new BasicNameValuePair("UserName", UserName));
                        JSONObject json = jsonParser.makeHttpRequest(pc_cancel_submit, "POST", params);
                        try {
                            success = json.getInt(TAG_SUCCESS);
                            message = json.getString(TAG_MESSAGE);
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            pDialog.dismiss();
                        }
                        Intent in = getIntent();
                        Intent inten = getIntent();
                        Bundle bundle = in.getExtras();
                        inten.getExtras();
                        String MPO_CODE = bundle.getString("MPO_CODE");
                        String pc_sl_no = message;
                        Intent sameint = new Intent(DoctorServiceFollowup.this, DoctorServiceFollowup.class);
                        sameint.putExtra("UserName", UserName);
                        sameint.putExtra("UserName_2", UserName_2);
                        sameint.putExtra("Ord_NO", message);
                        sameint.putExtra("userName", UserName);
                        startActivity(sameint);
                        selectedCategories4.clear();

                    }
                });
                server.start();

            }
        });

    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        button = findViewById(R.id.selectCategoryButton);
        button2 = findViewById(R.id.selectCategoryButton2);
        logback = findViewById(R.id.logback);
        logback.setTypeface(fontFamily);
        logback.setText("\uf08b");
        user_show = findViewById(R.id.user_show);
        context = this;
        Bundle b = getIntent().getExtras();
        UserName = b.getString("userName");
        UserName_2 = b.getString("UserName_2");
        user_show.setText(String.format("%s %s ", UserName, UserName_2));
    }

    public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait...");
            dialog.setMessage("Loading Doctor Service Data!");
            dialog.show();
            array_list = new ArrayList<Category5>();
            my_val = Dashboard.globalmpocode;
            categoryJsonParser = new FavouriteCategoriesJsonParser6();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            array_list = categoryJsonParser.getParsedCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            lv_approval = findViewById(R.id.category_approval);
            CategoryAdapter6 CategoryAdapter6 = new CategoryAdapter6(context, R.layout.adapter_doc_service_approval, array_list);
            lv_approval.setAdapter(CategoryAdapter6);
            super.onPostExecute(s);
            dialog.dismiss();
        }

    }


}















