package com.example.movieexamen.data


import com.example.movieexamen.model.Movie

data class MovieResponse(
    val Search: List<Movie>,
    val totalResults: String,
    val Response: String
)