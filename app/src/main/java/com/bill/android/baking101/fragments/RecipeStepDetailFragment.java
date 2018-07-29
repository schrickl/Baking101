package com.bill.android.baking101.fragments;

import android.content.Context;
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
import android.widget.TextView;

import com.bill.android.baking101.R;
import com.bill.android.baking101.adapters.RecipeStepsAdapter;
import com.bill.android.baking101.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailFragment extends Fragment {

    private static final String LOG_TAG = RecipeStepDetailFragment.class.getSimpleName();
    private RecipeStepDetailFragment.OnStepClickListener mCallback;
    @BindView(R.id.tv_ingredients)
    TextView mIngredients;
    @BindView(R.id.rv_step)
    RecyclerView mRecyclerView;

    // OnStepClickListener interface, calls a method in the host activity named onStepSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    public RecipeStepDetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
//        try {
//            mCallback = (RecipeStepsFragment.OnStepClickListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + getString(R.string.error_on_step_click));
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_list, container, false);

        ButterKnife.bind(this, rootView);

        Recipe mRecipe = getActivity().getIntent().getParcelableExtra("recipe_extra");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecipeStepsAdapter mAdapter = new RecipeStepsAdapter(getActivity(), this, mRecipe.getSteps());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
            mIngredients.append(new StringBuilder().append(mRecipe.getIngredients().get(i).getQuantity()).append(" ").append(mRecipe.getIngredients().get(i).getmMeasure()).append(" ").append(mRecipe.getIngredients().get(i).getmName()).toString());
            if (i != mRecipe.getIngredients().size() - 1) {
                mIngredients.append("\n");
            }
        }
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle(mRecipe.getName());
        }

        // Return the root view
        return rootView;
    }
}

