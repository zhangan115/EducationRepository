package com.xueli.application.view.study.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.library.adapter.RVAdapter;
import com.library.utils.GlideUtils;
import com.library.utils.SPHelper;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.xueli.application.BuildConfig;
import com.xueli.application.R;
import com.xueli.application.app.App;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.mode.bean.study.StudyMessage;
import com.xueli.application.mode.study.StudyRepository;
import com.xueli.application.util.UserUtils;
import com.xueli.application.view.LazyLoadFragment;
import com.xueli.application.view.user.vip.VipActivity;
import com.xueli.application.view.web.MessageDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 展示学习列表
 * Created by pingan on 2018/3/7.
 */

public class StudyListFragment extends LazyLoadFragment implements RecycleRefreshLoadLayout.OnLoadListener
        , SwipeRefreshLayout.OnRefreshListener
        , StudyListContract.View {

    private static final String SHOW_LIST_TYPE = "show_type";
    private RecycleRefreshLoadLayout refreshLoadLayout;
    private ExpendRecycleView expendRecycleView;
    private RelativeLayout noDataLayout;
    private long type;
    private StudyListContract.Presenter mPresenter;
    private List<StudyMessage> datas;
    private boolean isRefresh;

    public static StudyListFragment newInstance(long position) {
        Bundle args = new Bundle();
        args.putLong(SHOW_LIST_TYPE, position);
        StudyListFragment fragment = new StudyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StudyListPresenter(StudyRepository.getRepository(getActivity()), this);
        if (getArguments() != null) {
            type = getArguments().getLong(SHOW_LIST_TYPE);
        }
    }

    @Override
    public void requestData() {
        this.datas.clear();
        expendRecycleView.getAdapter().notifyDataSetChanged();
        mPresenter.getStudyMessage(type);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_show_lsit_fragment, container, false);
        refreshLoadLayout = rootView.findViewById(R.id.rlyLayout);
        expendRecycleView = rootView.findViewById(R.id.expendRv);
        refreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        refreshLoadLayout.setOnRefreshListener(this);
        refreshLoadLayout.setOnLoadListener(this);
        expendRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        noDataLayout = rootView.findViewById(R.id.rlNoData);
        datas = new ArrayList<>();
        RVAdapter<StudyMessage> adapter = new RVAdapter<StudyMessage>(expendRecycleView, datas, R.layout.show_study_item) {
            @Override
            public void showData(ViewHolder vHolder, StudyMessage data, int position) {
                FrameLayout frameImage = (FrameLayout) vHolder.getView(R.id.frameImage);
                ImageView ivImage = (ImageView) vHolder.getView(R.id.ivImage);
                ImageView ivVideo = (ImageView) vHolder.getView(R.id.ivVideo);
                TextView tvLearnTitle = (TextView) vHolder.getView(R.id.tvLearnTitle);
                TextView tvLearnFrom = (TextView) vHolder.getView(R.id.tvLearnFrom);
                TextView tvReadCounts = (TextView) vHolder.getView(R.id.tvReadCounts);
                if (data.getMsgType() == 3) {
                    ivVideo.setVisibility(View.VISIBLE);
                    frameImage.setVisibility(View.VISIBLE);
                    ivImage.setImageDrawable(findDrawById(R.drawable.img_learn1));
                } else if (data.getMsgType() == 2) {
                    ivVideo.setVisibility(View.GONE);
                    frameImage.setVisibility(View.VISIBLE);
                    GlideUtils.ShowImage(getActivity(), data.getFirstPic(), ivImage, R.drawable.img_learn1);
                } else {
                    frameImage.setVisibility(View.GONE);
                }
                tvLearnTitle.setText(data.getTitle());
                tvLearnFrom.setVisibility(View.VISIBLE);
                tvLearnFrom.setText(data.getCreateTimeStr());
                tvReadCounts.setText(data.getBrowseCount() + " 浏览");
            }
        };
        expendRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!BuildConfig.DEBUG) {
                    if (type == 4) {
                        if (!UserUtils.isVip1(App.getInstance().getCurrentUser())) {
                            showVipDialog();
                            return;
                        }
                    } else if (type == 5) {
                        if (!UserUtils.isVip2(App.getInstance().getCurrentUser())) {
                            showVipDialog();
                            return;
                        }
                    } else if (type == 6) {
                        if (!UserUtils.isVip3(App.getInstance().getCurrentUser())) {
                            showVipDialog();
                            return;
                        }
                    }
                }
                Intent messageIntent = new Intent(getActivity(), MessageDetailActivity.class);
                messageIntent.putExtra(ConstantStr.KEY_BUNDLE_STR, datas.get(position).getTitle());
                if (getActivity() != null) {
                    SPHelper.write(getActivity(), ConstantStr.SP_CACHE, ConstantStr.SP_MESSAGE_DETAIL, datas.get(position).getDetail());
                }
                startActivity(messageIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onLoadMore() {
        if (isRefresh) {
            return;
        }
        if (datas == null || datas.size() == 0) {
            return;
        }
        mPresenter.getStudyMessage(type, datas.get(datas.size() - 1).getId());
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        requestData();
    }

    @Override
    public void showLoading() {
        refreshLoadLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        isRefresh = false;
        refreshLoadLayout.setRefreshing(false);
    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(List<StudyMessage> datas) {
        noDataLayout.setVisibility(View.GONE);
        this.datas.clear();
        this.datas.addAll(datas);
        expendRecycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMoreData(List<StudyMessage> datas) {
        this.datas.addAll(datas);
        expendRecycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void noMoreData() {
        refreshLoadLayout.setNoMoreData(true);
    }

    @Override
    public void hideLoadingMore() {
        refreshLoadLayout.loadFinish();
    }

    @Override
    public void setPresenter(StudyListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showVipDialog() {
        if (getActivity() == null) return;
        new MaterialDialog.Builder(getActivity()).title("提示").content("马上充值成为会员，信息浏览将畅通无阻")
                .positiveText("马上充值").negativeText("暂不充值").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                startActivity(new Intent(getActivity(), VipActivity.class));
            }
        }).show();
    }
}
