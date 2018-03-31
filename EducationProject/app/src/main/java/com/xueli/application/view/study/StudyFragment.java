package com.xueli.application.view.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xueli.application.R;
import com.xueli.application.view.MvpFragment;
import com.xueli.application.view.study.list.StudyListFragment;

/**
 * 学习
 * Created by pingan on 2018/3/4.
 */

public class StudyFragment extends MvpFragment {

    public static StudyFragment newInstance() {
        Bundle args = new Bundle();
        StudyFragment fragment = new StudyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment, container, false);
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        ViewPager viewPager = rootView.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return StudyListFragment.newInstance(position + 4);
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
