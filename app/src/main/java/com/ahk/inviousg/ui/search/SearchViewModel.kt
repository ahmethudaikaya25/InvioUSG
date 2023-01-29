package com.ahk.inviousg.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahk.inviousg.data.model.DetailedMovie
import com.ahk.inviousg.data.model.MovieSummary
import com.ahk.inviousg.domain.moviedb.AddRecentlyViewedUseCase
import com.ahk.inviousg.domain.omdb.GetDetailsUseCase
import com.ahk.inviousg.domain.omdb.SearchUseCase
import com.ahk.inviousg.ui.search.state.UIState
import com.ahk.inviousg.ui.search.state.models.FormInformation
import com.ahk.inviousg.ui.search.state.models.LoadingType
import com.ahk.inviousg.ui.search.state.models.QueryType
import com.ahk.inviousg.ui.search.state.models.SuccessStateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val addRecentlyViewedUseCase: AddRecentlyViewedUseCase
) : ViewModel() {

    private val mutableUIState = MutableLiveData<UIState>(UIState.Idle)
    val uiState = mutableUIState as LiveData<UIState>

    private val mutableFormInformation = MutableLiveData(FormInformation())
    val formInformation = mutableFormInformation as LiveData<FormInformation>

    fun searchTextChanged() = searchMovies()

    private fun searchMovies() {
        mutableUIState.postValue(UIState.Loading(LoadingType.SEARCH_LOADING))
        formInformation.value?.let {
            searchUseCase.invoke(formInformation.value?.searchQuery ?: "", it.queryType)
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    mutableUIState.postValue(UIState.Success(SuccessStateModel(result)))
                }, { error ->
                    mutableUIState.postValue(
                        UIState.Error(
                            exception = error,
                            message = error.message
                        )
                    )
                })
        }
    }

    private fun getDetails(item: MovieSummary): Single<DetailedMovie> {
        mutableUIState.postValue(UIState.Loading(LoadingType.FRAGMENT_LOADING))
        return getDetailsUseCase.invoke(item.imdbID)
    }

    fun onListItemClicked(item: MovieSummary) {
        mutableUIState.postValue(UIState.Loading(LoadingType.FRAGMENT_LOADING))
        getDetails(item)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { detailedMovie ->
                val date = Calendar.getInstance()
                addRecentlyViewedUseCase.invoke(item.mapToRecentlyViewed(date.toString()))
                    .subscribeOn(Schedulers.io())
                    .andThen {
                        mutableUIState.postValue(
                            UIState.NavigateToDetailScreen(
                                LoadingType.FRAGMENT_LOADING,
                                detailedMovie
                            )
                        )
                    }
                    .doOnError {
                        mutableUIState.postValue(
                            UIState.Error(
                                exception = it,
                                message = it.message
                            )
                        )
                    }
                    .subscribe()
            }.doOnError { error ->
                mutableUIState.postValue(UIState.Error(error, error.message))
            }
            .subscribe()
    }

    fun startupCheckup() {
        mutableUIState.postValue(UIState.Loading(LoadingType.STARTUP_LOADING))
        searchUseCase.invoke("search", QueryType.ALL)
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                mutableUIState.postValue(UIState.Idle)
            }, { error ->
                mutableUIState.postValue(UIState.Error(exception = error, message = error.message))
            })
    }

    fun onFilterChanged(queryType: QueryType) {
        val form = formInformation.value
        if (form != null) {
            form.queryType = queryType
            mutableFormInformation.postValue(form)
        }
        searchMovies()
    }
}
