package com.example.hw09retrofitrijksmuseum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw09retrofitrijksmuseum.databinding.ItemArtBinding

class ArtAdapter(
    context: Context
) : ListAdapter<Art, ArtViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        return ArtViewHolder(
            binding = ItemArtBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Art>() {
            override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ArtViewHolder(
    private val binding: ItemArtBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Art) {
        with(binding) {
            imageArt.load(item.webImage.url)
            artName.text = item.title
        }
    }
}



