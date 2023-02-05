package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.opl.pharmavector.R;
import com.opl.pharmavector.depot_report.CustCreditActivity;
import com.opl.pharmavector.depot_report.CustOutstandingActivity;
import com.opl.pharmavector.depot_report.CustReplacementActivity;
import com.opl.pharmavector.depot_report.Cust_Sp_Pct_Activity;
import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionSurveyReport;
import com.opl.pharmavector.util.NetInfo;

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
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import es.dmoral.toasty.Toasty;

public class Report extends Activity implements OnClickListener {
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
    public String CurrenOrder, usename12;
    private String UserName, UserName_1, UserName_2;
    public AutoCompleteTextView actv;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, growth;
    private final ArrayList<String> array_sort = new ArrayList<String>();
    private final String URL_Achievement = BASE_URL + "mposalesreports/Achievement1.php";
    Button viewbycustomer_btn, viewbycustomersale_btn, viewbycustomerreturn_btn, viewbycustomerorderstatus_btn;
    Button viewstock_btn, targetquantity_btn, targetvalue_btn, customervalue_btn, btn_cust_credit, btn_cust_sp_pct, btn_cust_outstanding, btn_cust_replacement;
    CardView cardProductQuantity, cardProductValue, cardOrderQuantity, cardOrderValue, cardReturnStatus, cardCurrentStock, cardPartyOrder, cardSaleGrowth, cardOpsoninList, cardGroupProduct,
            cardMrdPrescription, cardMspPrescription, card4pPrescription, cardCreditList, cardSpecialRate, cardOutstandingList, cardCustomerReplacement;
    Button orderinvoice_btn, achivement_btn, admin_product_list, group_wise_product_ord_summary, back_btn, mrd_pres_report, fourp_pres_report, msp_pres_report;
    String message_3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll);

        initViews();
        cardCreditList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, CustCreditActivity.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardSpecialRate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, Cust_Sp_Pct_Activity.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardOutstandingList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, CustOutstandingActivity.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardCustomerReplacement.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, CustReplacementActivity.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardProductQuantity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");
                    //String userName = b.getString("UserName");
                    final String UserName_1 = b.getString("userName_1");
                    final String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, Targetquantity.class);
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
                    }
                });
                mysells.start();
            }
        });
        cardProductValue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");
                    //String userName = b.getString("UserName");
                    final String UserName_1 = b.getString("userName_1");
                    final String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, Targetvalue.class);
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
                    }
                });
                mysells.start();
            }
        });
        cardOrderQuantity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, Viewbycustomer.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardOrderValue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, Viewbycustomersale.class);
                            System.out.println("userName ");
                            Log.d("Changepassword", "Login");
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardReturnStatus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, Viewbycustomerreturn.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardPartyOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, Viewbycustomerorderstatus.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardCurrentStock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, Stokeview.class);
                            System.out.println("userName ");
                            Log.d("Changepassword", "Login");
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        back_btn.setOnClickListener(new OnClickListener() {
            final Bundle b = getIntent().getExtras();

            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
                backthred.start();
            }
        });
        cardSaleGrowth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetInfo.isOnline(getBaseContext())) {
                    showSnack();
                } else {
                    final JSONParser jsonParser = new JSONParser();
                    final List<NameValuePair> params = new ArrayList<NameValuePair>();
                    Bundle b = getIntent().getExtras();
                    usename12 = b.getString("UserName");
                    pDialog = new ProgressDialog(Report.this);
                    pDialog.setMessage("Calculating your Achievment ");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();
                    String userName = b.getString("UserName");
                    params.add(new BasicNameValuePair("id", Dashboard.globalmpocode));

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
                                Log.w("please wait ...." + message, json.toString());
                                if (success == 1) {
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
                            Log.e("mpoflag==>", Dashboard.ff_type);
                            Intent sameint = new Intent(Report.this, Achievement.class);
                            sameint.putExtra("UserName", MPO_CODE);
                            sameint.putExtra("Ord_NO", message);
                            sameint.putExtra("invoice", invoice);
                            sameint.putExtra("target", target_data);
                            sameint.putExtra("achivement", achivement);
                            sameint.putExtra("growth", growth);
                            sameint.putExtra("userName_1", usename12);
                            sameint.putExtra("UserName_2", UserName_2);
                            startActivity(sameint);
                            pDialog.dismiss();
                        }

                        private void SaveToDataBase() {}
                    });
                    server.start();
                }
            }
        });
        cardOpsoninList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, AdminProductList.class);
                            i.putExtra("userName", userName);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_2", UserName_2);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardGroupProduct.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    final Bundle b = getIntent().getExtras();
                    final String userName = b.getString("UserName");
                    final String UserName_1 = b.getString("userName_1");
                    final String UserName_2 = b.getString("userName_2");

                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, GroupwiseProductOrderSummary2.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", userName);
                            i.putExtra("UserName_2", userName);
                            i.putExtra("userName", userName);
                            i.putExtra("userName_1", userName);
                            i.putExtra("userName_2", userName);
                            //Log.e("passedvalues==>",userName);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
//		customervalue_btn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Thread mysells = new Thread(new Runnable() {
//					final Bundle b = getIntent().getExtras();
//					final String userName = b.getString("UserName");
//					//String userName = b.getString("UserName");
//					final String UserName_1 = b.getString("userName_1");
//					final String UserName_2 = b.getString("userName_2");
//
//					@Override
//					public void run() {
//						if (!NetInfo.isOnline(getBaseContext())) {
//							showSnack();
//						}
//						else {
//							Intent i = new Intent(Report.this, Customervalue.class);
//							i.putExtra("UserName", UserName);
//							i.putExtra("new_version", userName);
//							i.putExtra("UserName_1", UserName_1);
//							i.putExtra("UserName_2", UserName_2);
//							i.putExtra("userName", userName);
//							i.putExtra("new_version", userName);
//							i.putExtra("userName_1", UserName_1);
//							i.putExtra("userName_2", UserName_2);
//							Log.d("userName", "UserName" + userName);
//							Log.d("UserName_1", "UserName_1" + UserName_1);
//							Log.d("UserName_2", "UserName_2" + UserName_2);
//							startActivity(i);
//						}
//					}
//				});
//				mysells.start();
//			}
//		});
//		orderinvoice_btn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Thread mysells = new Thread(new Runnable() {
//					final Bundle b = getIntent().getExtras();
//					final String userName = b.getString("UserName");
//					//String userName = b.getString("UserName");
//					final String UserName_1 = b.getString("userName_1");
//					final String UserName_2 = b.getString("userName_2");
//
//					@Override
//					public void run() {
//						if (!NetInfo.isOnline(getBaseContext())) {
//							showSnack();
//						}
//						else {
//							Intent i = new Intent(Report.this, Orderinvoice.class);
//							i.putExtra("UserName", UserName);
//							i.putExtra("new_version", userName);
//							i.putExtra("UserName_1", UserName_1);
//							i.putExtra("UserName_2", UserName_2);
//							i.putExtra("userName", userName);
//							i.putExtra("new_version", userName);
//							i.putExtra("userName_1", UserName_1);
//							i.putExtra("userName_2", UserName_2);
//							Log.d("userName", "UserName" + userName);
//							Log.d("UserName_1", "UserName_1" + UserName_1);
//							Log.d("UserName_2", "UserName_2" + UserName_2);
//							startActivity(i);
//						}
//					}
//				});
//				mysells.start();
//			}
//		});
        cardMrdPrescription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            //showSnack();
                            Intent i = new Intent(Report.this, MRDPresReport.class);
                            i.putExtra("userName", userName);
                            i.putExtra("UserName", userName);
                            i.putExtra("report_flag", "MRD");
                            i.putExtra("asm_flag", "N");
                            i.putExtra("sm_flag", "N");
                            i.putExtra("gm_flag", "N");
                            i.putExtra("rm_flag", "N");
                            i.putExtra("fm_flag", "N");
                            i.putExtra("mpo_flag", "Y");
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        cardMspPrescription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, MRDPresReport.class);
                            i.putExtra("userName", userName);
                            i.putExtra("UserName", userName);
                            i.putExtra("report_flag", "MSP");
                            i.putExtra("asm_flag", "N");
                            i.putExtra("sm_flag", "N");
                            i.putExtra("gm_flag", "N");
                            i.putExtra("rm_flag", "N");
                            i.putExtra("fm_flag", "N");
                            i.putExtra("mpo_flag", "Y");
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
        card4pPrescription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        } else {
                            Intent i = new Intent(Report.this, MRDPresReport.class);
                            i.putExtra("userName", userName);
                            i.putExtra("UserName", userName);
                            i.putExtra("report_flag", "4P");
                            i.putExtra("asm_flag", "N");
                            i.putExtra("sm_flag", "N");
                            i.putExtra("gm_flag", "N");
                            i.putExtra("rm_flag", "N");
                            i.putExtra("fm_flag", "N");
                            i.putExtra("mpo_flag", "Y");
                            startActivity(i);
                        }
                    }
                });
                mysells.start();
            }
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        cardOrderQuantity = findViewById(R.id.cardOrderQuantity);
        cardOrderValue = findViewById(R.id.cardOrderValue);
        cardReturnStatus = findViewById(R.id.cardReturnStatus);
        cardPartyOrder = findViewById(R.id.cardPartyOrder);
        cardCurrentStock = findViewById(R.id.cardCurrentStock);
        cardProductQuantity = findViewById(R.id.cardProductQuantity);
        cardProductValue = findViewById(R.id.cardProductValue);
        //customervalue_btn = findViewById(R.id.customervalue);
        //orderinvoice_btn = findViewById(R.id.orderinvoice);
        cardSaleGrowth = findViewById(R.id.cardSaleGrowth);
        back_btn = findViewById(R.id.backbt);
        cardOpsoninList = findViewById(R.id.cardOpsoninList);
        cardGroupProduct = findViewById(R.id.cardGroupProduct);
        cardMrdPrescription = findViewById(R.id.cardMrdPrescription);
        card4pPrescription = findViewById(R.id.card4pPrescription);
        cardMspPrescription = findViewById(R.id.cardMspPrescription);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 "); //&#xf060
        cardCreditList = findViewById(R.id.cardCreditList);
        cardSpecialRate = findViewById(R.id.cardSpecialRate);
        cardOutstandingList = findViewById(R.id.cardOutstandingList);
        cardCustomerReplacement = findViewById(R.id.cardCustomerReplacement);

        Bundle b = getIntent().getExtras();
        UserName = b.getString("UserName");
        UserName_1 = b.getString("userName_1");
        UserName_2 = b.getString("UserName_2");
        message_3 = b.getString("message_3");
    }

    private void showSnack() {
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "No internet Connection, Please Check Your Connection";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    @Override
    public void onClick(View v) {}

    protected void onPostExecute() {}
}
