package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.ui.components.Header
import com.sebasdev.apprestaurant_android.ui.components.ListCardProducts
import com.sebasdev.apprestaurant_android.ui.components.NavigationBottomBar
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.FavoriteViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
fun FavoriteScreen(
  navigationController: NavHostController,
  preferencesDataStore: PreferencesDataStore,
  favoriteViewModel: FavoriteViewModel,
  productDetailViewModel: ProductDetailViewModel
) {
  val productsFavorite: List<Product> by favoriteViewModel.productsFavorite.observeAsState(listOf())

  LaunchedEffect(true) {
    favoriteViewModel.getProductsFavorite()
  }

  Scaffold(
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Header("Favoritos", true, navigationController, preferencesDataStore)
        Spacer(modifier = Modifier.size(20.dp))
        ListCardProducts(navigationController, products = productsFavorite, true, productDetailViewModel)
      }
    },
    bottomBar = { NavigationBottomBar(navigationController, 1) }
  )
}