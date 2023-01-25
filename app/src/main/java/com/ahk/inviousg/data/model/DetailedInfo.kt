package com.ahk.inviousg.data.model

data class DetailedInfo(
    val actors: String,
    val awards: String,
    val boxOffice: String,
    val country: String,
    val dvd: String,
    val director: String,
    val genre: String,
    val language: String,
    val metaScore: String,
    val plot: String,
    val poster: String,
    val production: String,
    val rated: String,
    val ratings: List<Rating>,
    val released: String,
    val response: String,
    val runtime: String,
    val title: String,
    val type: String,
    val website: String,
    val writer: String,
    val year: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String
)
