package com.bill.android.baking101.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bill.android.baking101.R;

public class RecipeStepDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = RecipeStepDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
    }
}
