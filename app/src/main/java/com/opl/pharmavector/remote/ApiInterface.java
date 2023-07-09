package com.opl.pharmavector.remote;

import com.opl.pharmavector.RecyclerData;
import com.opl.pharmavector.amdashboard.VacantModel;
import com.opl.pharmavector.dcfpFollowup.DcfpEntrySetUpModel;
import com.opl.pharmavector.dcfpFollowup.DcrFollowupModel;
import com.opl.pharmavector.dcfpFollowup.DcfpEntryDoctorModel;
import com.opl.pharmavector.doctorList.model.DoctorFFModel;
import com.opl.pharmavector.doctorList.model.DoctorModel;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mpodcr.DcfpModel;
import com.opl.pharmavector.mpodcr.DcfpStatusModel;
import com.opl.pharmavector.msd_doc_support.adapter.MSDApprovalModel;
import com.opl.pharmavector.msd_doc_support.adapter.MSDCommitmentModel;
import com.opl.pharmavector.msd_doc_support.adapter.MSDSubmitModel;
import com.opl.pharmavector.personalExpense.model.MotorCycleModel;
import com.opl.pharmavector.pmdVector.model.BrandModel;
import com.opl.pharmavector.pmdVector.model.CompanyModel;
import com.opl.pharmavector.pmdVector.model.FFTeamModel;
import com.opl.pharmavector.pmdVector.model.ProductModel;
import com.opl.pharmavector.pmdVector.model.RXModel;
import com.opl.pharmavector.pmdVector.model.RegionUnitModel;
import com.opl.pharmavector.pmdVector.model.RegionValModel;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.MovieModel;
import com.opl.pharmavector.prescriptionsurvey.rx_model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("vector_login/userLog.php")
    Call<Patient> userData(
            @Field("key") String key,
            @Field("vector_version") String vector_version,
            @Field("vectortoken") String vectortoken,
            @Field("user_lang") String user_lang,
            @Field("user_lat") String user_lat,
            @Field("model") String model,
            @Field("brand") String brand,
            @Field("user_code") String user_code,
            @Field("user_loc") String user_loc
    );

    @FormUrlEncoded
    @POST("vector_login/userLogInLog.php")
    Call<Patient> userLogIn(
            @Field("user_id") String user_id,
            @Field("mpo_code") String mpo_code,
            @Field("vector_version") String vector_version,
            @Field("user_lang") String user_lang,
            @Field("user_lat") String user_lat,
            @Field("model") String model,
            @Field("brand") String brand,
            @Field("user_code") String user_code,
            @Field("user_loc") String user_loc
    );

    @FormUrlEncoded
    @POST("vector_login/lock_emp_check.php")
    Call<Patient> lock_emp_check(
            @Field("user_code") String user_code
    );

    @FormUrlEncoded
    //@POST("vector_login/vector_pmd_login.php")
    @POST("vector_login/vectorlogin_newversion.php")
    Call<Patient> vectorlogin(
            @Field("username") String tempLogin,
            @Field("password") String tempPassword,
            @Field("vectortoken") String vectortoken
    );

    @FormUrlEncoded
    @POST("vector_login/oniktest.php")
    Call<List<Patient>> pmdImage(
            @Field("mpo_code") String mpo_code
    );

    @FormUrlEncoded
    @POST("prescription_survey/prescriptioncount_new.php")
    Call<List<Patient>> prescriptioncount(
            @Field("mpo_code") String mpo_code,
            @Field("brand_code") String brand_code,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("pres_type") String pres_type
    );

    @FormUrlEncoded
    @POST("prescription_survey/pres_sub_total.php")
    Call<List<Patient>> pres_sub_total(
            @Field("mpo_code") String mpo_code,
            @Field("brand_code") String brand_code,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("pres_type") String pres_type
    );

    @FormUrlEncoded
    @POST("pmd_vector/pmd_rx/prescriptioncount_new.php")
    Call<List<Patient>> pmd_prescriptioncount(
            @Field("mpo_code") String passed_manager_code,
            @Field("brand_code") String brand_code,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("pres_type") String pres_type,
            @Field("pmd_loccode") String pmd_loccode
    );

    @FormUrlEncoded
    @POST("rx_dcc_camp/generation/prescriptioncount.php")
    Call<List<Patient>> rx_dcc_doc_count(
            @Field("mpo_code") String passed_manager_code,
            @Field("brand_code") String brand_code,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("pres_type") String pres_type,
            @Field("pmd_loccode") String pmd_loccode
    );

    @FormUrlEncoded
    @POST("rx_dcc_camp/generation/rx_dcc_brand_detail.php")
    Call<List<Patient>> rx_dcc_brand_detail(
            @Field("cust_code") String cust_code,
            @Field("brand_code") String brand_code
    );

    @FormUrlEncoded
    @POST("prescription_survey/iog_prescriptioncount.php")
    Call<List<Patient>> prescriptioncount2(
            @Field("mpo_code") String mpo_code,
            @Field("brand_code") String brand_code,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("pres_type") String pres_type,
            @Field("rx_gift_code") String rx_gift_code
    );

    @FormUrlEncoded
    @POST("pmd_vector/pmd_rx/iog_prescriptioncount.php")
    Call<List<Patient>> pmd_prescriptioncount2(
            @Field("mpo_code") String mpo_code,
            @Field("brand_code") String brand_code,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("pres_type") String pres_type,
            @Field("pmd_loccode") String pmd_loccode
    );

    @POST("prescription_survey/getDoctorDegree.php")
    Call<List<Patient>> getDoctorDegree();

    @FormUrlEncoded
    @POST("doctor_gift/getGiftType.php")
    Call<List<Patient>> getGiftType(
            @Field("mpo_code") String mpo_code,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded
    @POST("doctor_gift/getItemCount.php")
    Call<List<Patient>> getItemCount(
            @Field("mpo_code") String mpo_code,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded
    @POST("mpodcr/get_dcfp_list.php")
    Call<DcfpModel> getDcfpPreviewList(
            @Field("id") String id,
            @Field("select_date") String to_date
    );

    @FormUrlEncoded
    @POST("area_manager_api/amdcr/get_vacant_mpo.php")
    Call<VacantModel> getVacantMpoList(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("doctor_gift/getGiftItem.php")
    Call<List<Patient>> getGiftItem(
            @Field("mpo_code") String mpo_code,
            @Field("gift_type_name") String gift_type_name,
            @Field("gift_type_code") String gift_type_code,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded
    @POST("prescription_survey/getdoctorinfo.php")
    Call<List<Patient>> getDoctorinfo(
            @Field("mpo_code") String mpo_code,
            @Field("survey_date") String survey_date,
            @Field("to_date") String to_date,
            @Field("product_code") String product_code
    );

    @FormUrlEncoded
    @POST("contact/getcontactinfo.php")
    Call<ArrayList<RecyclerData>> getcontactinfo(
            @Field("mpo_code") String mpo_code,
            @Field("brand_code") String brand_code
    );

    @FormUrlEncoded
    @POST("pmd_vector/ff_contacts/get_ff_contact_info.php")
    Call<ArrayList<RecyclerData>> getFFContactInfo(
            @Field("deignation_type") String deignation_type,
            @Field("ff_code") String ff_code,
            @Field("team_type") String team_type
    );

    @FormUrlEncoded
    @POST("mpodcr/get_dcfp_flag.php")
    Call<DcfpStatusModel> getDcfpStatusFlag( @Field("id") String id);

    @GET("get_team.php")
    Call<FFTeamModel> getFFTeamList();

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("pmd_vector/sales_4p/get_brandwise_data.php")
    Call<BrandModel> getBrandWiseList(@Field("mnyr") String mnyr, @Field("brand_code") String brand_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("pmd_vector/sales_4p/get_companywise_brand.php")
    Call<CompanyModel> getCompanyWiseList(@Field("mnyr") String mnyr, @Field("brand_code") String brand_code);

    @GET("pmd_vector/sales_4p/get_regionwise_unit_sh.php")
    Call<RegionUnitModel> getRegionUnitShareList();

    @GET("pmd_vector/sales_4p/get_regionwise_val_sh.php")
    Call<RegionValModel> getRegionValShareList();

    @GET("mpodcr/mpo_pexpense/get_exp_unit.php")
    Call<MotorCycleModel> getMotorExpenseList();

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("get_mrd_doctor_ff_list.php")
    Call<DoctorFFModel> getDoctorFFList(@Field("ff_code") String ff_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("dcfp/get_doctor.php")
    Call<DcfpEntryDoctorModel> getDcfpEntryDoctorList(@Field("id") String id);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("dcfp/get_dcfp_setup.php")
    Call<DcfpEntrySetUpModel> getDcfpEntrySetUpList(@Field("id") String id, @Field("DOC_CODE") String doc_code);

    //@POST("dcfp/submit_dcfp.php")
    //Call<DcfpEntrySetUpModel> getDcfpEntryListSubmit(@Body);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("dcfp/self_followup.php")
    Call<List<DcrFollowupModel>> getDcrSelfFollowup(@Field("id") String id, @Field("t_date") String t_date, @Field("f_date") String f_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("dcfp/dcfp_followup.php")
    Call<List<DcrFollowupModel>> getDcrDcfpFollowup(@Field("id") String id, @Field("t_date") String t_date, @Field("f_date") String f_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("get_mrd_doctor.php")
    Call<DoctorModel> getDoctorDetailsList(@Field("ff_code") String ff_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("msd_doc_support/msd_program_approval_list.php")
    Call<List<MSDApprovalModel>> getMSDApprovalList(@Field("mpo_code") String mpo_code, @Field("p_month") String p_month);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("msd_doc_support/msd_program_approval_list_submit.php")
    Call<MSDSubmitModel> getMSDApprovalSubmit(@Field("MSD_SLNO") String msd_slno, @Field("mpo_code") String username);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("msd_doc_support/msd_program_commitment_followup.php")
    Call<List<MSDCommitmentModel>> getMSDCommitmentFollowup(@Field("p_month") String p_month);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("pmd_vector/get_brandList.php")
    //@POST("pmd_vector/pmd_rx/get_brandList.php")
    Call<ProductModel> getProductBrandList(@Field("manager_code") String manager_code);

    @FormUrlEncoded
    @POST("prescription_survey/loadimage.php")
    Call<List<MovieModel>> getMovies(
            @Field("index") int index,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("product_code") String product_code,
            @Field("manager_code") String manager_code,
            @Field("user_code") String user_code
    );

    @FormUrlEncoded
    @POST("prescription_survey/loadimage.php")
    Call<ArrayList<rx_model>> getLoad_Rx(
            @Field("index") int index,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("product_code") String product_code,
            @Field("manager_code") String manager_code,
            @Field("user_code") String user_code
    );

    @FormUrlEncoded
    @POST("prescription_survey/loadmpoimage.php")
    Call<List<MovieModel>> loadmpoimage(
            @Field("index") int index,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("product_code") String product_code,
            @Field("pres_type") String pres_type,
            @Field("mpo_code") String mpo_code,
            @Field("report_type") String report_type,
            @Field("pres_sub_type") String pres_sub_type
    );

    @FormUrlEncoded
    @POST("mposalesreports/depo_report/credit_customer_info.php")
    Call<ArrayList<RecyclerData>> getCustCredit(
            @Field("ff_code") String ff_code,
            @Field("cust_code") String cust_code
    );

    @FormUrlEncoded
    @POST("mposalesreports/depo_report/sp_pct_cust_info.php")
    Call<ArrayList<RecyclerData>> getCust_Sp_Pct(
            @Field("ff_code") String ff_code,
            @Field("cust_code") String cust_code
    );

    @FormUrlEncoded
    @POST("mposalesreports/depo_report/cust_replacement_info.php")
    Call<ArrayList<RecyclerData>> getCust_Replacement(
            @Field("ff_code") String ff_code,
            @Field("cust_code") String cust_code
    );

    @FormUrlEncoded
    @POST("pmd_vector/pmd_rx/loadimage.php")
    Call<List<RXModel>> getMovies2(
            @Field("index") int index,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("product_code") String product_code,
            @Field("manager_code") String manager_code,
            @Field("mpo_code") String mpo_code
    );

    @FormUrlEncoded
    @POST("doctor_gift/insert_feedback.php")
    Call<Patient> insertFeedback2(
            @Field("key") String key,
            @Field("rating") Float rating,
            @Field("rating1") Float rating1,
            @Field("rating2") Float rating2,
            @Field("rating3") Float rating3,
            @Field("rating4") Float rating4,
            @Field("rating5") Float rating5,
            @Field("gift_type_name") String gift_type_name,
            @Field("gift_item_name") String gift_item_name,
            @Field("ff_code") String ff_code,
            @Field("feedback_date") String feedback_date
    );

    @FormUrlEncoded
    @POST("doctor_gift/insert_feedback.php")
    Call<Patient> insertFeedback(
            @Field("key") String key,
            @Field("rating1") String rating1,
            @Field("rating2") String rating2,
            @Field("rating3") String rating3,
            @Field("rating4") String rating4,
            @Field("rating5") String rating5,
            @Field("rating6") String rating6,
            @Field("gift_type_name") String gift_type_name,
            @Field("gift_item_name") String gift_item_name,
            @Field("ff_code") String ff_code,
            @Field("feedback_date") String feedback_date
    );

    //postmsdReq
    @FormUrlEncoded
    @POST("msd_doc_support/insert_msd_req.php")
    Call<Patient> postmsdReq(
            @Field("img") String img,
            @Field("mpo_code") String mpo_code,
            @Field("doc_code") String doc_code,
            @Field("gift_items") String gift_items,
            @Field("date") String date,
            @Field("description") String description
    );
     /*
                params.put("img", getStringImage(decoded));
                params.put("mpo_code", Dashboard.globalmpocode);
                params.put("doc_code", doc_code);
                params.put("gift_items",multi_spinner.getSelectedItem().toString());
                params.put("date",ded.getText().toString().trim());
                params.put("description",tv_doc_chamber_address.getText().toString().trim() );
         */

    @FormUrlEncoded
    @POST("geoloc/insert_gps_vector.php")
    Call<Patient> insertgps(
            @Field("doc_code") String doc_code,
            @Field("doc_address") String doc_address,
            @Field("track_lang") double track_lang,
            @Field("track_lat") double track_lat,
            @Field("mpo_code") String mpo_code,
            @Field("gps_status") String status,
            @Field("chambar_img") String image
    );

    @FormUrlEncoded
    @POST("utils/otpConfirmation.php")
    Call<Patient> sendotpforpassword(
            @Field("phone_number") String phone_number
    );

    @FormUrlEncoded
    @POST("utils/setNewpassword.php")
    Call<Patient> setnewpassword(
            @Field("otp_number") String otp_number,
            @Field("otpSLnumber") String otpSLnumber,
            @Field("ffrole") String ffrole,
            @Field("phone_number") String phone_number,
            @Field("confirm_password") String confirm_password
    );

    @FormUrlEncoded
    @POST("vectorexam/fetch_exam_flag.php")
    Call<List<Patient>> getExamflag(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("mpodcr/dcr_count.php")
    Call<List<Patient>> postDcrCheck(
            @Field("mpo_code") String mpo_code,
            @Field("from_date") String from_date
    );

    @FormUrlEncoded
    @POST("promofollowupv2/mpo_promo_followup.php")
    Call<List<Patient>> mpo_sample_followup(
            @Field("mpo_code") String mpo_code,
            @Field("user_flag") String user_flag,
            @Field("promo_type") String promo_type,
            @Field("promo_month") String proposed_date2,
            //promo_subtype
            @Field("promo_subtype") String promo_subtype
    );

    @FormUrlEncoded
    @POST("promofollowupv2/fm_promo_followup.php")
    Call<List<Patient>> fm_promo_followup(
            @Field("id") String mpo_code,
            @Field("user_flag") String user_flag,
            @Field("promo_type") String promo_type,
            @Field("promo_month") String proposed_date2,
            @Field("promo_subtype") String promo_subtype
    );

    @FormUrlEncoded
    @POST("promofollowupv2/rm_promo_followup.php")
    Call<List<Patient>> rm_promo_followup(
            @Field("id") String mpo_code,
            @Field("user_flag") String user_flag,
            @Field("promo_type") String promo_type,
            @Field("promo_month") String proposed_date2,
            @Field("promo_subtype") String promo_subtype
    );

    @FormUrlEncoded
    @POST("promofollowupv2/rmfollowfm_promo_sample.php")
    Call<List<Patient>> fm_mpo_promo_followup(
            @Field("id") String mpo_code,
            @Field("user_flag") String user_flag,
            @Field("promo_type") String promo_type,
            @Field("promo_month") String proposed_date2,
            @Field("promo_subtype") String promo_subtype
    );

    @FormUrlEncoded
    @POST("utils/get_master_code_all.php")
    Call<List<Patient>> getMasterCode(
            @Field("id") String mpo_code
    );

    @FormUrlEncoded
    @POST("msd_doc_support/mpo_followup.php")
    Call<List<Patient>> msd_mpo_followup(
            @Field("mpo_code") String mpo_code,
            @Field("user_flag") String user_flag
    );

    @FormUrlEncoded
    @POST("msd_doc_support/msd_program_followup.php")
    Call<List<Patient>> msd_program_followup(
            @Field("mpo_code") String mpo_code,
            @Field("user_flag") String user_flag,
            @Field("month") String month
    );

    @FormUrlEncoded
    @POST("prescription_survey/executive_summary/sm_followup.php")
    Call<List<Patient>> mrd_sm_followup(
            @Field("mpo_code") String mpo_code,
            @Field("user_flag") String user_flag,
            @Field("service_month") String service_month,
            @Field("summary_type") String summary_type
    );

    @FormUrlEncoded
    @POST("prescription_survey/executive_summary/self_followup.php")
    Call<List<Patient>> mrd_self_followup(
            @Field("mpo_code") String mpo_code,
            @Field("self_flag") String self_flag,
            @Field("summary_type") String summary_type,
            @Field("service_month") String service_month
    );

    //mrd_doc_followup
    @FormUrlEncoded
    @POST("prescription_survey/executive_summary/mrd_doc_followup.php")
    Call<List<Patient>> mrd_doc_followup(
            @Field("mpo_code") String user_code,
            @Field("service_month") String service_month,
            @Field("summary_type") String summary_type
    );

    @FormUrlEncoded
    @POST("prescription_survey/executive_summary/mrd_prod_followup.php")
    Call<List<Patient>> mrd_prod_followup(
            @Field("mpo_code") String mpo_code,
            @Field("doc_code") String doc_code,
            @Field("service_month") String service_month,
            @Field("summary_type") String summary_type
    );

    @FormUrlEncoded
    @POST("msd_doc_support/update_status.php")
    Call<Patient> postupdateStatus(
            @Field("selected_service_no") String selected_service_no,
            @Field("selected_service_no_serial") String selected_service_no_serial
    );
}
