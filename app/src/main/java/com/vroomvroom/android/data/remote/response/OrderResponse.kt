package com.vroomvroom.android.data.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
class OrderResponse(
    @field:Json(name = "_id")
    val id: String? = null,
    @field:Json(name = "customer")
    val customer: UserResponse? = null,
    @field:Json(name = "merchant")
    val merchant: MerchantResponse? = null,
    @field:Json(name = "address")
    val address: AddressResponse? = null,
    @field:Json(name = "products")
    val products: List<OrderProductResponse>? = null,
    @field:Json(name = "delivery_fee")
    val deliveryFee : Float? = null,
    @field:Json(name = "status")
    val status : Int? = null,
    @field:Json(name = "reviewed")
    val isReviewed : Boolean? = null,
    @field:Json(name = "created_at")
    val createdAt : String? = null,
)

@Keep
@JsonClass(generateAdapter = true)
class AddressResponse(
    @field:Json(name = "street")
    val street: String? = null,
    @field:Json(name = "barangay")
    val barangay: String? = null,
    @field:Json(name = "city")
    val city: String? = null,
    @field:Json(name = "additional_information")
    val additionalInfo: String? = null,
    @field:Json(name = "latitude")
    val latitude: Double,
    @field:Json(name = "longitude")
    val longitude: Double,
)

@Keep
@JsonClass(generateAdapter = true)
class OrderProductResponse(
    @field:Json(name = "quantity")
    val quantity: Int? = null,
    @field:Json(name = "price")
    val price: Float? = null,
    @field:Json(name = "product")
    val product: ProductResponse? = null,
    @field:Json(name = "options")
    val options: List<OptionResponse>? = null,
)