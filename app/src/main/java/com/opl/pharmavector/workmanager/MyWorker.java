package com.opl.pharmavector.workmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.HttpHandler;
import com.opl.pharmavector.Offlinereport;
import com.opl.pharmavector.Ordmain;
import com.opl.pharmavector.Product;
import com.opl.pharmavector.R;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionEntry;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {

        //Log.e("workManager,",Dashboard.track_lang+"--"+Dashboard.track_lat);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.getExamflag(Dashboard.globalmpocode);
        call.enqueue(new Callback<List<Patient>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Patient>> call, retrofit2.Response<List<Patient>> response) {
                List<Patient> patientdetail = response.body();
                assert patientdetail != null;
                String exam_flag = patientdetail.get(0).getFirst_name();
                if (exam_flag.equals("N")){
                    displayNotification("Exam Schedule: \t"+ Dashboard.globalmpocode, "You have No Exam");
                }else{
                    displayNotification("Exam Schedule: \t"+ Dashboard.globalmpocode, "You have an Exam Today");
                }
            }
            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
            }
        });

        return Result.success();
    }


    private void displayNotification(String title, String task) {

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           // NotificationChannel channel = new NotificationChannel("vector", "Exam", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel = new NotificationChannel("vectorexam", "vectorexam", IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "vectorexam")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);
        notificationManager.notify(2, notification.build());

    }



}


