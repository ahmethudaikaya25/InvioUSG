package com.ahk.inviousg.domain.usecases

import com.ahk.inviousg.domain.omdb.OMDBRepository

class GetDetailsUseCase(val omdbRepository: OMDBRepository) {
    fun invoke(imdbID: String) {
        omdbRepository.getMovieDetails(imdbID)
            .doOnSuccess { response ->
                println(response.response)
            }
            .doOnError {
                println(it)
            }
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .subscribe()
    }
}
