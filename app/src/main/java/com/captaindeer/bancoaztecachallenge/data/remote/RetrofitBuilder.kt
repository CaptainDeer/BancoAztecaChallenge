package com.captaindeer.bancoaztecachallenge.data.remote

import com.captaindeer.bancoaztecachallenge.data.remote.responses.MovieResponse
import com.captaindeer.bancoaztecachallenge.data.remote.services.MoviesApiService
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    private val client = OkHttpClient.Builder().build()
    private lateinit var moviesApiService: MoviesApiService

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/movie/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    suspend fun getTopMovies(): Response<MovieResponse>{
        moviesApiService = retrofit.create(MoviesApiService::class.java)
        return moviesApiService.getTopMovies()
    }



}