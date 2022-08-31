package com.example.hw09retrofitrijksmuseum

import retrofit2.Call
import retrofit2.http.GET

interface RijksmuseumAPI {

    @GET("api/en/collection?key=ZOavwPKX&involvedMaker=Rembrandt+van+Rijn")
    fun getArtobject(): Call<List<ArtObject>>
}