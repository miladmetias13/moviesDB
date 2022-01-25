package com.milad.moviesdb.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.milad.moviesdb.databinding.ItemMovieBinding
import com.milad.moviesdb.model.entity.Summary
import com.milad.moviesdb.ui.main.viewholder.MovieViewHolder

class MovieListAdapter(
    private val onItemClicked: (Summary, View) -> Unit
) : ListAdapter<Summary, MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Summary>() {
            override fun areItemsTheSame(oldItem: Summary, newItem: Summary): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Summary, newItem: Summary): Boolean =
                oldItem.id == newItem.id
        }
    }
}
