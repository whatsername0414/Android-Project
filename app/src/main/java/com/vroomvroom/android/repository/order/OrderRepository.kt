package com.vroomvroom.android.repository.order

import com.vroomvroom.android.data.local.entity.cart.CartItemWithOptions
import com.vroomvroom.android.data.enums.OrderStatus
import com.vroomvroom.android.data.model.order.Order
import com.vroomvroom.android.data.local.entity.user.AddressEntity
import com.vroomvroom.android.data.model.user.Address
import com.vroomvroom.android.view.resource.Resource

interface OrderRepository {

    suspend fun getOrders(orderStatus: OrderStatus): Resource<List<Order>>?
    suspend fun getOrder(id: String): Resource<Order>?
    suspend fun createOrders(
        merchant: String,
        deliveryFee: Float,
        address: Address,
        cartItems: List<CartItemWithOptions>
    ): Resource<String>?
    suspend fun cancelOrder(id: String, reason: String): Resource<Boolean>?
    suspend fun updateOrderAddress(
        id: String,
        address: Address
    ): Resource<Boolean>?
    suspend fun createReview(
        id: String,
        merchantId: String,
        rate: Int,
        comment: String
    ): Resource<Boolean>?
}