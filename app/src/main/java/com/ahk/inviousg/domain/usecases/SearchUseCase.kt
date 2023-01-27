package com.ahk.inviousg.domain.usecases

import com.ahk.inviousg.domain.omdb.OMDBRepository

class SearchUseCase(val omdbRepository: OMDBRepository) {
    fun invoke(query: String) {
        omdbRepository.searchMovies(query)
            .doOnSuccess { response ->
                println(response.response)
                println(response.totalResults)
                println(response.summaryItems)
            }
            .doOnError {
                println(it)
            }
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .subscribe()
    }
}
