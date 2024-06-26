package com.vroomvroom.android.data.mapper

import com.vroomvroom.android.data.local.entity.merchant.SearchEntity
import com.vroomvroom.android.data.model.merchant.Merchant
import com.vroomvroom.android.data.model.merchant.Option
import com.vroomvroom.android.data.model.merchant.OptionSection
import com.vroomvroom.android.data.model.merchant.Product
import com.vroomvroom.android.data.model.merchant.ProductSection
import com.vroomvroom.android.data.model.merchant.Review
import com.vroomvroom.android.data.model.search.Search
import com.vroomvroom.android.data.remote.response.MerchantResponse
import com.vroomvroom.android.data.remote.response.OptionResponse
import com.vroomvroom.android.data.remote.response.OptionSectionResponse
import com.vroomvroom.android.data.remote.response.ProductResponse
import com.vroomvroom.android.data.remote.response.ProductSectionResponse
import com.vroomvroom.android.data.remote.response.ReviewResponse

fun MerchantResponse?.toMerchant(): Merchant {
    return Merchant(
        closing = this?.closing ?: 0,
        id = this?.id.orEmpty(),
        image = this?.image.orEmpty(),
        latLong = this?.location.orEmpty(),
        name = this?.name.orEmpty(),
        opening = this?.opening ?: 0,
        productSections = this?.productSections?.map { it.toProductSection() }.orEmpty(),
        isOpen = this?.isOpen ?: false,
        isFavorite = this?.isFavorite ?: false,
        ratings = this?.reviews?.map { it.rating ?: 0 }?.average() ?: 0.0,
        reviews = this?.reviews?.map { it.toReview() }.orEmpty(),
    )
}

fun ProductSectionResponse.toProductSection(): ProductSection {
    return ProductSection(
        id = id.orEmpty(),
        name = name.orEmpty(),
        products = products?.map { it.toProduct() }.orEmpty(),
    )
}

fun ProductResponse?.toProduct(): Product {
    return Product(
        description = this?.description.orEmpty(),
        id = this?.id.orEmpty(),
        image = this?.image.orEmpty(),
        name = this?.name.orEmpty(),
        optionSections = this?.optionSections?.map { it.toOptionSection() }.orEmpty(),
        price = this?.price ?: 0f,
    )
}

fun OptionSectionResponse?.toOptionSection(): OptionSection {
    return OptionSection(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        options = this?.options?.map { it.toOption() }.orEmpty(),
        required = this?.required ?: false,
    )
}

fun OptionResponse?.toOption(): Option {
    return Option(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        price = this?.price ?: 0f
    )
}

fun ReviewResponse?.toReview(): Review {
    return Review(
        id = this?.id.orEmpty(),
        userName = this?.user?.name.orEmpty(),
        comment = this?.comment.orEmpty(),
        rating = this?.rating ?: 0,
        createdAt = this?.createdAt.orEmpty(),
    )
}

fun Search.toSearchEntity(): SearchEntity {
    return SearchEntity(
        id = id,
        searchTerm = searchTerm,
        fromLocal = fromLocal,
        createdAt = createdAt,
    )
}

fun SearchEntity.toSearch(): Search {
    return Search(
        id = id ?: -1,
        searchTerm = searchTerm,
        fromLocal = fromLocal,
        createdAt = createdAt,
    )
}