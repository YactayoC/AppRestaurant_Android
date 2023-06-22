package com.sebasdev.apprestaurant_android.domain.usecase

import com.sebasdev.apprestaurant_android.data.repository.HomeRepository
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.Supplier

class HomeUseCase {
  private val repository = HomeRepository()

  suspend fun getProducts(): List<Product> = repository.getProducts()
  suspend fun getCategories(): List<Category> = repository.getCategories()
  suspend fun getProduct(id: String): Product = repository.getProduct(id)
  suspend fun getSuppliers(): List<Supplier> = repository.getSuppliers()
  suspend fun getSupplier(id: String): Supplier = repository.getSupplier(id)
}