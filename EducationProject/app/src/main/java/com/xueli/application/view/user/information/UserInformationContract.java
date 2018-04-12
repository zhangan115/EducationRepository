package com.xueli.application.view.user.information;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;
import com.xueli.application.mode.bean.user.User;

import org.json.JSONObject;

import java.io.File;

public interface UserInformationContract {

    interface Presenter extends BasePresenter {

        void uploadUserPhoto(File file);

        void updateUserInfo(JSONObject jsonObject);

    }

    interface View extends BaseView<Presenter> {


        void uploadUserPhotoSuccess(String url);

        void uploadUserPhotoFail();

        void showUploadProgress();

        void hideProgress();

        void updateUserInfoSuccess(User user);

        void updateUserInfoFail();
    }

}
