package com.ahk.inviousg.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahk.inviousg.data.model.dto.DetailedMovieDTO
import com.ahk.inviousg.data.model.dto.MovieSummaryDTO
import com.ahk.inviousg.domain.firebase.FetchRemoteConfigUseCase
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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val addRecentlyViewedUseCase: AddRecentlyViewedUseCase,
    private val fetchRemoteConfigUseCase: FetchRemoteConfigUseCase,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val mutableUIState =
        MutableLiveData<UIState>(UIState.Loading(LoadingType.SEARCH_LOADING))
    val uiState = mutableUIState as LiveData<UIState>

    private val mutableFormInformation = MutableLiveData(FormInformation())
    val formInformation = mutableFormInformation as LiveData<FormInformation>

    fun searchTextChanged() = searchMovies()

    private fun searchMovies() {
        lateinit var disposable: Disposable
        mutableUIState.postValue(UIState.Loading(LoadingType.SEARCH_LOADING))
        formInformation.value?.let {
            disposable =
                searchUseCase.invoke(formInformation.value?.searchQuery ?: "", it.queryType)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { result ->
                            mutableUIState.postValue(UIState.Success(SuccessStateModel(result)))
                        },
                        { error ->
                            mutableUIState.postValue(
                                UIState.Error(
                                    exception = error,
                                    message = error.message,
                                ),
                            )
                        },
                    )
        }
        disposable.let { compositeDisposable.add(it) }
    }

    private fun getDetails(item: MovieSummaryDTO): Single<DetailedMovieDTO> {
        mutableUIState.postValue(UIState.Loading(LoadingType.FRAGMENT_LOADING))
        return getDetailsUseCase.invoke(item.imdbID)
    }

    fun onListItemClicked(item: MovieSummaryDTO) {
        mutableUIState.postValue(UIState.Loading(LoadingType.FRAGMENT_LOADING))

        val disposable = getDetails(item)
            .subscribeOn(Schedulers.io())
            .flatMap { detailedMovie ->
                val date = Calendar.getInstance()
                return@flatMap addRecentlyViewedUseCase.invoke(item.mapToRecentlyViewed(date.toString()))
                    .andThen(Single.just(detailedMovie))
            }
            .subscribe(
                { detailedMovie ->
                    mutableUIState.postValue(
                        UIState.NavigateToDetailScreen(
                            LoadingType.FRAGMENT_LOADING,
                            detailedMovie,
                        ),
                    )
                },
                { error ->
                    mutableUIState.postValue(UIState.Error(error, error.message))
                },
            )
        compositeDisposable.add(disposable)
    }

    fun startupCheckup() {
        mutableUIState.postValue(UIState.Loading(LoadingType.STARTUP_LOADING))
        val disposable = fetchRemoteConfigUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .toSingleDefault(Any())
            .flatMap { searchUseCase.invoke("search", QueryType.ALL) }
            .subscribe(
                {
                    mutableUIState.postValue(UIState.Idle)
                },
                { error ->
                    mutableUIState.postValue(
                        UIState.Error(
                            exception = error,
                            message = error.message,
                        ),
                    )
                },
            )
        compositeDisposable.add(disposable)
    }

    fun onFilterChanged(queryType: QueryType) {
        val form = formInformation.value
        if (form != null) {
            form.queryType = queryType
            mutableFormInformation.postValue(form)
        }
        searchMovies()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
