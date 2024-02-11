package com.opl.pharmavector.tourPlan;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.R;
import com.opl.pharmavector.prescriber.FromDateList;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.saleReport.AMGroupOrdSumDetails;
import com.opl.pharmavector.saleReport.GrpOrdSumDetailAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class TourReviewActivity extends Activity implements TourReviewAdapter.ItemClickListener {
    public Button btnShow, btnBack;
    public String user_code, month_name;
    public RecyclerView recyclerTourReview;
    public TourReviewAdapter tourReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_review);

        initViews();
        getTourReviewMonthList();
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        btnShow = findViewById(R.id.btnShow);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setTypeface(fontFamily);
        btnBack.setText("\uf060 ");

        btnBack.setOnClickListener(v -> finish());
        btnShow.setOnClickListener(v -> {
            if (month_name != null) {
                getTourReviewDetailList();
            }
        });
        Bundle bundle = getIntent().getExtras();
        user_code = bundle.getString("UserName");
        recyclerTourReview = findViewById(R.id.recyclerTourReview);
    }

    public void getTourReviewMonthList() {
        ProgressDialog monthDialog = new ProgressDialog(TourReviewActivity.this);
        monthDialog.setMessage("Tour Month Loading...");
        monthDialog.setTitle("Tour Month Followup");
        monthDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TReviewMonModel> call = apiInterface.getTourReviewMonthList(user_code);

        call.enqueue(new Callback<TReviewMonModel>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<TReviewMonModel> call, @NonNull retrofit2.Response<TReviewMonModel> response) {
                List<TReviewMonthList> reviewMonLists = null;
                if (response.body() != null) {
                    reviewMonLists = response.body().getReviewMonthLists();
                }

                if (response.isSuccessful()) {
                    monthDialog.dismiss();
                    initMonthSpinner(Objects.requireNonNull(reviewMonLists));
                } else {
                    monthDialog.dismiss();
                    Toast.makeText(TourReviewActivity.this, "No data Available!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TReviewMonModel> call, @NonNull Throwable t) {
                monthDialog.dismiss();
            }
        });
    }

    public void getTourReviewDetailList() {
        ProgressDialog reviewDialog = new ProgressDialog(TourReviewActivity.this);
        reviewDialog.setMessage("Tour Review Loading...");
        reviewDialog.setTitle("Tour Review Followup");
        reviewDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TReviewDetailModel> call = apiInterface.getTourReviewDetailList(user_code, month_name);

        call.enqueue(new Callback<TReviewDetailModel>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<TReviewDetailModel> call, @NonNull retrofit2.Response<TReviewDetailModel> response) {
                List<TReviewDetailsList> reviewDetailList = null;

                if (response.body() != null) {
                    reviewDialog.dismiss();
                    reviewDetailList = response.body().getReviewDetailList();
                    tourReviewAdapter = new TourReviewAdapter(TourReviewActivity.this, reviewDetailList, TourReviewActivity.this);
                    LinearLayoutManager linearManager = new LinearLayoutManager(TourReviewActivity.this);
                    recyclerTourReview.setLayoutManager(linearManager);
                    recyclerTourReview.setAdapter(tourReviewAdapter);
                    recyclerTourReview.addItemDecoration(new DividerItemDecoration(TourReviewActivity.this, DividerItemDecoration.VERTICAL));
                }
                Log.d("Group Order List -- : ", String.valueOf(reviewDetailList));
            }

            @Override
            public void onFailure(@NonNull Call<TReviewDetailModel> call, @NonNull Throwable t) {
                reviewDialog.dismiss();
            }
        });
    }

    private void initMonthSpinner(List<TReviewMonthList> reviewMonList) {
        MaterialSpinner monthSpinner = findViewById(R.id.monthSpinner);
        ArrayList<String> monthNameList = new ArrayList<>();

        if (reviewMonList.size() > 0) {
            for (TReviewMonthList monthName : reviewMonList) {
                monthNameList.add(monthName.getMnyrDesc());
            }
        }
        monthSpinner.setItems(monthNameList);
        monthSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                String tempMonName = String.valueOf(item).trim();

                for (int i = 0; i < reviewMonList.size(); i++) {
                    if (reviewMonList.get(i).getMnyrDesc().contains(tempMonName)) {
                        month_name = reviewMonList.get(i).getMnyr();
                    }
                }
                Log.d("month name", month_name);
            }
        });
    }

    @Override
    public void onClick(int position, TReviewDetailsList reviewModel) {

    }
}