package com.xueli.application.view.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.user.NewVersion;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 首页
 * Created by pingan on 2018/3/4.
 */
class MainPresenter implements MainContract.Presenter {

    private final UserDataSource mDataSource;
    private final MainContract.View mView;
    private CompositeSubscription mSubscriptions;

    MainPresenter(UserDataSource mDataSource, MainContract.View mView) {
        this.mDataSource = mDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getNewVersion() {
        mSubscriptions.add(mDataSource.getNewVersion(new IObjectCallBack<NewVersion>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull NewVersion newVersion) {
                mView.showNewVersion(newVersion);
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
}
