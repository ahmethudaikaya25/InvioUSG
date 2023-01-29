package com.ahk.inviousg.domain.omdb

import com.ahk.inviousg.data.model.MovieSummary
import com.ahk.inviousg.domain.internetstate.InternetStateRepository
import com.ahk.inviousg.ui.search.state.models.QueryType
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class SearchUseCase(
    private val omdbRepository: OMDBRepository,
    private val internetStateRepository: InternetStateRepository
) {
    fun invoke(query: String, queryType: QueryType): Single<List<MovieSummary>> {
        if (query.length < 3) {
            return Single.just(emptyList())
        }
        return when (queryType) {
            QueryType.ALL -> searchAll(query)
            else -> searchByType(query, queryType)
        }
    }

    private fun searchAll(query: String): Single<List<MovieSummary>> {
        return internetStateRepository.isInternetAvailable()
            .toSingleDefault(Any())
            .delay(1, TimeUnit.SECONDS)
            .flatMap { omdbRepository.searchMovies(query) }
    }

    private fun searchByType(query: String, queryType: QueryType): Single<List<MovieSummary>> {
        return internetStateRepository.isInternetAvailable()
            .toSingleDefault(Any())
            .delay(1, TimeUnit.SECONDS)
            .flatMap { omdbRepository.searchMovies(query, queryType.name) }
    }
}
