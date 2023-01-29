package com.ahk.inviousg.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentlyViewed(
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "year")
    val year: String,
    @PrimaryKey(autoGenerate = false)
    val imdbID: String,
    @ColumnInfo(name = "added_date")
    val addedDate: String
)
