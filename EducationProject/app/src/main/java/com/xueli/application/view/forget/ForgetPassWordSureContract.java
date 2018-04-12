package com.xueli.application.view.forget;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

import java.util.Map;

public interface ForgetPassWordSureContract {

    interface Presenter extends BasePresenter {

        void changePass(Map<String, String> map);
    }

    interface View extends BaseView<Presenter> {

        void showMessage(String message);

        void changeSuccess();

        void changeFail(String str);

    }
}
