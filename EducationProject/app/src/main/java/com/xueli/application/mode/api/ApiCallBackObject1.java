package com.xueli.application.mode.api;

import android.support.annotation.NonNull;

import com.xueli.application.app.App;
import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.callback.IObjectCallBack;

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

public class ApiCallBackObject1<T> {

    private Observable<Bean<T>> mObservable;

    public ApiCallBackObject1() {

    }

    public ApiCallBackObject1(@NonNull Observable<Bean<T>> observable) {
        this.mObservable = observable;
    }

    public Subscription execute(final IObjectCallBack<T> callBack) {
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
                .doOnNext(new Action1<Bean<T>>() {
                    @Override
                    public void call(Bean<T> t) {
                        if (t.getErrorCode() == ApiErrorCode.SUCCEED) {
                            callBack.onFinish();
                            callBack.onSuccess();
                            if (t.getData() == null) {
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
                }).flatMap(new Func1<Bean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(Bean<T> tBean) {
                        return Observable.just(tBean.getData());
                    }
                }).subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(T t) {

                    }
                });
    }
}
