package com.cookandroid.movie;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {
    public static Retrofit zretrofit = null;
    public static Retrofit getClient(){
        zretrofit = new Retrofit.Builder()
                .baseUrl("http://mellowcode.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return zretrofit;
    }
}
