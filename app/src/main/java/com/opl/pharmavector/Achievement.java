package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opl.pharmavector.R;
import com.opl.pharmavector.exam.ExamDashboard;
import com.opl.pharmavector.exam.QuizActivity;
import com.opl.pharmavector.util.KeyboardUtils;
import com.opl.pharmavector.util.NetInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Achievement extends Activity implements OnItemSelectedListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_growth = "growth";
    private Button logout;
    TextView user_show;
    public Button next, back;
    public EditText osi, op, od, dateResult, ref;
    public String userName_1, userName;
    protected Handler handler;
    public ProgressBar bar_1, bar_2, bar_3, bar_4;
    public EditText date_ext, val_mpo, submit_mpo, req_mpo, val_growth;
    TextView myTextView;
    Button btn_1, btn_2, btn_3, btn_4, submitBtn;
    String UserName_2, invoice, tar, achivement, growth, Ord_NO, msg, number;
    double d, e, amount, amount2;
    int i, j;
    DecimalFormat formatter, INVOICE_VALUE;
    String TARGET_VALUE, number2, INVOICE;
    private ArrayList<Customer> customerlist;
    private Spinner cust;
    private final String URL_CUSOTMER = BASE_URL + "mposalesreports/get_mpoList.php";
    LinearLayout generalteamlayout;
    AutoCompleteTextView actv;
    private final String URL_SEGMENT_Achievement = BASE_URL + "mposalesreports/Achievement2.php";
    public int success;
    public String message, ord_no, target_data, searchString, select_party;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement);
        initViews();

        logout.setOnClickListener(v -> finish());
        submitBtn.setOnClickListener(v -> {
            if (!NetInfo.isOnline(getBaseContext())) {

            } else {
                new callserver().execute();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = findViewById(R.id.user_show);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        val_mpo = findViewById(R.id.val_mpo);
        submit_mpo = findViewById(R.id.submit_mpo);
        req_mpo = findViewById(R.id.req_mpo);
        val_growth = findViewById(R.id.val_growth);
        bar_1 = findViewById(R.id.bar_1);
        bar_2 = findViewById(R.id.bar_2);
        bar_3 = findViewById(R.id.bar_3);
        bar_4 = findViewById(R.id.bar_4);
        submitBtn = findViewById(R.id.submitBtn);
        myTextView = findViewById(R.id.user_show1);
        Bundle b = getIntent().getExtras();

        if (b.isEmpty()) {
            String userName = "";
            myTextView.setText(Dashboard.globalmpocode);
        } else {
            userName = b.getString("UserName");
            userName_1 = b.getString("userName_1");
            UserName_2 = b.getString("UserName_2");
            invoice = b.getString("target");
            tar = b.getString("invoice");
            achivement = b.getString("achivement");
            growth = b.getString("growth");
            Ord_NO = b.getString("Ord_NO");
            msg = b.getString("UserName_2");
            myTextView.setText(Dashboard.globalterritorycode + Dashboard.globalmpocode);

            d = Double.parseDouble(tar);
            i = (int) d;
            e = Double.parseDouble(invoice);
            j = (int) e;
            number = tar;
            amount = Double.parseDouble(number);
            formatter = new DecimalFormat("#,##,###.00");
            TARGET_VALUE = formatter.format(amount);
            number2 = invoice;
            amount2 = Double.parseDouble(number2);
            INVOICE_VALUE = new DecimalFormat("#,##,###.00");
            INVOICE = INVOICE_VALUE.format(amount2);
            val_mpo.setText(TARGET_VALUE);
            bar_1.setMax(j);
            bar_1.setProgress(i);
            submit_mpo.setText(INVOICE);
            req_mpo.setText(achivement);
            val_growth.setText(growth);
            bar_3.setMax(j);
            bar_3.setProgress(i);
            bar_3.setRotation(180);
            bar_4.setMax(100);
            bar_4.setProgress(100);
            bar_4.setRotation(180);
        }
        customerlist = new ArrayList<>();
        generalteamlayout = findViewById(R.id.generalteamlayout);
        actv = findViewById(R.id.autoCompleteTextView1);
        actv.setFocusableInTouchMode(true);
        actv.setFocusable(true);
        actv.requestFocus();
        cust = findViewById(R.id.customer);
        cust.setPrompt("Select Customer");
        String ff_type = Dashboard.ff_type;

        if (ff_type.equals("G")) {
            generalteamlayout.setVisibility(View.VISIBLE);
            new GetCategories().execute();
            customerInit();
            //actv.setText(Dashboard.globalmpocode);
        } else if (ff_type.equals("T")) {
            generalteamlayout.setVisibility(View.VISIBLE);
            new GetCategories().execute();
            customerInit();
            //actv.setText(Dashboard.globalmpocode);
        } else {
            generalteamlayout.setVisibility(View.VISIBLE);
            new GetCategories().execute();
            customerInit();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void customerInit() {
        actv.setOnClickListener(v -> {
            if (actv.getText().toString() != "") {
                String selectedcustomer = actv.getText().toString();
                cust.setTag(selectedcustomer);
            }
        });
        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv.showDropDown();
                return false;
            }
        });
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.contains("/")) {
                        Log.e("selectedsegment==>", inputorder);
                        String mpo_segment_code = inputorder.substring(inputorder.indexOf("/") + 1);
                        String[] first_split = inputorder.split("/");
                        String territory_name = first_split[0].trim();
                        actv.setText(territory_name);
                        KeyboardUtils.hideKeyboard(Achievement.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, R.layout.spinner_text_view, customer);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }

    class GetCategories extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("segment_code", Dashboard.globalmpocode));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        customerlist.add(custo);
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

    @SuppressLint("StaticFieldLeak")
    class callserver extends AsyncTask<Void, Void, Void> {
        final JSONParser jsonParser = new JSONParser();
        final List<NameValuePair> params = new ArrayList<>();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Achievement.this, "Calculating Sales, Target, Achievment and Growth", "Wait....", true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            params.add(new BasicNameValuePair("segment_code", actv.getText().toString()));
            params.add(new BasicNameValuePair("mpo_code", Dashboard.globalmpocode));
            Log.e("segmentcode-->", actv.getText().toString());
            JSONObject json = jsonParser.makeHttpRequest(URL_SEGMENT_Achievement, "POST", params);

            if (json != null) {
                try {
                    success = json.getInt("success");
                    message = json.getString(TAG_MESSAGE);
                    invoice = json.getString("invoice");
                    target_data = json.getString("target");
                    achivement = json.getString("achivement");
                    growth = json.getString("growth");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            Thread backthred = new Thread(() -> {
                try {
                    if (!NetInfo.isOnline(getBaseContext())) {

                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            val_mpo.setText("");
                            submit_mpo.setText("");
                            req_mpo.setText("");
                            val_growth.setText("");
                            val_mpo.setText(invoice);
                            submit_mpo.setText(target_data);
                            req_mpo.setText(achivement);
                            val_growth.setText(growth);
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            backthred.start();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
}
