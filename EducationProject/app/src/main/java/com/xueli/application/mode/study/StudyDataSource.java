package com.xueli.application.mode.study;

import android.support.annotation.NonNull;

import com.xueli.application.mode.bean.school.SchoolBean;
import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.callback.IListCallBack;

import rx.Subscription;

public interface StudyDataSource {

    @NonNull
    Subscription getStudyList(long id, IListCallBack<StudyMessage> callBack);

    @NonNull
    Subscription getSchoolList(IListCallBack<SchoolBean> callBack);
    @NonNull
    Subscription getAllSchoolList(IListCallBack<SchoolBean> callBack);

    @NonNull
    Subscription getStudyList(long id, long lastId, IListCallBack<StudyMessage> callBack);

    interface IMessageCallBack {

        void onSuccess(String s);

        void onError();
    }

    @NonNull
    Subscription getMessageDetail(long id, IMessageCallBack callBack);


    @NonNull
    Subscription getStudyDetail(long id, IMessageCallBack callBack);

}
