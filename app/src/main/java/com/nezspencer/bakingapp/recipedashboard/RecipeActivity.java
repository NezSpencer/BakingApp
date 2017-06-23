package com.nezspencer.bakingapp.recipedashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.BakingInterface;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.Recipe;
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
        if (progressDialog!=null && !progressDialog.isShowing())
        progressDialog.show();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void hideLoadingProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showError(@StringRes int errormsg) {
        Toast.makeText(this,errormsg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipeClicked(Recipe recipe,int position) {

        Intent intent = new Intent(RecipeActivity.this,RecipeItemActivity.class);
        intent.putExtra(KEY_PARCELABLE,position);
        AppClass.selectedRecipe = recipe;
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoadingProgress();
    }
}
