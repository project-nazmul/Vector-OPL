package com.opl.pharmavector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.nativecss.NativeCSS;
import com.opl.pharmavector.mrd_pres_report.MRDPresReport;
import com.opl.pharmavector.report.LocationTrackerActivity;
import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.ProgressDialog;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;

import androidx.cardview.widget.CardView;

public class AdminReportDashboard extends Activity implements View.OnClickListener {
    public String userName_1, userName, userName_2;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public AutoCompleteTextView actv;
    private ListView lv, lv2;
    private ProgressDialog pDialog;
    private DatabaseHandler db;
    private String TAG = Offlinereport.class.getSimpleName();
    ArrayList<HashMap<String, String>> productList;
    ArrayList<HashMap<String, String>> customerlist;
    private Button logout;
    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;
    public String UserName_2;
    public String new_version;
    public String asm_flag, sm_flag, gm_flag;
    Typeface fontFamily;
    private SessionManager session;
    Bundle b;
    Button fourp_pres_report, mrd_pres_report, brand_wise_sale_btn, admin_product_list, product_wise_sale, group_wise_product_ord_summary, msp_pres_report, backbt;
    CardView cardBrandSale, cardProductSale, cardOpsoProduct, cardGroupProduct, cardMrdPrescription, card4pPrescription, cardMspPrescription, cardLocationTracker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminreportdashboard);

        initViews();
        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminReportDashboard.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
            builder.setTitle("Exit !").setMessage("Are you sure you want to exit Vector?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Thread server = new Thread(() -> {
                            JSONParser jsonParser = new JSONParser();
                            List<NameValuePair> params = new ArrayList<>();
                            params.add(new BasicNameValuePair("logout", "logout"));
                            JSONObject json = jsonParser.makeHttpRequest(Login.LOGIN_URL, "POST", params);
                        });
                        server.start();
                        logoutUser();
                    })
                    .setNegativeButton("No", (dialog, which) -> Log.d("MainActivity", ""))
                    .show();
        });

        cardLocationTracker.setOnClickListener(v -> {
            Intent i = new Intent(AdminReportDashboard.this, LocationTrackerActivity.class);
            i.putExtra("userName", userName);
            i.putExtra("userCode", UserName_2);
            i.putExtra("userRole", "AD");
            startActivity(i);
        });

        cardProductSale.setOnClickListener(v -> {
            Thread mysells = new Thread(() -> {
                if (asm_flag.equals("Y")) {
                    Intent i = new Intent(AdminReportDashboard.this, RMWiseProductSale.class);
                    i.putExtra("userName", userName);
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", UserName_2);
                    startActivity(i);
                } else if (sm_flag.equals("Y")) {
                    Intent i = new Intent(AdminReportDashboard.this, ASMWiseProductSale.class);
                    i.putExtra("userName", userName);
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", UserName_2);
                    startActivity(i);
                } else if (gm_flag.equals("Y")) {
                    Intent i = new Intent(AdminReportDashboard.this, SMWiseProductSale.class);
                    i.putExtra("userName", userName);
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", UserName_2);
                    startActivity(i);
                }
            });
            mysells.start();
        });

        cardGroupProduct.setOnClickListener(v -> {
            Thread mysells = new Thread(() -> {
                if (asm_flag.equals("Y")) {
                    Intent i = new Intent(AdminReportDashboard.this, GroupwiseProductOrderSummary2.class);
                    i.putExtra("userName", userName);
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", userName);
                    i.putExtra("message_3", "ASM");
                    startActivity(i);
                } else if (sm_flag.equals("Y")) {
                    Intent i = new Intent(AdminReportDashboard.this, GroupwiseProductOrderSummary2.class);
                    i.putExtra("userName", userName);
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", userName);
                    i.putExtra("message_3", "SM");
                    startActivity(i);
                } else if (gm_flag.equals("Y")) {
                    Intent i = new Intent(AdminReportDashboard.this, GroupwiseProductOrderSummary2.class);
                    i.putExtra("userName", userName);
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", userName);
                    i.putExtra("message_3", "GM");
                    startActivity(i);
                }
            });
            mysells.start();
        });

        cardBrandSale.setOnClickListener(v -> {
            Thread mysells = new Thread(() -> {
                if (asm_flag.equals("Y")) {
                    Intent i = new Intent(AdminReportDashboard.this, RMBrandwiseProductSale.class);
                    i.putExtra("userName", userName);
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", UserName_2);
                    startActivity(i);
                } else if (sm_flag.equals("Y")) {
                    Intent i = new Intent(AdminReportDashboard.this, ASMBrandwiseProductSale.class);
                    i.putExtra("userName", userName);
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", UserName_2);
                    startActivity(i);
                } else if (gm_flag.equals("Y")) {
                    Intent i = new Intent(AdminReportDashboard.this, SMBrandwiseProductSale.class);
                    i.putExtra("userName", userName);
                    i.putExtra("UserName", userName);
                    i.putExtra("UserName_2", UserName_2);
                    startActivity(i);
                }
            });
            mysells.start();
        });

        cardOpsoProduct.setOnClickListener(v -> {
            Thread mysells = new Thread(() -> {
                Intent i = new Intent(AdminReportDashboard.this, AdminProductList.class);
                i.putExtra("userName", userName);
                i.putExtra("UserName", userName);
                i.putExtra("UserName_2", UserName_2);
                startActivity(i);
            });
            mysells.start();
        });

        cardMrdPrescription.setOnClickListener(v -> {
            Thread mysells = new Thread(() -> {
                Intent i = new Intent(AdminReportDashboard.this, MRDPresReport.class);
                i.putExtra("userName", userName);
                i.putExtra("UserName", userName);
                i.putExtra("report_flag", "MRD");
                i.putExtra("asm_flag", asm_flag);
                i.putExtra("sm_flag", sm_flag);
                i.putExtra("gm_flag", gm_flag);
                i.putExtra("rm_flag", "N");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "N");
                startActivity(i);
            });
            mysells.start();
        });

        card4pPrescription.setOnClickListener(v -> {
            Thread mysells = new Thread(() -> {
                Intent i = new Intent(AdminReportDashboard.this, MRDPresReport.class);
                i.putExtra("userName", userName);
                i.putExtra("UserName", userName);
                i.putExtra("report_flag", "4P");
                i.putExtra("asm_flag", asm_flag);
                i.putExtra("sm_flag", sm_flag);
                i.putExtra("gm_flag", gm_flag);
                i.putExtra("rm_flag", "N");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "N");
                startActivity(i);
            });
            mysells.start();
        });

        cardMspPrescription.setOnClickListener(v -> {
            Thread mysells = new Thread(() -> {
                Intent i = new Intent(AdminReportDashboard.this, MRDPresReport.class);
                i.putExtra("userName", userName);
                i.putExtra("UserName", userName);
                i.putExtra("report_flag", "MSP");
                i.putExtra("asm_flag", asm_flag);
                i.putExtra("sm_flag", sm_flag);
                i.putExtra("gm_flag", gm_flag);
                i.putExtra("rm_flag", "N");
                i.putExtra("fm_flag", "N");
                i.putExtra("mpo_flag", "N");
                startActivity(i);
            });
            mysells.start();
        });

        session = new SessionManager(getApplicationContext());
        backbt.setOnClickListener(v -> {
            Thread backthred = new Thread(() -> {
                try {
                    if (asm_flag.equals("Y")) {
                        Intent i = new Intent(AdminReportDashboard.this, AssistantManagerDashboard.class);
                        i.putExtra("UserName", AssistantManagerDashboard.globalASMCode);
                        i.putExtra("new_version", AssistantManagerDashboard.new_version);
                        i.putExtra("UserName_2", AssistantManagerDashboard.globalZONECode);
                        i.putExtra("message_3", AssistantManagerDashboard.message_3);
                        i.putExtra("password", AssistantManagerDashboard.password);
                        i.putExtra("ff_type", AssistantManagerDashboard.ff_type);
                        i.putExtra("vector_version", R.string.vector_version);
                        i.putExtra("emp_code", AssistantManagerDashboard.globalempCode);
                        i.putExtra("emp_name", AssistantManagerDashboard.globalempName);
                        startActivity(i);
                    } else if (sm_flag.equals("Y")) {
                        Intent i = new Intent(AdminReportDashboard.this, SalesManagerDashboard.class);
                        i.putExtra("UserName", SalesManagerDashboard.globalSMCode);
                        i.putExtra("new_version", SalesManagerDashboard.new_version);
                        i.putExtra("UserName_2", SalesManagerDashboard.globalDivisionCode);
                        i.putExtra("message_3", SalesManagerDashboard.message_3);
                        i.putExtra("password", SalesManagerDashboard.password);
                        i.putExtra("ff_type", SalesManagerDashboard.ff_type);
                        i.putExtra("vector_version", R.string.vector_version);
                        i.putExtra("emp_code", SalesManagerDashboard.globalempCode);
                        i.putExtra("emp_name", SalesManagerDashboard.globalempName);
                        startActivity(i);
                    } else if (gm_flag.equals("Y")) {
                        Intent i = new Intent(AdminReportDashboard.this, GMDashboard1.class);
                        i.putExtra("UserName", GMDashboard1.globalAdmin);
                        i.putExtra("new_version", GMDashboard1.new_version);
                        i.putExtra("UserName_2", GMDashboard1.globalAdminDtl);
                        i.putExtra("message_3", GMDashboard1.message_3);
                        i.putExtra("password", GMDashboard1.password);
                        i.putExtra("ff_type", GMDashboard1.ff_type);
                        i.putExtra("vector_version", R.string.vector_version);
                        i.putExtra("emp_code", GMDashboard1.globalempCode);
                        i.putExtra("emp_name", GMDashboard1.globalempName);
                        startActivity(i);
                    }
                    //finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            backthred.start();
        });
    }

    private void initViews() {
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        brand_wise_sale_btn = findViewById(R.id.brand_wise_sale);
        logout = findViewById(R.id.logout);
        admin_product_list = findViewById(R.id.admin_product_list);
        product_wise_sale = findViewById(R.id.product_wise_sale);
        group_wise_product_ord_summary = findViewById(R.id.group_wise_product_ord_summary);
        fourp_pres_report = findViewById(R.id.fourp_pres_report);
        msp_pres_report = findViewById(R.id.msp_pres_report);
        mrd_pres_report = findViewById(R.id.mrd_pres_report);
        cardBrandSale = findViewById(R.id.cardBrandSale);
        cardProductSale = findViewById(R.id.cardProductSale);
        cardOpsoProduct = findViewById(R.id.cardOpsoProduct);
        cardGroupProduct = findViewById(R.id.cardGroupProduct);
        cardMrdPrescription = findViewById(R.id.cardMrdPrescription);
        card4pPrescription = findViewById(R.id.card4pPrescription);
        cardMspPrescription = findViewById(R.id.cardMspPrescription);
        cardLocationTracker = findViewById(R.id.cardLocationTracker);

        logout.setTypeface(fontFamily);
        logout.setText("\uf08b");
        b = getIntent().getExtras();
        userName = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        asm_flag = b.getString("asm_flag");
        sm_flag = b.getString("sm_flag");
        gm_flag = b.getString("gm_flag");
        new_version = b.getString("new_version");
        backbt = (Button) findViewById(R.id.backbt);
        backbt.setTypeface(fontFamily);
        backbt.setText("\uf060 "); //&#xf060
        db = new DatabaseHandler(this);
    }

    private void logoutUser() {
        session.setLogin(false);
        session.invalidate();
        Intent intent = new Intent(AdminReportDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    @Override
    public void onClick(View v) {}
}
