package com.xueli.application.mode;

import android.support.annotation.NonNull;

import com.xueli.application.app.App;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 文件上传
 * Created by pingan on 2017/12/5.
 */

public class FilePartManager {

    public static List<MultipartBody.Part> getPostFileParts(String token, @NonNull File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("accountId", String.valueOf(App.getInstance().getCurrentUser().getId()))
                .addFormDataPart("token", token);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), requestFile);
        return builder.build().parts();
    }

}
