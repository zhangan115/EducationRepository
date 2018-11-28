package com.xueli.application.view.home.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.library.utils.GlideUtils;
import com.orhanobut.logger.Logger;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.bean.user.AlPaySuccessCallBack;
import com.xueli.application.mode.bean.user.PaySchoolList;
import com.xueli.application.mode.user.UserRepository;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.user.vip.PayResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayActivity extends MvpActivity<PayContract.Presenter> implements PayContract.View {

    //学校
    private long schoolId = 0;
    private String schoolName = "";
    private TextView schoolNameTextView;
    private static int SCHOOL_REQUEST = 100;
    //类型
    private long typeId = 0;
    private String typeName = "";
    private TextView typeNameTextView;
    private static int TYPE_REQUEST = 101;
    //年级
    private long gradeId = 0;
    private String gradeName = "";
    private TextView gradeNameTextView;
    private static int GRADE_REQUEST = 102;
    //data
    private List<PaySchoolList> datas = new ArrayList<>();
    private PaySchoolList currentSchool;
    private PaySchoolList.TuitionListBean currentTuition;
    private int payType = 1;//0 al 1 weiXin
    private boolean isNot = false;
    //view
    private TextView studyMoneyTv;
    private TextView tableMoneyTv;
    private TextView allMoneyTv;
    private ImageView chooseImageView;
    private boolean canPay = true;
    private Float money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_pay, "学费缴纳");

        findViewById(R.id.btnSure).setOnClickListener(this);
        schoolNameTextView = findViewById(R.id.schoolNameTv);
        typeNameTextView = findViewById(R.id.typeNameTv);
        gradeNameTextView = findViewById(R.id.gradeNameTv);
        studyMoneyTv = findViewById(R.id.studyMoneyTv);
        tableMoneyTv = findViewById(R.id.tableMoneyTv);
        allMoneyTv = findViewById(R.id.allMoneyTv);
        chooseImageView = findViewById(R.id.chooseTable);
        TextView userName = findViewById(R.id.userName);
        TextView userCardNum = findViewById(R.id.userCardNum);
        ImageView ivUserPhoto = findViewById(R.id.userPhoto);
        if (App.getInstance().getCurrentUser() != null) {
            GlideUtils.ShowCircleImage(this, App.getInstance().getCurrentUser().getHeadImage(), ivUserPhoto, R.drawable.img_avatar_default);
            userName.setText("姓名： " + App.getInstance().getCurrentUser().getRealName());
            userCardNum.setText("身份证:  " + App.getInstance().getCurrentUser().getIdcard());
            findViewById(R.id.chooseSchoolLayout).setOnClickListener(this);
            findViewById(R.id.chooseTypeLayout).setOnClickListener(this);
            findViewById(R.id.chooseGradeLayout).setOnClickListener(this);
        }
        mPresenter.getPay();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.chooseSchoolLayout:
                if (datas.size() == 0) return;
                Intent schoolIntent = new Intent(this, ChoosePayActivity.class);
                schoolIntent.putExtra("title", "选择院校");
                long[] schoolIds = new long[datas.size()];
                ArrayList<String> schoolList = new ArrayList<>();
                for (int i = 0; i < datas.size(); i++) {
                    schoolIds[i] = datas.get(i).getSchoolId();
                    schoolList.add(datas.get(i).getSchoolName());
                }
                if (schoolId != 0) {
                    schoolIntent.putExtra("id", schoolId);
                }
                schoolIntent.putStringArrayListExtra("data", schoolList);
                schoolIntent.putExtra("ids", schoolIds);
                startActivityForResult(schoolIntent, SCHOOL_REQUEST);
                break;
            case R.id.chooseTypeLayout:
                if (schoolId == 0) {
                    showMessage("请选择院校");
                    return;
                }
                PaySchoolList school = null;
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getSchoolId() == schoolId) {
                        school = datas.get(i);
                        break;
                    }
                }
                if (school == null || school.getTuitionList() == null || school.getTuitionList().size() == 0)
                    return;
                Intent typeIntent = new Intent(this, ChoosePayActivity.class);
                typeIntent.putExtra("title", "选择类别");
                long[] typeIds = new long[school.getTuitionList().size()];
                ArrayList<String> typeList = new ArrayList<>();
                for (int i = 0; i < school.getTuitionList().size(); i++) {
                    PaySchoolList.TuitionListBean bean = school.getTuitionList().get(i);
                    typeIds[i] = bean.getTuitionId();
                    typeList.add(bean.getTuitionType());
                }
                if (typeId != 0) {
                    typeIntent.putExtra("id", typeId);
                }
                typeIntent.putStringArrayListExtra("data", typeList);
                typeIntent.putExtra("ids", typeIds);
                startActivityForResult(typeIntent, TYPE_REQUEST);
                break;
            case R.id.chooseGradeLayout:
                if (typeId == 0) return;
                List<String> items = new ArrayList<>();
                if (currentSchool == null) return;
                if (currentTuition == null) return;
                if (TextUtils.isEmpty(currentTuition.getGrade1Tuition())) {
                    return;
                }
                items.add("一年级");
                if (!TextUtils.isEmpty(currentTuition.getGrade2Tuition())) {
                    items.add("二年级");
                }
                if (!TextUtils.isEmpty(currentTuition.getGrade3Tuition())) {
                    items.add("三年级");
                }
                new MaterialDialog.Builder(this).items(items).itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        gradeId = position;
                        gradeNameTextView.setText(text);
                        float allMoney = 0;
                        if (position == 0) {
                            studyMoneyTv.setText(currentTuition.getGrade1Tuition());
                            allMoney = Float.valueOf(currentTuition.getGrade1Tuition());
                            tableMoneyTv.setText(currentTuition.getNetStudyFee() + "元/" + currentTuition.getNetStudyYear() + "年");
                            allMoney = +allMoney + Float.valueOf(currentTuition.getNetStudyFee());
                            isNot = true;
                            chooseImageView.setImageDrawable(findDrawById(R.drawable.icon_btn_radio2));
                        } else if (position == 1) {
                            isNot = false;
                            chooseImageView.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
                            studyMoneyTv.setText(currentTuition.getGrade2Tuition());
                            allMoney = Float.valueOf(currentTuition.getGrade2Tuition());
                            money = Float.valueOf(currentTuition.getGrade2Tuition());
                        } else {
                            isNot = false;
                            chooseImageView.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
                            studyMoneyTv.setText(currentTuition.getGrade3Tuition());
                            allMoney = Float.valueOf(currentTuition.getGrade3Tuition());
                            money = Float.valueOf(currentTuition.getGrade3Tuition());
                        }
                        allMoneyTv.setText(String.valueOf(allMoney) + "元");
                    }
                }).build().show();
                break;
            case R.id.btnSure:
                if (schoolId == 0) return;
                if (typeId == 0) return;
                if (gradeId == -1) return;
                new MaterialDialog.Builder(this).content("当前准备去缴费!").positiveText("确定").negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                final BottomSheetDialog bottomDialog = new BottomSheetDialog(PayActivity.this);
                                View view = LayoutInflater.from(PayActivity.this).inflate(R.layout.bottom_pay_layout, null);
                                payType = 1;
                                view.findViewById(R.id.closeImageView).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        bottomDialog.dismiss();
                                    }
                                });
                                view.findViewById(R.id.toPayButton).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (!canPay) {
                                            return;
                                        }
                                        Map<String, String> map = new HashMap<>();
                                        map.put("schoolId", String.valueOf(schoolId));
                                        map.put("majorTypeId", String.valueOf(typeId));
                                        map.put("tuitionId", String.valueOf(currentTuition.getTuitionId()));
                                        map.put("classGrade", String.valueOf(gradeId + 1));
                                        map.put("payType", String.valueOf(payType));
                                        map.put("appType", "android");
                                        map.put("payObjective", "1");//0为会员卡1为学费
                                        if (isNot) {
                                            map.put("isnot", "0");
                                        } else {
                                            map.put("isNot", "1");
                                        }
                                        mPresenter.paySchoolAl(map);
                                        canPay = false;
                                    }
                                });
                                final ImageView alImage = view.findViewById(R.id.alChoose);
                                final ImageView weiXinImage = view.findViewById(R.id.weiXinChoose);
                                alImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alImage.setImageDrawable(findDrawById(R.drawable.icon_btn_radio2));
                                        weiXinImage.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
                                        payType = 1;
                                    }
                                });
//                                weiXinImage.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        alImage.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
//                                        weiXinImage.setImageDrawable(findDrawById(R.drawable.icon_btn_radio2));
//                                        payType = 0;
//                                    }
//                                });
                                bottomDialog.setContentView(view);
                                bottomDialog.show();
                            }
                        }).build().show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCHOOL_REQUEST) {
                if (data != null) {
                    schoolId = data.getLongExtra("id", 0);
                    schoolName = data.getStringExtra("name");
                    if (schoolId != 0 && !TextUtils.isEmpty(schoolName)) {
                        for (PaySchoolList school : this.datas) {
                            if (school.getSchoolId() == schoolId) {
                                currentSchool = school;
                                break;
                            }
                        }
                        schoolNameTextView.setText(schoolName);
                    }
                    typeId = 0;
                    typeNameTextView.setText("");
                    gradeId = -1;
                    gradeNameTextView.setText("");
                    studyMoneyTv.setText("");
                    tableMoneyTv.setText("");
                    allMoneyTv.setText("");
                    isNot = false;
                    chooseImageView.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
                }
            } else if (requestCode == TYPE_REQUEST) {
                typeId = data.getLongExtra("id", 0);
                typeName = data.getStringExtra("name");
                if (typeId != 0 && !TextUtils.isEmpty(typeName)) {
                    typeNameTextView.setText(typeName);
                }
                for (PaySchoolList.TuitionListBean tuition : this.currentSchool.getTuitionList()) {
                    if (typeId == tuition.getTuitionId()) {
                        this.currentTuition = tuition;
                        break;
                    }
                }
                gradeId = -1;
                gradeNameTextView.setText("");
                studyMoneyTv.setText("");
                tableMoneyTv.setText("");
                allMoneyTv.setText("");
                isNot = false;
                chooseImageView.setImageDrawable(findDrawById(R.drawable.icon_btn_radio1));
            }
        }
    }

    @Override
    public void payAli(String payMessage) {
        alPay(payMessage);
    }

    @Override
    public void payWeiXin(String payMessage) {

    }

    private int AL_PAY_FLAG = 100;

    private void alPay(final String orderStr) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask alPay = new PayTask(PayActivity.this);
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
                    try {
                        JSONObject json = new JSONObject(resultInfo);
                        AlPaySuccessCallBack callBack = new Gson().fromJson(json.toString(), AlPaySuccessCallBack.class);
                        Map<String, String> map = new HashMap<>();
                        map.put("outTradeNo", callBack.getAlipay_trade_app_pay_response().getOut_trade_no());
                        map.put("trade_no", callBack.getAlipay_trade_app_pay_response().getTrade_no());
                        mPresenter.paySuccessCallBack(map);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        canPay = true;
                    }
                    ///outTradeNo系统订单号 trade_no为第三方订单号
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    canPay = true;
                    Logger.d(resultInfo);
                    Logger.d(resultStatus);
                    App.getInstance().showToast("支付失败");
                }
            }
        }
    };

    @Override
    public void showPaySchoolList(List<PaySchoolList> list) {
        datas.clear();
        datas.addAll(list);
    }

    @Override
    public void showMessage(@Nullable String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void showLoading() {
        showProgressDialog("请求中,请稍等");
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void finishSuccess() {
        showMessage("支付成功!");
        finish();
    }

    @Override
    public void payFinish() {
        canPay = true;
    }

    @Override
    public void setPresenter(PayContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBindPresenter() {
        new PayPresenter(UserRepository.getRepository(this), this);
    }
}
