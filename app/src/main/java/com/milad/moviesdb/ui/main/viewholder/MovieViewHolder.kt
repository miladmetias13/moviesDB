package com.milad.moviesdb.ui.main.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.milad.moviesdb.R
import com.milad.moviesdb.databinding.ItemMovieBinding
import com.milad.moviesdb.model.entity.Summary
import com.milad.moviesdb.ui.main.adapter.MovieListAdapter

class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(summary: Summary, onItemClicked: (Summary, View) -> Unit) {
        binding.movieTitle.text = summary.title
        binding.overview.text = summary.overview
        binding.movieImage.load(summary.getPoster()) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }

        binding.root.setOnClickListener {
            onItemClicked(summary, binding.root)
        }
    }
}
