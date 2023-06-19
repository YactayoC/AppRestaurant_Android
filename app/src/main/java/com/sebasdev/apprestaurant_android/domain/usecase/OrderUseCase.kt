package com.sebasdev.apprestaurant_android.domain.usecase

import com.sebasdev.apprestaurant_android.data.repository.OrderRepository
import com.sebasdev.apprestaurant_android.domain.model.Order

class OrderUseCase {
  private val repository = OrderRepository()

  suspend fun getOrders(idUser: String): List<Order> = repository.getOrders(idUser)
  suspend fun getOrder(idOrder: String): Order = repository.getOrder(idOrder)
  suspend fun createOrder(
    product: String,
    user: String,
    subtotal: Double,
    total: Double,
    quantity: Int,
    state: String
  ): String = repository.addOrder(product, user, subtotal, total, quantity, state)
}