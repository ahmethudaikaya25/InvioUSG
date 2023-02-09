package com.ahk.inviousg.data.model.dto

import com.ahk.inviousg.data.model.RecentlyViewed

data class MovieSummaryDTO(
    val imdbID: String,
    val poster: String,
    val title: String,
    val year: String
) {
    fun mapToRecentlyViewed(date: String) = RecentlyViewed(
        title = title,
        year = year,
        imdbID = imdbID,
        poster = poster,
        addedDate = date
    )
}