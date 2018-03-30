package com.xueli.application.view.bank.list;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.exam.ExamList;
import com.xueli.application.mode.bean.exam.QuestionType;

import java.util.List;
import java.util.Map;

/**
 * 试卷列表
 * Created by pingan on 2018/3/25.
 */

interface BankListContact {

    interface Presenter extends BasePresenter {

        void getBankList(Map<String, String> map);

        void getQuestionType();
    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void noData();

        void showData(List<ExamList> datas);

        void showMessage(String message);

        void showQuestionData(List<QuestionType> list);
    }
}
