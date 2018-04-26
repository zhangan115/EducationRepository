package com.xueli.application.view.user.vip;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xueli.application.R;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.view.MvpActivity;

import java.util.ArrayList;
import java.util.List;


public class VipActivity extends MvpActivity<VipContract.Presenter> implements VipContract.View {

    private List<ImageView> datas;
    private ViewPager mViewPager;
    private int mCurrentItem;
    private boolean isAL = true;
    private ImageView ivAl, ivWx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.vip_activity, "会员充值");
        datas = new ArrayList<>();
        ImageView iv1 = new ImageView(VipActivity.this);
        iv1.setImageDrawable(findDrawById(R.drawable.vip_icon_118));
        ImageView iv2 = new ImageView(VipActivity.this);
        iv2.setImageDrawable(findDrawById(R.drawable.vip_icon_280));
        ImageView iv3 = new ImageView(VipActivity.this);
        iv3.setImageDrawable(findDrawById(R.drawable.vip_icon_380));
        datas.add(iv1);
        datas.add(iv2);
        datas.add(iv3);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(3);
        int pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.0f / 5.0f);
        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = pagerWidth;
        }
        mViewPager.setLayoutParams(lp);
        mViewPager.setPageMargin(-50);
        LinearLayout ll_main = findViewById(R.id.llViewPager);
        ll_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mViewPager.dispatchTouchEvent(motionEvent);
            }
        });
        mViewPager.setPageTransformer(true, new GallyPageTransformer());
        mViewPager.setAdapter(new MyViewPagerAdapter(datas));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mCurrentItem = 0;
        findViewById(R.id.btnSure).setOnClickListener(this);
        findViewById(R.id.ll_al).setOnClickListener(this);
        findViewById(R.id.ll_wx).setOnClickListener(this);
        ivAl = findViewById(R.id.iv_al);
        ivWx = findViewById(R.id.iv_wx);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_al:
                isAL = true;
                chooseState();
                break;
            case R.id.ll_wx:
                isAL = false;
                chooseState();
                break;
            case R.id.btnSure:
                if (isAL) {

                } else {

                }
                break;
        }
    }

    private void chooseState() {
        if (isAL) {
            ivAl.setImageDrawable(findDrawById(R.drawable.icon_btn_radio2));
            ivWx.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
        } else {
            ivWx.setImageDrawable(findDrawById(R.drawable.icon_btn_radio2));
            ivAl.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
        }
    }

    @Override
    protected void onBindPresenter() {
        new VipPresenter(UserRepository.getRepository(this), this);
    }

    @Override
    public void setPresenter(VipContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<ImageView> listViews;

        public MyViewPagerAdapter(List<ImageView> listViews) {
            this.listViews = listViews;
        }

        @Override
        public int getCount() {
            return listViews == null ? 0 : listViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = listViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(listViews.get(position));
        }
    }


    public class GallyPageTransformer implements ViewPager.PageTransformer {
        private static final float min_scale = 0.85f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
            float rotate = 20 * Math.abs(position);
            if (position < -1) {

            } else if (position < 0) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setRotationY(rotate);
            } else if (position >= 0 && position < 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setRotationY(-rotate);
            } else if (position >= 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setRotationY(-rotate);
            }
        }
    }

}
