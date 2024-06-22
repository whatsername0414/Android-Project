package com.vroomvroom.android.data.local.entity.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroomvroom.android.utils.Constants

@Entity(tableName = Constants.CART_PRODUCT_OPTION_TABLE)
data class CartProductOptionEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: String,
    val price: Float? = null,
    val productId: Int? = null
 )