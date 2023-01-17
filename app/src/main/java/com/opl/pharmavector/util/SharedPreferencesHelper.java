package com.opl.pharmavector.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class SharedPreferencesHelper {

	private static final String PREFS_FILE_NAME = "salematrix";
	private static final String max = "max";

	public static int get_max(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getInt(SharedPreferencesHelper.max, 0);
	}

	public static void set_max(final Context ctx, final int max) {
		final SharedPreferences prefs = ctx.getSharedPreferences(SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putInt(SharedPreferencesHelper.max, max);
		editor.commit();
	}

}
