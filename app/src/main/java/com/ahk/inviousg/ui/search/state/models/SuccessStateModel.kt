package com.ahk.inviousg.ui.search.state.models

import com.ahk.inviousg.data.model.dto.MovieSummaryDTO

data class SuccessStateModel(
    val searchResults: List<MovieSummaryDTO>
)
