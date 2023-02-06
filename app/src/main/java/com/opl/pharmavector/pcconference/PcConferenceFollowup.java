package com.opl.pharmavector.pcconference;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.opl.pharmavector.Category3;
import com.opl.pharmavector.Category6;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.PCDashboard;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.SessionManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PcConferenceFollowup extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<com.opl.pharmavector.Category3> categoriesList;
    private SessionManager session;
    private ArrayList<com.opl.pharmavector.Category6> categoriesList2;
    public ProgressDialog pDialog;
    ListView productListView;
    int listsize;
    Button back_btn;
    public int success;
    public String message, ord_no, supervisor, subordinate;
    TextView  todate, rname, mTextView;
    public TextView totqty, totval;
    public String userName_1, userName, UserName_2, active_string, act_desiredString, user, sm_flag, sm_code, user_flag;
    public String from_date, to_date, pc_conference_serial, pc_conference_flag, pc_conference_flag_1;
    public static ArrayList<String> sl;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PROD_VAT_2;
    public static ArrayList<String> PROD_VAT_3;
    public static ArrayList<String> PROD_VAT_4;
    public static ArrayList<String> PROD_VAT_5;
    public static ArrayList<String> PROD_VAT_6;
    public static ArrayList<String> PROD_VAT_7;
    public static ArrayList<String> PROD_VAT_8;
    public static ArrayList<String> PROD_VAT_9;
    public static ArrayList<String> PROD_VAT_10;
    public static ArrayList<String> PROD_VAT_11;
    public static ArrayList<String> PROD_VAT_12;
    public static ArrayList<String> PROD_VAT_13;
    private android.widget.Spinner count_dcr;
    private ArrayList<com.opl.pharmavector.Customer> dateextendlist;
    private ArrayList<com.opl.pharmavector.Customer> mpodonedcr;
    private ArrayList<com.opl.pharmavector.Customer> mporeqdcr;
    public String get_ext_dt;
    public String admin_flag = "Y";
    private ArrayList<Customer> mpodcrlist;
    private ArrayList<String> array_sort = new ArrayList<String>();
    private String URL_PRODUCT_VIEW = BASE_URL+"pcconference/mpo_pc_conference/mpo_pc_conference_followup.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_conference_followup);
        initViews();

        productListView.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            String sm_code = (String) productListView.getAdapter().getItem(arg2);
            Intent i = new Intent(PcConferenceFollowup.this, PcConferenceEdit.class);
            String[] second_split = sm_code.split("/");
            pc_conference_serial = second_split[0].trim();
            String second = second_split[1].trim();
            String[] second_split1 = sm_code.split("#");
            pc_conference_flag_1 = second_split1[1].trim();
            pc_conference_flag = second_split1[0].trim();
            String subordinate_flag = pc_conference_flag_1;
            String[] second_split3 = pc_conference_flag.split("/");
            String pc_sl_no = second_split3[0].trim();
            String pc_boss_flag = second_split3[1].trim();

            if (subordinate_flag.equals("N")) {
                LayoutInflater li = getLayoutInflater();
                View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                Toast toast = new Toast(getApplicationContext());
                TextView text = layout.findViewById(R.id.text);
                text.setText("You can not edit this conference as it is not forwarded to you by " + subordinate);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout); //setting the view of custom toast layout
                toast.show();
            } else if (pc_boss_flag.equals("Y")) {
                LayoutInflater li = getLayoutInflater();
                View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                Toast toast = new Toast(getApplicationContext());
                TextView text = layout.findViewById(R.id.text);
                text.setText("This conference is already approved by " + supervisor + ".You can not edit now.");
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout); //setting the view of custom toast layout
                toast.show();
            } else if (subordinate_flag.equals("C")) {
                LayoutInflater li = getLayoutInflater();
                View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                Toast toast = new Toast(getApplicationContext());
                TextView text = layout.findViewById(R.id.text);
                text.setText("This conference is cancelled by " + supervisor + ".You can not edit now.");
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
                //Toast.makeText(getBaseContext(), "This conference is cancelled by "+ supervisor +".You can not edit now.", Toast.LENGTH_LONG).show();
            } else if (pc_boss_flag.equals("X")) {
                LayoutInflater li = getLayoutInflater();
                View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                Toast toast = new Toast(getApplicationContext());
                TextView text = layout.findViewById(R.id.text);
                text.setText("This Conference is finally approved by General Manager.Can not edit this now.");
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout); //setting the view of custom toast layout
                toast.show();
            } else {
                i.putExtra("UserName", userName);
                i.putExtra("userName_1", userName_1);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", user);
                i.putExtra("user_flag", user_flag);
                i.putExtra("pc_conference_serial", pc_conference_serial);
                startActivity(i);
            }
        });

        new GetCategories().execute();
        session = new SessionManager(getApplicationContext());
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent i = new Intent(PcConferenceFollowup.this, PCDashboard.class);
                    i.putExtra("UserName", userName);
                    i.putExtra("new_version", userName);
                    i.putExtra("UserName_1", UserName_2);
                    i.putExtra("UserName_2", UserName_2);
                    i.putExtra("userName", userName);
                    i.putExtra("new_version", userName);
                    i.putExtra("userName_1", UserName_2);
                    i.putExtra("userName_2", UserName_2);
                    i.putExtra("user_flag", user_flag);
                    Log.d("userName", "UserName" + userName);
                    Log.d("UserName_1", "UserName_1" + userName);
                    Log.d("UserName_2", "UserName_2" + UserName_2);
                    //startActivity(i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = findViewById(R.id.pListView);
        back_btn = findViewById(R.id.backbt);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        listsize = productListView.getChildCount();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        categoriesList = new ArrayList<Category3>();
        categoriesList2 = new ArrayList<Category6>();
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        user = b.getString("UserName_2");
        UserName_2 = b.getString("UserName_2");
        user_flag = b.getString("user_flag");
        mTextView = findViewById(R.id.aprv_head);
        mTextView.setPaintFlags(mTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        switch (user_flag) {
            case "M":
                supervisor = "Area Manager";
                break;
            case "A":
                supervisor = "Regional Manager";
                break;
            case "R":
                supervisor = "Assistant Manager";
                subordinate = "Area Manager.";
                break;
            case "ASM":
                supervisor = "Sales Manager";
                subordinate = "Regional Manager.";
                break;
            case "SM":
                supervisor = "General Manager";
                subordinate = "Assistant Manager.";
                break;
        }
        todate = findViewById(R.id.todate);
        rname = findViewById(R.id.rm_code);
        todate.setText("PC conference followup\t" + userName);
        rname.setText("Conference\nDate");
        mpodcrlist = new ArrayList<Customer>();
        dateextendlist = new ArrayList<com.opl.pharmavector.Customer>();
        mpodonedcr = new ArrayList<com.opl.pharmavector.Customer>();
        mporeqdcr = new ArrayList<com.opl.pharmavector.Customer>();
    }

    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList2.size(); i++) {
            description.add(categoriesList2.get(i).getId());
        }
    }

    public void finishActivity(View v) {
        finish();
    }

    class Spinner {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();
            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            ArrayList<String> value13 = new ArrayList<String>();
            ArrayList<String> value14 = new ArrayList<String>();
            ArrayList<String> value15 = new ArrayList<String>();
            ArrayList<String> value16 = new ArrayList<String>();
            ArrayList<String> value17 = new ArrayList<String>();
            ArrayList<String> value18 = new ArrayList<String>();
            ArrayList<String> value19 = new ArrayList<String>();
            ArrayList<String> value20 = new ArrayList<String>();

            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1, prod_vat_2, prod_vat_3, prod_vat_4, prod_vat_5, prod_vat_6, prod_vat_7, prod_vat_8, prod_vat_9, prod_vat_10, prod_vat_11, prod_vat_12, prod_vat_13,
                    prod_vat_14, prod_vat_15, prod_vat_16, prod_vat_17,
                    sellvalue_2, sellvalue_3;

            for (int i = 0; i < categoriesList2.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList2.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList2.get(i).getsl());
                sl.add(categoriesList2.get(i).getsl());
                lables.add(categoriesList2.get(i).getName());
                p_ids.add(categoriesList2.get(i).getId());
                quanty.add(categoriesList2.get(i).getQuantity());
                prod_rate_1 = categoriesList2.get(i).getPROD_RATE();
                value.add(prod_rate_1);
                prod_vat_1 = categoriesList2.get(i).getPROD_VAT();
                value4.add(prod_vat_1);
                prod_vat_2 = categoriesList2.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);
                prod_vat_3 = categoriesList2.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);
                prod_vat_4 = categoriesList2.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);
                prod_vat_5 = categoriesList2.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);
                prod_vat_6 = categoriesList2.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);
                prod_vat_7 = categoriesList2.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);
                prod_vat_8 = categoriesList2.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);
                prod_vat_9 = categoriesList2.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);
                prod_vat_10 = categoriesList2.get(i).getPROD_VAT_10();
                value13.add(prod_vat_10);
                Log.w("FOLLOWUPvalue14", prod_vat_10);
                prod_vat_11 = categoriesList2.get(i).getPROD_VAT_11();
                value14.add(prod_vat_11);
                Log.w("FOLLOWUPvalue15", prod_vat_11);
                prod_vat_12 = categoriesList2.get(i).getPROD_VAT_12();
                value15.add(prod_vat_12);
                Log.w("FOLLOWUPvalue16", prod_vat_12);
                prod_vat_13 = categoriesList2.get(i).getPROD_VAT_13();
                value16.add(prod_vat_13);
                Log.w("FOLLOWUPvalue17", prod_vat_13);
                prod_vat_15 = categoriesList2.get(i).getPROD_VAT_14();
                value18.add(prod_vat_15);
                Log.w("mpocode-conference", prod_vat_15);
                prod_vat_16 = categoriesList2.get(i).getPROD_VAT_15();
                value19.add(prod_vat_16);
                Log.w("mpocode-conference", prod_vat_16);
                prod_vat_17 = categoriesList2.get(i).getPROD_VAT_16();
                value20.add(prod_vat_17);
                Log.w("mpocode-conference", prod_vat_17);
            }
            PcConferenceFollowupAdapter adapter = new PcConferenceFollowupAdapter(PcConferenceFollowup.this, sl, lables, quanty, value, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13,
                    value14, value15, value16, value17, value18, value19, value20);
            productListView.setAdapter(adapter);
        }

        private float round(float x, int i) {
            return 0;
        }

        public String getTotalQ() {
            return TotalQ;
        }

        public String getTotalV() {
            return TotalV;
        }
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PcConferenceFollowup.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("user_flag", user_flag));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            com.opl.pharmavector.Category6 cat3 = new com.opl.pharmavector.Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11"),
                                    catObj.getString("PROD_VAT_12"),
                                    catObj.getString("PROD_VAT_13"),
                                    catObj.getString("PROD_VAT_14"),
                                    catObj.getString("PROD_VAT_15"),
                                    catObj.getString("PROD_VAT_16")
                            );
                            categoriesList2.add(cat3);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(PcConferenceFollowup.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(PcConferenceFollowup.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            PcConferenceFollowup.Spinner sp = new PcConferenceFollowup.Spinner();
            sp.populateSpinner();
            popSpinner();
        }
    }

    @Override
    public void onClick(View v) {}

    protected void onPostExecute() {}
}

