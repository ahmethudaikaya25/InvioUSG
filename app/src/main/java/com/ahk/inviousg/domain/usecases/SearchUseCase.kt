package com.ahk.inviousg.domain.usecases

import com.ahk.inviousg.data.model.SummaryItem
import com.ahk.inviousg.domain.omdb.OMDBRepository
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class SearchUseCase(val omdbRepository: OMDBRepository) {
    fun invoke(query: String): Single<List<SummaryItem>> {
        if (query.length < 3) {
            return Single.just(emptyList())
        }
        return omdbRepository.searchMovies(query).delay(1000, TimeUnit.MILLISECONDS)
    }
}
