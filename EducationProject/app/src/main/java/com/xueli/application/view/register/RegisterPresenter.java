package com.xueli.application.view.register;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.user.VerificationCode;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * 注册
 * Created by pingan on 2018/3/25.
 */

class RegisterPresenter implements RegisterContract.Presenter {

    private final UserDataSource mUserDataSource;
    private final RegisterContract.View mView;
    private CompositeSubscription mSubscriptions;

    RegisterPresenter(UserDataSource mUserDataSource, RegisterContract.View mView) {
        this.mUserDataSource = mUserDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }
    private boolean isCountDown = false;

    @Override
    public void sendPhoneCode(String phoneNum) {
        mSubscriptions.add(mUserDataSource.sendPhoneCode(phoneNum, new IObjectCallBack<VerificationCode>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull VerificationCode s) {
                mView.sendCodeSuccess();
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        }));
    }

    @Override
    public boolean isCountDown() {
        return isCountDown;
    }

    @Override
    public void startCountDown() {
        isCountDown = true;
        mSubscriptions.add(mUserDataSource.startCountDown(new UserDataSource.CountDownCallBack() {
            @Override
            public void onCountDown(String time) {
                mView.startCountDown(time);
            }

            @Override
            public void countDownFinish() {
                isCountDown = false;
                mView.contDownFinish();
            }
        }));
    }

    @Override
    public void userReg(Map<String, String> map) {
        mSubscriptions.add(mUserDataSource.userReg(map, new IObjectCallBack<String>() {

            @Override
            public void onSuccess() {
                mView.registerSuccess();
            }

            @Override
            public void onData(@NonNull String s) {

            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        }));
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
