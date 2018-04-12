package com.xueli.application.view.forget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.user.VerificationCode;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import rx.subscriptions.CompositeSubscription;

class ForgetPassWordPresenter implements ForgetPassWordContract.Presenter {

    private final UserDataSource mUserDataSource;
    private final ForgetPassWordContract.View mView;
    private CompositeSubscription mSubscriptions;
    private boolean isCountDown;

    ForgetPassWordPresenter(UserDataSource mUserDataSource, ForgetPassWordContract.View mView) {
        this.mUserDataSource = mUserDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }


    @Override
    public void sendPhoneCode(String userName, String phoneNum) {
        mSubscriptions.add(mUserDataSource.userForgetPass(userName, phoneNum, new IObjectCallBack<VerificationCode>() {
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
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
        isCountDown = false;
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
