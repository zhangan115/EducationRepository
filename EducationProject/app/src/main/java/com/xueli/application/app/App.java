package com.xueli.application.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.bugly.crashreport.CrashReport;
import com.xueli.application.base.AbsBaseApp;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.api.Api;
import com.xueli.application.mode.bean.user.User;
import com.google.gson.Gson;
import com.library.utils.Base64Util;
import com.library.utils.SPHelper;
import com.xueli.application.view.login.LoginActivity;

import java.io.UnsupportedEncodingException;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * application
 * Created by pingan on 2017/11/30.
 */

public class App extends AbsBaseApp {

    private static App _instance;
    private User mUser;//当前用户
    private Toast toast;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        CrashReport.initCrashReport(getApplicationContext(), "4b0d84c0f4", true);
    }

    public static App getInstance() {
        return _instance;
    }

    public void editHost(String host) {
        SPHelper.write(this, ConstantStr.USER_INFO, ConstantStr.APP_HOST, host);
    }

    @Override
    public String AppHost() {
        return SPHelper.readString(this, ConstantStr.USER_INFO, ConstantStr.APP_HOST, Api.HOST);
    }

    @Override
    public void showToast(@Nullable String message) {
//        if (toast == null) {
//            toast = new Toast(this);
//            toast.setDuration(Toast.LENGTH_SHORT);
//        }
//        if (!TextUtils.isEmpty(message)) {
//            toast.setText(message);
//            toast.show();
//        }
        Toast.makeText(_instance, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取当前用户
     *
     * @return 用户
     */
    public User getCurrentUser() {
        if (mUser == null) {
            String userInfo = SPHelper.readString(getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.USER_BEAN);
            if (!TextUtils.isEmpty(userInfo)) {
                mUser = new Gson().fromJson(userInfo, User.class);
            }
        }
        return mUser;
    }

    @NonNull
    @Override
    public String httpCacheFile() {
        return null;
    }

    @NonNull
    @Override
    public String imageCacheFile() {
        return getCacheFile("xueli").getAbsolutePath();
    }

    @Override
    public Intent needLoginIntent() {
        return null;
    }

    public void setUserInfo(@NonNull String info) {
        SPHelper.write(this, ConstantStr.USER_INFO, ConstantStr.USER_INFO, Base64Util.encode(info.getBytes()));
    }

    @Nullable
    public String getUserInfo() {
        String json = SPHelper.readString(this, ConstantStr.USER_INFO, ConstantStr.USER_INFO);
        String name = null;
        try {
            name = new String(Base64Util.decode(json), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return name;
    }

    public void cleanUserInfo() {
        SPHelper.remove(this, ConstantStr.USER_INFO, ConstantStr.USER_INFO);
    }

    public void setCurrentUser(User user) {
        this.mUser = user;
        String userInfo = new Gson().toJson(user);
        SPHelper.write(this, ConstantStr.USER_INFO, ConstantStr.USER_BEAN, userInfo);
    }

    @Override
    public void exitApp() {
        super.exitApp();
        SPHelper.remove(this, ConstantStr.USER_INFO, ConstantStr.TOKEN);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
