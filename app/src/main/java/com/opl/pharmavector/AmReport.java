package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.nativecss.NativeCSS;
import com.opl.pharmavector.order_online.ReadComments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class AmReport extends Activity implements OnClickListener {

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_growth = "growth";
    private ArrayList<com.opl.pharmavector.Category> categoriesList;
    ProgressDialog pDialog;
    ListView productListView;
    Button submit;
    EditText qnty;
    EditText inputOne, inputtwo;
    public int success;
    int textlength = 0;
    public TextView totqty, totval;
    public android.widget.Spinner ordspin;
    public String userName_1, userName;
    com.opl.pharmavector.JSONParser jsonParser;
    List<NameValuePair> params;
    public String CurrenOrder, usename12;
    private String UserName, UserName_1, UserName_2;
    public AutoCompleteTextView actv;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, growth;
    private ArrayList<String> array_sort = new ArrayList<String>();
    private String URL_PRODUCT_VIEW = "http://opsonin.com.bd/dept_order_android_am1/get_products_view.php";
    private String URL_Achievement = "http://opsonin.com.bd/dept_order_android_am1/Achievement1.php";
	Typeface fontFamily;
    Button viewbycustomer_btn, viewbycustomersale_btn, viewbycustomerreturn_btn, viewbycustomerorderstatus_btn, viewstock_btn,
            targetquantity_btn, targetvalue_btn, customervalue_btn, orderinvoice_btn, achivement_btn, back_btn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amscroll);
        initViews();


        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                          finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();
            }
        });


        targetquantity_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmReport.this, Targetquantity.class);
                        i.putExtra("UserName", UserName);
                        i.putExtra("new_version", userName);
                        i.putExtra("UserName_1", UserName_1);
                        i.putExtra("UserName_2", UserName_2);
                        i.putExtra("userName", userName);
                        i.putExtra("new_version", userName);
                        i.putExtra("userName_1", UserName_1);
                        i.putExtra("userName_2", UserName_2);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        targetvalue_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmReport.this, Targetvalue.class);
                        i.putExtra("UserName", UserName);
                        i.putExtra("new_version", userName);
                        i.putExtra("UserName_1", UserName_1);
                        i.putExtra("UserName_2", UserName_2);
                        i.putExtra("userName", userName);
                        i.putExtra("new_version", userName);
                        i.putExtra("userName_1", UserName_1);
                        i.putExtra("userName_2", UserName_2);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        customervalue_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmReport.this, com.opl.pharmavector.Customervalue.class);
                        i.putExtra("UserName", UserName);
                        i.putExtra("new_version", userName);
                        i.putExtra("UserName_1", UserName_1);
                        i.putExtra("UserName_2", UserName_2);
                        i.putExtra("userName", userName);
                        i.putExtra("new_version", userName);
                        i.putExtra("userName_1", UserName_1);
                        i.putExtra("userName_2", UserName_2);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        orderinvoice_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmReport.this, com.opl.pharmavector.Orderinvoice.class);
                        i.putExtra("UserName", UserName);
                        i.putExtra("new_version", userName);
                        i.putExtra("UserName_1", UserName_1);
                        i.putExtra("UserName_2", UserName_2);
                        i.putExtra("userName", userName);
                        i.putExtra("new_version", userName);
                        i.putExtra("userName_1", UserName_1);
                        i.putExtra("userName_2", UserName_2);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        achivement_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final com.opl.pharmavector.JSONParser jsonParser = new com.opl.pharmavector.JSONParser();
                final List<NameValuePair> params = new ArrayList<NameValuePair>();
                Bundle b = getIntent().getExtras();
                usename12 = b.getString("UserName");
                String userName = b.getString("UserName");
                params.add(new BasicNameValuePair("id", userName));
                Thread server = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        JSONObject json = jsonParser.makeHttpRequest(URL_Achievement, "POST", params);
                        try {
                            success = json.getInt(TAG_SUCCESS);
                            message = json.getString(TAG_MESSAGE);
                            invoice = json.getString(TAG_invoice);
                            target_data = json.getString(TAG_target);
                            achivement = json.getString(TAG_achivement);
                            growth = json.getString(TAG_growth);
                            if (success == 1) {
                            } else {
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
                        String userName_1 = bundle.getString("userName_1");
                        Intent sameint = new Intent(AmReport.this, com.opl.pharmavector.Achievement.class);
                        sameint.putExtra("UserName", MPO_CODE);
                        sameint.putExtra("Ord_NO", message);
                        sameint.putExtra("invoice", invoice);
                        sameint.putExtra("target", target_data);
                        sameint.putExtra("achivement", achivement);
                        sameint.putExtra("growth", growth);
                        sameint.putExtra("userName_1", usename12);
                        startActivity(sameint);

                    }

                    private void SaveToDataBase() {
                        // TODO Auto-generated method stub

                    }

                });

                server.start();


            }

        });
        viewbycustomer_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmReport.this, Viewbycustomer.class);
                        i.putExtra("UserName", userName);
                        i.putExtra("UserName_1", userName);
                        i.putExtra("new_version", userName);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        viewbycustomersale_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmReport.this, Viewbycustomersale.class);
                        i.putExtra("UserName", userName);
                        i.putExtra("UserName_1", userName);
                        i.putExtra("new_version", userName);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        /*
        viewbycustomerreturn_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmReport.this, Viewbycustomerreturn.class);
                        i.putExtra("UserName", userName);
                        i.putExtra("UserName_1", userName);
                        i.putExtra("new_version", userName);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });

        */
        viewbycustomerorderstatus_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmReport.this, Viewbycustomerorderstatus.class);
                        i.putExtra("UserName", userName);
                        i.putExtra("UserName_1", userName);
                        i.putExtra("new_version", userName);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        viewstock_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmReport.this, Stokeview.class);
                        i.putExtra("UserName", userName);
                        i.putExtra("UserName_1", userName);
                        i.putExtra("new_version", userName);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });

    }

	private void initViews() {

		fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
		viewbycustomer_btn = (Button) findViewById(R.id.viewbycustomer);
		viewbycustomersale_btn = (Button) findViewById(R.id.viewbycustomersale);
		viewbycustomerreturn_btn = (Button) findViewById(R.id.viewbycustomerreturn);
		viewbycustomerorderstatus_btn = (Button) findViewById(R.id.viewbycustomerorderstatus);
		viewstock_btn = (Button) findViewById(R.id.viewstock);
		targetquantity_btn = (Button) findViewById(R.id.targetquantity);
		targetvalue_btn = (Button) findViewById(R.id.targetvalue);
		customervalue_btn = (Button) findViewById(R.id.customervalue);
		orderinvoice_btn = (Button) findViewById(R.id.orderinvoice);
		achivement_btn = (Button) findViewById(R.id.achivement);
		back_btn = (Button) findViewById(R.id.backbt);
		back_btn.setTypeface(fontFamily);
		back_btn.setText("\uf060 ");// &#xf060
		Bundle b = getIntent().getExtras();
		UserName = b.getString("UserName");
		UserName_1 = b.getString("userName_1");
		UserName_2 = b.getString("userName_2");

	}


	@Override
    public void onClick(View v) {


    }

    protected void onPostExecute() {

    }

}
