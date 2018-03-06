package com.xueli.application.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.xueli.application.R;
import com.xueli.application.mode.Injection;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.bank.BankFragment;
import com.xueli.application.view.home.HomeFragment;
import com.xueli.application.view.study.StudyFragment;
import com.xueli.application.view.user.UserFragment;

import java.util.ArrayList;

public class MainActivity extends MvpActivity<MainContract.Presenter> implements MainContract.View {

    private int selectPosition = 0;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
    }

    @Override
    protected void onBindPresenter() {
        new MainPresenter(Injection.getInjection().provideUserRepository(getApp()), this);
    }


    private void initView() {
        mFragments = getFragments();
        AHBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.str_first_nav_1, R.drawable.icon_home, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.str_first_nav_2, R.drawable.icon_home, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.str_first_nav_3, R.drawable.icon_home, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.str_first_nav_4, R.drawable.icon_home, R.color.colorPrimary);
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
        transaction.replace(R.id.frame_container, mFragments.get(selectPosition), "tag_" + selectPosition);
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
        StudyFragment studyFragment = (StudyFragment) getSupportFragmentManager().findFragmentByTag("tag_3");
        if (studyFragment == null) {
            studyFragment = StudyFragment.newInstance();
        }
        UserFragment userFragment = (UserFragment) getSupportFragmentManager().findFragmentByTag("tag_4");
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
    public void showMessage(@Nullable String message) {
        getApp().showToast(message);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
