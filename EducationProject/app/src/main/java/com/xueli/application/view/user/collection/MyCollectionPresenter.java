package com.xueli.application.view.user.collection;

import com.xueli.application.mode.exam.ExamDataSource;

import rx.subscriptions.CompositeSubscription;

class MyCollectionPresenter implements MyCollectionContract.Presenter {

    private final ExamDataSource examDataSource;
    private final MyCollectionContract.View mView;
    private CompositeSubscription mSubscriptions;

    MyCollectionPresenter(ExamDataSource examDataSource, MyCollectionContract.View mView) {
        this.examDataSource = examDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void getMyCollection() {

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
