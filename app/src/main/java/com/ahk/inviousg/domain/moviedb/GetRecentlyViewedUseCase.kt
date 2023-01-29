package com.ahk.inviousg.domain.moviedb

import com.ahk.inviousg.data.model.MovieSummary
import io.reactivex.Single

class GetRecentlyViewedUseCase(
    private val recentlyViewedRepository: RecentlyViewedRepository
) {
    fun invoke(): Single<List<MovieSummary>> {
        return recentlyViewedRepository.getRecentlyViewed()
    }
}
