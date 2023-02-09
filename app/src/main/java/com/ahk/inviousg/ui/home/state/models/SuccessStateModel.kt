package com.ahk.inviousg.ui.home.state.models

import com.ahk.inviousg.data.model.dto.MovieSummaryDTO

data class SuccessStateModel(
    var movieList: List<MovieSummaryDTO> = emptyList()
)
