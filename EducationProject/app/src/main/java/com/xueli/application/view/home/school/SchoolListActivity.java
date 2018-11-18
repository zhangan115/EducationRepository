package com.xueli.application.view.home.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.library.adapter.RVAdapter;
import com.library.utils.DisplayUtil;
import com.library.utils.GlideUtils;
import com.library.utils.SPHelper;
import com.library.widget.ExpendRecycleView;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.school.SchoolBean;
import com.xueli.application.mode.study.StudyRepository;
import com.xueli.application.view.MvpActivity;
import com.xueli.application.view.home.HomeFragmentV2;
import com.xueli.application.view.web.MessageDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class SchoolListActivity extends MvpActivity<SchoolContract.Presenter> implements SchoolContract.View {

    private ExpendRecycleView recycleView;
    private List<SchoolBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_school_list, "院校招生");
        datas = new ArrayList<>();
        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new GridLayoutManager(this, 2));
        final int width = (getResources().getDisplayMetrics().widthPixels - DisplayUtil.dip2px(this, 10)) / 2;
        final int height = width * 15 / 16;
        RVAdapter<SchoolBean> adapter = new RVAdapter<SchoolBean>(recycleView, datas, R.layout.item_school) {
            @Override
            public void showData(ViewHolder vHolder, SchoolBean data, int position) {
                ImageView imageView = (ImageView) vHolder.getView(R.id.icon);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                if (params != null) {
                    params.height = height;
                    params.width = width;
                    imageView.setLayoutParams(params);
                }
                GlideUtils.ShowImage(SchoolListActivity.this, data.getFirstPic(), imageView, R.drawable.img_unfinished);
            }
        };
        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String title = datas.get(position).getTitle();
                String content = datas.get(position).getDetail();
                SPHelper.write(SchoolListActivity.this, ConstantStr.SP_CACHE, ConstantStr.SP_MESSAGE_DETAIL, content);
                Intent intent = new Intent(SchoolListActivity.this, MessageDetailActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, title);
                startActivity(intent);
            }
        });
        mPresenter.getSchool();
    }

    @Override
    public void showSchool(List<SchoolBean> list) {
        datas.clear();
        datas.addAll(list);
        recycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMessage(@Nullable String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(SchoolContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onBindPresenter() {
        new SchoolPresenter(StudyRepository.getRepository(this), this);
    }
}
