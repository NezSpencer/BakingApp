package com.nezspencer.bakingapp.recipeitem;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nezspencer.bakingapp.R;
import com.nezspencer.bakingapp.pojo.RecipeIngredients;
import com.nezspencer.bakingapp.pojo.RecipeSteps;

import java.util.List;

/**
 * Created by nezspencer on 6/20/17.
 */

public class IngredientStepAdapter extends RecyclerView.Adapter<IngredientStepAdapter.MyHolder> {

    private List<RecipeSteps> steps;
    private List<RecipeIngredients> ingredients;
    private Context context;
    private SimpleExoPlayer player;

    public IngredientStepAdapter(Context context, List<RecipeSteps> steps) {
        this.context = context;
        this.steps = steps;
        this.ingredients = null;
    }

    public IngredientStepAdapter(Context context, List<RecipeIngredients> ingredients, @Nullable
                                 String type) {
        this.context = context;
        this.ingredients = ingredients;
        this.steps = null;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_detail_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        RecipeSteps step;
        RecipeIngredients ingredient;

        if (steps != null && steps.size()>0){
            step = steps.get(position);
            Log.e("LOGGER","size is "+steps.size());

            String videoUrl = step.getVideoURL();
            holder.textView.setText(step.getDescription());
            if (TextUtils.isEmpty(videoUrl)){
                holder.playerView.setVisibility(View.GONE);
            }
            else {
                holder.playerView.setVisibility(View.VISIBLE);
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelection.Factory videoTrackSelectorFactory = new AdaptiveTrackSelection.Factory
                        (bandwidthMeter);
                TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectorFactory);

                player = ExoPlayerFactory.newSimpleInstance(context,trackSelector);

                holder.playerView.setPlayer(player);

                DefaultBandwidthMeter meter = new DefaultBandwidthMeter();
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                        Util.getUserAgent(context,context.getPackageName()),meter);
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

                Uri videoUri = Uri.parse(step.getVideoURL()).buildUpon().build();
                MediaSource videoSource = new ExtractorMediaSource(videoUri,dataSourceFactory,
                        extractorsFactory,null,null);

                player.prepare(videoSource);


            }
        }
        else {
            Log.e("LOGGER","size is "+ingredients.size());
            ingredient = (RecipeIngredients) ingredients.get(position);
            String ingredientString =""+ingredient.getQuantity()+" "+ingredient.getMeasure()+
                    " of "+ingredient.getIngredient();
            holder.textView.setText(ingredientString);
            holder.playerView.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {

        return (steps ==null)?ingredients.size() : steps.size();
    }

    static final class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        SimpleExoPlayerView playerView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_recipe_text_item);
            playerView = (SimpleExoPlayerView) itemView.findViewById(R.id.vid_player);
        }
    }

    public void releaseExoPlayer(){
        if(player != null)
        {
            player.release();
            player = null;
        }


    }
}
