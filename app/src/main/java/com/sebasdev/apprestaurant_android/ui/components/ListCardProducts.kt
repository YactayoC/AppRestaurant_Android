package com.sebasdev.apprestaurant_android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel


@Composable
fun ListCardProducts(
  navigationController: NavHostController,
  products: List<Product>,
  isAll: Boolean? = false,
  productDetailViewModel: ProductDetailViewModel
) {
  val randomProducts = products.take(5)
  val productsShow = if (isAll == true) products else randomProducts

  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    verticalArrangement = Arrangement.spacedBy(15.dp),
    contentPadding = PaddingValues(bottom = 50.dp),
    horizontalArrangement = Arrangement.spacedBy(15.dp),
    content = {
      if (products.isEmpty()) {
        items(1) {
          Text("No hay productos")
        }
      } else {
        items(productsShow.size) {
          CardProduct(navigationController, true, products[it], productDetailViewModel)
        }
      }
    }
  )
}