package com.opl.pharmavector;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.opl.pharmavector.R;
import com.opl.pharmavector.Contact1;
import com.opl.pharmavector.ContactsAdapter;
import com.opl.pharmavector.MyApplication;
import com.opl.pharmavector.MyDividerItemDecoration;
import com.opl.pharmavector.app.AppController;
import com.opl.pharmavector.doctorgift.DocGiftLoad;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.w3c.dom.Text;
import static com.opl.pharmavector.ContactsAdapter.selectedphonenumber;

import static com.opl.pharmavector.app.AppController.*;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

public class DoctorGiftActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {

    private static final String TAG = DoctorGiftActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Contact1> contactList;
    private ContactsAdapter mAdapter;
    private SearchView searchView;

    private static final String URL2 = BASE_URL+"doctor_gift/doctors_list_for_special_gift.php";
    private final String doctor_gift_list_submit = BASE_URL+"doctor_gift/doctor_gift_list_submit.php";


    public int success;
    public String message;
    List<NameValuePair> params;
    ProgressDialog pDialog;
    public String userName,sl_no,gift_ppm_code,gift_ppm_name,gift_brand_name,gift_brand_code,gift_category,gift_ppm_desc,UserName_2;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public String categoriesPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_gift_activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(DoctorGiftActivity.this, "hELLO oNIK", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new ContactsAdapter(this, contactList, this);

        Button btn_submit=(Button) findViewById(R.id.btn_submit);
        Bundle b = getIntent().getExtras();
        //String ordernumber=b.getString("Ord_NO");
        userName = b.getString("userName");
        gift_brand_code = b.getString("gift_brand_code");
        sl_no = b.getString("sl_no");
        UserName_2 = b.getString("UserName_2");
        Log.e("UserName_2 ", "> " + UserName_2);

        whiteNotificationBar(recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);
        new REQ_JSON().execute();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                categoriesPhone = selectedphonenumber.toString();
                categoriesPhone = categoriesPhone.substring(1, categoriesPhone.length() - 1);

                Log.e("categoriesPhone", categoriesPhone);
                if (selectedphonenumber.size() < 1) {
                    Toast.makeText(DoctorGiftActivity.this, "Please Select Doctor", Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread server = new Thread(new Runnable() {

                        @Override
                        public void run() {

                            JSONParser jsonParser = new JSONParser();
                            Log.d("MPO_CODE", userName);
                            Log.d("gift_brand_code", gift_brand_code);
                            Log.d("sl_no", sl_no);
                            Log.e("selectedphonenumber", String.valueOf(selectedphonenumber));

                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("selecteddoctorcode", categoriesPhone));
                            params.add(new BasicNameValuePair("MPO_CODE", userName));
                            params.add(new BasicNameValuePair("sl_no", sl_no));
                            params.add(new BasicNameValuePair("gift_brand_code", gift_brand_code));


                            JSONObject json = jsonParser.makeHttpRequest(doctor_gift_list_submit, "POST", params);
                            try {
                                success = json.getInt(TAG_SUCCESS);
                                message = json.getString(TAG_MESSAGE);

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                            Intent in = getIntent();
                            Intent inten = getIntent();
                            Bundle bundle = in.getExtras();
                            String parse_ordno= message+"-"+userName;

                            inten.getExtras();
                            Intent sameint = new Intent(DoctorGiftActivity.this, DocGiftLoad.class);
                            sameint.putExtra("UserName", userName);
                            sameint.putExtra("UserName_2", UserName_2);
                            sameint.putExtra("Ord_NO", parse_ordno);
                            startActivity(sameint);


                        }
                    });
                    server.start();
                }


            }
        });


    }



    public class REQ_JSON extends AsyncTask<Void, Void, Void> {

        private RequestQueue rq;
        private StringRequest sr;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(DoctorGiftActivity.this);
            pDialog.setMessage("Loading Doctors List...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {

            sr = new StringRequest(Request.Method.POST, URL2 , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response == null) {
                        Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    List<Contact1> items = new Gson().fromJson(response.toString(), new TypeToken<List<Contact1>>() { }.getType());
                    contactList.clear();
                    contactList.addAll(items);
                    mAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(DoctorGiftActivity.this,
                                "Netwrok connection not Available",
                                Toast.LENGTH_LONG).show();

                    } else if (error instanceof AuthFailureError) {

                    } else if (error instanceof ServerError) {

                        Toast.makeText(DoctorGiftActivity.this,
                                "Server connection Lost, Try later",
                                Toast.LENGTH_LONG).show();


                    } else if (error instanceof NetworkError) {

                        Toast.makeText(DoctorGiftActivity.this,
                                "Netwrok has slow connection",
                                Toast.LENGTH_LONG).show();


                    } else if (error instanceof ParseError) {

                    }

                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", userName);
                    params.put("MPO_CODE", userName);
                    return params;
                }
            };



            sr.setRetryPolicy(new DefaultRetryPolicy(
                    450000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            getInstance().addToRequestQueue(sr);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(Contact1 contact) {
        //Toast.makeText(getApplicationContext(), "Selected: " + contact.getName() + ", " + contact.getPhone(), Toast.LENGTH_LONG).show();
    }



}
