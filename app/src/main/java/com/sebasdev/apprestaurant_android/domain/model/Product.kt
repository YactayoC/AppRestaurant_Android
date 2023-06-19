package com.sebasdev.apprestaurant_android.domain.model

data class Product(
  val _id: String,
  val name: String,
  val description: String,
  val price: Double,
  val category: String,
  val image: String,
  val createdAt: String,
  val updatedAt: String,
  val quantity: Int?
)
