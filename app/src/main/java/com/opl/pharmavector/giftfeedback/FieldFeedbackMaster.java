package com.opl.pharmavector.giftfeedback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.opl.pharmavector.Category;

import com.opl.pharmavector.NoticeBoard;
import com.opl.pharmavector.NoticeBoardAdapter;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.giftfeedback.adapter.FeedbackAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FieldFeedbackMaster extends Activity implements OnClickListener {

    Button backBtn;
    ListView pListView;
    Typeface fontFamily;
    ProgressDialog pDialog;
    String usercode;
    static ArrayList<Category> categoriesList;
    ArrayList<String> lables;
    ArrayList<Integer> quanty;
    ArrayList<String> sl;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PPM_CODE;
    public static ArrayList<String> P_CODE;
    public static HashMap<String, Integer> nameSerialPair;
    FeedbackAdapter adapter;
    HashMap<Integer, String> mapQuantity;
    final String campaign_credit = "http://opsonin.com.bd/vector_opl_v1/vector_feedback/get_feedback_master.php";

    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackfollowup_dashboard);
        intiViews();
    }

    private void intiViews() {
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        backBtn = findViewById(R.id.backBtn);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060 ");
        pListView= findViewById(R.id.pListView);
        Bundle b = getIntent().getExtras();
        usercode = b.getString("user_code");

        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        mapQuantity = new HashMap<Integer, String>();
        nameSerialPair = new HashMap<String, Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        PPM_CODE = new ArrayList<String>();
        P_CODE = new ArrayList<String>();
        categoriesList = new ArrayList<Category>();
        new GetCategories().execute();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        finish();
                    }
                });
                backthred.start();
            }
        });
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
            PROD_RATE.add(categoriesList.get(i).getPROD_RATE());//feedbacktitle
            PROD_VAT.add(categoriesList.get(i).getPROD_VAT());//feddbackdetails
            PPM_CODE.add(categoriesList.get(i).getPPM_CODE());//feedbackserialno
            P_CODE.add(categoriesList.get(i).getP_CODE());//adminrespnse
            nameSerialPair.put(categoriesList.get(i).getName(), Integer.parseInt(categoriesList.get(i).getsl()));
            int p_serial = Integer.parseInt(categoriesList.get(i).getsl());
            quanty.add(categoriesList.get(i).getQuantity());
            mapQuantity.put(o, String.valueOf(categoriesList.get(i).getQuantity()));

        }
        adapter = new FeedbackAdapter(FieldFeedbackMaster.this, sl,lables,p_ids,PROD_RATE,PROD_VAT,PPM_CODE,P_CODE);
        pListView.setAdapter(adapter);
    }


    private class GetCategories extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FieldFeedbackMaster.this);
            pDialog.setTitle("Please wait !");
            pDialog.setMessage("Loading Feedback ... ... ");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("usercode", usercode));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(campaign_credit, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),  //topicname
                                    catObj.getString("name"), //topicdetails
                                    catObj.getInt("quantity"),
                                    catObj.getString("PROD_RATE"),//feedback title
                                    catObj.getString("PROD_VAT"),//feedback details
                                    catObj.getString("PPM_CODE"),// feedback slno
                                    catObj.getString("P_CODE") // adminresponse

                            );
                            categoriesList.add(cat);
                        }
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
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }


    protected void onPostExecute() {
    }

    @Override
    public void onClick(View view) {
    }


}


