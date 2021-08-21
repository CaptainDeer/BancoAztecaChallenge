package com.captaindeer.bancoaztecachallenge.ui.movies

import android.content.Context
import android.net.Uri
import android.util.Log
import com.captaindeer.bancoaztecachallenge.data.local.DatabaseBuilder
import com.captaindeer.bancoaztecachallenge.data.local.entities.MovieEntity
import com.captaindeer.bancoaztecachallenge.data.remote.RetrofitBuilder
import com.captaindeer.bancoaztecachallenge.data.remote.responses.ItemMovieResponse
import com.captaindeer.bancoaztecachallenge.data.remote.responses.models.PostModel
import com.captaindeer.bancoaztecachallenge.utils.OnLine
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MoviesPresenter(private val view: MoviesInterface.View, private val context: Context) :
    MoviesInterface.Presenter {
    private var retrofit = RetrofitBuilder()
    private var storage: FirebaseStorage? = null
    private var firestore: FirebaseFirestore? = null
    private val database = DatabaseBuilder(context)

    override fun getTopMovies() {
        if (OnLine.isNetworkAvailable(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                val moviesListResponse = retrofit.getTopMovies()
                if (moviesListResponse.isSuccessful) {
                    moviesListResponse.body()!!.results.forEach {
                        database.MoviesDao().insert(
                            MovieEntity(
                                it.id,
                                it.original_languaje.toString(),
                                it.overview,
                                it.poster_path,
                                it.title,
                                it.vote_average
                            )
                        )
                    }
                    withContext(Dispatchers.Main) {
                        view.setUpdateData(database.MoviesDao().getTopMovies())
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        view.setUpdateData(database.MoviesDao().getTopMovies())
                        view.onError(moviesListResponse.errorBody().toString())
                    }
                }
            }
        } else {
            view.setUpdateData(database.MoviesDao().getTopMovies())
            view.onError("Not internet connection")
        }
    }

    override fun getUserImages() {
        firestore = FirebaseFirestore.getInstance()
        firestore!!.collection("MediaUrls").get().addOnSuccessListener {
            if (!it.isEmpty) {
                val itemMovieList = mutableListOf<PostModel>()
                val itemMoviesList = it.toObjects(PostModel::class.java)
                itemMovieList.addAll(itemMoviesList)
                Log.e("TAG","Cantidad leida: "+itemMoviesList.toString())
                view.setUpdateDataPost(itemMovieList)
            } else {
                return@addOnSuccessListener
            }
        }

    }

    override fun imagePost(filepath: Uri) {
        storage = FirebaseStorage.getInstance()
        val storageReference = storage!!.reference
        val ref: StorageReference = storageReference.child("Media/" + UUID.randomUUID().toString())
        ref.putFile(filepath).addOnCompleteListener {
            if (it.isSuccessful) {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val post = hashMapOf(
                        "uri" to uri
                    )
                    firestore = FirebaseFirestore.getInstance()
                    firestore!!.collection("MediaUrls/").document(UUID.randomUUID().toString())
                        .set(PostModel(uri.toString()))
                        .addOnCompleteListener {
                            //View de que si se armo
                        }
                        .addOnFailureListener {
                            //View de que no sirvio
                        }
                }
            }
        }
    }
}