package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.ui.components.Header
import com.sebasdev.apprestaurant_android.ui.components.InputSearch
import com.sebasdev.apprestaurant_android.ui.components.ListCardProducts
import com.sebasdev.apprestaurant_android.ui.components.NavigationBottomBar
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorBlackCustom
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.HomeViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  navigationController: NavHostController,
  homeViewModel: HomeViewModel,
  preferencesDataStore: PreferencesDataStore,
  productDetailViewModel: ProductDetailViewModel
) {
  val products: List<Product> by homeViewModel.products.observeAsState(initial = emptyList())
  val categories: List<Category> by homeViewModel.categories.observeAsState(initial = emptyList())
  val valueSearch: String by homeViewModel.valueSearch.observeAsState(initial = "")

  LaunchedEffect(true) {
    homeViewModel.getProducts()
    homeViewModel.getCategories()
  }

  Scaffold(
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Header("Inicio", true, navigationController, preferencesDataStore)
        Spacer(modifier = Modifier.size(20.dp))
        Description()
        Spacer(modifier = Modifier.size(20.dp))
        InputSearch(valueSearch, navigationController) { homeViewModel.onValueSearchChanged(it) }
        Spacer(modifier = Modifier.size(20.dp))
        Products(navigationController, products, categories, productDetailViewModel)
      }
    },
    bottomBar = { NavigationBottomBar(navigationController, 0) }
  )
}

@Composable
fun Description() {
  Column {
    Row {
      Text(text = "Obten ", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
      Text(
        text = "la mejor comida",
        fontSize = 28.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.primary
      )
    }
    Text(text = "a tu alrededor", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
  }
}

@Composable
fun Products(
  navigationController: NavHostController,
  products: List<Product>,
  categories: List<Category>,
  productDetailViewModel: ProductDetailViewModel
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(15.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        text = "Productos principales",
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold,
        color = ColorBlackCustom
      )
      Text(
        text = "Ver todos",
        fontSize = 15.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clickable {
          navigationController.navigate(AppScreens.ResultSearchScreen.createRoute("todas"))
        }
      )
    }

    if (products.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(15.dp),
        contentAlignment = Alignment.Center
      ) {
        CircularProgressIndicator()
      }
    } else {
      CategoriesItem(navigationController, categories)
      ListCardProducts(navigationController, products, productDetailViewModel = productDetailViewModel)
    }
  }
}

@Composable
fun CategoriesItem(navigationController: NavHostController, categories: List<Category>) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Button(
      onClick = { },
      elevation = ButtonDefaults.buttonElevation(10.dp),
      shape = MaterialTheme.shapes.medium
    ) {
      Text(text = "Todas", color = ColorWhiteCustom)
    }

    LazyRow(verticalAlignment = Alignment.CenterVertically,
      contentPadding = PaddingValues(10.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp), content = {
        items(categories.size) {
          Button(
            onClick = {
              navigationController.navigate(AppScreens.ResultSearchScreen.createRoute(categories[it].category))
            },
            elevation = ButtonDefaults.buttonElevation(4.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
              containerColor = ColorWhiteCustom,
            )
          ) {
            Text(text = categories[it].category, color = MaterialTheme.colorScheme.tertiary)
          }
        }
      })
  }
}