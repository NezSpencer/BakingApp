package com.nezspencer.bakingapp.DI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by nezspencer on 6/9/17.
 */

public class Injector {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static final String baseUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/";

    public static final Retrofit provideRetrofit(){

        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(provideOkHttpClient())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        return retrofit;
    }

    public static OkHttpClient provideOkHttpClient(){
        if (okHttpClient == null)
            okHttpClient= new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
                    .build();

        return okHttpClient;
    }
}
