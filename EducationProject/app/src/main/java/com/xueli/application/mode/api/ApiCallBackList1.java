package com.xueli.application.mode.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.app.App;
import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.callback.IListCallBack;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 请求回调
 * Created by zhangan on 2017-05-16.
 */

public class ApiCallBackList1<T> {

    private Observable<Bean<List<T>>> mObservable;

    public ApiCallBackList1() {

    }

    public ApiCallBackList1(@Nullable Observable<Bean<List<T>>> observable) {
        this.mObservable = observable;
    }

    public Subscription execute(final @NonNull IListCallBack<T> callBack) {
        return mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable t) {
                        callBack.onFinish();
                        callBack.onError(t.getMessage());
                        callBack.noData();
                    }
                })
                .doOnNext(new Action1<Bean<List<T>>>() {
                    @Override
                    public void call(Bean<List<T>> t) {
                        if (t.getErrorCode() == ApiErrorCode.SUCCEED) {
                            callBack.onFinish();
                            callBack.onSuccess();
                            if (t.getData() == null || t.getData().size() == 0) {
                                callBack.noData();
                            } else {
                                callBack.onData(t.getData());
                            }
                        } else if (t.getErrorCode() == ApiErrorCode.NOT_LOGGED) {
                            App.getInstance().needLogin();
                        } else if (t.getErrorCode() != ApiErrorCode.NOT_LOGGED) {
                            callBack.onFinish();
                            callBack.onError(t.getErrorMessage());
                            callBack.noData();
                        }
                    }
                })
                .flatMap(new Func1<Bean<List<T>>, Observable<List<T>>>() {
                    @Override
                    public Observable<List<T>> call(Bean<List<T>> listBean) {
                        return Observable.just(listBean.getData());
                    }
                }).subscribe(new Subscriber<List<T>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<T> ts) {

                    }
                });
    }

}
