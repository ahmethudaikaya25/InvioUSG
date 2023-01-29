package com.ahk.inviousg.ui.search.state

import com.ahk.inviousg.data.model.DetailedResponse
import com.ahk.inviousg.ui.search.state.models.LoadingType
import com.ahk.inviousg.ui.search.state.models.SuccessStateModel

sealed class UIState {
    object Idle : UIState()
    data class Loading(val loadingType: LoadingType) : UIState()
    data class Success(val successStateModel: SuccessStateModel) : UIState()
    data class Error(val exception: Exception?, val message: String?) : UIState()
    data class NavigateToDetailScreen(val detailedResponse: DetailedResponse, val loadingType: LoadingType) : UIState()
}
