package com.yumin.mp3encoder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;

public class GLESUtils {

    public static boolean isSupportGLES20(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >=0x20000
                || ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Android SDK built for x86")));
    }
}
