package com.ahk.inviousg.data.api

import com.ahk.inviousg.data.model.DetailedMovie
import com.ahk.inviousg.data.model.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDBAPIService {
    @GET("/?")
    fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") query: String
    ): Single<SearchResponse>

    @GET("/?")
    fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") query: String,
        @Query("type") queryType: String
    ): Single<SearchResponse>

    @GET("/?")
    fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbID: String
    ): Single<DetailedMovie>
}
