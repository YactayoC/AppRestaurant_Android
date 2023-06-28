package com.sebasdev.apprestaurant_android.ui.navigation

sealed class AppScreens(val route: String) {
  // Auth
  object LoginScreen : AppScreens("login")
  object RegisterScreen : AppScreens("register")

  // Main
  object HomeScreen : AppScreens("home")
  object FavoriteScreen : AppScreens("favorite")
  object CartScreen : AppScreens("cart")
  object ProfileScreen : AppScreens("profile")
  object ProfileUpdateScreen : AppScreens("profileUpdate")
  object OrderHistoryScreen : AppScreens("orderHistory")
  object OrderRecentsScreen : AppScreens("orderRecents")
  object ContactScreen : AppScreens("contactScreen")

  // Detail
  object ProductDetailScreen : AppScreens("productDetail/{id}") {
    fun createRoute(id: String) = "productDetail/$id"
  }

  object ResultSearchScreen : AppScreens("resultSearch/{valueSearch}") {
    fun createRoute(valueSearch: String) = "resultSearch/$valueSearch"
  }

  object OrderDetailScreen : AppScreens("orderDetail/{id}") {
    fun createRoute(id: String) = "orderDetail/$id"
  }

  // Admin
  object HomeAdminScreen : AppScreens("homeAdmin")
  object ManagmentAdminScreen : AppScreens("managmentAdmin")
  object ManagmentProductsAdminScreen : AppScreens("managmentProductsAdmin")
  object ManagmentSupplierAdminScreen : AppScreens("managmentSupplierAdmin")
  object ManagmentListItemAdminScreen :
    AppScreens("managmentListProductsAdminScreen/{option}") {
    fun createRoute(option: String) =
      "managmentListProductsAdminScreen/$option"
  }

  object ManagmentItemProductScreen : AppScreens("managmentItemProduct/{id}/{option}") {
    fun createRoute(id: String, option: String) = "managmentItemProduct/$id/$option"
  }

  object ManagmentItemSupplierScreen : AppScreens("managmentItemSupplier/{id}/{option}") {
    fun createRoute(id: String, option: String) = "managmentItemSupplier/$id/$option"
  }
}
