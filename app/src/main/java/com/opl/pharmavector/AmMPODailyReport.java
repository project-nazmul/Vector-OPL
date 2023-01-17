

 //MPODailyReport
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
 import com.opl.pharmavector.R;

 import android.app.Activity;
 import android.app.DatePickerDialog;
 import android.app.ProgressDialog;
 import android.content.Intent;
 import android.graphics.Color;
 import android.graphics.Typeface;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.LinearLayout;
 import android.widget.ListView;
 import android.widget.TextView;
 import android.widget.Toast;

 //MpoDcrReportValAdapter
 public class AmMPODailyReport extends Activity implements OnClickListener {
     private static Activity parent;
     public static final String TAG_SUCCESS = "success";
     public static final String TAG_MESSAGE = "message";
     // array list for spinner adapter
     private ArrayList<AmCategory3> categoriesList;
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
     public static ArrayList<String> sl;
     public static ArrayList<String> p_ids;
     public static ArrayList<Integer> p_quanty;
     public static ArrayList<String> PROD_RATE;
     public static ArrayList<String> PROD_VAT;
     public static ArrayList<String> PROD_VAT_2;
     public static ArrayList<String> PROD_VAT_3;
     public static ArrayList<String> PROD_VAT_4;
     public android.widget.Spinner mpo;
     ProgressDialog pDialog2;
     private ArrayList<String> array_sort = new ArrayList<String>();
     private final String URL_PRODUCT_VIEW =BASE_URL+"area_manager_api/am_dcr_followup/MPODailyReport.php";
     private final String URL_MPO = BASE_URL+"area_manager_api/am_dcr_followup/getmpoforam.php";
     private ArrayList<com.opl.pharmavector.AmCustomer> mpolist;

     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);


       //  setContentView(R.layout.dcrreport);

         setContentView(R.layout.ammpodcrreport);

         Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");
         productListView = (ListView) findViewById(R.id.pListView);
         Button back_btn = (Button) findViewById(R.id.backbt);
         Button view_btn = (Button) findViewById(R.id.view);
         Button submitBtn = (Button) findViewById(R.id.submitBtn);

         Button submitBtn_2 = (Button) findViewById(R.id.submitBtn_2);


         fromdate = (TextView) findViewById(R.id.fromdate);
         todate = (TextView) findViewById(R.id.todate);

         mpo = (android.widget.Spinner) findViewById(R.id.mpo);
         mpo.setPrompt("List of Mpo");

         mpo.setSelected(false);  // must
         mpo.setSelection(0,true);  //must
         mpolist = new ArrayList<com.opl.pharmavector.AmCustomer>();

         back_btn.setTypeface(fontFamily);
         back_btn.setText("\uf060 ");// &#xf060
         final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);
         totqty = (TextView) findViewById(R.id.totalsellquantity);
         totval = (TextView) findViewById(R.id.totalsellvalue);
         int listsize = productListView.getChildCount();
         p_ids = new ArrayList<String>();
         p_quanty = new ArrayList<Integer>();
         PROD_RATE = new ArrayList<String>();
         categoriesList = new ArrayList<AmCategory3>();
         Bundle b = getIntent().getExtras();
         String userName = b.getString("UserName");
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






         new GetEmp().execute();

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

                 new DatePickerDialog(AmMPODailyReport.this, date_form, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                         myCalendar.get(Calendar.DAY_OF_MONTH)).show();
             }
         });

         /*---------------------------from date range-----------------end-----------*/


         mpo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                 String  mar_name= mpo.getSelectedItem().toString();
                 String mar[] = mar_name.split("-");
                 String mpoforam=mar[0].trim();


                 Log.e("listofmpo ", "> " +mpo.getSelectedItem().toString());
                 Log.e("mpoforam ", "> " + mpoforam);


             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

                 // sometimes you need nothing here
             }
         });






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

                 new DatePickerDialog(AmMPODailyReport.this, date_to, myCalendar
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

                             Intent i = new Intent(AmMPODailyReport.this,  AmReportDashboard.class);
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



         submitBtn_2.setOnClickListener(new OnClickListener() {


             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             @Override
             public void onClick(final View v) {
                 try {
                     String fromdate1=fromdate.getText().toString();
                     String todate1=todate.getText().toString();

                     String  mar_name= mpo.getSelectedItem().toString();
                     String mar[] = mar_name.split("-");
                     String mpoforam=mar[0].trim();


                    // String mpoforam= mpo.getSelectedItem().toString();



                     System.out.println("else  fromdate1 "+fromdate1);
                     System.out.println("else todate1"+todate1);

                     System.out.println("mpoforam"+mpoforam);
                     Log.e("mpoforam","mpoforam"+mpoforam);

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
				finish();
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	*/

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
             ArrayList<String> sl = new ArrayList<String>();
             ArrayList<String> lables = new ArrayList<String>();
             ArrayList<String> quanty = new ArrayList<String>();
             ArrayList<String> value = new ArrayList<String>();
             ArrayList<String> value4 = new ArrayList<String>();
             ArrayList<String> value5 = new ArrayList<String>();
             ArrayList<String> value6 = new ArrayList<String>();
             ArrayList<String> value7 = new ArrayList<String>();

             int quantity = 0;
             float prod_rate, prod_vat, sellvalue;
             String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, sellvalue_2,sellvalue_3;



             for (int i = 0; i < categoriesList.size(); i++) {
                 Log.i("OPSONIN", " P_ID " + categoriesList.get(i).getId());
                 Log.i("OPSONIN--", " P_ID " + categoriesList.get(i).getsl());

                 sl.add(categoriesList.get(i).getsl());

                 lables.add(categoriesList.get(i).getName());
                 p_ids.add(categoriesList.get(i).getId());
                 quanty.add(categoriesList.get(i).getQuantity());

                 //quantity = categoriesList.get(i).getQuantity();
                 prod_rate_1 = categoriesList.get(i).getPROD_RATE();
                 sellvalue_2 = prod_rate_1;
                 value.add(sellvalue_2);

                 prod_vat_1= categoriesList.get(i).getPROD_VAT();
                 value4.add(prod_vat_1);


                 prod_vat_2= categoriesList.get(i).getPROD_VAT_2();
                 value5.add(prod_vat_2);


                 prod_vat_3= categoriesList.get(i).getPROD_VAT_3();
                 value6.add(prod_vat_3);

                 prod_vat_4= categoriesList.get(i).getPROD_VAT_4();
                 value7.add(prod_vat_4);


             }


             AmMpoDcrReportValAdapter adapter = new AmMpoDcrReportValAdapter(AmMPODailyReport.this,sl,lables, quanty, value,value4,value5,value6,value7);



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


         String  mar_name= mpo.getSelectedItem().toString();
         String mar[] = mar_name.split("-");
         String mpoforam=mar[0].trim();

        // String mpoforam= mpo.getSelectedItem().toString();

         String str = todate.getText().toString();
         String date_1 = str.replaceAll("[^\\d.-]", "");
         final String ord_no = mpoforam + "-" + date_1;




         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pDialog = new ProgressDialog(AmMPODailyReport.this);
             pDialog.setTitle("Data Loading !");
             pDialog.setMessage("Loading Please Wait..");
             pDialog.setCancelable(false);
             pDialog.show();
         }
         @Override
         protected Void doInBackground(Void... arg0) {
             Log.e("Response: ", ">  ");


             Log.e(" todate: ", ">  todate == "+todate1);
             Log.e(" fromdate: ", ">  fromdate ==  "+fromdate1);
             Log.e(" dcr_no: ", ">  dcr_no ==  "+ord_no);
             Log.e(" mpoforam: ", ">  mpoforam ==  "+mpoforam);
             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id = userName;



             Log.e(" id ", ">  id ==  "+id);
             List<NameValuePair> params = new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id", id));
             params.add(new BasicNameValuePair("to_date", todate1));
             params.add(new BasicNameValuePair("from_date", fromdate1));
             params.add(new BasicNameValuePair("ord_no", ord_no));
             params.add(new BasicNameValuePair("mpoid", mpoforam));

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
                             AmCategory3 cat3 = new AmCategory3(
                                     catObj.getString("sl"),
                                     catObj.getString("id"),
                                     catObj.getString("name"),
                                     catObj.getString("quantity"),
                                     catObj.getString("PROD_RATE"),
                                     catObj.getString("PROD_VAT"),
                                     catObj.getString("PROD_VAT_2"),
                                     catObj.getString("PROD_VAT_3"),
                                     catObj.getString("PROD_VAT_4"));
                             categoriesList.add(cat3);
                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             } else {
                 Log.e("JSON Data", "Didn't receive any data from server!");
                 Toast.makeText(AmMPODailyReport.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                 Toast.makeText(AmMPODailyReport.this, "Please make a order first !",Toast.LENGTH_LONG).show();
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


/*

			String sale_velue = sp.getTotalV();
			//String number = "100000000";
			double sale_velue1 = Double.parseDouble(sale_velue);
			DecimalFormat formatter = new DecimalFormat("#,##,###.00");
			String sale_velue2 = formatter.format(sale_velue1);





			String  target_value= sp.getTotalQ();
			//String number = "100000000";
			double target_value2 = Double.parseDouble(target_value);
			DecimalFormat formatter2 = new DecimalFormat("#,##,###.00");
			String target_value3 = formatter2.format(target_value2);


			totqty.setText("Target value="+target_value3);
			totval.setText("Sale value="+sale_velue2);


*/

             //totqty.setText(sp.getTotalQ());
             //totval.setText(sp.getTotalV());

         }
     }




     private void  populateSpinner2() {
         List<String> lables = new ArrayList<String>();
         for (int i = 0; i <mpolist.size(); i++) {
             lables.add(mpolist.get(i).getName());
         }

         // Creating adapter for spinner
         ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, lables);
         mpo.setAdapter(spinnerAdapter);



     }
















     class GetEmp extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pDialog2 = new ProgressDialog(AmMPODailyReport.this);
             pDialog2.setMessage("Fetching MPO..");
             pDialog2.setCancelable(false);
             pDialog2.show();

         }

         @Override
         protected Void doInBackground(Void... arg0) {


             // ServiceHandler jsonParser = new ServiceHandler();
             // String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);

             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id=userName;

             List<NameValuePair>params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id",id));
             ServiceHandler jsonParser=new ServiceHandler();
             // String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

             String json=jsonParser.makeServiceCall(URL_MPO, ServiceHandler.POST, params);

             Log.e("Response: ", "> " + json);

             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray customer = jsonObj.getJSONArray("customer");
                         for (int i = 0; i < customer.length(); i++) {
                             JSONObject catObj = (JSONObject) customer.get(i);
                             com.opl.pharmavector.AmCustomer custo = new com.opl.pharmavector.AmCustomer(catObj.getInt("id"),catObj.getString("name"));
                          //   visitorlist.add(custo);
                             mpolist.add(custo);
                         }
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
             pDialog2.dismiss();
             populateSpinner2();
         }

     }















     /*------------- list items on click event----------------*/
     @Override
     public void onClick(View v) {
     }

     protected void onPostExecute() {
     }


     private void view() {
         Intent i = new Intent(AmMPODailyReport.this, AmReport.class);
         startActivity(i);
         finish();

     }

 }
