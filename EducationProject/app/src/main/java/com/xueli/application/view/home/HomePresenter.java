package com.xueli.application.view.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.app.App;
import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.study.StudyDataSource;

import java.util.ArrayList;
import java.util.List;

import rx.subscriptions.CompositeSubscription;

class HomePresenter implements HomeContract.Presenter {

    private final StudyDataSource mDataSource;
    private final HomeContract.View mView;

    private CompositeSubscription subscription;

    HomePresenter(StudyDataSource mDataSource, HomeContract.View mView) {
        this.mDataSource = mDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getHeaderAd() {
        subscription.add(mDataSource.getStudyList(1, new IListCallBack<StudyMessage>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<StudyMessage> list) {
                mView.showHeaderAd(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {
                mView.noHeaderAd();
            }
        }));
    }

    @Override
    public void getHot() {
        subscription.add(mDataSource.getStudyList(7, new IListCallBack<StudyMessage>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<StudyMessage> list) {
                mView.showHot(new ArrayList<>(list));
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {
                mView.noHeaderAd();
            }
        }));
    }

    @Override
    public void checkNewVersion() {

    }

    @Override
    public void getMessage() {
        subscription.add(mDataSource.getStudyList(2, new IListCallBack<StudyMessage>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<StudyMessage> list) {
                mView.showMessageList(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {
                mView.noMessage();
            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }
}
