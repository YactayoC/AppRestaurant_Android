package com.sebasdev.apprestaurant_android.data.remote.api

import com.google.gson.Gson
import com.sebasdev.apprestaurant_android.data.remote.model.response.ErrorResponse
import com.sebasdev.apprestaurant_android.data.remote.model.response.LoginResponse
import com.sebasdev.apprestaurant_android.data.remote.model.response.RegisterResponse
import com.sebasdev.apprestaurant_android.data.remote.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class AuthServiceImpl {
  private val retrofit = RetrofitInstance.getRetrofit()

  suspend fun login(email: String, password: String): LoginResponse {
    return withContext(Dispatchers.IO) {
      try {
        val response = retrofit.create(AuthService::class.java).login(email, password)
        if (response.isSuccessful) {
          response.body() ?: throw IOException("Error al iniciar sesión")
        } else {
          val errorMessage = response.errorBody()?.string()
          val error = parseErrorBody(errorMessage)
          LoginResponse(null, null, error)
        }
      } catch (e: Exception) {
        throw IOException(e.message)
      }
    }
  }

  suspend fun register(
    fullname: String,
    email: String,
    password: String,
    phone: String
  ): RegisterResponse {
    return withContext(Dispatchers.IO) {
      try {
        val response =
          retrofit.create(AuthService::class.java).register(fullname, email, password, phone)
        if (response.isSuccessful) {
          response.body() ?: throw IOException("Error al registrar usuario")
        } else {
          val errorMessage = response.errorBody()?.string()
          val error = parseErrorBody(errorMessage)
          RegisterResponse(null, error)
        }
      } catch (e: Exception) {
        throw IOException(e.message)
      }
    }
  }

  private fun parseErrorBody(errorMessage: String?): ErrorResponse {
    return Gson().fromJson(errorMessage, ErrorResponse::class.java)
  }
}