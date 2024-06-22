package com.vroomvroom.android.data.local.entity.cart

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroomvroom.android.utils.Constants.CART_TABLE

@Entity(tableName = CART_TABLE)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val productId: String,
    val image: String,
    @Embedded
    val cartMerchant: CartMerchantEntity,
    val name: String,
    val price: Float,
    val quantity: Int,
    val notes: String? = null
)

data class CartMerchantEntity(
    val merchantId: String,
    val merchantName: String,
)
