package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.report.LocationTrackerActivity;
import com.opl.pharmavector.rxdcc.DccRxCamp;

public class RmSalesReportDashboard extends Activity implements OnClickListener {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_growth = "growth";
    private ArrayList<Category> categoriesList;
    ProgressDialog pDialog;
    public int success;
    int textlength = 0;
    public TextView totqty, totval;
    public android.widget.Spinner ordspin;
    public String userName_1, userName;
    public String usename12;
    private String UserName, UserName_2, UserName_1 ;
    public AutoCompleteTextView actv;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, growth;
    private ArrayList<String> array_sort = new ArrayList<String>();
    private String URL_Achievement = BASE_URL+"regional_manager_api/sales_reports/Achievement1.php";
    Button targetquantity_btn, targetvalue_btn, brand_wise_sale, achivement_btn, mpo_wise_product_sale_btn,
            mpo_achv_followup, admin_product_list, group_wise_product_ord_summary,back_btn,mrd_pres_report,fourp_pres_report,msp_pres_report,dcc_rx_camp;
    CardView cardProductQuantity, cardProductValue, cardSaleGrowth, cardSalesReport, cardSalesFollowUp, cardMpoGrowth, cardOpsoninList, cardProductOrder,
            cardMrdPrescription, card4pPrescription, cardMspPrescription, cardDccCampaign, cardLocationTracker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rmsalesreportdashboard);

        initViews();
        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                logoutUser();
            }
        });
        cardLocationTracker.setOnClickListener(v -> {
            Intent i = new Intent(RmSalesReportDashboard.this, LocationTrackerActivity.class);
            i.putExtra("userName", userName);
            i.putExtra("userCode", UserName);
            i.putExtra("userRole", "RM");
            startActivity(i);
        });
        cardDccCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, DccRxCamp.class);
                        i.putExtra("manager_code", RmDashboard.globalRMCode);
                        i.putExtra("manager_detail", "RM");
                        startActivity(i);
                    }
                });
                mysells.start();
            }
        });
        cardMrdPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, MRDPresReport.class);
                        i.putExtra("userName", userName);
                        i.putExtra("UserName", userName);
                        i.putExtra("report_flag", "SPI");
                        i.putExtra("asm_flag", "N");
                        i.putExtra("sm_flag", "N");
                        i.putExtra("gm_flag", "N");
                        i.putExtra("rm_flag", "Y");
                        i.putExtra("fm_flag", "N");
                        startActivity(i);
                    }
                });
                mysells.start();
            }
        });
        card4pPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, MRDPresReport.class);
                        i.putExtra("userName", userName);
                        i.putExtra("UserName", userName);
                        i.putExtra("report_flag", "4P");
                        i.putExtra("report_flag", "4P");
                        i.putExtra("asm_flag", "N");
                        i.putExtra("sm_flag", "N");
                        i.putExtra("gm_flag", "N");
                        i.putExtra("rm_flag", "Y");
                        i.putExtra("fm_flag", "N");
                        startActivity(i);
                    }
                });
                mysells.start();
            }
        });
        cardMspPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, MRDPresReport.class);
                        i.putExtra("userName", userName);
                        i.putExtra("UserName", userName);
                        i.putExtra("report_flag", "MSP");
                        i.putExtra("report_flag", "MSP");
                        i.putExtra("asm_flag", "N");
                        i.putExtra("sm_flag", "N");
                        i.putExtra("gm_flag", "N");
                        i.putExtra("rm_flag", "Y");
                        i.putExtra("fm_flag", "N");
                        startActivity(i);
                    }
                });
                mysells.start();
            }
        });
        cardProductQuantity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, RmTargetquantity.class);
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
        cardProductValue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    //String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, RmTargetvalue.class);
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
        cardSaleGrowth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONParser jsonParser = new JSONParser();
                final List<NameValuePair> params = new ArrayList<NameValuePair>();
                Bundle b = getIntent().getExtras();
                usename12 = b.getString("UserName");
                pDialog = new ProgressDialog(RmSalesReportDashboard.this);
                pDialog.setMessage("Calculating your Achievment ");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                String userName = b.getString("UserName");
                params.add(new BasicNameValuePair("id", userName));

                Thread server = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject json = jsonParser.makeHttpRequest(URL_Achievement, "POST", params);
                        try {
                            success = json.getInt(TAG_SUCCESS);
                            message = json.getString(TAG_MESSAGE);
                            invoice = json.getString(TAG_invoice);
                            target_data = json.getString(TAG_target);
                            achivement = json.getString(TAG_achivement);
                            growth = json.getString(TAG_growth);

                            if (success == 1) {
                                pDialog.dismiss();
                            } else {
                                SaveToDataBase();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent in = getIntent();
                        Intent inten = getIntent();
                        Bundle bundle = in.getExtras();
                        inten.getExtras();
                        String MPO_CODE = bundle.getString("MPO_CODE");
                        String userName_1 = bundle.getString("userName_1");
                        Intent sameint = new Intent(RmSalesReportDashboard.this, RmAchievement.class);
                        sameint.putExtra("UserName", MPO_CODE);
                        sameint.putExtra("Ord_NO", message);
                        sameint.putExtra("invoice", invoice);
                        sameint.putExtra("target", target_data);
                        sameint.putExtra("achivement", achivement);
                        sameint.putExtra("growth", growth);
                        sameint.putExtra("userName_1", usename12);
                        startActivity(sameint);
                    }

                    private void SaveToDataBase() {}
                });
                server.start();
            }
        });
        cardSalesReport.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, FMwiseProductSale.class);
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
        cardSalesFollowUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    //String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, FMBrandwiseProductSale.class);
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
        cardMpoGrowth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    //String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, RMfollowMpoAchv.class);
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
        cardOpsoninList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, AdminProductList.class);
                        i.putExtra("userName", userName);
                        i.putExtra("UserName", userName);
                        i.putExtra("UserName_2", UserName_2);
                        startActivity(i);
                    }
                });
                mysells.start();
            }
        });
        cardProductOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    Bundle b = getIntent().getExtras();
                    String userName = b.getString("UserName");
                    String UserName_1 = b.getString("userName_1");
                    String UserName_2 = b.getString("userName_2");
                    @Override
                    public void run() {
                        Intent i = new Intent(RmSalesReportDashboard.this, GroupwiseProductOrderSummary2.class);
                        i.putExtra("UserName", UserName);
                        i.putExtra("new_version", userName);
                        i.putExtra("UserName_1", UserName_1);
                        i.putExtra("UserName_2", UserName_2);
                        i.putExtra("userName", userName);
                        i.putExtra("new_version", userName);
                        i.putExtra("userName_1", UserName_1);
                        i.putExtra("userName_2", UserName_2);
                        i.putExtra("new_version", userName);
                        i.putExtra("userName", userName);
                        i.putExtra("new_version", userName);
                        i.putExtra("message_3", "RM");
                        startActivity(i);
                    }
                });
                mysells.start();
            }
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        cardProductQuantity = findViewById(R.id.cardProductQuantity);
        cardProductValue = findViewById(R.id.cardProductValue);
        cardSalesFollowUp = findViewById(R.id.cardSalesFollowUp);
        cardSaleGrowth = findViewById(R.id.cardSaleGrowth);
        cardSalesReport = findViewById(R.id.cardSalesReport);
        cardMpoGrowth = findViewById(R.id.cardMpoGrowth);
        cardOpsoninList = findViewById(R.id.cardOpsoninList);
        cardProductOrder = findViewById(R.id.cardProductOrder);
        cardMrdPrescription = findViewById(R.id.cardMrdPrescription);
        card4pPrescription = findViewById(R.id.card4pPrescription);
        cardMspPrescription= findViewById(R.id.cardMspPrescription);
        cardDccCampaign = findViewById(R.id.cardDccCampaign);
        cardLocationTracker = findViewById(R.id.cardLocationTracker);

        //mpo_wise_product_sale_btn.setText("4. Product wise Sales Follow-up");
        //mpo_achv_followup.setText("6. Target, Sale, Achievement \nand Growth of Area Manager");
        back_btn = findViewById(R.id.backbt);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 "); //&#xf060
        Bundle b = getIntent().getExtras();
        UserName = b.getString("UserName");
        UserName_1 = b.getString("userName_1");
        UserName_2 = b.getString("userName_2");
    }

    @Override
    public void onClick(View v) {}

    protected void onPostExecute() {}

    private void logoutUser() {
        Intent intent = new Intent(RmSalesReportDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }
}
