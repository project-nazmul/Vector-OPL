package com.opl.pharmavector.network;

import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.MovieModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
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
}
