package com.opl.pharmavector.prescriptionsurvey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.tabs.TabLayout;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.PhotoLoader;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.GalleryAdapter;
import com.opl.pharmavector.ImageUtil;
import com.opl.pharmavector.MyCommand;
import com.opl.pharmavector.app.AppController;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.SessionManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.libaml.android.view.chip.ChipLayout;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import android.view.MotionEvent;

import es.dmoral.toasty.Toasty;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

public class PrescriptionEntry extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final String TAG = PrescriptionEntry.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final int RC_CAMERA_AND_LOCATION = 124;
    public String region_code;
    public String emp_code;
    public String emp_name, emp_name2;
    public String doc_qual, doc_name, doc_code, market_code, ward_name, dept_name;
    public AutoCompleteTextView actv, actv0, actv2, actv_gift;
    Button buttonChoose, buttonUpload;
    Button buttonmultiUpload;
    Toolbar toolbar;
    ImageView imageView;
    TextView txt_name, user_show1, user_show2;
    Bitmap bitmap, decoded;
    int success;
    Uri imageUri;
    Button buttonCapture;
    Button btn_multi;
    int PICK_IMAGE_REQUEST = 2;
    int bitmap_size = 40; // range 1 - 100
    String tag_json_obj = "json_obj_req";
    ArrayList<String> imageList = new ArrayList<>();
    ArrayList<String> imagesEncodedList = new ArrayList<>();
    private final String UPLOAD_URL_MULTI = BASE_URL + "prescription_survey/doc_prescription_multi_upload.php";
    private final String UPLOAD_URL = BASE_URL + "prescription_survey/image_upload_api/vector_pres_survey_web_new.php";
    private final String UPLOAD_Gift_URL = BASE_URL + "prescription_survey/image_upload_api/gift_pres_survey_web.php";
    private final String URL_CUSOTMER = BASE_URL + "prescription_survey/get_mpowise_doc.php";
    private final String URL_DOCTOR_NEW = BASE_URL + "prescription_survey/get_mpowise_doc_inst.php";
    private final String URL_INST = BASE_URL + "prescription_survey/get_institute.php";
    private final String URL_DEPT_WARD = BASE_URL + "prescription_survey/get_depward.php";
    private final String URL_GIFT_LIST = BASE_URL + "prescription_survey/get_mpowise_giftlist.php";
    private final String URL_GIFT_WISE_DOC_LIST = BASE_URL + "prescription_survey/get_giftwise_doclist.php";
    private String KEY_IMAGE = "image";
    private String KEY_EMP_CODE = "empcode";
    private String KEY_IMAGE_TYPE = "imagetype";
    private String KEY_NAME = "name";
    private String KEY_DOCCODE = "docname";
    private int REQUEST_CAMERA = 1;
    private GridView gvGallery;
    private GalleryAdapter galleryAdapter;
    private Button btn_back;
    private SessionManager session;
    private MyCommand<String> myCommand;
    private ChipLayout degree_name;
    private LinearLayout chiplayout;
    private ConstraintLayout constrainlayout;
    private List<String> lables;
    private String[] doc_degree;
    private TabLayout tab, gift_tab;
    String Tab_Flag = "D";
    String GIFT_Tab_Flag = "R";
    AutoCompleteTextView actv_doc, actv_dept, autoDoctorNew;
    private ArrayList<Customer> customerlist;
    private ArrayList<Customer> doctorListNew;
    private ArrayList<Customer> departmentlist;
    private ArrayList<Customer> giftlist;
    Spinner cust, dept, gift;
    ProgressDialog pDialog, pDialog2, pDialogNew;
    String json;
    private TextView tv_wardname;
    public String img_local_path;
    String img_make, img_lat, img_long, img_model, img_len, img_width, img_datetime, camera_image_time, img_make_multi, img_lat_multi, img_long_multi;
    public int selected_brand;
    public int mymultiupload = 0;
    public StringRequest stringRequest;
    public String encodedStringmulti;
    public ArrayList<String> mylist;
    public static ArrayList<String> selectedCategories = new ArrayList<>();
    LinearLayout gift_tab_layout;
    String ppm_name, ppm_code = "xxxxxx", ppm_prod_code, inst_type, brand_name, manufacturer;
    ArrayAdapter<String> doc_adapter;
    TabLayout tabLayout;
    ArrayList<Uri> mImageUriList = new ArrayList<>();
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_GALLERY_PERMISSION_CODE = 101;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pres_entry);

        ActivityCompat.requestPermissions(PrescriptionEntry.this, permissions(), 1);
        initViews();
        getDeviceDetails();
        new GetDoctor().execute();
        getDoctorDegree();
        permissionEvent();
        tabEvents();
        tabLayout = findViewById(R.id.tablayout2);
        giftAutocompleteEvent();
        doctorAutocompleteEvent();
        departmentwardAutocompleteEvent();
        doctorAutoEventNew();
        brandselectEvent();
        btnevents();
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
        ActivityCompat.requestPermissions(PrescriptionEntry.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

        if (ContextCompat.checkSelfPermission(PrescriptionEntry.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(PrescriptionEntry.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(PrescriptionEntry.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }
    }

    private void btnevents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
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

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                imagesEncodedList.clear();
                if (Tab_Flag.equals("D")) {
                    if (degree_name.getText().toString().equals("") || degree_name.getText().toString() == null) {
                        doctorSnack();
                        degree_name.setFocusableInTouchMode(true);
                        degree_name.setFocusable(true);
                        degree_name.requestFocus();
                    } else if (actv_doc.getText().toString().equals("") || actv_doc.getText().toString() == null) {
                        doctorSnack();
                        actv_doc.setFocusableInTouchMode(true);
                        actv_doc.setFocusable(true);
                        actv_doc.requestFocus();
                    } else {
                        degree_name.setEnabled(false);
                        if (selected_brand == 1) {
                            initMultiUpload();
                        } else {
                            initSingleUpload();
                        }
                    }
                }
                else if (Tab_Flag.equals("O")) {
                    if (actv_doc.getText().toString().equals("") || actv_doc.getText().toString() == null) {
                        doctorSnack();
                        actv_doc.setFocusableInTouchMode(true);
                        actv_doc.setFocusable(true);
                        actv_doc.requestFocus();
                    } else if (actv_dept.getText().toString().equals("") || actv_dept.getText().toString() == null) {
                        doctorSnack();
                        actv_dept.setFocusableInTouchMode(true);
                        actv_dept.setFocusable(true);
                        actv_dept.requestFocus();
                    } else if (autoDoctorNew.getText().toString().equals("") || autoDoctorNew.getText().toString() == null) {
                        doctorSnack();
                        autoDoctorNew.setFocusableInTouchMode(true);
                        autoDoctorNew.setFocusable(true);
                        autoDoctorNew.requestFocus();
                    } else {
                        degree_name.setEnabled(false);
                        if (selected_brand == 1) {
                            initMultiUpload();
                        } else {
                            initSingleUpload();
                        }
                    }
                }
                else if (Tab_Flag.equals("I")) {
                    if (actv_doc.getText().toString().equals("") || actv_doc.getText().toString() == null) {
                        doctorSnack();
                        actv_doc.setFocusableInTouchMode(true);
                        actv_doc.setFocusable(true);
                        actv_doc.requestFocus();
                    } else if (actv_dept.getText().toString().equals("") || actv_dept.getText().toString() == null) {
                        doctorSnack();
                        actv_dept.setFocusableInTouchMode(true);
                        actv_dept.setFocusable(true);
                        actv_dept.requestFocus();
                    } else if (autoDoctorNew.getText().toString().equals("") || autoDoctorNew.getText().toString() == null) {
                        doctorSnack();
                        autoDoctorNew.setFocusableInTouchMode(true);
                        autoDoctorNew.setFocusable(true);
                        autoDoctorNew.requestFocus();
                    } else {
                        degree_name.setEnabled(false);
                        if (selected_brand == 1) {
                            initMultiUpload();
                        } else {
                            initSingleUpload();
                        }
                    }
                } else {
                    if (GIFT_Tab_Flag.equals("SG")) {
                        initSingleUpload();
                    } else if (GIFT_Tab_Flag.equals("BL")) {
                        initSingleUpload();
                    } else {
                        degree_name.setEnabled(false);
                        Log.e("selectedbarand==>", String.valueOf(selected_brand));
                        if (selected_brand == 1) {
                            initMultiUpload();
                        } else {
                            initSingleUpload();
                        }
                    }
                }
            }
        });

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    imageView.setVisibility(View.VISIBLE);
                    gvGallery.setVisibility(View.GONE);
                    buttonmultiUpload.setVisibility(View.GONE);
                    buttonUpload.setVisibility(View.VISIBLE);

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    gvGallery.setVisibility(View.GONE);
                    buttonmultiUpload.setVisibility(View.GONE);
                    buttonUpload.setVisibility(View.VISIBLE);
                    openCamera();
                }
            }
        });

        btn_multi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.GONE);
                gvGallery.setVisibility(View.VISIBLE);
                buttonmultiUpload.setVisibility(View.VISIBLE);
                buttonUpload.setVisibility(View.GONE);
                showFileChooserMultiple();
            }
        });

        buttonmultiUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Tab_Flag.equals("D") && GIFT_Tab_Flag.equals("SG")) {
                    //uploadGiftMultiImage();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        uploadGiftMultiImageOS13();
                    } else {
                        uploadGiftMultiImage();
                    }
                } else if (Tab_Flag.equals("D") && GIFT_Tab_Flag.equals("BL")) {
                    //uploadGiftMultiImage();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        uploadGiftMultiImageOS13();
                    } else {
                        uploadGiftMultiImage();
                    }
                } else {
                    //uploadMultiImage();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        uploadMultiImageOS13();
                    } else {
                        uploadMultiImage();
                    }
                }
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree_name.getText().toString().trim();

                if (imageView.getDrawable() == null) {
                    new AlertDialog.Builder(PrescriptionEntry.this).setTitle("Alert ! No Prescription to Upload ")
                            .setMessage("Please Select a Prescription to Upload")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                } else {
                    if (Tab_Flag.equals("D") && GIFT_Tab_Flag.equals("SG")) {
                        uploadGiftImage();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                            uploadGiftImageOS13();
//                        } else {
//                            uploadGiftImage();
//                        }
                    } else if (Tab_Flag.equals("D") && GIFT_Tab_Flag.equals("BL")) {
                        uploadGiftImage();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                            uploadGiftImageOS13();
//                        } else {
//                            uploadGiftImage();
//                        }
                    } else if (Tab_Flag.equals("G") && GIFT_Tab_Flag.equals("Rx")) {
                        uploadGiftImage();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                            uploadGiftImageOS13();
//                        } else {
//                            uploadGiftImage();
//                        }
                    } else {
                        uploadImage();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                           uploadImageOS13();
//                        } else {
//                            uploadImage();
//                        }
                    }
                }
            }
        });
    }

    public void getDeviceDetails() {
        brand_name = Build.BRAND;
        manufacturer = Build.MANUFACTURER;
        Log.e("model", Build.MODEL);
        Log.e("brand", Build.BRAND);
        Log.e("manufacturer", Build.MANUFACTURER);
        Log.e("id", Build.ID);
        Log.e("device", Build.DEVICE);
        Log.e("versioncode", Build.VERSION.RELEASE);
    }

    private void doctorSnack() {
        new Thread() {
            public void run() {
                PrescriptionEntry.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "Please Select all required field first";
                        Toasty.info(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    private void tabEvents() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        //doc_code = "";
                        //doc_name = "";
                        Tab_Flag = "D";
                        buttonChoose.setEnabled(true);
                        constrainlayout.setVisibility(View.VISIBLE);
                        gift_tab_layout.setVisibility(View.GONE);
                        actv_doc.setText("");
                        actv_dept.setText("");
                        actv_dept.setVisibility(View.GONE);
                        tv_wardname.setVisibility(View.GONE);
                        autoDoctorNew.setVisibility(View.GONE);
                        customerlist.clear();
                        gift_tab.setVisibility(View.VISIBLE);
                        gift_tab.getTabAt(0).select();
                        actv_doc.setHint("Please  Select Doctor Name...");
                        new GetDoctor().execute();
                        break;
                    case 1:
                        //doc_code = "";
                        //doc_name = "";
                        actv_dept.setText("");
                        actv_dept.setFocusable(false);
                        autoDoctorNew.setText("");
                        doctorListNew.clear();
                        departmentlist.clear();
                        populateDept();
                        populateDoctorListNew();
                        Tab_Flag = "O";
                        GIFT_Tab_Flag = "R";
                        inst_type = "OPD";
                        constrainlayout.setVisibility(View.VISIBLE);
                        gift_tab_layout.setVisibility(View.GONE);
                        gift_tab.setVisibility(View.GONE);
                        actv_doc.setText("");
                        autoDoctorNew.setText("");
                        actv_dept.setVisibility(View.VISIBLE);
                        tv_wardname.setVisibility(View.GONE);
                        autoDoctorNew.setVisibility(View.VISIBLE);
                        customerlist.clear();
                        actv_doc.setHint("Select Institute name...");
                        new GetDoctor().execute();
                        buttonChoose.setEnabled(true);
                        break;
                    case 2:
                        //doc_code = "";
                        //doc_name = "";
                        actv_dept.setText("");
                        actv_dept.setFocusable(false);
                        autoDoctorNew.setText("");
                        doctorListNew.clear();
                        departmentlist.clear();
                        populateDept();
                        populateDoctorListNew();
                        Tab_Flag = "I";
                        GIFT_Tab_Flag = "R";
                        inst_type = "IPD";
                        buttonChoose.setEnabled(true);
                        constrainlayout.setVisibility(View.VISIBLE);
                        gift_tab_layout.setVisibility(View.GONE);
                        gift_tab.setVisibility(View.GONE);
                        actv_doc.setText("");
                        autoDoctorNew.setText("");
                        actv_dept.setVisibility(View.VISIBLE);
                        tv_wardname.setVisibility(View.GONE);
                        autoDoctorNew.setVisibility(View.VISIBLE);
                        customerlist.clear();
                        actv_doc.setHint("Select Institute name...");
                        new GetDoctor().execute();
                        buttonChoose.setEnabled(true);
                        break;
                    case 3:
                        //doc_code = "";
                        //doc_name = "";
                        actv_dept.setText("");
                        actv_dept.setFocusable(false);
                        autoDoctorNew.setText("");
                        doctorListNew.clear();
                        departmentlist.clear();
                        populateDept();
                        populateDoctorListNew();
                        Tab_Flag = "G";
                        GIFT_Tab_Flag = "Rx";
                        inst_type = "RXG";
                        /* gift_tab_layout.setVisibility(View.GONE);
                        giftlist.clear();
                        actv_gift.setAdapter(null);
                        new GetGiftList().execute();
                        actv_gift.setAdapter(null);
                        actv_gift.setText("");
                        constrainlayout.setVisibility(View.GONE);
                        buttonChoose.setEnabled(true);
                        actv_doc.setText("");
                        actv_dept.setVisibility(View.VISIBLE);
                        tv_wardname.setVisibility(View.GONE);
                        customerlist.clear();
                        actv_doc.setHint("Select Institute name...");
                        new GetDoctor().execute();
                        buttonChoose.setEnabled(true); */
                        new GetGiftList().execute();
                        actv_gift.setAdapter(null);
                        actv_gift.setText("");
                        buttonChoose.setEnabled(true);
                        constrainlayout.setVisibility(View.GONE);
                        gift_tab_layout.setVisibility(View.VISIBLE);
                        gift_tab.setVisibility(View.GONE);
                        actv_doc.setText("");
                        autoDoctorNew.setText("");
                        actv_dept.setVisibility(View.VISIBLE);
                        tv_wardname.setVisibility(View.GONE);
                        autoDoctorNew.setVisibility(View.VISIBLE);
                        customerlist.clear();
                        Log.e("ONCLICKtabflag-->", Tab_Flag);
                        actv_doc.setHint("Select Institute name...");
                        //new GetDoctor().execute();
                        buttonChoose.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        gift_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Tab_Flag = "D";
                        GIFT_Tab_Flag = "R";
                        constrainlayout.setVisibility(View.VISIBLE);
                        gift_tab_layout.setVisibility(View.GONE);
                        actv_doc.setText("");
                        actv_dept.setText("");
                        actv_dept.setVisibility(View.GONE);
                        tv_wardname.setVisibility(View.GONE);
                        customerlist.clear();
                        gift_tab.setVisibility(View.VISIBLE);
                        gift_tab.getTabAt(0).select();
                        actv_doc.setHint("Please  Select Doctor Name...");
                        actv_doc.setAdapter(null);
                        new GetDoctor().execute();
                        buttonChoose.setEnabled(true);
                        break;
                    case 1:
                        Tab_Flag = "D";
                        GIFT_Tab_Flag = "SG";
                        constrainlayout.setVisibility(View.GONE);
                        gift_tab_layout.setVisibility(View.VISIBLE);
                        giftlist.clear();
                        actv_gift.setAdapter(null);
                        new GetGiftList().execute();
                        customerlist.clear();
                        actv_doc.setAdapter(null);
                        actv_gift.setAdapter(null);
                        actv_gift.setText("");
                        actv_dept.setText("");
                        actv_doc.setText("");
                        actv_doc.setHint("Please  Select Doctor Name...");
                        buttonChoose.setEnabled(true);
                        break;
                    case 2:
                        Tab_Flag = "D";
                        GIFT_Tab_Flag = "BL";
                        constrainlayout.setVisibility(View.GONE);
                        gift_tab_layout.setVisibility(View.VISIBLE);
                        giftlist.clear();
                        actv_gift.setAdapter(null);
                        new GetGiftList().execute();
                        customerlist.clear();
                        actv_doc.setAdapter(null);
                        actv_gift.setAdapter(null);
                        actv_gift.setText("");
                        actv_dept.setText("");
                        actv_doc.setText("");
                        actv_doc.setHint("Please  Select Doctor Name...");
                        buttonChoose.setEnabled(true);
                        break;
                    case 3:
                        Tab_Flag = "D";
                        GIFT_Tab_Flag = "SD";
                        constrainlayout.setVisibility(View.VISIBLE);
                        gift_tab_layout.setVisibility(View.GONE);
                        actv_doc.setText("");
                        actv_dept.setText("");
                        actv_dept.setVisibility(View.GONE);
                        tv_wardname.setVisibility(View.GONE);
                        customerlist.clear();
                        gift_tab.setVisibility(View.VISIBLE);
                        gift_tab.getTabAt(3).select();
                        actv_doc.setHint("Please  Select Doctor Name...");
                        actv_doc.setAdapter(null);
                        new GetDoctor().execute();
                        buttonChoose.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void departmentwardAutocompleteEvent() {
        actv_dept.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actv_dept.showDropDown();
                return false;
            }
        });
        actv_dept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        actv_dept.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();

                    if (inputorder.contains("-")) {
                        dept_name = inputorder;
                        actv_dept.setText(inputorder);
                        hideKeyBoard();
                        buttonChoose.setEnabled(true);
                    } else {
                        hideKeyBoard();
                    }
                    hideKeyBoard();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {

            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void doctorAutocompleteEvent() {
        actv_doc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //hideKeyBoard();
                actv_doc.showDropDown();
                return false;
            }
        });
        actv_doc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        actv_doc.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //actv.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();

                    if (inputorder.contains("-")) {
                        Log.e("SelectedDoctor ==>", inputorder);
                        doc_code = inputorder.substring(inputorder.indexOf("-") + 1);
                        String[] first_split = inputorder.split("-");
                        doc_name = first_split[0].trim();
                        actv_doc.setText(doc_name);
                        hideKeyBoard();
                        buttonChoose.setEnabled(true);

                        if (Tab_Flag.equals("O")) {
                            actv_dept.setText("");
                            autoDoctorNew.setText("");
                            doctorListNew.clear();
                            departmentlist.clear();
                            actv_dept.setVisibility(View.VISIBLE);
                            new GetDept().execute();
                            new GetNewDoctorList().execute();
                            buttonChoose.setEnabled(true);
                        } else if (Tab_Flag.equals("I")) {
                            actv_dept.setText("");
                            autoDoctorNew.setText("");
                            doctorListNew.clear();
                            departmentlist.clear();
                            actv_dept.setVisibility(View.VISIBLE);
                            new GetDept().execute();
                            new GetNewDoctorList().execute();
                            buttonChoose.setEnabled(true);
                        } else if (Tab_Flag.equals("G")) {
                            actv_dept.setText("");
                            autoDoctorNew.setText("");
                            doctorListNew.clear();
                            departmentlist.clear();
                            actv_dept.setVisibility(View.VISIBLE);
                            new GetDept().execute();
                            new GetNewDoctorList().execute();
                            buttonChoose.setEnabled(true);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {

            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void doctorAutoEventNew() {
        autoDoctorNew.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoDoctorNew.showDropDown();
                return false;
            }
        });
        autoDoctorNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        autoDoctorNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputOrder = s.toString();
                    int total_string = inputOrder.length();

                    if (inputOrder.contains("-")) {
                        Log.e("SelectedDoctor ==>", inputOrder);
                        doc_code = inputOrder.substring(inputOrder.indexOf("-") + 1);
                        String[] first_split = inputOrder.split("-");
                        doc_name = first_split[0].trim();
                        autoDoctorNew.setText(doc_name);
                        //hideKeyBoard();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void giftAutocompleteEvent() {
        actv_gift.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //hideKeyBoard();
                actv_gift.showDropDown();
                return false;
            }
        });
        actv_gift.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        actv_gift.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                try {
                    final String inputorder = s.toString();
                    int total_string = inputorder.length();

                    if (inputorder.contains("-")) {
                        Log.e("SelectedGift ==>", inputorder);
                        String ppp_init = inputorder.substring(inputorder.indexOf("-") + 1);
                        String[] first_split_ppm = inputorder.split("-");
                        ppm_name = first_split_ppm[0].trim();
                        String second = ppp_init.trim();
                        String ppp_init2 = second.substring(second.indexOf("-") + 1);
                        ppm_prod_code = ppp_init2;
                        String[] second_split_ppm = second.split("-");
                        ppm_code = second_split_ppm[0].trim();
                        Log.e("ppm_name=>", ppm_name);
                        Log.e("ppm_prod_code=>", ppm_prod_code);
                        Log.e("ppm_code=>", ppm_code);
                        actv_gift.setText(ppm_name);

                        if (Tab_Flag.equals("G")) {
                            new GetDoctor().execute();
                        } else {
                            new GetDoctorforGift().execute();
                        }
                        buttonChoose.setEnabled(true);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {

            }
        });
    }

    private void brandselectEvent() {
        degree_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chiplayout.setBackgroundResource(R.drawable.bg_accent_rectangle_rounded_corners);
            }
        });
        degree_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //chiplayout.setBackgroundResource(R.drawable.bg_accent_rectangle_rounded_corners);
            }
        });
        degree_name.addLayoutTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        degree_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        degree_name.setOnChipItemChangeListener(new ChipLayout.ChipItemChangeListener() {
            int k = 0;

            @Override
            public void onChipAdded(int pos, String txt) {
                k++;
                selected_brand = k;

                if (selected_brand == 0) {
                    imageView.setImageResource(0);
                    gvGallery.setAdapter(null);
                    imageView.setVisibility(View.GONE);
                    gvGallery.setVisibility(View.GONE);
                } else if (selected_brand == 1) {
                    chipCountOne();
                } else {
                    chipCountMultiple();
                }
            }

            @Override
            public void onChipRemoved(int pos, String txt) {
                k--;
                selected_brand = k;
                if (selected_brand == 0) {
                    imageView.setImageResource(0);
                    gvGallery.setAdapter(null);
                    imageView.setVisibility(View.GONE);
                    gvGallery.setVisibility(View.GONE);
                } else if (selected_brand == 1) {
                    chipCountOne();
                } else {
                    chipCountMultiple();
                }
            }
        });
    }

    private void chipCountOne() {
        imageView.setImageResource(0);
        imageView.setVisibility(View.GONE);
        gvGallery.setVisibility(View.VISIBLE);
    }

    private void chipCountMultiple() {
        imageView.setVisibility(View.VISIBLE);
        gvGallery.setVisibility(View.GONE);
        gvGallery.setAdapter(null);
    }

    private void initSingleUpload() {
        imageView.setVisibility(View.VISIBLE);
        gvGallery.setVisibility(View.GONE);
        buttonmultiUpload.setVisibility(View.GONE);
        buttonUpload.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mImageUriList.clear();
            imagesEncodedList.clear();
            showFileChooserSingleOS13();
        } else {
            showFileChooserSingle();
        }
    }

    private void initMultiUpload() {
        imageView.setVisibility(View.GONE);
        gvGallery.setVisibility(View.VISIBLE);
        buttonmultiUpload.setVisibility(View.VISIBLE);
        buttonUpload.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (galleryAdapter != null) {
                mImageUriList.clear();
                imagesEncodedList.clear();
                galleryAdapter =  new GalleryAdapter(getApplicationContext(), mImageUriList);
                galleryAdapter.notifyDataSetChanged();
            }
            showFileChooserMultipleOS13();
        } else {
            showFileChooserMultiple();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        toolbar = findViewById(R.id.toolbar);
        buttonChoose = findViewById(R.id.buttonChoose);
        buttonCapture = findViewById(R.id.buttonCapture);
        btn_multi = findViewById(R.id.btn_multi);
        gvGallery = findViewById(R.id.gv);
        imageView = findViewById(R.id.imageView);
        btn_back = findViewById(R.id.back);
        btn_back.setTypeface(fontFamily);
        btn_back.setText("\uf08b");
        user_show1 = findViewById(R.id.user_show1);
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonmultiUpload = findViewById(R.id.buttonmultiUpload);
        session = new SessionManager(getApplicationContext());
        imageView.setVisibility(View.VISIBLE);
        gvGallery.setVisibility(View.VISIBLE);

        buttonUpload.setVisibility(View.VISIBLE);
        buttonUpload.setTypeface(fontFamily);
        buttonUpload.setText("\uf1d8");

        buttonmultiUpload.setTypeface(fontFamily);
        buttonmultiUpload.setText("\uf1d8");
        buttonmultiUpload.setVisibility(View.GONE);
        user_show1.setText(Dashboard.globalmpocode + " [ " + Dashboard.globalterritorycode + " ] ");
        myCommand = new MyCommand<>(getApplicationContext());

        degree_name = findViewById(R.id.chipText);
        tab = findViewById(R.id.tablayout);
        gift_tab = findViewById(R.id.tablayout2);
        tv_wardname = findViewById(R.id.tv_wardname);
        autoDoctorNew = findViewById(R.id.autoDoctorNew);
        actv_doc = findViewById(R.id.autoCompleteTextViewDoctor);
        actv_dept = findViewById(R.id.autoCompleteTextViewDept);
        customerlist = new ArrayList<Customer>();
        doctorListNew = new ArrayList<Customer>();
        departmentlist = new ArrayList<Customer>();
        giftlist = new ArrayList<Customer>();
        cust = findViewById(R.id.customer);
        gift = findViewById(R.id.gift);
        dept = findViewById(R.id.department);
        dept_name = "xxx";
        ward_name = "xxxx";
        imageView.setVisibility(View.GONE);
        gvGallery.setVisibility(View.GONE);

        gift_tab_layout = findViewById(R.id.gift_tab_layout);
        constrainlayout = findViewById(R.id.constrainlayout);
        actv_gift = findViewById(R.id.actv_gift);
    }

    private void populategiftlist() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < giftlist.size(); i++) {
            lables.add(giftlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        gift.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[lables.size()]);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        actv_gift.setThreshold(2);
        actv_gift.setAdapter(Adapter);
        actv_gift.setTextColor(Color.BLUE);
    }

    class GetGiftList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PrescriptionEntry.this);
            pDialog.setMessage("Fetching GiftList..");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", Dashboard.globalmpocode));
            params.add(new BasicNameValuePair("gift_tab_flag", GIFT_Tab_Flag));
            Log.e("ID==>", Dashboard.globalmpocode + "\n" + GIFT_Tab_Flag);
            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(URL_GIFT_LIST, ServiceHandler.POST, params);
            Log.e("JSONGift", json);
            giftlist.clear();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");

                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        giftlist.add(custo);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populategiftlist();
        }
    }

    private void populatedocforgift() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[0]);
        doc_adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        actv_doc.setThreshold(2);
        actv_doc.setAdapter(doc_adapter);
        actv_doc.setTextColor(Color.BLUE);
    }

    class GetDoctorforGift extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PrescriptionEntry.this);
            pDialog.setMessage("Fetching Doctors..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", Dashboard.globalmpocode));
            params.add(new BasicNameValuePair("ppm_prod_code", ppm_prod_code));
            params.add(new BasicNameValuePair("ppm_code", ppm_code));
            params.add(new BasicNameValuePair("gift_tab_flag", GIFT_Tab_Flag));
            params.add(new BasicNameValuePair("ppm_name", ppm_name));
            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(URL_GIFT_WISE_DOC_LIST, ServiceHandler.POST, params);
            customerlist.clear();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        customerlist.add(custo);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populatedocforgift();
        }
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < customerlist.size(); i++) {
            lables.add(customerlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        cust.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[0]);
        doc_adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        actv_doc.setThreshold(2);
        actv_doc.setAdapter(doc_adapter);
        actv_doc.setTextColor(Color.BLUE);
    }

    private void populateDoctorListNew() {
        List<String> labels = new ArrayList<String>();
        for (int i = 0; i < doctorListNew.size(); i++) {
            labels.add(doctorListNew.get(i).getName());
        }
        String[] customer = labels.toArray(new String[0]);
        ArrayAdapter<String> doc_adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        autoDoctorNew.setThreshold(2);
        autoDoctorNew.setAdapter(doc_adapter);
        autoDoctorNew.setTextColor(Color.BLUE);
    }

    class GetDoctor extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PrescriptionEntry.this);
            pDialog.setMessage("Fetching Doctors..");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", Dashboard.globalmpocode));
            ServiceHandler jsonParser = new ServiceHandler();
            Log.e("tab_flgPASSED==>", Tab_Flag);

            if (Tab_Flag.equals("D")) {
                json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
                Log.e("JSONDoctor", json);
            } else {
                params.add(new BasicNameValuePair("inst_for", inst_type));
                json = jsonParser.makeServiceCall(URL_INST, ServiceHandler.POST, params);
                Log.e("JSON opd", json);
            }
            customerlist.clear();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");

                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        customerlist.add(custo);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }
    }

    class GetNewDoctorList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogNew = new ProgressDialog(PrescriptionEntry.this);
            pDialogNew.setMessage("Fetching Doctors List..");
            pDialogNew.setCancelable(true);
            pDialogNew.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", Dashboard.globalmpocode));
            params.add(new BasicNameValuePair("inst_code", doc_code));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_DOCTOR_NEW, ServiceHandler.POST, params);
            doctorListNew.clear();
            Log.e("tab_flagPASSED==>", Tab_Flag);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");

                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        doctorListNew.add(custo);
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
            if (pDialogNew.isShowing())
                pDialogNew.dismiss();
            populateDoctorListNew();
        }
    }

    private void populateDept() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < departmentlist.size(); i++) {
            lables.add(departmentlist.get(i).getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, lables);
        dept.setAdapter(spinnerAdapter);
        String[] customer = lables.toArray(new String[0]);
        //ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, customer);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        actv_dept.setThreshold(2);
        actv_dept.setAdapter(Adapter);
        actv_dept.setTextColor(Color.BLUE);
    }

    class GetDept extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PrescriptionEntry.this);
            pDialog.setMessage("Fetching Department..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", doc_code));
            ServiceHandler jsonParser = new ServiceHandler();
            json = jsonParser.makeServiceCall(URL_DEPT_WARD, ServiceHandler.POST, params);
            Log.e("getDept==>", json);
            departmentlist.clear();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray customer = jsonObj.getJSONArray("customer");
                    for (int i = 0; i < customer.length(); i++) {
                        JSONObject catObj = (JSONObject) customer.get(i);
                        Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                        departmentlist.add(custo);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateDept();
        }
    }

    public void getDoctorDegree() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Patient>> call = apiInterface.getDoctorDegree();
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, retrofit2.Response<List<Patient>> response) {
                List<Patient> patientdetail = response.body();
                assert patientdetail != null;
                lables = new ArrayList<String>();
                for (int i = 0; i < patientdetail.size(); i++) {
                    lables.add(patientdetail.get(i).getFirst_name());
                }
                doc_degree = GetStringArray((ArrayList<String>) lables); // new String[]{"india", "australia", "austria", "indonesia", "canada"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PrescriptionEntry.this, R.layout.spinner_text_view, doc_degree);
                degree_name.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {

            }
        });
    }

    private void uploadImageOS13() {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Bitmap image = null;
        try {
            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUriList.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        encodedStringmulti = ImageBase64.encode(image);

        final ProgressDialog loading = ProgressDialog
                .show(this, "Uploading Prescription in Server...", "Please wait...", false, false);
        stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String name = jObj.getString("success");
                            String email = jObj.getString("message");
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                mImageUriList.clear();
                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                new AlertDialog.Builder(PrescriptionEntry.this).setTitle("Successful")
                                        .setMessage("Prescription has been submitted")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                                refresh();
                            } else {
                                refresh();
                                Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(PrescriptionEntry.this, PrescriptionEntry.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ServerError) {
                        } else if (error instanceof NetworkError) {
                        } else if (error instanceof ParseError) {} }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_IMAGE, encodedStringmulti);
                params.put("brand_names", degree_name.getText().toString().trim());
                params.put("mpo_code", Dashboard.globalmpocode);
                params.put("tab_flag", Tab_Flag);
                params.put("gift_tab_flag", GIFT_Tab_Flag);
                params.put("doc_code", doc_code);
                params.put("dept_name", dept_name);
                params.put("img_make", Build.BRAND);
                params.put("img_model", Build.MODEL);
                params.put("img_len", "ImageLength: 1600\\n");
                params.put("img_width", "ImageWidth: 720\\n");
                params.put("img_datetime", currentTime);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void uploadImage() {
        Log.e("img_datetime", img_datetime);
        if ((img_datetime.trim().equals("null"))) {
            Log.e("SecondValueNull-->", img_local_path.trim());
            img_datetime = img_local_path.trim();
        }
        final ProgressDialog loading = ProgressDialog
                .show(this, "Uploading Prescription in Server...", "Please wait...", false, false);

        String encodedImage;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            encodedImage = getStringImageOS13();
        } else {
            encodedImage = getStringImage(decoded);
        }

        if (!Objects.equals(img_make, "Make : null\n")) {
            if (img_make.toUpperCase().contains(brand_name.toUpperCase()) || img_make.toUpperCase().contains(manufacturer.toUpperCase())) {
                if (!Objects.equals(img_lat, "GPSLatitude : null\n") && !Objects.equals(img_long, "GPSLongitude : null\n")) {
                    stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jObj = new JSONObject(response);
                                        String name = jObj.getString("success");
                                        String email = jObj.getString("message");
                                        success = jObj.getInt(TAG_SUCCESS);

                                        if (success == 1) {
                                            Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                            new AlertDialog.Builder(PrescriptionEntry.this).setTitle("Successful")
                                                    .setMessage("Prescription has been submitted")
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                        }
                                                    }).show();
                                            refresh();
                                        } else {
                                            refresh();
                                            //Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                            Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    loading.dismiss();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    loading.dismiss();
                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                        Toast.makeText(PrescriptionEntry.this, PrescriptionEntry.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                                    } else if (error instanceof AuthFailureError) {
                                    } else if (error instanceof ServerError) {
                                    } else if (error instanceof NetworkError) {
                                    } else if (error instanceof ParseError) {} }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            //params.put(KEY_IMAGE, getStringImage(decoded));
                            params.put(KEY_IMAGE, encodedImage);
                            params.put("brand_names", degree_name.getText().toString().trim());
                            params.put("mpo_code", Dashboard.globalmpocode);
                            params.put("tab_flag", Tab_Flag);
                            params.put("gift_tab_flag", GIFT_Tab_Flag);
                            params.put("doc_code", doc_code);
                            params.put("dept_name", dept_name);
                            params.put("img_make", Build.BRAND);
                            params.put("img_model", Build.MODEL);
                            params.put("img_len", img_len);
                            params.put("img_width", img_width);
                            params.put("img_datetime", img_datetime);
                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            90000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
                } else {
                    refresh();
                    loading.dismiss();
                    imageCopyAlert("This image is captured by others Device. !!!");
                }
            } else {
                refresh();
                loading.dismiss();
                imageCopyAlert("This image is captured by others Device. !!!");
            }
        } else {
            refresh();
            loading.dismiss();
            imageCopyAlert("This image is downloaded from communication apps like messenger, whatsApp etc. !!!");
        }
    }

    private void uploadGiftImageOS13() {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Bitmap image = null;
        try {
            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUriList.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        encodedStringmulti = ImageBase64.encode(image);

        final ProgressDialog loading = ProgressDialog
                .show(this, "Uploading Prescription in Server...", "Please wait...", false, false);
        stringRequest = new StringRequest(Request.Method.POST, UPLOAD_Gift_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String name = jObj.getString("success");
                            String email = jObj.getString("message");
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                mImageUriList.clear();
                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                new AlertDialog.Builder(PrescriptionEntry.this).setTitle("Successful")
                                        .setMessage(" Prescription has been submitted")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {}
                                        }).show();
                                refresh();
                            } else {
                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(PrescriptionEntry.this, PrescriptionEntry.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ServerError) {
                        } else if (error instanceof NetworkError) {
                        } else if (error instanceof ParseError) {} }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_IMAGE, encodedStringmulti);
                params.put("ppm_code", ppm_code);
                params.put("brand_names", ppm_prod_code);
                params.put("ppm_name", ppm_name);
                params.put("mpo_code", Dashboard.globalmpocode);
                params.put("tab_flag", Tab_Flag);
                params.put("gift_tab_flag", GIFT_Tab_Flag);
                params.put("brand_code", ppm_prod_code);
                params.put("doc_code", doc_code);
                params.put("dept_name", dept_name);
                params.put("img_make", Build.BRAND);
                params.put("img_model", Build.MODEL);
                params.put("img_len", "ImageLength: 1600\n");
                params.put("img_width", "ImageWidth: 720\n");
                params.put("img_datetime", currentTime);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void uploadGiftImage() {
        Log.e("uploadGiftImage==>", "uploadGiftImage-->1448");
        Log.e("ppm_code", ppm_code);
        Log.e("brand_names", ppm_prod_code);
        Log.e("ppm_name", ppm_name);
        Log.e("mpo_code", Dashboard.globalmpocode);
        Log.e("tab_flag", Tab_Flag);
        Log.e("gift_tab_flag", GIFT_Tab_Flag);
        Log.e("brand_code", ppm_prod_code);
        Log.e("doc_code", doc_code);
        Log.e("dept_name", dept_name);
        Log.e("img_make", Build.BRAND);
        Log.e("img_model", Build.MODEL);
        Log.e("img_len", img_len);
        Log.e("img_width", img_width);
        Log.e("img_datetime", img_datetime);

        final ProgressDialog loading = ProgressDialog
                .show(this, "Uploading Prescription in Server...", "Please wait...", false, false);

        String encodedImage;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            encodedImage = getStringImageOS13();
        } else {
            encodedImage = getStringImage(decoded);
        }

        if (!Objects.equals(img_make, "Make : null\n")) {
            if (img_make.toUpperCase().contains(brand_name.toUpperCase()) || img_make.toUpperCase().contains(manufacturer.toUpperCase())) {
                if (!Objects.equals(img_lat, "GPSLatitude : null\n") && !Objects.equals(img_long, "GPSLongitude : null\n")) {
                    stringRequest = new StringRequest(Request.Method.POST, UPLOAD_Gift_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jObj = new JSONObject(response);
                                        String name = jObj.getString("success");
                                        String email = jObj.getString("message");
                                        success = jObj.getInt(TAG_SUCCESS);

                                        if (success == 1) {
                                            Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                            new AlertDialog.Builder(PrescriptionEntry.this).setTitle("Successful")
                                                    .setMessage(" Prescription has been submitted")
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {}
                                                    }).show();
                                            refresh();
                                        } else {
                                            Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                            Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    loading.dismiss();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    loading.dismiss();
                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                        Toast.makeText(PrescriptionEntry.this, PrescriptionEntry.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                                    } else if (error instanceof AuthFailureError) {
                                    } else if (error instanceof ServerError) {
                                    } else if (error instanceof NetworkError) {
                                    } else if (error instanceof ParseError) {} }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            //params.put(KEY_IMAGE, getStringImage(decoded));
                            params.put(KEY_IMAGE, encodedImage);
                            params.put("ppm_code", ppm_code);
                            params.put("brand_names", ppm_prod_code);
                            params.put("ppm_name", ppm_name);
                            params.put("mpo_code", Dashboard.globalmpocode);
                            params.put("tab_flag", Tab_Flag);
                            params.put("gift_tab_flag", GIFT_Tab_Flag);
                            params.put("brand_code", ppm_prod_code);
                            params.put("doc_code", doc_code);
                            params.put("dept_name", dept_name);
                            params.put("img_make", Build.BRAND);
                            params.put("img_model", Build.MODEL);
                            params.put("img_len", img_len);
                            params.put("img_width", img_width);
                            params.put("img_datetime", img_datetime);
                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            90000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
                } else {
                    refresh();
                    loading.dismiss();
                    imageCopyAlert("This image is captured by others Device. !!!");
                }
            } else {
                refresh();
                loading.dismiss();
                imageCopyAlert("This image is captured by others Device. !!!");
            }
        } else {
            refresh();
            loading.dismiss();
            imageCopyAlert("This image is downloaded from communication apps like messenger, whatsApp etc. !!!");
        }
    }

    private void uploadMultiImageOS13() {
        int j;
        String encodedImage;
        for (j = 0; j < mImageUriList.size(); j++) {
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUriList.get(j));
                encodedStringmulti = ImageBase64.encode(image);
                //encodedImage = getStringImageOS13();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String selectImagePath = getImagePath(mImageUriList.get(j));
            //String myimagepath = mImageUriList.get(j);
            Log.e("uploadMultiImageOS13-->", selectImagePath);
            ExifInterface exif2 = null;

            try {
                exif2 = new ExifInterface(selectImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert exif2 != null;
            img_make_multi = getTagString(ExifInterface.TAG_MAKE, exif2);
            img_lat_multi = getTagString(ExifInterface.TAG_GPS_LATITUDE, exif2);
            img_long_multi = getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif2);
            String img_model_multi = getTagString(ExifInterface.TAG_MODEL, exif2);
            String img_len_multi = getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif2);
            String img_width_multi = getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif2);
            String img_datetime_multi = getTagString(ExifInterface.TAG_DATETIME, exif2);
            String img_datetime_multi2 = img_datetime_multi.substring(img_datetime_multi.indexOf(':') + 1);
            String url = BASE_URL + "prescription_survey/image_upload_api/vector_pres_survey_web_multi_test.php";

            if (!Objects.equals(img_make_multi, "Make : null\n")) {
                if (img_make_multi.toUpperCase().contains(brand_name.toUpperCase()) || img_make_multi.toUpperCase().contains(manufacturer.toUpperCase())) {
                    if (!Objects.equals(img_lat_multi, "GPSLatitude : null\n") && !Objects.equals(img_long_multi, "GPSLongitude : null\n")) {
                        stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jObj = new JSONObject(response);
                                            String name = jObj.getString("success");
                                            String email = jObj.getString("message");
                                            success = jObj.getInt(TAG_SUCCESS);

                                            if (success == 1) {
                                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                                AutoDismiss();
                                                refresh();
                                            } else {
                                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                                Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Toast.makeText(PrescriptionEntry.this, PrescriptionEntry.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                                        } else if (error instanceof AuthFailureError) {
                                        } else if (error instanceof ServerError) {
                                        } else if (error instanceof NetworkError) {
                                        } else if (error instanceof ParseError) {} }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("image", encodedStringmulti);
                                params.put("countimage", String.valueOf(mImageUriList.size()));
                                params.put("brand_names", degree_name.getText().toString().trim());
                                params.put("mpo_code", Dashboard.globalmpocode);
                                params.put("tab_flag", Tab_Flag);
                                params.put("gift_tab_flag", GIFT_Tab_Flag);
                                params.put("doc_code", doc_code);
                                params.put("dept_name", dept_name);
                                params.put("img_make", Build.BRAND);
                                params.put("img_model", Build.MODEL);
                                params.put("img_len", img_len_multi);
                                params.put("img_width", img_width_multi);
                                params.put("img_datetime", img_datetime_multi2);
                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(90000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
                    } else {
                        imageCopyAlert("This image is captured by others Device. !!!");
                    }
                } else {
                    imageCopyAlert("This image is captured by others Device. !!!");
                }
            } else {
                imageCopyAlert("This image is downloaded from communication apps like messenger, whatsApp etc. !!!");
            }
        }
        gvGallery.setAdapter(null);
        imagesEncodedList.clear();
        mImageUriList.clear();
    }

    private void uploadMultiImage() {
        int j;
        for (j = 0; j < imagesEncodedList.size(); j++) {
            Log.d("imagesEncodedList-->", String.valueOf(j));

            try {
                Bitmap bitmap = PhotoLoader.init().from(imagesEncodedList.get(j)).requestSize(512, 512).getBitmap();
                encodedStringmulti = ImageBase64.encode(bitmap);
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
            }
            String myimagepath = imagesEncodedList.get(j);
            Log.e("myImagePathOnclick-->", myimagepath);
            ExifInterface exif2 = null;

            try {
                exif2 = new ExifInterface(myimagepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert exif2 != null;
            img_make_multi = getTagString(ExifInterface.TAG_MAKE, exif2);
            img_lat_multi = getTagString(ExifInterface.TAG_GPS_LATITUDE, exif2);
            img_long_multi = getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif2);
            String img_model_multi = getTagString(ExifInterface.TAG_MODEL, exif2);
            String img_len_multi = getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif2);
            String img_width_multi = getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif2);
            String img_datetime_multi = getTagString(ExifInterface.TAG_DATETIME, exif2);
            String img_datetime_multi2 = img_datetime_multi.substring(img_datetime_multi.indexOf(':') + 1);
            String url = BASE_URL + "prescription_survey/image_upload_api/vector_pres_survey_web_multi_test.php";

            if (!Objects.equals(img_make_multi, "Make : null\n")) {
                if (img_make_multi.toUpperCase().contains(brand_name.toUpperCase()) || img_make_multi.toUpperCase().contains(manufacturer.toUpperCase())) {
                    if (!Objects.equals(img_lat_multi, "GPSLatitude : null\n") && !Objects.equals(img_long_multi, "GPSLongitude : null\n")) {
                        stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jObj = new JSONObject(response);
                                            String name = jObj.getString("success");
                                            String email = jObj.getString("message");
                                            success = jObj.getInt(TAG_SUCCESS);

                                            if (success == 1) {
                                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                                AutoDismiss();
                                                refresh();
                                            } else {
                                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                                Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Toast.makeText(PrescriptionEntry.this, PrescriptionEntry.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                                        } else if (error instanceof AuthFailureError) {
                                        } else if (error instanceof ServerError) {
                                        } else if (error instanceof NetworkError) {
                                        } else if (error instanceof ParseError) {} }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                Log.e("multiImageDetails-->", img_make_multi + img_model_multi + img_len_multi + img_width_multi + img_datetime_multi2);

                                params.put("image", encodedStringmulti);
                                params.put("countimage", String.valueOf(imagesEncodedList.size()));
                                params.put("brand_names", degree_name.getText().toString().trim());
                                params.put("mpo_code", Dashboard.globalmpocode);
                                params.put("tab_flag", Tab_Flag);
                                params.put("gift_tab_flag", GIFT_Tab_Flag);
                                params.put("doc_code", doc_code);
                                params.put("dept_name", dept_name);
                                params.put("img_make", Build.BRAND);
                                params.put("img_model", Build.MODEL);
                                params.put("img_len", img_len_multi);
                                params.put("img_width", img_width_multi);
                                params.put("img_datetime", img_datetime_multi2);
                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(90000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
                    } else {
                        imageCopyAlert("This image is captured by others Device. !!!");
                    }
                } else {
                    imageCopyAlert("This image is captured by others Device. !!!");
                }
            } else {
                imageCopyAlert("This image is downloaded from communication apps like messenger, whatsApp etc. !!!");
            }
        }
        gvGallery.setAdapter(null);
        imagesEncodedList.clear();
    }

    private void imageCopyAlert(String message) {
        new AlertDialog.Builder(PrescriptionEntry.this).setTitle("Alert ! No Prescription to Upload ")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void uploadGiftMultiImageOS13() {
        int j;
        for (j = 0; j < mImageUriList.size(); j++) {
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUriList.get(j));
                encodedStringmulti = ImageBase64.encode(image);
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String selectImagePath = getImagePath(mImageUriList.get(j));
            Log.e("uploadMultiImageOS13-->", selectImagePath);
            ExifInterface exif2 = null;

            try {
                exif2 = new ExifInterface(selectImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert exif2 != null;
            img_make_multi = getTagString(ExifInterface.TAG_MAKE, exif2);
            img_lat_multi = getTagString(ExifInterface.TAG_GPS_LATITUDE, exif2);
            img_long_multi = getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif2);
            String img_model_multi = getTagString(ExifInterface.TAG_MODEL, exif2);
            String img_len_multi = getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif2);
            String img_width_multi = getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif2);
            String img_datetime_multi = getTagString(ExifInterface.TAG_DATETIME, exif2);
            String img_datetime_multi2 = img_datetime_multi.substring(img_datetime_multi.indexOf(':') + 1);
            String url = BASE_URL + "prescription_survey/image_upload_api/vector_pres_survey_gift_multi.php";


            if (!Objects.equals(img_make_multi, "Make : null\n")) {
                if (img_make_multi.toUpperCase().contains(brand_name.toUpperCase()) || img_make_multi.toUpperCase().contains(manufacturer.toUpperCase())) {
                    if (!Objects.equals(img_lat_multi, "GPSLatitude : null\n") && !Objects.equals(img_long_multi, "GPSLongitude : null\n")) {
                        stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jObj = new JSONObject(response);
                                            String name = jObj.getString("success");
                                            String email = jObj.getString("message");
                                            success = jObj.getInt(TAG_SUCCESS);

                                            if (success == 1) {
                                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                                AutoDismiss();
                                                refresh();
                                            } else {
                                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                                Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Toast.makeText(PrescriptionEntry.this, PrescriptionEntry.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                                        } else if (error instanceof AuthFailureError) {
                                        } else if (error instanceof ServerError) {
                                        } else if (error instanceof NetworkError) {
                                        } else if (error instanceof ParseError) {} }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("image", encodedStringmulti);
                                params.put("countimage", String.valueOf(mImageUriList.size()));
                                params.put("brand_names", degree_name.getText().toString().trim());
                                params.put("mpo_code", Dashboard.globalmpocode);
                                params.put("tab_flag", Tab_Flag);
                                params.put("gift_tab_flag", GIFT_Tab_Flag);
                                params.put("doc_code", doc_code);
                                params.put("dept_name", dept_name);
                                params.put("img_make", Build.BRAND);
                                params.put("img_model", Build.MODEL);
                                params.put("img_len", img_len_multi);
                                params.put("img_width", img_width_multi);
                                params.put("img_datetime", img_datetime_multi2);
                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                90000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
                    } else {
                        imageCopyAlert("This image is captured by others Device. !!!");
                    }
                } else {
                    imageCopyAlert("This image is captured by others Device. !!!");
                }
            } else {
                imageCopyAlert("This image is downloaded from communication apps like messenger, whatsApp etc. !!!");
            }
        }
        gvGallery.setAdapter(null);
        imagesEncodedList.clear();
        mImageUriList.clear();
        finish();
        startActivity(getIntent());
    }

    private void uploadGiftMultiImage() {
        Log.e("clicked==>", "uploadGiftMultiImage");

        int j;
        for (j = 0; j < imagesEncodedList.size(); j++) {
            try {
                Bitmap bitmap = PhotoLoader.init().from(imagesEncodedList.get(j)).requestSize(512, 512).getBitmap();
                encodedStringmulti = ImageBase64.encode(bitmap);
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT)
                        .show();
            }
            String myimagepath = imagesEncodedList.get(j);
            Log.e("myImagePathOnclick-->", myimagepath);
            ExifInterface exif2 = null;

            try {
                exif2 = new ExifInterface(myimagepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String img_make_gift_multi = getTagString(ExifInterface.TAG_MAKE, exif2);
            String img_lat_gift_multi = getTagString(ExifInterface.TAG_GPS_LATITUDE, exif2);
            String img_long_gift_multi = getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif2);
            String img_model_multi = getTagString(ExifInterface.TAG_MODEL, exif2);
            String img_len_multi = getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif2);
            String img_width_multi = getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif2);
            String img_datetime_multi = getTagString(ExifInterface.TAG_DATETIME, exif2);
            String img_datetime_multi2 = img_datetime_multi.substring(img_datetime_multi.indexOf(':') + 1);
            String url = BASE_URL + "prescription_survey/image_upload_api/vector_pres_survey_gift_multi.php";

            if (!Objects.equals(img_make_gift_multi, "Make : null\n")) {
                if (img_make_gift_multi.toUpperCase().contains(brand_name.toUpperCase()) || img_make_gift_multi.toUpperCase().contains(manufacturer.toUpperCase())) {
                    if (!Objects.equals(img_lat_gift_multi, "GPSLatitude : null\n") && !Objects.equals(img_long_gift_multi, "GPSLongitude : null\n")) {
                        stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jObj = new JSONObject(response);
                                            String name = jObj.getString("success");
                                            String email = jObj.getString("message");
                                            success = jObj.getInt(TAG_SUCCESS);

                                            if (success == 1) {
                                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                                AutoDismiss();
                                                refresh();
                                            } else {
                                                Toast.makeText(PrescriptionEntry.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                                Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            Toast.makeText(PrescriptionEntry.this, PrescriptionEntry.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                                        } else if (error instanceof AuthFailureError) {
                                        } else if (error instanceof ServerError) {
                                        } else if (error instanceof NetworkError) {
                                        } else if (error instanceof ParseError) {} }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                Log.e("multiimageDetails-->", img_make_multi + img_model_multi + img_len_multi + img_width_multi + img_datetime_multi2);
                                params.put("image", encodedStringmulti);
                                params.put("countimage", String.valueOf(imagesEncodedList.size()));
                                params.put("brand_names", degree_name.getText().toString().trim());
                                params.put("mpo_code", Dashboard.globalmpocode);
                                params.put("tab_flag", Tab_Flag);
                                params.put("gift_tab_flag", GIFT_Tab_Flag);
                                params.put("doc_code", doc_code);
                                params.put("dept_name", dept_name);
                                params.put("img_make", Build.BRAND);
                                params.put("img_model", Build.MODEL);
                                params.put("img_len", img_len_multi);
                                params.put("img_width", img_width_multi);
                                params.put("img_datetime", img_datetime_multi2);
                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                90000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
                    } else {
                        imageCopyAlert("This image is captured by others Device. !!!");
                    }
                } else {
                    imageCopyAlert("This image is captured by others Device. !!!");
                }
            } else {
                imageCopyAlert("This image is downloaded from communication apps like messenger, whatsApp etc. !!!");
            }
        }
        gvGallery.setAdapter(null);
        imagesEncodedList.clear();
        finish();
        startActivity(getIntent());
    }

    private void showFileChooserSingle() {
        ImagePicker.create(this)
                .single()
                .showCamera(false)
                .start();
    }

    private void showFileChooserMultiple() {
        ImagePicker.create(this)
                .multi()
                .showCamera(false)
                .limit(5)
                .start();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void showFileChooserSingleOS13() {
        int imageSelectLimit = 1;
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        intent.putExtra(MediaStore.ACTION_PICK_IMAGES, imageSelectLimit);
        startActivityForResult(intent, 102);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void showFileChooserMultipleOS13() {
        int imageSelectLimit = 5;
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, imageSelectLimit);
        startActivityForResult(intent, 102);
    }

    private void openCamera() {
        ImagePicker.cameraOnly().start(this);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000, true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }

    public static String[] storge_permissions = {
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
     };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storge_permissions_33;
        } else {
            p = storge_permissions;
        }
        return p;
    }

    @SuppressLint("Recycle")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Bitmap bitmap2 = Bitmap.createScaledBitmap(photo, 600, 600, true);
            imageView.setImageBitmap(bitmap2);
            setToImageView(photo);
            getStringImage(decoded);
            Uri _uri = getImageUri(getApplicationContext(), photo);
            String filePath = null;

            if (_uri != null && "content".equals(_uri.getScheme())) {
                Cursor cursor = this.getContentResolver().query(_uri, new String[] {
                        android.provider.MediaStore.Images.ImageColumns.DATA}, null, null, null);
                cursor.moveToFirst();
                filePath = cursor.getString(0);
                cursor.close();
            } else {
                assert _uri != null;
                filePath = _uri.getPath();
            }
            String filename = _uri.getLastPathSegment();
            Log.e("filePath-->", filename);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ShowExif(exif);
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            img_datetime = currentTime;
            Log.e("cameraCaptureTime-->", img_datetime);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (data != null) {
                    int count = data.getClipData().getItemCount();

                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        mImageUriList.add(imageUri);
                        imagesEncodedList.add(imageUri.getPath());
                        Log.d("imageUrl1-->", String.valueOf(imageUri));
                        Log.d("imageUrl2-->", String.valueOf(imageUri.getPath()));
                    }

                    if (mImageUriList.size() > 1) {
                        imageView.setVisibility(View.GONE);
                        gvGallery.setVisibility(View.VISIBLE);
                        buttonmultiUpload.setVisibility(View.VISIBLE);
                        buttonUpload.setVisibility(View.GONE);

                        galleryAdapter = new GalleryAdapter(getApplicationContext(), mImageUriList);
                        gvGallery.setAdapter(galleryAdapter);
                        gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery.getLayoutParams();
                        mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
                    }
                    else if (mImageUriList.size() == 1) {
                        imageView.setVisibility(View.VISIBLE);
                        gvGallery.setVisibility(View.GONE);
                        buttonmultiUpload.setVisibility(View.GONE);
                        buttonUpload.setVisibility(View.VISIBLE);
                        Uri imageUri = data.getClipData().getItemAt(0).getUri();
                        imageView.setImageURI(imageUri);

                        ClipData.Item clippedFile = data.getClipData().getItemAt(0);
                        Uri singleImgUri = clippedFile.getUri();
                        Log.d("imageUri1", String.valueOf(singleImgUri));
                        String selectImagePath = getImagePath(singleImgUri);
                        Log.d("imageUri2", String.valueOf(selectImagePath));

                        ExifInterface exif = null;
                        try {
                            exif = new ExifInterface(selectImagePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ShowExif(exif);
                    }
                }
                } else {
                    if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
                        List<Image> images = ImagePicker.getImages(data);
                        ArrayList<Uri> mArrayUri = new ArrayList<>();

                        for (Image image : images) {
                            mArrayUri.add(Uri.parse(image.getPath()));
                            imagesEncodedList.add(image.getPath());
                            Log.e("imageUrl1-->", String.valueOf(Uri.parse(image.getPath())));
                            Log.e("imageUrl2-->", String.valueOf(image.getPath()));
                        }

                        if (mArrayUri.size() > 1) { // multiple images selected
                            imageView.setVisibility(View.GONE);
                            gvGallery.setVisibility(View.VISIBLE);
                            buttonmultiUpload.setVisibility(View.VISIBLE);
                            buttonUpload.setVisibility(View.GONE);
                            Bitmap decodedBitmap = ImageUtil.getDecodedBitmap(mArrayUri.get(0).getPath(), 2048);
                            setToImageView(decodedBitmap);
                            galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery.getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);
                        }
                        else if (mArrayUri.size() == 1) {
                            imageView.setVisibility(View.VISIBLE);
                            gvGallery.setVisibility(View.GONE);
                            buttonmultiUpload.setVisibility(View.GONE);
                            buttonUpload.setVisibility(View.VISIBLE);
                            setToImageView(ImageUtil.getDecodedBitmap(mArrayUri.get(0).getPath(), 2048));
                            img_local_path = mArrayUri.get(0).getPath();
                            Log.e("imgLocalPath=>", img_local_path);

                            ExifInterface exif = null;
                            try {
                                exif = new ExifInterface(img_local_path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ShowExif(exif);
                        }
                    }
                }
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public String getImagePath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);

        if( cursor != null ){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return uri.getPath();
    }

    private void ShowExif(ExifInterface exif) {
        String myAttribute = "Exif information ---\n";
        myAttribute += getTagString(ExifInterface.TAG_DATETIME_ORIGINAL, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_TIMESTAMP, exif);
        myAttribute += getTagString(ExifInterface.TAG_DATETIME, exif);
        myAttribute += getTagString(ExifInterface.TAG_FLASH, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
        myAttribute += getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
        myAttribute += getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
        myAttribute += getTagString(ExifInterface.TAG_MAKE, exif);
        myAttribute += getTagString(ExifInterface.TAG_MODEL, exif);
        myAttribute += getTagString(ExifInterface.TAG_ORIENTATION, exif);
        myAttribute += getTagString(ExifInterface.TAG_WHITE_BALANCE, exif);
        img_make = getTagString(ExifInterface.TAG_MAKE, exif);
        img_lat = getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
        img_long = getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
        img_model = getTagString(ExifInterface.TAG_MODEL, exif);
        img_len = getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
        img_width = getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
        img_datetime = getTagString(ExifInterface.TAG_DATETIME, exif);
        img_datetime = img_datetime.substring(img_datetime.indexOf(':') + 1);
        Log.e("myimgtime-->", img_datetime);
        Log.e("myAttribute--->", myAttribute);
    }

    public static String[] GetStringArray(ArrayList<String> arr) {
        // declaration and initialise String Array
        String[] str = new String[arr.size()];
        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {
            // Assign each value to String array
            str[j] = arr.get(j);
        }
        return str;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public String getStringImageOS13() {
        Bitmap bitmapImage = null;
        try {
            bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUriList.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return ImageBase64.encode(bitmapImage);
    }

    private String getTagString(String tag, ExifInterface exif) {
        return (tag + " : " + exif.getAttribute(tag) + "\n");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable("imageUri", imageUri);
    }

    // Recover the saved state when the activity is recreated.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //imageUri= savedInstanceState.getParcelable("picUri");
    }

    private void setToImageView(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        imageView.setImageBitmap(decoded);
    }

    private void refresh() {
        imageView.setImageResource(0);
        gvGallery.setAdapter(null);
    }

    public void AutoDismiss() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PrescriptionEntry.this);
        builder.setTitle("Uploading Images")
                .setMessage("Uploaded Successfully")
                .setCancelable(false).setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //this for skip dialog
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        }, 50); // change 5000 with a specific time you want
    }

    private void hideKeyBoard() {
        // InputMethodManager imm = (InputMethodManager) context.getSystemService(PrescriptionImageSearch.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        InputMethodManager imm = (InputMethodManager) getSystemService(PrescroptionImageSearch.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}

