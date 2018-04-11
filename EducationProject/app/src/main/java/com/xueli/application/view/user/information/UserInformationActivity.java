package com.xueli.application.view.user.information;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.library.utils.GlideUtils;
import com.library.utils.PhotoUtils;
import com.soundcloud.android.crop.Crop;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.view.MvpActivity;

import java.io.File;

/**
 * 用户信息
 * Created by pingan on 2018/3/13.
 */

public class UserInformationActivity extends MvpActivity<UserInformationContract.Presenter> implements UserInformationContract.View {


    private File photoFile;
    private static final int ACTION_START_CAMERA = 100;
    private static final int ACTION_START_PHOTO = 101;
    private ImageView ivUserPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.user_information_activity, "个人信息");
        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        GlideUtils.ShowImage(this, App.getInstance().getCurrentUser().getHeadImage(), ivUserPhoto, R.drawable.img_avatar);
        TextView tvUserNickName = findViewById(R.id.tvUserNickName);
        tvUserNickName.setText(App.getInstance().getCurrentUser().getAccountName());
        findViewById(R.id.ivUserPhoto).setOnClickListener(this);
    }

    @Override
    protected void onBindPresenter() {
        new UserInformationPresenter(UserRepository.getRepository(this), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivUserPhoto:
                new MaterialDialog.Builder(this)
                        .items(R.array.choose_photo)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                if (position == 0) {
                                    photoFile = new File(App.getInstance().imageCacheFile(), System.currentTimeMillis() + ".jpg");
                                    Intent intent = new Intent();
                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    ContentValues contentValues = new ContentValues(1);
                                    contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                                    Uri uri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                    startActivityForResult(intent, ACTION_START_CAMERA);
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    startActivityForResult(intent, ACTION_START_PHOTO);
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    private File userPhoto;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_START_CAMERA && resultCode == RESULT_OK) {
            Uri photoIn = Uri.fromFile(photoFile);
            beginCrop(photoIn);
        }
        if (requestCode == ACTION_START_PHOTO && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(this.getCacheDir(), "user.jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            PhotoUtils.cropPhoto(this, new File(uri.getEncodedPath()), new PhotoUtils.PhotoListener() {
                @Override
                public void onSuccess(File file) {
                    userPhoto = file;
                    mPresenter.uploadUserPhoto(file);
                }
            });
        } else if (resultCode == Crop.RESULT_ERROR) {
            App.getInstance().showToast(Crop.getError(result).getMessage());
        }
    }

    @Override
    public void uploadUserPhotoSuccess(String url) {
        App.getInstance().getCurrentUser().setHeadImage(url);
        GlideUtils.ShowCircleImage(this, userPhoto.getAbsolutePath(), ivUserPhoto, R.drawable.img_avatar);
    }

    @Override
    public void uploadUserPhotoFail() {

    }

    @Override
    public void showUploadProgress() {
        showProgressDialog("上传中...");
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void setPresenter(UserInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
