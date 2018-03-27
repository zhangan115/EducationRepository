package com.xueli.application.widget;

import android.content.Context;
import android.text.Editable;
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
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOption.setValue(s.toString().trim());
                if (iEnter != null) {
                    iEnter.onEnter();
                }
            }
        });
    }

    public interface IEnter {
        void onEnter();
    }
}
