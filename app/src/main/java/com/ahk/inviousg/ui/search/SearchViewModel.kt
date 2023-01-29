package com.ahk.inviousg.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahk.inviousg.data.model.DetailedResponse
import com.ahk.inviousg.data.model.SummaryItem
import com.ahk.inviousg.domain.usecases.GetDetailsUseCase
import com.ahk.inviousg.domain.usecases.SearchUseCase
import com.ahk.inviousg.ui.search.state.UIState
import com.ahk.inviousg.ui.search.state.models.FormInformation
import com.ahk.inviousg.ui.search.state.models.LoadingType
import com.ahk.inviousg.ui.search.state.models.SuccessStateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val getDetailsUseCase: GetDetailsUseCase
) : ViewModel() {

    private val mutableUIState = MutableLiveData<UIState>(UIState.Idle)
    val uiState = mutableUIState as LiveData<UIState>

    private val mutableFormInformation = MutableLiveData(FormInformation())
    val formInformation = mutableFormInformation as LiveData<FormInformation>

    fun searchTextChanged() {
        mutableUIState.postValue(UIState.Loading(LoadingType.SEARCH_LOADING))
        searchUseCase.invoke(formInformation.value?.searchQuery ?: "")
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    mutableUIState.postValue(UIState.Success(SuccessStateModel(result)))
                },
                { error ->
                    mutableUIState.postValue(UIState.Error(error as Exception, error.message))
                }
            )
    }

    private fun getDetails(item: SummaryItem): Single<DetailedResponse> {
        mutableUIState.postValue(UIState.Loading(LoadingType.FRAGMENT_LOADING))
        return getDetailsUseCase.invoke(item.imdbID)
    }

    fun onListItemClicked(item: SummaryItem) {
        mutableUIState.postValue(UIState.Loading(LoadingType.FRAGMENT_LOADING))
        getDetails(item)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    mutableUIState.postValue(UIState.NavigateToDetailScreen(it, LoadingType.FRAGMENT_LOADING))
                },
                { error ->
                    mutableUIState.postValue(UIState.Error(error as Exception, error.message))
                }
            )
    }
}
