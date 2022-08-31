package com.example.hw09retrofitrijksmuseum

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RijksmuseumAPI {

    @GET("api/en/collection")
    fun getArtObject(key: String, involvedMaker: String): Call<List<ArtObject>>
}