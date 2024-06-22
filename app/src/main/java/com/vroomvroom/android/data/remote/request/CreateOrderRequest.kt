package com.vroomvroom.android.data.remote.request

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
class CreateOrderRequest(
    @field:Json(name = "merchant")
    val merchant: String,
    @field:Json(name = "address")
    val address: AddressRequest,
    @field:Json(name = "products")
    val products: List<OrderProductRequest>,
    @field:Json(name = "deliveryFee")
    val deliveryFee: Float,
)

@Keep
@JsonClass(generateAdapter = true)
class OrderProductRequest(
    @field:Json(name = "product")
    val product: String,
    @field:Json(name = "options")
    val options: List<String>,
    @field:Json(name = "quantity")
    val quantity: Int,
    @field:Json(name = "price")
    val price: Float,
    @field:Json(name = "notes")
    val notes: String,
)