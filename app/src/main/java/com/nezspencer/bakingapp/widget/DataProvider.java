package com.nezspencer.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.database.Utils;
import com.nezspencer.bakingapp.pojo.RecipeIngredients;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nezspencer on 6/25/17.
 */

public class DataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static List<String> ingredientList= new ArrayList<>();
    private final Intent intent;
    private final Context mContext;

    public DataProvider(Intent intent1, Context context) {
        intent = intent1;
        mContext = context;
    }

    public void initialize(){
        ingredientList.clear();
        final Context context = mContext.getApplicationContext();
        final String string = PreferenceManager.getDefaultSharedPreferences(context).getString(
                context.getString(R.string.key_recipe_name), context.getString(R.string.key_recipe_fault));


                List<RecipeIngredients> ingredients = Utils.getIngredients(string, context);
                Log.e("OO"," "+ingredients.size());

                for (RecipeIngredients ingredient :
                        ingredients) {
                    String ingredientString =""+ingredient.getQuantity()+" "+ingredient.getMeasure()+
                            " of "+ingredient.getIngredient();
                    ingredientList.add(ingredientString);
                }



    }
    @Override
    public void onCreate() {
        initialize();
    }

    @Override
    public void onDataSetChanged() {
        initialize();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.wiget_list_item);
        view.setTextViewText(R.id.tv_widget_ingred, ingredientList.get(i));

        return view;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
