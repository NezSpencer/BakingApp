package com.nezspencer.bakingapp.recipeitem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nezspencer.bakingapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipeItemListFragment extends Fragment implements RecipeItemAdapter.OnRecipeRecyclerListener {

    private OnRecipeItemClickListener mListener;
    @Bind(R.id.rv_recipe_item_list)
    RecyclerView recyclerView;

    private RecipeItemAdapter itemAdapter;

    public RecipeItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_item_list,container,false);
        ButterKnife.bind(this,view);
        recyclerView.setHasFixedSize(true);
        itemAdapter = new RecipeItemAdapter(this);
        recyclerView.setAdapter(itemAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeItemClickListener) {
            mListener = (OnRecipeItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRecipeRecyclerClicked(int position) {
        mListener.onRecipeItemClick(position);
    }

    public interface OnRecipeItemClickListener {
        void onRecipeItemClick(int position);
    }
}
