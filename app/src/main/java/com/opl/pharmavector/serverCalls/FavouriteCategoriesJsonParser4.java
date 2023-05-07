package com.opl.pharmavector.serverCalls;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.util.Log;


import com.opl.pharmavector.pcconference.PcApproval;
import com.opl.pharmavector.pcconference.PcProposalDoc;
import com.opl.pharmavector.pcconference.RMPCPermission;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.model.Category4;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavouriteCategoriesJsonParser4 {
    public static ArrayList<String> selectedCategories4 = new ArrayList<>();
    private String URL_CUSOTMER = BASE_URL+"pcconference/pc_approval/pc_conference_approval.php";

    public ArrayList<Category4> getParsedCategories() {
        ArrayList<Category4> MyArraylist = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        PcApproval RM_CODE;
        String myvalue;
        String conf_type_val;
        String conf_date;
        myvalue = PcApproval.UserName;
        params.add(new BasicNameValuePair("id", myvalue));
        ServiceHandler jsonParser = new ServiceHandler();
        String json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
        try {
            JSONArray jsonArray2 = new JSONArray(json);
            for (int i = 0; i < jsonArray2.length(); i++) {
                Category4 genres = new Category4();
                JSONObject MyJsonObject = jsonArray2.getJSONObject(i);
                genres.setCateogry_sl(MyJsonObject.getString("CONF_PROPOSE_DT_T"));
                genres.setCateogry_id(MyJsonObject.getString("PC_SLNO"));
                genres.setCategory_Name(MyJsonObject.getString("CONF_VENUE"));
                genres.setCategory_Name2(MyJsonObject.getString("PARTICIPET"));
                genres.setCategory_Name3(MyJsonObject.getString("TOTAL_BUDGET"));
                genres.setCategory_Name4(MyJsonObject.getString("FOOD_BUDGET"));
                genres.setCategory_Name5(MyJsonObject.getString("MISC"));
                genres.setCategory_Name6(MyJsonObject.getString("VENU_CHARGE"));
                genres.setCategory_Name7(MyJsonObject.getString("DOC_PHONE"));
                genres.setCategory_Name8(MyJsonObject.getString("PC_RMP"));
                genres.setCategory_Name9(MyJsonObject.getString("NOF_DOC_CUM_CHEMIST"));
                genres.setCategory_Name10(MyJsonObject.getString("NOF_IN_HOUSE"));
                genres.setSelected(Boolean.parseBoolean(MyJsonObject.getString("SELECTED")));
                MyArraylist.add(genres);
                if (MyJsonObject.getString("SELECTED").equals("true")) {
                    selectedCategories4.add(MyJsonObject.getString("PC_SLNO"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return MyArraylist;
    }
}