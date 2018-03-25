package com.xueli.application.view.login;

import android.support.annotation.Nullable;

import com.xueli.application.mode.user.UserDataSource;

import rx.subscriptions.CompositeSubscription;


/**
 * (P层)登陆
 * Created by zhangan on 2016-12-06.
 */

final class LoginPresenter implements LoginContract.Presenter {

    private UserDataSource mUserDataSource;
    private LoginContract.View mView;
    private CompositeSubscription mSubscriptions;

    LoginPresenter(UserDataSource repository, LoginContract.View view) {
        this.mUserDataSource = repository;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void login(String name, String pass) {
        mView.loginLoading();
        mSubscriptions.add(mUserDataSource.login(name, pass, new UserDataSource.LoadUserCallBack() {

            @Override
            public void onLoginSuccess() {
                mView.loginSuccess();
            }

            @Override
            public void onLoginFail(@Nullable String failMessage) {
                mView.showMessage(failMessage);
            }

            @Override
            public void onFinish() {
                mView.loginHideLoading();
            }

            @Override
            public void showMessage(String message) {
                mView.showMessage(message);
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
