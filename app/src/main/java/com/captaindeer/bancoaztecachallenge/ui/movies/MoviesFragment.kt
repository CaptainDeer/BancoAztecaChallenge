package com.captaindeer.bancoaztecachallenge.ui.movies

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.captaindeer.bancoaztecachallenge.data.local.entities.MovieEntity
import com.captaindeer.bancoaztecachallenge.data.remote.responses.ItemMovieResponse
import com.captaindeer.bancoaztecachallenge.data.remote.responses.models.PostModel
import com.captaindeer.bancoaztecachallenge.databinding.FragmentMoviesBinding
import com.captaindeer.bancoaztecachallenge.ui.adapters.ImagesAdapter
import com.captaindeer.bancoaztecachallenge.ui.adapters.MoviesAdapter
import com.captaindeer.bancoaztecachallenge.ui.adapters.listeners.MovieListener
import com.captaindeer.bancoaztecachallenge.ui.itemmovie.ItemMovieFragment
import java.io.IOException

class MoviesFragment : Fragment(), MoviesInterface.View, MovieListener {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding
    private var presenter: MoviesPresenter ?= null
    private var adapter : MoviesAdapter ?= null
    private var adapterImages: ImagesAdapter ?= null
    private var filepath: Uri? = null
    private val moviesList = mutableListOf<MovieEntity>()
    private val postModel = mutableListOf<PostModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MoviesPresenter(this,requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Adapter
        adapter = MoviesAdapter(moviesList,this)
        adapterImages = ImagesAdapter(postModel)
        //RecyclerView
        binding!!.moviesRv.setHasFixedSize(true)
        binding!!.moviesRv.layoutManager = GridLayoutManager(requireContext(),1, GridLayoutManager.HORIZONTAL,false)
        binding!!.moviesRv.adapter = adapter

        binding!!.moviesRvImages.setHasFixedSize(true)
        binding!!.moviesRvImages.layoutManager = GridLayoutManager(requireContext(),1, GridLayoutManager.HORIZONTAL,false)
        binding!!.moviesRvImages.adapter = adapterImages

        //Init
        presenter!!.getTopMovies()
        presenter!!.getUserImages()

        //Click
        binding!!.moviesFabBtn.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                22
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 22 && resultCode == Activity.RESULT_OK && data != null) {
            filepath = data.data
            try {
                val bitMap: Bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, filepath)
                presenter!!.imagePost(filepath!!)
            } catch (e: IOException) {
                Log.e("TAG", e.printStackTrace().toString())
            }
        }
    }

    override fun onError(msg: String) {
        Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show()
    }

    override fun setUpdateData(moviesList: MutableList<MovieEntity>) {
        adapter!!.updateData(moviesList as ArrayList)
    }

    override fun setUpdateDataPost(postModel: MutableList<PostModel>) {
        adapterImages!!.updateData(postModel)
    }

    override fun showDialogItemMovies(movieEntity: MovieEntity) {
        ItemMovieFragment(movieEntity).show(requireFragmentManager(), "customDialog")

    }


}