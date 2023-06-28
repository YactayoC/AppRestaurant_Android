package com.sebasdev.apprestaurant_android.domain.model

data class Order(
  val _id: String?,
  val user: User,
  val direction: String,
  val product: Product,
  val subtotal: Double,
  val total: Double,
  val quantity: Int,
  val state: String,
  val createdAt: String?,
  val updatedAt: String?
)
