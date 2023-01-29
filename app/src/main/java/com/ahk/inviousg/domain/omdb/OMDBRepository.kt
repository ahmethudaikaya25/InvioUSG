package com.ahk.inviousg.domain.omdb

import com.ahk.inviousg.data.api.OMDBAPIService
import com.ahk.inviousg.data.model.DetailedMovie
import com.ahk.inviousg.data.model.MovieSummary
import com.ahk.inviousg.util.API_KEY
import io.reactivex.Single

class OMDBRepository(
    private val omdbService: OMDBAPIService
) {
    fun searchMovies(query: String): Single<List<MovieSummary>> =
        mapSearchResponse(omdbService.searchMovies(API_KEY, query))

    fun searchMovies(query: String, queryType: String): Single<List<MovieSummary>> =
        mapSearchResponse(omdbService.searchMovies(API_KEY, query, queryType))

    fun getMovieDetails(imdbID: String): Single<DetailedMovie> =
        mapDetailedResponse(omdbService.getMovieDetails(API_KEY, imdbID))
}
