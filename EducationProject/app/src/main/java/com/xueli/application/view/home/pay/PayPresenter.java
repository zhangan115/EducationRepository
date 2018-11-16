package com.xueli.application.view.home.pay;

import com.xueli.application.mode.user.UserDataSource;

import rx.subscriptions.CompositeSubscription;

public class PayPresenter implements PayContract.Presenter {

    private final UserDataSource mDataSource;
    private final PayContract.View mView;
    private CompositeSubscription subscription;

    PayPresenter(UserDataSource mDataSource, PayContract.View mView) {
        this.mDataSource = mDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getPay() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
