package com.xueli.application.view.user.subject_error;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.exam.ExamList;
import com.xueli.application.mode.bean.exam.QuestionType;

import java.util.List;
import java.util.Map;

/**
 * 过往错题
 * Created by pingan on 2018/3/25.
 */

public interface ErrorSubjectContract {

    interface Presenter extends BasePresenter {

        void getErrorSubjectList(Map<String, String> map);

        void getQuestionType();
    }

    interface View extends BaseView<Presenter> {

        void showData(List<ExamList> datas);

        void showMessage(String message);

        void showQuestionData(List<QuestionType> list);

        void noData();

        void showLoading();

        void hideLoading();
    }
}
