package com.sebasdev.apprestaurant_android.data.repository

import com.sebasdev.apprestaurant_android.data.remote.api.AuthServiceImpl
import com.sebasdev.apprestaurant_android.data.remote.model.LoginResponse
import com.sebasdev.apprestaurant_android.data.remote.model.RegisterResponse

class AuthRepository {
  private val api = AuthServiceImpl()

  suspend fun login(email: String, password: String): LoginResponse {
    return api.login(email, password)
  }

  suspend fun register(
    fullname: String,
    email: String,
    password: String,
    phone: String
  ): RegisterResponse {
    return api.register(fullname, email, password, phone)
  }
}