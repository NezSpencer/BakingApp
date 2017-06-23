package com.nezspencer.bakingapp.recipeitem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.Recipe;
import com.nezspencer.bakingapp.pojo.RecipeSteps;
import com.nezspencer.bakingapp.recipedashboard.RecipeActivity;

public class RecipeItemActivity extends AppCompatActivity implements RecipeItemListFragment.OnRecipeItemClickListener{

    private boolean isTablet;
    public static final String KEY_ITEM = "item key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

        if (getIntent().hasExtra(RecipeActivity.KEY_PARCELABLE)){
            int recipePosition = getIntent().getIntExtra(RecipeActivity.KEY_PARCELABLE,0        );
            AppClass.appItemList.clear();

            Recipe recipe = AppClass.appRecipeList.get(recipePosition);


            for (int i=0; i<=recipe.getSteps().length; i++)
                if (i==0)
                    AppClass.appItemList.add("Recipe Ingredients");
                else{
                    RecipeSteps step = recipe.getSteps()[i-1];
                    Log.e("LOGGER"," "+(step==null));
                    AppClass.appItemList.add(step.getShortDescription());
                }
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
}
