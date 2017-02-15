package com.holiestar.toolkit.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by user on 5/1/2015.
 */
public class UtilApp {
    private static Context context;
    public static void initialize(Context _context) {
        context = _context;
    }

    public static boolean isAppInstalled( String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static int getVersionCode( String packageName){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info  = manager.getPackageInfo(packageName, 0);
            int versionCode=info.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static String getVersionName() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void startApp(Context context, String packageName){
        try {
            PackageManager manager = context.getPackageManager();
            context.startActivity(manager.getLaunchIntentForPackage(packageName));
        } catch (Exception e) {
        }
    }

    public static boolean hasAndroidWear(){
        if(android.os.Build.VERSION.SDK_INT < 18){
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo("com.google.android.wearable.app", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
