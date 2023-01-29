package com.ahk.inviousg.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahk.inviousg.data.model.RecentlyViewed

@Database(entities = [RecentlyViewed::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
