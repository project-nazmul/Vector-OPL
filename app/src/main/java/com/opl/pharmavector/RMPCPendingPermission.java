 //RMPCPendingPermission


 //RMPCPermission


 package com.opl.pharmavector;
 import android.app.Activity;
 import android.app.DatePickerDialog;
 import android.app.ProgressDialog;
 import android.content.Context;
 import android.content.Intent;
 import android.graphics.Typeface;
 import android.net.ParseException;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.text.InputType;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.view.WindowManager;
 import android.view.inputmethod.InputMethodManager;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.LinearLayout;
 import android.widget.ListView;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.List;
 import java.util.Locale;
 import androidx.appcompat.app.AppCompatActivity;

 import com.google.api.client.util.StringUtils;
 import com.opl.pharmavector.R;

 import com.opl.pharmavector.adapter.CategoryAdapter8;
 import com.opl.pharmavector.adapter.CategoryAdapter3;
 import com.opl.pharmavector.model.Category;
 import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser10;

 import org.apache.http.NameValuePair;
 import org.apache.http.message.BasicNameValuePair;
 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;
 import java.text.SimpleDateFormat;
 import java.util.Date;

 import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
 import static com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser10.selectedCategories;

 import static java.lang.String.valueOf;

 public class RMPCPendingPermission extends AppCompatActivity  {
     Context context;
     ArrayList<Category> array_list;

     FavouriteCategoriesJsonParser10 categoryJsonParser;


     String categoriesCsv;
     String categoriesPhone;

     private RadioGroup radio_pc;
     private RadioButton radio_regular;
     private RadioButton radio_special;

     public static final String TAG_SUCCESS = "success";
     public static final String TAG_MESSAGE = "message";
     public static final String TAG_MESSAGE_1 = "message_1";
     public static final String TAG_invoice = "invoice";
     public static final String TAG_target = "target";
     public static final String TAG_achivement = "achivement";
     public static final String TAG_MESSAGE_new_version = "new_version";
     public int success;
     public String message, ord_no,invoice,target_data,achivement,searchString,select_party,my_val,conf_type_val;
     private String pc_conference_submit = BASE_URL+"pc_conference_submit.php";
     private ArrayList<Customer> checkdatelist;
     private ArrayList<Customer> checkdatelist2;


     private String pc_conference_permission_rm = BASE_URL+"pc_conference_permission_rm.php";
     private String pc_conference_permission_delete = BASE_URL+"pc_conference_permission_delete.php";
     private String pending_pc_count_for_rm = BASE_URL+"pending_pc_count_for_rm.php";
     private String pending_month_detect = BASE_URL+"pending_month_detect.php";

     EditText conference_date;
     public String product_list;
     public static String RM_CODE;
     public static String proposed_conference_date;
     public static String conf_type;
     public static String proposed_conference_date2;
     private RadioGroup radioSexGroup;
     private RadioButton radioSexButton;
     private Button btnDisplay;
     public String monthYearStr;
     public String get_ext_dt,date_flag,check_flag,pending_month;

     SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
     SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");

     public String monthPicker;
     public String year_val,month_val;
     public TextView succ_msg;

     public String month_name_val;
     public ListView mListViewBooks;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        // setContentView(R.layout.proposal_activity_main);

         setContentView(R.layout.rm_pending_permission);


         InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
         inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

         Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
         final  Button delete_mpo = (Button) findViewById(R.id.delete_mpo);
         final Button submit = (Button) findViewById(R.id.selectCategoryButton);
         final Button back = (Button) findViewById(R.id.back);
         final Button permision = (Button) findViewById(R.id.permision);
         final  Button edit = (Button) findViewById(R.id.edit);
         final LinearLayout layout1 =(LinearLayout) findViewById(R.id.slistLayout);

         submit.setVisibility(View.GONE);
         delete_mpo.setVisibility(View.GONE);

         succ_msg=(TextView)findViewById(R.id.succ_msg);
         succ_msg.setVisibility(View.GONE);



         layout1.setVisibility(View.GONE);

         radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
         radioSexGroup.setVisibility(View.GONE);

         checkdatelist = new ArrayList<Customer>();
         checkdatelist2 = new ArrayList<Customer>();

         context = this;



         Bundle b = getIntent().getExtras();
         RM_CODE =b.getString("UserName");
         final String rm_area = b.getString("UserName_2");

         TextView user_show = (TextView) findViewById(R.id.user_show);
         conference_date =  findViewById(R.id.conference_date);
         conference_date.setFocusableInTouchMode(true);
         conference_date.setFocusable(true);
         conference_date.requestFocus();
         conference_date.setClickable(true);
         conference_date.setInputType(InputType.TYPE_NULL);

         user_show.setText(RM_CODE+ " - "+ rm_area +" "  );
         categoriesCsv="";

         back.setTypeface(fontFamily);
         back.setText("\uf08b");

         permision.setEnabled(false);
         edit.setEnabled(false);


         back.setOnClickListener(new View.OnClickListener() {
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
                             Intent i = new Intent(RMPCPendingPermission.this, PCDashboard.class);
                             i.putExtra("UserName", RM_CODE);
                             i.putExtra("new_version", RM_CODE);
                             i.putExtra("UserName_1", RM_CODE);
                             i.putExtra("UserName_2", rm_area);
                             i.putExtra("userName", RM_CODE);
                             i.putExtra("new_version", RM_CODE);
                             i.putExtra("userName_1", rm_area);
                             i.putExtra("userName_2", rm_area);
                             i.putExtra("user_flag", "R");
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



         new PendingMonth().execute();
         permision.setEnabled(true);
         edit.setEnabled(true);



/*
         conference_date.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                 pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                         monthYearStr = year + "-" + (month + 1) + "-" + i2;
                         conference_date.setText(formatMonthYear(monthYearStr));
                         Log.e("monthyear",monthYearStr);
                         String test = monthYearStr;
                         year_val=test.substring(0,4);
                         int month_int=  month ;
                         month_val= String.valueOf(month_int);
                         // proposed_conference_date= year + "/" + (month + 1) + "/" + 01;
                         proposed_conference_date= "01" + "/" + (month) + "/" + year;
                         proposed_conference_date2 = "31" + "/" + (month) + "/" + year;
                         if(month_val== String.valueOf(1)){
                             month_name_val="January";
                         }  else if(month_val== String.valueOf(2)){
                             month_name_val="Feb";
                         }
                         else if(month_val== String.valueOf(3)){
                             month_name_val="March";
                         } else if(month_val== String.valueOf(4)){
                             month_name_val="April";
                         } else if(month_val== String.valueOf(5)){
                             month_name_val="May";
                         } else if(month_val== String.valueOf(6)){
                             month_name_val="June";
                         } else if(month_val== String.valueOf(7)){
                             month_name_val="July";
                         } else if(month_val== String.valueOf(8)){
                             month_name_val="August";
                         }
                         else if(month_val== String.valueOf(9)){
                             month_name_val="September";
                         } else if(month_val== String.valueOf(10)){
                             month_name_val="October";
                         } else if(month_val== String.valueOf(11)){
                             month_name_val="November";
                         }
                         else if(month_val== String.valueOf(12)){
                             month_name_val="December";
                         }
                         Log.e("proposed_confere_date",proposed_conference_date);
                         permision.setEnabled(true);
                         edit.setEnabled(true);
                     }
                 });
                 pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");


             }
         });


         */


         permision.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 Bundle b = getIntent().getExtras();
                 int selectedId = radioSexGroup.getCheckedRadioButtonId();
                 radioSexButton = (RadioButton) findViewById(selectedId);
                 Toast.makeText(RMPCPendingPermission.this, radioSexButton.getText(), Toast.LENGTH_SHORT).show();
                 Date c = Calendar.getInstance().getTime();
                 SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                 String Today = df.format(c);
                 Date Today_date1= null;
                 try {
                     Today_date1 = new SimpleDateFormat("dd/MM/yyyy").parse(Today);
                 } catch (java.text.ParseException e) {
                     e.printStackTrace();
                 }
                 System.out.println(Today_date1+"\t"+Today_date1);
                 Date Conference_date1= null;
                 try {
                     Conference_date1 = new SimpleDateFormat("dd/MM/yyyy").parse(proposed_conference_date2);
                 } catch (java.text.ParseException e) {
                     e.printStackTrace();
                 }
                 System.out.println(Conference_date1+"\t"+Conference_date1);
                 if (Today_date1.after(Conference_date1)) {
                     conf_type="P";
                     layout1.setVisibility(View.VISIBLE);
                     submit.setVisibility(View.VISIBLE);
                     delete_mpo.setVisibility(View.GONE);
                     new asyncTask_getCategories().execute();
                     new GeTPcConferenceDate().execute();

                 }
                 else {

                     LayoutInflater li = getLayoutInflater();
                     View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                     Toast toast = new Toast(getApplicationContext());
                     TextView text = (TextView) layout.findViewById(R.id.text);
                     text.setText("You can only assign from pending.");
                     toast.setDuration(Toast.LENGTH_LONG);
                     toast.setView(layout);//setting the view of custom toast layout
                     toast.show();

                 }

             }
         });



         submit.setOnClickListener(new View.OnClickListener() {


             public void onClick(View v) {

                 categoriesCsv = selectedCategories.toString();
                 categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);
                 Bundle b = getIntent().getExtras();
                 int commas = 0;

                 for(int i = 0; i < categoriesCsv.length(); i++) {
                     if(categoriesCsv.charAt(i) == ',') commas++;
                 }


                 int count_submit_mpo=(commas+1);



                 int already_submit_mpo=Integer.valueOf(date_flag);






                 if ( (categoriesCsv.length())<1) {
                     Toast.makeText(context, "Please Select MPO", Toast.LENGTH_SHORT).show();
                 }


                 else if(count_submit_mpo > already_submit_mpo ){

                     Toast.makeText(context, "You can not assign more than your Pending Conference", Toast.LENGTH_SHORT).show();

                 }



                 else {


                     Thread server = new Thread(new Runnable() {

                         @Override
                         public void run() {

                             JSONParser jsonParser = new JSONParser();
                             Bundle b = getIntent().getExtras();
                             final String UserName = b.getString("UserName_2");
                             final String UserName_2 = b.getString("UserName_2");


                             conf_type="P";
                             List<NameValuePair> params = new ArrayList<NameValuePair>();

                             params.add(new BasicNameValuePair("categoriesCsv", categoriesCsv));
                             params.add(new BasicNameValuePair("RM_CODE", RM_CODE));
                             params.add(new BasicNameValuePair("CONF_TYPE", conf_type));
                             params.add(new BasicNameValuePair("CONF_DATE", proposed_conference_date));

                             JSONObject json = jsonParser.makeHttpRequest(pc_conference_permission_rm, "POST", params);

                             try {
                                 success = json.getInt(TAG_SUCCESS);
                                 message = json.getString(TAG_MESSAGE);

                             } catch (JSONException e) {
                                 // TODO Auto-generated catch block
                                 e.printStackTrace();

                             }

                             Intent in = getIntent();
                             Intent inten = getIntent();
                             Bundle bundle = in.getExtras();
                             inten.getExtras();
                             Intent sameint = new Intent(RMPCPendingPermission.this, PCDashboard.class);
                             sameint.putExtra("UserName", RM_CODE);
                             sameint.putExtra("UserName_2", UserName_2);
                             sameint.putExtra("user_flag", "R");

                             startActivity(sameint);

                             selectedCategories.clear();




                         }
                     });
                     server.start();











                 }
             }
         });





     }




     String formatMonthYear(String str) {
         Date date = null;
         try {
             date = input.parse(str);
         } catch (ParseException e) {
             e.printStackTrace();
         } catch (java.text.ParseException e) {
             e.printStackTrace();
         }
         return sdf.format(date);
     }










     public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
         // ProgressDialog dialog = new ProgressDialog(context);

         @Override
         protected void onPreExecute() {

             array_list = new ArrayList<>();
             my_val=RM_CODE;
             conf_type_val=conf_type;
             categoryJsonParser = new FavouriteCategoriesJsonParser10();
             super.onPreExecute();
         }

         @Override
         protected Void doInBackground(Void... params) {


             array_list = categoryJsonParser.getParsedCategories();
             return null;
         }

         @Override
         protected void onPostExecute(Void s) {

             mListViewBooks = (ListView) findViewById(R.id.category_listView);
             final CategoryAdapter8 categoryAdapter = new CategoryAdapter8(context, R.layout.row_category, array_list);
             mListViewBooks.setAdapter(categoryAdapter);
             super.onPostExecute(s);

         }

     }





     private void  pc_pending_month_detect() {

         List<String> lables = new ArrayList<String>();
         for (int i = 0; i <checkdatelist2.size(); i++) {
             pending_month= checkdatelist2.get(i).getName();
         }



         monthYearStr=pending_month;

         String monthYearStr2=pending_month;
         conference_date.setText(formatMonthYear(monthYearStr2));

         String test = monthYearStr;


         year_val=test.substring(0,4);

         int firstIndex = test.indexOf("-");
         int lastIndex = test.lastIndexOf("-");
         month_val= test.substring(firstIndex+1, lastIndex);


         proposed_conference_date= "01" + "/" + (month_val) + "/" +  year_val;
         proposed_conference_date2 = "31" + "/" + (month_val) + "/" +  year_val;


         if(month_val.equals("1")){
             month_name_val="January";
         }

         else if(month_val.equals("2")){
             month_name_val="Feb";
         }
         else if(month_val.equals("3")){
             month_name_val="March";
         } else if(month_val.equals("4")){
             month_name_val="April";
         } else if(month_val.equals("5")){
             month_name_val="May";
         } else if(month_val.equals("6")){
             month_name_val="June";
         } else if(month_val.equals(valueOf(7))){
             month_name_val="July";
         } else if(month_val.equals(valueOf(8))){
             month_name_val="August";
         }
         else if(month_val.equals(valueOf(9))){
             month_name_val="September";
         } else if(month_val.equals(valueOf(10))){
             month_name_val="October";
         } else if(month_val.equals(valueOf(11))){
             month_name_val="November";
         }
         else if(month_val.equals(valueOf(12))){
             month_name_val="December";
         }





     }





     private void  pc_conference_date_check() {
         date_flag="0";
         List<String> lables = new ArrayList<String>();
         for (int i = 0; i <checkdatelist.size(); i++) {
             date_flag= checkdatelist.get(i).getName();
         }
         succ_msg.setVisibility(View.VISIBLE);
         succ_msg.setText("Your Pending Conference for "+ month_name_val +"  is " + date_flag);

     }












     class PendingMonth extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();

         }

         @Override
         protected Void doInBackground(Void... arg0) {

             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id=userName;

             List<NameValuePair>params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id",RM_CODE));
             Log.e("Response: ", "> " +RM_CODE +"-----------"+proposed_conference_date );
             ServiceHandler jsonParser=new ServiceHandler();


             String json=jsonParser.makeServiceCall(pending_month_detect, ServiceHandler.POST, params);

             Log.e("Response: ", "> " + json);

             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray customer = jsonObj.getJSONArray("customer");
                         for (int i = 0;i < 1; i++) {
                             JSONObject catObj = (JSONObject) customer.get(i);
                             Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
                             checkdatelist2.add(custo);

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
             //    pDialog2.dismiss();
             pc_pending_month_detect();
         }

     }


     class GeTPcConferenceDate extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();

         }

         @Override
         protected Void doInBackground(Void... arg0) {

             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id=userName;

             List<NameValuePair>params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id",RM_CODE));
             params.add(new BasicNameValuePair("proposed_conference_date",proposed_conference_date));

             Log.e("Response: ", "> " +RM_CODE +"-----------"+proposed_conference_date );


             ServiceHandler jsonParser=new ServiceHandler();


             String json=jsonParser.makeServiceCall(pending_pc_count_for_rm, ServiceHandler.POST, params);

             Log.e("Response: ", "> " + json);

             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray customer = jsonObj.getJSONArray("customer");
                         for (int i = 0;i < 1; i++) {
                             JSONObject catObj = (JSONObject) customer.get(i);
                             Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
                             checkdatelist.add(custo);

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
             //    pDialog2.dismiss();
             pc_conference_date_check();
         }

     }


 }










