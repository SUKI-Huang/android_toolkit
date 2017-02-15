package com.holiestar.toolkit.utils;

/**
 * Created by tony1 on 2/14/2017.
 */

public class UtilDate {

    public static long getUnixTime(){
        return getUnixTime(System.currentTimeMillis());
    }

    public static long getUnixTime(long time){
        return time / 1000L;
    }
}
