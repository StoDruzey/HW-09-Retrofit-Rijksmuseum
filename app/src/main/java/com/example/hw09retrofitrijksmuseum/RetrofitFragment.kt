package com.example.hw09retrofitrijksmuseum

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hw09retrofitrijksmuseum.databinding.FragmentRetrofitBinding
import retrofit2.*

class RetrofitFragment : Fragment() {

    private var _binding: FragmentRetrofitBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        ArtAdapter(
            context = requireContext(),
            onArtClicked = {
                findNavController()
                    .navigate(RetrofitFragmentDirections
                        .actionRetrofitFragmentToDetailsFragment(it.longTitle, it.webImage.url))
            }
        )
    }

    private var artObjectRequest: Call<ArtObject>? = null

    private val currentArtList = mutableListOf<Art>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRetrofitBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            swipeRefresh.setOnRefreshListener {
                executeRequest {
                    swipeRefresh.isRefreshing = false
                }
            }

            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.bottom = INTERVAL_BETWEEN_ITEMS
                    }
                }
            )
        }
        executeRequest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        artObjectRequest?.cancel()
        _binding = null
    }

    private fun executeRequest(
        onRequestFinished: () -> Unit = {}
    ) {

        val finishRequest = {
            onRequestFinished()
            artObjectRequest = null
        }

        artObjectRequest?.cancel()
        artObjectRequest = RijksmuseumService.api
            .getArtObject("Rembrandt van Rijn", 100, "ZOavwPKX")
            .apply {
                enqueue(object : Callback<ArtObject> {
                    override fun onResponse(call: Call<ArtObject>, response: Response<ArtObject>) {
                        if (response.isSuccessful) {
                            val artList = response.body()?.artObjects ?: return
                            currentArtList.addAll(artList)
                            val items = artList.map { PagingData.Item(it) } + PagingData.Loading
                            adapter.submitList(items)
                        } else {
                            handleException(HttpException(response))
                        }
                        finishRequest()
                    }

                    override fun onFailure(call: Call<ArtObject>, t: Throwable) {
                        if (!call.isCanceled) {
                            handleException(t)
                        }
                        finishRequest()
                    }
                })
            }
    }

    private fun handleException(e: Throwable) {
        Toast.makeText(requireContext(), e.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
    }
    companion object {
        val INTERVAL_BETWEEN_ITEMS = 50
    }
}