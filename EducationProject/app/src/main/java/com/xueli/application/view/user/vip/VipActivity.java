package com.xueli.application.view.user.vip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.library.utils.GlideUtils;
import com.orhanobut.logger.Logger;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.bean.user.AlPaySuccessCallBack;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VipContent;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.util.UserUtils;
import com.xueli.application.view.MvpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VipActivity extends MvpActivity<VipContract.Presenter> implements VipContract.View {

    private List<ImageView> datas;
    private List<VipContent> vipContentList;
    private ViewPager mViewPager;
    private int mCurrentItem;
    private boolean isAL = true;
    private ImageView ivAl, ivWx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.vip_activity, "会员充值");
        setDarkStatusIcon(true);
        datas = new ArrayList<>();
        vipContentList = new ArrayList<>();
        ImageView ivUserPhoto = findViewById(R.id.ivUserPhoto);
        GlideUtils.ShowCircleImage(this, App.getInstance().getCurrentUser().getHeadImage(), ivUserPhoto, R.drawable.img_avatar_default);
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
        mViewPager.setPageTransformer(true, new GailyPageTransformer());
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
        refreshVipUi();
        mPresenter.getVipCardList();
    }

    private void refreshVipUi() {
        ImageView ivUserVip = findViewById(R.id.ivUserVip);
        TextView tvIsVip = findViewById(R.id.tvIsVip);
        if (UserUtils.isVip(App.getInstance().getCurrentUser())) {
            ivUserVip.setImageDrawable(findDrawById(R.drawable.tag_vip));
            tvIsVip.setVisibility(View.GONE);
        } else {
            ivUserVip.setImageDrawable(findDrawById(R.drawable.tag_vip2));
            tvIsVip.setVisibility(View.VISIBLE);
        }
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
                    //支付宝
                    Map<String, String> map = new HashMap<>();
                    map.put("payType", String.valueOf(1));
                    map.put("cardId", String.valueOf(vipContentList.get(mCurrentItem).getId()));
                    map.put("appType", "android");
                    map.put("payObjective", "0");//0为会员卡1为学费
                    mPresenter.paySchoolAl(map);
                } else {
                    //微信
                    Map<String, String> map = new HashMap<>();
                    map.put("payType", String.valueOf(0));
                    map.put("cardId", String.valueOf(vipContentList.get(mCurrentItem).getId()));
                    map.put("appType", "android");
                    map.put("payObjective", "0");//0为会员卡1为学费
                    mPresenter.paySchoolWeiXin(map);
                }
                break;
        }
    }

    private int AL_PAY_FLAG = 100;

    private void alPay(final String orderStr) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask alPay = new PayTask(VipActivity.this);
                Map<String, String> result = alPay.payV2(orderStr, true);
                Message msg = new Message();
                msg.what = AL_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == AL_PAY_FLAG) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Logger.d(resultInfo);
                    JSONObject json;
                    try {
                        json = new JSONObject(resultInfo);
                        AlPaySuccessCallBack callBack = new Gson().fromJson(json.toString(), AlPaySuccessCallBack.class);
                        Map<String, String> map = new HashMap<>();
                        map.put("outTradeNo", callBack.getAlipay_trade_app_pay_response().getOut_trade_no());
                        map.put("trade_no", callBack.getAlipay_trade_app_pay_response().getTrade_no());
                        mPresenter.paySuccessCallBack(map);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Logger.d(resultInfo);
                    Logger.d(resultStatus);
                    App.getInstance().showToast("支付失败");
                }
            }
        }
    };


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

    @Override
    public void showVipContent(List<VipContent> list) {
        vipContentList.clear();
        vipContentList.addAll(list);
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < vipContentList.size(); i++) {
            viewList.add(LayoutInflater.from(this).inflate(R.layout.item_vip, null));
        }
        mViewPager.setAdapter(new VipViewPagerAdapter(viewList));
    }

    @Override
    public void paySuccess(User user) {
        App.getInstance().setCurrentUser(user);
        refreshVipUi();
    }

    @Override
    public void showAlOrderStr(String orderStr) {

    }

    @Override
    public void payAli(String payMessage) {
        alPay(payMessage);
    }

    @Override
    public void payWeiXin(String payMessage) {

    }

    @Override
    public void showMessage(String message) {

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<ImageView> listViews;

        MyViewPagerAdapter(List<ImageView> listViews) {
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

    public class VipViewPagerAdapter extends PagerAdapter {
        private List<View> listViews;

        VipViewPagerAdapter(List<View> listViews) {
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

        private int[] drawables = new int[]{R.drawable.card_f, R.drawable.card_s, R.drawable.card_t};

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = listViews.get(position);
            TextView price = view.findViewById(R.id.tv_price);
            ImageView imageView = view.findViewById(R.id.iv_bg);
            price.setText(vipContentList.get(position).getPrice() + "元");
            imageView.setImageDrawable(findDrawById(drawables[position % 3]));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(listViews.get(position));
        }
    }


    public class GailyPageTransformer implements ViewPager.PageTransformer {
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
