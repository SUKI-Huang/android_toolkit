package com.holiestar.toolkit.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.TypedValue;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by Suki on 6/15/2016.
 */
public class UtilString {

    private static Context context;

    public static void initialize(Context _context) {
        context = _context;
    }

    public static int getMaxLength(List<String> strs){
       return getMaxLength((String[]) strs.toArray());
    }

    public static int getMaxLength(String[] strs){
        if(strs==null){
            return 0;
        }
        int maxLength=0;
        for(int i=0;i<strs.length;i++){
            int length=strs[i].length();
            if(length>maxLength){
                maxLength=length;
            }
        }
        return maxLength;
    }

    public static String getMaxWidthText(List<String> strs,int sp){
        return getMaxWidthText((String[]) strs.toArray(),sp);
    }

    public static String getMaxWidthText(String[] strs, int sp) {
        String result = "";
        float maxWidth = 0;

        for (int i = 0; i < strs.length; i++) {
            float width = getWidth(strs[i], sp, Typeface.DEFAULT);
            if (width > maxWidth) {
                maxWidth = width;
                result = strs[i];
            }
        }
        return result;
    }

    public static float getWidth(String str, int sp, Typeface typeface) {
        Paint paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        paint.setAntiAlias(true);
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()));
        return paint.measureText(str);
    }

    public static float getHeight(String str, int sp, Typeface typeface) {
        Paint paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        paint.setAntiAlias(true);
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()));
        float textHeight = paint.descent()+paint.ascent();
        return textHeight;
    }

    public static String getEncodeUTF8(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            return str;
        }
    }

    public static String getMD5_8Char(String str,int maxLength){
      return getMD5(str,8);
    }

    public static String getMD5(String str,int maxLength){
        String md5=getMD5(str);
        if(md5.length()>maxLength){
            return md5.substring(0,maxLength);
        }else{
            return md5;
        }
    }

    public static String getMD5(String str) {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for(int i = 0; i < charArray.length; i++)
        {
            byteArray[i] = (byte)charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for( int i = 0; i < md5Bytes.length; i++)
        {
            int val = ((int)md5Bytes[i])&0xff;
            if(val < 16)
            {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().substring(8, 24);// 16位
    }
}
