package com.captaindeer.bancoaztecachallenge.data.remote.responses

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: ArrayList<ItemMovieResponse>
)