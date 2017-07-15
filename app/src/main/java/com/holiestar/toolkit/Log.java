package com.holiestar.toolkit;

/**
 * Created by Suki on 7/15/2017.
 */

public class Log {

    public static void d(String tag, String message, Object... args) {
        android.util.Log.d(tag, String.format(message, args));
    }

    public static void i(String tag, String message, Object... args) {
        android.util.Log.i(tag, String.format(message, args));
    }

    public static void e(String tag, Exception e) {
        android.util.Log.e(tag, e.getMessage(), e);
    }

    public static void e(String tag, String message, Exception e) {
        android.util.Log.e(tag, message, e);
    }
}
