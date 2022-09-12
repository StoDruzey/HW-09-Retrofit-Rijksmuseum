package com.example.hw09retrofitrijksmuseum

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RijksmuseumAPI {

    @GET("api/en/collection")
    fun getArtObject(@Query("involvedMaker") involvedMaker: String,
                     @Query("ps") ps: Int,
                     @Query("key") key: String
    ): Call<ArtObject>
}