package com.nezspencer.bakingapp.recipedashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nezspencer.bakingapp.BakingInterface;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.Recipe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements BakingInterface.RecipeActivityContract {


    @Bind(R.id.recipe_recycler)
    RecyclerView recipeRecycler;
    private RecipeListAdapter recipeListAdapter;
    private RecipeListPresenter presenter;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        ButterKnife.bind(this);


        recipeListAdapter = new RecipeListAdapter(this,new ArrayList<Recipe>());
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
        recipeListAdapter.swapList(recipeList);
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
    public void onRecipeItemClicked(int position) {

    }
}
