package com.ahk.inviousg.domain.omdb

import com.ahk.inviousg.data.api.OMDBAPIService
import com.ahk.inviousg.data.mapper.mapDetailedResponse
import com.ahk.inviousg.data.mapper.mapSearchResponse
import com.ahk.inviousg.data.model.DetailedResponse
import com.ahk.inviousg.data.model.SummaryItem
import io.reactivex.Single

class OMDBRepository(
    val omdbService: OMDBAPIService
) {
    fun searchMovies(query: String): Single<List<SummaryItem>> =
        mapSearchResponse(omdbService.searchMovies("", query))

    fun getMovieDetails(imdbID: String): Single<DetailedResponse> =
        mapDetailedResponse(omdbService.getMovieDetails("", imdbID))
}
