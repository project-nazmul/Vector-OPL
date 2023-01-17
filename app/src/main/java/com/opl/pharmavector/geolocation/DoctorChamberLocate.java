package com.opl.pharmavector.geolocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.app.AppController;
import com.opl.pharmavector.doctorgift.DocGiftDashBoard;
import com.opl.pharmavector.doctorgift.DocGiftEntryApproval;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionEntry;
//import com.opl.pharmavector.remote.ApiClient;
//import com.opl.pharmavector.remote.ApiInterface;

import com.opl.pharmavector.network.ApiClient;
import com.opl.pharmavector.network.ApiService;

import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;
import static com.opl.pharmavector.util.KeyboardUtils.hideKeyboard;

public class DoctorChamberLocate extends AppCompatActivity {

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
    Button buttonCapture;
    Bitmap bitmap, decoded;
    int PICK_IMAGE_REQUEST = 2;
    int bitmap_size = 40; // range 1 - 100
    String tag_json_obj = "json_obj_req";
    ArrayList<String> imageList = new ArrayList<>();
    ArrayList<String> imagesEncodedList = new ArrayList<>();
    public String img_local_path;
    public StringRequest stringRequest;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String UPLOAD_URL = BASE_URL+"geoloc/insert_gps_vector.php";
    Button fab1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_chamber);
        initViews();
        permissionEvent();


        btn_submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (imageView.getDrawable() == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(DoctorChamberLocate.this).setTitle("Alert ! No Image is selected")
                                    .setMessage("Please Capture Doctors Chambar picture to submit")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                        }
                    });

                }else {
                    if (distance < 950) {
                        status = "Y";
                        // postGpsLoc();
                        submitMessage();
                        uploadImage();
                    } else {
                        status = "P";
                        showSnack();
                        Log.e("KEY_IMAGE==>", getStringImage(decoded));

                        uploadImage();
                    }
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


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initViews() {
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
        fab1 = findViewById(R.id.back);
        fab1.setTypeface(fontFamily);
        fab1.setText("\uf060 ");// &#xf060

        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(0);
        current_lang = Dashboard.track_lang;
        current_lat = Dashboard.track_lat;
        tv_doc_chamber_address.setText(Dashboard.track_add);
        tv_doc_chamber_address.setClickable(false);
        tv_doc_chamber_address.setEnabled(false);

        new GetDoctor().execute();
        doctorAutocompleteEvent();
    }

    private void uploadImage() {
        final ProgressDialog loading = ProgressDialog
                .show(this, "Uploading Prescription in Server...", "Please wait...", false, false);

        stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String name = jObj.getString("success");
                            String email = jObj.getString("message");
                            success = jObj.getInt(TAG_SUCCESS);
                            if (success == 1) {
                                Toast.makeText(DoctorChamberLocate.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                new AlertDialog.Builder(DoctorChamberLocate.this).setTitle("Succesful")
                                        .setMessage(" Doctor Location has been submitted")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();

                                refresh();

                            } else {
                                Toast.makeText(DoctorChamberLocate.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                Log.e("error_message==>", jObj.getString(TAG_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loading.dismiss();

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(DoctorChamberLocate.this, DoctorChamberLocate.this.getString(R.string.error_network_timeout), Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
                            //TODO
                        } else if (error instanceof NetworkError) {

                            //TODO
                        } else if (error instanceof ParseError) {
                            //TODO
                        }

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("chambar_img", getStringImage(decoded));
                params.put("mpo_code", Dashboard.globalmpocode);
                params.put("doc_code", doc_code);
                params.put("doc_address", Dashboard.track_add);
                params.put("track_lang", String.valueOf(Double.parseDouble(Dashboard.track_lang)));
                params.put("track_lat", String.valueOf(Double.parseDouble(Dashboard.track_lat)));
                params.put("gps_status", status);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);


    }



    private void refresh() {
        imageView.setImageResource(0);

    }
    private void doctorAutocompleteEvent() {
        actv_doc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // hideKeyBoard();
                actv_doc.showDropDown();
                return false;
            }
        });
        actv_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        actv_doc.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(final Editable s) {
                // TODO Auto-generated method stub
                try {

                    final String inputorder = s.toString();
                    int total_string = inputorder.length();
                    if (inputorder.indexOf("-") != -1) {
                        Log.e("SelectedDoctor ==>", inputorder);
                        doc_code = inputorder.substring(inputorder.indexOf("-") + 1);
                        String[] first_split = inputorder.split("-");
                        doc_name = first_split[0].trim();
                        actv_doc.setText(doc_name);
                        hideKeyboard(DoctorChamberLocate.this);
                        new callserver().execute();
                    } else {

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void length() {
                // TODO Auto-generated method stub
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
        String[] customer = lables.toArray(new String[lables.size()]);
        doc_adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, customer);
        actv_doc.setThreshold(2);
        actv_doc.setAdapter(doc_adapter);
        actv_doc.setTextColor(Color.BLUE);
    }

    class GetDoctor extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DoctorChamberLocate.this);
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
            String URL_CUSOTMER = "http://opsonin.com.bd/vector_opl_v1/geoloc/get_mpowise_doc.php";
            json = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            Log.e("JSON Doctor", json);

            customerlist.clear();
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray customer = jsonObj.getJSONArray("customer");
                        for (int i = 0; i < customer.length(); i++) {
                            JSONObject catObj = (JSONObject) customer.get(i);
                            Customer custo = new Customer(catObj.getInt("id"), catObj.getString("name"));
                            customerlist.add(custo);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }

    }

    @SuppressLint("StaticFieldLeak")
    class callserver extends AsyncTask<Void, Void, Void> {
        final JSONParser jsonParser = new JSONParser();
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(DoctorChamberLocate.this, "Showing OPL Saved Address", "Wait....", true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            String URL_DOC_ADDRESS = "http://opsonin.com.bd/vector_opl_v1/geoloc/get_doc_address.php";
            params.add(new BasicNameValuePair("doc_code", doc_code));
            JSONObject json = jsonParser.makeHttpRequest(URL_DOC_ADDRESS, "POST", params);
            if (json != null) {
                try {
                    doc_address = json.getString("ADDRESS");
                    Geocoder coder = new Geocoder(DoctorChamberLocate.this);
                    List<Address> address;
                    LatLng p1 = null;
                    LatLng p2 = null;
                    try {
                        doc_address = doc_address.trim();
                        address = coder.getFromLocationName(doc_address, 30);
                        if (address == null) {
                            Log.e("address==>", String.valueOf(address));
                        }
                        assert address != null;
                        Address location = address.get(0);
                        p1 = new LatLng(location.getLatitude(), location.getLongitude());
                        double lat1 = location.getLatitude();
                        double lat2 = location.getLongitude();
                        Location startPoint = new Location("locationA");
                        startPoint.setLatitude(lat1); //selected doctor lat
                        startPoint.setLongitude(lat2); //selected doctor long
                        Location endPoint = new Location("locationA");
                        endPoint.setLatitude(Double.parseDouble(Dashboard.track_lat));  //mycurrent Lat
                        endPoint.setLongitude(Double.parseDouble(Dashboard.track_lang)); //my current lang
                        distance = startPoint.distanceTo(endPoint);
                        getAddress(Double.parseDouble(Dashboard.track_lat), Double.parseDouble(Dashboard.track_lang));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Thread backthred = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        if (!NetInfo.isOnline(getBaseContext())) {
                        } else {

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    doc_opl_db_address.setText(doc_address);
                                    doc_opl_db_address.setEnabled(false);
                                    doc_opl_db_address.setClickable(false);
                                }
                            });


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            });
            backthred.start();

        }
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(DoctorChamberLocate.this, Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocation(lat, lng, 30);
            Address obj = addresses.get(0);
            String opl_add = obj.getAddressLine(0);
            opl_add = opl_add + "\n" + obj.getCountryName();
            opl_add = opl_add + "\n" + obj.getCountryCode();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void getLocationFromAddress(DoctorChamberLocate context, String strAddress) {
        Geocoder coder = new Geocoder(DoctorChamberLocate.this);
        List<Address> address;
        LatLng p1 = null;
        try {

            strAddress = doc_address;
            strAddress = strAddress.trim();

            address = coder.getFromLocationName(strAddress, 30);
            if (address == null) {
                return;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
            Log.e("opllocationfromfunc==>", String.valueOf(p1));


        } catch (IOException ex) {

            ex.printStackTrace();
        }

    }


    private void showSnack() {
        new Thread() {
            public void run() {
                DoctorChamberLocate.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "Your submitted Chamber location in under Verification.";
                        Toasty.warning(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();

                    }
                });
            }
        }.start();

    }

    private void submitMessage() {
        new Thread() {
            public void run() {
                DoctorChamberLocate.this.runOnUiThread(new Runnable() {
                    public void run() {
                        String message;
                        message = "Your Location is successfully submitted";
                        Toasty.warning(getApplicationContext(), message, Toast.LENGTH_LONG, true).show();

                    }
                });
            }
        }.start();

    }

    private void permissionEvent() {
        ActivityCompat.requestPermissions(DoctorChamberLocate.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

        if (ContextCompat
                .checkSelfPermission(DoctorChamberLocate.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(DoctorChamberLocate.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(DoctorChamberLocate.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }

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

            if (mArrayUri.size() > 1) {// multiple images selected
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
                //ShowExif(exif);
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

