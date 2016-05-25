package com.sc.data.packaging;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * 需要补充
 * 对 图标标题子菜单的封装。
 *
 *
 * Created by Missyouag on 2015/8/28.
 */
public class IconString implements Parcelable {
    private String mValue;
    /**
     * 本地图片
     */
    private int img;
    /**
     * 网络图片
     */

    private String img2;
    private String mValue2;

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getmValue2() {
        return mValue2;
    }

    public void setmValue2(String mValue2) {
        this.mValue2 = mValue2;
    }


    public boolean hasImge() {
        if (TextUtils.isEmpty(img2) && img < 1)
            return false;
        return true;
    }

    public IconString(int img, String mValue, String mValue2) {
        this.mValue2 = mValue2;
        this.mValue = mValue;
        this.img = img;
    }

    public IconString(String img2, String mValue, String mValue2) {
        this.mValue = mValue;
        this.img2 = img2;
        this.mValue2 = mValue2;
    }

    public IconString(String img2, String mValue) {
        this.img2 = img2;
        this.mValue = mValue;
    }

    public IconString(int img, String mValue) {

        this.mValue = mValue;
        this.img = img;
    }

    public IconString(String mValue) {

        this.mValue = mValue;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mValue);
        out.writeString(mValue2);
        out.writeInt(img);
        out.writeString(img2);
    }

    public static final Creator<IconString> CREATOR
            = new Creator<IconString>() {
        public IconString createFromParcel(Parcel in) {
            return new IconString(in);
        }

        public IconString[] newArray(int size) {
            return new IconString[size];
        }
    };

    private IconString(Parcel in) {
        mValue = in.readString();
        mValue2 = in.readString();
        img = in.readInt();
        img2 = in.readString();
    }

    @Override
    public String toString() {
        return "IconString{" +
                "mValue='" + mValue + '\'' +
                ", img=" + img +
                ", img2='" + img2 + '\'' +
                ", mValue2='" + mValue2 + '\'' +
                '}';
    }
}
