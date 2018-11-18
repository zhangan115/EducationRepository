package com.xueli.application.mode.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.FilePartManager;
import com.xueli.application.mode.api.Api;
import com.xueli.application.mode.api.ApiCallBackList;
import com.xueli.application.mode.api.ApiCallBackList1;
import com.xueli.application.mode.api.ApiCallBackObject1;
import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.bean.user.NewVersion;
import com.xueli.application.mode.bean.user.PaySchoolList;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VerificationCode;
import com.xueli.application.mode.bean.user.VipContent;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.enrol.EnrolApi;
import com.xueli.application.mode.study.StudyApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
        final long count = 60;
        return Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(60)
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

    @NonNull
    @Override
    public Subscription uploadUserPhoto(@NonNull File file, @NonNull final IObjectCallBack<String> callBack) {
        Observable<Bean<List<String>>> observable = Api.createRetrofit().create(UserApi.class)
                .postFile(FilePartManager.getPostFileParts(file));
        return new ApiCallBackList<String>(observable) {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onData(List<String> strings) {
                callBack.onData(strings.get(0));
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onError(message);
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }

            @Override
            public void noData() {

            }
        }.execute().subscribe(new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<String> strings) {

            }
        });
    }


    @NonNull
    @Override
    public Subscription uploadUserInfo(@NonNull JSONObject jsonObject, @NonNull IObjectCallBack<User> callBack) {
        if (App.getInstance().getCurrentUser() == null) return rx.Observable.just("").subscribe();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
            jsonObject.put("accountId", App.getInstance().getCurrentUser().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackObject1<>(Api.createRetrofit().create(EnrolApi.class)
                .uploadData(jsonObject.toString()))
                .execute(callBack);
    }

    @NonNull
    @Override
    public Subscription userForgetPass(String name, String phone, @NonNull IObjectCallBack<VerificationCode> callBack) {
        Observable<Bean<VerificationCode>> observable = Api.createRetrofit().create(UserApi.class).userForgetPass(phone);
        return new ApiCallBackObject1<>(observable).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription userUpdatePass(Map<String, String> map, @NonNull IObjectCallBack<String> callBack) {
        Observable<Bean<String>> observable = Api.createRetrofit().create(UserApi.class).userUpdatePass(map);
        return new ApiCallBackObject1<>(observable).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getNewVersion(final IObjectCallBack<NewVersion> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Bean<NewVersion>> observable = Api.createRetrofit().create(UserApi.class).newVersion(jsonObject.toString());
        return new ApiCallBackObject1<>(observable).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getVipCardList(IListCallBack<VipContent> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Bean<List<VipContent>>> observable = Api.createRetrofit().create(UserApi.class).vipCardList(jsonObject.toString());
        return new ApiCallBackList1<>(observable).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription payVip(long cardId, IObjectCallBack<User> callBack) {
        if (App.getInstance().getCurrentUser() == null) return rx.Observable.just("").subscribe();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("carId", String.valueOf(cardId));
        map.put("maccountId", String.valueOf(App.getInstance().getCurrentUser().getId()));
        Observable<Bean<User>> observable = Api.createRetrofit().create(UserApi.class).payVip(map, jsonObject.toString());
        return new ApiCallBackObject1<>(observable).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription paySuccess(long cardId, IObjectCallBack<User> callBack) {
        if (App.getInstance().getCurrentUser() == null) return rx.Observable.just("").subscribe();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("carId", String.valueOf(cardId));
        map.put("maccountId", String.valueOf(App.getInstance().getCurrentUser().getId()));
        Observable<Bean<User>> observable = Api.createRetrofit().create(UserApi.class).paySuccess(map, jsonObject.toString());
        return new ApiCallBackObject1<>(observable).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getAlOrderString(long cardId, IObjectCallBack<String> callBack) {
        if (App.getInstance().getCurrentUser() == null) return rx.Observable.just("").subscribe();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("carId", String.valueOf(cardId));
        map.put("maccountId", String.valueOf(App.getInstance().getCurrentUser().getId()));
        Observable<Bean<String>> observable = Api.createRetrofit().create(UserApi.class).getOrderString(map, jsonObject.toString());
        return new ApiCallBackObject1<>(observable).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription paySchoolList(IListCallBack<PaySchoolList> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackList1<>(Api.createRetrofit().create(UserApi.class)
                .paySchoolList(jsonObject.toString()))
                .execute(callBack);
    }

}
