package com.opl.pharmavector.prescriptionsurvey;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.lang.Runnable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.FMwiseProductSale;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RMWiseProductSale;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.exam.ExamDashboard;
import com.opl.pharmavector.giftfeedback.GiftFeedbackEntry;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.ImageLoadActivity;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.ImageLoadParam;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionFollowup extends Activity implements OnClickListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<com.opl.pharmavector.Category> categoriesList;
    public ProgressDialog pDialog;
    ListView productListView;
    public int success;
    public String message, ord_no;
    TextView fromdate, todate;
    public TextView totqty, totval, prescription_head, prescription_head2;
    public TextView sub_tot_opd, sub_tot_ipd, sub_tot_reg , sub_tot_bl, sub_tot_sg,sub_tot_sd, sub_tot_rx, sub_tot;
    public String userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date;
    com.opl.pharmavector.JSONParser jsonParser;
    List<NameValuePair> params;
    Button back_btn, view_btn, submitBtn;
    Calendar c_todate, c_fromdate;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PPM_CODE;
    private ArrayList<String> array_sort = new ArrayList<String>();
    String json;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> departmentlist;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate;
    Calendar myCalendar, myCalendar1;
    DatePickerDialog.OnDateSetListener date_form, date_to;
    public String manager_code, manager_detail;
    LinearLayout layout2;
    AutoCompleteTextView actv_mpo, actv_brand_name, actv_zone, actv_rm, actv_am;
    public android.widget.Spinner spin_doc;
    public android.widget.Spinner spin_brand;
    public android.widget.Spinner spin_zone;
    public android.widget.Spinner spin_rm;
    public android.widget.Spinner userSpinner;
    String actv_mpo_code_split, mpo_code = "xx";
    String actv_brand_code_split, brand_code = "xx", brand_name;
    String actv_rm_code_split, rm_name, rm_code = "xx";
    String am_name, am_code;
    CardView cardview4;
    String fieldforce_val = "Territory";
    String Prescription_val = "All";
    String pres_type = "xx";
    //private String URL_PRODUCT_VIEW = BASE_URL+"prescription_survey/prescription_report_target_achv_followup.php";
    //private String URL_PRES_VIEW = BASE_URL+"prescription_survey/prescription_type_report_target_achv_followup.php";
    private String URL_PRODUCT_VIEW = BASE_URL+"prescription_survey/prescription_survey_followup.php";
    private String URL_PRES_VIEW = BASE_URL+"prescription_survey/prescription_survey_followup.php";
    private String URL_MPO = BASE_URL+"prescription_survey/get_mpoList.php";
    private String URL_LIST = BASE_URL+"prescription_survey/get_List.php";
    private String URL_BRAND = BASE_URL+"prescription_survey/get_brandList.php";
    private HintSpinner<User> userHintSpinner;
    private List<User> users;
    String passed_manager_code;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_report);

        Bundle b = getIntent().getExtras();
        assert b != null;
        manager_code = b.getString("manager_code");
        manager_detail = b.getString("manager_detail");
        passed_manager_code = manager_code;

        initViews();
        initCalender();
        btnClickEvent();
        new GetCategories().execute();
        postPrescriptionCount();
        postSubTotal();
        paraMeterCheck();
        autoCompleteEvents();

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Log.i("Selected Item in list", arg0.toString());
                String rm_code = (String) productListView.getAdapter().getItem(arg2);
                Log.i("Field Force Code------>", rm_code);
                String first_split[] = rm_code.split("/");
                String brand_split = first_split[0].trim();
                String  passed_mpo = first_split[1].trim();
                String second_split[] = brand_split.split("-");
                String passed_brandname = second_split[0].trim();
                String  passed_brandcode = second_split[1].trim();
                Intent i = new Intent(PrescriptionFollowup.this, ImageLoadParam.class);
                i.putExtra("passed_mpo", passed_mpo);
                i.putExtra("passed_brandcode", passed_brandcode);
                i.putExtra("passed_brandname",passed_brandname);
                i.putExtra("from_date", fromdate.getText().toString());
                i.putExtra("to_date", todate.getText().toString());
                i.putExtra("pres_type", pres_type);
                i.putExtra("report_type","A");
                i.putExtra("pres_sub_type","xx");
                Log.e("passedforLoadImage==>",passed_brandcode+"\n"+passed_mpo+"\n"+pres_type+"\n"+
                        fromdate.getText().toString()+"\n"+todate.getText().toString());
                startActivity(i);
            }
        });
    }

    private void initViews(){
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = findViewById(R.id.pListView);
        back_btn = findViewById(R.id.backbt);
        view_btn = findViewById(R.id.view);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setTypeface(fontFamily);
        submitBtn.setText("\uf1d8"); // &#xf1d8
        prescription_head = findViewById(R.id.tot_pres);
        prescription_head2 = findViewById(R.id.tot_pres_brand);
        sub_tot_opd = findViewById(R.id.sub_tot_opd);
        sub_tot_ipd = findViewById(R.id.sub_tot_ipd);
        sub_tot_reg = findViewById(R.id.sub_tot_reg);
        sub_tot_sd = findViewById(R.id.sub_tot_sd);
        sub_tot_bl = findViewById(R.id.sub_tot_bl);
        sub_tot_sg = findViewById(R.id.sub_tot_sg);
        sub_tot_rx = findViewById(R.id.sub_tot_rx);
        sub_tot = findViewById(R.id.sub_tot);
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        totqty = findViewById(R.id.totalsellquantity);
        totval = findViewById(R.id.totalsellvalue);
        customerlist = new ArrayList<Customer>();
        departmentlist = new ArrayList<Customer>();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        PPM_CODE = new ArrayList<String>();
        layout2 = findViewById(R.id.layout2);
        cardview4 = findViewById(R.id.cardview4);
        if (manager_detail.equals("MPO")){
            layout2.setVisibility(View.GONE);
        }else{
            layout2.setVisibility(View.VISIBLE);
        }spin_doc = findViewById(R.id.spin_doc);
        spin_brand = findViewById(R.id.spin_brand);
        spin_rm = findViewById(R.id.spin_rm);
        actv_mpo = findViewById(R.id.autoCompleteTextView1);
        actv_brand_name = findViewById(R.id.autoCompleteTextView2);
        actv_rm = findViewById(R.id.actv_rm);
        categoriesList = new ArrayList<com.opl.pharmavector.Category>();

        if (!"MPO".equals(manager_detail)){
            cardview4.setVisibility(View.VISIBLE);
            initUserHintSpinner();
            initTypeHintSpinner();
        } else {
            cardview4.setVisibility(View.GONE);
            initTypeHintSpinner();
        }
    }

    private void initUserHintSpinner() {
        MaterialSpinner mspinner =  findViewById(R.id.mspinner);
        switch (manager_detail) {
            case "AD":
                mspinner.setItems("National", "Division","Zone", "Region", "Area", "Territory");
                break;
            case "SM":
                mspinner.setItems("Zone","Region", "Area", "Territory");
                break;
            case "ASM":
                mspinner.setItems("Region", "Area", "Territory");
                break;
            case "RM":
                mspinner.setItems("Area", "Territory");
                break;
            case "FM":
                mspinner.setItems("Territory");
                break;
        }

        mspinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar snackbar = Snackbar.make(view, "Field Type: " + item, Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(PrescriptionFollowup.this, R.color.colorAccentEditor));
                snackbar.show();

                fieldforce_val = String.valueOf(item);
                if (fieldforce_val.trim().equals("National")){
                    passed_manager_code = manager_code;
                    brand_code="xx";
                    actv_rm.setText("");
                    actv_brand_name.setText("");
                    customerlist.clear();
                    categoriesList.clear();

                    new GetCategories().execute();
                    postPrescriptionCount();
                    postSubTotal();
                } else {
                    actv_rm.setText("");
                    actv_brand_name.setText("");
                    customerlist.clear();
                    new GetList().execute();
                }
            }
        });
    }

    private void initTypeHintSpinner() {
        MaterialSpinner mspinner2 =  findViewById(R.id.mspinner2);
        mspinner2.setItems("All", "Regular", "Special", "Brand Loyalty");
        mspinner2.setVisibility(View.GONE);

        mspinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar snackbar = Snackbar.make(view, "Prescription Type: " + item, Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(PrescriptionFollowup.this, R.color.colorAccentEditor));
                snackbar.show();

                Prescription_val = String.valueOf(item);
                if (Prescription_val.trim().equals("All")){
                    pres_type ="xx";
                } else  if (Prescription_val.trim().equals("Regular")){
                    pres_type ="R";
                } else  if (Prescription_val.trim().equals("Special")){
                    pres_type ="SG";
                } else  if (Prescription_val.trim().equals("Brand Loyalty")){
                    pres_type ="BL";
                }
            }
        });
    }

    private void paraMeterCheck() {
        switch (manager_detail) {
            case "FM":
            case "RM":
            case "ASM":
            case "SM":
                cardview4.setVisibility(View.VISIBLE);
                new GetBrandList().execute();
                break;
            case "AD":
                cardview4.setVisibility(View.VISIBLE);
                new GetList().execute();
                new GetBrandList().execute();
                break;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void initCalender() {
        c_todate = Calendar.getInstance();
        dftodate = new SimpleDateFormat("dd/MM/yyyy");
        current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);
        c_fromdate = Calendar.getInstance();
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_fromdate);
        myCalendar = Calendar.getInstance();
        date_form = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                fromdate.setTextColor(Color.BLACK);
                fromdate.setText("");
                fromdate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        myCalendar1 = Calendar.getInstance();
        date_to = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                // String myFormat = "dd/MM/yyyy";
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                todate.setTextColor(Color.BLACK);
                todate.setText("");
                todate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        fromdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PrescriptionFollowup.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        todate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PrescriptionFollowup.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autoCompleteEvents() {
        actv_brand_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // hideKeyBoard();
                actv_brand_name.showDropDown();
                return false;
            }
        });
        actv_brand_name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        actv_brand_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actv_brand_name.setTextColor(Color.RED);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                actv_brand_name.setTextColor(Color.GREEN);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.contains("-")) {
                        actv_brand_code_split = inputorder.substring(inputorder.indexOf("-") + 1);
                        String[] first_split = inputorder.split("-");
                        brand_name = first_split[0].trim();
                        brand_code = first_split[1].trim();
                        actv_brand_name.setText(brand_name);
                        KeyboardUtils.hideKeyboard(PrescriptionFollowup.this);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {

            }
        });

        actv_rm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // hideKeyBoard();
                actv_rm.showDropDown();
                return false;
            }
        });
        actv_rm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        actv_rm.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actv_rm.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                actv_rm.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.contains("-")) {
                        actv_rm_code_split = inputorder.substring(inputorder.indexOf("-") + 1);
                        String[] first_split = inputorder.split("-");
                        rm_code = first_split[0].trim();
                        rm_name  = first_split[1].trim();
                        actv_rm.setText(rm_code);
                        passed_manager_code=rm_code;
                        KeyboardUtils.hideKeyboard(PrescriptionFollowup.this);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {

            }
        });
    }

    private void btnClickEvent() {
        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
                backthred.start();
            }
        });

        submitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1 = fromdate.getText().toString();
                    String todate1 = todate.getText().toString();
                    Log.e("prestype-->",pres_type);

                    if (fromdate1.isEmpty() || (fromdate1.equals("From Date")) || (fromdate1.equals("From Date is required"))) {
                        fromdate.setText("From Date is required");
                        fromdate.setTextColor(Color.RED);
                    } else if (todate1.isEmpty() || (todate1.equals("To Date")) || (todate1.equals("To Date is required"))) {
                        todate.setText("To Date is required");
                        todate.setTextColor(Color.RED);
                    } else {
                        if (actv_mpo.getText().toString() == null || actv_mpo.getText().toString().equals("")) {
                            mpo_code = "xx";
                        }
                        if (actv_brand_name.getText().toString() == null || actv_brand_name.getText().toString().equals("")) {
                            brand_code = "xx";
                        }
                        categoriesList.clear();
                        new GetCategories().execute();
                        postPrescriptionCount();
                        postSubTotal();
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

    public void finishActivity(View v) {
        finish();
    }

    public void postSubTotal() {
        if (actv_brand_name.getText().toString().equals("")){
            brand_code ="xx";
        }
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.pres_sub_total(passed_manager_code,brand_code,fromdate.getText().toString(), todate.getText().toString(),pres_type);
        call.enqueue(new Callback<List<Patient>>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Patient>> call, retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();
                assert giftitemCount != null;
                if (response.isSuccessful()) {
                    if (giftitemCount.size() == 0) {
                        Toast.makeText(PrescriptionFollowup.this, "Gift item is not available", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < giftitemCount.size(); i++) {
                            sub_tot_opd.setText("OPD\n" + giftitemCount.get(i).getFirst_name());
                            sub_tot_ipd.setText("IPD\n" + giftitemCount.get(i).getLast_name());
                            sub_tot_reg.setText("REG\n" + giftitemCount.get(i).getPhone_number());
                            sub_tot_sd.setText("SERV DOC\n" + giftitemCount.get(i).getEmail());
                            sub_tot_bl.setText("BL\n" + giftitemCount.get(i).getGender());
                            sub_tot_sg.setText("SG\n" + giftitemCount.get(i).getStatus());
                            sub_tot_rx.setText("RX\n" + giftitemCount.get(i).getFees());
                            sub_tot.setText("Sub Total\n" + giftitemCount.get(i).getValue());
                        }
                    }
                } else {
                    Toast.makeText(PrescriptionFollowup.this, "Server error! Please try moments later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                // pDialog.dismiss();
                // Toast.makeText(PrescriptionFollowup.this, "Processing Prescription Count", Toast.LENGTH_SHORT).show();
                postSubTotal();
            }
        });
    }
    public void postPrescriptionCount() {
        if (actv_brand_name.getText().toString().equals("")){
            brand_code ="xx";
        }
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.prescriptioncount(passed_manager_code,brand_code,fromdate.getText().toString(), todate.getText().toString(),pres_type);
        Log.e("brand_code",brand_code);
        call.enqueue(new Callback<List<Patient>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Patient>> call, retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();
                assert giftitemCount != null;
                if (response.isSuccessful()) {
                    if (giftitemCount.size() == 0) {
                        Toast.makeText(PrescriptionFollowup.this, "Gift item is not available", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < giftitemCount.size(); i++) {
                            prescription_head.setText("Total Prescription:\t" + giftitemCount.get(i).getLast_name());
                            prescription_head2.setText("Total Brand:\t" + giftitemCount.get(i).getFirst_name());
                        }
                    }
                } else {
                    Toast.makeText(PrescriptionFollowup.this, "Server error! Please try moments later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
               // pDialog.dismiss();
               // Toast.makeText(PrescriptionFollowup.this, "Processing Prescription Count", Toast.LENGTH_SHORT).show();
                postPrescriptionCount();
            }
        });
    }

    class Spinner {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner() {
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> brand_name = new ArrayList<String>();
            ArrayList<Integer> quanty = new ArrayList<Integer>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> achv = new ArrayList<String>();
            ArrayList<String> growth = new ArrayList<String>();
            ArrayList<String> mpo_code = new ArrayList<String>();

            ArrayList<String> r_target = new ArrayList<String>();
            ArrayList<String> sg_target = new ArrayList<String>();
            ArrayList<String> bl_target = new ArrayList<String>();
            ArrayList<String> total_target = new ArrayList<String>();

            int quantity = 0;
            String prod_rate, brandname;
            String prod_vat;
            String sellvalue, mpocode;
            String ppm_code;

            String rtarget,sgtarget,bltarget,totaltarget;

            for (int i = 0; i < categoriesList.size(); i++) {
                brandname = categoriesList.get(i).getId();
                ppm_code = (categoriesList.get(i).getName());
                quantity = categoriesList.get(i).getQuantity();
                prod_rate = (categoriesList.get(i).getPROD_RATE());
                prod_vat = (categoriesList.get(i).getPROD_VAT());
                mpocode = (categoriesList.get(i).getPPM_CODE());

                rtarget = (categoriesList.get(i).getP_CODE());
                sgtarget = (categoriesList.get(i).getSHIFT_CODE());

                bltarget = (categoriesList.get(i).getPACK_CODE());
                totaltarget = (categoriesList.get(i).getTOTAL_CODE());

                brand_name.add(brandname);
                quanty.add(quantity);
                value.add(prod_rate);
                achv.add(prod_vat);
                growth.add(ppm_code);
                mpo_code.add(mpocode);
                r_target.add(rtarget);
                sg_target.add(sgtarget);
                bl_target.add(bltarget);
                total_target.add(totaltarget);
            }
            RXShowAdapter adapter = new RXShowAdapter(PrescriptionFollowup.this, brand_name, quanty, value, achv, growth, mpo_code, r_target,sg_target,bl_target,total_target);
            productListView.setAdapter(adapter);
        }

        private float round(float x, int i) {
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
        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PrescriptionFollowup.this);
            pDialog.setTitle("Prescription Report");
            pDialog.setMessage("Calculating Prescription ");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", passed_manager_code));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            params.add(new BasicNameValuePair("mpo_code", "xx"));
            params.add(new BasicNameValuePair("brand_code", brand_code));
            params.add(new BasicNameValuePair("pres_type", pres_type));

            categoriesList.clear();
            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();

            if (pres_type.equals("xx")){
                 Log.e("pres_type->",pres_type);
                 Log.e("FROM DATE----->",fromdate1);
                 Log.e("TO DATE----->",todate1);
                 json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);
            } else {
                 Log.e("pres_typeelse->",pres_type);
                 json = jsonParser.makeServiceCall(URL_PRES_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);
            }

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray categories = jsonObj.getJSONArray("categories");
                    int cat_length = categories.length();
                    if (cat_length == 0) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PrescriptionFollowup.this, "No data available on this search", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            com.opl.pharmavector.Category cat = new com.opl.pharmavector.Category(
                                    catObj.getString("sl"),
                                    catObj.getString("id"), //brand_name
                                    catObj.getString("name"), //regular
                                    catObj.getInt("quantity"), //special
                                    catObj.getString("PROD_RATE"),//brand_loyalty
                                    catObj.getString("PROD_VAT"),//total
                                    catObj.getString("PPM_CODE"),  //mpoCode
                                    catObj.getString("P_CODE"),  //RX_TARGET
                                    catObj.getString("SHIFT_CODE"),  //SG_TARGET
                                    catObj.getString("PACK_CODE"), //BL_TARGET
                                    catObj.getString("TOTAL_CODE") //TOTAL_TARGET
                            );
                            categoriesList.add(cat);
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(PrescriptionFollowup.this, "No data available on this search", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.e("onpostexecute=>","on post execute");
            super.onPostExecute(result);
            try {
                if((pDialog != null) && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally
            {
                pDialog.dismiss();
            }
            Spinner sp = new Spinner();
            sp.populateSpinner();
            popSpinner();
            postPrescriptionCount();
            postSubTotal();
        }
    }

    private void populateSpinnerRM() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        spin_rm.setAdapter(spinnerAdapter);
        //String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        actv_rm.setThreshold(2);
        actv_rm.setAdapter(Adapter);
        actv_rm.setTextColor(Color.BLUE);
    }

    class GetList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("function", fieldforce_val.trim()));
            params.add(new BasicNameValuePair("manager_code", manager_code));
            params.add(new BasicNameValuePair("manager_detail", manager_detail));
            Log.d("spinnerLoadParam==>", fieldforce_val.trim() + "----" + manager_code+"---"+ manager_detail);

            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(URL_LIST, ServiceHandler.POST, params);
            customerlist.clear();
            Log.e("getList==>",json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        customerlist.add(custo);
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
            populateSpinnerRM();
        }
    }

    private void populatebrandSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < departmentlist.size(); i++) {
            lables.add(departmentlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        spin_brand.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        actv_brand_name.setThreshold(2);
        actv_brand_name.setAdapter(Adapter);
        actv_brand_name.setTextColor(Color.BLUE);
    }

    class GetBrandList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("manager_code", manager_code));
            params.add(new BasicNameValuePair("manager_detail", manager_detail));
            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(URL_BRAND, ServiceHandler.POST, params);
            Log.e("myBrandList==>",json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        departmentlist.add(custo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                new GetBrandList().execute();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            populatebrandSpinner();
        }
    }

    @Override
    public void onClick(View v) {

    }

    protected void onPostExecute() {

    }

    private void view() {
        Intent i = new Intent(PrescriptionFollowup.this, com.opl.pharmavector.Report.class);
        startActivity(i);
        finish();
    }
}



