package com.sebasdev.apprestaurant_android.domain.usecase

import com.sebasdev.apprestaurant_android.data.remote.model.LoginResponse
import com.sebasdev.apprestaurant_android.data.remote.model.RegisterResponse
import com.sebasdev.apprestaurant_android.data.repository.AuthRepository

class AuthUseCase {
  private val repository = AuthRepository()

  suspend fun onLogin(email: String, password: String): LoginResponse {
    return repository.login(email, password)
  }

  suspend fun onRegister(fullname: String, email: String, password: String, phone: String): RegisterResponse {
    return repository.register(fullname, email, password, phone)
  }
}