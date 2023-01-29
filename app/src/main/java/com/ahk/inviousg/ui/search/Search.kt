package com.ahk.inviousg.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ahk.inviousg.data.model.DetailedResponse
import com.ahk.inviousg.data.model.SummaryItem
import com.ahk.inviousg.databinding.FragmentSearchBinding
import com.ahk.inviousg.ui.search.adapter.SummaryAdapter
import com.ahk.inviousg.ui.search.state.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Search : Fragment() {
    lateinit var binding: FragmentSearchBinding
    val viewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.searchResults.adapter = SummaryAdapter(emptyList()).apply {
            mutableOnClick.subscribe(
                viewModel::onListItemClicked
            )
        }
        binding.searchResults.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner, ::onStateChange)
    }

    private fun onStateChange(uiState: UIState) {
        when (uiState) {
            is UIState.Idle -> {
                onIdleState()
            }
            is UIState.Success -> {
                onSearchResultReceived(uiState.successStateModel.searchResults)
            }
            is UIState.Error -> {
                // show error state
            }
            is UIState.NavigateToDetailScreen -> {
                onNavigateToDetailScreen(uiState.detailedResponse)
            }
            else -> {
            }
        }
    }

    private fun onIdleState() {
    }

    private fun onSearchResultReceived(summaryItems: List<SummaryItem>) {
        summaryItems.let {
            (binding.searchResults.adapter as SummaryAdapter).setData(it)
        }
    }

    private fun onNavigateToDetailScreen(detailedResponse: DetailedResponse) {
        findNavController().navigate(
            SearchDirections.actionNavigationSearchToNavigationDetailed(
                detailedResponse
            )
        )
    }
}
