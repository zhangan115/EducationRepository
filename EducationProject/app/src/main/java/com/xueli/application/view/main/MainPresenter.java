package com.xueli.application.view.main;

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
}
