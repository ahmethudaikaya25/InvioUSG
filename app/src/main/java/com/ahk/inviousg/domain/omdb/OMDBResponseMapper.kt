package com.ahk.inviousg.domain.omdb

import com.ahk.inviousg.data.model.DataExceptions
import com.ahk.inviousg.data.model.DetailedMovie
import com.ahk.inviousg.data.model.MovieSummary
import com.ahk.inviousg.data.model.SearchResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject

fun mapSearchResponse(response: Single<SearchResponse>): Single<List<MovieSummary>> {
    val single: SingleSubject<List<MovieSummary>> = SingleSubject.create()
    response.subscribeOn(Schedulers.io()).subscribe({ responseVal ->
        when (responseVal.response) {
            "True" -> {
                single.onSuccess(responseVal.movieSummaries)
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

fun mapDetailedResponse(response: Single<DetailedMovie>): Single<DetailedMovie> {
    val single: SingleSubject<DetailedMovie> = SingleSubject.create()
    response.subscribeOn(Schedulers.io()).subscribe({ responseVal ->
        when (responseVal.response) {
            "True" -> {
                single.onSuccess(responseVal)
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
