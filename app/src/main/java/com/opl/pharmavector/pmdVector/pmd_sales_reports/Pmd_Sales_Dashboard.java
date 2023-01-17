package com.opl.pharmavector.pmdVector.pmd_sales_reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.ASMWiseProductSale;
import com.opl.pharmavector.AdminReportDashboard;
import com.opl.pharmavector.GMDashboard1;
import com.opl.pharmavector.GroupwiseProductOrderSummary2;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RMWiseProductSale;
import com.opl.pharmavector.Report;
import com.opl.pharmavector.SMBrandwiseProductSale;
import com.opl.pharmavector.SMWiseProductSale;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.pmdVector.DashBoardPMD;
import com.opl.pharmavector.util.NetInfo;

import es.dmoral.toasty.Toasty;

public class Pmd_Sales_Dashboard extends Activity {
    private SessionManager session;
    Button back_btn;
    CardView cardview1,cardview2,cardview3,cardview4,cardview5,cardview6;

    ImageView img1,img2,img3,img4,img5,img6;

    TextView txt_vw1,txt_vw2,txt_vw3,txt_vw4,txt_vw5,txt_vw6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pmd_sales_dashboard);
        initViews();

        cardview1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {

                            Intent i = new Intent(Pmd_Sales_Dashboard.this, SMWiseProductSale.class);
                            i.putExtra("userName", DashBoardPMD.pmd_code);
                            i.putExtra("UserName", DashBoardPMD.pmd_code);
                            i.putExtra("UserName_2", DashBoardPMD.pmd_code);
                            startActivity(i);

                    }
                });
                mysells.start();

            }
        });

        cardview2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(Pmd_Sales_Dashboard.this, SMBrandwiseProductSale.class);
                        i.putExtra("userName", DashBoardPMD.pmd_code);
                        i.putExtra("UserName", DashBoardPMD.pmd_code);
                        i.putExtra("UserName_2", DashBoardPMD.pmd_code);
                        startActivity(i);

                    }
                });
                mysells.start();

            }
        });

        cardview3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Thread mysells = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!NetInfo.isOnline(getBaseContext())) {
                            showSnack();
                        }
                        else {
                            // TODO Auto-generated method stub
                            Intent i = new Intent(Pmd_Sales_Dashboard.this, GroupwiseProductOrderSummary2.class);
                            i.putExtra("userName", DashBoardPMD.pmd_code);
                            i.putExtra("UserName", DashBoardPMD.pmd_code);
                            i.putExtra("UserName_2", DashBoardPMD.pmd_code);
                            startActivity(i);
                        }
                    }
                });
                mysells.start();

            }
        });



        back_btn.setOnClickListener(new View.OnClickListener() {
            Bundle b = getIntent().getExtras();
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        finish();
                    }
                });

                backthred.start();
            }
        });

    }

    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(),	"fonts/fontawesome.ttf");
        back_btn =  findViewById(R.id.btn_back);
        back_btn.setTypeface(fontFamily);
        back_btn.setText("\uf060 ");// &#xf060

        cardview1 = findViewById(R.id.cardview1);
        txt_vw1      = findViewById(R.id.txt_vw1);
        img1        = findViewById(R.id.img1);

        cardview2 = findViewById(R.id.cardview2);
        txt_vw2      = findViewById(R.id.txt_vw2);
        img2        = findViewById(R.id.img2);

        cardview3 = findViewById(R.id.cardview3);
        txt_vw3      = findViewById(R.id.txt_vw3);
        img3        = findViewById(R.id.img3);

        cardview4 = findViewById(R.id.cardview4);
        txt_vw4      = findViewById(R.id.txt_vw4);
        img4        = findViewById(R.id.img4);

        cardview5 = findViewById(R.id.cardview5);
        txt_vw5      = findViewById(R.id.txt_vw5);
        img5        = findViewById(R.id.img5);

        cardview6 = findViewById(R.id.cardview6);
        txt_vw6      = findViewById(R.id.txt_vw6);
        img6        = findViewById(R.id.img6);
    }

    private void logoutUser() {
        session.setLogin(false);
        session.invalidate();
        Intent intent = new Intent(Pmd_Sales_Dashboard.this, DashBoardPMD.class);
        startActivity(intent);
        finishActivity(BIND_ABOVE_CLIENT);
        finish();

    }

    private void showSnack() {
        new Thread() {
            public void run() {
                Pmd_Sales_Dashboard.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "No internet Connection, Please Check Your Connection";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();

    }



}