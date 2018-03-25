package com.xueli.application.mode.exam;

import android.support.annotation.NonNull;

import com.xueli.application.mode.callback.IListCallBack;

import java.util.Map;

import rx.Subscription;

/**
 * 考试mode
 * Created by pingan on 2018/3/25.
 */

public interface ExamDataSource {

    @NonNull
    Subscription getExamList(Map<String, String> map, IListCallBack<String> callBack);

}
