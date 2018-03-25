package com.xueli.application.view.user.subject_error;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

/**
 * 过往错题
 * Created by pingan on 2018/3/25.
 */

public interface ErrorSubjectContract {

    interface Presenter extends BasePresenter {

        void getErrorSubjectList();
    }

    interface View extends BaseView<Presenter> {

        void showMessage(String message);

        void showData();

        void noData();
    }
}
