package com.xueli.application.view.home.pay;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.user.PaySchoolList;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.user.UserDataSource;

import java.util.List;

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
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
