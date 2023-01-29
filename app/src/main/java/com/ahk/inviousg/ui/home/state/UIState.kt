package com.ahk.inviousg.ui.home.state

import com.ahk.inviousg.data.model.DetailedMovie
import com.ahk.inviousg.ui.home.state.models.SuccessStateModel

sealed class UIState {
    object Idle : UIState()
    object Loading : UIState()
    data class Success(val movieList: SuccessStateModel) : UIState()
    data class Error(val exception: Throwable?, val message: String?) : UIState()
    data class NavigateToDetailScreen(val detailedMovie: DetailedMovie) : UIState()
}
