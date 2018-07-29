package com.bill.android.baking101.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private int mId;
    private String mName;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;
    private int mServings;
    private String  mThumbnail;

    public Recipe (int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings, String thumbnail) {
        mId = id;
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mThumbnail = thumbnail;
    }

    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        mSteps = in.createTypedArrayList(Step.CREATOR);
        mServings = in.readInt();
        mThumbnail = in.readString();
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

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.mSteps = steps;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int servings) {
        this.mServings = servings;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.mThumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mIngredients=" + mIngredients +
                ", mSteps=" + mSteps +
                ", mServings=" + mServings +
                ", mThumbnail='" + mThumbnail + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeTypedList(mIngredients);
        dest.writeTypedList(mSteps);
        dest.writeInt(mServings);
        dest.writeString(mThumbnail);
    }
}
