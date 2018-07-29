package com.bill.android.baking101.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bill.android.baking101.R;
import com.bill.android.baking101.activities.RecipeStepsActivity;
import com.bill.android.baking101.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;

    public RecipeAdapter(Context context, ArrayList<Recipe> itemList) {
        mContext = context;
        this.mRecipeList = itemList;
    }

    @Override
    public int getItemCount() {
        return mRecipeList == null ? 0 : mRecipeList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card_item, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Recipe recipe = mRecipeList.get(position);

        holder.name.setText(recipe.getName());
        // Set a drawable for the image
        if (recipe.getId() == 1) {
            holder.image.setImageResource(R.drawable.nutella_pie);
        } else if (recipe.getId() == 2) {
            holder.image.setImageResource(R.drawable.brownies);
        } else if (recipe.getId() == 3) {
            holder.image.setImageResource(R.drawable.yellow_cake);
        } else {
            holder.image.setImageResource(R.drawable.cheesecake);
        }
        holder.servings.setText(String.valueOf(recipe.getServings()) + " Servings");

        holder.view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeStepsActivity.class);
                intent.putExtra(mContext.getResources().getString(R.string.recipe_extra), recipe);
                mContext.startActivity(intent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.ll_recipe_card) View view;
        @BindView(R.id.iv_card) ImageView image;
        @BindView(R.id.tv_name) TextView name;
        @BindView(R.id.tv_servings) TextView servings;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
