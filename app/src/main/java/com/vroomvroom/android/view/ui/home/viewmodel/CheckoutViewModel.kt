package com.vroomvroom.android.view.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroomvroom.android.data.local.entity.cart.CartItemWithOptions
import com.vroomvroom.android.data.local.entity.user.AddressEntity
import com.vroomvroom.android.data.model.user.Address
import com.vroomvroom.android.repository.order.OrderRepository
import com.vroomvroom.android.view.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _order by lazy { MutableLiveData<Resource<String>>() }
    val order: LiveData<Resource<String>>
        get() = _order
    val isLocationConfirmed by lazy { MutableLiveData(false) }
    var subtotal = 0.0

    fun createOrder(
        merchantId: String,
        deliveryFee: Float,
        address: Address,
        cartItems: List<CartItemWithOptions>
    ) {
        _order.postValue(Resource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val response = orderRepository.createOrders(
                merchantId, deliveryFee, address, cartItems)
            response?.let { data ->
                when (data) {
                    is Resource.Success -> {
                        _order.postValue(data)
                    }
                    is Resource.Error -> {
                        _order.postValue(data)
                    }
                    else -> {
                        _order.postValue(data)
                    }
                }
            }
        }
    }

}