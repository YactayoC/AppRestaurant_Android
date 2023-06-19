package com.sebasdev.apprestaurant_android.data.remote.model

import com.sebasdev.apprestaurant_android.domain.model.User

data class LoginResponse(
  val user: User?,
  val token: String?,
  val errorMessage: ErrorResponse?
)
