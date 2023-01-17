package com.opl.pharmavector.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Content-Type: application/x-www-form-urlencoded
public class ApiClient {
    public static String BASE_URL_RM = "http://opsonin.com.bd/dept_order_android_rm2/";
    public static String BASE_URL_AM = "http://opsonin.com.bd/dept_order_android_am2/";
    public static String BASE_URL    = "http://opsonin.com.bd/vector_opl_v1/";
    //public static String BASE_URL =   "http://opsonin.com.bd:83/vector_opl_v1/";
    private static Retrofit retrofit;
    public static Retrofit getApiClient(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.retryOnConnectionFailure(true);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.writeTimeout(60,TimeUnit.SECONDS);

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
