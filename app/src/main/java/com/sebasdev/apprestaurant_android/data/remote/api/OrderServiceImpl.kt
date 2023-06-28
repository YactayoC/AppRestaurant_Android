package com.sebasdev.apprestaurant_android.data.remote.api

import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateOrderRequest
import com.sebasdev.apprestaurant_android.data.remote.network.RetrofitInstance
import com.sebasdev.apprestaurant_android.domain.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class OrderServiceImpl {
  private val retrofit = RetrofitInstance.getRetrofit()

  suspend fun getOrders(idUser: String): List<Order> {
    return withContext(Dispatchers.IO) {
      try {
        val response = retrofit.create(OrderService::class.java).getOrders(idUser)
        if (response.isSuccessful) {
          response.body() ?: throw Exception("Error al obtener ordenes")
        } else {
          throw Exception("Error al obtener ordenes")
        }
      } catch (e: Exception) {
        throw Exception(e.message)
      }
    }
  }

  suspend fun getOrder(idOrder: String): Order {
    return withContext(Dispatchers.IO) {
      try {
        val response = retrofit.create(OrderService::class.java).getOrder(idOrder)
        if (response.isSuccessful) {
          response.body() ?: throw Exception("Error al obtener la orden")
        } else {
          throw Exception("Error al obtener la orden")
        }
      } catch (e: Exception) {
        throw Exception(e.message)
      }
    }
  }

  suspend fun addOrder(
    product: String,
    user: String,
    direction: String,
    subtotal: Double,
    total: Double,
    quantity: Int,
    state: String,
  ): String {
    return withContext(Dispatchers.IO) {
      try {
        val request = CreateOrderRequest(product, user, direction, subtotal, total, quantity, state)
        val response = retrofit.create(OrderService::class.java).addOrder(request)

        if (response.isSuccessful) {
          response.body()?.message ?: ""
        }
        else {
          throw Exception("Error al agregar la orden")
        }
      } catch (e: IOException) {
        throw Exception(e.message)
      }
    }
  }
}