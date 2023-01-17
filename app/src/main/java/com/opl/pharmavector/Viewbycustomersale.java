package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.graphics.Typeface;

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

public class Viewbycustomersale extends Activity implements OnClickListener {

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private ArrayList<Category3> categoriesList;
    private ArrayList<Category1> categoriesList1;
    private ArrayList<Category2> categoriesList2;
    ProgressDialog pDialog;
    ListView productListView;
    public int success;
    public String message, ord_no;
    public TextView totqty, totval, product_name, sqnty, velue;
    public android.widget.Spinner ordspin;
    public android.widget.Spinner ordspin2;
    TextView fromdate, todate;
    public String userName_1, userName, active_string, act_desiredString, active_string2;
    public String CurrenCustomer = "";
    public String CurrenCust = "", current_ord = "";
    public String CurrenCustomer2 = "";
    public AutoCompleteTextView actv, actv2;
    public static ArrayList<String> p_ids;
    public static ArrayList<String> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;

    private ArrayList<String> array_sort = new ArrayList<String>();
    private String URL_PRODUCT_VIEW = BASE_URL+"mposalesreports/depo_report/viewbycustomersale.php";
    private String URL_CUSOTMER = BASE_URL+"mposalesreports/depo_report/ord_wise_customerlist.php";
    private String URL_ORD = BASE_URL+"mposalesreports/depo_report/customerwiseordno.php";
    Button back_btn, submitBtn;

    LinearLayout ln;
    Calendar c_todate, c_fromdate;
    SimpleDateFormat dftodate, dffromdate;
    String current_todate, current_fromdate;
    Calendar myCalendar, myCalendar1;
    DatePickerDialog.OnDateSetListener date_form, date_to;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbycustomersale);
        initViews();
		calenderinitViews();
		new GetCategories1().execute();
		spinnerinitViews();


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
                        product_name.setText("Product Name");

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

	private void spinnerinitViews() {

		ordspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				CurrenCustomer = ordspin.getSelectedItem().toString();
				//	ordspin.getSelectedItem().toString().setTextSize(5);


				System.out.println("Currenustomer ordspin.setOnItemSelectedListener" + CurrenCustomer);
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		actv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CurrenCustomer = actv.getText().toString();
				active_string = actv.getText().toString();

				if (active_string.indexOf("//") != -1) {
					categoriesList2.clear();
					categoriesList.clear();
					actv2.setText("");
					actv.setText("");
					new GetCategories2().execute();

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
					actv2.setText("");


					String arr1[] = CurrenCustomer.split("//");
					CurrenCustomer = arr1[1];
					new GetCategories2().execute();
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

		ordspin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				CurrenCustomer2 = ordspin2.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		actv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CurrenCustomer2 = actv2.getText().toString();
				active_string2 = actv2.getText().toString();
				int inputsize = CurrenCustomer2.length();
				if (inputsize > 12) {
					actv2.setText("");
					CurrenCustomer2 = "";
					categoriesList2.clear();

				}


			}
		});
		actv2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub


				String inputorder = s.toString();
				current_ord = s.toString();
				int inputsize = inputorder.length();
				if (inputsize > 10) {
					String arr2[] = current_ord.split("//");
					current_ord = arr2[0];

				}


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
				current_ord = s.toString();
				int inputsize = inputorder.length();
				if (inputsize > 10) {
					String arr2[] = current_ord.split("//");
					current_ord = arr2[0];

				}

			}

		});
		actv2.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				actv2.showDropDown();
				return false;
			}
		});

	}

	private void initViews() {

		Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
		productListView = (ListView) findViewById(R.id.pListView);
		ordspin = (android.widget.Spinner) findViewById(R.id.customer);
		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		ordspin2 = (android.widget.Spinner) findViewById(R.id.ord);
		actv2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
		back_btn = (Button) findViewById(R.id.backbt);
		submitBtn = (Button) findViewById(R.id.submitBtn);
		fromdate = (TextView) findViewById(R.id.fromdate);
		todate = (TextView) findViewById(R.id.todate);
		back_btn.setTypeface(fontFamily);
		back_btn.setText("\uf060 ");// &#xf060
		ln = (LinearLayout) findViewById(R.id.totalshow);
		totqty = (TextView) findViewById(R.id.totalsellquantity);
		totval = (TextView) findViewById(R.id.totalsellvalue);
		product_name = (TextView) findViewById(R.id.sproduct_name);
		sqnty = (TextView) findViewById(R.id.sqnty1);
		velue = (TextView) findViewById(R.id.ssellvelue);
		p_ids = new ArrayList<String>();
		p_quanty = new ArrayList<String>();
		PROD_RATE = new ArrayList<String>();
		categoriesList = new ArrayList<Category3>();
		categoriesList1 = new ArrayList<Category1>();
		categoriesList2 = new ArrayList<Category2>();
		Bundle b = getIntent().getExtras();
		userName = b.getString("UserName");

	}

	private void calenderinitViews() {

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
				new DatePickerDialog(Viewbycustomersale.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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

				new DatePickerDialog(Viewbycustomersale.this, date_to, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}


    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList1.size(); i++) {
            description.add(categoriesList1.get(i).getId());

        }

        description = Utils.removeDuplicatesFromList(description);
        ArrayAdapter<String> dataAdapterDes = new ArrayAdapter<String>(this,  R.layout.spinner_text_view, description);
        ordspin.setAdapter(dataAdapterDes);

        String[] customer = description.toArray(new String[description.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,  R.layout.spinner_text_view, customer);


        actv.setThreshold(0);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
        //actv.setTextSize(5);


        //actv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 65);


    }

    public void finishActivity(View v) {
        finish();
    }

    class Spinner {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner() {
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<Float> quanty = new ArrayList<Float>();
            ArrayList<Float> value = new ArrayList<Float>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue, prod_rate1, prod_vat1, sellvalue1;
            for (int i = 0; i < categoriesList.size(); i++) {
                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());


                prod_rate1 = Float.parseFloat(categoriesList.get(i).getQuantity());
                quanty.add(prod_rate1);


                prod_rate = Float.parseFloat(categoriesList.get(i).getPROD_RATE());
                String pay_value = categoriesList.get(i).getPROD_RATE();
                sellvalue = prod_rate;

                //Float sellvalue2= Float.parseFloat(sellvalue1);
                Float pay_value1 = Float.parseFloat(pay_value);
                value.add(pay_value1);

            }

            float totalqty = 0F;
            float totalvlu = 0F;
            for (int i = 0; i < quanty.size(); i++) {
                totalqty += (quanty.get(i));
                totalvlu += (value.get(i));
            }

            Log.e("totalqty: ", "> " + totalqty);
            float Totaqnty = totalqty;

            String TotalQ = String.format("%.02f", Totaqnty);

            float Totalsellvalue1 = totalvlu;
            String total_value = String.format("%.02f", Totalsellvalue1);
            TotalV = String.valueOf(total_value);
            //	system.err.println("The Total sale Quantity/Value" + TotalQ + "/"+ TotalV);


            String sale_velue = TotalV;
            //String number = "100000000";
            double sale_velue1 = Double.parseDouble(sale_velue);
            DecimalFormat formatter = new DecimalFormat("#,##,###.00");
            String sale_velue2 = formatter.format(sale_velue1);


            totval.setText("Inv. val:" + sale_velue2);


            String target_value = TotalQ;
            //String number = "100000000";
            double target_value2 = Double.parseDouble(target_value);
            DecimalFormat formatter2 = new DecimalFormat("#,##,###.00");
            String target_value3 = formatter2.format(target_value2);


            //TotalQ = String.valueOf("Ord. Value:"+total_qnty);
            totqty.setText("Ord. val:" + target_value3);


            ViewbycustomerProductShowAdaptersale adapter = new ViewbycustomerProductShowAdaptersale(Viewbycustomersale.this, lables, quanty, value);
            productListView.setAdapter(adapter);
        }

        private float round(float x, float i) {
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
            pDialog = new ProgressDialog(Viewbycustomersale.this);
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
            String id = userName;


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("current_customer", CurrenCustomer));
            params.add(new BasicNameValuePair("current_ord", current_ord));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));


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
                            Category3 cat = new Category3(
                                    catObj.getString("sl"), catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"));
                            categoriesList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(Viewbycustomersale.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(Viewbycustomersale.this, "Please make a order first !", Toast.LENGTH_LONG).show();
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


            //totqty.setText("--");
            //totval.setText(sp.getTotalV());

        }
    }


    /*-----------------------------24.03.2018---------START------------------*/

    private class GetCategories1 extends AsyncTask<Void, Void, Void> {
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Viewbycustomersale.this);
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
            String id = userName;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("current_customer", CurrenCustomer));
            params.add(new BasicNameValuePair("current_ord", current_ord));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
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

                Toast.makeText(Viewbycustomersale.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(Viewbycustomersale.this, "Please make a order first !", Toast.LENGTH_LONG).show();
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


    private void popSpinner1() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList2.size(); i++) {
            Log.d("categoriesList2", categoriesList2.get(i).getId());
            description.add(categoriesList2.get(i).getId());
            Log.d("categoriesList2", "Login" + categoriesList2.get(i).getId());
        }

        description = Utils.removeDuplicatesFromList(description);
        ArrayAdapter<String> dataAdapterDes = new ArrayAdapter<String>(this,  R.layout.spinner_text_view, description);
        ordspin2.setAdapter(dataAdapterDes);

        String[] ord = description.toArray(new String[description.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, ord);


        actv2.setThreshold(0);
        actv2.setAdapter(Adapter);
        actv2.setTextColor(Color.BLUE);
        //actv.setTextSize(5);


        //actv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 65);


    }


    private class GetCategories2 extends AsyncTask<Void, Void, Void> {
        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        String fromdate1 = fromdate.getText().toString();
        String todate1 = todate.getText().toString();

        String test = CurrenCust;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Viewbycustomersale.this);
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
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("current_customer", CurrenCustomer));
            params.add(new BasicNameValuePair("to_date", todate1));
            params.add(new BasicNameValuePair("from_date", fromdate1));

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_ORD, ServiceHandler.POST, params);
            Log.e("Response-----------: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories2 = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories2.length(); i++) {
                            JSONObject catObj = (JSONObject) categories2.get(i);
                            Category2 cat2 = new Category2(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"));
                            categoriesList2.add(cat2);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(Viewbycustomersale.this, "Nothing To Disply", Toast.LENGTH_SHORT).show();
                Toast.makeText(Viewbycustomersale.this, "Please make a order first !", Toast.LENGTH_LONG).show();
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
            popSpinner1();
            totqty.setText(sp.getTotalQ());
            totval.setText(sp.getTotalV());

        }
    }










    /*-----------------------------24.03.2018---------end------------------*/


    /*------------- list items on click event----------------*/
    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }

    private void view() {
        Intent i = new Intent(Viewbycustomersale.this, Report.class);
        startActivity(i);
        finish();

    }

}
