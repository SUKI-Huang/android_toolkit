package com.holiestar.toolkit.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.util.UUID;

/**
 * Created by user on 5/1/2015.
 */
public class UtilSystem {

    private static Context context;

    public static void initialize(Context _context) {
        context = _context;
    }

    public static int getApiLevel(){
        return Build.VERSION.SDK_INT;
    }

    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getBrand(){
        return Build.MANUFACTURER;
    }

    public static String getDeviceModel(){
        return Build.MODEL;
    }

    public static String getUUID() {
        String uuid="";
        String androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            //device before android 2.2 always 9774d56d682e549c
            if (!androidId.equals("9774d56d682e549c")) {
                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
            } else {
                uuid = UUID.randomUUID().toString();
            }
        } catch (Exception e) {
            uuid = UUID.randomUUID().toString();
        }
        return uuid;
    }

    private static Boolean isMarshmallowOrHigher;
    public static boolean isMarshmallowOrHigher(){
        if(isMarshmallowOrHigher==null){
            isMarshmallowOrHigher= new Boolean(Build.VERSION.SDK_INT >= 23);
        }
        return isMarshmallowOrHigher;
    }

    private static Boolean isLollipopOrHigher;
    public static boolean isLollipopOrHigher(){
        if(isLollipopOrHigher==null){
            isLollipopOrHigher= new Boolean(Build.VERSION.SDK_INT >= 21);
        }
        return isLollipopOrHigher;
    }

    private static Boolean isKitkatOrHigher;
    public static boolean isKitkatOrHigher(){
        if(isKitkatOrHigher==null){
            isKitkatOrHigher= new Boolean(Build.VERSION.SDK_INT >= 19);
        }
        return isKitkatOrHigher;
    }

}
