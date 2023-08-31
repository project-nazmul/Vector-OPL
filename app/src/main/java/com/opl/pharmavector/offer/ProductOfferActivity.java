package com.opl.pharmavector.offer;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.Category;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.order_online.ReadComments;
import com.opl.pharmavector.prescriber.FieldForceList;
import com.opl.pharmavector.prescriber.FieldForceModel;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.report.LocationTrackerActivity;
import com.opl.pharmavector.report.LocationTrackerAdapter;
import com.opl.pharmavector.util.OfferDialogeAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductOfferActivity extends Activity {
    EditText productSearch;
    RecyclerView recyclerProductOffer;
    private ProductOfferAdapter productOfferAdapter;
    private List<ProductOfferList> productOfferList = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_offer);

        productSearch = findViewById(R.id.productSearch);
        recyclerProductOffer = findViewById(R.id.recyclerProductOffer);
        initProductOfferAdapter();
        getProductOfferList();

        productSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                productOfferFilter(s.toString().trim());
            }
        });
    }

    private void initProductOfferAdapter() {
        productOfferAdapter = new ProductOfferAdapter(ProductOfferActivity.this, productOfferList);
        LinearLayoutManager manager = new LinearLayoutManager(ProductOfferActivity.this);
        recyclerProductOffer.setLayoutManager(manager);
        recyclerProductOffer.setAdapter(productOfferAdapter);
        recyclerProductOffer.addItemDecoration(new DividerItemDecoration(ProductOfferActivity.this, DividerItemDecoration.VERTICAL));
    }

    void productOfferFilter(String query) {
        List<ProductOfferList> queryOfferList = new ArrayList<>();

        for(ProductOfferList offerList: productOfferList) {
            if(offerList.getName().toLowerCase().contains(query.toLowerCase())) {
                queryOfferList.add(offerList);
            }
        }
        productOfferAdapter.searchOfferList(queryOfferList);
    }

    private void getProductOfferList() {
        ProgressDialog productOfferDialog = new ProgressDialog(ProductOfferActivity.this);
        productOfferDialog.setMessage("Product offer List Loading...");
        productOfferDialog.setTitle("Product Offer List Followup");
        productOfferDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProductOfferModel> call = apiInterface.getProductOfferList("");
        productOfferList.clear();
        Log.d("productOffer", productOfferList.toString());

        call.enqueue(new Callback<ProductOfferModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ProductOfferModel> call, Response<ProductOfferModel> response) {
                if (response.isSuccessful()) {
                    productOfferDialog.dismiss();
                    productOfferList = Objects.requireNonNull(response.body()).getProductOfferList();
                    productOfferAdapter.searchOfferList(productOfferList);
                }
            }

            @Override
            public void onFailure(Call<ProductOfferModel> call, Throwable t) {
                productOfferDialog.dismiss();
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retrieved Data!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}