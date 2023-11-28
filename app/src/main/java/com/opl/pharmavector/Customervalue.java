package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Customervalue extends Activity implements OnClickListener {
	private static Activity parent;
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_MESSAGE = "message";
	//array list for spinner adapter
	private ArrayList<Category> categoriesList;
	public ProgressDialog pDialog;
	ListView productListView;
	Button submit,submitBtn;
	//private EditText current_qnty;
	EditText qnty;
	Boolean result;
	EditText inputOne, inputtwo;
	public int success;
	public String message, ord_no;
	TextView date2, ded,fromdate,todate;
	int textlength = 0;
	public TextView totqty, totval;
	//public android.widget.Spinner ordspin;
	public String userName_1,userName,active_string,act_desiredString;
	public String from_date,to_date;
	JSONParser jsonParser;
	List<NameValuePair> params;
	//public String CurrenCustomer="";
	//public AutoCompleteTextView actv;
	public static ArrayList<String> p_ids;
	public static ArrayList<Integer> p_quanty;
	public static ArrayList<String> PROD_RATE;
	public static ArrayList<String> PROD_VAT;
	private ArrayList<String> array_sort = new ArrayList<String>();
	private final String URL_PRODUCT_VIEW =BASE_URL+"ViewbyDate.php";

	@SuppressLint("SetTextI18n")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customervalue);

		Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
		productListView = (ListView) findViewById(R.id.pListView);
		Button back_btn = (Button) findViewById(R.id.backbt);
		Button view_btn = (Button) findViewById(R.id.view);
		Button submitBtn = (Button) findViewById(R.id.submitBtn);
        fromdate = (TextView) findViewById(R.id.fromdate);
        todate = (TextView) findViewById(R.id.todate);
		back_btn.setTypeface(fontFamily);
		back_btn.setText("\uf060 "); //&#xf060
		final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);
		totqty = (TextView) findViewById(R.id.totalsellquantity);
		totval = (TextView) findViewById(R.id.totalsellvalue);
		totqty.setText("Quantity");
		totval.setText("Sellvalue");
		int listsize = productListView.getChildCount();
		Log.i("Size of ProductLIstview", "ProductLIstView SIZE: " + listsize);
		p_ids = new ArrayList<String>();
		p_quanty = new ArrayList<Integer>();
		PROD_RATE = new ArrayList<String>();
		categoriesList = new ArrayList<Category>();
		Bundle b = getIntent().getExtras();
		String userName = b.getString("UserName");

		//new ViewbyDate.GetCategories().execute();
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date_form = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
            }
        };
        fromdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Customervalue.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Calendar myCalendar1 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date_to = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
            }
        };
        todate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Customervalue.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
		back_btn.setOnClickListener(new OnClickListener() {
			Bundle b = getIntent().getExtras();
			String userName = b.getString("UserName");

			@Override
			public void onClick(final View v) {
				Thread backthred = new Thread(new Runnable() {
					@Override
					public void run() {
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
                    String fromdate1=fromdate.getText().toString();
                    String todate1=todate.getText().toString();
					System.out.println("else  fromdate1 "+fromdate1);
					System.out.println("else todate1"+todate1);

                    if (fromdate1.isEmpty()||(fromdate1.equals("From Date"))) {
                        fromdate.setError( "From Date is required!" );
                    } else if (todate1.isEmpty()||(todate1.equals("To Date"))) {
                        todate.setError( "To Date is required!" );
                    } else {
                        System.out.println("after text change elsfromdate1eeeeeeeee"+fromdate1);
                        System.out.println("elsetodate1 "+todate1);
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
			public void onClick(View v) {}
		});
    }

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menus, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.setting:
				startActivity(new Intent(this, setting.class));
				return true;
			case R.id.help:
				startActivity(new Intent(this, Help.class));
				return true;
			case R.id.home:
				finish();
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void popSpinner() {
		List<String> description = new ArrayList<String>();

		for (int i = 0; i < categoriesList.size(); i++) {
			Log.d("Changepa---ssword",categoriesList.get(i).getId());
			description.add(categoriesList.get(i).getId());
			Log.d("Changep---assword","Login"+categoriesList.get(i).getId());
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
			ArrayList<Integer> quanty = new ArrayList<Integer>();
			ArrayList<Float> value = new ArrayList<Float>();
			int quantity = 0;
			float prod_rate, prod_vat, sellvalue;

			for (int i = 0; i < categoriesList.size(); i++) {
				Log.i("OPSONIN", " P_ID " + categoriesList.get(i).getId());
				Log.i("OPSONIN", " P_ID rezaul--------");
				lables.add(categoriesList.get(i).getName());
				p_ids.add(categoriesList.get(i).getId());
				quanty.add(categoriesList.get(i).getQuantity());
				quantity = categoriesList.get(i).getQuantity();
				prod_rate = Float.parseFloat(categoriesList.get(i).getPROD_RATE());
				//sellvalue = quantity * prod_rate;
				sellvalue = prod_rate;
				String sellvalue1 =String.format("%.02f", sellvalue);
				Float sellvalue2= Float.parseFloat(sellvalue1);
				value.add(prod_rate);
			}
			int totalqty = 0;
			float totalvlu = 0F;

			for (int i = 0; i < quanty.size(); i++) {
				totalqty += (quanty.get(i));
				totalvlu += (value.get(i));
			}
			TotalQ = String.valueOf(totalqty);
			float Totalsellvalue1 = totalvlu;
			String total_value = String.format("%.02f", Totalsellvalue1);
			TotalV = String.valueOf(total_value);
			System.err.println("The Total sale Quantity/Value" + TotalQ + "/"+ TotalV);
			CustomervalueProductShowAdapter adapter = new CustomervalueProductShowAdapter(Customervalue.this,lables, quanty, value);
			productListView.setAdapter(adapter);
		}
		private float round(float x, int i) {return 0;}
		public String getTotalQ() {
			return TotalQ;
		}
		public String getTotalV() {
			return TotalV;
		}
	}

	private class GetCategories extends AsyncTask<Void, Void, Void> {
		String fromdate1=fromdate.getText().toString();
		String todate1=todate.getText().toString();
		Bundle b = getIntent().getExtras();
		String userName = b.getString("UserName");

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Customervalue.this);
			pDialog.setTitle("Sale Loading !");
			pDialog.setMessage("Fatching Sale, Please Wait..");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy---------------------------y");
			Log.e(" todate: ", ">  yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+todate1);
			Log.e(" fromdate: ", ">  yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+fromdate1);
			Bundle b = getIntent().getExtras();
			String userName = b.getString("UserName");
			String id = userName;
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("to_date", todate1));
			params.add(new BasicNameValuePair("from_date", fromdate1));
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW,ServiceHandler.POST, params);
			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					JSONArray categories = jsonObj.getJSONArray("categories");

					for (int i = 0; i < categories.length(); i++) {
						JSONObject catObj = (JSONObject) categories.get(i);
						Category cat = new Category(
								catObj.getString("sl"),
								catObj.getString("id"),
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
				Toast.makeText(Customervalue.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
				Toast.makeText(Customervalue.this, "Please make a order first !",Toast.LENGTH_LONG).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			Customervalue.Spinner sp = new Customervalue.Spinner();
			sp.populateSpinner();
			popSpinner();
			totqty.setText(sp.getTotalQ());
			totval.setText(sp.getTotalV());
		}
	}

	@Override
	public void onClick(View v) {}

	protected void onPostExecute() {}

	private void view() {
		Intent i = new Intent(Customervalue.this, Report.class);
		startActivity(i);
		finish();
	}
}
