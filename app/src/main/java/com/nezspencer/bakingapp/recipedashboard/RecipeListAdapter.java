package com.nezspencer.bakingapp.recipedashboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.Recipe;

/**
 * Created by nezspencer on 6/15/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {

    private RecipeClickListener listener;

    public interface RecipeClickListener {
        void onRecipeClicked(Recipe recipe);
    }

    int[] recipeImages = {
            R.drawable.nutella2_app,
            R.drawable.brownie1_app,
            R.drawable.yellowcake1_app,
            R.drawable.cheesecake1_app
    };

    public RecipeListAdapter(RecipeClickListener listener) {
        this.listener = listener;

    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list,parent,false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, final int position) {
        final Recipe recipe = AppClass.appRecipeList.get(position);
        holder.recipeTextView.setText(recipe.getName());
        holder.recipeImageView.setImageResource(recipeImages[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(recipe);
            }
        });


    }

    @Override
    public int getItemCount() {
        return AppClass.appRecipeList.size();
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
