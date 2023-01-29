package com.ahk.inviousg.ui.home.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.ahk.inviousg.R
import com.ahk.inviousg.data.model.DataExceptions
import com.ahk.inviousg.ui.home.state.UIState
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView

@BindingAdapter("loadingVisibility")
fun setVisibility(view: CircularProgressIndicator, uiState: UIState) {
    when (uiState) {
        is UIState.Loading -> {
            view.visibility = View.VISIBLE
        }
        else -> {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("homeErrorVisibility")
fun setErrorVisibility(view: MaterialTextView, uiState: UIState) {
    when (uiState) {
        is UIState.Error -> {
            when (uiState.exception) {
                is DataExceptions.MovieNotFoundException -> {
                    view.text = uiState.exception.message
                    view.visibility = View.VISIBLE
                }
                else -> {
                    view.text = view.context.getString(R.string.database_error)
                    view.visibility = View.VISIBLE
                }
            }
        }
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("recentlyViewedTitleVisibility")
fun setRecentlyViewedTitleVisibility(view: MaterialTextView, uiState: UIState) {
    when (uiState) {
        is UIState.Error -> {
            view.visibility = View.GONE
        }
        else -> view.visibility = View.VISIBLE
    }
}
