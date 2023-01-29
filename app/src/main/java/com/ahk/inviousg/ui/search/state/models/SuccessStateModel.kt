package com.ahk.inviousg.ui.search.state.models

import com.ahk.inviousg.data.model.MovieSummary

data class SuccessStateModel(
    val searchResults: List<MovieSummary>
)
