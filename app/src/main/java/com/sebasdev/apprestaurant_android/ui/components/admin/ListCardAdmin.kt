package com.sebasdev.apprestaurant_android.ui.components.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.Supplier
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel

@Composable
fun ListCardAdmin(
  products: List<Product>,
  suppliers: List<Supplier>,
  navigationController: NavHostController,
  productDetailViewModel: ProductDetailViewModel,
  option: String,
) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(1),
    verticalArrangement = Arrangement.spacedBy(15.dp),
    contentPadding = PaddingValues(bottom = 50.dp),
    horizontalArrangement = Arrangement.spacedBy(15.dp),
    content = {
      if (option == "products") {
        if (products.isEmpty()) {
          items(1) {
            Text("No hay productos")
          }
        } else {
          items(products.size) {
            CardAdmin(
              navigationController, productDetailViewModel, products[it], null
            )
          }
        }

      } else {
        if (suppliers.isEmpty()) {
          items(1) {
            Text("No hay productos")
          }
        } else {
          items(suppliers.size) {
            CardAdmin(
              navigationController, productDetailViewModel, null, suppliers[it]
            )
          }
        }
      }
    }
  )
}