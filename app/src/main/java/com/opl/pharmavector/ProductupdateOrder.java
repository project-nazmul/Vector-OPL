package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opl.pharmavector.R;
import com.opl.pharmavector.database.DatabaseQueryHelper;
import com.opl.pharmavector.order_online.ReadComments;
import com.opl.pharmavector.util.NetInfo;
import com.opl.pharmavector.util.SharedPreferencesHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductupdateOrder extends Activity implements OnClickListener {

	public static final String TAG_SUCCESS = "success";
	public static final String TAG_MESSAGE = "message";
	public static final String TAG_invoice = "invoice";
	public static final String TAG_target = "target";
	public static final String TAG_achivement = "achivement";
	public static final String TAG_MESSAGE_1 = "message_1";
	public static final String TAG_MESSAGE_2 = "message_2";
	public String userName_1,userName;

	// array list for spinner adapter
	static ArrayList<Category> categoriesList;
	ProgressDialog pDialog;
	static ListView productListView;
	ProductListupdateAdapter adapter2;
	Button submit;
	// private EditText current_qnty;
	EditText qnty, searchview;
	EditText inputOne, inputtwo;
	public int success;
	public String message, ord_no,invoice,target,achivement,searchString,message_1,message_2;
	int textlength = 0;
	ProductListupdateAdapter adapter;

	JSONParser jsonParser;
	List<NameValuePair> params;
	static TextView totalsellquantity;

	static TextView totalsellvalue;
	public LinearLayout totalshow;
	public Button calc;

	public static ArrayList<String> p_ids;
	public static ArrayList<Integer> p_quanty;

	public static ArrayList<String> PROD_RATE;
	public static ArrayList<String> PROD_VAT;
	ArrayList<String> lables;
	ArrayList<Integer> quanty;
	HashMap<Integer, String> mapQuantity;

	static HashMap<String, Integer> nameSerialPair;
	ArrayList<String> sl;
	/*- Initializing*/
	String last_quantity = "1";
	int last_position = 1;
	String quantity = "1";

	ArrayList<Category> arraylist = new ArrayList<Category>();

	public static String URL_NEW_CATEGORY = BASE_URL+"put_updateproducts.php";
	private String URL_CATEGORIES = BASE_URL+"get_updateproducts.php";
	private String campaign_credit = BASE_URL+"get_campaign_updateproducts.php";

	@SuppressLint("DefaultLocale")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productorder_update_new);

		Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
		submit = (Button) findViewById(R.id.submitBtn);
		submit.setTypeface(fontFamily);
		submit.setText("\uf1d8"); // &#xf1d8
		productListView = (ListView) findViewById(R.id.pListView);
		Button back_btn = (Button) findViewById(R.id.backBtn);
		TextView showorders=(TextView)findViewById(R.id.showorders);

		showorders.setTypeface(fontFamily);
		showorders.setText("\uf055");//&#xf055
		back_btn.setTypeface(fontFamily);
		back_btn.setText("\uf060 ");// &#xf060
		calc = (Button) findViewById(R.id.calc);
		calc.setTypeface(fontFamily);
		calc.setText("\uf1ec"); // &#xf01e &#xf1ec
		calc.setOnClickListener(this);
		searchview = (EditText) findViewById(R.id.p_search);

		TextView search = (TextView) findViewById(R.id.search);
		search.setTypeface(fontFamily);
		search.setText("\uf056"); // &#xf002 , &#xf010// [&#xf056;]
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchview.setText("");

			}
		});

		totalsellquantity = (TextView) findViewById(R.id.totalsellquantity);
		totalsellquantity.setVisibility(View.VISIBLE);

		totalsellvalue = (TextView) findViewById(R.id.totalsellvalue);
		//Spinner am_pm = (Spinner) findViewById(R.id.ampm);
		//Spinner cash_credit = (Spinner) findViewById(R.id.cashcredit);


		totalshow = (LinearLayout) findViewById(R.id.totalshow);
		// totalshow.setOnClickListener(this);
		// Button print=(Button)findViewById(R.id.print);

		//ProductListupdateAdapter.qnty = null;
		ProductListupdateAdapter.qnty = "10";
		ProductListupdateAdapter.qntyID.clear();
		ProductListupdateAdapter.qntyVal.clear();

		p_ids = new ArrayList<String>();
		p_quanty = new ArrayList<Integer>();

		mapQuantity = new HashMap<Integer, String>();
		nameSerialPair = new HashMap<String, Integer>();

		PROD_RATE = new ArrayList<String>();
		PROD_VAT = new ArrayList<String>();

		categoriesList = new ArrayList<Category>();

		Intent in = getIntent();
		Intent inten = getIntent();

		Bundle bundle = in.getExtras();

		// 5. get status value from bundle
		Bundle extra = inten.getExtras();


		extra.getString("CUST_CODE");
		bundle.getString("ORDER_DELEVERY_DATE");
		bundle.getString("ORD_NO");


		submit.setOnClickListener(this);

		back_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Inflater inf = new Inflater();
				inf.end();
				finish();
			/*	Intent intent = new Intent(ProductOrder.this,
						ReadComments.class);
				intent.putExtra("UserName", MPO_CODE);
				finishActivity(v);
				startActivity(intent);*/

			}
		});
		showorders.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchString="1";
			//	searchview.setText(null);
			//	searchview.setVisibility(LinearLayout.GONE);;
				adapter.getFilter().filter(searchString);


			}
		});




		searchview.addTextChangedListener(new TextWatcher() {

			@SuppressLint("DefaultLocale")
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				ArrayList<String> resList = new ArrayList<String>();
				ArrayList<Integer> resList2 = new ArrayList<Integer>();

				if(s.length()<1){
					 searchString = null;
				}

				else{
				 searchString = s.toString().toLowerCase();
				}

				 searchString = s.toString().toLowerCase();
				Log.i("HIRA"," searchString "+searchString+" adapter "+adapter);
				if(searchString!=null && adapter!=null){
					adapter.getFilter().filter(searchString);
				}







				/*
				ArrayList<String> resList = new ArrayList<String>();
				ArrayList<Integer> resList2 = new ArrayList<Integer>();
				String searchString = s.toString().toLowerCase();
				Log.i("HIRA"," searchString "+searchString+" adapter "+adapter);
				if(searchString!=null && adapter!=null){
					adapter.getFilter().filter(searchString);
				}

				*/



			}

			private boolean isNumeric(int test) {
				// TODO Auto-generated method stub
				return false;
			}

			private boolean isNumeric(String string) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String text = searchview.getText().toString().toLowerCase(Locale.getDefault());

			}
		});
		new GetCategories().execute();

		TextView clickme = (TextView) findViewById(R.id.clickme);
		clickme.setOnTouchListener(new View.OnTouchListener() {

			private Handler mHandler;
			private long mInitialDelay = 100;
			private long mRepeatDelay = 80;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (mHandler != null)
						return true;
					mHandler = new Handler();
					mHandler.postDelayed(mAction, mInitialDelay);
					break;
				case MotionEvent.ACTION_UP:
					if (mHandler == null)
						return true;
					mHandler.removeCallbacks(mAction);
					mHandler = null;
					break;
				}
				return false;
			}

			Runnable mAction = new Runnable() {
				@Override
				public void run() {
					// LinearLayout
					// listview=(LinearLayout)findViewById(R.id.listview);
					productListView.scrollTo(
							(int) productListView.getScrollX(),
							(int) productListView.getScrollY() + 11);
					mHandler.postDelayed(mAction, mRepeatDelay);
				}
			};

		});

		try {
			Toast myToast = new Toast(getBaseContext());

			// String displayedText =
			// (((LinearLayout)myToast.getView()).getChildAt(0)).getContext().toString();
		//	String displayedText = myToast.getView().getContext().toString();
		//	totalsellquantity.setText("--" + displayedText);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/*-------------- Extra Menus--------------*/
	/*
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
			//Intent i = new Intent(this, Login.class);
			//i.putExtra("UserName_1",userName_1);
			//i.putExtra("UserName",userName);
			//startActivity(i);
			//return true;
			finish();
		default:
			return super.onOptionsItemSelected(item);


		}
	}
*/
	/*------------Extra menus end-------------------*/


	public void finishActivity(View v) {
		finish();
	}

	/**
	 * Adding spinner data
	 * */

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// System.out.println("EditTxtID " + ProductListupdateAdapter.editTxtID.size());
		super.onResume();
	}

	private void populateSpinner() {

		lables = new ArrayList<String>();
		quanty = new ArrayList<Integer>();
		sl = new ArrayList<String>();

		for (int i = 0; i < categoriesList.size(); i++) {
			lables.add(categoriesList.get(i).getName());
			sl.add(categoriesList.get(i).getsl());
			// p_ids.add(categoriesList.get(i).getId());

			p_ids.add(categoriesList.get(i).getId());
			PROD_RATE.add(categoriesList.get(i).getPROD_RATE());
			PROD_VAT.add(categoriesList.get(i).getPROD_VAT());

			nameSerialPair.put(categoriesList.get(i).getName(),
					Integer.parseInt(categoriesList.get(i).getsl()));

			// p_quanty.add(categoriesList.get(i).getQuantity());

			// Log.i("OPSONIN", " P_ID " + categoriesList.get(i).getId());

			int p_serial = Integer.parseInt(categoriesList.get(i).getsl());
			// quanty.add(Integer.parseInt(categoriesList.get(i).getQuantity()));
			quanty.add(categoriesList.get(i).getQuantity());
			mapQuantity.put(p_serial,
					String.valueOf(categoriesList.get(i).getQuantity()));

			// quanty.add(10.0f);
		}

		adapter = new ProductListupdateAdapter(ProductupdateOrder.this, sl, lables, mapQuantity);

		// productListView.setSelection(300);
		productListView.setAdapter(adapter);

	}

	/**
	 * Async task to get all food categories
	 * */
	private class GetCategories extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ProductupdateOrder.this);
			pDialog.setTitle("Please wait !");
			pDialog.setMessage("Loading Products..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			//ServiceHandler jsonParser = new ServiceHandler();
			//String json = jsonParser.makeServiceCall(URL_CATEGORIES,ServiceHandler.GET);

			Bundle b = getIntent().getExtras();
			String ORD_NO=b.getString("ORD_NO");
			String MPO_CODE=b.getString("MPO_CODE");
			String cash_credit = b.getString("cash_credit");
			//extra.getString("cash_credit");
			//ServiceHandler jsonParser = new ServiceHandler();
			//String json = null;

			if (cash_credit.equals("Campaign Credit")) {



				  List<NameValuePair>params=new ArrayList<NameValuePair>();
				    params.add(new BasicNameValuePair("id",MPO_CODE));
				    params.add(new BasicNameValuePair("ORD_NO",ORD_NO));
				    ServiceHandler jsonParser=new ServiceHandler();
				    String json=jsonParser.makeServiceCall(campaign_credit, ServiceHandler.POST, params);
				Log.e("Response: ", "> " + json);

				if (json != null) {
					try {
						JSONObject jsonObj = new JSONObject(json);
						if (jsonObj != null) {
							JSONArray categories = jsonObj
									.getJSONArray("categories");
							for (int i = 0; i < categories.length(); i++) {
								JSONObject catObj = (JSONObject) categories.get(i);
								Category cat = new Category(catObj.getString("sl"),
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
				}





			}else{



			  List<NameValuePair>params=new ArrayList<NameValuePair>();
			    params.add(new BasicNameValuePair("id",MPO_CODE));
			    params.add(new BasicNameValuePair("ORD_NO",ORD_NO));
			    ServiceHandler jsonParser=new ServiceHandler();
			    String json=jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.POST, params);
			Log.e("Response: ", "> " + json);

			if (json != null) {
				try {
					JSONObject jsonObj = new JSONObject(json);
					if (jsonObj != null) {
						JSONArray categories = jsonObj
								.getJSONArray("categories");
						for (int i = 0; i < categories.length(); i++) {
							JSONObject catObj = (JSONObject) categories.get(i);
							Category cat = new Category(catObj.getString("sl"),
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
			}


			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			populateSpinner();
		}

	}

	/*------------- list items on click event----------------*/

	/**
	 * Async task to create a new food category
	 * */
	private class AddNewCategory extends AsyncTask<String, Void, Void> {

		boolean isNewCategoryCreated = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ProductupdateOrder.this);
			pDialog.setMessage("Creating new category..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... arg) {

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog.isShowing())
				pDialog.dismiss();
			if (isNewCategoryCreated) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// fetching all categories
						new GetCategories().execute();
					}
				});
			}
		}
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == submit.getId()) {
			if (!NetInfo.isOnline(getBaseContext())) {
				Toast.makeText(getBaseContext(), "Internet conection failed",
						Toast.LENGTH_SHORT).show();
				final ProgressDialog progress = ProgressDialog.show(this,
						"Saving Data Offline", "Please Wait..", true);

				SaveToDataBase();

				for (int i = 0; i < DatabaseQueryHelper.getAll().size(); i++) {
					Log.w("quantity", ""+DatabaseQueryHelper.getAll().get(i).quantity);
				}

				return;
			}
			jsonParser = new JSONParser();
			params = new ArrayList<NameValuePair>();
			Intent in = getIntent();
			Intent inten = getIntent();
			Bundle bundle = in.getExtras();
			Bundle extra = inten.getExtras();
			String CUST_CODE = extra.getString("CUST_CODE");
			String  ORD_NO = extra.getString("ORD_NO");

			final String message_1 = extra.getString("message_1");
			String AM_PM = extra.getString("AM_PM");

			String cash_credit = extra.getString("cash_credit");



			String MPO_CODE = extra.getString("MPO_CODE");
			String ORDER_DELEVERY_DATE = bundle.getString("ORDER_DELEVERY_DATE");
			String last_quantity = ProductListupdateAdapter.qnty;
			int last_position = ProductListupdateAdapter.last_position;
			if (last_quantity != null) {
				ProductListupdateAdapter.dataSet.put(last_position, last_quantity);
				ProductListupdateAdapter.qntyID.add(last_position);
				ProductListupdateAdapter.qntyVal.add(last_quantity);
				ProductListupdateAdapter.set2.add(last_position);
			}
			if (ProductListupdateAdapter.qntyID.size() < 1) {
				Toast.makeText(this, "No item inserted", Toast.LENGTH_SHORT)
						.show();
			} else {
				String qnty;
				ArrayList<Integer> position = new ArrayList<Integer>();
				for (int j : ProductListupdateAdapter.set2) {
					position.add(j);
				}
				int k=0;
				for (int i = 1; i < ProductListupdateAdapter.p_quanty.size(); i++) {
					int value = Integer.parseInt(ProductListupdateAdapter.p_quanty.get(i));

					if (value > 0) {
						k=k+1;
						int qnty1 = Integer.parseInt(ProductListupdateAdapter.p_quanty.get(i));

						params.add(new BasicNameValuePair("total", String.valueOf(k)));
						params.add(new BasicNameValuePair("id"+ String.valueOf(k), p_ids.get(i - 1)));
						params.add(new BasicNameValuePair("ORD_QNTY"+ String.valueOf(k),ProductListupdateAdapter.p_quanty.get(i)));
						params.add(new BasicNameValuePair("PROD_RATE"+ String.valueOf(k), PROD_RATE.get(i - 1)));
						params.add(new BasicNameValuePair("PROD_VAT"+ String.valueOf(k), PROD_VAT.get((i - 1))));

						System.out.println("id--k"+String.valueOf(k)+"="+ p_ids.get(k- 1));
						System.out.println("total"+"="+ String.valueOf(k));

						System.out.println("id"+String.valueOf(k)+"="+ p_ids.get(i- 1));
						System.out.println("ORD_QNTY"+String.valueOf(k)+"="+ ProductListupdateAdapter.p_quanty.get(i));
						System.out.println("PROD_RATE"+String.valueOf(k)+"="+ PROD_RATE.get(i- 1));
						System.out.println("PROD_VAT"+String.valueOf(k)+"="+ PROD_VAT.get(i- 1));



					}

				}

				params.add(new BasicNameValuePair("MPO_CODE", MPO_CODE));
				params.add(new BasicNameValuePair("CUST_CODE", CUST_CODE));
				params.add(new BasicNameValuePair("ORD_NO", ORD_NO));
				params.add(new BasicNameValuePair("PAY_MODE", cash_credit));
				params.add(new BasicNameValuePair("AM_PM", AM_PM));

				params.add(new BasicNameValuePair("ORDER_DELEVERY_DATE",
						ORDER_DELEVERY_DATE));
				final ProgressDialog progress = ProgressDialog.show(this,
						"Saving Data", "Please Wait..", true);
				Thread server = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						JSONObject json = jsonParser.makeHttpRequest(
								URL_NEW_CATEGORY, "POST", params);
						progress.dismiss();

						try {
							success = json.getInt(TAG_SUCCESS);
							message = json.getString(TAG_MESSAGE);
							message_2 = json.getString(TAG_MESSAGE_2);
							//message_ = json.getString(TAG_MESSAGE_1);

							//	message_1 = json.getString(TAG_MESSAGE_1);
							// //check why you get success 0

							Log.w("please wait ...." + message, json.toString());
							if (success == 1) {

								// startActivity(i);
								ProductListupdateAdapter.qnty = null;
								ProductListupdateAdapter.qntyID.clear();
								ProductListupdateAdapter.qntyVal.clear();
								ProductListupdateAdapter.set2.clear();
								ProductListupdateAdapter.dataSet.clear();
								ProductListupdateAdapter.p_quanty.clear();
								ProductListupdateAdapter.mProductSerialList.clear();
							}else {
								SaveToDataBase();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Intent in = getIntent();
						Intent inten = getIntent();
						Bundle bundle = in.getExtras();
						inten.getExtras();
						String MPO_CODE = bundle.getString("MPO_CODE");
						Intent sameint = new Intent(ProductupdateOrder.this, ReadComments.class);
						sameint.putExtra("UserName", MPO_CODE);
						sameint.putExtra("Ord_NO", message);
						sameint.putExtra("UserName_1", message_1);
						sameint.putExtra("UserName_2", message_2);


					//	sameint.putExtra("message_", message_1);
						startActivity(sameint);
					//finish();

					}

				});

				server.start();
			}

		}
		if (v.getId() == calc.getId()) {

			float sum = 0f;
			int qnty1 = 0;
			float rate = 0f;
			float product_value = 0f;
			int total_item = ProductListupdateAdapter.p_quanty.size();
			// System.out.println("total_item " + total_item);
			for (int i = 1; i < total_item; i++) {
				int value = Integer
						.parseInt(ProductListupdateAdapter.p_quanty.get(i));
				// System.out.println("value " + value);

				if (value > 0) {

					qnty1 = Integer
							.parseInt(ProductListupdateAdapter.p_quanty.get(i));
					rate = Float.parseFloat(PROD_RATE.get(i - 1));
					System.out.println("qnty1 " + qnty1);
					System.out.println("rate " + rate);
					product_value = qnty1 * rate;
					sum = sum + product_value;
					System.out.println("sum " + sum);

				}

			}
			totalsellquantity.setVisibility(View.VISIBLE);
			String total_value = String.format("%.02f", sum);
			totalsellquantity.setText( total_value);

		}

	}

	@SuppressLint("SimpleDateFormat")
	private void SaveToDataBase() {
		for (int i = 1; i < ProductListupdateAdapter.p_quanty.size(); i++) {
			int value = Integer.parseInt(ProductListupdateAdapter.p_quanty.get(i));
			Intent in = getIntent();
			int max=SharedPreferencesHelper.get_max(getBaseContext());

			Intent inten = getIntent();
			Bundle bundle = in.getExtras();
			Bundle extra = inten.getExtras();
			String CUST_CODE = extra.getString("CUST_CODE");
			String AM_PM = extra.getString("AM_PM");
			String MPO_CODE = extra.getString("MPO_CODE");
			String ORDER_DELEVERY_DATE = bundle
					.getString("ORDER_DELEVERY_DATE");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String currentDateandTime = sdf.format(new Date());
			if (value>0) {
				DatabaseQueryHelper.SaveSingleOrderDatabase(p_ids.get(i - 1),
						value, CUST_CODE, ORDER_DELEVERY_DATE,
						""+max, ""
								+ currentDateandTime, MPO_CODE, AM_PM);
				SharedPreferencesHelper.set_max(getBaseContext(), max+1);
			}


		}
	}

	protected void onPostExecute() {

	}

}
