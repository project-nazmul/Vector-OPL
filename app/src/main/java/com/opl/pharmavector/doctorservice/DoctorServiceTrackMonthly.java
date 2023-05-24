package com.opl.pharmavector.doctorservice;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.lang.Runnable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.AmCategory3;
import com.opl.pharmavector.AmCustomer;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.MonthYearPickerDialog2;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;

public class DoctorServiceTrackMonthly extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<AmCategory3> categoriesList;
    private ArrayList<AmCategory3> categoriesList3;
    public ProgressDialog pDialog;
    ListView productListView;
    ListView productListView2;
    Button submit, submitBtn;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no;
    TextView date2, ded;
    int textlength = 0;
    public TextView totqty, totval;
    public String userName_1, userName, active_string, act_desiredString, mpo_code, mpoforam;
    public String from_date, to_date;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public static ArrayList<String> sl;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PROD_VAT_2;
    public static ArrayList<String> PROD_VAT_3;
    public static ArrayList<String> PROD_VAT_4;
    public Spinner mpo;
    private ArrayList<Customer> customerlist;
    private android.widget.Spinner cust;
    private android.widget.Spinner territory;
    private ArrayList<Customer> territorylist;
    ProgressDialog pDialog2;
    private ArrayList<String> array_sort = new ArrayList<String>();
    public String service_no;
    public String global_mpo_code;
    private final String URL_PRODUCT_VIEW = BASE_URL+"doctor_service/month_wise_doctor_track.php";
    private final String URL_MPO = BASE_URL+"doctor_service/doctors_list_doc_service3.php";
    private final String URL_PRODUCT_VIEW_3 = BASE_URL+"doctor_service/doc_service_status.php";
    private final String load_territory = BASE_URL+"doctor_service/load_territory.php";
    TextView conference_date, conference_date2;
    public String monthYearStr, date_param;
    public String monthPicker;
    public String year_val, month_val;
    public String year_val2, month_val2;
    public TextView succ_msg;
    public String month_name_val, proposed_conference_date, proposed_conference_date2;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    public String monthYearStr2, date_param2;
    public String month_name_val2, proposed_conference_date22;
    public int count;
    public String global_area_code_for_rm;
    private ArrayList<com.opl.pharmavector.AmCustomer> mpolist;
    private ArrayList<com.opl.pharmavector.AmCustomer> listterritory;
    private ArrayList<com.opl.pharmavector.AmCustomer> listarea;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.doctor_service_tracking);

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView =  findViewById(R.id.pListView);
        Button back_btn =  findViewById(R.id.backbt);
        Button view_btn =  findViewById(R.id.view);
        Button submitBtn =  findViewById(R.id.submitBtn);
        final LinearLayout lay_3 =  findViewById(R.id.lay_3);
        lay_3.setVisibility(View.GONE);
        final LinearLayout lay_doc =  findViewById(R.id.doc_lay);
        final LinearLayout c =  findViewById(R.id.c);

        final LinearLayout l4 =  findViewById(R.id.l4);
        l4.setVisibility(View.GONE);
        final TextView doc_address =  findViewById(R.id.doc_address);
        final TextView doc_name =  findViewById(R.id.doc_name);

        final TextView ter_name =  findViewById(R.id.ter_name);
        final TextView area_name =  findViewById(R.id.area_name);
        final TextView region_name =  findViewById(R.id.region_name);
        final TextView zone_name =  findViewById(R.id.zone_name);
        final TextView division_name =  findViewById(R.id.division_name);

        final TextView detail_head =  findViewById(R.id.detail_head);
        Button submitBtn_2 =  findViewById(R.id.submitBtn_2);
        conference_date =  findViewById(R.id.conference_date);
        conference_date.setClickable(true);
        conference_date2 =  findViewById(R.id.conference_date2);
        conference_date2.setClickable(true);

        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        mpo_code = b.getString("userName");
        count = mpo_code.length() - mpo_code.replace("0", "").length();

        final AutoCompleteTextView actv =  findViewById(R.id.autoCompleteTextView1);
        final AutoCompleteTextView actv2 =  findViewById(R.id.autoCompleteTextView2);
        final AutoCompleteTextView actv3 =  findViewById(R.id.autoCompleteTextView3);

        if (count == 0) { //mpo
            lay_doc.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
            actv2.setVisibility(View.GONE);
            actv.setFocusableInTouchMode(true);
            actv.setFocusable(true);
            actv.requestFocus();
            global_mpo_code = mpo_code;
            new GetEmp().execute();
        } else if (count == 1) { // area managerp
            region_name.setVisibility(View.GONE);
            zone_name.setVisibility(View.GONE);
            division_name.setVisibility(View.GONE);
            actv3.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 5);
            params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            ter_name.setLayoutParams(params);
            ViewGroup.LayoutParams params3 = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 4);
            params3.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            actv2.setLayoutParams(params3);
            area_name.setHint("Territory Code");
            ViewGroup.LayoutParams params2 = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 5);
            params2.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            area_name.setLayoutParams(params2);
            actv2.setFocusableInTouchMode(true);
            actv2.setFocusable(true);
            actv2.requestFocus();
            new GetTerritory().execute();
        } else if (count == 2) {  // regional manager
            actv3.setFocusableInTouchMode(true);
            actv3.setFocusable(true);
            actv3.requestFocus();
            division_name.setVisibility(View.GONE);
            zone_name.setHint("Area name");
            region_name.setHint("Area code");
            ViewGroup.LayoutParams params01 = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 2.5);
            params01.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            ter_name.setLayoutParams(params01);
            ViewGroup.LayoutParams params02 = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 2.5);
            params02.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            area_name.setLayoutParams(params02);
            ViewGroup.LayoutParams params03 = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 2.5);
            params03.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            region_name.setLayoutParams(params03);
            ViewGroup.LayoutParams params04 = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 2.5);
            params04.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            zone_name.setLayoutParams(params04);
            actv3.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params2 = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2);
            params2.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            actv2.setLayoutParams(params2);
            actv2.setFocusable(false);
            ViewGroup.LayoutParams params3 = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2);
            params3.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            actv3.setLayoutParams(params3);
            new GetArea().execute();
        } else if (count == 3) { /// asm dsm
            lay_doc.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
            actv2.setVisibility(View.GONE);
            actv.setFocusableInTouchMode(true);
            actv.setFocusable(true);
            actv.requestFocus();
            global_mpo_code = mpo_code;
            new GetEmp().execute();
        } else if (count == 4) { ///
            lay_doc.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
            actv2.setVisibility(View.GONE);
            actv.setFocusableInTouchMode(true);
            actv.setFocusable(true);
            actv.requestFocus();
            global_mpo_code = mpo_code;
            new GetEmp().execute();
        } else {
            lay_doc.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
            actv2.setVisibility(View.GONE);
            actv.setFocusableInTouchMode(true);
            actv.setFocusable(true);
            actv.requestFocus();
            global_mpo_code = mpo_code;
            new GetEmp().execute();
        }
        cust = (android.widget.Spinner) findViewById(R.id.mpo);
        cust.setPrompt("Select Doctor");
        territory = (android.widget.Spinner) findViewById(R.id.sp_territory);
        territory.setPrompt("Select Doctor");
        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        territorylist = new ArrayList<Customer>();
        territory.setOnItemSelectedListener(this);

        actv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actv.getText().toString() != "") {
                    String selectedcustomer = actv.getText().toString();
                    cust.setTag(selectedcustomer);
                }
            }
        });

        actv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // String selectedcustomer2 = actv2.getText().toString();
                // System.out.println("Selectedcustomer = "+selectedcustomer2);
                // territory.setTag(selectedcustomer2);
                mpolist.clear();
            }
        });

        actv2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv2.showDropDown();
                return false;
            }
        });

        actv3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv3.showDropDown();
                return false;
            }
        });

        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv.showDropDown();
                return false;
            }
        });

        actv3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    String mar_name = s.toString();
                    String mar[] = mar_name.split("//");
                    String doctor_name = mar[0].trim();
                    String mpoforam_initial = mar[1].trim();
                    String second_split[] = mpoforam_initial.split("/");
                    mpoforam = second_split[0].trim();
                    String split_doc_address = second_split[1].trim();
                    doc_name.setText(doctor_name);
                    doc_address.setText(split_doc_address);
                    lay_3.setVisibility(View.GONE);
                    l4.setVisibility(View.GONE);
                    productListView2.setAdapter(null);
                    productListView.setAdapter(null);
                    actv.setText("");
                    actv.setHint("Type Doctor Name ");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(actv.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        actv2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    String mar_name2 = s.toString();
                    String[] mar2 = mar_name2.split("/");
                    String doctor_name2 = mar2[0].trim();
                    String mpoforam_initial2 = mar2[1].trim();
                    global_mpo_code = doctor_name2;
                    Log.e("global_mpo_code ", "> " + global_mpo_code);
                    ter_name.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    ter_name.setText(mpoforam_initial2);
                    area_name.setText(global_mpo_code);
                    actv2.setText("");
                    actv2.setHint("Type Territory name");
                    new GetEmp().execute();
                    actv.setFocusableInTouchMode(true);
                    actv.setFocusable(true);
                    actv.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        actv3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    String mar_name2 = s.toString();
                    String mar2[] = mar_name2.split("/");
                    String doctor_name2 = mar2[0].trim();
                    String mpoforam_initial2 = mar2[1].trim();
                    global_area_code_for_rm = doctor_name2;
                    zone_name.setText(mpoforam_initial2);
                    region_name.setText(global_area_code_for_rm);
                    actv3.setText("");
                    actv3.setHint("Type Area Code");

                    new GetTerritory().execute();
                    actv2.setFocusableInTouchMode(true);
                    actv2.setFocusable(true);
                    actv2.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(cal.getTime());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        String year_name = year_date.format(cal.getTime());
        String first_date = "JAN" + "-" + year_name.toUpperCase();
        String last_date = month_name.toUpperCase() + "-" + year_name.toUpperCase();
        conference_date.setText(first_date);
        conference_date2.setText(last_date);

        conference_date.setOnClickListener(v -> {
            MonthYearPickerDialog2 pickerDialog = new MonthYearPickerDialog2();
            pickerDialog.setListener((datePicker, year, month, i2) -> {
                monthYearStr = year + "-" + 1 + "-" + i2;
                //conference_date.setText(formatMonthYear(monthYearStr));
                String test = monthYearStr;
                year_val = test.substring(0, 4);
                month_val = String.valueOf(month);
                // proposed_conference_date= year + "/" + (month + 1) + "/" + 01;
                proposed_conference_date = "01" + "/" + (month) + "/" + year;
                proposed_conference_date = "31" + "/" + (month) + "/" + year;

                if (!month_val.equals(String.valueOf(1))) {
                    if (month_val.equals(String.valueOf(2))) {
                        month_name_val = "FEB";
                    } else if (month_val.equals(String.valueOf(3))) {
                        month_name_val = "MAR";
                    } else if (month_val.equals(String.valueOf(4))) {
                        month_name_val = "APR";
                    } else if (month_val.equals(String.valueOf(5))) {
                        month_name_val = "MAY";
                    } else if (month_val.equals(String.valueOf(6))) {
                        month_name_val = "JUN";
                    } else if (month_val.equals(String.valueOf(7))) {
                        month_name_val = "JUL";
                    } else if (month_val.equals(String.valueOf(8))) {
                        month_name_val = "AUG";
                    } else if (month_val.equals(String.valueOf(9))) {
                        month_name_val = "SEP";
                    } else if (month_val.equals(String.valueOf(10))) {
                        month_name_val = "OCT";
                    } else if (month_val.equals(String.valueOf(11))) {
                        month_name_val = "NOV";
                    } else if (month_val.equals(String.valueOf(12))) {
                        month_name_val = "DEC";
                    }
                } else {
                    month_name_val = "JAN";
                }
                date_param = month_name_val + "-" + year;
                conference_date.setText(date_param);
            });
            pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
        });

        conference_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog2 pickerDialog = new MonthYearPickerDialog2();
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        monthYearStr2 = year + "-" + (month + 1) + "-" + i2;
                        Log.e("conference_date2", monthYearStr2);
                        String test2 = monthYearStr2;
                        year_val2 = test2.substring(0, 4);
                        int month_int2 = month;
                        month_val2 = String.valueOf(month_int2);
                        // proposed_conference_date= year + "/" + (month + 1) + "/" + 01;
                        proposed_conference_date2 = "01" + "/" + (month) + "/" + year;
                        proposed_conference_date22 = "31" + "/" + (month) + "/" + year;
                        if (month_val2.equals(String.valueOf(1))) {
                            month_name_val2 = "JAN";
                        } else if (month_val2.equals(String.valueOf(2))) {
                            month_name_val2 = "FEB";
                        } else if (month_val2.equals(String.valueOf(3))) {
                            month_name_val2 = "MAR";
                        } else if (month_val2.equals(String.valueOf(4))) {
                            month_name_val2 = "APR";
                        } else if (month_val2.equals(String.valueOf(5))) {
                            month_name_val2 = "MAY";
                        } else if (month_val2.equals(String.valueOf(6))) {
                            month_name_val2 = "JUN";
                        } else if (month_val2.equals(String.valueOf(7))) {
                            month_name_val2 = "JUL";
                        } else if (month_val2.equals(String.valueOf(8))) {
                            month_name_val2 = "AUG";
                        } else if (month_val2.equals(String.valueOf(9))) {
                            month_name_val2 = "SEP";
                        } else if (month_val2.equals(String.valueOf(10))) {
                            month_name_val2 = "OCT";
                        } else if (month_val2.equals(String.valueOf(11))) {
                            month_name_val2 = "NOV";
                        } else if (month_val2.equals(String.valueOf(12))) {
                            month_name_val2 = "DEC";
                        }
                        date_param2 = month_name_val2 + "-" + year;
                        conference_date2.setText(date_param2);
                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");


            }
        });


        mpolist = new ArrayList<AmCustomer>();
        listterritory = new ArrayList<AmCustomer>();
        listarea = new ArrayList<AmCustomer>();


        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        totqty =  findViewById(R.id.totalsellquantity);
        totval =  findViewById(R.id.totalsellvalue);
        int listsize = productListView.getChildCount();
        Log.i("Size of ProductLIstview", "ProductLIstView SIZE: " + listsize);
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        categoriesList = new ArrayList<AmCategory3>();
        categoriesList3 = new ArrayList<AmCategory3>();


        productListView2 =  findViewById(R.id.pListView2);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                service_no = (String) productListView.getAdapter().getItem(arg2);
                int size = categoriesList3.size();
                if (size > 0) {
                    lay_3.setVisibility(View.VISIBLE);
                    l4.setVisibility(View.VISIBLE);
                    productListView2.setAdapter(null);
                    categoriesList3.clear();
                    new GetCategories3().execute();
                    detail_head.setText("Service No - " + service_no);
                } else {
                    lay_3.setVisibility(View.VISIBLE);
                    l4.setVisibility(View.VISIBLE);
                    categoriesList3.clear();
                    new GetCategories3().execute();
                    detail_head.setText("Service No - " + service_no);
                }

            }
        });




        

        back_btn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

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


        submitBtn_2.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");

            @Override
            public void onClick(final View v) {
                try {
                    if (conference_date.equals("") || (conference_date.getText().toString().equals("Service Start Month")) || (conference_date.equals("Service Start Month"))) {
                        conference_date.setText("Service Start Month");
                        conference_date.setTextColor(Color.RED);
                    } else if (conference_date2.equals("") || (conference_date2.equals("Service End Month")) || (conference_date2.equals("Service End Month"))) {


                        conference_date2.setText("Service End Month is required");
                        conference_date2.setTextColor(Color.RED);

                    } else {


                        categoriesList.clear();
                        new GetCategories().execute();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


    private void popSpinner() {


        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            description.add(categoriesList.get(i).getId());

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class Spinner {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();

            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1, prod_vat_2, prod_vat_3, prod_vat_4, sellvalue_2, sellvalue_3;


            for (int i = 0; i < categoriesList.size(); i++) {
                sl.add(categoriesList.get(i).getsl());

                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1 = categoriesList.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2 = categoriesList.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3 = categoriesList.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4 = categoriesList.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);


            }

            DoctorServiceTrackMonthlyAdapter adapter = new DoctorServiceTrackMonthlyAdapter(DoctorServiceTrackMonthly.this, sl, lables, quanty, value, value4, value5, value6, value7);


            productListView.setAdapter(adapter);
        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }

        public String getTotalQ() {
            return TotalQ;
        }

        public String getTotalV() {
            return TotalV;
        }
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");


        final String from_date = conference_date.getText().toString();
        final String to_date = conference_date2.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DoctorServiceTrackMonthly.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("ord_no", ord_no));
            params.add(new BasicNameValuePair("mpoid", mpoforam));

            params.add(new BasicNameValuePair("to_date", to_date));
            params.add(new BasicNameValuePair("from_date", from_date));

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            AmCategory3 cat3 = new AmCategory3(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"));
                            categoriesList.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(DoctorServiceTrackMonthly.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(DoctorServiceTrackMonthly.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Spinner sp = new Spinner();
            sp.populateSpinner();
            popSpinner();


        }
    }



    /*============================================================DOCTOR SERVICE STATUS =============================================================================*/


    private void popSpinner3() {


        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList3.size(); i++) {
            description3.add(categoriesList3.get(i).getId());
        }

    }

    class Spinner3 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner3() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();

            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1, prod_vat_2, prod_vat_3, prod_vat_4, sellvalue_2, sellvalue_3;


            for (int i = 0; i < categoriesList3.size(); i++) {
                sl.add(categoriesList3.get(i).getsl());
                lables.add(categoriesList3.get(i).getName());
                p_ids.add(categoriesList3.get(i).getId());
                quanty.add(categoriesList3.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList3.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1 = categoriesList3.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2 = categoriesList3.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3 = categoriesList3.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4 = categoriesList3.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);
            }
            DocServiceStatus adapter2 = new DocServiceStatus(DoctorServiceTrackMonthly.this, sl, lables, quanty, value, value4, value5, value6, value7);


            productListView2.setAdapter(adapter2);
        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }

        public String getTotalQ() {
            return TotalQ;
        }

        public String getTotalV() {
            return TotalV;
        }
    }

    private class GetCategories3 extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");


        final String from_date = conference_date.getText().toString();
        final String to_date = conference_date2.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DoctorServiceTrackMonthly.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("ord_no", ord_no));
            params.add(new BasicNameValuePair("service_no", service_no));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW_3, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            AmCategory3 cat3 = new AmCategory3(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"));
                            categoriesList3.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(DoctorServiceTrackMonthly.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(DoctorServiceTrackMonthly.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Spinner3 sp3 = new Spinner3();
            sp3.populateSpinner3();
            popSpinner3();


        }
    }


    /*====================================================================DOCTOR SERVICE STATUS=====================================================================*/


    private void populateSpinner0() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < mpolist.size(); i++) {
            lables.add(mpolist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,  R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv =  findViewById(R.id.autoCompleteTextView1);
        actv.setThreshold(2);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }


    private void populateSpinner1() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < listterritory.size(); i++) {
            lables.add(listterritory.get(i).getName());
        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,  R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,  R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv2 =  findViewById(R.id.autoCompleteTextView2);
        actv2.setThreshold(2);
        actv2.setAdapter(Adapter);
        actv2.setTextColor(Color.BLUE);
    }

    private void populateSpinner2() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < listarea.size(); i++) {
            lables.add(listarea.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv3 =  findViewById(R.id.autoCompleteTextView3);
        actv3.setThreshold(2);
        actv3.setAdapter(Adapter);
        actv3.setTextColor(Color.BLUE);
    }


    class GetEmp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(DoctorServiceTrackMonthly.this);
            pDialog2.setMessage("Fetching Doctors..");
            pDialog2.setCancelable(false);
            pDialog2.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", global_mpo_code));

            ServiceHandler jsonParser = new ServiceHandler();
            // String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            String json = jsonParser.makeServiceCall(URL_MPO, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"), catObj.getString("name"));
                            //   visitorlist.add(custo);
                            mpolist.add(custo);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog2.dismiss();
            populateSpinner0();
        }

    }


    class GetTerritory extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(DoctorServiceTrackMonthly.this);
            pDialog2.setMessage("Fetching Territory  ..");
            pDialog2.setCancelable(false);
            pDialog2.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            if (count == 1) {
                params.add(new BasicNameValuePair("id", mpo_code));
            } else if (count == 2) {
                params.add(new BasicNameValuePair("id", global_area_code_for_rm));
            }


            ServiceHandler jsonParser = new ServiceHandler();
            // String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            String json = jsonParser.makeServiceCall(load_territory, ServiceHandler.POST, params);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"), catObj.getString("name"));
                            //   visitorlist.add(custo);
                            listterritory.add(custo);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog2.dismiss();
            populateSpinner1();
        }

    }


    class GetArea extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(DoctorServiceTrackMonthly.this);
            pDialog2.setMessage("Fetching Area  ..");
            pDialog2.setCancelable(false);
            pDialog2.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", mpo_code));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(load_territory, ServiceHandler.POST, params);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"), catObj.getString("name"));
                            listarea.add(custo);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog2.dismiss();
            populateSpinner2();
        }

    }
    public void finishActivity(View v) {
        finish();
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
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }




}



