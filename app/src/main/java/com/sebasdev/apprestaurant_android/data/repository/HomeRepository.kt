package com.sebasdev.apprestaurant_android.data.repository

import com.sebasdev.apprestaurant_android.data.remote.api.HomeServiceImpl
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product

class HomeRepository {
  private val api = HomeServiceImpl()

  suspend fun getProducts(): List<Product> {
    return api.getProducts()
  }

  suspend fun getCategories(): List<Category> {
    return api.getCategories()
  }

  suspend fun getProduct(id: String): Product {
    return api.getProduct(id)
  }
}