package com.nezspencer.bakingapp.recipedashboard;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nezspencer.bakingapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by nezspencer on 6/26/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class IdlingResourcesTest {

    private IdlingResource idlingResource;
    @Rule
    public ActivityTestRule<RecipeActivity> mTestRule = new ActivityTestRule<RecipeActivity>
            (RecipeActivity.class);

    @Before
    public void setUpIdlingResources() {
        idlingResource = mTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(idlingResource);
    }

    @After
    public void cleanUp() {
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void verifyFirstItemText() throws Exception{

        Espresso.onView(ViewMatchers.withId(R.id.recipe_recycler))
                .perform(RecyclerViewActions.scrollToPosition(0));
    }

    @Test
    public void checkIf$RecipeNameIsCorrect() throws Exception{

        Espresso.onView(ViewMatchers.withId(R.id.recipe_recycler)).perform(RecyclerViewActions.
                scrollToPosition(2)).check(ViewAssertions.matches(ViewMatchers.hasDescendant(
                ViewMatchers.withText("Yellow Cake")
        )));


    }
}
