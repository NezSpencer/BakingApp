package com.nezspencer.bakingapp.recipedashboard;

import com.google.gson.Gson;
import com.nezspencer.bakingapp.BakingInterface;
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
import rx.Observer;

/**
 * Created by nezspencer on 6/7/17.
 */

public class RecipeListPresenter {

    private BakingInterface.RecipeActivityContract recipeContract;
    private Observer<String> recipeObserver;

    public RecipeListPresenter(BakingInterface.RecipeActivityContract contract) {
        this.recipeContract = contract;

        recipeObserver = new Observer<String>(){
            @Override
            public void onCompleted() {
                recipeContract.hideLoadingProgress();
            }

            @Override
            public void onError(Throwable e) {
                recipeContract.hideLoadingProgress();
                onNetworkError();
            }

            @Override
            public void onNext(String recipe) {

                changeToRecipeList(recipe);

            }
        };

    }

    public void onNetworkError(){
        recipeContract.showError(R.string.error_retry);
    }

    public void onRequestCompleted(ArrayList<Recipe> recipeList){

        recipeContract.hideLoadingProgress();
        recipeContract.setRecipeList(recipeList);
    }

    public void fetchRecipe(){
        recipeContract.showLoadingProgress();
        Call<String> recipeCall = Injector.provideRetrofit().create(BakingApi.class).getRecipe();
            recipeCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    changeToRecipeList(response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    onNetworkError();
                }
            });


    }

    public void changeToRecipeList(String recipeString) {

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        try {
            JSONArray recipeArray = new JSONArray(recipeString);
            Gson gson = new Gson();

            for (int i =0; i<recipeArray.length(); i++){
                JSONObject object = recipeArray.getJSONObject(i);
                Recipe recipe = gson.fromJson(object.toString(),Recipe.class);
                recipeArrayList.add(recipe);
            }

            onRequestCompleted(recipeArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
