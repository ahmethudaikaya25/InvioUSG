package com.ahk.inviousg.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ahk.inviousg.data.model.dto.MovieSummaryDTO

class SummaryDiffUtil(
    private val oldList: List<MovieSummaryDTO>,
    private val newList: List<MovieSummaryDTO>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].imdbID == newList[newItemPosition].imdbID

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.imdbID == newItem.imdbID &&
            oldItem.title == newItem.title &&
            oldItem.year == newItem.year &&
            oldItem.poster == newItem.poster
    }
}
