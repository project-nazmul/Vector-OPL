
package com.opl.pharmavector.prescriptionsurvey;

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
import com.opl.pharmavector.prescriptionsurvey.PrescriptionEntry;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RmDashboard;
import com.opl.pharmavector.SalesManagerDashboard;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.exam.ExamDashboard;
import com.opl.pharmavector.exam.ExamResultFollowup;
import com.opl.pharmavector.prescriptionsurvey.imageloadmore.ImageLoadActivity;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PrescriptionDashboard extends Activity implements View.OnClickListener {

    public String userName_1, userName, userName_2;
    public AutoCompleteTextView actv;
    private DatabaseHandler db;
    public TextView user_show1, user_show2;
    private SessionManager session;
    public String name;
    public String ename;
    public String mpo_code;
    public String message_3;
    public String new_version;
    public String user_flag,userFlag;
    public String message;
    public String success;
    public TextView textView;
    public Button message_head;
    private Button btn_rx_entry, btn_rx_followup,btn_rx_search,btn_rx_followup2,btn_back,btn_mpo_rx_followup2;
    private ProgressBar bar_1, bar_2,bar_3,bar_4,mpo_bar4;
    public LinearLayout l2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        assert b != null;
        userFlag = b.getString("user_flag");
        if (userFlag.equals("MPO")){
            setContentView(R.layout.prescriptiondashboard);
            initViews();
            btnClickEvents();
        }
        else{
            setContentView(R.layout.activity_manager_prescriptiondashboard);
            initViewsManager();
            btnManagersClickEvents();
        }
        session = new SessionManager(getApplicationContext());
    }

    private void btnManagersClickEvents() {
    }

    private void initViewsManager() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");

        if (userFlag.equals("FM")){
            user_show1 =  findViewById(R.id.user_show1);
            user_show2 =  findViewById(R.id.user_show1);
            user_show1.setText(new StringBuilder().append(AmDashboard.globalFMCode).append(" [ ").append(AmDashboard.globalAreaCode).append(" ] ").toString());
            btn_rx_followup =  findViewById(R.id.rx_followup);
            btn_rx_followup2 =  findViewById(R.id.rx_followup_2);
            btn_rx_search =  findViewById(R.id.rx_search);
            bar_2 =  findViewById(R.id.progressBar2);
            bar_3 = findViewById(R.id.progressBar3);
            bar_4 = findViewById(R.id.progressBar4);

            btn_back = findViewById(R.id.back);
            btn_back.setTypeface(fontFamily);
            btn_back.setText("\uf08b");


            btn_rx_followup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code", AmDashboard.globalFMCode);
                    i.putExtra("manager_detail", "FM");
                    startActivity(i);
                }
            });

            btn_rx_followup2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code", AmDashboard.globalFMCode);
                    i.putExtra("manager_detail", "FM");
                    startActivity(i);
                }
            });

            bar_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code", AmDashboard.globalFMCode);
                    i.putExtra("manager_detail", "FM");
                    startActivity(i);
                }
            });



            bar_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code", AmDashboard.globalFMCode);
                    i.putExtra("manager_detail", "FM");
                    startActivity(i);
                }
            });

            btn_rx_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    i.putExtra("manager_code",AmDashboard.globalFMCode);
                    i.putExtra("manager_detail", AmDashboard.globalAreaCode);
                    startActivity(i);
                }
            });
            bar_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    Log.e("fmpassed==>",AmDashboard.globalFMCode);
                    i.putExtra("manager_code",AmDashboard.globalFMCode);
                    i.putExtra("manager_detail", AmDashboard.globalAreaCode);
                    i.putExtra("manager_flag","FM");
                    startActivity(i);
                }
            });

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Thread backthred = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Intent i = new Intent(PrescriptionDashboard.this,AmDashboard.class);
                            i.putExtra("UserName", AmDashboard.globalFMCode);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_2", AmDashboard.globalAreaCode);
                            i.putExtra("manager_flag","FM");
                            startActivity(i);
                        }
                    });
                    backthred.start();
                }
            });



        }

        else if (userFlag.equals("RM")){
            user_show1 =  findViewById(R.id.user_show1);
            user_show2 =  findViewById(R.id.user_show1);
            user_show1.setText(new StringBuilder().append(RmDashboard.globalRMCode).append(" [ ").append(RmDashboard.globalRegionalCode).append(" ] ").toString());
            btn_rx_followup =  findViewById(R.id.rx_followup);
            btn_rx_followup2 =  findViewById(R.id.rx_followup_2);
            btn_rx_search =  findViewById(R.id.rx_search);
            bar_2 =  findViewById(R.id.progressBar2);
            bar_3 = findViewById(R.id.progressBar3);
            bar_4 = findViewById(R.id.progressBar4);
            btn_back = findViewById(R.id.back);
            btn_back.setTypeface(fontFamily);
            btn_back.setText("\uf08b");

            btn_rx_followup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code", RmDashboard.globalRMCode);
                    i.putExtra("manager_detail", "RM");
                    startActivity(i);
                }
            });

            btn_rx_followup2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code", RmDashboard.globalRMCode);
                    i.putExtra("manager_detail", "RM");
                    startActivity(i);
                }
            });

            bar_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code", RmDashboard.globalRMCode);
                    i.putExtra("manager_detail", "RM");
                    startActivity(i);
                }
            });

            bar_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code", RmDashboard.globalRMCode);
                    i.putExtra("manager_detail", "RM");
                    startActivity(i);
                }
            });


            bar_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code", RmDashboard.globalRMCode);
                    i.putExtra("manager_detail", "RM");
                    startActivity(i);
                }
            });
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Thread backthred = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Intent i = new Intent(PrescriptionDashboard.this,RmDashboard.class);
                            i.putExtra("UserName",RmDashboard.globalRMCode);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_2", RmDashboard.globalRegionalCode);
                            startActivity(i);
                        }
                    });
                    backthred.start();
                }
            });


            btn_rx_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    i.putExtra("manager_code",RmDashboard.globalRMCode);
                    i.putExtra("manager_detail", RmDashboard.globalRegionalCode);
                    i.putExtra("manager_flag","RM");
                    startActivity(i);
                }
            });
            bar_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    i.putExtra("manager_code",RmDashboard.globalRMCode);
                    i.putExtra("manager_detail", RmDashboard.globalRegionalCode);
                    i.putExtra("manager_flag","RM");
                    //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                    startActivity(i);
                }
            });

        }

        else if (userFlag.equals("ASM")){

            user_show1 =  findViewById(R.id.user_show1);
            user_show2 =  findViewById(R.id.user_show1);
            user_show1.setText(new StringBuilder().append(AssistantManagerDashboard.globalASMCode).append(" [ ").append(AssistantManagerDashboard.globalZONECode).append(" ] ").toString());
            btn_rx_followup =  findViewById(R.id.rx_followup);
            btn_rx_followup2 =  findViewById(R.id.rx_followup_2);
            btn_rx_search =  findViewById(R.id.rx_search);
            bar_2 =  findViewById(R.id.progressBar2);
            bar_3 = findViewById(R.id.progressBar3);
            bar_4 = findViewById(R.id.progressBar4);
            btn_back = findViewById(R.id.back);
            btn_back.setTypeface(fontFamily);
            btn_back.setText("\uf08b");

            btn_rx_followup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code",AssistantManagerDashboard.globalASMCode);
                    i.putExtra("manager_detail","ASM");
                    startActivity(i);
                }
            });

            btn_rx_followup2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code",AssistantManagerDashboard.globalASMCode);
                    i.putExtra("manager_detail","ASM");
                    startActivity(i);
                }
            });


            bar_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code",AssistantManagerDashboard.globalASMCode);
                    i.putExtra("manager_detail","ASM");
                    startActivity(i);
                }
            });


            bar_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code",AssistantManagerDashboard.globalASMCode);
                    i.putExtra("manager_detail", "ASM");
                    startActivity(i);
                }
            });
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Thread backthred = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Intent i = new Intent(PrescriptionDashboard.this,AssistantManagerDashboard.class);
                            i.putExtra("UserName",AssistantManagerDashboard.globalASMCode);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_2", AssistantManagerDashboard.globalZONECode);
                            startActivity(i);
                        }
                    });
                    backthred.start();
                }
            });


            btn_rx_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    i.putExtra("manager_code",AssistantManagerDashboard.globalASMCode);
                    i.putExtra("manager_detail",AssistantManagerDashboard.globalZONECode);
                    i.putExtra("manager_flag","ASM");
                    startActivity(i);
                }
            });
            bar_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    i.putExtra("manager_code",AssistantManagerDashboard.globalASMCode);
                    i.putExtra("manager_detail",AssistantManagerDashboard.globalZONECode);
                    i.putExtra("manager_flag","ASM");
                    //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                    startActivity(i);
                }
            });

        }

        else if (userFlag.equals("SM")){

            user_show1 =  findViewById(R.id.user_show1);
            user_show2 =  findViewById(R.id.user_show1);
            user_show1.setText(new StringBuilder().append(SalesManagerDashboard.globalSMCode).append(" [ ").append(SalesManagerDashboard.globalDivisionCode).append(" ] ").toString());
            btn_rx_followup =  findViewById(R.id.rx_followup);
            btn_rx_followup2 =  findViewById(R.id.rx_followup_2);
            btn_rx_search =  findViewById(R.id.rx_search);
            bar_2 =  findViewById(R.id.progressBar2);
            bar_3 = findViewById(R.id.progressBar3);
            bar_4 = findViewById(R.id.progressBar4);
            btn_back = findViewById(R.id.back);
            btn_back.setTypeface(fontFamily);
            btn_back.setText("\uf08b");

            btn_rx_followup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code",SalesManagerDashboard.globalSMCode);
                    i.putExtra("manager_detail", "SM");
                    startActivity(i);
                }
            });

            btn_rx_followup2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code",SalesManagerDashboard.globalSMCode);
                    i.putExtra("manager_detail", "SM");
                    startActivity(i);
                }
            });

            bar_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code",SalesManagerDashboard.globalSMCode);
                    i.putExtra("manager_detail", "SM");
                    startActivity(i);
                }
            });


            bar_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code",SalesManagerDashboard.globalSMCode);
                    i.putExtra("manager_detail", "SM");
                    startActivity(i);
                }
            });

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Thread backthred = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Intent i = new Intent(PrescriptionDashboard.this,SalesManagerDashboard.class);
                            i.putExtra("UserName",SalesManagerDashboard.globalSMCode);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_2", SalesManagerDashboard.globalDivisionCode);

                            startActivity(i);
                        }
                    });
                    backthred.start();
                }
            });

            btn_rx_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    i.putExtra("manager_code",SalesManagerDashboard.globalSMCode);
                    i.putExtra("manager_detail",SalesManagerDashboard.globalDivisionCode);
                    i.putExtra("manager_flag","SM");
                    startActivity(i);
                }
            });
            bar_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    i.putExtra("manager_code",SalesManagerDashboard.globalSMCode);
                    i.putExtra("manager_detail",SalesManagerDashboard.globalDivisionCode);
                    i.putExtra("manager_flag","SM");
                    //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                    startActivity(i);
                }
            });


        }

        else if (userFlag.equals("AD")){

            user_show1 =  findViewById(R.id.user_show1);
            user_show2 =  findViewById(R.id.user_show1);
            user_show1.setText(new StringBuilder().append(GMDashboard1.globalAdmin).append(" [ ").append(GMDashboard1.globalAdminDtl).append(" ] ").toString());
            btn_rx_followup =  findViewById(R.id.rx_followup);
            btn_rx_followup2 =  findViewById(R.id.rx_followup_2);
            btn_rx_search =  findViewById(R.id.rx_search);
            bar_2 =  findViewById(R.id.progressBar2);
            bar_3 = findViewById(R.id.progressBar3);
            bar_4 = findViewById(R.id.progressBar4);
            btn_back = findViewById(R.id.back);
            btn_back.setTypeface(fontFamily);
            btn_back.setText("\uf08b");

            btn_rx_followup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code",GMDashboard1.globalAdmin);
                    i.putExtra("manager_detail", "AD");
                    startActivity(i);
                }
            });

            btn_rx_followup2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code",GMDashboard1.globalAdmin);
                    i.putExtra("manager_detail", "AD");
                    startActivity(i);
                }
            });

            bar_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                    i.putExtra("manager_code",GMDashboard1.globalAdmin);
                    i.putExtra("manager_detail", "AD");
                    startActivity(i);
                }
            });


            bar_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                    i.putExtra("manager_code",GMDashboard1.globalAdmin);
                    i.putExtra("manager_detail",  "AD");
                    startActivity(i);
                }
            });//ManagerPrescriptionFollowup
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Thread backthred = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Intent i = new Intent(PrescriptionDashboard.this,GMDashboard1.class);
                            i.putExtra("UserName",GMDashboard1.globalAdmin);
                            i.putExtra("new_version", userName);
                            i.putExtra("UserName_2", GMDashboard1.globalAdminDtl);
                            startActivity(i);
                        }
                    });
                    backthred.start();
                }
            });


            btn_rx_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    i.putExtra("manager_code",GMDashboard1.globalAdmin);
                    i.putExtra("manager_detail",GMDashboard1.globalAdminDtl);
                    i.putExtra("manager_flag","AD");
                    startActivity(i);
                }
            });
            bar_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                    i.putExtra("manager_code",GMDashboard1.globalAdmin);
                    i.putExtra("manager_detail",GMDashboard1.globalAdminDtl);
                    i.putExtra("manager_flag","AD");
                    //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                    startActivity(i);
                }
            });

        }


    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        LinearLayout l1 =  findViewById(R.id.mpo_doc_l1);
        LinearLayout mpo_exam =  findViewById(R.id.mpo_exam);
        btn_rx_entry =  findViewById(R.id.rx_entry);
        btn_rx_followup =  findViewById(R.id.rx_followup);
        btn_rx_search =  findViewById(R.id.rx_search);
        btn_mpo_rx_followup2 =  findViewById(R.id.mpo_rx_followup2);

        bar_1 = findViewById(R.id.progressBar1);
        bar_2 =  findViewById(R.id.progressBar2);
        bar_3 = findViewById(R.id.progressBar3);

        mpo_bar4 = findViewById(R.id.progressBar5);
        btn_back = (Button) findViewById(R.id.back);
        btn_back.setTypeface(fontFamily);
        btn_back.setText("\uf08b");
        user_show1 =  findViewById(R.id.user_show1);
        user_show2 =  findViewById(R.id.user_show1);
        user_show1.setText(new StringBuilder().append(Dashboard.globalmpocode).append(" [ ").append(Dashboard.globalterritorycode).append(" ] ").toString());
    }

    private void btnClickEvents() {


        btn_rx_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(PrescriptionDashboard.this, PrescriptionEntry.class);
                startActivity(i);
            }
        });

        bar_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(PrescriptionDashboard.this, PrescriptionEntry.class);
                startActivity(i);
            }
        });
        btn_rx_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                i.putExtra("manager_code", Dashboard.globalmpocode);
                i.putExtra("manager_detail",Dashboard.globalterritorycode);
                i.putExtra("manager_flag", "MPO");

                startActivity(i);
            }
        });
        bar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup.class);
                i.putExtra("manager_code", Dashboard.globalmpocode);
                i.putExtra("manager_detail",Dashboard.globalterritorycode);
                i.putExtra("manager_flag", "MPO");
                startActivity(i);
            }
        });


        btn_mpo_rx_followup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                i.putExtra("manager_code", Dashboard.globalmpocode);
                i.putExtra("manager_detail",Dashboard.globalterritorycode);
                i.putExtra("manager_flag", "MPO");
                startActivity(i);
            }
        });


        mpo_bar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(PrescriptionDashboard.this, PrescriptionFollowup2.class);
                i.putExtra("manager_code", Dashboard.globalmpocode);
                i.putExtra("manager_detail",Dashboard.globalterritorycode);
                i.putExtra("manager_flag", "MPO");
                startActivity(i);
            }
        });


        btn_rx_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);
                i.putExtra("manager_code",Dashboard.globalmpocode);
                i.putExtra("manager_detail",Dashboard.globalterritorycode);
                i.putExtra("manager_flag", "MPO");
                startActivity(i);
            }
        });
        bar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(PrescriptionDashboard.this, ImageLoadActivity.class);

                i.putExtra("manager_code",Dashboard.globalmpocode);
                i.putExtra("manager_detail",Dashboard.globalterritorycode);
                i.putExtra("manager_flag", "MPO");
              //  Intent i = new Intent(PrescriptionDashboard.this, PrescroptionImageSearch.class);
                startActivity(i);
            }
        });






        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(PrescriptionDashboard.this,Dashboard.class);
                        i.putExtra("UserName", Dashboard.globalmpocode);
                        i.putExtra("new_version", userName);
                        i.putExtra("UserName_2", Dashboard.globalterritorycode);
                        i.putExtra("ff_type", Dashboard.ff_type);
                        i.putExtra("emp_code", Dashboard.globalempCode);
                        i.putExtra("emp_name", Dashboard.globalempName);
                        startActivity(i);
                    }
                });
                backthred.start();

            }
        });

    }


    private void logoutUser() {
        session.setLogin(false);
        session.invalidate();
        Intent intent = new Intent(PrescriptionDashboard.this, Login.class);
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






