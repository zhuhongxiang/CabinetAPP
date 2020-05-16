package com.example.cabinetapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class TimeUtil {

    public static String getCurrentTime()
    {
        long currenttime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(currenttime);
        String currentTimeStr = dateFormat.format(date);
        return currentTimeStr;
    }

    public static String getCurrentDate()
    {
        long currenttime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(currenttime);
        String currentTimeStr = dateFormat.format(date);
        return currentTimeStr;
    }
}
