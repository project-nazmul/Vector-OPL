//PCBill

package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.lang.Runnable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nativecss.NativeCSS;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

public class PCBill extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    // array list for spinner adapter
    private ArrayList<com.opl.pharmavector.Category3> categoriesList;
    private SessionManager session;
    private ArrayList<com.opl.pharmavector.Category6> categoriesList2;

    public ProgressDialog pDialog;
    ListView productListView;
    Button submit, submitBtn;
    // private EditText current_qnty;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no, supervisor, subordinate;
    TextView date2, ded, fromdate, todate, rname;
    int textlength = 0;
    public TextView totqty, totval;
    //public android.widget.Spinner ordspin;
    public String userName_1, userName, UserName_2, active_string, act_desiredString, user, sm_flag, sm_code, user_flag;
    public String from_date, to_date, pc_conference_serial, pc_conference_flag, pc_conference_flag_1, pc_conference_iou;
    JSONParser jsonParser;
    List<NameValuePair> params;


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

    private String URL_PRODUCT_VIEW = BASE_URL+"pcconference/mpo_pc_conference_bill_followup_new.php";
    public String participent, no_pc_inhouse, no_pc_rmp, no_of_dcc;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbill);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = (ListView) findViewById(R.id.pListView);
        Button back_btn = (Button) findViewById(R.id.backbt);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        int listsize = productListView.getChildCount();
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


        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.successmsg);

        LinearLayout slistLayout = (LinearLayout) findViewById(R.id.slistLayout);

        TextView succ_msg = (TextView) findViewById(R.id.succ_msg);
        TextView ordno = (TextView) findViewById(R.id.ordno);

        final String ordernumber = b.getString("Ord_NO");


        if (ordernumber == null) {
            mainlayout.setVisibility(LinearLayout.GONE);
        } else {

            slistLayout.setVisibility(LinearLayout.GONE);

            succ_msg.setTextSize(12);
            succ_msg.setText("Submitted.");
            succ_msg.setTextColor(Color.BLACK);
            ordno.setText("PC Bill for Conference serial number " + "\t" + ordernumber);
            ordno.setTextSize(12);
            ordno.setTextColor(Color.BLACK);
        }


        if (user_flag.equals("M")) {

            supervisor = "Area Manager";

        } else if (user_flag.equals("A")) {

            supervisor = "Regional Manager";


        } else if (user_flag.equals("R")) {

            supervisor = "Assistant Manager";

            subordinate = "Area Manager.";
        } else if (user_flag.equals("ASM")) {

            supervisor = "Sales Manager";
            subordinate = "Regional Manager.";
        } else if (user_flag.equals("SM")) {

            supervisor = "General Manager";
            subordinate = "Assistant Manager.";

        }


        //Toast.makeText(PCBill.this, userName, Toast.LENGTH_LONG).show();

        todate = (TextView) findViewById(R.id.todate);
        rname = (TextView) findViewById(R.id.rm_code);

        todate.setText("PC Bill Entry - \t" + userName);

        rname.setText("Conference\nDate");


        mpodcrlist = new ArrayList<Customer>();
        dateextendlist = new ArrayList<com.opl.pharmavector.Customer>();
        mpodonedcr = new ArrayList<com.opl.pharmavector.Customer>();
        mporeqdcr = new ArrayList<com.opl.pharmavector.Customer>();


        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String sm_code = (String) productListView.getAdapter().getItem(arg2);
                Intent i = new Intent(PCBill.this, PCBillSubmit.class);
                String second_split[] = sm_code.split("/");
                pc_conference_serial = second_split[0].trim();
                String second = second_split[1].trim();
                String second_split1[] = sm_code.split("#");
                pc_conference_flag_1 = second_split1[1].trim();

                String pc_conference_iou_main = second_split1[0].trim();
                String subordinate_flag = pc_conference_flag_1;
                String second_split3[] = pc_conference_iou_main.split("/");
                pc_conference_iou = second_split3[1].trim();



                i.putExtra("UserName", userName);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", user);
                i.putExtra("user_flag", user_flag);
                i.putExtra("pc_conference_serial", pc_conference_serial);
                i.putExtra("pc_conference_iou", pc_conference_iou);
                i.putExtra("conf_type_val", subordinate_flag);
                Log.e("pc_conference_serial",pc_conference_serial);
                Log.e("pc_conference_iou",pc_conference_iou);
                Log.e("conf_type_val",subordinate_flag);
                Log.e("user_flag",user_flag);
                Log.e("UserName_2",user);
                Log.e("UserName", userName);
             //   Log.e("userName_1", userName_1);
                Log.e("UserName", userName);

                 startActivity(i);


            }
        });


        new GetCategories().execute();

        session = new SessionManager(getApplicationContext());

        back_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try {


                    Intent i = new Intent(PCBill.this, PCDashboard.class);
                    i.putExtra("UserName", userName);
                    i.putExtra("new_version", userName);
                    i.putExtra("UserName_1", UserName_2);
                    i.putExtra("UserName_2", UserName_2);

                    i.putExtra("userName", userName);
                    i.putExtra("new_version", userName);
                    i.putExtra("userName_1", UserName_2);
                    i.putExtra("userName_2", UserName_2);
                    i.putExtra("user_flag", user_flag);

                    startActivity(i);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


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

                sl.add(categoriesList2.get(i).getsl());
                lables.add(categoriesList2.get(i).getName());
                p_ids.add(categoriesList2.get(i).getId());
                quanty.add(categoriesList2.get(i).getQuantity());
                prod_rate_1 = categoriesList2.get(i).getPROD_RATE();
                value.add(prod_rate_1);
                prod_vat_1 = categoriesList2.get(i).getPROD_VAT();
                value4.add(prod_vat_1);
                no_of_dcc = prod_vat_1;
                prod_vat_2 = categoriesList2.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);
                no_pc_rmp = prod_vat_2;
                prod_vat_3 = categoriesList2.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);
                no_pc_inhouse = prod_vat_3;
                prod_vat_4 = categoriesList2.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);
                participent = prod_vat_4;

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


                prod_vat_11 = categoriesList2.get(i).getPROD_VAT_11();
                value14.add(prod_vat_11);


                prod_vat_12 = categoriesList2.get(i).getPROD_VAT_12();
                value15.add(prod_vat_12);


                prod_vat_13 = categoriesList2.get(i).getPROD_VAT_13();
                value16.add(prod_vat_13);


                prod_vat_15 = categoriesList2.get(i).getPROD_VAT_14();
                value18.add(prod_vat_15);


                prod_vat_16 = categoriesList2.get(i).getPROD_VAT_15();
                value19.add(prod_vat_16);


                prod_vat_17 = categoriesList2.get(i).getPROD_VAT_16();
                value20.add(prod_vat_17);


            }


            PCBillFollowupAdapter adapter = new PCBillFollowupAdapter(PCBill.this, sl, lables, quanty, value, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13,
                    value14, value15, value16, value17, value18, value19, value20);


            productListView.setAdapter(adapter);
        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
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
            pDialog = new ProgressDialog(PCBill.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("user_flag", user_flag));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, ServiceHandler.POST, params);
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
                Toast.makeText(PCBill.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(PCBill.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            PCBill.Spinner sp = new PCBill.Spinner();
            sp.populateSpinner();
            popSpinner();


        }
    }


    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }


    private void view() {
        Intent i = new Intent(PCBill.this, Report.class);
        startActivity(i);
        finish();

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void logoutUser() {
        session.setLogin(false);
        // session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(PCBill.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();

    }

}

