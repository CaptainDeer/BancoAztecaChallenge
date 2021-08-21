package com.captaindeer.bancoaztecachallenge.data.remote.services

import com.captaindeer.bancoaztecachallenge.data.remote.responses.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MoviesApiService {

    @GET(value = "top_rated?api_key=e2a5d3113100bf1260b4f91c5a503cc9")
    suspend fun getTopMovies(): Response<MovieResponse>
}