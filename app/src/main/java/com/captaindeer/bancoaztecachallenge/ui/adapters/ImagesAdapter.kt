package com.captaindeer.bancoaztecachallenge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.captaindeer.bancoaztecachallenge.R
import com.captaindeer.bancoaztecachallenge.data.remote.responses.models.PostModel
import com.captaindeer.bancoaztecachallenge.ui.adapters.viewholders.ImagesViewHolder

class ImagesAdapter(private var postModel: MutableList<PostModel>) :
    RecyclerView.Adapter<ImagesViewHolder>() {

    fun updateData(postModel: MutableList<PostModel>) {
        this.postModel = postModel
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ImagesViewHolder(
            layoutInflater.inflate(R.layout.item_images, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val image = postModel[position]

        holder.bind(image.image)

    }

    override fun getItemCount(): Int = postModel.size
}