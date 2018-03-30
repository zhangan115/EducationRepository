package com.xueli.application.view.study.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.study.StudyDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

class StudyListPresenter implements StudyListContract.Presenter {

    private final StudyDataSource mStudyDataSource;
    private final StudyListContract.View mView;
    private CompositeSubscription mSubscriptions;

    StudyListPresenter(StudyDataSource mStudyDataSource, StudyListContract.View mView) {
        this.mStudyDataSource = mStudyDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void getStudyMessage(long id) {
        mStudyDataSource.getStudyList(id, new IListCallBack<StudyMessage>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<StudyMessage> list) {
                mView.showData(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {
                mView.noData();
            }
        });
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
