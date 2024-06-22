package com.vroomvroom.android.data.remote.request

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
class AddressRequest(
    @field:Json(name = "street")
    val street: String,
    @field:Json(name = "barangay")
    val barangay: String,
    @field:Json(name = "city")
    val city: String,
    @field:Json(name = "additionalInformation")
    val additionalInfo: String,
    @field:Json(name = "latitude")
    val latitude: Double,
    @field:Json(name = "longitude")
    val longitude: Double,
)
