package com.bill.android.baking101.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {

    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public Step(String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.mShortDescription = shortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.mVideoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.mThumbnailUrl = thumbnailUrl;
    }



    protected Step(Parcel in) {
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }

    @Override
    public String toString() {
        return "Step{" +
                "mShortDescription='" + mShortDescription + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mVideoUrl='" + mVideoUrl + '\'' +
                ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                '}';
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mThumbnailUrl);
    }
}
