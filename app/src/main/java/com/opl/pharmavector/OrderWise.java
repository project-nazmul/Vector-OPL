package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.lang.Runnable;
//import java.util.DateFormat;
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
import android.content.Intent;
import android.graphics.Color;
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
import java.text.DecimalFormat;

public class OrderWise extends Activity implements OnClickListener {
	private static Activity parent;
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_MESSAGE = "message";
	// array list for spinner adapter
	private ArrayList<Category> categoriesList;
	public ProgressDialog pDialog;
	ListView productListView;
	Button submit,submitBtn;
	// private EditText current_qnty;
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

	private DecimalFormat df;
	private DecimalFormat dfnd;
	//private String URL_PRODUCT_VIEW ="http://opsonin.com.bd/dept_order_android_v2/ViewbyDate.php";
	private String URL_PRODUCT_VIEW =BASE_URL+"OrderWise.php";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderwise);
		Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
		productListView = (ListView) findViewById(R.id.pListView);
		Button back_btn = (Button) findViewById(R.id.backbt);
		Button view_btn = (Button) findViewById(R.id.view);
		Button submitBtn = (Button) findViewById(R.id.submitBtn);
        fromdate = (TextView) findViewById(R.id.fromdate);
        todate = (TextView) findViewById(R.id.todate);

		back_btn.setTypeface(fontFamily);
		back_btn.setText("\uf060 ");// &#xf060
		final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);
		totqty = (TextView) findViewById(R.id.totalsellquantity);
		totval = (TextView) findViewById(R.id.totalsellvalue);
		//totqty.setText("Quantity");
		//totval.setText("Sellvalue");

		//submitBtn.setTypeface(fontFamily);
		//submitBtn.setText("\uf06e");
		//submitBtn.setTypeface(null, Typeface.BOLD);
		//submitBtn.setTypeface(null, Typeface.BOLD);
		submitBtn.setTextSize(24);


		//&#xf06e
		int listsize = productListView.getChildCount();
		Log.i("Size of ProductLIstview", "ProductLIstView SIZE: " + listsize);
		p_ids = new ArrayList<String>();
		p_quanty = new ArrayList<Integer>();
		PROD_RATE = new ArrayList<String>();
		categoriesList = new ArrayList<Category>();
		Bundle b = getIntent().getExtras();
		String userName = b.getString("UserName");
		Toast.makeText(OrderWise.this, userName, Toast.LENGTH_LONG).show();



		Calendar c_todate = Calendar .getInstance();
		//System.out.println("Current time => "+c.getTime());
		SimpleDateFormat dftodate = new SimpleDateFormat("dd/MM/yyyy");
		String current_todate = dftodate.format(c_todate.getTime());
		todate.setText(current_todate);


		Calendar c_fromdate = Calendar .getInstance();
		//System.out.println("Current time => "+c.getTime());
		SimpleDateFormat dffromdate = new SimpleDateFormat("01/MM/yyyy");
		String current_fromdate = dffromdate.format(c_fromdate.getTime());
		fromdate.setText(current_fromdate);



		//todate.setTextColor(Color.BLACK);











		//new ViewbyDate.GetCategories().execute();
		/*------------------------------------------------------*/
     /*--------------------------from-date range----------------------------*/
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date_form = new DatePickerDialog.OnDateSetListener() {
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

				fromdate.setTextColor(Color.BLACK);
				fromdate.setText("");
                fromdate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        fromdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(OrderWise.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

		/*---------------------------from date range-----------------end-----------*/





		/*--------------------------to-date range----------------------------*/

        final Calendar myCalendar1 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date_to = new DatePickerDialog.OnDateSetListener() {
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

				todate.setTextColor(Color.BLACK);
				todate.setText("");
                todate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        todate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(OrderWise.this, date_to, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

		/*---------------------------to date range-----------------end-----------*/

		back_btn.setOnClickListener(new OnClickListener() {
			Bundle b = getIntent().getExtras();
			//UserName = b.getString("UserName");
			//UserName_1= b.getString("UserName_1");
			//UserName_2= b.getString("UserName_2");
			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				Thread backthred = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						try {
							Log.d("New_pass2", "New_pass2");

							Bundle b = getIntent().getExtras();
							String userName = b.getString("UserName");
							String UserName_1 = b.getString("userName_1");
							String UserName_2 = b.getString("userName_2");

							Intent i = new Intent(OrderWise.this,  Report.class);
							i.putExtra("UserName", userName);
							i.putExtra("new_version", userName);
							i.putExtra("UserName_1", UserName_1);
							i.putExtra("UserName_2", UserName_2);

							i.putExtra("userName", userName);
							i.putExtra("new_version", userName);
							i.putExtra("userName_1", UserName_1);
							i.putExtra("userName_2", UserName_2);

							Log.d("userName","UserName"+userName);

							Log.d("UserName_1","UserName_1"+UserName_1);
							Log.d("UserName_2","UserName_2"+UserName_2);







							startActivity(i);
							//finish();
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
                    if (fromdate1.isEmpty()||(fromdate1.equals("From Date"))||(fromdate1.equals("From Date is required"))) {
                        //fromdate.setError( "From Date is required!" );
						fromdate.setText("From Date is required");
						fromdate.setTextColor(Color.RED);
                    }
                    else if (todate1.isEmpty()||(todate1.equals("To Date"))||(todate1.equals("To Date is required"))) {
                        //todate.setError( "To Date is required!" );

						todate.setText("To Date is required");
						todate.setTextColor(Color.RED);

                    }
                    else {
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
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

    }

	/*-------------- Extra Menus--------------*/
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

	/*------------Extra menus end-------------------*/

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
				Log.d("------rkrk---------",""+quanty.get(i));

				totalvlu += (value.get(i));
				Log.d("------rkrk---------",""+value.get(i));
			}
			TotalQ = String.valueOf(totalqty);
			float Totalsellvalue1 = totalvlu;
			String total_value = String.format("%.02f", Totalsellvalue1);
			TotalV = String.valueOf(total_value);
			System.err.println("The Total sale Quantity/Value" + TotalQ + "/"+ TotalV);
			OrderwiseProductShowAdapter adapter = new OrderwiseProductShowAdapter(OrderWise.this,lables, quanty, value);
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

		String fromdate1=fromdate.getText().toString();
		String todate1=todate.getText().toString();
		Bundle b = getIntent().getExtras();
		String userName = b.getString("UserName");
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(OrderWise.this);
			pDialog.setTitle("Sale Loading !");
			pDialog.setMessage("Fetching Sale, Please Wait..");
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
					if (jsonObj != null) {
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
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
				Toast.makeText(OrderWise.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
				Toast.makeText(OrderWise.this, "Please make a order first !",Toast.LENGTH_LONG).show();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			OrderWise.Spinner sp = new OrderWise.Spinner();
			sp.populateSpinner();
			popSpinner();
			//totqty.setText("Total Quantity="+sp.getTotalQ());
			//totqty.setText("Total Quantity="+sp.getTotalQ());

			String number = sp.getTotalV();
			//String number = "100000000";
			double amount = Double.parseDouble(number);
			DecimalFormat formatter = new DecimalFormat("#,##,###.00");
			String formatted = formatter.format(amount);



			totqty.setText("");
			totval.setText("Total Value="+formatted);

		}
	}
	/*------------- list items on click event----------------*/

























	@Override
	public void onClick(View v) {
	}
	protected void onPostExecute() {
	}
	private void view() {
		Intent i = new Intent(OrderWise.this, Report.class);
		startActivity(i);
		finish();

	}

}
