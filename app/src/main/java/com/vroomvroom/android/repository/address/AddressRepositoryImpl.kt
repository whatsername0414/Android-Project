package com.vroomvroom.android.repository.address

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.vroomvroom.android.data.local.dao.UserDao
import com.vroomvroom.android.data.local.entity.user.AddressEntity
import com.vroomvroom.android.data.mapper.toAddress
import com.vroomvroom.android.data.mapper.toAddressEntity
import com.vroomvroom.android.data.model.user.Address
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : AddressRepository  {

    //UserLocation
    override suspend fun insertAddress(address: Address) {
        val entity = address.toAddressEntity()
        userDao.insertAddress(entity)
    }
    override suspend fun updateAddress(address: Address) {
        val entity = address.toAddressEntity()
        userDao.updateAddress(entity)
    }
    override suspend fun updateAllAddress() = userDao.updateAllAddress()
    override suspend fun deleteAddress(address: Address) {
        val entity = address.toAddressEntity()
        userDao.deleteAddress(entity)
    }
    override suspend fun deleteAllAddress() = userDao.deleteAllAddress()
    override fun getUserAllAddress(): LiveData<List<Address>> {
        return Transformations.map(userDao.getAllAddress()) { entities ->
            entities?.map { it.toAddress() }
        }
    }
}