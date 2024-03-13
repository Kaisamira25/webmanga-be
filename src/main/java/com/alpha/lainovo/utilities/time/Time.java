package com.alpha.lainovo.utilities.time;

import java.util.Calendar;
import java.util.Date;

public class Time {
    public static Date getTheTimeRightNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        return new Date(calendar.getTime().getTime());
    }

    public static Date getTheTimeWhenTokenExpiration(int expirationTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
