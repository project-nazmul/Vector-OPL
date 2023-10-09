package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class ManagersSalesFollowup extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<CategoryNew> categoriesList;
    public ProgressDialog pDialog;
    ListView productListView;
    Button submitBtn;
    public int success;
    public String message, ord_no;
    TextView fromdate, todate;
    public TextView totqty, totval, mpo_code, mpo_name;
    public String userName_1, userName, active_string, act_desiredString;
    public String from_date, to_date;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    private android.widget.Spinner cust;
    public String p_code, asm_flag, sm_flag, gm_flag;
    private ArrayList<Customer> customerlist;
    public String query_code;
    Button back_btn, view_btn;
    LinearLayout ln;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate,current_validation, UserName_2;
    Calendar myCalendar, myCalendar1;
    DatePickerDialog.OnDateSetListener date_form, date_to;
    TextView achivement, sproduct_name, sqnty1, ssellvelue, gval;
    int listsize;
    String listvalue;
    AutoCompleteTextView actv;
    ArrayList<Customer> mpodcrlist;
    private final String URL_PRODUCT_VIEW = BASE_URL+"salesfollowupreport/RMSalesFollowup.php";
    Bundle b;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpoachvfollowup);

        initViews();
        calendarInit();
        screenShotProtect();

        if (asm_flag.equals("Y")) {
            mpo_code.setText("Region\nCode");
            mpo_name.setText("Region\nName");
            mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);

            productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    query_code = (String) productListView.getAdapter().getItem(position);
                    Intent i = new Intent(ManagersSalesFollowup.this, AMwiseProductSale2.class);
                    i.putExtra("sm_flag", "N");
                    i.putExtra("asm_flag", "N");
                    i.putExtra("gm_flag", "N");
                    i.putExtra("am_flag", "Y");
                    i.putExtra("UserName", query_code);
                    i.putExtra("query_code", query_code);
                    i.putExtra("UserName", query_code);
                    i.putExtra("UserName_1", query_code);
                    i.putExtra("UserName_2", query_code);
                    i.putExtra("userName", query_code);
                    i.putExtra("UserName", query_code);
                    i.putExtra("query_code", query_code);
                    i.putExtra("to_date", todate.getText().toString());
                    i.putExtra("from_date", fromdate.getText().toString());
                    startActivity(i);
                }
            });
        }
        else if (sm_flag.equals("Y")) {
            mpo_code.setText("Zone\nCode");
            mpo_name.setText("Zone\nName");
            mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String sm_code = (String) productListView.getAdapter().getItem(position);
                    Log.i("Selected Item in list", sm_code);

                    Intent i = new Intent(ManagersSalesFollowup.this, ManagersSalesFollowup2.class);
                    i.putExtra("sm_flag", "N");
                    i.putExtra("asm_flag", "Y");
                    i.putExtra("gm_flag", "N");
                    i.putExtra("UserName", sm_code);
                    i.putExtra("query_code", sm_code);
                    i.putExtra("to_date", todate.getText().toString());
                    i.putExtra("from_date", fromdate.getText().toString());
                    startActivity(i);
                }
            });
        }
        else if (gm_flag.equals("Y")) {
            mpo_code.setText("Division\nCode");
            mpo_name.setText("Division\nName");
            mpo_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            sproduct_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            sqnty1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            ssellvelue.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            achivement.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            gval.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            query_code = b.getString("query_code");

            productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String sm_code = (String) productListView.getAdapter().getItem(position);
                    Intent i = new Intent(ManagersSalesFollowup.this, ManagersSalesFollowup2.class);
                    i.putExtra("sm_flag", "Y");
                    i.putExtra("asm_flag", "N");
                    i.putExtra("gm_flag", "N");
                    i.putExtra("to_date", todate.getText().toString());
                    i.putExtra("from_date", fromdate.getText().toString());
                    i.putExtra("UserName", sm_code);
                    i.putExtra("query_code", sm_code);
                    startActivity(i);
                }
            });
        }

        new GetCategories().execute();
        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!actv.getText().toString().equals("")) {
                    String selectedcustomer = actv.getText().toString();
                    cust.setTag(selectedcustomer);
                }
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
                    if (inputorder.contains("//")) {
                        String arr[] = inputorder.split("//");
                        String product_name = arr[0].trim();
                        String product_code = arr[1].trim();
                        p_code = product_code;
                        actv.setText(product_name);
                    } else {
                        //ded.setText("Select Date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {

            }
        });

        myCalendar = Calendar.getInstance();
        date_form = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                fromdate.setTextColor(Color.BLACK);
                fromdate.setText("");
                fromdate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        fromdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ManagersSalesFollowup.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                categoriesList.clear();
                productListView.setAdapter(null);
            }
        });

        myCalendar1 = Calendar.getInstance();
        date_to = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                todate.setTextColor(Color.BLACK);
                todate.setText("");
                todate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        todate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ManagersSalesFollowup.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
                categoriesList.clear();
                productListView.setAdapter(null);
            }
        });

        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String UserName_1 = b.getString("userName_1");
                            String UserName_2 = b.getString("userName_2");
                            if (asm_flag.equals("Y")) {
                                Intent i = new Intent(ManagersSalesFollowup.this, AssistantManagerDashboard.class);
                                i.putExtra("UserName", AssistantManagerDashboard.globalASMCode);
                                i.putExtra("new_version", AssistantManagerDashboard.new_version);
                                i.putExtra("UserName_2", AssistantManagerDashboard.globalZONECode);
                                i.putExtra("message_3", AssistantManagerDashboard.message_3);
                                i.putExtra("password", AssistantManagerDashboard.password);
                                i.putExtra("ff_type", AssistantManagerDashboard.ff_type);
                                i.putExtra("vector_version", R.string.vector_version);
                                i.putExtra("emp_code", AssistantManagerDashboard.globalempCode);
                                i.putExtra("emp_name", AssistantManagerDashboard.globalempName);
                                startActivity(i);
                            } else if (sm_flag.equals("Y")) {
                                Intent i = new Intent(ManagersSalesFollowup.this, SalesManagerDashboard.class);
                                i.putExtra("UserName", SalesManagerDashboard.globalSMCode);
                                i.putExtra("new_version", SalesManagerDashboard.new_version);
                                i.putExtra("UserName_2", SalesManagerDashboard.globalDivisionCode);
                                i.putExtra("message_3", SalesManagerDashboard.message_3);
                                i.putExtra("password", SalesManagerDashboard.password);
                                i.putExtra("ff_type", SalesManagerDashboard.ff_type);
                                i.putExtra("vector_version", R.string.vector_version);
                                i.putExtra("emp_code", SalesManagerDashboard.globalempCode);
                                i.putExtra("emp_name", SalesManagerDashboard.globalempName);
                                startActivity(i);
                            } else if (gm_flag.equals("Y")) {
                                Intent i = new Intent(ManagersSalesFollowup.this, GMDashboard1.class);
                                i.putExtra("UserName", GMDashboard1.globalAdmin);
                                i.putExtra("new_version", GMDashboard1.new_version);
                                i.putExtra("UserName_2", GMDashboard1.globalAdminDtl);
                                i.putExtra("message_3", GMDashboard1.message_3);
                                i.putExtra("password", GMDashboard1.password);
                                i.putExtra("ff_type", GMDashboard1.ff_type);
                                i.putExtra("vector_version", R.string.vector_version);
                                i.putExtra("emp_code", GMDashboard1.globalempCode);
                                i.putExtra("emp_name", GMDashboard1.globalempName);
                                startActivity(i);
                            }
                            // finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });

        submitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1 = fromdate.getText().toString();
                    String todate1 = todate.getText().toString();
                    if (fromdate1.isEmpty() || (fromdate1.equals("From Date")) || (fromdate1.equals("From Date is required"))) {
                        fromdate.setText("From Date is required");
                        fromdate.setTextColor(Color.RED);
                    } else if (todate1.isEmpty() || (todate1.equals("To Date")) || (todate1.equals("To Date is required"))) {
                        todate.setText("To Date is required");
                        todate.setTextColor(Color.RED);
                    } else {
                        categoriesList.clear();
                        new GetCategories().execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ln.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void screenShotProtect() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    private void restructureValidation() {
        new Thread() {
            public void run() {
                ManagersSalesFollowup.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "Due to restructure you need to Select Date range between January to September, 2021 and October to December 2021";
                        Toasty.success(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    private void calendarInit() {
        c_todate = Calendar.getInstance();
        dftodate = new SimpleDateFormat("dd/MM/yyyy");
        current_todate = dftodate.format(c_todate.getTime());
        todate.setText(current_todate);
        c_fromdate = Calendar.getInstance();

        SimpleDateFormat myvalidation = new SimpleDateFormat("01/10/2021");
        current_validation = myvalidation.format(c_fromdate.getTime());
        dffromdate = new SimpleDateFormat("01/MM/yyyy");
        current_fromdate = dffromdate.format(c_fromdate.getTime());
        fromdate.setText(current_fromdate);
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = findViewById(R.id.pListView);
        back_btn = findViewById(R.id.backbt);
        view_btn = findViewById(R.id.view);
        submitBtn = findViewById(R.id.submitBtn_manager);
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        mpo_code = findViewById(R.id.mpo_code);
        mpo_name = findViewById(R.id.mpo_name);
        sproduct_name = findViewById(R.id.sproduct_name);
        sqnty1 = findViewById(R.id.sqnty1);
        ssellvelue = findViewById(R.id.ssellvelue);
        gval = findViewById(R.id.gval);
        achivement = findViewById(R.id.achivement_sales_admin);
        cust = findViewById(R.id.dcrlist);
        mpodcrlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener(this);
        actv = findViewById(R.id.autoCompleteTextView1);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 "); // &#xf060
        ln = findViewById(R.id.totalshow);
        totqty = findViewById(R.id.totalsellquantity);
        totval = findViewById(R.id.totalsellvalue);
        listsize = productListView.getChildCount();
        listvalue = productListView.toString();
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        PROD_VAT = new ArrayList<String>();
        categoriesList = new ArrayList<CategoryNew>();
        b = getIntent().getExtras();
        userName = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        asm_flag = b.getString("asm_flag");
        sm_flag = b.getString("sm_flag");
        gm_flag = b.getString("gm_flag");
        customerlist = new ArrayList<Customer>();
        cust.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    }

    private void producpopulatespinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            description.add(categoriesList.get(i).getId());
        }
    }

    public void finishActivity(View v) {
        finish();
    }

    class Spinner {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner() {
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> achv = new ArrayList<String>();
            ArrayList<String> mpo_code = new ArrayList<String>();
            ArrayList<String> growth_val = new ArrayList<String>();
            ArrayList<String> ff_names = new ArrayList<String>();
            ArrayList<String> mon_growth = new ArrayList<String>();
            ArrayList<String> cum_growth = new ArrayList<String>();
            String quantity, monGrowth, cumGrowth;
            float achievment;
            String prod_rate, prod_vat, sellvalue;
            String mpo, growth, ff_name;

            for (int i = 0; i < categoriesList.size(); i++) {
                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());
                quantity = categoriesList.get(i).getQuantity();
                prod_rate = String.valueOf((categoriesList.get(i).getPROD_RATE()));
                prod_vat = String.valueOf((categoriesList.get(i).getPROD_VAT()));
                mpo = String.valueOf(categoriesList.get(i).getPPM_CODE());
                growth = String.valueOf(categoriesList.get(i).getP_CODE());
                ff_name = String.valueOf(categoriesList.get(i).getFF_NAME());
                monGrowth = String.valueOf(categoriesList.get(i).getMON_GROWTH());
                cumGrowth = String.valueOf(categoriesList.get(i).getCUM_GROWTH());
                value.add(prod_rate);
                achv.add(prod_vat);
                mpo_code.add(mpo);
                ff_names.add(ff_name);
                growth_val.add(growth);
                mon_growth.add(monGrowth);
                cum_growth.add(cumGrowth);
            }
            MPOwiseAchvfollowupAdapter2 adapter = new MPOwiseAchvfollowupAdapter2(ManagersSalesFollowup.this, lables, quanty,
                    value, achv, mpo_code, ff_names, growth_val, mon_growth, cum_growth);
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
        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManagersSalesFollowup.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String UserName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("p_code", p_code));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            params.add(new BasicNameValuePair("asm_flag", asm_flag));
            params.add(new BasicNameValuePair("sm_flag", sm_flag));
            params.add(new BasicNameValuePair("gm_flag", gm_flag));
            com.opl.pharmavector.ServiceHandler jsonParser = new com.opl.pharmavector.ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, com.opl.pharmavector.ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray categories = jsonObj.getJSONArray("categories");
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject catObj = (JSONObject) categories.get(i);
                        CategoryNew cat = new CategoryNew(
                                catObj.getString("sl"),
                                catObj.getString("id"),
                                catObj.getString("name"),
                                catObj.getString("quantity"),
                                catObj.getString("PROD_RATE"),
                                catObj.getString("PROD_VAT"),
                                catObj.getString("PPM_CODE"),
                                catObj.getString("P_CODE"),
                                catObj.getString("FF_NAME"),
                                catObj.getString("MON_GROWTH"),
                                catObj.getString("CUM_GROWTH")
                        );
                        categoriesList.add(cat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ManagersSalesFollowup.this, "Nothing To Display", Toast.LENGTH_SHORT).show();
                Toast.makeText(ManagersSalesFollowup.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            ManagersSalesFollowup.Spinner sp = new ManagersSalesFollowup.Spinner();
            sp.populateSpinner();
            popSpinner();
            totqty.setText("");
            totval.setText("");
        }
    }

    @Override
    public void onClick(View v) {

    }

    protected void onPostExecute() {

    }

    private void view() {
        Intent i = new Intent(ManagersSalesFollowup.this, com.opl.pharmavector.Report.class);
        startActivity(i);
        finish();
    }
}







