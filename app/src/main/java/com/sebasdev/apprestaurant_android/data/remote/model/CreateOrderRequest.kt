package com.sebasdev.apprestaurant_android.data.remote.model

data class CreateOrderRequest (
  val product: String,
  val user: String,
  val subtotal: Double,
  val total: Double,
  val quantity: Int,
  val state: String
)