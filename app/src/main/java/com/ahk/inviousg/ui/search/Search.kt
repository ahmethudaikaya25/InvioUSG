package com.ahk.inviousg.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ahk.inviousg.data.model.dto.DetailedMovieDTO
import com.ahk.inviousg.data.model.dto.MovieSummaryDTO
import com.ahk.inviousg.databinding.FragmentSearchBinding
import com.ahk.inviousg.ui.search.adapter.SummaryAdapter
import com.ahk.inviousg.ui.search.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class Search : Fragment() {
    lateinit var binding: FragmentSearchBinding
    val viewModel: SearchViewModel by viewModels()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.searchResults.adapter = SummaryAdapter(emptyList()).apply {
            val disposable = mutableOnClick.subscribe(
                viewModel::onListItemClicked,
            )
            compositeDisposable.add(disposable)
        }
        binding.searchResults.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.startupCheckup()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner, ::onStateChange)
    }

    private fun onStateChange(uiState: UIState) {
        when (uiState) {
            is UIState.Success -> {
                onSearchResultReceived(uiState.successStateModel.searchResults)
            }
            is UIState.NavigateToDetailScreen -> {
                onNavigateToDetailScreen(uiState.detailedMovie)
            }
            else -> {
                println("Unhandled state: $uiState")
            }
        }
    }

    private fun onSearchResultReceived(movieSummaries: List<MovieSummaryDTO>) {
        movieSummaries.let {
            (binding.searchResults.adapter as SummaryAdapter).setData(it)
        }
    }

    private fun onNavigateToDetailScreen(detailedMovie: DetailedMovieDTO) {
        findNavController().navigate(
            SearchDirections.actionNavigationSearchToNavigationDetailed(
                detailedMovie,
            ),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}
