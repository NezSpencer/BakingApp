package com.nezspencer.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.database.RecipeContract;
import com.nezspencer.bakingapp.pojo.Recipe;
import com.nezspencer.bakingapp.pojo.RecipeIngredients;
import com.nezspencer.bakingapp.pojo.RecipeSteps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RecipeWidgetUpdateService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_CHANGE_IMAGE = "com.nezspencer.bakingapp.widget.action" +
            ".changeimage";

    /*int[] recipeImages = {
            R.drawable.nutella2_app,
            R.drawable.brownie1_app,
            R.drawable.yellowcake1_app,
            R.drawable.cheesecake1_app,
            R.drawable.nutella1_app,
            R.drawable.brownie2_app,
            R.drawable.yellowcake2_app,
            R.drawable.cheesecake2_app
    };*/



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
    public static void startActionChangeImage(Context context) {
        Intent intent = new Intent(context, RecipeWidgetUpdateService.class);
        intent.setAction(ACTION_CHANGE_IMAGE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("LOGGER"," service is processing request...");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHANGE_IMAGE.equals(action)) {

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        fetchRecipeFromDb();
                    }
                });

            }         }
    }


    /*private void fetchRecipe(){

        Injector.provideRetrofit().create(BakingApi.class)
                .getRecipe().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                changeToRecipeList(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }*/

    void changeToRecipeList(String recipeString) {

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
            deliverRecipeList(recipeArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void deliverRecipeList(List<Recipe> recipeList){
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this,RecipeWidget
                .class));

        RecipeWidget.updateRecipeWidget(this,widgetManager,appWidgetIds,recipeList);
        Log.e("LOGGER"," service is done!");
    }

    void fetchRecipeFromDb(){

        List<Recipe> list = new ArrayList<>();
        List<RecipeSteps> stepList = new ArrayList<>();
        List<RecipeIngredients> ingredientList = new ArrayList<>();
        int count =0;
        Cursor cursor = getContentResolver().query(RecipeContract.TableRecipe.CONTENT_URI,
                null,null,null, RecipeContract.TableRecipe.SORT_ORDER_DEFAULT);

        while (cursor != null && cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex(RecipeContract.TableRecipe
                    .COLUMN_NAME));

            String stepSelect = RecipeContract.TableStep.COLUMN_NAME+"=?";
            String[] stepArg ={name};
            Cursor cursorStep = getContentResolver().query(
                    RecipeContract.TableStep.CONTENT_URI,
                    null,stepSelect,stepArg, RecipeContract.TableStep.SORT_ORDER_DEFAULT);

            while (cursorStep != null && cursorStep.moveToNext()){
                String vidUrl = cursorStep.getString(cursorStep.getColumnIndex(RecipeContract
                        .TableStep.COLUMN_VIDEOURL));
                String shortDesc = cursorStep.getString(cursorStep.getColumnIndex(RecipeContract
                        .TableStep.COLUMN_SHORTDESC));
                String desc = cursorStep.getString(cursorStep.getColumnIndex(RecipeContract
                        .TableStep.COLUMN_DESC));
                RecipeSteps steps = new RecipeSteps();
                steps.setVideoURL(vidUrl);
                steps.setShortDescription(shortDesc);
                steps.setDescription(desc);
                stepList.add(steps);
            }

            if (cursorStep != null)
                cursorStep.close();

            String ingredSelect = RecipeContract.TableIngredient.COLUMN_NAME+"=?";
            String[] ingredArg ={name};
            Cursor ingredCursor = getContentResolver().query(RecipeContract.TableIngredient
                    .CONTENT_URI,null,ingredSelect,ingredArg, RecipeContract.TableIngredient
                    .SORT_ORDER_DEFAULT);

            while (ingredCursor != null && ingredCursor.moveToNext()){

                String measure = ingredCursor.getString(ingredCursor.getColumnIndex
                        (RecipeContract.TableIngredient.COLUMN_MEASURE));
                double quantity = ingredCursor.getDouble(ingredCursor.getColumnIndex
                        (RecipeContract.TableIngredient.COLUMN_QUANTITY));
                String ingred = ingredCursor.getString(ingredCursor.getColumnIndex(RecipeContract
                        .TableIngredient.COLUMN_INGREDIENT));

                RecipeIngredients ingredient = new RecipeIngredients();

                ingredient.setIngredient(ingred);
                ingredient.setMeasure(measure);
                ingredient.setQuantity(quantity);

                ingredientList.add(ingredient);
            }

            if(ingredCursor != null)
                ingredCursor.close();

            Recipe recipe = new Recipe();
            recipe.setName(name);
            recipe.setIngredients(ingredientList.toArray(new RecipeIngredients[ingredientList.size()]));
            recipe.setSteps(stepList.toArray(new RecipeSteps[stepList.size()]));

            list.add(recipe);
        }

        if (cursor != null)
            cursor.close();

        deliverRecipeList(list);
    }


}
