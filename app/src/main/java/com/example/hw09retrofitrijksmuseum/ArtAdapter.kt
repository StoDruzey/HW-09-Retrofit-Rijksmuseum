package com.example.hw09retrofitrijksmuseum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw09retrofitrijksmuseum.databinding.ItemArtBinding
import com.example.hw09retrofitrijksmuseum.databinding.ItemLoadingBinding

class ArtAdapter(
    context: Context,
    private val onArtClicked: (Art) -> Unit
) : ListAdapter<PagingData<Art>, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PagingData.Item -> TYPE_ART
            PagingData.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ART -> {
                ArtViewHolder(
                    binding = ItemArtBinding.inflate(layoutInflater, parent, false),
                    onArtClicked = onArtClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Usupported viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PagingData.Item -> {
                checkNotNull(holder as ArtViewHolder) { "Incorrect viewHolder $item" }
                holder.bind(item.data)
            }
            PagingData.Loading -> {
                // no op
            }
        }
    }

    companion object {

        private const val TYPE_ART = 0
        private const val TYPE_LOADING = 0

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PagingData<Art>>() {
            override fun areItemsTheSame(
                oldItem: PagingData<Art>,
                newItem: PagingData<Art>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PagingData<Art>,
                newItem: PagingData<Art>
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ArtViewHolder(
    private val binding: ItemArtBinding,
    private val onArtClicked: (Art) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Art) {
        with(binding) {
            imageArt.load(item.webImage.url)
            artName.text = item.title

            root.setOnClickListener {
                onArtClicked(item)
            }
        }
    }
}

class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)



