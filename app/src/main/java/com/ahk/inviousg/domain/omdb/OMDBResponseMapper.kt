package com.ahk.inviousg.domain.omdb

import com.ahk.inviousg.data.model.DataExceptions
import com.ahk.inviousg.data.model.DetailedMovie
import com.ahk.inviousg.data.model.SearchResponse
import com.ahk.inviousg.data.model.dto.DetailedMovieDTO
import com.ahk.inviousg.data.model.dto.MovieSummaryDTO
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject

fun mapSearchResponse(response: Single<SearchResponse>): Single<List<MovieSummaryDTO>> {
    val single: SingleSubject<List<MovieSummaryDTO>> = SingleSubject.create()
    response.subscribeOn(Schedulers.io()).subscribe({ responseVal ->
        when (responseVal.response) {
            "True" -> {
                single.onSuccess(responseVal.movieSummaries.map { it.mapToMovieSummaryDTO() })
            }
            "False" -> {
                DataExceptions.getException(responseVal.error).let { single.onError(it) }
            }
        }
    }, {
        single.onError(DataExceptions.DataNetworkException("Network Error"))
    })
    return single
}

fun mapDetailedResponse(response: Single<DetailedMovie>): Single<DetailedMovieDTO> {
    val single: SingleSubject<DetailedMovieDTO> = SingleSubject.create()
    response.subscribeOn(Schedulers.io()).subscribe({ responseVal ->
        when (responseVal.response) {
            "True" -> {
                single.onSuccess(responseVal.mapToDetailedMovieDTO())
            }
            "False" -> {
                responseVal.error?.let { it ->
                    DataExceptions.getException(it)
                }
            }
        }
    }, {
        single.onError(DataExceptions.DataNetworkException("Network Error"))
    })
    return single
}
