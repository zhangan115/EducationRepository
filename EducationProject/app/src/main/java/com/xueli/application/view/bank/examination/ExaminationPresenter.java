package com.xueli.application.view.bank.examination;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.exam.ExamDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * 考试
 * Created by pingan on 2018/3/26.
 */

class ExaminationPresenter implements ExaminationContract.Presenter {

    private final ExaminationContract.View mView;
    private final ExamDataSource mDataSource;
    private CompositeSubscription mSubscriptions;

    ExaminationPresenter(ExaminationContract.View mView, ExamDataSource mDataSource) {
        this.mView = mView;
        this.mDataSource = mDataSource;
        mView.setPresenter(this);
    }

    @Override
    public void getPaperSections(long id) {
        mView.showLoading();
        mSubscriptions.add(mDataSource.getPaperSections(id, new IListCallBack<PaperSections>() {
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
