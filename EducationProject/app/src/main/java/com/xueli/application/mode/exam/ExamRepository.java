package com.xueli.application.mode.exam;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.api.Api;
import com.xueli.application.mode.api.ApiCallBackList1;
import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.user.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import rx.Subscription;

/**
 * 考试
 * Created by pingan on 2018/3/25.
 */

public class ExamRepository implements ExamDataSource {

    private static ExamRepository repository;
    private SharedPreferences sp;

    private ExamRepository(@NonNull Context context) {
        sp = context.getSharedPreferences(ConstantStr.USER_INFO, Context.MODE_PRIVATE);
    }

    public static ExamRepository getRepository(Context context) {
        if (repository == null) {
            repository = new ExamRepository(context);
        }
        return repository;
    }

    @NonNull
    @Override
    public Subscription getExamList(Map<String, String> map, IListCallBack<String> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackList1<>(Api.createRetrofit().create(ExamApi.class)
                .getExamList(map, jsonObject.toString())).execute(callBack);
    }
}
