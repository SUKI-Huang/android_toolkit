package com.holiestar.toolkit.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SsuChi on 3/4/2015.
 */
public class UtilNetwork {
    private static Context context;

    public static void initialize(Context _context) {
        context = _context;
    }

    public static boolean isAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null) && activeNetworkInfo.isConnected();
    }

    public static int getConnectionType() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            return activeNetwork.getType();
        }
        return -1;
    }

    public static boolean isWiFi(){
        return getConnectionType()==ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isMobile(){
        return getConnectionType()==ConnectivityManager.TYPE_MOBILE;
    }

}
