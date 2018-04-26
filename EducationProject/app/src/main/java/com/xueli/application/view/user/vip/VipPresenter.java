package com.xueli.application.view.user.vip;

import com.xueli.application.mode.user.UserDataSource;

import rx.subscriptions.CompositeSubscription;

class VipPresenter implements VipContract.Presenter {

    private final UserDataSource mDataSource;
    private final VipContract.View mView;
    private CompositeSubscription subscription;

    VipPresenter(UserDataSource mDataSource, VipContract.View mView) {
        this.mDataSource = mDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        subscription = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }
}
