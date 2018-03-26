package com.xueli.application.mode.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.xueli.application.mode.bean.user.VerificationCode;
import com.xueli.application.mode.callback.IObjectCallBack;

import java.util.Map;

import rx.Subscription;

/**
 * 用户操作 data source
 * Created by pingan on 2017/12/2.
 */

public interface UserDataSource {

    interface LoadUserCallBack {

        void onLoginSuccess();

        void onLoginFail(@Nullable String failMessage);

        void onFinish();

        void showMessage(String message);
    }

    /**
     * 登陆
     *
     * @param name     用户名
     * @param pass     密码
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription login(@NonNull String name, @NonNull String pass, @NonNull LoadUserCallBack callBack);

    interface AutoLoginCallBack {

        void onNeedLogin();

        void onAutoFinish();

        void showWelcome();

        void showMessage(@NonNull String message);
    }

    /**
     * 自动登陆
     *
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription autoLogin(@NonNull AutoLoginCallBack callBack);

    @NonNull
    Subscription sendPhoneCode(@NonNull String phoneCode, @NonNull IObjectCallBack<VerificationCode> callBack);

    interface CountDownCallBack {

        void onCountDown(String time);

        void countDownFinish();
    }

    @NonNull
    Subscription startCountDown(CountDownCallBack callBack);

    @NonNull
    Subscription userReg(Map<String, String> map, IObjectCallBack<String> callBack);
}
