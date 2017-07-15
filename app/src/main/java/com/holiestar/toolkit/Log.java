package com.holiestar.toolkit;

/**
 * Created by Suki on 7/15/2017.
 */

public class Log {

    public static void d(String tag, String message) {
        android.util.Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        android.util.Log.i(tag, message);
    }

    public static void e(String tag, String message) {
        android.util.Log.e(tag, message);
    }

    public static void e(String tag, String message, Exception e) {
        android.util.Log.e(tag, message, e);
    }
}
