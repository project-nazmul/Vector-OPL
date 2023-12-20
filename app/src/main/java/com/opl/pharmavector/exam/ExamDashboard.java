package com.opl.pharmavector.exam;

import static com.opl.pharmavector.exam.ExamApi.BASE_URL_EXAM;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.opl.pharmavector.AmDashboard;
import com.opl.pharmavector.AssistantManagerDashboard;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.GMDashboard1;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.pcconference.PcProposal;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RmDashboard;
import com.opl.pharmavector.SalesManagerDashboard;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.VacantMpoPwd;
import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ExamDashboard extends Activity implements View.OnClickListener {
    public String userName_1, userName, userName_2;
    public AutoCompleteTextView actv;
    private DatabaseHandler db;
    public TextView user_show1, user_show2;
    private SessionManager session;
    public int counter;
    public String name;
    public String ename;
    public String mpo_code;
    public String message_3;
    public String new_version;
    public String myexamid;
    public String myexamtime, myexamtimeleft,myexamstarttime,myexamendtime;
    public String user_flag;
    final String mpofetch_exam_flag = BASE_URL_EXAM+"mpo_fetch_exam_flag.json";
    final String amfetch_exam_flag =  BASE_URL_EXAM+"am_fetch_exam_flag.php";
    final String rmfetch_exam_flag =  BASE_URL_EXAM+"rm_fetch_exam_flag.php";
    public String message;
    public String success, mymessage, mysuccess;
    public String exam_flag;
    public TextView textView;
    public Button message_head;
    public LinearLayout l1, l2, mpo_exam;
    String text;
    Button start_exam,back,exam_result;
    ProgressBar bar_1,bar_2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examdashboard);

        initViews();
        switch (user_flag) {
            case "M":
                new mpoFetchExamFlag().execute();
                start_exam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new mpoFetchExamFlag().execute();
                            }
                        });
                }
            });
                bar_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new mpoFetchExamFlag().execute();
                            }
                        });
                    }
                });
                break;
            case "A":
                new amFetchExamFlag().execute();
                break;
            case "R":
                new rmFetchExamFlag().execute();
                break;
        }

        exam_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ArrayList<String> UserName_2 = db.getterritoryname();
                String user = UserName_2.toString();
                Intent i = new Intent(ExamDashboard.this, ExamResultFollowup.class);
                i.putExtra("mpo_code", name);
                i.putExtra("territory_name", user);
                i.putExtra("user_flag", new_version);
                i.putExtra("message_3", message_3);
                i.putExtra("myexamid", myexamid);
                i.putExtra("myexamtime", myexamtime);
                i.putExtra("user_flag", user_flag);
                startActivity(i);
            }
        });
        bar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ArrayList<String> UserName_2 = db.getterritoryname();
                String user = UserName_2.toString();
                Intent i = new Intent(ExamDashboard.this, ExamResultFollowup.class);
                i.putExtra("mpo_code", name);
                i.putExtra("territory_name", user);
                i.putExtra("user_flag", new_version);
                i.putExtra("message_3", message_3);
                i.putExtra("myexamid", myexamid);
                i.putExtra("myexamtime", myexamtime);
                i.putExtra("user_flag", user_flag);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                        finish();
            }
        });
        session = new SessionManager(getApplicationContext());
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        l1 = findViewById(R.id.mpo_doc_l1);
        mpo_exam = findViewById(R.id.mpo_exam);
        l2 = findViewById(R.id.timer_layout);
        textView = findViewById(R.id.textView);
        l2.setVisibility(View.GONE);
        start_exam = findViewById(R.id.start_exam);
        bar_1 = findViewById(R.id.progressBar1);
        exam_result = findViewById(R.id.exam_result);
        bar_2 = findViewById(R.id.progressBar2);
        message_head = findViewById(R.id.message_head);
        back = findViewById(R.id.back);
        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        user_show1 = findViewById(R.id.user_show1);
        Bundle b = getIntent().getExtras();
        assert b != null;
        name = b.getString("mpo_code");
        ename = b.getString("territory_name");
        mpo_code = b.getString("mpo_code");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
        myexamid = b.getString("myexamid");
        myexamtime = b.getString("myexamtime");
        myexamtimeleft = b.getString("myexamtime");
        user_flag = b.getString("user_flag");
        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        db = new DatabaseHandler(this);
        ArrayList<String> mpo_code_interna = db.getterritoryname();
        String mpo_code_i = mpo_code_interna.toString();
        user_show1.setText(name + " " + ename + " ");
    }

    @SuppressLint("StaticFieldLeak")
    class mpoFetchExamFlag extends AsyncTask<Void, Void, Void> {
        final JSONParser jsonParser = new JSONParser();
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ExamDashboard.this, "Preparing your Quiz", "Wait....", true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            params.add(new BasicNameValuePair("id", name));
            mymessage = "0";
            myexamtimeleft = "0";
            JSONObject json = jsonParser.makeHttpRequest(mpofetch_exam_flag, "POST", params);

            if (json != null) {
                try {
                    mysuccess = json.getString("mysuccess");
                    mymessage = json.getString("mymessage");
                    myexamid = json.getString("myexamid");
                    myexamtime = json.getString("myexamtime");
                    myexamtimeleft = json.getString("myexamtime");
                    myexamstarttime = json.getString("starttime");
                    myexamendtime = json.getString("endtime");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            exam_flag = mysuccess;

            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf= new SimpleDateFormat("hh:mm a");
                    String currentitme = sdf.format(new Date());

                 if (exam_flag.equals("Y")){
                     try{
                         Date date1 = sdf.parse(currentitme);
                         Date date2 = sdf.parse(myexamstarttime);
                         Date date3 = sdf.parse(myexamendtime);
                         System.out.println(date1.compareTo(date2));
                         int output = date1.compareTo(date2);
                         System.out.println(output);

                         if (output < 0){
                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     message_head.setVisibility(View.GONE);
                                     l2.setVisibility(View.VISIBLE);
                                     textView.setVisibility(View.VISIBLE);
                                     textView.setText("Exam will Start at "+ myexamstarttime);
                                 }
                             });
                         } else if (output == 0) {
                             ArrayList<String> UserName_2 = db.getterritoryname();
                             String user = UserName_2.toString();
                             Intent i = new Intent(ExamDashboard.this, QuizActivity.class);
                             i.putExtra("mpo_code", mpo_code);
                             i.putExtra("territory_name", user);
                             i.putExtra("user_flag", new_version);
                             i.putExtra("message_3", message_3);
                             i.putExtra("myexamid", myexamid);
                             i.putExtra("myexamtime", myexamtime);
                             i.putExtra("myexamtimeleft", myexamtime);
                             i.putExtra("user_flag", user_flag);
                             startActivity(i);
                         } else {
                             int output2 = date1.compareTo(date3);
                             Log.e("output2==>", String.valueOf(output2));

                             if(output2<0){
                                 ArrayList<String> UserName_2 = db.getterritoryname();
                                 String user = UserName_2.toString();
                                 Intent i = new Intent(ExamDashboard.this, QuizActivity.class);
                                 i.putExtra("mpo_code", mpo_code);
                                 i.putExtra("territory_name", user);
                                 i.putExtra("user_flag", new_version);
                                 i.putExtra("message_3", message_3);
                                 i.putExtra("myexamid", myexamid);
                                 i.putExtra("myexamtime", myexamtime);
                                 i.putExtra("myexamtimeleft", myexamtime);
                                 i.putExtra("user_flag", user_flag);
                                 startActivity(i);
                             } else {
                                 runOnUiThread(new Runnable() {
                                     @SuppressLint("SetTextI18n")
                                     @Override
                                     public void run() {
                                         l2.setVisibility(View.VISIBLE);
                                         textView.setVisibility(View.VISIBLE);
                                         Log.e("you are late==>","Exam is over");
                                         textView.setText("Exam was started at\t"+ myexamstarttime + "\tand ended at\t "+ myexamendtime+" Today");
                                     }
                                 });
                             }
                         }
                     } catch (ParseException e) {
                         Log.e("error-->", String.valueOf(e));
                     }
                 }else{
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             message_head.setVisibility(View.GONE);
                             l2.setVisibility(View.VISIBLE);
                             textView.setText("No exam is Scheduled");
                         }
                     });
                  }
                }
            });
            backthred.start();
        }
    }

    class amFetchExamFlag extends AsyncTask<Void, Void, Void> {
        final JSONParser jsonParser = new JSONParser();
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ExamDashboard.this, "Preparing your Quiz", "Wait....", true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            params.add(new BasicNameValuePair("id", name));
            mymessage = "0";
            myexamtimeleft = "0";
            JSONObject json = jsonParser.makeHttpRequest(amfetch_exam_flag, "POST", params);

            if (json != null) {
                try {
                    mysuccess = json.getString("mysuccess");
                    mymessage = json.getString("mymessage");
                    myexamid = json.getString("myexamid");
                    myexamtime = json.getString("myexamtime");
                    myexamtimeleft = json.getString("myexamtimeleft");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            exam_flag = mysuccess;

            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        switch (exam_flag) {
                            case "Y":
                            case "R": {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(ExamDashboard.this, QuizActivity.class);
                                i.putExtra("mpo_code", mpo_code);
                                i.putExtra("territory_name", user);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
                                i.putExtra("myexamid", myexamid);
                                i.putExtra("myexamtime", myexamtime);
                                i.putExtra("myexamtimeleft", myexamtimeleft);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);
                                break;
                            }
                            case "F":
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void run() {
                                        message_head.setVisibility(View.GONE);
                                        l2.setVisibility(View.VISIBLE);
                                        textView.setText("Exam Time is Over. You can't enroll in the Exam");
                                    }
                                });
                                break;
                            case "P":
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                                        //AlertDialog.Builder builder = new AlertDialog.Builder(GMDashboard1.this, R.style.Theme_Design_BottomSheetDialog);
                                        builder.setTitle("Currently No exam !").setMessage("Exam is not Scheduled")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Thread server = new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {}
                                                        });
                                                        server.start();
                                                    }
                                                })
                                                .show();
                                    }
                                });
                                break;
                            case "L":
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                                        //AlertDialog.Builder builder = new AlertDialog.Builder(GMDashboard1.this, R.style.Theme_Design_BottomSheetDialog);
                                        builder.setTitle("You can not enroll !").setMessage("You have already given this exam.")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Thread server = new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                            }
                                                        });
                                                        server.start();
                                                    }
                                                })
                                                .show();
                                    }
                                });
                                message_head.setVisibility(View.GONE);
                                l2.setVisibility(View.VISIBLE);
                                textView.setText("You can not enroll !\nYou have already given this exam.");
                                break;

                            default:
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int number = Integer.parseInt(mymessage.toString());
                                        if (number > 0) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ExamDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                                            builder.setTitle("Please Wait !").setMessage("Exam will start within\n" + mymessage + "\nMinutes")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Thread server = new Thread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                }
                                                            });
                                                            server.start();
                                                        }
                                                    })
                                                    .show();
                                            int mycountdown_timer = (Integer.parseInt(mymessage));

                                            new CountDownTimer(mycountdown_timer * 60000, 1000) {
                                                @SuppressLint("SetTextI18n")
                                                public void onTick(long millisUntilFinished) {
                                                    l2.setVisibility(View.VISIBLE);
                                                    text = String.format(Locale.getDefault(), "Time Remaining %02d : %02d ",
                                                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                                                    textView.setText(text);
                                                    counter++;
                                                }

                                                public void onFinish() {
                                                    ArrayList<String> UserName_2 = db.getterritoryname();
                                                    String user = UserName_2.toString();
                                                    Intent i = new Intent(ExamDashboard.this, QuizActivity.class);
                                                    i.putExtra("mpo_code", mpo_code);
                                                    i.putExtra("territory_name", user);
                                                    i.putExtra("user_flag", new_version);
                                                    i.putExtra("message_3", message_3);
                                                    i.putExtra("myexamid", myexamid);
                                                    i.putExtra("myexamtime", myexamtime);
                                                    i.putExtra("myexamtimeleft", myexamtimeleft);
                                                    i.putExtra("user_flag", user_flag);
                                                    startActivity(i);
                                                }
                                            }.start();
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ExamDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                                            builder.setTitle("You are late !").setMessage("Exam already Started\n")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Thread server = new Thread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                }
                                                            });
                                                            server.start();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                });
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            backthred.start();
        }
    }

    @SuppressLint("StaticFieldLeak")
    class rmFetchExamFlag extends AsyncTask<Void, Void, Void> {
        final JSONParser jsonParser = new JSONParser();
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ExamDashboard.this, "Preparing your Quiz", "Wait....", true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            params.add(new BasicNameValuePair("id", name));
            mymessage = "0";
            myexamtimeleft = "0";
            JSONObject json = jsonParser.makeHttpRequest(rmfetch_exam_flag, "POST", params);

            if (json != null) {
                try {
                    mysuccess = json.getString("mysuccess");
                    mymessage = json.getString("mymessage");
                    myexamid = json.getString("myexamid");
                    myexamtime = json.getString("myexamtime");
                    myexamtimeleft = json.getString("myexamtimeleft");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            exam_flag = mysuccess;

            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        switch (exam_flag) {
                            case "Y":
                            case "R": {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(ExamDashboard.this, QuizActivity.class);
                                i.putExtra("mpo_code", mpo_code);
                                i.putExtra("territory_name", user);
                                i.putExtra("user_flag", new_version);
                                i.putExtra("message_3", message_3);
                                i.putExtra("myexamid", myexamid);
                                i.putExtra("myexamtime", myexamtime);
                                i.putExtra("myexamtimeleft", myexamtimeleft);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);
                                break;
                            }
                            case "F":
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void run() {
                                        message_head.setVisibility(View.GONE);
                                        l2.setVisibility(View.VISIBLE);
                                        textView.setText("Exam Time is Over. You can't enroll in the Exam");
                                    }
                                });
                                break;
                            case "P":
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                                        //AlertDialog.Builder builder = new AlertDialog.Builder(GMDashboard1.this, R.style.Theme_Design_BottomSheetDialog);
                                        builder.setTitle("Currently No exam !").setMessage("Exam is not Scheduled")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Thread server = new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                            }
                                                        });
                                                        server.start();
                                                    }
                                                })
                                                .show();
                                    }
                                });
                                break;
                            case "L":
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                                        //AlertDialog.Builder builder = new AlertDialog.Builder(GMDashboard1.this, R.style.Theme_Design_BottomSheetDialog);
                                        builder.setTitle("You can not enroll !").setMessage("You have already given this exam.")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Thread server = new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                            }
                                                        });
                                                        server.start();
                                                    }
                                                })
                                                .show();
                                    }
                                });
                                message_head.setVisibility(View.GONE);
                                l2.setVisibility(View.VISIBLE);
                                textView.setText("You can not enroll !\nYou have already given this exam.");
                                break;

                            default:
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int number = Integer.parseInt(mymessage.toString());
                                        if (number > 0) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ExamDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                                            builder.setTitle("Please Wait !").setMessage("Exam will start within\n" + mymessage + "\nMinutes")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Thread server = new Thread(new Runnable() {
                                                                @Override
                                                                public void run() {

                                                                }
                                                            });
                                                            server.start();
                                                        }
                                                    })
                                                    .show();
                                            int mycountdown_timer = (Integer.parseInt(mymessage));

                                            new CountDownTimer(mycountdown_timer * 60000, 1000) {
                                                @SuppressLint("SetTextI18n")
                                                public void onTick(long millisUntilFinished) {
                                                    l2.setVisibility(View.VISIBLE);
                                                    text = String.format(Locale.getDefault(), "Time Remaining %02d : %02d ",
                                                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                                                    textView.setText(text);
                                                    counter++;
                                                }

                                                public void onFinish() {
                                                    ArrayList<String> UserName_2 = db.getterritoryname();
                                                    String user = UserName_2.toString();
                                                    Intent i = new Intent(ExamDashboard.this, QuizActivity.class);
                                                    i.putExtra("mpo_code", mpo_code);
                                                    i.putExtra("territory_name", user);
                                                    i.putExtra("user_flag", new_version);
                                                    i.putExtra("message_3", message_3);
                                                    i.putExtra("myexamid", myexamid);
                                                    i.putExtra("myexamtime", myexamtime);
                                                    i.putExtra("myexamtimeleft", myexamtimeleft);
                                                    i.putExtra("user_flag", user_flag);
                                                    startActivity(i);
                                                }
                                            }.start();
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ExamDashboard.this, R.style.Theme_Design_BottomSheetDialog);
                                            builder.setTitle("You are late !").setMessage("Exam already Started\n")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Thread server = new Thread(new Runnable() {
                                                                @Override
                                                                public void run() {

                                                                }
                                                            });
                                                            server.start();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }
                                });
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            backthred.start();
        }
    }

    private void logoutUser() {
        session.setLogin(false);
        session.invalidate();

        Intent intent = new Intent(ExamDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {}
}




