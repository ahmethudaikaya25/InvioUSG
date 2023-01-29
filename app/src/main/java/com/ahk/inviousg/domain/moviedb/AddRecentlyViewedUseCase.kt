package com.ahk.inviousg.domain.moviedb

import com.ahk.inviousg.data.model.RecentlyViewed

class AddRecentlyViewedUseCase(
    private val recentlyViewedRepository: RecentlyViewedRepository
) {
    fun invoke(recentlyViewed: RecentlyViewed) =
        recentlyViewedRepository.addRecentlyViewed(recentlyViewed)
}
