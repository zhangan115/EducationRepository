package com.xueli.application.view.home.pay;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.app.App;
import com.xueli.application.mode.bean.user.PaySchoolList;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.WeiXinPayBean;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import java.util.List;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

public class PayPresenter implements PayContract.Presenter {

    private final UserDataSource mDataSource;
    private final PayContract.View mView;
    private CompositeSubscription subscription;

    PayPresenter(UserDataSource mDataSource, PayContract.View mView) {
        this.mDataSource = mDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getPay() {
        subscription.add(mDataSource.paySchoolList(new IListCallBack<PaySchoolList>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<PaySchoolList> list) {
                mView.showPaySchoolList(list);
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
    public void paySchoolAl(Map<String, String> map) {
        mView.showLoading();
        subscription.add(mDataSource.paySchool(map, new IObjectCallBack<String>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull String s) {
                mView.payAli(s);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.payFinish();
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }
        }));
    }

    @Override
    public void paySchoolWeiXin(Map<String, String> map) {
        mView.showLoading();
        subscription.add(mDataSource.payWeiXinVip(map, new IObjectCallBack<WeiXinPayBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull WeiXinPayBean s) {
                mView.payWeiXin(s);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.payFinish();
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }
        }));
    }

    @Override
    public void paySuccessCallBack(Map<String, String> map) {
        subscription.add(mDataSource.paySchoolCallBack(map, new IObjectCallBack<User>() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onData(@NonNull User user) {
                App.getInstance().setCurrentUser(user);
                mView.finishSuccess();
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
                mView.payFinish();
            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
