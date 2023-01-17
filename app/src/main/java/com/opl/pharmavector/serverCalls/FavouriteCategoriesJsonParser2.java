package com.opl.pharmavector.serverCalls;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.util.Log;

import com.opl.pharmavector.pcconference.PcProposalDoc;
import com.opl.pharmavector.pcconference.RMPCPermission;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.model.Category;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;




public class FavouriteCategoriesJsonParser2 {


    public static ArrayList<String> selectedCategories = new ArrayList<>();
    private String URL_CUSOTMER = BASE_URL+"pcconference/pc_conference_mpo.php";


    public ArrayList<Category> getParsedCategories() {
        ArrayList<Category> MyArraylist = new ArrayList<>();
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        RMPCPermission RM_CODE;
        String myvalue;
        String conf_type_val;
        String conf_date,depot_code;
        myvalue = RMPCPermission.RM_CODE;
        conf_type_val = RMPCPermission.conf_type;
        conf_date = RMPCPermission.proposed_conference_date;
       // depot_code = RMPCPermission.rm_depot_code;
        params.add(new BasicNameValuePair("id",myvalue));
        params.add(new BasicNameValuePair("conf_date",conf_date));
        params.add(new BasicNameValuePair("conf_type",conf_type_val));
        //params.add(new BasicNameValuePair("depot_code",depot_code));
        ServiceHandler jsonParser=new ServiceHandler();
        String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);



        try{
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

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




        return MyArraylist;
    }
}