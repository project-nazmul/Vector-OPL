package com.opl.pharmavector.doctorgift;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.ContactsAdapter.selectedphonenumber;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.os.Looper;
import android.text.InputType;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.nativecss.NativeCSS;
import com.opl.pharmavector.Achievement;
import com.opl.pharmavector.AmCustomer;
import com.opl.pharmavector.Contact;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.DoctorGiftActivity;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.MonthYearPickerDialog;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.Updateview;
import com.opl.pharmavector.prescriptionsurvey.PrescroptionImageSearch;
import com.opl.pharmavector.stirng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import android.app.DatePickerDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


public class DocGiftLoad extends FragmentActivity implements OnItemSelectedListener {

    private Spinner spinner1, spinner2, cashcredit, cashcredit_test, credit;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    private Button logout,btn_next,btn_back;
    private TextView  newversion;
    public String get_ext_dt3;
    public Button next, back;
    public static String name = null, newversion_text = null, ename = null;
    public EditText osi, op, od, dateResult, ref;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> gift_list;
    public Array pay_cash;
    public Array pay_cradit;
    public String pay_cash1, userName, userName_1, userName_2,gift_product;
    public stirng pay_credit1;
    public String webtime1, webtime2;
    private Spinner cust;
    ProgressDialog pDialog;
    EditText ded;
    TextView  note;
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, select_party_new;
    public String cust_code_name_arr, cust_detail_new,user_flag;
    public String sl_no,gift_ppm_code,gift_ppm_name,gift_brand_name,gift_category,gift_ppm_desc,ppm_code;
    protected Handler handler;
    public ImageView medimg;
    public String convertimage;
    public byte[] imageBytes;
    public String monthYearStr;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    public static String proposed_conference_date2;
    public static String proposed_conference_date;
    public String monthPicker;
    public String year_val,month_val,UserName_2;
    private String URL_CUSOTMER = "http://opsonin.com.bd/vector_opl_v1/doctor_gift/get_gift_list.php";
    private static String url_med_info = "http://opsonin.com.bd/vector_opl_v1/doctor_gift/get_gift_image.php";
    private String url_submit = "http://opsonin.com.bd/vector_opl_v1/doctor_gift/doctor_gift_info_submit.php";
    public static final String LOGIN_URL = "http://opsonin.com.bd/dept_order_android_all/login_22.php";
    public int month_int;
    public String month_name;
    public String delivered_month,mpo_code,doctor_code;
    ArrayList<Gift> records;
    ArrayList<MPO> mporecords;
    ArrayList<Doctor> doctor_records;
    private SessionManager session;
    public AutoCompleteTextView MPOSpinner;
    public AutoCompleteTextView DoctorSpinner;
    ProgressDialog progressDialog;
    Button btn_submit;

    @SuppressLint({"CutPasteId", "ClickableViewAccessibility"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_gift_load);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = (Button) findViewById(R.id.logout);
        btn_back = (Button) findViewById(R.id.btn_back);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b");; //&#xf08b
        btn_next.setTypeface(fontFamily);
        btn_next.setText("\uf101");
        btn_next.setVisibility(View.GONE);

        TextView user_show = (TextView) findViewById(R.id.user_show);
        newversion = (TextView) findViewById(R.id.newversion);

    }
    /*============================================= End  Next Button Event click==========================================================================*/
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < records.size(); i++) {
            lables.add(records.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.sp_row_item, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }
    private void populateMPOSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < mporecords.size(); i++) {
            lables.add(mporecords.get(i).getMPOname()+" ("+mporecords.get(i).getMPOcode()+")");
        }
        String[] MPOString = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.sp_row_item, MPOString);
        MPOSpinner.setAdapter(Adapter);
        MPOSpinner.setTextColor(Color.BLUE);
    }
    private void populateDoctorSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < doctor_records.size(); i++) {
            lables.add("Dr. "+doctor_records.get(i).getDoctorName()+" ("+doctor_records.get(i).getDoctorTerriName()+")");
        }
        String[] DoctorString = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.sp_row_item, DoctorString);
        DoctorSpinner.setAdapter(Adapter);
        DoctorSpinner.setTextColor(Color.BLUE);
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


     class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DocGiftLoad.this);
            pDialog.setMessage("Loading Gifts...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            params.add(new BasicNameValuePair("proposed_conference_date", proposed_conference_date));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            gift_list.add(custo);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }

    }

     private void populateimage() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            convertimage = customerlist.get(i).getName();
        }
        imageBytes = Base64.decode(convertimage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        medimg.setImageBitmap(decodedImage);

    }

     public class GetMedicineDetails extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("gift_brand_code", "brand_code"));
            params.add(new BasicNameValuePair("gift_number", sl_no));
            params.add(new BasicNameValuePair("ppm_code", ppm_code));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(url_med_info, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            customerlist.add(custo);
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
           populateimage();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub

    }

    public void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(DocGiftLoad.this);
                pDialog.setMessage("Loading Gifts...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s!=null) {
                    try {
                        LoadGiftList(s);
                        populateSpinner();
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                    //Log.e("Error Loadding------>","No gift Aavailable in selected month");
                } else {
                    Toast.makeText(DocGiftLoad.this,"No gift available for selected month",Toast.LENGTH_LONG).show();
                    getJSON("http://opsonin.com.bd/vector_opl_v1/doctor_gift/GiftList.php");
                }
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("proposed_conference_date","UTF-8") + "=" + URLEncoder.encode(delivered_month, "UTF-8");
                    bufferedWriter.write(login_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream  = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private void LoadGiftList(String json) throws JSONException {
            JSONArray jsonArray = new JSONArray(json);
            records=new ArrayList<Gift>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Gift p=new Gift(null,null,null,null,null);
                p.setId(obj.getString("SLNO"));
                //p.setBrandCode(obj.getString("BRAND_CODE"));
                p.setPpmCode(obj.getString("PPM_CODE"));
                p.setCatagories(obj.getString("GIFT_CATEGORY"));
                p.setName(obj.getString("PPM_DESC"));
                records.add(p);

        }
    }
    public void getMPO(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(DocGiftLoad.this);
                pDialog.setMessage("Loading Gifts...");
                pDialog.setCancelable(false);
                pDialog.show();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s!=null){
                    try {
                        LoadMPO(s);
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        populateMPOSpinner();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    getMPO("http://opsonin.com.bd/vector_opl_v1/doctor_gift/MPOList.php");
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("UserName","UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
                    bufferedWriter.write(login_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream  = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    public void GetDoctorList(final String urlWebService) {

        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(DocGiftLoad.this);
                pDialog.setMessage("Loading Doctor List...");
                pDialog.setCancelable(false);
                pDialog.show();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null){
                    try {
                        LoadDoctorList(s);
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        populateDoctorSpinner();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(DocGiftLoad.this,"Please try later",Toast.LENGTH_LONG).show();
                    //("http://opsonin.com.bd/vector_opl_v1/doctor_gift/DoctorList.php");
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("UserName","UTF-8") + "=" + URLEncoder.encode(mpo_code, "UTF-8");
                    bufferedWriter.write(login_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream  = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private void LoadMPO(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        mporecords=new ArrayList<MPO>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            MPO p=new MPO(null,null);
            p.setMPOcode(obj.getString("MPO_CODE"));
            p.setMPOname(obj.getString("MPO_NAME"));
            mporecords.add(p);
        }
    }
    private void LoadDoctorList(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        doctor_records=new ArrayList<Doctor>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Doctor p=new Doctor(null,null,null);
            p.setDoctorCode(obj.getString("DOC_CODE"));
            p.setDoctorName(obj.getString("DOC_NAME"));
            p.setDoctorTerriName(obj.getString("TERRI_NAME"));
            doctor_records.add(p);
        }
    }

    private void logoutUser() {
        session.setLogin(false);
        // session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(DocGiftLoad.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }
    private void hideKeyBoard(){
        // InputMethodManager imm = (InputMethodManager) context.getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        InputMethodManager imm = (InputMethodManager) getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
