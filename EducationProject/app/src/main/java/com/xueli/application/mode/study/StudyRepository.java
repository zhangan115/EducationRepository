package com.xueli.application.mode.study;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.api.Api;
import com.xueli.application.mode.api.ApiCallBackList1;
import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.callback.IListCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscription;

public class StudyRepository implements StudyDataSource {

    private static StudyRepository repository;
    private SharedPreferences sp;

    private StudyRepository(Context context) {
        sp = context.getSharedPreferences(ConstantStr.USER_INFO, Context.MODE_PRIVATE);
    }

    public static StudyRepository getRepository(Context context) {
        if (repository == null) {
            repository = new StudyRepository(context);
        }
        return repository;
    }

    @NonNull
    @Override
    public Subscription getStudyList(long id, IListCallBack<StudyMessage> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackList1<>(Api.createRetrofit().create(StudyApi.class)
                .getMessageList(id, App.getInstance().getCurrentUser().getId()
                        , jsonObject.toString()))
                .execute(callBack);
    }
}
