package com.sebasdev.apprestaurant_android.data.remote.api

import com.google.gson.Gson
import com.sebasdev.apprestaurant_android.data.remote.model.response.ErrorResponse
import com.sebasdev.apprestaurant_android.data.remote.network.RetrofitInstance
import com.sebasdev.apprestaurant_android.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserServiceImpl {
  private val retrofit = RetrofitInstance.getRetrofit()

  suspend fun getProductsFavorite(idUser: String): List<Product> {
    return withContext(Dispatchers.IO) {
      try {
        val response = retrofit.create(UserService::class.java).getProductsFavorite(idUser)
        if (response.isSuccessful) {
          response.body() ?: emptyList()
        } else {
          val errorMessage = response.errorBody()?.string()
          val error = parseErrorBody(errorMessage)
          throw Exception(error.message)
        }
      } catch (e: Exception) {
        throw Exception(e.message)
      }
    }
  }

  suspend fun addProductFavorite(
    idUser: String,
    idProduct: String,
    isAdd: Boolean
  ): String {
    return withContext(Dispatchers.IO) {
      try {
        val response =
          retrofit.create(UserService::class.java).addProductFavorite(idUser, idProduct, isAdd)
        if (response.isSuccessful) {
          response.body()?.message ?: ""
        } else {
          throw Exception("Error al agregar producto a favoritos")
        }
      } catch (e: Exception) {
        throw Exception(e.message)
      }
    }
  }

  suspend fun updateUser(
    idUser: String,
    fullname: String,
    direction: String,
    phone: String,
    password: String,
    profile: String,
  ): String {
    return withContext(Dispatchers.IO) {
      try {
        val response = retrofit.create(UserService::class.java)
          .updateUser(idUser, fullname, direction, phone, password, profile)
        if (response.isSuccessful) {
          response.body()?.message ?: ""
        } else {
          throw Exception("Error al actualizar usuario")
        }
      } catch (e: Exception) {
        throw Exception(e.message)
      }
    }
  }

  private fun parseErrorBody(errorMessage: String?): ErrorResponse {
    return Gson().fromJson(errorMessage, ErrorResponse::class.java)
  }
}