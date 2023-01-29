package com.ahk.inviousg.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahk.inviousg.data.model.SummaryItem
import com.ahk.inviousg.databinding.SearchItemBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SummaryAdapter(
    var summaryItems: List<SummaryItem>
) : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {

    private val onClick: PublishSubject<SummaryItem> = PublishSubject.create()
    val mutableOnClick: Observable<SummaryItem> = onClick

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

        fun bind(summaryItem: SummaryItem) {
            searchItemBinding.summaryItem = summaryItem
            searchItemBinding.root.setOnClickListener(this)
            searchItemBinding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                summaryItems[position].let {
                    onClick.onNext(it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return summaryItems.size
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.bind(summaryItems[position])
    }

    fun setData(newData: List<SummaryItem>) {
        val diffUtil = SummaryDiffUtil(summaryItems, newData)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        summaryItems = newData
        diffResult.dispatchUpdatesTo(this)
    }
}
