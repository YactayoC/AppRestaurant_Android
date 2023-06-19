package com.sebasdev.apprestaurant_android.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorBlackCustom
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel
import java.util.Locale


@Composable
fun CardProduct(
  navigationController: NavHostController,
  showOptions: Boolean = true,
  product: Product,
  productDetailViewModel: ProductDetailViewModel
) {
  val context = LocalContext.current
  val messageResponseCart: String by productDetailViewModel.messageResponseCart.observeAsState(initial = "")

  LaunchedEffect(messageResponseCart) {
    if (messageResponseCart.isNotEmpty()) {
      Toast.makeText(context, messageResponseCart, Toast.LENGTH_SHORT).show()
      productDetailViewModel.clearMessageResponseCart()
    }
  }

  Card(
    colors = CardDefaults.cardColors(
      containerColor = ColorWhiteCustom,
    ),
    modifier = Modifier.clickable {
      navigationController.navigate(AppScreens.ProductDetailScreen.createRoute(product._id))
    },
    elevation = CardDefaults.cardElevation(5.dp),
  ) {

    Box(
      modifier = Modifier
        .size(200.dp, 100.dp)

    ) {
      Image(
        painter = rememberImagePainter(data = product.image, builder = {
          size(OriginalSize)
          crossfade(true)
        }),
        contentDescription = "product",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth()
      )
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
      Text(
        text = product.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
        color = ColorBlackCustom,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 5.dp)
      )
      if (showOptions) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Text(
            text = "S/. ${product.price}",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp
          )
          IconButton(
            onClick = { productDetailViewModel.addToCart(product) },
            modifier = Modifier
              .clip(RoundedCornerShape(5.dp))
              .size(28.dp),
            colors = IconButtonDefaults.iconButtonColors(
              containerColor = MaterialTheme.colorScheme.primary
            )
          ) {
            Icon(
              imageVector = Icons.Outlined.Add,
              contentDescription = "add",
              tint = ColorWhiteCustom
            )
          }
        }
      }
    }
  }
}
