package com.vroomvroom.android.data.api

import com.vroomvroom.android.data.model.BaseResponse
import com.vroomvroom.android.data.model.merchant.Category
import com.vroomvroom.android.data.remote.response.MerchantResponse
import retrofit2.Response
import retrofit2.http.*

interface MerchantService {

    @GET("categories")
    suspend fun getCategories(
        @Query("type") type: String,
    ): Response<BaseResponse<List<Category>>>

    @GET("merchants")
    suspend fun getMerchants(
        @Query("searchTerm") searchTerm: String?
    ): Response<BaseResponse<List<MerchantResponse>>>

    @GET("merchants/{path}")
    suspend fun getMerchants(
        @Path("path") path: String,
        @Query("searchTerm") searchTerm: String?
    ): Response<BaseResponse<List<MerchantResponse>>>

    @GET("merchants/{merchantId}")
    suspend fun getMerchant(
        @Path("merchantId") id: String
    ): Response<BaseResponse<MerchantResponse>>

    @GET("merchants/favorites")
    suspend fun getFavorites(): Response<BaseResponse<List<MerchantResponse>>>

    @PUT("merchants/{id}/favorite")
    suspend fun updateFavorite(
        @Path("id") id: String
    ): Response<BaseResponse<Map<String, String>>>
}