package com.xueli.application.view.home.school;

import com.xueli.application.mode.study.StudyDataSource;

import rx.subscriptions.CompositeSubscription;

class SchoolPresenter  implements SchoolContract.Presenter {

    private final StudyDataSource mDataSource;
    private final SchoolContract.View mView;

    private CompositeSubscription subscription;

    SchoolPresenter(StudyDataSource mDataSource, SchoolContract.View mView) {
        this.mDataSource = mDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getSchool() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }
}
