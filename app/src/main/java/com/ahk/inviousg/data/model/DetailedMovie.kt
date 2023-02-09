package com.ahk.inviousg.data.model

import com.ahk.inviousg.data.model.dto.DetailedMovieDTO
import com.google.gson.annotations.SerializedName

data class DetailedMovie(
    @SerializedName("Actors")
    val actors: String?,
    @SerializedName("Awards")
    val awards: String?,
    @SerializedName("BoxOffice")
    val boxOffice: String?,
    @SerializedName("Country")
    val country: String?,
    @SerializedName("DVD")
    val dvd: String?,
    @SerializedName("Director")
    val director: String?,
    @SerializedName("Error")
    val error: String?,
    @SerializedName("Genre")
    val genre: String?,
    @SerializedName("Language")
    val language: String?,
    @SerializedName("Metascore")
    val metascore: String?,
    @SerializedName("Plot")
    val plot: String?,
    @SerializedName("Poster")
    val poster: String?,
    @SerializedName("Production")
    val production: String?,
    @SerializedName("Rated")
    val rated: String?,
    @SerializedName("Ratings")
    val ratings: List<Rating>?,
    @SerializedName("Released")
    val released: String?,
    @SerializedName("Response")
    val response: String?,
    @SerializedName("Runtime")
    val runtime: String?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("Website")
    val website: String?,
    @SerializedName("Writer")
    val writer: String?,
    @SerializedName("Year")
    val year: String?,
    @SerializedName("imdbID")
    val imdbID: String?,
    val imdbRating: String?,
    val imdbVotes: String?
) {
    fun mapToDetailedMovieDTO(): DetailedMovieDTO {
        return DetailedMovieDTO(
            genre = genre,
            plot = plot,
            poster = poster,
            released = released,
            runtime = runtime,
            title = title,
            imdbRating = imdbRating
        )
    }
}
