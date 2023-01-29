package com.ahk.inviousg.ui.search.adapter

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahk.inviousg.ui.search.state.UIState
import com.ahk.inviousg.ui.search.state.models.LoadingType
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator

@BindingAdapter("visibility")
fun setLoadingVisibility(view: ProgressBar, uiState: UIState) {
    when (view) {
        is LinearProgressIndicator -> {
            if (uiState is UIState.Loading && uiState.loadingType == LoadingType.SEARCH_LOADING) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
        is CircularProgressIndicator -> {
            if (uiState is UIState.Loading || uiState is UIState.NavigateToDetailScreen && uiState.loadingType == LoadingType.FRAGMENT_LOADING) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
        else -> return
    }
}

@BindingAdapter("visibility")
fun setRecyclerViewVisibility(view: RecyclerView, uiState: UIState) {
    when (uiState) {
        is UIState.Success -> view.visibility = ProgressBar.VISIBLE
        else -> view.visibility = ProgressBar.GONE
    }
}

@BindingAdapter("isEnable")
fun setEnabling(view: View, uiState: UIState) {
    when (uiState) {
        is UIState.NavigateToDetailScreen -> view.isEnabled = false
        is UIState.Loading -> {
            if (uiState.loadingType == LoadingType.FRAGMENT_LOADING) view.isEnabled = false
        }
        else -> view.isEnabled = true
    }
}
