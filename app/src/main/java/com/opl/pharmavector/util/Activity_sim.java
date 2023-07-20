package com.opl.pharmavector.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.opl.pharmavector.Dashboard;

import java.util.Objects;

public final class Activity_sim {
	@SuppressLint({"MissingPermission", "HardwareIds"})
	private static String getMyPhoneNumber( ){
	    TelephonyManager mTelephonyMgr;
	    mTelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		assert mTelephonyMgr != null;
		return mTelephonyMgr.getLine1Number();
	}

	private static TelephonyManager getSystemService(String telephonyService) {
		return null;
	}

	private static String getMy11DigitPhoneNumber(){
	    String s = getMyPhoneNumber();
	    return s != null && s.length() > 2 ? s.substring(2) : null;
	}	

	public static boolean isSim(){
		return !Objects.requireNonNull(getMy11DigitPhoneNumber()).isEmpty();
	}
}
