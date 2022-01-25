package com.milad.moviesdb.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.milad.moviesdb.databinding.ItemGenreBinding
import com.milad.moviesdb.databinding.ItemMovieBinding
import com.milad.moviesdb.model.entity.Summary
import com.milad.moviesdb.ui.main.viewholder.GenreViewHolder
import com.milad.moviesdb.ui.main.viewholder.MovieViewHolder

class GenreListAdapter(
    private val onItemClicked: (Summary, View) -> Unit
) : ListAdapter<MutableMap.MutableEntry<String,List<Summary>>, GenreViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenreViewHolder(
        ItemGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        holder.bind(getItem(position)!!, onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MutableMap.MutableEntry<String,List<Summary>>>() {
            override fun areItemsTheSame(oldItem: MutableMap.MutableEntry<String,List<Summary>>, newItem: MutableMap.MutableEntry<String,List<Summary>>): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(oldItem: MutableMap.MutableEntry<String,List<Summary>>, newItem: MutableMap.MutableEntry<String,List<Summary>>): Boolean =
                oldItem.key == newItem.key
        }
    }
}
