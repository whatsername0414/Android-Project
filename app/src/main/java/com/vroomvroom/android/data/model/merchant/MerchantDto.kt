package com.vroomvroom.android.data.model.merchant

import com.google.gson.annotations.SerializedName

data class MerchantDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("categories")
    val categories: List<CategoryDto>,
    @SerializedName("product_sections")
    val productSections: List<ProductSectionsDto>?,
    @SerializedName("rates")
    val rates: Int?,
    @SerializedName("ratings")
    val ratings: Double?,
    @SerializedName("favorite")
    var favorite: Boolean?,
    @SerializedName("location")
    val location: List<Double>?,
    @SerializedName("opening")
    val opening: Int,
    @SerializedName("closing")
    val closing: Int,
    @SerializedName("isOpen")
    val isOpen: Boolean,
    @SerializedName("reviews")
    val reviews: List<ReviewDto>?
)

data class ProductSectionsDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("products")
    val products: List<ProductDto>
)

data class ProductDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("price")
    val price: Double,
    @SerializedName("description")
    val description: String?,
    @SerializedName("option_sections")
    val optionSections: List<OptionSectionDto>?
)

data class OptionSectionDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("required")
    val required: Boolean,
    @SerializedName("options")
    val options: List<OptionDto>
)

data class OptionDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double?
)

data class ReviewDto(
    @SerializedName("_id")
    val id: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("created_at")
    val createdAt: String
)
