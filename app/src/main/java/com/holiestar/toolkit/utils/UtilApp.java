package com.holiestar.toolkit.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;

/**
 * Created by user on 5/1/2015.
 */
public class UtilApp {
    private static Context context;

    public static void initialize(Context _context) {
        context = _context;
    }

    public static boolean isAppInstalled(String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static int getVersionCode(String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            int versionCode = info.versionCode;
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

    public static void startApp(Context context, String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            context.startActivity(manager.getLaunchIntentForPackage(packageName));
        } catch (Exception e) {
        }
    }

    public static boolean hasAndroidWear() {
        if (android.os.Build.VERSION.SDK_INT < 18) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo("com.google.android.wearable.app", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static int getkeyStoreHash() {
        try {
            int hashCode = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures[0].hashCode();
            return hashCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    private static String getFacebookKeyHash(String packageName) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyResult = new String(Base64.encode(md.digest(), 0));//String something = new String(Base64.encodeBytes(md.digest()));
                return keyResult;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
