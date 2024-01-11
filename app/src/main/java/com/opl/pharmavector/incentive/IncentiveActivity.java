package com.opl.pharmavector.incentive;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.achieve.AchieveEarnAdapter;
import com.opl.pharmavector.achieve.AchieveEarnModel;
import com.opl.pharmavector.achieve.AchieveEarningList;
import com.opl.pharmavector.achieve.AchieveMonthList;
import com.opl.pharmavector.achieve.AchvMonthModel;
import com.opl.pharmavector.pmdVector.model.FFTeamList;
import com.opl.pharmavector.pmdVector.model.FFTeamModel;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncentiveActivity extends Activity implements View.OnClickListener, AchieveEarnAdapter.ContactCallback {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public ProgressDialog pDialog;
//    public String json, team_type = "XX", team_name = "All", deignation_type = "XX", deignation_name = "All", place_type = "XX",
//            place_name = "All", actv_rm_code_split, ff_name, ff_code = "XX", month_name = "", userName, userCode, userRole, teamCode;
    public String json, quarter_type = "XX", incentive_name = "All", deignation_type = "XX", deignation_name = "All", place_type = "XX",
        place_name = "All", actv_rm_code_split, ff_name, ff_code = "XX", incentive_type = "", team_code, userName, userCode, userRole, teamCode,
        designation_code;
    Button back_btn, submitBtn, submitBtn1;
    public android.widget.Spinner spin_rm;
    AutoCompleteTextView autoCompleteTextView1, autoCompleteTextView2;
    public ArrayList<Customer> customerlist;
    public ArrayList<Customer> departmentlist;
    public ArrayList<com.opl.pharmavector.Category> categoriesList;
    TextView lbl_place_name;
    private RecyclerView recyclerAchieve;
    public RecyclerView.LayoutManager layoutManager;
    public ArrayList<AchieveEarningList> achieveEarnList;
    public List<FFTeamList> recyclerTeamList;
    private AchieveEarnAdapter achieveEarnAdapter;
    ApiInterface apiInterface;
    ProgressBar progressBar;
    ArrayList<String> yearLists;
    LinearLayout layoutMpo, gmLayout;
    //MaterialSpinner teamSpinner, divisionSpinner, desigSpinner;
    MaterialSpinner incentiveSpinner, quarterSpinner, yearSpinner, teamSpinner, designationSpinner;
    private String selected_number, selected_person, profile_image;
    public String pmdImageUrl = ApiClient.BASE_URL + "vector_ff_image/sales/";
    private final String url_getfieldforce = BASE_URL + "achv_and_earn/get_ff_list.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incentive);

        initViews();
        autoCompleteEvents();
        //initPlaceSpinner();
        initDesignationSpinner();
        //getAchieveFFTeamList();
        //getAchievementMonth();
        getLastTwoYearsList();
        getIncentiveTypeList();
        getIncentiveQtrList();
        getIncentiveTeamList();
        getIncentiveDesignation();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerAchieve.setAdapter(null);
                if ((Objects.equals(userRole, "FM") || Objects.equals(userRole, "RM") || Objects.equals(userRole, "ASM") || Objects.equals(userRole, "SM") || Objects.equals(userRole, "AD") || Objects.equals(userRole, "PMD")) && Objects.equals(deignation_type, "SELF")) {
                    getAchieveEarnSelfList();
                } else {
                    getAchievementEarnList();
                }
            }
        });
        submitBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerAchieve.setAdapter(null);
                getAchieveEarnSelfList();
            }
        });
    }

    private void getLastTwoYearsList() {
        yearLists = new ArrayList<>();
        int currentYear = Year.now().getValue();
        yearLists.add(String.valueOf(currentYear));
        Year previousYear = Year.now().minusYears(1);
        yearLists.add(String.valueOf(previousYear));

        initAchieveYearSpinner();
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        back_btn = findViewById(R.id.backbt);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setTypeface(fontFamily);
        submitBtn1 = findViewById(R.id.submitBtn1);
        customerlist = new ArrayList<Customer>();
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);
        categoriesList = new ArrayList<com.opl.pharmavector.Category>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerAchieve = findViewById(R.id.recyclerAchieveEarn);
        layoutManager = new LinearLayoutManager(this);
        recyclerAchieve.setLayoutManager(layoutManager);
        achieveEarnList = new ArrayList<>();
        gmLayout = findViewById(R.id.gmLayout);
        layoutMpo = findViewById(R.id.layoutMpo);
        teamSpinner = findViewById(R.id.teamSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        quarterSpinner = findViewById(R.id.quarterSpinner);
        incentiveSpinner = findViewById(R.id.incentiveSpinner);
        designationSpinner = findViewById(R.id.designationSpinner);
        //desigSpinner = findViewById(R.id.desigSpinner);
        //divisionSpinner = findViewById(R.id.divisionSpinner);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        userName = bundle.getString("UserName");
        userCode = bundle.getString("UserCode");
        userRole = bundle.getString("UserRole");
        teamCode = bundle.getString("TeamCode");

        if (Objects.equals(userRole, "MPO")) {
            gmLayout.setVisibility(View.GONE);
            layoutMpo.setVisibility(View.VISIBLE);
            teamSpinner.setVisibility(View.GONE);
            //divisionSpinner.setVisibility(View.GONE);
            //desigSpinner.setVisibility(View.GONE);
            autoCompleteTextView2.setVisibility(View.GONE);
        } else if (Objects.equals(userRole, "FM")) {
            //divisionSpinner.setVisibility(View.GONE);
            autoCompleteTextView2.setVisibility(View.GONE);
        }
    }

    private void getAchievementEarnList() {
        String ff_type = "";
        ProgressDialog ppDialog = new ProgressDialog(IncentiveActivity.this);
        ppDialog.setMessage("Loading Achieve Data ...");
        ppDialog.setCancelable(true);
        ppDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        if (Objects.equals(userRole, "FM")) {
            //ff_type = "XX";
            ff_type = userCode;
        } else {
            ff_type = autoCompleteTextView2.getText().toString().trim();
        }
        //Call<AchieveEarnModel> call = apiInterface.getAchievementEarnList(deignation_type, ff_type, team_type, month_name);
        Call<AchieveEarnModel> call = apiInterface.getAchievementEarnList(deignation_type, ff_type, quarter_type, incentive_type);
        Log.d("ff_type", ff_type);

        call.enqueue(new Callback<AchieveEarnModel>() {
            @Override
            public void onResponse(Call<AchieveEarnModel> call, Response<AchieveEarnModel> response) {
                List<AchieveEarningList> achieveLists = null;

                if (response.isSuccessful()) {
                    ppDialog.dismiss();
                    if (response.body() != null) {
                        achieveLists = (response.body()).getAchieveEarnList();
                    }
                    achieveEarnAdapter = new AchieveEarnAdapter(IncentiveActivity.this, (ArrayList<AchieveEarningList>) achieveLists, userRole, deignation_type, IncentiveActivity.this);
                    LinearLayoutManager manager = new LinearLayoutManager(IncentiveActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerAchieve.setLayoutManager(manager);
                    recyclerAchieve.setAdapter(achieveEarnAdapter);
                }
            }

            @Override
            public void onFailure(Call<AchieveEarnModel> call, Throwable t) {
                ppDialog.dismiss();
                Log.e("Data load problem--->", "Failed to Retried Data For-- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getAchieveEarnSelfList() {
        ProgressDialog ppDialog = new ProgressDialog(IncentiveActivity.this);
        ppDialog.setMessage("Loading Achieve Data ...");
        ppDialog.setCancelable(true);
        ppDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<AchieveEarnModel> call;
        if (Objects.equals(userRole, "AD") || Objects.equals(userRole, "PMD")) {
            call = apiInterface.getADAchieveEarnSelfList(userCode, incentive_type, quarter_type);
            //call = apiInterface.getADAchieveEarnSelfList(userCode, month_name, team_type);
        } else {
           //call = apiInterface.getAchieveEarnSelfList(userCode, month_name);
           call = apiInterface.getAchieveEarnSelfList(userCode, incentive_type);
        }
        Log.d("ff_type", incentive_type);

        call.enqueue(new Callback<AchieveEarnModel>() {
            @Override
            public void onResponse(Call<AchieveEarnModel> call, Response<AchieveEarnModel> response) {
                List<AchieveEarningList> achieveLists = null;
                if (response.isSuccessful()) {
                    ppDialog.dismiss();
                    if (response.body() != null) {
                        achieveLists = (response.body()).getAchieveEarnList();
                    }
                    achieveEarnAdapter = new AchieveEarnAdapter(IncentiveActivity.this, (ArrayList<AchieveEarningList>) achieveLists, userRole, deignation_type, IncentiveActivity.this);
                    LinearLayoutManager manager = new LinearLayoutManager(IncentiveActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerAchieve.setLayoutManager(manager);
                    recyclerAchieve.setAdapter(achieveEarnAdapter);
                }
            }

            @Override
            public void onFailure(Call<AchieveEarnModel> call, Throwable t) {
                ppDialog.dismiss();
                Log.d("Data load problem--->", "Failed to Retried Data For-- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    //private void getAchieveFFTeamList() {
    private void getIncentiveQtrList() {
        ProgressDialog pDialog = new ProgressDialog(IncentiveActivity.this);
        pDialog.setMessage("Loading Data ...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Call<FFTeamModel> call;
        Call<IncentiveQtrModel> call;

        if (Objects.equals(userRole, "PMD")) {
            //call = apiInterface.getPmdAchieveTeamList(userCode);
            call = apiInterface.getIncentiveQuarterList();
        } else {
            //call = apiInterface.getAchieveFFTeamList();
            call = apiInterface.getIncentiveQuarterList();
        }

        //call.enqueue(new Callback<FFTeamModel>() {
        call.enqueue(new Callback<IncentiveQtrModel>() {
            @Override
            //public void onResponse(Call<FFTeamModel> call, Response<FFTeamModel> response) {
            public void onResponse(Call<IncentiveQtrModel> call, Response<IncentiveQtrModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    //List<FFTeamList> teamList = null;
                    List<IncentiveQtrList> incentiveList = null;

                    if (response.body() != null) {
                        //teamList = (response.body()).getTeamList();
                        incentiveList = (response.body()).getIncentiveQtrLists();
                    }
                    initIncentiveQtrSpinner(Objects.requireNonNull(incentiveList));
                    Log.d("Team List -- : ", String.valueOf(incentiveList));
                }
            }

            @Override
            public void onFailure(Call<IncentiveQtrModel> call, Throwable t) {
                pDialog.dismiss();
                Log.d("Data load problem--->", "Failed to Retried Data For-- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getIncentiveTeamList() {
        ProgressDialog pDialog = new ProgressDialog(IncentiveActivity.this);
        pDialog.setMessage("Loading Data ...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<IncentiveTeamModel> call = apiInterface.getIncentiveTeamList(userCode);

        call.enqueue(new Callback<IncentiveTeamModel>() {
            @Override
            public void onResponse(Call<IncentiveTeamModel> call, Response<IncentiveTeamModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<IncentiveTeamList> incentiveList = null;

                    if (response.body() != null) {
                        incentiveList = (response.body()).getIncentiveTeamLists();
                    }
                    initIncentiveTeamSpinner(Objects.requireNonNull(incentiveList));
                    Log.d("Team List -- : ", String.valueOf(incentiveList));
                }
            }

            @Override
            public void onFailure(Call<IncentiveTeamModel> call, Throwable t) {
                pDialog.dismiss();
                Log.d("Data load problem --->", "Failed to Retried Data For -- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getIncentiveDesignation() {
        ProgressDialog pDialog = new ProgressDialog(IncentiveActivity.this);
        pDialog.setMessage("Loading Data ...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<IncentiveDsgModel> call = apiInterface.getIncentiveDesignation(userCode);

        call.enqueue(new Callback<IncentiveDsgModel>() {
            @Override
            public void onResponse(Call<IncentiveDsgModel> call, Response<IncentiveDsgModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<IncentiveDsgList> designationList = null;

                    if (response.body() != null) {
                        designationList = (response.body()).getIncentiveDsgLists();
                    }
                    initIncentiveDesignation(Objects.requireNonNull(designationList));
                    Log.d("Designation List -- : ", String.valueOf(designationList));
                }
            }

            @Override
            public void onFailure(Call<IncentiveDsgModel> call, Throwable t) {
                pDialog.dismiss();
                Log.d("Data load problem --->", "Failed to Retried Data For -- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    //private void getAchievementMonth() {
    private void getIncentiveTypeList() {
        ProgressDialog pDialog = new ProgressDialog(IncentiveActivity.this);
        pDialog.setMessage("Loading Incentive Type...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Call<AchvMonthModel> call = apiInterface.getAchievementMonths();
        //Call<AchvMonthModel> call = apiInterface.getAchievementIncentive();
        Call<IncentiveModel> call = apiInterface.getAchievementIncentive();

        //call.enqueue(new Callback<AchvMonthModel>() {
        call.enqueue(new Callback<IncentiveModel>() {
            @Override
            //public void onResponse(Call<AchvMonthModel> call, Response<AchvMonthModel> response) {
            public void onResponse(Call<IncentiveModel> call, Response<IncentiveModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    //List<AchieveMonthList> incentiveList = null;
                    List<IncentiveList> incentiveList = null;

                    if (response.body() != null) {
                        incentiveList = (response.body()).getIncentiveType();
                    }
                    initIncentiveSpinner(Objects.requireNonNull(incentiveList));
//                    if (Objects.equals(userRole, "AD") || Objects.equals(userRole, "FM") || Objects.equals(userRole, "RM") || Objects.equals(userRole, "ASM") || Objects.equals(userRole, "SM") || Objects.equals(userRole, "PMD")) {
//                        //initMonthSpinner(Objects.requireNonNull(incentiveList));
//                        initIncentiveSpinner(Objects.requireNonNull(incentiveList));
//                    } else {
//                        initIncentiveSpinner(Objects.requireNonNull(incentiveList));
//                        //initMonthSpinner(Objects.requireNonNull(incentiveList));
//                        //initMpoMonthSpinner(Objects.requireNonNull(incentiveList));
//                    }
                    Log.d("Month List -- : ", String.valueOf(incentiveList));
                }
            }

            @Override
            public void onFailure(Call<IncentiveModel> call, Throwable t) {
                pDialog.dismiss();
                Log.d("Data load problem--->", "Failed to Retried Data For-- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    //private void initTeamSpinner(List<FFTeamList> teamList) {
    private void initIncentiveQtrSpinner(List<IncentiveQtrList> incentiveLists) {
        ArrayList<String> quarterNameList = new ArrayList<>();

        if (incentiveLists.size() > 0) {
            for (IncentiveQtrList quarterName : incentiveLists) {
                quarterNameList.add(quarterName.getQtrDesc());
            }
        }
//        ArrayList<String> teamNameList = new ArrayList<>();
//        if (teamList.size() > 0) {
//            for (FFTeamList teamName : teamList) {
//                teamNameList.add(teamName.getTeamName());
//            }
//        }
        //teamSpinner.setItems(teamNameList);
        //teamSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
        quarterSpinner.setItems(quarterNameList);
        quarterSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                String incentive_name = String.valueOf(item).trim();
                //team_name = String.valueOf(item).trim();

//                for (int i = 0; i < teamList.size(); i++) {
//                    if (teamList.get(i).getTeamName().contains(team_name)) {
//                        team_type = teamList.get(i).getTeamCode();
//                    }
//                }
                for (int i = 0; i < incentiveLists.size(); i++) {
                    if (incentiveLists.get(i).getQtrDesc().contains(incentive_name)) {
                        quarter_type = incentiveLists.get(i).getQtrCode();
                    }
                }
                Log.d("team code", quarter_type);
            }
        });
    }

    private void initIncentiveTeamSpinner(List<IncentiveTeamList> incentiveLists) {
        ArrayList<String> teamNameList = new ArrayList<>();

        if (incentiveLists.size() > 0) {
            for (IncentiveTeamList quarterName : incentiveLists) {
                teamNameList.add(quarterName.getTeamName());
            }
        }
        teamSpinner.setItems(teamNameList);
        teamSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                String team_name = String.valueOf(item).trim();

                for (int i = 0; i < incentiveLists.size(); i++) {
                    if (incentiveLists.get(i).getTeamName().contains(incentive_name)) {
                        team_code = incentiveLists.get(i).getTeamCode();
                    }
                }
                //Log.d("team code", team_code);
            }
        });
    }

    private void initIncentiveDesignation(List<IncentiveDsgList> designationLists) {
        ArrayList<String> designationList = new ArrayList<>();

        if (designationLists.size() > 0) {
            for (IncentiveDsgList quarterName : designationLists) {
                designationList.add(quarterName.getTitleDesc());
            }
        }
        designationSpinner.setItems(designationList);
        designationSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                String designation_name = String.valueOf(item).trim();

                for (int i = 0; i < designationLists.size(); i++) {
                    if (designationLists.get(i).getTitleDesc().contains(designation_name)) {
                        designation_code = designationLists.get(i).getTitleCode();
                    }
                }
                Log.d("designation code", designation_code);
            }
        });
    }

    //private void initMonthSpinner(List<AchieveMonthList> monthList) {
    private void initIncentiveSpinner(List<IncentiveList> incentiveLists) {
        //MaterialSpinner monthSpinner = findViewById(R.id.monthSpinner);
        incentiveSpinner = findViewById(R.id.incentiveSpinner);
        ArrayList<String> incentiveList = new ArrayList<>();
        //ArrayList<String> monthNameList = new ArrayList<>();

        if (incentiveLists.size() > 0) {
            for (IncentiveList incentiveType : incentiveLists) {
                incentiveList.add(incentiveType.getIncentiveDesc());
            }
        }
//        if (monthList.size() > 0) {
//            for (AchieveMonthList monthName : monthList) {
//                monthNameList.add(monthName.getMnyrDesc());
//            }
//        }
        incentiveSpinner.setItems(incentiveList);
        incentiveSpinner.setText(incentiveList.get(0).toString());
        //monthSpinner.setItems(monthNameList);

        //monthSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
        incentiveSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                team_name = String.valueOf(item).trim();
//                for (int i = 0; i < monthList.size(); i++) {
//                    if (monthList.get(i).getMnyr().contains(team_name)) {
//                        month_name = monthList.get(i).getMnyr();
//                    }
//                }
                incentive_name = String.valueOf(item).trim();

                for (int i = 0; i < incentiveLists.size(); i++) {
                    if (incentiveLists.get(i).getIncentiveDesc().contains(incentive_name)) {
                        incentive_type = incentiveLists.get(i).getIncentiveType();
                    }
                }
                Log.d("month name", incentive_name);
            }
        });
    }
//    private void initMpoMonthSpinner(List<AchieveMonthList> monthList) {
//        MaterialSpinner mpoSpinner = findViewById(R.id.mpoMonthSpinner);
//        ArrayList<String> monthNameList = new ArrayList<>();
//
//        if (monthList.size() > 0) {
//            for (AchieveMonthList monthName : monthList) {
//                monthNameList.add(monthName.getMnyrDesc());
//            }
//        }
//        mpoSpinner.setItems(monthNameList);
//
//        mpoSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                team_name = String.valueOf(item).trim();
//
//                for (int i = 0; i < monthList.size(); i++) {
//                    if (monthList.get(i).getMnyr().contains(team_name)) {
//                        month_name = monthList.get(i).getMnyr();
//                    }
//                }
////                for (int i = 0; i < monthList.size(); i++) {
////                    if (monthList.get(i).getMnyr().contains(incentive_name)) {
////                        incentive_type = monthList.get(i).getMnyr();
////                    }
////                }
//                Log.d("month name", month_name);
//            }
//        });
//    }

    @SuppressLint("SetTextI18n")
    //private void initPlaceSpinner() {
    private void initAchieveYearSpinner() {
        yearSpinner.setItems(yearLists);
//        if (Objects.equals(userRole, "RM")) {
//            divisionSpinner.setItems("All", "Area", "Territory");
//        } else if (Objects.equals(userRole, "ASM")) {
//            divisionSpinner.setItems("All", "Region", "Area", "Territory");
//        } else if (Objects.equals(userRole, "SM")) {
//            divisionSpinner.setItems("All", "Zone", "Region", "Area", "Territory");
//        } else {
//            divisionSpinner.setItems("All", "Division", "Zone", "Region", "Area", "Territory");
//        }
//        divisionSpinner.setText("All");
//
//        if (Objects.equals(userRole, "AM") || Objects.equals(userRole, "RM") || Objects.equals(userRole, "ASM") || Objects.equals(userRole, "SM")) {
//            place_type = userCode;
//            autoCompleteTextView2.setText(place_type);
//        } else {
//            autoCompleteTextView2.setText(place_type);
//        }
//
//        divisionSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                place_name = String.valueOf(item);
//                if (place_name.trim().equals("All")) {
//                    if (Objects.equals(userRole, "AM") || Objects.equals(userRole, "RM") || Objects.equals(userRole, "ASM") || Objects.equals(userRole, "SM")) {
//                        place_type = userCode;
//                    } else {
//                        place_type = "XX";
//                    }
//                } else if (place_name.trim().equals("Division")) {
//                    place_type = "D";
//                } else if (place_name.trim().equals("Zone")) {
//                    place_type = "Z";
//                } else if (place_name.trim().equals("Region")) {
//                    place_type = "R";
//                } else if (place_name.trim().equals("Area")) {
//                    place_type = "A";
//                } else if (place_name.trim().equals("Territory")) {
//                    place_type = "T";
//                }
//
//                if (place_type.equals("XX")) {
//                    autoCompleteTextView2.setText("XX");
//                } else {
//                    if ((Objects.equals(userRole, "RM") || Objects.equals(userRole, "ASM") || Objects.equals(userRole, "SM")) && place_name.trim().equals("All")) {
//                        autoCompleteTextView2.setText(place_type);
//                    } else {
//                        autoCompleteTextView2.setText("");
//                        customerlist.clear();
//                        categoriesList.clear();
//                        new GetFieldForce().execute();
//                    }
//                }
//            }
//        });
    }

    private void initDesignationSpinner() {
//        if (Objects.equals(userRole, "FM")) {
//            desigSpinner.setItems("MPO", "SELF");
//        } else if (Objects.equals(userRole, "RM")) {
//            desigSpinner.setItems("MPO", "AM", "SELF");
//        } else if (Objects.equals(userRole, "ASM")) {
//            desigSpinner.setItems("MPO", "AM", "RM", "SELF");
//        } else if (Objects.equals(userRole, "SM")) {
//            desigSpinner.setItems("MPO", "AM", "RM", "ASM/DSM", "SELF");
//        } else {
//            desigSpinner.setItems("MPO", "AM", "RM", "ASM/DSM", "SM", "SELF");
//        }
//        desigSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                deignation_name = String.valueOf(item);
//                if (deignation_name.trim().equals("MPO")) {
//                    deignation_type = "MPO";
//                } else if (deignation_name.trim().equals("AM")) {
//                    deignation_type = "FM";
//                } else if (deignation_name.trim().equals("RM")) {
//                    deignation_type = "RM";
//                } else if (deignation_name.trim().equals("ASM/DSM")) {
//                    deignation_type = "AM";
//                } else if (deignation_name.trim().equals("DSM")) {
//                    deignation_type = "AM";
//                } else if (deignation_name.trim().equals("SM")) {
//                    deignation_type = "SM";
//                } else if (deignation_name.trim().equals("SELF")) {
//                    deignation_type = "SELF";
//                }
//            }
//        });
    }

    @Override
    public void onContactPhoneCall(AchieveEarningList achieveList) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + achieveList.getMobilePhone()));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Call failed, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onContactPhoneSms(AchieveEarningList achieveList) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("smsto:" + achieveList.getMobilePhone()));
            intent.putExtra("sms_body", "Dear Sir,");
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    class GetFieldForce extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("team_type", incentive_type));
            params.add(new BasicNameValuePair("place_type", place_type));

            if (Objects.equals(userRole, "AD")) {
                params.add(new BasicNameValuePair("ff_code", "XX"));
            } else {
                params.add(new BasicNameValuePair("ff_code", userCode));
            }
            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(url_getfieldforce, ServiceHandler.POST, params);
            customerlist.clear();

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
            populateFieldForceAuto();
        }
    }

    private void populateFieldForceAuto() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        String[] customer = lables.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        autoCompleteTextView2.setThreshold(2);
        autoCompleteTextView2.setAdapter(Adapter);
        autoCompleteTextView2.setTextColor(Color.BLUE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autoCompleteEvents() {
        autoCompleteTextView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCompleteTextView2.showDropDown();
                return false;
            }
        });
        autoCompleteTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        autoCompleteTextView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoCompleteTextView2.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                autoCompleteTextView2.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();

                    if (inputorder.contains("-")) {
                        actv_rm_code_split = inputorder.substring(inputorder.indexOf("-") + 1);
                        String[] first_split = inputorder.split("-");
                        ff_name = first_split[0].trim();
                        ff_code = first_split[1].trim();
                        autoCompleteTextView2.setText(ff_code);
                        KeyboardUtils.hideKeyboard(IncentiveActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {}
        });
    }

    public void finishActivity(View v) {
        finish();
    }

    public class ViewDialog {
        @SuppressLint("SetTextI18n")
        public void showDialog() {
            final Dialog dialog = new Dialog(IncentiveActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_4_contact);
            Button btn_call = dialog.findViewById(R.id.btn_call);
            Button btn_sms = dialog.findViewById(R.id.btn_sms);
            Button read_back = dialog.findViewById(R.id.read_back);
            TextView message = dialog.findViewById(R.id.message);
            TextView service = dialog.findViewById(R.id.service);
            ImageView imgPmdContact = dialog.findViewById(R.id.imgPmdContact);
            profile_image = pmdImageUrl + "PE03726" + "." + "jpg";
            Picasso.get().load(profile_image).into(imgPmdContact);
            TextView title = dialog.findViewById(R.id.title);
            title.setText("Call/SMS");
            message.setText("Name : \t" + selected_person);
            service.setText("Selected Number \t" + selected_number);
            btn_call.setText("Call");
            btn_sms.setText("SMS");
            read_back.setText("X");

            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + selected_number));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Call failed, please try again later!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
            btn_sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    try {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("smsto:" + selected_number));
                        intent.putExtra("sms_body", "Dear Sir,");
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
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
    }

    @Override
    public void onClick(View v) {}
}