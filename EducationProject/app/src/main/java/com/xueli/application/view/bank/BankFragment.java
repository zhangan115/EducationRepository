package com.xueli.application.view.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xueli.application.R;
import com.xueli.application.common.ConstantStr;
import com.xueli.application.view.MvpFragment;
import com.xueli.application.view.bank.list.BankListActivity;

/**
 * 题库
 * Created by pingan on 2018/3/4.
 */

public class BankFragment extends MvpFragment implements View.OnClickListener {

    public static BankFragment newInstance() {
        Bundle args = new Bundle();
        BankFragment fragment = new BankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bank_fragment, container, false);
        rootView.findViewById(R.id.iv1).setOnClickListener(this);
        rootView.findViewById(R.id.iv2).setOnClickListener(this);
        rootView.findViewById(R.id.iv3).setOnClickListener(this);
        rootView.findViewById(R.id.iv4).setOnClickListener(this);
        rootView.findViewById(R.id.iv5).setOnClickListener(this);
        rootView.findViewById(R.id.iv6).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), BankListActivity.class);
        String tag = (String) v.getTag();
        intent.putExtra(ConstantStr.KEY_BUNDLE_STR, tag);
        startActivity(intent);
    }
}
