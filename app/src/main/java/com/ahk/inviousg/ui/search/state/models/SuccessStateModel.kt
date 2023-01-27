package com.ahk.inviousg.ui.search.state.models

import android.app.appsearch.SearchResult

data class SuccessStateModel(
    val searchQuery: String,
    val searchResults: List<SearchResult>
)
