package com.nezspencer.bakingapp.recipedashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.BakingInterface;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.Recipe;
import com.nezspencer.bakingapp.pojo.RecipeSteps;
import com.nezspencer.bakingapp.recipeitem.RecipeItemActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements BakingInterface
        .RecipeActivityContract, RecipeListAdapter.RecipeClickListener {


    @Bind(R.id.recipe_recycler)
    RecyclerView recipeRecycler;
    private RecipeListAdapter recipeListAdapter;
    private RecipeListPresenter presenter;

    private ProgressDialog progressDialog;
    public static final String KEY_PARCELABLE ="parcelableExtra";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        ButterKnife.bind(this);


        recipeListAdapter = new RecipeListAdapter(this);
        recipeRecycler.setHasFixedSize(true);
        recipeRecycler.setAdapter(recipeListAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        presenter = new RecipeListPresenter(this);
        presenter.fetchRecipe();
    }

    @Override
    public void setRecipeList(ArrayList<Recipe> recipeList) {
        AppClass.appRecipeList.clear();
        AppClass.appRecipeList.addAll(recipeList);
        recipeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingProgress() {
        if (!progressDialog.isShowing())
        progressDialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showError(@StringRes int errormsg) {
        Toast.makeText(this,errormsg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        AppClass.appItemList.clear();

        for (int i=0; i<=recipe.getSteps().length; i++)
            if (i==0)
                AppClass.appItemList.add("Recipe Ingredients");
            else{
                RecipeSteps step = recipe.getSteps()[i-1];
                AppClass.appItemList.add(step.getShortDescription());
            }

        AppClass.selectedRecipe = recipe;
        Intent intent = new Intent(RecipeActivity.this,RecipeItemActivity.class);
        intent.putExtra(KEY_PARCELABLE,recipe);
        startActivity(intent);
    }
}
