package com.ahk.inviousg.domain.usecases

import com.ahk.inviousg.data.model.DetailedResponse
import com.ahk.inviousg.domain.omdb.OMDBRepository
import io.reactivex.Single

class GetDetailsUseCase(val omdbRepository: OMDBRepository) {
    fun invoke(imdbID: String): Single<DetailedResponse> {
        return omdbRepository.getMovieDetails(imdbID)
    }
}
