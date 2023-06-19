package com.sebasdev.apprestaurant_android.data.remote.model

data class ErrorResponse(
  val statusCode: Int,
  val message: String,
  val error: String
)
