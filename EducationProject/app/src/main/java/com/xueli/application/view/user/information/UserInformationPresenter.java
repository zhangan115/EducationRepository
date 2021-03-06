package com.xueli.application.view.user.information;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import org.json.JSONObject;

import java.io.File;

import rx.subscriptions.CompositeSubscription;

class UserInformationPresenter implements UserInformationContract.Presenter {

    private final UserDataSource mApplicationDataSource;
    private final UserInformationContract.View mView;
    private CompositeSubscription mSubscription;

    UserInformationPresenter(UserDataSource mApplicationDataSource, UserInformationContract.View mView) {
        this.mApplicationDataSource = mApplicationDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void uploadUserPhoto(File file) {
        mView.showUploadProgress();
        mSubscription.add(mApplicationDataSource.uploadUserPhoto(file, new IObjectCallBack<String>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull String s) {
                mView.uploadUserPhotoSuccess(s);
            }

            @Override
            public void onError(String message) {
                mView.uploadUserPhotoFail();
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideProgress();
            }
        }));
    }

    @Override
    public void updateUserInfo(JSONObject jsonObject) {
        mSubscription.add(mApplicationDataSource.uploadUserInfo(jsonObject, new IObjectCallBack<User>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull User s) {
                mView.updateUserInfoSuccess(s);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.updateUserInfoFail();
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
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
