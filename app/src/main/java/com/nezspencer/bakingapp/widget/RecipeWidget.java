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

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.Recipe;
import com.nezspencer.bakingapp.recipedashboard.RecipeActivity;
import com.nezspencer.bakingapp.recipeitem.RecipeItemActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    private static int currentImagePosition =0;

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                          @DrawableRes int imageRes, int recipePosition, int[]
                                                  appWidgetIds,
                                          int imagePosition) {

        currentImagePosition = imagePosition;
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context,appWidgetManager,imageRes, recipePosition, appWidgetId);
        }



    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        RecipeWidgetUpdateService.startActionChangeImage(context, currentImagePosition);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, @DrawableRes
                                int imageRes, int recipePosition,int appWidgetId){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        views.setImageViewResource(R.id.iv_widget,imageRes);
        Recipe recipe = AppClass.appRecipeList.get(recipePosition);
        views.setTextViewText(R.id.appwidget_text,recipe.getName());
        Log.e("LOGGER"," widget is updating...");

        Intent launchIntent = new Intent(context, RecipeItemActivity.class);
        launchIntent.putExtra(RecipeActivity.KEY_PARCELABLE,recipePosition);
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
}

