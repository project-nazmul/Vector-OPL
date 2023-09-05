package com.opl.pharmavector.pcconference;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.R;
import com.opl.pharmavector.adapter.CategoryAdapter;
import com.opl.pharmavector.model.Category;
import com.opl.pharmavector.pcconference.PcProposal;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser;
import com.opl.pharmavector.serverCalls.InsertUpdateFavouriteCategories;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class PcProposalDoc extends AppCompatActivity{
    Context context;
    ArrayList<Category> array_list;
    FavouriteCategoriesJsonParser categoryJsonParser;
    String categoriesCsv;
    String categoriesPhone,UserName_2;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public int success;
    public String message, ord_no,invoice,target_data,achivement,searchString,select_party,my_val;
    private String pc_conference_submit = BASE_URL+"pcconference/mpo_pc_conference/mpo_pc_conference_submit.php";
    public String conference_date;
    public String market_code;
    public String market_name;
    public String venue_name;
    public String pc_rmp_val;
    public String doc_val;
    public String inhouse_val;
    public String total_participant;
    public String venue_charge;
    public String food_per_person;
    public String food_total_bdt;
    public String miscell,miscell_bdt;
    public String impact,TOTAL_BUDGET;
    public String product_list,conf_type_val;
    public static String UserName;
    TextView user_show;
    Button button,logback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_proposal_activity);

        initViews();
        user_show.setText(String.format("%s %s ", UserName, UserName_2));

        if (venue_charge.trim().equals("")) {
            venue_charge="0";
        } if (miscell_bdt.trim().equals("")) {
            miscell_bdt="0";
        }
        int result1 = Integer.valueOf(food_total_bdt);
        int result2 = Integer.valueOf(miscell_bdt);
        int result3 = Integer.valueOf(venue_charge);
        int result= result1+result2+result3;
        new asyncTask_getCategories().execute();

        logback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                categoriesCsv = FavouriteCategoriesJsonParser.selectedCategories.toString();
                categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);
                categoriesPhone = FavouriteCategoriesJsonParser.selectedphonenumber.toString();
                categoriesPhone = categoriesPhone.substring(1, categoriesPhone.length() - 1);
                Bundle b = getIntent().getExtras();

            if (categoriesCsv.length() < 0) {
            Toast.makeText(context, "Please Select Doctor", Toast.LENGTH_SHORT).show();
            } else {
                    Thread server = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            JSONParser jsonParser = new JSONParser();
                            Bundle b = getIntent().getExtras();
                            final String userName = b.getString("UserName");
                            final String UserName_2 = b.getString("UserName_2");
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("categoriesCsv", categoriesCsv));
                            params.add(new BasicNameValuePair("categoriesPhone", categoriesPhone));
                            params.add(new BasicNameValuePair("MPO_CODE", UserName));
                            params.add(new BasicNameValuePair("conference_date", conference_date));
                            params.add(new BasicNameValuePair("market_code", market_code));
                            params.add(new BasicNameValuePair("market_name", market_name));
                            params.add(new BasicNameValuePair("venue_name", venue_name));
                            params.add(new BasicNameValuePair("pc_rmp_val", pc_rmp_val));
                            params.add(new BasicNameValuePair("doc_val", doc_val));
                            params.add(new BasicNameValuePair("inhouse_val", inhouse_val));
                            params.add(new BasicNameValuePair("total_participant", total_participant));
                            params.add(new BasicNameValuePair("food_per_person", food_per_person));
                            params.add(new BasicNameValuePair("food_total_bdt", food_total_bdt));
                            params.add(new BasicNameValuePair("miscell", miscell));
                            params.add(new BasicNameValuePair("miscell_bdt", miscell_bdt));
                            params.add(new BasicNameValuePair("venue_charge", venue_charge));
                            params.add(new BasicNameValuePair("impact", impact));
                            params.add(new BasicNameValuePair("UserName", UserName));
                            params.add(new BasicNameValuePair("TOTAL_BUDGET", TOTAL_BUDGET));
                            params.add(new BasicNameValuePair("conf_type_val", conf_type_val));
                            JSONObject json = jsonParser.makeHttpRequest(pc_conference_submit, "POST", params);

                            try {
                                success = json.getInt(TAG_SUCCESS);
                                message = json.getString(TAG_MESSAGE);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            Intent in = getIntent();
                            Intent inten = getIntent();
                            Bundle bundle = in.getExtras();
                            inten.getExtras();
                            String MPO_CODE = bundle.getString("MPO_CODE");
                            String pc_sl_no = message;
                            Intent sameint = new Intent(PcProposalDoc.this, PcProposal.class);
                            sameint.putExtra("UserName", UserName);
                            sameint.putExtra("UserName_2", UserName_2);
                            sameint.putExtra("Ord_NO", message);
                            startActivity(sameint);
                        }
                    });
                    server.start();
                }
            }
        });

    }

    private void initViews() {

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        button = (Button) findViewById(R.id.selectCategoryButton);
        logback = (Button) findViewById(R.id.logback);
        logback.setTypeface(fontFamily);
        logback.setText("\uf08b");
        user_show= findViewById(R.id.user_show);
        context = this;
        Bundle b = getIntent().getExtras();


        conference_date = b.getString("conference_date");
        market_code = b.getString("market_code");
        market_name = b.getString("market_name");
        venue_name = b.getString("venue_name");
        pc_rmp_val = b.getString("pc_rmp_val");
        doc_val = b.getString("doc_val");
        inhouse_val = b.getString("inhouse_val");
        total_participant = b.getString("total_participant");
        venue_charge = b.getString("venue_charge");
        food_per_person = b.getString("food_per_person");
        food_total_bdt = b.getString("food_total_bdt");
        miscell = b.getString("miscell");
        miscell_bdt = b.getString("miscell_bdt");
        impact = b.getString("impact");
        UserName = b.getString("MPO_CODE");
        conf_type_val = b.getString("conf_type_val");
        UserName_2 = b.getString("UserName_2");
        TOTAL_BUDGET= b.getString("total_budget");
    }


    public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait...");
            dialog.setMessage("Loading Doctors!");
            dialog.show();
            array_list = new ArrayList<>();
            my_val=UserName;
            categoryJsonParser = new FavouriteCategoriesJsonParser();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            array_list = categoryJsonParser.getParsedCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {

            ListView mListViewBooks = (ListView) findViewById(R.id.category_listView);
            final CategoryAdapter categoryAdapter = new CategoryAdapter(context, R.layout.row_category, array_list);
            mListViewBooks.setAdapter(categoryAdapter);
            super.onPostExecute(s);
            dialog.dismiss();
        }

    }



}










