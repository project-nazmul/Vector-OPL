
package com.opl.pharmavector;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opl.pharmavector.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class AmAchievment extends Activity implements OnItemSelectedListener {
    private Button logout, achivbtn;

    private TextView user_show;
    private SessionManager session;

    public Button next, back;
    Editor editor;
    public EditText osi, op, od, dateResult, ref;
    // private ListView cust;
    private ArrayList<Customer> customerlist;
    private Spinner cust;
    ProgressDialog pDialog;
    EditText mEdit;
    TextView date2, ded, myTextView;
    public String userName_1, userName,UserName_2;
    protected Handler handler;
    public ProgressBar bar_1, bar_2, bar_3, bar_4;
    public EditText date_ext, val_mpo, submit_mpo, req_mpo, val_growth;
    Button btn_1, btn_2, btn_3, btn_4;

    @SuppressLint("CutPasteId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.amachievement);
        initvIews();
        Bundle b = getIntent().getExtras();
        if (b.isEmpty()) {
            String userName = " ";
            myTextView.setText(userName);

        }
        else {

            userName = b.getString("UserName");
            userName_1 = b.getString("userName_1");
            UserName_2 = b.getString("UserName_2");
            String invoice = b.getString("target");
            String tar = b.getString("invoice");
            String achivement = b.getString("achivement");
            String growth = b.getString("growth");
            String msg = b.getString("UserName_2");
            String Ord_NO = b.getString("UserName_2");

            myTextView.setText(MessageFormat.format("{0} - {1}", AmDashboard.globalFMCode, AmDashboard.globalAreaCode));

            double d = Double.parseDouble(tar);
            int i = (int) d;
            double e = Double.parseDouble(invoice);
            int j = (int) e;
            String number = tar;
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,##,###.00");
            String TARGET_VALUE = formatter.format(amount);
            String number2 = invoice;
            double amount2 = Double.parseDouble(number2);
            DecimalFormat INVOICE_VALUE = new DecimalFormat("#,##,###.00");
            String INVOICE = INVOICE_VALUE.format(amount2);
            val_mpo.setText(TARGET_VALUE);


            bar_1.setMax(j);
            bar_1.setProgress(i);
            submit_mpo.setText(INVOICE);
            req_mpo.setText(achivement);
            val_growth.setText(growth);
            bar_3.setMax(j);
            bar_3.setProgress(i);
            bar_3.setRotation(180);
            bar_4.setMax(100);
            bar_4.setProgress(100);
            bar_4.setRotation(180);

        }
        session = new SessionManager(getApplicationContext());

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initvIews() {

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        logout = findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b"); //&#xf08b
        user_show = (TextView) findViewById(R.id.user_show);


        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);


        val_mpo = findViewById(R.id.val_mpo);
        submit_mpo = findViewById(R.id.submit_mpo);

        req_mpo = findViewById(R.id.req_mpo);
        val_growth = findViewById(R.id.val_growth);

        bar_1 = findViewById(R.id.bar_1);
        bar_2 = findViewById(R.id.bar_2);
        bar_3 = findViewById(R.id.bar_3);
        bar_4 = findViewById(R.id.bar_4);


        back = findViewById(R.id.logout);
        back.setTypeface(fontFamily);
        back.setText("\uf08b");// &#xf060


        customerlist = new ArrayList<Customer>();
        myTextView = findViewById(R.id.user_show1);

    }



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub

    }

}
