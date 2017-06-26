package com.nezspencer.bakingapp.recipedashboard;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.BakingInterface;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.database.RecipeContract;
import com.nezspencer.bakingapp.pojo.Recipe;
import com.nezspencer.bakingapp.pojo.RecipeIngredients;
import com.nezspencer.bakingapp.pojo.RecipeSteps;
import com.nezspencer.bakingapp.recipeitem.RecipeItemActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements BakingInterface
        .RecipeActivityContract, RecipeListAdapter.RecipeClickListener, LoaderManager
        .LoaderCallbacks<List<Recipe>> {

    @Nullable private SimpleIdlingResource idlingResource;

    @Bind(R.id.recipe_recycler)
    RecyclerView recipeRecycler;
    private static List<Recipe> recipeList= new ArrayList<>();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private RecipeListAdapter recipeListAdapter;
    private RecipeListPresenter presenter;

    private ProgressDialog progressDialog;
    public static final String KEY_POSITION ="data position";
    public static final String KEY_WIDGET = "from widget";
    public static final String KEY_IS_PROGRESS_SHOWING =" progress showing";

    public static final int DB_LOADER = 123;
    public static final String DB_SAVE =" save data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        recipeListAdapter = new RecipeListAdapter(this);
        recipeRecycler.setHasFixedSize(true);
        recipeRecycler.setAdapter(recipeListAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        presenter = new RecipeListPresenter(this);
        presenter.fetchRecipe();

        if (idlingResource != null)
            idlingResource.setmIdleNow(false);
    }

    @Override
    public void setRecipeList(ArrayList<Recipe> recipeList1) {
        recipeList.clear();
        recipeList.addAll(recipeList1);

        initializeLoader();
    }

    @Override
    public void showLoadingProgress() {
        if (progressDialog!=null && !progressDialog.isShowing())
        {
            progressDialog.show();
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putBoolean(KEY_IS_PROGRESS_SHOWING,true)
                    .apply();
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        boolean isProgressShowing = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(KEY_IS_PROGRESS_SHOWING,false);
        if (isProgressShowing)
            showLoadingProgress();

    }

    @Override
    public void hideLoadingProgress() {
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();

            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putBoolean(KEY_IS_PROGRESS_SHOWING,true)
                    .apply();
        }


    }

    @Override
    public void showError(@StringRes int errormsg) {
        Toast.makeText(this,errormsg,Toast.LENGTH_SHORT).show();
        initializeLoader();

    }

    @Override
    public void onRecipeClicked(Recipe recipe,int position) {

        Intent intent = new Intent(RecipeActivity.this,RecipeItemActivity.class);
        intent.putExtra(KEY_POSITION,position);
        AppClass.selectedRecipe = recipe;
        saveRecipeName(recipe.getName());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoadingProgress();
    }

    public void saveToDB(){
        if (recipeList != null && recipeList.size()> 0){
            getContentResolver().delete(RecipeContract.TableRecipe.CONTENT_URI,
                    null,null);
            getContentResolver().delete(RecipeContract.TableStep.CONTENT_URI,
                    null,null);
            getContentResolver().delete(RecipeContract.TableIngredient.CONTENT_URI,
                    null,null);

            for (int i =0; i <recipeList.size(); i++){
                Recipe recipe = recipeList.get(i);
                ContentValues recipeValue = new ContentValues();
                ContentValues[] stepValues = new ContentValues[recipe.getSteps().length];
                ContentValues [] ingredientValues = new ContentValues[recipe.getIngredients().length];

                recipeValue.put(RecipeContract.TableRecipe.COLUMN_NAME,recipe.getName());

                for (int j=0; j < recipe.getSteps().length; j++){
                    RecipeSteps step = recipe.getSteps()[j];
                    ContentValues stepValue = new ContentValues();
                    stepValue.put(RecipeContract.TableStep.COLUMN_NAME,recipe.getName());
                    stepValue.put(RecipeContract.TableStep.COLUMN_VIDEOURL,step.getVideoURL());
                    stepValue.put(RecipeContract.TableStep.COLUMN_SHORTDESC,step.getShortDescription());
                    stepValue.put(RecipeContract.TableStep.COLUMN_DESC,step.getDescription());
                    stepValue.put(RecipeContract.TableStep.COLUMN_THUMBNAIL_URL,step.getThumbnailURL());

                    stepValues[j] = stepValue;
                }

                for (int j =0; j < recipe.getIngredients().length; j++){
                    RecipeIngredients ingredient = recipe.getIngredients()[j];
                    ContentValues ingredientVal = new ContentValues();
                    ingredientVal.put(RecipeContract.TableIngredient.COLUMN_NAME,recipe.getName());
                    ingredientVal.put(RecipeContract.TableIngredient.COLUMN_MEASURE,ingredient.getMeasure());
                    ingredientVal.put(RecipeContract.TableIngredient.COLUMN_QUANTITY,ingredient.getQuantity());
                    ingredientVal.put(RecipeContract.TableIngredient.COLUMN_INGREDIENT,ingredient.getIngredient());

                    ingredientValues[j] = ingredientVal;
                }

                //insert all to db

                getContentResolver().insert(RecipeContract.TableRecipe.CONTENT_URI,recipeValue);
                getContentResolver().bulkInsert(RecipeContract.TableStep.CONTENT_URI,stepValues);
                getContentResolver().bulkInsert(RecipeContract.TableIngredient.CONTENT_URI,
                        ingredientValues);
            }
        }






    }


    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(RecipeActivity.this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                forceLoad();
            }

            @Override
            public List<Recipe> loadInBackground() {
                saveToDB();

                return fetchRecipeFromDb();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        Toast.makeText(RecipeActivity.this,"Recipe saved!",Toast.LENGTH_SHORT).show();
        hideLoadingProgress();
        AppClass.appRecipeList.clear();
        AppClass.appRecipeList.addAll(data);
        recipeListAdapter.notifyDataSetChanged();

        String name = AppClass.appRecipeList.get(0).getName();
        saveRecipeName(name);

        if (idlingResource != null)
            idlingResource.setmIdleNow(true);

    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

    List<Recipe> fetchRecipeFromDb(){

        List<Recipe> list = new ArrayList<>();
        List<RecipeSteps> stepList = new ArrayList<>();
        List<RecipeIngredients> ingredientList = new ArrayList<>();

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
                String thumbUrl = cursorStep.getString(cursorStep.getColumnIndex(RecipeContract
                        .TableStep.COLUMN_THUMBNAIL_URL));

                RecipeSteps steps = new RecipeSteps();
                steps.setVideoURL(vidUrl);
                steps.setShortDescription(shortDesc);
                steps.setDescription(desc);
                steps.setThumbnailURL(thumbUrl);
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

        return list;


    }

    public void initializeLoader(){
        Bundle bundle = new Bundle();
        bundle.putString(DB_SAVE," saving");
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> dbLoader = loaderManager.getLoader(DB_LOADER);
        if (dbLoader == null)
            loaderManager.initLoader(DB_LOADER,bundle,RecipeActivity.this);
        else
            loaderManager.restartLoader(DB_LOADER,bundle,this);
    }

    @VisibleForTesting
    @Nullable
    public IdlingResource getIdlingResource(){
        if (idlingResource == null)
        {
            idlingResource = new SimpleIdlingResource();
        }

        return idlingResource;
    }

    public void saveRecipeName(String name){
        PreferenceManager.getDefaultSharedPreferences(RecipeActivity.this)
                .edit().putString(getString(R.string.key_recipe_name),name).apply();
    }
}
