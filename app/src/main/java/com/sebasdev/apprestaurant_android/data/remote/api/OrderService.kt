package com.sebasdev.apprestaurant_android.data.remote.api

import com.sebasdev.apprestaurant_android.data.remote.model.response.AddOrderResponse
import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateOrderRequest
import com.sebasdev.apprestaurant_android.domain.model.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
//  @FormUrlEncoded
  @POST("order")
  suspend fun addOrder(
    @Body request: CreateOrderRequest
  ): Response<AddOrderResponse>

  @GET("order/all/{idUser}")
  suspend fun getOrders(@Path("idUser") idUser: String): Response<List<Order>>

  @GET("order/{idOrder}")
  suspend fun getOrder(@Path("idOrder") idOrder: String): Response<Order>
}