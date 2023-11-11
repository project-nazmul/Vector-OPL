package com.opl.pharmavector.giftfeedback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.github.tutorialsandroid.appxupdater.AppUpdater;
import com.github.tutorialsandroid.appxupdater.AppUpdaterUtils;
import com.github.tutorialsandroid.appxupdater.enums.AppUpdaterError;
import com.github.tutorialsandroid.appxupdater.enums.Display;
import com.github.tutorialsandroid.appxupdater.enums.UpdateFrom;
import com.github.tutorialsandroid.appxupdater.objects.Update;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.opl.pharmavector.AmDashboard;
import com.opl.pharmavector.AssistantManagerDashboard;
import com.opl.pharmavector.Changepassword;
import com.opl.pharmavector.Contact;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.DatabaseHandler;
import com.opl.pharmavector.GMDashboard1;
import com.opl.pharmavector.GalleryAdapter;
import com.opl.pharmavector.ImageUtil;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.NoticeBoard;
import com.opl.pharmavector.NoticeBoardDetails;
import com.opl.pharmavector.Offlinereadcomments;
import com.opl.pharmavector.R;
import com.opl.pharmavector.RmDashboard;
import com.opl.pharmavector.SalesManagerDashboard;
import com.opl.pharmavector.SessionManager;
import com.opl.pharmavector.app.AppController;
import com.opl.pharmavector.app.Config;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionEntry;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup;
import com.opl.pharmavector.util.NetInfo;
import com.opl.pharmavector.util.NotificationUtils;
import com.opl.pharmavector.util.PreferenceManager;
import com.opl.pharmavector.util.ResetPasswordDialog;

import es.dmoral.toasty.Toasty;
import pub.devrel.easypermissions.EasyPermissions;

public class FieldFeedBack extends AppCompatActivity {
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    Typeface fontFamily;
    Button btn_back,btn_submit,buttonChoose,btn_feedback;
    MaterialSpinner mspinner2;
    ImageView imageView;
    EditText ed_title,ed_feedback_detail,ed_setName;
    String user_code,user_detail,topic_master,topic_detail;
    public String img_local_path;
    public StringRequest stringRequest;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    ArrayList<String> imagesEncodedList = new ArrayList<>();
    Bitmap decoded;
    int bitmap_size = 40;
    int success;
    public static String UPLOAD_URL = "http://opsonin.com.bd/vector_opl_v1/vector_feedback/insert_feedback.php";
    public String message;
    ArrayList<Uri> imageUriList = new ArrayList<>();

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_feedback);

        initViews();
        permissionEvent();
        initUserHintSpinner();
        btnEvents();
    }

    private void initViews() {
        fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        ed_title = findViewById(R.id.ed_title);
        ed_feedback_detail = findViewById(R.id.ed_feedback_detail);
        ed_setName = findViewById(R.id.ed_setName);
        btn_back = findViewById(R.id.btn_back);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setTypeface(fontFamily);
        btn_submit.setText("\uf1d8");
        btn_back.setTypeface(fontFamily);
        btn_back.setText("\uf060");
        buttonChoose = findViewById(R.id.buttonChoose);
        buttonChoose.setTypeface(fontFamily);
        buttonChoose.setText("\uf067");
        Bundle b = getIntent().getExtras();
        assert b != null;
        user_code = b.getString("UserName");
        user_detail = b.getString("user_flag");
        mspinner2 = findViewById(R.id.mspinner2);
        imageView = findViewById(R.id.imageView);
        btn_feedback = findViewById(R.id.btn_feedback);
        ed_setName.setText(Build.MODEL);
        ed_setName.setClickable(false);
        ed_setName.setEnabled(false);
    }

    private void initUserHintSpinner() {
        MaterialSpinner mspinner = findViewById(R.id.mspinner);
        switch (user_detail) {
            case "MPO":
                mspinner.setItems("Product Order", "DCR","Personal Expenses", "Followup","Sales Report", "PC conference", "Doctor Service","Mrc Exam", "Prescription Capture");
                break;
/*
            case "SM":
                mspinner.setItems("Zone", "Region", "Area", "Territory");
                break;

            case "ASM":
                mspinner.setItems("Region", "Area", "Territory");
                break;
*/
            case "RM":
                mspinner.setItems("DCR","Personal Expenses","Monitor", "Campaign","Sales Report", "PC conference", "Doctor Service", "Promo Material Followup","Mrc Exam","Prescription Capture");
                break;
            case "FM":
                mspinner.setItems("DCR","Personal Expenses", "Followup","Sales Report", "PC conference", "Doctor Service","Mrc Exam","Prescription Capture");
                break;
        }

        mspinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar snackbar = Snackbar.make(view, "Vector Topic: " + item, Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(FieldFeedBack.this, R.color.colorAccentEditor));
                snackbar.show();
                topic_master = String.valueOf(item);

                switch (user_detail) {
                    case "MPO":
                        if (topic_master.trim().equals("Product Order")) {
                            mspinner2.setItems("Online", "Offline");
                        } else if (topic_master.trim().equals("DCR")) {
                            mspinner2.setItems("Online", "Report");
                        } else if (topic_master.trim().equals("Personal Expenses")) {
                            mspinner2.setItems("Online", "Report");
                        } else if (topic_master.trim().equals("Followup")) {
                            mspinner2.setItems("DCC", "Promo Material");
                        } else if (topic_master.trim().equals("Sales Report")) {
                            mspinner2.setItems("Report 1", "Report 2", "Report 3", "Report 4", "Report 5", "Report 6", "Report 7", "Report 8", "Report 9", "Report 10", "Report 11");
                        } else if (topic_master.trim().equals("Doctor Service")) {
                            mspinner2.setItems("Acknowledgement", "Followup", "Tracking Doctor Servie", "Doctor Chamber Geo Tagging");
                        } else if (topic_master.trim().equals("Mrc Exam")) {
                            mspinner2.setItems("Exam attened", "Exam Result");
                        } else if (topic_master.trim().equals("Prescription Capture")) {
                            mspinner2.setItems("RX Entry", "RX Summary A", "RX Summary B");
                        } else if (topic_master.trim().equals("PC conference")) {
                            mspinner2.setItems("Proposal", "Followup", "Bill");
                        } else if (topic_master.trim().equals("Promo Material Followup")) {
                            mspinner2.setItems("Sample", "PPM", "Gift");
                        }
                        break;
                    case "FM":
                        if (topic_master.trim().equals("DCR")) {
                            mspinner2.setItems("Online", "Report");
                        } else if (topic_master.trim().equals("Personal Expenses")) {
                            mspinner2.setItems("Online", "Report");
                        } else if (topic_master.trim().equals("Followup")) {
                            mspinner2.setItems("MPO DCC", "Chemist Gift");
                        } else if (topic_master.trim().equals("Sales Report")) {
                            mspinner2.setItems("Report 1", "Report 2", "Report 3", "Report 4", "Report 5", "Report 6", "Report 7", "Report 8");
                        } else if (topic_master.trim().equals("Doctor Service")) {
                            mspinner2.setItems("Acknowledgement", "Followup", "Tracking Doctor Servie", "Doctor Chamber Geo Tagging");
                        } else if (topic_master.trim().equals("Mrc Exam")) {
                            mspinner2.setItems("Exam attened", "Exam Result");
                        } else if (topic_master.trim().equals("Prescription Capture")) {
                            mspinner2.setItems("RX Summary A", "RX Summary B");
                        } else if (topic_master.trim().equals("PC conference")) {
                            mspinner2.setItems("Proposal", "Followup", "Bill");
                        }
                        break;
                    case "RM":
                        if (topic_master.trim().equals("DCR")) {
                            mspinner2.setItems("Online", "Report");
                        } else if (topic_master.trim().equals("Personal Expenses")) {
                            mspinner2.setItems("Online", "Report");
                        } else if (topic_master.trim().equals("Followup")) {
                            mspinner2.setItems("DCC", "Promo Material");
                        } else if (topic_master.trim().equals("Sales Report")) {
                            mspinner2.setItems("Report 1", "Report 2", "Report 3", "Report 4", "Report 5", "Report 6", "Report 7", "Report 8");
                        } else if (topic_master.trim().equals("Doctor Service")) {
                            mspinner2.setItems("Acknowledgement", "Followup", "Tracking Doctor Servie", "Doctor Chamber Geo Tagging");
                        } else if (topic_master.trim().equals("Mrc Exam")) {
                            mspinner2.setItems("Exam attened", "Exam Result");
                        } else if (topic_master.trim().equals("Prescription Capture")) {
                            mspinner2.setItems("RX Summary A", "RX Summary B");
                        } else if (topic_master.trim().equals("PC conference")) {
                            mspinner2.setItems("Proposal", "Followup", "Bill");
                        }else if (topic_master.trim().equals("Monitor")) {
                            mspinner2.setItems("AM summary", "DCR");
                        }else if (topic_master.trim().equals("Followup")) {
                            mspinner2.setItems("DCC", "Promo Material");
                        }
                        break;
                }
            }
        });

        mspinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar snackbar = Snackbar.make(view, "Topic subtype: " + item, Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(ContextCompat.getColor(FieldFeedBack.this, R.color.colorAccentEditor));
                snackbar.show();
                topic_detail = String.valueOf(item);
            }
        });
    }

    private void btnEvents() {
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (ed_title.getText().toString() == null || ed_title.getText().toString().equals("")) {
                    message= "Write down Feedback Title";
                    showSnack();
                } else if (ed_feedback_detail.getText().toString() == null || ed_feedback_detail.getText().toString().equals("")) {
                    message= "Write down Feedback Description";
                    showSnack();
                } else if (ed_setName.getText().toString().equals(null) || ed_setName.getText().toString().equals("")) {
                    message= "Write down Your Mobile set name and Model";
                    showSnack();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        //mImageUriList.clear();
                        //imagesEncodedList.clear();
                        initSingleUploadOS13();
                    } else {
                        initSingleUpload();
                    }
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable() == null){
                    message= "Select your issues Screenshot";
                    showSnack();
                } else {
                    uploadImage();
                }
            }
        });

        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
                backthred.start();
            }
        });

        btn_feedback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(FieldFeedBack.this, FieldFeedbackMaster.class);
                        i.putExtra("user_code", user_code);
                        i.putExtra("user_role", "MPO");
                        Log.e("passed-->",user_code);
                        startActivity(i);
                    }
                });
                backthred.start();
            }
        });
    }

    private void uploadImage() {
        final ProgressDialog loading = ProgressDialog.show(this, "Sending Your Feedback ...", "Please wait...", false, false);
        String encodedImage;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            encodedImage = getStringImageOS13();
        } else {
            encodedImage = getStringImage(decoded);
        }
        Log.d("encodedImage", encodedImage);

        stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, response -> {
            try {
                JSONObject jObj = new JSONObject(response);
                String name = jObj.getString("success");
                String email = jObj.getString("message");
                success = jObj.getInt(TAG_SUCCESS);
                Log.e("success", String.valueOf(success));

                if (success == 1) {
                    Toast.makeText(FieldFeedBack.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(FieldFeedBack.this).setTitle("Successful")
                            .setMessage("Your Feedback is submitted")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            }).show();
                    refresh();
                } else {
                    Toast.makeText(FieldFeedBack.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loading.dismiss();
        },
                error -> {
                    loading.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(FieldFeedBack.this, FieldFeedBack.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {

                    } else if (error instanceof ServerError) {

                    } else if (error instanceof NetworkError) {

                    } else if (error instanceof ParseError) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("image", encodedImage);
                params.put("ff_code", user_code);
                params.put("topic_master",topic_master);
                params.put("topic_detail", topic_detail);
                params.put("ed_title", ed_title.getText().toString());
                params.put("ed_feedback_detail", ed_feedback_detail.getText().toString());
                params.put("ed_setName", ed_setName.getText().toString());
                Log.e("image==>",getStringImage(decoded));
                Log.e("ff_code==>",user_code);
                Log.e("topic_master==>",topic_master);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                0, 0));
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    public String getStringImageOS13() {
        Bitmap bitmapImage = null;
        try {
            bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUriList.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ImageBase64.encode(bitmapImage);
    }

    private void initSingleUpload() {
        imageView.setVisibility(View.VISIBLE);
        showFileChooserSingle();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void initSingleUploadOS13() {
        imageView.setVisibility(View.VISIBLE);
        showFileChooserSingleOS13();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void permissionEvent() {
        ActivityCompat.requestPermissions(FieldFeedBack.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

        if (ContextCompat.checkSelfPermission(FieldFeedBack.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(FieldFeedBack.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(FieldFeedBack.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }
    }

    private void showFileChooserSingle() {
        ImagePicker.create(this)
                .single()
                .showCamera(false)
                .start();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void showFileChooserSingleOS13() {
        int imageSelectLimit = 1;
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        intent.putExtra(MediaStore.ACTION_PICK_IMAGES, imageSelectLimit);
        startActivityForResult(intent, 102);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (data != null) {
                int count = data.getClipData().getItemCount();

               if (count == 1) {
                    imageView.setVisibility(View.VISIBLE);
                    Uri imageUri = data.getClipData().getItemAt(0).getUri();
                    imageUriList.add(imageUri);
                    imageView.setImageURI(imageUri);
                }
            }
        } else {
            if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
                List<Image> images = ImagePicker.getImages(data);
                ArrayList<Uri> mArrayUri = new ArrayList<>();

                for (Image image: images) {
                    mArrayUri.add(Uri.parse(image.getPath()));
                    imagesEncodedList.add(image.getPath());
                }
                imageView.setVisibility(View.VISIBLE);
                setToImageView(ImageUtil.getDecodedBitmap(mArrayUri.get(0).getPath(), 1024));
                img_local_path = mArrayUri.get(0).getPath();
                ExifInterface exif = null;
            }
        }
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void setToImageView(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        imageView.setImageBitmap(decoded);
    }

    private void showSnack() {
        new Thread()
        {
            public void run()
            {
               FieldFeedBack.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void refresh() {
        ed_title.getText().clear();
        ed_feedback_detail.getText().clear();
        ed_setName.getText().clear();
        imageView.setImageResource(0);
    }
}



