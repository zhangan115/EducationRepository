package com.xueli.application.view.user.information;

import com.xueli.application.base.BasePresenter;
import com.xueli.application.base.BaseView;

import java.io.File;

public interface UserInformationContract {

    interface Presenter extends BasePresenter {

        void uploadUserPhoto(File file);

    }

    interface View extends BaseView<Presenter> {


        void uploadUserPhotoSuccess(String url);

        void uploadUserPhotoFail();

        void showUploadProgress();

        void hideProgress();
    }

}
