


package com.opl.pharmavector.serverCalls;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.util.Log;


import com.opl.pharmavector.doctorservice.ManagerDoctorServiceFollowup;
import com.opl.pharmavector.pcconference.PcApproval;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.model.Category5;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;



public class FavouriteCategoriesJsonParser9
{

    public static ArrayList<String> selectedCategories4 = new ArrayList<>();

    private final String URL_CUSOTMER = BASE_URL+"doctor_service/doctor_service_followup_monthly.php";

    public ArrayList<Category5> getParsedCategories() {

        ArrayList<Category5> MyArraylist = new ArrayList<>();
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        PcApproval RM_CODE;
        String myvalue;
        String mydate;

        String conf_type_val;
        String conf_date;
        myvalue =  ManagerDoctorServiceFollowup.UserName;
        mydate =  ManagerDoctorServiceFollowup.date_param;
        params.add(new BasicNameValuePair("id",myvalue));
        params.add(new BasicNameValuePair("service_month",mydate));
        ServiceHandler jsonParser=new ServiceHandler();
        String json=jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
        try{

            JSONArray jsonArray2 = new JSONArray(json);
            for (int i = 0; i < jsonArray2.length(); i++) {


                Category5 genres = new Category5();
                JSONObject MyJsonObject = jsonArray2.getJSONObject(i);

                genres.setCateogry_sl(MyJsonObject.getString("SERVICE_INFO"));
                genres.setCateogry_id(MyJsonObject.getString("MPO_CODE")); // NOT VISIBLE
                genres.setCategory_Name(MyJsonObject.getString("REQUEST_DT"));
                genres.setCategory_Name2(MyJsonObject.getString("DOC_NAME"));
                genres.setCategory_Name3(MyJsonObject.getString("ADDRESS"));
                genres.setCategory_Name4(MyJsonObject.getString("SERV_DESC"));
                genres.setCategory_Name5(MyJsonObject.getString("APPROXIMATE_COST"));
                genres.setCategory_Name6(MyJsonObject.getString("IS_MRD_CHECKED"));
                genres.setCategory_Name7(MyJsonObject.getString("IS_SALES_APPROVED"));
                genres.setCategory_Name8(MyJsonObject.getString("IS_PAYMENT"));
                genres.setCategory_Name9(MyJsonObject.getString("MPO_ACK"));
                genres.setCategory_Name10(MyJsonObject.getString("MPO_CODE"));
                genres.setSelected(Boolean.parseBoolean(MyJsonObject.getString("SELECTED")));
                MyArraylist.add(genres);

                if (MyJsonObject.getString("SELECTED").equals("true")) {
                    selectedCategories4.add(MyJsonObject.getString("SERVICE_NO"));
                    Log.e("selectedCategories4: ", "> " + selectedCategories4);



                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




        return MyArraylist;
    }
}




