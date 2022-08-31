package com.example.hw09retrofitrijksmuseum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hw09retrofitrijksmuseum.databinding.FragmentFirstBinding
import com.google.gson.Gson
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class FragmentFirst : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = requireNotNull(_binding)

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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.rijksmuseum.nl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rijksmuseumAPI = retrofit.create<RijksmuseumAPI>()

        val request = rijksmuseumAPI.getArtObject("ZOavwPKX", "Rembrandt+van+Rijn")
        request.enqueue(object : Callback<List<ArtObject>>{
            override fun onResponse(call: Call<List<ArtObject>>, response: Response<List<ArtObject>>) {
                if (response.isSuccessful) {

                } else {
                    handleException(HttpException(response))
                }
            }

            override fun onFailure(call: Call<List<ArtObject>>, t: Throwable) {
                if (!call.isCanceled) {
                    handleException(t)
                }
            }
        })
        request.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleException(e: Throwable) {

    }
}