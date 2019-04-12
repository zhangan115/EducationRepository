package com.xueli.application.mode.exam;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.api.Api;
import com.xueli.application.mode.api.ApiCallBackList1;
import com.xueli.application.mode.api.ApiCallBackObject1;
import com.xueli.application.mode.bean.exam.ExamList;
import com.xueli.application.mode.bean.exam.FaultExam;
import com.xueli.application.mode.bean.exam.PaperCollection;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.bean.exam.QuestionType;
import com.xueli.application.mode.bean.exam.UploadData;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
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
    public Subscription getExamList(Map<String, String> map, IListCallBack<ExamList> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackList1<>(Api.createRetrofit().create(ExamApi.class)
                .getExamList(map, jsonObject.toString())).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getPaperSections(long id, IListCallBack<PaperSections> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackList1<>(Api.createRetrofit().create(ExamApi.class)
                .getExamPaperQuesting(id, jsonObject.toString())).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription collectionPaper(long paperQuestionId, long accountId, IObjectCallBack<PaperCollection> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackObject1<>(Api.createRetrofit().create(ExamApi.class)
                .postPaperCollection(paperQuestionId, accountId, jsonObject.toString())).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription unCollectionPaper(long id, IObjectCallBack<PaperCollection> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackObject1<>(Api.createRetrofit().create(ExamApi.class)
                .cancelPaperCollection(id, jsonObject.toString()))
                .execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getMyCollection(IListCallBack<PaperSections> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (App.getInstance().getCurrentUser() == null) return rx.Observable.just("").subscribe();
        return new ApiCallBackList1<>(Api.createRetrofit().create(ExamApi.class)
                .getMyCollection(App.getInstance().getCurrentUser().getId()
                        , jsonObject.toString()))
                .execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getMyFaultExam(IListCallBack<FaultExam> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (App.getInstance().getCurrentUser() == null) return rx.Observable.just("").subscribe();
        return new ApiCallBackList1<>(Api.createRetrofit().create(ExamApi.class)
                .getMyFaultExam(App.getInstance().getCurrentUser().getId()
                        , jsonObject.toString()))
                .execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getMyFaultExamPaper(long id, IListCallBack<PaperSections> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackList1<>(Api.createRetrofit().create(ExamApi.class)
                .getMyFaultExamPaper(id, jsonObject.toString())).execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getMyCollection(long lastId, IListCallBack<PaperSections> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (App.getInstance().getCurrentUser() == null) return rx.Observable.just("").subscribe();
        return new ApiCallBackList1<>(Api.createRetrofit().create(ExamApi.class)
                .getMyCollection(App.getInstance().getCurrentUser().getId()
                        , lastId
                        , jsonObject.toString()))
                .execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getQuestionTypeList(IListCallBack<QuestionType> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ApiCallBackList1<>(Api.createRetrofit().create(ExamApi.class)
                .getQuestionType(jsonObject.toString()))
                .execute(callBack);
    }

    @NonNull
    @Override
    public Subscription uploadData(List<PaperSections> data, IObjectCallBack<String> callBack) {
        if (App.getInstance().getCurrentUser() == null) return rx.Observable.just("").subscribe();
        UploadData uploadData = new UploadData(sp.getString(ConstantStr.TOKEN, "")
                , String.valueOf(App.getInstance().getCurrentUser().getId())
                , data);
        Logger.d(uploadData.getUploadJson());
        return new ApiCallBackObject1<>(Api.createRetrofit().create(ExamApi.class)
                .uploadData(uploadData.getUploadJson()))
                .execute(callBack);
    }

    @NonNull
    @Override
    public Subscription getPaperList(String bResult, Long lastId, IListCallBack<PaperSections> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", sp.getString(ConstantStr.TOKEN, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (App.getInstance().getCurrentUser() == null) return rx.Observable.just("").subscribe();
        return new ApiCallBackList1<>(Api.createRetrofit().create(ExamApi.class)
                .getPaperSectionList(App.getInstance().getCurrentUser().getId()
                        , bResult, lastId, jsonObject.toString()))
                .execute(callBack);
    }

}
