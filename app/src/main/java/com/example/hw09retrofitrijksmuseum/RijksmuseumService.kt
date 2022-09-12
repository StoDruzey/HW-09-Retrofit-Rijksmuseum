package com.example.hw09retrofitrijksmuseum

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RijksmuseumService {

    private const val BASE_URL = "https://www.rijksmuseum.nl/"

    val api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create<RijksmuseumAPI>()
    }
}