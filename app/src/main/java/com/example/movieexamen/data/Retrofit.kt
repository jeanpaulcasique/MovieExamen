package com.example.movieexamen.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com/") // Asegúrate de que esta es la URL base correcta.
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
