package com.ahk.inviousg.domain.omdb

import com.ahk.inviousg.data.api.OMDBAPIService
import com.ahk.inviousg.data.model.dto.DetailedMovieDTO
import com.ahk.inviousg.data.model.dto.MovieSummaryDTO
import com.ahk.inviousg.util.API_KEY
import io.reactivex.Single

class OMDBRepository(
    private val omdbService: OMDBAPIService
) {
    fun searchMovies(query: String): Single<List<MovieSummaryDTO>> =
        mapSearchResponse(omdbService.searchMovies(API_KEY, query))

    fun searchMovies(query: String, queryType: String): Single<List<MovieSummaryDTO>> =
        mapSearchResponse(omdbService.searchMovies(API_KEY, query, queryType))

    fun getMovieDetails(imdbID: String): Single<DetailedMovieDTO> =
        mapDetailedResponse(omdbService.getMovieDetails(API_KEY, imdbID))
}
