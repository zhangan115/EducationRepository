package com.xueli.application.mode.enrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.api.Api;
import com.xueli.application.mode.api.ApiCallBackList1;
import com.xueli.application.mode.api.ApiCallBackObject1;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.enrol.bean.MajorBean;
import com.xueli.application.mode.enrol.bean.SchoolBean;
import com.xueli.application.mode.exam.ExamApi;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscription;

public class EnrolRepository implements EnrolDataSource {

    private static EnrolRepository repository;
    private SharedPreferences sp;

    private EnrolRepository(Context context) {
        sp = context.getSharedPreferences(ConstantStr.USER_INFO, Context.MODE_PRIVATE);
    }

    public static EnrolRepository getRepository(Context context) {
        if (repository == null) {
            repository = new EnrolRepository(context);
        }
        return repository;
    }

    @NonNull
    @Override
    public Subscription getSchoolList(IListCallBack<SchoolBean> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackList1<>(Api.createRetrofit().create(EnrolApi.class)
                .getSchoolList(jsonObject.toString()))
                .execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getMajorList(Long schoolId, IListCallBack<MajorBean> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackList1<>(Api.createRetrofit().create(EnrolApi.class)
                .getMajorList(schoolId, jsonObject.toString()))
                .execute(callBack);
    }

    @NonNull
    @Override
    public Subscription uploadData(JSONObject jsonObject, IObjectCallBack<User> callBack) {
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
}
