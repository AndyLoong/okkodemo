package com.gionee.okko.utils;

/**
 * Created by xiaozhilong on 18-1-2.
 */

public class Logger {
    private static final String PREFIX = "OKKOLOG_";
    private static boolean DEBUG = true;

    public void setLoggable(boolean loggable) {
        DEBUG = loggable;
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(PREFIX + tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(PREFIX + tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(PREFIX + tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(PREFIX + tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(PREFIX + tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(PREFIX + tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(PREFIX + tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(PREFIX + tag, msg, tr);
        }
    }
}
