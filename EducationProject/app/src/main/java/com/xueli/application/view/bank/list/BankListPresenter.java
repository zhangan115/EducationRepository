package com.xueli.application.view.bank.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.exam.ExamDataSource;

import java.util.List;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * 试卷列表
 * Created by pingan on 2018/3/25.
 */

class BankListPresenter implements BankListContact.Presenter {

    private final ExamDataSource mExamDataSource;
    private final BankListContact.View mView;
    private CompositeSubscription mSubscriptions;

    BankListPresenter(ExamDataSource examDataSource, BankListContact.View mView) {
        this.mExamDataSource = examDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void getBankList(Map<String, String> map) {
        mView.showLoading();
        mSubscriptions.add(mExamDataSource.getExamList(map, new IListCallBack<String>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<String> list) {

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
