package com.sebasdev.apprestaurant_android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.screens.auth.LoginScreen
import com.sebasdev.apprestaurant_android.ui.screens.auth.RegisterScreen
import com.sebasdev.apprestaurant_android.ui.screens.main.CartScreen
import com.sebasdev.apprestaurant_android.ui.screens.main.FavoriteScreen
import com.sebasdev.apprestaurant_android.ui.screens.main.HomeScreen
import com.sebasdev.apprestaurant_android.ui.screens.main.OrderHistoryScreen
import com.sebasdev.apprestaurant_android.ui.screens.main.OrderRecentsScreen
import com.sebasdev.apprestaurant_android.ui.screens.main.ProductDetailScreen
import com.sebasdev.apprestaurant_android.ui.screens.main.ProfileScreen
import com.sebasdev.apprestaurant_android.ui.screens.main.ProfileUpdateScreen
import com.sebasdev.apprestaurant_android.ui.screens.main.ResultSearchScreen
import com.sebasdev.apprestaurant_android.ui.viewmodel.auth.LoginViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.auth.RegisterViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.CartViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.FavoriteViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.HomeViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.OrderHistoryViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.OrderRecentsViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.OrderViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProfileUpdateViewModel

@Composable
fun AppNavigation(preferencesDataStore: PreferencesDataStore) {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = AppScreens.CartScreen.route) {
    composable(route = AppScreens.LoginScreen.route) {
      LoginScreen(navController, LoginViewModel(preferencesDataStore))
    }

    composable(route = AppScreens.RegisterScreen.route) {
      RegisterScreen(navController, RegisterViewModel())
    }

    composable(route = AppScreens.HomeScreen.route) {
      HomeScreen(
        navController,
        HomeViewModel(preferencesDataStore),
        preferencesDataStore,
        ProductDetailViewModel(preferencesDataStore)
      )
    }

    composable(route = AppScreens.FavoriteScreen.route) {
      FavoriteScreen(
        navController,
        preferencesDataStore,
        FavoriteViewModel(preferencesDataStore),
        ProductDetailViewModel(preferencesDataStore)
      )
    }

    composable(route = AppScreens.CartScreen.route) {
      CartScreen(
        navController,
        preferencesDataStore,
        CartViewModel(preferencesDataStore),
        OrderViewModel(preferencesDataStore)
      )
    }

    composable(route = AppScreens.ProfileScreen.route) {
      ProfileScreen(navController, preferencesDataStore)
    }

    composable(route = AppScreens.ProfileUpdateScreen.route) {
      ProfileUpdateScreen(
        navController,
        preferencesDataStore,
        ProfileUpdateViewModel(preferencesDataStore)
      )
    }

    composable(route = AppScreens.OrderHistoryScreen.route) {
      OrderHistoryScreen(navController, OrderHistoryViewModel(preferencesDataStore))
    }

    composable(route = AppScreens.OrderRecentsScreen.route) {
      OrderRecentsScreen(navController, OrderRecentsViewModel(preferencesDataStore))
    }

    composable(
      route = AppScreens.ProductDetailScreen.route,
      arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
      ProductDetailScreen(
        navController,
        ProductDetailViewModel(preferencesDataStore),
        backStackEntry.arguments?.getString("id") ?: "0"
      )
    }

    composable(
      route = AppScreens.ResultSearchScreen.route,
      arguments = listOf(navArgument("valueSearch") { type = NavType.StringType })
    ) { backStackEntry ->
      ResultSearchScreen(
        navController,
        HomeViewModel(preferencesDataStore),
        backStackEntry.arguments?.getString("valueSearch") ?: "",
        ProductDetailViewModel(preferencesDataStore)
      )
    }
  }
}