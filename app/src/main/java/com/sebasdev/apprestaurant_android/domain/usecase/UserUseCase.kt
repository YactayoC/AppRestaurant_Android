package com.sebasdev.apprestaurant_android.domain.usecase

import com.sebasdev.apprestaurant_android.data.repository.UserRepository
import com.sebasdev.apprestaurant_android.domain.model.Product

class UserUseCase {
  private val repository = UserRepository()

  suspend fun getProductsFavorite(idUser: String): List<Product> {
    return repository.getProductsFavorite(idUser)
  }

  suspend fun addProductFavorite(idUser: String, idProduct: String, isAdd: Boolean): String {
    return repository.addProductFavorite(idUser, idProduct, isAdd)
  }

  suspend fun updateUser(
    idUser: String,
    fullname: String,
    direction: String,
    phone: String,
    password: String,
  ): String {
    return repository.updateUser(idUser, fullname, direction, phone, password)
  }
}