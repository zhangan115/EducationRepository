package com.xueli.application.view.web;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.study.StudyDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

class MessageDetailPresenter implements MessageDetailContract.Presenter {

    private final StudyDataSource mStudyDataSource;
    private final MessageDetailContract.View mView;
    private CompositeSubscription mSubscriptions;

    MessageDetailPresenter(StudyDataSource mStudyDataSource, MessageDetailContract.View mView) {
        this.mStudyDataSource = mStudyDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void getUrl(long id) {
        mSubscriptions.add(mStudyDataSource.getMessageDetail(id, new StudyDataSource.IMessageCallBack() {
            @Override
            public void onSuccess(String s) {
                mView.showUrl(s);
            }

            @Override
            public void onError() {

            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
