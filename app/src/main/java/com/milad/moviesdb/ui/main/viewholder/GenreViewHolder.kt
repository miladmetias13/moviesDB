package com.milad.moviesdb.ui.main.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.milad.moviesdb.R
import com.milad.moviesdb.databinding.ItemGenreBinding
import com.milad.moviesdb.databinding.ItemMovieBinding
import com.milad.moviesdb.model.entity.Summary
import com.milad.moviesdb.ui.main.adapter.MovieListAdapter
import androidx.recyclerview.widget.LinearLayoutManager

class GenreViewHolder(private val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: MutableMap.MutableEntry<String,List<Summary>> , onItemClicked: (Summary, View) -> Unit) {
        val mAdapter = MovieListAdapter(onItemClicked)
        binding.genreTitle.text = genre.key
        binding.rvMovies.adapter = mAdapter
        mAdapter.submitList(genre.value)
    }
}
