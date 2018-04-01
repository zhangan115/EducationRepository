package com.xueli.application.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueli.application.R;
import com.xueli.application.mode.bean.exam.SectionOption;

public class InputTypeLayout extends LinearLayout {

    private TextView choose;
    private EditText content;
    private Context context;
    private SectionOption mOption;
    private String position;
    private IEnter iEnter;

    public InputTypeLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.input_type_layout, this);
        choose = findViewById(R.id.tvChoose);
        content = findViewById(R.id.tvContent);
    }

    public void setData(final SectionOption option, String position, IEnter enter) {
        this.mOption = option;
        this.position = position;
        this.iEnter = enter;
        choose.setText(position);
        content.setText(option.getValue());
        if (enter == null) {
            content.setEnabled(false);
        }
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (iEnter == null) return;
                try {
                    boolean isFinish = !TextUtils.isEmpty(s.toString());
                    String value = s.toString().trim();
                    if (TextUtils.isEmpty(mOption.getValue()) && !TextUtils.isEmpty(value)) {
                        mOption.setValue(value);
                        if (mOption.getOptSta() && !TextUtils.isEmpty(mOption.getOptVal())) {
                            iEnter.onEnter(isFinish, mOption.getValue().equals(mOption.getOptVal()));
                        } else {
                            iEnter.onEnter(isFinish, false);
                        }
                    } else {
                        if (!mOption.getValue().equals(value)) {
                            mOption.setValue(value);
                            if (mOption.getOptSta() && !TextUtils.isEmpty(mOption.getOptVal())) {
                                iEnter.onEnter(isFinish, mOption.getValue().equals(mOption.getOptVal()));
                            } else {
                                iEnter.onEnter(isFinish, false);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface IEnter {
        void onEnter(boolean isFinish, boolean isRight);
    }
}
