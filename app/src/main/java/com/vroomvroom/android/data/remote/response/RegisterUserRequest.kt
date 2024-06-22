package com.vroomvroom.android.data.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
class RegisterUserRequest(
    @field:Json(name = "fcmToken")
    val token: String,
    @field:Json(name = "type")
    val userType: String,
)