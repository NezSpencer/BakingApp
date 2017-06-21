package com.nezspencer.bakingapp;

import android.app.Application;

import com.nezspencer.bakingapp.pojo.Recipe;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

/**
 * Created by nezspencer on 6/15/17.
 */

public class AppClass extends Application {

    public static ArrayList<Recipe> appRecipeList;
    public static ArrayList<String> appItemList;
    public static Recipe selectedRecipe;

    @Override
    public void onCreate() {
        super.onCreate();
        appRecipeList = new ArrayList<>();
        appItemList = new ArrayList<>();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
