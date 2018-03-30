package com.xueli.application.view.user.collection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.exam.PaperCollection;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.exam.ExamDataSource;

import java.util.List;

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
        mView.showLoading();
        mSubscriptions.add(examDataSource.getMyCollection(new IListCallBack<PaperSections>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<PaperSections> list) {
                mView.showData(list);
            }

            @Override
            public void onError(@Nullable String message) {

            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {
                mView.noData();
            }
        }));
    }

    @Override
    public void cancelCollection(long id) {
        mSubscriptions.add(examDataSource.unCollectionPaper(id, new IObjectCallBack<PaperCollection>() {
            @Override
            public void onSuccess() {
                mView.onCancelSuccess();
            }

            @Override
            public void onData(@NonNull PaperCollection paperCollection) {

            }

            @Override
            public void onError(@Nullable String message) {

            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        }));
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
