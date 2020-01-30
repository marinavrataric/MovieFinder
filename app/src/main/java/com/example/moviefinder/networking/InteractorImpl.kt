package com.example.moviefinder.networking

import com.example.moviefinder.model.MovieResponse
import retrofit2.Callback

class InteractorImpl(private val apiService: tmdbApiService) : Interactor {

    override fun getMovie(MovieResponseCallack: Callback<MovieResponse>) {
        apiService
            .getPopularMovies()
            .enqueue(MovieResponseCallack)
    }

    override fun searchMovies(
        MovieResponseCallack: Callback<MovieResponse>,
        searchQuery: String) {
        apiService
            .searchMovies(searchQuery)
            .enqueue(MovieResponseCallack)
    }
}