package com.captaindeer.bancoaztecachallenge.ui.itemmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.captaindeer.bancoaztecachallenge.data.local.entities.MovieEntity
import com.captaindeer.bancoaztecachallenge.databinding.FragmentItemMovieBinding

class ItemMovieFragment(private val itemMovieModel: MovieEntity) : DialogFragment() {

    private var _binding: FragmentItemMovieBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemMovieBinding.inflate(inflater, container, false)

        binding!!.itemMovieId.text = "Id: "+ itemMovieModel.id.toString()
        binding!!.itemMovieTvTitle.text ="Title: "+ itemMovieModel.title
        binding!!.itemMovieTvVote.text = "Vote average: "+itemMovieModel.vote_average.toString()
        binding!!.itemMovieTvOverview.text = "Overview: " +itemMovieModel.overview
        Glide.with(requireActivity())
            .load("https://image.tmdb.org/t/p/w500" + itemMovieModel.poster_path)
            .into(binding!!.itemMovieIv)

        binding!!.itemMovieBtn.setOnClickListener {
            dismiss()
        }
        return binding?.root
    }
}
