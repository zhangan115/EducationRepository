package com.xueli.application.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantInt;
import com.xueli.application.mode.Injection;
import com.xueli.application.mode.bean.user.NewVersion;
import com.xueli.application.util.DownloadAppUtils;
import com.xueli.application.util.UpdateManager;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.bank.BankFragment;
import com.xueli.application.view.home.HomeFragment;
import com.xueli.application.view.study.StudyFragment;
import com.xueli.application.view.user.UserFragment;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends MvpActivity<MainContract.Presenter> implements MainContract.View, EasyPermissions.PermissionCallbacks, UpdateManager.OnCancelListener {

    private int selectPosition = 0;
    private ArrayList<Fragment> mFragments;
    private NewVersion newVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView(savedInstanceState);
        checkPermission();
    }

    @Override
    protected void onBindPresenter() {
        new MainPresenter(Injection.getInjection().provideUserRepository(getApp()), this);
    }

    private void initView(Bundle savedInstanceState) {
        mFragments = getFragments();
        AHBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.str_first_nav_1, R.drawable.icon_toolbar_home2, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.str_first_nav_2, R.drawable.icon_toolbar_learn2, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.str_first_nav_3, R.drawable.icon_toolbar_item_bank2, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.str_first_nav_4, R.drawable.icon_toolbar_mine2, R.color.colorPrimary);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.setTitleTextSizeInSp(14f, 14f);
        bottomNavigation.setBackgroundColor(findColorById(R.color.colorWhite));
        bottomNavigation.setDefaultBackgroundColor(findColorById(R.color.colorWhite));
        bottomNavigation.setForceTint(false);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(findColorById(R.color.colorPrimary));
        bottomNavigation.setInactiveColor(findColorById(R.color.text_gray_999));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (selectPosition != position) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.hide(mFragments.get(selectPosition));
                    if (mFragments.get(position).isAdded()) {
                        ft.show(mFragments.get(position));
                    } else {
                        ft.add(R.id.frame_container, mFragments.get(position), "tag_" + position);
                    }
                    selectPosition = position;
                    ft.commit();
                    return true;
                }
                return false;
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (savedInstanceState != null) {
            selectPosition = savedInstanceState.getInt("selectPosition");
            if (mFragments.get(selectPosition).isAdded()) {
                transaction.show(mFragments.get(selectPosition));
            } else {
                transaction.add(R.id.frame_container, mFragments.get(selectPosition), "tag_" + selectPosition);
            }
        } else {
            transaction.add(R.id.frame_container, mFragments.get(selectPosition), "tag_" + selectPosition);
        }
        bottomNavigation.setCurrentItem(selectPosition);
        transaction.commit();
    }

    public ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("tag_0");
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        BankFragment bankFragment = (BankFragment) getSupportFragmentManager().findFragmentByTag("tag_1");
        if (bankFragment == null) {
            bankFragment = BankFragment.newInstance();
        }
        StudyFragment studyFragment = (StudyFragment) getSupportFragmentManager().findFragmentByTag("tag_2");
        if (studyFragment == null) {
            studyFragment = StudyFragment.newInstance();
        }
        UserFragment userFragment = (UserFragment) getSupportFragmentManager().findFragmentByTag("tag_3");
        if (userFragment == null) {
            userFragment = UserFragment.newInstance();
        }
        fragments.add(homeFragment);
        fragments.add(bankFragment);
        fragments.add(studyFragment);
        fragments.add(userFragment);
        return fragments;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putInt("selectPosition", selectPosition);
        }
    }

    @Override
    public void showMessage(@Nullable String message) {
        getApp().showToast(message);
    }

    @Override
    public void showNewVersion(final NewVersion newVersion) {
        this.newVersion = newVersion;
        if (newVersion.getVersion() > ConstantInt.VERSION_NO) {
            new MaterialDialog.Builder(this)
                    .content(newVersion.getNote())
                    .negativeText("取消")
                    .positiveText("确定")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            new UpdateManager(MainActivity.this, newVersion.getUrl()).updateApp(MainActivity.this);
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (newVersion.getFlag() == 1) {
                                new MaterialDialog.Builder(MainActivity.this)
                                        .content("改版本必须升级否则无法使用")
                                        .positiveText(R.string.sure)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                new UpdateManager(MainActivity.this, newVersion.getUrl()).updateApp(MainActivity.this);
                                            }
                                        })
                                        .negativeText(R.string.cancel)
                                        .cancelable(false)
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                finish();
                                            }
                                        }).show();
                            }
                        }
                    })
                    .show();
        }
    }

    @AfterPermissionGranted(REQUEST_EXTERNAL)
    public void checkPermission() {
        if (!EasyPermissions.hasPermissions(this.getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.request_permissions),
                    REQUEST_EXTERNAL, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            mPresenter.getNewVersion();
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

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    long currentTime = 0;

    @Override
    public void onBackPressed() {
        if (currentTime == 0) {
            currentTime = System.currentTimeMillis();
            showMessage("再次点击将退出应用");
        } else {
            if (System.currentTimeMillis() - currentTime < 3000) {
                super.onBackPressed();
            } else {
                currentTime = 0;
            }
        }

    }

    @Override
    public void onCancelUpload() {
        if (this.newVersion != null && this.newVersion.getFlag() == 1) {
            finish();
        }
    }
}
