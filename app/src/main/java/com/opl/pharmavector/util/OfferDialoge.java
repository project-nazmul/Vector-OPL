package com.opl.pharmavector.util;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.opl.pharmavector.Category;
import com.opl.pharmavector.pcconference.PcProposalDoc;
import com.opl.pharmavector.ProductListProductListAdapter;
import com.opl.pharmavector.order_online.ProductOrdernew;
import  com.opl.pharmavector.R;
import com.opl.pharmavector.order_online.ReadComments;
import com.opl.pharmavector.ServiceHandler;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferDialoge extends BottomSheetDialogFragment {
    ListView productListView;
    List<String> mList = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    private String campaign_credit = BASE_URL+"opsonin_product_offer.php";
    static ArrayList<Category> categoriesList;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> SHIFT_CODE;
    public static ArrayList<String> PPM_CODE;
    public static ArrayList<String> P_CODE;
    public static ArrayList<String> PROD_VAT_2;
    public static ArrayList<String> PROD_VAT_3;
    public static ArrayList<String> PROD_VAT_4;
    ArrayList<String> lables;
    ArrayList<Integer> quanty;
    HashMap<Integer, String> mapQuantity;
    static HashMap<String, Integer> nameSerialPair;
    ArrayList<String> sl;
    OfferDialogeAdapter adapter;
    public String customercode;
    public ImageButton remove;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoriesList = new ArrayList<Category>();
        categoriesList.clear();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        mapQuantity = new HashMap<Integer, String>();
        nameSerialPair = new HashMap<String, Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        SHIFT_CODE = new ArrayList<String>();
        PPM_CODE = new ArrayList<String>();
        P_CODE = new ArrayList<String>();
        categoriesList = new ArrayList<Category>();
        customercode = ReadComments.CustomerCode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_offer_dialog, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productListView = (ListView) view.findViewById(R.id.pListView);
        remove =   (ImageButton) view.findViewById(R.id.remove);

        //mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, mList);
        new GetCategories().execute();
        remove.setOnClickListener(v -> {
            //you can use isShowing() because BottomSheet inherit from Dialog class
          dismiss();
        });
    }

    private void populateSpinner() {
        lables = new ArrayList<String>();
        quanty = new ArrayList<Integer>();
        sl = new ArrayList<String>();

        ArrayList<String> value6 = new ArrayList<String>();
        ArrayList<String> value7 = new ArrayList<String>();
        ArrayList<String> value8 = new ArrayList<String>();
        int quantity = 0;

        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getName());
            sl.add(categoriesList.get(i).getsl());
            int o = Integer.parseInt(categoriesList.get(i).getsl());
            Log.e("serial",""+o);
            p_ids.add(categoriesList.get(i).getId());
            PROD_RATE.add(categoriesList.get(i).getPROD_RATE());
            PROD_VAT.add(categoriesList.get(i).getPROD_VAT());
            PPM_CODE.add(categoriesList.get(i).getPPM_CODE());
            P_CODE.add(categoriesList.get(i).getP_CODE());
            SHIFT_CODE.add(categoriesList.get(i).getSHIFT_CODE());
            nameSerialPair.put(categoriesList.get(i).getName(), Integer.parseInt(categoriesList.get(i).getsl()));
            int p_serial = Integer.parseInt(categoriesList.get(i).getsl());
            quanty.add(categoriesList.get(i).getQuantity());
            mapQuantity.put(o, String.valueOf(categoriesList.get(i).getQuantity()));
        }
        Log.e("Total Products",""+categoriesList.size());
        adapter = new OfferDialogeAdapter(getActivity(), sl, lables, mapQuantity,PPM_CODE,P_CODE,PROD_RATE,PROD_VAT,p_ids,SHIFT_CODE);
        productListView.setAdapter(adapter);
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("CUST_CODE", customercode));

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(campaign_credit, ServiceHandler.GET, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray categories = jsonObj.getJSONArray("categories");
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject catObj = (JSONObject) categories.get(i);
                        Category cat = new Category(
                                catObj.getString("sl"),
                                catObj.getString("id"),
                                catObj.getString("name"),
                                catObj.getInt("quantity"),
                                catObj.getString("PROD_RATE"),
                                catObj.getString("PROD_VAT"),
                                catObj.getString("PPM_CODE"),
                                catObj.getString("P_CODE"),
                                catObj.getString("SHIFT_CODE")
                        );
                        categoriesList.add(cat);
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
            populateSpinner();
        }
    }
}

