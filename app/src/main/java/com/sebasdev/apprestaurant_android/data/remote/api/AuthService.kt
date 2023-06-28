package com.sebasdev.apprestaurant_android.data.remote.api

import com.sebasdev.apprestaurant_android.data.remote.model.response.LoginResponse
import com.sebasdev.apprestaurant_android.data.remote.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
  @FormUrlEncoded
  @POST("auth/login")
  suspend fun login(
    @Field("email") email: String,
    @Field("password") password: String
  ): Response<LoginResponse>

  @FormUrlEncoded
  @POST("auth/register")
  suspend fun register(
    @Field("fullname") fullname: String,
    @Field("email") email: String,
    @Field("password") password: String,
    @Field("phone") phone: String
  ): Response<RegisterResponse>
}