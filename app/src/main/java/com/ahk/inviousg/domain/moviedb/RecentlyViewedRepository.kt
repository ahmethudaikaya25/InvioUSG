package com.ahk.inviousg.domain.moviedb

import com.ahk.inviousg.data.database.MovieDao
import com.ahk.inviousg.data.model.DataExceptions
import com.ahk.inviousg.data.model.RecentlyViewed
import com.ahk.inviousg.data.model.dto.MovieSummaryDTO
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RecentlyViewedRepository(
    private val movieDao: MovieDao,
) {
    fun getRecentlyViewed(): Single<List<MovieSummaryDTO>> {
        return Single.fromCallable(movieDao::getRecentMovies)
            .flatMap {
                mapDatabaseToDomain(it)
            }
    }

    private fun mapDatabaseToDomain(recentMovies: List<RecentlyViewed>): Single<List<MovieSummaryDTO>> {
        if (recentMovies.isEmpty()) {
            return Single.error(DataExceptions.MovieNotFoundException("No recently viewed movies found"))
        }
        val movies = recentMovies.map {
            MovieSummaryDTO(
                imdbID = it.imdbID,
                title = it.title,
                year = it.year,
                poster = it.poster,
            )
        }
        return Single.just(movies).subscribeOn(Schedulers.io())
    }

    fun addRecentlyViewed(recentlyViewed: RecentlyViewed): Completable =
        movieDao.upsertMovie(recentlyViewed)
}
