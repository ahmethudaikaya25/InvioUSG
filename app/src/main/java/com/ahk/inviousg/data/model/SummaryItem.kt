package com.ahk.inviousg.data.model

import com.google.gson.annotations.SerializedName

data class SummaryItem(
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Year")
    val year: String,
    val imdbID: String
)
