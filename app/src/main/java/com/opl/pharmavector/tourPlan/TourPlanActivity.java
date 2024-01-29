package com.opl.pharmavector.tourPlan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourPlanActivity extends Activity {
    public Button submitPlan;
    public Typeface fontFamily;
    public TextView tourRemarks, startTimeHour, startTimeMin, startTimeSec, endTimeHour, endTimeMin, endTimeSec, tourObjective, successMessage, startTimeAm, endTimeAM;
    public AutoCompleteTextView tourMorning, tourEvening;
    public MaterialSpinner tourNature, tourMode, tourClass, tourMonth;
    public String userName, userCode, terriCode, tourNatureVal, tourModeVal, tourClassVal, tourMorningVal, tourEveningVal, tourMorningCode, tourEveningCode, tourMonthVal,
    tourObjectVal, tourRemarkVal, tourNatureCode = "00001", tourModeCode = "00007", tourClassCode = "00006", fromTimeHour = "09", fromTimeMin = "00", fromTimeAm = "AM",
            toTimeHour = "10", toTimeMin = "00", toTimeAm = "PM";
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
        getTourEveningList("");
        autoTourMorningEvent();
        autoTourEveningEvent();

        startTimeHour.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            int amPm = mCurrentTime.get(Calendar.AM_PM);
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(TourPlanActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                timeCheckerAmPm(selectedHour, selectedMinute);
                startTimeHour.setText(String.valueOf(fromTimeHour));
                startTimeMin.setText(String.valueOf(fromTimeMin));
                startTimeAm.setText(fromTimeAm);
                //fromTimeAm = (mCurrentTime.get(Calendar.AM_PM) == Calendar.AM) ? "am" : "pm";
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        startTimeMin.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(TourPlanActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                timeCheckerAmPm(selectedHour, selectedMinute);
                startTimeHour.setText(String.valueOf(fromTimeHour));
                startTimeMin.setText(String.valueOf(fromTimeMin));
                startTimeAm.setText(fromTimeAm);
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        startTimeSec.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(TourPlanActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                timeCheckerAmPm(selectedHour, selectedMinute);
                startTimeHour.setText(String.valueOf(fromTimeHour));
                startTimeMin.setText(String.valueOf(fromTimeMin));
                startTimeAm.setText(fromTimeAm);
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        endTimeHour.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(TourPlanActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                timeCheckerAmPm(selectedHour, selectedMinute);
                endTimeHour.setText(String.valueOf(toTimeHour));
                endTimeMin.setText(String.valueOf(toTimeMin));
                endTimeAM.setText(toTimeAm);
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        endTimeMin.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(TourPlanActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                timeCheckerAmPm(selectedHour, selectedMinute);
                endTimeHour.setText(String.valueOf(toTimeHour));
                endTimeMin.setText(String.valueOf(toTimeMin));
                endTimeAM.setText(toTimeAm);
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        endTimeSec.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(TourPlanActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                timeCheckerAmPm(selectedHour, selectedMinute);
                endTimeHour.setText(String.valueOf(toTimeHour));
                endTimeMin.setText(String.valueOf(toTimeMin));
                endTimeAM.setText(toTimeAm);
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        submitPlan.setOnClickListener(v -> {
            tourObjectVal = tourObjective.getText().toString();
            tourRemarkVal = tourRemarks.getText().toString();
            /*fromTimeHour = startTimeHour.getText().toString();
            fromTimeMin = startTimeMin.getText().toString();
            toTimeHour = endTimeHour.getText().toString();
            toTimeMin = endTimeMin.getText().toString();*/

            if (tourMonthVal != null && tourMorningVal != null && tourEveningVal != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TourPlanActivity.this);
                builder.setTitle("Tour Plan").setMessage("Are you want to submit Tour Plan?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ProgressDialog pDialog = new ProgressDialog(TourPlanActivity.this);
                                pDialog.setMessage("Tour Plan Submit...");
                                pDialog.setCancelable(true);
                                pDialog.show();
                                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                                Call<TourPlanResponse> call = apiInterface.submitDailyTourPlanEntry(userCode, terriCode, tourMonthVal, tourObjectVal, tourRemarkVal, tourMorningVal,
                                        tourEveningVal, tourNatureCode, tourModeCode, tourClassCode, fromTimeHour, fromTimeMin, fromTimeAm, "", toTimeHour, toTimeMin, toTimeAm);

                                call.enqueue(new Callback<TourPlanResponse>() {
                                    @Override
                                    public void onResponse(Call<TourPlanResponse> call, Response<TourPlanResponse> response) {
                                        if (response.isSuccessful()) {
                                            pDialog.dismiss();

                                            if (response.body() != null) {
                                                successMessage.setVisibility(View.VISIBLE);
                                                successMessage.setText(response.body().getMessage());
                                                Toast.makeText(TourPlanActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                            Log.d("tourNature: ", String.valueOf(tourNatureLists));
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<TourPlanResponse> call, Throwable t) {
                                        pDialog.dismiss();
                                        Toast toast = Toast.makeText(getBaseContext(), "Failed to submit tour plan!", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
            } else {
                Toast.makeText(TourPlanActivity.this, "Please select necessary field!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void timeCheckerAmPm(int selectedHour, int selectedMinute) {
        if (selectedHour == 12) {
            fromTimeHour = "00";
            fromTimeAm = "PM";
            toTimeHour = "00";
            toTimeAm = "PM";
        } else if (selectedHour > 12) {
            fromTimeHour = String.valueOf(selectedHour - 12);
            if (Integer.parseInt(fromTimeHour) < 10) {
                fromTimeHour = "0" + fromTimeHour;
            } else {
                fromTimeHour = fromTimeHour;
            }
            fromTimeAm = "PM";
            toTimeHour = String.valueOf(selectedHour - 12);
            if (Integer.parseInt(toTimeHour) < 10) {
                toTimeHour = "0" + toTimeHour;
            } else {
                toTimeHour = toTimeHour;
            }
            toTimeAm = "PM";
        } else {
            fromTimeHour = String.valueOf(selectedHour);
            if (Integer.parseInt(fromTimeHour) < 10) {
                fromTimeHour = "0" + fromTimeHour;
            } else {
                fromTimeHour = fromTimeHour;
            }
            fromTimeAm = "AM";
            toTimeHour = String.valueOf(selectedHour);
            if (Integer.parseInt(toTimeHour) < 10) {
                toTimeHour = "0" + toTimeHour;
            } else {
                toTimeHour = toTimeHour;
            }
            toTimeAm = "PM";
        }
        if (selectedMinute < 10) {
            fromTimeMin = "0" + String.valueOf(selectedMinute);
            toTimeMin = "0" + String.valueOf(selectedMinute);
        } else {
            fromTimeMin = String.valueOf(selectedMinute);
            toTimeMin = String.valueOf(selectedMinute);
        }
    }

    private void initViews() {
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        tourMode = findViewById(R.id.tourMode);
        endTimeAM = findViewById(R.id.endTimeAm);
        tourClass = findViewById(R.id.tourClass);
        tourMonth = findViewById(R.id.tourMonth);
        endTimeMin = findViewById(R.id.endTimeMin);
        endTimeSec = findViewById(R.id.endTimeSec);
        submitPlan = findViewById(R.id.submitPlan);
        tourNature = findViewById(R.id.tourNature);
        tourRemarks = findViewById(R.id.tourRemark);
        startTimeAm = findViewById(R.id.startTimeAm);
        tourMorning = findViewById(R.id.tourMorning);
        tourEvening = findViewById(R.id.tourEvening);
        endTimeHour = findViewById(R.id.endTimeHour);
        startTimeMin = findViewById(R.id.startTimeMin);
        startTimeSec = findViewById(R.id.startTimeSec);
        startTimeHour = findViewById(R.id.startTimeHour);
        tourObjective = findViewById(R.id.tourObjective);
        successMessage = findViewById(R.id.successMessage);
        submitPlan.setText("\uf1d8");
        submitPlan.setTypeface(fontFamily);
        tourModeLists = new ArrayList<>();
        tourMonthLists = new ArrayList<>();
        tourClassLists = new ArrayList<>();
        tourNatureLists = new ArrayList<>();
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

                for (int i = 0; i < tourNatureLists.size(); i++) {
                    if (tourNatureLists.get(i).getTnDesc().equals(tourNatureVal)) {
                        tourNatureCode = tourNatureLists.get(i).getTnCode();
                    }
                }
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

                for (int i = 0; i < tourModeLists.size(); i++) {
                    if (tourModeLists.get(i).getTmDesc().contains(tourModeVal)) {
                        tourModeCode = tourModeLists.get(i).getTmCode();
                    }
                }
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

                for (int i = 0; i < tourClassLists.size(); i++) {
                    if (tourClassLists.get(i).getTmcDesc().contains(tourClassVal)) {
                        tourClassCode = tourClassLists.get(i).getTmcCode();
                    }
                }
                Log.d("tourClass1", tourClassVal);
            }
        });
    }

    private void initTourMorningSpinner(List<TourMorningList> tourMorningLists) {
        ArrayAdapter<String> Adapter;
        List<String> tourMorningList = new ArrayList<String>();

        for (int i = 0; i < tourMorningLists.size(); i++) {
            tourMorningList.add(tourMorningLists.get(i).getMpoCode() + " - " + tourMorningLists.get(i).getTerriName());
        }
        String[] customer = tourMorningList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        tourMorning.setThreshold(2);
        tourMorning.setAdapter(adapter);
        tourMorning.setTextColor(Color.BLUE);

        tourMorning.setOnItemClickListener((parent, view, position, id) -> {
            String tempMorningVal = adapter.getItem(position).toString().trim();
            tourMorningVal = tempMorningVal.split("-")[0];
        });
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
//                try {
//                    String tempMorningVal = s.toString().trim();
//                    tourMorningVal = tempMorningVal.split("-")[0];
//
//                    for (int i = 0; i < tourMorningLists.size(); i++) {
//                        if (tourMorningLists.get(i).getMpoCode().contains(tourMorningVal)) {
//                            tourMorningCode = tourMorningLists.get(i).getMpoCode();
//                            getTourEveningList(tourMorningCode);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
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

        for (int i = 0; i < tourEveningLists.size(); i++) {
            tourEveningList.add(tourEveningLists.get(i).getMpoCode() + " - " + tourEveningLists.get(i).getTerriName());
        }
        String[] customer = tourEveningList.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        tourEvening.setThreshold(2);
        tourEvening.setAdapter(adapter);
        tourEvening.setTextColor(Color.BLUE);

        tourEvening.setOnItemClickListener((parent, view, position, id) -> {
            String tempEveningVal = adapter.getItem(position).toString().trim();
            tourEveningVal = tempEveningVal.split("-")[0];
        });
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
//                try {
//                    String tempEveningVal = s.toString().trim();
//                    tourEveningVal = tempEveningVal.split("-")[0];
//
//                    for (int i = 0; i < tourEveningLists.size(); i++) {
//                        if (tourEveningLists.get(i).getMpoCode().contains(tourEveningVal)) {
//                            tourEveningCode = tourEveningLists.get(i).getMpoCode();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
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
                    if (tourNatureLists.size() > 0) {
                        initTourNatureSpinner(tourNatureLists);
                        tourNature.setText(tourNatureLists.get(0).getTnDesc());
                    }
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
                    if (tourModeLists.size() > 0) {
                        initTourModeSpinner(tourModeLists);
                        tourMode.setText(tourModeLists.get(6).getTmDesc());
                    }
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
                    if (tourClassLists.size() > 0) {
                        initTourClassSpinner(tourClassLists);
                        tourClass.setText(tourClassLists.get(5).getTmcDesc());
                    }
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