package com.sebasdev.apprestaurant_android.data.remote.api

import com.sebasdev.apprestaurant_android.data.remote.model.AddOrderResponse
import com.sebasdev.apprestaurant_android.data.remote.model.CreateOrderRequest
import com.sebasdev.apprestaurant_android.domain.model.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
//  @FormUrlEncoded
  @POST("order")
  suspend fun addOrder(
//    @Field("product") product: String,
//    @Field("user") user: String,
//    @Field("subtotal") subtotal: Double,
//    @Field("total") total: Double,
//    @Field("quantity") quantity: Int,
//    @Field("state") state: String,
    @Body request: CreateOrderRequest
  ): Response<AddOrderResponse>

  @GET("order/all/{idUser}")
  suspend fun getOrders(@Path("idUser") idUser: String): Response<List<Order>>

  @GET("order/{idOrder}")
  suspend fun getOrder(@Path("idOrder") idOrder: String): Response<Order>
}