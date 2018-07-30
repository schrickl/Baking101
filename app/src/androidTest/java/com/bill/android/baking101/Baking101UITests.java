package com.bill.android.baking101;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bill.android.baking101.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
public class Baking101UITests {

    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final String NUTELLA = "Nutella";
    private static final String INTRO = "Recipe Introduction";
    private static final String STEP_ONE = "1";
    private static final String STEP_TWO = "2";
    private static final String STEP_THREE = "3";
    private static final String STEP_FOUR = "4";
    private static final String STEP_FIVE = "5";
    private static final String STEP_SIX = "6";



    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void nutellaPieRecipe() {

        // Checks that the Cheesecake recipe is the 4th recipe in the list
        onView(withId(R.id.rv_recipe)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText(NUTELLA_PIE)).check(matches(isDisplayed()));
    }

    @Test
    public void nutellaPieRecipeAndSteps() {

        // Checks that the RecipeStepsActivity is launched when the 4th item is clicked and
        // displays the Cheesecake recipe steps.
        onView(withId(R.id.rv_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_ingredients)).check(matches(withText(containsString(NUTELLA))));

        // Checks that the RecipeStepDetailFragment is launched when the 1st item is clicked and
        // displays the Recipe Introduction.
        onView(withId(R.id.rv_step)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(INTRO))));

        // Checks that the app steps through each of the recipe steps when the next button
        // is clicked.
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_ONE))));
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_TWO))));
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_THREE))));
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_FOUR))));
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_FIVE))));
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_SIX))));

        // Checks the the next button is disabled when the end of the steps is reached.
        onView(withId(R.id.btn_next)).check(matches(not(isEnabled())));

        // Checks that the app steps through each of the recipe steps when the previous button
        // is clicked.
        onView(withId(R.id.btn_previous)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_FIVE))));
        onView(withId(R.id.btn_previous)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_FOUR))));
        onView(withId(R.id.btn_previous)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_THREE))));
        onView(withId(R.id.btn_previous)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_TWO))));
        onView(withId(R.id.btn_previous)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(STEP_ONE))));
        onView(withId(R.id.btn_previous)).perform(click());
        onView(withId(R.id.tv_step_description)).check(matches(withText(startsWith(INTRO))));

        // Checks the the previous button is disabled when the beginning of the steps is reached.
        onView(withId(R.id.btn_previous)).check(matches(not(isEnabled())));
    }
}