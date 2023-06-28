package com.sebasdev.apprestaurant_android.domain.usecase

import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateProductRequest
import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateSupplierRequest
import com.sebasdev.apprestaurant_android.data.repository.HomeRepository
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.Supplier

class HomeUseCase {
  private val repository = HomeRepository()

  suspend fun getProducts(): List<Product> = repository.getProducts()
  suspend fun getCategories(): List<Category> = repository.getCategories()
  suspend fun addProduct(request: CreateProductRequest): String = repository.addProduct(request)
  suspend fun getProduct(id: String): Product = repository.getProduct(id)
  suspend fun updateProduct(id: String, request: CreateProductRequest): String =
    repository.updateProduct(id, request)
  suspend fun deleteProduct(id: String): String = repository.deleteProduct(id)
  suspend fun getSuppliers(): List<Supplier> = repository.getSuppliers()
  suspend fun getSupplier(id: String): Supplier = repository.getSupplier(id)
  suspend fun addSupplier(request: CreateSupplierRequest): String = repository.addSupplier(request)
  suspend fun updateSupplier(id: String, request: CreateSupplierRequest): String =
    repository.updateSupplier(id, request)
  suspend fun deleteSupplier(id: String): String = repository.deleteSupplier(id)
}