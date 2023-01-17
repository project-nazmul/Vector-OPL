package com.opl.pharmavector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class AmDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 8;
    // Database Name
    private static final String DATABASE_NAME = "localdatabase";


    // Contacts table sd_mpo
    private static final String TABLE_MPO = "SD_MPO";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_SNAME = "sname";
    private static final String KEY_PH_NO = "phone_number";


    private static final String TABLE_CUSTOMERS = "CUSTOMERS";

    private static final String c_id = "id";
    private static final String c_name = "name";
    private static final String c_cust = "customer";
    private static final String c_mpo = "mpo";




    private static final String TABLE_PRODUCTS = "PRODUCTS";

    private static final String KEY_SL = "sl";
    private static final String KEY_PRODUCT_CODE = "PRODUCT_CODE";
    private static final String KEY_PRODUCT_NAME = "PRODUCT_NAME";
    private static final String KEY_PROD_RATE = "PROD_RATE";
    private static final String KEY_PROD_VAT = "PROD_VAT";
    private static final String KEY_PROD_QUANT = "Quantity";



    private static final String TABLE_ORDMAIN = "ORDMAIN";

    private static final String ORD_ID = "id";
    private static final String ORD_NO = "ORD_NO";
    private static final String CUST_CODE = "CUST_CODE";
    private static final String MPO_CODE = "MPO_CODE";
    private static final String ORD_DATE = "ORD_DATE";

    private static final String DELI_DATE = "DELI_DATE";
    private static final String DELI_TIME = "DELI_TIME";
    private static final String SHIFT_STAT = "SHIFT_STAT";
    private static final String PAY_MODE = "PAY_MODE";


    private static final String TABLE_ORDITEM = "ORDITEM";
    private static final String ORD_ITEM_ID = "id";
    private static final String ORD_NO_ITEM = "ORD_NO";
    private static final String P_CODE = "P_CODE";
    private static final String PROD_RATE = "PROD_RATE";
    private static final String ORD_QNTY = "ORD_QNTY";
    private static final String ENTER_DT_ITEM = "ENTER_DT";
    private static final String PROD_VAT = "PROD_VAT";



    private static final String TABLE_DCRDATE = "DCRDATE";
    private static final String d_id = "id";
    private static final String d_date = "date";



    private static final String TABLE_DCRDATERANGE = "DCRDATERANGE";
    private static final String d_idrange = "mpo_code";
    private static final String d_daterange = "date_range";
    private static final String d_specificdate = "specific_dates";








    public AmDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_MPO + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FNAME + " TEXT,"
                + KEY_SNAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_CONTACTS);


        String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_SL + " INTEGER PRIMARY KEY,"
                + KEY_PROD_QUANT + " TEXT,"
                + KEY_PRODUCT_CODE + " TEXT,"
                + KEY_PRODUCT_NAME + " TEXT,"
                + KEY_PROD_RATE + " TEXT,"
                + KEY_PROD_VAT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_PRODUCTS);


        String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + TABLE_CUSTOMERS + "("
                + c_id + " INTEGER PRIMARY KEY,"
                + c_name + " TEXT,"
                + c_cust + " TEXT,"
                + c_mpo + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_CUSTOMERS);



        String CREATE_TABLE_ORDMAIN = "CREATE TABLE " + TABLE_ORDMAIN + "("
                + ORD_ID + " integer primary key autoincrement not null,"
                + ORD_NO + " TEXT,"
                + CUST_CODE + " TEXT,"
                + MPO_CODE + " TEXT,"
                + ORD_DATE + " TEXT,"
                + DELI_DATE + " TEXT,"
                + DELI_TIME + " TEXT,"
                + SHIFT_STAT + " TEXT,"
                + PAY_MODE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_ORDMAIN);



        String CREATE_TABLE_ORDITEM = "CREATE TABLE " + TABLE_ORDITEM + "("
                + ORD_ITEM_ID + " integer primary key autoincrement not null,"
                + ORD_NO_ITEM + " TEXT,"
                + P_CODE + " TEXT,"
                + PROD_RATE + " TEXT,"
                + ORD_QNTY + " TEXT,"
                + ENTER_DT_ITEM + " TEXT,"
                + PROD_VAT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_ORDITEM);




        String CREATE_TABLE_DCRDATE = "CREATE TABLE " + TABLE_DCRDATE + "("
                + d_id + " TEXT,"

                + d_date + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_DCRDATE);




        String CREATE_TABLE_DCRDATERANGE = "CREATE TABLE " + TABLE_DCRDATERANGE + "("
                + d_idrange + " TEXT,"
                + d_specificdate + " TEXT,"
                + d_daterange + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_DCRDATERANGE);





    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MPO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDMAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DCRDATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DCRDATERANGE);

        onCreate(db);

    }


    public void addProducts(Product product) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PRODUCT_CODE, product.getproductcode());
        values.put(KEY_PRODUCT_NAME, product.getproductname());
        values.put(KEY_PROD_RATE, product.getproductrate());
        values.put(KEY_PROD_VAT, product.getproductvat());
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }


    public void addCustomers(OfflineCustomer customer) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(c_mpo, customer.getCCUST());
        values.put(c_cust, customer.getCMPO());
        values.put(c_name, customer.getCName());
        db.insert(TABLE_CUSTOMERS, null, values);
      //  db.insert(TABLE_ORDMAIN, null, values);
        db.close();
    }


    public void addDcrDate(DcrDate dcrDate) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();


        values.put(d_id, dcrDate.getCName());
        values.put(d_date, dcrDate.getCCUST());


        db.insert(TABLE_DCRDATE, null, values);
        //  db.insert(TABLE_ORDMAIN, null, values);
        db.close();
    }




    public void addDcrDateRange (DcrDate dcrDate) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();


        values.put(d_idrange, dcrDate.getCName());
        values.put(d_specificdate, dcrDate.getCCUST());
        values.put(d_daterange, dcrDate.getCMPO());

        db.insert(TABLE_DCRDATERANGE, null, values);
        //  db.insert(TABLE_ORDMAIN, null, values);
        db.close();
    }







    public void Ordmain(Ordmain ordmain) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ORD_NO, ordmain.getordno());
        values.put(CUST_CODE, ordmain.getcustcode());
        values.put(MPO_CODE, ordmain.getmpocode());
        values.put(ORD_DATE, ordmain.getdelidate());
        values.put(DELI_DATE, ordmain.getdelidate());
        values.put(DELI_TIME, ordmain.getdelitime());
        values.put(SHIFT_STAT, ordmain.getshiftstat());
        values.put(PAY_MODE, ordmain.getpaymode());

        db.insert(TABLE_ORDMAIN,null,values);
        db.close();
    }

    public void Orditem(Orditem orditem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ORD_NO_ITEM, orditem.getitemordno());
        values.put(P_CODE, orditem.prodcode());
        values.put(PROD_RATE, orditem.prodrate());
        values.put(ORD_QNTY, orditem.ordquant());
        values.put(PROD_VAT, orditem.prodvat());
        db.insert(TABLE_ORDITEM,null,values);
        db.close();
    }

    public void updateOrditem(Orditem orditem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ORD_NO_ITEM, orditem.getitemordno());
        values.put(P_CODE, orditem.prodcode());
        values.put(PROD_RATE, orditem.prodrate());
        values.put(ORD_QNTY, orditem.ordquant());
        values.put(PROD_VAT, orditem.prodvat());

        String ord_to_update=   (orditem.getitemordno());

        Log.w("updateOrditem", String.valueOf(values)+"order no"+ord_to_update) ;


      //  db.execSQL(" DELETE  FROM "+ TABLE_ORDITEM + " WHERE " + ORD_NO + " = '"+ ord_to_update +"'" );

        db.insert(TABLE_ORDITEM,null,values);


      //  db.update(TABLE_ORDITEM, values, ORD_NO_ITEM + " IN ( ?, ?, ?, ?)", new String[]{String.valueOf(orditem.getitemordno()),  "id1", "id2", "id3"});


        db.close();
    }






    //Insert values to the table contacts
    public void addContacts(com.opl.pharmavector.Contact contact) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, contact.getFName());
        values.put(KEY_SNAME, contact.getSName());
        values.put(KEY_PH_NO, contact.gettname());


        db.insert(TABLE_MPO, null, values);
        db.close();

    }



    public void addTerritory(String tname) {

        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE SD_MPO SET phone_number = '"+tname+"' WHERE id = "+ 1;
        db.execSQL(strSQL);
        db.close();


    }



    public void logindata() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MPO);
    }


    public void deleteProductdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE  FROM " + TABLE_PRODUCTS);
    }


    public void deleteCustomerdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CUSTOMERS);
    }



    public void deleteDcrdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DCRDATE);
    }



    public void deleteDaterange() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DCRDATERANGE);
    }


    public void deleteOfflineordermain( String ordno) {
        SQLiteDatabase db = this.getWritableDatabase();


          Log.w("Offline order number",ordno);
          db.execSQL(" DELETE  FROM "+ TABLE_ORDMAIN + " WHERE " + ORD_NO + " = '"+ ordno +"'" );
          db.close();


    }

    public void deleteOfflineorderitem( String ordno) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.w("Offline order number",ordno);
        db.execSQL("DELETE  FROM "+ TABLE_ORDITEM + " WHERE " + ORD_NO + " = '"+ordno+"'");
        db.close();
    }



    public boolean checkUserLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "select * from SD_MPO where fname='" + username + "' and sname='" + password + "'";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }










    public boolean checkOrdNo( ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "select date from DCRDATE ";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }




    public boolean checkdaterange( ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "select date_range from DCRDATERANGE ";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }






    public ArrayList<String> getValues(String table) {
        ArrayList<String> values = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT START_DATE FROM  " + TABLE_DCRDATERANGE, null);

        if(cursor.moveToFirst()) {
            do {
                values.add(cursor.getString(cursor.getColumnIndex("value")));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return values;
    }






    public ArrayList<String> getALLCUSTOMERS() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        // Select All Query
        try {
            String selectQuery = "SELECT  name FROM " + TABLE_CUSTOMERS;


            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    String customer_name = cursor.getString(cursor.getColumnIndex("name"));
                    list.add(customer_name);
                }

            }

        } catch (Exception e){
       e.printStackTrace();

    }
finally {
            db.endTransaction();
            db.close();
        }
        return list;
        }




    public ArrayList<String> getterritoryname() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        // Select All Query
        try {
            String selectQuery = "SELECT  phone_number FROM " + TABLE_MPO;


            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    String territory_name = cursor.getString(cursor.getColumnIndex("phone_number"));
                    list.add(territory_name);
                }

            }

        } catch (Exception e){
            e.printStackTrace();

        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }



    public ArrayList<String> getOfflineOrdDtl() {

        ArrayList<String> o_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT  ORD_NO,CUST_CODE,SHIFT_STAT,DELI_TIME,PAY_MODE,DELI_DATE FROM  ORDMAIN" ;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String ord_no = cursor.getString(cursor.getColumnIndex("ORD_NO"));
                    String cst_code = cursor.getString(cursor.getColumnIndex("CUST_CODE"));
                    String deli_time = cursor.getString(cursor.getColumnIndex("DELI_TIME"));
                    String pay_mode = cursor.getString(cursor.getColumnIndex("PAY_MODE"));
                    String deli_date = cursor.getString(cursor.getColumnIndex("DELI_DATE"));
                    String ord_dtl= ord_no+"/"+cst_code+deli_date+"///"+pay_mode;

                    o_list.add(ord_dtl);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return o_list;
    }




    public ArrayList<test> getProductdetails()
    {
        ArrayList<test> array_list = new ArrayList<test>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {

            String selectQuery = "SELECT SL,PRODUCT_NAME, PRODUCT_CODE,PROD_RATE,PROD_VAT FROM PRODUCTS" ;

            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                String product_sl = cursor.getString(cursor.getColumnIndex("sl"));
                String product_code = cursor.getString(cursor.getColumnIndex("PRODUCT_CODE"));
                String product_name = cursor.getString(cursor.getColumnIndex("PRODUCT_NAME"));
                String product_rate =  cursor.getString(cursor.getColumnIndex("PROD_RATE"));
                String product_vat = cursor.getString(cursor.getColumnIndex("PROD_VAT"));
                String product_quant ="0" ;//cursor.getString(cursor.getColumnIndex("Quantity"));


                array_list.add(new test(product_sl,product_code,product_quant,product_name,product_rate,product_vat));

            }
            // }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return array_list;
    }



    public ArrayList<test1> getsavedProductdetails(String ord_no)
    {
        ArrayList<test1> array_list2 = new ArrayList<test1>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();


        try {



            String selectQuery =  "SELECT  PRODUCTS.SL,PRODUCTS.PRODUCT_CODE,PRODUCTS.PRODUCT_NAME,PRODUCTS.PROD_RATE,PRODUCTS.PROD_VAT, ifnull(ORDITEM.ORD_QNTY,0) AS ORD\n" +
                    "FROM PRODUCTS\n" +
                    "LEFT JOIN ORDITEM\n" +
                    "ON PRODUCTS.PRODUCT_Code = ORDITEM.P_CODE\n" +
                    "AND ORDITEM.ORD_NO= '" + ord_no + "' \n " +

                    "ORDER BY PRODUCTS.Product_NAME";


            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {


                String product_sl =    cursor.getString(cursor.getColumnIndex("sl"));
                String product_code =  cursor.getString(cursor.getColumnIndex("PRODUCT_CODE"));
                String product_name =  cursor.getString(cursor.getColumnIndex("PRODUCT_NAME"));
                String product_rate =  cursor.getString(cursor.getColumnIndex("PROD_RATE"));
                String product_vat =   cursor.getString(cursor.getColumnIndex("PROD_VAT"));
                String product_quant =  cursor.getString(cursor.getColumnIndex("ORD"));

                Log.d("product_sl",""+product_sl);
                Log.d("product_sl",""+product_sl+"=>"+product_code+"=>"+product_name+"=>"+product_quant);

                array_list2.add(new test1(product_sl,product_code,product_quant,product_name,product_rate,product_vat));

            }
            // }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return array_list2;
    }















}








