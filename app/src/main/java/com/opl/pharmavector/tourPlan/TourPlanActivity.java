package com.opl.pharmavector.tourPlan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.R;
import com.opl.pharmavector.achieve.AchieveEarnActivity;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourPlanActivity extends Activity {
    public Typeface fontFamily;
    public TextView tourRemarks;
    public AutoCompleteTextView tourMorning, tourEvening;
    public MaterialSpinner tourNature, tourMode, tourClass, tourMonth;
    public String userName, userCode, terriCode, tourNatureVal, tourModeVal, tourClassVal, tourMorningVal, tourEveningVal, tourMorningCode, tourEveningCode, tourMonthVal;
    public List<TourNatureList> tourNatureLists;
    public List<TourModeList> tourModeLists;
    public List<TourClassList> tourClassLists;
    public List<TourMonthList> tourMonthLists;
    public List<TourMorningList> tourMorningLists;
    public List<TourMorningList> tourEveningLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_plan);

        initViews();
        getTourMonthList();
        getTourNatureList();
        getTourModeList();
        getTourClassList();
        getTourMorningList();
        autoTourMorningEvent();
        autoTourEveningEvent();
    }

    private void initViews() {
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        tourNature = findViewById(R.id.tourNature);
        tourMode = findViewById(R.id.tourMode);
        tourClass = findViewById(R.id.tourClass);
        tourMonth = findViewById(R.id.tourMonth);
        tourMorning = findViewById(R.id.tourMorning);
        tourEvening = findViewById(R.id.tourEvening);
        tourRemarks = findViewById(R.id.tourRemark);
        tourMonthLists = new ArrayList<>();
        tourNatureLists = new ArrayList<>();
        tourModeLists = new ArrayList<>();
        tourClassLists = new ArrayList<>();
        tourMorningLists = new ArrayList<>();
        tourEveningLists = new ArrayList<>();

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userCode = b.getString("UserCode");
        terriCode = b.getString("TerriCode");
    }

    private void initTourNatureSpinner(List<TourNatureList> tourNatureLists) {
        List<String> tourNatureList = new ArrayList<String>();

        for (int i = 0; i < tourNatureLists.size(); i++) {
            tourNatureList.add(tourNatureLists.get(i).getTnDesc());
        }
        tourNature.setItems(tourNatureList);

        tourNature.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                tourNatureVal = String.valueOf(item).trim();
//                for (int i = 0; i < monthList.size(); i++) {
//                    if (monthList.get(i).getMnyrDesc().contains(team_name)) {
//                        month_name = monthList.get(i).getMnyr();
//                    }
//                }
                Log.d("tourNature1", tourNatureVal);
            }
        });
    }

    private void initTourModeSpinner(List<TourModeList> tourModeLists) {
        List<String> tourModeList = new ArrayList<String>();

        for (int i = 0; i < tourModeLists.size(); i++) {
            tourModeList.add(tourModeLists.get(i).getTmDesc());
        }
        tourMode.setItems(tourModeList);

        tourMode.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                tourModeVal = String.valueOf(item).trim();
//                for (int i = 0; i < monthList.size(); i++) {
//                    if (monthList.get(i).getMnyrDesc().contains(team_name)) {
//                        month_name = monthList.get(i).getMnyr();
//                    }
//                }
                Log.d("tourMode1", tourNatureVal);
            }
        });
    }

    private void initTourClassSpinner(List<TourClassList> tourClassLists) {
        List<String> tourClassList = new ArrayList<String>();

        for (int i = 0; i < tourClassLists.size(); i++) {
            tourClassList.add(tourClassLists.get(i).getTmcDesc());
        }
        tourClass.setItems(tourClassList);

        tourClass.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                tourClassVal = String.valueOf(item).trim();
//                for (int i = 0; i < monthList.size(); i++) {
//                    if (monthList.get(i).getMnyrDesc().contains(team_name)) {
//                        month_name = monthList.get(i).getMnyr();
//                    }
//                }
                Log.d("tourClass1", tourClassVal);
            }
        });
    }

    private void initTourMorningSpinner(List<TourMorningList> tourMorningLists) {
        List<String> tourMorningList = new ArrayList<String>();

//        for (int i = 0; i < tourMorningLists.size(); i++) {
//            tourMorningList.add(tourEveningLists.get(i).getMpoCode() + " - " + tourEveningLists.get(i).getTerriName());
//        }
//        tourMorning.setItems(tourMorningList);
//
//        tourMorning.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                String tempMorningVal = String.valueOf(item).trim();
//                tourMorningVal = tempMorningVal.split("-")[0];
//
//                for (int i = 0; i < tourMorningList.size(); i++) {
//                    if (tourMorningLists.get(i).getMpoCode().contains(tourMorningVal)) {
//                        tourMorningCode = tourMorningLists.get(i).getMpoCode();
//                        getTourEveningList(tourMorningCode);
//                    }
//                }
//                Log.d("tourMorn1", tourMorningVal + "::" + tourMorningCode);
//            }
//        });

        for (int i = 0; i < tourMorningLists.size(); i++) {
            tourMorningList.add(tourMorningLists.get(i).getMpoCode() + " - " + tourMorningLists.get(i).getTerriName());
        }
        String[] customer = tourMorningList.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        tourMorning.setThreshold(2);
        tourMorning.setAdapter(Adapter);
        tourMorning.setTextColor(Color.BLUE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autoTourMorningEvent() {
        tourMorning.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tourMorning.showDropDown();
                return false;
            }
        });
        tourMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        tourMorning.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tourMorning.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tourMorning.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    String tempMorningVal = s.toString().trim();
                    tourMorningVal = tempMorningVal.split("-")[0];

                    for (int i = 0; i < tourMorningLists.size(); i++) {
                        if (tourMorningLists.get(i).getMpoCode().contains(tourMorningVal)) {
                            tourMorningCode = tourMorningLists.get(i).getMpoCode();
                            getTourEveningList(tourMorningCode);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            private void length() {}
        });
    }

    private void initTourMonthSpinner(List<TourMonthList> tourMonthLists) {
        List<String> tourMonthList = new ArrayList<String>();

        for (int i = 0; i < tourMonthLists.size(); i++) {
            tourMonthList.add(tourMonthLists.get(i).getCalDay());
        }
        tourMonth.setItems(tourMonthList);

        tourMonth.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                tourMonthVal = String.valueOf(item).trim();
                //tourMonthVal = tempMorningVal.split("-")[0];

//                for (int i = 0; i < tourMorningList.size(); i++) {
//                    if (tourMorningLists.get(i).getMpoCode().contains(tourMorningVal)) {
//                        tourMorningCode = tourMorningLists.get(i).getMpoCode();
//                        getTourEveningList(tourMorningCode);
//                    }
//                }
                Log.d("tourMorn1", tourMorningVal + "::" + tourMorningCode);
            }
        });
    }

    private void initTourEveningSpinner(List<TourMorningList> tourEveningLists) {
        List<String> tourEveningList = new ArrayList<String>();

//        for (int i = 0; i < tourEveningLists.size(); i++) {
//            tourEveningList.add(tourEveningLists.get(i).getMpoCode() + " - " + tourEveningLists.get(i).getTerriName());
//        }
//        tourEvening.setItems(tourEveningList);
//
//        tourEvening.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                tourEveningVal = String.valueOf(item).trim();
////                for (int i = 0; i < monthList.size(); i++) {
////                    if (monthList.get(i).getMnyrDesc().contains(team_name)) {
////                        month_name = monthList.get(i).getMnyr();
////                    }
////                }
//                Log.d("tourEven1", tourEveningVal);
//            }
//        });

        for (int i = 0; i < tourEveningLists.size(); i++) {
            tourEveningList.add(tourEveningLists.get(i).getMpoCode() + " - " + tourEveningLists.get(i).getTerriName());
        }
        String[] customer = tourEveningList.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        tourEvening.setThreshold(2);
        tourEvening.setAdapter(Adapter);
        tourEvening.setTextColor(Color.BLUE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autoTourEveningEvent() {
        tourEvening.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tourEvening.showDropDown();
                return false;
            }
        });
        tourEvening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        tourEvening.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tourEvening.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tourEvening.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    String tempEveningVal = s.toString().trim();
                    tourEveningVal = tempEveningVal.split("-")[0];

                    for (int i = 0; i < tourEveningLists.size(); i++) {
                        if (tourEveningLists.get(i).getMpoCode().contains(tourEveningVal)) {
                            tourEveningCode = tourEveningLists.get(i).getMpoCode();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            private void length() {}
        });
    }

    private void getTourNatureList() {
        ProgressDialog pDialog = new ProgressDialog(TourPlanActivity.this);
        pDialog.setMessage("Loading Tour Nature...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TourNatureModel> call = apiInterface.getDailyTourNatureList(userCode);
        tourNatureLists.clear();

        call.enqueue(new Callback<TourNatureModel>() {
            @Override
            public void onResponse(Call<TourNatureModel> call, Response<TourNatureModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    if (response.body() != null) {
                        tourNatureLists.addAll((response.body()).getTourNatureLists());
                    }
                    initTourNatureSpinner(tourNatureLists);
                    Log.d("tourNature: ", String.valueOf(tourNatureLists));
                }
            }

            @Override
            public void onFailure(Call<TourNatureModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getTourModeList() {
        ProgressDialog pDialog = new ProgressDialog(TourPlanActivity.this);
        pDialog.setMessage("Loading Tour Mode...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TourModeModel> call = apiInterface.getDailyTourModeList(userCode);
        tourModeLists.clear();

        call.enqueue(new Callback<TourModeModel>() {
            @Override
            public void onResponse(Call<TourModeModel> call, Response<TourModeModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    if (response.body() != null) {
                        tourModeLists.addAll((response.body()).getTourModeLists());
                    }
                    initTourModeSpinner(tourModeLists);
                    Log.d("tourMode: ", String.valueOf(tourModeLists));
                }
            }

            @Override
            public void onFailure(Call<TourModeModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getTourClassList() {
        ProgressDialog pDialog = new ProgressDialog(TourPlanActivity.this);
        pDialog.setMessage("Loading Tour Class...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TourClassModel> call = apiInterface.getDailyTourClassList(userCode);
        tourClassLists.clear();

        call.enqueue(new Callback<TourClassModel>() {
            @Override
            public void onResponse(Call<TourClassModel> call, Response<TourClassModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    if (response.body() != null) {
                        tourClassLists.addAll((response.body()).getTourClassLists());
                    }
                    initTourClassSpinner(tourClassLists);
                    Log.d("tourClass: ", String.valueOf(tourClassLists));
                }
            }

            @Override
            public void onFailure(Call<TourClassModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getTourMorningList() {
        ProgressDialog pDialog = new ProgressDialog(TourPlanActivity.this);
        pDialog.setMessage("Loading Tour Location...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TourMorningModel> call = apiInterface.getDailyTourMorningList(terriCode);
        tourMorningLists.clear();

        call.enqueue(new Callback<TourMorningModel>() {
            @Override
            public void onResponse(Call<TourMorningModel> call, Response<TourMorningModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    if (response.body() != null) {
                        tourMorningLists.addAll((response.body()).getTourMorningLists());
                    }
                    initTourMorningSpinner(tourMorningLists);
                    Log.d("tourMorn: ", String.valueOf(tourMorningLists));
                }
            }

            @Override
            public void onFailure(Call<TourMorningModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getTourMonthList() {
        ProgressDialog pDialog = new ProgressDialog(TourPlanActivity.this);
        pDialog.setMessage("Loading Tour Month...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TourMonthModel> call = apiInterface.getDailyTourMonthList(terriCode);
        tourMonthLists.clear();

        call.enqueue(new Callback<TourMonthModel>() {
            @Override
            public void onResponse(Call<TourMonthModel> call, Response<TourMonthModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    if (response.body() != null) {
                        tourMonthLists.addAll((response.body()).getTourMonthLists());
                    }
                    initTourMonthSpinner(tourMonthLists);
                    Log.d("tourMorn: ", String.valueOf(tourMorningLists));
                }
            }

            @Override
            public void onFailure(Call<TourMonthModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getTourEveningList(String tourMorningCode) {
        ProgressDialog pDialog = new ProgressDialog(TourPlanActivity.this);
        pDialog.setMessage("Loading Tour Location...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TourMorningModel> call = apiInterface.getDailyTourEveningList(terriCode, tourMorningCode);
        tourEveningLists.clear();

        call.enqueue(new Callback<TourMorningModel>() {
            @Override
            public void onResponse(Call<TourMorningModel> call, Response<TourMorningModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    if (response.body() != null) {
                        tourEveningLists.addAll((response.body()).getTourMorningLists());
                    }
                    initTourEveningSpinner(tourEveningLists);
                    Log.d("tourEven: ", String.valueOf(tourEveningLists));
                }
            }

            @Override
            public void onFailure(Call<TourMorningModel> call, Throwable t) {
                pDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}