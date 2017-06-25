package com.nezspencer.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.RemoteViews;

import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.Recipe;
import com.nezspencer.bakingapp.recipedashboard.RecipeActivity;
import com.nezspencer.bakingapp.recipeitem.RecipeItemActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static int currentImagePosition =0;
    static int recipePosition =0;

    static int[] recipeImages = {
            R.drawable.nutella2_app,
            R.drawable.brownie1_app,
            R.drawable.yellowcake1_app,
            R.drawable.cheesecake1_app,
            R.drawable.nutella1_app,
            R.drawable.brownie2_app,
            R.drawable.yellowcake2_app,
            R.drawable.cheesecake2_app
    };


    private static List<Recipe> recipeList;

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds, List<Recipe> list) {

        recipeList = list;
        currentImagePosition++;
        setRecipePosition(currentImagePosition);
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context,appWidgetManager,recipeImages[currentImagePosition],
                    recipePosition, appWidgetId);
        }



    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        RecipeWidgetUpdateService.startActionChangeImage(context);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, @DrawableRes
                                int imageRes, int recipePosition,int appWidgetId){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        views.setImageViewResource(R.id.iv_widget,imageRes);
        Recipe recipe = recipeList.get(recipePosition);
        views.setTextViewText(R.id.appwidget_text,recipe.getName());
        Log.e("LOGGER"," widget is updating...");

        Intent launchIntent = new Intent(context, RecipeItemActivity.class);
        launchIntent.putExtra(RecipeActivity.KEY_WIDGET,recipe);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchIntent.setData(Uri.parse(launchIntent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,launchIntent,0);

        views.setOnClickPendingIntent(R.id.my_widget,pendingIntent);
        Log.e("LOGGER"," Pending intent is set");


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void setRecipePosition(int currentImagePosition){

        if (currentImagePosition < recipeList.size())
            recipePosition = currentImagePosition;
        else {
            //equal or greater than list size
            recipePosition = currentImagePosition - recipeList.size();
        }
    }
}

