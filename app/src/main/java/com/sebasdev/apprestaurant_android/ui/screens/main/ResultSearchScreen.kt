package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.ui.components.ListCardProducts
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.HomeViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultSearchScreen(
  navigationController: NavHostController,
  homeViewModel: HomeViewModel,
  valueSearch: String,
  productDetailViewModel: ProductDetailViewModel
) {
  val products: List<Product> by homeViewModel.products.observeAsState(listOf())
  val filteredProducts = products.filter {
    it.name.contains(valueSearch, ignoreCase = true)
  }.let { filteredByName ->
    filteredByName.ifEmpty {
      products.filter {
        it.category.contains(valueSearch, ignoreCase = true)
      }
    }
  }

  LaunchedEffect(true) {
    homeViewModel.getProducts()
  }

  Scaffold(
    topBar = {
      MyTopAppBar(navigationController, "Resultados de la busqueda")
    },
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Spacer(modifier = Modifier.size(60.dp))
        Text(text = "Resultados de la busqueda: $valueSearch")
        Spacer(modifier = Modifier.size(20.dp))
        ListCardProducts(
          navigationController,
          if (valueSearch == "todas") products else filteredProducts,
          true,
          productDetailViewModel = productDetailViewModel
        )
      }
    },
  )
}