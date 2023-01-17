package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
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

public class Viewbycustomerorderstatus extends Activity implements OnClickListener {

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<Category> categoriesList;
    private ArrayList<Category1> categoriesList1;
    ProgressDialog pDialog;
    ListView productListView;
    public int success;
    public String message, ord_no;
    public TextView totqty, totval, product_name, sqnty, velue;
    public android.widget.Spinner ordspin;
    TextView  fromdate, todate;
    public String userName_1, userName, active_string, act_desiredString, active_string2;
    public String CurrenCustomer = "";
    public AutoCompleteTextView actv;
    Button  submitBtn, back_btn, view_btn;
    LinearLayout ln;
    Calendar c_todate, c_fromdate,myCalendar,myCalendar1;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate;
	DatePickerDialog.OnDateSetListener date_form,date_to;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    final String URL_PRODUCT_VIEW = BASE_URL+"mposalesreports/depo_report/partywiseordstatus.php";
    final String URL_CUSOTMER = BASE_URL+"mposalesreports/depo_report/ord_wise_customerlist.php";



    @SuppressLint({"SimpleDateFormat", "ClickableViewAccessibility"})
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewbycustomerorderstatus);
        initViews();
        initCalender();

        new GetCategories1().execute();

        ordspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CurrenCustomer = ordspin.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
			}
        });

        actv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                CurrenCustomer = actv.getText().toString();
                active_string = actv.getText().toString();
                if (active_string.contains("//")) {
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
                String inputorder = s.toString();
                CurrenCustomer = s.toString();
                if (inputorder.contains("//")) {
                    categoriesList.clear();
                    String[] arr1 = CurrenCustomer.split("//");
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

	@SuppressLint("SimpleDateFormat")
	private void initCalender() {

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
				//String myFormat = "dd/MM/yyyy";
				String myFormat = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
				fromdate.setText(sdf.format(myCalendar.getTime()));
				new GetCategories1().execute();

			}

		};
		fromdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(Viewbycustomerorderstatus.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
				//String myFormat = "dd/MM/yyyy";
				String myFormat = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
				todate.setText(sdf.format(myCalendar.getTime()));
				new GetCategories1().execute();

			}

		};
		todate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				new DatePickerDialog(Viewbycustomerorderstatus.this, date_to, myCalendar
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
        back_btn.setText("\uf060 ");
        ln = findViewById(R.id.totalshow);
        totqty = findViewById(R.id.totalsellquantity);
        totval = findViewById(R.id.totalsellvalue);
        product_name = findViewById(R.id.sproduct_name);
        sqnty = findViewById(R.id.sqnty1);
        velue = findViewById(R.id.ssellvelue);
        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();
        categoriesList = new ArrayList<Category>();
        categoriesList1 = new ArrayList<Category1>();

    }


    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList1.size(); i++) {
            description.add(categoriesList1.get(i).getId());
        }
        description = Utils.removeDuplicatesFromList(description);
        ArrayAdapter<String> dataAdapterDes = new ArrayAdapter<String>(this, R.layout.spinner_text_view, description);
        ordspin.setAdapter(dataAdapterDes);
        String[] customer = description.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
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
            ArrayList<Integer> quanty = new ArrayList<Integer>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> vat = new ArrayList<String>();
            int quantity = 0;
            for (int i = 0; i < categoriesList.size(); i++) {
                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());
                String pay_value = categoriesList.get(i).getPROD_RATE();

                value.add(pay_value);
            }
            ViewbycustomerorderstatusProductShowAdapter adapter = new ViewbycustomerorderstatusProductShowAdapter(Viewbycustomerorderstatus.this, lables, quanty, value);
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

    @SuppressLint("StaticFieldLeak")
    private class GetCategories extends AsyncTask<Void, Void, Void> {
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Viewbycustomerorderstatus.this);
            pDialog.setTitle("Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @SuppressLint("WrongThread") String fromdate1 = fromdate.getText().toString();
            @SuppressLint("WrongThread") String todate1 = todate.getText().toString();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("current_customer", CurrenCustomer));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray categories = jsonObj.getJSONArray("categories");
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject catObj = (JSONObject) categories.get(i);
                        Category cat = new Category(
                                catObj.getString("sl"), catObj.getString("id"),
                                catObj.getString("name"),
                                catObj.getInt("quantity"),
                                catObj.getString("PROD_RATE"),
                                catObj.getString("PROD_VAT"));
                        categoriesList.add(cat);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(Viewbycustomerorderstatus.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(Viewbycustomerorderstatus.this, "Please make a order first !", Toast.LENGTH_LONG).show();
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


    @SuppressLint("StaticFieldLeak")
    private class GetCategories1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Viewbycustomerorderstatus.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            @SuppressLint("WrongThread") String fromdate1 = fromdate.getText().toString();
            @SuppressLint("WrongThread") String todate1 = todate.getText().toString();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("current_customer", CurrenCustomer));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray categories1 = jsonObj.getJSONArray("categories");
                    for (int i = 0; i < categories1.length(); i++) {
                        JSONObject catObj = (JSONObject) categories1.get(i);
                        Category1 cat1 = new Category1(
                                catObj.getString("sl"),
                                catObj.getString("id"),
                                catObj.getString("name"));
                        categoriesList1.add(cat1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

                Toast.makeText(Viewbycustomerorderstatus.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(Viewbycustomerorderstatus.this, "Please make a order first !", Toast.LENGTH_LONG).show();
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
        Intent i = new Intent(Viewbycustomerorderstatus.this, Report.class);
        startActivity(i);
        finish();

    }

}
