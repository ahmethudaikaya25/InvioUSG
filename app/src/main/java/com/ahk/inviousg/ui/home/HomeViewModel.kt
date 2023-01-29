package com.ahk.inviousg.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahk.inviousg.data.model.DetailedMovie
import com.ahk.inviousg.data.model.MovieSummary
import com.ahk.inviousg.domain.moviedb.AddRecentlyViewedUseCase
import com.ahk.inviousg.domain.moviedb.GetRecentlyViewedUseCase
import com.ahk.inviousg.domain.omdb.GetDetailsUseCase
import com.ahk.inviousg.ui.home.state.UIState
import com.ahk.inviousg.ui.home.state.models.SuccessStateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val getRecentlyViewedUseCase: GetRecentlyViewedUseCase,
    private val addRecentlyViewedUseCase: AddRecentlyViewedUseCase
) : ViewModel() {
    private val mutableUIState = MutableLiveData<UIState>(UIState.Idle)
    val uiState = mutableUIState as LiveData<UIState>

    fun receiveRecentlyViewed() {
        mutableUIState.postValue(UIState.Loading)
        getRecentlyViewedUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                mutableUIState.postValue(UIState.Success(SuccessStateModel(result)))
            }, { error ->
                mutableUIState.postValue(UIState.Error(exception = error, message = error.message))
            })
    }

    fun onListItemClicked(item: MovieSummary) {
        mutableUIState.postValue(UIState.Loading)

        getDetails(item)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { details ->
                val date = Calendar.getInstance()
                addRecentlyViewedUseCase.invoke(item.mapToRecentlyViewed(date.toString()))
                    .subscribeOn(Schedulers.io())
                    .andThen {
                        mutableUIState.postValue(
                            UIState.NavigateToDetailScreen(details)
                        )
                    }.doOnError { error ->
                        mutableUIState.postValue(UIState.Error(error, error.message))
                    }
                    .subscribe()
            }.doOnError { error ->
                mutableUIState.postValue(UIState.Error(error, error.message))
            }
            .subscribe()
    }

    private fun getDetails(item: MovieSummary): Single<DetailedMovie> {
        mutableUIState.postValue(UIState.Loading)
        return getDetailsUseCase.invoke(item.imdbID)
    }
}
