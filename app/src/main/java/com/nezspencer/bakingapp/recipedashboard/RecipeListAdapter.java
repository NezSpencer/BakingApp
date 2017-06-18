package com.nezspencer.bakingapp.recipedashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.Recipe;

import java.util.ArrayList;

/**
 * Created by nezspencer on 6/15/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {

    private ArrayList<Recipe> recipes;
    private Context context;

    int[] recipeImages = {
            R.drawable.nutella2_app,
            R.drawable.brownie1_app,
            R.drawable.yellowcake1_app,
            R.drawable.cheesecake1_app
    };

    public RecipeListAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;

    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_list,parent,false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {

        holder.recipeTextView.setText(recipes.get(position).getName());
        holder.recipeImageView.setImageResource(recipeImages[position]);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void swapList(ArrayList<Recipe> newList){
        recipes.clear();
        recipes.addAll(newList);
        notifyDataSetChanged();
    }

    static class RecipeHolder extends RecyclerView.ViewHolder{

        TextView recipeTextView;
        ImageView recipeImageView;

        public RecipeHolder(View itemView) {
            super(itemView);

            recipeImageView = (ImageView) itemView.findViewById(R.id.iv_recipe);
            recipeTextView = (TextView) itemView.findViewById(R.id.tv_recipe_name);
        }
    }
}
