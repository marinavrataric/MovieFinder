package com.example.moviefinder.networking

import com.example.moviefinder.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.themoviedb.org/3/movie/popular?api_key=0b0e8d104f0d6130a4fc67848f89e107
//https://api.themoviedb.org/3/genre/movie/list?api_key=0b0e8d104f0d6130a4fc67848f89e107
//https://api.themoviedb.org/3/search/company?api_key=0b0e8d104f0d6130a4fc67848f89e107&query=man

interface tmdbApiService {

    @GET("movie/popular?api_key=0b0e8d104f0d6130a4fc67848f89e107")
    fun getPopularMovies() : Call<MovieResponse>

    @GET("search/movie?api_key=0b0e8d104f0d6130a4fc67848f89e107")
    fun searchMovies(@Query("query") name: String) : Call<MovieResponse>
}