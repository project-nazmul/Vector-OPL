package com.opl.pharmavector.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class VectorUtils {
    public static final String googlePlayLink = "market://details?id=com.opl.pharmavector";
    public static final String alternativeLink = "https://play.google.com/store/apps/details?id=com.opl.pharmavector";

    public static void screenShotProtect(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }
}
