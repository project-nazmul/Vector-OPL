package com.opl.pharmavector.exam;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExamApi {
    public static final String BASE_URL_EXAM = "http://opsonin.com.bd:83/vectorexam/";
    //private static final String BASE_URL = "http://opsonin.com.bd:83/vectorexam/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_EXAM)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .build())
                    .build();
        }
        return retrofit;
    }
}

