package com.ahk.inviousg.domain.moviedb

import com.ahk.inviousg.data.model.dto.MovieSummaryDTO
import io.reactivex.Single

class GetRecentlyViewedUseCase(
    private val recentlyViewedRepository: RecentlyViewedRepository
) {
    fun invoke(): Single<List<MovieSummaryDTO>> {
        return recentlyViewedRepository.getRecentlyViewed()
    }
}
