package com.xueli.application.mode.enrol;

import android.support.annotation.NonNull;

import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;
import com.xueli.application.mode.enrol.bean.MajorBean;
import com.xueli.application.mode.enrol.bean.SchoolBean;

import org.json.JSONObject;

import rx.Subscription;

public interface EnrolDataSource {

    @NonNull
    Subscription getSchoolList(IListCallBack<SchoolBean> callBack);

    @NonNull
    Subscription getMajorList(Long schoolId, IListCallBack<MajorBean> callBack);

    @NonNull
    Subscription uploadData(JSONObject jsonObject, IObjectCallBack<String> callBack);
}
