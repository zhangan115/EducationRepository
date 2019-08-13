package com.xueli.application.view.login.bindSchool;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.library.utils.SPHelper;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.user.BindPhoneBean;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
    public void updateUserInfo(JSONObject json) {
        mSubscriptions.add(mUserDataSource.updateUserInfo(json, new IObjectCallBack<BindPhoneBean>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull BindPhoneBean bean) {
                App.getInstance().setCurrentUser(bean.getUser());
                SPHelper.write(App.getInstance(), ConstantStr.USER_INFO, ConstantStr.TOKEN, bean.getUser().getToken());
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
