package com.vroomvroom.android.repository.address

import androidx.lifecycle.LiveData
import com.vroomvroom.android.data.local.entity.user.AddressEntity
import com.vroomvroom.android.data.model.user.Address

interface AddressRepository {
    suspend fun insertAddress(address: Address)
    suspend fun updateAddress(address: Address)
    suspend fun updateAllAddress(): Int
    suspend fun deleteAddress(address: Address)
    suspend fun deleteAllAddress()
    fun getUserAllAddress(): LiveData<List<Address>>
}