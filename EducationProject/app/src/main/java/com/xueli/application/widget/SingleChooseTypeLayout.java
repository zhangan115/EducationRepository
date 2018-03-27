package com.xueli.application.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xueli.application.R;
import com.xueli.application.mode.bean.exam.SectionOption;

public class SingleChooseTypeLayout extends LinearLayout implements View.OnClickListener {

    private TextView choose, content;
    private Context context;
    private SectionOption option;
    private int position;
    private IClick iClick;
    private int flag;

    public SingleChooseTypeLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.single_choose_type_layout, this);
        choose = findViewById(R.id.tvChoose);
        content = findViewById(R.id.tvContent);
        setOnClickListener(this);
    }

    public void setData(SectionOption option, int position, int flag, IClick click) {
        this.option = option;
        this.position = position;
        this.iClick = click;
        this.flag = flag;
        content.setText(option.getOptVal());
        refreshUI(option, position);
    }

    public void refreshUI(SectionOption option, int position) {
        if (option.isChoose()) {
            choose.setText("");
            content.setTextColor(context.getResources().getColor(R.color.text_blue));
            choose.setBackground(context.getResources().getDrawable(R.drawable.icon_btn_choice));
        } else {
            choose.setText(getChoose(position));
            content.setTextColor(context.getResources().getColor(R.color.text_gray_333));
            choose.setBackground(context.getResources().getDrawable(R.drawable.icon_btn_radio1));
        }
    }

    @Override
    public void onClick(View v) {
        if (option != null) {
            iClick.onItemClick(position, flag);
        }
    }

    public interface IClick {
        void onItemClick(int position, int flag);
    }

    private String getChoose(int position) {
        if (flag == 2 || flag == 3) {
            byte[] bytes = String.valueOf(position).getBytes();
            byte[] strBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                strBytes[i] = (byte) (bytes[i] + 17);
            }
            return new String(strBytes);
        } else {
            return String.valueOf(position + 1) + ".";
        }
    }

}
