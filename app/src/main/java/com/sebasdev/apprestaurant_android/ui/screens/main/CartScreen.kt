package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.ui.components.Header
import com.sebasdev.apprestaurant_android.ui.components.ModalPay
import com.sebasdev.apprestaurant_android.ui.components.NavigationBottomBar
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.theme.ColorBlackCustom
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.CartViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.OrderViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
  navigationController: NavHostController,
  preferencesDataStore: PreferencesDataStore,
  cartViewModel: CartViewModel,
  orderViewModel: OrderViewModel
) {
  val productsCart: List<Product> by cartViewModel.products.observeAsState(initial = emptyList())
  val subTotalAmount: Double by cartViewModel.subTotalAmount.observeAsState(initial = 0.0)
  val message: String by cartViewModel.message.observeAsState(initial = "")
  val messageResponse: String by orderViewModel.messageResponse.observeAsState(initial = "")
  val showModal: Boolean by cartViewModel.showModal.observeAsState(initial = false)
  val context = LocalContext.current

  LaunchedEffect(true) {
    cartViewModel.getCartProducts()
  }

  LaunchedEffect(message) {
    if (message.isNotEmpty()) {
      Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
      cartViewModel.clearMessage()
    }
  }

  LaunchedEffect(messageResponse) {
    if (messageResponse.isNotEmpty()) {
      Toast.makeText(context, messageResponse, Toast.LENGTH_SHORT).show()
      orderViewModel.clearMessage()
    }
  }

  Scaffold(
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Header("Carrito", true, navigationController, preferencesDataStore)
        Spacer(modifier = Modifier.size(20.dp))
        ProductsCart(productsCart, cartViewModel)
        //ButtonPay(subTotalAmount) { orderViewModel.addOrder(subTotalAmount)}
        ButtonPay(subTotalAmount) { cartViewModel.setShowModal(true) }

        if (showModal) {
          ModalPay(
            cartViewModel = cartViewModel,
            subTotal = subTotalAmount,
            orderViewModel = orderViewModel
          )
        }

      }
    },
    bottomBar = { NavigationBottomBar(navigationController, 2) }
  )
}


@Composable
fun ProductsCart(productsCart: List<Product>, cartViewModel: CartViewModel) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(1),
    verticalArrangement = Arrangement.spacedBy(15.dp),
    contentPadding = PaddingValues(bottom = 50.dp),
    horizontalArrangement = Arrangement.spacedBy(15.dp),
    modifier = Modifier.height(450.dp),
    content = {
      items(productsCart.size) {
        Card(
          colors = CardDefaults.cardColors(
            containerColor = ColorWhiteCustom,
          ),
          elevation = CardDefaults.cardElevation(5.dp),
        ) {
          GroupButtons(productsCart[it], cartViewModel)
        }
      }
    })
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun GroupButtons(product: Product, cartViewModel: CartViewModel) {
  Row(
    Modifier
      .fillMaxWidth()
      .padding(15.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Image(
      painter = rememberImagePainter(data = product.image, builder = {
        size(OriginalSize)
        crossfade(true)
      }),
      contentDescription = "cart",
      Modifier
        .width(100.dp)
        .height(80.dp)
        .clip(RoundedCornerShape(5.dp)),
      contentScale = ContentScale.Crop
    )

    Column(
      modifier = Modifier
        .weight(0.35f)
        .padding(start = 10.dp),
    ) {
      Text(text = product.name.capitalize(), color = ColorBlackCustom, fontSize = 16.sp)
      Text(
        text = "S/. ${product.price}",
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 14.sp
      )
    }
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(15.dp),
      modifier = Modifier.weight(0.3f)
    ) {
      IconButton(
        onClick = { cartViewModel.increaseQuantity(product) },
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
      Text(text = "${product.quantity}", color = ColorBlackCustom, fontSize = 16.sp)
      IconButton(
        onClick = { cartViewModel.decreaseQuantity(product) },
        modifier = Modifier
          .clip(RoundedCornerShape(5.dp))
          .size(28.dp),
        colors = IconButtonDefaults.iconButtonColors(
          containerColor = MaterialTheme.colorScheme.primary
        )
      ) {
        Icon(
          imageVector = Icons.Outlined.Remove,
          contentDescription = "minus",
          tint = ColorWhiteCustom
        )
      }
    }
  }
}

@Composable
fun ButtonPay(subTotalAmount: Double, showModal: (Boolean) -> Unit) {
  Column(
    verticalArrangement = Arrangement.Bottom,
    modifier = Modifier
      .fillMaxSize()
      .padding(bottom = 60.dp)
  ) {
    Card(
      elevation = CardDefaults.cardElevation(5.dp),
      colors = CardDefaults.cardColors(
        containerColor = ColorWhiteCustom,
      ),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column {
        Row(
          Modifier
            .fillMaxWidth()
            .padding(15.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(text = "Subtotal", color = ColorBlackCustom, fontSize = 20.sp)
          Text(text = "S/. ${subTotalAmount ?: 0.0}", color = ColorBlackCustom, fontSize = 20.sp)
        }

        Button(
          enabled = subTotalAmount != 0.0,
          onClick = { showModal(true) },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(5.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = ColorWhiteCustom
          )
        ) {
          Text(text = "Pagar ahora", fontSize = 15.sp, modifier = Modifier.padding(5.dp))
        }
      }
    }
  }
}