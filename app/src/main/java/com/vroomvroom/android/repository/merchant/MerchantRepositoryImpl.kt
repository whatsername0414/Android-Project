package com.vroomvroom.android.repository.merchant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.vroomvroom.android.data.api.MerchantService
import com.vroomvroom.android.data.local.dao.SearchDao
import com.vroomvroom.android.data.local.entity.merchant.SearchEntity
import com.vroomvroom.android.data.mapper.toMerchant
import com.vroomvroom.android.data.mapper.toSearch
import com.vroomvroom.android.data.mapper.toSearchEntity
import com.vroomvroom.android.data.model.merchant.*
import com.vroomvroom.android.data.model.search.Search
import com.vroomvroom.android.repository.BaseRepository
import com.vroomvroom.android.view.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MerchantRepositoryImpl @Inject constructor(
    private val service: MerchantService,
    private val searchDao: SearchDao,
) : MerchantRepository, BaseRepository()  {

    override suspend fun getCategories(type: String): Resource<List<Category>>? {
        var data: Resource<List<Category>>? = null
        try {
            val result = service.getCategories(type)
            if (result.isSuccessful) {
                result.body()?.data?.let {
                    data = handleSuccess(it)
                }
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun getMerchants(
        category: String?,
        searchTerm: String?,
        isLoggedIn: Boolean
    ): Resource<List<Merchant>>? {
        var data: Resource<List<Merchant>>? = null
        try {
            val result = if (isLoggedIn) service.getMerchants(searchTerm)
            else service.getMerchants("unauthorized", searchTerm)
            if (result.isSuccessful) {
                result.body()?.data?.let { merchants ->
                    withContext(Dispatchers.Default) {
                        data = handleSuccess(merchants.map { it.toMerchant() })
                    }
                }
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun getMerchant(id: String): Resource<Merchant>? {
        var data: Resource<Merchant>? = null
        try {
            val result = service.getMerchant(id)
            if (result.isSuccessful) {
                result.body()?.data?.let { merchant ->
                    withContext(Dispatchers.Default) {
                        data = handleSuccess(merchant.toMerchant())
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error: ${e.message}")
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun getFavorites(): Resource<List<Merchant>>? {
        var data: Resource<List<Merchant>>? = null
        try {
            val result = service.getFavorites()
            if (result.isSuccessful) {
                result.body()?.data?.let { merchants ->
                    withContext(Dispatchers.Default) {
                        data = handleSuccess(merchants.map { it.toMerchant() })
                    }
                }
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun updateFavorite(id: String): Resource<Boolean> {
        val data: Resource<Boolean>
        try {
            val result = service.updateFavorite(id)
            data = if (result.isSuccessful) {
                handleSuccess(true)
            } else {
                handleSuccess(false)
            }
        } catch (e: Exception) {
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun insertSearch(search: String) {
        val date = System.currentTimeMillis()
        val entity = SearchEntity(
            searchTerm = search,
            fromLocal = true,
            createdAt = date,
        )
        searchDao.insertSearch(entity)
    }

    override suspend fun deleteSearch(search: Search) {
        searchDao.deleteSearch(search.toSearchEntity())
    }

    override fun getAllSearch(): LiveData<List<Search>> {
        return Transformations.map(searchDao.getAllSearch()) {it.map { it.toSearch() }}
    }

    companion object {
        const val TAG = "MerchantRepositoryImpl"
    }
}