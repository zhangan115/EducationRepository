package com.xueli.application.view.login.bindSchool;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.library.utils.SPHelper;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import java.util.Map;

import rx.subscriptions.CompositeSubscription;

final class BindSchoolPresenter implements BindSchoolContract.Presenter {

    private UserDataSource mUserDataSource;
    private BindSchoolContract.View mView;
    private CompositeSubscription mSubscriptions;

    BindSchoolPresenter(UserDataSource repository, BindSchoolContract.View view) {
        this.mUserDataSource = repository;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void updateUserInfo(Map<String, String> map) {
        mSubscriptions.add(mUserDataSource.updateUserInfo(map, new IObjectCallBack<User>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull User user) {
                Log.d("za", "===>" + user.getId());
                App.getInstance().setCurrentUser(user);
                SPHelper.write(App.getInstance(), ConstantStr.USER_INFO, ConstantStr.TOKEN, user.getToken());
                mView.updateUserInfoSuccess();
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
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
