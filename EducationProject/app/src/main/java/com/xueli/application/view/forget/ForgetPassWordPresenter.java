package com.xueli.application.view.forget;

import com.xueli.application.mode.user.UserDataSource;

import rx.subscriptions.CompositeSubscription;

class ForgetPassWordPresenter implements ForgetPassWordContract.Presenter {

    private final UserDataSource mUserDataSource;
    private final ForgetPassWordContract.View mView;
    private CompositeSubscription mSubscriptions;

    ForgetPassWordPresenter(UserDataSource mUserDataSource, ForgetPassWordContract.View mView) {
        this.mUserDataSource = mUserDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void sendPhoneCode(String phoneNum) {

    }

    @Override
    public boolean isCountDown() {
        return false;
    }

    @Override
    public void startCountDown() {

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
