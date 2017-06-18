package com.nezspencer.bakingapp;

import android.support.annotation.StringRes;

import com.nezspencer.bakingapp.pojo.Recipe;

import java.util.ArrayList;

/**
 * Created by nezspencer on 5/28/17.
 */

public interface BakingInterface {

    interface RecipeActivityContract{

        void setRecipeList(ArrayList<Recipe> recipeList);

        void showLoadingProgress();

        void  hideLoadingProgress();

        void showError(@StringRes int errormsg);

        void onRecipeItemClicked(int position);
    }
}
