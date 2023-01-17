package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.opl.pharmavector.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddComment extends Activity implements OnClickListener{

    private EditText title, message;
    private Button  mSubmit;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    //testing on Emulator:
    private static final String POST_COMMENT_URL = BASE_URL+"addcomment.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcomment);
        title = (EditText)findViewById(R.id.title);
        message = (EditText)findViewById(R.id.message);
        mSubmit = (Button)findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        new PostComment().execute();
    }

    class PostComment extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddComment.this);
            pDialog.setMessage("Posting Comment...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            @SuppressLint("WrongThread") String post_title = title.getText().toString();
            @SuppressLint("WrongThread") String post_message = message.getText().toString();

            //We need to change this:
            String post_username = "temp";

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", post_username));
                params.add(new BasicNameValuePair("title", post_title));
                params.add(new BasicNameValuePair("message", post_message));
                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        POST_COMMENT_URL, "POST", params);
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    finish();
                    return json.getString(TAG_MESSAGE);
                }else{
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(AddComment.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }


}
