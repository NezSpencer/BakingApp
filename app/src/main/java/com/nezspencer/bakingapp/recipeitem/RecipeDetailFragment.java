package com.nezspencer.bakingapp.recipeitem;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.RecipeIngredients;
import com.nezspencer.bakingapp.pojo.RecipeSteps;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {

    private static int itemPosition=0;
    @Bind(R.id.rv_recipe_detail)
    RecyclerView recyclerRecipeDetail;

    private IngredientStepAdapter adapter;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(RecipeItemActivity.KEY_ITEM)){
            itemPosition = getArguments().getInt(RecipeItemActivity.KEY_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail,container,false);
        ButterKnife.bind(this,view);



        recyclerRecipeDetail.setHasFixedSize(true);
        if (itemPosition == 0){
            List<RecipeIngredients> ingredients = Arrays.asList(
                    AppClass.selectedRecipe.getIngredients());
            Log.e("LOGGER"," ingredient size is "+ingredients.size());
            adapter = new IngredientStepAdapter(getActivity(),ingredients,null);
        }

        else {
            List<RecipeSteps> steps = Arrays.asList(AppClass.selectedRecipe.getSteps()
                    [itemPosition-1]);

            adapter = new IngredientStepAdapter(getActivity(),steps);
        }
        recyclerRecipeDetail.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.releaseExoPlayer();
    }
}
