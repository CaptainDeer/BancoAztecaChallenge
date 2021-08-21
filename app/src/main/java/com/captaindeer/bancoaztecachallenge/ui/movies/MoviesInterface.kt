package com.captaindeer.bancoaztecachallenge.ui.movies

import android.net.Uri
import com.captaindeer.bancoaztecachallenge.data.local.entities.MovieEntity
import com.captaindeer.bancoaztecachallenge.data.remote.responses.ItemMovieResponse
import com.captaindeer.bancoaztecachallenge.data.remote.responses.models.PostModel

interface MoviesInterface {

    interface Presenter {
        fun imagePost(filepath: Uri)
        fun getTopMovies()
        fun getUserImages()
    }

    interface View {
        fun onError(msg:String)
        fun setUpdateData(moviesList: MutableList<MovieEntity>)
        fun setUpdateDataPost(postModel: MutableList<PostModel>)
        fun showDialogItemMovies(movieEntity: MovieEntity)
    }
}