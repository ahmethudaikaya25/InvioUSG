package com.ahk.inviousg.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahk.inviousg.data.model.dto.DetailedMovieDTO
import com.ahk.inviousg.data.model.dto.MovieSummaryDTO
import com.ahk.inviousg.databinding.FragmentHomeBinding
import com.ahk.inviousg.ui.home.state.UIState
import com.ahk.inviousg.ui.search.adapter.SummaryAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: HomeViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recentViewed.adapter = SummaryAdapter(emptyList()).apply {
            val disposable = mutableOnClick.subscribe(viewModel::onListItemClicked)
            compositeDisposable.add(disposable)
        }
        viewModel.startFragment()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner, ::onStateChange)
    }

    private fun onStateChange(uiState: UIState) {
        when (uiState) {
            is UIState.Success -> {
                onSearchResultReceived(uiState.movieList.movieList)
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
            (binding.recentViewed.adapter as SummaryAdapter).setData(it)
        }
    }

    private fun onNavigateToDetailScreen(detailedMovie: DetailedMovieDTO) {
        findNavController().navigate(
            HomeDirections.actionNavigationHomeToNavigationDetailed(
                detailedMovie,
            ),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}
