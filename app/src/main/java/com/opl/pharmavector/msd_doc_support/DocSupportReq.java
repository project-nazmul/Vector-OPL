package com.opl.pharmavector.msd_doc_support;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.GalleryAdapter;
import com.opl.pharmavector.ImageUtil;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.Login;
import com.opl.pharmavector.MultiSelectionSpinner;
import com.opl.pharmavector.MultiSelectionSpinner2;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.app.AppController;
import com.opl.pharmavector.doctorgift.DocGiftDashBoard;
import com.opl.pharmavector.doctorgift.DocGiftEntryApproval;
import com.opl.pharmavector.giftfeedback.GiftFeedbackEntry;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.pcconference.PcProposal;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionEntry;
//import com.opl.pharmavector.remote.ApiClient;
//import com.opl.pharmavector.remote.ApiInterface;

import com.opl.pharmavector.network.ApiService;

import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import io.grpc.Context;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.opl.pharmavector.util.KeyboardUtils.hideKeyboard;

public class DocSupportReq extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    String json;
    ProgressDialog pDialog;
    private ArrayList<Customer> customerlist;
    Spinner cust;
    ArrayAdapter<String> doc_adapter;
    AutoCompleteTextView actv_doc;
    EditText tv_doc_chamber_address, doc_opl_db_address;
    Button btn_submit;
    public double distance;
    String current_lang, current_lat, doc_code, doc_name, doc_address;
    private String message;
    public String status = "N";
    ImageView imageView;
    Button buttonCapture,buttonGallery;
    Bitmap bitmap, decoded;
    int PICK_IMAGE_REQUEST = 2;
    int bitmap_size = 40; // range 1 - 100
    String tag_json_obj = "json_obj_req";
    ArrayList<String> imageList = new ArrayList<>();
    ArrayList<String> imagesEncodedList = new ArrayList<>();
    public String img_local_path,requirement_date;
    public StringRequest stringRequest;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    Button fab1;
    MultiSelectionSpinner2 multi_spinner;
    MultiAutoCompleteTextView m_actv;
    ArrayAdapter<String> service_adapter;
    int pos;
    EditText ded;
    String msd_services[]={"Articale/Journals","Up-To-Date DVD","Dr. Najeebs Lecture","Medical Books/Journals"};
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    String serviceno;
    String errormessage;
    String user_code,user_name,user_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msd_doc_support_req);

        initViews();
        multiSpinnerinitViews();
        permissionEvent();

        MultiSelectionSpinner2 multiSelectionSpinner = new MultiSelectionSpinner2(DocSupportReq.this);
        String onlick = multiSelectionSpinner.simple_adapter.toString();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (actv_doc.getText().toString().equals("") ){
                    errormessage ="Select Doctor";
                    showSnack();
                } else if (multi_spinner.getSelectedItem().toString().equals("Select Requirements")){
                    errormessage ="Select Doctor's Requirement";
                    showSnack();
                } else if (tv_doc_chamber_address.getText().toString().equals("")){
                    errormessage ="Select Requirement Description";
                    showSnack();
                }
               /* else if((imageView.getDrawable() == null)){
                    errormessage ="Select Image";
                    showSnack();
                }
*/
                else {
                    postuploadImage();
                    //Log.e("chambar_img", getStringImage(decoded));
                    Log.e("mpo_code", Dashboard.globalmpocode);
                    Log.e("doc_code", doc_code);
                    Log.e("gift_items",multi_spinner.getSelectedItem().toString());
                    Log.e("require_date",ded.getText().toString().trim());
                    Log.e("description",tv_doc_chamber_address.getText().toString().trim() );
                }
            }
        });
        
        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);
                openCamera();
            }
        });

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multi_spinner.getSelectedItem().toString().trim();
                initSingleUpload();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(DocSupportReq.this, Dashboard.class);
                i.putExtra("UserName", Dashboard.globalmpocode);
                i.putExtra("UserName_2", Dashboard.globalterritorycode);
                i.putExtra("new_version", Dashboard.new_version);
                i.putExtra("message_3", Dashboard.message_3);
                i.putExtra("password", Dashboard.password);
                i.putExtra("ff_type", Dashboard.ff_type);
                i.putExtra("vector_version", R.string.vector_version);
                i.putExtra("emp_code", Dashboard.globalempCode);
                i.putExtra("emp_name", Dashboard.globalempName);
                startActivity(i);
            }
        });
    }

    private void initSingleUpload() {
        imageView.setVisibility(View.VISIBLE);
        showFileChooserSingle();
    }

    private void multiSpinnerinitViews() {
        multi_spinner = findViewById(R.id.input1);
        List<String> list = new ArrayList<String>();
        list.add("Article/Journals");
        list.add("Up-To-Date DVD");
        list.add("Dr. Najeeb's Lecture");
        list.add("Medical Books/e-Books");
        list.add("Presentation");
        list.add("MedixBD e-Prescription");
        list.add("Opsonin e-Health Rx Apps");
        list.add("Banner/Invitation Card/Poster/Design");
        //list.add("Others");
        multi_spinner.setItems(list);
    }

    private void initViews(){
        Typeface fontFamily = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        tabLayout = findViewById(R.id.tablayout);
        customerlist = new ArrayList<Customer>();
        actv_doc = findViewById(R.id.autoCompleteTextViewDoctor);
        cust = findViewById(R.id.customer);
        tv_doc_chamber_address = findViewById(R.id.tv_doc_chamber_address);
        doc_opl_db_address = findViewById(R.id.doc_opl_db_address);
        btn_submit = findViewById(R.id.btn_submit);
        buttonCapture = findViewById(R.id.buttonCapture);
        buttonGallery = findViewById(R.id.buttonGallery);
        ded =  findViewById(R.id.date);
        multi_spinner = findViewById(R.id.input1);

        Bundle b = getIntent().getExtras();
        assert b != null;
        user_code = b.getString("user_code");
        user_name = b.getString("user_name");
        user_flag = b.getString("user_flag");
        fab1 = findViewById(R.id.back);
        fab1.setTypeface(fontFamily);
        fab1.setText("\uf060 "); // &#xf060
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(0);
        current_lang = Dashboard.track_lang;
        current_lat = Dashboard.track_lat;

        new GetDoctor().execute();
        doctorAutocompleteEvent();
        calendarInit();
    }

    private void calendarInit() {
        myCalendar = Calendar.getInstance();

        ded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(ded);
            }
        });
    }

    private void showDatePicker(EditText textView) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            textView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void postuploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Patient> call = null;

        if((imageView.getDrawable() == null)) {
             call = apiInterface.postmsdReq("AAAAAAAAA", Dashboard.globalmpocode,
                    doc_code, multi_spinner.getSelectedItem().toString(),
                    ded.getText().toString().trim(), tv_doc_chamber_address.getText().toString().trim());
        } else {
            call = apiInterface.postmsdReq(getStringImage(decoded), Dashboard.globalmpocode,
                    doc_code, multi_spinner.getSelectedItem().toString(),
                    ded.getText().toString().trim(), tv_doc_chamber_address.getText().toString().trim());
        }

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                progressDialog.dismiss();
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();
                serviceno = response.body().getSamplename();

                if (value.equals("1")) {
                    Log.e("oplResponse-->",serviceno);
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog();
                } else {
                    Toast.makeText(DocSupportReq.this, "Network Error, Please try later", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DocSupportReq.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public class ViewDialog {
        public void showDialog() {
            final Dialog dialog = new Dialog(DocSupportReq.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_alert);

            Button dialogButton = (Button) dialog.findViewById(R.id.read_btn);
            TextView message = (TextView) dialog.findViewById(R.id.message);
            TextView service = (TextView) dialog.findViewById(R.id.service);
            service.setText(serviceno);

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    refresh();
                    Intent i = new Intent(DocSupportReq.this, DocSupportReq.class);
                    i.putExtra("user_code", Dashboard.globalmpocode);
                    i.putExtra("user_name", Dashboard.globalterritorycode);
                    i.putExtra("user_flag", "MPO");
                    startActivity(i);
                }
            });
            dialog.show();
        }
    }

    private void refresh() {
        imageView.setImageResource(0);
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

        actv_doc.setOnClickListener(new View.OnClickListener() {
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
                        hideKeyboard(DocSupportReq.this);
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
    }

    class GetDoctor extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DocSupportReq.this);
            pDialog.setMessage("Fetching Doctors..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", Dashboard.globalmpocode));
            ServiceHandler jsonParser = new ServiceHandler();
            String URL_CUSOTMER = "http://opsonin.com.bd/vector_opl_v1/msd_doc_support/get_mpowise_doc.php";
            json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            Log.e("JSON Doctor", json);

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

    private void showSnack() {
        new Thread() {
            public void run() {
                DocSupportReq.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toasty.warning(getApplicationContext(), errormessage, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    private void submitMessage() {
        new Thread() {
            public void run() {
                DocSupportReq.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "Your Requisition is successfully submitted";
                        Toasty.warning(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();
                    }
                });
            }
        }.start();
    }

    private void permissionEvent() {
        ActivityCompat.requestPermissions(DocSupportReq.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

        if (ContextCompat.checkSelfPermission(DocSupportReq.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DocSupportReq.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(DocSupportReq.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }
    }

    private void showFileChooserSingle() {
        ImagePicker.create(this)
                .single()
                .showCamera(false)
                .start();
    }

    private void openCamera() {
        ImagePicker.cameraOnly().start(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            List<Image> images = ImagePicker.getImages(data);
            ArrayList<Uri> mArrayUri = new ArrayList<>();

            for (Image image : images) {
                mArrayUri.add(Uri.parse(image.getPath()));
                imagesEncodedList.add(image.getPath());
            }

            if (mArrayUri.size() > 1) { // multiple images selected
                imageView.setVisibility(View.GONE);
                Bitmap decodedBitmap = ImageUtil.getDecodedBitmap(mArrayUri.get(0).getPath(), 2048);
                setToImageView(decodedBitmap);
            } else if (mArrayUri.size() == 1) {// single image selected
                imageView.setVisibility(View.VISIBLE);
                setToImageView(ImageUtil.getDecodedBitmap(mArrayUri.get(0).getPath(), 2048));
                img_local_path = mArrayUri.get(0).getPath();

                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(img_local_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ShowExif(exif);
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

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void ShowExif(ExifInterface exif) {
        String myAttribute = "Exif information ---\n";
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
        Log.e("myAttribute==> ", myAttribute);
        /*
        img_make = getTagString(ExifInterface.TAG_MAKE, exif);
        img_model = getTagString(ExifInterface.TAG_MODEL, exif);
        img_len = getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
        img_width = getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
        img_datetime = getTagString(ExifInterface.TAG_DATETIME, exif);
        img_datetime = img_datetime.substring(img_datetime.indexOf(':') + 1);
        */
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

    private String getTagString(String tag, ExifInterface exif) {
        return (tag + " : " + exif.getAttribute(tag) + "\n");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}


