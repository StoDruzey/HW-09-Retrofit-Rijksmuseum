package com.example.hw09retrofitrijksmuseum

import com.google.gson.annotations.SerializedName

data class ArtObject(
    val artObjects: List<Art>
)

data class Art(
    val id: String,
    val title: String,
    val longTitle: String,
    val webImage: WebImage
)

data class WebImage(
    val url: String
)


