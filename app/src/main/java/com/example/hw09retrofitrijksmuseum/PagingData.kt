package com.example.hw09retrofitrijksmuseum

sealed class PagingData<out T> {
    data class Item<T>(val data: T) : PagingData<T>()

    object Loading : PagingData<Nothing>()
}