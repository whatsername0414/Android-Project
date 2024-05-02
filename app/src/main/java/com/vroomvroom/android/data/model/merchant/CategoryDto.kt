package com.vroomvroom.android.data.model.merchant

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("type")
    val type: String
)
