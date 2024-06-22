package com.vroomvroom.android.repository.order

import android.util.Log
import com.vroomvroom.android.data.api.OrderService
import com.vroomvroom.android.data.enums.OrderStatus
import com.vroomvroom.android.data.local.entity.cart.CartItemWithOptions
import com.vroomvroom.android.data.mapper.toAddress
import com.vroomvroom.android.data.mapper.toCreateOrderRequest
import com.vroomvroom.android.data.mapper.toOrder
import com.vroomvroom.android.data.model.order.Order
import com.vroomvroom.android.data.local.entity.user.AddressEntity
import com.vroomvroom.android.data.mapper.toAddressRequest
import com.vroomvroom.android.data.model.user.Address
import com.vroomvroom.android.repository.BaseRepository
import com.vroomvroom.android.view.resource.Resource
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val service: OrderService
) : OrderRepository, BaseRepository() {
    override suspend fun getOrders(orderStatus: OrderStatus): Resource<List<Order>>? {
        var data: Resource<List<Order>>? = null
        try {
            val result = service.getOrders(status = orderStatus.ordinal)
            result.body()?.let { response ->
                data = handleSuccess(response.data.map { it.toOrder() })
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun getOrder(id: String): Resource<Order>? {
        var data: Resource<Order>? = null
        try {
            val result = service.getOrder(id)
            result.body()?.let { response ->
                data = handleSuccess(response.data.toOrder())
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun createOrders(
        merchant: String,
        deliveryFee: Float,
        address: Address,
        cartItems: List<CartItemWithOptions>
    ): Resource<String>? {
        var data: Resource<String>? = null
        try {
            val request = toCreateOrderRequest(
                merchant = merchant,
                deliveryFee = deliveryFee,
                address = address,
                cartItems = cartItems
            )
            val result = service.createOrder(request)
            if (result.isSuccessful) {
                result.body()?.data?.let {
                    data = handleSuccess(it["orderId"].orEmpty())
                }
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun cancelOrder(id: String, reason: String): Resource<Boolean>? {
        var data: Resource<Boolean>? = null
        try {
            val body = mapOf("reason" to reason)
            val result = service.cancelOrder(id, body)
            if (result.isSuccessful && result.code() == 200) {
                data = handleSuccess(true)
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun updateOrderAddress(
        id: String,
        address: Address
    ): Resource<Boolean>? {
        var data: Resource<Boolean>? = null
        try {
            val request = address.toAddressRequest()
            val result = service.updateOrderAddress(id, request)
            if (result.isSuccessful && result.code() == 201) {
                data = handleSuccess(true)
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun createReview(
        id: String,
        merchantId: String,
        rate: Int,
        comment: String
    ): Resource<Boolean>? {
        var data: Resource<Boolean>? = null
        try {
            val body = mapOf(
                "merchantId" to merchantId,
                "rate" to rate.toString(),
                "comment" to comment
            )
            val result = service.createReview(id, body)
            if (result.isSuccessful && result.code() == 201) {
                data = handleSuccess(true)
            } else {
                return handleException(result.code(), result.errorBody())
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }


    companion object {
        const val TAG = "OrderRepositoryImpl"
    }
}