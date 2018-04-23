package com.xueli.application.view.user.spread_envoy;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.library.utils.DisplayUtil;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.util.QRCodeUtil;
import com.xueli.application.view.BaseActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import gdut.bsx.share2.FileUtil;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;
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

public class SpreadEnvoyActivity extends BaseActivity {

    private FrameLayout frameLayout;
    private ImageView ivSpreadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.spread_envory_activity, "推广大使");
        frameLayout = findViewById(R.id.frame);
        ivSpreadImage = findViewById(R.id.ivSpreadImage);
        frameLayout.setDrawingCacheEnabled(true);
        frameLayout.buildDrawingCache();


        Observable.just(App.getInstance().getCurrentUser().getAccountName() + ".jpg").subscribeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(String s) {
                        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap("大家好", DisplayUtil.dip2px(SpreadEnvoyActivity.this, 138));
                        return Observable.just(bitmap);
                    }
                }).subscribe(new Subscriber<Bitmap>() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
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
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}
