package com.xueli.application.view.bank.record;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.exam.ExamDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

final class RecordPresenter implements RecordContact.Presenter {

    private final ExamDataSource mExamDataSource;
    private final RecordContact.View mView;
    private CompositeSubscription mSubscriptions;

    RecordPresenter(ExamDataSource examDataSource, RecordContact.View mView) {
        this.mExamDataSource = examDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void getBankList(String bResult, Long lastId) {
        mSubscriptions.add(mExamDataSource.getPaperList(bResult, lastId, new IListCallBack<PaperSections>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<PaperSections> list) {
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
