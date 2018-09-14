package com.xueli.application.view.user.information;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.library.utils.GlideUtils;
import com.library.utils.IdcardUtils;
import com.library.utils.PhotoUtils;
import com.soundcloud.android.crop.Crop;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.view.MvpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 用户信息
 * Created by pingan on 2018/3/13.
 */

public class UserInformationActivity extends MvpActivity<UserInformationContract.Presenter> implements UserInformationContract.View, EasyPermissions.PermissionCallbacks {


    private File photoFile;
    private static final int ACTION_START_CAMERA = 100;
    private static final int ACTION_START_PHOTO = 101;
    private ImageView ivUserPhoto;
    private JSONObject jsonObject;
    private TextView tvUserData;
    private TextView tvUserGender;
    private EditText etUserNum;
    private EditText etUserNickName, etUserName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.user_information_activity, "个人信息");
        setDarkStatusIcon(true);
        jsonObject = new JSONObject();
        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        findViewById(R.id.ivUserPhoto).setOnClickListener(this);
        findViewById(R.id.btnSure).setOnClickListener(this);
        etUserNum = findViewById(R.id.etUserNum);
        tvUserData = findViewById(R.id.tvUserData);
        tvUserGender = findViewById(R.id.tvUserGender);
        etUserNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString()) && IdcardUtils.validateCard(s.toString())) {
                    Short yyyy = IdcardUtils.getYearByIdCard(s.toString());
                    Short mm = IdcardUtils.getMonthByIdCard(s.toString());
                    Short dd = IdcardUtils.getDateByIdCard(s.toString());
                    StringBuilder sb = new StringBuilder();
                    if (yyyy != null) {
                        sb.append(String.valueOf(yyyy));
                        sb.append("-");
                    }
                    if (mm != null) {
                        sb.append(String.valueOf(mm));
                        sb.append("-");
                    }
                    if (dd != null) {
                        sb.append(String.valueOf(dd));
                    }
                    tvUserData.setText(sb.toString());
                    if (IdcardUtils.getGenderByIdCard(s.toString()).equals("M")) {
                        tvUserGender.setText("男");
                    } else if (IdcardUtils.getGenderByIdCard(s.toString()).equals("F")) {
                        tvUserGender.setText("女");
                    }
                }
            }
        });
        user = App.getInstance().getCurrentUser();
        if (user != null) {
            GlideUtils.ShowCircleImage(this, user.getHeadImage(), ivUserPhoto, R.drawable.img_avatar_default);
            etUserNickName = findViewById(R.id.etUserNickName);
            if (!TextUtils.isEmpty(user.getUserName())) {
                etUserNickName.setText(user.getUserName());
            }
            etUserName = findViewById(R.id.etUserName);
            if (!TextUtils.isEmpty(user.getRealName())) {
                etUserName.setText(user.getRealName());
            }
            if (!TextUtils.isEmpty(user.getIdcard())) {
                etUserNum.setText(user.getIdcard());
            }
        }
    }

    @Override
    protected void onBindPresenter() {
        new UserInformationPresenter(UserRepository.getRepository(this), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivUserPhoto:
                checkPermission();
                break;
            case R.id.btnSure:
                try {
                    if (TextUtils.isEmpty(etUserNum.getText().toString())) {
                        App.getInstance().showToast("请输入身份证号码");
                        return;
                    }
                    jsonObject.put("idcard", etUserNum.getText().toString());
                    if (TextUtils.isEmpty(etUserNickName.getText().toString())) {
                        App.getInstance().showToast("请输入昵称");
                        return;
                    }
                    jsonObject.put("userName", etUserNickName.getText().toString());
                    if (TextUtils.isEmpty(etUserName.getText().toString())) {
                        App.getInstance().showToast("请输入真实姓名");
                        return;
                    }
                    jsonObject.put("realName", etUserName.getText().toString());
                    if (IdcardUtils.getGenderByIdCard(etUserNum.getText().toString()).equals("M")) {
                        jsonObject.put("sex", 1);
                    } else if (IdcardUtils.getGenderByIdCard(etUserNum.getText().toString()).equals("F")) {
                        jsonObject.put("sex", 0);
                    } else {
                        App.getInstance().showToast("无法识别");
                        return;
                    }
                    mPresenter.updateUserInfo(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        user.setHeadImage(url);
        App.getInstance().setCurrentUser(user);
        GlideUtils.ShowCircleImage(this, userPhoto.getAbsolutePath(), ivUserPhoto, R.drawable.img_avatar_default);
    }

    @Override
    public void uploadUserPhotoFail() {
        hideProgressDialog();
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
    public void updateUserInfoSuccess(User user) {
        this.user = user;
        App.getInstance().setCurrentUser(user);
        finish();
    }

    @Override
    public void updateUserInfoFail() {

    }

    @Override
    public void setPresenter(UserInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @AfterPermissionGranted(REQUEST_EXTERNAL)
    public void checkPermission() {
        if (!EasyPermissions.hasPermissions(this.getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.request_permissions),
                    REQUEST_EXTERNAL, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            requestTakePhoto();
        }
    }

    public void requestTakePhoto() {
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
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == REQUEST_EXTERNAL) {
            if (EasyPermissions.hasPermissions(this.getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestTakePhoto();
            }
        }
    }


    public static final int REQUEST_EXTERNAL = 10;//内存卡权限

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == REQUEST_EXTERNAL) {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this)
                        .setRationale(getString(R.string.need_save_setting))
                        .setTitle(getString(R.string.request_permissions))
                        .setPositiveButton(getString(R.string.sure))
                        .setNegativeButton(getString(R.string.cancel))
                        .setRequestCode(REQUEST_EXTERNAL)
                        .build()
                        .show();
            }
        }
    }
}
