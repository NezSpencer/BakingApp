package com.nezspencer.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.DI.Injector;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.api.BakingApi;
import com.nezspencer.bakingapp.pojo.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipeWidgetUpdateService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_CHANGE_IMAGE = "com.nezspencer.bakingapp.widget.action" +
            ".changeimage";

    int[] recipeImages = {
            R.drawable.nutella2_app,
            R.drawable.brownie1_app,
            R.drawable.yellowcake1_app,
            R.drawable.cheesecake1_app,
            R.drawable.nutella1_app,
            R.drawable.brownie2_app,
            R.drawable.yellowcake2_app,
            R.drawable.cheesecake2_app
    };



    // TODO: Rename parameters
    private static final String EXTRA_SWAP = "com.nezspencer.bakingapp.widget.extra.SWAP";

    public RecipeWidgetUpdateService() {
        super("RecipeWidgetUpdateService");
    }

   /*
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionChangeImage(Context context, int currentImagePosition) {
        Intent intent = new Intent(context, RecipeWidgetUpdateService.class);
        intent.setAction(ACTION_CHANGE_IMAGE);
        intent.putExtra(EXTRA_SWAP, currentImagePosition);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("LOGGER"," service is processing request...");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHANGE_IMAGE.equals(action)) {
                int imagePosition = intent.getIntExtra(EXTRA_SWAP,0);
                fetchRecipe(imagePosition);
            }         }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void swapRecipe(int currentImageId,int recipePosition) {

        int imageId = currentImageId;
        if (currentImageId >=0 && currentImageId < (recipeImages.length -1)) {
            imageId++;
        }
        else
            imageId = 0;
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this,RecipeWidget
                .class));

        RecipeWidget.updateRecipeWidget(this,widgetManager,recipeImages[imageId],recipePosition,
                appWidgetIds, imageId);
        Log.e("LOGGER"," service is done!");
    }

    private void fetchRecipe(final int position){
        //if there is data already on the list, dont fetch new one from network
        if (AppClass.appRecipeList.size() > 0)
            getImageAndTitle(position);
        else {
            //no data in list fetch new one
            Injector.provideRetrofit().create(BakingApi.class)
                    .getRecipe().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    changeToRecipeList(response.body(), position);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }

    }

    void changeToRecipeList(String recipeString, int position) {

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        try {
            JSONArray recipeArray = new JSONArray(recipeString);
            Gson gson = new Gson();

            for (int i =0; i<recipeArray.length(); i++){
                JSONObject object = recipeArray.getJSONObject(i);
                Recipe recipe = gson.fromJson(object.toString(),Recipe.class);
                recipeArrayList.add(recipe);
            }
            AppClass.appRecipeList = recipeArrayList;
            getImageAndTitle(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void getImageAndTitle(int position){

        if (AppClass.appRecipeList != null && AppClass.appRecipeList.size()>0 ){

            if (position >= AppClass.appRecipeList.size()){
                swapRecipe(position, position-AppClass.appRecipeList.size());
            }
            else
                swapRecipe(position, position);

        }
    }


}
