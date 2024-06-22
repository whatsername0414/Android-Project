package com.vroomvroom.android.data.remote.response


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
class MerchantResponse(
    @field:Json(name = "closing")
    val closing: Int?,
    @field:Json(name = "created_at")
    val createdAt: String?,
    @field:Json(name = "_id")
    val id: String?,
    @field:Json(name = "image")
    val image: String?,
    @field:Json(name = "location")
    val location: List<Double>?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "opening")
    val opening: Int?,
    @field:Json(name = "is_open")
    val isOpen: Boolean?,
    @field:Json(name = "is_favorite")
    val isFavorite: Boolean?,
    @field:Json(name = "reviews")
    val reviews: List<ReviewResponse>?,
    @field:Json(name = "product_sections")
    val productSections: List<ProductSectionResponse>?,
)
@Keep
@JsonClass(generateAdapter = true)
class ProductSectionResponse(
    @field:Json(name = "created_at")
    val createdAt: String?,
    @field:Json(name = "_id")
    val id: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "products")
    val products: List<ProductResponse>?,
)

@Keep
@JsonClass(generateAdapter = true)
class ProductResponse(
    @field:Json(name = "created_at")
    val createdAt: String?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "_id")
    val id: String?,
    @field:Json(name = "image")
    val image: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "option_sections")
    val optionSections: List<OptionSectionResponse>?,
    @field:Json(name = "price")
    val price: Float?,
)

@Keep
@JsonClass(generateAdapter = true)
class OptionSectionResponse(
    @field:Json(name = "created_at")
    val createdAt: String?,
    @field:Json(name = "_id")
    val id: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "options")
    val options: List<OptionResponse>?,
    @field:Json(name = "required")
    val required: Boolean?,
)

@Keep
@JsonClass(generateAdapter = true)
class OptionResponse(
    @field:Json(name = "created_at")
    val createdAt: String?,
    @field:Json(name = "_id")
    val id: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "price")
    val price: Float?,
)

@Keep
@JsonClass(generateAdapter = true)
class ReviewResponse(
    @field:Json(name = "created_at")
    val createdAt: String?,
    @field:Json(name = "_id")
    val id: String?,
    @field:Json(name = "rating")
    val rating: Int?,
    @field:Json(name = "comment")
    val comment: String?,
    @field:Json(name = "user")
    val user: UserResponse?,
)