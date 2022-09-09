package com.example.hw09retrofitrijksmuseum

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.example.hw09retrofitrijksmuseum.databinding.FragmentRetrofitBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFragment : Fragment() {

    private var _binding: FragmentRetrofitBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy { ArtAdapter(requireContext()) }

    private var artObjectRequest: Call<ArtObject>? = null

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
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL))
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.rijksmuseum.nl/")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
            .build()

        val rijksmuseumApi = retrofit.create<RijksmuseumAPI>()

        artObjectRequest = rijksmuseumApi
            .getArtObject("Rembrandt van Rijn", 100, "ZOavwPKX")
            .apply {
                enqueue(object : Callback<ArtObject> {
                    override fun onResponse(call: Call<ArtObject>, response: Response<ArtObject>) {
                        if (response.isSuccessful) {
                            val artList = response.body()?.artObjects ?: return
                            adapter.submitList(artList)
                        } else {
                            handleException(HttpException(response))
                        }
                    }

                    override fun onFailure(call: Call<ArtObject>, t: Throwable) {
                        if (!call.isCanceled) {
                            handleException(t)
                        }
                    }
                })
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        artObjectRequest?.cancel()
        _binding = null
    }

    private fun handleException(e: Throwable) {
        Toast.makeText(requireContext(), e.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
    }
}

//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val newRequest = chain.request().newBuilder()
//                    .addHeader("key", "ZOavwPKX")
//                    .build()
//                chain.proceed(newRequest)
//            }
//            .build()