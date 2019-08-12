package com.xueli.application.view.login.bindPhone;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VerificationCode;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import org.json.JSONObject;

import rx.subscriptions.CompositeSubscription;

final class BindPhonePresenter implements BindPhoneContract.Presenter {

    private UserDataSource mUserDataSource;
    private BindPhoneContract.View mView;
    private CompositeSubscription mSubscriptions;
    private boolean isCountDown;

    BindPhonePresenter(UserDataSource repository, BindPhoneContract.View view) {
        this.mUserDataSource = repository;
        this.mView = view;
        mView.setPresenter(this);
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
    public void getCode(String phoneNum) {
        mSubscriptions.add(mUserDataSource.sendPhoneCode(phoneNum, new IObjectCallBack<VerificationCode>() {
            @Override
            public void onSuccess() {

            }


            @Override
            public void onData(@NonNull VerificationCode s) {
                mView.sendCodeSuccess(s);
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
    public void getUserInfo(JSONObject requestJson) {
        mSubscriptions.add(mUserDataSource.queryUserInfo(requestJson, new IObjectCallBack<User>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull User user) {
                mView.requestIUser(user);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {
                mView.needReg();
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
