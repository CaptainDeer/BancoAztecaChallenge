package com.captaindeer.bancoaztecachallenge.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ItemMovieResponse(

    @SerializedName("id") val id: Int,
    @SerializedName("original_languaje") val original_languaje: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Float
)