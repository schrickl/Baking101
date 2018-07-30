package com.bill.android.baking101.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bill.android.baking101.R;
import com.bill.android.baking101.fragments.RecipeStepDetailFragment;
import com.bill.android.baking101.fragments.RecipeStepsFragment;
import com.bill.android.baking101.models.Recipe;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {

    private static final String LOG_TAG = RecipeStepsActivity.class.getSimpleName();
    private Recipe mRecipe;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_list);

        mRecipe = getIntent().getParcelableExtra(getResources().getString(R.string.recipe_extra));

        // Determine if we're on a tablet
        if (findViewById(R.id.recipe_step_detail_fragment_tab) != null) {
            mTwoPane = true;
            //TextView recipeDetailName = findViewById(R.id.tv_recipe_detail_name);
            //recipeDetailName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onStepSelected(int position) {

        if (mTwoPane) {
            RecipeStepDetailFragment frag = (RecipeStepDetailFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_step_detail_fragment_tab);
            frag.playVideo(position);
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(getResources().getString(R.string.recipe_extra), mRecipe);
            intent.putExtra(getResources().getString(R.string.recipe_position), position);
            startActivity(intent);
        }
    }
}