package com.example.movieexamen.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    fun searchMovies(
        @Query("s") imdbId: String,
        @Query("apikey") apiKey: String = "fde4bf0f"
    ): Call<MovieResponse>

}


