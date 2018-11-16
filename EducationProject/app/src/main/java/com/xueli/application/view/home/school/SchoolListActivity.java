package com.xueli.application.view.home.school;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.library.adapter.RVAdapter;
import com.library.utils.DisplayUtil;
import com.library.widget.ExpendRecycleView;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.mode.study.StudyRepository;
import com.xueli.application.view.MvpActivity;

import java.util.ArrayList;
import java.util.List;

public class SchoolListActivity extends MvpActivity<SchoolContract.Presenter> implements SchoolContract.View {

    private ExpendRecycleView recycleView;
    private List<Integer> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_school_list, "院校招生");
        datas = new ArrayList<>();
        datas.add(R.drawable.guizhouyikeda);
        datas.add(R.drawable.hunankejida);
        datas.add(R.drawable.jiangxizhongyiyao);
        datas.add(R.drawable.jilinjiaoyu);
        datas.add(R.drawable.zunyishifan);
        datas.add(R.drawable.zunyiyikeda);
        datas.add(R.drawable.xiangtan);
        datas.add(R.drawable.hebeigongcheng);
        datas.add(R.drawable.qiandongnan);
        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new GridLayoutManager(this, 2));
        final int width = (getResources().getDisplayMetrics().widthPixels - DisplayUtil.dip2px(this, 10)) / 2;
        final int height = width * 15 / 16;
        RVAdapter<Integer> adapter = new RVAdapter<Integer>(recycleView, datas, R.layout.item_school) {
            @Override
            public void showData(ViewHolder vHolder, Integer data, int position) {
                ImageView imageView = (ImageView) vHolder.getView(R.id.icon);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                if (params != null) {
                    params.height = height;
                    params.width = width;
                    imageView.setLayoutParams(params);
                }
                imageView.setImageDrawable(findDrawById(data));
            }
        };
        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    public void showSchool() {

    }

    @Override
    public void loginLoading() {

    }

    @Override
    public void loginHideLoading() {

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
