package com.ahk.inviousg.domain.omdb

import com.ahk.inviousg.data.model.DetailedMovie
import com.ahk.inviousg.domain.internetstate.InternetStateRepository
import io.reactivex.Single

class GetDetailsUseCase(
    private val omdbRepository: OMDBRepository,
    private val internetStateRepository: InternetStateRepository
) {
    fun invoke(imdbID: String): Single<DetailedMovie> {
        return internetStateRepository.isInternetAvailable()
            .toSingleDefault(Any())
            .flatMap {
                omdbRepository.getMovieDetails(imdbID)
            }
    }
}
