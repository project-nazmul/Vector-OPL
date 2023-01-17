package com.opl.pharmavector.doctorgift;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.opl.pharmavector.PCBillFollowup;
import com.opl.pharmavector.PCDashboard;
import com.opl.pharmavector.R;

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

public class GiftEntryFollowup extends Activity {
    ArrayList<DataHolder> records;
    ListView listView;
    String username,UserName_2,new_version,user_flag;
    Button backbt;
    ProgressDialog pDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_entry_followup);
        listView = (ListView)findViewById(R.id.pListView);
        Bundle b = getIntent().getExtras();
        username = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        user_flag = b.getString("user_flag");
        getJSON("http://opsonin.com.bd/vector_opl_v1/doctor_gift/doc_gift_entry_followup.php");
        backbt =findViewById(R.id.backbt);
        backbt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                    Intent i = new Intent(GiftEntryFollowup.this, DocGiftDashBoard.class);
                    i.putExtra("UserName", username);
                    i.putExtra("new_version", new_version);
                    i.putExtra("UserName_2", UserName_2);
                    i.putExtra("user_flag", user_flag);
                    startActivity(i);
            }
        });
    }
    public void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(GiftEntryFollowup.this);
                pDialog.setMessage("Data Loading...");
                pDialog.setCancelable(false);
                pDialog.show();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    LoadQuetions(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (pDialog.isShowing())
                    pDialog.dismiss();
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
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            DataHolder p=new DataHolder(null,null,null,null,null,null,null,null,null,null,null,null);
            p.setvalue1(String.valueOf(i+1));
            p.setvalue2(obj.getString("FOR_MONTH"));
            p.setvalue3(obj.getString("TERRI_CODE"));
            p.setvalue4(obj.getString("GIFT_NAME"));
            p.setvalue5(obj.getString("DOC_NAME"));
            p.setvalue6(obj.getString("DOC_CODE"));
            p.setvalue7(obj.getString("AM"));
            p.setvalue8(obj.getString("RM"));
            p.setvalue9(obj.getString("ASM"));
            p.setvalue10(obj.getString("SM"));
            p.setvalue11(obj.getString("GM"));
            records.add(p);
        }
        CustomAdapterFollowup adapter = new CustomAdapterFollowup(this,records);
        listView.setAdapter(adapter);
    }
}
