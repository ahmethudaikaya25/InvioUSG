package com.ahk.inviousg.domain

import com.ahk.inviousg.data.api.OMDBAPIService
import com.ahk.inviousg.domain.omdb.OMDBRepository
import com.ahk.inviousg.domain.usecases.SearchUseCase
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
    fun provideSearchUseCase(omdbRepository: OMDBRepository): SearchUseCase =
        SearchUseCase(omdbRepository)
}
