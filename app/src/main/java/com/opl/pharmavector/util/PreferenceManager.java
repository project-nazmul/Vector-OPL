package com.opl.pharmavector.util;


import android.content.Context;
import android.content.SharedPreferences;
import com.opl.pharmavector.util.PreferenceConstants;

public class PreferenceManager {

    private Context mContext;

    public PreferenceManager(Context context) {

        this.mContext = context;
    }

    //get shared pref
    private SharedPreferences getPreferences() {
        return mContext.getSharedPreferences("Vector", Context.MODE_PRIVATE);
    }


    public void clearPreferences() {
        getPreferences().edit().clear().apply();

    }


    public void setcurrentVersion(String currentVersion) {
        getPreferences().edit().putString(
                PreferenceConstants.currentVersion,
                currentVersion
        ).apply();
    }

    public String getcurrentVersion() {
        return getPreferences().getString(
                PreferenceConstants.currentVersion, "50.1.7"
        );
    }




    public void setusername(String username) {
        getPreferences().edit().putString(
                PreferenceConstants.username,
                username
        ).apply();
    }

    public String getusername() {
        return getPreferences().getString(
                PreferenceConstants.username, "0"
        );
    }


    public void setpassword(String password) {
        getPreferences().edit().putString(
                PreferenceConstants.password,
                password
        ).apply();
    }


    public String getpassword() {
        return getPreferences().getString(
                PreferenceConstants.password, "0"
        );
    }


    public void setTasbihCounter(int tasbihCounter) {
        getPreferences().edit().putInt(
                PreferenceConstants.COUNTER,
                tasbihCounter
        ).apply();
    }


    public int getTasbihCounter() {
        return getPreferences().getInt(
                PreferenceConstants.COUNTER, 0
        );
    }

    public void setuserrole(String userrole) {
        getPreferences().edit().putString(
                PreferenceConstants.userrole,
                userrole
        ).apply();
    }

    public String getuserrole() {
        return getPreferences().getString(
                PreferenceConstants.userrole, "0"
        );
    }


    public void setuserdtl(String userdtl) {
        getPreferences().edit().putString(
                PreferenceConstants.userdtl,
                userdtl
        ).apply();
    }

    public String getuserdtl() {
        return getPreferences().getString(
                PreferenceConstants.userdtl, "0"
        );
    }

    public void setfftype(String fftype) {
        getPreferences().edit().putString(
                PreferenceConstants.fftype,
                fftype
        ).apply();
    }

    public String getfftype() {
        return getPreferences().getString(
                PreferenceConstants.fftype, "G"
        );
    }


    public void setexecutive_name(String executive_name) {
        getPreferences().edit().putString(
                PreferenceConstants.executive_name,
                executive_name
        ).apply();
    }

    public String getexecutive_name() {
        return getPreferences().getString(
                PreferenceConstants.executive_name, "PMD"
        );
    }

    public void setemp_code(String emp_code) {
        getPreferences().edit().putString(
                PreferenceConstants.emp_code,
                emp_code
        ).apply();
    }

    public String getemp_code() {
        return getPreferences().getString(
                PreferenceConstants.emp_code, "PMD"
        );
    }

    public void setAdmin_Code(String admin_code) {
        getPreferences().edit().putString(
                PreferenceConstants.admin_code,
                admin_code
        ).apply();
    }

    public String getAdmin_Code() {
        return getPreferences().getString(
                PreferenceConstants.admin_code, "PE03477"
        );
    }

    public void setAdmin_Name(String admin_name) {
        getPreferences().edit().putString(
                PreferenceConstants.admin_name,
                admin_name
        ).apply();
    }

    public String getAdmin_Name() {
        return getPreferences().getString(
                PreferenceConstants.admin_name, "Admin"
        );
    }

    public void setAdmin_Terri(String admin_terri) {
        getPreferences().edit().putString(
                PreferenceConstants.admin_terri,
                admin_terri
        ).apply();
    }

    public String getAdmin_Terri() {
        return getPreferences().getString(
                PreferenceConstants.admin_terri, "National"
        );
    }
}
