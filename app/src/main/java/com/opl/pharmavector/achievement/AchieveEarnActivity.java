package com.opl.pharmavector.achievement;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RecyclerData;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.pmdVector.ff_contact.FFContactAdapter;
import com.opl.pharmavector.pmdVector.ff_contact.ff_contact_activity;
import com.opl.pharmavector.pmdVector.model.FFTeamList;
import com.opl.pharmavector.pmdVector.model.FFTeamModel;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.KeyboardUtils;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AchieveEarnActivity extends Activity implements View.OnClickListener, FFContactAdapter.FFContactCallback {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public ProgressDialog pDialog;
    public String json, team_type = "XX", team_name = "All", deignation_type = "XX", deignation_name = "All", place_type = "XX", place_name = "All", actv_rm_code_split, ff_name, ff_code = "XX";;
    Button back_btn, submitBtn;
    public android.widget.Spinner spin_rm;
    AutoCompleteTextView autoCompleteTextView1, autoCompleteTextView2;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> departmentlist;
    private ArrayList<com.opl.pharmavector.Category> categoriesList;
    TextView lbl_place_name;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecyclerData> recyclerDataArrayList;
    List<FFTeamList> recyclerTeamList;
    private FFContactAdapter ffContactAdapter;
    ApiInterface apiInterface;
    ProgressBar progressBar;
    private String selected_number, selected_person, profile_image;
    public String pmdImageUrl = ApiClient.BASE_URL + "vector_ff_image/sales/";
    private final String url_getfieldforce = BASE_URL + "achv_and_earn/get_ff_list.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve_earn);

        initViews();
        autoCompleteEvents();
        //initTeamSpinner(teamList);
        initPlaceSpinner();
        initDesignationSpinner();
        getTeamList();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(null);
                getContact();
            }
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        back_btn = findViewById(R.id.backbt);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setTypeface(fontFamily);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        customerlist = new ArrayList<Customer>();
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);
        lbl_place_name = findViewById(R.id.lbl_place_name);
        categoriesList = new ArrayList<com.opl.pharmavector.Category>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerDataArrayList = new ArrayList<>();
    }

    private void getContact() {
        ProgressDialog ppDialog = new ProgressDialog(AchieveEarnActivity.this);
        ppDialog.setMessage("Loading Data ...");
        ppDialog.setCancelable(true);
        ppDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<RecyclerData>> call = apiInterface.getAchvEarnFFContact(deignation_type, autoCompleteTextView2.getText().toString().trim(), team_type);
        Log.d("ff_type", autoCompleteTextView2.getText().toString().trim());

        call.enqueue(new Callback<ArrayList<RecyclerData>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerData>> call, Response<ArrayList<RecyclerData>> response) {
                if (response.isSuccessful()) {
                    ppDialog.dismiss();
                    recyclerDataArrayList = response.body();

                    for (int i = 0; i < recyclerDataArrayList.size(); i++) {
                        ffContactAdapter = new FFContactAdapter(AchieveEarnActivity.this, recyclerDataArrayList, pmdImageUrl, AchieveEarnActivity.this);
                        LinearLayoutManager manager = new LinearLayoutManager(AchieveEarnActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(ffContactAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerData>> call, Throwable t) {
                Log.e("Data load problem--->", "Failed to Retried Data For-- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void getTeamList() {
        ProgressDialog pDialog = new ProgressDialog(AchieveEarnActivity.this);
        pDialog.setMessage("Loading Data ...");
        pDialog.setCancelable(true);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<FFTeamModel> call = apiInterface.getAchieveFFTeamList();

        call.enqueue(new Callback<FFTeamModel>() {
            @Override
            public void onResponse(Call<FFTeamModel> call, Response<FFTeamModel> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    List<FFTeamList> teamList = null;
                    if (response.body() != null) {
                        teamList = (response.body()).getTeamList();
                    }
                    initTeamSpinner(teamList);
                    Log.d("Team List -- : ", String.valueOf(teamList));
                }
            }

            @Override
            public void onFailure(Call<FFTeamModel> call, Throwable t) {
                pDialog.dismiss();
                Log.d("Data load problem--->", "Failed to Retried Data For-- " + t);
                Toast toast = Toast.makeText(getBaseContext(), "Failed to Retried Data", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    private void initTeamSpinner(List<FFTeamList> teamList) {
        MaterialSpinner mspinner = findViewById(R.id.mspinner);
        //mspinner.setItems("All", "Dynamos", "Titan", "Gallant", "Excelon", "Vergence");
        ArrayList<String> teamNameList = new ArrayList<>();
        if (teamList.size() > 0) {
            for (FFTeamList teamName : teamList) {
                teamNameList.add(teamName.getTeamName());
            }
        }
        mspinner.setItems(teamNameList);

        mspinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                team_name = String.valueOf(item).trim();
                for (int i = 0; i < teamList.size(); i++) {
                    if (teamList.get(i).getTeamName().contains(team_name)) {
                        team_type = teamList.get(i).getTeamCode();
                    }
                }
                Log.d("team code", team_type);
            }
        });
    }

    private void initPlaceSpinner() {
        MaterialSpinner mspinner2 = findViewById(R.id.mspinner2);
        mspinner2.setItems("All", "Division", "Zone", "Region", "Area", "Territory");

        mspinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                place_name = String.valueOf(item);
                if (place_name.trim().equals("All")) {
                    place_type = "XX";
                } else if (place_name.trim().equals("Division")) {
                    place_type = "D";
                } else if (place_name.trim().equals("Zone")) {
                    place_type = "Z";
                } else if (place_name.trim().equals("Region")) {
                    place_type = "R";
                } else if (place_name.trim().equals("Area")) {
                    place_type = "A";
                } else if (place_name.trim().equals("Territory")) {
                    place_type = "T";
                }
                if (place_type.equals("XX")) {
                    autoCompleteTextView2.setText("XX");
                    lbl_place_name.setText("All");
                } else {
                    autoCompleteTextView2.setText("");
                    customerlist.clear();
                    categoriesList.clear();
                    new GetFieldForce().execute();
                }
            }
        });
    }

    private void initDesignationSpinner() {
        MaterialSpinner mspinner3 = findViewById(R.id.mspinner3);
        mspinner3.setItems("All", "MPO", "AM", "RM", "ASM/DSM", "SM");

        mspinner3.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                deignation_name = String.valueOf(item);
                if (deignation_name.trim().equals("All")) {
                    deignation_type = "XX";
                } else if (deignation_name.trim().equals("MPO")) {
                    deignation_type = "MPO";
                } else if (deignation_name.trim().equals("AM")) {
                    deignation_type = "FM";
                } else if (deignation_name.trim().equals("RM")) {
                    deignation_type = "RM";
                } else if (deignation_name.trim().equals("ASM/DSM")) {
                    deignation_type = "AM";
                } else if (deignation_name.trim().equals("DSM")) {
                    deignation_type = "AM";
                } else if (deignation_name.trim().equals("SM")) {
                    deignation_type = "SM";
                }
            }
        });
    }

    @Override
    public void onFFContactPhoneCall(RecyclerData contact) {
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
    public void onFFContactPhoneSms(RecyclerData contact) {
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

    class GetFieldForce extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("team_type", team_type));
            params.add(new BasicNameValuePair("place_type", place_type));
            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(url_getfieldforce, ServiceHandler.POST, params);
            customerlist.clear();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");

                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        customerlist.add(custo);
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
            populateFieldForceAuto();
        }
    }

    private void populateFieldForceAuto() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        //spin_rm.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[0]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        autoCompleteTextView2.setThreshold(2);
        autoCompleteTextView2.setAdapter(Adapter);
        autoCompleteTextView2.setTextColor(Color.BLUE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void autoCompleteEvents() {
        autoCompleteTextView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //hideKeyBoard();
                autoCompleteTextView2.showDropDown();
                return false;
            }
        });
        autoCompleteTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
        autoCompleteTextView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoCompleteTextView2.setTextColor(Color.BLUE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                autoCompleteTextView2.setTextColor(Color.BLUE);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();

                    if (inputorder.contains("-")) {
                        actv_rm_code_split = inputorder.substring(inputorder.indexOf("-") + 1);
                        String[] first_split = inputorder.split("-");
                        ff_name = first_split[0].trim();
                        ff_code = first_split[1].trim();
                        autoCompleteTextView2.setText(ff_code);
                        lbl_place_name.setText(ff_name);
                        KeyboardUtils.hideKeyboard(AchieveEarnActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {

            }
        });
    }

    public void finishActivity(View v) {
        finish();
    }

    public class ViewDialog {
        @SuppressLint("SetTextI18n")
        public void showDialog() {
            final Dialog dialog = new Dialog(AchieveEarnActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_4_contact);
            Button btn_call = dialog.findViewById(R.id.btn_call);
            Button btn_sms = dialog.findViewById(R.id.btn_sms);
            Button read_back = dialog.findViewById(R.id.read_back);
            TextView message = dialog.findViewById(R.id.message);
            TextView service = dialog.findViewById(R.id.service);
            ImageView imgPmdContact = dialog.findViewById(R.id.imgPmdContact);
            profile_image = pmdImageUrl + "PE03726" + "." + "jpg";
            Picasso.get().load(profile_image).into(imgPmdContact);
            TextView title = dialog.findViewById(R.id.title);
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
                        intent.setData(Uri.parse("smsto:" + selected_number));
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

    @Override
    public void onClick(View v) {

    }
}