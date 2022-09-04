package com.example.hw09retrofitrijksmuseum

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.example.hw09retrofitrijksmuseum.databinding.FragmentFirstBinding
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class FragmentFirst : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = requireNotNull(_binding)

//    private var currentRequest: Call<List<ArtObject>>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFirstBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("key", "ZOavwPKX")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.rijksmuseum.nl/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val rijksmuseumApi = retrofit.create<RijksmuseumAPI>()

        rijksmuseumApi
            .getArtObject("Rembrandt+van+Rijn")
            .enqueue(object : Callback<List<ArtObject>> {
                override fun onResponse(call: Call<List<ArtObject>>, response: Response<List<ArtObject>>) {
                    println()
                }

                override fun onFailure(call: Call<List<ArtObject>>, t: Throwable) {
                    println()
                }
            })

//        currentRequest = rijksmuseumApi
//            .getArtObject()
//            .apply {
//                enqueue(object : Callback<List<ArtObject>> {
//                    override fun onResponse(call: Call<List<ArtObject>>, response: Response<List<ArtObject>>) {
//                        if (response.isSuccessful) {
//                            val artObject = response.body() ?: return
//                            binding.imageView.load(artObject[1].url)
//                        } else {
//                            handleException(HttpException(response))
//                        }
//                    }
//
//                    override fun onFailure(call: Call<List<ArtObject>>, t: Throwable) {
//                        if (!call.isCanceled) {
//                            handleException(t)
//                        }
//                    }
//                })
//            }

        with(binding) {
            button.setOnClickListener {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        currentRequest?.cancel()
        _binding = null
    }

    private fun handleException(t: Throwable) {

    }
}