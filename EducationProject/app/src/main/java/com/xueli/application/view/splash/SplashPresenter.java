package com.xueli.application.view.splash;

import android.support.annotation.NonNull;


import com.xueli.application.mode.user.UserDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 启动屏幕界面
 * Created by zhangan on 2017-07-24.
 */

class SplashPresenter implements SplashContract.Presenter {

    private UserDataSource mUserDataSource;
    private SplashContract.View mView;
    @NonNull
    private CompositeSubscription mSubscriptions;

    SplashPresenter(UserDataSource userDataSource, SplashContract.View view) {
        this.mUserDataSource = userDataSource;
        this.mView = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        mSubscriptions.add(mUserDataSource.autoLogin(new UserDataSource.AutoLoginCallBack() {
            @Override
            public void showWelcome() {
                mView.showWelcome();
            }

            @Override
            public void showMessage(@NonNull String message) {

            }

            @Override
            public void onNeedLogin() {
                mView.needLogin();
            }

            @Override
            public void onAutoFinish() {
                mView.openHome();
            }
        }));
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
