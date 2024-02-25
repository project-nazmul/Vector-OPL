package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.graphics.Typeface;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
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
import com.opl.pharmavector.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Viewbycustomerreturn extends Activity implements OnClickListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    //array list for spinner adapter
    private ArrayList<Category3> categoriesList;
    private ArrayList<Category1> categoriesList1;
    private ArrayList<Category2> categoriesList2;
    ProgressDialog pDialog;
    ListView productListView;
    public int success;
    public String message, ord_no;
    public TextView totqty, totval, product_name, sqnty, velue, returnpercentage, heading;
    public android.widget.Spinner ordspin;
    TextView fromdate, todate;
    public String userName_1, userName, active_string, act_desiredString, active_string2;
    public String CurrenCustomer = "";
    public AutoCompleteTextView actv, actv2;
    Button submitBtn;
    public static ArrayList<String> p_ids;
    public static ArrayList<String> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> percentage;
    private String URL_PRODUCT_VIEW = BASE_URL+"mposalesreports/depo_report/viewbycustomerreturn.php";
    private String URL_CUSOTMER = BASE_URL+"mposalesreports/depo_report/ord_wise_customerlist.php";
    private String URL_ORD = BASE_URL+"mposalesreports/depo_report/customerwiseordno.php";
    Button back_btn, view_btn;
    LinearLayout ln;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate;
    Calendar myCalendar, myCalendar1;
    DatePickerDialog.OnDateSetListener date_form, date_to;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbycustomerreturn);

        initViews();
        calendarInit();
        new GetCategories1().execute();

        ordspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CurrenCustomer = ordspin.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        actv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrenCustomer = actv.getText().toString();
                active_string = actv.getText().toString();

                if (active_string.contains("//")) {
                    categoriesList2.clear();
                    categoriesList.clear();
                    actv.setText("");
                }
            }
        });
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                //----------------have to work----------------------------
                String inputorder = s.toString();
                CurrenCustomer = s.toString();

                if (inputorder.indexOf("//") != -1) {
                    categoriesList2.clear();
                    categoriesList.clear();
                    String arr1[] = CurrenCustomer.split("//");
                    CurrenCustomer = arr1[1];
                }

            }

        });
        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv.showDropDown();
                return false;
            }
        });


        back_btn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            finishActivity(v);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();


            }
        });
        submitBtn.setOnClickListener(new OnClickListener() {

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");

            @Override
            public void onClick(final View v) {
                try {
                    String fromdate1 = fromdate.getText().toString();
                    String todate1 = todate.getText().toString();
                    if (fromdate1.isEmpty() || (fromdate1.equals("From Date"))) {
                        fromdate.setError("From Date is required!");
                    } else if (todate1.isEmpty() || (todate1.equals("To Date"))) {
                        todate.setError("To Date is required!");
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
                // TODO Auto-generated method stub

            }
        });
    }

	private void calendarInit() {

		c_todate = Calendar.getInstance();
		dftodate = new SimpleDateFormat("dd/MM/yyyy");
		current_todate = dftodate.format(c_todate.getTime());
		todate.setText(current_todate);
		c_fromdate = Calendar.getInstance();
		dffromdate = new SimpleDateFormat("01/MM/yyyy");
		current_fromdate = dffromdate.format(c_fromdate.getTime());
		fromdate.setText(current_fromdate);
		myCalendar = Calendar.getInstance();
		date_form = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
								  int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel();
			}

			private void updateLabel() {
				String myFormat = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
				fromdate.setText(sdf.format(myCalendar.getTime()));
				new GetCategories1().execute();

			}

		};
		fromdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(Viewbycustomerreturn.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		myCalendar1 = Calendar.getInstance();
		date_to = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
								  int dayOfMonth) {
				// TODO Auto-generated method stub
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, monthOfYear);
				myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel();
			}

			private void updateLabel() {
				String myFormat = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
				todate.setText(sdf.format(myCalendar.getTime()));
				new GetCategories1().execute();

			}

		};
		todate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new DatePickerDialog(Viewbycustomerreturn.this, date_to, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        productListView = findViewById(R.id.pListView);
        ordspin = findViewById(R.id.customer);
        actv = findViewById(R.id.autoCompleteTextView1);
        back_btn = findViewById(R.id.backbt);
        view_btn = findViewById(R.id.view);
        submitBtn = findViewById(R.id.submitBtn);
        fromdate = findViewById(R.id.fromdate);
        todate = findViewById(R.id.todate);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 "); //&#xf060
        ln = findViewById(R.id.totalshow);

        heading = findViewById(R.id.heading);
        totqty = findViewById(R.id.totalsellquantity);
        totval = findViewById(R.id.totalsellvalue);

        product_name = findViewById(R.id.sproduct_name);
        sqnty = findViewById(R.id.sqnty1);
        velue = findViewById(R.id.ssellvelue);
        returnpercentage = findViewById(R.id.returnpercentage);


        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<String>();
        PROD_RATE = new ArrayList<String>();
        percentage = new ArrayList<String>();
        categoriesList = new ArrayList<Category3>();
        categoriesList1 = new ArrayList<Category1>();
        categoriesList2 = new ArrayList<Category2>();
		Bundle b = getIntent().getExtras();
		userName = b.getString("UserName");
    }


    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList1.size(); i++) {
            description.add(categoriesList1.get(i).getId());
        }
        description = Utils.removeDuplicatesFromList(description);
        ArrayAdapter<String> dataAdapterDes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, description);
        ordspin.setAdapter(dataAdapterDes);
        String[] customer = description.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, customer);
        actv.setThreshold(0);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
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
            ArrayList<String> percentage = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue, ord_rate, ord_rate1;
            for (int i = 0; i < categoriesList.size(); i++) {
                p_ids.add(categoriesList.get(i).getId());
                lables.add(categoriesList.get(i).getName());
                percentage.add(categoriesList.get(i).getPROD_VAT());
                quanty.add(categoriesList.get(i).getQuantity());
                value.add(categoriesList.get(i).getPROD_RATE());
            }
            float totalqty = 0F;
            float totalvlu = 0F;
            for (int i = 0; i < quanty.size(); i++) {
                totalqty += Float.parseFloat(quanty.get(i));//(quanty.get(i));
                totalvlu += Float.parseFloat(value.get(i));//quanty.get(i)(value.get(i));
            }

            float total_qnty = totalqty;
            String total_qnty1 = String.format("%.02f", total_qnty);
            float Totalsellvalue1 = totalvlu;
            String total_value = String.format("%.02f", Totalsellvalue1);
            TotalV = String.valueOf("Invoice:" + total_value);
            String sale_velue = total_value;
            double sale_velue1 = Double.parseDouble(sale_velue);
            DecimalFormat formatter = new DecimalFormat("#,##,###.00");
            String sale_velue2 = formatter.format(sale_velue1);
            totval.setText("Inv. val:" + sale_velue2);
            String target_value = total_qnty1;
            double target_value2 = Double.parseDouble(target_value);
            DecimalFormat formatter2 = new DecimalFormat("#,##,###.00");
            String target_value3 = formatter2.format(target_value2);
            totqty.setText("Ord. val:" + target_value3);
            ViewbycustomerreturnProductShowAdapter adapter = new ViewbycustomerreturnProductShowAdapter(Viewbycustomerreturn.this, lables, quanty, value, percentage);
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
        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Viewbycustomerreturn.this);
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
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("current_customer", CurrenCustomer));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, ServiceHandler.POST, params);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category3 cat3 = new Category3(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"));
                            categoriesList.add(cat3);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(Viewbycustomerreturn.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(Viewbycustomerreturn.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Spinner sp = new Spinner();
            sp.populateSpinner();
            popSpinner();
            //totqty.setText(sp.getTotalQ());
            //totval.setText(sp.getTotalV());

        }
    }


    private class GetCategories1 extends AsyncTask<Void, Void, Void> {
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Viewbycustomerreturn.this);
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
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("current_customer", CurrenCustomer));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);

                    if (jsonObj != null) {
                        JSONArray categories1 = jsonObj.getJSONArray("categories");

                        for (int i = 0; i < categories1.length(); i++) {
                            JSONObject catObj = (JSONObject) categories1.get(i);
                            Category1 cat1 = new Category1(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"));
                            categoriesList1.add(cat1);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(Viewbycustomerreturn.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(Viewbycustomerreturn.this, "Please make a order first !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            Spinner sp = new Spinner();
            sp.populateSpinner();
            popSpinner();
            totqty.setText(sp.getTotalQ());
            totval.setText(sp.getTotalV());

        }
    }

    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }

    private void view() {
        Intent i = new Intent(Viewbycustomerreturn.this, Report.class);
        startActivity(i);
        finish();

    }

}
