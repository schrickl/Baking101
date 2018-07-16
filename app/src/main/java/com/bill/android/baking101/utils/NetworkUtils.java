package com.bill.android.baking101.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildRecipeeUrl() {
        URL url = null;

        try {
            url = new URL(RECIPE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "A network error occurred!");
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }
}
