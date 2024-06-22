package com.vroomvroom.android.data.api

import com.vroomvroom.android.data.model.BaseResponse
import com.vroomvroom.android.data.remote.response.OrderResponse
import com.vroomvroom.android.data.remote.request.AddressRequest
import com.vroomvroom.android.data.remote.request.CreateOrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderService {

    @GET("orders")
    suspend fun getOrders(
        @Query("type") type: String = "customer",
        @Query("status") status: Int
    ): Response<BaseResponse<List<OrderResponse>>>

    @GET("orders/{id}")
    suspend fun getOrder(
        @Path("id") id: String
    ): Response<BaseResponse<OrderResponse>>

    @POST("orders")
    suspend fun createOrder(
        @Body body: CreateOrderRequest
    ): Response<BaseResponse<Map<String, String>>>

    @PATCH("orders/{id}/cancel")
    suspend fun cancelOrder(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): Response<BaseResponse<OrderResponse>>

    @PATCH("orders/{id}/update-address")
    suspend fun updateOrderAddress(
        @Path("id") id: String,
        @Body body: AddressRequest
    ): Response<BaseResponse<OrderResponse>>

    @PUT("orders/{id}/review")
    suspend fun createReview(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): Response<BaseResponse<OrderResponse>>

}