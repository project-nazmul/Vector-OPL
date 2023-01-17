

//RmDCCFollowupnew


package com.opl.pharmavector;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL_RM;

import java.net.MalformedURLException;
import java.net.URL;
import java.lang.Runnable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.nativecss.NativeCSS;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;


public class RmDCCFollowupnew extends Activity implements OnClickListener {
    private static Activity parent;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    // array list for spinner adapter
    private ArrayList<Category6> categoriesList;
    private ArrayList<Category6> categoriesList2,categoriesList3,categoriesList4,categoriesList5,categoriesList6,categoriesList7,
            categoriesList8,categoriesList9,categoriesList10,categoriesList11,categoriesList12;









    public ProgressDialog pDialog;
    ListView productListView;
    ListView productListView2,productListView3,productListView4,productListView5,productListView6,productListView7,productListView8,
            productListView9,productListView10,productListView11,productListView12;
    Button submit,submitBtn;
    // private EditText current_qnty;
    EditText qnty;
    Boolean result;
    EditText inputOne, inputtwo;
    public int success;
    public String message, ord_no,asm_code,asm_flag,gm_code,gm_flag;
    TextView date2, ded,fromdate,todate,detail_head;
    int textlength = 0;
    public TextView totqty, totval;
    //public android.widget.Spinner ordspin;
    public String userName_1,userName,active_string,act_desiredString;
    public String from_date,to_date;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public  String test;
    //public String CurrenCustomer="";
    //public AutoCompleteTextView actv;
    public static ArrayList<String> sl;
    public static ArrayList<String> p_ids;
    public static ArrayList<Integer> p_quanty;
    public static ArrayList<String> PROD_RATE;
    public static ArrayList<String> PROD_VAT;
    public static ArrayList<String> PROD_VAT_2;
    public static ArrayList<String> PROD_VAT_3;
    public static ArrayList<String> PROD_VAT_4;


    public static ArrayList<String> PROD_VAT_5;
    public static ArrayList<String> PROD_VAT_6;
    public static ArrayList<String> PROD_VAT_7;

    public static ArrayList<String> PROD_VAT_8;




    private ArrayList<String> array_sort = new ArrayList<String>();




    private String URL_DETAIL_VIEW =BASE_URL_RM+"rm_dcc_campaign/jan_followup.php";
    private String URL_FEB =BASE_URL_RM+"rm_dcc_campaign/feb_followup.php";
    private String URL_MAR =BASE_URL_RM+"rm_dcc_campaign/mar_followup.php";
    private String URL_APR =BASE_URL_RM+"rm_dcc_campaign/apr_followup.php";
    private String URL_MAY =BASE_URL_RM+"rm_dcc_campaign/may_followup.php";
    private String URL_JUN =BASE_URL_RM+"rm_dcc_campaign/jun_followup.php";
    private String URL_JUL =BASE_URL_RM+"rm_dcc_campaign/jul_followup.php";
    private String URL_AUG =BASE_URL_RM+"rm_dcc_campaign/aug_followup.php";
    private String URL_SEP =BASE_URL_RM+"rm_dcc_campaign/sep_followup.php";
    private String URL_OCT =BASE_URL_RM+"rm_dcc_campaign/oct_followup.php";
    private String URL_NOV =BASE_URL_RM+"rm_dcc_campaign/nov_followup.php";
    private String URL_DEC =BASE_URL_RM+"rm_dcc_campaign/dec_followup.php";







    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.amdccfollowup_new);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(),"fonts/fontawesome.ttf");




        productListView = (ListView) findViewById(R.id.pListView1);
        productListView2 = (ListView) findViewById(R.id.pListView2);
        productListView3 = (ListView) findViewById(R.id.pListView3);
        productListView4 = (ListView) findViewById(R.id.pListView4);
        productListView4 = (ListView) findViewById(R.id.pListView4);
        productListView5 = (ListView) findViewById(R.id.pListView5);


        /*


         */


        LinearLayout l_dec= (LinearLayout) findViewById(R.id.dec);
        View v_dec=(View) findViewById(R.id.v_dec);
        NestedScrollView n_dec = findViewById(R.id.n_dec);


        LinearLayout l_nov= (LinearLayout) findViewById(R.id.nov);
        View v_nov=(View) findViewById(R.id.v_nov);
        NestedScrollView n_nov = findViewById(R.id.n_nov);


        LinearLayout l_oct= (LinearLayout) findViewById(R.id.oct);
        View v_oct=(View) findViewById(R.id.v_oct);
        NestedScrollView n_oct = findViewById(R.id.n_oct);


        LinearLayout l_sep= (LinearLayout) findViewById(R.id.sep);
        View v_sep=(View) findViewById(R.id.v_sep);
        NestedScrollView n_sep = findViewById(R.id.n_sep);


        LinearLayout l_aug= (LinearLayout) findViewById(R.id.aug);
        View v_aug=(View) findViewById(R.id.v_aug);
        NestedScrollView n_aug = findViewById(R.id.n_aug);

        LinearLayout l_jul= (LinearLayout) findViewById(R.id.jul);
        View v_jul=(View) findViewById(R.id.v_jul);
        NestedScrollView n_jul = findViewById(R.id.n_jul);


        LinearLayout l_jun= (LinearLayout) findViewById(R.id.jun);
        View v_jun=(View) findViewById(R.id.v_jun);
        NestedScrollView n_jun = findViewById(R.id.n_jun);


        LinearLayout l_may= (LinearLayout) findViewById(R.id.may);
        View v_may=(View) findViewById(R.id.v_may);
        NestedScrollView n_may = findViewById(R.id.n_may);


        LinearLayout l_apr= (LinearLayout) findViewById(R.id.apr);
        View v_apr=(View) findViewById(R.id.v_apr);
        NestedScrollView n_apr = findViewById(R.id.n_apr);



        LinearLayout l_mar= (LinearLayout) findViewById(R.id.mar);
        View v_mar=(View) findViewById(R.id.v_mar);
        NestedScrollView n_mar = findViewById(R.id.n_mar);

        LinearLayout l_feb= (LinearLayout) findViewById(R.id.feb);
        View v_feb=(View) findViewById(R.id.v_feb);
        NestedScrollView n_feb = findViewById(R.id.n_feb);


        productListView6 = (ListView) findViewById(R.id.pListView6);
        productListView7 = (ListView) findViewById(R.id.pListView7);
        productListView8 = (ListView) findViewById(R.id.pListView8);
        productListView9 = (ListView) findViewById(R.id.pListView9);
        productListView10 = (ListView) findViewById(R.id.pListView10);
        productListView11 = (ListView) findViewById(R.id.pListView11);
        productListView12 = (ListView) findViewById(R.id.pListView12);




        Button back_btn = (Button) findViewById(R.id.backbt);








        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060


        final LinearLayout ln = (LinearLayout) findViewById(R.id.totalshow);

        totqty = (TextView) findViewById(R.id.totalsellquantity);
        totval = (TextView) findViewById(R.id.totalsellvalue);
        detail_head = (TextView) findViewById(R.id.detail_head);


        int listsize = productListView.getChildCount();
        Log.i("Size of ProductLIstview", "ProductLIstView SIZE: " + listsize);

        int listsize2 = productListView2.getChildCount();
        // int listsize3 = productListView3.getChildCount();


        // Log.i("SizeofProductLIstview2", "ProductLIstView2 SIZE: " + listsize2);

        p_ids = new ArrayList<String>();
        p_quanty = new ArrayList<Integer>();
        PROD_RATE = new ArrayList<String>();


        categoriesList = new ArrayList<Category6>();
        categoriesList2 = new ArrayList<Category6>();
        categoriesList3 = new ArrayList<Category6>();
        categoriesList4 = new ArrayList<Category6>();
        categoriesList5 = new ArrayList<Category6>();
        categoriesList6 = new ArrayList<Category6>();
        categoriesList7 = new ArrayList<Category6>();
        categoriesList8 = new ArrayList<Category6>();
        categoriesList9 = new ArrayList<Category6>();
        categoriesList10 = new ArrayList<Category6>();
        categoriesList11 = new ArrayList<Category6>();
        categoriesList12 = new ArrayList<Category6>();

        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        String UserName_2 = b.getString("UserName_2");


        asm_flag= b.getString("asm_flag");
        asm_code= b.getString("asm_code");

        gm_code= b.getString("gm_code");
        gm_flag= b.getString("gm_flag");


        Toast.makeText(RmDCCFollowupnew.this, gm_flag, Toast.LENGTH_LONG).show();



        Calendar now = Calendar.getInstance();
        //
        System.out.println("CurrentYearis : " + now.get(Calendar.YEAR));
        // month start from 0 to 11
        System.out.println("CurrentMonthis : " + (now.get(Calendar.MONTH) + 1));
        System.out.println("CurrentDateis : " + now.get(Calendar.DATE));
        int current_month= (now.get(Calendar.MONTH) + 1);
        System.out.println("checkcurrentmonth " +  current_month);




        if(current_month == 12){

            new Dec().execute();
            new Oct().execute();
            new Sep().execute();
            new Aug().execute();
            new Jul().execute();
            new Jun().execute();
            new May().execute();
            new Apr().execute();
            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }


        else if(current_month == 11){


            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);

            new Nov().execute();
            new Oct().execute();
            new Sep().execute();
            new Aug().execute();
            new Jul().execute();
            new Jun().execute();
            new May().execute();
            new Apr().execute();
            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }




        else if(current_month == 10){


            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);
            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);

            new Oct().execute();
            new Sep().execute();
            new Aug().execute();
            new Jul().execute();
            new Jun().execute();
            new May().execute();
            new Apr().execute();
            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }


        else if(current_month == 9){



            l_oct.setVisibility(View.GONE);
            v_oct.setVisibility(View.GONE);
            n_oct.setVisibility(View.GONE);
            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);
            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);




            new Sep().execute();
            new Aug().execute();
            new Jul().execute();
            new Jun().execute();
            new May().execute();
            new Apr().execute();
            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }



        else if(current_month == 8){


            l_sep.setVisibility(View.GONE);
            v_sep.setVisibility(View.GONE);
            n_sep.setVisibility(View.GONE);
            l_oct.setVisibility(View.GONE);
            v_oct.setVisibility(View.GONE);
            n_oct.setVisibility(View.GONE);
            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);
            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);





            new Aug().execute();
            new Jul().execute();
            new Jun().execute();
            new May().execute();
            new Apr().execute();
            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }


        else  if(current_month == 7){


            l_aug.setVisibility(View.GONE);
            v_aug.setVisibility(View.GONE);
            n_aug.setVisibility(View.GONE);


            l_sep.setVisibility(View.GONE);
            v_sep.setVisibility(View.GONE);
            n_sep.setVisibility(View.GONE);

            l_oct.setVisibility(View.GONE);
            v_oct.setVisibility(View.GONE);
            n_oct.setVisibility(View.GONE);



            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);

            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);








            new Jul().execute();
            new Jun().execute();
            new May().execute();
            new Apr().execute();
            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }




        else if(current_month == 6){


            l_aug.setVisibility(View.GONE);
            v_aug.setVisibility(View.GONE);
            n_aug.setVisibility(View.GONE);


            l_sep.setVisibility(View.GONE);
            v_sep.setVisibility(View.GONE);
            n_sep.setVisibility(View.GONE);

            l_oct.setVisibility(View.GONE);
            v_oct.setVisibility(View.GONE);
            n_oct.setVisibility(View.GONE);
            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);

            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);



            l_jul.setVisibility(View.GONE);
            v_jul.setVisibility(View.GONE);
            n_jul.setVisibility(View.GONE);


            new Jun().execute();
            new May().execute();
            new Apr().execute();
            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }

        else if(current_month == 5){

            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);

            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);

            l_jun.setVisibility(View.GONE);
            v_jun.setVisibility(View.GONE);
            n_jun.setVisibility(View.GONE);
            l_jul.setVisibility(View.GONE);
            v_jul.setVisibility(View.GONE);
            n_jul.setVisibility(View.GONE);

            l_aug.setVisibility(View.GONE);
            v_aug.setVisibility(View.GONE);
            n_aug.setVisibility(View.GONE);


            l_sep.setVisibility(View.GONE);
            v_sep.setVisibility(View.GONE);
            n_sep.setVisibility(View.GONE);

            l_oct.setVisibility(View.GONE);
            v_oct.setVisibility(View.GONE);
            n_oct.setVisibility(View.GONE);

            new May().execute();
            new Apr().execute();
            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }



        else if(current_month == 4){

            l_may.setVisibility(View.GONE);
            v_may.setVisibility(View.GONE);
            n_may.setVisibility(View.GONE);

            l_jun.setVisibility(View.GONE);
            v_jun.setVisibility(View.GONE);
            n_jun.setVisibility(View.GONE);


            l_jul.setVisibility(View.GONE);
            v_jul.setVisibility(View.GONE);
            n_jul.setVisibility(View.GONE);

            l_aug.setVisibility(View.GONE);
            v_aug.setVisibility(View.GONE);
            n_aug.setVisibility(View.GONE);

            l_sep.setVisibility(View.GONE);
            v_sep.setVisibility(View.GONE);
            n_sep.setVisibility(View.GONE);

            l_oct.setVisibility(View.GONE);
            v_oct.setVisibility(View.GONE);
            n_oct.setVisibility(View.GONE);


            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);

            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);


            new Apr().execute();
            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }

        else if(current_month == 3){
            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);

            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);

            l_aug.setVisibility(View.GONE);
            v_aug.setVisibility(View.GONE);
            n_aug.setVisibility(View.GONE);


            l_sep.setVisibility(View.GONE);
            v_sep.setVisibility(View.GONE);
            n_sep.setVisibility(View.GONE);

            l_oct.setVisibility(View.GONE);
            v_oct.setVisibility(View.GONE);
            n_oct.setVisibility(View.GONE);

            l_jul.setVisibility(View.GONE);
            v_jul.setVisibility(View.GONE);
            n_jul.setVisibility(View.GONE);
            l_jun.setVisibility(View.GONE);
            v_jun.setVisibility(View.GONE);
            n_jun.setVisibility(View.GONE);


            l_may.setVisibility(View.GONE);
            v_may.setVisibility(View.GONE);
            n_may.setVisibility(View.GONE);

            l_apr.setVisibility(View.GONE);
            v_apr.setVisibility(View.GONE);
            n_apr.setVisibility(View.GONE);

            new Mar().execute();
            new Feb().execute();
            new January().execute();

        }

        else if(current_month == 2){

            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);

            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);

            l_aug.setVisibility(View.GONE);
            v_aug.setVisibility(View.GONE);
            n_aug.setVisibility(View.GONE);


            l_sep.setVisibility(View.GONE);
            v_sep.setVisibility(View.GONE);
            n_sep.setVisibility(View.GONE);

            l_oct.setVisibility(View.GONE);
            v_oct.setVisibility(View.GONE);
            n_oct.setVisibility(View.GONE);


            l_jul.setVisibility(View.GONE);
            v_jul.setVisibility(View.GONE);
            n_jul.setVisibility(View.GONE);

            l_jun.setVisibility(View.GONE);
            v_jun.setVisibility(View.GONE);
            n_jun.setVisibility(View.GONE);


            l_may.setVisibility(View.GONE);
            v_may.setVisibility(View.GONE);
            n_may.setVisibility(View.GONE);

            l_apr.setVisibility(View.GONE);
            v_apr.setVisibility(View.GONE);
            n_apr.setVisibility(View.GONE);

            l_mar.setVisibility(View.GONE);
            v_mar.setVisibility(View.GONE);
            n_mar.setVisibility(View.GONE);

            new Feb().execute();
            new January().execute();

        }


        else if(current_month == 1){
            l_aug.setVisibility(View.GONE);
            v_aug.setVisibility(View.GONE);
            n_aug.setVisibility(View.GONE);

            l_nov.setVisibility(View.GONE);
            v_nov.setVisibility(View.GONE);
            n_nov.setVisibility(View.GONE);

            l_dec.setVisibility(View.GONE);
            v_dec.setVisibility(View.GONE);
            n_dec.setVisibility(View.GONE);

            l_sep.setVisibility(View.GONE);
            v_sep.setVisibility(View.GONE);
            n_sep.setVisibility(View.GONE);

            l_oct.setVisibility(View.GONE);
            v_oct.setVisibility(View.GONE);
            n_oct.setVisibility(View.GONE);

            l_jul.setVisibility(View.GONE);
            v_jul.setVisibility(View.GONE);
            n_jul.setVisibility(View.GONE);

            l_jun.setVisibility(View.GONE);
            v_jun.setVisibility(View.GONE);
            n_jun.setVisibility(View.GONE);

            l_may.setVisibility(View.GONE);
            v_may.setVisibility(View.GONE);
            n_may.setVisibility(View.GONE);

            l_apr.setVisibility(View.GONE);
            v_apr.setVisibility(View.GONE);
            n_apr.setVisibility(View.GONE);

            l_mar.setVisibility(View.GONE);
            v_mar.setVisibility(View.GONE);
            n_mar.setVisibility(View.GONE);

            l_feb.setVisibility(View.GONE);
            v_feb.setVisibility(View.GONE);
            n_feb.setVisibility(View.GONE);





            new January().execute();

        }
        test=userName;



        //  new May().execute();
        //  new Apr().execute();
        // new Mar().execute();
        // new Feb().execute();
        // new January().execute();



        back_btn.setOnClickListener(new OnClickListener() {
            Bundle b = getIntent().getExtras();
            //UserName = b.getString("UserName");
            //UserName_1= b.getString("UserName_1");
            //UserName_2= b.getString("UserName_2");
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {
                            Log.d("New_pass2", "New_pass2");

                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String UserName_1 = b.getString("userName_1");
                            String UserName_2 = b.getString("userName_2");

                            Intent i = new Intent(RmDCCFollowupnew.this,  RmDCCFollowup.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_1", UserName_1);
                            i.putExtra("UserName_2", UserName_2);

                            i.putExtra("userName", userName);
                            i.putExtra("new_version", userName);
                            i.putExtra("userName_1", UserName_1);
                            i.putExtra("userName_2", UserName_2);


                            i.putExtra("asm_flag", asm_flag);
                            i.putExtra("asm_code", asm_code);

                            i.putExtra("gm_flag", gm_flag);
                            i.putExtra("gm_code", gm_code);


                            Log.d("userName","UserName"+userName);

                            Log.d("UserName_1","UserName_1"+UserName_1);
                            Log.d("UserName_2","UserName_2"+UserName_2);




                            startActivity(i);
                            //finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();
                URL css;
                try {
                    css = new URL("http://10.0.2.2:8000/styles.css");
                    NativeCSS.styleWithCSS("style.css", css, Never);
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });



    }



    private void popSpinner() {
        List<String> description = new ArrayList<String>();
        for (int i = 0; i < categoriesList.size(); i++) {
            Log.d("Changepa---ssword",categoriesList.get(i).getId());
            description.add(categoriesList.get(i).getId());
            Log.d("Changep---assword","Login"+categoriesList.get(i).getId());
        }


    }

    private void popSpinner2() {
        List<String> description2 = new ArrayList<String>();
        for (int i = 0; i < categoriesList2.size(); i++) {
            Log.d("categoriesList2",categoriesList2.get(i).getId());

            description2.add(categoriesList2.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList2.get(i).getId());
        }


    }




    private void popSpinner3() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList3.size(); i++) {
            Log.d("categoriesList2",categoriesList3.get(i).getId());

            description3.add(categoriesList3.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList3.get(i).getId());
        }


    }




    private void popSpinner4() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList4.size(); i++) {
            Log.d("categoriesList2",categoriesList4.get(i).getId());

            description3.add(categoriesList4.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList4.get(i).getId());
        }


    }


    private void popSpinner5() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList5.size(); i++) {
            Log.d("categoriesList2",categoriesList5.get(i).getId());

            description3.add(categoriesList5.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList5.get(i).getId());
        }


    }



    private void popSpinner6() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList6.size(); i++) {
            Log.d("categoriesList2",categoriesList6.get(i).getId());

            description3.add(categoriesList6.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList6.get(i).getId());
        }


    }

    private void popSpinner7() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList7.size(); i++) {
            Log.d("categoriesList2",categoriesList7.get(i).getId());

            description3.add(categoriesList7.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList7.get(i).getId());
        }


    }

    private void popSpinner8() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList8.size(); i++) {
            Log.d("categoriesList2",categoriesList8.get(i).getId());

            description3.add(categoriesList8.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList8.get(i).getId());
        }


    }


    private void popSpinner9() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList9.size(); i++) {
            Log.d("categoriesList2",categoriesList9.get(i).getId());

            description3.add(categoriesList9.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList9.get(i).getId());
        }


    }


    private void popSpinner10() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList10.size(); i++) {
            Log.d("categoriesList2",categoriesList10.get(i).getId());

            description3.add(categoriesList10.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList10.get(i).getId());
        }


    }


    private void popSpinner11() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList11.size(); i++) {
            Log.d("categoriesList2",categoriesList11.get(i).getId());

            description3.add(categoriesList11.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList11.get(i).getId());
        }


    }


    private void popSpinner12() {
        List<String> description3 = new ArrayList<String>();
        for (int i = 0; i < categoriesList12.size(); i++) {
            Log.d("categoriesList2",categoriesList12.get(i).getId());

            description3.add(categoriesList12.get(i).getId());

            Log.d("categoriesList2","Login"+categoriesList12.get(i).getId());
        }


    }

    public void finishActivity(View v) {
        finish();
    }

    class Spinner {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList.get(i).getsl());

                sl.add(categoriesList.get(i).getsl());

                lables.add(categoriesList.get(i).getName());
                p_ids.add(categoriesList.get(i).getId());
                quanty.add(categoriesList.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView2.setAdapter(adapter);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }


    class Spinner2 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner2() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList2.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList2.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList2.get(i).getsl());

                sl.add(categoriesList2.get(i).getsl());

                lables.add(categoriesList2.get(i).getName());
                p_ids.add(categoriesList2.get(i).getId());
                quanty.add(categoriesList2.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList2.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList2.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList2.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList2.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList2.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList2.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList2.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList2.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList2.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList2.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter2 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView.setAdapter(adapter2);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }


    class Spinner3 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner3() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList3.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList3.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList3.get(i).getsl());

                sl.add(categoriesList3.get(i).getsl());

                lables.add(categoriesList3.get(i).getName());
                p_ids.add(categoriesList3.get(i).getId());
                quanty.add(categoriesList3.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList3.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList3.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList3.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList3.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList3.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList3.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList3.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList3.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList3.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList3.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter3 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView3.setAdapter(adapter3);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }


    class Spinner4 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner4() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList4.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList4.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList4.get(i).getsl());

                sl.add(categoriesList4.get(i).getsl());

                lables.add(categoriesList4.get(i).getName());
                p_ids.add(categoriesList4.get(i).getId());
                quanty.add(categoriesList4.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList4.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList4.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList4.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList4.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList4.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList4.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList4.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList4.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList4.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList4.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter4 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView4.setAdapter(adapter4);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }

    class Spinner5 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner5() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList5.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList5.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList5.get(i).getsl());

                sl.add(categoriesList5.get(i).getsl());

                lables.add(categoriesList5.get(i).getName());
                p_ids.add(categoriesList5.get(i).getId());
                quanty.add(categoriesList5.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList5.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList5.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList5.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList5.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList5.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList5.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList5.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList5.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList5.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList5.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter5 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView5.setAdapter(adapter5);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }

    class Spinner6 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner6() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList6.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList6.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList6.get(i).getsl());

                sl.add(categoriesList6.get(i).getsl());

                lables.add(categoriesList6.get(i).getName());
                p_ids.add(categoriesList6.get(i).getId());
                quanty.add(categoriesList6.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList6.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList6.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList6.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList6.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList6.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList6.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList6.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList6.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList6.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList6.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter6 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView6.setAdapter(adapter6);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }

    class Spinner7 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner7() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList7.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList7.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList7.get(i).getsl());

                sl.add(categoriesList7.get(i).getsl());

                lables.add(categoriesList7.get(i).getName());
                p_ids.add(categoriesList7.get(i).getId());
                quanty.add(categoriesList7.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList7.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList7.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList7.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList7.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList7.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList7.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList7.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList7.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList7.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList7.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter7 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView7.setAdapter(adapter7);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }




    class Spinner8 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner8() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList8.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList8.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList8.get(i).getsl());

                sl.add(categoriesList8.get(i).getsl());

                lables.add(categoriesList8.get(i).getName());
                p_ids.add(categoriesList8.get(i).getId());
                quanty.add(categoriesList8.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList8.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList8.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList8.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList8.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList8.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList8.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList8.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList8.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList8.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList8.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter8 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView8.setAdapter(adapter8);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }


    class Spinner9 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner9() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList9.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList9.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList9.get(i).getsl());

                sl.add(categoriesList9.get(i).getsl());

                lables.add(categoriesList9.get(i).getName());
                p_ids.add(categoriesList9.get(i).getId());
                quanty.add(categoriesList9.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList9.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList9.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList9.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList9.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList9.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList9.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList9.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList9.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList9.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList9.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter9 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView9.setAdapter(adapter9);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }

    class Spinner10 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner10() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList10.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList10.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList10.get(i).getsl());

                sl.add(categoriesList10.get(i).getsl());

                lables.add(categoriesList10.get(i).getName());
                p_ids.add(categoriesList10.get(i).getId());
                quanty.add(categoriesList10.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList10.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList10.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList10.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList10.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList10.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList10.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList10.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList10.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList10.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList10.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter10 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView10.setAdapter(adapter10);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }



    class Spinner11 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner11() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList11.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList11.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList11.get(i).getsl());

                sl.add(categoriesList11.get(i).getsl());

                lables.add(categoriesList11.get(i).getName());
                p_ids.add(categoriesList11.get(i).getId());
                quanty.add(categoriesList11.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList11.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList11.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList11.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList11.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList11.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList11.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList11.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList11.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList11.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList11.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter11 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView11.setAdapter(adapter11);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }


    class Spinner12 {
        private String TotalQ;
        private String TotalV;

        private void populateSpinner12() {
            ArrayList<String> sl = new ArrayList<String>();
            ArrayList<String> lables = new ArrayList<String>();
            ArrayList<String> quanty = new ArrayList<String>();
            ArrayList<String> value = new ArrayList<String>();
            ArrayList<String> value4 = new ArrayList<String>();
            ArrayList<String> value5 = new ArrayList<String>();
            ArrayList<String> value6 = new ArrayList<String>();
            ArrayList<String> value7 = new ArrayList<String>();


            ArrayList<String> value8 = new ArrayList<String>();
            ArrayList<String> value9 = new ArrayList<String>();
            ArrayList<String> value10 = new ArrayList<String>();
            ArrayList<String> value11 = new ArrayList<String>();
            ArrayList<String> value12 = new ArrayList<String>();
            int quantity = 0;
            float prod_rate, prod_vat, sellvalue;
            String prod_rate_1, prod_vat_1,prod_vat_2,prod_vat_3,prod_vat_4, prod_vat_5,prod_vat_6,prod_vat_7,prod_vat_8,prod_vat_9,sellvalue_2,sellvalue_3;



            for (int i = 0; i < categoriesList12.size(); i++) {
                Log.i("OPSONIN", " P_ID " + categoriesList12.get(i).getId());
                Log.i("OPSONIN--", " P_ID " + categoriesList12.get(i).getsl());

                sl.add(categoriesList12.get(i).getsl());

                lables.add(categoriesList12.get(i).getName());
                p_ids.add(categoriesList12.get(i).getId());
                quanty.add(categoriesList12.get(i).getQuantity());

                //quantity = categoriesList.get(i).getQuantity();
                prod_rate_1 = categoriesList12.get(i).getPROD_RATE();
                sellvalue_2 = prod_rate_1;
                value.add(sellvalue_2);

                prod_vat_1= categoriesList12.get(i).getPROD_VAT();
                value4.add(prod_vat_1);


                prod_vat_2= categoriesList12.get(i).getPROD_VAT_2();
                value5.add(prod_vat_2);


                prod_vat_3= categoriesList12.get(i).getPROD_VAT_3();
                value6.add(prod_vat_3);

                prod_vat_4= categoriesList12.get(i).getPROD_VAT_4();
                value7.add(prod_vat_4);





                prod_vat_5= categoriesList12.get(i).getPROD_VAT_5();
                value8.add(prod_vat_5);


                prod_vat_6= categoriesList12.get(i).getPROD_VAT_6();
                value9.add(prod_vat_6);


                prod_vat_7= categoriesList12.get(i).getPROD_VAT_7();
                value10.add(prod_vat_7);

                prod_vat_8= categoriesList12.get(i).getPROD_VAT_8();
                value11.add(prod_vat_8);

                prod_vat_9= categoriesList12.get(i).getPROD_VAT_9();
                value12.add(prod_vat_9);

            }


            DccCampAdapter adapter12 = new DccCampAdapter(RmDCCFollowupnew.this,sl,lables, quanty, value,value4,value5,value6,value7,value8,value9,value10,value11,value12);
            productListView12.setAdapter(adapter12);




        }

        private float round(float x, int i) {
            // TODO Auto-generated method stub
            return 0;
        }
        public String getTotalQ() {
            return TotalQ;
        }
        public String getTotalV() {
            return TotalV;
        }
    }








    private class January extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            pDialog.setTitle("Data Loading !");
            pDialog.setMessage("Loading Please Wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id =userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_DETAIL_VIEW,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList2.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            Spinner2 sp = new Spinner2();

            sp.populateSpinner2();
            popSpinner2();


        }
    }


    private class Feb extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            //  pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            //  pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_FEB,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //  pDialog.dismiss();

            Spinner sp = new Spinner();

            sp.populateSpinner();
            popSpinner();


        }
    }


    private class Mar extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            //  pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            //  pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_MAR,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList3.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //  pDialog.dismiss();

            Spinner3 sp3 = new Spinner3();

            sp3.populateSpinner3();
            popSpinner3();


        }
    }


    private class Apr extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            //  pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            //  pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_APR,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList4.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //  pDialog.dismiss();

            Spinner4 sp4 = new Spinner4();

            sp4.populateSpinner4();
            popSpinner4();


        }
    }

    private class May extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            //  pDialog.setTitle("Data Loading !");
            //   pDialog.setMessage("Loading Please Wait..");
            //   pDialog.setCancelable(false);
            //  pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_MAY,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList5.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //  pDialog.dismiss();

            Spinner5 sp5 = new Spinner5();

            sp5.populateSpinner5();
            popSpinner5();


        }
    }

    private class Jun extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            // pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            // pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_JUN,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList6.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //   pDialog.dismiss();

            Spinner6 sp6 = new Spinner6();

            sp6.populateSpinner6();
            popSpinner6();


        }
    }


    private class Jul extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            // pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            // pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_JUL,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList7.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //   pDialog.dismiss();

            Spinner7 sp7 = new Spinner7();

            sp7.populateSpinner7();
            popSpinner7();


        }
    }


    private class Aug extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            // pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            // pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_AUG,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList8.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //   pDialog.dismiss();

            Spinner8 sp8 = new Spinner8();

            sp8.populateSpinner8();
            popSpinner8();


        }
    }




    private class Sep extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            // pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            // pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_SEP,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList9.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //   pDialog.dismiss();

            Spinner9 sp9 = new Spinner9();

            sp9.populateSpinner9();
            popSpinner9();


        }
    }




    private class Oct extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            // pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            // pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_OCT,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList10.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //   pDialog.dismiss();

            Spinner10 sp10 = new Spinner10();

            sp10.populateSpinner10();
            popSpinner10();


        }
    }




    private class Nov extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            // pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            // pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_NOV,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList11.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //   pDialog.dismiss();

            Spinner11 sp11 = new Spinner11();

            sp11.populateSpinner11();
            popSpinner11();


        }
    }




    private class Dec extends AsyncTask<Void, Void, Void> {


        Bundle b = getIntent().getExtras();
        String userName = b.getString("UserName");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // pDialog = new ProgressDialog(RmDCCFollowupnew.this);
            // pDialog.setTitle("Data Loading !");
            // pDialog.setMessage("Loading Please Wait..");
            // pDialog.setCancelable(false);
            // pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.e("Response: ", ">  ");


            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;//userName;


            Log.e(" id ", ">  test ==  "+userName);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_DEC,ServiceHandler.POST, params);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category6 cat6 = new Category6(
                                    catObj.getString("sl"),
                                    catObj.getString("id"),
                                    catObj.getString("name"),
                                    catObj.getString("quantity"),
                                    catObj.getString("PROD_RATE"),
                                    catObj.getString("PROD_VAT"),
                                    catObj.getString("PROD_VAT_2"),
                                    catObj.getString("PROD_VAT_3"),
                                    catObj.getString("PROD_VAT_4"),
                                    catObj.getString("PROD_VAT_5"),
                                    catObj.getString("PROD_VAT_6"),
                                    catObj.getString("PROD_VAT_7"),
                                    catObj.getString("PROD_VAT_8"),
                                    catObj.getString("PROD_VAT_9"),
                                    catObj.getString("PROD_VAT_10"),
                                    catObj.getString("PROD_VAT_11")

                            );
                            categoriesList12.add(cat6);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
                Toast.makeText(RmDCCFollowupnew.this, "Nothing To Disply",Toast.LENGTH_SHORT).show();
                Toast.makeText(RmDCCFollowupnew.this, "Please make a order first !",Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (pDialog.isShowing())
            //   pDialog.dismiss();

            Spinner12 sp12 = new Spinner12();

            sp12.populateSpinner12();
            popSpinner12();


        }
    }


































    /*------------- list items on click event----------------*/
    @Override
    public void onClick(View v) {
    }

    protected void onPostExecute() {
    }


    private void view() {
        Intent i = new Intent(RmDCCFollowupnew.this, Report.class);
        startActivity(i);
        finish();

    }

}


