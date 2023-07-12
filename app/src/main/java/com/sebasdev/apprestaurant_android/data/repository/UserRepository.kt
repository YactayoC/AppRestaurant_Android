package com.sebasdev.apprestaurant_android.data.repository

import com.sebasdev.apprestaurant_android.data.remote.api.UserServiceImpl
import com.sebasdev.apprestaurant_android.domain.model.Product

class UserRepository {
  private val api = UserServiceImpl()

  suspend fun getProductsFavorite(idUser: String): List<Product> {
    return api.getProductsFavorite(idUser)
  }

  suspend fun addProductFavorite(
    idUser: String,
    idProduct: String,
    isAdd: Boolean
  ): String {
    return api.addProductFavorite(idUser, idProduct, isAdd)
  }

  suspend fun updateUser(
    idUser: String,
    fullname: String,
    direction: String,
    phone: String,
    password: String,
  ): String {
    return api.updateUser(idUser, fullname, direction, phone, password)
  }
}