package com.vroomvroom.android.data.model.order

import com.vroomvroom.android.data.enums.OrderStatus
import com.vroomvroom.android.data.model.user.User
import com.vroomvroom.android.data.model.merchant.Merchant
import com.vroomvroom.android.data.model.merchant.Option
import com.vroomvroom.android.data.model.merchant.Product
import com.vroomvroom.android.data.model.user.Address

data class Order(
	val id: String,
	val customer: User,
	val merchant: Merchant,
	val address: Address,
	val products: List<OrderProduct>,
	val deliveryFee: Float,
	val status: OrderStatus,
	val isReviewed: Boolean,
	val createdAt: String,
)

data class OrderProduct(
	val quantity: Int,
	val price: Float,
	val product: Product,
	val options: List<Option>,
)