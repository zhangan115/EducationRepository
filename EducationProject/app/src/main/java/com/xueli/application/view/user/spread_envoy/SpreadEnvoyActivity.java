package com.xueli.application.view.user.spread_envoy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.utils.DisplayUtil;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.util.QRCodeUtil;
import com.xueli.application.view.BaseActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import gdut.bsx.share2.FileUtil;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 推广大使
 * Created by pingan on 2018/3/26.
 */

public class SpreadEnvoyActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private FrameLayout frameLayout;
    private ImageView ivSpreadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.spread_envory_activity, "推广大使");
        setDarkStatusIcon(true);
        frameLayout = findViewById(R.id.frame);
        ivSpreadImage = findViewById(R.id.ivSpreadImage);
        frameLayout.setDrawingCacheEnabled(true);
        frameLayout.buildDrawingCache();
        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvUserShared = findViewById(R.id.tvUserShared);
        String meStr;
        if (!TextUtils.isEmpty(App.getInstance().getCurrentUser().getUserName())) {
            meStr = "我是  " + App.getInstance().getCurrentUser().getUserName();
        } else {
            meStr = "我是  " + App.getInstance().getCurrentUser().getAccountName();
        }
        tvUserName.setText(meStr);
        String inviteStr = "邀请码:" + App.getInstance().getCurrentUser().getInviteCode();
        tvUserShared.setText(inviteStr);
        findViewById(R.id.tvShare).setOnClickListener(this);
        Observable.just(App.getInstance().getCurrentUser().getAccountName() + ".jpg").subscribeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(String s) {
                        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap("http://39.106.210.43/FxExam/resources/xueli.apk"
                                , DisplayUtil.dip2px(SpreadEnvoyActivity.this, 138)
                                , BitmapFactory.decodeResource(getResources(), R.drawable.icon_application)
                                , 0.3f);
                        return Observable.just(bitmap);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        ivSpreadImage.setImageBitmap(bitmap);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvShare) {
            checkPermission();
        }
    }

    public void savePicture(Bitmap bm, String fileName) {
        if (bm == null) {
            return;
        }
        File myCaptureFile = new File(App.getInstance().getExternalCacheDir(), fileName);
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterPermissionGranted(REQUEST_EXTERNAL)
    public void checkPermission() {
        if (!EasyPermissions.hasPermissions(this.getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.request_permissions),
                    REQUEST_EXTERNAL, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            rx.Observable.just(App.getInstance().getCurrentUser().getAccountName() + ".jpg").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            Bitmap bmp = frameLayout.getDrawingCache();
                            savePicture(bmp, s);
                        }
                    })
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(String s) {
                            new Share2.Builder(SpreadEnvoyActivity.this)
                                    .setContentType(ShareContentType.IMAGE)
                                    .setShareFileUri(FileUtil.getFileUri(SpreadEnvoyActivity.this, null, new File(App.getInstance().getExternalCacheDir(), s)))
                                    .setTitle("分享学历无忧")
                                    .build()
                                    .shareBySystem();
                        }
                    });
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

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
