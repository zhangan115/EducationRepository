package com.xueli.application.view.bank.examination;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueli.application.R;
import com.xueli.application.mode.bean.exam.ExamListBean;
import com.xueli.application.mode.bean.exam.PaperSectionList;

import java.util.List;

/**
 * 答题adapter
 * Created by pingan on 2018/3/22.
 */

public class SubjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PaperSectionList> dataList;
    private Context context;
    private OnItemClickListener listener;
    private boolean isShowAnswer;

    SubjectAdapter(List<PaperSectionList> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public SubjectAdapter(List<PaperSectionList> dataList, Context context, boolean isShowAnswer) {
        this.dataList = dataList;
        this.context = context;
        this.isShowAnswer = isShowAnswer;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(context).inflate(R.layout.subject_name_item, null);
            return new TitleViewHolder(view);
        } else {
            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(context).inflate(R.layout.subject_position_item, null);
            return new SubjectViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).mTextView.setText(dataList.get(position).getName());
            if (isShowAnswer) {
                ((TitleViewHolder) holder).llAnswer.setVisibility(View.VISIBLE);
                ((TitleViewHolder) holder).rightTv.setText(String.valueOf(dataList.get(position).getRightCount()));
                ((TitleViewHolder) holder).faultTv.setText(String.valueOf(dataList.get(position).getFaultCount()));
            } else {
                ((TitleViewHolder) holder).llAnswer.setVisibility(View.GONE);
            }
        } else {
            ((SubjectViewHolder) holder).mTextView.setText(dataList.get(position).getName());
            ((SubjectViewHolder) holder).mTextView.setTag(dataList.get(position).getId());
            ((SubjectViewHolder) holder).mTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        long id = (long) v.getTag();
                        listener.onItemClick(id);
                    }
                }
            });
            if (dataList.get(position).isFinish()) {
                if (isShowAnswer && !dataList.get(position).isRight()) {
                    ((SubjectViewHolder) holder).mTextView.setBackground(context.getResources().getDrawable(R.drawable.shape_red_circle));
                    ((SubjectViewHolder) holder).mTextView.setTextColor(context.getResources().getColor(R.color.circle_red));
                } else {
                    ((SubjectViewHolder) holder).mTextView.setBackground(context.getResources().getDrawable(R.drawable.shape_blue_circle));
                    ((SubjectViewHolder) holder).mTextView.setTextColor(context.getResources().getColor(R.color.text_blue_dark));
                }
            } else {
                ((SubjectViewHolder) holder).mTextView.setBackground(context.getResources().getDrawable(R.drawable.shape_gray_circle));
                ((SubjectViewHolder) holder).mTextView.setTextColor(context.getResources().getColor(R.color.text_gray_333));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView rightTv;
        TextView faultTv;
        LinearLayout llAnswer;

        TitleViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.tvSubjectName);
            rightTv = view.findViewById(R.id.tvRightCount);
            faultTv = view.findViewById(R.id.tvFaultCount);
            llAnswer = view.findViewById(R.id.llAnswer);
        }

    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        SubjectViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.tvSubjectPosition);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(long id);
    }
}