package com.sebasdev.apprestaurant_android.data.repository

import android.util.Log
import com.sebasdev.apprestaurant_android.data.remote.api.OrderServiceImpl
import com.sebasdev.apprestaurant_android.domain.model.Order

class OrderRepository {
  private val api = OrderServiceImpl()

  suspend fun getOrders(idUser: String): List<Order> {
    return api.getOrders(idUser)
  }

  suspend fun getOrder(idOrder: String): Order {
    return api.getOrder(idOrder)
  }

  suspend fun addOrder(
    product: String,
    user: String,
    subtotal: Double,
    total: Double,
    quantity: Int,
    state: String
  ): String {
    Log.d("LOGGER", "Repository: $product, $user, $subtotal, $total, $quantity, $state")
    return api.addOrder(product, user, subtotal, total, quantity, state)
  }
}