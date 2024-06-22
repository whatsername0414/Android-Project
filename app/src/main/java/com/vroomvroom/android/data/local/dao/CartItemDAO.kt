package com.vroomvroom.android.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vroomvroom.android.data.local.entity.cart.CartProductOptionEntity
import com.vroomvroom.android.data.local.entity.cart.CartItemEntity
import com.vroomvroom.android.data.local.entity.cart.CartItemWithOptions

@Dao
interface CartItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItemEntity: CartItemEntity): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItemOptions(cartItemOptions: List<CartProductOptionEntity>)

    @Update
    suspend fun updateCartItem(cartItemEntity: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(cartItemEntity: CartItemEntity)

    @Query("DELETE FROM cart_table")
    suspend fun deleteAllCartItem()
    @Query("DELETE FROM cart_product_option_table")
    suspend fun deleteAllCartItemOption()

    @Transaction
    @Query("SELECT * FROM cart_table")
    fun getAllCartItem(): LiveData<List<CartItemWithOptions>>
}