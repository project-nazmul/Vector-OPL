package com.opl.pharmavector.saleReport;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.Targetquantity;
import com.opl.pharmavector.dcfpFollowup.DcfpFollowupActivity;
import com.opl.pharmavector.dcfpFollowup.DcrFollowupAdapter;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupOderSummaryNew extends Activity implements GrpOrdSummaryAdapter.ItemClickListener {
    public Button submitButton, backButton;
    public String userName, userCode;
    public TextView fromDateOrd, toDateOrder;
    private GrpOrdSummaryAdapter grpOrdSummaryAdapter;
    private RecyclerView recyclerOrderSumNew;
    private Calendar fromCalendar, toCalendar;
    DatePickerDialog.OnDateSetListener dateFormOrd, dateToOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_ord_summary_new);

        initViews();
        calenderClickEvents();
        submitButton.setOnClickListener(v -> {
            if (userCode != null) {
                getGroupProdOrderSummary();
            }
        });
        backButton.setOnClickListener(v -> finish());
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        Bundle bundle = getIntent().getExtras();
        //userName = bundle.getString("UserCode");
        userCode = bundle.getString("UserCode");
        fromDateOrd = findViewById(R.id.fromDateOrd);
        toDateOrder = findViewById(R.id.toDateOrder);
        backButton = findViewById(R.id.backButton);
        backButton.setTypeface(fontFamily);
        backButton.setText("\uf060 ");
        submitButton = findViewById(R.id.submitButton);
        recyclerOrderSumNew = findViewById(R.id.recyclerOrderSumNew);

        Calendar calendarToDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatToDate = new SimpleDateFormat("dd/MM/yyyy");
        String currentToDate = formatToDate.format(calendarToDate.getTime());
        toDateOrder.setText(currentToDate);
        Calendar calendarFromDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatFromDate = new SimpleDateFormat("01/MM/yyyy");
        String currentFromDate = formatFromDate.format(calendarFromDate.getTime());
        fromDateOrd.setText(currentFromDate);
        toCalendar = Calendar.getInstance();
        fromCalendar = Calendar.getInstance();
    }

    private void calenderClickEvents() {
        dateFormOrd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fromCalendar.set(Calendar.YEAR, year);
                fromCalendar.set(Calendar.MONTH, monthOfYear);
                fromCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                fromDateOrd.setTextColor(Color.BLACK);
                fromDateOrd.setText("");
                fromDateOrd.setText(sdf.format(fromCalendar.getTime()));
            }
        };
        fromDateOrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(GroupOderSummaryNew.this, dateFormOrd, fromCalendar.get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH),
                        fromCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dateToOrder = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                toCalendar.set(Calendar.YEAR, year);
                toCalendar.set(Calendar.MONTH, monthOfYear);
                toCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                toDateOrder.setTextColor(Color.BLACK);
                toDateOrder.setText("");
                toDateOrder.setText(sdf.format(toCalendar.getTime()));
            }
        };
        toDateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(GroupOderSummaryNew.this, dateToOrder, fromCalendar
                        .get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH),
                        toCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void getGroupProdOrderSummary() {
        String selectFromDate = fromDateOrd.getText().toString().trim();
        String selectToDate = toDateOrder.getText().toString().trim();

        ProgressDialog pDialog = new ProgressDialog(GroupOderSummaryNew.this);
        pDialog.setMessage("Loading Order Summary ...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<GroupOrdSummaryModel> call = apiInterface.getGroupProdOrderSummary(userCode, selectFromDate, selectToDate);

        call.enqueue(new Callback<GroupOrdSummaryModel>() {
            @Override
            public void onResponse(Call<GroupOrdSummaryModel> call, Response<GroupOrdSummaryModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<GroupOrderSummaryList> orderSummaryLists = null;

                    if (response.body() != null) {
                        orderSummaryLists = (response.body()).getGroupOrdSummaryLists();
                        grpOrdSummaryAdapter = new GrpOrdSummaryAdapter(GroupOderSummaryNew.this, orderSummaryLists, GroupOderSummaryNew.this);
                        LinearLayoutManager linearManager = new LinearLayoutManager(GroupOderSummaryNew.this);
                        recyclerOrderSumNew.setLayoutManager(linearManager);
                        recyclerOrderSumNew.setAdapter(grpOrdSummaryAdapter);
                        recyclerOrderSumNew.addItemDecoration(new DividerItemDecoration(GroupOderSummaryNew.this, DividerItemDecoration.VERTICAL));
                    }
                    Log.d("Group Order List -- : ", String.valueOf(orderSummaryLists));
                }
            }

            @Override
            public void onFailure(Call<GroupOrdSummaryModel> call, Throwable t) {
                pDialog.dismiss();
                Log.d("Data load problem --->", "Failed to Retried Data For -- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(int position, GroupOrderSummaryList orderModel) {

    }
}