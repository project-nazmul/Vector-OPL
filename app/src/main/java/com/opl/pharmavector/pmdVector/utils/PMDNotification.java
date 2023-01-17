package com.opl.pharmavector.pmdVector.utils;



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.opl.pharmavector.Category;

import com.opl.pharmavector.NoticeBoardDetails;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;

import com.opl.pharmavector.pmdVector.DashBoardPMD;
import com.opl.pharmavector.pmdVector.adapter.PMDNotificationAdapter;
import com.opl.pharmavector.remote.ApiClient;
import com.squareup.picasso.Picasso;


public class PMDNotification extends Activity implements OnClickListener {

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_SUCCESS1 = "success_1";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_MESSAGE_2 = "message_2";
    public static final String TAG_ord_no = "ord_no";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public String userName_1, userName="xx";
    // array list for spinner adapter
    static ArrayList<Category> categoriesList;
    ProgressDialog pDialog;
    static ListView productListView;
    Button back_btn;

    public String get_ext_dt3;
    public String brand_name;
    public int success, success_1, ordsl;
    public String message, ord_no, invoice, target, achivement, searchString, message_1, message_2;
    PMDNotificationAdapter adapter;
    public LinearLayout totalshow;
    public Button calc;
    public String brand;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PPM_CODE;
    public static ArrayList<String> P_CODE;
    ArrayList<String> lables;
    ArrayList<Integer> quanty;
    HashMap<Integer, String> mapQuantity;
    public static HashMap<String, Integer> nameSerialPair;
    ArrayList<String> sl;

    private final String campaign_credit = ApiClient.BASE_URL+"notification/get_notification.php";
    public static String base_url = ApiClient.BASE_URL+"pmd_vector/pmd_images/";

    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_pmd_notice_board);

        initViews();

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String notification_id = (String) productListView.getAdapter().getItem(arg2);
                Intent i = new Intent(PMDNotification.this, NoticeBoardDetails.class);
                i.putExtra("UserName", DashBoardPMD.pmd_loccode);
                i.putExtra("UserName_2", DashBoardPMD.pmd_type);
                i.putExtra("notification_id", notification_id);
                startActivity(i);
            }
        });

        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();


            }
        });

        new GetCategories().execute();



    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView =  findViewById(R.id.pListView);
        productListView.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);

        TextView parent_menu= findViewById(R.id.parent_menu);

        parent_menu.setText(MessageFormat.format("{0}\nNotification", DashBoardPMD.pmd_loccode));

        ImageView imageView2 = findViewById(R.id.imageView2);
        String profile_image= base_url+DashBoardPMD.pmd_code+"."+"jpg" ;
        Picasso.get()
                .load(profile_image)
                .into(imageView2);
        back_btn = findViewById(R.id.backBtn);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060

        PMDNotificationAdapter.qnty = null;
        PMDNotificationAdapter.qntyID.clear();
        PMDNotificationAdapter.qntyVal.clear();

        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        mapQuantity = new HashMap<Integer, String>();
        nameSerialPair = new HashMap<String, Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        PPM_CODE = new ArrayList<String>();
        P_CODE = new ArrayList<String>();
        categoriesList = new ArrayList<Category>();
    }


    public void finishActivity(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        // System.out.println("EditTxtID " + PMDNotificationAdapter.editTxtID.size());
        super.onResume();

    }


    private void populateSpinner() {

        lables = new ArrayList<String>();
        quanty = new ArrayList<Integer>();
        sl = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getName());
            sl.add(categoriesList.get(i).getsl());
            int o = Integer.parseInt(categoriesList.get(i).getsl());
            p_ids.add(categoriesList.get(i).getId());
            PROD_RATE.add(categoriesList.get(i).getPROD_RATE());
            PROD_VAT.add(categoriesList.get(i).getPROD_VAT());
            PPM_CODE.add(categoriesList.get(i).getPPM_CODE());
            P_CODE.add(categoriesList.get(i).getP_CODE());
            nameSerialPair.put(categoriesList.get(i).getName(), Integer.parseInt(categoriesList.get(i).getsl()));
            int p_serial = Integer.parseInt(categoriesList.get(i).getsl());
            quanty.add(categoriesList.get(i).getQuantity());
            mapQuantity.put(o, String.valueOf(categoriesList.get(i).getQuantity()));
        }
        adapter = new PMDNotificationAdapter(PMDNotification.this, sl, lables, mapQuantity, PPM_CODE, P_CODE);
        productListView.setAdapter(adapter);

    }


    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PMDNotification.this);
            pDialog.setTitle("Please wait !");
            pDialog.setMessage("Loading Notifications ... ... ");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("MPO_CODE", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(campaign_credit, ServiceHandler.POST, params);

            Log.e("json-->",json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray categories = jsonObj.getJSONArray("categories");
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject catObj = (JSONObject) categories.get(i);
                        Category cat = new Category(catObj.getString("sl"),
                                catObj.getString("id"),
                                catObj.getString("name"),
                                catObj.getInt("quantity"),
                                catObj.getString("PROD_RATE"),
                                catObj.getString("PROD_VAT"),
                                catObj.getString("PPM_CODE"),
                                catObj.getString("P_CODE"));
                        categoriesList.add(cat);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }

    }


    @Override
    public void onClick(View v) {

    }

    protected void onPostExecute() {

    }

}



