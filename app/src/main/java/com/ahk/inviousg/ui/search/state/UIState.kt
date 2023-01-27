package com.ahk.inviousg.ui.search.state

import com.ahk.inviousg.ui.search.state.models.ErrorStateModel
import com.ahk.inviousg.ui.search.state.models.SuccessStateModel

sealed class UIState {
    object Loading : UIState()
    data class Success(val successStateModel: SuccessStateModel) : UIState()
    data class Error(val errorStateModel: ErrorStateModel) : UIState()
}
