package com.example.rickmorty.presentation.model

sealed class LceState<out T> {

    data class Content<T>(val value: T) : LceState<T>()

    data class Error(val throwable: Throwable) : LceState<Nothing>()
}