package com.sebasdev.apprestaurant_android.data.repository

import com.sebasdev.apprestaurant_android.data.remote.api.HomeServiceImpl
import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateProductRequest
import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateSupplierRequest
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.Supplier

class HomeRepository {
  private val api = HomeServiceImpl()

  suspend fun getProducts(): List<Product> {
    return api.getProducts()
  }

  suspend fun getCategories(): List<Category> {
    return api.getCategories()
  }

  suspend fun addProduct(request: CreateProductRequest): String {
    return api.addProduct(request)
  }

  suspend fun getProduct(id: String): Product {
    return api.getProduct(id)
  }

  suspend fun updateProduct(id: String, request: CreateProductRequest): String {
    return api.updateProduct(id, request)
  }

  suspend fun deleteProduct(id: String): String {
    return api.deleteProduct(id)
  }

  suspend fun getSuppliers(): List<Supplier> {
    return api.getSuppliers()
  }

  suspend fun getSupplier(id: String): Supplier {
    return api.getSupplier(id)
  }

  suspend fun addSupplier(request: CreateSupplierRequest): String {
    return api.addSupplier(request)
  }

  suspend fun updateSupplier(id: String, request: CreateSupplierRequest): String {
    return api.updateSupplier(id, request)
  }

  suspend fun deleteSupplier(id: String): String {
    return api.deleteSupplier(id)
  }
}