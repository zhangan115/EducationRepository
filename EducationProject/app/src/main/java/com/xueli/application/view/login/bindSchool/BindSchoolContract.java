package com.xueli.application.view.login.bindSchool;


import android.support.annotation.Nullable;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

import org.json.JSONObject;

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
         * @param json 数据
         */
        void updateUserInfo(JSONObject json);

    }

    interface View extends BaseView<Presenter> {

        void updateUserInfoSuccess();

        void showMessage(@Nullable String message);

    }
}
