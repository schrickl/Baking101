package com.bill.android.baking101.activities;

import android.content.Context;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bill.android.baking101.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRecipeData();
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

    public static class FetchRecipeTask extends AsyncTask<String, Void, ArrayList<Movie>> {


        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            return null;
        }
    }
}
