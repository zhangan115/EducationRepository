package com.xueli.application.view.login.bindSchool;


import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

import java.util.Map;


/**
 * 用户登录
 * Created by zhangan on 2017-02-16.
 */
interface BindSchoolContract {

    interface Presenter extends BasePresenter {

        /**
         * 更新用户信息
         *
         * @param map 数据
         */
        void updateUserInfo(Map<String, String> map);

    }

    interface View extends BaseView<Presenter> {

        void updateUserInfoSuccess();

        void showMessage(@Nullable String message);

    }
}
