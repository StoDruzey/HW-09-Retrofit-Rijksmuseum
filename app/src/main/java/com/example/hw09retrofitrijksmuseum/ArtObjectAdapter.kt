package com.example.hw09retrofitrijksmuseum

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw09retrofitrijksmuseum.databinding.ItemArtobjectBinding

class ArtObjectAdapter : ListAdapter<ArtObject, ArtObjectViewHolder> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtObjectViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ArtObjectViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<> {
            
        }
    }
}

class ArtObjectViewHolder(
    private val binding: ItemArtobjectBinding
) :RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ArtObject) {
        with(binding) {
            imageArt.load(item.)
        }
    }
}



