package com.opl.pharmavector.dcfpFollowup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.R;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DcfpDoctorListActivity extends Activity implements DcfpDoctorListAdapter1.DcfpClickListener1 {
    int scrollX = 0, scrollY = 0;
    private Context context;
    private TextView firstWeek;
    HorizontalScrollView scrollView;
    Button doctorListBtn, backBtn;
    private RecyclerView dcfpListRecycler;
    private DcfpDoctorListAdapter1 dcfpDoctorAdapter1;
    private DcfpDoctorListAdapter2 dcfpDoctorAdapter2;
    AutoCompleteTextView autoDoctorFFList;
    public String userName, userName_2, new_version, message_3;
    PreferenceManager preferenceManager;
    private List<DcfpDoctorReportList> dcfpDoctorLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcfp_doctor_list);

        initViews();
        getDcfpDoctorListInfo();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        scrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                //final Button button3 = (Button) findViewById(R.id.button3);
//                final LinearLayout firstWeek = findViewById(R.id.secondWeek);
//                int scrollWidth = 0;
//                final int count = ((LinearLayout) scrollView.getChildAt(0)).getChildCount();
//
//                for (int i = 0; i < count; i++) {
//                    final View child = ((LinearLayout) scrollView.getChildAt(0)).getChildAt(i);
//                    if (child != firstWeek) {
//                        scrollWidth += child.getWidth();
//                    } else {
//                        break;
//                    }
//                }
//                scrollView.scrollBy(700, 0);
//                //scrollView.smoothScrollTo(200, 200);
//            }
//        });

        //doctorRecycler.canScrollHorizontally(2);
//        doctorRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, scrollX, scrollY);
//                //scrollX += dx + 2;
//                //headerScroll.scrollTo(scrollX, 0);
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
    }

    private void initViews() {
        context = this;
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        preferenceManager = new PreferenceManager(this);
        //count = preferenceManager.getTasbihCounter();

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        userName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
        backBtn = findViewById(R.id.backBtn);
        //firstWeek = findViewById(R.id.tvFirstWeek);
        backBtn.setTypeface(fontFamily);
        backBtn.setText("\uf060 ");
        doctorListBtn = findViewById(R.id.doctorListBtn);
        dcfpListRecycler = findViewById(R.id.recyclerDcfpList);
        autoDoctorFFList = findViewById(R.id.autoDoctorMpoList);
        autoDoctorFFList.setText(userName);
        scrollView = findViewById(R.id.scrollView);
        dcfpDoctorAdapter2 = new DcfpDoctorListAdapter2(DcfpDoctorListActivity.this, dcfpDoctorLists);
        dcfpDoctorAdapter1 = new DcfpDoctorListAdapter1(DcfpDoctorListActivity.this, dcfpDoctorLists, DcfpDoctorListActivity.this);
        //scrollX = firstWeek.getLeft();
        //scrollY = firstWeek.getTop();
        //scrollView.scrollTo(5, 4);
    }

    public void getDcfpDoctorListInfo() {
        ProgressDialog dcfpDoctorDialog = new ProgressDialog(DcfpDoctorListActivity.this);
        dcfpDoctorDialog.setMessage("DCFP Doctor List Loading...");
        dcfpDoctorDialog.setTitle("DCFP Doctor List Followup");
        dcfpDoctorDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DcfpDoctorReportModel> call = apiInterface.getDcfpDoctorList(userName);
        dcfpDoctorLists.clear();

        call.enqueue(new Callback<DcfpDoctorReportModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<DcfpDoctorReportModel> call, @NonNull retrofit2.Response<DcfpDoctorReportModel> response) {
                //List<DcfpDoctorReportList> dcfpDoctorLists = null;
                if (response.body() != null) {
                    dcfpDoctorLists = response.body().getDcfpDoctorLists();
                }
                Log.d("dcfpData", dcfpDoctorLists.toString());

                if (response.isSuccessful()) {
                    LinearLayoutManager manager1 = new LinearLayoutManager(DcfpDoctorListActivity.this);
                    dcfpDoctorAdapter1 = new DcfpDoctorListAdapter1(DcfpDoctorListActivity.this, dcfpDoctorLists, DcfpDoctorListActivity.this);
                    dcfpListRecycler.setLayoutManager(manager1);
                    dcfpListRecycler.setAdapter(dcfpDoctorAdapter1);
                    dcfpDoctorAdapter1.notifyDataSetChanged();

//                    LinearLayoutManager manager2 = new LinearLayoutManager(DcfpDoctorListActivity.this);
//                    dcfpDoctorAdapter2 = new DcfpDoctorListAdapter2(DcfpDoctorListActivity.this, dcfpDoctorLists);
//                    dcfpListRecycler.setLayoutManager(manager2);
//                    dcfpListRecycler.setAdapter(dcfpDoctorAdapter2);
                    //doctorRecycler.addItemDecoration(new DividerItemDecoration(DcfpDoctorListActivity.this, DividerItemDecoration.VERTICAL));
//                    for (int i = 0; i < (doctorFFData != null ? doctorFFData.size() : 0); i++) {
//                        doctorFFList.add(new DoctorFFList(
//                                doctorFFData.get(i).getMpoCode(),
//                                doctorFFData.get(i).getTerriName()));
//                    }
                    dcfpDoctorDialog.dismiss();
                    //populateDoctorFFList();
                    //Log.d("company List", companyDatalist.get(0).getComDesc());
                } else {
                    dcfpDoctorDialog.dismiss();
                    Toast.makeText(DcfpDoctorListActivity.this, "No data Available !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DcfpDoctorReportModel> call, @NonNull Throwable t) {
                dcfpDoctorDialog.dismiss();
                //dcfpDoctorListInfo();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDcfpClick1(int position, DcfpDoctorReportList model) {
        scrollX = position;
        //scrollView.scrollTo(0, position);
        dcfpListRecycler.post(new Runnable() {
            @Override
            public void run() {
                dcfpListRecycler.scrollToPosition(position);
                //Here adapter.getItemCount()== child count
            }
        });
        dcfpDoctorAdapter2.notifyDataSetChanged();
    }
}