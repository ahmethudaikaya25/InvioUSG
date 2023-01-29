package com.ahk.inviousg.domain

import com.ahk.inviousg.data.api.OMDBAPIService
import com.ahk.inviousg.data.database.MovieDao
import com.ahk.inviousg.data.internetstate.InternetStateProvider
import com.ahk.inviousg.domain.internetstate.InternetStateRepository
import com.ahk.inviousg.domain.moviedb.AddRecentlyViewedUseCase
import com.ahk.inviousg.domain.moviedb.GetRecentlyViewedUseCase
import com.ahk.inviousg.domain.moviedb.RecentlyViewedRepository
import com.ahk.inviousg.domain.omdb.GetDetailsUseCase
import com.ahk.inviousg.domain.omdb.OMDBRepository
import com.ahk.inviousg.domain.omdb.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideOMDBRepository(omdbApiService: OMDBAPIService): OMDBRepository =
        OMDBRepository(omdbApiService)

    @Provides
    fun provideInternetStateRepository(internetStateProvider: InternetStateProvider): InternetStateRepository =
        InternetStateRepository(internetStateProvider)

    @Provides
    fun provideSearchUseCase(
        omdbRepository: OMDBRepository,
        internetStateRepository: InternetStateRepository
    ): SearchUseCase =
        SearchUseCase(omdbRepository, internetStateRepository)

    @Provides
    fun provideGetDetailsUseCase(
        omdbRepository: OMDBRepository,
        internetStateRepository: InternetStateRepository
    ): GetDetailsUseCase =
        GetDetailsUseCase(omdbRepository, internetStateRepository)

    @Provides
    fun provideGetRecentlyViewedUseCase(
        recentlyViewedRepository: RecentlyViewedRepository
    ): GetRecentlyViewedUseCase =
        GetRecentlyViewedUseCase(recentlyViewedRepository)

    @Provides
    fun provideAddRecentlyViewedUseCase(
        recentlyViewedRepository: RecentlyViewedRepository
    ): AddRecentlyViewedUseCase =
        AddRecentlyViewedUseCase(recentlyViewedRepository)

    @Provides
    fun provideRecentlyViewedRepository(movieDao: MovieDao): RecentlyViewedRepository =
        RecentlyViewedRepository(movieDao)
}
