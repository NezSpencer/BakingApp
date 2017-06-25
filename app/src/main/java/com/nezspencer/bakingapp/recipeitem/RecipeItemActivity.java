package com.nezspencer.bakingapp.recipeitem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.Recipe;
import com.nezspencer.bakingapp.pojo.RecipeSteps;
import com.nezspencer.bakingapp.recipedashboard.RecipeActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipeItemActivity extends AppCompatActivity implements RecipeItemListFragment.OnRecipeItemClickListener{

    private boolean isTablet;
    public static final String KEY_ITEM = "item key";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);



        if (getIntent().hasExtra(RecipeActivity.KEY_POSITION)){
            int recipePosition = getIntent().getIntExtra(RecipeActivity.KEY_POSITION,0        );
            AppClass.appItemList.clear();

            Recipe recipe = AppClass.appRecipeList.get(recipePosition);
            AppClass.selectedRecipe = recipe;
            initializeAppItemList(recipe);
        }

        else if(getIntent().hasExtra(RecipeActivity.KEY_WIDGET)){
            Recipe recipe = (Recipe) getIntent().getSerializableExtra(RecipeActivity.KEY_WIDGET);
            AppClass.selectedRecipe = recipe;

            initializeAppItemList(recipe);
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(AppClass.selectedRecipe.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        if(findViewById(R.id.frame_recipe_item_detail) == null){
            //phone view
            isTablet = false;
        }
        else {
            //tablet view
            isTablet = true;

        }
    }

    @Override
    public void onRecipeItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ITEM,position);
        if (isTablet){
            RecipeDetailFragment detailFragment = new RecipeDetailFragment();


            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_recipe_item_detail,
                    detailFragment,"detail frag").commit();
        }

        else {

            Intent detailIntent = new Intent(this,RecipeDetailActivity.class);
            detailIntent.putExtra(KEY_ITEM,position);
            startActivity(detailIntent);
        }
    }

    void initializeAppItemList(Recipe recipe){
        AppClass.appItemList = new ArrayList<>();
        for (int i=0; i<=recipe.getSteps().length; i++)
            if (i==0)
                AppClass.appItemList.add("Recipe Ingredients");
            else{
                RecipeSteps step = recipe.getSteps()[i-1];
                Log.e("LOGGER"," "+(step==null));
                if (step != null)
                AppClass.appItemList.add(step.getShortDescription());
            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        NavUtils.navigateUpFromSameTask(this);
    }
}
