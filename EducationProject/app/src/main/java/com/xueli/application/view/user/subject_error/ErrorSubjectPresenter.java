package com.xueli.application.view.user.subject_error;

import com.xueli.application.mode.exam.ExamDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 错题
 * Created by pingan on 2018/3/25.
 */

class ErrorSubjectPresenter implements ErrorSubjectContract.Presenter {

    private final ExamDataSource examDataSource;
    private final ErrorSubjectContract.View mView;
    private CompositeSubscription mSubscriptions;

    ErrorSubjectPresenter(ExamDataSource examDataSource, ErrorSubjectContract.View mView) {
        this.examDataSource = examDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void getErrorSubjectList() {

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
