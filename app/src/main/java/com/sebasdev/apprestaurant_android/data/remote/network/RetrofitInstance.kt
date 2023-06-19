package com.sebasdev.apprestaurant_android.data.remote.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
  fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl("http://192.168.1.12:3000/api/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }
}