package com.xueli.application.mode.bean.exam;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pingan on 2018/3/26.
 */

public class SectionOptionClone implements Parcelable {

    private boolean optSta;
    private String optVal;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.optSta ? (byte) 1 : (byte) 0);
        dest.writeString(this.optVal);
    }

    public SectionOptionClone() {
    }

    protected SectionOptionClone(Parcel in) {
        this.optSta = in.readByte() != 0;
        this.optVal = in.readString();
    }

    public static final Creator<SectionOptionClone> CREATOR = new Creator<SectionOptionClone>() {
        @Override
        public SectionOptionClone createFromParcel(Parcel source) {
            return new SectionOptionClone(source);
        }

        @Override
        public SectionOptionClone[] newArray(int size) {
            return new SectionOptionClone[size];
        }
    };
}
