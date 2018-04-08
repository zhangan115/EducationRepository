package com.xueli.application.mode.bean.exam;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pingan on 2018/3/26.
 */

public class SectionOption implements Parcelable {

    private boolean optSta;
    private String optVal;
    private boolean choose;
    private String value;

    public SectionOptionClone cloneSection() {
        SectionOptionClone option = new SectionOptionClone();
        option.setOptSta(optSta);
        option.setOptVal(optVal);
        return option;
    }

    public boolean getOptSta() {
        return optSta;
    }

    public void setOptSta(boolean optSta) {
        this.optSta = optSta;
    }

    public String getOptVal() {
        return optVal;
    }

    public void setOptVal(String optVal) {
        this.optVal = optVal;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SectionOption() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.optSta ? (byte) 1 : (byte) 0);
        dest.writeString(this.optVal);
        dest.writeByte(this.choose ? (byte) 1 : (byte) 0);
        dest.writeString(this.value);
    }

    protected SectionOption(Parcel in) {
        this.optSta = in.readByte() != 0;
        this.optVal = in.readString();
        this.choose = in.readByte() != 0;
        this.value = in.readString();
    }

    public static final Creator<SectionOption> CREATOR = new Creator<SectionOption>() {
        @Override
        public SectionOption createFromParcel(Parcel source) {
            return new SectionOption(source);
        }

        @Override
        public SectionOption[] newArray(int size) {
            return new SectionOption[size];
        }
    };
}
