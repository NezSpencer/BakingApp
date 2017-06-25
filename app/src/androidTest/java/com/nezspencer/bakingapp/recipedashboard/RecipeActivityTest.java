package com.nezspencer.bakingapp.recipedashboard;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nezspencer.bakingapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by nezspencer on 6/23/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule public ActivityTestRule<RecipeActivity> mTextRule = new
            ActivityTestRule<>(RecipeActivity.class);

    private IdlingResource mIdlingResource;
    @Before
    public void setUp() throws Exception {
        mIdlingResource = mTextRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void isProgressBarShowing() throws Exception {

        Espresso.onView(ViewMatchers.withText("Loading please wait...")).check(ViewAssertions
                .matches
                (ViewMatchers.isDisplayed()));

    }

    @Test
    public void progressBarDisappearsAfterLoading() throws Exception{

        Espresso.onView(ViewMatchers.withId(R.id.toolbar)).check(ViewAssertions.matches
                (ViewMatchers.isDisplayed()));


    }

    @After
    public void unregisterIdlingResources(){
        if (mIdlingResource != null)
            Espresso.unregisterIdlingResources(mIdlingResource);
    }

}