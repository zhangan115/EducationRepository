package com.xueli.application.view.bank.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.DragEvent;
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
import com.xueli.application.common.ConstantInt;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.exam.ExamList;
import com.xueli.application.mode.bean.exam.QuestionType;
import com.xueli.application.mode.exam.ExamRepository;
import com.xueli.application.view.BaseActivity;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.bank.examination.ExaminationActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 历年真题，模拟试题
 * Created by pingan on 2018/3/13.
 */

public class BankListActivity extends MvpActivity<BankListContact.Presenter> implements SwipeRefreshLayout.OnRefreshListener
        , BankListContact.View {
    //view
    private RecycleRefreshLoadLayout refreshLoadLayout;
    private ExpendRecycleView expendRecycleView;
    @Nullable
    private DrawerLayout drawerLayout;
    private RelativeLayout noDataLayout;
    private LinearLayout llQuestionTypes;
    //筛选
    private EditText etBankName;
    private TextView[] tvTypes = new TextView[3];
    private TextView[] tvYears = new TextView[3];
    private TextView[] tvSubjects;
    //data
    private List<ExamList> datas;
    private String type, isYear;
    private Map<String, String> map;
    private int currentYear;
    private List<QuestionType> questionTypes;
    private boolean isChenRenGaoKao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tag = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        String[] types = tag.split(",");
        if (types.length == 2) {
            type = String.valueOf(types[0]);
            isYear = String.valueOf(types[1]);
        }
        isChenRenGaoKao = TextUtils.equals(type, "1");
        if (isChenRenGaoKao) {
            setLayoutAndToolbar(R.layout.bank_list_activity_1, isYear.equals("0") ? "模拟试题" : "历年真题");
        } else {
            setTheme(R.style.bankFilterStyle);
            transparentStatusBar();
            setLayoutAndToolbar(R.layout.bank_list_activity, isYear.equals("0") ? "模拟试题" : "历年真题");
        }
        initView();
        refreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        expendRecycleView.setLayoutManager(new GridLayoutManager(this, 1));
        if (drawerLayout != null) {
            drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(@NonNull View drawerView) {

                }

                @Override
                public void onDrawerClosed(@NonNull View drawerView) {
                    App.getInstance().hideSoftKeyBoard(BankListActivity.this);
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        }
        map = new HashMap<>();
        datas = new ArrayList<>();
        setTypeAndYear();
        RVAdapter<ExamList> adapter = new RVAdapter<ExamList>(expendRecycleView, datas, R.layout.bank_list_item) {
            @Override
            public void showData(ViewHolder vHolder, ExamList data, int position) {
                TextView tvExamName = (TextView) vHolder.getView(R.id.tvExamName);
                tvExamName.setText(data.getTitle());
            }
        };
        expendRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(BankListActivity.this, ExaminationActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, datas.get(position).getId());
                startActivity(intent);
            }
        });
        refreshLoadLayout.setOnRefreshListener(this);
        String type = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR_1);
        if (!TextUtils.isEmpty(type)) {
            String[] pagerTypes = type.split(",");
            map.put("questionCatalogId", pagerTypes[0]);
            map.put("pagerType", pagerTypes[1]);
        }
        mPresenter.getBankList(map);
        if (!isChenRenGaoKao) {
            mPresenter.getQuestionType();
        }
    }

    private void initView() {
        refreshLoadLayout = findViewById(R.id.rcrLayout);
        expendRecycleView = findViewById(R.id.expendRv);
        noDataLayout = findViewById(R.id.rlNoData);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView == null) {
            return;
        }
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
    }

    private void setTextViewState(int position, TextView[] textViews) {
        for (int i = 0; i < textViews.length; i++) {
            if (textViews[i] == null) {
                continue;
            }
            if (i == position) {
                textViews[i].setTextColor(findColorById(R.color.text_blue));
                textViews[i].setBackground(findDrawById(R.drawable.shape_edit_bg_blue));
            } else {
                textViews[i].setTextColor(findColorById(R.color.text_gray_333));
                textViews[i].setBackground(findDrawById(R.drawable.shape_edit_bg));
            }
        }
    }

    private void setTypeAndYear() {
        map.put("type", type);
        map.put("isYear", isYear);
    }

    @Override
    protected void onBindPresenter() {
        new BankListPresenter(ExamRepository.getRepository(this), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return !isChenRenGaoKao;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter && drawerLayout != null) {
            drawerLayout.openDrawer(Gravity.RIGHT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset:
                map.clear();
                setTypeAndYear();
                setTextViewState(-1, tvTypes);
                setTextViewState(-1, tvYears);
                setTextViewState(-1, tvSubjects);
                etBankName.setText("");
                onRefresh();
                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.btnSure:
                String str = etBankName.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    map.put("title", str);
                } else {
                    map.remove("title");
                }
                onRefresh();
                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
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

    @Override
    public void onRefresh() {
        noDataLayout.setVisibility(View.GONE);
        datas.clear();
        expendRecycleView.getAdapter().notifyDataSetChanged();
        mPresenter.getBankList(map);
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
    public void showData(List<ExamList> list) {
        datas.clear();
        datas.addAll(list);
        expendRecycleView.getAdapter().notifyDataSetChanged();
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            map.put("questionCatalogId", String.valueOf(questionTypes.get(position).getId()));
            setTextViewState(position, tvSubjects);
        }
    };

    @Override
    public void setPresenter(BankListContact.Presenter presenter) {
        mPresenter = presenter;
    }
}
