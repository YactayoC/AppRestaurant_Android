package com.sebasdev.apprestaurant_android.data.remote.model.response

data class ErrorResponse(
  val statusCode: Int,
  val message: String,
  val error: String
)
