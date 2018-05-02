package com.xueli.application.util;

import android.text.TextUtils;

import com.library.utils.DataUtil;
import com.xueli.application.mode.bean.user.User;

public class UserUtils {

    public static boolean isVip(User user) {
        return isVip1(user) || isVip2(user) || isVip3(user);
    }

    public static boolean isVip1(User user) {
        return !TextUtils.isEmpty(user.getMyCardEndTime1()) && System.currentTimeMillis() < DataUtil.date2TimeStamp(user.getMyCardEndTime1(), "yyyy-MM-dd");
    }

    public static boolean isVip2(User user) {
        return !TextUtils.isEmpty(user.getMyCardEndTime2()) && System.currentTimeMillis() < DataUtil.date2TimeStamp(user.getMyCardEndTime2(), "yyyy-MM-dd");
    }

    public static boolean isVip3(User user) {
        return !TextUtils.isEmpty(user.getMyCardEndTime3()) && System.currentTimeMillis() < DataUtil.date2TimeStamp(user.getMyCardEndTime3(), "yyyy-MM-dd");
    }
}
