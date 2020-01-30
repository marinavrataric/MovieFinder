package com.example.moviefinder.model

data class Movies(
    val id: Int,
    val title: String,
    val name: String,
    val releaseDate: String?,
    val posterPath: String?,
    val original_language: String?,
    val overview: String?,
    val voteavarage: Double?
)
