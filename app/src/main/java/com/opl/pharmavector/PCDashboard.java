//PCDashboard

package com.opl.pharmavector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nativecss.NativeCSS;
import com.opl.pharmavector.pcconference.PcApproval;
import com.opl.pharmavector.pcconference.PcConferenceFollowup;
import com.opl.pharmavector.pcconference.PcProposal;
import com.opl.pharmavector.pcconference.RMPCPermission;
import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.ProgressDialog;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import static com.android.volley.VolleyLog.TAG;
import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;
import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

public class PCDashboard extends Activity implements View.OnClickListener {

    public String userName_1, userName, userName_2, UserName_2;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public AutoCompleteTextView actv;
    private ListView lv, lv2;
    private ProgressDialog pDialog;
    private DatabaseHandler db;
    private String TAG = Offlinereport.class.getSimpleName();
    ArrayList<HashMap<String, String>> productList;
    ArrayList<HashMap<String, String>> customerlist;
    private Button logout;
    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;
    public TextView user_show1, user_show2;
    private SessionManager session;
    public String get_ext_dt, date_flag, check_flag;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage, pc_pending_noti, mpo_pc_pending_noti;
    ProgressDialog pDialog2;
    public int pc_pending_rm_count;
    private String pc_conference_date_check = BASE_URL+"pc_pending_count.php";
    public String user_flag;
    public ProgressBar bar_10, bar_16;
    LinearLayout mpo_layout, rm_layout, pc_approval, mpo_pc_followup, pc_conference_followup_admin;
    LinearLayout pc_bill_mpo_layout,pc_bill_approval, pc_bill_followup_admin_layout, mpo_pc_pending_lay, pc_pending;
    private ArrayList<Customer> checkdatelist;
    Button pc_conference_btn, pc_conference_followup_btn, pc_conference_followup1, rm_pc_req_btn, pc_approval_btn,
            pc_bill_btn, pc_bill_followup, pc_bill_approval_btnButton, pc_bill_admin_followup, pc_pending_permission,
            mpo_pc_pending_btn, back;
    ProgressBar bar_11, bar_12, bar_3, bar_5, bar_6, bar_7, bar_8, bar_9, bar_21;
    String new_version, mpo_code_i;
    ArrayList<String> mpo_code_interna;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcdashboard);

        initViews();
        logoutbtnEvent();



        pc_bill_admin_followup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", user_flag);

                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PCBillFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                Log.w("PcConferenceManager", userName + "---" + UserName_2 + user_flag);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PCBillFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });


        pc_bill_approval_btnButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                // Intent i = new Intent(PCDashboard.this, PcConferenceFollowup.class);
                                Intent i = new Intent(PCDashboard.this, PcBillApproval.class);

                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PCDashboard.this, PcBillApproval.class);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });

        pc_bill_followup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(PCDashboard.this, PCBillFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PCBillFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(PCDashboard.this, PCBillFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(PCDashboard.this, PCBillFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });


        pc_bill_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PCBill.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                startActivity(i);

                            } else {


                                Intent i = new Intent(PCDashboard.this, PCBill.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);


                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });


        pc_approval_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                // Intent i = new Intent(PCDashboard.this, PcConferenceFollowup.class);
                                Intent i = new Intent(PCDashboard.this, PcApproval.class);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                i.putExtra("userName", userName);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                startActivity(i);

                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(PCDashboard.this, PcApproval.class);
                                i.putExtra("userName", userName);
                                i.putExtra("UserName_2", user);
                                i.putExtra("new_version", new_version);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });


        mpo_pc_pending_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {
                                Intent i = new Intent(PCDashboard.this, PCPendingProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_16.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {
                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("Passed to Readco", userName + "---" + UserName_2 + new_version);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PCPendingProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });

        pc_pending_permission.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                Log.w("RMPCPendingPermission", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, RMPCPendingPermission.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("RMPCPendingPermission", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {

                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("RMPCPendingPermission", userName + "---" + UserName_2 + new_version);
                                startActivity(i);

                            } else if (pc_pending_rm_count <= 0) {


                                class LooperThread extends Thread {
                                    public Handler mHandler;

                                    public void run() {
                                        Looper.prepare();
                                        mHandler = new Handler() {
                                            public void handleMessage(Message msg) {
                                                Toast.makeText(PCDashboard.this, "You have no Pending PC in your Territory", Toast.LENGTH_SHORT).show();
                                            }
                                        };

                                        Looper.loop();
                                    }
                                }


                            } else {

                                Intent i = new Intent(PCDashboard.this, RMPCPendingPermission.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("RMPCPendingPermission  ", userName + "---" + UserName_2);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();


            }
        });

        rm_pc_req_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, RMPCPermission.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_3.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("Passed to Readco", userName + "---" + UserName_2 + new_version);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, RMPCPermission.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });

        pc_conference_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_11.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("Passed to Readco", userName + "---" + UserName_2 + new_version);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("Passed to Readco", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });


        pc_conference_followup1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", user_flag);
                                Log.w("PCConferencefollowup", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PcConferenceFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                Log.w("PCConferencefollowup", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_21.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                Log.w("PCConferencefollowup", userName + "---" + UserName_2 + new_version);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PcConferenceFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);

                                Log.w("PCConferencefollowup", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });

        pc_conference_followup_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PCDashboard.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("user_flag", user_flag);
                                Log.w("PcConferenceManager", userName + "---" + UserName_2);
                                startActivity(i);

                            } else {

                                Intent i = new Intent(PCDashboard.this, PcConferenceFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                Log.w("PcConferenceManager", userName + "---" + UserName_2 + user_flag);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        bar_12.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            if (!NetInfo.isOnline(getBaseContext())) {


                                Intent i = new Intent(PCDashboard.this, PcProposal.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                Log.w("PcConferenceManager", userName + "---" + UserName_2 + new_version);
                                startActivity(i);

                            } else {


                                Intent i = new Intent(PCDashboard.this, PcConferenceFollowup.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", UserName_2);
                                i.putExtra("new_version", new_version);
                                i.putExtra("user_flag", user_flag);
                                Log.w("PcConferenceManager", userName + "---" + UserName_2);

                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });


    }

    private void logoutbtnEvent() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //  finish();
                        try {
                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String UserName_1 = b.getString("userName_1");
                            String UserName_2 = b.getString("userName_2");
                            user_flag = b.getString("user_flag");

                            switch (user_flag.toString().trim()) {
                                case "M": {
                                    Intent i = new Intent(PCDashboard.this, Dashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("UserName_1", UserName_1);
                                    i.putExtra("UserName_2", Dashboard.globalterritorycode);
                                    i.putExtra("userName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("userName_1", Dashboard.globalmpocode);
                                    i.putExtra("UserName_2", Dashboard.globalterritorycode);
                                    i.putExtra("ff_type", Dashboard.ff_type);
                                    i.putExtra("emp_code", Dashboard.globalempCode);
                                    i.putExtra("emp_name", Dashboard.globalempName);
                                    startActivity(i);
                                    break;
                                }
                                case "A": {
                                    Intent i = new Intent(PCDashboard.this, AmDashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("UserName_1", UserName_1);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("userName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("userName_1", UserName_1);
                                    i.putExtra("userName_2", UserName_2);
                                    Log.d("userName", "UserName" + userName);
                                    Log.d("UserName_1", "UserName_1" + UserName_1);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    startActivity(i);
                                    break;
                                }
                                case "R": {
                                    Intent i = new Intent(PCDashboard.this, RmDashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("UserName_1", UserName_1);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("userName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("userName_1", UserName_1);
                                    i.putExtra("userName_2", UserName_2);
                                    Log.d("userName", "UserName" + userName);
                                    Log.d("UserName_1", "UserName_1" + UserName_1);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    startActivity(i);
                                    break;
                                }
                                case "ASM": {
                                    Intent i = new Intent(PCDashboard.this, AssistantManagerDashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("UserName_1", UserName_1);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("userName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("userName_1", UserName_1);
                                    i.putExtra("userName_2", UserName_2);
                                    Log.d("userName", "UserName" + userName);
                                    Log.d("UserName_1", "UserName_1" + UserName_1);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    startActivity(i);
                                    break;
                                }
                                case "SM": {
                                    Intent i = new Intent(PCDashboard.this, SalesManagerDashboard.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("UserName_1", UserName_1);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("userName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("userName_1", UserName_1);
                                    i.putExtra("userName_2", UserName_2);
                                    Log.d("userName", "UserName" + userName);
                                    Log.d("UserName_1", "UserName_1" + UserName_1);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    startActivity(i);
                                    break;
                                }
                                case "GM": {
                                    Intent i = new Intent(PCDashboard.this, GMDashboard1.class);
                                    i.putExtra("UserName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("UserName_1", UserName_1);
                                    i.putExtra("UserName_2", UserName_2);
                                    i.putExtra("userName", userName);
                                    i.putExtra("new_version", userName);
                                    i.putExtra("userName_1", UserName_1);
                                    i.putExtra("userName_2", UserName_2);
                                    Log.d("userName", "UserName" + userName);
                                    Log.d("UserName_1", "UserName_1" + UserName_1);
                                    Log.d("UserName_2", "UserName_2" + UserName_2);
                                    startActivity(i);
                                    break;
                                }
                            }
                            //finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                backthred.start();

            }
        });


        session = new SessionManager(getApplicationContext());
        logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(PCDashboard.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                builder.setTitle("Exit !").setMessage("Are you sure you want to exit Vector?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread server = new Thread(new Runnable() {

                                    @Override
                                    public void run() {

                                        JSONParser jsonParser = new JSONParser();
                                        List<NameValuePair> params = new ArrayList<NameValuePair>();

                                        params.add(new BasicNameValuePair("logout", "logout"));

                                        JSONObject json = jsonParser.makeHttpRequest(Login.LOGIN_URL, "POST", params);

                                    }
                                });

                                server.start();
                                logoutUser();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("MainActivity", "");
                            }
                        })
                        .show();


            }
        });

    }

    private void gmFunc() {
        pc_conference_followup_admin.setVisibility(View.VISIBLE);
        pc_bill_followup_admin_layout.setVisibility(View.VISIBLE);

        pc_pending.setVisibility(View.GONE);
        mpo_layout.setVisibility(View.GONE);
        rm_layout.setVisibility(View.GONE);
        mpo_pc_followup.setVisibility(View.GONE);
        pc_approval.setVisibility(View.VISIBLE);
        pc_bill_mpo_layout.setVisibility(View.GONE);
        mpo_pc_pending_lay.setVisibility(View.GONE);


    }

    private void smFunc() {
        //pc_bill_followup_admin_layout.setVisibility(View.VISIBLE);
        pc_bill_followup_admin_layout.setVisibility(View.VISIBLE);
        pc_conference_followup_admin.setVisibility(View.VISIBLE);

        pc_pending.setVisibility(View.GONE);
        mpo_layout.setVisibility(View.GONE);
        rm_layout.setVisibility(View.GONE);
        pc_approval.setVisibility(View.VISIBLE);
        mpo_pc_followup.setVisibility(View.GONE);
        pc_bill_mpo_layout.setVisibility(View.GONE);
        mpo_pc_pending_lay.setVisibility(View.GONE);
       // pc_bill_approval.setVisibility(View.GONE);
    }

    private void asmFunc() {
        pc_conference_followup_admin.setVisibility(View.VISIBLE);
        pc_bill_followup_admin_layout.setVisibility(View.VISIBLE);

        pc_pending.setVisibility(View.GONE);
        mpo_layout.setVisibility(View.GONE);
        rm_layout.setVisibility(View.GONE);
        pc_approval.setVisibility(View.VISIBLE);
        mpo_pc_followup.setVisibility(View.GONE);
        pc_bill_mpo_layout.setVisibility(View.GONE);
        mpo_pc_pending_lay.setVisibility(View.GONE);
    }


    private void rmFunc() {
        pc_conference_followup_admin.setVisibility(View.VISIBLE);
        pc_bill_followup_admin_layout.setVisibility(View.VISIBLE);
        rm_layout.setVisibility(View.VISIBLE);
        pc_approval.setVisibility(View.VISIBLE);
        pc_pending.setVisibility(View.VISIBLE);
        mpo_layout.setVisibility(View.GONE);
        mpo_pc_pending_lay.setVisibility(View.GONE);
        mpo_pc_followup.setVisibility(View.GONE);
        pc_bill_mpo_layout.setVisibility(View.GONE);
        new GeTPCPending().execute();

    }

    private void amFunc() {
        pc_pending.setVisibility(View.GONE);
        mpo_layout.setVisibility(View.GONE);
        rm_layout.setVisibility(View.GONE);
        pc_approval.setVisibility(View.VISIBLE);
        mpo_pc_followup.setVisibility(View.GONE);
        pc_conference_followup_admin.setVisibility(View.VISIBLE);
        pc_bill_mpo_layout.setVisibility(View.GONE);
        pc_bill_followup_admin_layout.setVisibility(View.VISIBLE);
        mpo_pc_pending_lay.setVisibility(View.GONE);
       // pc_bill_approval.setVisibility(View.GONE);
    }

    private void mpoFunc() {
        rm_layout.setVisibility(View.GONE);
        pc_approval.setVisibility(View.GONE);
        pc_pending.setVisibility(View.GONE);
        pc_bill_followup_admin_layout.setVisibility(View.GONE);
        pc_conference_followup_admin.setVisibility(View.GONE);
        pc_bill_mpo_layout.setVisibility(View.VISIBLE);

       // new GeTPCPending().execute();

        mpo_layout.setVisibility(View.VISIBLE);
        mpo_pc_followup.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        mpo_layout = findViewById(R.id.mpo_pc);
        rm_layout = findViewById(R.id.rm_pc);
        pc_approval = findViewById(R.id.pc_approval);
        mpo_pc_followup = findViewById(R.id.mpo_pc_followup);
        pc_conference_followup_admin = findViewById(R.id.pc_conference_followup_admin);
        pc_bill_mpo_layout = findViewById(R.id.pc_bill_mpo_layout);
        pc_bill_followup_admin_layout = findViewById(R.id.pc_bill_approval_layout);
        mpo_pc_pending_lay = findViewById(R.id.mpo_pc_pending_lay);
        pc_pending = findViewById(R.id.pc_pending);
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        pc_conference_btn = findViewById(R.id.pc_conference_btn);
        bar_11 = findViewById(R.id.progressBar14);
        pc_conference_followup_btn = findViewById(R.id.pc_conference_followup);
        bar_12 = findViewById(R.id.progressBar2);
        pc_conference_followup1 = findViewById(R.id.pc_conference_followup1);
        bar_21 = findViewById(R.id.progressBar21);
        rm_pc_req_btn = findViewById(R.id.pc_req_btn);
        bar_3 = findViewById(R.id.progressBar3);
        pc_approval_btn = findViewById(R.id.pc_approval_btn);
        bar_5 = findViewById(R.id.progressBar5);
        pc_bill_btn = findViewById(R.id.pc_bill_btn);
        bar_6 = findViewById(R.id.progressBar6);
        pc_bill_followup = findViewById(R.id.pc_bill_followup);
        bar_7 = findViewById(R.id.progressBar7);

        pc_bill_approval = findViewById(R.id.pc_bill_approval);
        pc_bill_approval_btnButton = findViewById(R.id.pc_bill_approval_btn);
        bar_8 = findViewById(R.id.progressBar8);
        pc_bill_admin_followup = findViewById(R.id.pc_bill_admin_followup);
        bar_9 = findViewById(R.id.progressBar9);
        pc_pending_permission = findViewById(R.id.pc_pending_permission);
        bar_10 = findViewById(R.id.progressBar10);
        mpo_pc_pending_btn = findViewById(R.id.mpo_pc_pending_btn);
        bar_16 = findViewById(R.id.progressBar16);
        pc_pending_noti = findViewById(R.id.progress_circle_text10);
        mpo_pc_pending_noti = findViewById(R.id.mpo_pc_pending_noti);
        back = findViewById(R.id.back);
        back.setTypeface(fontFamily);
        back.setText("\uf08b");
        user_show1 = findViewById(R.id.user_show1);

        Bundle b = getIntent().getExtras();
        userName = b.getString("UserName");
        UserName_2 = b.getString("UserName_2");
        new_version = b.getString("new_version");
        user_flag = b.getString("user_flag");
       // Log.e("userflag-->",user_flag);
        checkdatelist = new ArrayList<Customer>();
        logout = findViewById(R.id.logout);
        logout.setTypeface(fontFamily);
        logout.setText("\uf08b");

        switch (user_flag) {
            case "M":
                mpoFunc();
                break;
            case "R":
                rmFunc();
                break;
            case "A":
                amFunc();
                break;
            case "ASM":
                asmFunc();
                break;
            case "SM":
                smFunc();
                break;
            case "GM":
                gmFunc();
                break;
        }



        db = new DatabaseHandler(this);
        mpo_code_interna = db.getterritoryname();
        mpo_code_i = mpo_code_interna.toString();

        user_show1.setText(userName + " " + UserName_2 + " ");


    }


    private void pc_conference_date_check() {
        date_flag = "0";
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < checkdatelist.size(); i++) {
            date_flag = checkdatelist.get(i).getName();
        }



        pc_pending_rm_count = Integer.parseInt(date_flag);

        int set_my_test = 12 - pc_pending_rm_count;

        Spannable word = new SpannableString("Pending \n");
        word.setSpan(new ForegroundColorSpan(Color.rgb(51, 181, 229)), 1, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        Spannable wordTwo = new SpannableString(date_flag);
        wordTwo.setSpan(new ForegroundColorSpan(Color.rgb(51, 181, 229)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        pc_pending_noti.setText(word);
        pc_pending_noti.append(wordTwo);
        bar_10.setMax(12);
        bar_10.setProgress(set_my_test);


        if (user_flag.equals("M")) {


            if (date_flag == String.valueOf(0)) {
                bar_16.setMax(0);
            } else {
                bar_16.setMax(Integer.parseInt(date_flag + date_flag));
            }

            bar_16.setProgress(Integer.parseInt(date_flag));

            Spannable word1 = new SpannableString("Pending \n");
            word1.setSpan(new ForegroundColorSpan(Color.rgb(51, 181, 229)), 1, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            Spannable wordTwo2 = new SpannableString(date_flag);
            wordTwo2.setSpan(new ForegroundColorSpan(Color.rgb(51, 181, 229)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            mpo_pc_pending_noti.setText(word1);
            mpo_pc_pending_noti.append(wordTwo2);


        }


    }


    class GeTPCPending extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(PCDashboard.this);
            pDialog2.setMessage("Loading Data...");
            pDialog2.setCancelable(false);
            pDialog2.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            String userName = b.getString("UserName");
            String id = userName;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", userName));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(pc_conference_date_check, ServiceHandler.POST, params);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < 1; i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            checkdatelist.add(custo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog2.dismiss();
            pc_conference_date_check();
        }
    }


    private void logoutUser() {
        session.setLogin(false);
        // session.removeAttribute();
        session.invalidate();
        Intent intent = new Intent(PCDashboard.this, Login.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();

    }


    @Override
    public void onBackPressed() {
        finish();


    }


    @Override
    public void onClick(View v) {

    }
}
