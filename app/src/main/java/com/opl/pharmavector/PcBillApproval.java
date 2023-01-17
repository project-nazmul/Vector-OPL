
package com.opl.pharmavector;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.opl.pharmavector.adapter.CategoryAdapter5;
import com.opl.pharmavector.model.Category4;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser5;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
import static com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser5.selectedCategories5;

public class PcBillApproval extends AppCompatActivity {
    Context context;
    ArrayList<Category4> array_list;
    FavouriteCategoriesJsonParser5 categoryJsonParser;
    String categoriesCsv;




    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public int success;
    public String message, ord_no,invoice,target_data,achivement,searchString,select_party,my_val;

    private String pc_approval_submit = BASE_URL+"pc_bill_approval_submit.php";
    private String pc_cancel_submit = BASE_URL+"pc_bill_cancel_submit.php";

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
    public String product_list;
    public String approval_user_role;
    public static String UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_bill_approval);


        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        Button bill_approve_button = (Button) findViewById(R.id.selectCategoryButton);

        Button button2 = (Button) findViewById(R.id.selectCategoryButton2);


        Button logback = (Button) findViewById(R.id.logback);


        logback.setTypeface(fontFamily);
        logback.setText("\uf08b");


        TextView user_show=(TextView) findViewById(R.id.user_show);

        context = this;

        Bundle b = getIntent().getExtras();
        UserName = b.getString("userName");
        final String UserName_2 = b.getString("UserName_2");

        user_show.setText(UserName+ " "+ UserName_2 +" "  );



        int commas = 0;
        for(int i = 0; i < UserName.length(); i++) {
            if(UserName.charAt(i) == '0') commas++;
        }

        if(commas== 1){

            if (UserName.length()==7)
            {
                approval_user_role = "GM";
            }

            else {
                approval_user_role = "A"; // AREA MANAGER

            }
        }  else if(commas== 2){
            approval_user_role="R"; ///REGIONAL
        }
        else if(commas== 3){
            approval_user_role="ASM"; // ASM/DSM
        } else if(commas== 4){
            approval_user_role="SM";
        } else if(commas== 5){
            approval_user_role="GM";
        }

        else if(commas== 0){

            if (UserName.length()==7)
            {
                approval_user_role = "GM";
            }

            else {
                approval_user_role = "M"; ///MPO

            }
        }



        new asyncTask_getCategories().execute();



        logback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {

/*
                            if(approval_user_role.trim().equals("A")){
                                Intent i = new Intent(PcBillApproval.this, PCDashboard.class);
                                i.putExtra("UserName", UserName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", approval_user_role);
                                Log.d("UserName_2","UserName_2"+UserName_2);
                                startActivity(i);
                            }
                            else if(approval_user_role.toString().trim().equals("R")){
                                Intent i = new Intent(PcBillApproval.this, PCDashboard.class);
                                i.putExtra("UserName", UserName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", approval_user_role);
                                Log.d("UserName_2","UserName_2"+UserName_2 );
                                startActivity(i);
                            }
                            else if(approval_user_role.toString().trim().equals("ASM")){
                                Intent i = new Intent(PcBillApproval.this, PCDashboard.class);
                                i.putExtra("UserName", UserName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", approval_user_role);
                                Log.d("UserName_2","UserName_2"+UserName_2 );
                                startActivity(i);
                            }
                            else if(approval_user_role.toString().trim().equals("SM")){
                                Intent i = new Intent(PcBillApproval.this, PCDashboard.class);
                                i.putExtra("UserName", UserName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", approval_user_role);
                                Log.d("UserName_2","UserName_2"+UserName_2 );
                                startActivity(i);
                            }

                            else if(approval_user_role.toString().trim().equals("GM")){
                                Intent i = new Intent(PcBillApproval.this, PCDashboard.class);
                                i.putExtra("UserName", UserName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", approval_user_role);
                                Log.d("UserName_2","UserName_2"+UserName_2 );
                                startActivity(i);
                            }
*/
                             finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();

            }
        });








        bill_approve_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




                new AlertDialog.Builder(PcBillApproval.this)
                        .setTitle(" 'APPROVE', Are you sure?")
                        .setMessage("You agree to Approve pc bill requision ")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {

                                categoriesCsv = FavouriteCategoriesJsonParser5.selectedCategories5.toString();
                                categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);
                                Bundle b = getIntent().getExtras();

                                Log.e("categoriesCsv",categoriesCsv);
                                Log.e("categoriesCsv",categoriesCsv);

                                if (categoriesCsv.length() < 0) {
                                    Toast.makeText(context, "Please select a bill to approve ", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Thread server = new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            JSONParser jsonParser = new JSONParser();
                                            Bundle b = getIntent().getExtras();
                                            final String userName = b.getString("UserName");
                                            final String UserName_2 = b.getString("UserName_2");

                                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                                            params.add(new BasicNameValuePair("categoriesCsv", categoriesCsv));
                                            params.add(new BasicNameValuePair("UserName", UserName));
                                            Log.e("categoriesCsv",categoriesCsv);
                                            Log.e("UserName",UserName);
                                            Log.e("categoriesCsv",categoriesCsv);
                                            JSONObject json = jsonParser.makeHttpRequest(pc_approval_submit, "POST", params);

                                            try {
                                                success = json.getInt(TAG_SUCCESS);
                                                message = json.getString(TAG_MESSAGE);
                                                Log.w("please wait TRY ...." + message, json.toString());
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                                Log.w("Please wait ...." + message, json.toString());
                                            }


                                            Intent in = getIntent();
                                            Intent inten = getIntent();
                                            Bundle bundle = in.getExtras();
                                            inten.getExtras();
                                            String MPO_CODE = bundle.getString("MPO_CODE");
                                            String pc_sl_no = message;

                                            Log.w("successmessage",  "UserName" + MPO_CODE + "UserName_2" + pc_sl_no);
                                            Intent sameint = new Intent(PcBillApproval.this, PcBillApproval.class);
                                            sameint.putExtra("UserName", UserName);
                                            sameint.putExtra("UserName_2", UserName_2);
                                            sameint.putExtra("Ord_NO", message);
                                            sameint.putExtra("userName", UserName);
                                            startActivity(sameint);
                                            Log.w("Passedin DCR TO DCR",  "UserName" + UserName + "message" + message);

                                            selectedCategories5.clear();

                                        }
                                    });
                                    server.start();
                                }

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(true)
                        .setNegativeButton(android.R.string.no, null).show();






            }
        });




        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                new AlertDialog.Builder(PcBillApproval.this)
                        .setTitle(" 'CANCEL', Are you sure?")
                        .setMessage("You agree to Cancel pc bill requision ")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which) {


                                categoriesCsv = FavouriteCategoriesJsonParser5.selectedCategories5.toString();
                                categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);
                                Bundle b = getIntent().getExtras();
                                if (categoriesCsv.length() < 0) {
                                    Toast.makeText(context, "Please Conference to Cancel", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Thread server = new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            JSONParser jsonParser = new JSONParser();
                                            Bundle b = getIntent().getExtras();
                                            final String userName = b.getString("UserName");
                                            final String UserName_2 = b.getString("UserName_2");

                                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                                            params.add(new BasicNameValuePair("categoriesCsv", categoriesCsv));
                                            params.add(new BasicNameValuePair("UserName", UserName));
                                            Log.e("categoriesCsv",categoriesCsv);
                                            Log.e("UserName",UserName);
                                            Log.e("categoriesCsv",categoriesCsv);
                                            JSONObject json = jsonParser.makeHttpRequest(pc_cancel_submit, "POST", params);

                                            try {
                                                success = json.getInt(TAG_SUCCESS);
                                                message = json.getString(TAG_MESSAGE);
                                                Log.w("please wait TRY ...." + message, json.toString());
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                                Log.w("Please wait ...." + message, json.toString());
                                            }


                                            Intent in = getIntent();
                                            Intent inten = getIntent();
                                            Bundle bundle = in.getExtras();
                                            inten.getExtras();
                                            String MPO_CODE = bundle.getString("MPO_CODE");
                                            String pc_sl_no = message;

                                            Log.w("successmessage",  "UserName" + MPO_CODE + "UserName_2" + pc_sl_no);
                                            Intent sameint = new Intent(PcBillApproval.this, PcBillApproval.class);
                                            sameint.putExtra("UserName", UserName);
                                            sameint.putExtra("UserName_2", UserName_2);
                                            sameint.putExtra("Ord_NO", message);
                                            sameint.putExtra("userName", UserName);
                                            startActivity(sameint);
                                            Log.w("Passed in DCR TO DCR",  "UserName" + UserName + "message" + message);

                                            selectedCategories5.clear();

                                        }
                                    });
                                    server.start();
                                }


                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(true)
                        .setNegativeButton(android.R.string.no, null).show();
















            }
        });








    }





    public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait...");
            dialog.setMessage("Loading PC Bill!");
            dialog.show();
            array_list = new ArrayList<Category4>();

            my_val=UserName;
            categoryJsonParser = new FavouriteCategoriesJsonParser5();

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
            final CategoryAdapter5 CategoryAdapter5 = new CategoryAdapter5(context, R.layout.row_category_4, array_list);
            mListViewBooks.setAdapter(CategoryAdapter5);
            super.onPostExecute(s);
            dialog.dismiss();
        }

    }



}
















