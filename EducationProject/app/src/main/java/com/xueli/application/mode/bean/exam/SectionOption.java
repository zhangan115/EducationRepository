package com.xueli.application.mode.bean.exam;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pingan on 2018/3/26.
 */

public class SectionOption implements Parcelable {

    private String optSta;
    private String optVal;

    public String getOptSta() {
        return optSta;
    }

    public void setOptSta(String optSta) {
        this.optSta = optSta;
    }

    public String getOptVal() {
        return optVal;
    }

    public void setOptVal(String optVal) {
        this.optVal = optVal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.optSta);
        dest.writeString(this.optVal);
    }

    public SectionOption() {
    }

    protected SectionOption(Parcel in) {
        this.optSta = in.readString();
        this.optVal = in.readString();
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
