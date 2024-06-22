package com.vroomvroom.android.data.api

import com.vroomvroom.android.data.model.BaseResponse
import com.vroomvroom.android.data.remote.response.RegisterUserRequest
import com.vroomvroom.android.data.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterUserRequest
    ): Response<BaseResponse<UserResponse>>

    @POST("auth/email-otp")
    suspend fun generateEmailOtp(
        @Body body: Map<String, String>
    ): Response<BaseResponse<Map<String, Any>>>

    @POST("auth/verify-email-otp")
    suspend fun verifyEmailOtp(
        @Body body: Map<String, String>
    ): Response<BaseResponse<Map<String, Any>>>
}