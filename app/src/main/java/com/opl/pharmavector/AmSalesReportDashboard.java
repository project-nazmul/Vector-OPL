package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

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
import android.widget.TextView;

import com.opl.pharmavector.mrd_pres_report.MRDPresReport;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AmSalesReportDashboard extends Activity implements OnClickListener {

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
    public TextView totqty, totval;
    public android.widget.Spinner ordspin;
    public String userName_1, userName;
    public String usename12;
    private String UserName, UserName_1, UserName_2;
    public AutoCompleteTextView actv;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, growth;

    private final String URL_Achievement = BASE_URL+"area_manager_api/sales_reports/Achievement.php";

    Button mpo_wise_product_sale_btn, brand_wise_sale_btn, targetquantity_btn, targetvalue_btn, achivement_btn,
            back_btn, mpo_achv_followup, admin_product_list, group_wise_product_ord_summary,mrd_pres_report,fourp_pres_report,msp_pres_report,dcc_rx_camp;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.amsalesreportdashboard);
        initViews();


        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Intent i = new Intent(AmSalesReportDashboard.this, AmDashboard.class);
                            i.putExtra("UserName", AmDashboard.globalFMCode);
                            i.putExtra("new_version", AmDashboard.new_version);
                            i.putExtra("UserName_2", AmDashboard.globalAreaCode);
                            i.putExtra("message_3", AmDashboard.message_3);
                            i.putExtra("password", AmDashboard.password);
                            i.putExtra("ff_type", AmDashboard.ff_type);
                            i.putExtra("vector_version", R.string.vector_version);
                            i.putExtra("emp_code", AmDashboard.globalempCode);
                            i.putExtra("emp_name", AmDashboard.globalempName);
                            startActivity(i);
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
                        Intent i = new Intent(AmSalesReportDashboard.this, AmTargetquantity.class);
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
                        Intent i = new Intent(AmSalesReportDashboard.this, AmTargetvalue.class);
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

                final JSONParser jsonParser = new JSONParser();
                final List<NameValuePair> params = new ArrayList<NameValuePair>();
                Bundle b = getIntent().getExtras();
                usename12 = b.getString("UserName");
                UserName_2 = b.getString("UserName_2");
                pDialog = new ProgressDialog(AmSalesReportDashboard.this);
                pDialog.setMessage("Calculating your Achievment ");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
               // pDialog.show();
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

                                //pDialog.dismiss();

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
                        Intent sameint = new Intent(AmSalesReportDashboard.this, AmAchievment.class);
                        sameint.putExtra("UserName", MPO_CODE);
                        sameint.putExtra("Ord_NO", message);
                        sameint.putExtra("invoice", invoice);
                        sameint.putExtra("target", target_data);
                        sameint.putExtra("achivement", achivement);
                        sameint.putExtra("growth", growth);
                        sameint.putExtra("userName_1", usename12);
                        sameint.putExtra("UserName_2", UserName_2);

                        //Log.e("values-->",MPO_CODE+"--"+message+"---"+usename12+"---"+UserName_2);
                        startActivity(sameint);


                    }
                    private void SaveToDataBase() {
                        // TODO Auto-generated method stub
                    }
                });
                server.start();
            }

        });

        mpo_wise_product_sale_btn.setOnClickListener(new OnClickListener() {
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
                        Intent i = new Intent(AmSalesReportDashboard.this, ProductSale.class);
                        i.putExtra("UserName", UserName);
                        i.putExtra("new_version", userName);
                        i.putExtra("UserName_1", UserName_1);
                        i.putExtra("UserName_2", UserName_2);
                        i.putExtra("userName", userName);
                        i.putExtra("new_version", userName);
                        i.putExtra("userName_1", UserName_1);
                        i.putExtra("userName_2", UserName_2);
                        i.putExtra("activity", "P");

                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        brand_wise_sale_btn.setOnClickListener(new OnClickListener() {
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
                        Intent i = new Intent(AmSalesReportDashboard.this, BrandwiseProductSale.class);
                        i.putExtra("UserName", UserName);
                        i.putExtra("new_version", userName);
                        i.putExtra("UserName_1", UserName_1);
                        i.putExtra("UserName_2", UserName_2);
                        i.putExtra("userName", userName);
                        i.putExtra("new_version", userName);
                        i.putExtra("userName_1", UserName_1);
                        i.putExtra("userName_2", UserName_2);
                        i.putExtra("activity", "B");
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });
        mpo_achv_followup.setOnClickListener(new OnClickListener() {
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
                        Intent i = new Intent(AmSalesReportDashboard.this, AMfollowMpoAchv.class);
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
        admin_product_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmSalesReportDashboard.this, AdminProductList.class);
                        i.putExtra("userName", userName);
                        i.putExtra("UserName", userName);
                        i.putExtra("UserName_2", UserName_2);
                        startActivity(i);
                    }
                });
                mysells.start();

            }
        });
        group_wise_product_ord_summary.setOnClickListener(new OnClickListener() {
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
                        Intent i = new Intent(AmSalesReportDashboard.this, GroupwiseProductOrderSummary2.class);
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
                        i.putExtra("message_3", "FM");
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });

        mrd_pres_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmSalesReportDashboard.this, MRDPresReport.class);
                        i.putExtra("userName", AmDashboard.globalFMCode);
                        i.putExtra("UserName", AmDashboard.globalFMCode);
                        i.putExtra("report_flag", "MRD");
                        i.putExtra("asm_flag", "N");
                        i.putExtra("sm_flag", "N");
                        i.putExtra("gm_flag", "N");
                        i.putExtra("rm_flag", "N");
                        i.putExtra("fm_flag", "Y");
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });

        fourp_pres_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmSalesReportDashboard.this, MRDPresReport.class);
                        i.putExtra("userName", AmDashboard.globalFMCode);
                        i.putExtra("UserName", AmDashboard.globalFMCode);
                        i.putExtra("report_flag", "4P");
                        i.putExtra("asm_flag", "N");
                        i.putExtra("sm_flag", "N");
                        i.putExtra("gm_flag", "N");
                        i.putExtra("rm_flag", "N");
                        i.putExtra("fm_flag", "Y");
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });

        msp_pres_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(AmSalesReportDashboard.this, MRDPresReport.class);
                        i.putExtra("userName", userName);
                        i.putExtra("UserName", userName);
                        i.putExtra("report_flag", "MSP");
                        i.putExtra("report_flag", "MSP");
                        i.putExtra("asm_flag", "N");
                        i.putExtra("sm_flag", "N");
                        i.putExtra("gm_flag", "N");
                        i.putExtra("rm_flag", "N");
                        i.putExtra("fm_flag", "Y");
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });

    }

    private void initViews() {

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        mpo_wise_product_sale_btn =  findViewById(R.id.mpo_wise_product_sale);
        brand_wise_sale_btn =  findViewById(R.id.brand_wise_sale);
        targetquantity_btn =  findViewById(R.id.targetquantity);
        targetvalue_btn =  findViewById(R.id.targetvalue);
        achivement_btn =  findViewById(R.id.achivement);
        back_btn =  findViewById(R.id.backbt);
        mpo_achv_followup =  findViewById(R.id.mpo_achv_followup);
        admin_product_list =  findViewById(R.id.admin_product_list);
        group_wise_product_ord_summary =  findViewById(R.id.am_grp_wise_prd_ord);
        mrd_pres_report =  findViewById(R.id.mrd_pres_report);
        fourp_pres_report =  findViewById(R.id.fourp_pres_report);
        msp_pres_report =  findViewById(R.id.msp_pres_report);
        dcc_rx_camp = findViewById(R.id.dcc_rx_camp);
        dcc_rx_camp.setVisibility(View.GONE);
        //mrd_pres_report
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060
        Bundle b = getIntent().getExtras();
        UserName = b.getString("UserName");
        UserName_1 = b.getString("userName_1");
        UserName_2 = b.getString("UserName_2");
    }


    @Override
    public void onClick(View v) {


    }

    protected void onPostExecute() {

    }

}
