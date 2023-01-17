 //PCPendingProposal



 package com.opl.pharmavector;

//PCPendingProposal
 import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

 import android.app.Activity;
 import android.app.DatePickerDialog;
 import android.app.ProgressDialog;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.graphics.Color;
 import android.graphics.Typeface;
 import android.os.AsyncTask;
 import android.os.Bundle;

 import androidx.appcompat.app.AlertDialog;
 import androidx.appcompat.app.AppCompatActivity;


 import android.text.Editable;
 import android.text.InputFilter;
 import android.text.InputType;
 import android.text.Spanned;
 import android.text.TextUtils;
 import android.text.TextWatcher;
 import android.util.Log;
 import android.view.KeyEvent;
 import android.view.MotionEvent;
 import android.view.View;
 import android.view.WindowManager;
 import android.view.inputmethod.InputMethodManager;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.AutoCompleteTextView;
 import android.widget.Button;
 import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;
 import android.widget.Toolbar;


 import com.google.android.material.textfield.TextInputLayout;
 import com.opl.pharmavector.pcconference.PcProposalDoc;
 import com.opl.pharmavector.R;

 import org.apache.http.NameValuePair;
 import org.apache.http.message.BasicNameValuePair;
 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;

 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.List;
 import java.util.Locale;


 public class PCPendingProposal extends Activity implements AdapterView.OnItemSelectedListener {

     private Toolbar toolbar;
     private EditText mpo_name, territory_name, mpo_code,venue_charge,food,miscell,miscell_bdt,impact,fd_bdt;
     private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
     private TextView user_show,p_month_disp;
     private Button btnSignUp;
     private EditText pc_rmp,doc_chemist,in_house;
     private ArrayList<Customer> customerlist;
     private ArrayList<Customer> brandlist;
     private ArrayList<Customer> dateextendlist;


     private ArrayList<Customer> checkdatelist;
     private ArrayList<Customer> checkflag;
     private ArrayList<Customer> checkpcdate;

     private EditText market_name,venue_name,total_participent;
     TextView ded;
     public int pc_rmp_val=0;
     public int result_participant=0;
     public int doc_val=0;
     public int inhouse_val=0;
     public int food_val=0;
     public MultiSelectionSpinner2 spinner;
     private Spinner cust;
     //private MaterialSpinner cust;
     public String proposed_conference_date;
     ProgressDialog pDialog;
     public String market_code,m_name,food_allowance,v_charge,miscell_bdt_val,miscell_val,food_val_venu;


     public String userName,UserName_2;
     public static final String TAG_SUCCESS = "success";
     public static final String TAG_MESSAGE = "message";
     public static final String TAG_MESSAGE_1 = "message_1";
     public static final String TAG_invoice = "invoice";
     public static final String TAG_target = "target";
     public static final String TAG_achivement = "achivement";
     public static final String TAG_MESSAGE_new_version = "new_version";
     public int success;
     public String message, ord_no,invoice,target_data,achivement,searchString,select_party;

     public String get_ext_dt,date_flag,check_flag,check_pc_date;

     public String budget_limit,person_limit,total_conference,setup_venue_charge;
     private String URL_CUSOTMER = BASE_URL+"mkt_restructure_vw.php";
     private String URL_PC_PRODUCT = BASE_URL+"pc_productlist.php";

     private String mpo_pending_pc_date_fetch = BASE_URL+"mpo_pending_pc_date_fetch.php";


     private String pc_conference_setup_date = BASE_URL+"pc_conference_setup_date.php";
     private String pc_conference_date_check = BASE_URL+"pc_conference_date_check.php";
     private String pc_conference_check_flag = BASE_URL+"pc_conference_check_flag.php";




     private RadioGroup radioSexGroup;
     private RadioButton radioSexButton;
     public  String conf_type_val;


     @Override
     protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         setContentView(R.layout.pc_conference_proposal);
         inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
         inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
         final TextView error_dt=(TextView)findViewById(R.id.errordt);
         user_show = (TextView) findViewById(R.id.user_show);
         btnSignUp = (Button) findViewById(R.id.submit);
         final Button  back=(Button) findViewById(R.id.back);
         final Button  logback=(Button) findViewById(R.id.logback);

         ded = (TextView) findViewById(R.id.conference_date);
         venue_charge = (EditText) findViewById(R.id.venue_charge);
         food = (EditText) findViewById(R.id.food);

         p_month_disp = (TextView) findViewById(R.id.p_month_disp);
         p_month_disp.setVisibility(View.VISIBLE);

         fd_bdt = (EditText) findViewById(R.id.fd_bdt);
         miscell = (EditText) findViewById(R.id.miscell);


         miscell.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);


         miscell.setFilters(new InputFilter[] {
                 new InputFilter() {
                     @Override
                     public CharSequence filter(CharSequence cs, int start,
                                                int end, Spanned spanned, int dStart, int dEnd) {
                         // TODO Auto-generated method stub
                         if(cs.equals("")){ // for backspace
                             return cs;
                         }
                         if(cs.toString().matches("[a-zA-Z ]+")){
                             return cs;
                         }
                         return "";
                     }
                 }
         });









         miscell_bdt = (EditText) findViewById(R.id.miscell_bdt);


         impact = (EditText) findViewById(R.id.impact);


         impact.setInputType(InputType.TYPE_CLASS_TEXT |
                 InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                 InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);



         pc_rmp = (EditText) findViewById(R.id.pc_rmp);
         doc_chemist = (EditText) findViewById(R.id.doc_chemist);
         in_house = (EditText) findViewById(R.id.in_house);
         venue_name = (EditText) findViewById(R.id.venue_name);



         venue_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);



         market_name = (EditText) findViewById(R.id.market_name);
         market_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);


         spinner = (MultiSelectionSpinner2)findViewById(R.id.input1);
         spinner.setVisibility(View.GONE);
         total_participent = (EditText) findViewById(R.id.total_participent);
         cust = (Spinner) findViewById(R.id.customer);
         customerlist = new ArrayList<Customer>();
         brandlist = new ArrayList<Customer>();
         dateextendlist = new ArrayList<Customer>();
         checkdatelist = new ArrayList<Customer>();
         checkflag = new ArrayList<Customer>();
         checkpcdate = new ArrayList<Customer>();


         Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
         cust.setOnItemSelectedListener(this);
         conf_type_val="P";




         radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);

         radioSexGroup.setVisibility(View.GONE);







         LinearLayout mainlayout = (LinearLayout)findViewById(R.id.successmsg);
         TextView succ_msg=(TextView) findViewById(R.id.succ_msg);
         TextView ordno=(TextView) findViewById(R.id.ordno);
         /*================================================================get customer========================================*/



         new GetMarket().execute();
         new GeTPcConferenceSetupData().execute();
         new GetMpoPendingPcDate().execute();





         ded.setFocusableInTouchMode(true);
         ded.setFocusable(true);
         ded.requestFocus();
         v_charge="0";
         miscell_bdt_val="0";
         miscell_val="0";
         final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
         final Bundle[] b = {getIntent().getExtras()};
         userName = b[0].getString("UserName");
         UserName_2 = b[0].getString("UserName_2");


         final String new_version = b[0].getString("new_version");
         final String ordernumber = b[0].getString("Ord_NO");
         user_show.setText(userName+ " "+ UserName_2 +" "  );
         back.setTypeface(fontFamily);
         back.setText("\uf060 ");
         logback.setTypeface(fontFamily);
         logback.setText("\uf08b");

         btnSignUp.setTypeface(fontFamily);
         btnSignUp.setText("\uf061");








         if(ordernumber==null){
             mainlayout.setVisibility(LinearLayout.GONE);
         }
         else{

             succ_msg.setTextSize(12);
             succ_msg.setText("Submitted.");
             succ_msg.setTextColor(Color.BLACK);
             ordno.setText("PC Serial no."+ordernumber);
             ordno.setTextSize(12);
             ordno.setTextColor(Color.BLACK);
         }




         logback.setOnClickListener(new View.OnClickListener() {
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





                             Intent i = new Intent(PCPendingProposal.this, PCDashboard.class);
                             i.putExtra("UserName", userName);
                             i.putExtra("new_version", userName);
                             i.putExtra("UserName_1", UserName_2);
                             i.putExtra("UserName_2", UserName_2);

                             i.putExtra("userName", userName);
                             i.putExtra("new_version", userName);
                             i.putExtra("userName_1", UserName_2);
                             i.putExtra("userName_2", UserName_2);
                             i.putExtra("user_flag", "M");
                             startActivity(i);
                             //finish();
                         } catch (Exception e) {
                             e.printStackTrace();
                         }

                     }
                 });

                 backthred.start();

             }
         });





         back.setOnClickListener(new View.OnClickListener() {
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

                             Intent i = new Intent(PCPendingProposal.this, PCDashboard.class);
                             i.putExtra("UserName", userName);
                             i.putExtra("new_version", userName);
                             i.putExtra("UserName_1", UserName_2);
                             i.putExtra("UserName_2", UserName_2);

                             i.putExtra("userName", userName);
                             i.putExtra("new_version", userName);
                             i.putExtra("userName_1", UserName_2);
                             i.putExtra("userName_2", UserName_2);
                             i.putExtra("user_flag", "M");
                             startActivity(i);
                             //finish();
                         } catch (Exception e) {
                             e.printStackTrace();
                         }

                     }
                 });

                 backthred.start();

             }
         });



         final Calendar myCalendar = Calendar.getInstance();
         final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
             @Override
             public void onDateSet(DatePicker view, int year, int monthOfYear,
                                   int dayOfMonth) {
                 // TODO Auto-generated method stub
                 myCalendar.set(Calendar.YEAR, year);
                 myCalendar.set(Calendar.MONTH, monthOfYear);
                 myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                 updateLabel();
             }

             private void updateLabel() {
                 String myFormat = "dd/MM/yyyy";
                 SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                 ded.setText(sdf.format(myCalendar.getTime()));
                 proposed_conference_date= ded.getText().toString().trim();


                 int selectedId = radioSexGroup.getCheckedRadioButtonId();
                 radioSexButton = (RadioButton) findViewById(selectedId);
                 // Toast.makeText(PCPendingProposal.this, radioSexButton.getText(), Toast.LENGTH_SHORT).show();



                 conf_type_val ="P";






                 new GeTPcConferenceDate().execute();
                 new GeTPcConferenceFlag().execute();




             }

         };

         ded.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {


                 new DatePickerDialog(PCPendingProposal.this, date, myCalendar
                         .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                         myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                 hideKeyboard(v);




             }
         });




         /*====================================== PC/RMP  participant change  ========================================*/

         pc_rmp.addTextChangedListener(new TextWatcher() {

             public void onTextChanged(CharSequence s, int start, int before,
                                       int count) {


                 if (pc_rmp.getText().toString().trim().equals("")){

                     if (doc_chemist.getText().toString().trim().equals("")){
                         doc_val=0;
                     }
                     else{
                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }

                     if (in_house.getText().toString().trim().equals("")){
                         inhouse_val=0;

                     }
                     else{

                         inhouse_val=Integer.parseInt(in_house.getText().toString().trim());

                     }

                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }

                     pc_rmp_val = 0;
                     result_participant = pc_rmp_val + doc_val+inhouse_val;
                     String total2 = Double.toString(result_participant);
                     total_participent.setText(String.valueOf(result_participant));





                     int food_val_temp= result_participant*food_val;
                     food_allowance=String.valueOf(food_val_temp);
                     fd_bdt.setText(String.valueOf(food_val_temp));


                 }

                 else {

                     if (doc_chemist.getText().toString().trim().equals("")){

                         doc_val=0;
                     }
                     else{
                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }


                     if (in_house.getText().toString().trim().equals("")){
                         inhouse_val=0;

                     }
                     else{

                         inhouse_val=Integer.parseInt(in_house.getText().toString().trim());

                     }

                     pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());
                     double result = pc_rmp_val +doc_val+inhouse_val;
                     String total2 = Double.toString(result);
                     total_participent.setText(total2);
                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }
                     int food_val_temp= result_participant*food_val;
                     fd_bdt.setText(String.valueOf(food_val_temp));

                     food_allowance=String.valueOf(food_val_temp);





                 }



             }


             public void beforeTextChanged(CharSequence s, int start, int count,
                                           int after) {

             }

             public void afterTextChanged(Editable s) {


                 if (pc_rmp.getText().toString().trim().equals("")){
                     if (doc_chemist.getText().toString().trim().equals("")){

                         doc_val=0;
                     }
                     else{


                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }


                     if (in_house.getText().toString().trim().equals("")){
                         inhouse_val=0;

                     }
                     else{

                         inhouse_val=Integer.parseInt(in_house.getText().toString().trim());

                     }



                     pc_rmp_val = 0;

                     result_participant = pc_rmp_val + doc_val+inhouse_val;
                     String total2 = Double.toString(result_participant);


                     total_participent.setText(String.valueOf(result_participant));


                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }
                     int food_val_temp= result_participant*food_val;
                     Log.e("temp_food_val", String.valueOf(food_val_temp));

                     fd_bdt.setText(String.valueOf(food_val_temp));
                     food_allowance=String.valueOf(food_val_temp);

                 }

                 else {

                     if (doc_chemist.getText().toString().trim().equals("")){

                         doc_val=0;
                     }
                     else{
                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }


                     if (in_house.getText().toString().trim().equals("")){
                         inhouse_val=0;

                     }
                     else{

                         inhouse_val=Integer.parseInt(in_house.getText().toString().trim());

                     }


                     pc_rmp_val = Integer.parseInt(pc_rmp.getText().toString().trim());
                     int result = pc_rmp_val +doc_val+inhouse_val;
                     String total2 = Integer.toString(result);

                     total_participent.setText(total2);


                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }
                     int food_val_temp= result*food_val;



                     fd_bdt.setText(String.valueOf(food_val_temp));

                     food_allowance=String.valueOf(food_val_temp);


                 }


             }
         });

         /*======================================  end  PC/RMP  participant change  ========================================*/


         /*====================================== doctor cum chemist participant change  ========================================*/


         doc_chemist.addTextChangedListener(new TextWatcher() {

             public void onTextChanged(CharSequence s, int start, int before,
                                       int count) {


                 if (doc_chemist.getText().toString().trim().equals("")){

                     if (pc_rmp.getText().toString().trim().equals("")){

                         pc_rmp_val=0;
                     }
                     else{
                         pc_rmp_val=Integer.parseInt(pc_rmp.getText().toString().trim());

                     }

                     if (in_house.getText().toString().trim().equals("")){
                         inhouse_val=0;

                     }
                     else{

                         inhouse_val=Integer.parseInt(in_house.getText().toString().trim());

                     }




                     doc_val = 0;
                     int result = pc_rmp_val +doc_val+inhouse_val;
                     String total2 = Integer.toString(result);
                     total_participent.setText(total2);


                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }


                     int food_val_temp= result*food_val;
                     food_allowance=String.valueOf(food_val_temp);
                     fd_bdt.setText(String.valueOf(food_val_temp));


                 }

                 else {

                     if (doc_chemist.getText().toString().trim().equals("")){

                         doc_val=0;
                     }
                     else{
                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }



                     if (in_house.getText().toString().trim().equals("")){
                         inhouse_val=0;

                     }
                     else{

                         inhouse_val=Integer.parseInt(in_house.getText().toString().trim());

                     }


                     if (pc_rmp.getText().toString().trim().equals("")){
                         pc_rmp_val=0;

                     }
                     else{

                         pc_rmp_val=Integer.parseInt(pc_rmp.getText().toString().trim());

                     }



                     int result = pc_rmp_val +doc_val+inhouse_val;
                     String total2 = Integer.toString(result);
                     total_participent.setText(total2);



                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }


                     int food_val_temp= result*food_val;
                     food_allowance=String.valueOf(food_val_temp);
                     fd_bdt.setText(String.valueOf(food_val_temp));





                 }



             }



             public void beforeTextChanged(CharSequence s, int start, int count,
                                           int after) {

             }

             public void afterTextChanged(Editable s) {


                 if (doc_chemist.getText().toString().trim().equals("")){



                     if (pc_rmp.getText().toString().trim().equals("")){

                         pc_rmp_val=0;
                     }
                     else{
                         pc_rmp_val=Integer.parseInt(pc_rmp.getText().toString().trim());

                     }

                     if (in_house.getText().toString().trim().equals("")){
                         inhouse_val=0;

                     }
                     else{

                         inhouse_val=Integer.parseInt(in_house.getText().toString().trim());

                     }




                     doc_val = 0;
                     int result = pc_rmp_val +doc_val+inhouse_val;
                     String total2 = Integer.toString(result);
                     total_participent.setText(total2);

                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }

                     int food_val_temp= result*food_val;
                     food_allowance=String.valueOf(food_val_temp);
                     fd_bdt.setText(String.valueOf(food_val_temp));





                 }





                 else {

                     if (doc_chemist.getText().toString().trim().equals("")){

                         doc_val=0;
                     }
                     else{
                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }



                     if (in_house.getText().toString().trim().equals("")){
                         inhouse_val=0;

                     }
                     else{

                         inhouse_val=Integer.parseInt(in_house.getText().toString().trim());

                     }

                     if (pc_rmp.getText().toString().trim().equals("")){
                         pc_rmp_val=0;

                     }
                     else{

                         pc_rmp_val=Integer.parseInt(pc_rmp.getText().toString().trim());

                     }

                     int result = pc_rmp_val +doc_val+inhouse_val;
                     String total2 = Integer.toString(result);
                     total_participent.setText(total2);

                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }


                     int food_val_temp= result*food_val;
                     food_allowance=String.valueOf(food_val_temp);
                     fd_bdt.setText(String.valueOf(food_val_temp));




                 }








             }
         });

         /*====================================== end  doctor cum chemist participant change  ========================================*/



         /*====================================== start  in house participant change  =========================================*/

         in_house.addTextChangedListener(new TextWatcher() {

             public void onTextChanged(CharSequence s, int start, int before,
                                       int count) {


                 if (in_house.getText().toString().trim().equals("")){



                     if (pc_rmp.getText().toString().trim().equals("")){

                         pc_rmp_val=0;
                     }
                     else{
                         pc_rmp_val=Integer.parseInt(pc_rmp.getText().toString().trim());

                     }

                     if (doc_chemist.getText().toString().trim().equals("")){
                         doc_val=0;

                     }
                     else{

                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }



                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }


                     inhouse_val = 0;
                     int result = pc_rmp_val +doc_val+inhouse_val;


                     String total2 = Integer.toString(result);
                     total_participent.setText(total2);


                     int food_val_temp= result*food_val;
                     food_allowance=String.valueOf(food_val_temp);
                     fd_bdt.setText(String.valueOf(food_val_temp));




                 }

                 else {

                     if (doc_chemist.getText().toString().trim().equals("")){

                         doc_val=0;
                     }
                     else{
                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }

                     if (pc_rmp.getText().toString().trim().equals("")){

                         pc_rmp_val=0;
                     }
                     else{
                         pc_rmp_val=Integer.parseInt(pc_rmp.getText().toString().trim());

                     }

                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }

                     inhouse_val = Integer.parseInt(in_house.getText().toString().trim());
                     int result = pc_rmp_val +doc_val+inhouse_val;
                     String total2 = Integer.toString(result);
                     total_participent.setText(total2);


                     int food_val_temp= result*food_val;
                     food_allowance=String.valueOf(food_val_temp);
                     fd_bdt.setText(String.valueOf(food_val_temp));



                 }



             }








             public void beforeTextChanged(CharSequence s, int start, int count,
                                           int after) {

             }

             public void afterTextChanged(Editable s) {


                 if (in_house.getText().toString().trim().equals("")){


                     if (pc_rmp.getText().toString().trim().equals("")){
                         pc_rmp_val=0;
                     }
                     else{
                         pc_rmp_val=Integer.parseInt(pc_rmp.getText().toString().trim());

                     }

                     if (doc_chemist.getText().toString().trim().equals("")){
                         doc_val=0;

                     }
                     else{

                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }





                     inhouse_val = 0;
                     int result = pc_rmp_val +doc_val+inhouse_val;
                     String total2 = Integer.toString(result);
                     total_participent.setText(total2);

                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }
                     int food_val_temp= result*food_val;
                     food_allowance=String.valueOf(food_val_temp);
                     fd_bdt.setText(String.valueOf(food_val_temp));

                 }

                 else {

                     if (doc_chemist.getText().toString().trim().equals("")){

                         doc_val=0;
                     }
                     else{
                         doc_val=Integer.parseInt(doc_chemist.getText().toString().trim());

                     }

                     if (pc_rmp.getText().toString().trim().equals("")){

                         pc_rmp_val=0;
                     }
                     else{
                         pc_rmp_val=Integer.parseInt(pc_rmp.getText().toString().trim());

                     }





                     inhouse_val = Integer.parseInt(in_house.getText().toString().trim());
                     int result = pc_rmp_val +doc_val+inhouse_val;
                     String total2 = Integer.toString(result);
                     total_participent.setText(total2);


                     if (food.getText().toString().trim().equals("")){
                         food_val=0;

                     }
                     else{

                         food_val=Integer.parseInt(food.getText().toString().trim());

                     }
                     int food_val_temp= result*food_val;
                     food_allowance=String.valueOf(food_val_temp);
                     fd_bdt.setText(String.valueOf(food_val_temp));




                 }


             }
         });


         /*====================================== end in house participant change  =========================================*/

         /*====================================== change in food value =========================================*/





         food.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if (total_participent.getText().toString().trim().equals("") || total_participent.getText().toString().trim().equals(0)){

                     new AlertDialog.Builder(PCPendingProposal.this)
                             .setTitle("Total Person not Selected")
                             .setMessage("Please enter  Participants for PC Conference ")
                             .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                             {
                                 public void onClick(DialogInterface dialog, int which) {
                                     pc_rmp.setFocusableInTouchMode(true);
                                     pc_rmp.setFocusable(true);
                                     pc_rmp.requestFocus();
                                 }
                             })
                             .setIcon(android.R.drawable.ic_dialog_alert)
                             .setCancelable(true)
                             .show();




                 }

             }
         });

         food.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
                 if (hasFocus) {


                     if (total_participent.getText().toString().trim().equals("") || total_participent.getText().toString().trim().equals(0)){

                         new AlertDialog.Builder(PCPendingProposal.this)
                                 .setTitle("Total Person not Selected")
                                 .setMessage("Please enter total Participants for PC Conference ")
                                 .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                                 {
                                     public void onClick(DialogInterface dialog, int which) {
                                         pc_rmp.setFocusableInTouchMode(true);
                                         pc_rmp.setFocusable(true);
                                         pc_rmp.requestFocus();
                                     }
                                 })
                                 .setIcon(android.R.drawable.ic_dialog_alert)
                                 .setCancelable(true)
                                 .show();




                     }



                 }
             }
         });

         food.addTextChangedListener(new TextWatcher() {

             public void onTextChanged(CharSequence s, int start, int before,
                                       int count) {

                 if (total_participent.getText().toString().trim().equals("") || total_participent.getText().toString().trim().equals(0)){

                     new AlertDialog.Builder(PCPendingProposal.this)
                             .setTitle("Total Person not Selected")
                             .setMessage("Please enter total Participants for PC Conference ")
                             .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                             {
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.cancel();
                                 }
                             })
                             .setIcon(android.R.drawable.ic_dialog_alert)
                             .setCancelable(true)
                             .show();


                 }



                 else if (food.getText().toString().trim().equals("") || food.getText().toString().trim().equals(" ") ){

                     food.setText("0");
                     food.setSelection(food.getText().length());
                     fd_bdt.setText("0");


                 }


                 else {

                     int a = Integer.parseInt(total_participent.getText().toString().trim());
                     int b = Integer.parseInt(food.getText().toString().trim());

                     food_allowance = String.valueOf(a * b);
                     fd_bdt.setText(food_allowance);

                 }


             }

             public void beforeTextChanged(CharSequence s, int start, int count,
                                           int after) {

             }

             public void afterTextChanged(Editable s) {

             }
         });

         /*====================================== change in food value =========================================*/




         /*====================================== change in food value =========================================*/

         venue_charge.addTextChangedListener(new TextWatcher() {

             public void onTextChanged(CharSequence s, int start, int before,
                                       int count) {

                 if (food.getText().toString().trim().equals("") || food.getText().toString().trim().equals(0)){

                     new AlertDialog.Builder(PCPendingProposal.this)
                             .setTitle("Food Budget not given")
                             .setMessage("Please enter Food Budget per person for PC Conference ")
                             .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                             {
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.cancel();
                                 }
                             })
                             .setIcon(android.R.drawable.ic_dialog_alert)
                             .setCancelable(true)
                             .show();

                     food.setText("0");
                     food.setSelection(food.getText().length());
                     fd_bdt.setText("0");


                 }


                 else {

                     if (venue_charge.getText().toString().trim().equals("") || venue_charge.getText().toString().trim().equals(0)) {

                         int a=0;
                         int  total_budget_venue = a +Integer.parseInt(food_allowance);
                         food_val_venu= String.valueOf(total_budget_venue);
                         fd_bdt.setText(food_val_venu);
                     }

                     else{

                         int a= Integer.parseInt(venue_charge.getText().toString());
                         int  total_budget_venue = a +Integer.parseInt(food_allowance);
                         food_val_venu= String.valueOf(total_budget_venue);
                         fd_bdt.setText(food_val_venu);


                     }







                 }


             }

             public void beforeTextChanged(CharSequence s, int start, int count,
                                           int after) {

             }

             public void afterTextChanged(Editable s) {

             }
         });






         /*====================================== change in food value =========================================*/
         miscell_bdt.addTextChangedListener(new TextWatcher() {

             public void onTextChanged(CharSequence s, int start, int before,
                                       int count) {

                 if (food.getText().toString().trim().equals("") || food.getText().toString().trim().equals(0)){

                     new AlertDialog.Builder(PCPendingProposal.this)
                             .setTitle("Food Budget not given")
                             .setMessage("Please enter Food Budget per person for PC Conference ")
                             .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                             {
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.cancel();
                                 }
                             })
                             .setIcon(android.R.drawable.ic_dialog_alert)
                             .setCancelable(true)
                             .show();

                     food.setText("0");
                     food.setSelection(food.getText().length());
                     fd_bdt.setText("0");


                 }

                 else  if (venue_charge.getText().toString().trim().equals("") || venue_charge.getText().toString().trim().equals(0)){

                     new AlertDialog.Builder(PCPendingProposal.this)
                             .setTitle("Venue Charge not given")
                             .setMessage("Please enter Venue Charge per person for PC Conference ")
                             .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                             {
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.cancel();
                                 }
                             })
                             .setIcon(android.R.drawable.ic_dialog_alert)
                             .setCancelable(true)
                             .show();

                     venue_charge.setText("0");
                     venue_charge.setSelection(food.getText().length());
                     venue_charge.setText("0");


                 }



                 else {

                     if (miscell_bdt.getText().toString().trim().equals("") || miscell_bdt.getText().toString().trim().equals(0)) {

                         int a=0;
                         int  total_budget_venue = a +Integer.parseInt(food_val_venu);
                         String food_val_tot= String.valueOf(total_budget_venue);
                         fd_bdt.setText(food_val_tot);
                     }

                     else{

                         int a= Integer.parseInt(miscell_bdt.getText().toString());
                         int  total_budget_venue = a +Integer.parseInt(food_val_venu);
                         String food_val_tot= String.valueOf(total_budget_venue);
                         fd_bdt.setText(food_val_tot);
                     }




                 }


             }

             public void beforeTextChanged(CharSequence s, int start, int count,
                                           int after) {

             }

             public void afterTextChanged(Editable s) {

             }
         });




         /*====================================== change in food value =========================================*/



















         /*====================================== distance in KM=========================================*/


         cust.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

             public void onItemSelected(AdapterView<?> parent, View view,
                                        int pos, long id) {
                 // An spinnerItem was selected. You can retrieve the selected item using
                 // parent.getItemAtPosition(pos)

                 //   Toast.makeText(PCPendingProposal.this, "Hello Toast",Toast.LENGTH_SHORT).show();

                 //  Toast.makeText(parent.getContext(),
                 //      "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                 //      Toast.LENGTH_SHORT).show();



                 String text = cust.getSelectedItem().toString();

                 String[] second_split = text.split("-");
                 m_name = second_split[0];
                 market_code = second_split[1];


                 actv.setText(m_name);

                 // Toast.makeText(parent.getContext(), "spinnerselected " , Toast.LENGTH_SHORT).show();



             }

             public void onNothingSelected(AdapterView<?> parent) {
                 // Do nothing, just another required interface callback
             }

         });



         /*======================================================================= next button click============================================================================*/

         btnSignUp.setOnClickListener(new View.OnClickListener() {

             public void onClick(View v) {
                 Calendar c = Calendar.getInstance();
                 int cYear = c.get(Calendar.YEAR);
                 int cMonth = c.get(Calendar.MONTH)+1;
                 int cDay = c.get(Calendar.DAY_OF_MONTH);
                 int gyear=myCalendar.get(Calendar.YEAR);
                 int gmonth=myCalendar.get(Calendar.MONTH)+1;
                 if(gyear>cYear){
                     gmonth=myCalendar.get(Calendar.MONTH)+13;

                 }

                 int gday =   myCalendar.get(Calendar.DAY_OF_MONTH);
                 int gmonth_day=gmonth*30;
                 int totalday_given=gmonth_day+gday;
                 int cmonth_day=cMonth*30;
                 int totalday_valid1=cmonth_day+cDay;
                 int totalday_valid=totalday_valid1+90;

                 error_dt.setText(" ");

                 Bundle b = getIntent().getExtras();
                 userName = b.getString("UserName");



                 if ((ded.getText().toString().trim().equals(""))|| (ded.getText().toString().trim().equals("Delivery Date"))|| (ded.getText().toString().trim().equals("Please Select date"))){
                     ded.setTextSize(14);
                     ded.setText("Please Select date");
                     error_dt.setText("Please Select date");

                 }


                 else if(totalday_given<totalday_valid1){
                     ded.setError( "Conference date can not be backdate" );
                     error_dt.setText("Conference date can not be backdate" );
                 }




                 else if(totalday_given < totalday_valid1){
                     ded.setError( "Back date can not be used for  PC Conference" );
                     error_dt.setText("Back date can not be used for  PC Conference ");
                     error_dt.setTextColor(Color.RED);

                 }

                 else if(  (actv.getText().toString().trim().equals("")) ||   (actv.getText().toString().isEmpty())   )
                 {
                     actv.setError("Market not Assigned");
                     error_dt.setText("Market is not Properly selected");
                 }

                 else if(  (venue_name.getText().toString().trim().equals("")) ||   (venue_name.getText().toString().isEmpty())   )
                 {
                     venue_name.setError("Please enter Venue name");
                     error_dt.setText("Venue name is not given");
                 }

                 else if (total_participent.getText().toString().trim().equals("") || total_participent.getText().toString().trim().equals(0)){
                     pc_rmp.setError("Enter Participants");
                     error_dt.setText("Please enter participants");

                 }


                 else if ( conf_type_val.equals("P") &&  (      (   Integer.parseInt( total_participent.getText().toString().trim() ) - inhouse_val) >  Integer.parseInt(person_limit) )  ){

                     pc_rmp.setError("Doctor Participant  limit is exceed");
                     error_dt.setText("Doctor Participant  is not more than "+ person_limit );
                 }



                 else if (food.getText().toString().trim().equals("") || food.getText().toString().trim().equals(0)){

                     food.setError("Enter Food cost");
                     error_dt.setText("Food cost is not given");
                 }

                 else if (  conf_type_val.equals("P") &&  Integer.parseInt(food.getText().toString().trim()) >  Integer.parseInt(budget_limit)){
                     food.setError("Food cost limit is exceed");
                     error_dt.setText("Food cost is not more then   BDT. "+ budget_limit+" Tk ");
                 }



                 else
                 {
                     if (venue_charge.getText().toString().trim().equals("")) {
                         v_charge="0";
                     }
                     else{
                         v_charge= venue_charge.getText().toString();
                     }


                     if(  (miscell_bdt.getText().toString().trim().equals("")) ||   (miscell_bdt.getText().toString().isEmpty())   )
                     {
                         miscell_bdt_val="0";
                     }

                     else{
                         miscell_bdt_val= miscell_bdt.getText().toString();
                     }


                     if(  (miscell.getText().toString().trim().equals("")) ||   (miscell.getText().toString().isEmpty())   )
                     {
                         miscell_val="0";
                     }

                     else{
                         miscell_val= miscell.getText().toString();
                     }



                     Thread next = new Thread(new Runnable() {
                         @Override
                         public void run() {



                             Intent in = new Intent(PCPendingProposal.this, PendingProposalDoc.class);
                             Bundle extras = new Bundle();
                             Bundle b = getIntent().getExtras();
                             String userName = b.getString("UserName");

                             extras.putString("MPO_CODE",userName);
                             extras.putString("ORDER_DELEVERY_DATE", ded.getText().toString());
                             extras.putString("conference_date",  ded.getText().toString());
                             extras.putString("market_code", market_code);
                             extras.putString("market_name",  actv.getText().toString().trim());
                             extras.putString("venue_name",  venue_name.getText().toString().trim());
                             extras.putString("venue_name",  venue_name.getText().toString().trim());
                             extras.putString("pc_rmp_val", String.valueOf(pc_rmp_val));
                             extras.putString("doc_val",  String.valueOf(doc_val));
                             extras.putString("inhouse_val",  String.valueOf( inhouse_val));
                             extras.putString("total_participant", total_participent.getText().toString().trim());
                             extras.putString("venue_charge",venue_charge.getText().toString().trim());
                             extras.putString("food_per_person", food.getText().toString().trim());
                             extras.putString("food_total_bdt",  fd_bdt.getText().toString().trim());
                             extras.putString("miscell",  miscell.getText().toString().trim());
                             extras.putString("miscell_bdt",  miscell_bdt.getText().toString().trim());
                             extras.putString("impact",  impact.getText().toString().trim());
                             extras.putString("UserName_2", UserName_2);
                             extras.putString("conf_type_val", conf_type_val);
                             extras.putString("pending_month", check_pc_date);
                             in.putExtras(extras);
                             startActivity(in);




                         }
                     });

                     next.start();





                 }


             }
         });























         /*======================================================================= next button click============================================================================*/






     }

     private void populateSpinner2() {
         List<String> list2 = new ArrayList<String>();
         for (int i = 0; i <brandlist.size(); i++) {
             list2.add(brandlist.get(i).getName());

         }

         spinner.setItems(list2);



     }


     private void populateSpinner() {
         List<String> lables = new ArrayList<String>();
         for (int i = 0; i <customerlist.size(); i++) {
             lables.add(customerlist.get(i).getName());

         }


         ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.spinner_text_view, lables);
         cust.setAdapter(spinnerAdapter);
         String[] customer = lables.toArray(new String[lables.size()]);
         ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,R.layout.spinner_text_view, customer);


         AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
         actv.setAdapter(Adapter);
         actv.setTextColor(Color.BLUE);


     }




     class GetMarket extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pDialog = new ProgressDialog(PCPendingProposal.this);
             pDialog.setMessage("Loading Market name..");
             pDialog.setCancelable(false);
             pDialog.show();

         }

         @Override
         protected Void doInBackground(Void... arg0) {

             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id=userName;

             List<NameValuePair> params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id",id));
             ServiceHandler jsonParser=new ServiceHandler();
             String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);



             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray customer = jsonObj.getJSONArray("customer");
                         for (int i = 0; i < customer.length(); i++) {
                             JSONObject catObj = (JSONObject) customer.get(i);

                             Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
                             customerlist.add(custo);
                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             } else {

             }

             return null;
         }

         @Override
         protected void onPostExecute(Void result) {
             super.onPostExecute(result);
             if (pDialog.isShowing())
                 pDialog.dismiss();
             populateSpinner();
         }

     }




     class GetPCBrand extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pDialog = new ProgressDialog(PCPendingProposal.this);
             pDialog.setMessage("Loading PC Brand .. .. ..");
             pDialog.setCancelable(false);
             pDialog.show();

         }

         @Override
         protected Void doInBackground(Void... arg0) {


             // ServiceHandler jsonParser = new ServiceHandler();
             // String json = jsonParser.makeServiceCall(URL_CUSOTMER,ServiceHandler.GET);

             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id=userName;

             List<NameValuePair> params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id",proposed_conference_date));

             ServiceHandler jsonParser=new ServiceHandler();
             String json=jsonParser.makeServiceCall(URL_PC_PRODUCT, ServiceHandler.POST, params);


             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray customer = jsonObj.getJSONArray("customer");
                         for (int i = 0; i < customer.length(); i++) {
                             JSONObject catObj = (JSONObject) customer.get(i);




                             Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
                             brandlist.add(custo);
                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             } else {

             }

             return null;
         }

         @Override
         protected void onPostExecute(Void result) {
             super.onPostExecute(result);
             if (pDialog.isShowing())
                 pDialog.dismiss();
             populateSpinner2();
         }

     }






     private void  pc_conference_setup_data() {
         List<String> lables = new ArrayList<String>();
         for (int i = 0; i <dateextendlist.size(); i++) {
             get_ext_dt= dateextendlist.get(i).getName();
         }


         String split[] = get_ext_dt.split("/");
         String get_val=split[0].trim();
         String get_val2=split[1].trim();
         String split1[]=get_val2.split("#");
         total_conference = split1[0].trim();
         setup_venue_charge = split1[1].trim();
         Log.w("showvalue1","-------total_conference---"+total_conference+"---setup_venue_charge--"+setup_venue_charge);
         String split2[] = get_val.split("-");
         budget_limit=split2[0].trim();
         person_limit=split2[1].trim();
         food.setHint("Food Budget max BDT "+  budget_limit +" per person" );
         venue_charge.setHint("Venue Charge max BDT "+  setup_venue_charge  +" ");
         miscell_bdt.setHint("Miscellaneous Charge max BDT "+  " 300 "  +" ");

     }

     class GeTPcConferenceSetupData extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();

         }

         @Override
         protected Void doInBackground(Void... arg0) {

             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id=userName;

             List<NameValuePair>params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id",proposed_conference_date));
             ServiceHandler jsonParser=new ServiceHandler();
             String json=jsonParser.makeServiceCall(pc_conference_setup_date, ServiceHandler.POST, params);


             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray customer = jsonObj.getJSONArray("customer");
                         for (int i = 0;i < 1; i++) {
                             JSONObject catObj = (JSONObject) customer.get(i);
                             Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
                             dateextendlist.add(custo);

                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             } else {

             }

             return null;
         }

         @Override
         protected void onPostExecute(Void result) {
             super.onPostExecute(result);
             //    pDialog2.dismiss();
             pc_conference_setup_data();
         }

     }






     private void  pc_conference_date_check() {
         date_flag="0";
         List<String> lables = new ArrayList<String>();
         for (int i = 0; i <checkdatelist.size(); i++) {
             date_flag= checkdatelist.get(i).getName();
         }


     }

     class GeTPcConferenceDate extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();

         }

         @Override
         protected Void doInBackground(Void... arg0) {

             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id=userName;
             List<NameValuePair>params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id",id));
             params.add(new BasicNameValuePair("conf_type_val",conf_type_val));
             params.add(new BasicNameValuePair("proposed_conference_date",proposed_conference_date));
             ServiceHandler jsonParser=new ServiceHandler();
             String json=jsonParser.makeServiceCall(pc_conference_date_check, ServiceHandler.POST, params);



             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray customer = jsonObj.getJSONArray("customer");
                         for (int i = 0;i < 1; i++) {
                             JSONObject catObj = (JSONObject) customer.get(i);
                             Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
                             checkdatelist.add(custo);

                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             } else {

             }

             return null;
         }

         @Override
         protected void onPostExecute(Void result) {
             super.onPostExecute(result);
             //    pDialog2.dismiss();
             pc_conference_date_check();
         }

     }




     /*=========================================== pc conference permission check================================================*/

     private void  pc_conference_flag() {
         check_flag="0";
         List<String> lables = new ArrayList<String>();
         for (int i = 0; i <checkflag.size(); i++) {
             check_flag= checkflag.get(i).getName();
         }


     }







     class GeTPcConferenceFlag extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();

         }

         @Override
         protected Void doInBackground(Void... arg0) {

             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id=userName;

             List<NameValuePair>params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id",id));
             params.add(new BasicNameValuePair("conf_type_val",conf_type_val));
             params.add(new BasicNameValuePair("proposed_conference_date",proposed_conference_date));
             ServiceHandler jsonParser=new ServiceHandler();
             // String json=jsonParser.makeServiceCall(pc_conference_date_check, ServiceHandler.POST, params);
             String json=jsonParser.makeServiceCall(pc_conference_check_flag, ServiceHandler.POST, params);


             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray customer = jsonObj.getJSONArray("customer");
                         for (int i = 0;i < 1; i++) {
                             JSONObject catObj = (JSONObject) customer.get(i);
                             Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));
                             // checkdatelist.add(custo);
                             checkflag.add(custo);


                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             } else {

             }

             return null;
         }

         @Override
         protected void onPostExecute(Void result) {
             super.onPostExecute(result);

             pc_conference_flag();
         }

     }

     /*======================================================================================================================*/


     private void  get_mpo_pending_pc_date_fetch() {
         check_pc_date="0";
         List<String> lables = new ArrayList<String>();
         for (int i = 0; i <checkpcdate.size(); i++) {
             check_pc_date= checkpcdate.get(i).getName();
         }



         p_month_disp.setText("PC for  Month \n"+check_pc_date);
         p_month_disp.setClickable(false);
     }




     class GetMpoPendingPcDate extends AsyncTask<Void, Void, Void> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();

         }

         @Override
         protected Void doInBackground(Void... arg0) {

             Bundle b = getIntent().getExtras();
             String userName = b.getString("UserName");
             String id=userName;

             List<NameValuePair>params=new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("id",id));
             params.add(new BasicNameValuePair("conf_type_val",conf_type_val));
             ServiceHandler jsonParser=new ServiceHandler();
             String json=jsonParser.makeServiceCall(mpo_pending_pc_date_fetch, ServiceHandler.POST, params);


             if (json != null) {
                 try {
                     JSONObject jsonObj = new JSONObject(json);
                     if (jsonObj != null) {
                         JSONArray customer = jsonObj.getJSONArray("customer");
                         for (int i = 0;i < 1; i++) {
                             JSONObject catObj = (JSONObject) customer.get(i);
                             Customer custo = new Customer(catObj.getInt("id"),catObj.getString("name"));

                             checkpcdate.add(custo);


                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             } else {

             }

             return null;
         }

         @Override
         protected void onPostExecute(Void result) {
             super.onPostExecute(result);
             get_mpo_pending_pc_date_fetch();

         }

     }



     /*======================================================================================================================*/


     @Override
     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

     }

     @Override
     public void onNothingSelected(AdapterView<?> parent) {

     }


     public void hideKeyboard(View view) {
         InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
         inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
     }

 }
