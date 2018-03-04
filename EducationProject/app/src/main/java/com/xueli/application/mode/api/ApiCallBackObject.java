package com.xueli.application.mode.api;

import android.support.annotation.NonNull;

import com.xueli.application.app.App;
import com.xueli.application.mode.bean.Bean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 请求回调
 * Created by zhangan on 2017-05-16.
 */

public abstract class ApiCallBackObject<T> {

    private Observable<Bean<T>> mObservable;

    public ApiCallBackObject() {

    }

    public ApiCallBackObject(@NonNull Observable<Bean<T>> observable) {
        this.mObservable = observable;
    }

    public Observable<T> execute() {
        return mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable t) {
                        onFinish();
                        onFail(t.getMessage());
                        noData();
                    }
                })
                .doOnNext(new Action1<Bean<T>>() {
                    @Override
                    public void call(Bean<T> t) {
                        if (t.getErrorCode() == ApiErrorCode.SUCCEED) {
                            onFinish();
                            onSuccess();
                            if (t.getData() == null) {
                                noData();
                            } else {
                                onData(t.getData());
                            }

                        } else if (t.getErrorCode() == ApiErrorCode.NOT_LOGGED) {
                            App.getInstance().needLogin();
                        } else if (t.getErrorCode() != ApiErrorCode.NOT_LOGGED) {
                            onFinish();
                            onFail(t.getMessage());
                            noData();
                        }
                    }
                }).flatMap(new Func1<Bean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(Bean<T> tBean) {
                        return Observable.just(tBean.getData());
                    }
                });
    }

    public abstract void onData(@NonNull T data);

    public abstract void onSuccess();

    public abstract void onFail(@NonNull String message);

    public abstract void onFinish();

    public abstract void noData();
}
