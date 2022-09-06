package com.example.hw09retrofitrijksmuseum

import com.google.gson.annotations.SerializedName

data class Result(
    val artObjects: List<Art>
)

data class Art(
    val id: String,
    val longTitle: String,
    val webImage: webImage
)

data class webImage(
    val url: String
)


