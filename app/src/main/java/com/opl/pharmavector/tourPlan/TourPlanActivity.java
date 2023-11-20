package com.opl.pharmavector.tourPlan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourPlanActivity extends Activity {
    public Typeface fontFamily;
    public TextView tourRemarks;
    public MaterialSpinner tourNature, tourMode, tourClass, tourMorning, tourEvening;
    public String userName, userCode, tourNatureVal, tourModeVal, tourClassVal, tourMorningVal, tourEveningVal, tourMorningCode;
    public List<TourNatureList> tourNatureLists;
    public List<TourModeList> tourModeLists;
    public List<TourClassList> tourClassLists;
    public List<TourMorningList> tourMorningLists;
    public List<TourMorningList> tourEveningLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_plan);

        initViews();
        getDailyTourNatureList();
        getDailyTourModeList();
        getDailyTourClassList();
        getDailyTourMorningList();
    }

    private void initViews() {
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        tourNature = findViewById(R.id.tourNature);
        tourMode = findViewById(R.id.tourMode);
        tourClass= findViewById(R.id.tourClass);
        tourMorning = findViewById(R.id.tourMorning);
        tourEvening = findViewById(R.id.tourEvening);
        tourRemarks = findViewById(R.id.tourRemark);
        tourNatureLists = new ArrayList<>();
        tourModeLists = new ArrayList<>();
        tourClassLists = new ArrayList<>();
        tourMorningLists = new ArrayList<>();
        tourEveningLists = new ArrayList<>();

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userCode = b.getString("UserCode");
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

        for (int i = 0; i < tourMorningLists.size(); i++) {
            tourMorningList.add(tourMorningLists.get(i).getRegion());
        }
        tourMorning.setItems(tourMorningList);

        tourMorning.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                tourMorningVal = String.valueOf(item).trim();

                for (int i = 0; i < tourMorningList.size(); i++) {
                    if (tourMorningLists.get(i).getRegion().contains(tourMorningVal)) {
                        tourMorningCode = tourMorningLists.get(i).getRmCode();
                        getDailyTourEveningList(tourMorningCode);
                    }
                }
                Log.d("tourMorn1", tourMorningVal + "::" + tourMorningCode);
            }
        });
    }

    private void initTourEveningSpinner(List<TourMorningList> tourEveningLists) {
        List<String> tourEveningList = new ArrayList<String>();

        for (int i = 0; i < tourEveningLists.size(); i++) {
            tourEveningList.add(tourEveningLists.get(i).getRegion());
        }
        tourEvening.setItems(tourEveningList);

        tourEvening.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                tourEveningVal = String.valueOf(item).trim();
//                for (int i = 0; i < monthList.size(); i++) {
//                    if (monthList.get(i).getMnyrDesc().contains(team_name)) {
//                        month_name = monthList.get(i).getMnyr();
//                    }
//                }
                Log.d("tourEven1", tourEveningVal);
            }
        });
    }

    private void getDailyTourNatureList() {
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

    private void getDailyTourModeList() {
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

    private void getDailyTourClassList() {
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

    private void getDailyTourMorningList() {
        ProgressDialog pDialog = new ProgressDialog(TourPlanActivity.this);
        pDialog.setMessage("Loading Tour Location...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TourMorningModel> call = apiInterface.getDailyTourMorningList(userCode);
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

    private void getDailyTourEveningList(String tourMorningCode) {
        ProgressDialog pDialog = new ProgressDialog(TourPlanActivity.this);
        pDialog.setMessage("Loading Tour Location...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TourMorningModel> call = apiInterface.getDailyTourEveningList(userCode, tourMorningCode);
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