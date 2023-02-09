package com.ahk.inviousg.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahk.inviousg.data.model.dto.MovieSummaryDTO
import com.ahk.inviousg.databinding.SearchItemBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SummaryAdapter(
    var movieSummaries: List<MovieSummaryDTO>
) : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {

    private val onClick: PublishSubject<MovieSummaryDTO> = PublishSubject.create()
    val mutableOnClick: Observable<MovieSummaryDTO> = onClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val searchItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            com.ahk.inviousg.R.layout.search_item,
            parent,
            false
        ) as SearchItemBinding

        return SummaryViewHolder(searchItemBinding)
    }

    inner class SummaryViewHolder(private val searchItemBinding: SearchItemBinding) :
        RecyclerView.ViewHolder(searchItemBinding.root), View.OnClickListener {

        fun bind(movieSummary: MovieSummaryDTO) {
            searchItemBinding.summaryItem = movieSummary
            searchItemBinding.root.setOnClickListener(this)
            searchItemBinding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                movieSummaries[position].let {
                    onClick.onNext(it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movieSummaries.size
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.bind(movieSummaries[position])
    }

    fun setData(newData: List<MovieSummaryDTO>) {
        val diffUtil = SummaryDiffUtil(movieSummaries, newData)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        movieSummaries = newData
        diffResult.dispatchUpdatesTo(this)
    }
}
