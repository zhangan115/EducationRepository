package com.xueli.application.view.enrol;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.enrol.EnrolDataSource;
import com.xueli.application.mode.enrol.bean.MajorBean;
import com.xueli.application.mode.enrol.bean.SchoolBean;

import org.json.JSONObject;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

class EnrolPresenter implements EnrolContract.Presenter {

    private final EnrolDataSource mDataSource;
    private final EnrolContract.View mView;
    private CompositeSubscription subscription;

    EnrolPresenter(EnrolDataSource mDataSource, EnrolContract.View mView) {
        this.mDataSource = mDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        subscription = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }

    @Override
    public void getSchoolList() {
        subscription.add(mDataSource.getSchoolList(new IListCallBack<SchoolBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<SchoolBean> list) {
                mView.showSchoolList(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.schoolError();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {
                mView.schoolError();
            }
        }));
    }

    @Override
    public void getMajorList(Long schoolId) {
        subscription.add(mDataSource.getMajorList(schoolId, new IListCallBack<MajorBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<MajorBean> list) {
                mView.showMajor(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.majorError();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {
                mView.majorError();
            }
        }));
    }

    @Override
    public void uploadData(JSONObject jsonObject) {
        subscription.add(mDataSource.uploadData(jsonObject, new IObjectCallBack<User>() {
            @Override
            public void onSuccess() {
                mView.uploadSuccess();
            }

            @Override
            public void onData(@NonNull User s) {

            }

            @Override
            public void onError(@Nullable String message) {
                mView.uploadFail();
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        }));
    }
}
