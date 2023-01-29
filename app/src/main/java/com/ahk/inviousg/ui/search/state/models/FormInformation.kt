package com.ahk.inviousg.ui.search.state.models

data class FormInformation(
    var searchQuery: String? = "",
    var queryType: QueryType = QueryType.ALL
)

enum class LoadingType {
    SEARCH_LOADING,
    FRAGMENT_LOADING,
    STARTUP_LOADING,
    NONE
}

enum class QueryType {
    ALL,
    MOVIE,
    SERIES,
    EPISODE
}
