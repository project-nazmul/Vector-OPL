 //ExamAnswerBoard
 package com.opl.pharmavector.exam;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Locale;
 import java.util.zip.Inflater;

 import org.apache.http.NameValuePair;
 import org.apache.http.message.BasicNameValuePair;
 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;
 import android.annotation.SuppressLint;
 import android.app.Activity;
 import android.app.ProgressDialog;
 import android.content.ActivityNotFoundException;
 import android.content.Context;
 import android.content.Intent;
 import android.graphics.Typeface;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.os.Handler;
 import android.speech.RecognizerIntent;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.util.Log;
 import android.view.MotionEvent;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.view.WindowManager;
 import android.view.inputmethod.InputMethodManager;
 import android.widget.AdapterView;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.LinearLayout;
 import android.widget.ListView;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.opl.pharmavector.Category;
 import com.opl.pharmavector.JSONParser;
 import com.opl.pharmavector.R;
 import com.opl.pharmavector.ServiceHandler;


 public class ExamAnswerBoard extends Activity  {

     public static final String TAG_SUCCESS = "success";
     public static final String TAG_SUCCESS1 = "success_1";
     public static final String TAG_MESSAGE = "message";
     public static final String TAG_MESSAGE_1 = "message_1";
     public static final String TAG_MESSAGE_2 = "message_2";
     public static final String TAG_ord_no = "ord_no";
     public static final String TAG_invoice = "invoice";
     public static final String TAG_target = "target";
     public static final String TAG_achivement = "achivement";
     public String userName_1,userName;
     // array list for spinner adapter
     static ArrayList<Category> categoriesList;
     ProgressDialog pDialog;
     static ListView productListView;

     public String get_ext_dt3;

     public String brand_name,product_flag1;
     public int brand_quant,product_min,product_flag=0;

     public String onik;


     public String new_version,UserName_2,message_3;
     Button submit;
     // private EditText current_qnty;
     public static EditText qnty, searchview;
     EditText inputOne, inputtwo;
     public int success,success_1,ordsl;
     public String message, ord_no,invoice,target,achivement,searchString,message_1,message_2,myexamid;
     int textlength = 0;
     ExamBoardAdapter adapter;
     JSONParser jsonParser;
     List<NameValuePair> params;
     static TextView totalsellquantity;
     static TextView totalsellvalue;
     public LinearLayout totalshow;
     public Button calc;
     public String brand;
     public static ArrayList<String> p_ids;
     public static ArrayList<Integer> p_quanty;
     public static ArrayList<String> PROD_RATE;
     public static ArrayList<String> PROD_VAT;
     public static ArrayList<String> PPM_CODE;
     public static ArrayList<String> P_CODE;
     ArrayList<String> lables;
     ArrayList<Integer> quanty;
     HashMap<Integer, String> mapQuantity;
     static HashMap<String, Integer> nameSerialPair;
     ArrayList<String> sl;
     /*- Initializing*/
     String last_quantity = "1";
     int last_position = 1;
     String quantity = "1";
     public int brand_total= 0;
     Toast toast1,toast2;
     ArrayList<Category> arraylist = new ArrayList<Category>();
     private final int REQ_CODE_SPEECH_INPUT = 100;

     private String campaign_credit = "http://opsonin.com.bd:83/vectorexam/exam_answer.json";

     private ArrayList<com.opl.pharmavector.AmCustomer> mporeqdcr;
     private ArrayList<com.opl.pharmavector.AmCustomer> brand_info;



     @SuppressLint("DefaultLocale")
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

         getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
         setContentView(R.layout.exam_result_board);
         Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");


         productListView = (ListView) findViewById(R.id.pListView);
         productListView.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
         Button back_btn = (Button) findViewById(R.id.backBtn);
         back_btn.setTypeface(fontFamily);
         back_btn.setText("\uf060 ");// &#xf060
         Bundle b = getIntent().getExtras();

            userName = b.getString("UserName");
            UserName_2 = b.getString("UserName_2");
            new_version = b.getString("new_version");
            message_3 = b.getString("message_3");
            myexamid = b.getString("myexamid");

         new GetCategories().execute();
         ExamBoardAdapter.qnty = null;
         p_ids = new ArrayList<String>();
         p_quanty = new ArrayList<Integer>();
         mapQuantity = new HashMap<Integer, String>();
         nameSerialPair = new HashMap<String, Integer>();
         mporeqdcr = new ArrayList<com.opl.pharmavector.AmCustomer>();
         brand_info = new ArrayList<com.opl.pharmavector.AmCustomer>();
         PROD_RATE = new ArrayList<String>();
         PROD_VAT = new ArrayList<String>();
         PPM_CODE = new ArrayList<String>();
         P_CODE = new ArrayList<String>();
         categoriesList = new ArrayList<Category>();

         back_btn.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View v) {
                 // TODO Auto-generated method stub
                 Inflater inf = new Inflater();
                 inf.end();
                 finish();


             }
         });

     }


     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         switch (requestCode) {
             case REQ_CODE_SPEECH_INPUT: {
                 if (resultCode == RESULT_OK && null != data) {

                     ArrayList<String> result = data
                             .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                     searchview.setText(result.get(0));
                 }
                 break;
             }

         }
     }

     public void finishActivity(View v) {
         finish();
     }

     @Override
     protected void onResume() {
         // TODO Auto-generated method stub
         // System.out.println("EditTxtID " + NoticeBoardAdapter.editTxtID.size());
         super.onResume();

     }

     private void populateSpinner() {

         lables = new ArrayList<String>();
         quanty = new ArrayList<Integer>();
         sl = new ArrayList<String>();

         for (int i = 0; i < categoriesList.size(); i++) {
             lables.add(categoriesList.get(i).getName());
             sl.add(categoriesList.get(i).getsl());
             int o = Integer.parseInt(categoriesList.get(i).getsl());
             p_ids.add(categoriesList.get(i).getId());
             PROD_RATE.add(categoriesList.get(i).getPROD_RATE());
             PROD_VAT.add(categoriesList.get(i).getPROD_VAT());
             PPM_CODE.add(categoriesList.get(i).getPPM_CODE());
             P_CODE.add(categoriesList.get(i).getP_CODE());
             nameSerialPair.put(categoriesList.get(i).getName(), Integer.parseInt(categoriesList.get(i).getsl()));
             int p_serial = Integer.parseInt(categoriesList.get(i).getsl());
             quanty.add(categoriesList.get(i).getQuantity());
             mapQuantity.put(o, String.valueOf(categoriesList.get(i).getQuantity()));
         }
         adapter = new ExamBoardAdapter(ExamAnswerBoard.this, sl, lables, mapQuantity,PPM_CODE,P_CODE);


         productListView.setAdapter(adapter);

     }


     private class GetCategories extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pDialog = new ProgressDialog(ExamAnswerBoard.this);
             pDialog.setTitle("Please wait !");
             pDialog.setMessage("Answers are Loading ... ... ");
             pDialog.setCancelable(false);
             pDialog.show();

         }

         @Override
         protected Void doInBackground(Void... arg0) {

             List<NameValuePair> params = new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("MPO_CODE", userName));
             params.add(new BasicNameValuePair("CUST_CODE", userName));
             params.add(new BasicNameValuePair("myexamid", myexamid));
             ServiceHandler jsonParser = new ServiceHandler();
             String json = jsonParser.makeServiceCall(campaign_credit,ServiceHandler.GET,params);
             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray categories = jsonObj.getJSONArray("categories");
                         for (int i = 0; i < categories.length(); i++) {
                             JSONObject catObj = (JSONObject) categories.get(i);
                             Category cat = new Category(catObj.getString("sl"),
                                     catObj.getString("id"),
                                     catObj.getString("name"),
                                     catObj.getInt("quantity"),
                                     catObj.getString("PROD_RATE"),
                                     catObj.getString("PROD_VAT"),
                                     catObj.getString("PPM_CODE"),
                                     catObj.getString("P_CODE"));
                             categoriesList.add(cat);
                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             } else {

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

     protected void onPostExecute() {

     }

 }



