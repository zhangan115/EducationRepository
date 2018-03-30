package com.xueli.application.mode.study;

import android.support.annotation.NonNull;

import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.callback.IListCallBack;

import rx.Subscription;

public interface StudyDataSource {

    @NonNull
    Subscription getStudyList(long id, IListCallBack<StudyMessage> callBack);

}
