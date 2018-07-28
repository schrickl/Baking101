package com.bill.android.baking101.utils;

import android.util.Log;

import com.bill.android.baking101.models.Ingredient;
import com.bill.android.baking101.models.Recipe;
import com.bill.android.baking101.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeJsonUtils {

    private static final String LOG_TAG = RecipeJsonUtils.class.getSimpleName();
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String RECIPE_INGREDIENTS_QUANTITY = "quantity";
    private static final String RECIPE_INGREDIENTS_MEASURE = "measure";
    private static final String RECIPE_INGREDIENTS_INGREDIENT = "ingredient";
    private static final String RECIPE_STEPS = "steps";
    private static final String RECIPE_STEPS_SHORT_DESC = "shortDescription";
    private static final String RECIPE_STEPS_DESC = "description";
    private static final String RECIPE_STEPS_VIDEO_URL = "videoURL";
    private static final String RECIPE_STEPS_THUMBNAIL_URL = "thumbnailURL";

    public static ArrayList<Recipe> getRecipeStringsFromJson(String recipeJsonString) throws JSONException {

        ArrayList<Recipe> recipes = new ArrayList<>();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ArrayList<Step> steps = new ArrayList<>();

        if (recipeJsonString != null) {
            JSONArray recipesArray = new JSONArray(recipeJsonString);

            for (int i = 0; i < recipesArray.length(); i++) {
                JSONObject recipe = recipesArray.getJSONObject(i);

                JSONArray ingredientsArray = recipe.getJSONArray(RECIPE_INGREDIENTS);

                for (int j = 0; j < ingredientsArray.length(); j++) {
                    JSONObject ingredient = ingredientsArray.getJSONObject(j);
                    ingredients.add(j, new Ingredient(ingredient.getInt(RECIPE_INGREDIENTS_QUANTITY),
                            ingredient.getString(RECIPE_INGREDIENTS_MEASURE),
                            ingredient.getString(RECIPE_INGREDIENTS_INGREDIENT)));
                }

                JSONArray stepsArray = recipe.getJSONArray(RECIPE_STEPS);

                for (int k = 0; k < stepsArray.length(); k++) {
                    JSONObject step = stepsArray.getJSONObject(k);
                    steps.add(k, new Step(step.getString(RECIPE_STEPS_SHORT_DESC),
                            step.getString(RECIPE_STEPS_DESC),
                            step.getString(RECIPE_STEPS_VIDEO_URL),
                            step.getString(RECIPE_STEPS_THUMBNAIL_URL)));
                }

                recipes.add(i, new Recipe(recipe.getInt(RECIPE_ID),
                        recipe.getString(RECIPE_NAME), ingredients.get(i), steps.get(i),
                        recipe.getInt(RECIPE_SERVINGS),
                        recipe.getString(RECIPE_IMAGE)));
            }
        }

        for (int i = 0; i < recipes.size(); i++) {
            Log.d(LOG_TAG, "recipe[" + i + "]: " + recipes.get(i).toString());
        }

        return recipes;
    }
}
