package com.vroomvroom.android.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    val data: T
)