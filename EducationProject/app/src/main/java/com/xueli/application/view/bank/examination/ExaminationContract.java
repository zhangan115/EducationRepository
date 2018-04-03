package com.xueli.application.view.bank.examination;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.exam.PaperSectionList;
import com.xueli.application.mode.bean.exam.PaperSections;

import java.util.List;

/**
 * 考试
 * Created by pingan on 2018/3/26.
 */

interface ExaminationContract {

    interface Presenter extends BasePresenter {

        void getPaperSections(long id);

        void getFaultExamPaperSections(long id);

        //收藏
        void collectPaper(long paperQuestionId, long accountId);

        void uploadData(List<PaperSections> list);

    }

    interface View extends BaseView<Presenter> {

        void showData(List<PaperSections> list);

        void showPaperSectionListData(List<PaperSectionList> list);

        void showLoading();

        void hideLoading();

        void noData();

        void showMessage(String message);

        void collectStateChange(long id, boolean isCollect);

        void showUploadDialog();

        void hideUploadDialog();

        void uploadSuccess();
    }
}
