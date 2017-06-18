package com.nezspencer.bakingapp.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nezspencer on 6/9/17.
 */

public interface BakingApi {

    @GET("59121517_baking/baking.json")
    Call<String> getRecipe();

}
