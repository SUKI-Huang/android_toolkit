package com.holiestar.toolkit.utils;

import android.os.Build;

/**
 * Created by user on 5/1/2015.
 */
public class UtilSystem {

    public static int getApiLevel(){
        return Build.VERSION.SDK_INT;
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
