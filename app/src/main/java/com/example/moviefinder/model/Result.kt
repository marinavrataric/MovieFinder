package com.example.moviefinder.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val movieId: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("vote_average")
    val voteAverage: Double
)