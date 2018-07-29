package com.bill.android.baking101.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bill.android.baking101.R;
import com.bill.android.baking101.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {

    private static final String LOG_TAG = RecipeStepsAdapter.class.getSimpleName();
    private static RecipeStepsAdapter.RecyclerViewOnClickHandler mClickHandler;
    private Context mContext;
    private ArrayList<Step> mStepList;

    public RecipeStepsAdapter(Context context, RecipeStepsAdapter.RecyclerViewOnClickHandler handler, ArrayList<Step> steps) {
        mContext = context;
        mClickHandler = handler;
        mStepList = steps;
    }

    public interface RecyclerViewOnClickHandler {
        void onClick(int position);
    }

    @Override
    public int getItemCount() {
        return mStepList == null ? 0 : mStepList.size();
    }

    @Override
    public RecipeStepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_item, parent, false);
        RecipeStepsAdapter.ViewHolder myViewHolder = new RecipeStepsAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Step step = mStepList.get(position);

        Log.i(LOG_TAG, "step: " + step.getShortDescription());
        holder.step.setText(step.getShortDescription());
   }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_step) TextView step;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }
}

