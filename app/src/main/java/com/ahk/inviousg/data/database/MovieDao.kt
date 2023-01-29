package com.ahk.inviousg.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ahk.inviousg.data.model.RecentlyViewed
import io.reactivex.Completable

@Dao
interface MovieDao {
    @Query("SELECT * FROM RecentlyViewed ORDER BY added_date DESC LIMIT 15")
    fun getRecentMovies(): List<RecentlyViewed>

    @Upsert
    fun upsertMovie(movie: RecentlyViewed): Completable
}
