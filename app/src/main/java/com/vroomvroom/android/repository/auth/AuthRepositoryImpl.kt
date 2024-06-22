package com.vroomvroom.android.repository.auth

import android.util.Log
import com.vroomvroom.android.data.api.AuthService
import com.vroomvroom.android.data.local.dao.UserDao
import com.vroomvroom.android.data.mapper.toUserEntity
import com.vroomvroom.android.data.local.entity.user.AddressEntity
import com.vroomvroom.android.data.model.user.Address
import com.vroomvroom.android.data.remote.response.RegisterUserRequest
import com.vroomvroom.android.repository.BaseRepository
import com.vroomvroom.android.view.resource.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val userDao: UserDao,
) : AuthRepository, BaseRepository() {
    override suspend fun register(fcmToken: String): Resource<Boolean>? {
        var data: Resource<Boolean>? = null
        try {
            val request = RegisterUserRequest(
                token = fcmToken,
                userType = "user"
            )
            val result = service.register(request)
            if (result.isSuccessful && result.code() == 200) {
                result.body()?.data?.let { response ->
                    userDao.insertUser(response.toUserEntity())
                    data = handleSuccess(true)
                }
            } else {
                handleSuccess(false)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error: ${e.message}")
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun generateEmailOtp(emailAddress: String): Resource<Boolean> {
        val data: Resource<Boolean>
        try {
            val body = mapOf("emailAddress" to emailAddress)
            val result = service.generateEmailOtp(body)
            if (result.isSuccessful && result.code() == 200) {
                data = handleSuccess(true)
            } else {
                return handleException(result.code(), result.errorBody())
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error: ${e.message}")
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun verifyEmailOtp(emailAddress: String, otp: String): Resource<Boolean> {
        val data: Resource<Boolean>
        try {
            val body = mapOf(
                "emailAddress" to emailAddress,
                "otp" to otp
            )
            val result = service.verifyEmailOtp(body)
            if (result.isSuccessful && result.code() == 200) {
                data = handleSuccess(true)
            } else {
                return handleException(result.code(), result.errorBody())
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error: ${e.message}")
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    companion object {
        const val TAG = "AuthRepositoryImpl"
    }
}