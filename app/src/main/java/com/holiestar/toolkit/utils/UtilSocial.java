package com.holiestar.toolkit.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;


public class UtilSocial {


    public static void goGooglePlay(Context context, String PackageName) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + PackageName));
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?" + PackageName));
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static void goUrl(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Could not open link.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void shareUrl(Context context, String url,String shareTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, shareTitle));
        try {
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception e) {
        }
    }


    public static void goFacebook(Context context, String accountName) {
        Intent i = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            if (packageInfo.versionCode > 3002850) {
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/" + accountName));
            } else {
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + accountName));
            }
            context.startActivity(i);
        } catch (Exception e) {
            i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + accountName));
            context.startActivity(i);
        }
    }

    public static void goInstagram(Context context, String accountName) {
        String urlInstagram = "http://instagram.com/_u/" + accountName;
        boolean hasInstallIG;
        try {
            context.getPackageManager().getPackageInfo("com.instagram.android", 0);
            hasInstallIG=true;
        } catch (PackageManager.NameNotFoundException e) {
            hasInstallIG=false;
        }

        if (hasInstallIG) {
            try {
                Intent i = context.getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                i.setComponent(new ComponentName("com.instagram.android", "com.instagram.android.activity.UrlHandlerActivity"));
                i.setData(Uri.parse(urlInstagram));
                context.startActivity(i);
            } catch (Exception e) {

                Uri uri = Uri.parse(urlInstagram);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        } else {
            Uri uri = Uri.parse(urlInstagram);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
        try {
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception e) {
        }

    }

    public static void goLine(Context context,String accountName) {
        Intent i = null;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info  = manager.getPackageInfo("jp.naver.line.android", 0);
            i = new Intent(Intent.ACTION_VIEW, Uri.parse("line://ti/p/@"+accountName));
        } catch (Exception e) {
            e.printStackTrace();
            i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://line.me/ti/p/@"+accountName));
        }
        if (i == null) {
            return;
        }
        context.startActivity(i);
    }


}
