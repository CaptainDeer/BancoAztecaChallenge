package com.captaindeer.bancoaztecachallenge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.captaindeer.bancoaztecachallenge.R
import com.captaindeer.bancoaztecachallenge.data.local.entities.MovieEntity
import com.captaindeer.bancoaztecachallenge.data.remote.responses.ItemMovieResponse
import com.captaindeer.bancoaztecachallenge.ui.adapters.listeners.MovieListener
import com.captaindeer.bancoaztecachallenge.ui.adapters.viewholders.MoviesViewHolder

class MoviesAdapter(private var moviesList: MutableList<MovieEntity>, private val listener: MovieListener) :
    RecyclerView.Adapter<MoviesViewHolder>() {

    fun updateData(moviesList: ArrayList<MovieEntity>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(
            layoutInflater.inflate(R.layout.item_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = moviesList[position]

        holder.bind(movie.title, movie.vote_average.toString(), movie.poster_path)

        holder.itemView.setOnClickListener {
            listener.showDialogItemMovies(movie)
        }
    }

    override fun getItemCount(): Int = moviesList.size
}