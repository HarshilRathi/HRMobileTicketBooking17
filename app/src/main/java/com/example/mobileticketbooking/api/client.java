package com.example.mobileticketbooking.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class client {

    public static final String Base_URL="http://api.themoviedb.org/3/";
    public static Retrofit retrofit=null;

    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit=new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
