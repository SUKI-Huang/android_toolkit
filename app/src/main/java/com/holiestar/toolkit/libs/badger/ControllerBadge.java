package com.holiestar.toolkit.libs.badger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by tony1 on 3/2/2017.
 */

public class ControllerBadge {
    private static ControllerBadge controllerBadge;
    private static Context context;
    private SharedPreferences setting;

    private final String SETTING_NAME = "badgeCount";
    private final String SETTING_COUNT = "Count";

    public static void initialize(Context _context){
        context=_context;
    }

    private ControllerBadge(Context context) {
        this.context = context.getApplicationContext();
        this.setting = context.getSharedPreferences(SETTING_NAME, 0);

    }

    public static ControllerBadge getInstance() {
        if (controllerBadge == null) {
            controllerBadge = new ControllerBadge(context);
        }
        return controllerBadge;
    }

    public void addCount() {
        int count=getCount()+1;
        setCount(count);
        ShortcutBadger.applyCount(context, count);
        Log.i(getClass().getSimpleName(),"addCount:"+count);
    }

    public void clear() {
        setCount(0);
        ShortcutBadger.applyCount(context, 0);
        Log.i(getClass().getSimpleName(), "clear");
    }

    private int getCount() {
        return setting.getInt(SETTING_COUNT,0);
    }

    public void setCount(int count){
        setting.edit().putInt(SETTING_COUNT,count).apply();
    }

}