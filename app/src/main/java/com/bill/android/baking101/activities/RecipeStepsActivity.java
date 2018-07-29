package com.bill.android.baking101.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bill.android.baking101.R;
import com.bill.android.baking101.fragments.RecipeStepsFragment;
import com.bill.android.baking101.models.Recipe;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {

    private static final String LOG_TAG = RecipeStepsActivity.class.getSimpleName();
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_list);

        mRecipe = getIntent().getParcelableExtra(getResources().getString(R.string.recipe_extra));
    }

    @Override
    public void onStepSelected(int position) {

        Intent intent = new Intent(this, RecipeStepDetailActivity.class);
        intent.putExtra(getResources().getString(R.string.recipe_extra), mRecipe);
        intent.putExtra(getResources().getString(R.string.recipe_position), position);
        startActivity(intent);
    }
}