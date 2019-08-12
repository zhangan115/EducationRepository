package com.xueli.application.view.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.user.QQLoginBean;
import com.xueli.application.mode.bean.user.WeiXinLoginBean;
import com.xueli.application.mode.callback.IObjectCallBack;
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
    public void weiXinLogin(String code) {
        mSubscriptions.add(mUserDataSource.getWeiXinAccessCode(code, new IObjectCallBack<WeiXinLoginBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull WeiXinLoginBean s) {
                mView.showWeiXinBean(s);
            }

            @Override
            public void onError(@Nullable String message) {

            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.loginHideLoading();
            }
        }));
    }

    @Override
    public void qqLogin(String openId) {
        mSubscriptions.add(mUserDataSource.getQQAccessCode(openId, new IObjectCallBack<QQLoginBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull QQLoginBean qqLoginBean) {
                mView.showQQLoginBean(qqLoginBean);
            }

            @Override
            public void onError(@Nullable String message) {

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
