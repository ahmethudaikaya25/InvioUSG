package com.ahk.inviousg.ui.search.state

import com.ahk.inviousg.ui.search.Search

sealed class UIState {
    object Loading : UIState()
    data class Success(val result: List<Search>) : UIState()
    data class Error(val message: String, val exception: Exception) : UIState()
}
