package com.nezspencer.bakingapp.recipedashboard;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.recipeitem.RecipeItemActivity;

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
public class IntentLaunchTest {

    private IdlingResource idlingResource;
    @Rule
    public ActivityTestRule<RecipeActivity> testRule = new ActivityTestRule<RecipeActivity>
            (RecipeActivity.class);

    @Before
    public void initialize(){
        idlingResource = testRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(idlingResource);


    }

    @After
    public void cleanUp() throws Exception{
        Espresso.unregisterIdlingResources(idlingResource);
        Intents.release();
    }

    @Test
    public void shouldOpenRecipeItemActivity() throws Exception{
        Intents.init();
        Espresso.onView(ViewMatchers.withId(R.id.recipe_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));


        Intents.intended(IntentMatchers.hasComponent(RecipeItemActivity.class.getName()));
    }
}
