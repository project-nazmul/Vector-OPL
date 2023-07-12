package com.opl.pharmavector.serverCalls;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.model.Category5;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavouriteCategoriesJsonParser6 {
    public static ArrayList<String> selectedCategories4 = new ArrayList<>();
    final String URL_CUSOTMER = BASE_URL+"doctor_service/mpo_doctor_service_followup.php";

    public ArrayList<Category5> getParsedCategories() {
        ArrayList<Category5> MyArraylist = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", Dashboard.globalmpocode));
        ServiceHandler jsonParser = new ServiceHandler();
        String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);

        try {
            JSONArray jsonArray2 = new JSONArray(json);

            for (int i = 0; i < jsonArray2.length(); i++) {
                Category5 genres = new Category5();
                JSONObject MyJsonObject = jsonArray2.getJSONObject(i);
                genres.setCateogry_sl(MyJsonObject.getString("SERVICE_NO"));
                genres.setCateogry_id(MyJsonObject.getString("SERVICE_NO")); // NOT VISIBLE
                genres.setCategory_Name(MyJsonObject.getString("SERV_MONTH"));
                genres.setCategory_Name2(MyJsonObject.getString("DOC_NAME"));
                genres.setCategory_Name3(MyJsonObject.getString("ADDRESS"));
                genres.setCategory_Name4(MyJsonObject.getString("SERV_DESC"));
                genres.setCategory_Name5(MyJsonObject.getString("APPROXIMATE_COST"));
                genres.setCategory_Name6(MyJsonObject.getString("IS_MRD_CHECKED"));
                genres.setCategory_Name7(MyJsonObject.getString("IS_SALES_APPROVED"));
                genres.setCategory_Name8(MyJsonObject.getString("APPROVE_DT"));
                genres.setCategory_Name9(MyJsonObject.getString("IS_PAYMENT"));
                genres.setCategory_Name10(MyJsonObject.getString("PAYMENT_DT"));
                genres.setSelected(Boolean.parseBoolean(MyJsonObject.getString("SELECTED")));
                MyArraylist.add(genres);

                if (MyJsonObject.getString("SELECTED").equals("true")) {
                    selectedCategories4.add(MyJsonObject.getString("SERVICE_NO"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return MyArraylist;
    }
}

