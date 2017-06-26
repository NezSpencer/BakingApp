package com.nezspencer.bakingapp.recipeitem;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private boolean shouldShowText;

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
    public void onBindViewHolder(final MyHolder holder, int position) {

        RecipeSteps step;
        RecipeIngredients ingredient;

        if (steps != null && steps.size()>0){
            step = steps.get(position);
            Log.e("LOGGER","size is "+steps.size());

            String videoUrl = step.getVideoURL();
            String thumbUrl = step.getThumbnailURL();


            if (TextUtils.isEmpty(videoUrl) && TextUtils.isEmpty(thumbUrl)){
                holder.showOnlyText();
                if (holder.fabToggle != null)
                    holder.textViewNoVid.setText(step.getDescription());
                else
                    holder.textView.setText(step.getDescription());

            }
            else {
                if (holder.fabToggle != null){
                    //landscape view
                    holder.textView.setText(step.getDescription());
                    holder.fabToggle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shouldShowText = !shouldShowText;
                            holder.describeCard.setVisibility(shouldShowText?View.VISIBLE: View.INVISIBLE);
                        }
                    });
                }
                else {
                    holder.textView.setText(step.getDescription());
                }

                holder.showVideoWithText();

                if (TextUtils.isEmpty(videoUrl)){
                    holder.playerCard.setVisibility(View.GONE);
                    holder.imageCard.setVisibility(View.VISIBLE);
                    Glide.with(context).load(thumbUrl)
                            .placeholder(R.mipmap.ic_launcher)
                            .into(holder.stepImage);

                }
                else {
                    holder.imageCard.setVisibility(View.GONE);
                    holder.playerCard.setVisibility(View.VISIBLE);

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

                    Uri videoUri = Uri.parse(videoUrl).buildUpon().build();
                    MediaSource videoSource = new ExtractorMediaSource(videoUri,dataSourceFactory,
                            extractorsFactory,null,null);

                    player.prepare(videoSource);
                }
            }
        }
        else {

            Log.e("LOGGER","size is "+ingredients.size());
            ingredient = ingredients.get(position);
            String ingredientString =""+ingredient.getQuantity()+" "+ingredient.getMeasure()+
                    " of "+ingredient.getIngredient();
            holder.textView.setText(ingredientString);

            holder.showOnlyText();
            holder.playerView.setVisibility(View.GONE);
            holder.playerCard.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {

        return (steps ==null)?ingredients.size() : steps.size();
    }

    static final class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CardView playerCard;
        SimpleExoPlayerView playerView;
        CardView describeCard;
        FloatingActionButton fabToggle;
        TextView textViewNoVid;
        CardView describeCardNoVid;
        CardView imageCard;
        ImageView stepImage;

        public MyHolder(View itemView) {
            super(itemView);
            playerCard = (CardView) itemView.findViewById(R.id.card_playerview);
            textView = (TextView) itemView.findViewById(R.id.tv_recipe_text_item);
            playerView = (SimpleExoPlayerView) itemView.findViewById(R.id.vid_player);
            describeCard = (CardView) itemView.findViewById(R.id.card_text);
            describeCardNoVid = (CardView) itemView.findViewById(R.id.card_text_no_vid);
            fabToggle = (FloatingActionButton) itemView.findViewById(R.id.fab_toggle_text);
            textViewNoVid = (TextView) itemView.findViewById(R.id.tv_recipe_text_item_no_vid);
            imageCard = (CardView) itemView.findViewById(R.id.card_image);
            stepImage = (ImageView) itemView.findViewById(R.id.iv_stepImage);
        }

        void showVideoWithText(){
            textView.setVisibility(View.VISIBLE);
            if (fabToggle != null){
                describeCard.setVisibility(View.GONE);
                describeCardNoVid.setVisibility(View.GONE);
            }
        }

        void showOnlyText(){
            playerCard.setVisibility(View.GONE);
            imageCard.setVisibility(View.GONE);
            if (fabToggle != null){
                fabToggle.setVisibility(View.GONE);
                describeCard.setVisibility(View.GONE);
                imageCard.setVisibility(View.GONE);
                describeCardNoVid.setVisibility(View.VISIBLE);
            }
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
