package com.nezspencer.bakingapp.recipeitem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nezspencer.bakingapp.AppClass;
import com.nezspencer.bakingapp.R;

/**
 * Created by nezspencer on 6/19/17.
 */

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.ItemHolder> {

    private OnRecipeRecyclerListener listener;

    public interface OnRecipeRecyclerListener {
        void onRecipeRecyclerClicked(int position);
        }


    public RecipeItemAdapter(OnRecipeRecyclerListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_step_item,parent,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        String text = AppClass.appItemList.get(position);
        holder.textView.setText(text);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeRecyclerClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return AppClass.appItemList.size();
    }


    static final class ItemHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_step_ingredient);
        }
    }
}
