package com.captaindeer.bancoaztecachallenge.ui.adapters.listeners

import com.captaindeer.bancoaztecachallenge.data.local.entities.MovieEntity

interface MovieListener {

    fun showDialogItemMovies(movieEntity: MovieEntity)
}