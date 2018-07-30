package com.bill.android.baking101.fragments;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bill.android.baking101.R;
import com.bill.android.baking101.adapters.RecipeStepsAdapter;
import com.bill.android.baking101.adapters.RecipeStepsAdapter.RecyclerViewOnClickHandler;
import com.bill.android.baking101.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment extends Fragment implements RecyclerViewOnClickHandler {

    private static final String LOG_TAG = RecipeStepsFragment.class.getSimpleName();
    private OnStepClickListener mCallback;
    private Recipe mRecipe;
    @BindView(R.id.tv_ingredients) TextView mIngredients;
    @BindView(R.id.rv_step) RecyclerView mRecyclerView;
    @BindView(R.id.sv_recipe_list) ScrollView mScrollView;

    @Override
    public void onClick(int position) {
        mCallback.onStepSelected(position);
    }

    // OnStepClickListener interface, calls a method in the host activity named onStepSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    public RecipeStepsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.error_on_step_click));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_list, container, false);

        ButterKnife.bind(this, rootView);

        if (savedInstanceState == null) {
            mScrollView.smoothScrollTo(0, 0);
            mRecipe = getActivity().getIntent().getParcelableExtra(getResources().getString(R.string.recipe_extra));

            // Save last viewed recipe in SharedPrefs for the widget to use
            SharedPreferences prefs = getContext().getSharedPreferences(getString(R.string.widget_prefs), Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getString(R.string.recipe_name), mRecipe.getName());
            editor.putString(getString(R.string.recipe_ingredients), ingredientsBuilder());
            editor.commit();

            // and let the widget know there is a new most-recently-selected recipe to display
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            getContext().sendBroadcast(intent);
        } else {
            mRecipe = savedInstanceState.getParcelable(getResources().getString(R.string.recipe_extra));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecipeStepsAdapter mAdapter = new RecipeStepsAdapter(getActivity(), this, mRecipe.getSteps());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mIngredients.setText(ingredientsBuilder());

        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle(mRecipe.getName());
        }

        // Return the root view
        return rootView;
    }

    private String ingredientsBuilder() {
        String ingredients = "";

        for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
            ingredients += mRecipe.getIngredients().get(i).getQuantity() + " " + mRecipe.getIngredients().get(i).getmMeasure() + " " + mRecipe.getIngredients().get(i).getmName();
            if (i != mRecipe.getIngredients().size() - 1) {
                ingredients += ("\n");
            }
        }

        return ingredients;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(getResources().getString(R.string.recipe_extra), mRecipe);
        super.onSaveInstanceState(outState);
    }
}
