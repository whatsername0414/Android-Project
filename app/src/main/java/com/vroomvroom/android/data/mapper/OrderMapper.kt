package com.vroomvroom.android.data.mapper

import com.vroomvroom.android.data.enums.OrderStatus
import com.vroomvroom.android.data.local.entity.cart.CartItemWithOptions
import com.vroomvroom.android.data.model.order.Order
import com.vroomvroom.android.data.model.order.OrderProduct
import com.vroomvroom.android.data.remote.response.OrderProductResponse
import com.vroomvroom.android.data.remote.response.OrderResponse
import com.vroomvroom.android.data.local.entity.user.AddressEntity
import com.vroomvroom.android.data.model.user.Address
import com.vroomvroom.android.data.remote.request.AddressRequest
import com.vroomvroom.android.data.remote.request.CreateOrderRequest
import com.vroomvroom.android.data.remote.request.OrderProductRequest

fun OrderResponse.toOrder(): Order {
    return Order(
        id = id.orEmpty(),
        customer = customer.toUser(),
        merchant = merchant.toMerchant(),
        address = address.toAddress(),
        products = products?.map { it.toOrderProduct() }.orEmpty(),
        deliveryFee  = deliveryFee ?: 0f,
        status = status.toOrderStatus(),
        isReviewed = isReviewed ?: false,
        createdAt = createdAt.orEmpty(),
    )
}

fun OrderProductResponse?.toOrderProduct(): OrderProduct {
    return OrderProduct(
        quantity = this?.quantity ?: 0,
        price = this?.price ?: 0f,
        product = this?.product.toProduct(),
        options = this?.options?.map { it.toOption() }.orEmpty(),
    )
}

fun Int?.toOrderStatus(): OrderStatus {
    return when(this) {
        1 -> OrderStatus.CONFIRMED
        2 -> OrderStatus.ACCEPTED
        3 -> OrderStatus.PICKED_UP
        4 -> OrderStatus.DELIVERED
        5 -> OrderStatus.CANCELLED
        else -> OrderStatus.PENDING
    }
}

fun toCreateOrderRequest(
    merchant: String,
    deliveryFee: Float,
    address: Address,
    cartItems: List<CartItemWithOptions>
): CreateOrderRequest {
    val deliveryAddress = AddressRequest(
        street = address.street.orEmpty(),
        barangay = address.barangay.orEmpty(),
        city = address.city.orEmpty(),
        additionalInfo =  address.additionalInfo.orEmpty(),
        latitude = address.latitude,
        longitude = address.longitude
    )
    return CreateOrderRequest(
        merchant = merchant,
        address = deliveryAddress,
        products = ArrayList(cartItems.map { it.toOrderProductRequest() }),
        deliveryFee = deliveryFee,
    )
}

private fun CartItemWithOptions.toOrderProductRequest(): OrderProductRequest {
    val productPrice = cartItem.price
    val optionsTotalPrice = cartItemOptions?.sumOf { it.price?.toDouble() ?: 0.0 }
    return OrderProductRequest(
        product = cartItem.productId,
        options = ArrayList(cartItemOptions?.map { it.id }.orEmpty()),
        quantity = cartItem.quantity,
        price = productPrice + (optionsTotalPrice?.toFloat() ?: 0f),
        notes = cartItem.notes.orEmpty()
    )
}