package com.vroomvroom.android.data.mapper

import com.vroomvroom.android.data.local.entity.cart.CartItemEntity
import com.vroomvroom.android.data.local.entity.cart.CartProductOptionEntity
import com.vroomvroom.android.data.local.entity.cart.CartMerchantEntity
import com.vroomvroom.android.data.model.merchant.Option

fun toCartItemEntity(
    productId: String,
    name: String,
    image: String,
    price: Float,
    quantity: Int,
    notes: String?,
    merchantId: String,
    merchantName: String
): CartItemEntity {
    return CartItemEntity(
        productId = productId,
        cartMerchant = mapToMerchantEntity(merchantId, merchantName),
        name = name,
        image = image,
        price = price,
        quantity = quantity,
        notes = notes
    )
}

private fun mapToMerchantEntity(id: String, name: String): CartMerchantEntity {
    return CartMerchantEntity(id, name)
}

fun Option.toCartItemOptionEntity(
    type: String,
    productId: Int,
): CartProductOptionEntity {
    return CartProductOptionEntity(
        id = id,
        name = name,
        type = type,
        price = price,
        productId = productId,
    )
}