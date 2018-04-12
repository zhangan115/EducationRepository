package com.xueli.application.view.forget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import java.util.Map;

import rx.subscriptions.CompositeSubscription;

class ForgetPassWordSurePresenter implements ForgetPassWordSureContract.Presenter {

    private final ForgetPassWordSureContract.View mView;
    private final UserDataSource dataSource;
    private CompositeSubscription subscription;

    ForgetPassWordSurePresenter(UserDataSource dataSource, ForgetPassWordSureContract.View view) {
        this.mView = view;
        this.dataSource = dataSource;
        mView.setPresenter(this);
    }

    @Override
    public void changePass(Map<String, String> map) {
        subscription.add(dataSource.userUpdatePass(map, new IObjectCallBack<String>() {
            @Override
            public void onSuccess() {
                mView.changeSuccess();
            }

            @Override
            public void onData(@NonNull String s) {

            }

            @Override
            public void onError(@Nullable String message) {
                mView.changeFail(message);
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
        subscription = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }
}
