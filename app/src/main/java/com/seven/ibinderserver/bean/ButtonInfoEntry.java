package com.seven.ibinderserver.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author kuan
 * Created on 2018/7/31.
 * @description
 */
public class ButtonInfoEntry implements Parcelable {
    private String mButtonName;
    private int mButtonBackground;

    protected ButtonInfoEntry(Parcel in) {
        mButtonName = in.readString();
        mButtonBackground = in.readInt();
    }

    public ButtonInfoEntry(){}

    public static final Creator<ButtonInfoEntry> CREATOR = new Creator<ButtonInfoEntry>() {
        @Override
        public ButtonInfoEntry createFromParcel(Parcel in) {
            return new ButtonInfoEntry(in);
        }

        @Override
        public ButtonInfoEntry[] newArray(int size) {
            return new ButtonInfoEntry[size];
        }
    };

    public String getButtonName() {
        return mButtonName;
    }

    public void setButtonName(String buttonName) {
        mButtonName = buttonName;
    }

    public int getButtonBackground() {
        return mButtonBackground;
    }

    public void setButtonBackground(int buttonBackground) {
        mButtonBackground = buttonBackground;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mButtonName);
        dest.writeInt(mButtonBackground);
    }
}
