package com.opl.pharmavector.msd_doc_support;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;


import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;


import com.opl.pharmavector.model.Patient;

import com.opl.pharmavector.msd_doc_support.adapter.MSDAdapter;
import com.opl.pharmavector.promomat.adapter.PromoAdapter;
import com.opl.pharmavector.promomat.adapter.RecyclerTouchListener;
import com.opl.pharmavector.promomat.model.Promo;
import com.opl.pharmavector.promomat.util.FixedGridLayoutManager;
import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocSupportFollowup extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public ProgressDialog pDialog;
    Button back_btn;
    public int success;
    public String message, ord_no;
    public TextView totqty, totval, mpo_code,fromdate, todate;
    public String userName_1, userName, active_string, act_desiredString,selected_service_no,selected_service_no_serial;
    public String from_date, to_date;
    TextView sproduct_name,sserial,sqnty1,ssellvelue,gval,user_show1,achivement,week1,week2,week3,week4;
    public String p_code, asm_flag, sm_flag, gm_flag,user_code, promo_type, user_flag,UserName_2;
    int scrollX = 0;
    RecyclerView rvCompany;
    HorizontalScrollView headerScroll;
    MSDAdapter promoAdapter;
    List<Promo> promoList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_service_followup);

        initViews();
        prepareMPOPromo();
        setUpRecyclerView();

        if (user_flag.equals("MPO")){
            rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    selected_service_no = promoList.get(position).getCode();
                    selected_service_no_serial = promoList.get(position).getSerial();

                    if (promoList.get(position).getWeek2().equals("Y") &&  promoList.get(position).getWeek3().equals("N") ){
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog();
                    } if (promoList.get(position).getWeek2().equals("Y") &&  promoList.get(position).getWeek3().equals("Y") ){
                        ViewDialog alert = new ViewDialog();
                        //alert.showDialog();
                        alert.approvedDialog();
                    } else if (promoList.get(position).getWeek2().equals("N")){
                        ViewDialog alert = new ViewDialog();
                        alert.alertDialog();
                    }
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }

        rvCompany.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX += dx;
                headerScroll.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void prepareMPOPromo() {
        pDialog = new ProgressDialog(DocSupportFollowup.this);
        pDialog.setMessage("Doctor Support Data Loading...");
        pDialog.setTitle("Please wait");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.msd_mpo_followup(user_code,user_flag);
        Log.e("passedParam==>",user_code+user_flag);
        promoList.clear();

        call.enqueue(new Callback<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse( Call<List<Patient>> call,  retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();
                if (response.isSuccessful()) {
                    for (int i = 0; i < giftitemCount.size(); i++) {
                        promoList.add(new Promo( giftitemCount.get(i).getSerial(),
                                giftitemCount.get(i).getMpocode(),
                                giftitemCount.get(i).getMonth(), giftitemCount.get(i).getPacksize(),
                                giftitemCount.get(i).getSamplename(), giftitemCount.get(i).getType(),
                                giftitemCount.get(i).getWeek1(), giftitemCount.get(i).getWeek2(),
                                giftitemCount.get(i).getWeek3(),giftitemCount.get(i).getWeek4(),
                                giftitemCount.get(i).getTotal(),giftitemCount.get(i).getAci()));
                    }
                    promoAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(DocSupportFollowup.this,"No data Available",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                pDialog.dismiss();
                //prepareMPOPromo();
            }
        });
    }

    public void updateStatus(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Status...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.postupdateStatus(selected_service_no,selected_service_no_serial);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                progressDialog.dismiss();
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")) {
                    prepareMPOPromo();
                } else {
                    Toast.makeText(DocSupportFollowup.this, "Network Error, Please try later", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DocSupportFollowup.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public class ViewDialog {
        @SuppressLint("SetTextI18n")
        public void showDialog( ){
            final Dialog dialog = new Dialog(DocSupportFollowup.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_msd_doc_support);
            Button dialogButton =  dialog.findViewById(R.id.read_btn);
            Button read_back =  dialog.findViewById(R.id.read_back);
            TextView message =  dialog.findViewById(R.id.message);
            TextView service =  dialog.findViewById(R.id.service);
            TextView title = dialog.findViewById(R.id.title);
            title.setText("Confirm Service");
            message.setText("Press Received button to confirm the service");
            service.setText("Confirm Service\t"+selected_service_no);
            dialogButton.setText("Received");
            read_back.setText("Back");

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateStatus();
                    dialog.dismiss();
                }
            });

            read_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            dialog.show();

        }

        public void alertDialog( ){
            final Dialog dialog = new Dialog(DocSupportFollowup.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_msd_doc_support);

            Button dialogButton =  dialog.findViewById(R.id.read_btn);
            TextView message =  dialog.findViewById(R.id.message);
            TextView title = dialog.findViewById(R.id.title);
            title.setText("Pending");
            message.setText("MSD is working on it. Please wait.");
            TextView service =  dialog.findViewById(R.id.service);
            service.setText("Pending Service\t"+selected_service_no);

            dialogButton.setText("Ok");

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    //  refresh();

                }
            });

            dialog.show();

        }


        public void approvedDialog( ){
            final Dialog dialog = new Dialog(DocSupportFollowup.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_msd_doc_support);

            Button dialogButton =  dialog.findViewById(R.id.read_btn);
            TextView message =  dialog.findViewById(R.id.message);
            TextView title = dialog.findViewById(R.id.title);
            title.setText("Approved");
            message.setText("You have already confirmed this service.");
            TextView service =  dialog.findViewById(R.id.service);
            service.setText("Confirmed Service\t"+selected_service_no);

            dialogButton.setText("Ok");

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }

    }

    private void initViews() {

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");

        rvCompany = findViewById(R.id.rvCompany);
        headerScroll = findViewById(R.id.headerScroll);
        user_show1 =  findViewById(R.id.user_show1);
        back_btn = findViewById(R.id.backbt);


        mpo_code =  findViewById(R.id.mpo_code);
        sproduct_name =  findViewById(R.id.sproduct_name);
        sserial =  findViewById(R.id.sserial);
        sqnty1 =  findViewById(R.id.sqnty1);
        ssellvelue =  findViewById(R.id.ssellvelue);
        gval =  findViewById(R.id.gval);
        achivement =  findViewById(R.id.achivement_sales_admin);

        week1 =  findViewById(R.id.week1);
        week2 =  findViewById(R.id.week2);
        week3 =  findViewById(R.id.week3);
        week4 =  findViewById(R.id.week4);


        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");




        Bundle b = getIntent().getExtras();
        userName = b.getString("userName");
        UserName_2 = b.getString("UserName_2");
        promo_type = b.getString("promo_type");
        user_flag = b.getString("user_flag");
        user_code = b.getString("user_code");

    }






    public void setUpRecyclerView() {

        promoAdapter = new MSDAdapter(DocSupportFollowup.this, promoList);

        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvCompany.setLayoutManager(manager);
        rvCompany.setAdapter(promoAdapter);
        rvCompany.addItemDecoration(new DividerItemDecoration(DocSupportFollowup.this, DividerItemDecoration.VERTICAL));

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void finishActivity(View v) {
        finish();
    }








    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }

    private void view() {
        Intent i = new Intent(DocSupportFollowup.this, com.opl.pharmavector.Report.class);
        startActivity(i);
        finish();

    }

}









