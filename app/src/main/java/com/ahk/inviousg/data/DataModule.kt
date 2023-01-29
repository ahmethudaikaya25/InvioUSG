package com.ahk.inviousg.data

import android.content.Context
import androidx.room.Room
import com.ahk.inviousg.data.database.Database
import com.ahk.inviousg.data.database.MovieDao
import com.ahk.inviousg.data.internetstate.InternetStateChecker
import com.ahk.inviousg.data.internetstate.InternetStateProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideInternetStateProvider(@ApplicationContext context: Context): InternetStateProvider =
        InternetStateChecker(context)

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(
            context,
            Database::class.java,
            "database"
        ).build()

    @Provides
    fun provideMovieDao(database: Database): MovieDao = database.movieDao()
}
