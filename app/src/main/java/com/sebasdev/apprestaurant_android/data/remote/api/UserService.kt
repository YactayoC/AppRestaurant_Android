package com.sebasdev.apprestaurant_android.data.remote.api

import com.sebasdev.apprestaurant_android.data.remote.model.response.AddProductFavoriteResponse
import com.sebasdev.apprestaurant_android.data.remote.model.response.UpdateUserResponse
import com.sebasdev.apprestaurant_android.domain.model.Product
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
  @FormUrlEncoded
  @POST("user/favorites/{idUser}")
  suspend fun addProductFavorite(
    @Path("idUser") idUser: String,
    @Field("idProduct") idProduct: String,
    @Field("isAdd") isAdd: Boolean
  ): Response<AddProductFavoriteResponse>

  @GET("user/favorites/{idUser}")
  suspend fun getProductsFavorite(
    @Path("idUser") idUser: String,
  ): Response<List<Product>>

  @PATCH("user/{idUser}")
  suspend fun updateUser(
    @Path("idUser") idUser: String,
    @Field("fullname") fullname: String,
    @Field("direction") direction: String,
    @Field("phone") phone: String,
    @Field("password") password: String,
    @Field("profile") profile: String,
  ): Response<UpdateUserResponse>
}