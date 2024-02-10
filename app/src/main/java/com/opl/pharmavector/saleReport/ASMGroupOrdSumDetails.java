package com.opl.pharmavector.saleReport;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
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
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ASMGroupOrdSumDetails extends Activity implements GrpOrdSumDetailAdapter.ItemClickListener {
    public String userName, userCode, productCode, productName, productPack, productTeam, selectDate;
    public Button submitButton, backButton;
    public TextView fromDateOrd, toDateOrder, tvProductCode, tvProductName, tvProductPack, tvProductTeam;
    private RecyclerView recyclerOrderSumNew;
    private Calendar fromCalendar, toCalendar;
    private GrpOrdSumDetailAdapter grpOrdSumDetailAdapter;
    DatePickerDialog.OnDateSetListener dateFormOrd, dateToOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_ord_details_new);

        initViews();
        //calenderClickEvents();
//        submitButton.setOnClickListener(v -> {
//            if (userCode != null) {
//                getGroupOrdSummaryDetails();
//            }
//        });
        backButton.setOnClickListener(v -> finish());
        if (userCode != null) {
            getGroupOrdSummaryDetails();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        Bundle bundle = getIntent().getExtras();
        userCode = bundle.getString("userCode");
        productCode = bundle.getString("prodCode");
        productName = bundle.getString("prodDesc");
        productPack = bundle.getString("prodPack");
        productTeam = bundle.getString("prodTeam");
        selectDate = bundle.getString("selectDate");
        //fromDateOrd = findViewById(R.id.fromDateOrd);
        //toDateOrder = findViewById(R.id.toDateOrder);
        backButton = findViewById(R.id.backButton);
        tvProductCode = findViewById(R.id.tvProductCode);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPack = findViewById(R.id.tvProductPack);
        tvProductTeam = findViewById(R.id.tvProductTeam);
        backButton.setTypeface(fontFamily);
        backButton.setText("\uf060 ");
        submitButton = findViewById(R.id.submitButton);
        recyclerOrderSumNew = findViewById(R.id.recyclerOrderSumNew);

//        Calendar calendarToDate = Calendar.getInstance();
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatToDate = new SimpleDateFormat("dd/MM/yyyy");
//        String currentToDate = formatToDate.format(calendarToDate.getTime());
//        fromDateOrd.setText(currentToDate);
//        Calendar calendarFromDate = Calendar.getInstance();
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatFromDate = new SimpleDateFormat("01/MM/yyyy");
//        String currentFromDate = formatFromDate.format(calendarFromDate.getTime());
//        fromDateOrd.setText(currentFromDate);
        toCalendar = Calendar.getInstance();
        fromCalendar = Calendar.getInstance();
        if (productCode != null) {
            String prodCodeHtml = "<font color=#000000>Product Code: </font>" + "<font color=#FFCC0000>" + "<b>" + productCode + "</b>" + "</font>";
            tvProductCode.setText(Html.fromHtml(prodCodeHtml));
        }
        if (productName != null) {
            String prodNameHtml = "<font color=#000000>Product Name: </font>" + "<font color=#FFCC0000>" + "<b>" + productName + "</b>" + "</font>";
            tvProductName.setText(Html.fromHtml(prodNameHtml));
        }
        if (productPack != null) {
            String prodPackHtml = "<font color=#000000>Pack Size: </font>" + "<font color=#FFCC0000>" + "<b>" + productPack + "</b>" + "</font>";
            tvProductPack.setText(Html.fromHtml(prodPackHtml));
        }
        if (productTeam != null) {
            String prodTeamHtml = "<font color=#000000>Product Group: </font>" + "<font color=#FFCC0000>" + "<b>" + productTeam + "</b>" + "</font>";
            tvProductTeam.setText(Html.fromHtml(prodTeamHtml));
        }
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
                new DatePickerDialog(ASMGroupOrdSumDetails.this, dateFormOrd, fromCalendar.get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH),
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
                new DatePickerDialog(ASMGroupOrdSumDetails.this, dateToOrder, fromCalendar
                        .get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH),
                        toCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void getGroupOrdSummaryDetails() {
        //String selectFromDate = fromDateOrd.getText().toString().trim();
        //String selectToDate = toDateOrder.getText().toString().trim();

        ProgressDialog pDialog = new ProgressDialog(ASMGroupOrdSumDetails.this);
        pDialog.setMessage("Loading Order Summary Details...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<GroupOrdSumDetailModel> call = apiInterface.getGroupOrderSummaryDetail(userCode, selectDate, selectDate);

        call.enqueue(new Callback<GroupOrdSumDetailModel>() {
            @Override
            public void onResponse(Call<GroupOrdSumDetailModel> call, Response<GroupOrdSumDetailModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<GroupOrderSumDetailList> orderSumDetailLists = null;

                    if (response.body() != null) {
                        orderSumDetailLists = (response.body()).getOrdSumDetailLists();
                        grpOrdSumDetailAdapter = new GrpOrdSumDetailAdapter(ASMGroupOrdSumDetails.this, orderSumDetailLists, ASMGroupOrdSumDetails.this);
                        LinearLayoutManager linearManager = new LinearLayoutManager(ASMGroupOrdSumDetails.this);
                        recyclerOrderSumNew.setLayoutManager(linearManager);
                        recyclerOrderSumNew.setAdapter(grpOrdSumDetailAdapter);
                        recyclerOrderSumNew.addItemDecoration(new DividerItemDecoration(ASMGroupOrdSumDetails.this, DividerItemDecoration.VERTICAL));
                    }
                    Log.d("Group Order List -- : ", String.valueOf(orderSumDetailLists));
                }
            }

            @Override
            public void onFailure(Call<GroupOrdSumDetailModel> call, Throwable t) {
                pDialog.dismiss();
                Log.d("Data load problem --->", "Failed to Retried Data For -- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(int position, GroupOrderSumDetailList orderModel) {
        if (orderModel.getFfCode() != null) {
            Intent i = new Intent(ASMGroupOrdSumDetails.this, RMGroupOrderSumDetails.class);
            i.putExtra("userCode", orderModel.getFfCode());
            i.putExtra("selectDate", selectDate);
            i.putExtra("prodCode", productCode);
            i.putExtra("prodDesc", productName);
            i.putExtra("prodPack", productPack);
            i.putExtra("prodTeam", productTeam);
            startActivity(i);
        }
    }
}