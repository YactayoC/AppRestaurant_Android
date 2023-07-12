package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
  navigationController: NavHostController,
  productDetailViewModel: ProductDetailViewModel,
  id: String
) {
  val context = LocalContext.current
  val messageResponse: String by productDetailViewModel.messageResponse.observeAsState(initial = "")
  val messageResponseCart: String by productDetailViewModel.messageResponseCart.observeAsState(initial = "")
  val isFavorite: Boolean by productDetailViewModel.isFavorite.observeAsState(initial = false)
  val product: Product by productDetailViewModel.product.observeAsState(
    initial = Product(
      "",
      "",
      "",
      0.0,
      "",
      "",
      "",
      "",
      0,
    )
  )

  LaunchedEffect(isFavorite) {
    productDetailViewModel.getProduct(id)
  }

  LaunchedEffect(messageResponse) {
    if (messageResponse.isNotEmpty()) {
      Toast.makeText(context, messageResponse, Toast.LENGTH_SHORT).show()
      productDetailViewModel.clearMessageResponse()
    }
  }

  LaunchedEffect(messageResponseCart) {
    if (messageResponseCart.isNotEmpty()) {
      Toast.makeText(context, messageResponseCart, Toast.LENGTH_SHORT).show()
      productDetailViewModel.clearMessageResponseCart()
    }
  }

  Scaffold(
    topBar = {
      MyTopAppBar(navigationController, "Detalles del producto")
    },
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Spacer(modifier = Modifier.size(60.dp))
        ImageProduct(product)
        Spacer(modifier = Modifier.size(20.dp))
        TitleProduct(product)
        Spacer(modifier = Modifier.size(20.dp))
        DescriptionProduct(product)
        Spacer(modifier = Modifier.size(20.dp))
        // RelatedProducts(navigationController)
        Spacer(modifier = Modifier.size(30.dp))
        ButtonAddToCart() { productDetailViewModel.addToCart(product) }
      }
    },
    floatingActionButton = {
      FloatingButtonAddFavorite(isFavorite) { productDetailViewModel.onClickButtonFavorite(id) }
    }
  )
}

@Composable
fun ImageProduct(product: Product) {
  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    Image(
      painter = rememberImagePainter(data = product.image, builder = {
        size(OriginalSize)
        crossfade(true)
      }),
      contentDescription = "producto detalle",
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp)),
      contentScale = ContentScale.FillWidth
    )
  }
}

@Composable
fun TitleProduct(product: Product) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = product.name.capitalize(), fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(200.dp))
    Text(text = "S/. ${product.price}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
  }
}

@Composable
fun DescriptionProduct(product: Product) {
  Text(
    text = product.description,
    fontSize = 16.sp,
    color = MaterialTheme.colorScheme.secondary
  )
}

@Composable
fun ButtonAddToCart(onAddCart: () -> Unit) {
  Button(
    onClick = { onAddCart() },
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(5.dp),
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = ColorWhiteCustom
    )
  ) {
    Text(text = "Agregar al carrito", fontSize = 16.sp, modifier = Modifier.padding(5.dp))
  }
}

@Composable
fun FloatingButtonAddFavorite(isFavorite: Boolean, onClickButtonFavorite: () -> Unit) {
  FloatingActionButton(
    onClick = { onClickButtonFavorite() },
    containerColor = MaterialTheme.colorScheme.primary
  ) {
    Icon(
      imageVector =
      if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
      contentDescription = "favorite"
    )
  }
}

//@Composable
//fun RelatedProducts(navigationController: NavHostController) {
//  Column {
//    Row(
//      modifier = Modifier.fillMaxWidth(),
//      verticalAlignment = Alignment.CenterVertically,
//      horizontalArrangement = Arrangement.SpaceBetween,
//    ) {
//      Text(
//        text = "Productos relacionados",
//        fontSize = 22.sp,
//        fontWeight = FontWeight.ExtraBold,
//        color = ColorBlackCustom
//      )
//      Text(
//        text = "Ver todos",
//        fontSize = 15.sp,
//        fontWeight = FontWeight.ExtraBold,
//        color = MaterialTheme.colorScheme.primary,
//        modifier = Modifier.clickable { }
//      )
//    }
//    Spacer(modifier = Modifier.size(20.dp))
//    LazyHorizontalGrid(
//      rows = GridCells.Fixed(1),
//      horizontalArrangement = Arrangement.spacedBy(10.dp),
//      modifier = Modifier.height(140.dp),
//      content = {
//      items(5) {
//        CardProduct(navigationController, imageWidth = 150, false)
//      }
//    })
//  }
//}