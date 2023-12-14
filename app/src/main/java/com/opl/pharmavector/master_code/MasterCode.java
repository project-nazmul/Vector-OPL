package com.opl.pharmavector.master_code;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.AmDashboard;
import com.opl.pharmavector.AssistantManagerDashboard;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RmDashboard;
import com.opl.pharmavector.SalesManagerDashboard;
import com.opl.pharmavector.master_code.adapter.MasterAdapter;
import com.opl.pharmavector.master_code.adapter.PromoAdapter;
import com.opl.pharmavector.master_code.model.MasterCList;
import com.opl.pharmavector.master_code.model.MasterModel;
import com.opl.pharmavector.pmdVector.DashBoardPMD;
import com.opl.pharmavector.productOffer.ProductOfferList;
import com.opl.pharmavector.promomat.model.Promo;
import com.opl.pharmavector.promomat.util.FixedGridLayoutManager;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.PreferenceManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;

public class MasterCode extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener, MasterAdapter.MasterAdapterListener {
    PreferenceManager preferenceManager;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public ProgressDialog pDialog;
    Button back_btn;
    public int success;
    public String message, ord_no;
    public TextView totqty, totval, masterCode, fromdate, todate;
    public String userName_1, userName, active_string, act_desiredString, admin_code;
    public String from_date, to_date;
    TextView masterRole, masterSl, masterTerriName, masterEmpNo, masterEmpName, user_show1, achivement, masterDepotDsc, week2, week3, week4;
    public String p_code, asm_flag, sm_flag, gm_flag, user_code, promo_type, user_flag, UserName_2;
    int scrollX = 0;
    RecyclerView rvCompany;
    HorizontalScrollView headerScroll;
    PromoAdapter promoAdapter;
    SearchView searchView;
    List<MasterCList> masterList = new ArrayList<>();
    EditText searchview;
    MasterAdapter masterAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_code);

        preferenceManager = new PreferenceManager(this);
        initViews();
        prepareMasterCode();
        setUpRecyclerView();

        rvCompany.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, 2, dy);
                scrollX += dx;
                headerScroll.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        searchview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchview, InputMethodManager.SHOW_IMPLICIT);
                searchview.setFocusable(true);
                searchview.setFocusableInTouchMode(true);
                searchview.setClickable(true);
                searchview.setText("");
            }
        });
        searchview.addTextChangedListener(new TextWatcher() {
            @SuppressLint({"DefaultLocale", "NotifyDataSetChanged"})
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String searchString = s.toString();
//                masterAdapter.getFilter().filter(searchString);
//                masterAdapter.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("searchVal", s.toString());
                if (!s.toString().isEmpty()) {
                    masterCodeFilter(s.toString().trim());
                }
            }
        });
    }

    void masterCodeFilter(String query) {
        List<MasterCList> masterCodeList = new ArrayList<>();

        for (MasterCList codeList : masterList) {
            if (codeList.getEname() != null) {
                if (codeList.getMpoCode().toUpperCase().contains(query.toUpperCase()) || codeList.getFfRoll().toLowerCase().contains(query.toLowerCase()) ||
                        codeList.getEmpno().toUpperCase().contains(query.toUpperCase()) || codeList.getTerriName().toUpperCase().contains(query.toUpperCase()) ||
                        codeList.getEname().toUpperCase().contains(query.toUpperCase()) || codeList.getDepotDesc().toUpperCase().contains(query.toUpperCase())) {
                    masterCodeList.add(codeList);
                }
            }
        }
        masterAdapter.searchMasterCode(masterCodeList);
    }

    public void prepareMasterCode() {
        pDialog = new ProgressDialog(MasterCode.this);
        pDialog.setMessage("Loading Master Code...");
        pDialog.setTitle("Please wait ");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MasterModel> call = apiInterface.getNewMasterCode(user_code);

        call.enqueue(new Callback<MasterModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<MasterModel> call, retrofit2.Response<MasterModel> response) {
                assert response.body() != null;
                List<MasterCList> masterCodeList = (response.body().getMasterCodeList());

                if (response.isSuccessful()) {
                    masterList.addAll(masterCodeList);
                    masterAdapter.notifyDataSetChanged();
                    pDialog.dismiss();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(MasterCode.this, "No data Available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MasterModel> call, Throwable t) {
                pDialog.dismiss();
                prepareMasterCode();
            }
        });
    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        rvCompany = findViewById(R.id.rvCompany);
        headerScroll = findViewById(R.id.headerScroll);
        user_show1 = findViewById(R.id.user_show1);
        back_btn = findViewById(R.id.backbt);
        masterCode = findViewById(R.id.masterTerriCode);
        masterRole = findViewById(R.id.masterUserRole);
        masterSl = findViewById(R.id.masterSlNo);
        masterTerriName = findViewById(R.id.masterTerriName);
        masterEmpNo = findViewById(R.id.masterEmpNo);
        masterEmpName = findViewById(R.id.masterEmpName);
        achivement = findViewById(R.id.achivement_sales_admin);
        masterDepotDsc = findViewById(R.id.masterDepotDsc);
        week2 = findViewById(R.id.week2);
        week3 = findViewById(R.id.week3);
        week4 = findViewById(R.id.week4);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");
        Bundle b = getIntent().getExtras();
        userName = b.getString("userName");
        admin_code = b.getString("admin_code");
        UserName_2 = b.getString("UserName_2");
        promo_type = b.getString("promo_type");
        user_flag = b.getString("user_flag");
        user_code = b.getString("user_code");
        searchview = findViewById(R.id.p_search);
        //searchview.setFilters(new InputFilter[] { new InputFilter.AllCaps()} );
        //searchview.setFilters(new InputFilter[] { new InputFilter.LengthFilter(5)} );
    }

    public void setUpRecyclerView() {
        masterAdapter = new MasterAdapter(this, masterList, this);
        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvCompany.setLayoutManager(manager);
        rvCompany.setAdapter(masterAdapter);
        rvCompany.addItemDecoration(new DividerItemDecoration(MasterCode.this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void finishActivity(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {}

    protected void onPostExecute() {}

    @Override
    public void onPromoSelected(MasterCList promo) {
        String user_code = promo.getMpoCode();
        String user_role = promo.getFfRoll();
        String ff_type = promo.getFfType();
        String user_designation = promo.getFfDesc();
        String user_terriName = promo.getTerriName();
        String message_1 = promo.getDepotDesc();
        String message_2 = message_1;
        String emp_code = promo.getEmpno();
        String emp_name = promo.getEname();

        switch (user_role) {
            case "MPO": {
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterCode.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("Master Code Access").setMessage("You are going to login into MPO screen. Press Confirm to proceed")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(MasterCode.this, Dashboard.class);
                                        i.putExtra("new_version", "new_version");
                                        i.putExtra("message_3", user_role);
                                        i.putExtra("Designation", user_designation);
                                        i.putExtra("vector_version", "MC1");
                                        i.putExtra("UserName", user_code);
                                        i.putExtra("UserName_1", message_1);
                                        i.putExtra("UserName_2", message_2);
                                        i.putExtra("new_version", R.string.vector_version);
                                        i.putExtra("TerriName", user_terriName);
                                        i.putExtra("emp_code", emp_code);
                                        i.putExtra("emp_name", emp_name);
                                        i.putExtra("ff_type", ff_type);
                                        startActivity(i);
                                    }
                                });
                                server.start();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
                break;
            }

            case "AM": {
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterCode.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("Confirm").setMessage("You are going to login into Area Manager screen. Press Confirm to proceed")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(MasterCode.this, AmDashboard.class);
                                        i.putExtra("UserName", user_code);
                                        i.putExtra("Designation", user_designation);
                                        i.putExtra("UserName_1", message_1);
                                        i.putExtra("UserName_2", message_2);
                                        i.putExtra("new_version", "new_version");
                                        i.putExtra("message_3", "FM");
                                        i.putExtra("TerriName", user_terriName);
                                        i.putExtra("emp_code", emp_code);
                                        i.putExtra("emp_name", emp_name);
                                        i.putExtra("ff_type", ff_type);
                                        startActivity(i);
                                    }
                                });
                                server.start();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
                break;
            }
            case "RM": {
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterCode.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("Master Code Access").setMessage("You are going to login into Regional Manager screen. Press Confirm to proceed")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(MasterCode.this, RmDashboard.class);
                                        i.putExtra("UserName", user_code);
                                        i.putExtra("Designation", user_designation);
                                        i.putExtra("UserName_1", message_1);
                                        i.putExtra("UserName_2", message_2);
                                        i.putExtra("new_version", "new_version");
                                        i.putExtra("message_3", user_role);
                                        i.putExtra("TerriName", user_terriName);
                                        i.putExtra("emp_code", emp_code);
                                        i.putExtra("emp_name", emp_name);
                                        i.putExtra("ff_type", ff_type);
                                        startActivity(i);
                                    }
                                });
                                server.start();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
                break;
            }
            case "ASM": {
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterCode.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("Master Code Access").setMessage("You are going to login into Assistant/Deputy Sales Manager screen. Press Confirm to proceed")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(MasterCode.this, AssistantManagerDashboard.class);
                                        i.putExtra("UserName", user_code);
                                        i.putExtra("Designation", user_designation);
                                        i.putExtra("UserName_1", message_1);
                                        i.putExtra("UserName_2", message_2);
                                        i.putExtra("new_version", "new_version");
                                        i.putExtra("message_3", "AM");
                                        i.putExtra("TerriName", user_terriName);
                                        i.putExtra("emp_code", emp_code);
                                        i.putExtra("emp_name", emp_name);
                                        i.putExtra("ff_type", ff_type);
                                        startActivity(i);
                                    }
                                });
                                server.start();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
                break;
            }
            case "SM": {
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterCode.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("Master Code Access").setMessage("You are going to login into Sales Manager screen. Press Confirm to proceed")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(MasterCode.this, SalesManagerDashboard.class);
                                        i.putExtra("UserName", user_code);
                                        i.putExtra("Designation", user_designation);
                                        i.putExtra("UserName_1", message_1);
                                        i.putExtra("UserName_2", message_2);
                                        i.putExtra("new_version", "new_version");
                                        i.putExtra("message_3", user_role);
                                        i.putExtra("TerriName", user_terriName);
                                        i.putExtra("emp_code", emp_code);
                                        i.putExtra("emp_name", emp_name);
                                        i.putExtra("ff_type", ff_type);
                                        startActivity(i);
                                    }
                                });
                                server.start();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
                break;
            }
            case "PMD": {
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterCode.this, R.style.Theme_Design_BottomSheetDialog);
                builder.setTitle("Master Code Access").setMessage("You are going to login into Sales Manager screen. Press Confirm to proceed")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(MasterCode.this, DashBoardPMD.class);
                                        i.putExtra("executive_code", user_code);
                                        i.putExtra("Designation", user_designation);
                                        i.putExtra("UserName_1", message_1);
                                        i.putExtra("UserName_2", message_2);
                                        i.putExtra("new_version", "new_version");
                                        i.putExtra("message_3", user_role);
                                        i.putExtra("TerriName", user_terriName);
                                        i.putExtra("executive_code", emp_code);
                                        i.putExtra("executive_name", emp_name);
                                        i.putExtra("ff_type", ff_type);
                                        startActivity(i);
                                    }
                                });
                                server.start();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
                break;
            }
        }
    }

    @Override
    public void onPromoSelected(Promo promo) {

    }
}



