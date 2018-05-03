package com.xueli.application.view.user.vip;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VipContent;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.user.UserDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

class VipPresenter implements VipContract.Presenter {

    private final UserDataSource mDataSource;
    private final VipContract.View mView;
    private CompositeSubscription subscription;

    VipPresenter(UserDataSource mDataSource, VipContract.View mView) {
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
    public void getVipCardList() {
        subscription.add(mDataSource.getVipCardList(new IListCallBack<VipContent>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<VipContent> list) {
                mView.showVipContent(list);
            }


            @Override
            public void onError(@Nullable String message) {

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
    public void payVip(long cardId) {
        mDataSource.payVip(cardId, new IObjectCallBack<User>() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onData(@NonNull User user) {
                mView.paySuccess(user);
            }

            @Override
            public void onError(@Nullable String message) {

            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void getOrderString(long cardId) {
        mDataSource.getAlOrderString(cardId, new IObjectCallBack<String>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull String s) {
                mView.showAlOrderStr(s);
            }

            @Override
            public void onError(@Nullable String message) {

            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
