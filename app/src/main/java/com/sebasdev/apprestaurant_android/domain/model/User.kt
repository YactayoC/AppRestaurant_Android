package com.sebasdev.apprestaurant_android.domain.model

data class User(
  val _id: String,
  val fullname: String,
  val email: String,
  val password: String,
  val direction: String?,
  val phone: String,
  val profile: String,
  val favoriteProducts: List<Product>,
  val createdAt: String,
  val updatedAt: String
)
