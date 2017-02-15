package com.holiestar.toolkit.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Suki on 6/17/2016.
 */
public class UtilClipboard {
    public static void copyToClipboard(Context context, String str){
        if(str==null){
            return;
        }
        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setText(str);
    }
}
