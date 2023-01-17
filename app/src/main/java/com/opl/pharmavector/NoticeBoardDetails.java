package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NoticeBoardDetails extends AppCompatActivity {
    private String TAG = NoticeBoardDetails.class.getSimpleName();
    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;
    public String noti_title, noti_date, noti_detail, imagelink, flash_details;
    public String notification_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_from_url);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Bundle b = getIntent().getExtras();
        final String userName = b.getString("UserName");
        final String UserName_2 = b.getString("UserName_2");
        notification_id = b.getString("notification_id");
        new GetContacts().execute();
    }


    class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String url = BASE_URL+"notification/get_notification_detail.php";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("notification_id", notification_id));
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
            if (imagelink != null && imagelink.contains("product-landing-banner.jpg")) {
                mImageView.setVisibility(View.GONE);
            } else {
                Toast.makeText(NoticeBoardDetails.this, "Image is Loading", Toast.LENGTH_LONG).show();
                Picasso.get().load(imagelink).into(mImageView);
            }
            TextView notice_title = findViewById(R.id.notice_title);
            TextView notice_date = findViewById(R.id.notice_date);
            TextView notice_message = findViewById(R.id.product_name);
            TextView flash_message = findViewById(R.id.flash);
            notice_message.setText(noti_detail);
            Linkify.addLinks(notice_message, Linkify.WEB_URLS);
            notice_date.setText(noti_date);
            notice_title.setText(noti_title);

            if (flash_details != null && flash_details.equals("0")) {

                flash_message.setVisibility(View.GONE);

            } else {
                flash_message.setVisibility(View.VISIBLE);
                notice_message.setVisibility(View.GONE);
                flash_message.setText(flash_details);
                Linkify.addLinks(flash_message, Linkify.WEB_URLS);
            }


        }
    }


}