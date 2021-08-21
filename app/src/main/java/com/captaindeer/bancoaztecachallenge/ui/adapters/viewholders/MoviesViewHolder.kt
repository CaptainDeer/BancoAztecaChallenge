package com.captaindeer.bancoaztecachallenge.ui.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.captaindeer.bancoaztecachallenge.databinding.ItemMoviesBinding

class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemMoviesBinding.bind(view)

    fun bind(title: String, score: String, image: String) {
        Glide.with(itemView).load("https://image.tmdb.org/t/p/w500"+image).into(binding.itemMoviesIv)
        binding.itemMoviesTvTitle.text = title
        binding.itemMoviesTvScore.text = score
    }
}