package com.ahk.inviousg.domain.omdb

import com.ahk.inviousg.data.model.dto.DetailedMovieDTO
import com.ahk.inviousg.domain.firebase.FetchRemoteConfigUseCase
import com.ahk.inviousg.domain.internetstate.InternetStateRepository
import io.reactivex.Single

class GetDetailsUseCase(
    private val omdbRepository: OMDBRepository,
    private val internetStateRepository: InternetStateRepository,
    private val fetchRemoteConfigUseCase: FetchRemoteConfigUseCase,
) {
    fun invoke(imdbID: String): Single<DetailedMovieDTO> =
        internetStateRepository.isInternetAvailable()
            .andThen(fetchRemoteConfigUseCase.invoke())
            .toSingle { Any() }
            .flatMap {
                omdbRepository.getMovieDetails(imdbID)
            }
}
