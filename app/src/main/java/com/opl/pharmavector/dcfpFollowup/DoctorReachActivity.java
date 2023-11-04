package com.opl.pharmavector.dcfpFollowup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.AmDashboard;
import com.opl.pharmavector.AssistantManagerDashboard;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.GMDashboard1;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RmDashboard;
import com.opl.pharmavector.SalesManagerDashboard;
import com.opl.pharmavector.achieve.AchieveEarnActivity;
import com.opl.pharmavector.achieve.AchieveMonthList;
import com.opl.pharmavector.achieve.AchvMonthModel;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.mrd_pres_report.adapter.MRDAdapter;
import com.opl.pharmavector.mrd_pres_report.adapter.MRDDocAdapter;
import com.opl.pharmavector.promomat.adapter.RecyclerTouchListener;
import com.opl.pharmavector.promomat.model.Promo;
import com.opl.pharmavector.promomat.util.FixedGridLayoutManager;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorReachActivity extends Activity {
    String team_name, month_name;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public ProgressDialog pDialog, pDialog2, pDialog3, pDialog4, pDialog5, pDialog6, pDialogSelf, pDialogProd;
    Button back_btn;
    public int success;
    public String message, ord_no;
    public TextView totqty, totval, mpo_code, fromdate, todate;
    public String userName_1, userName, active_string, act_desiredString, selected_service_no, selected_service_no_serial, summary_type;
    public String from_date, to_date;
    TextView user_show1;
    public String p_code;
    public String asm_flag;
    public String sm_flag;
    public String gm_flag;
    public String user_code;
    public String fm_code;
    public String report_flag;
    public String user_flag;
    public String UserName_2;
    public String rm_flag = "N";
    public String fm_flag = "N";
    public String mpo_flag = "N";
    public String self_flag, temp_mpo_code;
    public static String docAdapterflag="N";
    int scrollX0 = 0, scrollX = 0, scrollX2 = 0, scrollX3 = 0, scrollX4 = 0, scrollX5 = 0, scrollX6 = 0, scrollX7 = 0, scrollX8 = 0;
    RecyclerView rvNational, rvDivision, rvRegion, rvArea, rvMpo, rvSegment, rvSelf, rvDoc, rvDocProduct;
    HorizontalScrollView headerScroll0, headerScroll, headerScroll2, headerScroll3, headerScroll4, headerScroll5, headerScroll6, headerScroll7, headerScroll8;
    DoctorReachAdapter promoAdapter0, promoAdapter, promoAdapter2, promoAdapter3, promoAdapter4, promoAdapter5, promoAdapter6;
    MRDDocAdapter promoAdapter7, promoAdapter8;
    TextView txt_self;
    List<DoctorReachSelfList> selfList = new ArrayList<>();
    List<DoctorReachSelfList> promoList = new ArrayList<>();
    List<DoctorReachSelfList> divisionList = new ArrayList<>();
    List<DoctorReachSelfList> regionList = new ArrayList<>();
    List<DoctorReachSelfList> areaList = new ArrayList<>();
    List<DoctorReachSelfList> mpoList = new ArrayList<>();
    List<DoctorReachSelfList> segmentList = new ArrayList<>();
    List<DoctorReachSelfList> docList = new ArrayList<>();
    List<DoctorReachSelfList> docProductList = new ArrayList<>();
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    MaterialSpinner monthSpinner;
    Button btn_search;
    String service_month;
    CardView selfcardview, smcardview, asmcardview, rmcardview, amcardview, mpocardview, segmentcardview, doctorcardview, doctorproductcardview;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_reach);

        initViews();
        getDoctorReachMonth();
        if ("SPI".equals(report_flag)) {
            summary_type = "SPI";
            user_show1.setText("SPI Report");
            txt_self.setText("National");

            if (sm_flag.equals("Y")) {
                txt_self.setText("Division");
                smcardview.setVisibility(View.GONE);
                user_code = SalesManagerDashboard.globalSMCode;
                self_flag = "SM";
            }
            else if (asm_flag.equals("Y")) {
                txt_self.setText("Zone");
                smcardview.setVisibility(View.GONE);
                asmcardview.setVisibility(View.GONE);
                user_code = AssistantManagerDashboard.globalASMCode;
                self_flag = "ASM";
            }
            else if (rm_flag.equals("Y")) {
                txt_self.setText("Region");
                smcardview.setVisibility(View.GONE);
                asmcardview.setVisibility(View.GONE);
                rmcardview.setVisibility(View.GONE);
                user_code = RmDashboard.globalRMCode;
                self_flag = "ASM";
            }
            else if (fm_flag.equals("Y")) {
                txt_self.setText("Area");
                smcardview.setVisibility(View.GONE);
                asmcardview.setVisibility(View.GONE);
                rmcardview.setVisibility(View.GONE);
                amcardview.setVisibility(View.GONE);
                user_code = AmDashboard.globalFMCode;
                self_flag = "AM";
            }
            else if (mpo_flag.equals("Y")) {
                txt_self.setText("Territory");
                smcardview.setVisibility(View.GONE);
                asmcardview.setVisibility(View.GONE);
                rmcardview.setVisibility(View.GONE);
                amcardview.setVisibility(View.GONE);
                mpocardview.setVisibility(View.GONE);
                user_code = Dashboard.globalmpocode;
                self_flag = "MPO";
            }
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gm_flag.equals("Y")) {
                        if (monthSpinner.getText().toString().equals("") || monthSpinner.getText().toString().equals(" ")) {
                            showSnack();
                        } else {
                            service_month = monthSpinner.getText().toString();
                            self_flag = "GM";
                            user_code = GMDashboard1.globalAdmin;

                            prepareSelfData();
                            setUpselfRV();
                            prepareSMdata();
                            setUpRecyclerView();

                            rvNational.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvNational, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = promoList.get(position).getFfCode();
                                    prepareASMdata();
                                    setUpRV_Division();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvDivision.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvDivision, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = divisionList.get(position).getFfCode();
                                    Log.e("myASmcode-->", user_code);
                                    prepareRegiondata();
                                    setUpRV_Region();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvRegion.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvRegion, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = regionList.get(position).getFfCode();
                                    prepareAreadata();
                                    setUpRV_Area();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvArea.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvArea, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = areaList.get(position).getFfCode();
                                    fm_code = user_code;
                                    //prepareMpodata();
                                    //setUpRV_MPO();
                                    //prepareDocList();
                                    //setUpRV_DoclIST();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvMpo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = mpoList.get(position).getFfCode();
                                    Log.e("mpo_Code-->", user_code);
                                    temp_mpo_code = mpoList.get(position).getFfCode();

                                    //prepareSegmentData();
                                    //setUpRV_Segment();
                                    //prepareDocList();
                                    //setUpRV_DoclIST();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvDoc.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = docList.get(position).getFfCode();
                                    Log.e("doc_code-->", user_code);

                                    //prepareProdList();
                                    //setUpRV_ProdList();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        }
                    } else if (sm_flag.equals("Y")) {
                        if (monthSpinner.getText().toString().equals("") || monthSpinner.getText().toString().equals(" ")) {
                            showSnack();
                        } else {
                            service_month = monthSpinner.getText().toString();
                            user_code = SalesManagerDashboard.globalSMCode;
                            self_flag = "SM";
                            prepareSelfData();
                            setUpselfRV();
                            //prepareASMdata();
                            //setUpRV_Division();

                            rvDivision.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvDivision, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = divisionList.get(position).getFfCode();
                                    Log.e("myASmcode-->", user_code);
                                    //prepareRegiondata();
                                    //setUpRV_Region();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvRegion.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvRegion, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = regionList.get(position).getFfCode();
                                    Log.e("myRmcode-->", user_code);
                                    //prepareAreadata();
                                    //setUpRV_Area();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvArea.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvArea, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = areaList.get(position).getFfCode();
                                    fm_code = user_code;
                                    Log.e("myFMcode-->", user_code);
                                    //prepareMpodata();
                                    //setUpRV_MPO();
                                    //prepareDocList();
                                    //setUpRV_DoclIST();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvMpo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = mpoList.get(position).getFfCode();
                                    temp_mpo_code = mpoList.get(position).getFfCode();
                                    Log.e("Clickedmpo_Code-->", user_code);

                                    //prepareSegmentData();
                                    //setUpRV_Segment();
                                    //prepareDocList();
                                    //setUpRV_DoclIST();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvDoc.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = docList.get(position).getFfCode();
                                    Log.e("doc_code-->", user_code);

                                    //prepareProdList();
                                    //setUpRV_ProdList();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        }
                    } else if (asm_flag.equals("Y")) {
                        if (monthSpinner.getText().toString().equals("") || monthSpinner.getText().toString().equals(" ")) {
                            showSnack();
                        } else {
                            service_month = monthSpinner.getText().toString();
                            user_code = AssistantManagerDashboard.globalASMCode;
                            self_flag = "ASM";
                            prepareSelfData();
                            setUpselfRV();
                            //prepareRegiondata();
                            //setUpRV_Region();

                            rvRegion.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvRegion, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = regionList.get(position).getFfCode();
                                    Log.e("myRmcode-->", user_code);
                                    //prepareAreadata();
                                    //setUpRV_Area();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvArea.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvArea, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = areaList.get(position).getFfCode();
                                    fm_code = user_code;
                                    Log.e("myFMcode-->", user_code);
//                                        prepareMpodata();
//                                        setUpRV_MPO();
//                                        prepareDocList();
//                                        setUpRV_DoclIST();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvMpo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = mpoList.get(position).getFfCode();
                                    Log.e("mpo_Code-->", user_code);
                                    temp_mpo_code = mpoList.get(position).getFfCode();

//                                        prepareSegmentData();
//                                        setUpRV_Segment();
//                                        prepareDocList();
//                                        setUpRV_DoclIST();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvDoc.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = docList.get(position).getFfCode();
                                    Log.e("doc_code-->", user_code);

                                    //prepareProdList();
                                    //setUpRV_ProdList();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        }
                    } else if (rm_flag.equals("Y")) {
                        if (monthSpinner.getText().toString().equals("") || monthSpinner.getText().toString().equals(" ")) {
                            showSnack();
                        } else {
                            service_month = monthSpinner.getText().toString();
                            user_code = RmDashboard.globalRMCode;

                            self_flag = "RM";
                            prepareSelfData();
                            setUpselfRV();
                            //prepareAreadata();
                            //setUpRV_Area();

                            rvArea.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvArea, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = areaList.get(position).getFfCode();
                                    fm_code = user_code;
                                    Log.e("myFMcode-->", user_code);
//                                        prepareMpodata();
//                                        setUpRV_MPO();
//                                        prepareDocList();
//                                        setUpRV_DoclIST();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvMpo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = mpoList.get(position).getFfCode();
                                    temp_mpo_code = mpoList.get(position).getFfCode();
                                    Log.e("mpo_Code-->", user_code);
//                                        prepareSegmentData();
//                                        setUpRV_Segment();
//                                        prepareDocList();
//                                        setUpRV_DoclIST();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvDoc.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = docList.get(position).getFfCode();
                                    Log.e("doc_code-->", user_code);
                                    //prepareProdList();
                                    //setUpRV_ProdList();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        }

                    } else if (fm_flag.equals("Y")) {
                        if (monthSpinner.getText().toString().equals("") || monthSpinner.getText().toString().equals(" ")) {
                            showSnack();
                        } else {
                            service_month = monthSpinner.getText().toString();
                            user_code = AmDashboard.globalFMCode;
                            self_flag = "AM";
                            prepareSelfData();
                            setUpselfRV();
                            //prepareMpodata();
                            //setUpRV_MPO();
                            //prepareDocList();
                            //setUpRV_DoclIST();

                            rvMpo.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = mpoList.get(position).getFfCode();
                                    temp_mpo_code = mpoList.get(position).getFfCode();
                                    Log.e("mpo_Code-->", user_code);

                                    //prepareSegmentData();
                                    //setUpRV_Segment();
                                    //prepareDocList();
                                    //setUpRV_DoclIST();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                            rvDoc.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = docList.get(position).getFfCode();
                                    Log.e("doc_code-->", user_code);

                                    //prepareProdList();
                                    //setUpRV_ProdList();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        }
                    } else if (mpo_flag.equals("Y")) {
                        if (monthSpinner.getText().toString().equals("") || monthSpinner.getText().toString().equals(" ")) {
                            showSnack();
                        } else {
                            service_month = monthSpinner.getText().toString();
                            user_code = Dashboard.globalmpocode;
                            temp_mpo_code = user_code;
                            self_flag = "MPO";
                            prepareSelfData();
                            setUpselfRV();
                            //prepareMpodata();
                            //setUpRV_MPO();
                            //prepareSegmentData();
                            //setUpRV_Segment();
                            //prepareDocList();
                            //setUpRV_DoclIST();

                            rvDoc.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMpo, new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    user_code = docList.get(position).getFfCode();
                                    Log.e("doc_code-->", user_code);

                                    //prepareProdList();
                                    //setUpRV_ProdList();
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        }
                    }
                }
            });
        }
        onScrollEvents();
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        txt_self = findViewById(R.id.txt_self);
        rvSelf = findViewById(R.id.rvSelf);
        rvNational = findViewById(R.id.rvNational);
        rvDivision = findViewById(R.id.rvDivision);
        rvRegion = findViewById(R.id.rvRegion);
        rvArea = findViewById(R.id.rvArea);
        rvMpo = findViewById(R.id.rvMpo);
        rvSegment = findViewById(R.id.rvSegment);
        rvDoc = findViewById(R.id.rvDoc);
        rvDocProduct = findViewById(R.id.rvDocProduct);

        headerScroll = findViewById(R.id.headerScroll);
        headerScroll2 = findViewById(R.id.headerScroll2);
        headerScroll3 = findViewById(R.id.headerScroll3);
        headerScroll4 = findViewById(R.id.headerScroll4);
        headerScroll5 = findViewById(R.id.headerScroll5);
        headerScroll6 = findViewById(R.id.headerScroll6);
        headerScroll0 = findViewById(R.id.headerScroll0);
        headerScroll7 = findViewById(R.id.headerScroll7);
        headerScroll8 = findViewById(R.id.headerScroll8);

        user_show1 = findViewById(R.id.user_show1);
        back_btn = findViewById(R.id.backbt);
        btn_search = findViewById(R.id.btn_search);
        monthSpinner = findViewById(R.id.monthSpinner);

        selfcardview = findViewById(R.id.selfcardview);
        smcardview = findViewById(R.id.smcardview);
        asmcardview = findViewById(R.id.asmcardview);
        rmcardview = findViewById(R.id.rmcardview);
        amcardview = findViewById(R.id.fmcardview);
        mpocardview = findViewById(R.id.mpocardview);
        segmentcardview = findViewById(R.id.segmentcardview);
        doctorcardview = findViewById(R.id.doctorcardview);
        doctorproductcardview = findViewById(R.id.doctorproductcardview);

        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        Bundle b = getIntent().getExtras();
        userName = b.getString("userName");
        report_flag = b.getString("report_flag");
        gm_flag = b.getString("gm_flag");
        asm_flag = b.getString("asm_flag");
        sm_flag = b.getString("sm_flag");
        rm_flag = b.getString("rm_flag");
        fm_flag = b.getString("fm_flag");
        mpo_flag = b.getString("mpo_flag");
    }

    private void initMonthSpinner(List<AchieveMonthList> monthList) {
        MaterialSpinner monthSpinner = findViewById(R.id.monthSpinner);
        ArrayList<String> monthNameList = new ArrayList<>();

        if (monthList.size() > 0) {
            for (AchieveMonthList monthName : monthList) {
                monthNameList.add(monthName.getMnyrDesc());
            }
        }

        monthSpinner.setItems(monthNameList);
        monthSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                team_name = String.valueOf(item).trim();

                for (int i = 0; i < monthList.size(); i++) {
                    if (monthList.get(i).getMnyrDesc().contains(team_name)) {
                        month_name = monthList.get(i).getMnyr();
                    }
                }
                Log.d("month name", month_name);
            }
        });
    }

    private void getDoctorReachMonth() {
        ProgressDialog pDialog = new ProgressDialog(DoctorReachActivity.this);
        pDialog.setMessage("Loading Month ...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AchvMonthModel> call = apiInterface.getAchievementMonth();

        call.enqueue(new Callback<AchvMonthModel>() {
            @Override
            public void onResponse(Call<AchvMonthModel> call, Response<AchvMonthModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<AchieveMonthList> achvMonthList = null;

                    if (response.body() != null) {
                        achvMonthList = (response.body()).getAchvMonthList();
                    }
                    initMonthSpinner(Objects.requireNonNull(achvMonthList));
                    Log.d("Month List -- : ", String.valueOf(achvMonthList));
                }
            }

            @Override
            public void onFailure(Call<AchvMonthModel> call, Throwable t) {
                pDialog.dismiss();
                Log.d("Data load problem--->", "Failed to Retried Data For-- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void showSnack() {
        new Thread() {
            public void run() {
                DoctorReachActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "Please select Month";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    private void onScrollEvents() {
        rvSelf.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX0 += dx;
                headerScroll0.scrollTo(scrollX0, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        rvNational.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        rvDivision.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX2 += dx;
                headerScroll2.scrollTo(scrollX2, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        rvRegion.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX3 += dx;
                headerScroll3.scrollTo(scrollX3, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        rvArea.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX4 += dx;
                headerScroll4.scrollTo(scrollX4, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        rvMpo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX5 += dx;
                headerScroll5.scrollTo(scrollX5, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        rvSegment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX6 += dx;
                headerScroll6.scrollTo(scrollX6, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        rvDoc.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX7 += dx;
                headerScroll7.scrollTo(scrollX7, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        rvDocProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX8 += dx;
                headerScroll8.scrollTo(scrollX8, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void setUpselfRV() {
        promoAdapter0 = new DoctorReachAdapter(DoctorReachActivity.this, selfList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvSelf.setLayoutManager(manager);
        rvSelf.setAdapter(promoAdapter0);
        rvSelf.addItemDecoration(new DividerItemDecoration(DoctorReachActivity.this, DividerItemDecoration.VERTICAL));
    }

    public void prepareSelfData() {
        pDialogSelf = new ProgressDialog(DoctorReachActivity.this);
        pDialogSelf.setMessage("Loading...");
        pDialogSelf.setTitle("Please wait");

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.e("selfData-------->", user_code + "\n" + self_flag + "\n" + summary_type + "\n" + service_month);
        Call<DoctorReachSelfModel> call = apiInterface.doc_reach_self_followup(user_code, month_name);
        selfList.clear();

        call.enqueue(new Callback<DoctorReachSelfModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<DoctorReachSelfModel> call, retrofit2.Response<DoctorReachSelfModel> response) {
                List<DoctorReachSelfList> giftitemCount = Objects.requireNonNull(response.body()).getDoctorSelfLists();

                if (response.isSuccessful()) {
                    selfList.addAll(giftitemCount);
//                    for (int i = 0; i < giftitemCount.size(); i++) {
//                        selfList.add(new Promo(giftitemCount.get(i).getSerial(),
//                                        giftitemCount.get(i).getMpocode(),
//                                        giftitemCount.get(i).getMonth(), giftitemCount.get(i).getPacksize(),
//                                        giftitemCount.get(i).getSamplename(), giftitemCount.get(i).getType(),
//                                        giftitemCount.get(i).getWeek1(), giftitemCount.get(i).getWeek2(),
//                                        giftitemCount.get(i).getWeek3(), giftitemCount.get(i).getWeek4(),
//                                        giftitemCount.get(i).getTotal(),
//                                        giftitemCount.get(i).getAci(), giftitemCount.get(i).getAristo(),
//                                        giftitemCount.get(i).getPopular(), giftitemCount.get(i).getRadient(),
//                                        giftitemCount.get(i).getOsl(), giftitemCount.get(i).getBase(),
//                                        giftitemCount.get(i).getOplBase(), giftitemCount.get(i).getOplShare()
//                                )
//                        );
//                    }
                    promoAdapter0.notifyDataSetChanged();
                    pDialogSelf.dismiss();
                } else {
                    pDialogSelf.dismiss();
                    Toast.makeText(DoctorReachActivity.this, "No data Available !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorReachSelfModel> call, Throwable t) {
                pDialogSelf.dismiss();
            }
        });
    }

    public void prepareSMdata() {
        pDialog = new ProgressDialog(DoctorReachActivity.this);
        pDialog.setMessage("Division Data Loading...");
        pDialog.setTitle("Please wait");
        pDialog.show();
        user_flag = "SM";

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DoctorReachSelfModel> call = apiInterface.doc_reach_details_followup(user_code, month_name);
        promoList.clear();

        call.enqueue(new Callback<DoctorReachSelfModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<DoctorReachSelfModel> call, retrofit2.Response<DoctorReachSelfModel> response) {
                List<DoctorReachSelfList> giftitemCount = response.body().getDoctorSelfLists();

                if (response.isSuccessful()) {
                    promoList.addAll(giftitemCount);
//                    for (int i = 0; i < giftitemCount.size(); i++) {
//                        promoList.add(new Promo(giftitemCount.get(i).getSerial(),
//                                        giftitemCount.get(i).getMpocode(),
//                                        giftitemCount.get(i).getMonth(), giftitemCount.get(i).getPacksize(),
//                                        giftitemCount.get(i).getSamplename(), giftitemCount.get(i).getType(),
//                                        giftitemCount.get(i).getWeek1(), giftitemCount.get(i).getWeek2(),
//                                        giftitemCount.get(i).getWeek3(), giftitemCount.get(i).getWeek4(),
//                                        giftitemCount.get(i).getTotal(),
//                                        giftitemCount.get(i).getAci(), giftitemCount.get(i).getAristo(),
//                                        giftitemCount.get(i).getPopular(), giftitemCount.get(i).getRadient(),
//                                        giftitemCount.get(i).getOsl(), giftitemCount.get(i).getBase(),
//                                        giftitemCount.get(i).getOplBase(), giftitemCount.get(i).getOplShare()
//                                )
//                        );
//                    }
                    promoAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(DoctorReachActivity.this, "No data Available !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorReachSelfModel> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    public void setUpRecyclerView() {
        promoAdapter = new DoctorReachAdapter(DoctorReachActivity.this, promoList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvNational.setLayoutManager(manager);
        rvNational.setAdapter(promoAdapter);
        rvNational.addItemDecoration(new DividerItemDecoration(DoctorReachActivity.this, DividerItemDecoration.VERTICAL));
    }

    public void prepareASMdata() {
        pDialog2 = new ProgressDialog(DoctorReachActivity.this);
        pDialog2.setMessage("Zone Data Loading...");
        pDialog2.setTitle("Please wait");
        pDialog2.show();
        user_flag = "ASM";

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DoctorReachSelfModel> call = apiInterface.doc_reach_details_followup(user_code, month_name);
        divisionList.clear();

        call.enqueue(new Callback<DoctorReachSelfModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<DoctorReachSelfModel> call, retrofit2.Response<DoctorReachSelfModel> response) {
                List<DoctorReachSelfList> giftitemCount = Objects.requireNonNull(response.body()).getDoctorSelfLists();

                if (response.isSuccessful()) {
                    divisionList.addAll(giftitemCount);
                    promoAdapter2.notifyDataSetChanged();
                    pDialog2.dismiss();
                } else {
                    pDialog2.dismiss();
                    Toast.makeText(DoctorReachActivity.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorReachSelfModel> call, Throwable t) {
                pDialog2.dismiss();
            }
        });
    }

    public void setUpRV_Division() {
        promoAdapter2 = new DoctorReachAdapter(DoctorReachActivity.this, divisionList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvDivision.setLayoutManager(manager);
        rvDivision.setAdapter(promoAdapter2);
        rvDivision.addItemDecoration(new DividerItemDecoration(DoctorReachActivity.this, DividerItemDecoration.VERTICAL));
    }

    public void prepareRegiondata() {
        pDialog3 = new ProgressDialog(DoctorReachActivity.this);
        pDialog3.setMessage("Region Data Loading...");
        pDialog3.setTitle("Please wait");
        pDialog3.show();
        user_flag = "RM";

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DoctorReachSelfModel> call = apiInterface.doc_reach_details_followup(user_code, month_name);
        regionList.clear();

        call.enqueue(new Callback<DoctorReachSelfModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<DoctorReachSelfModel> call, retrofit2.Response<DoctorReachSelfModel> response) {
                List<DoctorReachSelfList> giftitemCount = Objects.requireNonNull(response.body()).getDoctorSelfLists();

                if (response.isSuccessful()) {
                    regionList.addAll(giftitemCount);
                    promoAdapter3.notifyDataSetChanged();
                    pDialog3.dismiss();
                } else {
                    pDialog3.dismiss();
                    Toast.makeText(DoctorReachActivity.this, "No data Available !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorReachSelfModel> call, Throwable t) {
                pDialog3.dismiss();
            }
        });
    }

    public void setUpRV_Region() {
        promoAdapter3 = new DoctorReachAdapter(DoctorReachActivity.this, regionList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvRegion.setLayoutManager(manager);
        rvRegion.setAdapter(promoAdapter3);
        rvRegion.addItemDecoration(new DividerItemDecoration(DoctorReachActivity.this, DividerItemDecoration.VERTICAL));
    }

    public void prepareAreadata() {
        pDialog4 = new ProgressDialog(DoctorReachActivity.this);
        pDialog4.setMessage("Area Data Loading...");
        pDialog4.setTitle("Please wait");
        pDialog4.show();
        user_flag = "AM";

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DoctorReachSelfModel> call = apiInterface.doc_reach_details_followup(user_code, month_name);
        areaList.clear();

        call.enqueue(new Callback<DoctorReachSelfModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<DoctorReachSelfModel> call, @NonNull retrofit2.Response<DoctorReachSelfModel> response) {
                List<DoctorReachSelfList> giftitemCount = Objects.requireNonNull(response.body()).getDoctorSelfLists();
                Log.e("printFMDATA-->", String.valueOf(giftitemCount.size()));

                if (response.isSuccessful()) {
                    if (giftitemCount.size() > 0) {
                        areaList.addAll(giftitemCount);
                        promoAdapter4.notifyDataSetChanged();
                        pDialog4.dismiss();
                    } else {
                        Log.e("No Data", "No data Available !");
                    }
                } else {
                    pDialog4.dismiss();
                    Toast.makeText(DoctorReachActivity.this, "No data Available !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorReachSelfModel> call, Throwable t) {
                pDialog4.dismiss();
            }
        });
    }

    public void setUpRV_Area() {
        promoAdapter4 = new DoctorReachAdapter(DoctorReachActivity.this, areaList);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvArea.setLayoutManager(manager);
        rvArea.setAdapter(promoAdapter4);
        rvArea.addItemDecoration(new DividerItemDecoration(DoctorReachActivity.this, DividerItemDecoration.VERTICAL));
    }
}