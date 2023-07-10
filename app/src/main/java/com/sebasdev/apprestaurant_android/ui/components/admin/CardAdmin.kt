package com.sebasdev.apprestaurant_android.ui.components.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.Supplier
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CardAdmin(
  navigationController: NavHostController,
  productDetailViewModel: ProductDetailViewModel,
  product: Product?,
  supplier: Supplier?,
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primary
    )
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Card(
        colors = CardDefaults.cardColors(
          containerColor = ColorWhiteCustom
        ),
        modifier = Modifier.width(300.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          Image(
            painter = rememberImagePainter(
              data = product?.image ?: "https://www.esan.edu.pe/images/blog/2021/12/17/1500x844-requisitos-proveedores-17-12-2021.jpg",
              builder = {
                size(OriginalSize)
                crossfade(true)
              }),
            contentDescription = "Products",
            modifier = Modifier
              .height(100.dp)
              .width(120.dp)
              .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
          )
          if (product != null) {
            Text(text = "${product?.name?.capitalize()}", color = Color.Black, fontSize = 20.sp)
          }
          if (supplier != null) {
            Text(text = "${supplier?.name?.capitalize()}", color = Color.Black, fontSize = 20.sp)
          }

        }
      }

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Icon(
          imageVector = Icons.Outlined.Edit,
          contentDescription = "Next",
          Modifier
            .size(30.dp)
            .clickable {
              if (product != null) {
                navigationController.navigate(
                  AppScreens.ManagmentItemProductScreen.createRoute(
                    product._id, "update"
                  )
                )
              }

              if (supplier != null) {
                navigationController.navigate(
                  AppScreens.ManagmentItemSupplierScreen.createRoute(
                    supplier._id, "update"
                  )
                )
              }
            }
        )
      }
    }
  }
}