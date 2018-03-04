package com.xueli.application.mode.user;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * user repository
 * Created by pingan on 2017/12/2.
 */
public class UserRepository implements UserDataSource {

    private long WELCOME_TIME = 1500;//欢迎页面展示时长
    private static UserRepository repository;

    private UserRepository(@NonNull Context context) {

    }

    public static UserRepository getRepository(Context context) {
        if (repository == null) {
            repository = new UserRepository(context);
        }
        return repository;
    }

    @NonNull
    @Override
    public Subscription login(@NonNull final String name, @NonNull final String pass, @NonNull final LoadUserCallBack callBack) {
        return null;
    }

    @NonNull
    @Override
    public Subscription autoLogin(@NonNull final AutoLoginCallBack callBack) {
        autoFinish(System.currentTimeMillis(), callBack, false);
        return Observable.just(null).subscribe();
    }

    private void autoFinish(long startTime, @NonNull final AutoLoginCallBack callBack, final boolean isSuccess) {
        long finishTime = System.currentTimeMillis();
        if (finishTime - startTime < WELCOME_TIME) {
            long waiteTime = WELCOME_TIME - (finishTime - startTime);
            Observable.just(null).delaySubscription(waiteTime, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            if (isSuccess) {
                                callBack.onAutoFinish();
                            } else {
                                callBack.onNeedLogin();
                            }
                        }
                    });
        } else {
            callBack.onAutoFinish();
        }
    }
}
