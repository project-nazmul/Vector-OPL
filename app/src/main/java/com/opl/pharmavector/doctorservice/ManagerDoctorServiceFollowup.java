package com.opl.pharmavector.doctorservice;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

import com.opl.pharmavector.MonthYearPickerDialog;
import com.opl.pharmavector.R;

import com.opl.pharmavector.adapter.CategoryAdapter7;
import com.opl.pharmavector.model.Category5;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser8;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser9;

public class ManagerDoctorServiceFollowup extends AppCompatActivity {
    Context context;
    ArrayList<Category5> array_list;
    FavouriteCategoriesJsonParser8 categoryJsonParser;
    FavouriteCategoriesJsonParser9 categoryJsonParser2;
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
    public String message, ord_no,invoice,target_data,achivement,searchString,select_party,my_val,my_date;
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
    public String miscell,miscell_bdt;
    public String impact,TOTAL_BUDGET;
    public String product_list;
    public String user_flag;
    public static String UserName,date_param;
    EditText conference_date;
    public String get_ext_dt,date_flag,check_flag;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    public String monthYearStr;
    public String monthPicker;
    public String year_val,month_val;
    public TextView succ_msg;
    ListView mListViewBooks;
    public String month_name_val,proposed_conference_date,proposed_conference_date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_doc_service_followup);

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        Button button =  findViewById(R.id.selectCategoryButton);
        button.setVisibility(View.GONE);
        Button button2 =  findViewById(R.id.selectCategoryButton2);
        Button logback =  findViewById(R.id.logback);
        Button show =  findViewById(R.id.show);
        show.setTypeface(fontFamily);
        show.setText("\uf1d8");
        logback.setTypeface(fontFamily);
        logback.setText("\uf08b");

        conference_date = (EditText) findViewById(R.id.conference_date);
        conference_date.setFocusableInTouchMode(true);
        conference_date.setFocusable(true);
        conference_date.requestFocus();
        conference_date.setClickable(true);
        conference_date.setInputType(InputType.TYPE_NULL);

        conference_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        monthYearStr = year + "-" + (month + 1) + "-" + i2;
                        conference_date.setText(formatMonthYear(monthYearStr));
                        Log.e("monthyear",monthYearStr);
                        String test = monthYearStr;
                        year_val=test.substring(0,4);
                        int month_int=  month ;
                        month_val= String.valueOf(month_int);
                        // proposed_conference_date= year + "/" + (month + 1) + "/" + 01;
                        proposed_conference_date= "01" + "/" + (month) + "/" + year;
                        proposed_conference_date2 = "31" + "/" + (month) + "/" + year;
                        if(month_val== String.valueOf(1)){
                            month_name_val="JAN";
                        }  else if(month_val== String.valueOf(2)){
                            month_name_val="FEB";
                        } else if(month_val== String.valueOf(3)){
                            month_name_val="MAR";
                        } else if(month_val== String.valueOf(4)){
                            month_name_val="APR";
                        } else if(month_val== String.valueOf(5)){
                            month_name_val="MAY";
                        } else if(month_val== String.valueOf(6)){
                            month_name_val="JUN";
                        } else if(month_val== String.valueOf(7)){
                            month_name_val="JUL";
                        } else if(month_val== String.valueOf(8)){
                            month_name_val="AUG";
                        }
                        else if(month_val== String.valueOf(9)){
                            month_name_val="SEP";
                        } else if(month_val== String.valueOf(10)){
                            month_name_val="OCT";
                        } else if(month_val== String.valueOf(11)){
                            month_name_val="NOV";
                        }
                        else if(month_val== String.valueOf(12)){
                            month_name_val="DEC";
                        }
                        Log.e("proposed_confere_date",proposed_conference_date);
                        date_param= month_name_val+"-"+year;
                        Log.e("date_param",date_param);
                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });
        TextView user_show=(TextView) findViewById(R.id.user_show);
        context = this;
        Bundle b = getIntent().getExtras();


        UserName = b.getString("userName");
        user_flag= b.getString("user_flag");


        final String UserName_2 = b.getString("UserName_2");
        user_show.setText(UserName+"\t"+ "[ "+ UserName_2 +" ]"  );


        new asyncTask_getCategories().execute();

        logback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    //conference_date
                    if ((conference_date.getText().toString().trim().equals(""))) {
                        Toast.makeText(ManagerDoctorServiceFollowup.this, "Select a Service Month",Toast.LENGTH_SHORT).show();
                        conference_date.setFocusable(true);
                        conference_date.setError("Service Month not selected !");
                        conference_date.setText("Please Select Service Month ");
                        conference_date.setTextColor(Color.RED);
                    } else {
                        array_list.clear();
                        mListViewBooks.setAdapter(null);
                        new asyncTask_getCategories2().execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait...");
            dialog.setMessage("Loading Doctor Service !");
            dialog.show();
            array_list = new ArrayList<Category5>();
            my_val = UserName;
            categoryJsonParser = new FavouriteCategoriesJsonParser8();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            array_list = categoryJsonParser.getParsedCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            mListViewBooks = (ListView) findViewById(R.id.category_listView);
            final CategoryAdapter7 CategoryAdapter7 = new CategoryAdapter7(context, R.layout.row_category_4, array_list);
            mListViewBooks.setAdapter(CategoryAdapter7);
            super.onPostExecute(s);
            dialog.dismiss();
        }
    }

    public class asyncTask_getCategories2 extends AsyncTask<Void, Void, Void> {
       ProgressDialog dialog2 = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog2.setTitle("Please wait...");
            dialog2.setMessage("Loading Monthly Doctor Service Data!");
            dialog2.show();
            array_list = new ArrayList<>();

            my_val = UserName;
            my_date = date_param;
            categoryJsonParser2 = new FavouriteCategoriesJsonParser9();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            array_list = categoryJsonParser2.getParsedCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            ListView mListViewBooks = findViewById(R.id.category_listView);
            final CategoryAdapter7 CategoryAdapter7 = new CategoryAdapter7(context, R.layout.row_category_4, array_list);
            mListViewBooks.setAdapter(CategoryAdapter7);
            super.onPostExecute(s);
            dialog2.dismiss();
        }
    }
}


















