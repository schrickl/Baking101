package com.bill.android.baking101.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private double mQuantity;
    private String mMeasure;
    private String mName;

    public Ingredient(double quantity, String measure, String name) {
        mQuantity = quantity;
        mMeasure = measure;
        mName = name;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public void setQuantity(double quantity) {
        this.mQuantity = quantity;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    public String getmName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "mQuantity=" + mQuantity +
                ", mMeasure='" + mMeasure + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }

    protected Ingredient(Parcel in) {
        mQuantity = in.readDouble();
        mMeasure = in.readString();
        mName = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(mQuantity);
        parcel.writeString(mMeasure);
        parcel.writeString(mName);
    }
}
