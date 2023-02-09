package com.ahk.inviousg.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailedMovieDTO(
    val title: String?,
    val runtime: String?,
    val imdbRating: String?,
    val released: String?,
    val plot: String?,
    val genre: String?,
    val poster: String?
) : Parcelable
