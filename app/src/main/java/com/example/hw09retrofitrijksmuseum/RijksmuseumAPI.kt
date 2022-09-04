package com.example.hw09retrofitrijksmuseum

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RijksmuseumAPI {

    @GET("api/en/collection?involvedMaker=Rembrandt+van+Rijn&key=ZOavwPKX")
    fun getArtObject(): Call<List<ArtObject>>
//@GET("users")
//fun getArtObject(@Query("since") since: Int, @Query("per_page") perPage: Int): Call<List<ArtObject>>
    //@Query("involvedMaker") involvedMaker: String, @Query("key") key: String
}