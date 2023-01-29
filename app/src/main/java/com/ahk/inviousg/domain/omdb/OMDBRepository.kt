package com.ahk.inviousg.domain.omdb

import com.ahk.inviousg.data.api.OMDBAPIService
import com.ahk.inviousg.data.model.DetailedMovie
import com.ahk.inviousg.data.model.MovieSummary
import io.reactivex.Single

class OMDBRepository(
    private val omdbService: OMDBAPIService
) {
    fun searchMovies(query: String): Single<List<MovieSummary>> =
        mapSearchResponse(omdbService.searchMovies("", query))

    fun searchMovies(query: String, queryType: String): Single<List<MovieSummary>> =
        mapSearchResponse(omdbService.searchMovies("", query, queryType))

    fun getMovieDetails(imdbID: String): Single<DetailedMovie> =
        mapDetailedResponse(omdbService.getMovieDetails("", imdbID))
}
