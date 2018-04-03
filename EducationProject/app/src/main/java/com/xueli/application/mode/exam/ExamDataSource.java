package com.xueli.application.mode.exam;

import android.support.annotation.NonNull;

import com.xueli.application.mode.bean.exam.ExamList;
import com.xueli.application.mode.bean.exam.FaultExam;
import com.xueli.application.mode.bean.exam.PaperCollection;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.bean.exam.QuestionType;
import com.xueli.application.mode.bean.exam.UploadData;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.callback.IObjectCallBack;

import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * 考试mode
 * Created by pingan on 2018/3/25.
 */

public interface ExamDataSource {

    @NonNull
    Subscription getExamList(Map<String, String> map, IListCallBack<ExamList> callBack);

    @NonNull
    Subscription getPaperSections(long id, IListCallBack<PaperSections> callBack);

    @NonNull
    Subscription collectionPaper(long paperQuestionId, long accountId, IObjectCallBack<PaperCollection> callBack);

    @NonNull
    Subscription unCollectionPaper(long id, IObjectCallBack<PaperCollection> callBack);

    @NonNull
    Subscription getMyCollection(IListCallBack<PaperSections> callBack);
    @NonNull
    Subscription getMyFaultExam(IListCallBack<FaultExam> callBack);
    @NonNull
    Subscription getMyFaultExamPaper(long id,IListCallBack<PaperSections> callBack);

    @NonNull
    Subscription getMyCollection(long lastId, IListCallBack<PaperSections> callBack);

    @NonNull
    Subscription getQuestionTypeList(IListCallBack<QuestionType> callBack);

    @NonNull
    Subscription uploadData(List<PaperSections> data, IObjectCallBack<String> callBack);

}
