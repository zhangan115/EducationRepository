package com.xueli.application.view.study.list.detail;

import com.xueli.application.mode.study.StudyDataSource;

import rx.subscriptions.CompositeSubscription;

class StudyDetailPresenter implements StudyDetailContract.Presenter {

    private final StudyDataSource mStudyDataSource;
    private final StudyDetailContract.View mView;
    private CompositeSubscription mSubscriptions;

    StudyDetailPresenter(StudyDataSource mStudyDataSource, StudyDetailContract.View mView) {
        this.mStudyDataSource = mStudyDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getDetail(long id) {
        mSubscriptions.add(mStudyDataSource.getStudyDetail(id, new StudyDataSource.IMessageCallBack() {
            @Override
            public void onSuccess(String s) {
                mView.showUrl(s);
            }

            @Override
            public void onError() {

            }
        }));
    }
}
