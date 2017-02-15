package com.holiestar.toolkit.utils;

import android.content.Context;

import java.util.Locale;

/**
 * Created by user on 5/2/2015.
 */
public class UtilLanguage {
    private static Context context;
    public static void initialize(Context _context){
        context=_context.getApplicationContext();
    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage()+ context.getResources().getConfiguration().locale.getCountry();
    }

    public static boolean isZhCHT(){
        String lan=getLanguage();
        return lan.contains("zhTW")||lan.contains("zhHK");
    }

    public static boolean isZhCHS(){
        String lan=getLanguage();
        if(lan.contains("zh")){
            if(lan.contains("zhTW")||lan.contains("zhHK")){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    public static boolean isZh(){
        return getLanguage().contains("zh");
    }

    public static boolean isZhTW(){
        return getLanguage().contains("zhTW");
    }

    public static boolean isZhCN(){
        return getLanguage().contains("zhCN");
    }

    public static boolean isJaJP(){
        return getLanguage().contains("jaJP");
    }

    public static boolean isKoKR(){
        return getLanguage().contains("koKR");
    }

    public static boolean isEn(){
        return getLanguage().contains("en");
    }

    public static boolean isTh(){
        return getLanguage().contains("th");
    }

    public static String getVTLanguage() {
        String ln= Locale.getDefault().getLanguage();
        if(ln.contains("zh")){
            return "zh_tw";
        }else if(ln.contains("ja")){
            return "ja";
        }else if(ln.contains("vi")){
            return "vi";
        }else{
            return "en";
        }
    }

}
