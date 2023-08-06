package com.opl.pharmavector.pcconference;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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


import androidx.appcompat.app.AppCompatActivity;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.MonthYearPickerDialog;
import com.opl.pharmavector.PCDashboard;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RmDashboard;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.adapter.CategoryAdapter;
import com.opl.pharmavector.adapter.CategoryAdapter2;
import com.opl.pharmavector.adapter.CategoryAdapter3;
import com.opl.pharmavector.model.Category;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser2;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser3;
import com.opl.pharmavector.serverCalls.InsertUpdateFavouriteCategories;

import android.widget.DatePicker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
import static com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser2.selectedCategories;
import static com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser3.selectedCategories3;
import static java.lang.String.valueOf;

public class RMPCPermission extends AppCompatActivity {
    Context context;
    ArrayList<Category> array_list;
    FavouriteCategoriesJsonParser2 categoryJsonParser;
    FavouriteCategoriesJsonParser3 categoryJsonParser3;
    String categoriesCsv;
    String categoriesCsv2;
    String categoriesPhone;
    private ArrayList<Customer> customerlist;
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
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, my_val, conf_type_val;

    private ArrayList<Customer> checkdatelist;
    
    private final String pc_conference_permission_rm = BASE_URL+"pcconference/pc_conference_permission_rm.php";
    private final String pc_conference_permission_delete = BASE_URL+"pcconference/pc_conference_permission_delete.php";
    private final String pc_conference_date_check = BASE_URL+"pcconference/pc_conference_rm_validation.php";
    private final String rm_depo_check = BASE_URL+"pcconference/rm_depo_check.php";
    private final String rm_depo_check_new = BASE_URL+"pcconference/rm_depo_check_new.php";
    private final String URL_CUSOTMER = BASE_URL+"pcconference/get_depot.php";
    
    EditText conference_date;
    public String product_list;
    public static String RM_CODE;
    public static String conf_type;
    public static String proposed_conference_date2;
    public static String proposed_conference_date;
    public static String rm_depot_code;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button btnDisplay;
    public String get_ext_dt, date_flag, check_flag;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    public String monthPicker;
    public String monthYearStr;
    public String year_val, month_val;
    public String month_name_val;
    public TextView succ_msg;
    public ListView mListViewBooks;
    public int count_of_depot, count_of_pc, available_pc_initial, pc_count_date_flag, left_pc;
    public static final String tag_get_depot = "NO_DEPOT";
    public static final String tag_get_count_of_pc = "PC_COUNT";
    private Spinner cust;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proposal_activity_main);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        final Button delete_mpo = (Button) findViewById(R.id.delete_mpo);
        final Button submit = (Button) findViewById(R.id.selectCategoryButton);
        final Button back = (Button) findViewById(R.id.back);
        final Button permision = (Button) findViewById(R.id.permision);
        final Button edit = (Button) findViewById(R.id.edit);
        final LinearLayout layout1 = (LinearLayout) findViewById(R.id.slistLayout);
        submit.setVisibility(View.GONE);
        delete_mpo.setVisibility(View.GONE);
        succ_msg = (TextView) findViewById(R.id.succ_msg);
        succ_msg.setVisibility(View.GONE);
        cust = (Spinner) findViewById(R.id.customer);
        mListViewBooks = (ListView) findViewById(R.id.category_listView);
        layout1.setVisibility(View.GONE);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        checkdatelist = new ArrayList<Customer>();
        customerlist = new ArrayList<Customer>();
        context = this;
        Bundle b = getIntent().getExtras();
        RM_CODE = b.getString("UserName");
        final String rm_area = b.getString("UserName_2");
        TextView user_show = (TextView) findViewById(R.id.user_show);
        conference_date = (EditText) findViewById(R.id.conference_date);
        conference_date.setFocusableInTouchMode(true);
        conference_date.setFocusable(true);
        conference_date.requestFocus();
        conference_date.setClickable(true);
        conference_date.setInputType(InputType.TYPE_NULL);
        user_show.setText(RM_CODE + " - " + rm_area + " ");
        categoriesCsv = "";

        //new GeTRmDepot().execute();

        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        permision.setEnabled(false);
        edit.setEnabled(false);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            Intent i = new Intent(RMPCPermission.this, RmDashboard.class);
                            i.putExtra("UserName", RmDashboard.globalRMCode);
                            i.putExtra("new_version", RmDashboard.new_version);
                            i.putExtra("UserName_2", RmDashboard.globalRegionalCode);
                            i.putExtra("message_3", RmDashboard.message_3);
                            i.putExtra("password", RmDashboard.password);
                            i.putExtra("ff_type", RmDashboard.ff_type);
                            i.putExtra("vector_version", R.string.vector_version);
                            i.putExtra("emp_code", RmDashboard.globalempCode);
                            i.putExtra("emp_name", RmDashboard.globalempName);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();

            }
        });

        conference_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        monthYearStr = year + "-" + (month + 1) + "-" + i2;
                        conference_date.setText(formatMonthYear(monthYearStr));
                        Log.e("monthyear", monthYearStr);
                        String test = monthYearStr;
                        year_val = test.substring(0, 4);
                        int month_int = month;
                        month_val = String.valueOf(month_int);
                        // proposed_conference_date= year + "/" + (month + 1) + "/" + 01;
                        proposed_conference_date = "01" + "/" + (month) + "/" + year;
                        proposed_conference_date2 = "31" + "/" + (month) + "/" + year;
                        if (month_val == String.valueOf(1)) {
                            month_name_val = "January";
                        } else if (month_val == String.valueOf(2)) {
                            month_name_val = "Feb";
                        } else if (month_val == String.valueOf(3)) {
                            month_name_val = "March";
                        } else if (month_val == String.valueOf(4)) {
                            month_name_val = "April";
                        } else if (month_val == String.valueOf(5)) {
                            month_name_val = "May";
                        } else if (month_val == String.valueOf(6)) {
                            month_name_val = "June";
                        } else if (month_val == String.valueOf(7)) {
                            month_name_val = "July";
                        } else if (month_val == String.valueOf(8)) {
                            month_name_val = "August";
                        } else if (month_val == String.valueOf(9)) {
                            month_name_val = "September";
                        } else if (month_val == String.valueOf(10)) {
                            month_name_val = "October";
                        } else if (month_val == String.valueOf(11)) {
                            month_name_val = "November";
                        } else if (month_val == String.valueOf(12)) {
                            month_name_val = "December";
                        }
                        Log.e("proposed_confere_date", proposed_conference_date);
                        permision.setEnabled(true);
                        edit.setEnabled(true);

                        if (mListViewBooks.getCount() > 0) {
                            mListViewBooks.setAdapter(null);
                            categoriesCsv = "";
                            succ_msg.setVisibility(View.GONE);
                        }

                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");


            }
        });

        permision.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Bundle b = getIntent().getExtras();
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                String conf_type_val = String.valueOf(radioSexButton.getText());
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String Today = df.format(c);
                Date Today_date1 = null;
                try {
                    Today_date1 = new SimpleDateFormat("dd/MM/yyyy").parse(Today);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                Date Conference_date1 = null;
                try {
                    Conference_date1 = new SimpleDateFormat("dd/MM/yyyy").parse(proposed_conference_date2);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }


                if (Today_date1.after(Conference_date1)) {

                    LayoutInflater li = getLayoutInflater();
                    View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                    Toast toast = new Toast(getApplicationContext());
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("You can not assign conference permission in previous months.");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);//setting the view of custom toast layout
                    toast.show();
                } else if (conference_date.equals("") || conference_date.equals(null)) {

                    LayoutInflater li = getLayoutInflater();
                    View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout_id));
                    Toast toast = new Toast(getApplicationContext());
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Please select a Month first");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);//setting the view of custom toast layout
                    toast.show();
                } else {

                    conf_type = conf_type_val.substring(0, 1);
                    layout1.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    delete_mpo.setVisibility(View.GONE);
                    new GeTRmDepotCheck().execute();
                    new asyncTask_getCategories().execute();
                    new GeTPcConferenceDate().execute();
                }

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle b = getIntent().getExtras();
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                Toast.makeText(RMPCPermission.this, radioSexButton.getText(), Toast.LENGTH_SHORT).show();
                String conf_type_val = String.valueOf(radioSexButton.getText());
                conf_type = conf_type_val.substring(0, 1);
                layout1.setVisibility(View.VISIBLE);
                new asyncTask_getCategories2().execute();
                submit.setVisibility(View.GONE);
                delete_mpo.setVisibility(View.VISIBLE);
            }
        });


        delete_mpo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  rm_depot_code=  cust.getSelectedItem().toString();
                categoriesCsv2 = selectedCategories3.toString();
                categoriesCsv2 = categoriesCsv2.substring(1, categoriesCsv2.length() - 1);
                Bundle b = getIntent().getExtras();
                int commas = 0;
                for (int i = 0; i < categoriesCsv2.length(); i++) {
                    if (categoriesCsv2.charAt(i) == ',') commas++;
                }
                System.out.println(categoriesCsv2 + " Selected  " + commas + 1 + " MPO!");
                if ((categoriesCsv2.length()) < 1) {
                    Toast.makeText(context, "Select a Territory", Toast.LENGTH_SHORT).show();
                } else {
                    Thread server = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONParser jsonParser = new JSONParser();
                            Bundle b = getIntent().getExtras();
                            final String UserName = b.getString("UserName_2");
                            final String UserName_2 = b.getString("UserName_2");
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("categoriesCsv", categoriesCsv2));
                            params.add(new BasicNameValuePair("RM_CODE", RM_CODE));
                            params.add(new BasicNameValuePair("CONF_TYPE", conf_type));
                            params.add(new BasicNameValuePair("CONF_DATE", proposed_conference_date));
                            //params.add(new BasicNameValuePair("rm_depot_code", rm_depot_code));
                            JSONObject json = jsonParser.makeHttpRequest(pc_conference_permission_delete, "POST", params);
                            try {
                                success = json.getInt(TAG_SUCCESS);
                                message = json.getString(TAG_MESSAGE);
                                showCancelSnack();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent in = getIntent();
                            Intent inten = getIntent();
                            Bundle bundle = in.getExtras();
                            inten.getExtras();
                            Intent sameint = new Intent(RMPCPermission.this, RMPCPermission.class);
                            sameint.putExtra("UserName", RM_CODE);
                            sameint.putExtra("UserName_2", UserName_2);
                            startActivity(sameint);
                            selectedCategories.clear();
                            selectedCategories3.clear();
                            showCancelSnack();
                        }
                    });
                    server.start();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                categoriesCsv = selectedCategories.toString();
                categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);
                categoriesPhone = FavouriteCategoriesJsonParser.selectedphonenumber.toString();
                categoriesPhone = categoriesPhone.substring(1, categoriesPhone.length() - 1);
                Bundle b = getIntent().getExtras();
                int commas = 0;

                for (int i = 0; i < categoriesCsv.length(); i++) {
                    if (categoriesCsv.charAt(i) == ',') commas++;
                }
                int count_submit_mpo = (commas + 1);
                int remain_pc_for_month = (left_pc - count_submit_mpo);

                if ((categoriesCsv.length()) < 1) {
                    Toast.makeText(context, "Please Select MPO", Toast.LENGTH_SHORT).show();
                } else if (remain_pc_for_month < 0) {
                    Toast.makeText(context, "Can not assign more than\t " + left_pc +
                            " Territory ", Toast.LENGTH_SHORT).show();
                } else {
                    Thread server = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONParser jsonParser = new JSONParser();
                            Bundle b = getIntent().getExtras();
                            final String UserName = b.getString("UserName_2");
                            final String UserName_2 = b.getString("UserName_2");
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("categoriesCsv", categoriesCsv));
                            params.add(new BasicNameValuePair("categoriesPhone", categoriesPhone));
                            params.add(new BasicNameValuePair("RM_CODE", RM_CODE));
                            params.add(new BasicNameValuePair("CONF_TYPE", conf_type));
                            params.add(new BasicNameValuePair("CONF_DATE", proposed_conference_date));
                            JSONObject json = jsonParser.makeHttpRequest(pc_conference_permission_rm, "POST", params);

                            try {
                                success = json.getInt(TAG_SUCCESS);
                                message = json.getString(TAG_MESSAGE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent in = getIntent();
                            Intent inten = getIntent();
                            Bundle bundle = in.getExtras();
                            inten.getExtras();
                            Intent sameint = new Intent(RMPCPermission.this, RMPCPermission.class);
                            sameint.putExtra("UserName", RM_CODE);
                            sameint.putExtra("UserName_2", UserName_2);
                            startActivity(sameint);
                            showSnack();
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
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    public class asyncTask_getCategories2 extends AsyncTask<Void, Void, Void> {
        //ProgressDialog dialog = new ProgressDialog(context);
        @Override
        protected void onPreExecute() {
            array_list = new ArrayList<>();
            my_val = RM_CODE;
            conf_type_val = conf_type;
            categoryJsonParser3 = new FavouriteCategoriesJsonParser3();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            array_list = categoryJsonParser3.getParsedCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            final CategoryAdapter3 categoryAdapter = new CategoryAdapter3(context, R.layout.row_category, array_list);
            mListViewBooks.setAdapter(categoryAdapter);
            super.onPostExecute(s);
        }
    }

    public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
        //ProgressDialog dialog = new ProgressDialog(context);
        @Override
        protected void onPreExecute() {
            array_list = new ArrayList<>();
            my_val = RM_CODE;
            conf_type_val = conf_type;
            categoryJsonParser = new FavouriteCategoriesJsonParser2();
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
            final CategoryAdapter2 categoryAdapter = new CategoryAdapter2(context, R.layout.row_category, array_list);
            mListViewBooks.setAdapter(categoryAdapter);
            super.onPostExecute(s);
        }
    }

    private void pc_conference_date_check() {
        date_flag = "0";
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < checkdatelist.size(); i++) {
            date_flag = checkdatelist.get(i).getName();
        }
        pc_count_date_flag = Integer.parseInt(date_flag);

        left_pc = available_pc_initial - pc_count_date_flag;
        succ_msg.setVisibility(View.VISIBLE);
        succ_msg.setText("PC Assinged for you in\t" + month_name_val + "\t" + available_pc_initial +
                "\nTotal Submitted Territory for\t" + month_name_val + "\t" + pc_count_date_flag +
                "\nYou have \t" + (available_pc_initial - pc_count_date_flag) + "\tTerritory Left"
        );
    }

    class GeTPcConferenceDate extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", RM_CODE));
            params.add(new BasicNameValuePair("conf_type", conf_type));
            params.add(new BasicNameValuePair("proposed_conference_date", proposed_conference_date));

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(pc_conference_date_check, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < 1; i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        checkdatelist.add(custo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pc_conference_date_check();
        }
    }

    private void rm_depo_count_check_new() {
        available_pc_initial = (int) ((double) count_of_pc / (double) count_of_depot);
    }

    class GeTRmDepotCheck extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RMPCPermission.this);
            pDialog.setTitle("Please wait !");
            pDialog.setMessage("Loading ..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            final JSONParser jsonParser = new JSONParser();
            final List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", RM_CODE));
            params.add(new BasicNameValuePair("conf_type", conf_type));
            params.add(new BasicNameValuePair("proposed_conference_date", proposed_conference_date));
            JSONObject json = jsonParser.makeHttpRequest(rm_depo_check_new, "POST", params);

            try {
                count_of_depot = json.getInt(tag_get_depot);
                count_of_pc = json.getInt(tag_get_count_of_pc);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            rm_depo_count_check_new();
        }
    }

    private void showSnack() {
        new Thread() {
            public void run() {
                RMPCPermission.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "Territory Assigned Succesfully";
                        Toasty.success(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    private void showCancelSnack() {
        new Thread() {
            public void run() {
                RMPCPermission.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "Territory Cancelled Succesfully";
                        Toasty.warning(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }
}








