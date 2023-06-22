package com.sebasdev.apprestaurant_android.ui.screens.admin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.Supplier
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.components.admin.ListCardAdmin
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.HomeViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagmentListItemAdminScreen(
  navigationController: NavHostController,
  preferencesDataStore: PreferencesDataStore,
  homeViewModel: HomeViewModel,
  productDetailViewModel: ProductDetailViewModel,
  option: String
) {
  val products: List<Product> by homeViewModel.products.observeAsState(initial = emptyList())
  val suppliers: List<Supplier> by homeViewModel.suppliers.observeAsState(initial = emptyList())

  LaunchedEffect(true) {
    if (option == "products") {
      homeViewModel.getProducts()
    } else {
      homeViewModel.getSuppliers()
    }

  }

  Scaffold(
    topBar = {
      MyTopAppBar(
        navigationController,
        text = if (option == "products") "Gestionar productos" else "Gestionar proveedores"
      )
    },
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Spacer(modifier = Modifier.size(60.dp))
        IconButton(
          onClick = {
            navigationController.navigate(
              AppScreens.ManagmentItemProductScreen.createRoute(
                "0",
                "add"
              )
            )
          }, colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = ColorWhiteCustom
          ),
          modifier = Modifier.clip(RoundedCornerShape(50.dp))
        ) {
          Icon(imageVector = Icons.Outlined.Add, contentDescription = "add")
        }
        Spacer(modifier = Modifier.size(20.dp))
        if (option == "products") {
          if (products.isEmpty()) {
            Column(
              modifier = Modifier.fillMaxSize(),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              CircularProgressIndicator()
            }
          } else {
            Products(navigationController, productDetailViewModel, products, listOf())
          }
        }

        if (option == "suppliers") {
          if (suppliers.isEmpty()) {
            Column(
              modifier = Modifier.fillMaxSize(),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              CircularProgressIndicator()
            }
          } else {
            Products(navigationController, productDetailViewModel, listOf(), suppliers)
          }
        }


      }
    },
  )
}

@Composable
fun Products(
  navigationController: NavHostController,
  productDetailViewModel: ProductDetailViewModel,
  products: List<Product>,
  suppliers: List<Supplier>,
) {
  if (products.isNotEmpty()) {
    ListCardAdmin(products, listOf(), navigationController, productDetailViewModel, "products")
  } else {
    ListCardAdmin(listOf(), suppliers, navigationController, productDetailViewModel, "suppliers")
  }

}