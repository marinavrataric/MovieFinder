package com.example.moviefinder.networking

import com.example.moviefinder.model.MovieResponse
import retrofit2.Callback

interface Interactor {
    fun getMovie(MovieResponseCallack : Callback<MovieResponse>)
    fun searchMovies(
        MovieResponseCallack: Callback<MovieResponse>,
        searchQuery: String
    )
}