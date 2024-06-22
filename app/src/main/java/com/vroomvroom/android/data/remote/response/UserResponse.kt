package com.vroomvroom.android.data.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
class UserResponse(
    @field:Json(name = "_id")
    val id: String? = null,
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "email")
    val email: String? = null,
    @field:Json(name = "phone")
    val phone: PhoneResponse? = null
)

@Keep
@JsonClass(generateAdapter = true)
class PhoneResponse(
    @field:Json(name = "number")
    val number: String? = null,
    @field:Json(name = "verified")
    val verified: Boolean? = null
)
