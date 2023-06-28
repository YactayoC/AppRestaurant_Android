package com.sebasdev.apprestaurant_android.data.remote.model.request

data class CreateSupplierRequest (
  val name: String,
  val phone: String,
  val direction: String,
  val ruc: String,
  val email: String
)