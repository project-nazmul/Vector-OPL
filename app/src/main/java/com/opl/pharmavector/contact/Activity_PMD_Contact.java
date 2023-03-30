package com.opl.pharmavector.contact;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.Customer;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RecyclerData;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.prescriptionsurvey.PrescroptionImageSearch;
import com.opl.pharmavector.promomat.adapter.RecyclerTouchListener;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_PMD_Contact extends Activity implements ContactAdapter.ContactCallback {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private ContactAdapter recyclerViewAdapter;
    ApiInterface apiInterface;
    com.opl.pharmavector.prescriptionsurvey.Adapter.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    private TextView fromdate, todate;
    private Button fab, btn_back;
    private AutoCompleteTextView actv;
    private ArrayList<Customer> brandlist;
    private Spinner spin_brand;
    private LinearLayout imageLayout;
    private String brand_url = BASE_URL + "prescription_survey/get_brand.php";
    public String pmdImageUrl = ApiClient.BASE_URL + "vector_ff_image/pmd/";
    String product_name, product_code, p_brand_code, mpo_code, selected_number, selected_person, profile_image, userName, userName_2, new_version, message_3;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmd_contact);

        initViews();
        progressBar.setVisibility(View.GONE);
        hideKeyBoard();
        new GetBrand().execute();

        fab.setOnClickListener(view -> {
            recyclerView.setAdapter(null);
            getContact();
        });

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                selected_number = recyclerDataArrayList.get(position).getCol4();
//                selected_person = recyclerDataArrayList.get(position).getCol3();
//                if (selected_number != null && !selected_number.isEmpty() && !selected_number.equals("null")){
//                    ViewDialog alert = new ViewDialog();
//                    alert.showDialog(recyclerDataArrayList.get(position).getCol7());
//                }
//            }
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        btn_back.setOnClickListener(view -> finish());
        actv.setFocusableInTouchMode(true);
        actv.setFocusable(true);
        actv.requestFocus();
        actv.setOnClickListener(v -> {
            if (!actv.getText().toString().equals("")) {}
        });
        actv.setOnTouchListener((v, event) -> {
            actv.showDropDown();
            return false;
        });
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    String[] first_split = inputorder.split("//");
                    product_name = first_split[0].trim();
                    product_code = first_split[1].trim();
                    actv.setText(product_name);
                    hideKeyBoard();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {}
        });
    }

    @SuppressLint("SetTextI18n")
    public void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = findViewById(R.id.progress);
        fab = findViewById(R.id.fab);
        btn_back = findViewById(R.id.back);
        btn_back.setTypeface(fontFamily);
        btn_back.setText("\uf08b");
        actv = findViewById(R.id.autoCompleteTextView1);
        spin_brand = findViewById(R.id.spin_brand);

        recyclerDataArrayList = new ArrayList<>();
        brandlist = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
    }

    @Override
    public void onContactPhoneCall(RecyclerData contact) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact.getCol4()));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Call failed, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onContactPhoneSms(RecyclerData contact) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("smsto:" + contact.getCol4()));
            intent.putExtra("sms_body", "Dear Sir,");
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    class GetBrand extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<>();
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(brand_url, ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        brandlist.add(custo);
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
            populateSpinner();
        }
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<>();
        for (int i = 0; i < brandlist.size(); i++) {
            lables.add(brandlist.get(i).getName());
        }
        //Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        spin_brand.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setAdapter(Adapter);
        actv.setTextColor(Color.BLUE);
    }

    private void getContact() {
        p_brand_code = actv.getText().toString().trim();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<RecyclerData>> call = apiInterface.getcontactinfo("mpo_code", product_code);
        Log.d("Brand Code---->", product_code);

        call.enqueue(new Callback<ArrayList<RecyclerData>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerData>> call, Response<ArrayList<RecyclerData>> response) {
                if (response.isSuccessful()) {
                    recyclerDataArrayList = response.body();
                    Log.e("DATA-- : ", String.valueOf(recyclerDataArrayList));
                    for (int i = 0; i < recyclerDataArrayList.size(); i++) {
                        recyclerViewAdapter = new ContactAdapter(Activity_PMD_Contact.this, recyclerDataArrayList, pmdImageUrl, Activity_PMD_Contact.this);
                        LinearLayoutManager manager = new LinearLayoutManager(Activity_PMD_Contact.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerData>> call, Throwable t) {
                Log.e("Data load problem--->", "Failed to Retrieved Data -- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retrieved Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public class ViewDialog {
        @SuppressLint("SetTextI18n")
        public void showDialog(String empCode) {
            final Dialog dialog = new Dialog(Activity_PMD_Contact.this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_4_contact);

            Button btn_call = dialog.findViewById(R.id.btn_call);
            Button btn_sms = dialog.findViewById(R.id.btn_sms);
            Button read_back = dialog.findViewById(R.id.read_back);
            TextView message = dialog.findViewById(R.id.message);
            TextView service = dialog.findViewById(R.id.service);
            TextView title = dialog.findViewById(R.id.title);
            LinearLayout imageLayout = dialog.findViewById(R.id.imageLayout);
            ImageView imgPmdContact = dialog.findViewById(R.id.imgPmdContact);
            imageLayout.setVisibility(View.VISIBLE);

            if (!empCode.isEmpty()) {
                profile_image = pmdImageUrl + empCode + "." + "jpg";
                Picasso.get().load(profile_image).into(imgPmdContact);
            }
            title.setText("Call/SMS");
            message.setText("Name : \t" + selected_person);
            service.setText("Selected Number \t" + selected_number);

            btn_call.setText("Call");
            btn_sms.setText("SMS");
            read_back.setText("X");

            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + selected_number));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Call failed, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });

            btn_sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    try {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("smsto:" + selected_number)); // This ensures only SMS apps respond
                        intent.putExtra("sms_body", "Dear Sir,");
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });

            read_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}