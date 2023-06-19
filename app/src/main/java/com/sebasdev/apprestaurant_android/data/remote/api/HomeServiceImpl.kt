package com.sebasdev.apprestaurant_android.data.remote.api

import com.sebasdev.apprestaurant_android.data.remote.network.RetrofitInstance
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeServiceImpl {
  private val retrofit = RetrofitInstance.getRetrofit()

  suspend fun getProducts(): List<Product> {
    return withContext(Dispatchers.IO) {
      try {
        val response = retrofit.create(HomeService::class.java).getProducts()
        if (response.isSuccessful) {
          response.body() ?: throw Exception("Error al obtener productos")
        } else {
          throw Exception("Error al obtener productos")
        }
      } catch (e: Exception) {
        throw Exception(e.message)
      }
    }
  }

  suspend fun getCategories(): List<Category> {
    return withContext(Dispatchers.IO) {
      try {
        val response = retrofit.create(HomeService::class.java).getCategories()
        if (response.isSuccessful) {
          response.body() ?: throw Exception("Error al obtener categorias")
        } else {
          throw Exception("Error al obtener categorias")
        }
      } catch (e: Exception) {
        throw Exception(e.message)
      }
    }
  }

  suspend fun getProduct(id: String): Product {
    return withContext(Dispatchers.IO) {
      try {
        val response = retrofit.create(HomeService::class.java).getProduct(id)
        if (response.isSuccessful) {
          response.body() ?: throw Exception("Error al obtener el producto")
        } else {
          throw Exception("Error al obtener el producto")
        }
      } catch (e: Exception) {
        throw Exception(e.message)
      }
    }
  }
}