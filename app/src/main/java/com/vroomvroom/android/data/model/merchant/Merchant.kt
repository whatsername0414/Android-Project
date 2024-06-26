package com.vroomvroom.android.data.model.merchant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Merchant(
    val id: String,
    val name: String,
    val image: String,
    val productSections: @RawValue List<ProductSection>,
    val ratings: Double,
    var isFavorite: Boolean,
    val latLong: List<Double>,
    val opening: Int,
    val closing: Int,
    val isOpen: Boolean,
    val reviews: @RawValue List<Review>
) : Parcelable

data class ProductSection(
    val id: String,
    val name: String,
    val products: List<Product>
)

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val image: String,
    val price: Float,
    val description: String,
    val optionSections: @RawValue List<OptionSection>?
) : Parcelable

data class OptionSection(
    val id: String,
    val name: String,
    val required: Boolean,
    val options: List<Option>
)

data class Option(
    val id: String,
    val name: String,
    val price: Float
)

data class Review(
    val id: String,
    val userName: String,
    val comment: String,
    val rating: Int,
    val createdAt: String
)
