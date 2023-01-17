

package com.opl.pharmavector.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.opl.pharmavector.Dashboard;


public final class Activity_sim {
	
	

	private static String getMyPhoneNumber( ){
	    TelephonyManager mTelephonyMgr;
	    mTelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
	    return mTelephonyMgr.getLine1Number();
	}

	private static TelephonyManager getSystemService(String telephonyService) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getMy11DigitPhoneNumber(){
	    String s = getMyPhoneNumber();
	    return s != null && s.length() > 2 ? s.substring(2) : null;
	}	

	public static boolean isSim(){
		if (getMy11DigitPhoneNumber().isEmpty()) {
			return false;
		}
		else {
			return true;
		}
		
	}



	
}
