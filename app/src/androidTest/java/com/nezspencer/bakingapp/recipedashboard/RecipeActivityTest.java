package com.nezspencer.bakingapp.recipedashboard;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by nezspencer on 6/23/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeActivityTest {

    @Rule public ActivityTestRule<RecipeActivity> mTextRule = new
            ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void isProgressBarShowing() throws Exception {

        Espresso.onView(ViewMatchers.withText("Loading please wait...")).check(ViewAssertions
                .matches
                (ViewMatchers.isDisplayed()));

    }


}