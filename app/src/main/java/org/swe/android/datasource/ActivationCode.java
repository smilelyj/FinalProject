package org.swe.android.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import org.swe.android.datasource.amazondynamodb.AmazonDynamoDBActivationCode;

/**
 * Created by YongjiLi on 4/19/16.
 */
public class ActivationCode implements Parcelable {

    private String key;
    private boolean isNew;
    private String date;

    public ActivationCode(){}
    public ActivationCode(Parcel parcel) {
        key = parcel.readString();
        isNew = parcel.readByte() != 0;
        date = parcel.readString();
    }

    public ActivationCode(AmazonDynamoDBActivationCode activationCode) {
        this.key = activationCode.getKey();
        this.isNew = activationCode.getIsNew();
        this.date = activationCode.getDate();
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public boolean getIsNew() {
        return isNew;
    }
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "ActivationCode{" +
                "key='" + key + '\'' +
                ", isNew='" + isNew + '\'' +
                "date='" + date + '\'' +
                '}';
    }

    public static final Parcelable.Creator<ActivationCode> CREATOR = new Parcelable.Creator<ActivationCode>() {
        public ActivationCode createFromParcel(Parcel in) {
            return new ActivationCode(in);
        }
        public ActivationCode[] newArray(int size) {
            return new ActivationCode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeByte((byte) (isNew ? 1 : 0));
        parcel.writeString(date);
    }
}

