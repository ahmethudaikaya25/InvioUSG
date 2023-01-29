package com.ahk.inviousg.ui.search.adapter

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahk.inviousg.R
import com.ahk.inviousg.data.model.DataExceptions
import com.ahk.inviousg.ui.search.state.UIState
import com.ahk.inviousg.ui.search.state.models.LoadingType
import com.ahk.inviousg.ui.search.state.models.QueryType
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textview.MaterialTextView

@BindingAdapter("visibility")
fun setLoadingVisibility(view: ProgressBar, uiState: UIState) {
    when (view) {
        is LinearProgressIndicator -> {
            if (uiState is UIState.Loading && uiState.loadingType == LoadingType.SEARCH_LOADING) {
                view.visibility = VISIBLE
            } else {
                view.visibility = GONE
            }
        }
        is CircularProgressIndicator -> {
            when (uiState) {
                is UIState.Loading -> {
                    view.visibility = provideVisibilityAccordingToLoadingType(uiState.loadingType)
                }
                is UIState.NavigateToDetailScreen -> {
                    view.visibility = provideVisibilityAccordingToLoadingType(uiState.loadingType)
                }
                else -> view.visibility = GONE
            }
        }
        else -> return
    }
}

private fun provideVisibilityAccordingToLoadingType(loadingType: LoadingType): Int {
    return when (loadingType) {
        LoadingType.FRAGMENT_LOADING, LoadingType.STARTUP_LOADING -> VISIBLE
        else -> GONE
    }
}

@BindingAdapter("visibility")
fun setRecyclerViewVisibility(view: RecyclerView, uiState: UIState) {
    when (uiState) {
        is UIState.Success -> view.visibility = VISIBLE
        else -> view.visibility = GONE
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

@BindingAdapter("errorVisibility")
fun setErrorVisibility(view: MaterialTextView, uiState: UIState) {
    when (uiState) {
        is UIState.Error -> {
            when (uiState.exception) {
                is DataExceptions.MovieNotFoundException, is DataExceptions.InternetNotAvailableException -> {
                    view.text = uiState.exception.message
                    view.visibility = VISIBLE
                }
                else -> {
                    view.text = view.context.getString(R.string.database_error)
                    view.visibility = VISIBLE
                }
            }
        }
        else -> view.visibility = GONE
    }
}

@BindingAdapter("state")
fun setSearchType(view: MaterialButton, queryType: QueryType) {
    when (view.id) {
        R.id.all -> {
            if (queryType == QueryType.ALL) {
                view.isEnabled = false
                view.setTextColor(
                    AppCompatResources.getColorStateList(
                        view.context,
                        R.color.md_theme_dark_onPrimary
                    )
                )
            } else {
                view.isEnabled = true
                view.setTextColor(
                    AppCompatResources.getColorStateList(
                        view.context,
                        R.color.md_theme_dark_secondaryContainer
                    )
                )
            }
        }
        R.id.movie -> {
            if (queryType == QueryType.MOVIE) {
                view.isCheckable = false
                view.setTextColor(
                    AppCompatResources.getColorStateList(
                        view.context,
                        R.color.md_theme_dark_onPrimary
                    )
                )
            } else {
                view.isCheckable = true
                view.setTextColor(
                    AppCompatResources.getColorStateList(
                        view.context,
                        R.color.md_theme_dark_secondaryContainer
                    )
                )
            }
        }
        R.id.series -> {
            if (queryType == QueryType.SERIES) {
                view.isCheckable = false
                view.setTextColor(
                    AppCompatResources.getColorStateList(
                        view.context,
                        R.color.md_theme_dark_onPrimary
                    )
                )
            } else {
                view.isCheckable = true
                view.setTextColor(
                    AppCompatResources.getColorStateList(
                        view.context,
                        R.color.md_theme_dark_secondaryContainer
                    )
                )
            }
        }
        R.id.episode -> {
            if (queryType == QueryType.EPISODE) {
                view.isCheckable = false
                view.setTextColor(
                    AppCompatResources.getColorStateList(
                        view.context,
                        R.color.md_theme_dark_onPrimary
                    )
                )
            } else {
                view.isCheckable = true
                view.setTextColor(
                    AppCompatResources.getColorStateList(
                        view.context,
                        R.color.md_theme_dark_secondaryContainer
                    )
                )
            }
        }
    }
}
