package com.nezspencer.bakingapp.recipeitem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nezspencer.bakingapp.R;

public class RecipeItemActivity extends AppCompatActivity implements RecipeItemListFragment.OnRecipeItemClickListener{

    private boolean isTablet;
    public static final String KEY_ITEM = "item key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);

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
