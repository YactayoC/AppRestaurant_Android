package com.sebasdev.apprestaurant_android.data.remote.api

import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateProductRequest
import com.sebasdev.apprestaurant_android.data.remote.model.response.AddOrderResponse
import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateSupplierRequest
import com.sebasdev.apprestaurant_android.data.remote.model.response.AddProductResponse
import com.sebasdev.apprestaurant_android.data.remote.model.response.AddSupplierResponse
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.Supplier
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeService {
  @POST("product")
  suspend fun addProduct(
    @Body request: CreateProductRequest
  ): Response<AddProductResponse>

  @GET("product")
  suspend fun getProducts(): Response<List<Product>>

  @GET("product/categories/all")
  suspend fun getCategories(): Response<List<Category>>

  @PATCH("product/{id}")
  suspend fun updateProduct(
    @Path("id") id: String,
    @Body request: CreateProductRequest
  ): Response<AddProductResponse>

  @GET("product/{id}")
  suspend fun getProduct(@Path("id") id: String): Response<Product>

  @DELETE("product/{id}")
  suspend fun deleteProduct(@Path("id") id: String): Response<AddProductResponse>

  @POST("supplier")
  suspend fun addSupplier(
    @Body request: CreateSupplierRequest
  ): Response<AddSupplierResponse>

  @GET("supplier")
  suspend fun getSuppliers(): Response<List<Supplier>>

  @GET("supplier/{id}")
  suspend fun getSupplier(@Path("id") id: String): Response<Supplier>

  @PATCH("supplier/{id}")
  suspend fun updateSupplier(
    @Path("id") id: String,
    @Body request: CreateSupplierRequest
  ): Response<AddSupplierResponse>

  @DELETE("supplier/{id}")
  suspend fun deleteSupplier(@Path("id") id: String): Response<AddSupplierResponse>
}