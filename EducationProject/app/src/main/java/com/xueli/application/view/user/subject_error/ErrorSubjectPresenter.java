package com.xueli.application.view.user.subject_error;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xueli.application.mode.bean.exam.ExamList;
import com.xueli.application.mode.bean.exam.FaultExam;
import com.xueli.application.mode.bean.exam.PaperSections;
import com.xueli.application.mode.bean.exam.QuestionType;
import com.xueli.application.mode.callback.IListCallBack;
import com.xueli.application.mode.exam.ExamDataSource;

import java.util.List;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * 错题
 * Created by pingan on 2018/3/25.
 */

class ErrorSubjectPresenter implements ErrorSubjectContract.Presenter {

    private final ExamDataSource mExamDataSource;
    private final ErrorSubjectContract.View mView;
    private CompositeSubscription mSubscriptions;

    ErrorSubjectPresenter(ExamDataSource examDataSource, ErrorSubjectContract.View mView) {
        this.mExamDataSource = examDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }


    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getErrorSubjectList(Map<String, String> map) {
        mView.showLoading();
        mSubscriptions.add(mExamDataSource.getMyFaultExam( new IListCallBack<FaultExam>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<FaultExam> list) {
                mView.showData(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {
                mView.noData();
            }
        }));
    }

    @Override
    public void getQuestionType() {
        mSubscriptions.add(mExamDataSource.getQuestionTypeList(new IListCallBack<QuestionType>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<QuestionType> list) {
                mView.showQuestionData(list);
            }

            @Override
            public void onError(@Nullable String message) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {

            }
        }));
    }
}
