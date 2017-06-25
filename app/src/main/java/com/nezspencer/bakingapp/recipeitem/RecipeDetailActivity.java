package com.nezspencer.bakingapp.recipeitem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.RecipeIngredients;
import com.nezspencer.bakingapp.pojo.RecipeSteps;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 6/18/17.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    private static int itemPosition=0;
    @Bind(R.id.rv_recipe_detail)
    RecyclerView recyclerRecipeDetail;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private IngredientStepAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getIntent().hasExtra(RecipeItemActivity.KEY_ITEM)){
            itemPosition = getIntent().getIntExtra(RecipeItemActivity.KEY_ITEM,0);
        }

        recyclerRecipeDetail.setHasFixedSize(true);
        if (itemPosition == 0){
            List<RecipeIngredients> ingredients = Arrays.asList(
                    AppClass.selectedRecipe.getIngredients());
            Log.e("LOGGER"," ingredient size is "+ingredients.size());
            adapter = new IngredientStepAdapter(this,ingredients,null);
        }

        else {
            List<RecipeSteps> steps = Arrays.asList(AppClass.selectedRecipe.getSteps()
                    [itemPosition-1]);

            adapter = new IngredientStepAdapter(this,steps);
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(AppClass.selectedRecipe.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        recyclerRecipeDetail.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.releaseExoPlayer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        NavUtils.navigateUpFromSameTask(this);
    }
}
