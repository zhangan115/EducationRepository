package com.xueli.application.mode.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.api.Api;
import com.xueli.application.mode.api.ApiCallBackObject1;
import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VerificationCode;
import com.xueli.application.mode.callback.IObjectCallBack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * user repository
 * Created by pingan on 2017/12/2.
 */
public class UserRepository implements UserDataSource {

    private static UserRepository repository;
    private SharedPreferences sp;

    private UserRepository(@NonNull Context context) {
        sp = context.getSharedPreferences(ConstantStr.USER_INFO, Context.MODE_PRIVATE);
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
        Map<String, String> map = new HashMap<>();
        map.put("accountName", name);
        map.put("pssword", pass);
        return new ApiCallBackObject1<>(Api.createRetrofit().create(UserApi.class).userLogin(map))
                .execute(new IObjectCallBack<User>() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onData(@NonNull User user) {
                        App.getInstance().setCurrentUser(user);
                        sp.edit().putString(ConstantStr.TOKEN, user.getToken()).apply();
                        callBack.onLoginSuccess();
                    }

                    @Override
                    public void onError(@Nullable String message) {
                        callBack.showMessage(message);
                    }

                    @Override
                    public void noData() {
                    }

                    @Override
                    public void onFinish() {
                        callBack.onFinish();
                    }
                });
    }

    @NonNull
    @Override
    public Subscription autoLogin(@NonNull final AutoLoginCallBack callBack) {
        long WELCOME_TIME = 1500;
        return Observable.just(null).delaySubscription(WELCOME_TIME, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (TextUtils.isEmpty(sp.getString(ConstantStr.TOKEN, ""))) {
                            callBack.onNeedLogin();
                        } else {
                            callBack.onAutoFinish();
                        }
                    }
                });
    }

    @NonNull
    @Override
    public Subscription sendPhoneCode(@NonNull String phoneCode, @NonNull final IObjectCallBack<VerificationCode> callBack) {
        Observable<Bean<VerificationCode>> observable = Api.createRetrofit().create(UserApi.class).sendPhoneCode(phoneCode);
        return new ApiCallBackObject1<>(observable).execute(callBack);
    }


    @NonNull
    @Override
    public Subscription startCountDown(final CountDownCallBack callBack) {
        final long count = 30;
        return Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(30)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return count - aLong;
                    }
                })
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        callBack.countDownFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) {
                        callBack.onCountDown(String.valueOf(aLong));
                    }
                });
    }

    @NonNull
    @Override
    public Subscription userReg(Map<String, String> map, IObjectCallBack<String> callBack) {
        Observable<Bean<String>> observable = Api.createRetrofit().create(UserApi.class).userReg(map);
        return new ApiCallBackObject1<>(observable).execute(callBack);
    }

    private void autoFinish(long startTime, @NonNull final AutoLoginCallBack callBack, final boolean isSuccess) {
        long finishTime = System.currentTimeMillis();
        long WELCOME_TIME = 1500;
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
