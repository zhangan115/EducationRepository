package com.xueli.application.view.user.subject_error;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.adapter.RVAdapter;
import com.library.utils.DisplayUtil;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.ExamList;
import com.xueli.application.mode.bean.exam.FaultExam;
import com.xueli.application.mode.bean.exam.QuestionType;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.bank.examination.ExaminationActivity;
import com.xueli.application.view.bank.list.BankListActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 过往答错
 * Created by pingan on 2018/3/25.
 */

public class ErrorSubjectActivity extends MvpActivity<ErrorSubjectContract.Presenter> implements ErrorSubjectContract.View
        , SwipeRefreshLayout.OnRefreshListener {

    private RecycleRefreshLoadLayout refreshLoadLayout;
    private ExpendRecycleView expendRecycleView;
    private DrawerLayout drawerLayout;
    private RelativeLayout noDataLayout;
    private LinearLayout llQuestionTypes;
    //筛选
    private EditText etBankName;
    private TextView[] tvTypes = new TextView[3];
    private TextView[] tvYears = new TextView[3];
    private TextView[] tvSubjects;
    //data
    private List<FaultExam> datas;
    private Map<String, String> map;
    private int currentYear;
    private List<QuestionType> questionTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.error_subject_activity, "过往答错");
        setDarkStatusIcon(true);
        initView();
        map = new HashMap<>();
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                App.getInstance().hideSoftKeyBoard(ErrorSubjectActivity.this);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        expendRecycleView.setLayoutManager(new GridLayoutManager(this, 1));
        datas = new ArrayList<>();
        RVAdapter<FaultExam> adapter = new RVAdapter<FaultExam>(expendRecycleView, datas, R.layout.subject_error_item) {
            @Override
            public void showData(ViewHolder vHolder, FaultExam data, int position) {
                TextView errorSubject = (TextView) vHolder.getView(R.id.tvSubjectContent);
                errorSubject.setText(data.getTitle());
            }
        };
        expendRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ErrorSubjectActivity.this, ExaminationActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, datas.get(position).getExamedPaperId());
                intent.putExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, true);
                startActivity(intent);
            }
        });
        refreshLoadLayout.setOnRefreshListener(this);
        mPresenter.getErrorSubjectList(map);
        mPresenter.getQuestionType();
    }

    private void initView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        refreshLoadLayout = findViewById(R.id.rcrLayout);
        expendRecycleView = findViewById(R.id.expendRv);
        noDataLayout = findViewById(R.id.rlNoData);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.getHeaderView(0).findViewById(R.id.btnReset).setOnClickListener(this);
        navigationView.getHeaderView(0).findViewById(R.id.btnSure).setOnClickListener(this);
        etBankName = navigationView.getHeaderView(0).findViewById(R.id.etBankName);
        tvTypes[0] = navigationView.getHeaderView(0).findViewById(R.id.tvType1);
        tvTypes[1] = navigationView.getHeaderView(0).findViewById(R.id.tvType2);
        tvTypes[2] = navigationView.getHeaderView(0).findViewById(R.id.tvType3);
        llQuestionTypes = navigationView.getHeaderView(0).findViewById(R.id.llQuestionTypes);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        currentYear = calendar.get(Calendar.YEAR);
        tvYears[0] = navigationView.getHeaderView(0).findViewById(R.id.tvYear1);
        tvYears[1] = navigationView.getHeaderView(0).findViewById(R.id.tvYear2);
        tvYears[2] = navigationView.getHeaderView(0).findViewById(R.id.tvYear3);
        tvYears[0].setText(String.valueOf(currentYear));
        tvYears[1].setText(String.valueOf(currentYear - 1));
        tvYears[2].setText(String.valueOf(currentYear - 2));


        for (TextView tv : tvTypes) {
            tv.setOnClickListener(this);
        }
        for (TextView tv : tvYears) {
            tv.setOnClickListener(this);
        }
        findViewById(R.id.tvFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
    }

    private void setTextViewState(int position, TextView[] textViews) {
        if (textViews == null) return;
        for (int i = 0; i < textViews.length; i++) {
            if (textViews[i] == null) continue;
            if (i == position) {
                textViews[i].setTextColor(findColorById(R.color.text_blue));
                textViews[i].setBackground(findDrawById(R.drawable.shape_edit_bg_blue));
            } else {
                textViews[i].setTextColor(findColorById(R.color.text_gray_333));
                textViews[i].setBackground(findDrawById(R.drawable.shape_edit_bg));
            }
        }
    }


    @Override
    protected void onBindPresenter() {
        new ErrorSubjectPresenter(ExamRepository.getRepository(this), this);
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void showQuestionData(List<QuestionType> list) {
        questionTypes = list;
        tvSubjects = new TextView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            LinearLayout layout = new LinearLayout(this);
            LinearLayout.LayoutParams paramsLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setWeightSum(4);
            paramsLayout.setMargins(0, DisplayUtil.dip2px(this, 12), 0, 0);
            layout.setLayoutParams(paramsLayout);
            llQuestionTypes.addView(layout);
            for (int j = 0; j < 4; j++) {
                TextView textView = new TextView(this);
                tvSubjects[i] = textView;
                textView.setText(list.get(i).getTitle());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                textView.setTextColor(findColorById(R.color.text_gray_333));
                textView.setPadding(0, DisplayUtil.dip2px(this, 10)
                        , 0, DisplayUtil.dip2px(this, 10));
                textView.setGravity(Gravity.CENTER);
                textView.setBackground(findDrawById(R.drawable.shape_edit_bg));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                if (j == 1) {
                    params.setMargins(DisplayUtil.dip2px(this, 12), 0
                            , DisplayUtil.dip2px(this, 12), 0);
                } else if (j == 2) {
                    params.setMargins(0, 0
                            , DisplayUtil.dip2px(this, 12), 0);
                }
                textView.setLayoutParams(params);
                textView.setTag(i);
                textView.setOnClickListener(clickListener);
                layout.addView(textView);
                ++i;
                if (i == list.size()) {
                    break;
                }
            }
        }
    }

    @Override
    public void showLoading() {
        refreshLoadLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLoadLayout.setRefreshing(false);
    }

    @Override
    public void noData() {
        datas.clear();
        expendRecycleView.getAdapter().notifyDataSetChanged();
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(List<FaultExam> list) {
        datas.clear();
        datas.addAll(list);
        expendRecycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setPresenter(ErrorSubjectContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset:
                map.clear();
                setTextViewState(-1, tvTypes);
                setTextViewState(-1, tvYears);
                if (tvSubjects != null && tvSubjects.length > 0) {
                    setTextViewState(-1, tvSubjects);
                }
                etBankName.setText("");
                onRefresh();
                drawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.btnSure:
                String str = etBankName.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    map.put("title", str);
                } else {
                    map.remove("title");
                }
                onRefresh();
                drawerLayout.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.tvType1:
            case R.id.tvType2:
            case R.id.tvType3:
                String typeStr1 = (String) v.getTag();
                int type = Integer.valueOf(typeStr1);
                if (map.containsKey("pagerType")) {
                    String typeStr = map.get("pagerType");
                    if (String.valueOf(type + 1).equals(typeStr)) {
                        map.remove("pagerType");
                        setTextViewState(-1, tvTypes);
                    } else {
                        map.put("pagerType", String.valueOf(type + 1));
                        setTextViewState(type, tvTypes);
                    }
                } else {
                    map.put("pagerType", String.valueOf(type + 1));
                    setTextViewState(type, tvTypes);
                }
                break;
            case R.id.tvYear1:
            case R.id.tvYear2:
            case R.id.tvYear3:
                String yearStr = (String) v.getTag();
                int year = Integer.valueOf(yearStr);
                if (map.containsKey("pagerYear")) {
                    String typeStr = map.get("pagerYear");
                    if (String.valueOf(currentYear - year).equals(typeStr)) {
                        map.remove("pagerYear");
                        setTextViewState(-1, tvYears);
                    } else {
                        map.put("pagerYear", String.valueOf(currentYear - year));
                        setTextViewState(year, tvYears);
                    }
                } else {
                    map.put("pagerYear", String.valueOf(currentYear - year));
                    setTextViewState(year, tvYears);
                }
                break;
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (tvSubjects == null || tvSubjects.length == 0) return;
            int position = (int) v.getTag();
            map.put("questionCatalogId", String.valueOf(questionTypes.get(position).getId()));
            setTextViewState(position, tvSubjects);
        }
    };


    @Override
    public void onRefresh() {
        noDataLayout.setVisibility(View.GONE);
        datas.clear();
        expendRecycleView.getAdapter().notifyDataSetChanged();
        mPresenter.getErrorSubjectList(map);
    }
}
