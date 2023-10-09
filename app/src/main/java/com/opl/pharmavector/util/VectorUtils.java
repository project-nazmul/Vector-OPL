package com.opl.pharmavector.util;

import android.app.Activity;
import android.view.WindowManager;

public class VectorUtils {
    public static void screenShotProtect(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }
}
