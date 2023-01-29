package com.ahk.inviousg.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val movieSummaries: List<MovieSummary>,
    val totalResults: String,
    @SerializedName("Error")
    val error: String
)
