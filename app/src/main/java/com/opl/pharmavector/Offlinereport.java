package com.opl.pharmavector;

import static com.opl.pharmavector.remote.ApiClient.BASE_URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.opl.pharmavector.order_online.ReadComments;
import com.opl.pharmavector.util.NetInfo;

import org.apache.http.NameValuePair;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Offlinereport extends Activity implements View.OnClickListener {
    public String userName_1, userName, userName_2;
    JSONParser jsonParser;
    List<NameValuePair> params;
    public AutoCompleteTextView actv;
    private ListView lv, lv2;
    private ProgressDialog pDialog;
    private DatabaseHandler db;
    private static String url = BASE_URL+"get_products_offline.php";
    private String URL_CUSOTMER = BASE_URL+"get_customer.php";
    private String TAG = Offlinereport.class.getSimpleName();
    ArrayList<HashMap<String, String>> productList;
    ArrayList<HashMap<String, String>> customerlist;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offlinereport);
        Button offlineproduct_btn = (Button) findViewById(R.id.offlineproduct);
        Button offlineustomer_btn = (Button) findViewById(R.id.offcustomer);
        Button offlineorder_btn = (Button) findViewById(R.id.offlineorder);
        Button offlineupdateorder_btn = (Button) findViewById(R.id.offlineupdateorder);
        Button editofflineorder_btn = (Button) findViewById(R.id.editofflineorder);
        Button offlineback_btn = (Button) findViewById(R.id.offlineback);
        Button home_btn = (Button) findViewById(R.id.homepage);
        lv = (ListView) findViewById(R.id.list);
        lv2 = (ListView) findViewById(R.id.list2);
        Button gooffline_btn = (Button) findViewById(R.id.gooffline);
        db = new DatabaseHandler(this);
        Bundle b = getIntent().getExtras();
        final String userName = b.getString("UserName");
        final String UserName_2 = b.getString("UserName_2");

        if (!NetInfo.isOnline(getBaseContext())) {
            offlineproduct_btn.setVisibility(View.GONE);
            offlineustomer_btn.setVisibility(View.GONE);
            offlineupdateorder_btn.setVisibility(View.GONE);

        } else {

            offlineproduct_btn.setVisibility(View.VISIBLE);
            offlineustomer_btn.setVisibility(View.VISIBLE);
            offlineupdateorder_btn.setVisibility(View.VISIBLE);
        }

        offlineback_btn.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(Offlinereport.this, Offlinereadcomments.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Offlinereport.this, ReadComments.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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
        home_btn.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(Offlinereport.this, Dashboard.class);
                                i.putExtra("UserName", Dashboard.globalmpocode);
                                i.putExtra("new_version", Dashboard.new_version);
                                i.putExtra("UserName_2", Dashboard.globalterritorycode);
                                i.putExtra("message_3", Dashboard.message_3);
                                i.putExtra("password", Dashboard.password);
                                i.putExtra("ff_type", Dashboard.ff_type);
                                i.putExtra("vector_version", R.string.vector_version);
                                i.putExtra("emp_code", Dashboard.globalempCode);
                                i.putExtra("emp_name", Dashboard.globalempName);

                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Offlinereport.this, Dashboard.class);
                                i.putExtra("UserName", Dashboard.globalmpocode);
                                i.putExtra("new_version", Dashboard.new_version);
                                i.putExtra("UserName_2", Dashboard.globalterritorycode);
                                i.putExtra("message_3", Dashboard.message_3);
                                i.putExtra("password", Dashboard.password);
                                i.putExtra("ff_type", Dashboard.ff_type);
                                i.putExtra("vector_version", R.string.vector_version);
                                i.putExtra("emp_code", Dashboard.globalempCode);
                                i.putExtra("emp_name", Dashboard.globalempName);

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
        gooffline_btn.setOnClickListener(new View.OnClickListener() {
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
                                Intent i = new Intent(Offlinereport.this, Offlinereadcomments.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
                                startActivity(i);
                            } else {
                                ArrayList<String> UserName_2 = db.getterritoryname();
                                String user = UserName_2.toString();
                                Intent i = new Intent(Offlinereport.this, Offlinereadcomments.class);
                                i.putExtra("UserName", userName);
                                i.putExtra("UserName_2", user);
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
        offlineproduct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetInfo.isOnline(getBaseContext())) {
                    Toast.makeText(getBaseContext(), "Internet conection failed",
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(getBaseContext(), "Connect to Internet to Update Products",
                            Toast.LENGTH_LONG).show();
                } else {
                    productList = new ArrayList<>();
                    new GetProducts().execute();
                }
            }
        });
        offlineorder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            Intent i = new Intent(Offlinereport.this, Offlinereadcomments.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_2", userName_2);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();


            }
        });
        offlineupdateorder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String userName_1 = b.getString("UserName_1");
                            String userName_2 = b.getString("UserName_2");
                            Intent i = new Intent(Offlinereport.this, Offlineupdateview.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName_1);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backthred.start();
            }
        });
        editofflineorder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                Thread backthred = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            Bundle b = getIntent().getExtras();
                            String userName = b.getString("UserName");
                            String userName_1 = b.getString("UserName_1");
                            String userName_2 = b.getString("UserName_2");
                            Intent i = new Intent(Offlinereport.this, EditOffline.class);
                            i.putExtra("UserName", userName);
                            i.putExtra("UserName_1", userName_1);
                            i.putExtra("new_version", userName);
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                backthred.start();

            }
        });
        offlineustomer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetInfo.isOnline(getBaseContext())) {
                    Toast.makeText(getBaseContext(), "Internet conection failed",
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(getBaseContext(), "Connect to Internet to Update Customers",
                            Toast.LENGTH_LONG).show();
                } else {
                    customerlist = new ArrayList<>();
                    new GetCustomers().execute();
                }
            }
        });
    }

    private class GetProducts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Bundle b = getIntent().getExtras();
            String id = b.getString("UserName");
            pDialog = new ProgressDialog(Offlinereport.this);
            pDialog.setMessage("Updating product offline...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray contacts = jsonObj.getJSONArray("categories");
                    Log.e("offlineprod-->", String.valueOf(contacts));
                    db.deleteProductdata();
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String sl = c.getString("sl");
                        String code = c.getString("id");
                        String name = c.getString("name");
                        String prod_rate = c.getString("PROD_RATE");
                        String prod_vat = c.getString("PROD_VAT");
                        HashMap<String, String> contact = new HashMap<>();
                        contact.put("sl", sl);
                        contact.put("id", code);
                        contact.put("name", name);
                        contact.put("PROD_RATE", prod_rate);
                        contact.put("PROD_VAT", prod_vat);
                        productList.add(contact);
                        db.addProducts(new Product(code, name, prod_rate, prod_vat));
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    Offlinereport.this, productList,
                    R.layout.list_item, new String[]{"sl", "id", "name", "PROD_RATE", "PROD_VAT"}, new int[]{R.id.sl, R.id.product_code, R.id.product_name, R.id.product_rate, R.id.product_vat});
            lv.setAdapter(adapter);
        }

    }

    private class GetCustomers extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Offlinereport.this);
            pDialog.setMessage("Updating Customers...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Bundle b = getIntent().getExtras();
            String id = b.getString("UserName");
            Log.e("Offline Id----->",id);
            pDialog.show();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            ServiceHandler jsonParser = new ServiceHandler();
            String jsonStr2 = jsonParser.makeServiceCall(URL_CUSOTMER, ServiceHandler.POST, params);
            if (jsonStr2 != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr2);
                    JSONArray customers = jsonObj.getJSONArray("customer");
                    db.deleteCustomerdata();
                    for (int i = 0; i < customers.length() - 2; i++) {
                        JSONObject c = customers.getJSONObject(i);
                        String cust_id = c.getString("id");
                        String cust_name = c.getString("name");
                        String cust = c.getString("cust");
                        String mpo = c.getString("mpo");
                        HashMap<String, String> customer = new HashMap<>();
                        customer.put("id", cust_id);
                        customer.put("name", cust_name);
                        customer.put("cust", cust);
                        customer.put("mpo", mpo);
                        customerlist.add(customer);
                        db.addCustomers(new OfflineCustomer(cust_name, cust, mpo));
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Customer Updated" + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


            ListAdapter adapter = new SimpleAdapter(
                    Offlinereport.this, customerlist,
                    R.layout.list_item, new String[]{"id", "name", "cust", "mpo"}, new int[]{R.id.sl,
                    R.id.product_code, R.id.product_name, R.id.product_rate});


            lv2.isFastScrollEnabled();
            lv2.setAdapter(adapter);


        }

    }

    @Override
    public void onClick(View v) {
    }
    protected void onPostExecute() {
    }

}
