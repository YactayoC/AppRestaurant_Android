package com.sebasdev.apprestaurant_android.domain.usecase

import com.sebasdev.apprestaurant_android.data.repository.HomeRepository
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product

class HomeUseCase {
  private val repository = HomeRepository()

  suspend fun getProducts(): List<Product> = repository.getProducts()
  suspend fun getCategories(): List<Category> = repository.getCategories()
  suspend fun getProduct(id: String): Product = repository.getProduct(id)
}