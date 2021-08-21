package com.captaindeer.bancoaztecachallenge.ui.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.captaindeer.bancoaztecachallenge.databinding.ItemImagesBinding

class ImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemImagesBinding.bind(view)

    fun bind(image: String) {
        Glide.with(itemView).load(image).into(binding.itemImageIv)
    }
}