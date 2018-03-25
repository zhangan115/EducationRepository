package com.xueli.application.view.bank.examination;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xueli.application.R;

import java.util.List;

/**
 * 答题adapter
 * Created by pingan on 2018/3/22.
 */

public class SubjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ExaminationActivity.TestData> dataList;
    private Context context;

    public SubjectAdapter(List<ExaminationActivity.TestData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.subject_name_item, null);
            return new TitleViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.subject_position_item, null);
            return new SubjectViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).mTextView.setText("标题");
        } else {
            ((SubjectViewHolder) holder).mTextView.setText(String.valueOf(position));
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
        public TextView mTextView;

        public TitleViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.tvSubjectName);
        }

    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public SubjectViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.tvSubjectPosition);
        }
    }
}