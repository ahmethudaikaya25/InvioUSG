package com.ahk.inviousg.ui.search.state.models

import android.app.appsearch.SearchResult

data class FormInformation(
    var searchQuery: String? = "",
    val resultList: List<SearchResult>? = emptyList(),
    val loadingType: LoadingType? = LoadingType.NONE,
    val page: Int = 1
)

enum class LoadingType {
    SEARCH_LOADING,
    FRAGMENT_LOADING,
    NONE
}
