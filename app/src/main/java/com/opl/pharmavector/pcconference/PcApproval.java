package com.opl.pharmavector.pcconference;
import android.app.Dialog;
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

import com.opl.pharmavector.GMDashboard1;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.PCDashboard;
import com.opl.pharmavector.R;
import com.opl.pharmavector.adapter.CategoryAdapter4;
import com.opl.pharmavector.model.Category4;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser4;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
import static com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser3.selectedCategories3;
import static com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser4.selectedCategories4;


public class PcApproval extends AppCompatActivity {
    Context context;
    ArrayList<Category4> array_list;
    FavouriteCategoriesJsonParser4 categoryJsonParser;
    String categoriesCsv;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, my_val,statX;
    private final String pc_approval_submit = BASE_URL+"pcconference/pc_conference_approval_submit.php";
    private final String pc_cancel_submit = BASE_URL+"pcconference/pc_conference_cancel_submit.php";
    public String conference_date;
    public String market_code;
    public String market_name;
    public String venue_name;
    public String pc_rmp_val;
    public String doc_val;
    public String inhouse_val;
    public String venue_charge;
    public String miscell, miscell_bdt;
    public String impact;
    public String product_list;
    public String approval_user_role;
    public static String UserName,UserName_2;
    Button button, button2, logback;
    TextView user_show;
    int commas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_approval);
        initViews();
        checkAdmin();

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
                            if (approval_user_role.trim().equals("A")) {
                                Intent i = new Intent(PcApproval.this, PCDashboard.class);
                                i.putExtra("UserName", UserName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", approval_user_role);
                                startActivity(i);
                            } else if (approval_user_role.toString().trim().equals("R")) {
                                Intent i = new Intent(PcApproval.this, PCDashboard.class);
                                i.putExtra("UserName", UserName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", approval_user_role);
                                startActivity(i);
                            } else if (approval_user_role.toString().trim().equals("ASM")) {
                                Intent i = new Intent(PcApproval.this, PCDashboard.class);
                                i.putExtra("UserName", UserName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", approval_user_role);
                                startActivity(i);
                            } else if (approval_user_role.toString().trim().equals("SM")) {
                                Intent i = new Intent(PcApproval.this, PCDashboard.class);
                                i.putExtra("UserName", UserName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", approval_user_role);
                                startActivity(i);
                            } else if (approval_user_role.toString().trim().equals("GM")) {
                                Intent i = new Intent(PcApproval.this, GMDashboard1.class);
                                i.putExtra("UserName", GMDashboard1.globalAdmin);
                                i.putExtra("new_version", GMDashboard1.new_version);
                                i.putExtra("UserName_2", GMDashboard1.globalAdminDtl);
                                i.putExtra("message_3", GMDashboard1.message_3);
                                i.putExtra("password", GMDashboard1.password);
                                i.putExtra("ff_type", GMDashboard1.ff_type);
                                i.putExtra("vector_version", R.string.vector_version);
                                i.putExtra("emp_code", GMDashboard1.globalempCode);
                                i.putExtra("emp_name", GMDashboard1.globalempName);
                                //startActivity(i);
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
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, "Please Conference to approve ", Toast.LENGTH_SHORT).show();
                categoriesCsv = FavouriteCategoriesJsonParser4.selectedCategories4.toString();
                categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);
                Bundle b = getIntent().getExtras();

                if (categoriesCsv.length() < 0) {
                    Toast.makeText(context, "Please Select Conference to approve ", Toast.LENGTH_SHORT).show();
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
                            params.add(new BasicNameValuePair("UserName", UserName));
                            Log.e("categoriesCsv", categoriesCsv);
                            Log.e("UserName", UserName);
                            Log.e("categoriesCsv", categoriesCsv);
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

                            Log.w("successmessage", "UserName" + MPO_CODE + "UserName_2" + pc_sl_no);
                            Intent sameint = new Intent(PcApproval.this, PcApproval.class);
                            sameint.putExtra("UserName", UserName);
                            sameint.putExtra("UserName_2", UserName_2);
                            sameint.putExtra("Ord_NO", message);
                            sameint.putExtra("userName", UserName);
                            startActivity(sameint);
                            Log.w("Passed in DCR TO DCR", "UserName" + UserName + "message" + message);

                            selectedCategories4.clear();

                        }
                    });
                    server.start();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle( "ALert" )
                    .setIcon(R.drawable.ic_launcher)
                    .setMessage("Are you want to Cancel This PC?")
                    .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            dialoginterface.cancel();
                        }})
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i)
                    {
                    categoriesCsv = FavouriteCategoriesJsonParser4.selectedCategories4.toString();
                    categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);
                    Bundle b = getIntent().getExtras();
                    if (categoriesCsv.length() < 0) {
                        Toast.makeText(context, "Please Select Conference to Cancel", Toast.LENGTH_SHORT).show();
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
                                params.add(new BasicNameValuePair("UserName", UserName));
                                Log.e("categoriesCsv", categoriesCsv);
                                Log.e("UserName", UserName);
                                Log.e("categoriesCsv", categoriesCsv);
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

                                Log.w("successmessage", "UserName" + MPO_CODE + "UserName_2" + pc_sl_no);
                                Intent sameint = new Intent(PcApproval.this, PcApproval.class);
                                sameint.putExtra("UserName", UserName);
                                sameint.putExtra("UserName_2", UserName_2);
                                sameint.putExtra("Ord_NO", message);
                                sameint.putExtra("userName", UserName);
                                startActivity(sameint);
                                Log.w("Passed in DCR TO DCR", "UserName" + UserName + "message" + message);

                                selectedCategories4.clear();

                            }
                        });
                        server.start();
                    }

                    }
                }).show();//=====================================alert Status
            }
        });

    }

    private void alertView( String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle( "ALert" )
                .setIcon(R.drawable.ic_launcher)
                .setMessage(message)
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialoginterface, int i) {
                    dialoginterface.cancel();
                     }})
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        statX="1";
                    }
                }).show();
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        button = findViewById(R.id.selectCategoryButton);
        button2 = findViewById(R.id.selectCategoryButton2);
        logback = findViewById(R.id.logback);
        logback.setTypeface(fontFamily);
        logback.setText("\uf08b");
        user_show =  findViewById(R.id.user_show);
        context = this;
        Bundle b = getIntent().getExtras();
        UserName = b.getString("userName");
        UserName_2 = b.getString("UserName_2");
        user_show.setText(UserName + " " + UserName_2 + " ");

    }

    private void checkAdmin() {
        Log.e("globalAdminCode==>",UserName);
        for (int i = 0; i < UserName.length(); i++) {
            if (UserName.charAt(i) == '0') commas++;
        }

        Log.e("checkAdmin==>", String.valueOf(commas));
        switch (commas) {
            case 1:
                if (UserName.length() == 7) {
                    approval_user_role = "GM";
                } else {
                    approval_user_role = "A"; // AREA MANAGER
                }
                break;
            case 2:
                approval_user_role = "R"; ///REGIONAL
                break;
            case 3:
                approval_user_role = "ASM"; // ASM/DSM
                break;
            case 4:
                approval_user_role = "SM";
                break;
            case 5:
                approval_user_role = "GM";
                break;
            case 0:
                if (UserName.length() == 7) {
                    approval_user_role = "GM";
                } else {
                    approval_user_role = "M"; ///MPO

                }
                break;
        }
    }

    public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);
        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait...");
            dialog.setMessage("Loading PC Conference!");
            dialog.show();
            array_list = new ArrayList<Category4>();
            my_val = UserName;
            categoryJsonParser = new FavouriteCategoriesJsonParser4();
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
            final CategoryAdapter4 categoryAdapter4 = new CategoryAdapter4(context, R.layout.row_category_4, array_list);
            mListViewBooks.setAdapter(categoryAdapter4);
            super.onPostExecute(s);
            dialog.dismiss();
        }

    }


}















