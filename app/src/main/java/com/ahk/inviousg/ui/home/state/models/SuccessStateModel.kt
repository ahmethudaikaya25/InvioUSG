package com.ahk.inviousg.ui.home.state.models

import com.ahk.inviousg.data.model.MovieSummary

data class SuccessStateModel(
    var movieList: List<MovieSummary> = emptyList()
)
