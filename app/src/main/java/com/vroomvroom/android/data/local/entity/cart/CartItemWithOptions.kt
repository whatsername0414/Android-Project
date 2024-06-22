package com.vroomvroom.android.data.local.entity.cart

import androidx.room.Embedded
import androidx.room.Relation

class CartItemWithOptions(
    @Embedded
    val cartItem: CartItemEntity,
    @Relation(parentColumn = "id", entityColumn = "productId")
    val cartItemOptions: List<CartProductOptionEntity>?
)