package com.bill.android.baking101.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.bill.android.baking101.R;
import com.bill.android.baking101.adapters.RecipeAdapter;
import com.bill.android.baking101.models.Recipe;
import com.bill.android.baking101.utils.NetworkUtils;
import com.bill.android.baking101.utils.RecipeJsonUtils;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static ArrayList<Recipe> mRecipe = new ArrayList<>();
    private static ArrayList<Recipe> mRecipeList = new ArrayList<>();
    private static RecipeAdapter mAdapter;
    @BindView(R.id.rv_recipe) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        int columns = Math.max(1, (int) Math.floor(width / 300));
        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecipeAdapter(this, mRecipe);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState == null) {
            loadRecipeData();
        } else {
            mRecipe = savedInstanceState.getParcelableArrayList(getResources().getString(R.string.recipe_extra));
        }
    }

    private void loadRecipeData() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            new FetchRecipeTask().execute();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getResources().getString(R.string.recipe_extra), mRecipe);
        super.onSaveInstanceState(outState);
    }

    public static class FetchRecipeTask extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        @Override
        protected ArrayList<Recipe> doInBackground(Void... params) {

            URL recipeRequestUrl = NetworkUtils.buildRecipeUrl();

            try {
                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(recipeRequestUrl);

                mRecipeList = RecipeJsonUtils.getRecipeStringsFromJson(jsonRecipeResponse);

                return mRecipeList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipeData) {
            if (recipeData != null) {
                mRecipe.clear();
                mRecipe.addAll(mRecipeList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
