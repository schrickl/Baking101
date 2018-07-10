package com.bill.android.baking101.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

    private int mId;
    private String mName;
    private Ingredient mIngredients;
    private Step mSteps;
    private int mServings;
    private int mThumbnail;

    public Recipe (int id, String name, Ingredient ingredients, Step steps, int servings, int thumbnail) {
        mId = id;
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mThumbnail = thumbnail;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Ingredient getIngredients() {
        return mIngredients;
    }

    public void setIngredients(Ingredient ingredients) {
        this.mIngredients = ingredients;
    }

    public Step getSteps() {
        return mSteps;
    }

    public void setSteps(Step steps) {
        this.mSteps = steps;
    }

    public int getmervings() {
        return mServings;
    }

    public void setServings(int servings) {
        this.mServings = servings;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.mThumbnail = thumbnail;
    }

    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mServings = in.readInt();
        mThumbnail = in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeInt(mServings);
        parcel.writeInt(mThumbnail);
    }
}
