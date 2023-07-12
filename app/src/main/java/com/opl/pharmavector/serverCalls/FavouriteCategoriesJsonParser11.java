package com.opl.pharmavector.serverCalls;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.util.Log;


import com.opl.pharmavector.PendingProposalDoc;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.model.Category;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class FavouriteCategoriesJsonParser11{
    public static ArrayList<String> selectedCategories = new ArrayList<>();
    public static ArrayList<String> selectedphonenumber = new ArrayList<>();
    private final String URL_CUSOTMER = BASE_URL+"pc_conference_doctor.php";
    
    public ArrayList<Category> getParsedCategories() {
        ArrayList<Category> MyArraylist = new ArrayList<>();
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        PendingProposalDoc uername;
        String myvalue;
        myvalue = PendingProposalDoc.UserName;
        params.add(new BasicNameValuePair("id",myvalue));
        ServiceHandler jsonParser=new ServiceHandler();
        String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

        try {
            JSONArray jsonArray2 = new JSONArray(json);

            for (int i = 0; i < jsonArray2.length(); i++) {
                Category genres = new Category();
                JSONObject MyJsonObject = jsonArray2.getJSONObject(i);
                genres.setCateogry_sl(MyJsonObject.getString("SERIAL"));
                genres.setCateogry_id(MyJsonObject.getString("DOC_CODE"));
                genres.setCategory_Name(MyJsonObject.getString("DOC_CODE"));
                genres.setCategory_Name2(MyJsonObject.getString("DOC_NAME"));
                genres.setCategory_Name3(MyJsonObject.getString("DOC_PHONE"));
                genres.setSelected(Boolean.parseBoolean(MyJsonObject.getString("SELECTED")));
                MyArraylist.add(genres);

                if (MyJsonObject.getString("SELECTED").equals("true")) {
                    selectedCategories.add(MyJsonObject.getString("DOC_CODE"));
                    selectedphonenumber.add(MyJsonObject.getString("DOC_PHONE"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return MyArraylist;
    }
}