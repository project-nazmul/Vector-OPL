package com.opl.pharmavector.giftfeedback;
/*
public class FeedbackDetails {
}
*/

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeedbackDetails extends AppCompatActivity {
    private String TAG = com.opl.pharmavector.giftfeedback.FeedbackDetails.class.getSimpleName();
    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;
    public String noti_title, noti_date, noti_detail, imagelink, flash_details;
    public String notification_id,user_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_from_url);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Bundle b = getIntent().getExtras();
        user_code = b.getString("user_code");

        new GetContacts().execute();
    }


    class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String url = "http://opsonin.com.bd/vector_opl_v1/notification/get_notification_detail.php";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_code", user_code));
            ServiceHandler jsonParser = new ServiceHandler();
            String jsonStr = jsonParser.makeServiceCall(url, ServiceHandler.GET, params);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray contacts = jsonObj.getJSONArray("categories");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        noti_title = c.getString("PROD_RATE");
                        noti_date = c.getString("PROD_VAT");
                        noti_detail = c.getString("name");
                        flash_details = c.getString("PPM_CODE");
                        imagelink = c.getString("P_CODE");

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            PhotoView mImageView = (PhotoView) findViewById(R.id.image_view);
            TextView notice_title = findViewById(R.id.notice_title);
            TextView notice_date = findViewById(R.id.notice_date);
            TextView notice_message = findViewById(R.id.product_name);
            TextView flash_message = findViewById(R.id.flash);
            notice_message.setText(noti_detail);
            notice_date.setText(noti_date);
            notice_title.setText(noti_title);




        }
    }


}