package com.opl.pharmavector.doctorgift;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.DoctorGiftActivity;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.opl.pharmavector.ContactsAdapter.selectedphonenumber;
import static com.opl.pharmavector.doctorgift.CustomAdapterApproval.selectedApprovalList;

public class DocGiftEntryApproval extends AppCompatActivity {
    ArrayList<DataHolder> records;
    ListView listView;
    String username,UserName_2,new_version,user_flag;
    Button backbt,btn_approved;
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public int success;
    public String message;
    private String url_For_Approved = "http://opsonin.com.bd/vector_opl_v1/doctor_gift/doc_gift_entry_approval_submit.php";
    public String approvalList;
    ProgressDialog progressDialog,pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_gift_entry_approval);
        listView = (ListView)findViewById(R.id.pListView);
        btn_approved =findViewById(R.id.btn_submit);
        Bundle b = getIntent().getExtras();
        username = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        user_flag = b.getString("user_flag");
        getJSON("http://opsonin.com.bd/vector_opl_v1/doctor_gift/doc_gift_entry_approval.php");
        backbt =findViewById(R.id.backbt);

        backbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

/*
                if (distance > 950){
                    showSnack();
                }
                else{
                    showSnack2();
                }
                */


                Intent i = new Intent(DocGiftEntryApproval.this, DocGiftDashBoard.class);
                i.putExtra("UserName", username);
                i.putExtra("new_version", new_version);
                i.putExtra("UserName_2", UserName_2);
                i.putExtra("user_flag", user_flag);
                startActivity(i);
            }
        });

        btn_approved.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                progressDialog = new ProgressDialog(DocGiftEntryApproval.this);
                progressDialog.setMessage("Loading..."); // Setting Message
                progressDialog.setTitle("ProgressDialog"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                approvalList = selectedApprovalList.toString();
                approvalList = approvalList.substring(1, approvalList.length() - 1);

                if (selectedApprovalList.size() < 1) {
                    Toast.makeText(DocGiftEntryApproval.this, "Please Select Requisition No For Approved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread server = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONParser jsonParser = new JSONParser();
                            Log.d("userCode", username);
                            Log.d("approvalList", approvalList);

                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("approvalList", approvalList));
                            params.add(new BasicNameValuePair("userCode", username));
                            JSONObject json = jsonParser.makeHttpRequest(url_For_Approved, "POST", params);

                            try {
                                success = json.getInt(TAG_SUCCESS);
                                message = json.getString(TAG_MESSAGE);

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                            progressDialog.dismiss();
                            Intent i = new Intent(DocGiftEntryApproval.this, DocGiftEntryApproval.class);
                            i.putExtra("UserName", username);
                            i.putExtra("new_version", new_version);
                            i.putExtra("UserName_2", UserName_2);
                            i.putExtra("user_flag", user_flag);
                            startActivity(i);
                        }
                    });
                    server.start();
                }

            }
        });

    }
    public void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(DocGiftEntryApproval.this);
                pDialog.setMessage("Data Loading...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null){
                    try {
                        LoadQuetions(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }
                else{
                    getJSON("http://opsonin.com.bd/vector_opl_v1/doctor_gift/doc_gift_entry_approval.php");
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                    bufferedWriter.write(login_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream  = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private void LoadQuetions(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        records=new ArrayList<DataHolder>();
        Log.e("Jsonarraylenght ==>", String.valueOf(jsonArray.length()));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            DataHolder p=new DataHolder(null,null,null,null,null,null,null,null,null,null,null,null);
            p.setvalue1(String.valueOf(i+1));
            p.setvalue2(obj.getString("FOR_MONTH"));
            p.setvalue3(obj.getString("TERRI_CODE"));
            p.setvalue4(obj.getString("GIFT_NAME"));
            p.setvalue5(obj.getString("DOC_NAME"));
            p.setvalue6(obj.getString("DOC_CODE"));
            p.setvalue7(obj.getString("REQ_SLNO"));
            records.add(p);
        }
        CustomAdapterApproval adapter = new CustomAdapterApproval(this,records);
        listView.setAdapter(adapter);
    }
}