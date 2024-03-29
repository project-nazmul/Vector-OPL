package com.opl.pharmavector.remote;

import com.opl.pharmavector.RecyclerData;
import com.opl.pharmavector.achieve.AchieveEarnModel;
import com.opl.pharmavector.chemistList.model.ChemistModel;
import com.opl.pharmavector.chemistList.model.ContactModel;
import com.opl.pharmavector.incentive.IncentiveDataModel;
import com.opl.pharmavector.incentive.IncentiveDsgModel;
import com.opl.pharmavector.incentive.IncentiveTypeModel;
import com.opl.pharmavector.achieve.AchvMonthModel;
import com.opl.pharmavector.incentive.IncentiveQtrModel;
import com.opl.pharmavector.incentive.IncentiveTeamModel;
import com.opl.pharmavector.amdashboard.VacantModel;
import com.opl.pharmavector.dcfpFollowup.DcfpDocTotDateModel;
import com.opl.pharmavector.dcfpFollowup.DcfpDoctorMpoModel;
import com.opl.pharmavector.dcfpFollowup.DcfpDoctorReportModel;
import com.opl.pharmavector.dcfpFollowup.DcfpEntrySetUpModel;
import com.opl.pharmavector.dcfpFollowup.DcfpFollowMpoModel;
import com.opl.pharmavector.dcfpFollowup.DcrFollowupModel;
import com.opl.pharmavector.dcfpFollowup.DcfpEntryDoctorModel;
import com.opl.pharmavector.dcfpFollowup.DoctorReachSelfModel;
import com.opl.pharmavector.dcfpFollowup.MpoDcfpFollowModel;
import com.opl.pharmavector.doctorList.model.DoctorFFModel;
import com.opl.pharmavector.doctorList.model.DoctorModel;
import com.opl.pharmavector.liveDepot.AdsStockDetailModel;
import com.opl.pharmavector.liveDepot.AdsStockInfoModel;
import com.opl.pharmavector.liveDepot.LiveDepotStockModel;
import com.opl.pharmavector.liveDepot.LiveStokeDepotModel;
import com.opl.pharmavector.master_code.model.MasterModel;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.mpoMenu.MPOMenuModel;
import com.opl.pharmavector.mpodcr.DcfpModel;
import com.opl.pharmavector.mpodcr.DcfpStatusModel;
import com.opl.pharmavector.mpodcr.entry.DcrLocationModel;
import com.opl.pharmavector.mpodcr.entry.DcrNatureModel;
import com.opl.pharmavector.mpodcr.entry.DcrSubmitModel;
import com.opl.pharmavector.mrd_pres_report.SPIReportModel;
import com.opl.pharmavector.msd_doc_support.adapter.MSDApprovalModel;
import com.opl.pharmavector.msd_doc_support.adapter.MSDCommitmentModel;
import com.opl.pharmavector.msd_doc_support.adapter.MSDSubmitModel;
import com.opl.pharmavector.prescriptionsurvey.RxSumMISSelfModel;
import com.opl.pharmavector.productOffer.ProductOfferModel;
import com.opl.pharmavector.personalExpense.model.MotorCycleModel;
import com.opl.pharmavector.pmdVector.model.BrandModel;
import com.opl.pharmavector.pmdVector.model.CompanyModel;
import com.opl.pharmavector.pmdVector.model.FFTeamModel;
import com.opl.pharmavector.pmdVector.model.ProductModel;
import com.opl.pharmavector.pmdVector.model.RXModel;
import com.opl.pharmavector.pmdVector.model.RegionUnitModel;
import com.opl.pharmavector.pmdVector.model.RegionValModel;
import com.opl.pharmavector.prescriber.FieldForceModel;
import com.opl.pharmavector.prescriber.FromDateModel;
import com.opl.pharmavector.prescriber.GenericTypeModel;
import com.opl.pharmavector.prescriber.TopPrescriberModel;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.MovieModel;
import com.opl.pharmavector.prescriptionsurvey.rx_model;
import com.opl.pharmavector.report.LocationReportModel;
import com.opl.pharmavector.saleReport.GroupOrdSumDetailModel;
import com.opl.pharmavector.saleReport.GroupOrdSummaryModel;
import com.opl.pharmavector.tourPlan.TReviewDetailModel;
import com.opl.pharmavector.tourPlan.TReviewMonModel;
import com.opl.pharmavector.tourPlan.TUpdateDetailModel;
import com.opl.pharmavector.tourPlan.TourClassModel;
import com.opl.pharmavector.tourPlan.TourModeModel;
import com.opl.pharmavector.tourPlan.TourMonthModel;
import com.opl.pharmavector.tourPlan.TourMorningModel;
import com.opl.pharmavector.tourPlan.TourNatureModel;
import com.opl.pharmavector.tourPlan.TourPlanResponse;
import com.opl.pharmavector.util.PopUpUrlModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
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
            @Field("user_loc") String user_loc,
            @Field("emp_code") String emp_code
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
            @Field("user_loc") String user_loc,
            @Field("os_version") String os_version
    );

    @FormUrlEncoded
    @POST("vector_login/lock_emp_check.php")
    Call<Patient> lock_emp_check(
            @Field("user_code") String user_code,
            @Field("os_version") String os_version
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
    @POST("dcfp/get_mpo_dcfp_list.php")
    Call<MpoDcfpFollowModel> getDcfpMpoFollowupList(
            @Field("id") String id,
            @Field("select_date") String to_date,
            @Field("ts_code") String ts_code
    );

    @FormUrlEncoded
    @POST("get_mpo_list.php")
    Call<DcfpFollowMpoModel> getDcfpFollowMpoList(
            @Field("ff_code") String ff_code
    );

    @FormUrlEncoded
    @POST("get_segment_list.php")
    Call<DcfpFollowMpoModel> getDcfpMpoSegmentList(
            @Field("mpo_code") String mpo_code
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
            @Field("brand_code") String brand_code,
            @Field("search_by") String search_by
    );

    @FormUrlEncoded
    @POST("spi/get_from_month.php")
    Call<FromDateModel> getPrescriberFromDate(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("spi/get_to_month.php")
    Call<FromDateModel> getPrescriberToDate(
            @Field("id") String id,
            @Field("f_date") String f_date
    );

    @FormUrlEncoded
    @POST("spi/get_List.php")
    Call<FieldForceModel> getFieldForceCode(
            @Field("function") String function,
            @Field("manager_code") String manager_code,
            @Field("manager_detail") String manager_detail
    );

    @FormUrlEncoded
    @POST("location_tracker/get_List.php")
    Call<FieldForceModel> getLocationFfCode(
            @Field("function") String function,
            @Field("manager_code") String manager_code,
            @Field("manager_detail") String manager_detail
    );

    @FormUrlEncoded
    @POST("opsonin_product_offer_new.php")
    Call<ProductOfferModel> getProductOfferList(
            @Field("id") String user_code
    );

    @FormUrlEncoded
    @POST("opsonin_product_offer_pmd.php")
    Call<ProductOfferModel> getPMDProductOfferList(
            @Field("id") String user_code
    );

    @FormUrlEncoded
    @POST("location_tracker/get_location.php")
    Call<LocationReportModel> getLocationReportList(
            @Field("p_date") String p_date,
            @Field("ff_code") String ff_code
    );


    @FormUrlEncoded
    @POST("spi/get_generic.php")
    Call<GenericTypeModel> getGenericTypeList(
            @Field("id") String id,
            @Field("f_date") String f_date,
            @Field("t_date") String t_date
    );

    @FormUrlEncoded
    @POST("spi/get_top_prescriber.php")
    Call<TopPrescriberModel> getPrescriberDetailList(
            @Field("id") String id,
            @Field("f_date") String f_date,
            @Field("t_date") String t_date,
            @Field("ff_code") String ff_code,
            @Field("gen_code") String gen_code
    );

    @FormUrlEncoded
    @POST("pmd_vector/ff_contacts/get_ff_contact_info.php")
    Call<ArrayList<RecyclerData>> getFFContactInfo(
            @Field("deignation_type") String deignation_type,
            @Field("ff_code") String ff_code,
            @Field("team_type") String team_type
    );

    @FormUrlEncoded
    @POST("achv_and_earn/get_achv_and_earn.php")
    Call<AchieveEarnModel> getAchievementEarnList(
            @Field("deignation_type") String designation_type,
            @Field("ff_code") String ff_code,
            @Field("team_type") String team_type,
            @Field("p_month") String p_month
    );

    @FormUrlEncoded
    @POST("achv_and_earn/get_achv_and_earn_self.php")
    Call<AchieveEarnModel> getAchieveEarnSelfList(
            @Field("ff_code") String ff_code,
            @Field("p_month") String p_month
    );

    @FormUrlEncoded
    @POST("achv_and_earn/get_achv_and_earn_self.php")
    Call<AchieveEarnModel> getADAchieveEarnSelfList(
            @Field("ff_code") String ff_code,
            @Field("p_month") String p_month,
            @Field("team_type_ad") String team_type_ad
    );

    @FormUrlEncoded
    @POST("achv_and_earn/ff_contacts/get_ff_contact_info.php")
    Call<ArrayList<RecyclerData>> getAchvEarnFFContact(
            @Field("deignation_type") String deignation_type,
            @Field("ff_code") String ff_code,
            @Field("team_type") String team_type
    );

    @FormUrlEncoded
    @POST("mpodcr/get_dcfp_flag.php")
    Call<DcfpStatusModel> getDcfpStatusFlag(@Field("id") String id);

    @FormUrlEncoded
    @POST("menu/get_menu_refresh.php")
    Call<DcfpStatusModel> getMpoRefreshMenu(@Field("user_code") String user_code, @Field("emp_code") String emp_code, @Field("ff_role") String ff_role);

    @FormUrlEncoded
    @POST("menu/get_menu.php")
    Call<MPOMenuModel> getMpoDashMenuList(@Field("user_code") String user_code, @Field("emp_code") String emp_code, @Field("ff_role") String ff_role);

    @FormUrlEncoded
    @POST("tour_plan_observation/get_tour_nature.php")
    Call<DcrNatureModel> getDcrTourNature(@Field("id") String id, @Field("p_date") String p_date);

    @FormUrlEncoded
    @POST("tour_plan_observation/get_location.php")
    Call<DcrLocationModel> getDcrTourLocation(@Field("id") String id, @Field("p_date") String p_date, @Field("p_shift") String p_shift);

    @FormUrlEncoded
    @POST("tour_plan_observation/submit_observation.php")
    Call<DcrSubmitModel> dcrTourPlanSubmit(@Field("id") String id, @Field("emp_code") String emp_code, @Field("visited_mpo_code") String mpo_code,
                                           @Field("visit_date") String visit_date, @Field("tour_nature") String tour_nature, @Field("doc_code") String doc_code,
                                           @Field("dcr_type") String dcr_type, @Field("start_time") String start_time, @Field("end_time") String end_time,
                                           @Field("shift") String shift, @Field("competitor_analysis") String competitor_analysis, @Field("observation") String observation,
                                           @Field("visit_with") String visit_with, @Field("doc_chem_flag") String doc_chem_flag);

    @GET("get_team.php")
    Call<FFTeamModel> getFFTeamList();

    @GET("get_team_new.php")
    Call<FFTeamModel> getAchieveFFTeamList();

    @GET("incentive/get_quater.php")
    Call<IncentiveQtrModel> getIncentiveQuarterList();

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("get_team_new.php")
    Call<FFTeamModel> getPmdAchieveTeamList(@Field("id") String userId);

    @GET("spi/get_from_month.php")
    Call<FromDateModel> getSpiReportMonth();

    //@GET("achv_and_earn/get_mon.php")
    @GET("spi/get_from_month.php")
    Call<FromDateModel> getAchievementMonth();

    @GET("achv_and_earn/get_mon.php")
    Call<AchvMonthModel> getAchievementMonths();

    @GET("incentive/get_incentive_type.php")
    Call<IncentiveTypeModel> getAchievementIncentive();

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("incentive/get_incentive.php")
    Call<IncentiveDataModel> getIncentiveDataList(@Field("yr") String year,
                                                  @Field("id") String emp_code,
                                                  @Field("qtr") String incentive_qtr,
                                                  @Field("title_code") String dsg_code,
                                                  @Field("incentive_type") String incentive_type);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("common/get_all_team.php")
    Call<IncentiveTeamModel> getIncentiveTeamList(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("promotion/promotion_check.php")
    Call<PopUpUrlModel> getPopUpFlagCheck(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("prescription_survey/mis/rx_summary_self_followup.php")
    Call<RxSumMISSelfModel> getRxSumMISSelfList(@Field("id") String id,
                                                @Field("brand_code") String brand_code,
                                                @Field("f_date") String f_date,
                                                @Field("t_date") String t_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("depot_reports/get_depots.php")
    Call<LiveStokeDepotModel> getLiveStockDepotList(@Field("id") String id);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_tour_nature.php")
    Call<TourNatureModel> getDailyTourNatureList(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_tour_mode.php")
    Call<TourModeModel> getDailyTourModeList(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_tour_class.php")
    Call<TourClassModel> getDailyTourClassList(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/tour_plan_submit.php")
    Call<TourPlanResponse> submitDailyTourPlanEntry(@Field("id") String user_code, @Field("emp_code") String emp_code, @Field("tour_date") String tour_date,
            @Field("objective") String objective, @Field("remarks") String remarks, @Field("LOCATION_FROM") String location_from,
            @Field("LOCATION_TO") String location_to, @Field("TN_CODE") String tn_code, @Field("TM_CODE") String tm_code,
            @Field("TMC_CODE") String tmc_code, @Field("FROM_H") String from_hour, @Field("FROM_M") String from_min,
            @Field("FROM_AM") String from_am, @Field("TO_H") String to_hour, @Field("TO_M") String to_min, @Field("TO_AM") String to_am);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/tour_plan_update.php")
    Call<TourPlanResponse> submitDailyTourPlanUpdate(@Field("id") String user_code, @Field("emp_code") String emp_code, @Field("tour_date") String tour_date,
                                                    @Field("objective") String objective, @Field("remarks") String remarks, @Field("LOCATION_FROM") String location_from,
                                                    @Field("LOCATION_TO") String location_to, @Field("TN_CODE") String tn_code, @Field("TM_CODE") String tm_code,
                                                    @Field("TMC_CODE") String tmc_code, @Field("FROM_H") String from_hour, @Field("FROM_M") String from_min,
                                                    @Field("FROM_AM") String from_am, @Field("TO_H") String to_hour, @Field("TO_M") String to_min, @Field("TO_AM") String to_am);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_tour_month.php")
    Call<TourMonthModel> getDailyTourMonthList(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_tour_day.php")
    Call<TourMonthModel> getTourUpdateMonthList(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_tour_upd_details.php")
    Call<TUpdateDetailModel> getTourUpdateDetailList(@Field("id") String user_code, @Field("emp_code") String emp_code, @Field("tour_date") String tour_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_tour_location_from.php")
    Call<TourMorningModel> getDailyTourMorningList(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_tour_location_to.php")
    Call<TourMorningModel> getDailyTourEveningList(@Field("id") String user_code, @Field("location_from") String location_from);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("depot_reports/stock_view.php")
    Call<LiveDepotStockModel> getLiveDepotStockList(@Field("depot_code") String depot_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("ads/ads_product_stock.php")
    Call<AdsStockInfoModel> getAdsStockInfoLists(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("ads/ads_product_stock_pmd.php")
    Call<AdsStockInfoModel> getAdsStockPMDLists(@Field("id") String user_code, @Field("p_code") String p_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("get_product_followup.php")
    Call<AdsStockInfoModel> getAdsStockProductList(@Field("id") String user_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("ads/ads_product_depot_stock_detail.php")
    Call<AdsStockDetailModel> getAdsStockDetailLists(@Field("id") String user_code, @Field("p_code") String p_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("prescription_survey/mis/rx_summary_details_followup.php")
    Call<RxSumMISSelfModel> getRxSumMISDetailList(@Field("id") String id,
                                                @Field("brand_code") String brand_code,
                                                @Field("f_date") String f_date,
                                                @Field("t_date") String t_date);

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
    @POST("common/customer_contact_update.php")
    Call<ContactModel> updateChemistNumber(@Field("id") String user_code, @Field("emp_code") String emp_code, @Field("cust_code") String cust_code,
                                           @Field("MOBILE_NO_1") String MOBILE_NO_1, @Field("MOBILE_NO_2") String MOBILE_NO_2);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("dcfp/get_mpo_dcfp_summary.php")
    Call<DcfpDoctorReportModel> getDcfpDoctorList(@Field("id") String mpo_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("dcfp/get_dcfp_tot_day.php")
    Call<DcfpDocTotDateModel> getDcfpSelectTotDay(@Field("id") String mpo_code, @Field("p_date") String p_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("common/get_mpo_list.php")
    Call<DcfpDoctorMpoModel> getDcfpDocMpoList(@Field("id") String mpo_code);

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
    @POST("tour_plan_observation/tour_self_followup.php")
    Call<List<DcrFollowupModel>> getTourSelfFollowup(@Field("id") String id, @Field("t_date") String t_date, @Field("f_date") String f_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan_observation/tour_followup.php")
    Call<List<DcrFollowupModel>> getTourDetailFollowup(@Field("id") String id, @Field("self_flag") String self_flag, @Field("t_date") String t_date, @Field("f_date") String f_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan_observation/tour_role_wise_followup.php")
    Call<List<DcrFollowupModel>> getTourRoleWiseFollowup(@Field("id") String id, @Field("t_date") String t_date, @Field("f_date") String f_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan_observation/tour_child_followup.php")
    Call<List<DcrFollowupModel>> getTourChildWiseFollowup(@Field("id") String id, @Field("t_date") String t_date, @Field("f_date") String f_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("get_mrd_doctor.php")
    Call<DoctorModel> getDoctorDetailsList(@Field("ff_code") String ff_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("common/get_customer.php")
    Call<ChemistModel> getChemistDetailsList(@Field("ff_code") String ff_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_entry_month.php")
    Call<TReviewMonModel> getTourReviewMonthList(@Field("id") String emp_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("tour_plan/get_plan_details.php")
    Call<TReviewDetailModel> getTourReviewDetailList(@Field("id") String emp_code, @Field("p_mon") String month);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("incentive/get_designation.php")
    Call<IncentiveDsgModel> getIncentiveDesignation(@Field("id") String emp_code);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("order_summary/GroupwiseProductOrderSummary.php")
    Call<GroupOrdSummaryModel> getGroupProdOrderSummary(@Field("id") String emp_code, @Field("from_date") String from_date, @Field("to_date") String to_date);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("order_summary/GroupwiseProductOrderSummaryDetails.php")
    Call<GroupOrdSumDetailModel> getGroupOrderSummaryDetail(@Field("id") String emp_code, @Field("from_date") String from_date, @Field("to_date") String to_date, @Field("p_code") String p_code);

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
    @POST("utils/get_master_code_v1.php")
    Call<MasterModel> getNewMasterCode(
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
    //@POST("spi/sm_followup.php")
    @POST("spi/spi_ff_followup.php")
    Call<SPIReportModel> mrd_sm_followup(
            @Field("mpo_code") String mpo_code,
            @Field("user_flag") String user_flag,
            @Field("service_month") String service_month,
            @Field("summary_type") String summary_type
    );

    @FormUrlEncoded
    @POST("spi/doc_reach_details_followup.php")
    Call<DoctorReachSelfModel> doc_reach_details_followup(
            @Field("id") String mpo_code,
            @Field("p_mon") String p_month
    );

    @FormUrlEncoded
    @POST("spi/doc_reach_details_followup.php")
    Call<DoctorReachSelfModel> doc_reach_details_sm_follow(
            @Field("id") String mpo_code,
            @Field("p_mon") String p_month,
            @Field("ff_type") String ff_type
    );

    @FormUrlEncoded
    //@POST("spi/self_followup.php")
    @POST("spi/spi_ff_self_followup.php")
    Call<SPIReportModel> mrd_self_followup(
            @Field("mpo_code") String mpo_code,
            @Field("self_flag") String self_flag,
            @Field("summary_type") String summary_type,
            @Field("service_month") String service_month
    );

    @FormUrlEncoded
    @POST("spi/doc_reach_self_followup.php")
    Call<DoctorReachSelfModel> doc_reach_self_followup(
            @Field("id") String mpo_code,
            @Field("p_mon") String p_month
    );

    @FormUrlEncoded
    @POST("spi/doc_reach_team_followup.php")
    Call<DoctorReachSelfModel> doc_reach_team_followup(
            @Field("id") String mpo_code,
            @Field("p_mon") String p_month
    );

    //mrd_doc_followup
    @FormUrlEncoded
    @POST("spi/spi_doc_followup.php")
    Call<List<Patient>> mrd_doc_followup(
            @Field("mpo_code") String user_code,
            @Field("service_month") String service_month,
            @Field("summary_type") String summary_type
    );

    @FormUrlEncoded
    @POST("spi/spi_mpo_doc_followup.php")
    Call<List<Patient>> mrd_mpo_doc_followup(
            @Field("mpo_code") String user_code,
            @Field("service_month") String service_month,
            @Field("summary_type") String summary_type
    );

    @FormUrlEncoded
    @POST("spi/spi_prod_followup.php")
    Call<List<Patient>> mrd_prod_followup(
            @Field("mpo_code") String mpo_code,
            @Field("doc_code") String doc_code,
            @Field("service_month") String service_month,
            @Field("summary_type") String summary_type
    );

    @FormUrlEncoded
    @POST("spi/spi_mpo_prod_followup.php")
    Call<List<Patient>> mrd_mpo_prod_followup(
            @Field("mpo_code") String mpo_code,
            @Field("doc_code") String doc_code,
            @Field("service_month") String service_month,
            @Field("summary_type") String summary_type
    );

    @FormUrlEncoded
    @POST("spi/update_status.php")
    Call<Patient> postupdateStatus(
            @Field("selected_service_no") String selected_service_no,
            @Field("selected_service_no_serial") String selected_service_no_serial
    );
}
