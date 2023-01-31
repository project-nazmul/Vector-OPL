package com.opl.pharmavector.promomat;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.opl.pharmavector.MonthYearPickerDialog;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.MonthYearPickerDialog;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.pmdVector.pmdRX.PMDRXSummary;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup;
import com.opl.pharmavector.promomat.adapter.PromoAdapter;
import com.opl.pharmavector.promomat.adapter.RecyclerTouchListener;
import com.opl.pharmavector.promomat.model.Promo;
import com.opl.pharmavector.promomat.util.FixedGridLayoutManager;
import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class PromoMaterialFollowup extends FragmentActivity implements OnClickListener, AdapterView.OnItemSelectedListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public ProgressDialog pDialog;
    Button back_btn;
    public int success;
    public String message, ord_no;
    public TextView totqty, totval, mpo_code,fromdate, todate;
    public String userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date;
    TextView sproduct_name,sserial,sqnty1,ssellvelue,gval,user_show1,achivement,week1,week2,week3,week4;
    EditText ed_date;
    AutoCompleteTextView actv_type;
    public String p_code, asm_flag, sm_flag, gm_flag,user_code, promo_type, user_flag,UserName_2;
    int scrollX = 0;
    RecyclerView rvCompany;
    HorizontalScrollView headerScroll;
    PromoAdapter promoAdapter;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    public String monthPicker;
    public String monthYearStr;
    public String year_val, month_val;
    public String month_name_val,month_name;
    String proposed_date1;
    String proposed_date2 = "xx";
    String promo_subtype = "xx";
    List<Promo> promoList = new ArrayList<>();
    Button submitBtn;
    private ArrayList<Customer> departmentlist;
    public android.widget.Spinner spin_brand;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_material_followup);

        initViews();
        calenderUI();
        new GetBrandList().execute();
        checkSubtype();
        autoEvent();

        switch (promo_type) {
            case "S":
                if (user_flag.equals("MPO")) {
                    user_show1.setText(String.format("%s - MPO Promo Sample Followup", userName));
                    mpo_code.setText("MPO\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    prepareMPOPromo();
                }

                if (user_flag.equals("AM")) {
                    Log.e("AMsTARTS-->","prepareFMPromo()");

                    user_show1.setText(userName + " - " + "Promo Sample Followup");
                    mpo_code.setText("AM\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

                    prepareFMPromo();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code = promoList.get(position).getCode();
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "AMMPO");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "S");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                if (user_flag.equals("AMMPO")) {
                    Log.e("clicked==>","AMMPO--272");
                    user_show1.setText("MPO Promo Sample Followup");
                    mpo_code.setText("MPO\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    prepareAMMpo();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            //String sm_code = (String) productListView.getAdapter().getItem(position);
                            String sm_code = promoList.get(position).getCode();
                            //Log.e("smcode-->",sm_code);
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "MPO");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "S");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {}
                    }));
                }

                if (user_flag.equals("RM")) {
                    user_show1.setText(String.format("%s - Promo Sample Followup", userName));
                    mpo_code.setText("RM\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    prepareRMPromo();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code = promoList.get(position).getCode();
                            Log.e("clickedRM-->",sm_code+"GOTO--?RMAM");
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "RMAM");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "S");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                if (user_flag.equals("RMAM")) {
                    Log.e("clicked==>","RMAM--272");
                    user_show1.setText("Area Manager Promo Sample Followup");
                    mpo_code.setText("AM\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    prepareRMAM();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Log.e("clickEvent-->","Area Manager Promo Sampe Followup click");
                            String sm_code = promoList.get(position).getCode();
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            Log.e("userFlag-->",sm_code);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "AM");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "S");
                            i.putExtra("user_code", sm_code);
                           startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }
                break;

            case "P":
                if (user_flag.equals("MPO")) {
                    user_show1.setText(userName + " - " + "Promo PPM Followup");
                    mpo_code.setText("MPO\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setText("PPM\nName");
                    sqnty1.setText("Promo\nType");

                    prepareMPOPromo();
                }

                if (user_flag.equals("AM")) {
                    user_show1.setText(userName + " - " + "Promo PPM Followup");
                    mpo_code.setText("AM\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setText("PPM\nName");
                    sqnty1.setText("Promo\nType");

                    prepareFMPromo();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code = promoList.get(position).getCode();
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "AMMPO");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "P");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                if (user_flag.equals("AMMPO")) {
                    user_show1.setText("MPO Promo PPM Followup");
                    mpo_code.setText("MPO\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setText("PPM\nName");
                    sqnty1.setText("Promo\nType");

                    prepareAMMpo();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code =  promoList.get(position).getCode();
                          
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "MPO");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "P");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                if (user_flag.equals("RM")) {
                    user_show1.setText(userName + " - " + "Promo PPM Followup");
                    mpo_code.setText("RM\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setText("PPM\nName");
                    sqnty1.setText("Promo\nType");

                    prepareRMPromo();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code = promoList.get(position).getCode();
                          
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "RMAM");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "P");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                if (user_flag.equals("RMAM")) {
                    user_show1.setText("Area Manager Promo PPM Followup");
                    mpo_code.setText("AM\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setText("PPM\nName");
                    sqnty1.setText("Promo\nType");
                    prepareRMAM();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code = promoList.get(position).getCode();
                          
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "AMMPO");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "P");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }
                break;

            case "G":
                if (user_flag.equals("RM")) {
                    user_show1.setText(userName + " - " + "Promo Gift Followup");
                    mpo_code.setText("RM\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setText("Gift\nName");
                    sqnty1.setText("Promo\nType");

                    prepareRMPromo();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code = promoList.get(position).getCode();
                          
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "RMAM");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "G");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                if (user_flag.equals("RMAM")) {
                    user_show1.setText("Area Manager Promo Gift Followup");
                    mpo_code.setText("AM\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setText("Gift\nName");
                    sqnty1.setText("Promo\nType");

                    prepareRMAM();
                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code = promoList.get(position).getCode();
                          
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "AMMPO");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "G");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                if (user_flag.equals("AM")) {
                    user_show1.setText(userName + " - " + "Promo Gift Followup");
                    mpo_code.setText("AM\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

                    ssellvelue.setText("Gift\nName");
                    sqnty1.setText("Promo\nType");

                    prepareFMPromo();

                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code = promoList.get(position).getCode();
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "AMMPO");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "G");
                            i.putExtra("user_code", sm_code);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                if (user_flag.equals("AMMPO")) {
                    user_show1.setText("MPO Gift PPM Followup");
                    mpo_code.setText("MPO\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setText("Gift\nName");
                    sqnty1.setText("Promo\nType");

                    prepareAMMpo();
                    rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String sm_code =  promoList.get(position).getCode();
                          
                            Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                            i.putExtra("UserName", sm_code);
                            i.putExtra("UserName_2", sm_code);
                            i.putExtra("user_flag", "MPO");
                            i.putExtra("userName", sm_code);
                            i.putExtra("userName", sm_code);
                            i.putExtra("promo_type", "G");
                            i.putExtra("user_code", sm_code);
                           startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                if (user_flag.equals("MPO")) {
                    user_show1.setText(userName + " - " + "Promo Gift Followup");
                    mpo_code.setText("MPO\nCode");
                    mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    ssellvelue.setText("Promo\nType");
                    sqnty1.setText("Gift\nName");

                    achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                    gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

                    prepareMPOPromo();
                }
                break;
        }

        setUpRecyclerView();
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

        submitBtn.setOnClickListener(new OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(final View v) {
                try {
                        checkSubtype();
                        KeyboardUtils.hideKeyboard(PromoMaterialFollowup.this);
                        switch (promo_type) {

                        case "S":
                            if (user_flag.equals("MPO")) {
                                user_show1.setText(String.format("%s - MPO Promo Sample Followup", userName));
                                mpo_code.setText("MPO\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                prepareMPOPromo();
                            }

                            if (user_flag.equals("AM")) {
                                Log.e("AMsTARTS-->","prepareFMPromo()");

                                user_show1.setText(userName + " - " + "Promo Sample Followup");
                                mpo_code.setText("AM\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

                                prepareFMPromo();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        String sm_code = promoList.get(position).getCode();
                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "AMMPO");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "S");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("AMMPO")) {
                                Log.e("clicked==>","AMMPO--272");

                                user_show1.setText("MPO Promo Sample Followup");
                                mpo_code.setText("MPO\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                prepareAMMpo();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        //String sm_code = (String) productListView.getAdapter().getItem(position);
                                        String sm_code = promoList.get(position).getCode();
                                        // Log.e("smcode-->",sm_code);
                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "MPO");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "S");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("RM")) {
                                user_show1.setText(String.format("%s - Promo Sample Followup", userName));
                                mpo_code.setText("RM\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                prepareRMPromo();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        String sm_code = promoList.get(position).getCode();
                                        Log.e("clickedRM-->",sm_code+"GOTO--?RMAM");
                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "RMAM");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "S");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("RMAM")) {
                                Log.e("clicked==>","RMAM--272");
                                user_show1.setText("Area Manager Promo Sample Followup");
                                mpo_code.setText("AM\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                prepareRMAM();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        Log.e("clickEvent-->","Area Manager Promo Sampe Followup click");
                                        String sm_code = promoList.get(position).getCode();
                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        Log.e("userFlag-->",sm_code);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "AM");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "S");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }
                            break;

                        case "P":
                            if (user_flag.equals("MPO")) {
                                user_show1.setText(userName + " - " + "Promo PPM Followup");
                                mpo_code.setText("MPO\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setText("PPM\nName");
                                sqnty1.setText("Promo\nType");

                                prepareMPOPromo();
                            }

                            if (user_flag.equals("AM")) {
                                user_show1.setText(userName + " - " + "Promo PPM Followup");
                                mpo_code.setText("AM\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setText("PPM\nName");
                                sqnty1.setText("Promo\nType");

                                prepareFMPromo();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        String sm_code = promoList.get(position).getCode();
                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "AMMPO");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "P");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("AMMPO")) {
                                user_show1.setText("MPO Promo PPM Followup");
                                mpo_code.setText("MPO\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setText("PPM\nName");
                                sqnty1.setText("Promo\nType");

                                prepareAMMpo();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        String sm_code =  promoList.get(position).getCode();

                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "MPO");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "P");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("RM")) {
                                user_show1.setText(userName + " - " + "Promo PPM Followup");
                                mpo_code.setText("RM\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setText("PPM\nName");
                                sqnty1.setText("Promo\nType");

                                prepareRMPromo();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        String sm_code = promoList.get(position).getCode();

                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "RMAM");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "P");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("RMAM")) {
                                user_show1.setText("Area Manager Promo PPM Followup");
                                mpo_code.setText("AM\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setText("PPM\nName");
                                sqnty1.setText("Promo\nType");
                                prepareRMAM();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        String sm_code = promoList.get(position).getCode();

                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "AMMPO");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "P");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }
                            break;

                        case "G":
                            if (user_flag.equals("RM")) {
                                user_show1.setText(userName + " - " + "Promo Gift Followup");
                                mpo_code.setText("RM\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setText("Gift\nName");
                                sqnty1.setText("Promo\nType");

                                prepareRMPromo();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        String sm_code = promoList.get(position).getCode();

                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "RMAM");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "G");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("RMAM")) {
                                user_show1.setText("Area Manager Promo Gift Followup");
                                mpo_code.setText("AM\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setText("Gift\nName");
                                sqnty1.setText("Promo\nType");

                                prepareRMAM();
                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        String sm_code = promoList.get(position).getCode();

                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "AMMPO");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "G");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("AM")) {
                                user_show1.setText(userName + " - " + "Promo Gift Followup");
                                mpo_code.setText("AM\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

                                ssellvelue.setText("Gift\nName");
                                sqnty1.setText("Promo\nType");

                                prepareFMPromo();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {

                                        String sm_code = promoList.get(position).getCode();
                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "AMMPO");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "G");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("AMMPO")) {
                                user_show1.setText("MPO Gift PPM Followup");
                                mpo_code.setText("MPO\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setText("Gift\nName");
                                sqnty1.setText("Promo\nType");

                                prepareAMMpo();

                                rvCompany.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCompany, new RecyclerTouchListener.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        String sm_code =  promoList.get(position).getCode();

                                        Intent i = new Intent(PromoMaterialFollowup.this, PromoMaterialFollowup.class);
                                        i.putExtra("UserName", sm_code);
                                        i.putExtra("UserName_2", sm_code);
                                        i.putExtra("user_flag", "MPO");
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("userName", sm_code);
                                        i.putExtra("promo_type", "G");
                                        i.putExtra("user_code", sm_code);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                            }

                            if (user_flag.equals("MPO")) {
                                user_show1.setText(userName + " - " + "Promo Gift Followup");
                                mpo_code.setText("MPO\nCode");
                                mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sserial.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                week4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                ssellvelue.setText("Promo\nType");
                                sqnty1.setText("Gift\nName");

                                achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                                gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

                                prepareMPOPromo();
                            }
                            break;
                    }
                        setUpRecyclerView();
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        departmentlist = new ArrayList<Customer>();
        spin_brand = findViewById(R.id.spin_brand);
        rvCompany = findViewById(R.id.rvCompany);
        headerScroll = findViewById(R.id.headerScroll);
        user_show1 =  findViewById(R.id.user_show1);
        back_btn = findViewById(R.id.backbt);
        ed_date = findViewById(R.id.ed_date);
        actv_type = findViewById(R.id.actv_type);
        submitBtn = findViewById(R.id.submitBtn);
        ed_date.setFocusableInTouchMode(true);
        ed_date.setFocusable(true);
        ed_date.requestFocus();
        ed_date.setClickable(true);
        ed_date.setInputType(InputType.TYPE_NULL);
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

    private  void calenderUI(){
        ed_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();

                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        monthYearStr = year + "-" + (month + 1) + "-" + i2;
                        String test = monthYearStr;
                        year_val = test.substring(0, 4);
                        int month_int = month;
                        month_val = String.valueOf(month_int);
                        proposed_date1 = "01" + "/" + (month) + "/" + year;
                        if (month_val.equals(String.valueOf(1))) {
                            month_name_val = "January";
                            month_name ="JAN";
                        } else if (month_val.equals(String.valueOf(2))) {
                            month_name_val = "Feb";
                            month_name ="FEB";
                        } else if (month_val.equals(String.valueOf(3))) {
                            month_name_val = "March";
                            month_name ="MAR";
                        } else if (month_val.equals(String.valueOf(4))) {
                            month_name_val = "April";
                            month_name ="APR";
                        } else if (month_val.equals(String.valueOf(5))) {
                            month_name_val = "May";
                            month_name ="MAY";
                        } else if (month_val.equals(String.valueOf(6))) {
                            month_name_val = "June";
                            month_name ="JUN";
                        } else if (month_val.equals(String.valueOf(7))) {
                            month_name_val = "July";
                            month_name ="JUL";
                        } else if (month_val.equals(String.valueOf(8))) {
                            month_name_val = "August";
                            month_name ="AUG";
                        } else if (month_val.equals(String.valueOf(9))) {
                            month_name_val = "September";
                            month_name ="SEP";
                        } else if (month_val.equals(String.valueOf(10))) {
                            month_name_val = "October";
                            month_name ="OCT";
                        } else if (month_val.equals(String.valueOf(11))) {
                            month_name_val = "November";
                            month_name ="NOV";
                        } else if (month_val.equals(String.valueOf(12))) {
                            month_name_val = "December";
                            month_name ="DEC";
                        }
                        proposed_date2 = "01" + "-" + month_name + "-" + year;
                        Log.e("proposed_date1", proposed_date1);
                        Log.e("proposed_date1", proposed_date2);
                        ed_date.setText(proposed_date2);
                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });
    }

    private void checkSubtype(){
        if (actv_type.getText().toString().equals("")|| actv_type.getText().toString().equals(null)){
            promo_subtype = "xx";
        } else {
            promo_subtype = actv_type.getText().toString();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autoEvent(){
        actv_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // hideKeyBoard();
                actv_type.showDropDown();
                return false;
            }
        });
    }

    public void prepareMPOPromo() {
        pDialog = new ProgressDialog(PromoMaterialFollowup.this);
        pDialog.setMessage("Promo Loading...");
        pDialog.setTitle("Promo Followup");
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.mpo_sample_followup(user_code,user_flag,promo_type,proposed_date2,promo_subtype);
        promoList.clear();

        call.enqueue(new Callback<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Patient>> call, @NonNull retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();

                if (response.isSuccessful()) {
                    if (giftitemCount != null) {
                        for (int i = 0; i < giftitemCount.size(); i++) {
                            promoList.add(new Promo( giftitemCount.get(i).getSerial(),
                                    giftitemCount.get(i).getMpocode(),
                                    giftitemCount.get(i).getMonth(), giftitemCount.get(i).getPacksize(),
                                    giftitemCount.get(i).getSamplename(), giftitemCount.get(i).getType(),
                                    giftitemCount.get(i).getWeek1(), giftitemCount.get(i).getWeek2(),
                                    giftitemCount.get(i).getWeek3(),giftitemCount.get(i).getWeek4(),
                                    giftitemCount.get(i).getTotal()));
                        }
                    }
                    promoAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(PromoMaterialFollowup.this,"No data Available",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Patient>> call, @NonNull Throwable t) {
                  pDialog.dismiss();
                  prepareMPOPromo();
            }
        });
    }

    public void prepareFMPromo() {
        Log.e("fm_promo_followup",user_code+user_flag+promo_type+proposed_date2+promo_subtype);

        pDialog = new ProgressDialog(PromoMaterialFollowup.this);
        pDialog.setMessage("Promo Loading...");
        pDialog.setTitle("Promo Followup");
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.fm_promo_followup(user_code,user_flag,promo_type,proposed_date2,promo_subtype);
        promoList.clear();

        call.enqueue(new Callback<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<Patient>> call, @NonNull retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();
             
                if (response.isSuccessful()) {
                    for (int i = 0; i < giftitemCount.size(); i++) {
                        promoList.add(new Promo( giftitemCount.get(i).getSerial(),
                                giftitemCount.get(i).getMpocode(),
                                giftitemCount.get(i).getMonth(), giftitemCount.get(i).getPacksize(),
                                giftitemCount.get(i).getSamplename(), giftitemCount.get(i).getType(),
                                giftitemCount.get(i).getWeek1(), giftitemCount.get(i).getWeek2(),
                                giftitemCount.get(i).getWeek3(),giftitemCount.get(i).getWeek4(),
                                giftitemCount.get(i).getTotal()));
                    }
                    promoAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(PromoMaterialFollowup.this,"No data Available",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                pDialog.dismiss();
                prepareMPOPromo();
            }
        });
    }

    public void prepareRMPromo() {
        pDialog = new ProgressDialog(PromoMaterialFollowup.this);
       
        pDialog.setMessage("Promo Loading...");
        pDialog.setTitle("Promo Followup");
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.rm_promo_followup(user_code,user_flag,promo_type,proposed_date2,promo_subtype);

        call.enqueue(new Callback<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Patient>> call,  retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();
                promoList.clear();
                if (response.isSuccessful()) {
                    for (int i = 0; i < giftitemCount.size(); i++) {
                        promoList.add(new Promo( giftitemCount.get(i).getSerial(),
                                giftitemCount.get(i).getMpocode(),
                                giftitemCount.get(i).getMonth(), giftitemCount.get(i).getPacksize(),
                                giftitemCount.get(i).getSamplename(), giftitemCount.get(i).getType(),
                                giftitemCount.get(i).getWeek1(), giftitemCount.get(i).getWeek2(),
                                giftitemCount.get(i).getWeek3(),giftitemCount.get(i).getWeek4(),
                                giftitemCount.get(i).getTotal()));
                    }
                    promoAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(PromoMaterialFollowup.this,"No data Available",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                pDialog.dismiss();
                prepareMPOPromo();
            }
        });
    }

    public void prepareAMMpo() {
        promoList.clear();
        Log.e("prepareAMMPO==>",promo_subtype+"\n" +user_code + "\n"+ user_flag + "\n"+ promo_type);

        pDialog = new ProgressDialog(PromoMaterialFollowup.this);
        //pDialog.setMax(100);
        pDialog.setMessage("Promo Loading...");
        pDialog.setTitle("Promo Followup");
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.fm_mpo_promo_followup(user_code,user_flag,promo_type,proposed_date2,promo_subtype);

        call.enqueue(new Callback<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<Patient>> call, @NonNull Response<List<Patient>> response) {
                Log.e("myError",new Gson().toJson(response.body()));
                List<Patient> giftitemCount = response.body();
                //Log.e("count==>", String.valueOf(giftitemCount.size()));
                if (response.isSuccessful()) {
                    for (int i = 0; i < giftitemCount.size(); i++) {
                        promoList.add(new Promo( giftitemCount.get(i).getSerial(),
                                giftitemCount.get(i).getMpocode(),
                                giftitemCount.get(i).getMonth(), giftitemCount.get(i).getPacksize(),
                                giftitemCount.get(i).getSamplename(), giftitemCount.get(i).getType(),
                                giftitemCount.get(i).getWeek1(), giftitemCount.get(i).getWeek2(),
                                giftitemCount.get(i).getWeek3(),giftitemCount.get(i).getWeek4(),
                                giftitemCount.get(i).getTotal()));
                    }
                    promoAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(PromoMaterialFollowup.this,"No data Available",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                pDialog.dismiss();
                prepareMPOPromo();
            }
        });
    }

    public void prepareRMAM() {
        Log.e("PreapreRMAM==>",user_code+user_flag+promo_type+proposed_date2+promo_subtype);

        pDialog = new ProgressDialog(PromoMaterialFollowup.this);
        pDialog.setMessage("Promo Loading...");
        pDialog.setTitle("Promo Followup");
        pDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.fm_mpo_promo_followup(user_code,user_flag,promo_type,proposed_date2,promo_subtype);
        promoList.clear();

        call.enqueue(new Callback<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse( Call<List<Patient>> call, retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();
                   if (response.isSuccessful()) {
                       for (int i = 0; i < giftitemCount.size(); i++) {
                           promoList.add(new Promo( giftitemCount.get(i).getSerial(),
                                   giftitemCount.get(i).getMpocode(),
                                   giftitemCount.get(i).getMonth(), giftitemCount.get(i).getPacksize(),
                                   giftitemCount.get(i).getSamplename(), giftitemCount.get(i).getType(),
                                   giftitemCount.get(i).getWeek1(), giftitemCount.get(i).getWeek2(),
                                   giftitemCount.get(i).getWeek3(),giftitemCount.get(i).getWeek4(),
                                   giftitemCount.get(i).getTotal()));
                       }
                       promoAdapter.notifyDataSetChanged();
                       pDialog.dismiss();
                   } else {
                       pDialog.dismiss();
                       Toast.makeText(PromoMaterialFollowup.this,"No data Available",Toast.LENGTH_LONG).show();
                   }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                pDialog.dismiss();
                prepareRMAM();
            }
        });
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
        actv_type.setThreshold(1);
        actv_type.setAdapter(Adapter);
        actv_type.setTextColor(Color.BLUE);
        promo_subtype = actv_type.getText().toString();
    }

    @SuppressLint("StaticFieldLeak")
    class GetBrandList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("userflag==>",user_flag+"---"+user_code);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_code", user_code));
            params.add(new BasicNameValuePair("user_flag", user_flag));
            params.add(new BasicNameValuePair("promo_type", promo_type));
            params.add(new BasicNameValuePair("promo_month", proposed_date2));
            ServiceHandler jsonParser = new ServiceHandler();

            String URL_BRAND = BASE_URL+"promofollowupv2/get_List.php";
            Log.e("URL_BRAND==>",URL_BRAND);

            String json;
            json = jsonParser.makeServiceCall(URL_BRAND, ServiceHandler.POST, params);
            departmentlist.clear();

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
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            populatebrandSpinner();
        }
    }

    public void setUpRecyclerView() {
        promoAdapter = new PromoAdapter(PromoMaterialFollowup.this, promoList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvCompany.setLayoutManager(manager);
        rvCompany.setAdapter(promoAdapter);
        rvCompany.addItemDecoration(new DividerItemDecoration(PromoMaterialFollowup.this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void finishActivity(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {}

    protected void onPostExecute() {}

    private void view() {
        Intent i = new Intent(PromoMaterialFollowup.this, com.opl.pharmavector.Report.class);
        startActivity(i);
        finish();
    }
}







