package com.vroomvroom.android.data.model.search

data class Search(
    val id: Int,
    val searchTerm: String,
    val fromLocal: Boolean,
    val createdAt: Long,
)
