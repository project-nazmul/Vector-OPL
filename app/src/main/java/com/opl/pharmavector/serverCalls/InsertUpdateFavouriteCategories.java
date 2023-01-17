package com.opl.pharmavector.serverCalls;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;

public class InsertUpdateFavouriteCategories {



    public static String insertUpdateCall(String categoriesCsv) {
        String response = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(BASE_URL+"put_doc.php");
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("email", "tutorials@codingtrickshub.com"));
            nameValuePairs.add(new BasicNameValuePair("favouriteCategories", categoriesCsv));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            response = EntityUtils.toString(httpResponse.getEntity());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


}