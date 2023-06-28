package com.sebasdev.apprestaurant_android.data.remote.model.request

data class CreateProductRequest(
  val name: String,
  val description: String,
  val price: Double,
  val category: String,
  val image: String
)
