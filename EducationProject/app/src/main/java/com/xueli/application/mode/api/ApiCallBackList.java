package com.xueli.application.mode.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.app.App;
import com.xueli.application.mode.bean.Bean;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 请求回调
 * Created by zhangan on 2017-05-16.
 */

public abstract class ApiCallBackList<T> {

    private Observable<Bean<List<T>>> mObservable;

    public ApiCallBackList() {

    }

    public ApiCallBackList(@Nullable Observable<Bean<List<T>>> observable) {
        this.mObservable = observable;
    }

    public Observable<List<T>> execute() {
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
                .doOnNext(new Action1<Bean<List<T>>>() {
                    @Override
                    public void call(Bean<List<T>> t) {
                        if (t.getErrorCode() == ApiErrorCode.SUCCEED) {
                            onFinish();
                            onSuccess();
                            if (t.getData() == null || t.getData().size() == 0) {
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
                })
                .flatMap(new Func1<Bean<List<T>>, Observable<List<T>>>() {
                    @Override
                    public Observable<List<T>> call(Bean<List<T>> listBean) {
                        return Observable.just(listBean.getData());
                    }
                });
    }

    public abstract void onSuccess();

    public abstract void onData(List<T> data);

    public abstract void onFail(@NonNull String message);

    public abstract void onFinish();

    public abstract void noData();
}
