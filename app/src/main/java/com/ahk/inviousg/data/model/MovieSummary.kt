package com.ahk.inviousg.data.model

import com.google.gson.annotations.SerializedName
data class MovieSummary(
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String? = "",
    @SerializedName("Year")
    val year: String,
    val imdbID: String
) {
    fun mapToRecentlyViewed(date: String) = RecentlyViewed(
        title = title,
        year = year,
        imdbID = imdbID,
        poster = poster,
        addedDate = date
    )
}
