package com.ahk.inviousg.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahk.inviousg.data.model.dto.DetailedMovieDTO
import com.ahk.inviousg.data.model.dto.MovieSummaryDTO
import com.ahk.inviousg.domain.firebase.FetchRemoteConfigUseCase
import com.ahk.inviousg.domain.moviedb.AddRecentlyViewedUseCase
import com.ahk.inviousg.domain.moviedb.GetRecentlyViewedUseCase
import com.ahk.inviousg.domain.omdb.GetDetailsUseCase
import com.ahk.inviousg.ui.home.state.UIState
import com.ahk.inviousg.ui.home.state.models.SuccessStateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val getRecentlyViewedUseCase: GetRecentlyViewedUseCase,
    private val addRecentlyViewedUseCase: AddRecentlyViewedUseCase,
    private val fetchRemoteConfigUseCase: FetchRemoteConfigUseCase,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    companion object {
        private const val DEFAULT_IMDB_ID = "tt0120737"
    }

    private val mutableUIState = MutableLiveData<UIState>(UIState.Loading)
    val uiState = mutableUIState as LiveData<UIState>

    private fun receiveRecentlyViewed(): Single<List<MovieSummaryDTO>> =
        getRecentlyViewedUseCase.invoke()

    fun onListItemClicked(item: MovieSummaryDTO) {
        mutableUIState.postValue(UIState.Loading)
        val disposable = getDetails(item.imdbID)
            .subscribeOn(Schedulers.io())
            .flatMap {
                val date = Calendar.getInstance()
                return@flatMap addRecentlyViewedUseCase.invoke(item.mapToRecentlyViewed(date.toString()))
                    .subscribeOn(Schedulers.io())
                    .toSingleDefault(it)
            }
            .subscribe(
                { details ->
                    mutableUIState.postValue(
                        UIState.NavigateToDetailScreen(details),
                    )
                },
                { error ->
                    mutableUIState.postValue(UIState.Error(error, error.message))
                },
            )
        compositeDisposable.add(disposable)
    }

    fun startFragment() {
        mutableUIState.postValue(UIState.Loading)
        val disposable = startupCheck()
            .flatMap {
                receiveRecentlyViewed()
            }
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
        compositeDisposable.add(disposable)
    }

    private fun startupCheck() =
        fetchRemoteConfigUseCase.invoke()
            .toSingleDefault(Any())
            .flatMap { getDetailsUseCase.invoke(DEFAULT_IMDB_ID) }

    private fun getDetails(imdbID: String): Single<DetailedMovieDTO> {
        mutableUIState.postValue(UIState.Loading)
        return getDetailsUseCase.invoke(imdbID)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
