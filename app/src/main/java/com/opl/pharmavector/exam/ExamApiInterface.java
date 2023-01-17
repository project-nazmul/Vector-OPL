package com.opl.pharmavector.exam;


import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.MovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ExamApiInterface {
    @FormUrlEncoded
    @POST("vectorexam/save_exam_result.php")
    Call<List<Patient>> postScore(
            @Field("mpo_code") String mpo_code,
            @Field("brand_code") String brand_code,
            @Field("from_date") String from_date

    );


}
