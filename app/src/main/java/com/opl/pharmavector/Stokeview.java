package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nativecss.NativeCSS;
import com.opl.pharmavector.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Stokeview extends Activity implements OnClickListener {

	public static final String TAG_SUCCESS = "success";
	public static final String TAG_MESSAGE = "message";
	// array list for spinner adapter
	private ArrayList<Category> categoriesList;
	ProgressDialog pDialog;
	ListView productListView;
	Button submit;
	// private EditText current_qnty;
	EditText qnty;

	EditText inputOne, inputtwo;
	public int success;
	public String message, ord_no;
	int textlength = 0;
	public TextView totqty, totval;
	public android.widget.Spinner ordspin;
	public String userName_1,userName;
	JSONParser jsonParser;
	List<NameValuePair> params;
	public String CurrenOrder;
	public AutoCompleteTextView actv;

	public static ArrayList<String> p_ids;
	public static ArrayList<Integer> p_quanty;

	public static ArrayList<String> PROD_RATE;
	public static ArrayList<String> PROD_VAT;

	private ArrayList<String> array_sort = new ArrayList<String>();


	private String URL_PRODUCT_VIEW =BASE_URL+"mposalesreports/depo_report/Stokeview.php";



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stokeview);
		Typeface fontFamily = Typeface.createFromAsset(getAssets(),
				"fonts/fontawesome.ttf");
		productListView = (ListView) findViewById(R.id.pListView);
		ordspin = (android.widget.Spinner) findViewById(R.id.orderno);
		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		Button back_btn = (Button) findViewById(R.id.backbt);




		back_btn.setTypeface(fontFamily);
		back_btn.setText("\uf060 ");// &#xf060

		final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);
		totqty = (TextView) findViewById(R.id.totalsellquantity);
		totval = (TextView) findViewById(R.id.totalsellvalue);

		totqty.setText("Quantity");
		totval.setText("");


		p_ids = new ArrayList<String>();
		p_quanty = new ArrayList<Integer>();

		PROD_RATE = new ArrayList<String>();

		categoriesList = new ArrayList<Category>();
		Bundle b = getIntent().getExtras();
		String userName = b.getString("UserName");
		new GetCategories().execute();


		ordspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view,
									   int i, long l) {

				CurrenOrder = ordspin.getSelectedItem().toString();

			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});

		actv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CurrenOrder = actv.getText().toString();
				CurrenOrder = actv.getText().toString();
				int inputsize = CurrenOrder.length();
				if (inputsize > 5 || CurrenOrder.indexOf("//") != -1) {
					actv.setText("");
					CurrenOrder="";
					categoriesList.clear();
					new GetCategories().execute();
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

				System.out.println("inputorder "+inputorder);


				int inputsize = inputorder.length();
				if (!inputorder.toString().trim().isEmpty() && inputorder.indexOf("//") != -1) {
					CurrenOrder = inputorder;
					//Toast.makeText(Stokeview.this, "Selected Product Name :" + CurrenOrder,
							//Toast.LENGTH_LONG).show();
					categoriesList.clear();
					new GetCategories().execute();
				}


				else {
					/*
					ArrayList<String> lables = new ArrayList<String>();
					ArrayList<Integer> quanty = new ArrayList<Integer>();
					ArrayList<Float> value = new ArrayList<Float>();

					StokeProductShowAdapter blank_adapter = new StokeProductShowAdapter(
							Stokeview.this, lables, quanty, value);
					new GetCategories().execute();
					productListView.setAdapter(blank_adapter);
					*/
					categoriesList.clear();

				}

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




		ln.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

	}



	private void popSpinner() {
		List<String> description = new ArrayList<String>();

		// txtCategory.setText("");

		for (int i = 0; i < categoriesList.size(); i++) {
			description.add(categoriesList.get(i).getId());
		}

		description = Utils.removeDuplicatesFromList(description);
		ArrayAdapter<String> dataAdapterDes = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, description);
		// ordspin.setAdapter(dataAdapterDes);
		String[] customer = description.toArray(new String[description.size()]);
		ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, customer);
		//System.out.println("customer "+customer);
		actv.setThreshold(2);
		actv.setAdapter(Adapter);
		actv.setTextColor(Color.BLUE);
	}

	public void finishActivity(View v) {
		finish();
	}

	/**
	 *
	 * Adding spinner data
	 * */
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
				lables.add(categoriesList.get(i).getName());
				p_ids.add(categoriesList.get(i).getId());
				//	Log.i("OPSONIN", " P_ID " + categoriesList.get(i).getId());
				quanty.add(categoriesList.get(i).getQuantity());
				quantity = categoriesList.get(i).getQuantity();
				prod_rate = Float.parseFloat(categoriesList.get(i).getPROD_RATE());
				sellvalue = quantity * 1;
				String sellvalue1 =String.format("%.02f", sellvalue);
				Float sellvalue2= Float.parseFloat(sellvalue1);
				value.add(sellvalue2);
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
			System.err.println("The Total sell Quantity/Value" + TotalQ + "/"+ TotalV);
			StokeProductShowAdapter adapter = new StokeProductShowAdapter(Stokeview.this,lables, quanty, value);
			productListView.setAdapter(adapter);

		}

		private float round(float x, int i) {
			// TODO Auto-generated method stub
			return 0;
		}

		public String getTotalQ() {
			// System.out.println("TotalQ\t"+TotalQ);
			return TotalQ;
		}

		public String getTotalV() {
			// System.out.println("TotalV\t"+TotalV);
			return TotalV;
		}

	}

	/**
	 * Async task to get all food categories
	 * */
	private class GetCategories extends AsyncTask<Void, Void, Void> {

		Bundle b = getIntent().getExtras();
		String userName = b.getString("UserName");

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Stokeview.this);
			pDialog.setTitle("Stock quantity Loading !");
			pDialog.setMessage("Fetching Stock quantity, Please Wait..");
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
				Log.w("OrderNumbres", "" + CurrenOrder);
			params.add(new BasicNameValuePair("ord_no", CurrenOrder));
			ServiceHandler jsonParser = new ServiceHandler();
			String json = jsonParser.makeServiceCall(URL_PRODUCT_VIEW,ServiceHandler.POST, params);

			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray categories = jsonObj
								.getJSONArray("categories");
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
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
				Toast.makeText(Stokeview.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();	Toast.makeText(Stokeview.this, "Please make a order first !",Toast.LENGTH_LONG).show();
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
			totval.setText("");

			// totval.setText(sp.getTotalV());
		}

	}

	/*------------- list items on click event----------------*/

	@Override
	public void onClick(View v) {


	}

	protected void onPostExecute() {

	}


	private void view() {
		Intent i = new Intent(Stokeview.this, Report.class);
		startActivity(i);
		finish();

	}

}
