package com.xueli.application.view.home.school;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.school.SchoolBean;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.study.StudyDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

class SchoolPresenter implements SchoolContract.Presenter {

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
        subscription.add(mDataSource.getAllSchoolList(new IListCallBack<SchoolBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<SchoolBean> list) {
                mView.showSchool(list);
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
