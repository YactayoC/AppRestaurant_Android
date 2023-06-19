package com.sebasdev.apprestaurant_android.data.remote.api

import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {
  @GET("product")
  suspend fun getProducts(): Response<List<Product>>

  @GET("product/categories/all")
  suspend fun getCategories(): Response<List<Category>>

  @GET("product/{id}")
  suspend fun getProduct(@Path("id") id: String): Response<Product>
}