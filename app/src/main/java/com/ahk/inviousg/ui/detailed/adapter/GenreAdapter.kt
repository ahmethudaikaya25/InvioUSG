package com.ahk.inviousg.ui.detailed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahk.inviousg.databinding.GenreItemBinding

class GenreAdapter(
    var genres: List<String>
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val genreItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            com.ahk.inviousg.R.layout.genre_item,
            parent,
            false
        ) as GenreItemBinding

        return GenreViewHolder(genreItemBinding)
    }

    inner class GenreViewHolder(private val genreItemBinding: GenreItemBinding) :
        RecyclerView.ViewHolder(genreItemBinding.root) {

        fun bind(genre: String) {
            genreItemBinding.genreValue = genre
            genreItemBinding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }
}
