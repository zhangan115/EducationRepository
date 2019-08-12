package com.xueli.application.view.login.bindSchool;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.library.utils.SPHelper;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
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
        Map<String, String> map = new HashMap<>();
        try {
            if (json.has("phone")) {
                map.put("phone", json.getString("phone"));
            }
            if (json.has("verificationCode")) {
                map.put("verificationCode", json.getString("verificationCode"));
            }
            if (json.has("openId")) {
                map.put("openId", json.getString("openId"));
            }
            if (json.has("loginType")) {
                map.put("loginType", json.getString("loginType"));
            }
            if (json.has("password")) {
                map.put("password", json.getString("password"));
            }
            if (json.has("realName")) {
                map.put("realName", json.getString("realName"));
            }
            if (json.has("idcard")) {
                map.put("idcard", json.getString("idcard"));
            }
            if (json.has("suoxuezhuanye")) {
                map.put("suoxuezhuanye", json.getString("suoxuezhuanye"));
            }
            if (json.has("type")) {
                map.put("type", json.getString("type"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSubscriptions.add(mUserDataSource.updateUserInfo(map, new IObjectCallBack<User>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull User user) {
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
