package com.education.application.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.education.application.base.AbsBaseApp;
import com.education.application.common.ConstantStr;
import com.education.application.mode.api.Api;
import com.education.application.mode.bean.User;
import com.google.gson.Gson;
import com.library.utils.Base64Util;
import com.library.utils.SPHelper;

import java.io.UnsupportedEncodingException;

/**
 * application
 * Created by pingan on 2017/11/30.
 */

public class App extends AbsBaseApp {

    private static App _instance;
    private User mUser;//当前用户

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
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
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
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
        return getCacheFile("inspection").getAbsolutePath();
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
}
