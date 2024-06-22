package com.vroomvroom.android.repository.cart

import android.util.Log
import com.vroomvroom.android.data.local.dao.CartItemDAO
import com.vroomvroom.android.data.local.entity.cart.CartItemEntity
import com.vroomvroom.android.data.mapper.toCartItemOptionEntity
import com.vroomvroom.android.data.model.merchant.Option
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor (
    private val cartItemDAO: CartItemDAO
) : CartRepository {
    override suspend fun insertCartItem(cartItemEntity: CartItemEntity): Long {
        return cartItemDAO.insertCartItem(cartItemEntity)
    }
    override suspend fun insertCartItemOptions(options: Map<String, Option>, productId: Int) {
        val cartItemOptions = options.map { it.value.toCartItemOptionEntity(it.key, productId) }
        cartItemDAO.insertCartItemOptions(cartItemOptions)
    }
    override suspend fun updateCartItem(cartItemEntity: CartItemEntity) =
        cartItemDAO.updateCartItem(cartItemEntity)
    override suspend fun deleteCartItem(cartItemEntity: CartItemEntity) =
        cartItemDAO.deleteCartItem(cartItemEntity)
    override suspend fun deleteAllCartItem() = cartItemDAO.deleteAllCartItem()
    override suspend fun deleteAllCartItemOption() = cartItemDAO.deleteAllCartItemOption()
    override fun getAllCartItem() = cartItemDAO.getAllCartItem()
}