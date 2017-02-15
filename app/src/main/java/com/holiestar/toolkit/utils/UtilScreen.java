package com.holiestar.toolkit.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by SsuChi on 2015/1/12.
 */
public class UtilScreen {
    private static Context context;
    private static Float density;
    private static Double inches;

    public static void initialize(Context _context){
        context=_context.getApplicationContext();
    }

    public static boolean isTablet() {
        try {
            return (context.getResources().getConfiguration().screenLayout
                    & Configuration.SCREENLAYOUT_SIZE_MASK)
                    >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        } catch (Exception e) {
            return false;
        }
    }

    public static float getStatusBarHeight() {
        float result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getScreenLargerSide() {
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if(height>width){
            return height;
        }else{
            return width;
        }
    }

    public static int getScreenSmallerSide() {
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if(width>height){
            return height;
        }else{
            return width;
        }
    }

    public static float getDensity() {
        if(density==null){
            density=context.getResources().getDisplayMetrics().density;
        }
        return density;
    }

    public static int sp2px(float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    public static float dp2Pix(int dp){
       return dp*getDensity();
   }

    public static float dp2Pix(float dp){
        return dp*getDensity();
    }

    public static int getOrientation(){
        return  context.getResources().getConfiguration().orientation;
    }

    public static double getInches(){
        if(inches!=null){
            return inches;
        }
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        window.getDefaultDisplay().getMetrics(displayMetrics);
        Display display = window.getDefaultDisplay();
        double x = Math.pow(display.getWidth()/displayMetrics.xdpi,2);
        double y = Math.pow(display.getHeight()/displayMetrics.ydpi,2);
        double screenInches = Math.sqrt(x + y);
        screenInches =  (double)Math.round(screenInches * 10) / 10;
        inches=screenInches;
        return inches;
    }

}
