package com.example.mobileticketbooking.api;

import com.example.mobileticketbooking.model.movieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("movie/popular")
    Call<movieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<movieResponse> getTopRatedMovies(@Query("api_key") String apiKey);


}
