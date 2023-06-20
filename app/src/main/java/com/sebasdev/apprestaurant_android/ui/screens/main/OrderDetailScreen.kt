package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.domain.model.Order
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.User
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.OrderDetailViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
  navigationController: NavHostController,
  orderDetailViewModel: OrderDetailViewModel,
  id: String
) {
  val order: Order by orderDetailViewModel.order.observeAsState(
    Order(
      _id = "",
      user = User(
        _id = "",
        fullname = "",
        email = "",
        password = "",
        direction = null,
        phone = "",
        profile = "",
        favoriteProducts = emptyList(),
        createdAt = "",
        updatedAt = ""
      ),
      product = Product(
        _id = "",
        name = "",
        description = "",
        price = 0.0,
        category = "",
        image = "",
        createdAt = "",
        updatedAt = "",
        quantity = null
      ),
      subtotal = 0.0,
      total = 0.0,
      quantity = 0,
      state = "",
      createdAt = "",
      updatedAt = ""
    )
  );

  LaunchedEffect(true) {
    orderDetailViewModel.getOrderId(id)
  }

  Scaffold(
    topBar = {
      MyTopAppBar(navigationController, "Detalles del pedido")
    },
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Spacer(modifier = Modifier.size(60.dp))
        State(order)
        Spacer(modifier = Modifier.size(40.dp))
        OrderView(order)
        Spacer(modifier = Modifier.size(40.dp))
        Summary(order)
      }
    },
  )
}

@Composable
fun State(order: Order) {
  val color = when (order.state) {
    "en cocina" -> Color(0xFFdc8620)
    "en camino" -> Color(0xFF20BADC)
    "entregado" -> Color(0xFF12FDC20)
    "cancelado" -> Color(0xFFDC2020)
    else -> Color(0xFFdc8620)
  }


  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp),
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = "Estado del pedido:", fontSize = 18.sp)
      TextButton(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
          containerColor = color,
        ),
      ) {
        Text(text = "${order.state.capitalize()}", fontSize = 18.sp)
      }
    }
    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(
        containerColor = Color(0xFFEBEAEA),
      )
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(15.dp)
      ) {
        Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "location")
        Text(text = "${order.user.direction}", fontSize = 18.sp)
      }
    }

    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(
        containerColor = Color(0xFFEBEAEA),
      ),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(15.dp)
      ) {
        Icon(imageVector = Icons.Outlined.Phone, contentDescription = "phone")
        Text(text = "+51 ${order.user.phone}", fontSize = 18.sp)
      }
    }

    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(
        containerColor = Color(0xFFEBEAEA),
      ),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(15.dp)
      ) {
        Icon(imageVector = Icons.Outlined.CreditCard, contentDescription = "card")
        Text(text = "Metodo de pago: Bancario", fontSize = 18.sp)
      }
    }
  }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun OrderView(order: Order) {
  Text(text = "Pedido #${order._id}", fontSize = 25.sp, fontWeight = FontWeight.Bold)
  Spacer(modifier = Modifier.size(20.dp))
  Card(
    modifier = Modifier
      .fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = Color.Transparent,
    )
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(20.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        painter = rememberImagePainter(
          data = order.product.image,
          builder = {
            size(OriginalSize)
            crossfade(true)
          }),
        contentDescription = "order",
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .width(100.dp)
          .clip(RoundedCornerShape(10.dp))
      )
      Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
      ) {
        Text(text = "${order.product.name.capitalize()}", fontSize = 18.sp)
        Row(
          horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          Text(text = "S/ ${order.product.price}", fontSize = 18.sp)
          Text(text = "Cantidad: ${order.quantity}", fontSize = 18.sp)
        }
      }
    }
  }
}

@Composable
fun Summary(order: Order) {
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp),
  ) {
    Text(text = "Resumen del pedido", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = "Subtotal")
      Text(text = "S/ ${order.subtotal}")
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = "Costo de envio")
      Text(text = "S/ 7.50")
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = "Total", fontWeight = FontWeight.Bold, fontSize = 20.sp)
      Text(text = "S/ ${order.total}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
  }

}