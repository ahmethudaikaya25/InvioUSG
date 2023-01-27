package com.ahk.inviousg.data.mapper

import com.ahk.inviousg.data.model.DetailedResponse
import com.ahk.inviousg.data.model.OMDBExceptions
import com.ahk.inviousg.data.model.SearchResponse
import com.ahk.inviousg.data.model.SummaryItem
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject

fun mapSearchResponse(response: Single<SearchResponse>): Single<List<SummaryItem>> {
    val single: SingleSubject<List<SummaryItem>> = SingleSubject.create()
    response.subscribeOn(io.reactivex.schedulers.Schedulers.io())
        .doOnSuccess { responseVal ->
            when (responseVal.response) {
                "True" -> {
                    single.onSuccess(responseVal.summaryItems)
                }
                "False" -> {
                    OMDBExceptions.getException(responseVal.error)
                        .let { single.onError(it) }
                }
            }
        }
        .doOnError {
            println(it)
        }
        .subscribe()
    return single
}

fun mapDetailedResponse(response: Single<DetailedResponse>): Single<DetailedResponse> {
    val single: SingleSubject<DetailedResponse> = SingleSubject.create()
    response.subscribeOn(io.reactivex.schedulers.Schedulers.io())
        .doOnSuccess { responseVal ->
            when (responseVal.response) {
                "True" -> {
                    single.onSuccess(responseVal)
                }
                "False" -> {
                    OMDBExceptions.getException(responseVal.error)
                        .let { single.onError(it) }
                }
            }
        }
        .doOnError {
            println(it)
        }
        .subscribe()
    return single
}