package com.opl.pharmavector.giftfeedback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.app.DatePickerDialog;
import com.opl.pharmavector.MonthYearPickerDialog;
import com.opl.pharmavector.doctorgift.DocGiftLoad;
import com.opl.pharmavector.model.Patient;
import com.google.android.material.tabs.TabLayout;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionEntry;
import com.opl.pharmavector.R;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import java.util.Calendar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import java.text.SimpleDateFormat;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.app.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

public class GiftFeedbackEntry extends AppCompatActivity implements View.OnClickListener {
    public String userName_1, userName, userName_2;
    public AutoCompleteTextView actv;
    private DatabaseHandler db;
    public TextView user_show;
    private SessionManager session;
    public String gift_type_code,gift_type_name,gift_item_name;
    Button btn_back,btn_submit;
    TabLayout tab,gift_item_tab,count_brand_gift;
    ViewPager viewPager;
    //private ArrayList<String> tabTitle;
    private String[] doc_degree;
    List<String> tabTitle;
    List<String> tabItem;
    EditText ed_date;
    RatingBar rb_q1,rb_q2,rb_q3,rb_q4,rb_q5,rb_q6;
    TextView tv_q1,tv_q2,tv_q3,tv_q4,tv_q5,tv_q6,question_mark;
    LinearLayout layout_feedback_question;
    String rating1,rating2,rating3,rating4,rating5,rating6;
    public String monthYearStr;
    public String year_val,month_val;
    public String month_name_val;
    SimpleDateFormat sdf,input;
    public static String proposed_date1, proposed_date2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorgift_feedback);

        initViews();
        calenderClickevent();
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    gift_type_code = String.valueOf(tab.getPosition());
                    gift_type_name = String.valueOf(tab.getText());
                    gift_item_tab.removeAllTabs();
                    getGiftItem();
                } else {
                    gift_type_code = String.valueOf(tab.getPosition());
                    gift_type_name = String.valueOf(tab.getText());
                    gift_item_tab.removeAllTabs();
                    getGiftItem();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        gift_item_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab gift_item_tab) {
                gift_item_name =String.valueOf(gift_item_tab.getText());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        setQuestion();
        btn_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                rating1 = String.valueOf((rb_q1.getRating()));
                rating2 = String.valueOf(rb_q2.getRating());
                rating3 = String.valueOf((rb_q3.getRating()));
                rating4 = String.valueOf((rb_q4.getRating()));
                rating5= String.valueOf((rb_q5.getRating()));
                rating6 = String.valueOf((rb_q6.getRating()));
                postFeedbacktData("insert");
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }

        });
    }

    @SuppressLint({"CutPasteId", "SimpleDateFormat", "SetTextI18n"})
    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        btn_back = findViewById(R.id.btn_back);
        btn_back.setTypeface(fontFamily);
        btn_back.setText("\uf08b");
        user_show = (TextView) findViewById(R.id.user_show);
        tab = findViewById(R.id.tabs);
        gift_item_tab = findViewById(R.id.gift_item_tab);
        count_brand_gift = findViewById(R.id.count_brand_gift);
        count_brand_gift.setClickable(false);
        ed_date =  findViewById(R.id.deliverydate);
        question_mark =  findViewById(R.id.question_mark);
        question_mark.setVisibility(View.GONE);
        layout_feedback_question=  findViewById(R.id.layout_feedback_question);
        layout_feedback_question.setVisibility(View.GONE);
        rb_q1 =  findViewById(R.id.ratingBar1);
        rb_q2 =  findViewById(R.id.ratingBar2);
        rb_q3 =  findViewById(R.id.ratingBar3);
        rb_q4 =  findViewById(R.id.ratingBar4);
        rb_q5 =  findViewById(R.id.ratingBar5);
        rb_q6=  findViewById(R.id.ratingBar6);
        tv_q1 =  findViewById(R.id.tv1);
        tv_q2 =  findViewById(R.id.tv2);
        tv_q3 =  findViewById(R.id.tv3);
        tv_q4 =  findViewById(R.id.tv4);
        tv_q5 =  findViewById(R.id.tv5);
        tv_q6=  findViewById(R.id.tv6);
        sdf = new SimpleDateFormat("MMM yyyy");
        input = new SimpleDateFormat("yyyy-MM-dd");
        btn_submit =  findViewById(R.id.btn_submit);
        tabTitle = new ArrayList<String>();
        session = new SessionManager(getApplicationContext());
        ed_date.setFocusableInTouchMode(true);
        ed_date.setFocusable(true);
        ed_date.requestFocus();
        ed_date.setClickable(true);
        ed_date.setInputType(InputType.TYPE_NULL);
        viewPager=findViewById(R.id.frameLayout);
        user_show.setText(Dashboard.globalmpocode + " [ " + Dashboard.globalterritorycode + " ] ");
    }

    private void calenderClickevent() {
        ed_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        monthYearStr = year + "-" + (month + 1) + "-" + i2;
                        ed_date.setText(formatMonthYear(monthYearStr));
                        String test = monthYearStr;
                        year_val=test.substring(0,4);
                        int month_int=  month ;
                        month_val= String.valueOf(month_int);
                        proposed_date1= "01" + "/" + (month) + "/" + year;
                        proposed_date2 = "31" + "/" + (month) + "/" + year;

                        if(month_val== String.valueOf(1)){
                            month_name_val="January";
                        }  else if(month_val== String.valueOf(2)){
                            month_name_val="Feb";
                        } else if(month_val== String.valueOf(3)){
                            month_name_val="March";
                        } else if(month_val== String.valueOf(4)){
                            month_name_val="April";
                        } else if(month_val== String.valueOf(5)){
                            month_name_val="May";
                        } else if(month_val== String.valueOf(6)){
                            month_name_val="June";
                        } else if(month_val== String.valueOf(7)){
                            month_name_val="July";
                        } else if(month_val== String.valueOf(8)){
                            month_name_val="August";
                        } else if(month_val== String.valueOf(9)){
                            month_name_val="September";
                        } else if(month_val== String.valueOf(10)){
                            month_name_val="October";
                        } else if(month_val== String.valueOf(11)){
                            month_name_val="November";
                        } else if(month_val== String.valueOf(12)){
                            month_name_val="December";
                        }
                        layout_feedback_question.setVisibility(View.GONE);
                        tab.removeAllTabs();
                        gift_item_tab.removeAllTabs();
                        count_brand_gift.removeAllTabs();
                        getDoctorDegree();
                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });
    }

    public void getDoctorDegree() {
        ProgressDialog pDialog;
        pDialog = new ProgressDialog(GiftFeedbackEntry.this);
        pDialog.setTitle("Please wait !");
        pDialog.setMessage("Loading Gift Items..");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.getGiftType(Dashboard.globalmpocode,proposed_date1);

        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(@NotNull Call<List<Patient>> call, @NotNull retrofit2.Response<List<Patient>> response) {
                List<Patient> patientdetail = response.body();
                assert patientdetail != null;

                if (response.code() == 200) {
                    pDialog.dismiss();
                    Log.e("patientdetail==>", String.valueOf(patientdetail.size()));

                    for (int i = 0; i < patientdetail.size(); i++) {
                        tab.addTab(tab.newTab().setText(patientdetail.get(i).getFirst_name()));
                    }
                    layout_feedback_question.setVisibility(View.GONE);
                    getItemCount();
                } else {
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Patient>> call, @NotNull Throwable t) {
                pDialog.dismiss();
                getDoctorDegree();
                Toast.makeText(GiftFeedbackEntry.this, "Network error! Please wait while we are reconnecting", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getItemCount() {
        ProgressDialog pDialog;
        pDialog = new ProgressDialog(GiftFeedbackEntry.this);
        pDialog.setTitle("Please wait !");
        pDialog.setMessage("Loading Gift Items..");
        pDialog.setCancelable(true);
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.getItemCount(Dashboard.globalmpocode,proposed_date1);

        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(@NotNull Call<List<Patient>> call, @NotNull retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();
                assert giftitemCount != null;
                Log.e("getItemCount=>","response==>"+ String.valueOf(response));

                if (response.code() == 200) {
                    pDialog.dismiss();
                    Log.e("patientdetail==>", String.valueOf(giftitemCount.size()));

                    if (giftitemCount.size()==0){
                        layout_feedback_question.setVisibility(View.GONE);
                        Toast.makeText(GiftFeedbackEntry.this, "Gift item is not available", Toast.LENGTH_SHORT).show();
                        question_mark.setVisibility(View.GONE);
                    } else {
                        for (int i = 0; i < giftitemCount.size(); i++) {
                            count_brand_gift.addTab(count_brand_gift.newTab().setText(giftitemCount.get(i).getFirst_name()));
                        }
                        layout_feedback_question.setVisibility(View.VISIBLE);
                        question_mark.setVisibility(View.VISIBLE);
                    }
                } else {
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(GiftFeedbackEntry.this, "Network error! Please wait while we are reconnecting", Toast.LENGTH_SHORT).show();
                layout_feedback_question.setVisibility(View.GONE);
                tab.removeAllTabs();
                gift_item_tab.removeAllTabs();
                count_brand_gift.removeAllTabs();
                //getDoctorDegree();
                //getItemCount();
            }
        });
    }

    public void getGiftItem() {
        ProgressDialog pDialog;
        pDialog = new ProgressDialog(GiftFeedbackEntry.this);
        pDialog.setTitle("Please wait !");
        pDialog.setMessage("Loading Gift Items..");
        pDialog.setCancelable(true);
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.getGiftItem(Dashboard.globalmpocode,gift_type_name,gift_type_code,proposed_date1);

        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, retrofit2.Response<List<Patient>> response) {
                List<Patient> giftdetails = response.body();
                Log.e("getGiftItem=>","response==>"+ String.valueOf(response));
                assert giftdetails != null;

                if (response.code()== 200) {
                    Log.e("getGiftItem=>","size==>"+ String.valueOf(giftdetails.size()));

                    if (giftdetails.size()==0){
                        pDialog.dismiss();
                        layout_feedback_question.setVisibility(View.GONE);
                        Toast.makeText(GiftFeedbackEntry.this, "Gift item is not available", Toast.LENGTH_SHORT).show();
                        question_mark.setVisibility(View.GONE);
                    } else {
                        for (int i = 0; i < giftdetails.size(); i++) {
                            gift_item_tab.addTab(gift_item_tab.newTab().setText(giftdetails.get(i).getFirst_name()));
                        }
                        Log.e("gift_item==>", String.valueOf(giftdetails.size()));
                        layout_feedback_question.setVisibility(View.VISIBLE);
                        pDialog.dismiss();
                        question_mark.setVisibility(View.VISIBLE);
                    }
                } else {
                    pDialog.dismiss();
                    Toast.makeText(GiftFeedbackEntry.this, "Server error! Please try moments later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(GiftFeedbackEntry.this, "Network error! Please try again", Toast.LENGTH_SHORT).show();
                layout_feedback_question.setVisibility(View.GONE);
                //tab.removeAllTabs();
                //gift_item_tab.removeAllTabs();
                //count_brand_gift.removeAllTabs();
                //getDoctorDegree();
                //getItemCount();
            }
        });
    }

    private void setQuestion(){
        tv_q1.setText("1. How comfortable you are to promote this gift into doctor chamber?");
        tv_q2.setText("2. Rate the portability of this gift into doctor chamber?");
        tv_q3.setText("3. How happy you are with the look/presentation of this gift to promote?");
        tv_q4.setText("4. How fitting this gift for intended specialty (Orthopedics)?");
        tv_q5.setText("5. How unique this gift you think?");
        tv_q6.setText("6. Rate on the spot acceptance of the Gift.");
    }

    private void resetratingbar() {
        rb_q1.setRating(0F);
        rb_q2.setRating(0F);
        rb_q3.setRating(0F);
        rb_q4.setRating(0F);
        rb_q5.setRating(0F);
        rb_q6.setRating(0F);
    }

    private void postFeedbacktData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = apiInterface.insertFeedback(key,rating1,rating2,rating3,rating4,rating5,rating6,
                gift_type_name,gift_item_name,Dashboard.globalmpocode,proposed_date1.trim() );

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                progressDialog.dismiss();
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")) {
                    gift_item_tab.removeAllTabs();
                    resetratingbar();
                    layout_feedback_question.setVisibility(View.GONE);
                    tab.removeAllTabs();
                    gift_item_tab.removeAllTabs();
                    count_brand_gift.removeAllTabs();
                    getDoctorDegree();
                } else {
                    Toast.makeText(GiftFeedbackEntry.this, "Network Error, Please try later", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GiftFeedbackEntry.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    String formatMonthYear(String str) {
        Date date = null;
        try {
            date = input.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {}
}







