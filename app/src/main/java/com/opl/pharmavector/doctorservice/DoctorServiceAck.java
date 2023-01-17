package com.opl.pharmavector.doctorservice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import com.opl.pharmavector.R;

import com.opl.pharmavector.adapter.CategoryAdapter7;
import com.opl.pharmavector.model.Category5;
import com.opl.pharmavector.serverCalls.FavouriteCategoriesJsonParser7;

public class DoctorServiceAck extends AppCompatActivity {
    Context context;
    ArrayList<Category5> array_list;
    FavouriteCategoriesJsonParser7 categoryJsonParser;
    String categoriesCsv;
    String categoriesPhone;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_MESSAGE_1 = "message_1";
    public static final String TAG_invoice = "invoice";
    public static final String TAG_target = "target";
    public static final String TAG_achivement = "achivement";
    public static final String TAG_MESSAGE_new_version = "new_version";
    public int success;
    public String message, ord_no, invoice, target_data, achivement, searchString, select_party, my_val, UserName_2;
    public String conference_date;
    public String market_code;
    public String market_name;
    public String venue_name;
    public String pc_rmp_val;
    public String doc_val;
    public String inhouse_val;
    public String total_participant;
    public String venue_charge;
    public String food_per_person;
    public String food_total_bdt;
    public String miscell, miscell_bdt;
    public String impact, TOTAL_BUDGET;
    public String product_list;
    public String approval_user_role;
    public static String UserName;
    Button button, button2, logback;
    TextView user_show;
    int commas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.doc_service_approval_follow);

        intiViews();

        new asyncTask_getCategories().execute();
        logback.setOnClickListener(new View.OnClickListener() {
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

    }

    private void intiViews() {

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        button = findViewById(R.id.selectCategoryButton);
        button.setVisibility(View.GONE);
        button2 = findViewById(R.id.selectCategoryButton2);
        logback = findViewById(R.id.logback);
        logback.setTypeface(fontFamily);
        logback.setText("\uf08b");
        user_show = findViewById(R.id.user_show);
        context = this;
        Bundle b = getIntent().getExtras();
        UserName = b.getString("userName");
        UserName_2 = b.getString("UserName_2");
        user_show.setText(UserName + " " + UserName_2 + " ");
        commas = 0;
    }


    @SuppressLint("StaticFieldLeak")
    public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait...");
            dialog.setMessage("Loading Doctor Service!");
            dialog.show();
            array_list = new ArrayList<Category5>();
            my_val = UserName;
            categoryJsonParser = new FavouriteCategoriesJsonParser7();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            array_list = categoryJsonParser.getParsedCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            ListView mListViewBooks =  findViewById(R.id.category_listView);
            final CategoryAdapter7 CategoryAdapter7 = new CategoryAdapter7(context, R.layout.row_category_4, array_list);
            mListViewBooks.setAdapter(CategoryAdapter7);
            super.onPostExecute(s);
            dialog.dismiss();
        }
    }

}
















